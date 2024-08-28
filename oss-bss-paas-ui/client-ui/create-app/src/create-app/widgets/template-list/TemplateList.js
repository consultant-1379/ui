/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericList',
    './template-item/TemplateItem'
], function (_GenericList, TemplateItem) {
    'use strict';

    return _GenericList.extend({

        ITEM_CLASS: TemplateItem,

        ITEM_SELECT: 'item:select',
        ITEM_INFO: 'item:info',

        config: {
            loadingAnimation: true,
            styles: {
                'min-height': '250px'
            }
        },

        onItemCreated: function (itemW) {
            itemW.addEventHandler(TemplateItem.prototype.ITEM_INFO, buildCallback.call(this, this.ITEM_INFO));
            itemW.addEventHandler(TemplateItem.prototype.ITEM_SELECT, buildCallback.call(this, this.ITEM_SELECT));
        }
    });

    function buildCallback(name) {
        /* jshint validthis:true*/
        return function (options) {
            this.trigger(name, options);
        }.bind(this);
    }

});
