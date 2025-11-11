#!/bin/bash

# Opens the license dialog.
function licenseDialog() {
  TITLE="LICENSE"
  CURRENT_MENU="6. LICENSE"

  $DIALOG_CMD --backtitle "$MAIN_TITLE" \
              --title "$TITLE" \
              --textbox LICENSE 20 90
}

function newProject() {
  TITLE="NEW PROJECT"

  # Open the dialog.
  while "true"; do
    PROJECT_NAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                               --title "$TITLE" \
                               --inputbox "\nPlease enter the name of the project:" 10 70 "$PROJECT_NAME" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_NAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the name of the project!" 7 60
    else
      break
    fi
  done

  PROJECT_DIR=$(dirname "$(pwd)")
  PROJECT_DIR="$PROJECT_DIR/$PROJECT_NAME"

  while "true"; do
    PROJECT_DIR=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                              --title "$TITLE" \
                              --inputbox "\nPlease enter the directory of the project:" 10 70 "$PROJECT_DIR" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_DIR" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the directory of the project!" 7 60
    else
      break
    fi
  done

  while "true"; do
    PROJECT_PACKAGE_NAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                       --title "$TITLE" \
                                       --inputbox "\nPlease enter the package name of the project:" 10 70 "$PROJECT_PACKAGE_NAME" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_PACKAGE_NAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the package name of the project!" 7 60
    else
      break
    fi
  done

  while "true"; do
    PROJECT_VERSION=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                  --title "$TITLE" \
                                  --inputbox "\nPlease enter the initial version of the project:" 10 70 "$PROJECT_VERSION" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_VERSION" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the version of the project!" 7 60
    else
      break
    fi
  done

  while "true"; do
    BUILD_REPO_URL=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                 --title "$TITLE" \
                                 --inputbox "\nPlease enter the build repository URL:" 10 70 "$BUILD_REPOSITORY_URL" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$BUILD_REPO_URL" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the build repository URL!" 7 60
    else
      break
    fi
  done

  while "true"; do
    BUILD_REPO_USERNAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the build repository username:" 10 70 "$BUILD_REPOSITORY_USERNAME" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$BUILD_REPO_USERNAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the build repository username!" 7 60
    else
      break
    fi
  done

  while "true"; do
    BUILD_REPO_PASSWORD=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the build repository password:" 10 70 "$BUILD_REPOSITORY_PASSWORD" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$BUILD_REPO_PASSWORD" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the build repository password!" 7 60
    else
      break
    fi
  done

  while "true"; do
    PUBLISH_REPO_URL=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                 --title "$TITLE" \
                                 --inputbox "\nPlease enter the publish repository URL:" 10 70 "$PUBLISH_REPOSITORY_URL" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PUBLISH_REPO_URL" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the publish repository URL!" 7 60
    else
      break
    fi
  done

  while "true"; do
    PUBLISH_REPO_USERNAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the publish repository username:" 10 70 "$PUBLISH_REPOSITORY_USERNAME" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PUBLISH_REPO_USERNAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the publish repository username!" 7 60
    else
      break
    fi
  done

  while "true"; do
    PUBLISH_REPO_PASSWORD=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the publish repository password:" 10 70 "$PUBLISH_REPOSITORY_PASSWORD" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PUBLISH_REPO_PASSWORD" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the publish repository password!" 7 60
    else
      break
    fi
  done

  while "true"; do
    SONAR_ORG=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                            --title "$TITLE" \
                            --inputbox "\nPlease enter the sonar organization:" 10 70 "$SONAR_ORGANIZATION" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$SONAR_ORG" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the sonar organization!" 7 60
    else
      break
    fi
  done

  while "true"; do
    SONAR_TOK=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                            --title "$TITLE" \
                            --inputbox "\nPlease enter the sonar organization:" 10 70 "$SONAR_TOKEN" 2>&1 > /dev/tty)

    # Check / validate the input.
    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$SONAR_TOK" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the sonar token!" 7 60
    else
      break
    fi
  done

  mkdir -p "$PROJECT_DIR/src/main/java" \
           "$PROJECT_DIR/src/test/java" \
           "$PROJECT_DIR/src/test/resources"

  echo "BUILD_REPOSITORY_URL=$BUILD_REPO_URL" > "$PROJECT_DIR/.env"
  echo "BUILD_REPOSITORY_USERNAME=$BUILD_REPO_USERNAME" >> "$PROJECT_DIR/.env"
  echo "BUILD_REPOSITORY_PASSWORD=$BUILD_REPO_PASSWORD" >> "$PROJECT_DIR/.env"
  echo >> "$PROJECT_DIR/.env"
  echo "PUBLISH_REPOSITORY_URL=$PUBLISH_REPO_URL" >> "$PROJECT_DIR/.env"
  echo "PUBLISH_REPOSITORY_USERNAME=$PUBLISH_REPO_USERNAME" >> "$PROJECT_DIR/.env"
  echo "PUBLISH_REPOSITORY_PASSWORD=$PUBLISH_REPO_PASSWORD" >> "$PROJECT_DIR/.env"
  echo >> "$PROJECT_DIR/.env"
  echo "SONAR_ORGANIZATION=$SONAR_ORG" >> "$PROJECT_DIR/.env"
  echo "SONAR_TOKEN=$SONAR_TOK" >> "$PROJECT_DIR/.env"

  if [ ! -d "$PROJECT_DIR/src/main/resources" ]; then
    cp -r src/main/resources "$PROJECT_DIR/src/main"
  fi

  if [ ! -d "$PROJECT_DIR/src/main/ui" ]; then
    cp -r src/main/ui "$PROJECT_DIR/src/main"
  fi

  if [ ! -d "$PROJECT_DIR/src/templates" ]; then
    cp -r src/templates "$PROJECT_DIR/src"
    rm -f "$PROJECT_DIR/src/templates/build.gradle"
  fi

  if [ ! -f "$PROJECT_DIR/build.gradle" ]; then
    cp src/templates/build.gradle "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/gradle.properties" ]; then
    echo "buildPackage=$PROJECT_PACKAGE_NAME" > "$PROJECT_DIR/gradle.properties"
    echo "buildName=$PROJECT_NAME" >> "$PROJECT_DIR/gradle.properties"
    echo "buildVersion=$PROJECT_VERSION" >> "$PROJECT_DIR/gradle.properties"
  fi

  if [ ! -f "$PROJECT_DIR/settings.gradle" ]; then
    cp settings.gradle "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/functions.sh" ]; then
    cp functions.sh "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/build.sh" ]; then
    cp build.sh "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/codeAnalysis.sh" ]; then
    cp codeAnalysis.sh "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/publish.sh" ]; then
    cp codeAnalysis.sh "$PROJECT_DIR"
  fi

  if [ ! -f "$PROJECT_DIR/banner.txt" ]; then
    if [ -n "$FIGLET_CMD" ]; then
      $FIGLET_CMD "$PROJECT_NAME" > "$PROJECT_DIR/banner.txt"

      echo >> "$PROJECT_DIR/banner.txt"
    fi
  fi

  cd $PROJECT_DIR || exit 1

  $GRADLE_CMD wrapper > /dev/null

  ./build.sh
}

# Open the menu dialog.
function menuDialog() {
  TITLE="LET'S START!"
  OPTION=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                       --title "$TITLE" \
                       --default-item "$CURRENT_MENU" \
                       --menu "\nPlease select an option to continue:" 0 90 7 \
                       "1. CONFIGURE" "Starts the configuration process." \
                       "2. BUILD" "Starts the building process." \
                       "3. CODE ANALYSIS" "Starts the code analysis." \
                       "4. PUBLISH" "Publishes the build in the repository." \
                       "5. NEW PROJECT" "Creates a new project." \
                       "6. LICENSE" "Know more about licensing." \
                       "0. EXIT" "Exit this setup." 2>&1 > /dev/tty)

  # Check / validate the selected option.
  if [ $? -eq 1 ]; then
    clear

    exit 0
  else
    # Execute the selected option.
    case $OPTION in
      "1. CONFIGURE")
        menuDialog
        ;;
      "2. BUILD")
        ./build.sh
        menuDialog
        ;;
      "3. CODE ANALYSIS")
        ./codeAnalysis.sh
        menuDialog
        ;;
      "4. PUBLISH")
        ./publish.sh
        menuDialog
        ;;
      "5. NEW PROJECT")
        newProject
        menuDialog
        ;;
      "6. LICENSE")
        licenseDialog
        menuDialog
        ;;
      "0. EXIT")
        clear

        exit 0
        ;;
    esac
  fi
}

# Check the requirements for setup.
function checkRequirementsDialog() {
  if [ -z "$UNATTENDED" ]; then
    TITLE="CHECKING REQUIREMENTS"
    BINARIES="java gradle"

    eval "BINARIES=($BINARIES)"

    counter=0
    length=${#BINARIES[@]}

    # Check if required binaries are installed in the OS.
    for BINARY in "${BINARIES[@]}"
    do
      BINARY_CMD=$(which "$BINARY")

      sleep 0.10

      # Check if it was found. If don't, exit the setup.
      if [ ! -f "$BINARY_CMD" ]; then
        $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                    --title "$TITLE" \
                    --msgbox "\n$BINARY is not installed! Please install it first to continue!" 7 70 > /dev/tty

        clear

        exit 1
      else
        counter=$((counter + (100 / length)))

        echo $counter | $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                    --title "$TITLE" \
                                    --gauge "\nFinding required software installation..." 8 45
      fi
    done

    sleep 0.10

    $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                --title "$TITLE" \
                --msgbox "\nAll requirements found!" 7 35
  fi
}

# Opens the welcome dialog.
function welcomeDialog() {
  if [ -z "$UNATTENDED" ]; then
    MAIN_TITLE="Concepting Framework"
    TITLE="WELCOME!"
    ABOUT=$(cat README.md)

    $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                --title "$TITLE" \
                --msgbox "$ABOUT" 21 90
  fi
}

# Check the dependencies of this script.
function checkDependencies() {
  if [ -z "$UNATTENDED" ]; then
    DIALOG_CMD=$(which dialog)

    # Check if the requirements to start the setup exists.
    if [ -z "$DIALOG_CMD" ]; then
      echo "dialog is not installed! Please install it first to continue!"
      echo

      exit 1
    fi
  fi
}

# Prepare the environment to execute the commands of this script.
function prepareToExecute() {
  source functions.sh
}

# Main function.
function main() {
  checkDependencies
  prepareToExecute
  welcomeDialog
  checkRequirementsDialog
  menuDialog
}

trap "" SIGTSTP
trap "" SIGINT

main "$1"

trap SIGINT
trap SIGTSTP