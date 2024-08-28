/*global define*/
define([
    'jscore/core',
    'jscore/ext/privateStore',
    'text!./_loadingWidget.html'
], function (core, PrivateStore, LoadingDotsTemplate) {
    'use strict';

    var _ = PrivateStore.create();

    function _showLoadingAnimation(options) {
        /* jshint validthis:true*/
        var element = this.getElement(),
            loadingAnimationElt = core.Element.parse(LoadingDotsTemplate);

        // position relative necessary to position the ebLoader asset
        element.setStyle({
            position: 'relative'
        });

        if (options && options.small) {
            loadingAnimationElt.find('.ebLoader-Dots').setModifier('size', 'small');
        }
        element.append(loadingAnimationElt);

        _(this).loadingAnimationElt = loadingAnimationElt;
    }

    function _hideLoadingAnimation() {
        /* jshint validthis:true*/
        if (_(this).loadingAnimationElt !== undefined) {
            _(this).loadingAnimationElt.remove();
            delete _(this).loadingAnimationElt;
        }
    }

    function LoadingWidget() {
    }

    LoadingWidget.prototype.showLoadingAnimation = _showLoadingAnimation;
    LoadingWidget.prototype.hideLoadingAnimation = _hideLoadingAnimation;

    return LoadingWidget;
});
