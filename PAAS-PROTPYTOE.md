
# test
curl atrcxb2994.athtem.eei.ericsson.se:8888
curl analytics.ericsson.se.atrcxb2994.10.32.224.16.xip.io
curl cds.ericsson.se.atrcxb2994.10.32.224.16.xip.io


# deploy portal

cd /root/ericsson/src/cds-portal-v3
./script/docker-build-stage-2.sh 0.0.8-aia-prototype

ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-cds-portal-v3:0.0.8-aia-prototype
docker stop aia-cds-portal-v3-prototype
docker rm aia-cds-portal-v3-prototype
docker run -d -p 8889:3000 --name aia-cds-portal-v3-prototype armdocker.rnd.ericsson.se/aia/aia-cds-portal-v3:0.0.8-aia-prototype
ENDSSH

ssh root@atrcxb2994.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-cds-portal-v3:0.0.8-aia-prototype
docker stop aia-cds-portal-v3-prototype
docker rm aia-cds-portal-v3-prototype
docker run -d -p 8889:3000 --name aia-cds-portal-v3-prototype armdocker.rnd.ericsson.se/aia/aia-cds-portal-v3:0.0.8-aia-prototype
ENDSSH

http://atrcxb2994.athtem.eei.ericsson.se:8889


# deploy paas server
cd /root/ericsson/src/ui
mvn clean package docker:build -DskipTests
docker push armdocker.rnd.ericsson.se/aia/aia-ui:1.0.7


ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui:1.0.7
docker stop aia-ui
docker rm aia-ui
docker run -d -p 8888:8888 --name aia-ui -v /data:/data armdocker.rnd.ericsson.se/aia/aia-ui:1.0.7 --web.cdsv3.url=http://172.17.0.1:8889 --app.base.url=http://atrcxb2560-1.athtem.eei.ericsson.se:8888 --paas.store.maven.dir=/data/paas/maven-store --paas.application.generator.dir=/data/paas/generated-applications
ENDSSH

ssh root@atrcxb2994.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui:1.0.7
docker stop aia-ui
docker rm aia-ui
docker run -d -p 8888:8888 --name aia-ui -v /data:/data armdocker.rnd.ericsson.se/aia/aia-ui:1.0.7 --web.cdsv3.url=http://172.17.0.1:8889 --app.base.url=http://atrcxb2994.athtem.eei.ericsson.se:8888 --paas.store.maven.dir=/data/paas/maven-store --paas.application.generator.dir=/data/paas/generated-applications
ENDSSH

http://atrcxb2994.athtem.eei.ericsson.se:8888

http://atrcxb2994.athtem.eei.ericsson.se:8888/paas-ui/index.html?serviceUrl=http://localhost:8888#home/create-app

# deploy paas proxy

ssh root@atrcxb2994.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-paas-proxy:0.0.1
docker stop aia-paas-proxy
docker rm aia-paas-proxy
docker run -d -p 80:80 --name aia-paas-proxy armdocker.rnd.ericsson.se/aia/aia-paas-proxy:0.0.1
ENDSSH

curl atrcxb2994.athtem.eei.ericsson.se:8888
curl analytics.ericsson.se.atrcxb2994.10.32.224.16.xip.io
curl cds.ericsson.se.atrcxb2994.10.32.224.16.xip.io

# deploy paas ui
cd ~/ericsson/src/ui/oss-bss-paas-ui
./script/build.sh 0.0.7

curl http://atrcxb2994.athtem.eei.ericsson.se:8888/paas/v1/maven/cache
curl "http://atrcxb2994.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"

http://atrcxb2994.athtem.eei.ericsson.se:8888/?paasBaseUrl=http://localhost:8888#aia/ecosystem


# deploy repositories

cd ~/ericsson/src/ui/src/test/resources
jar -cMf repositories.zip repositories
scp repositories.zip root@atrcxb2994.athtem.eei.ericsson.se:/data/
rm repositories.zip

ssh root@atrcxb2994.athtem.eei.ericsson.se  <<'ENDSSH'
mkdir -p /data/paas-server-105-new-proxy/
cd /data/paas-server-105-new-proxy/
rm -rf repositories
unzip /data/repositories.zip
rm -rf /data/repositories.zip
rm -rf repositories/applications/invalid-app-2
ENDSSH



# rest


mvn clean package docker:build -DskipTests

docker push armdocker.rnd.ericsson.se/aia/aia-ui:105-new-proxy


ssh root@atrcxb2560-1.athtem.eei.ericsson.se  <<'ENDSSH'
docker pull armdocker.rnd.ericsson.se/aia/aia-ui:105-new-proxy
docker stop aia-ui-paas-prototype
docker rm aia-ui-paas-prototype
docker run -d -p 8878:8888 --name aia-ui-paas-prototype armdocker.rnd.ericsson.se/aia/aia-ui:105-new-proxy
ENDSSH



ssh root@atrcxb2994.athtem.eei.ericsson.se

http://atrcxb2994.athtem.eei.ericsson.se:8878

http://atrcxb2994.athtem.eei.ericsson.se:8879

http://atrcxb2994.athtem.eei.ericsson.se:7070



https://github.com/mesosphere/docker-containers/tree/master/mesos

10.32.224.16

docker stop paas-zookeeper
docker rm paas-zookeeper
docker stop paas-mesos-master
docker rm paas-mesos-master
docker stop paas-marathon
docker rm paas-marathon
docker stop paas-mesos-slave
docker rm paas-mesos-slave

# mesos root@atrcxb2560-1.athtem.eei.ericsson.se

docker run -d -p 12181:2181 -p 12888:2888 -p 13888:3888 --name paas-zookeeper garland/zookeeper
sleep 5

docker run --name paas-mesos-master --net="host" -p 15050:15050 -e "MESOS_HOSTNAME=10.59.132.197" -e "MESOS_IP=10.59.132.197" -e "MESOS_ZK=zk://10.59.132.197:12181/mesos" -e "MESOS_PORT=15050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=1" -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404
sleep 5

docker run --name paas-marathon -d -p 18080:8080 mesosphere/marathon --master zk://10.59.132.197:12181/mesos --zk zk://10.59.132.197:12181/marathon
sleep 5

docker run --name paas-mesos-slave --net="host" -p 15051:15051 --privileged -d --entrypoint="mesos-slave" -e "MESOS_DOCKER_CONFIG=file:///root/.docker/config.json" -e "MESOS_RESOURCES=ports:[0-34000]" -e "MESOS_CONTAINERIZERS=docker,mesos" -e "MESOS_MASTER=zk://10.59.132.197:12181/mesos" -e "MESOS_HOSTNAME=10.59.132.197" -e "MESOS_IP=10.59.132.197" -e "MESOS_PORT=15051" -e "MESOS_ZK=zk://10.59.132.197:12181/mesos" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_LOGGING_LEVEL=INFO" -v /root/docker.tar.gz:/root/docker.tar.gz -v /root/.docker/config.json:/root/.docker/config.json -v /var/run/docker.sock:/var/run/docker.sock armdocker.rnd.ericsson.se/aia/tmp-mesos-slave:latest


# mesos root@atrcxb2994.athtem.eei.ericsson.se

docker run -d -p 12181:2181 -p 12888:2888 -p 13888:3888 --name paas-zookeeper garland/zookeeper
sleep 5

docker run --name paas-mesos-master --net="host" -p 15050:15050 -e "MESOS_HOSTNAME=10.32.224.16" -e "MESOS_IP=10.32.224.16" -e "MESOS_ZK=zk://10.32.224.16:12181/mesos" -e "MESOS_PORT=15050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=1" -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d docker run --name paas-mesos-master --net="host" -p 15050:15050 -e "MESOS_HOSTNAME=10.59.132.197" -e "MESOS_IP=10.59.132.197" -e "MESOS_ZK=zk://10.59.132.197:12181/mesos" -e "MESOS_PORT=15050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=1" -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404
sleep 5

docker run --name paas-marathon -d -p 18080:8080 garland/mesosphere-docker-marathon --master zk://10.32.224.16:12181/mesos --zk zk://10.32.224.16:12181/marathon
sleep 5

docker run --name paas-mesos-slave -d --entrypoint="mesos-slave" -e "MESOS_MASTER=zk://10.32.224.16:12181/mesos" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_LOGGING_LEVEL=INFO" garland/mesosphere-docker-mesos-master:latest
sleep 5





docker run -d -p 12181:2181 -p 12888:2888 -p 13888:3888 --name paas-zookeeper garland/zookeeper
sleep 5

docker run --name paas-mesos-master --net="host" -p 15050:15050 -e "MESOS_HOSTNAME=10.32.224.16" -e "MESOS_IP=10.32.224.16" -e "MESOS_ZK=zk://10.32.224.16:12181/mesos" -e "MESOS_PORT=15050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=1" -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404
sleep 5

docker run --name paas-marathon -d -p 18080:8080 mesosphere/marathon --master zk://10.32.224.16:12181/mesos --zk zk://10.32.224.16:12181/marathon
sleep 5


docker stop paas-mesos-slave
docker rm paas-mesos-slave
docker run --name paas-mesos-slave --net="host" -p 15051:15051 --privileged -d --entrypoint="mesos-slave" -e "MESOS_DOCKER_CONFIG=file:///root/.docker/config.json" -e "MESOS_RESOURCES=ports:[500-34000]" -e "MESOS_CONTAINERIZERS=docker,mesos" -e "MESOS_MASTER=zk://10.32.224.16:12181/mesos" -e "MESOS_HOSTNAME=10.32.224.16" -e "MESOS_IP=10.32.224.16" -e "MESOS_PORT=15051" -e "MESOS_ZK=zk://10.32.224.16:12181/mesos" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_LOGGING_LEVEL=INFO" -v /root/docker.tar.gz:/root/docker.tar.gz -v /root/.docker/config.json:/root/.docker/config.json -v /var/run/docker.sock:/var/run/docker.sock armdocker.rnd.ericsson.se/aia/tmp-mesos-slave:latest
#docker run --name paas-mesos-slave --net="host" --privileged -d --entrypoint="mesos-slave" -e "MESOS_CONTAINERIZERS=docker,mesos" -e "MESOS_MASTER=zk://10.32.224.16:12181/mesos" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_LOGGING_LEVEL=INFO" -v /var/run/docker.sock:/var/run/docker.sock mesosphere/mesos-slave:0.28.0-2.0.16.ubuntu1404
sleep 5


docker logs paas-mesos-master

URIs
file:///root/docker.tar.gz





docker run -d -p 2181:2181 -p 2888:2888 -p 3888:3888 --name paas-zookeeper garland/zookeeper
sleep 5

docker run --name paas-mesos-master --net="host" -p 5050:5050 -e "MESOS_HOSTNAME=192.168.99.100" -e "MESOS_IP=192.168.99.100" -e "MESOS_ZK=zk://192.168.99.100:2181/mesos" -e "MESOS_PORT=5050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=1" -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404
sleep 5

docker run --name paas-marathon -d -p 8081:8080 mesosphere/marathon --master zk://192.168.99.100:2181/mesos --zk zk://192.168.99.100:2181/marathon
sleep 5

docker run --name paas-mesos-slave --net="host" -p 5051:5051 --privileged -d --entrypoint="mesos-slave" -e "MESOS_CONTAINERIZERS=docker,mesos" -e "MESOS_MASTER=zk://192.168.99.100:2181/mesos" -e "MESOS_HOSTNAME=192.168.99.100" -e "MESOS_IP=192.168.99.100" -e "MESOS_PORT=5051" -e "MESOS_ZK=zk://192.168.99.100:2181/mesos" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_LOGGING_LEVEL=INFO" -v /var/run/docker.sock:/var/run/docker.sock armdocker.rnd.ericsson.se/aia/tmp-mesos-slave:latest
sleep 5
docker logs paas-mesos-slave

mvn deploy:deploy-file -DgroupId=com.ericsson.aia.tmp \
  -DartifactId=kafka-avro-decoder \
  -Dversion=0.0.1 \
  -Dpackaging=jar \
  -Dfile=test.jar \
  -DrepositoryId=release  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

mvn deploy:deploy-file -DgroupId=com.ericsson.aia.tmp \
  -DartifactId=kafka-avro-decoder \
  -Dversion=0.0.1 \
  -Dpackaging=jar \
  -Dfile=test.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases


cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/model/model/1.0.3-SNAPSHOT
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.tmp \
  -DartifactId=model \
  -Dversion=0.0.1 \
  -Dpackaging=pom \
  -Dfile=model-1.0.3-SNAPSHOT.pom \
  -DrepositoryId=nexus  \
  -DgeneratePom=false \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases


cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/common/avro/avro-decoder/1.0.4-SNAPSHOT
mvn deploy:deploy-file -DpomFile=avro-decoder-1.0.4-SNAPSHOT.pom \
  -Dfile=avro-decoder-1.0.4-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/common/avro/kafka-avro-decoder/1.0.4-SNAPSHOT
mvn deploy:deploy-file -DpomFile=kafka-avro-decoder-1.0.4-SNAPSHOT.pom \
  -Dfile=kafka-avro-decoder-1.0.4-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/common/avro/avro-common/1.0.4-SNAPSHOT
mvn deploy:deploy-file -DpomFile=avro-common-1.0.4-SNAPSHOT.pom \
  -Dfile=avro-common-1.0.4-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/model/pojo/1.0.3-SNAPSHOT
mvn deploy:deploy-file -DpomFile=pojo-1.0.3-SNAPSHOT.pom \
  -Dfile=pojo-1.0.3-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

cd /c/Users/eguoduu/Downloads/aia-m2.tar/aia-m2/aia/model/xml/1.0.3-SNAPSHOT
mvn deploy:deploy-file -DpomFile=xml-1.0.3-SNAPSHOT.pom \
  -Dfile=xml-1.0.3-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases


cd "C:\Users\eguoduu\ericsson\tmp\saj-m2-aia-bps.tar\saj-m2-aia-bps\saj-m2-aia\bps\engine\bps-core\0.0.1-SNAPSHOT"
mvn deploy:deploy-file -DpomFile=bps-core-0.0.1-SNAPSHOT.pom \
  -Dfile=bps-core-0.0.1-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases



cd "C:\Users\eguoduu\ericsson\tmp\saj-m2-aia-itpf.tar\saj-m2-aia-itpf\saj-m2-aia\itpf\common\flow\eventflow-api\1.0.0"
mvn deploy:deploy-file -DpomFile=eventflow-api-1.0.0.pom \
  -Dfile=bps-core-0.0.1-SNAPSHOT.jar \
  -DrepositoryId=nexus \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases



[19/05/2016 15:46] Sandeep Pediredla:
there /root/.m2 zip
[19/05/2016 15:46] Sandeep Pediredla:
for jars
[19/05/2016 15:46] Sandeep Pediredla:
in 3650 http://ieatrcxb3650.athtem.eei.ericsson.se/
[19/05/2016 15:48] Guo Du:
what's the server password?
[19/05/2016 15:48] Sandeep Pediredla:

ssh root@ieatrcxb3650.athtem.eei.ericsson.se
gravity1


scp root@ieatrcxb3650.athtem.eei.ericsson.se:/root/guo/saj-m2-aia-itpf.tar.gz .



cd "C:\Users\eguoduu\ericsson\src\ui\src\main\resources\static"
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.ui \
  -DartifactId=aia-ui \
  -Dversion=0.0.2 \
  -Dpackaging=jar \
  -Dfile=static.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases



cd "C:\Users\eguoduu\ericsson\src\ui\src\main\resources\static\paas-ui"
rm -rf paas-ui.jar
jar -cf paas-ui.jar *
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.ui \
  -DartifactId=aia-ui-paas \
  -Dversion=0.0.1 \
  -Dclassifier=site \
  -Dpackaging=jar \
  -Dfile=paas-ui.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases
rm -rf paas-ui.jar



cd "C:\Users\eguoduu\Downloads"
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.model \
  -DartifactId=schema-registry-client \
  -Dversion=1.0.3 \
  -Dclassifier=site \
  -Dpackaging=jar \
  -Dfile=schema-registry-client-1.0.5-site.jar \
  -DgeneratePom=false \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases


cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\applications\aia-development-work-flow-demo\0.11.1"
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.applications \
  -DartifactId=aia-development-work-flow-demo \
  -Dversion=0.11.7 \
  -Dpackaging=json \
  -Dclassifier=pba \
  -Dfile=pba.json \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases



cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\applications\asr\0.1.0"
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.applications \
  -DartifactId=asr \
  -Dversion=0.1.2 \
  -Dpackaging=json \
  -Dclassifier=pba \
  -Dfile=pba.json \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases


cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\templates\aia-development-work-flow-demo\0.10.0"
rm -rf template.jar
jar -cvf template.jar *
cd src
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.templates \
  -DartifactId=aia-development-work-flow-demo \
  -Dversion=0.11.9 \
  -Dclassifier=template \
  -Dpackaging=jar \
  -Dfile=../template.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases
rm -rf ../template.jar
curl "http://atrcxb2994.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"
curl "http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"


cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\templates\aia-bps-streaming\0.1.0"
rm -rf template.jar
jar -cvf template.jar *
cd src
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.templates \
  -DartifactId=aia-bps-streaming \
  -Dversion=0.1.13 \
  -Dclassifier=template \
  -Dpackaging=jar \
  -Dfile=../template.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases
rm -rf ../template.jar
curl "http://atrcxb2994.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"
curl "http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"

cp -rf 'C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\templates\aia-bps-streaming\0.1.0' 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-streaming\0.1.5\' && \
rm -rf 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-streaming\0.1.5\template' && \
mv 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-streaming\0.1.5\0.1.0' 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-streaming\0.1.5\template'



cd "C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\templates\aia-bps-batch\0.1.0"
rm -rf template.jar
jar -cvf template.jar *
cd bin
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.paas.templates \
  -DartifactId=aia-bps-batch \
  -Dversion=0.1.4 \
  -Dclassifier=template \
  -Dpackaging=jar \
  -Dfile=../template.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases
rm -rf ../template.jar
curl "http://atrcxb2994.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"
curl "http://atrcxb2560-1.athtem.eei.ericsson.se:8888/paas/v1/maven/cache?reset=true"


cp -rf 'C:\Users\eguoduu\ericsson\src\ui\src\test\resources\repositories\templates\aia-bps-batch\0.1.0' 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-batch\0.1.1\' && \
rm -rf 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-batch\0.1.1\template' && \
mv 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-batch\0.1.1\0.1.0' 'C:\Users\eguoduu\ericsson\src\ui\target\maven-store\com\ericsson\aia\paas\templates\aia-bps-batch\0.1.1\template'

docker stop aia-ui-paas-prototype aia-cds-portal-v3 aia-ui aia-demo-app schema-registry kafka zookeeper alluxray
docker rm aia-ui-paas-prototype aia-cds-portal-v3 aia-ui aia-demo-app schema-registry kafka zookeeper alluxray
