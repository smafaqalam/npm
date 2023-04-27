// package com.hcl.icontrol.jenkins

// class ChecksumUtils {
//     static String getChecksum(String... fileNames) {
//         def fileContents = ''
//         fileNames.each { fileName ->
//             fileContents += new File(fileName).text
//         }
//         return fileContents.hashCode().toString().trim()
//     }
// }



package com.hcl.icontrol.jenkins
import hudson.FilePath
import hudson.util.IOUtils

class ChecksumUtils {
    static String getChecksum(String... fileNames) {
        def fileContents = ''
        fileNames.each { fileName ->
            fileContents += new FilePath(fileName).readFile()
        }
        return fileContents.hashCode().toString().trim()
    }
}
