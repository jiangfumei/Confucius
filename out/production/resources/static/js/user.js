$(function () {
        console.log(getUrlParam('id'));
        getUser(getUrlParam('id'));
    }
);


var user = {};

function getUser(id) {
    $.post("/admin/user/edit", {"id": id}, function (data) {
        console.log(data);
        if (data !== "") {
            user = data;
            $("input[name='id']").val(user.id);
            $("input[name='createBy']").val(user.createBy);
            $("input[name='createDate']").val(user.createDate);
            $("input[name='username']").val(user.username);
            $("input[name='trueName']").val(user.trueName);
            $("input[name='password']").val(user.password);
            $("input[name='updateDate']").val(user.detail);
        }
    });
}

/*
{
    "createBy" : 0,
    "createDate" : null,
    "updateBy" : 0,
    "updateDate" : null,
    "status" : null,
    "id" : 1,
    "username" : "admin",
    "password" : "admin",
    "trueName" : null,
    "detail" : null,
    "roleName" : null,
    "roles" : [ ],
    "enabled" : true,
    "authorities" : [ ],
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "accountNonExpired" : true
}*/

/*
<input type="hidden" name="id" th:value="${user.id}"/>
    <input type="hidden" name="password" th:value="${user.password}"/>
    <input type="hidden" name="createBy" th:value="${user.createBy}"/>
    <input type="hidden???" name="createDate" th:value="${user.createDate}"/>*/
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
