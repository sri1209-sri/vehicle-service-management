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
                runMvn 'clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit and integration tests...'
                runMvn 'test'
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
                runCommand "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Logging in and pushing image to Registry...'
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        runCommand "docker login -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}"
                        runCommand "docker tag ${DOCKER_IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                        runCommand "docker tag ${DOCKER_IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"
                        runCommand "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                        runCommand "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Applying Kubernetes Manifests...'
                runCommand 'kubectl apply -f k8s/configmap.yaml'
                runCommand 'kubectl apply -f k8s/secrets.yaml'
                runCommand 'kubectl apply -f k8s/mysql-service.yaml'
                runCommand 'kubectl apply -f k8s/mysql-deployment.yaml'
                runCommand 'kubectl apply -f k8s/pms-service.yaml'
                runCommand 'kubectl apply -f k8s/pms-deployment.yaml'
                runCommand 'kubectl rollout restart deployment/vehiserve-app-deployment'
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

// Cross-Platform Helper Methods
def runMvn(args) {
    if (isUnix()) {
        sh "./mvnw ${args}"
    } else {
        bat "mvnw.cmd ${args}"
    }
}

def runCommand(cmd) {
    if (isUnix()) {
        sh cmd
    } else {
        bat cmd
    }
}
