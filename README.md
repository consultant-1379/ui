# development

## run the site locally at http://localhost:8888
mvn spring-boot:run


## full build
mvn clean package docker:build -DskipTests
docker push armdocker.rnd.ericsson.se/aia/aia-ui
docker pull armdocker.rnd.ericsson.se/aia/aia-ui


# deploy to server

ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui
docker stop aia-ui
docker rm aia-ui
docker run -d -p 8888:8888 --name aia-ui armdocker.rnd.ericsson.se/aia/aia-ui
ENDSSH

ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui
ENDSSH

ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui
docker stop aia-ui
docker rm aia-ui
docker run -d -p 8888:8888 --name aia-ui armdocker.rnd.ericsson.se/aia/aia-ui --zuul.routes.paasv1.url=http://atrcxb2560-1.athtem.eei.ericsson.se:5002/proxy/appmgr/v1
ENDSSH

ssh root@atrcxb2560-1.athtem.eei.ericsson.se
docker exec -it aia-ui bash

mkdir -p /data/ui/static/paas-ui/
vi /data/ui/static/paas-ui/config.js

## access the service
http://atrcxb2560-1.athtem.eei.ericsson.se:8888
http://atrcxb2560-1.athtem.eei.ericsson.se:8888/aia-ui/v1/serviceability/info


## app builder
https://github.com/coreybutler/nvm-windows
nvm install 0.10.26
nvm use 0.10.26
#### cdt2 package check-updates
cd oss-bss-paas-ui
./script/build.sh
./script/start-server.sh
http://localhost:8181



## Guo's Local Hacks
eval "$(docker-machine env default)"
docker-machine ssh default
docker exec -it javadev bash
cd /root/ericsson/src/ui

curl 'http://localhost:8888/templates/' -H 'Pragma: no-cache' -H 'Origin: http://client-test-cors-org.appspot.com' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36' -H 'Accept: */*' -H 'Referer: http://client-test-cors-org.appspot.com/client' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -v

PoC
https://play.ericsson.net/media/t/1_ysvv63jg
http://confluence-nam.lmera.ericsson.se/pages/viewpage.action?pageId=132001351


$ git clone ssh://eguoduu@gerrit.ericsson.se:29418/OSSBSSID/paas-sdk-poc
Cloning into 'paas-sdk-poc'...
fatal: Project not found: OSSBSSID/paas-sdk-poc
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.



docker pull armdocker.rnd.ericsson.se/proj_ossbss/sdk_rest:1.0.5
Pulling repository armdocker.rnd.ericsson.se/proj_ossbss/sdk_rest
Error: Status 400 trying to pull repository proj_ossbss/sdk_rest: "{\n  \"errors\" : [ {\n    \"status\" : 400,\n    \"message\" : \"Unsupported docker v1 repository request
 for 'docker-v2-global-local'\"\n  } ]\n}"



http://atrcxb2560-1.athtem.eei.ericsson.se:5001/explore-ui/


curl 'http://localhost:8888/proxy/paas/v1/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, access-control-allow-origin, content-type' --compressed -v

curl 'http://localhost:8888/proxy/paas/v1/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, access-control-allow-origin, content-type' --compressed -v
curl 'http://127.0.0.1:8181/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, access-control-allow-origin, content-type' --compressed -v

curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:5001/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v

curl 'http://localhost:8888/proxy/paas/v1/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v


curl 'http://localhost:8888/proxy/paas/v1/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:5001/applications/' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:5001/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v
curl 'http://localhost:8888/debug/fake/' -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:5001/applications/' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:5001/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v

curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:5001/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v


curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:5001/applications/' -X OPTIONS -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:5001' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v

curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:5001/templates/' -H 'Pragma: no-cache' -H 'Access-Control-Request-Method: POST' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:5001' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: */*' -H 'Cache-Control: no-cache' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Access-Control-Request-Headers: accept, content-type' --compressed -v



curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/proxy/paas/v1/templates/' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas-ui/index.html' -H 'X-Requested-With: XMLHttpRequest' -H 'Cookie: _pk_id.21.1678=8f07e29a2932a81d.1460110358.5.1461159563.1461056768.' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o sample-response-templates.json


curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/proxy/paas/v1/integration_points/treeview/' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas-ui/index.html' -H 'X-Requested-With: XMLHttpRequest' -H 'Cookie: _pk_id.21.1678=8f07e29a2932a81d.1460110358.5.1461159563.1461056768.' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o sample-response-integration-points-tree-view.json

curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/proxy/paas/v1/extension_points/parent=urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas-ui/index.html' -H 'X-Requested-With: XMLHttpRequest' -H 'Cookie: _pk_id.21.1678=8f07e29a2932a81d.1460110358.5.1461159563.1461056768.' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o sample-response-integration-point.json

curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/proxy/paas/v1/applications/' -H 'Pragma: no-cache' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:8888' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Content-Type: application/json' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Cache-Control: no-cache' -H 'X-Requested-With: XMLHttpRequest' -H 'Cookie: _pk_id.21.1678=8f07e29a2932a81d.1460110358.5.1461159563.1461056768.' -H 'Connection: keep-alive' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas-ui/index.html' --data-binary '{"pba":{"name":"a b c","version":"1.2.3","description":"abc","platform":[{"type":"marathon","version":"latest"}],"structure":{"instance_connectivity":[]},"pba_policies":[],"dependencies":[{"type":"extend","reference":"urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b","qualifier":""}]},"image":[{"name":"a","version":"1","description":"EPS implementation pattern Data extension","platform":[{"type":"marathon","version":"latest"}],"envars":[],"structure":{"instance_configuration":{"memory":{}}},"pba_policies":[],"pba_lifecycle":{"scale_triggers":[{"kpi":"response_time","value":60},{"kpi":"cpu","value":"90"}]},"dependencies":[{"type":"parent","reference":"self"},{"type":"extend","reference":"urn:uuid:d8211420-7e53-11e5-aec7-0002a5d5c51b"}],"service":[]}]}' --compressed -o sample-response-create-app.json


aia/protoype/tooling


tooling/repository/git/aia-app



curl 'http://localhost:8888/proxy/paas/v1/templates/' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/templates.json

curl 'http://localhost:8888/proxy/paas/v1/integration_points/treeview/' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/treeview.json

curl 'http://localhost:8888/proxy/paas/v1/extension_points/parent=urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/extension_points.json

curl 'http://localhost:8888/proxy/paas/v1/extension_points/urn:uuid:d8211420-7e53-11e5-aec7-0002a5d5c51b' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/extension_point_data.json

curl 'http://localhost:8888/proxy/paas/v1/extension_points/urn:uuid:6a813aa0-7e47-11e5-a425-0002a5d5c51b' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/extension_point_ui.json

curl 'http://localhost:8888/proxy/paas/v1/extension_points/urn:uuid:9f539160-7e47-11e5-8e94-0002a5d5c51b' -H 'Pragma: no-cache' -H 'Origin: http://localhost:8181' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Referer: http://localhost:8181/' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed -o tmp/extension_point_service.json



curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/proxy/appmgr/v1/applications/' -H 'Pragma: no-cache' -H 'Origin: http://atrcxb2560-1.athtem.eei.ericsson.se:8888' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: en-GB,en-US;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36' -H 'Content-Type: application/json' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Cache-Control: no-cache' -H 'X-Requested-With: XMLHttpRequest' -H 'Cookie: _pk_id.21.1678=8f07e29a2932a81d.1460110358.5.1461159563.1461056768.' -H 'Connection: keep-alive' -H 'Referer: http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas-ui/index.html' --data-binary '{"pba":{"name":"a","version":"2","description":"a","platform":[{"type":"marathon","version":"latest"}],"structure":{"instance_connectivity":[]},"pba_policies":[],"dependencies":[{"type":"extend","name":"EP","version":"1.0.1","reference":"urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b","qualifier":""}]},"image":[{"name":"ext1","version":"1","description":"EPS implementation pattern Data extension","platform":[{"type":"marathon","version":"latest"}],"envars":[],"structure":{"instance_configuration":{"memory":{}}},"pba_policies":[],"pba_lifecycle":{"scale_triggers":[{"kpi":"response_time","value":60},{"kpi":"cpu","value":"90"}]},"dependencies":[{"type":"parent","reference":"self"},{"type":"extend","reference":"urn:uuid:d8211420-7e53-11e5-aec7-0002a5d5c51b","name":"ep-data-extension-point","version":"1.0.1"}],"service":[{"name":"new-ext-1","version":"1","description":"foo","service_port":1,"transport":"sync","paas_registration":true,"api-imp":""},{"name":"new-ext-2","version":"1","description":"a","service_port":1,"transport":"sync","paas_registration":true,"api-imp":""}]},{"name":"ext2","version":"2","description":"EPS implementation pattern UI extension","platform":[{"type":"marathon","version":"latest"}],"envars":[],"structure":{"instance_configuration":{"memory":{}}},"pba_policies":[],"pba_lifecycle":{"scale_triggers":[{"kpi":"response_time","value":60},{"kpi":"cpu","value":"90"}]},"dependencies":[{"type":"parent","reference":"self"},{"type":"extend","reference":"urn:uuid:6a813aa0-7e47-11e5-a425-0002a5d5c51b","name":"ep-ui-extension-point","version":"1.0.2"}],"service":[]}]}' --compressed -v







curl 'http://localhost:8888/paas/v1/portal/applications' -H 'Origin: http://localhost:63342' -v
curl 'http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas/v1/portal/applications' -H 'Origin: http://localhost:63342' -v


ILO               Hostname          NetA IP             NetB IP
10.151.35.228	  ieataiaxb6119	    10.148.255.237	    10.148.255.202

ssh evalmce@159.107.169.250 :evalmce

ssh root@10.0.17.127  :shroot

ssh root@10.148.255.202  :shroot

ping 8.8.8.8
ping armdocker.rnd.ericsson.se

ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDG4a4tvlkLzCH96psmf5CssHWDLZlyY64KpyGAUCdQ9g33CO7LA1rqJx5rzQXlIKYerwV5KuUK2Ve4Rjmh8IF6zSQ2kCt9E/2dB98no/JdEk6GZyyncieYgQk+tL5a1AvY5+3Ffnyw0M/Y/cYK9dQCKhwlXT5pwbLq5ETZMvPkEHWnR7dS0vfHDuArcEcAH1Q+5cZiRi9TIjVBXaRfQNyuKTD5wGvP5zKEAxSO0Waihx1d7uNJofGxpciUO1fwdolbLZ3Nqv6yLS7HJZsWK1O6FtEhbfda/A+iDcif4ldKKuOZBuryFsNqPH9F6WWr3+ClkmpVPN6ULA0RHt4EtO0J guo.du@ericsson.com