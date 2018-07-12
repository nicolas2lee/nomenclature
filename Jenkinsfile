pipeline {
agent any
tools {
    maven 'MAVEN-3.5.4'
    jdk 'JDK-8'
}
stages {
  stage('Checkout') {
    steps {
      git(url: 'https://github.com/nicolas2lee/nomenclature', credentialsId: 'nicolas2lee', branch: 'master')
    }
  }
  stage('Compile') {
    steps {
      withMaven() {
        sh 'mvn clean compile'
      }

    }
  }
  stage('Test') {
    steps {
      withMaven() {
        sh 'mvn test'
      }

    }
  }
  stage('Package') {
    steps {
      withMaven() {
        sh 'mvn package -DskipTests'
      }

    }
  }
  stage('Quality') {
    steps {
      withMaven() {
        sh 'mvn sonar:sonar -Dsonar.projectKey="nomenclature"\
            -Dsonar.host.url=http://localhost:9000 \
            -Dsonar.login=25ba9d58c24285499ea3cc9f98466f5d97a6f061'
      }

    }
  }
}
}