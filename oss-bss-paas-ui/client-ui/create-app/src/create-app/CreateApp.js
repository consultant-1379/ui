define([
    'jscore/core',
    'oss-bss-paas-lib/e/extConstants',
    'layouts/MultiSlidingPanels',
    './regions/app-creation-wizard/AppCreationWizard',
    'uit!./_createApp.hbs',
    'i18n!create-app/dictionary.json'
], function (core, extConstants, MultiSlidingPanels, AppCreationWizard, View, dictionary) {

    return core.App.extend({

        view: function () {
            var context = this.getContext();

            return new View({
                topSectionOptions: {
                    context: context,
                    title: dictionary.appTitle,
                    breadcrumb: this.options.breadcrumb
                },
                multiSlidingPanelsOptions: {
                    context: context
                },
                panels: {
                    right: {label: dictionary.info}
                },
                regionOptions: {
                    context: context
                }
            });
        },

        init: function (options) {
            this.options = options;
            this.options.breadcrumb[0]={name:'Portal',url:'/#aia'};
            extConstants.set('serviceUrl', options.properties.serviceUrl);
        },

        onStart: function () {
            if(window.navigator.userAgent.indexOf("MSIE ")>0){
                alert('IE is not supported!! Please use Chrome or Firefox!');
                window.location.href='/';
            }
            this.wizardRegion = this.view.findById('wizard');
            this.getEventBus().subscribe('create:cancel', onCancel.bind(this));
        },
        onBeforeLeave: function (e) {
            // TODO restore onBeforeLeave for prod
            return 'The form has not been completed.';
        }
    });

    function onCancel() {
        this.wizardRegion.stop();
        this.view.findById('multiSlidingPanels').destroy();
        this.wizardRegion = new AppCreationWizard({
            context: this.getContext()
        });

        this.view.findById('topSection').setContent(new MultiSlidingPanels({
            context: this.getContext(),
            main: {content: this.wizardRegion}
        }));

        window.location.hash = 'home';
    }
});
