pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Start"
        sh "mvn package"
        echo "End"
      }
    }
  }
}
