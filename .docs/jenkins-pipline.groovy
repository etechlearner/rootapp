pipeline {
    environment {
        registryCredential = 'DockerHub'
        PROJECT_ID = 'dev-gymrabbit'
        ZONE = 'us-central1-a'
        CLUSTER_NAME = 'gke-dev'
        IMAGE_NAME = 'rootapp'
        REGISTRY = "gymrabbit"
        REGISTRY_WITH_IMAGE = "$REGISTRY/$IMAGE_NAME"
        IMAGE_VERSION = getShortCommitHash()
        // IMAGE_VERSION = '2'
    }
    agent any
    stages {
        stage('Cloning Git') {
            steps {
                git 'git@gitlab.com:appiskeydev/java/gymrabbit/rootapp.git'
                sh 'git fetch --all'
                sh 'git reset --hard origin/master'
            }
        }
        stage('Build Artifact') {
            steps {
                sh 'rm -rf target'
                sh './mvnw -DskipTests package'
            }
        }
        stage('Building Docker image') {
            steps {
                script {
                    sh("docker build -t $REGISTRY_WITH_IMAGE:$IMAGE_VERSION .")
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        sh("docker push $REGISTRY_WITH_IMAGE:$IMAGE_VERSION")
                    }
                }
            }
        }
        stage('Remove Unused docker image') {
            steps {
                sh "docker rmi $REGISTRY_WITH_IMAGE:$IMAGE_VERSION"
            }
        }
        stage('Deploy on Dev Server') {
            steps {
                withCredentials([file(credentialsId: 'GC_KEY', variable: 'GC_KEY')]) {
                    sh("gcloud auth activate-service-account --key-file=${GC_KEY}")
                    sh("gcloud container clusters get-credentials $CLUSTER_NAME --zone $ZONE --project $PROJECT_ID")
                    sh("cat .docs/deployment.yaml | sed -e 's/KVERSION/${env.IMAGE_VERSION}/g' -e 's/KAPP_NAME/$IMAGE_NAME/g' -e 's/KIMAGE/$IMAGE_NAME/g' -e 's/KREGISTRY/$REGISTRY/g' | kubectl apply -f-")
                }
            }
        }
    }
}

def getShortCommitHash() {
    commitNumber = '1'
    try {
        commitNumber = 'd' + sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    } catch (err) {
        echo err.getMessage()
        echo "Error detected, but we will continue."
    }
    return commitNumber;

}