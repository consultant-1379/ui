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
package com.ericsson.aia.ui.cds

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class CdsV3Controller {

    @Value('${web.cdsv3.url}')
    String cdsV3Url


    @RequestMapping(value = '/cdsv3/portal-lib/1/services/aia-data.js', produces = 'application/javascript')
    def modifyAidData(HttpServletRequest request, HttpServletResponse response) {
        modifyTextFile(request,response){ String text->
            text.replaceAll('http://atrcxb2994.athtem.eei.ericsson.se:8878','')
        }
    }

    @RequestMapping(value = '/cdsv3/aia-ecosystem/AiaEcosystem.js', produces = 'application/javascript')
    def modifyAiaEcosystem(HttpServletRequest request, HttpServletResponse response) {
        modifyTextFile(request,response){ String text->
            text.replaceAll('http://atrcxb2994.athtem.eei.ericsson.se:8878','')
        }
    }

    @RequestMapping(value = '/cdsv3/api/projects/1/components', produces = 'application/json')
    def modifyAiaProjectComponents(HttpServletRequest request, HttpServletResponse response) {
        modifyTextFile(request,response){ String text->
            text.replaceAll('http://atrcxb2560-1.athtem.eei.ericsson.se:8888','')
        }
    }

    def modifyTextFile(HttpServletRequest request, HttpServletResponse response, def modify) {
        response.setHeader('Cache-Control', 'no-store')
        def requestUrl="$cdsV3Url${request.requestURI.substring(request.requestURI.indexOf('/',2))}"
        def resultText=new URL(requestUrl).text
        modify(resultText)
    }
}