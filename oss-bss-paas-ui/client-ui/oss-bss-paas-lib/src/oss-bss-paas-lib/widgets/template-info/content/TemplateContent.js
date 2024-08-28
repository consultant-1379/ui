/*global define*/
define([
    'jscore/core',
    'uit!./_templateContent.hbs'
], function (core, View) {
    'use strict';

    return core.Widget.extend({
        view: function () {
            return new View(this.options);
        }
    });
});
