var initSysFlag = sessionStorage.getItem("initSysFlag"); //系统消息数据初始化开关
var initUserFlag = sessionStorage.getItem("initUserFlag"); //用户消息数据初始化开关

$(document).ready(function () {
    $("#sub_main").toggle(10);
    // $("#main").toggle(10);
    init();//初始化聊天信息
    socketInti(wsUrl);//初始化连接
    if (localStorage.getItem("goFriend") != null) {
        sub_message(JSON.parse(localStorage.getItem("goFriend")).userFriendId, 1);
    }

    $("#remove_sub_message").on("click", function () {
        $("#sub_main").toggle(10);
        $("#msgList").toggle(10);
        sessionStorage.setItem("where_is_my", JSON.stringify(0));
    });
});

function init() {
    //默认是没有消息的，打开没有消息的页面
    $(".not-msg-div").show();
    //系统消息 start
    if (initSysFlag == null) {
        $.ajax({
            url: httpurl + "/api/queryByIdCountList",
            beforeSend:
                function (request) {
                    request.setRequestHeader(tokenKey, tokenValue)
                },
            type: "post",
            dataType: "json",
            success: function (results) {
                if (results.result.sys.length > 0) {
                    $(".not-msg-div").hide();
                    $("#main").css("background-color", "#eeeeee");
                    for (let i = 0; i < results.result.sys.length; i++) {
                        if (results.result.sys[i].sysMsgType == 0 || results.result.sys[i].sysMsgType == 1) {
                            if (results.result.sys[i].count > 0) {
                                localStorage.setItem("sysMsg", JSON.stringify(results.result.sys));
                            }
                        }
                    }
                    $.get("/assets/tmp/message/sys_message.lay", function (tpl) {
                        renderTemplate(tpl, "sys_msg_tmp", JSON.parse(localStorage.getItem("sysMsg")));
                    });
                    sessionStorage.setItem("initSysFlag", 0);
                } else {
                    $("#main").removeAttr("style", "");
                }
            }
        });
    } else {
        let data = JSON.parse(localStorage.getItem("sysMsg"));
        if (data != null) {
            $(".not-msg-div").hide();
            $("#main").css("background-color", "#eeeeee");
            $.get("/assets/tmp/message/sys_message.lay", function (tpl) {
                renderTemplate(tpl, "sys_msg_tmp", data);
            });
        } else {
            $("#main").removeAttr("style", "");
        }
    }
    //系统消息 end

    //好友，群消息 start
    if (initUserFlag == null) {

        $.ajax({
            url: httpurl + "/api/queryByUserIdGetMsgList",
            dataType: "json",
            type: "post",
            beforeSend:
                function (request) {
                    request.setRequestHeader(tokenKey, tokenValue)
                },
            success: function (results) {
                if (results.result.length > 0) {
                    $(".not-msg-div").hide();
                    $("#main").css("background-color", "#eeeeee");
                    let json = [];
                    for (let i = 0; i < results.result.length; i++) {
                        if (results.result[i].msgNotReadyCount > 0) {
                            json.push(results.result[i]);
                        }
                    }
                    localStorage.setItem("myFriendAndGroupMsg", JSON.stringify(json));
                    $.get("/assets/tmp/message/friend_message.lay", function (tpl) {
                        renderTemplate(tpl, "my_message", JSON.parse(localStorage.getItem("myFriendAndGroupMsg")));
                    });
                    sessionStorage.setItem("initUserFlag", 0);
                } else {
                    $("#main").removeAttr("style", "");
                }
            }
        });
    } else {
        let data = JSON.parse(localStorage.getItem("myFriendAndGroupMsg"));
        if (data != null) {
            $(".not-msg-div").hide();
            $("#main").css("background-color", "#eeeeee");
            $.get("/assets/tmp/message/friend_message.lay", function (tpl) {
                renderTemplate(tpl, "my_message", data);
            });
        } else {
            $("#main").removeAttr("style", "");
        }
    }


}

/**
 * @param id MongoDB的id
 * @param userId 数据库的userId
 * @param type
 * @param c
 */
function clickMsg(id, type, c, userId) {
    //消息被点击了，进入消息body的同时取消红点
    $("#sub_main").toggle(10);
    $("#msgList").toggle(10);
    let json = null;
    switch (type) {
        case 0:
            json = JSON.parse(localStorage.getItem("sysMsg"));
            break;
        case 1:
            json = JSON.parse(localStorage.getItem("sysMsg"));
            break;
        case 2:
            //好友消息
            sub_message(userId, 0);
            json = JSON.parse(localStorage.getItem("myFriendAndGroupMsg"));
            break;
        case 3:
            //群聊消息
            json = JSON.parse(localStorage.getItem("myFriendAndGroupMsg"));
            break;
    }

    //消息已读处理 1.将客户端的读取状态修改 然后服务端修改数据库
    let count;
    if (json != null) {
        for (let i = 0; i < json.length; i++) {
            if (type == 0 || type == 1) {
                if (json[i].id == id) {
                    count = json[i].count;
                    json[i].count = 0;
                    localStorage.setItem("sysMsg", JSON.stringify(json));
                }
            } else {
                //好友消息
                json[i].msgNotReadyCount = 0;
                localStorage.setItem("myFriendAndGroupMsg", JSON.stringify(json))
            }
            $(c.getElementsByClassName("layui-badge")[0]).text(0);
            $(c.getElementsByClassName("sub-message")[0]).text('[' + 0 + '条]');
            $(c.getElementsByClassName("layui-badge")[0]).hide();
            $(c.getElementsByClassName("layui-badge")[0]).css('position', 'absolute');
            $(c.getElementsByClassName("sub-message")[0]).hide();
            $(c.getElementsByClassName("sub-message")[0]).css('position', 'absolute');

            //并且修改数据库的已读状态
        }


        if (id != null) {
            $.ajax({
                url: httpurl + "/api/updateSysMsgType",
                type: "post",
                beforeSend:
                    function (request) {
                        request.setRequestHeader(tokenKey, tokenValue)
                    },
                dataType: "json",
                data: {id: id, readCount: count, type: type},
                success: function (result) {
                    //
                }
            })
        }

    }
}


/**
 * 模板的渲染方法
 * @param tmp 需要渲染的模板
 * @param resultContentId 模板渲染后显示在页面的内容的容器id
 * @param data json对象
 */
function renderTemplate(tmp, resultContentId, data) {
    layui.use(['form', 'laytpl'], function () {
        var laytpl = layui.laytpl;
        laytpl(tmp).render(data, function (html) {
            $("#" + resultContentId).html(html);
        });
    });
    layui.form.render();// 渲染
    try {
        document.getElementById("chatBox-content-demo").scrollTop = document.getElementById("chatBox-content-demo").scrollHeight
    } catch (e) {
        //
    }
};

//2020-04-17T18:36:37.948
function convertDateFromString(dateString) {
    if (dateString) {
        var sdate = dateString.split('T')[0].split("-")
        var sTime = dateString.split('T')[1].split(":")
        var date = new Date(sdate[0], sdate[1] - 1, sdate[2], sTime[0], sTime[1], sTime[2]);
        return date.Format("yyyy-MM-dd hh:mm:ss.S");
    }
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": '' //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
