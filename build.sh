#!/bin/bash

if [ ! -z "$REPOSITORY_URL" ]; then 
	./gradlew -PrepositoryUrl=$REPOSITORY_URL -PrepositoryUser=$REPOSITORY_USER -PrepositoryPassword=$REPOSITORY_PASSWORD clean build publishToMavenLocal
else
	./gradlew clean build publishToMavenLocal
fi	