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

  if [ -z "$BUILD_REPOSITORY_URL" ]; then
    echo "The build repository URL is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$BUILD_REPOSITORY_USERNAME" ]; then
    echo "The build repository username is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$BUILD_REPOSITORY_PASSWORD" ]; then
    echo "The build repository password is not defined! Please configure the project first!"

    exit 1
  fi
}

# Prepares the environment to execute this script.
function prepareToExecute() {
  source functions.sh

  showBanner
}

# Starts the build process.
function build() {
  ./gradlew build publishToMavenLocal
}

# Main function.
function main() {
  prepareToExecute
  checkDependencies
  build
}

main