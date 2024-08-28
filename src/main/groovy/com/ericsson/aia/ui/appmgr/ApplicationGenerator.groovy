/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.aia.ui.appmgr

import groovy.text.SimpleTemplateEngine
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.json.JsonParserFactory
import org.springframework.stereotype.Component

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Component
class ApplicationGenerator {


    public static final String FILE_NAME_DATA_FOR_TEMPLATING = 'data-for-templating.json'
    public static final String FILE_NAME_PBA = 'pba.json'

    @Value('${paas.application.generator.dir:target/generated-applications/}')
    String paasDownloadDir

    @Value('${app.base.url:http://localhost:8888}')
    String appBaseUrl

    def renderTemplatePbaInfo(File templateFolder){
        if(templateFolder){
            def templatingData= readAndParseJsonFile(templateFolder,FILE_NAME_DATA_FOR_TEMPLATING)
            def resultFileContent = applyGStringTemplating(new File(templateFolder,FILE_NAME_PBA),templatingData)
            def loadedPba=JsonParserFactory.jsonParser.parseMap(resultFileContent)
            overrideMapValue(templatingData.pbaInfo,loadedPba)
            loadedPba.pba.URN = "$loadedPba.pba.name:$loadedPba.pba.version".toString()
            loadedPba.pba.id = loadedPba.pba.URN
            loadedPba.pba.extensions.each{k,extension->
                extension.URN = "$loadedPba.pba.URN:$extension.name:$extension.version".toString()
                extension.id = extension.URN
            }
            loadedPba
        }
    }




    String createApplicationFromTemplate(def pbaInfo,File sourceFolder) {
        def applicationPath = "${new Date().format('yyyyMMdd-HHmmss')}/${pbaInfo.pba.name}.zip"
        def targetFile = new File(paasDownloadDir,applicationPath)
        def workspaceFolder = new File(targetFile.parentFile, pbaInfo.pba.name)
        workspaceFolder.mkdirs()

        applyApplicationTemplating(sourceFolder, workspaceFolder, pbaInfo)
        packageApplication(targetFile, workspaceFolder)
        applicationPath
    }

    File retrieveApplicationFile(String path) {
        new File(paasDownloadDir, path)
    }

    def applyApplicationTemplating(File sourceFolder, File workspaceFolder, pbaInfo) {
        def applcationTemplateBinding=[
                pbaInfo:pbaInfo,
                metaInfo:[
                    pbaNameInCamelCase: pbaInfo.pba.name.split('-').collect {it.capitalize()}.join('')
                ]
        ]
        sourceFolder.eachFileRecurse { def file ->
            if (file.isFile() && file.name != FILE_NAME_DATA_FOR_TEMPLATING) {
                def targetFile = new File(workspaceFolder, file.absolutePath.substring(sourceFolder.absolutePath.length()))
                targetFile.parentFile.mkdirs()
                targetFile.delete()
                if(file.name.endsWith('.jar')){
                    targetFile.withDataOutputStream { os->
                        file.withDataInputStream { is->
                            os << is
                        }
                    }
                }else{
                    def resultFileContent = applyGStringTemplating(file.text,applcationTemplateBinding)
                    targetFile << resultFileContent
                }
            }
        }

        def templatingData= readAndParseJsonFile(sourceFolder,FILE_NAME_DATA_FOR_TEMPLATING)
        templatingData.renames?.each{def renameDef->
            def sourceFile=new File(workspaceFolder, renameDef.from)
            def targetFile=new File(workspaceFolder,applyGStringTemplating(renameDef.to,applcationTemplateBinding))
            targetFile.parentFile.mkdirs()
            targetFile << sourceFile.text
            sourceFile.delete()
        }
    }


    String applyGStringTemplating(def inputText, def templateBinding){
        templateBinding.appBaseUrl=appBaseUrl
        def stringTemplate = new SimpleTemplateEngine().createTemplate(inputText)
        stringTemplate.make(templateBinding).toString()
    }

    def packageApplication(File targetFile, File workspaceFolder) {
        def zipFile = new ZipOutputStream(new FileOutputStream(targetFile))
        addFolderToArhieve(zipFile, '', workspaceFolder)
        zipFile.close()
    }

    def addFolderToArhieve(def zipFile, String pathPrefix, File folder) {
        folder.listFiles().each { def file ->
            if (file.isFile()) {
                zipFile.putNextEntry(new ZipEntry("$pathPrefix$file.name"))
                file.withInputStream { i ->
                    zipFile << i
                }
                zipFile.closeEntry()
            } else {
                String folderPath = "$pathPrefix$file.name/"
                zipFile.putNextEntry(new ZipEntry(folderPath))
                addFolderToArhieve(zipFile, folderPath, file)
                zipFile.closeEntry()
            }
        }
    }

    def overrideMapValue(from,to){
        if(from instanceof Map){
            from.each {k,v->
                if(v instanceof Map){
                    overrideMapValue(v,to[k])
                }else{
                    to[k]=v
                }
            }
        }
    }

    def readAndParseJsonFile(File baseFolder, String fileName) {
        def pbaFile = new File(baseFolder, fileName)
        if (pbaFile.exists()) {
            JsonParserFactory.jsonParser.parseMap(pbaFile.text)
        }
    }

}
