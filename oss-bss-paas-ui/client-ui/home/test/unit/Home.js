/*global define, describe, it, expect */
define([
    'home/Home'
], function (Home) {
    'use strict';

    describe('Home', function () {

        it('Home should be defined', function () {
            expect(Home).not.to.be.undefined;
        });

    });

});
