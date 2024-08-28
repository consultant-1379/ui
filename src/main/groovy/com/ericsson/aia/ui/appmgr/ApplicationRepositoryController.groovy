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

import com.ericsson.aia.ui.store.Store
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.json.JsonParserFactory
import org.springframework.core.io.FileSystemResource
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

@CrossOrigin
@RestController
public class ApplicationRepositoryController {

    public static final String GROUP_ID_APPLICATIONS = 'com.ericsson.aia.paas.applications'

    @Value('${app.base.url:http://localhost:8888}')
    String appBaseUrl

    @Autowired
    Store store

    @RequestMapping(value = '/paas/v1/repositories/applications', produces = 'application/json')
    def listApplications(HttpServletResponse response) {
        response.setHeader('Cache-Control', 'no-store')

        List applications=[]
        store.listFolders("/${GROUP_ID_APPLICATIONS.split('\\.').join('/')}/").each {def appName->
            def pbaFile=store.resolveArtifact(GROUP_ID_APPLICATIONS,appName,'latest','pba','json')
            if(pbaFile){
                applications << JsonParserFactory.jsonParser.parseMap(pbaFile.text).pba
            }
        }
        applications=applications.sort{it.portal?.order?:it.portal?.title}
        [items:applications, type: 'applications']
    }

    @RequestMapping(value = '/paas/v1/repositories/applications', method = RequestMethod.POST)
    def publishApplication(@RequestBody byte[] body) {
        def pbaInfo=JsonParserFactory.jsonParser.parseMap(new String(body))
        store.createApplication(GROUP_ID_APPLICATIONS,pbaInfo.pba.name,pbaInfo.pba.version,body)
        [url:"$appBaseUrl/paas/v1/repositories/applications/${pbaInfo.pba.name}/${pbaInfo.pba.version}/pba.json".toString()]
    }

    @RequestMapping(value = '/paas/v1/repositories/applications', method = RequestMethod.DELETE)
    def unpublishApplication(@RequestBody byte[] body) {
        def pbaInfo=JsonParserFactory.jsonParser.parseMap(new String(body))
        store.deleteApplication(GROUP_ID_APPLICATIONS,pbaInfo.pba.name,pbaInfo.pba.version)
        [url:"$appBaseUrl/paas/v1/repositories/applications/${pbaInfo.pba.name}/${pbaInfo.pba.version}/pba.json".toString()]
    }

    @RequestMapping(value = '/paas/v1/repositories/applications/{appName}/{appVersion}/pba.json')
    def renderPbaFile(HttpServletResponse response,@PathVariable String appName,@PathVariable String appVersion,@RequestParam(name = 'download',defaultValue = 'false') boolean forceDownload) {
        def pbaFile=store.resolveArtifact(GROUP_ID_APPLICATIONS,appName,appVersion,'pba','json')
        if(pbaFile){
            response.setHeader('Cache-Control', 'no-store')
            if(forceDownload){
                response.setHeader('Content-Description', 'File Transfer')
                response.setHeader('Content-Disposition', "attachment; filename=${appName}-${appVersion}-pba.json")
            }
            new FileSystemResource(pbaFile)
        }else{
            response.sendError(404)
        }
    }
}