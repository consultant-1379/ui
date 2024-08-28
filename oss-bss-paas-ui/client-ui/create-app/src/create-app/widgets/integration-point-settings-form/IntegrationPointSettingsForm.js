/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericForm',
    'uit!./_integrationPointSettingsForm.hbs'
], function (_GenericForm, View) {
    'use strict';

    return _GenericForm.extend({

        fieldNames: [
            'name',
            'version',
            'description',
            'port'
        ],

        view: function () {
            var actions = this.options.actions,
                hasSubmit = actions && actions.submit,
                hasCancel = actions && actions.cancel;

            return new View({
                portOptions: {
                    min: 1,
                    max: 65535,
                    compact: true
                },
                actions: !actions ? false : {
                    submit: !hasSubmit ? false : {
                        label: actions.submit.label || 'Submit'
                    },
                    cancel: !hasCancel ? false : {
                        label: actions.cancel.label || 'Cancel'
                    }
                }
            });
        },

        validate: function () {
            var values = this.getValues();

            if (values.name && values.version && values.description) {
                this.trigger('submit', values);
            }
        }
    });
});
