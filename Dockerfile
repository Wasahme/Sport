# Dockerfile for FitTracker Android App
FROM openjdk:17-jdk

# Set environment variables
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Install required packages
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Download and install Android Command Line Tools
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip \
    && unzip commandlinetools-linux-8512546_latest.zip -d /opt/android-sdk \
    && rm commandlinetools-linux-8512546_latest.zip

# Create proper directory structure
RUN mkdir -p /opt/android-sdk/cmdline-tools/latest \
    && mv /opt/android-sdk/cmdline-tools/* /opt/android-sdk/cmdline-tools/latest/ 2>/dev/null || true

# Accept licenses and install Android SDK components
RUN yes | sdkmanager --licenses
RUN sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

# Set working directory
WORKDIR /workspace

# Copy project files
COPY . .

# Make gradlew executable
RUN chmod +x gradlew

# Default command
CMD ["./gradlew", "assembleDebug"]