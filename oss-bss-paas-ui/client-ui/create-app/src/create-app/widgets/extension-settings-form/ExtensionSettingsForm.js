/*global define*/
define([
    'oss-bss-paas-lib/w/_GenericForm',
    'uit!./_extensionSettingsForm.hbs'
], function (_GenericForm, View) {
    'use strict';

    return _GenericForm.extend({

        fieldNames: [
            'description',
            'minInstances', 'maxInstances',
            'cpu',
            'memorySize', 'memoryUnit'
        ],

        view: function () {
            var actions = this.options.actions,
                hasSubmit = actions && actions.submit,
                hasCancel = actions && actions.cancel,
                spinnerOpts = {
                    min: 1,
                    compact: true
                };

            return new View({
                minInstanceOptions: spinnerOpts,
                maxInstanceOptions: spinnerOpts,
                cpuOptions: spinnerOpts,
                memorySizeOptions: {
                    value: 64,
                    min: 1,
                    compact: true
                },
                memoryUnitOptions: {
                    value: {name: 'Mb', value: 'Mb', title: 'Mb'},
                    items: [
                        {name: 'Kb', value: 'Kb', title: 'Kb'},
                        {name: 'Mb', value: 'Mb', title: 'Mb'},
                        {name: 'Gb', value: 'Gb', title: 'Gb'}
                    ],
                    modifiers: [
                        {name: 'width', value: 'mini'}
                    ]
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
            var values = this.getValues(),
                invalidFieldList = '';

            if (!values.description) {
                invalidFieldList += ' description,';
            }
            if (values.minInstance > values.maxInstance) {
                invalidFieldList += ' min/max instance';
            }

            if (invalidFieldList !== '') {
                alert('Invalid value for:' + invalidFieldList);
                return false;
            }

            return true;
        }
    });
});
