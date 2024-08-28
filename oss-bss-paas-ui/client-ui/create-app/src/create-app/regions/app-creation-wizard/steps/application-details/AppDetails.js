/*global define*/
define([
    'layouts/WizardStep',
    'uit!./_appDetails.hbs'
], function (WizardStep, View) {
    'use strict';

    return WizardStep.extend({

        title: 'Application Details',

        View: View,

        onViewReady: function () {
            var nameInputElt = this.view.findById('name'),
                versionInputElt = this.view.findById('version'),
                descriptionInputElt = this.view.findById('description'),
                appDetails = {
                    version: '0.0.1'
                },
                buildCallback = function (elt, attr) {
                    return function () {
                        appDetails[attr] = elt.getValue();
                        this.revalidate();
                    }.bind(this);
                }.bind(this);

            this.options.data.application = appDetails;

            versionInputElt.setValue(appDetails.version);

            nameInputElt.addEventHandler('input', buildCallback(nameInputElt, 'name'));
            versionInputElt.addEventHandler('change', buildCallback(versionInputElt, 'version'));
            descriptionInputElt.addEventHandler('change', buildCallback(descriptionInputElt, 'description'));
        },
        isValid: function () {
            var appDetails = this.options.data.application;
            return appDetails.name;
        }
    });
});
