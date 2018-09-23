var id = getUrlParam("id");
$(function () {

    window.location.href = 'url?id='+
    // getRole(getUrlParam(id));
});


var role = {};

function getRole(id) {
    $.post("/admin/role/edit", {"id": id}, function (data) {
        console.log(data);
        if (data !== "") {
            user = data;
            $("input[name='id']").val(roel.id);
            $("input[name='createBy']").val(role.createBy);
            $("input[name='createDate']").val(role.createDate);
            $("input[name='name']").val(role.name);
        }
    });
}

/**
 * 获取URL中的参数
 * @param name
 * @returns {*}
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) {
        return unescape(r[2]);
    }
    return null; //返回参数值
}
