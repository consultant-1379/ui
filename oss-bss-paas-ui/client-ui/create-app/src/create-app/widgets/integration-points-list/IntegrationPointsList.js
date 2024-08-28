/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericList',
    './integration-item/IntegrationItem'
], function (_GenericList, IntegrationItem) {
    'use strict';

    return _GenericList.extend({

        ITEM_CLASS: IntegrationItem,

        ITEM_DELETE: 'item:delete',

        config: {
            loadingAnimation: false
        },

        onItemCreated: function (itemW) {
            itemW.addEventHandler(IntegrationItem.prototype.ITEM_DELETE, buildCallback.call(this, this.ITEM_DELETE));
        }
    });

    function buildCallback(name) {
        /* jshint validthis:true*/
        return function (options) {
            this.trigger(name, options);
        }.bind(this);
    }
});
