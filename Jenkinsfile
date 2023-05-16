pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven_3_6_3"
    }

    stages {
        stage('Test Y Creacion de JAR') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Odra99/InterPackage-Resources.git']])
                sh 'ssh root@137.184.209.89 "cd /home/Interpackage/InterPackage-Resources && git pull origin main && mvn clean install -DskipTests"'
            }
        }

        stage('Detener El Servicio Docker') {
            steps {
                sh 'ssh root@137.184.209.89 "docker stop interpackage-docker-interpackage-service-resource-1"'
                sh 'ssh root@137.184.209.89 "docker rm interpackage-docker-interpackage-service-resource-1"'
            }
        }

        stage ('Desplegar Imgen de Docker'){
            steps{
                script{
                    sh 'ssh root@137.184.209.89 "cd /home/Interpackage/InterPackage-Docker && docker-compose up -d --build interpackage-service-resource"'
                }
            }  
        }

        

        stage('Jar en Produccion') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Odra99/InterPackage-Resources.git']])
                sh 'ssh root@164.90.232.216 "cd /home/Interpackage/InterPackage-Resources && git pull origin main && mvn clean install -DskipTests"'
            }
        }

        stage('Detener en Produccion') {
            steps {
                sh 'ssh root@164.90.232.216 "docker stop interpackage-docker-interpackage-service-resource-1"'
                sh 'ssh root@164.90.232.216 "docker rm interpackage-docker-interpackage-service-resource-1"'
            }
        }

        stage ('Desplegar en Produccion'){
            steps{
                script{
                    sh 'ssh root@164.90.232.216 "cd /home/Interpackage/InterPackage-Docker && docker-compose up -d --build interpackage-service-resource"'
                }
            }  
        }

        
    }
}