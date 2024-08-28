/*global define*/
define([
    '../base/loading-panel/_LoadingPanel',
    './content/ExtensionContent'
], function (_LoadingPanel, ExtensionContent) {
    'use strict';

    return _LoadingPanel.extend({
        CONTENT_CLASS: ExtensionContent
    });
});
