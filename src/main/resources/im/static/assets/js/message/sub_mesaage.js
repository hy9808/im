function sub_message(userId, type) {
    console.log("从别的地方进来了" + userId);
    if (type == 1) {
        //新增聊天
        $("#sub_main").toggle(10);
        $("#msgList").toggle(10);
    }
    sessionStorage.setItem("where_is_my", JSON.stringify(1));//代表进了聊天室
    sessionStorage.setItem("youUserId", userId); //对方id
    sessionStorage.setItem("myHeadImg", JSON.stringify("../../assets/img/message/has-message/系统消息_看图王.png"));
    sessionStorage.setItem("youHeadImg", JSON.stringify("../../assets/img/message/has-message/订单消息.png"));

    let youMsg = JSON.parse(sessionStorage.getItem("youUserId"));
    let msgKey = SUB_FRIEND_MESSAGE + youMsg;
    let locatMsg = JSON.parse(localStorage.getItem(msgKey));
    console.log("里面的KEy" + msgKey);
    if (JSON.parse(sessionStorage.getItem("sub_message_flag" + msgKey)) == null) {
        $.ajax({
            url: httpurl + "/api/queryAllById",
            type: "post",
            data: {recipientId: youMsg},
            dataType: "json",
            beforeSend:
                function (request) {
                    request.setRequestHeader(tokenKey, tokenValue)
                },
            success: function (results) {
                console.log("开始家在数据");
                sessionStorage.setItem("sub_message_flag" + msgKey, JSON.stringify(0));
                if (locatMsg == null) {
                    console.log("加载ajax");
                    //本地没有数据,直接映射,并且做本地缓存
                    localStorage.setItem(msgKey, JSON.stringify(results.result));
                    $.get("/assets/tmp/message/sub_friend_message.lay", function (tpl) {
                        renderTemplate(tpl, "message_context", results.result);
                    });
                } else {
                    console.log("第2次else");
                    //本地有数据,吧数据拼接到本地数据后面
                    locatMsg = Array.from(locatMsg);
                    locatMsg.push(results.result);
                    $.get("/assets/tmp/message/sub_friend_message.lay", function (tpl) {
                        renderTemplate(tpl, "message_context", locatMsg);
                    });
                }
            }
        })

    } else {
        console.log("第三次else");
        //已经加载过数据了,再次进来直接使用本地缓存
        $.get("/assets/tmp/message/sub_friend_message.lay", function (tpl) {
            renderTemplate(tpl, "message_context", locatMsg);
        });
    }

    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            var textContent = $(".post_message").val().replace(/[\n\r]/g, '<br>');
            if (textContent != "") {
                let msgKey = SUB_FRIEND_MESSAGE + youMsg;
                let locatMsg = Array.from(JSON.parse(localStorage.getItem(msgKey)));
                let userId = JSON.parse(sessionStorage.getItem("myUserId"));
                let youId = JSON.parse(sessionStorage.getItem("youUserId"));
                console.log("我要发短信息了userId" + userId + "发给谁：" + youId);
                $(".post_message").val("");
                websocket.send(getMessage(1, 0, null, userId, youId, textContent, null, null));
                layui.use(['form', 'laytpl'], function () {
                    let c = [{
                        userId: userId,
                        msgType: 0,
                        message: textContent,
                        createTime: new Date()
                    }];
                    locatMsg.push({
                        userId: userId,
                        msgType: 0,
                        message: textContent,
                        createTime: new Date()
                    });
                    localStorage.setItem(msgKey, JSON.stringify(locatMsg));
                    var laytpl = layui.laytpl;
                    laytpl($("#page_template_id").html()).render(c, function (html) {
                        $("#chatBox-content-demo").append(html);
                    });
                    layui.form.render();// 渲染
                    try {
                        document.getElementById("chatBox-content-demo").scrollTop = document.getElementById("chatBox-content-demo").scrollHeight
                    } catch (e) {
                        //
                    }
                });
            }
        }
    });


    /**
     * 获取发送出去的消息体
     * @param {Object} type 消息类型     0.连接  1.发送消息  2.接收消息  3.客户端保持心跳 4.群消息
     * @param {Object} msgType 消息类型  0.文字 1.图片,3.视频,
     * @param {Object} userId 发送的id
     * @param {Object} recipientId 接收者id
     * @param {Object} msg 消息
     * @param {Object} msgId 消息id 传null
     * @param {Object} ext 扩展字段 传null
     */
    function getMessage(type, msgType, sysMsgType, userId, recipientId, msg, msgId, ext) {
        return JSON.stringify({
            type: type,
            chatRecord: {
                id: msgId,
                userId: userId,
                msgType: msgType,
                recipientId: recipientId,
                sysMsgType: sysMsgType,
                message: msg,
            },
            ext: ext
        });
    }
};