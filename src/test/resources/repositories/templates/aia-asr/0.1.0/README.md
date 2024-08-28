# build
mvn

# publish docker image
docker push armdocker.rnd.ericsson.se/aia/aia-asr-correlation-ups:${pbaInfo.pba.version}

# run
docker run --env mainClass=com.ericsson.aia.asr.correlations.ups.UserPlaneCorrelation_MR --env masterUrl=spark://10.45.16.201:7077  --env applicationJar=/asr/uber-aia-ref-app-radio-ups-1.0.jar --env appArguments="dataIn1 dataIn1 spark://10.45.16.201:7077  tachyon://10.45.16.201:19998 10.45.16.201:9092 60" --env schemaRegistry.address="http://ieatrcxb3650.athtem.eei.ericsson.se:8081/" armdocker.rnd.ericsson.se/aia/aia-asr-correlation-ups:${pbaInfo.pba.version}