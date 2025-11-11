#!/bin/bash

# Shows the labels.
function showLabel() {
  if [[ "$0" == *"build.sh"* ]]; then
    echo "*** BUILD & TEST ***"
  elif [[ "$0" == *"codeAnalysis.sh"* ]]; then
    echo "*** CODE ANALYSIS ***"
  elif [[ "$0" == *"publish.sh"* ]]; then
    echo "*** PUBLISH ***"
  elif [[ "$0" == *"setup.sh"* ]]; then
    echo "*** SETUP ***"
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

# Prepares the environment to execute the commands of this script.
function prepareToExecute() {
  # Mandatory binaries.
  export JAVA_CMD=$(which java)
  export GRADLE_CMD=$(which gradle)
  export FIGLET_CMD=$(which figlet)

  # Mandatory files/paths.
  export WORK_DIR="$PWD"
  export BUILD_ENV_FILENAME="$WORK_DIR"/.env

  # Mandatory environment variables.
  if [ -f "$BUILD_ENV_FILENAME" ]; then
    LINES=$(cat "$BUILD_ENV_FILENAME")

    for LINE in $LINES
    do
      export $LINE
    done;
  fi
}

prepareToExecute