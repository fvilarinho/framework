#!/bin/bash

./gradlew -PrepositoryUrl=$REPOSITORY_URL -PrepositoryUser=$REPOSITORY_USER -PrepositoryPassword=$REPOSITORY_PASSWORD build publishToMavenLocal uploadArchives