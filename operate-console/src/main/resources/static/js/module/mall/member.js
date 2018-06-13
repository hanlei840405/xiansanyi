$(function () {
    $('#mall_member_index_dataGrid').datagrid({
        url: 'rest/mall/member/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        border: false,
        toolbar: [{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                var selected = $('#mall_member_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $.messager.confirm('确认', '是否删除该条数据', function (r) {
                        if (r) {
                            $.messager.progress();
                            $.ajax({
                                url: 'rest/mall/member/delete',
                                data: {id: selected.id},
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    var token = $("meta[name='_csrf']").attr("content");
                                    var header = $("meta[name='_csrf_header']").attr("content");
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (res) {
                                    if (res == 200) {
                                        $('#mall_member_index_view_dialog').dialog('close');
                                        $('#mall_member_index_dataGrid').datagrid('reload');
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
            {field: 'role', title: '角色', width: 150, align: 'right', formatter: function(value){
                if(value == '1') {
                    return '买家';
                } else if(value=='2') {
                    return '卖家';
                } else if(value=='3') {
                    return '买家/卖家';
                } else {
                    return ''
                }
            }},
            {field: 'gender', title: '性别', width: 150, align: 'right', formatter: function(value){
                if(value == 'F') {
                    return '女';
                } else if(value=='M') {
                    return '男';
                } else {
                    return ''
                }
            }},
            {field: 'status', title: '状态', width: 150, align: 'right', formatter: function(value){
                if(value == '0') {
                    return '取消';
                } else {
                    return '正常';
                }
            }}
        ]],
        onLoadSuccess: function () {
            $('#mall_member_index_dataGrid').datagrid('clearSelections');
        }
    });
});