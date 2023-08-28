pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "Package"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HandsAppPipeline/target/HandsApp.war /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war"
        echo "Deploy"
        sh "mkdir root@195.161.62.229:/opt/backups/234"
        sh "scp -r root@195.161.62.229:/opt/tomcat/webapps/ROOT root@195.161.62.229:/opt/backups/handsapp_backup"
        sh "scp /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war root@195.161.62.229:/opt/tomcat/webapps/111"
        echo "End1"
      }
    }
  }
}
