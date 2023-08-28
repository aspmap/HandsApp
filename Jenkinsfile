pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Start"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HapsApp%20Build%20and%20Run/target/HandsApp.war /var/lib/jenkins/workspace/HapsApp%20Build%20and%20Run/target/ROOT.war"
        echo "End"
      }
    }
  }
}
