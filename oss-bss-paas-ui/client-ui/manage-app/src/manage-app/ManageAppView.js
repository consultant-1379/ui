define([
    'jscore/core',
    'template!./manageApp.html',
    'styles!./manageApp.less'
], function (core, template, style) {

    return core.View.extend({

        getTemplate: function() {
            return template(this.options);
        },

        getStyle: function() {
            return style;
        }

    });

});
