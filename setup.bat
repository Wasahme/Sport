@echo off
REM Smart Medicine Doctor - Setup Script for Windows
REM This script helps set up the Android project

echo 🏥 Smart Medicine Doctor - Setup Script
echo ========================================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 11 or later.
    pause
    exit /b 1
)

echo ✅ Java is installed:
java -version 2>&1 | findstr "version"

REM Check if Android SDK is set
if "%ANDROID_HOME%"=="" (
    echo ⚠️  ANDROID_HOME is not set.
    echo Please set ANDROID_HOME to your Android SDK path.
    echo.
    echo Example:
    echo set ANDROID_HOME=C:\Users\YourUsername\AppData\Local\Android\Sdk
    echo.
    echo Or update the local.properties file with your SDK path.
    echo.
) else (
    echo ✅ ANDROID_HOME is set: %ANDROID_HOME%
)

REM Check if gradle wrapper exists
if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo ❌ Gradle wrapper JAR is missing.
    echo Downloading gradle-wrapper.jar...
    powershell -Command "Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.4.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'"
    if %errorlevel% equ 0 (
        echo ✅ Gradle wrapper JAR downloaded successfully.
    ) else (
        echo ❌ Failed to download gradle wrapper JAR.
        pause
        exit /b 1
    )
) else (
    echo ✅ Gradle wrapper JAR exists.
)

REM Check if local.properties exists
if not exist "local.properties" (
    echo ⚠️  local.properties file is missing.
    echo Creating local.properties with placeholder...
    (
        echo # This file should be updated with your actual Android SDK path
        echo # You can find your SDK path in Android Studio: File ^> Settings ^> Appearance ^& Behavior ^> System Settings ^> Android SDK
        echo # Or set the ANDROID_HOME environment variable
        echo.
        echo # Example paths:
        echo # Windows: sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
        echo # macOS: sdk.dir=/Users/YourUsername/Library/Android/sdk
        echo # Linux: sdk.dir=/home/YourUsername/Android/Sdk
        echo.
        echo # For now, using a placeholder path - please update this with your actual SDK path
        echo sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
    ) > local.properties
    echo ✅ local.properties created. Please update it with your actual SDK path.
) else (
    echo ✅ local.properties exists.
)

echo.
echo 🎯 Setup completed!
echo.
echo Next steps:
echo 1. Update local.properties with your Android SDK path
echo 2. Run: gradlew.bat build
echo 3. Run: gradlew.bat assembleDebug
echo 4. Install on device: gradlew.bat installDebug
echo.
echo For more information, see README.md
pause