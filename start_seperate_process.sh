#!/bin/bash

# Description: Launches ServerPlayer and ClientPlayer in separate JVMs for SEPARATE PID mode
# Usage: Run this script in the project root directory & from terminal on macOS

# Define your Maven project directory
PROJECT_DIR="/Users/rahulshukla/Desktop/360T/RS_Submission/ThreeSixZeroT_F"
CONFIG_FILE="$PROJECT_DIR/src/main/resources/config.properties"

# Extract value of separate.pid.mode
SEPARATE_PID_MODE=$(grep -E "^separate\.pid\.mode\s*=" "$CONFIG_FILE" | cut -d'=' -f2 | tr -d '[:space:]')

# Validate that it's explicitly set to false
if [ "$SEPARATE_PID_MODE" != "true" ]; then
  echo " Cannot continue: 'separate.pid.mode' must be set to 'true' in $CONFIG_FILE"
  exit 1
fi

# Start ServerPlayer
osascript -e "tell application \"Terminal\"
    do script \"cd $PROJECT_DIR; mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ServerPlayer\"
end tell"

# Wait to ensure the server starts before client connects
sleep 10

# Start ClientPlayer
osascript -e "tell application \"Terminal\"
    do script \"cd $PROJECT_DIR; mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ClientPlayer\"
end tell"
