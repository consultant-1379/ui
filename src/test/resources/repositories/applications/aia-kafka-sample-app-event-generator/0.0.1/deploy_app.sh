#!/bin/sh
if [ "$#" -ne 0 ]; then
  PAAS_SERVICE_BASE_URL=$1
else
  PAAS_SERVICE_BASE_URL="http://atrcxb2560-1.athtem.eei.ericsson.se:8888"
fi
echo "using $PAAS_SERVICE_BASE_URL as deployment target"

curl -X POST $PAAS_SERVICE_BASE_URL/paas/v1/applications --data @pba.json -H "Content-Type: application/json"