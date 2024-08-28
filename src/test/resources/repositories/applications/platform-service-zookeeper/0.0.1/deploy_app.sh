#!/bin/sh
curl -X POST http://localhost:8888/paas/v1/applications --data @pba.json -H "Content-Type: application/json"