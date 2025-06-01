#!/bin/bash

# Description: Starts the Java application for SAME PID message exchange
# Usage: Run this script in the project root directory

SCRIPT_DIR=$(pwd)

osascript <<EOF
tell application "Terminal"
    do script "cd $SCRIPT_DIR; mvn clean compile exec:java -Dexec.mainClass=com.messaging.Main"
end tell
EOF
