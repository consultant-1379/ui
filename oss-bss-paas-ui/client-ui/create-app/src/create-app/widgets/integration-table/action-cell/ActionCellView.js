/*global define*/
define([
    'jscore/core',
    'template!./_actionCell.hbs',
    'styles!./_actionCell.less'
], function (core, template, styles) {
    'use strict';

    return core.View.extend({

        getTemplate: function () {
            return template(this.options);
        },
        getStyle: function () {
            return styles;
        },

        getAddBtn: function () {
            return this.getElement().find('.ebIcon_add');
        },
        getDeleteBtn: function () {
            return this.getElement().find('.ebIcon_delete');
        },
        getInfoBtn: function () {
            return this.getElement().find('.ebIcon_info');
        }
    });
});
