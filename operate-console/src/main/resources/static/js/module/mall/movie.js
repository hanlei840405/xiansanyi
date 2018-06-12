$(function () {
    $('#mall_movie_index_dataGrid').datagrid({
        url: 'rest/mall/movie/page',
        method: 'GET',
        rownumbers: true,
        pagination: true,
        idField: 'id',
        fit: true,
        border: false,
        toolbar: [{
            text: '查看',
            iconCls: 'icon-edit',
            handler: function () {
                var selected = $('#mall_movie_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $('#mall_movie_index_view_dialog').dialog('open');
                } else {
                    $.messager.alert('警告', '请选择一条记录进行编辑');
                }
            }
        }, '-', {
            text: '取消',
            iconCls: 'icon-remove',
            handler: function () {
                var selected = $('#mall_movie_index_dataGrid').datagrid('getSelected');
                if (selected) {
                    $.messager.confirm('确认', '是否删除该条数据', function (r) {
                        if (r) {
                            $.messager.progress();
                            $.ajax({
                                url: 'rest/mall/movie/delete',
                                data: {id: selected.id},
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    var token = $("meta[name='_csrf']").attr("content");
                                    var header = $("meta[name='_csrf_header']").attr("content");
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (res) {
                                    if (res == 200) {
                                        $('#mall_movie_index_view_dialog').dialog('close');
                                        $('#mall_movie_index_dataGrid').datagrid('reload');
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
            {field: 'code', title: '编号', width: 170, align: 'right'},
            {field: 'location', title: '地址', width: 300, align: 'right'},
            {field: 'cinema', title: '影院', width: 170, align: 'right'},
            {field: 'movie', title: '片名', width: 170, align: 'right'},
            {field: 'buyer', title: '买家', width: 170, align: 'right'},
            {field: 'mobile', title: '电话', width: 170, align: 'right'},
            {field: 'seller', title: '卖家', width: 170, align: 'right'}
        ]],
        rowStyler: function (index, row) {
            if (row.status == '0') {
                return 'background-color:#6293BB;color:#fff;';
            }
        },
        onLoadSuccess: function () {
            $('#mall_movie_index_dataGrid').datagrid('clearSelections');
        }
    });
    $('#mall_movie_index_view_dialog').dialog({
        title: '影票',
        width: 750,
        height: 500,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '关闭',
            iconCls: 'icon-save',
            handler: function () {
                $('#mall_movie_index_view_dialog').dialog('close');
            }
        }],
        onOpen: function () {
            var selected = $('#mall_movie_index_dataGrid').datagrid('getSelected');
            var title = selected.code;
            if (selected.status == '0') {
                title = selected.code + '(取消)';
            }
            $('#mall_movie_index_view_dialog').dialog('setTitle', title);
            $('#mall_movie_index_view_dialog_form').form('reset');
            $('#mall_movie_index_view_dialog_form').form('load', 'rest/mall/movie/id?id=' + selected.id);
        }
    });

    $('#mall_movie_index_view_dialog_form_location').textbox({
        label: '地址',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_cinema').textbox({
        label: '影院',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_movie').textbox({
        label: '影片',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_time').textbox({
        label: '上映时间',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_buyer').textbox({
        label: '买家',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_mobile').textbox({
        label: '买家电话',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_price').numberbox({
        label: '价格',
        labelWidth: 120,
        readonly: true,
        min:0,
        precision:2,
        prefix:'￥',
        parser: function(value) {
            return value / 100;
        },
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_discount').numberbox({
        label: '折扣',
        labelWidth: 120,
        readonly: true,
        min:0,
        precision:2,
        prefix:'￥',
        parser: function(value) {
            return value / 100;
        },
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_fee').numberbox({
        label: '服务费',
        labelWidth: 120,
        readonly: true,
        min:0,
        precision:2,
        prefix:'￥',
        parser: function(value) {
            return value / 100;
        },
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_paid').textbox({
        label: '支付时间',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_sent2seller').textbox({
        label: '订单推送卖家时间',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_seller').textbox({
        label: '卖家',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_sent2buyer').textbox({
        label: '订单反馈买家时间',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_created').textbox({
        label: '订单创建时间',
        labelWidth: 120,
        readonly: true,
        width: 360
    });

    $('#mall_movie_index_view_dialog_form_payload').textbox({
        label: '卖家发货留言',
        labelWidth: 120,
        readonly: true,
        width: 720
    });

    registryDestroy(['mall_movie_index_view_dialog']);
});