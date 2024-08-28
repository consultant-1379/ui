/*global define*/
define([
    'jscore/base/jquery'
], function (jQuery) {
    'use strict';

    function mergeObjects() {
        return jQuery.extend.apply(jQuery, arguments);
    }

    function arrayEquals(arrA, arrB) {
        if (arrA.length !== arrB.length) {
            return false;
        }

        for (var i = 0; i < arrA.length; i++) {
            if (arrA[i] !== arrB[i]) {
                return false;
            }
        }

        return true;
    }

    return {
        mergeObjects: mergeObjects,
        arrayEquals: arrayEquals
    };
});
