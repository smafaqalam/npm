#!/usr/bin/env groovy
import com.hcl.icontrol.jenkins.TextUtils

def call(Map config) {
    try {
        String bucketName = "gs://${env.JENKINS_GCS_BUCKET}"
        //def newFileMap = config.subMap(config.keySet().toArray()[0..-2])
        Collection<String> checksumFiles = (config.CHECKSUM_FILES ?: []) as Collection<String>
        String checksum = config.checksum ?: checkSum(checksumFiles)
        String job = TextUtils.sanitizeCacheKey("${env.JOB_NAME}")

        def checksumfileExist = sh(script: "gsutil stat ${bucketName}/${job}-${config.NAME}-${checksum}", returnStatus: true) as Integer   
        def cachefileExist = sh(script: "gsutil stat ${bucketName}/${job}-${config.NAME}.tar.gz", returnStatus: true) as Integer
        if (checksumfileExist == 0 && cachefileExist == 0) {
            log('DEBUG', "Cache hit! Skipping npm-ci.")
            return true
        } 
        else {
            log('DEBUG', "Cache miss! Running npm-ci.")
            sh "echo ${checksum} > ${job}-${config.NAME}-${checksum}"
	        sh "gsutil cp ${job}-${config.NAME}-${checksum} ${bucketName}"
            return false
         } 
    } catch (Exception e) {
        log('DEBUG', "Error checking cache validity: ${e}")
        return false
    }
}
