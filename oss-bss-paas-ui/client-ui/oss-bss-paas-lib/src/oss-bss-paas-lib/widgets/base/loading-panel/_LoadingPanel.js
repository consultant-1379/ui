/*global define*/
define([
    'jscore/core',
    'jscore/ext/privateStore',
    '../../../ext/loading/LoadingWidget',
    'uit!./__loadingPanel.hbs'
], function (core, PrivateStore, LoadingWidget, View) {
    'use strict';

    var _ = PrivateStore.create();

    return core.Widget.extend({

        View: View,

        init: function () {
            _(this).loadingAnimationElt = new LoadingWidget();
        },

        onViewReady: function () {
            // used for the loading animation positioning
            this.getElement().setStyle({
                'min-height': '100px'
            });

            if (this.options.data === undefined) {
                this.showLoadingAnimation();
                return;
            }
            this.setContent(this.options.data);
        },

        showLoadingAnimation: function () {
            _(this).loadingAnimationElt.showLoadingAnimation.call(this);
        },
        hideLoadingAnimation: function () {
            _(this).loadingAnimationElt.hideLoadingAnimation.call(this);
        },

        setContent: function (data) {
            var ContentClass = this.CONTENT_CLASS;

            if (_(this).loadingAnimationElt !== undefined) {
                this.hideLoadingAnimation();
            }

            if (this.currentContent !== undefined) {
                this.currentContent.destroy();
            }

            this.currentContent = new ContentClass(data);
            this.currentContent.attachTo(this.getElement());
        }
    });
});
