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
package com.ericsson.aia.ui.store.maven

import com.ericsson.aia.ui.utils.VersionUtils
import com.ericsson.aia.ui.store.Store
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.apache.commons.compress.archivers.ArchiveInputStream
import org.apache.commons.compress.archivers.ArchiveStreamFactory
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.PostConstruct
import java.util.concurrent.TimeUnit

@RestController
public class MavenStore implements Store {

    @Value('${web.maven.repo.url:https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/content/repositories/aia-releases}')
    String mavenRepoUrl

    @Value('${web.maven.repo.cache.in.minutes:60}')
    int mavenRepoCacheInMinutes

    @Value('${paas.store.maven.dir:target/maven-store/}')
    String storeLocalDir

    LoadingCache<String, String> latestVersionCache
    LoadingCache<String, List<String>> foldersCache

    @PostConstruct
    void initCache() {
        latestVersionCache = createCache(this.&resolveLatestVersion)
        foldersCache = createCache(this.&resolveLatestFolders)
    }


    @RequestMapping('/paas/v1/maven/cache')
    def mavenCacheController(@RequestParam(name = 'reset',defaultValue = 'false') boolean resetCache) {
        if(resetCache){
            foldersCache.invalidateAll()
            latestVersionCache.invalidateAll()
        }
        [
                latestVersionCache:latestVersionCache.asMap(),
                foldersCache:foldersCache.asMap()
        ]
    }


    List<String> listFolders(String path) {
        foldersCache.get("$mavenRepoUrl$path")
    }

    List<String> listVersions(String groupId,String artifactId) {
        latestVersionCache.get("$mavenRepoUrl/${groupId.split('\\.').join('/')}/$artifactId/")
    }


    void createApplication(String groupId,String artifactId, String version, byte[] pbaFile){
        def artifactFile = resolveArtifactFile(groupId, artifactId, version,'pba','json')
        artifactFile.parentFile.mkdirs()
        artifactFile.delete()
        artifactFile << pbaFile
    }

    void deleteApplication(String groupId,String artifactId, String version){
        def artifactFile = resolveArtifactFile(groupId, artifactId, version,'pba','json')
        artifactFile.delete()
    }

    File resolveArtifactFile(String groupId, String artifactId, String version, String classifier, String extension) {
        new File(storeLocalDir, "${groupId.split('\\.').join('/')}/$artifactId/$version/$artifactId-$version-${classifier}.$extension")
    }

    File resolveArtifact(String groupId,String artifactId, String version, String classifier, String extension) {
        def artifactFile = resolveArtifactFile(groupId, artifactId, version,classifier,extension)
        if (!artifactFile.exists()) {
            def artifactBaseUrl = "$mavenRepoUrl/${groupId.split('\\.').join('/')}/$artifactId/"
            if (version == 'latest') {
                version = latestVersionCache.get(artifactBaseUrl)
                artifactFile = new File(storeLocalDir, "${groupId.split('\\.').join('/')}/$artifactId/$version/$artifactId-$version-${classifier}.$extension")
                if(new File("$artifactFile.absolutePath-HIDE").exists()){
                    return
                }
            }
            if(!artifactFile.exists()){
                def artifactUrl = "$artifactBaseUrl/$version/$artifactId-$version-${classifier}.$extension"
                downloadArtifact(artifactFile, artifactUrl, extension, classifier)
            }
        }
        if (artifactFile.exists()) {
            if (extension == 'jar') {
                new File(artifactFile.parentFile, classifier)
            } else {
                artifactFile
            }
        }
    }


    def downloadArtifact(File artifactFile, artifactUrl, String extension, String classifier) {
        try {
            artifactFile.parentFile.mkdirs()
            artifactFile.delete()
            artifactFile.withOutputStream { out ->
                out << new URL(artifactUrl).openStream()
            }

            if (extension == 'jar') {
                unpackJarFile(artifactFile, classifier)
            }
        } catch (FileNotFoundException ex) {
            FileUtils.deleteDirectory(artifactFile.parentFile)
        }
    }

    synchronized private void unpackJarFile(File artifactFile, String classifier) {
        def expandedJarFolder = new File(artifactFile.parentFile, classifier)
        new FileInputStream(artifactFile).withStream { is ->
            ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("zip", is)
            ZipArchiveEntry entry = null
            while ((entry = (ZipArchiveEntry) ais.getNextEntry()) != null) {
                File outFile = new File(expandedJarFolder, entry.getName())
                if (entry.getName().endsWith('/')) {
                    outFile.mkdirs();
                    continue
                }
                outFile.delete()
                outFile << ais
            }
        }
        FileUtils.deleteQuietly(new File(expandedJarFolder, 'META-INF'))
    }

    def createCache(def populateCacheAction) {
        CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(mavenRepoCacheInMinutes, TimeUnit.MINUTES)
                .build(
                new CacheLoader<Object, Object>() {
                    public Object load(Object path) {
                        populateCacheAction(path)
                    }
                }
        )
    }

    def resolveLatestVersion(String url) {
        def versions = resolveLatestFolders(url)
        versions?versions[-1]:'NO_VERSION_PUBLISHED_YET'
    }

    def resolveLatestFolders(String url) {
        def folders=[]
        try{
            def indexHtml = new URL(url).text
            indexHtml.findAll(/>[^<]*\/</).each {
                folders << it.substring(1, it.length() - 2)
            }
        }catch (Exception ignore){
            ignore.printStackTrace()
            println "WARN: failed to download $url : $ignore.message"
        }

        new File(storeLocalDir,url.substring(mavenRepoUrl.length())).list().each {String folderName->
            if(!folders.contains(folderName)){
                folders << folderName
            }
        }
        VersionUtils.sortVersions(folders)
    }

}