#!/usr/bin/env bash

echo "launch all from docker-compose...."
docker-compose up -d;
echo " "

echo "sleep for containers waiting..."
sleep 10
echo " "

echo "create users topic..."
docker-compose exec broker kafka-topics \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic users;
echo " "

echo "create pageviews topic..."
docker-compose exec broker kafka-topics \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic pageviews
echo " "

echo "upload connector config for pageviews topic..."
curl -X POST -H 'Content-Type: application/json' \
  --data @connector_pageviews_cos.config \
  http://localhost:8083/connectors
echo " "

echo "upload connector config for users topic..."
curl -X POST -H 'Content-Type: application/json' \
  --data @connector_users_cos.config \
  http://localhost:8083/connectors
echo " "




