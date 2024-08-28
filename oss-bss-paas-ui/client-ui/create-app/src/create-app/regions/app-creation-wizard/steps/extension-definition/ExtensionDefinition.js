/*global define*/
define([
    'jscore/core',
    'layouts/WizardStep',
    'oss-bss-paas-lib/s/ExtensionService',
    'oss-bss-paas-lib/w/ExtensionInfo',
    'oss-bss-paas-lib/e/extMiscUtils',
    '../../../../widgets/extension-definition-list/ExtensionDefinitionList',
    '../../../../widgets/extension-settings-form/ExtensionSettingsForm',
    'uit!./_extensionDefinition.hbs'
], function (core, WizardStep, extensionService, ExtensionInfo, extMiscUtils, ExtensionDefinitionList, ExtensionSettingsForm, View) {
    'use strict';

    var formTitleTxt = 'Extension config';

    return WizardStep.extend({

        title: 'Extensions',

        ITEM_DUPLICATE: 'item:duplicate',
        ITEM_INFO: 'item:info',
        ITEM_SETTINGS: 'item:settings',
        ITEM_UPDATE: 'item:update',

        PANEL_SHOW: 'wizard-step:show-panel',
        PANEL_HIDE: 'wizard-step:hide-panel',

        view: function () {
            return new View({
                formTitle: formTitleTxt,
                formOptions: {
                    actions: {
                        submit: {
                            label: 'Save'
                        },
                        cancel: true
                    }
                }
            });
        },

        init: function (options) {
            this.options = options;
            this.data = this.options.data;
            this.data.extensions = [];
            this.listEventBus = new core.EventBus();
        },

        onViewReady: function () {
            this.list = this.view.findById('list');
            this.listEventBus.subscribe(this.ITEM_SETTINGS, onItemSettings.bind(this));
            this.listEventBus.subscribe(this.ITEM_UPDATE, onExtensionUpdated.bind(this));
            this.listEventBus.subscribe(this.ITEM_DUPLICATE, onItemDuplicate.bind(this));
            this.listEventBus.subscribe(this.ITEM_INFO, onItemInfo.bind(this));

            var form = this.view.findById('form');

            form.addEventHandler(ExtensionSettingsForm.prototype.events.SUBMIT, function (data) {
                if (this.currentForm !== undefined) {
                    // merge override the extension object to apply the form changes
                    extMiscUtils.mergeObjects(true, this.currentForm.data, data);
                    this.currentForm.resolve();
                    delete this.currentForm;
                    resetForm.call(this);
                }
            }.bind(this));

            form.addEventHandler(ExtensionSettingsForm.prototype.events.CANCEL, function () {
                if (this.currentForm !== undefined) {
                    this.currentForm.reject();
                    delete this.currentForm;
                    resetForm.call(this);
                }
            }.bind(this));

            form.disable();
        },

        onDOMAttach: function () {
            // when the template selected is different from the last selection, refresh the list
            if (this.currentTemplateId !== this.data.template.id) {
                this.currentTemplateId = this.data.template.id;
                this.setExtensions([]);

                extensionService.get({
                    templateId: this.currentTemplateId
                }).then(this.setExtensions.bind(this));
            }
        },

        setExtensions: function (ext) {
            var evtBus = this.listEventBus;
            this.data.extensions = [];
            this.invalidExtensions = [];

            this.list.setItems(ext.map(function (item) {
                return {
                    listBus: evtBus,
                    data: {
                        id: item.id,
                        name: item.name,
                        version: item.version,
                        description: item.description
                    }
                };
            }));

            this.revalidate();
        },

        isValid: function () {
            return true; // hack by guo and suggested by marc :)
            // return this.data.extensions.length !== 0 && this.invalidExtensions.length === 0;
        }
    });

    function onItemDuplicate(options) {
        /* jshint validthis:true*/
        var clonedData = {},
            evtBus = this.listEventBus;

        extMiscUtils.mergeObjects(true, clonedData, options.data);

        this.list.addItem({
            listBus: evtBus,
            data: clonedData
        });
    }


    function onItemInfo(options) {
        /* jshint validthis:true*/
        if (this.extensionInfoPanel === undefined) {
            this.extensionInfoPanel = new ExtensionInfo();
        }

        extensionService.get(options).then(function (template) {
            this.extensionInfoPanel.setContent(template);
        }.bind(this));

        this.trigger(this.PANEL_SHOW, {
            header: 'Info',
            content: this.extensionInfoPanel
        });
    }

    function onItemSettings(options) {
        /* jshint validthis:true*/

        resetForm.call(this);

        this.currentForm = options;
        var form = this.view.findById('form'),
            formTitle = this.view.findById('formTitle');

        formTitle.setText(formTitleTxt + ' for ' + options.data.name);

        extensionService.get({
            id: options.data.extends.id
        }).then(function (data) {
            // preserve the extension object as the form is initialized
            extMiscUtils.mergeObjects(true, data, options.data);
            form.setValues(data);
            form.enable();
        });

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

    function onExtensionUpdated(options) {
        /* jshint validthis:true*/
        var itemData = options.data,
            validExtensionsList = this.data.extensions,
            invalidExtensionsList = this.invalidExtensions,
            indexOfExtInValid = this.data.extensions.indexOf(itemData),
            indexOfExtInInvalid = this.invalidExtensions.indexOf(itemData)
            ;

        if (!options.selected) {
            // take the item off both list when not selected
            if (indexOfExtInValid !== -1) {
                validExtensionsList.splice(indexOfExtInValid, 1);
            }
            if (indexOfExtInInvalid !== -1) {
                invalidExtensionsList.splice(indexOfExtInInvalid, 1);
            }
        }
        else {
            if (options.valid) {
                // add it to the validated extensions
                // remove it from invalids
                if (indexOfExtInValid === -1) {
                    validExtensionsList.push(itemData);
                }
                if (indexOfExtInInvalid !== -1) {
                    invalidExtensionsList.splice(indexOfExtInInvalid, 1);
                }
            }
            else {
                // add it to the invalid
                // remove it from valid
                if (indexOfExtInValid !== -1) {
                    validExtensionsList.splice(indexOfExtInValid, 1);
                }
                if (indexOfExtInInvalid === -1) {
                    invalidExtensionsList.push(itemData);
                }
            }
        }

        this.revalidate();
    }


});
