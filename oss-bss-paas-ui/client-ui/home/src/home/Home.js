define([
    'jscore/core',
    'uit!./_home.hbs'
], function (core, View) {

    var childColorIconMap = {
        'create-app': {
            name: 'Create',
            bgColor: 'green',
            icon: 'eaHome-icon_create',
            description: 'Create applications based on Implementation Patterns. Extend existing applications and Integrate with offered services.'
        },
        'explore-app': {
            name: 'Explore',
            bgColor: 'blue',
            icon: 'eaHome-icon_explore',
            description:'Explore the Application catalogue. Get the information about features provided by each application.'
        },
        'manage-app': {
            name: 'Manage',
            bgColor: 'red',
            icon: 'eaHome-icon_manage',
            description:'Manage the applications deployed on the PaaS environment. Control the behavior and check the status.'
        }
    };

    return core.App.extend({

        view: function () {
            var breadcrumb = this.options.breadcrumb;

            return new View({
                navOptions: {
                    links: breadcrumb[breadcrumb.length - 1].children.map(function (app) {
                        var url = app.url,
                            pkgName = url.substring(url.indexOf('/') + 1);

                        childColorIconMap[pkgName].url = url;
                        return childColorIconMap[pkgName];
                    })
                }
            });
        }
    });
});
