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

  if [ -z "$CURL_CMD" ]; then
    echo "curl is not installed! Please install it first to continue!"

    exit 1
  fi

  if [ -z "$JQ_CMD" ]; then
    echo "curl is not installed! Please install it first to continue!"

    exit 1
  fi

  if [ -z "$SONAR_ORGANIZATION" ]; then
    echo "The sonar organization is not defined! Please configure the project first!"

    exit 1
  fi

  if [ -z "$SONAR_TOKEN" ]; then
    echo "The sonar token is not defined! Please configure the project first!"

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
  ./gradlew sonar || exit 1

  SEPARATOR=_
  SONAR_PROJECT_KEY="$SONAR_ORGANIZATION$SEPARATOR$BUILD_NAME"
  QUALITY_GATE_STATUS=$($CURL_CMD -s \
                                  -H "Authorization: Bearer $SONAR_TOKEN" \
                                  "$SONAR_URL/api/qualitygates/project_status?projectKey=$SONAR_PROJECT_KEY" | $JQ_CMD -r '.projectStatus.status')

  echo

  if [ "$QUALITY_GATE_STATUS" == "OK" ]; then
    echo "Code analysis passed in quality gated!"

    exit 0
  else
    echo "Code analysis failed in quality gates!"

    exit 1
  fi
}

# Main function.
function main() {
  prepareToExecute
  checkDependencies
  codeAnalysis
}

main