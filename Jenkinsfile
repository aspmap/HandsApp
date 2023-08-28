pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Start"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HandsAppPipeline/target/HandsApp.war /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war"
        sh "scp /home/pavlov/ROOT.war root@195.161.62.229:/opt/tomcat/webapps/"
        echo "End"
      }
    }
  }
}
