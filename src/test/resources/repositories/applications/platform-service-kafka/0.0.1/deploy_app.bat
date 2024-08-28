@echo off
IF "%1"=="" GOTO USE_DEFAULT

set PAAS_SERVICE_BASE_URL=%1
GOTO RUN

:USE_DEFAULT
set PAAS_SERVICE_BASE_URL=http://atrcxb2560-1.athtem.eei.ericsson.se:8888

:RUN
echo deploying via %PAAS_SERVICE_BASE_URL%

curl -X POST %PAAS_SERVICE_BASE_URL%/paas/v1/applications --data @pba.json -H "Content-Type: application/json"