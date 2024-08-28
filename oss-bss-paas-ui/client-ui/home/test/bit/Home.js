/*global define, describe, it, expect */
define([
    'jscore/core',
    'home/Home',
    './home/widgets/link-list/LinkList'
], function (core, Home, LinkList) {
    'use strict';

    describe('Home', function () {

        var docElt = core.Element.wrap(document.body),
            breadcrumb = [
                {name: 'Top Application Name', url: '#top-app-url'},
                {
                    name: 'Application Name',
                    url: '#app-name',
                    children: [
                        {name: 'Child App A', url: '#app-name/child-app-a'},
                        {name: 'Child App B', url: '#app-name/child-app-b'}
                    ]
                }];

        it('it should call LinkList with all children', function () {
            var initSpy = sinon.spy();

            LinkList.prototype.init = initSpy;

            var homeApp = new Home({
                breadcrumb: breadcrumb
            });

            homeApp.start(docElt);

            var appChildren = breadcrumb[breadcrumb.length - 1].children,
                spyListArgs = initSpy.getCall(0).args[0].links;

            expect(initSpy.calledOnce).to.be.true;
            expect(spyListArgs).to.equal(appChildren);

            homeApp.stop();
        });
    });
});
