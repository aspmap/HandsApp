pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Start"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HapsApp Build and Run/target/HandsApp.war /var/lib/jenkins/workspace/HapsApp Build and Run/target/ROOT.war"
        echo "End"
      }
    }
  }
}
