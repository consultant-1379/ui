/*global define*/
define([
    'jscore/core',
    'uit!./_integrationItem.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        ITEM_DELETE: 'item:delete',

        view: function () {
            return new View(this.options);
        },

        onViewReady: function () {
            this.view.findById('delete').addEventHandler('click', function () {
                // prompt the user before delete
                if (confirm('Delete ' + this.options.name + ' ?')) {
                    this.trigger(this.ITEM_DELETE, this.options);
                    this.destroy();
                }
            }.bind(this));
        }
    });
});
