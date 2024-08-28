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
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@CrossOrigin
@RestController
public class TemplateRepositoryController {

    public static final String GROUP_ID_TEMPLATES = 'com.ericsson.aia.paas.templates'

    @Autowired
    Store store

    @Autowired
    ApplicationGenerator applicationGenerator

    @Value('${app.base.url:http://localhost:8888}')
    String appBaseUrl

    @RequestMapping(value = '/templates/', produces = 'application/json')
    def synchronized listTemplates(HttpServletResponse response) {
        response.setHeader('Cache-Control', 'no-store')
        List templates = []
        store.listFolders("/${GROUP_ID_TEMPLATES.split('\\.').join('/')}/").each { def appName ->
            def templateFolder = store.resolveArtifact(GROUP_ID_TEMPLATES, appName, 'latest', 'template', 'jar')
            def pbaInfo = applicationGenerator.renderTemplatePbaInfo(templateFolder)
            if (pbaInfo) {
                templates << pbaInfo.pba
            }
        }
        templates = templates.sort { it.portal?.order ?: it.portal?.title }
        [items: templates, type: 'templates']
    }

    @RequestMapping(value = '/integration_points/treeview/', produces = 'application/json')
    def integrationPointsTreeview(HttpServletResponse response) {
        listTemplates(response)
    }

    @RequestMapping(value = '/extension_points/parent={templateUrn:.+}', produces = 'application/json')
    def extensionPointList(HttpServletResponse response, @PathVariable String templateUrn) {
        def templates = listTemplates(response)
        def extentionItems = []
        templates.items.each { def template ->
            if (template.URN == templateUrn) {
                template.extensions.each { k, extension ->
                    extentionItems << extension
                }
            }
        }
        [items: extentionItems, type: 'extension_points']
    }

    @RequestMapping(value = '/templates/{templateURN:.+}', produces = 'application/json')
    def template(HttpServletResponse response, @PathVariable String templateURN) {
        def templates = listTemplates(response)
        def pba
        templates.items.each { def template ->
            if (template.URN == templateURN) {
                pba = template
            }
        }
        [pba: pba, image: []]
    }

    @RequestMapping(value = '/extension_points/{extensionURN:.+}', produces = 'application/json')
    def extensionPoint(HttpServletResponse response, @PathVariable String extensionURN) {
        def templates = listTemplates(response)
        def pba
        def templateURN = extensionURN.split(':').take(2).join(':')
        templates.items.each { def template ->
            if (template.URN == templateURN) {
                template.extensions.each { k, extension ->
                    if (extension.URN == extensionURN) {
                        pba = extension
                    }
                }
            }
        }
        [pba: pba, image: []]
    }

    @RequestMapping(value = '/applications/', method = RequestMethod.POST)
    def createApplication(@Valid @RequestBody def pbaInfo) {
        if(pbaInfo.pba.name.contains(' ')){
            pbaInfo.pba.title=pbaInfo.pba.name
            pbaInfo.pba.name=pbaInfo.pba.name.toLowerCase().split(' ').join('-')
        }
        def appPath=null
        pbaInfo.pba.dependencies.each { dependency ->
            if (dependency.type == 'extend') {
                def templateFolder = store.resolveArtifact(GROUP_ID_TEMPLATES, dependency.name,dependency.version, 'template', 'jar')
                appPath = applicationGenerator.createApplicationFromTemplate(pbaInfo,templateFolder)
            }
        }

        String downloadUrl = "$appBaseUrl/paas/v1/applications/download/$appPath"
        [url : downloadUrl,
         html: """
    <a href="$downloadUrl" target="_blank" class="ebBtn ebBtn_color_green">Download</a>
<h3>Minimal Requirements</h3>
To build and run the application, we assume you have following software installed. Please see README.md in project root for instruction to development the application locally.
        <ul>
            <li>
                JAVA 8
            </li>
            <li>
                Maven 3.0.0 with CDS maven <a target="_blank" href="https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/service/local/repositories/releases/content/com/ericsson/maven-settings/2.0.1/maven-settings-2.0.1.xml">settings.xml</a>
            </li>
            <li>
                Docker 1.10.3
            </li>
        </ul>
        <style>

.ebDialogBox-secondaryText{
    height: 200px;
    width: 400px;
}
        </style>
""".toString()]
    }

    @RequestMapping(value = '/paas/v1/applications/download/{timestamp}/{fileName:.+}')
    def downloadApplication(HttpServletResponse response,
                            @PathVariable String timestamp, @PathVariable String fileName) {
        response.setHeader('Cache-Control', 'no-store')
        response.setHeader('Content-Description', 'File Transfer')
        response.setHeader('Content-Disposition', "attachment; filename=${fileName}")
        new FileSystemResource(applicationGenerator.retrieveApplicationFile("$timestamp/$fileName"))
    }
}