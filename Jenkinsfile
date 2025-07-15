pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'localhost:5000'
        BE_IMAGE = "${DOCKER_REGISTRY}/useful-daily-app-be"
        FE_IMAGE = "${DOCKER_REGISTRY}/useful-daily-app-fe"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Wait for Docker') {
            steps {
                script {
                    sh '''
                        echo "Waiting for Docker daemon to be ready..."
                        for i in {1..30}; do
                            if docker version >/dev/null 2>&1; then
                                echo "Docker is ready!"
                                break
                            fi
                            echo "Waiting... ($i/30)"
                            sleep 2
                        done
                    '''
                }
            }
        }
        
        stage('Build Backend') {
            steps {
                dir('useful-daily-app-bp') {
                    script {
                        sh "docker build -t ${BE_IMAGE}:${BUILD_NUMBER} ."
                        sh "docker tag ${BE_IMAGE}:${BUILD_NUMBER} ${BE_IMAGE}:latest"
                        sh "docker push ${BE_IMAGE}:${BUILD_NUMBER}"
                        sh "docker push ${BE_IMAGE}:latest"
                    }
                }
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('useful-daily-app-fe') {
                    script {
                        sh "docker build -t ${FE_IMAGE}:${BUILD_NUMBER} ."
                        sh "docker tag ${FE_IMAGE}:${BUILD_NUMBER} ${FE_IMAGE}:latest"
                        sh "docker push ${FE_IMAGE}:${BUILD_NUMBER}"
                        sh "docker push ${FE_IMAGE}:latest"
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    sh '''
                        docker-compose -f docker-compose.app.yml down
                        docker-compose -f docker-compose.app.yml up -d
                    '''
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
