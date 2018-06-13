/**
 * Created by hanlei24 on 2017/6/12.
 */
var destroyUI = {};
$(function () {
    $('#portal_menu').tree({
        url: 'rest/system/menu/privileges',
        method: 'GET',
        animate: true,
        onClick: function (node) {
            if (node.attributes && node.attributes.url && node.attributes.url != null && node.attributes.url != '') {
                debugger;
                if ($('#portal_center').tabs('exists', node.text)) {
                    $('#portal_center').tabs('select', node.text);
                } else {
                    $('#portal_center').tabs('add', {
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

    $('#portal_center').tabs({
        border: false,
        onBeforeClose: function () {
            var containId = $('#portal_center').tabs('getSelected')[0].id;
            for (var i = 0;i < destroyUI[containId].length; i++) {
                var componentId = destroyUI[containId][i];
                $('#' + componentId).panel('destroy');
            }
        }
    });
});
function registryDestroy(components) {
    var containId = $('#portal_center').tabs('getSelected')[0].id;
    for (var i = 0; i < components.length; i++) {
        if (destroyUI[containId].indexOf(components[i]) == -1) {
            destroyUI[containId].push(components[i]);
        }
    }
}

function destroyComponents(componentId) {
    var containId = $('#portal_center').tabs('getSelected')[0].id;
    for (var i = 0;i < destroyUI[containId].length; i++) {
        var comp = destroyUI[containId][i];
        if (comp == componentId)  {
            $('#' + componentId).panel('destroy');
        }
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