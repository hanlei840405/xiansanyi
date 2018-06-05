$(function () {
    jQuery.i18n.properties({
        name: ['company/company','layout/layout','button/button'],
        path: 'js/i18n',
        mode: 'both',
        language: $.cookie('lang') || '',
        async: false
    });
    $('#company_datagrid').datagrid({
        url: 'company/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        toolbar: '#company_datagrid_toolbar',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'companyType', title: jQuery.i18n.prop('company.type'), width: 200, align: 'right',
                formatter: function (value) {
                    if (value == '2') {
                        return jQuery.i18n.prop('company.type.group');
                    }else if (value == '1') {
                        return jQuery.i18n.prop('company.type.head');
                    }
                    return jQuery.i18n.prop('company.type.branch')
                }},
            {field: 'companyCode', title: jQuery.i18n.prop('company.code'), width: 200, align: 'right'},
            {field: 'companyName', title: jQuery.i18n.prop('company.name'), width: 200, align: 'right'},
            {field: 'description', title: jQuery.i18n.prop('company.descr'), width: 400, align: 'right'},
            {
                field: 'status',
                title: jQuery.i18n.prop('company.status'),
                width: 100,
                align: 'right',
                formatter: function (value) {
                    if (value == '1') {
                        return jQuery.i18n.prop('company.status.active');
                    }
                    return jQuery.i18n.prop('company.status.inactive')
                }
            }
        ]],
        onLoadSuccess: function () {
            $('#company_datagrid').datagrid('clearSelections');
        }
    });

    var submitForm = function () {
        $('#company_edit_form').form('submit', {
            onSubmit: function () {
                return $(this).form('enableValidation').form('validate');
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.code == 200) {
                    $.messager.alert({
                        title: jQuery.i18n.prop('layout.modal.title.success'),
                        message: jQuery.i18n.prop('layout.modal.title.success'),
                        fn: function () {
                            $('#company_edit_dialog').dialog('close');
                            $('#company_datagrid').datagrid('clearSelections');
                            $('#company_datagrid').datagrid('reload');
                        }
                    });
                } else if (data.code == 1001) {
                    $.messager.alert({
                        title: jQuery.i18n.prop('layout.modal.title.failure'),
                        message: jQuery.i18n.prop('layout.modal.title.failure')
                    });
                } else {
                    $.messager.alert({
                        title: jQuery.i18n.prop('layout.modal.title.failure'),
                        message: jQuery.i18n.prop('layout.modal.title.failure')
                    });
                }
            }
        });
    };

    $('#company_edit_dialog').dialog({
        width: '400px',
        height: '300px',
        title: jQuery.i18n.prop('layout.modal.title.new') + '/' + jQuery.i18n.prop('layout.modal.title.edit'),
        modal: true,
        closed: true,
        buttons: [{
            text: jQuery.i18n.prop('button.submit'),
            iconCls: 'icon-ok',
            handler: submitForm
        }],
        onLoad: function () {
            $('#company_edit_form').form({
                iframe: false
            }),
            $('#company_edit_form_company_type').combobox({
                label: jQuery.i18n.prop('company.type'),
                labelWidth: '90px',
                panelHeight: 'auto',
                required: false,
                editable: false,
                valueField: 'value',
                textField: 'label',
                labelAlign: 'right',
                data: [{
                    label: jQuery.i18n.prop('company.type.group'),
                    value: '2'
                },{
                    label: jQuery.i18n.prop('company.type.head'),
                    value: '1'
                }, {
                    label: jQuery.i18n.prop('company.type.branch'),
                    value: '0'
                }]
            });
            $('#company_edit_form_company_name').textbox({
                label: jQuery.i18n.prop('company.name'),
                labelWidth: '90px',
                required: true,
                labelAlign: 'right'
            });
            $('#company_edit_form_company_description').textbox({
                label: jQuery.i18n.prop('company.descr'),
                labelWidth: '90px',
                labelAlign: 'right'
            });
            $('#company_edit_form_company_status').combobox({
                label: jQuery.i18n.prop('company.status'),
                labelWidth: '90px',
                panelHeight: 'auto',
                required: false,
                editable: false,
                valueField: 'value',
                textField: 'label',
                labelAlign: 'right',
                data: [{
                    label: jQuery.i18n.prop('company.status.active'),
                    value: '1'
                }, {
                    label: jQuery.i18n.prop('company.status.inactive'),
                    value: '0'
                }]
            });
            var selected = $('#company_datagrid').datagrid('getSelected');
            if (selected) {
                $('#company_edit_form').form('load', 'company/get?id=' + selected.id);
            }
        }
    });

    $('#company_datagrid_toolbar_search_company_name').textbox({
        buttonText:jQuery.i18n.prop('company.name')
    });

    $('#company_datagrid_toolbar_search').linkbutton({
        iconCls: 'icon-search',
        plain:true,
        onClick: function () {
            var name = $('#company_datagrid_toolbar_search_company_name').textbox('getValue');
            $('#company_datagrid').datagrid('reload', {
                'companyName': name
            });
        }
    });

    $('#company_datagrid_toolbar_add').linkbutton({
        iconCls: 'icon-add',
        plain:true,
        text: jQuery.i18n.prop('button.add'),
        onClick: function () {
            $('#company_datagrid').datagrid('clearSelections');
            $('#company_edit_dialog').dialog('open').dialog('refresh', 'company/to_edit');
        }
    });

    $('#company_datagrid_toolbar_edit').linkbutton({
        iconCls: 'icon-edit',
        plain:true,
        text: jQuery.i18n.prop('button.edit'),
        onClick: function () {
            var selected = $('#company_datagrid').datagrid('getSelected');
            if (selected == null) {
                $.messager.alert({
                    title: jQuery.i18n.prop('layout.modal.title.failure'),
                    msg: jQuery.i18n.prop('layout.modal.msg.failure.null')
                });
            } else {
                $('#company_edit_dialog').dialog('open').dialog('refresh', 'company/to_edit');
            }
        }
    });

    $('#company_datagrid_toolbar_del').linkbutton({
        iconCls: 'icon-remove',
        plain:true,
        text: jQuery.i18n.prop('button.delete'),
        onClick: function () {
            var checkeds = $('#company_datagrid').datagrid('getChecked');
            if (checkeds.length == 0) {
                $.messager.alert({
                    title: jQuery.i18n.prop('layout.modal.title.failure'),
                    msg: jQuery.i18n.prop('layout.modal.msg.failure.null')
                });
            } else {
                $.messager.confirm({
                    title: jQuery.i18n.prop('layout.modal.title.failure'),
                    msg: jQuery.i18n.prop('layout.modal.msg.warning.confirm'),
                    fn: function (r) {
                        if (r) {
                            var array = new Array();
                            for (var i = 0; i < checkeds.length; i++) {
                                array.push(checkeds[i].id);
                            }
                            $.get('hr/company/delete', {ids: array.join(',')}, function (data) {
                                if (data.code == 200) {
                                    $.messager.alert({
                                        title: jQuery.i18n.prop('layout.modal.title.success'),
                                        fn: function () {
                                            $('#company_datagrid').datagrid('reload');
                                            $('#company_datagrid').datagrid('clearSelections');
                                        }
                                    });
                                } else {
                                    $.messager.alert({
                                        title: jQuery.i18n.prop('layout.modal.title.failure'),
                                        msg: jQuery.i18n.prop('layout.modal.msg.failure.operation')
                                    });
                                }
                            })
                        }
                    }
                });
            }
        }
    });
    var containId = $('#center').tabs('getSelected')[0].id;
    registryDestroy(containId, 'company_edit_dialog');
});