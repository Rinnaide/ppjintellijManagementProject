pipeline {
    agent any
    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/Rinnaide/ppjintellijManagementProject']]])
            }
        }

        stage('Instalar Dependências') {
            steps {
                script {
                    // Define JAVA_HOME para o JDK 21 instalado
                    env.JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"
                    // Atualiza o PATH para incluir o bin do Java
                    env.PATH = "${env.JAVA_HOME}\\bin:$PATH"
                    // Instalar as dependências Maven antes de compilar o projeto
                    bat 'cd backend && mvnw clean install'  // Instala as dependências do Maven
                }
            }
        }

        stage('Verificar Docker') {
            steps {
                script {
                    // Verifica se o Docker está disponível e funcionando
                    bat 'docker version'
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