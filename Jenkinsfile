pipeline {
    agent any
    environment {
        COMMIT_HASH = "${sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()}"
        IMG_NAME = "restaurant-service"
        AWS_ID = credentials('aws-id')
        REPO_URL = credentials('service-restaurant')
    }

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Clean target') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh "mvn test package"

            }
        }
        // stage('Code Analysis: Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('SonarQube') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }
        // }
        // stage('Await Quality Gateway') {
        //     steps {
        //         waitForQualityGate abortPipeline: true
        //     }
        // }
        stage("Docker Build") {
            steps {
                echo "Docker Build...."
                sh 'aws ecr get-login-password --region us-west-1 | docker login --username AWS --password-stdin ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com'
                sh "docker build --tag ${IMG_NAME}:${COMMIT_HASH} ."
                sh 'docker tag ${IMG_NAME}:${COMMIT_HASH} ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com/${REPO_URL}:${COMMIT_HASH}'
                echo "Docker Push..."
                sh 'docker push ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com/${REPO_URL}:${COMMIT_HASH}'
            }
        }
//         stage("Deploy") {
//             steps {
//                 echo "Deploying cloudformation.."
//                 sh "aws cloudformation deploy --stack-name UserMsStack --template-file ./ecs.yaml --parameter-overrides 
//                 PortNumber=8080 ListenerArn=arn:aws:elasticloadbalancing:us-east-2:170505770705:listener/app/Aline-Private-LB/38a8e6d26b981100/a4866b14508da3b5 
//                 ApplicationName=${IMG_NAME} CommitHash=${COMMIT_HASH} ApplicationEnvironment=dev 
//                 --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM --region us-west-1"
//             }
//         }
    }
    post {
        always {
            sh 'mvn clean'
            sh "docker system prune -f"
        }
    }
}
