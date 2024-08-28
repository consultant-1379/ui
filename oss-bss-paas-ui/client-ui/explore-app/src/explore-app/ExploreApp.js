define([
    'jscore/core',
    './ExploreAppView',
    'i18n!explore-app/dictionary.json'
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
