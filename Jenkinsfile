pipeline {
 agent any
 stages {
 stage('Build') { 
steps {
 sh 'mvn -B -DskipTests clean package' 
}
 }
 stage('K8s') {
 steps {
 sh 'kubectl set image deployments/hello-node hello-node-5dd5dcf7b4-2xklx=sismics/docs:v1.11'
 }
 }
 }
 }
