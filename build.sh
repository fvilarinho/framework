#!/bin/bash

if [ ! -z "$REPOSITORY_USER" ]; then 
	./gradlew -PrepositoryUser=$REPOSITORY_USER -PrepositoryPassword=$REPOSITORY_PASSWORD clean build publishToMavenLocal
else
	./gradlew clean build publishToMavenLocal
fi	
