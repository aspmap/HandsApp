pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Package"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HandsAppPipeline/target/HandsApp.war /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war"
        echo "Deploy"
        sh "scp -r root@195.161.62.229:/opt/tomcat/webapps/ROOT root@195.161.62.229:/opt/backups/Jenkins"
        sh "scp /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war root@195.161.62.229:/opt/tomcat/webapps/111"
        sh "scp -r root@195.161.62.229:/opt/backups/Jenkins/resources/video root@195.161.62.229:/opt/backups/files"
        sh "scp -r root@195.161.62.229:/opt/backups/Jenkins/resources/img root@195.161.62.229:/opt/backups/files"
        echo "End1"
      }
    }
  }
}
