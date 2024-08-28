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
import spock.lang.Specification

class ApplicationRepositoryControllerTest extends AbstractTest {

    def testAppName = 'test-app'
    def testAppVersion = '0.1.0'
    String testAppPbaFile = """
{
  "pba": {
    "name": "$testAppName",
    "version": "$testAppVersion"
  }
}
"""

    def 'make sure test app not in repo'() {
        when:
        restDelete("/paas/v1/repositories/applications/$testAppName/$testAppVersion", Map.class)
        def entity = restDelete("/paas/v1/repositories/applications/$testAppName/$testAppVersion", Map.class)

        then:
        entity.statusCode == HttpStatus.OK
    }


    def 'publish application pba file'() {
        when:
        def entity = restPost("/paas/v1/repositories/applications/$testAppName/$testAppVersion/files/pba.json", Map.class, [:], testAppPbaFile)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.url == 'http://localhost:8888/test-app/0.1.0/files/pba.json'
    }

    void 'list app should return just published app'() {
        when:
        def entity = restGet('/paas/v1/repositories/applications', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.items.find { it.name == testAppName }
    }

    void 'get app pba should has version'() {
        when:
        def entity = restGet("/paas/v1/repositories/applications/$testAppName/$testAppVersion/files/pba.json?forceDownload=true", Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.pba.name == testAppName
        entity.body.pba.version == testAppVersion
    }


    def 'unpublish real application'() {
        when:
        def entity = restDelete("/paas/v1/repositories/applications/$testAppName/$testAppVersion", Map.class)

        then:
        entity.statusCode == HttpStatus.OK
    }

    void 'list app should not contain deleted application'() {
        when:
        def entity = restGet('/paas/v1/repositories/applications', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.items.find { it.name == testAppName } == null
    }

    void 'list app should contain aia-development-work-flow-demo application only latest version'() {
        when:
        def entity = restGet('/paas/v1/repositories/applications', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.items.find { it.name == 'aia-development-work-flow-demo' && it.version >= '0.10.0' }
        entity.body.items.find { it.name == 'aia-development-work-flow-demo' && it.version=='0.5.0' } == null
    }
}