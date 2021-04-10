# Big-Demo-Angular (Server)

## About

This is a backend component for a "large" demo

## Running 

run podman compose or docker compose to bring up the kafka dev image and postgres server then run

`mvn clean install`

followed by

`java -jar -Dspring.profiles.active=kafka,stomp,jpa target/bigdemo.jar`

and navigate to `http://localhost:8080/`

## Terraform

In order to show an idea of how to deploy this, I've provided some rough terraform scripts. 
The kafka plan does not work because said image isn't designed for kubernetes, but it's 
preserved for an idea. The postgres deployment works and it is feasible to modify the docker 
file for the server application (remember to podman-remote/docker-remote the image for minikube) 
to not rely on kafka and just serve from the DB.
