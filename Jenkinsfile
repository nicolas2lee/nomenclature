pipeline {
agent any
stages {
  stage('Checkout') {
    steps {
      git(url: 'https://github.com/nicolas2lee/nomenclature', credentialsId: 'nicolas2lee', branch: 'master')
    }
  }
  stage('Compile') {
    steps {
      withMaven(maven: 'MAVEN-3', jdk: 'JDK-8') {
        sh 'mvn clean compile'
      }

    }
  }
  stage('Test') {
    steps {
      withMaven(jdk: 'JDK-8', maven: 'MAVEN-3') {
        sh 'mvn test'
      }

    }
  }
  stage('Package') {
    steps {
      withMaven(jdk: 'JDK-8', maven: 'MAVEN-3') {
        sh 'mvn package -DskipTests'
      }

    }
  }
  stage('Quality') {
    steps {
      withMaven(jdk: 'JDK-8', maven: 'MAVEN-3') {
        sh 'mvn sonar:sonar -Dsonar.projectKey="nomenclature"\
            -Dsonar.host.url=http://localhost:9000 \
            -Dsonar.login=25ba9d58c24285499ea3cc9f98466f5d97a6f061'
      }

    }
  }
}
}