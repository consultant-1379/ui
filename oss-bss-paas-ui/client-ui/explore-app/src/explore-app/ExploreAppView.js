define([
    'jscore/core',
    'template!./exploreApp.html',
    'styles!./exploreApp.less'
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
