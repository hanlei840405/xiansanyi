$(function () {
    var granted = []; // 已授权过的菜单
    $('#system_role_index_dataGrid').datagrid({
        url: 'rest/system/role/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        border: false,
        toolbar: [{
            text: '新建',
            iconCls: 'icon-add',
            handler: function () {
                $('#system_role_index_edit_dialog').dialog('open');
            }
        }, '-', {
            text: '编辑',
            iconCls: 'icon-edit',
            handler: function () {
                var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $('#system_role_index_edit_dialog').dialog('open');
                } else {
                    $.messager.alert('警告', '请选择一条记录进行编辑');
                }
            }
        }, '-', {
            text: '授权',
            iconCls: 'icon-filter',
            handler: function () {
                var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
                if (selected) { // 打开菜单选择框
                    $('#system_role_index_grant_dialog').dialog('open');
                    $.ajax({
                        method: 'get',
                        url: 'rest/system/menu/allByRole',
                        data: {'code': selected.code},
                        success: function(res) {
                            // 初始化菜单树
                            $('#system_role_index_grant_dialog_form_menu_tree').tree({
                                checkbox: true,
                                animate: true,
                                data: res
                            });
                        }
                    });
                } else {
                    $.messager.alert('警告', '请选择一条记录进行编辑');
                }
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $.messager.confirm('确认','是否删除该条数据',function(r){
                        if (r){
                            $.messager.progress();
                            $.ajax({
                                url: 'rest/system/role/delete',
                                data: {id: selected.id},
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    var token = $("meta[name='_csrf']").attr("content");
                                    var header = $("meta[name='_csrf_header']").attr("content");
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (res) {
                                    if (res == 200) {
                                        $('#system_role_index_edit_dialog').dialog('close');
                                        $('#system_role_index_dataGrid').datagrid('reload');
                                        $.messager.progress('close');
                                    }
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert('警告', '请选择一条记录进行编辑');
                }
            }
        }],
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'code', title: '编号', width: 300, align: 'right'},
            {field: 'name', title: '名称', width: 300, align: 'right'},
            {
                field: 'status',
                title: '状态',
                width: 150,
                align: 'right',
                formatter: function (value) {
                    if (value == '1') {
                        return '有效';
                    }
                    return '无效'
                }
            }
        ]],
        onLoadSuccess: function () {
            $('#system_role_index_dataGrid').datagrid('clearSelections');
        }
    });
    // 角色编辑对话框
    $('#system_role_index_edit_dialog').dialog({
        title: '编辑',
        width: 300,
        height: 250,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                $.messager.progress();
                $('#system_role_index_edit_dialog_form').submit();
            }
        }],
        onOpen: function () {
            var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
            if (selected) { // 修改
                $('#system_role_index_edit_dialog_form').form('load', 'rest/system/role/id?id=' + selected.id);
            }else { // 新增
                $('#system_role_index_edit_dialog_form').form('reset');
            }
        }
    });
    // 角色授权对话框
    $('#system_role_index_grant_dialog').dialog({
        title: '授权',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                var checked = $('#system_role_index_grant_dialog_form_menu_tree').tree('getChecked', ['checked','indeterminate']);
                if(checked.length == 0) {
                    $.messager.progress('close');
                    $.messager.alert('警告', '请选择需要授权的菜单');
                }else {
                    $.messager.progress();
                    var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
                    var array = new Array();
                    for (var i = 0;i < checked.length; i++) {
                        array.push(checked[i].id);
                    }
                    $.ajax({
                        url: 'rest/system/menu/delete',
                        data: {'id': selected.id, 'array': array},
                        type: 'POST',
                        beforeSend: function (xhr) {
                            var token = $("meta[name='_csrf']").attr("content");
                            var header = $("meta[name='_csrf_header']").attr("content");
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (res) {
                            if (res == 200) {
                                $.messager.progress('close');
                            }
                        }
                    });
                }
            }
        }],
        onOpen: function () {
            var selected = $('#system_role_index_dataGrid').datagrid('getSelected');
            if (selected) { // 修改
                $('#system_role_index_edit_dialog_form').form('load', 'rest/system/role/id?id=' + selected.id);
            }else { // 新增
                $('#system_role_index_edit_dialog_form').form('reset');
            }
        }
    });
    // 角色编辑表单
    $('#system_role_index_edit_dialog_form').form({
        url: 'rest/system/role/save',
        iframe: false,
        onSubmit: function () {
            var isValid = $(this).form('validate');
            if (!isValid) {
                $.messager.progress('close');
            }
            return isValid;
        },
        success: function (resp) {
            if (resp == 200) {
                $('#system_role_index_edit_dialog').dialog('close');
                $('#system_role_index_dataGrid').datagrid('reload');
            } else {
                $.messager.alert('警告', '操作失败，请联系系统管理员');
            }
            $.messager.progress('close');
        }
    });
    $('#system_role_index_edit_dialog_form_code').textbox({
        prompt: '编码',
        required: true
    });
    $('#system_role_index_edit_dialog_form_name').textbox({
        prompt: '名称',
        required: true
    });
    $('#system_role_index_edit_dialog_form_status').combobox({
        prompt: '状态',
        valueField: 'value',
        textField: 'label',
        value: '1',
        panelHeight: 'auto',
        editable: false,
        required: true,
        data: [{
            label: '有效',
            value: '1'
        }, {
            label: '无效',
            value: '0'
        }]
    });

    var containId = $('#portal_center').tabs('getSelected')[0].id;
    registryDestroy(containId, 'system_role_index_edit_dialog');
});