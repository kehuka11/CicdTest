pipeline {
    agent any
    environment {
        container_count = 0
        container_health = 0
    }

    stages {


        stage('Checkout') {
            steps {
                script {
                    try {
                        def branch = env.BRANCH_NAME
                        checkout([$class: 'GitSCM', branches: [[name: "*/${params.branch}"]], userRemoteConfigs: [[url: '/home/repo']]])
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Checkout failed: ${e.message}")
                    }
                }
            }
        }

         stage('Create Docker network') {
             steps {
                 script {
                     try {
                         sh 'docker network create cicd_test'
                     }catch(Exception e) {
                         currentBuild.result = 'FAILURE'
                         error("docker-network failed: ${e.message}")
                     }
                 }
             }
         }

        stage('Start Container') {
            steps {
                script {
                    try {
                        sh 'cd ./demo; ls -la; PATH=$PATH:/usr/local/bin/docker-compose; docker-compose up -d'

                        while(container_count == container_health) {
                            sleep(time:60, unit: 'SECONDS')
                            env.result = sh 'docker ps | grep healthy'
                            container_health = env.result
                        }
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
                        sh 'cd ./demo;  chmod +x ./gradlew; ./gradlew flywayMigrate'
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Flyway Migrate failed: ${e.message}")
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    try {
                        sh 'cd ./demo; chmod +x ./gradlew; ./gradlew test'
                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("test failed: ${e.message}")
                    }
                }
            }
        }

        stage('Down Container') {
            steps {
                script {
                    try {
                        sh 'cd ./demo; docker-compose up -d'


                    } catch(Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("docker-compose failed: ${e.message}")
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