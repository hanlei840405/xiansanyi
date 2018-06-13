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
            {field: 'code', title: '编号', width: 150, align: 'right'},
            {field: 'location', title: '地址', width: 300, align: 'right'},
            {field: 'cinema', title: '影院', width: 145, align: 'right'},
            {field: 'movie', title: '片名', width: 145, align: 'right'},
            {field: 'buyer', title: '买家', width: 145, align: 'right'},
            {field: 'mobile', title: '电话', width: 145, align: 'right'},
            {field: 'seller', title: '卖家', width: 145, align: 'right'},
            {field: 'status', title: '状态', width: 140, align: 'right', formatter: function(value){
                if(value == '0') {
                    return '取消';
                } else if(value == '1') {
                    return '新建待支付';
                } else if(value == '2') {
                    return '已支付';
                } else if(value == '3') {
                    return '平台推送卖家';
                } else if(value == '4') {
                    return '卖家签收';
                } else if(value == '5') {
                    return '卖家推送给平台';
                } else if(value == '6') {
                    return '待平台审核';
                } else if(value == '7') {
                    return '平台推送给买家';
                }else {
                    return '完成';
                }
            }}
        ]],
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