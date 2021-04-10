#!/bin/bash
set -e

psql <<-EOL
  CREATE USER "demo" WITH PASSWORD 'demo';
  CREATE DATABASE "demo";
  GRANT ALL PRIVILEGES ON DATABASE "demo" TO "demo";
  \c demo
  CREATE SCHEMA demo AUTHORIZATION demo;
EOL