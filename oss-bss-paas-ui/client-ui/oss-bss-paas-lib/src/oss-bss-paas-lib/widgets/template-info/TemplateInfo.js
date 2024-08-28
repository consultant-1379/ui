/*global define*/
define([
    '../base/loading-panel/_LoadingPanel',
    './content/TemplateContent'
], function (_LoadingPanel, TemplateContent) {
    'use strict';

    return _LoadingPanel.extend({
        CONTENT_CLASS: TemplateContent
    });
});
