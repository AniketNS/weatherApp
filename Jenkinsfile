pipeline{
    agent any

    options{
        buildDiscarder(logRotator(numToKeepStr:'10', daysToKeepStr:'5'))
    }

    stages{
        stage('Checkout'){
            steps{
                 git url: 'https://github.com/AniketNS/weatherApp.git', branch: 'main', credentialsId: 'your-credentials-id' 
            }
        }

        stage('Build'){
            steps{
                sh 'mvn clean install'
            }
        }
    }

    triggers{
        //Poll SCM every 3 minutes
        pollSCM('H/3 * * * *')
    }

    
}