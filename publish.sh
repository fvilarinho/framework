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

  if [ -z "$REPOSITORY_URL" ]; then
    echo "The repository URL is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$REPOSITORY_USERNAME" ]; then
    echo "The repository username is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$REPOSITORY_PASSWORD" ]; then
    echo "The repository password is not defined! Please configure the project first!"

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