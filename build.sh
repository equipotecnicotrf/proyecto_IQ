#!/bin/bash

. ./build.env

clear

echo "Execute build $ARTIFACT_ID..."

#echo "Build $ARTIFACT_ID_PROJECT..."
#mvn -f ../../../oa-factura-electronica-service/src/$ARTIFACT_ID_PROJECT/pom.xml -DskipTests=true clean compile install

echo "Build $ARTIFACT_ID..."
mvn -Drelease.artifactId=$ARTIFACT_ID -Drelease.version=$VERSION -Drelease.packaging=$PACKAGING -DskipTests=true clean compile package

echo "Create dist folder..."
rm -rf dist
rm $ZIP_NAME
mkdir -p -v dist
mkdir -p -v dist/config
mkdir -p -v dist/logs

cp README-docker.md dist
cp -r config/* dist/config
cp Dockerfile dist
cp build.env dist
cp docker/docker.env dist
cp docker/docker.sh dist
cp target/$JAR_NAME dist

echo "Compress dist folder..."
cd dist
zip -rvdc $ZIP_NAME *
cd ..
cp dist/$ZIP_NAME $ZIP_NAME
rm -rf dist

echo "Build $ARTIFACT_ID successfully in $ZIP_NAME."