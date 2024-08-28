# deploy
cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\applications\platform-service-kafka\0.0.1"
./deploy_app.sh http://localhost:8888


# undeploy
cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\applications\platform-service-kafka\0.0.1"
./undeploy_app.sh http://localhost:8888



# publish
cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\applications\platform-service-kafka\0.0.1"
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.applications \
  -DartifactId=platform-service-kafka \
  -Dversion=0.0.4 \
  -Dpackaging=json \
  -Dclassifier=pba \
  -Dfile=pba.json \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases
