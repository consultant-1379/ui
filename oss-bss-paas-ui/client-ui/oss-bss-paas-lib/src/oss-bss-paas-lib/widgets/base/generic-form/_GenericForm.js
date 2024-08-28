/*global define*/
define([
    'jscore/core',
    'jscore/ext/privateStore',
    'widgets/SelectBox'
], function (core, PrivateStore, SelectBox) {
    'use strict';

    var _ = PrivateStore.create();

    return core.Widget.extend({

        // to be overridden to list the form fields
        fieldNames: [],

        events: {
            CANCEL: 'cancel',
            SUBMIT: 'submit'
        },

        onViewReady: function () {
            _(this).fieldMap = readFields.call(this, this.fieldNames);

            // element form MUST be defined
            this.view.findById('form').addEventHandler('submit', function (e) {
                e.preventDefault();
            });

            var submitBtn = this.view.findById('submit'),
                cancelBtn = this.view.findById('cancel');

            if (submitBtn !== undefined) {
                submitBtn.addEventHandler('click', this.submit.bind(this));
            }
            if (cancelBtn !== undefined) {
                cancelBtn.addEventHandler('click', this.cancel.bind(this));
            }
        },

        getValues: function () {
            var vals = {},
                fieldMap = _(this).fieldMap;

            Object.keys(fieldMap).forEach(function (key) {
                var fieldW = fieldMap[key];

                if (fieldW instanceof SelectBox) {
                    vals[key] = fieldW.getValue().value;
                    return;
                }
                vals[key] = fieldW.getValue();
            });

            return vals;
        },
        setValues: function (values) {
            var fieldMap = _(this).fieldMap;

            Object.keys(fieldMap).forEach(function (key) {
                var value = values[key];

                if (value !== undefined) {
                    var fieldW = fieldMap[key];

                    if (fieldW instanceof SelectBox) {

                        fieldW.setValue({name: value, value: value});
                        return;
                    }
                    fieldW.setValue(value);
                }
            });
        },

        reset: function () {
            applyToFields.call(this, {
                fnName: 'setValue'
            }, '');
        },
        validate: function () {
            return true;
        },

        submit: function () {
            if (this.validate()) {
                this.trigger(this.events.SUBMIT, this.getValues());
            }
        },

        cancel: function () {
            this.trigger(this.events.CANCEL);
        },

        disable: function () {
            setFieldEnabled.call(this, false);
        },
        enable: function () {
            setFieldEnabled.call(this, true);
        }
    });

    function isElement(w) {
        return w instanceof core.Element;
    }

    function isWidget(w) {
        return w instanceof core.Widget;
    }

    function setFieldEnabled(enabledState) {
        /* jshint validthis:true*/
        var actionName = enabledState ? 'enable' : 'disable',
            eltOpts = {
                fn: core.Element.prototype.setProperty,
                filter: isElement
            },
            widgetOpts = {fnName: actionName, filter: isWidget};

        applyToFields.call(this, eltOpts, 'disabled', !enabledState);
        applyToFields.call(this, widgetOpts);

        var submitBtn = this.view.findById('submit'),
            cancelBtn = this.view.findById('cancel');

        if (submitBtn !== undefined) {
            submitBtn.setProperty('disabled', !enabledState);
        }
        if (cancelBtn !== undefined) {
            cancelBtn.setProperty('disabled', !enabledState);
        }
    }

    function applyToFields(options) {
        /* jshint validthis:true*/
        var fieldMap = _(this).fieldMap,
            args = [].slice.apply(arguments);

        // apply all arguments except function
        args.shift();

        Object.keys(fieldMap).forEach(function (key) {
            var field = fieldMap[key];

            if (options.filter && !options.filter(field)) {
                return;
            }

            if (options.fn) {
                options.fn.apply(field, args);
            }
            else if (options.fnName && field[options.fnName]) {
                field[options.fnName].apply(field, args);
            }
        });
    }


    function readFields(fieldNames) {
        /* jshint validthis:true*/
        var fieldEltMap = {},
            view = this.view;

        fieldNames.forEach(function (key) {
            fieldEltMap[key] = view.findById(key);
        });

        return fieldEltMap;
    }

});
