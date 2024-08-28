/*global define*/
define([
    '../base/loading-panel/_LoadingPanel',
    './content/IntegrationContent'
], function (_LoadingPanel, IntegrationContent) {
    'use strict';

    return _LoadingPanel.extend({
        CONTENT_CLASS: IntegrationContent
    });
});
