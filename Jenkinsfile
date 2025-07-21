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
        
        stage('Build Backend') {
            steps {
                dir('useful-daily-app-bp') {
                    script {
                        def beImage = docker.build("${BE_IMAGE}:${BUILD_NUMBER}")
                        beImage.push()
                        beImage.push('latest')
                    }
                }
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('useful-daily-app-fe') {
                    script {
                        def feImage = docker.build("${FE_IMAGE}:${BUILD_NUMBER}")
                        feImage.push()
                        feImage.push('latest')
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    sh '''
                        # 只停止應用容器，不使用 down 命令
                        docker stop useful-daily-app-be useful-daily-app-fe || true
                        docker rm useful-daily-app-be useful-daily-app-fe || true
                        
                        # 重新啟動應用
                        docker compose -f docker-compose.app.yml -p useful-daily-app up -d
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