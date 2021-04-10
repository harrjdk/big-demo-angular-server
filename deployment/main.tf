provider "kubernetes" {
        config_context_cluster   = "minikube"
        config_path = "~/.kube/config"
}

resource "kubernetes_namespace" "bigdemo_server" {
    metadata {
        annotations = {
            name = "bigdemo-server"
        }
        labels = {
          "label" = "bigdemo-server"
        }

        name = "bigdemo-server-namespace"
    }
}

resource "kubernetes_deployment" "bigdemo_server_listener" {
    metadata {
        name = "bigdemo-listener"
        labels = {
          "app" = "bigdemo-listener"
        }
        namespace = "bigdemo-server-namespace"
    }

    spec {
      replicas = 2

      selector {
          match_labels = {
              "app" = "bigdemo-listener"
          }
      }

      template {
        metadata {
            labels = {
              "app" = "bigdemo-listener"
            }
        }

        spec {
          container {
              image = "dev.hornetshell/bigdemo-listener:latest"
              name = "bigdemo-listener"
              image_pull_policy = "IfNotPresent"

              resources {
                  limits = {
                      cpu = "1.0"
                      memory = "1536Mi"
                  }

                  requests = {
                      cpu = "0.5"
                      memory = "1024Mi"
                  }
              }

              // Change these to reflect the output of your $(minikube service postgres-service)
              env {
                name = "DB_SERVER"
                value = "192.168.49.2"
              }

              env {
                name = "DB_PORT"
                value = 32029
              }

              // Change these to reflect the output of your $(minikube service kafka-service)
              env {
                name = "BOOTSTRAP_SERVERS"
                value = "PLAINTEXT://192.168.49.2:31806"
              }

              liveness_probe {
                  http_get {
                      path = "/actuator/health"
                      port = 8080
                  }

                  // We need a long delay here because limited resources on make the changelog slow
                  initial_delay_seconds = 60
                  period_seconds = 5
              }
          }
        }
      }
    }
}

resource "kubernetes_service" "bigdemo_server" {
    metadata {
      name = "bigdemo-server-service"
    }
    spec {
        selector = {
          "app" = kubernetes_deployment.bigdemo_server_listener.metadata.0.labels.app
        }

        port {
          name = "http"
          port = 8080
          target_port = 8080
        }

        type = "NodePort"
    }
}