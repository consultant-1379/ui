#!/bin/bash

set -e

# Script to be run from root folder

PORT=8181

if [ -n "$1" ]; then
    PORT=$1
fi

echo "----------------------------------"
echo "Starting server on port ${PORT}"
echo ""

cd client-ui/home

# start chrome "127.0.0.1:${PORT}"

#cdt2 serve -p ${PORT}

cdt2 serve -p ${PORT}                               \
        --proxy-config ../../script/vm-proxy.json
