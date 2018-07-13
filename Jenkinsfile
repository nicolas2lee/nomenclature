pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    agent any

    stages {
      stage('Checkout') {
        steps {
          git(url: 'https://github.com/nicolas2lee/nomenclature', credentialsId: 'nicolas2lee', branch: 'master')
        }
      }
      stage('Compile') {
        steps {
          withMaven() {
            sh './mvnw clean compile'
          }

        }
      }
      stage('Test') {
        steps {
          withMaven() {
            sh './mvnw test'
          }

        }
      }
      stage('Package') {
        steps {
          withMaven() {
            sh './mvnw package -DskipTests'
          }

        }
      }
      stage('Quality') {
        steps {
          withMaven() {
            sh './mvnw sonar:sonar -Dsonar.projectKey="nomenclature"\
                -Pdocker'
          }

        }
      }
    }
}