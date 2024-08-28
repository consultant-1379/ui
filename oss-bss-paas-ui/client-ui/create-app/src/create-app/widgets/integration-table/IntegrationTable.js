/*global define*/
define([
    'jscore/core',
    'tablelib/Table',
    'tablelib/plugins/PinColumns',
    'tablelib/plugins/ResizableHeader',
    'tablelib/plugins/StickyScrollbar',
    'tablelib/plugins/Selection',
    './action-cell/ActionCell'
], function (core,
             // table lib
             Table, PinColumns, ResizableHeader, StickyScrollbar, Selection,
             // cells
             ActionCell) {
    'use strict';

    return core.Widget.extend({

        onViewReady: function () {
            initTable.call(this, []);
        },
        getData: function () {
            return this.table.getData();
        },
        addRow: function (row, index) {
            this.table.addRow(row, index);
        },
        removeRow: function (index) {
            this.table.removeRow(index);
        },

        setData: function (data, options) {
            this.table.setData(data.map(function (item) {
                return {
                    actions: {
                        add: options.actions.add,
                        remove: options.actions.remove,
                        info: options.actions.info,
                    },
                    templateName: item.template.name,
                    extensionName: item.extension.name,
                    integrationPointName: item.integration_point.name,
                    origin: item
                };
            }));
        }
    });

    function initTable(data) {
        /* jshint validthis:true*/
        var table = new Table({
            plugins: [
                new PinColumns(),
                new StickyScrollbar(),
                //new Selection({
                //    checkboxes: true,
                //    selectableRows: true
                //}),
                //new ResizableHeader()
            ],
            data: data,
            columns: [{
                title: 'Actions',
                width: '75px',
                cellType: ActionCell,
                sortable: false,
                resizable: false
            }, {
                title: 'Integration Point Name',
                attribute: 'integrationPointName',
                width: '200px',
                sortable: true,
                resizable: true
            }, {
                title: 'Extension Name',
                attribute: 'extensionName',
                width: '150px',
                sortable: true,
                resizable: true
            }, {
                title: 'Application Name',
                attribute: 'templateName',
                width: '150px',
                sortable: true,
                resizable: true
            }]
        });

        table.attachTo(this.getElement());
        this.table = table;

        table.addEventHandler('actioncell:info', buildCallback.call(this, 'table-item:info'));
        table.addEventHandler('actioncell:add', buildCallback.call(this, 'table-item:add'));
        table.addEventHandler('actioncell:remove', buildCallback.call(this, 'table-item:remove'));
    }

    function buildCallback(name) {
        /* jshint validthis:true*/
        return function (value, index) {
            this.trigger(name, value, index);
        }.bind(this);
    }

});
