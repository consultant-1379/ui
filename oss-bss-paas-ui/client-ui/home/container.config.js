define({
    defaultApp: 'home',
    name: 'App Builder',
    properties: {
        '*': {
            // to start the server, run : mvn spring-boot:run
            serviceUrl: 'http://localhost:8888'
        }
    },
    components: [
        {path: 'flyout'}
    ]
});
