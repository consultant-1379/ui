/*global define*/
define([
    'layouts/WizardStep',
    'uit!./_summary.hbs'
], function (WizardStep, View) {
    'use strict';

    return WizardStep.extend({

        title: 'Summary',

        View: View,

        onDOMAttach: function () {
            this.view.findById('code').setText(JSON.stringify(this.options.data, null, 2));
        },
        isValid: function () {
            return true;
        }
    });
});
