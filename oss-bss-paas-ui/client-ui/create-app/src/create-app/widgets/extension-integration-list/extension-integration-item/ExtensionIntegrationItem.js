/*global define*/
define([
    'jscore/core',
    'uit!./_extensionIntegrationItem.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        ITEM_ADD: 'item:add',
        ITEM_DELETE: 'item:delete',
        ITEM_SHOW_ADD_FORM: 'item:show-add-form',

        view: function () {
            return new View({
                data: {
                    name: this.options.name
                },
                listOptions: {
                    items: this.options.integration
                }
            });
        },

        onViewReady: function () {
            var extension = this.options,
                list = this.view.findById('list'),
                addBtn = this.view.findById('addBtn'),
                done = function () {
                    addBtn.setProperty('disabled', false);
                },
                options = {
                    extension: extension,
                    resolve: function (data) {
                        if (Array.isArray(data)) {
                            // replace the content when an array is returned
                            list.setItems(data);
                        }
                        else {
                            list.addItem(data);
                        }
                        done();
                    }.bind(this),
                    reject: function () {
                        done();
                    }
                };

            list.addEventHandler(list.ITEM_DELETE, function (integrationPointToRemove) {
                this.trigger(this.ITEM_DELETE, this.options, integrationPointToRemove);
            }.bind(this));

            addBtn.addEventHandler('click', function () {
                addBtn.setProperty('disabled', true);

                this.trigger(this.ITEM_SHOW_ADD_FORM, options);
            }.bind(this));
        }
    });
});
