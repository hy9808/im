var app = new Vue({
    el: '#app',
    data: {

    },
    methods: {},
})

$("#comfirmphone").click(function () {
    var account = $("#phone-text").val();
    console.log(account);
    $.ajax({
        type: "post",
        url: httpurl + "/api/selectByAccount?account=" + account,
        dataType: 'json',
        success: function (data) {
            var userFriendId = data.result;
            console.log(data.result);
            if (userFriendId != null) {
                window.location.href = "../friend/persondetail.html?userFriendId=" + userFriendId;
            } else {
                alert("未找到该用户，请确认后重新输入");
            }
        }
    })

    // $("#openPhone").click(function () {
    //     $.ajax({
    //         type: "post",
    //         url: httpurl +'api/getDialDistory?'+
    //     })
    // })
})

