/*global define*/
define([
    'tablelib/Cell',
    './ActionCellView'
], function (Cell, View) {
    'use strict';

    return Cell.extend({

        View: View,

        setValue: function () {
            var options = this.getRow().getData(),
                add = this.view.getAddBtn(),
                remove = this.view.getDeleteBtn(),
                info = this.view.getInfoBtn()
                ;

            bindOrDestroy.call(this, options.actions.info, info, 'actioncell:info', options);
            bindOrDestroy.call(this, options.actions.add, add, 'actioncell:add', options);
            bindOrDestroy.call(this, options.actions.remove, remove, 'actioncell:remove', options);
        }
    });

    function bindOrDestroy(actionsConfig, elt, eventName, value) {
        /* jshint validthis:true*/
        if (actionsConfig === true) {
            var table = this.getTable(),
                row = this.getRow();

            elt.addEventHandler('click', function () {
                table.trigger(eventName, value, row.getIndex());
            });
        }
        else {
            elt.remove();
        }
    }
});
