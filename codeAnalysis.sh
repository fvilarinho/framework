#!/bin/bash

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

  if [ -z "$SONAR_TOKEN" ]; then
    echo "The sonar token is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$SONAR_ORGANIZATION" ]; then
    echo "The sonar organization is not defined! Please configure the project first!"

    exit 1
  fi
}

# Prepares the environment to execute this script.
function prepareToExecute() {
  source functions.sh

  showBanner
}

# Starts the code analysis process.
function codeAnalysis() {
  ./gradlew build sonar
}

# Main function.
function main() {
  clear

  prepareToExecute
  checkDependencies
  codeAnalysis
}

main