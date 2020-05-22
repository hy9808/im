var websocket

var lockReconnect = false;  //避免ws重复连接var lockReconnect = false;  //避免ws重复连接

var socketUserId = null;


function socketInti(wsUrl) {
    websocket = new WebSocket(wsUrl);
    onOpen();
    //检查浏览器是否支持WebSocket
    if (window.WebSocket) {
        console.log('This browser supports WebSocket');
    } else {
        console.log('This browser does not supports WebSocket');
        return;
    }

    sessionStorage.setItem("where_is_my", JSON.stringify(0));//0代表我在聊天框外面 1.代表进去了

    websocket.onopen = function (evt) {
        websocket.send(getMessage(0, 0, null, $("#user").val(), null, null, null, tokenValue));
        // setInterval(function () {
        //     onOpen()
        // }, 1500);
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
    websocket.onerror = function (evt) {
        onError(evt)
    };


    function onOpen() {
        console.log("建立了连接开始心跳:");
        heartCheck.reset().start();
    }

    function onMessage(evt) {
        heartCheck.reset().start();
        var resultMsg = JSON.parse(evt.data);
        let addFlag = JSON.parse(sessionStorage.getItem("where_is_my"));
        switch (resultMsg.type) {
            case WS_OPEN_TYPE:
                socketUserId = resultMsg.chatRecord.userId;
                sessionStorage.setItem("myUserId", JSON.stringify(socketUserId));//储存当前的用户id
                break;
            case WS_CONFIRM_FRIEND_TYPE://朋友给我发信息
                if (addFlag == 0) { //
                    //我在消息列表,修改消息未读数量 和未读信息，还有更新时间，并且聊天记录增加
                    let list = JSON.parse(localStorage.getItem("myFriendAndGroupMsg"));
                    let msg = resultMsg.chatRecord.message;
                    let id = resultMsg.chatRecord.mongoId;
                    let whoId = resultMsg.chatRecord.userId;
                    let whoName = resultMsg.chatRecord.nickName;
                    let whoImg = resultMsg.chatRecord.img;
                    let time = resultMsg.chatRecord.updateTime;
                    var msgData = [{
                        outTime: time,
                        outMsg: msg,
                        nickName: whoName,
                        userId: whoId,
                        id: id,
                        img: whoImg,
                        subMsgType: 2,
                        msgNotReadyCount: 1
                    }];
                    if (list != null && list != []) {
                        let flag = true;
                        for (let i = 0; i < list.length; i++) {
                            //当前已有的消息列表
                            if (list[i].userId === whoId) {
                                //如果消息已有的消息列表匹配到了消息
                                let msgId = MESSAGE_FRIEND_ID + whoId;
                                msgAuto(msgId, whoImg, whoName, msg, time);

                                //全部都替换完毕后 将修改一下数据存起来
                                list[i].outTime = time;
                                list[i].outMsg = msg;
                                list[i].nickName = whoName;
                                list[i].img = whoImg;
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            list.push({
                                outTime: time,
                                outMsg: msg,
                                nickName: whoName,
                                userId: whoId,
                                id: id,
                                img: whoImg,
                                subMsgType: 2,
                                msgNotReadyCount: 1
                            });

                            // 匹配不到消息就新增一条,并且把缓存的数据也新增一条
                            localStorage.setItem("myFriendAndGroupMsg", JSON.stringify(list));
                            $(".not-msg-div").hide();
                            $("#main").css("background-color", "#eeeeee");
                            $.get("/assets/tmp/message/friend_message.lay", function (tpl) {
                                renderTemplate(tpl, "my_message", list);
                            });
                        }

                    } else {
                        //没有任何数据 增加列表
                        localStorage.setItem("myFriendAndGroupMsg", JSON.stringify(msgData));
                        $(".not-msg-div").hide();
                        $("#main").css("background-color", "#eeeeee");
                        $.get("/assets/tmp/message/friend_message.lay", function (tpl) {
                            renderTemplate(tpl, "my_message", msgData);
                        });
                    }

                } else {
                    //当前在消息里面
                    console.log("消息里面渲染开始");
                    layui.use(['form', 'laytpl'], function () {
                        let c = [{
                            userId: resultMsg.chatRecord.userId,
                            msgType: resultMsg.chatRecord.msgType,
                            message: resultMsg.chatRecord.message,
                            createTime: resultMsg.chatRecord.createTime
                        }];
                        console.log(c);
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
                //本地储存消息
                let msgKey = SUB_FRIEND_MESSAGE + resultMsg.chatRecord.userId;
                console.log(msgKey);
                let locatMsg = localStorage.getItem(msgKey);
                if (locatMsg != null) {
                    locatMsg = Array.from(JSON.parse(locatMsg));
                    let d = {
                        userId: resultMsg.chatRecord.userId,
                        msgType: resultMsg.chatRecord.msgType,
                        message: resultMsg.chatRecord.message,
                        createTime: resultMsg.chatRecord.createTime
                    };
                    if (locatMsg != null) {
                        locatMsg.push(d)
                    } else {
                        locatMsg = d;
                    }
                    localStorage.setItem(msgKey, JSON.stringify(locatMsg));
                }

                break;
            case WS_CONFIRM_GROUP_TYPE:
                break;
            case WS_SEND_FRIEND_TYPE:
                break;
            case WS_SEND_GROUP_TYPE:
                break;
            case WS_JUMP_TYPE:
                console.log("心跳检测回复啦");
                break;
        }


    }

    function writeToScreen(message) {
        layer.msg(message, {icon: 6});
    }


    function onClose(evt) {

    }

    function onError(evt) {

    }

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }

    /**
     *
     * @param msgId 具体那条消息
     * @param whoImg 消息头像
     * @param whoName 消息名字
     * @param msg  具体消息
     * @param time 更新时间
     */
    function msgAuto(msgId, whoImg, whoName, msg, time) {
        let count = null;
        let num = 0;
        let $1 = $(document.getElementById(msgId).getElementsByClassName("layui-badge"));
        $($1[0]).show();
        $(document.getElementById(msgId).getElementsByClassName("layui-badge")[0]).removeAttr("style");
        $(document.getElementById(msgId).getElementsByClassName("sub-message")[0]).show();
        $(document.getElementById(msgId).getElementsByClassName("sub-message")[0]).removeAttr("style");
        count = $(document.getElementById(msgId).getElementsByClassName("layui-badge")[0]).text();
        $(document.getElementById(msgId).getElementsByClassName("layui-badge")[0]).text(count == null || count === '' ? ++num : ++count);
        $(document.getElementById(msgId).getElementsByClassName("sub-message")[0]).text("[" + (count == null || count === '' ? num : count) + "条]")
        //消息处理，时间处理

        $(document.getElementById(msgId).getElementsByClassName("sub-message")[1]).text(msg);

        let nowImg = $(document.getElementById(msgId).getElementsByClassName("head_img")).attr("src");
        if (nowImg === whoImg) {
            //相同啥也不做
        } else {
            //不相同替换
            $(document.getElementById(msgId).getElementsByClassName("head_img")).attr("src", whoImg);
        }

        $(document.getElementById(msgId).getElementsByClassName("time_span")).text(layui.util.toDateString(time, 'MM-dd HH:mm'));


        // form.render();
    }


}

function reconnect() {
    if (lockReconnect) return;
    lockReconnect = true;
    setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
        socketInti(wsUrl);
        lockReconnect = false;
    }, 2000);
}


//心跳检测
var heartCheck = {
    timeout: 8500,
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function () {
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        return this;
    },
    start: function () {
        var self = this;
        this.timeoutObj = setTimeout(function () {
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            var str = socketUserId + "心跳了❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥";
            console.log('建立心跳');
            websocket.send(getMessage(WS_JUMP_TYPE, null, 0, socketUserId, null, str, null, null));
            self.serverTimeoutObj = setTimeout(function () {//如果超过一定时间还没重置，说明后端主动断开了
                websocket.close();     //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
            }, self.timeout)
        }, this.timeout)
    }
}


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