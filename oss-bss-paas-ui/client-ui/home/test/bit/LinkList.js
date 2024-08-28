/*global define, describe, it, expect */
define([
    'jscore/core',
    './home/widgets/link-list/LinkList'
], function (core, LinkList) {
    'use strict';

    describe('LinkList', function () {

        it('it should create a list of items matching items provided in links param', function () {

            var links = [
                    {name: 'Link A', url: '#link-a'},
                    {name: 'Link B', url: '#link-b'}
                ],
                list = new LinkList({
                    links: links
                }),
                linkElts = list.getElement().findAll('.eaHome-wLinkList-link');

            expect(linkElts.length).to.equal(links.length);

            linkElts.forEach(function (elt, i) {
                expect(elt.getAttribute('href')).to.equal(links[i].url);
                expect(elt.getText()).to.equal(links[i].name);
            });
        });
    });
});
