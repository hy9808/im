var parentsdiv = new Vue({
    el: '#app',
    data: {
        vo: {},
    },
    methods: {
        link(e) {
            alert(e);
        }
    },
})

let _flag = this;

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

$().ready(function () {
    var userId = new Object();
    userId = GetRequest();
    var id = userId["userFriendId"];
    console.log(id);
    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader(tokenKey, tokenValue);
        },
        type: "post",
        url: httpurl + "/api/chatFriendRequest/selectFriendDetail?id=" + id,
        dataType: "json",
        success: function (data) {
            console.log(data);
            parentsdiv.vo = data.result;
            _flag = data.result.flag;
            console.log(_aflag);
        }

    })
    if (_flag != 1) {
        console.log(_flag);
        $("#sendMessage").html("添加好友");
        $("#sendMessage").click(function () {
            var userFriendId = parentsdiv.vo.userFriendId;
            var nickName = parentsdiv.vo.nickName;
            window.location.href = "friendRequest.html?id=" + userFriendId + "&nickName=" + nickName;
        })
    } else {
        window.location.href = "../message/my_message.html";
    }
})

// $("#sendMessage").on("click", function () {
//     localStorage.setItem("goFriend", JSON.stringify(GetRequest()));
//     window.location.href = "../message/my_message.html";
//
// });

