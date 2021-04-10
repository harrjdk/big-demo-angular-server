provider "kubernetes" {
        config_context_cluster   = "minikube"
        config_path = "~/.kube/config"
}

resource "kubernetes_config_map" "postgres_start_script" {
  metadata {
    name = "postgres-start-script"
  }
  data = {
    "makedb.sh" = file("../makedb.sh")
  }
}

resource "kubernetes_deployment" "postgresql" {
    metadata {
        name = "postgresql"
        labels = {
          "app" = "postgresql"
        }
    }

    spec {
      replicas = 1

      selector {
          match_labels = {
              "app" = "postgresql"
          }
      }

      template {
        metadata {
            labels = {
              "app" = "postgresql"
            }
        }

        spec {
          container {
              image = "docker.io/postgres"
              name = "postgresql"

              resources {
                  limits = {
                      cpu = "3.0"
                      memory = "4096Mi"
                  }

                  requests = {
                      cpu = "2.0"
                      memory = "2048Mi"
                  }
              }

              volume_mount {
                mount_path = "/docker-entrypoint-initdb.d/"
                name = "startup"
              }

              env {
                name = "POSTGRES_HOST_AUTH_METHOD"
                value = "trust"
              }
          }

          volume {
            name = "startup"
            config_map {
                name = "postgres-start-script"
            }
          }
        }
      }
    }
}

resource "kubernetes_service" "postgres_service" {
    metadata {
      name = "postgres-service"
    }
    spec {
      selector = {
          "app" = kubernetes_deployment.postgresql.metadata.0.labels.app
      }

      port {
          name = "postgresql"
          port = 5432
          target_port = 5432
      }

      type = "NodePort"
    }
}