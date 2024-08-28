/*global define*/
define([
    'jscore/core'
], function (core) {
    'use strict';

    function removeAllChildren(elt) {
        elt.children().forEach(function (childElt) {
            childElt.remove();
        });
    }

    return {
        removeAllChildren: removeAllChildren
    };
});