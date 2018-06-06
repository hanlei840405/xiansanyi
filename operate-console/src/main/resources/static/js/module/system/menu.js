$(function () {
    $('#system_menu_index_dataGrid').datagrid({
        url: 'rest/system/menu/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        toolbar: [{
            text:'新建',
            iconCls:'icon-add',
            handler:function(){alert('add')}
        },'-',{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){alert('cut')}
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){alert('save')}
        }],
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'code', title: '编号', width: 100, align: 'right'},
            {field: 'name', title: '名称', width: 100, align: 'right'},
            {field: 'category', title: '类别', width: 100, align: 'right'},
            {field: 'url', title: '连接', width: 400, align: 'right'},
            {
                field: 'status',
                title: '状态',
                width: 100,
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
            $('#system_menu_index_dataGrid').datagrid('clearSelections');
        }
    });
});