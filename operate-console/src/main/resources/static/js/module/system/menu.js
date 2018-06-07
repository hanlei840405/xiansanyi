$(function () {
    $('#system_menu_index_menu_tree').tree({
        url: 'rest/system/menu/tree',
        method: 'GET',
        onClick: function (node) {
            $('#system_menu_index_dataGrid').datagrid('load',{id: node.id});
        }
    });
    $('#system_menu_index_dataGrid').datagrid({
        url: 'rest/system/menu/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        border: false,
        toolbar: [{
            text:'新建',
            iconCls:'icon-add',
            handler:function(){
                $('#system_menu_index_edit_dialog').dialog('open');
            }
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
            {field: 'url', title: '链接', width: 400, align: 'right'},
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
    $('#system_menu_index_edit_dialog').dialog({
                                                  title: '编辑',
                                                  width: 350,
                                                  height: 300,
                                                  closed: true,
                                                  cache: false,
                                                  modal: true,
                                                   buttons: [{
                                                                      text:'保存',
                                                                      iconCls:'icon-save',
                                                                      handler:function(){
                                                                          $.messager.progress();
                                                                          $('#system_menu_index_edit_dialog_form').submit();
                                                                      }
                                                                  }],
                                                   onOpen: function() {
                                                        $('#system_menu_index_edit_dialog_form').form('reset');
                                                   }
                                              });
    $('#system_menu_index_edit_dialog_form').form({
        url: 'rest/system/menu/save',
        iframe: false,
        onSubmit: function(){
            var isValid = $(this).form('validate');
            		if (!isValid){
            			$.messager.progress('close');
            		}
            		return isValid;
        },
        success:function(data){
            $.messager.progress('close');
        }
    });
    $('#system_menu_index_edit_dialog_form_name').textbox({
                                                prompt: '名称',
                                                required:true
                                            });
    $('#system_menu_index_edit_dialog_form_category').combobox({
                                                prompt: '类型',
                                                valueField: 'value',
                                                textField: 'label',
                                                value: 'MENU',
                                                required:true,
                                                editable: false,
                                                panelHeight:'auto',
                                                data: [{
                                                    label: '菜单',
                                                    value: 'MENU'
                                                },{
                                                    label: '导航',
                                                    value: 'NAVIGATION'
                                                },{
                                                    label: '事件',
                                                    value: 'ACTION'
                                                }]
                                            });
    $('#system_menu_index_edit_dialog_form_url').textbox({
                                                prompt: '链接'
                                             });
    $('#system_menu_index_edit_dialog_form_status').combobox({
                                                prompt: '状态',
                                                valueField: 'value',
                                                textField: 'label',
                                                value: '1',
                                                panelHeight:'auto',
                                                editable: false,
                                                required:true,
                                                data: [{
                                                    label: '有效',
                                                    value: '1'
                                                },{
                                                    label: '无效',
                                                    value: '0'
                                                }]
                                            });
    var containId = $('#portal_center').tabs('getSelected')[0].id;
    registryDestroy(containId, 'system_menu_index_edit_dialog');
});