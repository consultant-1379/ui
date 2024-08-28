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

        if (options.treeView === true) {
            return serviceUrl + 'integration_points/treeview/';
        }

        return serviceUrl + 'integration_points' + (options.id !== undefined ? '/' + options.id : '/');
    }


    function getIntegration(options) {
        options = options || {};

        return new Promise(function (resolve, reject) {

            var url = getUrl(options),
                itemIdMap = function (tmp) {
                    tmp.id = tmp.URN;
                    return tmp;
                },
                onSuccess = function (data) {
                    if (options.treeView === true) {
                        resolve(convertTreeViewFormat(data));
                    }
                    else if (data.items !== undefined) {
                        resolve(data.items.map(itemIdMap));
                    }
                    else {
                        resolve(data);
                    }
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

    function convertTreeViewFormat(data) {
        //console.log(data);

        var integrationPoints = [],
            getIdNameVersion = function (item) {
                return {
                    id: item.URN,
                    URN: item.URN,
                    name: item.name,
                    version: item.version
                };
            },
            buildItemData = function (app, ext, integ) {
                return {
                    template: getIdNameVersion(app),
                    extension: getIdNameVersion(ext),
                    integration_point: getIdNameVersion(integ)
                };
            };

        data.items.forEach(function (app) {
            var extensionKeys = Object.keys(app.extensions);

            if (extensionKeys.length === 0) {
                // no integration points available on this app
                return;
            }

            extensionKeys.forEach(function (extKey) {
                var extension = app.extensions[extKey],
                    integrationPointsKeys = Object.keys(extension.integration_points || {});

                if (integrationPointsKeys.length === 0) {
                    // no integration points available on this extension
                    return;
                }

                integrationPointsKeys.forEach(function (integPointKey) {
                    var integrationPoint = extension.integration_points[integPointKey];

                    integrationPoint.URN = integPointKey;

                    integrationPoints.push(buildItemData(app, extension, integrationPoint));
                });
            });
        });

        return integrationPoints;
    }


    return {
        get: getIntegration
    };
});
