@echo off
REM Description: Launches ServerPlayer and ClientPlayer in separate JVMs for SEPARATE PID mode
REM Usage: Run this script from the root project directory in Windows (cmd)

SET "PROJECT_DIR=C:\Users\rahulshukla\Desktop\360T\RS_Submission\ThreeSixZeroT_F"
SET "CONFIG_FILE=%PROJECT_DIR%\src\main\resources\config.properties"

REM Check project directory exists
IF NOT EXIST "%PROJECT_DIR%" (
    echo Error: Directory does not exist: %PROJECT_DIR%
    exit /b 1
)

REM Check config.properties exists
IF NOT EXIST "%CONFIG_FILE%" (
    echo Error: config.properties not found at %CONFIG_FILE%
    exit /b 1
)

REM Check if separate.pid.mode=true is set
FOR /F "usebackq tokens=1,* delims==" %%A IN ("%CONFIG_FILE%") DO (
    SET "key=%%A"
    SET "value=%%B"
    SETLOCAL ENABLEDELAYEDEXPANSION
    IF /I "!key!"=="separate.pid.mode" (
        IF /I "!value!" NEQ "true" (
            echo Error: 'separate.pid.mode' must be set to 'true' in %CONFIG_FILE%
            exit /b 1
        )
        ENDLOCAL
    )
)

echo Launching ServerPlayer...

start "ServerPlayer" cmd /k "cd /d %PROJECT_DIR% && mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ServerPlayer"

REM Wait before launching client
timeout /t 10 /nobreak >nul

echo Launching ClientPlayer...

start "ClientPlayer" cmd /k "cd /d %PROJECT_DIR% && mvn compile exec:java -Dexec.mainClass=com.messaging.separatePID.ClientPlayer"
