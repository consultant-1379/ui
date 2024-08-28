/*global define*/
define([
    'jscore/core',
    'uit!./_extensionItem.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        ITEM_INFO: 'item:info',
        ITEM_DUPLICATE: 'item:duplicate',
        ITEM_SETTINGS: 'item:settings',
        ITEM_UPDATE: 'item:update',

        view: function () {
            return new View({
                data: this.options.data,
                switcherOptions: {
                    onLabel: 'Extend',
                    offLabel: 'Not used'
                }
            });
        },

        onViewReady: function () {
            var extension = this.options.data,
                nameInputElt = this.view.findById('extensionName'),
                versionInputElt = this.view.findById('extensionVersion'),
                settingsBtn = this.view.findById('settingsBtn'),
                duplicateBtn = this.view.findById('duplicateBtn'),
                infoBtn = this.view.findById('infoBtn'),
                itemData = {
                    selected: false,
                    data: {
                        extends: extension,
                        name: '',
                        version: ''
                    }
                };

            this.itemData = itemData;

            nameInputElt.addEventHandler('change', function () {
                itemData.data.name = nameInputElt.getValue();
                validateData.call(this);
            }.bind(this));

            versionInputElt.addEventHandler('change', function () {
                itemData.data.version = versionInputElt.getValue();
                validateData.call(this);
            }.bind(this));

            settingsBtn.addEventHandler('click', onSettingsClick.bind(this));

            duplicateBtn.addEventHandler('click', onDuplicateClick.bind(this));
            infoBtn.addEventHandler('click', onInfoClick.bind(this));

            setSettingsBtnEnabled.call(this, false);

            this.view.findById('switcher').addEventHandler('change', onExtensionToggle.bind(this));
        }
    });

    function onDuplicateClick() {
        /* jshint validthis:true*/
        if (confirm('Duplicate the extension ?')) {
            this.options.listBus.publish(this.ITEM_DUPLICATE, {
                data: this.options.data
            });
        }
    }

    function onInfoClick() {
        /* jshint validthis:true*/
        this.options.listBus.publish(this.ITEM_INFO, {
            id: this.options.data.id
        });
    }

    function onSettingsClick() {
        /* jshint validthis:true*/
        setSettingsBtnEnabled.call(this, false);

        var afterChange = function () {
            validateData.call(this);
        }.bind(this);

        this.options.listBus.publish(this.ITEM_SETTINGS, {
            data: this.itemData.data,
            resolve: afterChange,
            reject: afterChange
        });
    }

    function setSettingsBtnEnabled(isEnabled) {
        /* jshint validthis:true*/
        var settingsBtn = this.view.findById('settingsBtn');

        if (isEnabled) {
            settingsBtn.removeModifier('disabled', 'ebIcon');
            settingsBtn.removeModifier('disabled', 'eaCreateApp-wExtensionList-itemSettingsIcon');
        }
        else {
            settingsBtn.setModifier('disabled', '', 'ebIcon');
            settingsBtn.setModifier('disabled', '', 'eaCreateApp-wExtensionList-itemSettingsIcon');
        }
    }

    function onExtensionToggle() {
        /* jshint validthis:true*/
        var isEnabled = this.view.findById('switcher').getValue();

        this.view.findById('extensionName').setProperty('disabled', !isEnabled);
        this.view.findById('extensionVersion').setProperty('disabled', !isEnabled);

        this.itemData.selected = isEnabled;
        // validate data will toggle settings button state
        validateData.call(this);
    }

    function validateData() {
        /* jshint validthis:true*/
        var itemData = this.itemData,
            itemValidated = itemData.data.name !== '' && itemData.data.version !== '';

        setSettingsBtnEnabled.call(this, itemValidated && this.itemData.selected);

        this.options.listBus.publish(this.ITEM_UPDATE, {
            valid: itemValidated,
            selected: itemData.selected,
            data: itemData.data
        });
    }

});
