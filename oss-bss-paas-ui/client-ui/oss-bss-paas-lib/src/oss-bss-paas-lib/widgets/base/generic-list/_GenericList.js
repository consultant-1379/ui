/*global define*/
define([
    'jscore/core',
    'jscore/ext/privateStore',
    'oss-bss-paas-lib/e/extDom',
    '../../../ext/loading/LoadingWidget',
    'uit!./__genericList.hbs'
], function (core, PrivateStore, extDom, LoadingWidget, View) {
    'use strict';

    var _ = PrivateStore.create();

    return core.Widget.extend({

        View: View,

        onViewReady: function () {
            var options = this.options || {},
                config = this.config;

            if (options.items === undefined && config !== undefined) {

                if (config.loadingAnimation) {
                    _(this).loadingAnimationElt = new LoadingWidget();
                    _(this).loadingAnimationElt.showLoadingAnimation.call(this);
                }

                if (config.styles !== undefined) {
                    this.getElement().setStyle(config.styles);
                }

                return;
            }

            this.setItems(options.items);
        },

        setItems: function (items) {
            items = items || [];

            if (_(this).loadingAnimationElt !== undefined) {
                _(this).loadingAnimationElt.hideLoadingAnimation.call(this);
            }

            extDom.removeAllChildren(this.getElement());

            items.forEach(this.addItem.bind(this));
        },

        addItem: function (item) {
            var element = this.getElement(),
                ItemClass = this.ITEM_CLASS;

            var itemW = new ItemClass(item);
            itemW.attachTo(element);

            if (this.onItemCreated !== undefined) {
                this.onItemCreated(itemW);
            }
        },

        addAllItems: function (items) {
            items.forEach(this.addItem.bind(this));
        }
    });
});
