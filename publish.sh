#!/bin/bash

if [ ! -z "$REPOSITORY_USER" ]; then 
	./gradlew -PrepositoryUser=$REPOSITORY_USER -PrepositoryPassword=$REPOSITORY_PASSWORD uploadArchives
else
	./gradlew uploadArchives
fi