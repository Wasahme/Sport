# Makefile for FitTracker Android App

.PHONY: help clean build debug release test lint docker-build docker-run

# Default target
help:
	@echo "FitTracker Android App - Available commands:"
	@echo ""
	@echo "  build      - Build debug APK"
	@echo "  debug      - Build debug APK"
	@echo "  release    - Build release APK"
	@echo "  clean      - Clean build files"
	@echo "  test       - Run tests"
	@echo "  lint       - Run lint checks"
	@echo "  install    - Install debug APK on connected device"
	@echo "  docker-build - Build using Docker"
	@echo "  docker-run   - Run build in Docker container"
	@echo "  setup      - Setup project (first time only)"

# Setup project (first time)
setup:
	@echo "Setting up FitTracker project..."
	chmod +x gradlew
	@echo "Setup complete! Run 'make build' to build the app."

# Clean build files
clean:
	@echo "Cleaning build files..."
	./gradlew clean

# Build debug APK
build: debug

# Build debug APK
debug:
	@echo "Building debug APK..."
	./gradlew assembleDebug
	@echo "Debug APK built successfully!"
	@echo "Location: app/build/outputs/apk/debug/app-debug.apk"

# Build release APK
release:
	@echo "Building release APK..."
	./gradlew assembleRelease
	@echo "Release APK built successfully!"
	@echo "Location: app/build/outputs/apk/release/app-release.apk"

# Run tests
test:
	@echo "Running tests..."
	./gradlew test
	@echo "Tests completed!"

# Run lint checks
lint:
	@echo "Running lint checks..."
	./gradlew lintDebug
	@echo "Lint checks completed!"

# Install debug APK on connected device
install:
	@echo "Installing debug APK on connected device..."
	adb install -r app/build/outputs/apk/debug/app-debug.apk
	@echo "APK installed successfully!"

# Build using Docker
docker-build:
	@echo "Building APK using Docker..."
	docker build -t fittracker-builder .
	@echo "Docker build completed!"

# Run build in Docker container
docker-run:
	@echo "Running build in Docker container..."
	docker run -v $(PWD):/workspace fittracker-builder ./gradlew assembleDebug
	@echo "Docker build completed!"

# Build using Docker Compose
docker-compose-build:
	@echo "Building APK using Docker Compose..."
	docker-compose --profile build up
	@echo "Docker Compose build completed!"

# Build release using Docker Compose
docker-compose-release:
	@echo "Building release APK using Docker Compose..."
	docker-compose --profile release up
	@echo "Docker Compose release build completed!"

# Show APK info
apk-info:
	@echo "APK Information:"
	@if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then \
		echo "Debug APK:"; \
		ls -lh app/build/outputs/apk/debug/app-debug.apk; \
	else \
		echo "Debug APK not found. Run 'make build' first."; \
	fi
	@if [ -f "app/build/outputs/apk/release/app-release.apk" ]; then \
		echo "Release APK:"; \
		ls -lh app/build/outputs/apk/release/app-release.apk; \
	else \
		echo "Release APK not found. Run 'make release' first."; \
	fi

# Full build process
all: clean build test lint apk-info
	@echo "Full build process completed!"

# Development workflow
dev: build install
	@echo "Development workflow completed!"

# Production workflow
prod: clean release lint apk-info
	@echo "Production workflow completed!"