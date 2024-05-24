pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    try {
                        def branch = env.BRANCH_NAME
                        checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], userRemoteConfigs: [[url: '']]])
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Checkout failed: ${e.message}")
                    }
                }
            }
        }

        stage('Start Docker') {
            steps {
                script {
                    try {
                        sh 'docker-compose up -d'

                        sleep(time:120, unit: 'SECONDS')
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("docker-compose failed: ${e.message}")
                    }
                }
            }
        }

        stage('Flyway Migrate') {
            steps {
                script {
                    try {
                        sh './gradlew flywayMigrate'
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Flyway Migrate failed: ${e.message}")
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (currentBuild.result == 'FAILURE') {
                    echo "Unit Test failed at stage: ${env.STAGE_NAME}"
                } else {
                    echo 'Unit Test succeeded'
                }
            }
        }
        failure {
            echo 'Unit Test failed'
        }
    }
}