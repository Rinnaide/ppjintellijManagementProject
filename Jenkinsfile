pipeline {
    agent any
    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/Rinnaide/ppjintellijManagementProject.git']]])
            }
        }

        stage('Instalar Dependências') {
            steps {
                script {
                    // Atualiza o PATH se necessário
                    env.PATH = "/usr/bin:$PATH"
                    // Instalar as dependências Maven antes de compilar o projeto
                    bat 'mvnw clean install'  // Instala as dependências do Maven
                }
            }
        }

        stage('Verificar Docker') {
            steps {
                script {
                    // Tentar iniciar o Docker Desktop se não estiver em execução
                    powershell 'Start-Process "C:\\Program Files\\Docker\\Docker\\Docker Desktop.exe" -NoNewWindow'
                    // Aguardar 30 segundos para o Docker iniciar
                    bat 'timeout /t 30 /nobreak > nul'
                    try {
                        bat 'docker version'
                    } catch (Exception e) {
                        echo 'Docker não pôde ser iniciado ou não está em execução. Verifique se o Docker Desktop está instalado e tente novamente.'
                        throw e
                    }
                }
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    def appName = 'projectmanagement'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Construir a imagem Docker
                    bat "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'projectmanagement'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Parar e remover o container existente, se houver
            		bat "docker stop ${appName} || exit 0"
            		bat "docker rm -v ${appName} || exit 0"  // Remover o container e os volumes associados

                    // Executar o novo container
                    bat "docker-compose up -d --build"
                }
            }
        }
    }
    post {
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Houve um erro durante o deploy.'
        }
    }
}