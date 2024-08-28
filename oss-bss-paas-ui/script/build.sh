#!/bin/bash

set -e

# Script to be run from root folder

# Proxy
if [ -z "$CDT_PROXY" ]; then
    CDT_PROXY="null"
fi

echo "---------------------------------"
echo " Building Application bundle"
echo "---------------------------------"

cd client-ui
BUILD_TARGET_FOLDER=../target

rm -rf $BUILD_TARGET_FOLDER

cdt2 build --packages  oss-bss-paas-lib,create-app,explore-app,manage-app,home  \
           --proxy  $CDT_PROXY                                                  \
           --deploy $BUILD_TARGET_FOLDER                                        \
           --deploy-with ../_deployWith/

#cd $BUILD_TARGET_FOLDER && tar -czf ./oss-bss-paas-portal.tar.gz *


cd $BUILD_TARGET_FOLDER
jar -cf site.jar *
mvn deploy:deploy-file -DgroupId=com.ericsson.aia.ui \
  -DartifactId=aia-ui-paas \
  -Dversion=$1 \
  -Dclassifier=site \
  -Dpackaging=jar \
  -Dfile=site.jar \
  -DrepositoryId=nexus  \
  -Durl=https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases

