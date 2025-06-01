@echo off
REM Description: Launches ServerPlayer and ClientPlayer in separate JVMs for SEPARATE PID mode
REM Usage: Run this script from the root project directory in Windows (cmd)

SET "PROJECT_DIR=C:\Users\rahulshukla\Desktop\360T\RS_Submission\ThreeSixZeroT_F"

IF NOT EXIST "%PROJECT_DIR%" (
    echo Error: Directory does not exist: %PROJECT_DIR%
    exit /b 1
)

echo Launching ServerPlayer...

start "ServerPlayer" cmd /k "cd /d %PROJECT_DIR% && mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ServerPlayer"

REM Allow time for the server to start
timeout /t 10 /nobreak >nul

echo Launching ClientPlayer...

start "ClientPlayer" cmd /k "cd /d %PROJECT_DIR% && mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ClientPlayer"
