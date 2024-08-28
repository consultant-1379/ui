#!/usr/bin/env bash


set -e

docker build -t armdocker.rnd.ericsson.se/aia/bps-engine-base:0.0.1 .
docker push armdocker.rnd.ericsson.se/aia/bps-engine-base:0.0.1
