@echo off
setlocal ENABLEDELAYEDEXPANSION

REM === Configuration ===
set "CONFIG_FILE=src\main\resources\config.properties"
set "EXPECTED_VALUE=false"
set "LAUNCH_CMD=mvn clean compile exec:java -Dexec.mainClass=com.messaging.Main"
PROJECT_DIR="/Users/rahulshukla/Desktop/360T/RS_Submission/ThreeSixZeroT_F"

rem === Check if config file exists ===
if not exist "%CONFIG_FILE%" (
    echo Config file not found: %CONFIG_FILE%
    exit /b 1
)

rem === Read the value of separate.pid.mode ===
set "SEPARATE_PID_MODE="

for /f "usebackq tokens=1,* delims==" %%A in ("%CONFIG_FILE%") do (
    set "KEY=%%A"
    set "VALUE=%%B"
    if /I "!KEY!"=="separate.pid.mode" (
        set "SEPARATE_PID_MODE=!VALUE!"
    )
)

rem === Trim whitespace ===
for /f "tokens=* delims= " %%A in ("!SEPARATE_PID_MODE!") do set "SEPARATE_PID_MODE=%%A"

rem === Validate value ===
if /I not "!SEPARATE_PID_MODE!"=="%EXPECTED_VALUE%" (
    echo Cannot continue: separate.pid.mode must be set to '%EXPECTED_VALUE%' in %CONFIG_FILE%
    exit /b 1
)

rem === Start the Java application ===
echo Launching Java app in Same PID mode...
start cmd /k "%LAUNCH_CMD%"

exit /b 0
