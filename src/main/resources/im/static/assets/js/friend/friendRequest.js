function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
let id = this;
$().ready(function () {
    var obj = new Object();
    obj = GetRequest();
    id = obj["userFriendId"];
    var name = obj["nickName"];
    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader(tokenKey, tokenValue);
        },
        type: "post",
    })
})

$("#send").click(function () {
    var content = $("#wenbenkuang").val();
    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader(tokenKey, tokenValue);
        },
        type: "post",
        url: httpurl + "/api/chatFriendRequest/addFriend",
        data:{
            id:id,
            content:content,
            nickName:name,
        },
        dataType: "json",
        success: function () {
            window.location.href = "friend.html";
        }
    })
})

var flag = false;
var timer;
var now;

//限制文本框的字数
$("#wenbenkuang").bind("keyup", function () {
    clearTimeout(timer);
    flag = true;
    timer = setTimeout(function () {
        flag = false;
        var content = $('#wenbenkuang').val().substring(0,30);//
        var c = content.substring(0, 30);//
        $('#wenbenkuang').val(c);//该三行写在“input”函数外面也可以
        var a = content.length;
        if (a > 30) {
            alert("您输入字数已上限");
            $("#wenbenkuang").html(content.substring(0, 30));
        }
    }, 500);
});

//限制输入框的字数
$("#shurukuang").bind("keyup", function () {
    clearTimeout(timer);
    flag = true;
    timer = setTimeout(function () {
        flag = false;
        var content = $('#shurukuang').val();//
        var c = content.substring(0, 10);//
        $('#shurukuang').val(c);//该三行写在“input”函数外面也可以
        var a = content.length;
        if (a > 10) {
            alert("您输入字数已上限");
            $("#shurukuang").html(content.substring(0, 10));
        }
    }, 100);
});