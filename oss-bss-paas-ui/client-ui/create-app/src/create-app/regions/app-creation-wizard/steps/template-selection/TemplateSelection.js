/*global define*/
define([
    'layouts/WizardStep',
    'oss-bss-paas-lib/s/TemplateService',
    'oss-bss-paas-lib/w/TemplateInfo',
    '../../../../widgets/template-list/TemplateList',
    'uit!./_templateSelection.hbs'
], function (WizardStep, templateService, TemplateInfo, TemplateList, View) {
    'use strict';

    return WizardStep.extend({

        title: 'Template',

        PANEL_SHOW: 'wizard-step:show-panel',
        PANEL_HIDE: 'wizard-step:hide-panel',

        View: View,

        onViewReady: function () {
            var templateList = this.view.findById('templatesList');

            templateList.addEventHandler(TemplateList.prototype.ITEM_INFO, onItemInfo.bind(this));
            templateList.addEventHandler(TemplateList.prototype.ITEM_SELECT, onItemSelect.bind(this));

            templateService.get().then(function (templates) {
                this.setTemplate(templates);
            }.bind(this));
        },
        setTemplate: function (templates) {
            this.view.findById('templatesList')
                .setItems(templates);

            this.templates = templates;
        },
        isValid: function () {
            return this.options.data.template !== undefined;
        }
    });

    function onItemInfo(options) {
        /* jshint validthis:true*/
        if (this.templateInfoPanel === undefined) {
            this.templateInfoPanel = new TemplateInfo();
        }

        templateService.get(options).then(function (template) {
            this.templateInfoPanel.setContent(template);
        }.bind(this));

        this.trigger(this.PANEL_SHOW, {
            header: 'Info',
            content: this.templateInfoPanel
        });
    }

    function onItemSelect(options) {
        /* jshint validthis:true*/
        var data = this.options.data;

        this.templates.some(function (template) {
            if (template.id === options.id) {
                data.template = template;
                return true;
            }
        });

        this.revalidate();
    }

});
