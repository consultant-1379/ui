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
package com.ericsson.aia.ui.site

import com.ericsson.aia.ui.AbstractTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

class SiteControllerTest extends AbstractTest {

    void 'render schema-registry-client latest site'() {
        when:
        def entity = restGet('/site/com.ericsson.aia.model/schema-registry-client/latest/', String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.contains('Schema Registry')
    }

    void 'access site should populate maven cache'() {
        when:
        def entity = restGet('/paas/v1/maven/cache', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.values().find{!it.isEmpty()}!=null
    }

    void 'maven cache should be empty after reset'() {
        when:
        restGet('/paas/v1/maven/cache?reset=true', Map.class)
        def entity = restGet('/paas/v1/maven/cache', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.values().find{!it.isEmpty()}==null
    }

}
