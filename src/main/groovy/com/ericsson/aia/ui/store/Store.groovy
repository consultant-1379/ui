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
package com.ericsson.aia.ui.store


public interface Store {
    List<String> listFolders(String path)
    List<String> listVersions(String groupId,String artifactId)
    File resolveArtifact(String groupId,String artifactId, String version, String classifier, String extension)
    void createApplication(String groupId,String artifactId, String version, byte[] pbaFile)
    void deleteApplication(String groupId,String artifactId, String version)
}