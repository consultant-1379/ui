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

        return serviceUrl + 'templates/' + (options.id !== undefined ? options.id : '');
    }

    function getTemplate(options) {
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

                    resolve(data);
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


    return {
        get: getTemplate
    };
});
