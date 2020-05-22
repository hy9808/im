var parentsdiv = new Vue({
    el: '#app',
    data: {
        vo: {},
    },
    methods: {
        add(e1,e2) {
            window.location.href = "friendRequest.html?userFriendId=" + e1 + "&nickName=" + e2;
        },
        send(e) {
            localStorage.setItem("goFriend", JSON.stringify(GetRequest()));
            window.location.href = "../message/my_message.html";
        },
    },
})

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
    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader(tokenKey, tokenValue);
        },
        type: "post",
        url: httpurl + "/api/chatFriendRequest/selectFriendDetail?id=" + id,
        dataType: "json",
        success: function (data) {
            parentsdiv.vo = data.result;
            console.log(parentsdiv.vo);
        }
    })

   /* $("#sendMessage").click(function () {
        localStorage.setItem("goFriend", JSON.stringify(GetRequest()));
        window.location.href = "../message/my_message.html";
    })
    $("#addFriend").click(function () {
        var userFriendId = parentsdiv.vo.userId;
        var nickName = parentsdiv.vo.nickName;
        window.location.href = "friendRequest.html?userFriendId=" + userFriendId + "&nickName=" + nickName;
    })*/
})

// $("#sendMessage").on("click", function () {
//     localStorage.setItem("goFriend", JSON.stringify(GetRequest()));
//     window.location.href = "../message/my_message.html";
//
// });

