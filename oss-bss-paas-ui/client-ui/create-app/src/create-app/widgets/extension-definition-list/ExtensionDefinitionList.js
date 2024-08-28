/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericList',
    './extension-item/ExtensionItem'
], function (_GenericList, ExtensionItem) {
    'use strict';

    return _GenericList.extend({

        ITEM_CLASS: ExtensionItem,

        config: {
            loadingAnimation: true,
            styles: {
                'min-height': '250px'
            }
        }
    });
});
