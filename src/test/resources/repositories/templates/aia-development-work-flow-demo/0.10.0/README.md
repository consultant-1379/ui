# step 1: build locally
mvn

# step 2: run docker image locally (ctrl+c to stop)
* you may inspect the container to IP and port to test the web site
docker run -it --rm armdocker.rnd.ericsson.se/aia/aia-development-work-flow-demo:${pbaInfo.pba.version}

# step 3: publish to docker repository
* require write permission to docker registry
docker push armdocker.rnd.ericsson.se/aia/aia-development-work-flow-demo:${pbaInfo.pba.version}

# step 4: deploy to paas platform
* require docker image been published
./bin/deploy_app.sh

# step 5: undeploy the app
./bin/undeploy_app.sh

# step 6: publish back to app manager
* After publish, you may find your application listed at:
* $appBaseUrl/#aia/ecosystem

./bin/publish_app.sh


# step 7: unpublish from app manager
./bin/unpublish_app.sh
