#!/bin/bash

if [ -f .env ]; then
  source .env
fi

./gradlew uploadArchives