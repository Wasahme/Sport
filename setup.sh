#!/bin/bash

# Smart Medicine Doctor - Setup Script
# This script helps set up the Android project

echo "🏥 Smart Medicine Doctor - Setup Script"
echo "========================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11 or later."
    exit 1
fi

echo "✅ Java is installed: $(java -version 2>&1 | head -n 1)"

# Check if Android SDK is set
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME is not set."
    echo "Please set ANDROID_HOME to your Android SDK path."
    echo ""
    echo "Example:"
    echo "export ANDROID_HOME=/path/to/your/android/sdk"
    echo ""
    echo "Or update the local.properties file with your SDK path."
    echo ""
else
    echo "✅ ANDROID_HOME is set: $ANDROID_HOME"
fi

# Check if gradle wrapper exists
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "❌ Gradle wrapper JAR is missing."
    echo "Downloading gradle-wrapper.jar..."
    curl -L -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.4.0/gradle/wrapper/gradle-wrapper.jar
    if [ $? -eq 0 ]; then
        echo "✅ Gradle wrapper JAR downloaded successfully."
    else
        echo "❌ Failed to download gradle wrapper JAR."
        exit 1
    fi
else
    echo "✅ Gradle wrapper JAR exists."
fi

# Make gradlew executable
chmod +x gradlew

# Check if local.properties exists
if [ ! -f "local.properties" ]; then
    echo "⚠️  local.properties file is missing."
    echo "Creating local.properties with placeholder..."
    cat > local.properties << EOF
# This file should be updated with your actual Android SDK path
# You can find your SDK path in Android Studio: File > Settings > Appearance & Behavior > System Settings > Android SDK
# Or set the ANDROID_HOME environment variable

# Example paths:
# Windows: sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
# macOS: sdk.dir=/Users/YourUsername/Library/Android/sdk
# Linux: sdk.dir=/home/YourUsername/Android/Sdk

# For now, using a placeholder path - please update this with your actual SDK path
sdk.dir=/path/to/your/android/sdk
EOF
    echo "✅ local.properties created. Please update it with your actual SDK path."
else
    echo "✅ local.properties exists."
fi

echo ""
echo "🎯 Setup completed!"
echo ""
echo "Next steps:"
echo "1. Update local.properties with your Android SDK path"
echo "2. Run: ./gradlew build"
echo "3. Run: ./gradlew assembleDebug"
echo "4. Install on device: ./gradlew installDebug"
echo ""
echo "For more information, see README.md"