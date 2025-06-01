#!/bin/bash

# Description: Launches ServerPlayer and ClientPlayer in separate JVMs for SEPARATE PID mode
# Usage: Run this script in the project root directory & from terminal on macOS

# Define your Maven project directory
PROJECT_DIR="/Users/rahulshukla/Desktop/360T/RS_Submission/ThreeSixZeroT_F"

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
