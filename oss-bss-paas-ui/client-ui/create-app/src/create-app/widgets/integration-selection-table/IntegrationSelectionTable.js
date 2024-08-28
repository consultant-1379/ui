/*global define*/
define([
    'jscore/core',
    'uit!./_integrationSelectionTable.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        view: function () {
            return new View({
                destinationOptions: {
                    items: []
                }
            });
        },

        onViewReady: function () {
            var sourceTable = this.view.findById('source'),
                destinationTable = this.view.findById('destination');

            sourceTable.addEventHandler('table-item:info', onItemInfo.bind(this));
            sourceTable.addEventHandler('table-item:add', onItemAdd.bind(this));

            destinationTable.addEventHandler('table-item:info', onItemInfo.bind(this));
            destinationTable.addEventHandler('table-item:remove', onItemRemove.bind(this));

            this.destinationTable = destinationTable;
        },

        setAvailableItems: function (data) {
            this.view.findById('source').setData(data, {
                actions: {
                    add: true,
                    info: true
                }
            });
        },

        getDependenciesItems: function () {
            return this.destinationTable.getData().map(function (item) {
                return item.origin;
            });
        },

        setDependenciesItems: function (data) {
            this.view.findById('destination').setData(data, {
                actions: {
                    remove: true,
                    info: true
                }
            });
        }
    });

    function onItemInfo(item) {
        /* jshint validthis:true*/
        this.trigger('table-item:info', item.id);
    }

    function onItemRemove(item, index) {
        /* jshint validthis:true*/
        this.destinationTable.removeRow(index);
    }

    function onItemAdd(itemToAdd) {
        /* jshint validthis:true*/
        var destData = this.destinationTable.getData(),
            itemFound = destData.some(function (destItem) {
                return destItem.origin.integration_point.id === itemToAdd.origin.integration_point.id;
            });

        if (itemFound) {
            alert('Dependency already added.');
            return;
        }

        // dirty clone before changing the actions
        itemToAdd = JSON.parse(JSON.stringify(itemToAdd));
        itemToAdd.actions = {
            remove: true,
            info: true
        };

        this.destinationTable.addRow(itemToAdd, 0);
    }
});
