/*global define*/
define([
    'jscore/core',
    'container/api',
    'widgets/Dialog',
    'oss-bss-paas-lib/s/ApplicationService',
    './steps/template-selection/TemplateSelection',
    './steps/application-details/AppDetails',
    './steps/extension-definition/ExtensionDefinition',
    './steps/integration-definition/IntegrationDefinition',
    './steps/dependencies-definition/DependenciesDefinition',
    './steps/summary/Summary',
    'uit!./_appCreationWizard.hbs'
], function (core, containerAPI, Dialog,
             applicationService,
             // steps
             TemplateSelection, AppDetails, ExtensionDefinition, IntegrationDefinition, DependenciesDefinition, Summary,
             //
             View) {
    'use strict';

    return core.Region.extend({

        init: function (options) {
            this.options = options || {};
            this.formData = {};

            this.wizardSteps = {
                appDetails: new AppDetails({data: this.formData}),
                template: new TemplateSelection({data: this.formData}),
                extension: new ExtensionDefinition({data: this.formData}),
                integration: new IntegrationDefinition({data: this.formData}),
                dependencies: new DependenciesDefinition({data: this.formData}),
                summary: new Summary({data: this.formData})
            };


            // TODO remove mock data
            //this.formData.application = {"name": "New App ", "version": "1.0.0", "description": "Description of new App"};
            //
            //this.formData.extensions = [{
            //    "extends": {
            //        "id": "urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b",
            //        "name": "ep-ui-extension",
            //        "description": "EPS implementation pattern UI extension",
            //        "minInstances": 2,
            //        "maxInstances": 5,
            //        "cpu": 3,
            //        "memorySize": 1,
            //        "memoryUnit": "Gb"
            //    },
            //    "name": "Extended ep-ui-extension name",
            //    "version": "1.0.0",
            //    "integration": [
            //        {"name": "ep-ui IP A", "version": "1.0.0", "description": "ep-ui IP A desc"},
            //        {"name": "ep-ui IP B", "version": "1.0.0", "description": "ep-ui IP B desc"},
            //        {"name": "ep-ui IP C", "version": "1.0.0", "description": "ep-ui IP C desc"}
            //    ]
            //}, {
            //    "extends": {
            //        "id": "urn:uuid:9f539160-7e47-11e5-8e94-0002a5d5c51b",
            //        "name": "ep-service-extension",
            //        "description": "EPS implementation pattern Service extension",
            //        "minInstances": 1,
            //        "maxInstances": 5,
            //        "cpu": 2,
            //        "memorySize": 4,
            //        "memoryUnit": "Gb"
            //    },
            //    "name": "Extended ep-service-extension name",
            //    "version": "1.0.0",
            //    "integration": [
            //        {"name": "ep-service IP A", "version": "1.0.0", "description": "ep-service IP A desc"}
            //    ]
            //}];
            //
            //this.formData.template = {
            //    "URN": "urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b",
            //    "description": "Event Processing Implementation pattern",
            //    "documentation": "---",
            //    "name": "ep-pattern",
            //    "path": "/sdkhome/templates/EP-1.0.1/39d97ac0-7e47-11e5-a432-0002a5d5c51b.pba",
            //    "type": "---",
            //    "version": "1.0.1",
            //    "id": "urn:uuid:39d97ac0-7e47-11e5-a432-0002a5d5c51b"
            //};

        },

        view: function () {
            return new View({
                wizardOptions: {
                    steps: [
                        this.wizardSteps.appDetails,
                        this.wizardSteps.template,
                        this.wizardSteps.extension,
                        this.wizardSteps.integration,
                        this.wizardSteps.dependencies
                        // this.wizardSteps.summary
                    ]
                }
            });
        },

        onStart: function () {
            var
                templateStep = this.wizardSteps.template,
                extensionStep = this.wizardSteps.extension,
                dependencyStep = this.wizardSteps.dependencies,
                wizard = this.view.findById('wizard');

            wizard.addEventHandler('stepchange', onStepChange.bind(this));
            wizard.addEventHandler('finish', onWizardFinish.bind(this));
            wizard.addEventHandler('cancel', onWizardCancel.bind(this));

            templateStep.addEventHandler(templateStep.PANEL_SHOW, showRightPanel.bind(this));
            templateStep.addEventHandler(templateStep.PANEL_HIDE, hideRightPanel.bind(this));

            extensionStep.addEventHandler(extensionStep.PANEL_SHOW, showRightPanel.bind(this));
            extensionStep.addEventHandler(extensionStep.PANEL_HIDE, hideRightPanel.bind(this));

            dependencyStep.addEventHandler(extensionStep.PANEL_SHOW, showRightPanel.bind(this));
        }
    });

    //--------------------------------------------------------------------

    function onWizardFinish() {
        /* jshint validthis:true*/
        if(!this.formData.application.version){
            this.formData.application.version=this.formData.template.version;
        }
        if(!this.formData.application.description){
            this.formData.application.description=this.formData.template.description;
        }
        console.log(this.formData);
        applicationService.post(this.formData)
            .then(function (options) {
                console.log(options);
                var dialogWidget = new Dialog({
                    header: 'Application created',
                    content: core.Element.parse('<span>' + options.html+ '</span>'),
                    visible: true,
                    buttons: [{
                        caption: 'Ok',
                        color: 'darkBlue',
                        action: function () {
                            dialogWidget.destroy();
                        }
                    }]
                });
            }, function (msg) {
                var dialogWidget = new Dialog({
                    header: 'Error',
                    content: 'Message: ' + msg,
                    visible: true,
                    buttons: [{
                        caption: 'Ok',
                        color: 'darkBlue',
                        action: function () {
                            dialogWidget.destroy();
                        }
                    }]
                });
                console.log(msg);
            });
    }

    function onStepChange() {
        /* jshint validthis:true*/
        hideRightPanel.call(this);
    }

    function onWizardCancel() {
        /* jshint validthis:true*/
        this.getEventBus().publish('create:cancel');
    }

    //--------------------------------------------------------------------

    function showRightPanel(options) {
        /* jshint validthis:true*/
        containerAPI.getEventBus().publish('flyout:show', {
            header: options.header,
            content: options.content
        });
    }

    function hideRightPanel() {
        /* jshint validthis:true*/
        this.getEventBus().publish('flyout:hide');
    }

    //--------------------------------------------------------------------
});
