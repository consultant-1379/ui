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

import com.ericsson.aia.ui.AbstractTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClientException
import spock.lang.Shared

class TemplateRepositoryControllerTest extends AbstractTest {

    def testAppName = 'test-app'
    def testAppVersion = '0.1.0'
    def testAppPba = [
            pba: [
                    name        : testAppName,
                    version     : testAppVersion,
                    description : 'testing description',
                    dependencies: [[
                                           type   : 'extend',
                                           name   : 'aia-development-work-flow-demo',
                                           version: '0.10.0'
                                   ]]
            ]
    ]

    @Shared
    String downloadUrl

    def 'create application from tempalte'() {
        when:
        def entity = restPost("/applications/", Map.class, [:], testAppPba)
        downloadUrl = entity.body?.url

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.url.endsWith('/test-app.zip')
    }


    def 'download created app'() {
        when:
        restGet(downloadUrl.substring(downloadUrl.indexOf('/', 10)), Map.class)

        then:
        thrown RestClientException
    }

    void 'list template should contain aia-development-work-flow-demo template'() {
        when:
        def entity = restGet('/integration_points/treeview/', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.items.find { it.name == 'aia-development-work-flow-demo' && it.version == '0.10.0' }
    }

    void 'get existing template'() {
        when:
        def entity = restGet('/templates/aia-asr:0.1.0', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.pba.name == 'aia-asr'
        entity.body.pba.version == '0.1.0'
    }

    void 'list template extension points'() {
        when:
        def entity = restGet('/extension_points/parent=aia-asr:0.1.0', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.items.find { it.name == 'asr-base-service-extension-point' && it.version == '1.0.0' }
    }

    void 'get extension point'() {
        when:
        def entity = restGet('/extension_points/aia-asr:0.1.0:asr-base-service-extension-point:1.0.0', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.pba.name == 'asr-base-service-extension-point'
        entity.body.pba.version == '1.0.0'
    }
}