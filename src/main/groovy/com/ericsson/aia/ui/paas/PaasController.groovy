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
package com.ericsson.aia.ui.paas

import com.ericsson.aia.ui.paas.AppDeployer
import com.ericsson.aia.ui.paas.PbaInfo
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@CompileStatic
@RestController
public class PaasController {

    @Autowired
    AppDeployer appDeployer

    @RequestMapping('/paas/v1/applications')
    def listRunningApps(){
        appDeployer.list()
    }

    @RequestMapping(value = '/paas/v1/applications', method = RequestMethod.POST)
    def deploy(@Valid @RequestBody def pbaInfo){
        appDeployer.deploy(pbaInfo)
    }

    @RequestMapping(value = '/paas/v1/applications', method = RequestMethod.DELETE)
    def undeploy(@Valid @RequestBody def pbaInfo){
        appDeployer.undeploy(pbaInfo)
    }
}