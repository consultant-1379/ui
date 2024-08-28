define([
    'jscore/core',
    './ManageAppView',
    'i18n!manage-app/dictionary.json'
], function (core, View, Dictionary) {

    return core.App.extend({

        view: function() {
            return new View({i18n: Dictionary});
        },

        onStart: function () {

        },

        onResume: function() {

        },

        onPause: function() {

        },

        onBeforeLeave: function() {
            
        }
    });

});
