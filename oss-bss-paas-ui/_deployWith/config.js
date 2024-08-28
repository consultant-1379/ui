/*global define*/
define({
    defaultApp: 'home',
    name: 'App Builder',
    properties: {
        '*': {
            serviceUrl: window.location.search.indexOf('serviceUrl')>0?window.location.search.substring(window.location.search.indexOf('=')+1):''
            // serviceUrl: 'http://localhost:8888/proxy/appmgr/v1'
        }
    },
    components: [
        {path: 'flyout'}
    ]
});
