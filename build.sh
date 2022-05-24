#!/bin/bash

if [ ! -z "$REPOSITORY_USER" ]; then 
	./gradlew -PrepositoryUser=$REPOSITORY_USER -PrepositoryPassword=$REPOSITORY_PASSWORD build publishToMavenLocal
else
	./gradlew build publishToMavenLocal
fi