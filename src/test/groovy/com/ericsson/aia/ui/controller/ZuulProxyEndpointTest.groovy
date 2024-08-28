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
package com.ericsson.aia.ui.controller

import com.ericsson.aia.ui.AbstractTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import spock.lang.Ignore

@Ignore
class ZuulProxyEndpointTest extends AbstractTest {

    void 'proxy endpoints should exist'(def path, def expectedType) {
        when:
        def entity = restGet(path, Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.type == expectedType

        where:
        path                             | expectedType
        '/proxy/appmgr/v0/templates/'    | 'templates'
        '/proxy/appmgr/v0/applications/' | 'applications'
    }

    void 'proxy endpoints should not contain duplicated headers'() {
        when:
        def entity = restGet('/proxy/appmgr/v0/templates/', Map.class, [Origin: 'http://localhost:8888'])

        then:
        entity.statusCode == HttpStatus.OK
        entity.headers.keySet().each { key ->
            assert entity.headers[key].size() == 1
        }
    }

    void 'proxy endpoints should support cross domain headers'() {
        when:
        def entity = restCall('/proxy/appmgr/v1/applications/', String.class, HttpMethod.OPTIONS,
                [
                        Origin                          : 'http://foo.bar',
                        'Access-Control-Request-Method' : 'POST',
                        'Access-Control-Request-Headers': 'accept, content-type']
        )

        then:
        entity.statusCode == HttpStatus.OK
        entity.headers['Access-Control-Allow-Origin'].first() == 'http://foo.bar'
        entity.headers['Access-Control-Request-Method'].first() == 'POST'
        entity.headers['Access-Control-Allow-Headers'].first() == 'accept, content-type'
    }
}