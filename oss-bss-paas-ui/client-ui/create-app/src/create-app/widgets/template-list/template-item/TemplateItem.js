/*global define*/
define([
    'jscore/core',
    'uit!./_templateItem.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        ITEM_SELECT: 'item:select',
        ITEM_INFO: 'item:info',

        view: function () {
            return new View(this.options);
        },

        onViewReady: function () {
            this.view.findById('radioBtn').addEventHandler('click', buildCallback.call(this, this.ITEM_SELECT));
            this.view.findById('infoBtn').addEventHandler('click', buildCallback.call(this, this.ITEM_INFO));
        }
    });

    function buildCallback(name) {
        /* jshint validthis:true*/
        return function () {
            this.trigger(name, {id: this.options.id});
        }.bind(this);
    }

});
