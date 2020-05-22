var app = new Vue({
    el: '#app',
    data: {
        flag: "false",
    },
    methods: {},
})
$(function () {
    // $(".exist").hide();
})

$("#comfirmphone").click(function () {
    var account = $("#phone-text").val();
    console.log(account);
    $.ajax({
        type: "post",
        url: httpurl + "/api/selectByAccount?account=" + account,
        dataType: 'json',
        success: function (data) {
            console.log(data.result)
            var id = data.result;
            if (id != null) {
                window.location.href = "../friend/persondetail.html?userFriendId=" + id;
            } else {

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

