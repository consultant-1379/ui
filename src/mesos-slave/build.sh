#!/usr/bin/env bash


set -e

docker build -t armdocker.rnd.ericsson.se/aia/tmp-mesos-slave:latest .
docker push armdocker.rnd.ericsson.se/aia/tmp-mesos-slave:latest
