define([
    'jscore/ext/net',
    'oss-bss-paas-lib/e/extConstants'
], function (net, extConstants) {
    'use strict';

    function getUrl() {
        var serviceUrl = extConstants.get('serviceUrl');

        if (serviceUrl.charAt(serviceUrl.length - 1) !== '/') {
            serviceUrl += '/';
        }

        return serviceUrl + 'applications/';
    }

    function postNewApplication(options) {
        options = options || {};

        return new Promise(function (resolve, reject) {

            var url = getUrl(options),
                onSuccess = function (data) {
                    resolve(data);
                },
                data = buildApplicationRequestData(options);

            net.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json',
                success: onSuccess,
                error: function (msg, xhr) {
                    reject(msg);
                }
            });
        });
    }

    function buildApplicationRequestData(options) {
        return {
            pba: buildPba(options),
            image: buildImageExtensions(options)
        };
    }

    function buildPba(options) {
        var app = options.application;
        return {
            name: app.name,
            version: app.version,
            description: app.description,
            platform: [{
                type: 'marathon',
                version: 'latest'
            }],
            structure: {
                instance_connectivity: []
            },
            pba_policies: [],
            dependencies: [{
                type: 'extend',
                name: options.template.name,
                version: options.template.version,
                reference: options.template.id,
                qualifier: ''
            }]
        };
    }

    function buildImageExtensions(options) {
        return options.extensions.map(function (ext) {
            var extensionDef= {
                name: ext.name,
                version: ext.version,
                description: ext.description || ext.extends.description,
                platform: [{
                    type: 'marathon',
                    version: 'latest'
                }],
                envars: [],
                structure: {
                    instance_configuration: {
                        cpu: ext.cpu,
                        memory: {
                            size: ext.memorySize,
                            unit: ext.memoryUnit
                        }
                    }
                },
                pba_policies: [],
                pba_lifecycle: {
                    min_instances: ext.minInstances,
                    max_instances: ext.maxInstances,
                    scale_triggers: [{
                        kpi: 'response_time',
                        value: 60
                    }, {
                        kpi: 'cpu',
                        value: '90'
                    }]
                },
                dependencies: [{
                    type: 'parent',
                    reference: 'self'
                }, {
                    type: 'extend',
                    reference: ext.extends.id,
                    name: ext.extends.name,
                    version: ext.extends.version
                }],
                service: buildOfferedIntegrationPoints(ext.integration || [])
            };

            if(ext.dependencies){
                ext.dependencies.forEach(function (dep) {
                    dep.integration_point.type="application";
                    extensionDef.dependencies.push(dep.integration_point);
                });
            }

            return extensionDef;
        });
    }

    function buildOfferedIntegrationPoints(arr) {
        return arr.map(function (integ) {
            return {
                name: integ.name,
                version: integ.version,
                description: integ.description,
                service_port: integ.port,
                transport: 'sync',
                paas_registration: true,
                'api-imp': ''
            };
        });
    }


    return {
        post: postNewApplication
    };
});
