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

import groovy.transform.CompileStatic
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@CompileStatic
//@FeignClient(url = '${marathon.url:http://atrcxb2560-1.athtem.eei.ericsson.se:7070/v2}')
@FeignClient(url = '${marathon.url:http://atrcxb2560-1.athtem.eei.ericsson.se:18080/v2}')
//@FeignClient(url = '${marathon.url:http://atrcxb2994.athtem.eei.ericsson.se:18080/v2}')
//@FeignClient(url = '${marathon.url:http://7d5b93b1.ngrok.io/v2}')
interface MarathonRestApi{

    @RequestMapping(method = RequestMethod.GET, value = "/apps")
    def list()

    @RequestMapping(method = RequestMethod.POST, value = "/apps", consumes = 'application/json')
    def deploy(def pba)

    @RequestMapping(method = RequestMethod.GET, value = "/apps{appId}")
    def show(@PathVariable('appId') String appId)

    @RequestMapping(method = RequestMethod.DELETE, value = "/apps{appId}")
    def undeploy(@PathVariable('appId') String appId)
}
