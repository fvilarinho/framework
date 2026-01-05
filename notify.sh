#!/bin/bash

# Check the dependencies of this script.
function checkDependencies() {
  if [ -z "$CURL_CMD" ]; then
    echo "curl is not installed! Please install it first to continue!"

    exit 1
  fi

  export WORKFLOW=$1

  if [ -z "$WORKFLOW" ]; then
    echo "Please specify a workflow!"

    exit 1
  fi

  export STATUS=$2

  if [ -z "$STATUS" ]; then
    echo "Please specify a notification status!"

    exit 1
  fi
}

# Prepares the environment to execute this script.
function prepareToExecute() {
  source functions.sh
}

# Sends a notification message to Slack service.
function notify() {
  URL="https://hooks.slack.com/services/$SLACK_TOKEN"

  if [ "$STATUS" == "success" ]; then
    MESSAGE="Hi there

Great news :tada: :smiley:!

The $WORKFLOW pipeline execution was *FLAWLESS*! Good job :heart:!"
  else
    MESSAGE="Hi!

I got some bad news :sob:!

The $WORKFLOW pipeline execution *FAILED* in some steps!"
  fi

  MESSAGE="$MESSAGE

•  <https://github.com/fvilarinho/framework/actions/workflows/$WORKFLOW.yml|Execution status>
•  <https://sonarcloud.io/summary/overall?id=fvilarinho_framework&branch=master|Code analysis status>"

  $CURL_CMD -s \
            -o /dev/null \
            -X POST \
            -H 'Content-type: application/json' \
            --data "{
  \"blocks\": [
    {
      \"type\": \"section\",
      \"text\": {
        \"type\": \"mrkdwn\",
        \"text\": \"$MESSAGE\"
      }
    }
  ]
}" "$URL"
}

# Main function.
function main() {
  prepareToExecute
  checkDependencies "$1" "$2"
  notify
}

main "$1" "$2"


