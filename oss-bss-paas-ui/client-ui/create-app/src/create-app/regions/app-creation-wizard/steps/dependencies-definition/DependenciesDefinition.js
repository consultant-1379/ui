/*global define*/
define([
    'layouts/WizardStep',
    'oss-bss-paas-lib/s/IntegrationService',
    'oss-bss-paas-lib/w/IntegrationInfo',
    'oss-bss-paas-lib/e/extMiscUtils',
    '../../../../widgets/extension-integration-list/ExtensionIntegrationList',
    '../../../../widgets/integration-table/IntegrationTable',
    'uit!./_dependenciesDefinition.hbs'
], function (WizardStep, integrationService, IntegrationInfo, extMiscUtils, ExtensionIntegrationList, IntegrationTable, View) {
    'use strict';

    var formTitleTxt = 'Select the dependency(ies)';

    return WizardStep.extend({

        title: 'Dependencies',

        PANEL_SHOW: 'wizard-step:show-panel',
        PANEL_HIDE: 'wizard-step:hide-panel',

        ITEM_ADD: 'item:add',

        view: function () {
            return new View({
                formTitle: formTitleTxt
            });
        },

        init: function (options) {
            this.options = options;
            this.data = this.options.data;
        },

        onViewReady: function () {
            this.list = this.view.findById('list');
            this.selectTable = this.view.findById('selectTable');
            this.dependencySection = this.view.findById('dependencySection');
            var submitBtn = this.view.findById('submit'),
                cancelBtn = this.view.findById('cancel');

            hideDependencySection.call(this, true);

            integrationService.get({
                treeView: true
            })
                .then(this.selectTable.setAvailableItems.bind(this.selectTable));

            this.list.addEventHandler(ExtensionIntegrationList.prototype.ITEM_SHOW_ADD_FORM, onItemShowDependencySelect.bind(this));
            this.list.addEventHandler(ExtensionIntegrationList.prototype.ITEM_DELETE, onDependencyDelete.bind(this));

            this.selectTable.addEventHandler('table-item:info', onItemInfo.bind(this));

            submitBtn.addEventHandler('click', function () {
                if (this.currentForm !== undefined && this.currentExtension !== undefined) {
                    var dependencies = this.selectTable.getDependenciesItems();
                    this.currentExtension.dependencies = dependencies;
                    this.currentForm.resolve(dependencies.map(function (item) {
                        return item.integration_point;
                    }));
                    delete this.currentForm;
                    resetForm.call(this);
                }
            }.bind(this));

            cancelBtn.addEventHandler('click', function () {
                if (this.currentForm !== undefined) {
                    this.currentForm.reject();
                    delete this.currentForm;
                    resetForm.call(this);
                }
            }.bind(this));
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

                this.list.setItems(extensions.map(function (ext) {
                    return {
                        id: ext.extends.id,
                        name: ext.name
                    };
                }));

                this.currentExtensionsIds = extensionsIds;
            }

            resetForm.call(this);
        },

        isValid: function () {
            return true;
        }
    });

    function onItemInfo(options) {
        /* jshint validthis:true*/
        if (this.integrationInfoPanel === undefined) {
            this.integrationInfoPanel = new IntegrationInfo();
        }

        integrationService.get(options).then(function (template) {
            this.integrationInfoPanel.setContent(template);
        }.bind(this));

        this.trigger(this.PANEL_SHOW, {
            header: 'Info',
            content: this.integrationInfoPanel
        });
    }


    function onDependencyDelete(extension, dependencyToRemove) {
        /* jshint validthis:true*/
        console.log('deleted', dependencyToRemove);

        var ext = getOriginalExtension.call(this, {extension: extension}),
            indexOfDep = -1;

        ext.dependencies.some(function (item, index) {
            if (item.integration_point.id === dependencyToRemove.id) {
                indexOfDep = index;
                return true;
            }
        });

        if (indexOfDep !== -1) {
            ext.dependencies.splice(indexOfDep, 1);
        }
    }

    function getOriginalExtension(options) {
        /* jshint validthis:true*/
        var currentExtension;

        // get the original extension from the data
        this.data.extensions.some(function (extItem) {
            // ids could be the same when the element is duplicated
            if (extItem.name === options.extension.name && extItem.extends.id === options.extension.id) {
                currentExtension = extItem;
                return true;
            }
        });

        return currentExtension;
    }

    function onItemShowDependencySelect(options) {
        /* jshint validthis:true*/

        resetForm.call(this);
        this.currentForm = options;

        this.currentExtension = getOriginalExtension.call(this, options);

        this.selectTable.setDependenciesItems(this.currentExtension.dependencies || []);

        this.view.findById('sectionTitle').setText(formTitleTxt + ' for ' + options.extension.name);
        hideDependencySection.call(this, false);
    }

    function hideDependencySection(hideStatus) {
        /* jshint validthis:true*/
        if (hideStatus === true) {
            this.dependencySection.setStyle({
                display: 'none'
            });
        }
        else {
            this.dependencySection.removeStyle('display');
        }
    }

    function resetForm() {
        /* jshint validthis:true*/
        if (this.currentForm !== undefined) {
            this.currentForm.reject();
            delete this.currentForm;
        }

        hideDependencySection.call(this, true);
    }
});
