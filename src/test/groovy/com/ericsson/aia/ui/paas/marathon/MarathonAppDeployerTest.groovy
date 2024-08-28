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
package com.ericsson.aia.ui.paas.marathon

import com.ericsson.aia.ui.AbstractTest
import org.springframework.http.HttpStatus


//@spock.lang.Ignore
class MarathonAppDeployerTest extends AbstractTest {

    def testAppId = '/test-web-server-latest'
    def testPbaInfo = [
            pba  : [
                    name   : 'test-web-server',
                    service: [[
                                      name          : 'test-service',
                                      container_port: 80,
                                      host_port: 0,
                                      service_port  : 0
                              ]]
            ],
            image: [[
                            image_id     : 'nginx',
                            image_name   : 'test-web-server',
                            image_version: 'latest'
                    ]]
    ]


    def 'make sure old app not exist'() {
        when:
        restDelete('/paas/v1/applications', Map.class, [:], testPbaInfo)
        def entity = restDelete('/paas/v1/applications', Map.class, [:], testPbaInfo)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.apps.size()==0
    }


    def 'deploy real application to docker'() {
        when:
        def entity = restPost('/paas/v1/applications', Map.class, [:], testPbaInfo)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.apps.find { it.value.id == testAppId && it.value.endpoints}
    }

    void 'list app should return running docker containers'() {
        when:
        def entity = restGet('/paas/v1/applications', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.apps.find { it.id == testAppId }
    }


    def 'undeploy real application to docker'() {
        when:
        def entity = restDelete('/paas/v1/applications', Map.class, [:], testPbaInfo)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.apps.size()==1
    }

    void 'list app should not contain deleted containers'() {
        when:
        def entity = restGet('/paas/v1/applications', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.apps.find { it.id == testAppId } == null
    }
}
