/**
 * Created by hanlei24 on 2017/6/12.
 */
var destroyUI = {};
$(function () {
    $('#menu').tree({
        url: 'menus',
        method: 'GET',
        onClick: function (node) {
            if (node.attributes && node.attributes.url && node.attributes.url != null && node.attributes.url != '') {
                if ($('#center').tabs('exists', node.id)) {
                    $('#center').tabs('select', node.id);
                } else {
                    $('#center').tabs('add', {
                        id: node.id,
                        title: node.text,
                        href: node.attributes.url,
                        closable: true,
                        bodyCls: 'content-doc',
                        border: false
                    });
                    destroyUI[node.id] = [];
                }
            }
        }
    });

    $('#center').tabs({
        border: false,
        onBeforeClose: function () {
            debugger;
            var containId = $('#center').tabs('getSelected')[0].id;
            for (var i = 0;i < destroyUI[containId].length; i++) {
                var componentId = destroyUI[containId][i];
                $('#' + componentId).panel('destroy');
            }
        }
    });
});
function registryDestroy(containId, componentId) {
    if (destroyUI[containId].indexOf(componentId) == -1) {
        destroyUI[containId].push(componentId);
    }
}

function formatDatetime(value, format) {
    var customerDate = new Date(value);
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+": customerDate.getMonth() + 1, // month
        "d+": customerDate.getDate(), // day
        "h+": customerDate.getHours(), // hour
        "m+": customerDate.getMinutes(), // minute
        "s+": customerDate.getSeconds(), // second
        "q+": Math.floor((customerDate.getMonth() + 3) / 3), // quarter
        "S": customerDate.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (customerDate.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}