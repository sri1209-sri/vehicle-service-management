pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'vehiserve-app'
        DOCKER_REGISTRY = 'docker.io/myusername' // Update with real registry details if needed
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials' // Jenkins Credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from Git...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot JAR...'
                // Uses Maven Wrapper to build without local Maven installation
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit and integration tests...'
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker Image...'
                sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Logging in and pushing image to Registry...'
                // Note: Wrapped inside catchError or comment out if credentials are not set up yet
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}"
                        sh "docker tag ${DOCKER_IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                        sh "docker tag ${DOCKER_IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"
                        sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                        sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Applying Kubernetes Manifests...'
                // Triggers local kubectl deployment
                sh 'kubectl apply -f k8s/configmap.yaml'
                sh 'kubectl apply -f k8s/secrets.yaml'
                sh 'kubectl apply -f k8s/mysql-service.yaml'
                sh 'kubectl apply -f k8s/mysql-deployment.yaml'
                sh 'kubectl apply -f k8s/pms-service.yaml'
                sh 'kubectl apply -f k8s/pms-deployment.yaml'
                sh 'kubectl rollout restart deployment/vehiserve-app-deployment'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please inspect logs.'
        }
    }
}
