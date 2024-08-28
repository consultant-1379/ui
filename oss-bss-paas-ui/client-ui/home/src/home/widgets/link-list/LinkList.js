/*global define*/
define([
    'jscore/core',
    'uit!./_linkList.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({

        view: function () {
            return new View(this.options);
        }
    });
});
