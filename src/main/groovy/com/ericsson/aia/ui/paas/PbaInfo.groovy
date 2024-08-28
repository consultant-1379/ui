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

import groovy.transform.CompileStatic

import javax.validation.constraints.NotNull


@CompileStatic
class PbaInfo {
    @NotNull
    Pba pba
    List<Image> image

    static class Pba {
        String id
        String name
        String version
        String description
        List<Service> service
        List dependencies
    }

    static class Image {
        String image_id
        String image_name
        String image_version
        String image_type
    }

    static class Service {
        String name
        Integer container_port
        Integer service_port
    }
}
