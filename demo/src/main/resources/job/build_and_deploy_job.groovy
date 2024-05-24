pipeline {
    agent any

    environment {
        DEPLOY_DIR = ''
    }

    triggers {
        githubPush()
    }

    options {
        timeout(time: 10, unit: 'MINITUS')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    try {
                        def branch = "main" // ビルド対象のブランチ
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: "*/${branch}"]],
                            userRemoteConfigs: [[url: 'remote branch url']]
                        ])
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Checkout failed: ${e.message}")
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        def gradleDir = "${env.WORKSPACE}" // リポジトリ内のGradle格納ディレクトリ
                        sh "cd ${gradleDir} ; ./gradlew bootjar" 

                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Build failed: ${e.message}")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    try {
                        def jarFile = findFiles(glob: '**/build/libs/*.jar')
                        jarFile.each { file ->
                            sh "cp ${file.path} ${DEPLOY_DIR}"
                        }
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Deploy failed: ${e.message}")
                    }
                }
            }
        }

        post {
            always {
                script {
                    if (currentBuild.result == 'FAILURE') {
                        echo "Build failed at stage: ${env.STAGE_NAME}"
                    } else {
                        echo 'Build and Deploy succeeded'
                    }
                }
            }
            failure {
                echo 'Build and Deploy failed'
            }
        }
    }
}