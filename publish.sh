#!/usr/bin/env bash

# Checks the dependencies of this script.
function checkDependencies() {
  if [ -z "$JAVA_CMD" ]; then
    echo "java is not installed! Please install it first to continue!"

    exit 1
  fi

  if [ -z "$GRADLE_CMD" ]; then
    echo "gradle is not installed! Please install it first to continue!"

    exit 1
  fi

  if [ -z "$PUBLISH_REPOSITORY_URL" ]; then
    echo "The publish repository URL is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$PUBLISH_REPOSITORY_USERNAME" ]; then
    echo "The publish repository username is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$PUBLISH_REPOSITORY_PASSWORD" ]; then
    echo "The publish repository password is not defined! Please configure the project first!"

    exit 1
  fi
}

# Prepares the environment to execute this script.
function prepareToExecute() {
  source functions.sh

  showBanner
}

# Publishes the build files in the repository.
function publish() {
  ./gradlew publish
}

# Main function.
function main() {
  prepareToExecute
  checkDependencies
  publish
}

main