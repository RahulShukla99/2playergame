#!/bin/bash

# Description: Starts the Java application for SAME PID message exchange
# Usage: Run this script in the project root directory & from terminal on macOS
# Ensure separate.pid.mode is false in Config property

CONFIG_FILE="src/main/resources/config.properties"

# Check if config file exists
if [ ! -f "$CONFIG_FILE" ]; then
  echo " Config file not found at $CONFIG_FILE"
  exit 1
fi

# Extract value of separate.pid.mode
SEPARATE_PID_MODE=$(grep -E "^separate\.pid\.mode\s*=" "$CONFIG_FILE" | cut -d'=' -f2 | tr -d '[:space:]')

# Validate that it's explicitly set to false
if [ "$SEPARATE_PID_MODE" != "false" ]; then
  echo " Cannot continue: 'separate.pid.mode' must be set to 'false' in $CONFIG_FILE"
  exit 1
fi

SCRIPT_DIR=$(pwd)

osascript <<EOF
tell application "Terminal"
    do script "cd $SCRIPT_DIR; mvn clean compile exec:java -Dexec.mainClass=com.messaging.Main"
end tell
EOF
