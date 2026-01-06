#!/usr/bin/env bash

# Shows the labels.
function showLabel() {
  echo "-=# $BUILD_VERSION #=-"
  echo

  if [[ "$0" == *"build.sh"* ]]; then
    echo "*** BUILD & TEST ***"
  elif [[ "$0" == *"codeAnalysis.sh"* ]]; then
    echo "*** CODE ANALYSIS ***"
  elif [[ "$0" == *"publish.sh"* ]]; then
    echo "*** PUBLISH ***"
  fi

  echo
}

# Shows the banner.
function showBanner() {
  echo

  if [ -f banner.txt ]; then
    cat banner.txt
  fi

  showLabel "$1"
}

# Load environment variables required for the build.
function loadBuildEnvironment() {
  export BUILD_ENV_FILENAME="$WORK_DIR"/.env

  # Mandatory environment variables.
  if [ -f "$BUILD_ENV_FILENAME" ]; then
    LINES=$(grep -v "^#" "$BUILD_ENV_FILENAME")

    for LINE in $LINES
    do
      export $LINE
    done;
  fi
}

# Prepares the environment to execute the commands of this script.
function prepareToExecute() {
  # Mandatory binaries.
  export JAVA_CMD=$(which java)
  export GRADLE_CMD=$(which gradle)
  export FIGLET_CMD=$(which figlet)
  export CURL_CMD=$(which curl)
  export JQ_CMD=$(which jq)

  # Mandatory files/paths.
  export WORK_DIR="$PWD"

  loadBuildEnvironment
}

prepareToExecute