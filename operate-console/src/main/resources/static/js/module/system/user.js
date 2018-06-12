$(function () {
    var granted = []; // 已授权过的菜单
    $('#system_user_index_dataGrid').datagrid({
        url: 'rest/system/user/page',
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
                $('#system_user_index_edit_dialog').dialog('open');
            }
        }, '-', {
            text: '编辑',
            iconCls: 'icon-edit',
            handler: function () {
                var selected = $('#system_user_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $('#system_user_index_edit_dialog').dialog('open');
                } else {
                    $.messager.alert('警告', '请选择一条记录进行编辑');
                }
            }
        }, '-', {
            text: '分配角色',
            iconCls: 'icon-filter',
            handler: function () {
                var selected = $('#system_user_index_dataGrid').datagrid('getSelected');
                if (selected) { // 打开菜单选择框
                    $('#system_user_index_grant_dialog').dialog('open');
                    $('#system_user_index_grant_dialog_role_dataGrid').datagrid('clearSelections');
                    // 获取用户的角色
                    $.ajax({
                        url: 'rest/system/user/roles',
                        data: {id: selected.id},
                        type: 'get',
                        success: function (res) {
                            for (var i = 0; i < res.length; i++) {
                                $('#system_user_index_grant_dialog_role_dataGrid').datagrid('selectRecord', res[i].id);
                            }
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
                var selected = $('#system_user_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $.messager.confirm('确认', '是否删除该条数据', function (r) {
                        if (r) {
                            $.messager.progress();
                            $.ajax({
                                url: 'rest/system/user/delete',
                                data: {id: selected.id},
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    var token = $("meta[name='_csrf']").attr("content");
                                    var header = $("meta[name='_csrf_header']").attr("content");
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (res) {
                                    if (res == 200) {
                                        $('#system_user_index_edit_dialog').dialog('close');
                                        $('#system_user_index_dataGrid').datagrid('reload');
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
            {field: 'username', title: '账号', width: 150, align: 'right'},
            {field: 'nick', title: '昵称', width: 150, align: 'right'},
            {field: 'lastName', title: '姓', width: 150, align: 'right'},
            {field: 'firstName', title: '名', width: 150, align: 'right'},
            {field: 'mobile', title: '电话', width: 150, align: 'right'},
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
            $('#system_user_index_dataGrid').datagrid('clearSelections');
        }
    });
    // 角色编辑对话框
    $('#system_user_index_edit_dialog').dialog({
        title: '编辑',
        width: 400,
        height: 350,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                $.messager.progress();
                $('#system_user_index_edit_dialog_form').submit();
            }
        }],
        onOpen: function () {
            var selected = $('#system_user_index_dataGrid').datagrid('getSelected');
            if (selected) { // 修改
                $('#system_user_index_edit_dialog_form').form('load', 'rest/system/user/id?id=' + selected.id);
            } else { // 新增
                $('#system_user_index_edit_dialog_form').form('reset');
            }
        }
    });
    // 角色授权对话框
    $('#system_user_index_grant_dialog').dialog({
        title: '分配角色',
        width: 600,
        height: 500,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                var selectedUser = $('#system_user_index_dataGrid').datagrid('getSelected');
                if(selectedUser.length == 0) {
                    $.messager.progress('close');
                    $.messager.alert('警告', '请选择需要分配的用户');
                }else {
                    $.messager.progress();
                    var selectedRole = $('#system_user_index_grant_dialog_role_dataGrid').datagrid('getSelected');
                    var array = new Array();
                    for (var i = 0;i < selectedRole.length; i++) {
                        array.push(selectedRole[i].id);
                    }
                    $.ajax({
                        url: 'rest/system/user/grant',
                        data: {'id': selected.id, 'array': array.join(',')},
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
            var selected = $('#system_user_index_dataGrid').datagrid('getSelected');
            if (selected) { // 修改
                $('#system_user_index_edit_dialog_form').form('load', 'rest/system/user/id?id=' + selected.id);
            } else { // 新增
                $('#system_user_index_edit_dialog_form').form('reset');
            }
        }
    });
    // 角色编辑表单
    $('#system_user_index_edit_dialog_form').form({
        url: 'rest/system/user/save',
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
                $('#system_user_index_edit_dialog').dialog('close');
                $('#system_user_index_dataGrid').datagrid('reload');
            } else {
                $.messager.alert('警告', '操作失败，请联系系统管理员');
            }
            $.messager.progress('close');
        }
    });
    $('#system_user_index_edit_dialog_form_nick').textbox({
        prompt: '昵称'
    });
    $('#system_user_index_edit_dialog_form_last_name').textbox({
        prompt: '姓'
    });
    $('#system_user_index_edit_dialog_form_first_name').textbox({
        prompt: '名'
    });
    $('#system_user_index_edit_dialog_form_mobile').textbox({
        prompt: '电话'
    });
    $('#system_user_index_edit_dialog_form_status').combobox({
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

    $('#system_user_index_grant_dialog_role_dataGrid').datagrid({
        url: 'rest/system/role/all',
        method: 'GET',
        rownumbers: true,
        pagination: false,
        idField: 'id',
        fit: true,
        border: false,
        toolbar: [{
            text: '分配',
            iconCls: 'icon-add',
            handler: function () {

            }
        }],
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'code', title: '编号', width: 300, align: 'right'},
            {field: 'name', title: '名称', width: 300, align: 'right'}
        ]],
        onLoadSuccess: function () {
            $('#system_user_index_grant_dialog_role_dataGrid').datagrid('clearSelections');
        }
    });
    registryDestroy(['system_user_index_edit_dialog', 'system_user_index_grant_dialog']);
});