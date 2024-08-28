#!/bin/sh

curl -X DELETE http://localhost:8888/paas/v1/applications --data @pba.json -H "Content-Type: application/json"