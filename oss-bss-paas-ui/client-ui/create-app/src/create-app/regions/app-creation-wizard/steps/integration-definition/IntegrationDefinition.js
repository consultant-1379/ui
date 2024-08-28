/*global define*/
define([
    'layouts/WizardStep',
    'oss-bss-paas-lib/e/extMiscUtils',
    '../../../../widgets/extension-integration-list/ExtensionIntegrationList',
    'uit!./_integrationDefinition.hbs'
], function (WizardStep, extMiscUtils, ExtensionIntegrationList, View) {
    'use strict';

    var formTitleTxt = 'New Integration point offered';

    return WizardStep.extend({

        title: 'Integrations',

        ITEM_ADD: 'item:add',

        view: function () {
            return new View({
                formTitle: formTitleTxt,
                formOptions: {
                    actions: {
                        submit: {
                            label: 'Add'
                        },
                        cancel: true
                    }
                }
            });
        },

        init: function (options) {
            this.options = options;
            this.data = this.options.data;
        },

        onViewReady: function () {
            var form = this.view.findById('form');
            this.list = this.view.findById('list');

            this.list.addEventHandler(ExtensionIntegrationList.prototype.ITEM_ADD, onItemAdd.bind(this));
            this.list.addEventHandler(ExtensionIntegrationList.prototype.ITEM_SHOW_ADD_FORM, onItemShowForm.bind(this));
            this.list.addEventHandler(ExtensionIntegrationList.prototype.ITEM_DELETE, onIntegrationPointDelete.bind(this));

            form.addEventHandler('submit', function (data) {
                if (this.currentForm !== undefined) {

                    if (this.currentForm.extension.integration === undefined) {
                        this.currentForm.extension.integration = [];
                    }

                    this.currentForm.extension.integration.push(data);
                    this.currentForm.resolve(data);
                    resetForm.call(this);
                }
            }.bind(this));

            form.addEventHandler('cancel', function () {
                if (this.currentForm !== undefined) {
                    this.currentForm.reject();
                    resetForm.call(this);
                }
            }.bind(this));

            form.disable();
        },

        onDOMAttach: function () {
            var extensions = this.data.extensions,
                extensionsIds = extensions.map(function (ext) {
                    return ext.extends.id;
                });

            /* jshint laxbreak:true*/
            if (this.currentExtensionsIds === undefined
                || !extMiscUtils.arrayEquals(this.currentExtensionsIds, extensionsIds)) {
                /* jshint laxbreak:false*/

                this.list.setItems(extensions);

                this.currentExtensionsIds = extensionsIds;
            }

            resetForm.call(this);
        },

        isValid: function () {
            return true;
        }
    });


    function onItemAdd(options) {


    }

    function onIntegrationPointDelete(extension, integrationPointToRemove) {
        console.log('deleted', integrationPointToRemove);

        var indexOfIntegP = extension.integration.indexOf(integrationPointToRemove);

        if (indexOfIntegP !== -1) {
            extension.integration.splice(indexOfIntegP, 1);
        }
    }

    function onItemShowForm(options) {
        /* jshint validthis:true*/

        resetForm.call(this);

        this.currentForm = options;
        var form = this.view.findById('form'),
            formTitle = this.view.findById('formTitle');

        formTitle.setText(formTitleTxt + ' by ' + options.extension.name);
        form.enable();
    }

    function resetForm() {
        /* jshint validthis:true*/
        if (this.currentForm !== undefined) {
            this.currentForm.reject();
            delete this.currentForm;
        }
        var form = this.view.findById('form');
        form.reset();
        form.disable();

        this.view.findById('formTitle').setText(formTitleTxt);
    }

});
