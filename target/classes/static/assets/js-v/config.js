//服务器host
var baseUrl = "127.0.0.1";
var httpurl = 'http://' + baseUrl + ':8088';
var wsOut = ":8089/ws";
var wsUrl = "ws://" + baseUrl + wsOut  //ws://IP:port/ws

//消息状态
var WS_OPEN_TYPE = 0;
var WS_SEND_FRIEND_TYPE = 1;
var WS_CONFIRM_FRIEND_TYPE = 2;
var WS_JUMP_TYPE = 3;
var WS_SEND_GROUP_TYPE = 4;
var WS_CONFIRM_GROUP_TYPE = 5;

//自定义元素id
//外部消息列表
var MESSAGE_FRIEND_ID = "new_friend_msg";
var MESSAGE_GROUP_ID = "new_group_msg";
//具体哪个消息的前缀
var SUB_FRIEND_MESSAGE = "sub_friend_message_";
var SUB_GROUP_MESSAGE = "sub_group_message_";

//测试用的token
var tokenKey = "Authorization";
var tokenValue = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyIjoie1wiYWNjb3VudE5vbkV4cGlyZWRcIjp0cnVlLFwiYWNjb3VudE5vbkxvY2tlZFwiOnRydWUsXCJhdXRob3JpdGllc1wiOlt7XCJhdXRob3JpdHlcIjpcIuWIneWni-S8muWRmOihqFwifSx7XCJhdXRob3JpdHlcIjpcIlZJUOS8muWRmFwifV0sXCJjcmVhdGVUaW1lXCI6MTU4NjQyMDgxNjAwMCxcImNyZWRlbnRpYWxzTm9uRXhwaXJlZFwiOnRydWUsXCJlbmFibGVkXCI6dHJ1ZSxcImlkXCI6MzUsXCJsaW5rc1wiOlt7XCJpZFwiOjMsXCJtZXRob2RcIjpcIjBcIn0se1wiaWRcIjoyLFwibWV0aG9kXCI6XCIwXCJ9LHtcImlkXCI6NCxcIm1ldGhvZFwiOlwiMFwifSx7XCJpZFwiOjUsXCJtZXRob2RcIjpcIjBcIn0se1wiaWRcIjo2LFwibWV0aG9kXCI6XCIwXCJ9LHtcImlkXCI6NyxcIm1ldGhvZFwiOlwiMFwifV0sXCJwYXNzd29yZFwiOlwiJDJhJDEwJGZZZHdhWjdka2plMmd5Q0RFUXoxdmVPOFF1Snk0NDFGZkpMUmFWVTZlb2MweGYvYmt3YUpHXCIsXCJ1c2VyQWxpYXNcIjpcIuW8oHRocmVlXCIsXCJ1c2VySW1nXCI6XCJcIixcInVzZXJuYW1lXCI6XCJ6czMzXCJ9IiwianRpIjoiWmpabU9EZ3dNVFV0TXpZellpMDBOR1k1TFRoaE5tTXRPREl5TjJabE5ERTBaRFV6IiwiZXhwIjoxNTg4MTQ4MjgzfQ.A48TP1CWue1GLZW5-t63FZMAd2HxstFpFRmaqnzbdqyXcl7aVXSj1QyfBlLFP4th1WqUv88x0c2-EeFtiKmp-1-o3kQT6hSeNtJC82YGokTPIEfVfsz3pP8WRc7lKefDmZwOYO8oO8WAzma0LjqSIk7wnVs5EtlogMko-0Ylw71bjcNyB9wyAKutrIHhDJHqyje9YME36hd_d-QUjRkPgzzQlDrsl1WDO9DcGu1785ad0ACbuYFbESWyfRNOEHsLmRSw7UDCdlicjsp8RslpnUOT2nxR1IGc-5jUitYPA_Vd9NkW5BnUIPvdvI4kQGq_OxYmsmGHZLjAA-tbqzkCtg";//显示异常
function showException(modular, exception) {
    swal("[" + modular + "]程序异常!", "抱歉！您访问的页面出现异常，请稍后重试或联系管理员 " + exception, "warning");
}

var form;
layui.use('form', function () {
    form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
    form.render();
});
