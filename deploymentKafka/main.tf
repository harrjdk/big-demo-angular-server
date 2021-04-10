// This doesn't work with this image but I kept it for reference
// in practice you'd need to set up a zookeeper, broker, etc.
// which is a little out of the scope of this but this tf plan 
// still shows env vars, services etc.

provider "kubernetes" {
        config_context_cluster   = "minikube"
        config_path = "~/.kube/config"
}

resource "kubernetes_deployment" "kafka" {
    metadata {
        name = "kafka"
        labels = {
          "app" = "kafka"
        }
    }

    spec {
      replicas = 1

      selector {
          match_labels = {
              "app" = "kafka"
          }
      }

      template {
        metadata {
            labels = {
              "app" = "kafka"
            }
        }

        spec {
          container {
              image = "docker.io/landoop/fast-data-dev"
              name = "kafka"

              resources {
                  limits = {
                      cpu = "2.0"
                      memory = "3072Mi"
                  }

                  requests = {
                      cpu = "1.0"
                      memory = "2048Mi"
                  }
              }

              env {
                  name = "ADV_HOST"
                  value = "localhost"
              }

              env {
                name = "SAMPLEDATA"
                value = 1
              }

              env {
                name = "RUNNING_SAMPLEDATA"
                value = 1
              }

              env {
                name = "RUNTESTS"
                value = 0
              }
          }
        }
      }
    }
}

resource "kubernetes_service" "kafka_service" {
    metadata {
      name = "kafka-service"
    }
    spec {
      selector = {
          "app" = kubernetes_deployment.kafka.metadata.0.labels.app
      }

      port {
          name = "kafka"
          port = 9092
          target_port = 9092
      }

      port {
        name = "webui"
        port = 3030
        target_port = 3030
      }

      type = "NodePort"
    }
}