/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericList',
    './extension-integration-item/ExtensionIntegrationItem'
], function (_GenericList, ExtensionIntegrationItem) {
    'use strict';

    return _GenericList.extend({

        ITEM_CLASS: ExtensionIntegrationItem,

        ITEM_ADD: 'item:add',
        ITEM_DELETE: 'item:delete',
        ITEM_SHOW_ADD_FORM: 'item:show-add-form',

        config: {
            loadingAnimation: false,
            styles: {
                'min-height': '150px'
            }
        },

        onItemCreated: function (itemW) {
            itemW.addEventHandler(ExtensionIntegrationItem.prototype.ITEM_ADD, buildCallback.call(this, this.ITEM_ADD));
            itemW.addEventHandler(ExtensionIntegrationItem.prototype.ITEM_DELETE, buildCallback.call(this, this.ITEM_DELETE));
            itemW.addEventHandler(ExtensionIntegrationItem.prototype.ITEM_SHOW_ADD_FORM, buildCallback.call(this, this.ITEM_SHOW_ADD_FORM));
        }
    });

    function buildCallback(name) {
        /* jshint validthis:true*/
        return function () {
            // forward all params to the trigger call
            var args = Array.prototype.slice.call(arguments);
            args.unshift(name);
            this.trigger.apply(this, args);
        }.bind(this);
    }
});
