define([
    'jscore/ext/net',
    'oss-bss-paas-lib/e/extConstants'
], function (net, extConstants) {
    'use strict';

    function getUrl(options) {
        var serviceUrl = extConstants.get('serviceUrl');

        if (serviceUrl.charAt(serviceUrl.length - 1) !== '/') {
            serviceUrl += '/';
        }

        serviceUrl += 'extension_points';

        if (options.templateId !== undefined) {
            serviceUrl += '/parent=' + options.templateId;
        }
        else if (options.id !== undefined) {
            serviceUrl += '/' + options.id;
        }

        return serviceUrl;
    }

    function getExtension(options) {
        options = options || {};

        return new Promise(function (resolve, reject) {

            var url = getUrl(options),
                itemIdMap = function (tmp) {
                    tmp.id = tmp.URN;
                    return tmp;
                },
                onSuccess = function (data) {
                    if (data.items !== undefined) {
                        resolve(data.items.map(itemIdMap));
                        return;
                    }

                    resolve(convertExtensionFormat(data));
                };

            net.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                success: onSuccess,
                error: function (msg, xhr) {
                    reject(msg);
                }
            });
        });
    }

    function convertExtensionFormat(ext) {
        var extPba = ext.pba,
            data = {
                // keep the data from the original
                origin: ext,
                name: extPba.name,
                description: extPba.description,
                version: extPba.version
            },
            pbaLifecycle = extPba.pba_lifecycle,
            pbaStructure = extPba.structure
            ;

        // map some data for easy usage
        if (pbaLifecycle) {
            data.minInstances = pbaLifecycle.min_instances;
            data.maxInstances = pbaLifecycle.max_instances;
        }

        if (pbaStructure && pbaStructure.instance_configuration) {
            data.cpu = pbaStructure.instance_configuration.cpu;
            data.memoryUnit = pbaStructure.instance_configuration.memory.unit;
            data.memorySize = pbaStructure.instance_configuration.memory.size;
        }

        return data;
    }


    return {
        get: getExtension
    };
});
