final def pipelineSdkVersion = 'v36'

pipeline {
    agent { label 'slave3' } 

    options {
        timeout(time: 120, unit: 'MINUTES')
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '999', artifactNumToKeepStr: '999'))
        skipDefaultCheckout()
        disableConcurrentBuilds()
    }
    
    stages {
        stage('Init') {
          steps {
                milestone 10
                library "s4sdk-pipeline-library@${pipelineSdkVersion}"
                stageInitS4sdkPipeline script: this, nodeLabel: 'slave3'
                abortOldBuilds script: this
            }
        }

        stage('Build and Test') {
          steps {
                milestone 20
                stageBuild script: this
            }
        }
   
        stage("Static Code Checks") {
          when { expression { commonPipelineEnvironment.configuration.runStage.STATIC_CODE_CHECKS } }
            steps { piperPipelineStageMavenStaticCodeChecks script: this }
        }

        stage("Lint") {
            when { expression { commonPipelineEnvironment.configuration.runStage.LINT } }
            steps { stageLint script: this }
        }
        
        stage("Backend Integration Tests") {
            when { expression { commonPipelineEnvironment.configuration.runStage.BACKEND_INTEGRATION_TESTS } }
            steps { stageBackendIntegrationTests script: this }
        }
        
        stage("Frontend Integration Tests") {
            when { expression { commonPipelineEnvironment.configuration.runStage.FRONTEND_INTEGRATION_TESTS } }
            steps { stageFrontendIntegrationTests script: this }
        }
                
        stage("Frontend Unit Tests") {
            when { expression { commonPipelineEnvironment.configuration.runStage.FRONTEND_UNIT_TESTS } }
            steps { stageFrontendUnitTests script: this }
        }

        stage("NPM Dependency Audit") {
            when { expression { commonPipelineEnvironment.configuration.runStage.NPM_AUDIT } }
            steps { stageNpmAudit script: this }
        }

        stage('Remote Tests') {
            when { expression { commonPipelineEnvironment.configuration.runStage.REMOTE_TESTS } }
            parallel {
                stage("End to End Tests") {
                    when { expression { commonPipelineEnvironment.configuration.runStage.E2E_TESTS } }
                    steps { stageEndToEndTests script: this }
                }
                stage("Performance Tests") {
                    when { expression { commonPipelineEnvironment.configuration.runStage.PERFORMANCE_TESTS } }
                    steps { stagePerformanceTests script: this }
                }
            }
        }

        stage('Quality Checks') {
            when { expression { commonPipelineEnvironment.configuration.runStage.QUALITY_CHECKS } }
            steps {
                milestone 50
                stageS4SdkQualityChecks script: this
            }
        }

        stage('Third-party Checks') {
            //when { expression { commonPipelineEnvironment.configuration.runStage.THIRD_PARTY_CHECKS } }
            parallel {
                stage('SonarQube Scan'){
                    //when { expression { commonPipelineEnvironment.configuration.runStage.SONARQUBE_SCAN } }
                    steps { stageSonarQubeScan script: this }
                }
            }
        }

        stage('Artifact Deployment') {
            when { expression { commonPipelineEnvironment.configuration.runStage.ARTIFACT_DEPLOYMENT } }
            steps {
                milestone 70
                piperPipelineStageArtifactDeployment script: this
            }
        }
      
        stage('Production Deployment') {
            when{
                anyOf{
                  	expression{env.BRANCH_NAME == 'QA'}
                    expression{env.BRANCH_NAME == 'develop'}
                }
            }
            steps { 
                stageProductionDeployment script: this 
            }
        }
      
        stage('TMS Upload') {
            when{
                anyOf{
                    expression{env.BRANCH_NAME == 'master'}
                }
            }
            steps { 
                stageProductionDeployment script: this 
            }
        }
    }
    
    post {
        always {
            script {
               debugReportArchive script: this
                //if (commonPipelineEnvironment?.configuration?.runStage?.SEND_NOTIFICATION) {
                    postActionSendNotification script: this
                //}
                postActionCleanupStashesLocks script: this
                sendAnalytics script: this

                if (commonPipelineEnvironment?.configuration?.runStage?.POST_PIPELINE_HOOK) {
                    stage('Post Pipeline Hook') {
                        stagePostPipelineHook script: this
                    }
                }
            }
        }
        success {
            script {
               if (commonPipelineEnvironment?.configuration?.runStage?.ARCHIVE_REPORT) {
                    postActionArchiveReport script: this
                }
            }
        }          
        failure {
          deleteDir()
        }
    }
}
