@echo off
setlocal ENABLEDELAYEDEXPANSION

REM === Configuration ===
set "PROJECT_DIR=C:\Users\rahulshukla\Desktop\360T\RS_Submission\ThreeSixZeroT_F"
set "CONFIG_FILE=%PROJECT_DIR%\src\main\resources\config.properties"
set "EXPECTED_VALUE=false"
set "MAIN_CLASS=com.messaging.Main"
set "LAUNCH_CMD=mvn clean compile exec:java -Dexec.mainClass=%MAIN_CLASS%"

REM === Validate Project Directory ===
if not exist "%PROJECT_DIR%" (
    echo [ERROR] Project directory not found: %PROJECT_DIR%
    exit /b 1
)

REM === Validate Config File ===
if not exist "%CONFIG_FILE%" (
    echo [ERROR] Configuration file not found: %CONFIG_FILE%
    exit /b 1
)

REM === Read 'separate.pid.mode' from config.properties ===
set "SEPARATE_PID_MODE="
for /f "usebackq tokens=1,* delims==" %%A in ("%CONFIG_FILE%") do (
    set "KEY=%%A"
    set "VALUE=%%B"
    if /I "!KEY!"=="separate.pid.mode" (
        set "SEPARATE_PID_MODE=!VALUE!"
    )
)

REM === Trim and Normalize Value ===
for /f "tokens=* delims= " %%A in ("!SEPARATE_PID_MODE!") do (
    set "SEPARATE_PID_MODE=%%A"
)

REM === Validate Expected Value ===
if /I not "!SEPARATE_PID_MODE!"=="%EXPECTED_VALUE%" (
    echo [ERROR] 'separate.pid.mode' must be set to '%EXPECTED_VALUE%' in %CONFIG_FILE%
    echo [INFO] Current value: "!SEPARATE_PID_MODE!"
    exit /b 1
)

REM === Launch the Java App in Same PID Mode ===
echo [INFO] Launching Java application in SAME PID mode...
start "SamePIDRunner" cmd /k "cd /d %PROJECT_DIR% && %LAUNCH_CMD%"

exit /b 0
