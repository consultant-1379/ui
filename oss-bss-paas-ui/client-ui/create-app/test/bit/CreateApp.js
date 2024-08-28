/*global define, describe, it, expect */
define([
    'create-app/CreateApp'
], function (CreateApp) {
    'use strict';

    describe('CreateApp', function () {

        it('CreateApp should be defined', function () {
            expect(CreateApp).not.to.be.undefined;
        });

    });

});
