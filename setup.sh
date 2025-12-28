#!/usr/bin/env bash

# Opens the license dialog.
function licenseDialog() {
  TITLE="LICENSE"
  CURRENT_MENU="6. LICENSE"
  $DIALOG_CMD --backtitle "$MAIN_TITLE" \
              --title "$TITLE" \
              --textbox LICENSE \
              20 \
              90
}

# Opens the dialog to input the attributes of the new project.
function projectInputDialog() {
  while true; do
    PROJECT_NAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                               --title "$TITLE" \
                               --inputbox "\nPlease enter the name of the project:" \
                               10 \
                               60 \
                               "$PROJECT_NAME" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_NAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the name of the project!" \
                  7 \
                  60
    else
      PROJECT_DIR=$(dirname "$(pwd)")
      PROJECT_DIR=$PROJECT_DIR/$PROJECT_NAME

      if [ -d "$PROJECT_DIR" ]; then
        $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                    --title "$TITLE" \
                    --msgbox "\nProject already exists!" \
                    7 \
                    60
      else
        break
      fi
    fi
  done

  while true; do
    PROJECT_DIR=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                              --title "$TITLE" \
                              --inputbox "\nPlease enter the directory of the project:" \
                              10 \
                              60 \
                              "$PROJECT_DIR" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_DIR" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the directory of the project!" \
                  7 \
                  60
    else
      break
    fi
  done

  PROJECT_PACKAGE_NAME="my.app.$PROJECT_NAME"

  while true; do
    PROJECT_PACKAGE_NAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                       --title "$TITLE" \
                                       --inputbox "\nPlease enter the package name of the project:" \
                                       10 \
                                       60 \
                                       "$PROJECT_PACKAGE_NAME" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_PACKAGE_NAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the package name of the project!" \
                  7 \
                  60
    else
      break
    fi
  done

  PROJECT_VERSION="1.0.0"

  while "true"; do
    PROJECT_VERSION=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                  --title "$TITLE" \
                                  --inputbox "\nPlease enter the initial version of the project:" \
                                  10 \
                                  60 \
                                  "$PROJECT_VERSION" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$PROJECT_VERSION" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the version of the project!" \
                  7 \
                  60
    else
      break
    fi
  done
}

# Opens the dialog to define the attributes of the repository.
function repositoryDialog() {
  while "true"; do
    REPOSITORY_URL=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                 --title "$TITLE" \
                                 --inputbox "\nPlease enter the repository URL:" \
                                 10 \
                                 60 \
                                 "$REPOSITORY_URL" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$REPOSITORY_URL" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the repository URL!" \
                  7 \
                  60
    else
      break
    fi
  done

  while "true"; do
    REPOSITORY_USERNAME=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the repository username:" \
                                      10 \
                                      60 \
                                      "$REPOSITORY_USERNAME" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$REPOSITORY_USERNAME" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the repository username!" 7 60
    else
      break
    fi
  done

  while "true"; do
    REPOSITORY_PASSWORD=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                      --title "$TITLE" \
                                      --inputbox "\nPlease enter the repository password:" \
                                      10 \
                                      60 \
                                     "$REPOSITORY_PASSWORD" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$REPOSITORY_PASSWORD" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the repository password!" \
                  7 \
                  60
    else
      break
    fi
  done
}

# Opens the dialog to define the attributes of the sonar.
function sonarDialog() {
  while "true"; do
    SONAR_ORGANIZATION=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                     --title "$TITLE" \
                                     --inputbox "\nPlease enter the sonar organization:" \
                                     10 \
                                     60 \
                                     "$SONAR_ORGANIZATION" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$SONAR_ORGANIZATION" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the sonar organization!" \
                  7 \
                  60
    else
      break
    fi
  done

  while "true"; do
    SONAR_TOKEN=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                              --title "$TITLE" \
                              --inputbox "\nPlease enter the sonar organization:" \
                              10 \
                              60 \
                              "$SONAR_TOKEN" 2>&1 > /dev/tty)

    if [ $? -eq 1 ]; then
      menuDialog
    elif [ -z "$SONAR_TOKEN" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\nYou must specify the sonar token!" \
                  7 \
                  60
    else
      break
    fi
  done
}

# Creates the environment file.
function createEnvironment() {
  echo "REPOSITORY_URL=$REPOSITORY_URL" > $BUILD_ENV_FILENAME
  echo "REPOSITORY_USERNAME=$REPOSITORY_USERNAME" >> $BUILD_ENV_FILENAME
  echo "REPOSITORY_PASSWORD=$REPOSITORY_PASSWORD" >> $BUILD_ENV_FILENAME

  echo >> $BUILD_ENV_FILENAME
  echo "SONAR_ORGANIZATION=$SONAR_ORGANIZATION" >> $BUILD_ENV_FILENAME
  echo "SONAR_TOKEN=$SONAR_TOKEN" >> $BUILD_ENV_FILENAME
}

# Creates the new project's structure directories.
function createProjectStructure() {
  mkdir -p $PROJECT_DIR/src/main/java \
           $PROJECT_DIR/src/test/java \
           $PROJECT_DIR/src/test/resources
}

# Creates the new project's banner.
function createProjectBanner() {
  BANNER_FILENAME=$PROJECT_DIR/banner.txt

  if [ ! -f "$BANNER_FILENAME" ]; then
    if [ -n "$FIGLET_CMD" ]; then
      $FIGLET_CMD -W "$PROJECT_NAME" > $BANNER_FILENAME

      echo >> $BANNER_FILENAME
    fi
  fi
}

# Creates the new project's environment file.
function createProjectEnvironment() {
  PROJECT_BUILD_ENV_FILENAME="$PROJECT_DIR/.env"

  if [ ! -f "$PROJECT_BUILD_ENV_FILENAME" ]; then
    echo "FRAMEWORK_REPOSITORY_URL=$FRAMEWORK_REPOSITORY_URL" > $PROJECT_BUILD_ENV_FILENAME
    echo "FRAMEWORK_REPOSITORY_USERNAME=$FRAMEWORK_REPOSITORY_USERNAME" >> $PROJECT_BUILD_ENV_FILENAME
    echo "FRAMEWORK_REPOSITORY_PASSWORD=$FRAMEWORK_REPOSITORY_PASSWORD" >> $PROJECT_BUILD_ENV_FILENAME

    echo >> $PROJECT_BUILD_ENV_FILENAME
    echo "REPOSITORY_URL=$PROJECT_REPOSITORY_URL" >> $PROJECT_BUILD_ENV_FILENAME
    echo "REPOSITORY_USERNAME=$PROJECT_REPOSITORY_USERNAME" >> $PROJECT_BUILD_ENV_FILENAME
    echo "REPOSITORY_PASSWORD=$PROJECT_REPOSITORY_PASSWORD" >> $PROJECT_BUILD_ENV_FILENAME

    echo >> $PROJECT_BUILD_ENV_FILENAME
    echo "SONAR_ORGANIZATION=$PROJECT_SONAR_ORGANIZATION" >> $PROJECT_BUILD_ENV_FILENAME
    echo "SONAR_TOKEN=$PROJECT_SONAR_TOKEN" >> $PROJECT_BUILD_ENV_FILENAME
  fi
}

# Creates the new project's build files.
function createProjectBuild() {
  if [ ! -f "$PROJECT_DIR/build.gradle" ]; then
    cp -f src/templates/build.gradle $PROJECT_DIR || exit 1
  fi

  if [ ! -f "$PROJECT_DIR/settings.gradle" ]; then
    cp -f settings.gradle $PROJECT_DIR || exit 1
  fi

  GRADLE_PROPERTIES_FILENAME="$PROJECT_DIR/gradle.properties"

  if [ ! -f "$GRADLE_PROPERTIES_FILENAME" ]; then
    echo "buildPackage=$PROJECT_PACKAGE_NAME" > $GRADLE_PROPERTIES_FILENAME
    echo "buildName=$PROJECT_NAME" >> $GRADLE_PROPERTIES_FILENAME
    echo "buildVersion=$PROJECT_VERSION" >> $GRADLE_PROPERTIES_FILENAME
  fi

  if [ ! -f "$PROJECT_DIR/functions.sh" ]; then
    cp -f functions.sh $PROJECT_DIR || exit 1
  fi

  if [ ! -f "$PROJECT_DIR/build.sh" ]; then
    cp -f build.sh $PROJECT_DIR || exit 1
  fi

  if [ ! -f "$PROJECT_DIR/codeAnalysis.sh" ]; then
    cp -f codeAnalysis.sh $PROJECT_DIR || exit 1
  fi

  if [ ! -f "$PROJECT_DIR/publish.sh" ]; then
    cp -f publish.sh $PROJECT_DIR || exit 1
  fi
}

# Copies required files to new project's directory.
function copyProjectFiles() {
  if [ ! -d $PROJECT_DIR/src/main/resources ]; then
    cp -r src/main/resources $PROJECT_DIR/src/main || exit 1
  fi

  if [ ! -d $PROJECT_DIR/src/main/ui ]; then
    cp -r src/main/ui $PROJECT_DIR/src/main || exit 1
  fi

  if [ ! -d $PROJECT_DIR/src/templates ]; then
    cp -r src/templates $PROJECT_DIR/src || exit 1

    rm -f $PROJECT_DIR/src/templates/build.gradle || exit 1
  fi
}

# Opens the configure's dialog.
function configureDialog() {
  TITLE="CONFIGURE"

  repositoryDialog
  sonarDialog

  createEnvironment
}

# Opens the new project's repository dialog.
function projectRepositoryDialog() {
  OLD_REPOSITORY_URL=$REPOSITORY_URL
  OLD_REPOSITORY_USERNAME=$REPOSITORY_USERNAME
  OLD_REPOSITORY_PASSWORD=$REPOSITORY_PASSWORD

  FRAMEWORK_REPOSITORY_URL=$OLD_REPOSITORY_URL
  FRAMEWORK_REPOSITORY_USERNAME=$OLD_REPOSITORY_USERNAME
  FRAMEWORK_REPOSITORY_PASSWORD=$OLD_REPOSITORY_PASSWORD

  REPOSITORY_URL="${REPOSITORY_URL/framework/$PROJECT_NAME}"

  repositoryDialog

  PROJECT_REPOSITORY_URL=$REPOSITORY_URL
  PROJECT_REPOSITORY_USERNAME=$REPOSITORY_USERNAME
  PROJECT_REPOSITORY_PASSWORD=$REPOSITORY_PASSWORD

  REPOSITORY_URL=$OLD_REPOSITORY_URL
  REPOSITORY_USERNAME=$OLD_REPOSITORY_USERNAME
  REPOSITORY_PASSWORD=$OLD_REPOSITORY_PASSWORD
}

# Opens the new project's sonar dialog.
function projectSonarDialog() {
  OLD_SONAR_ORGANIZATION=$SONAR_ORGANIZATION
  OLD_SONAR_TOKEN=$SONAR_TOKEN

  sonarDialog

  PROJECT_SONAR_ORGANIZATION=$SONAR_ORGANIZATION
  PROJECT_SONAR_TOKEN=$SONAR_TOKEN

  SONAR_ORGANIZATION=$OLD_SONAR_ORGANIZATION
  SONAR_TOKEN=$OLD_SONAR_TOKEN
}

# Opens the new project's dialog.
function newProjectDialog() {
  TITLE="NEW PROJECT"

  projectInputDialog
  projectRepositoryDialog
  projectSonarDialog

  createProjectStructure
  createProjectEnvironment
  createProjectBuild
  createProjectBanner

  copyProjectFiles

  cd $PROJECT_DIR || exit 1

  $GRADLE_CMD wrapper > /dev/null

  mkdir -p /tmp/framework

  LOGS_FILENAME=/tmp/framework/$PROJECT_NAME.log

  ./build.sh > $LOGS_FILENAME &

  $DIALOG_CMD --backtitle "$MAIN_TITLE" \
              --title "$TITLE" \
              --tailbox "$LOGS_FILENAME" \
              20 \
              90

  rm -f $LOGS_FILENAME
}

# Opens the main menu dialog.
function menuDialog() {
  TITLE="LET'S START!"
  OPTION=$($DIALOG_CMD --backtitle "$MAIN_TITLE" \
                       --title "$TITLE" \
                       --default-item "$CURRENT_MENU" \
                       --menu "\nPlease select an option to continue:" \
                       0 \
                       90 \
                       7 \
                       "1. CONFIGURE" "Configure framework attributes." \
                       "2. BUILD" "Start the building process." \
                       "3. CODE ANALYSIS" "Start the code analysis & quality checks." \
                       "4. PUBLISH" "Publish the current build in the repository." \
                       "5. NEW PROJECT" "Creates a new project." \
                       "6. LICENSE" "Know more about licensing." \
                       "0. EXIT" "Exit this setup." 2>&1 > /dev/tty)

  if [ $? -eq 1 ]; then
    clear

    exit 0
  else
    prepareToExecute

    case $OPTION in
      "1. CONFIGURE")
        configureDialog
        menuDialog
        ;;
      "2. BUILD")
        clear ; ./build.sh

        menuDialog
        ;;
      "3. CODE ANALYSIS")
        clear ; ./codeAnalysis.sh

        menuDialog
        ;;
      "4. PUBLISH")
        clear ; ./publish.sh

        menuDialog
        ;;
      "5. NEW PROJECT")
        newProjectDialog
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

# Checks the requirements for the setup.
function checkRequirementsDialog() {
  TITLE="CHECKING REQUIREMENTS"
  BINARIES="java gradle"

  eval "BINARIES=($BINARIES)"

  counter=0
  length=${#BINARIES[@]}

  # Checks if required binaries are installed in the OS.
  for BINARY in "${BINARIES[@]}"
  do
    BINARY_CMD=$(which "$BINARY")

    sleep 0.10

    # Checks if it was found. If don't, exit the setup.
    if [ ! -f "$BINARY_CMD" ]; then
      $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                  --title "$TITLE" \
                  --msgbox "\n$BINARY is not installed! Please install it first to continue!" \
                  7 \
                  60 > /dev/tty

      clear

      exit 1
    else
      # Progress bar update.
      counter=$((counter + (100 / length)))

      echo $counter | $DIALOG_CMD --backtitle "$MAIN_TITLE" \
                                  --title "$TITLE" \
                                  --gauge "\nFinding required software installation..." \
                                  8 \
                                  45
    fi
  done

  sleep 0.10

  $DIALOG_CMD --backtitle "$MAIN_TITLE" \
              --title "$TITLE" \
              --msgbox "\nAll requirements found!" \
              7 \
              35
}

# Checks the dependencies to run this script.
function checkDependencies() {
  DIALOG_CMD=$(which dialog)

  if [ -z "$DIALOG_CMD" ]; then
    echo "dialog is not installed! Please install it first to continue!"
    echo

    exit 1
  fi
}

# Prepares the environment to execute this script.
function prepareToExecute() {
  source functions.sh

  MAIN_TITLE="Concepting Framework $buildVersion"
}

# Main function.
function main() {
  checkDependencies
  prepareToExecute
  checkRequirementsDialog
  menuDialog
}

# Block interrupt/stop signals.
trap "" SIGTSTP
trap "" SIGINT

main "$1"

trap SIGINT
trap SIGTSTP