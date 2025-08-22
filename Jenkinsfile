pipeline {
    agent any
    
    environment {
        ANDROID_HOME = '/opt/android-sdk'
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }
    
    tools {
        jdk 'JDK 17'
        gradle 'Gradle 8.4'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Setup') {
            steps {
                sh 'chmod +x gradlew'
                sh 'echo "sdk.dir=${ANDROID_HOME}" > local.properties'
            }
        }
        
        stage('Build Debug') {
            steps {
                sh './gradlew assembleDebug'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
                }
            }
        }
        
        stage('Build Release') {
            steps {
                sh './gradlew assembleRelease'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'app/build/outputs/apk/release/*.apk', fingerprint: true
                }
            }
        }
        
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    publishTestResults testResultsPattern: '**/test-results/**/*.xml'
                }
            }
        }
        
        stage('Lint') {
            steps {
                sh './gradlew lintDebug'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'app/build/reports/lint-results-debug.html',
                        reportFiles: 'lint-results-debug.html',
                        reportName: 'Lint Report'
                    ])
                }
            }
        }
        
        stage('SonarQube Analysis') {
            when {
                branch 'main'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './gradlew sonarqube'
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}