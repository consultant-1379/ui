/*global define*/
define(function () {
    'use strict';

    var constantsMap = {};

    function getValue(key) {
        if (constantsMap[key] === undefined) {
            throw new Error('Constant not defined: ' + key);
        }

        return constantsMap[key];
    }

    function setValue(key, value) {
        constantsMap[key] = value;
    }

    return {
        get: getValue,
        set: setValue
    };
});
