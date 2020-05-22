var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//list
		keywords: '',	//检索关键词
		httpurl: '',
		loading:false	//加载状态
    },
    
	methods: {
		
        //初始执行
        init() {
        	$("#keywords").tips({
				side:3,
	            msg:'用户名或姓名',
	            bg:'#AE81FF',
	            time:2
	        });
        	this.httpurl = httpurl;
        },
        
        //获取列表
        getList: function(){
        	this.loading = true;
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'friends/search',
        		data: {fcount:3,keywords:this.keywords,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.loading = false;
        		 }else if ("exception" == data.result){
        			 layer.alert("好友检索异常");//显示异常
                 }
        		}
        	}).done().fail(function(){
        		layer.alert("登录失效! 请求服务器无响应，稍后再试");
                setTimeout(function () {
                	window.location.href = "../login.html";
                }, 2000);
            });
        },
        
        goAdd: function (uid){
			//询问框
			layer.confirm('<font color="black">您是要加他(她)好友吗?</font>', {
			  btn: ['申请','<font color="black">取消</font>'] //按钮
			}, function(){
			  $.ajax({
				  	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'iminterface/addFriends',	//添加好友接口
			    	data: {FUSERNAME:uid,FGROUP_ID:'9999',BZ:'申请加好友',tm:new Date().getTime()},
					dataType:'json',
					success: function(data){
						if('01' == data.result){
							layer.msg('<font color="black">添加成功,等待对方同意</font>', {icon: 1});
						}else{
							layer.msg('<font color="black">您已经添加过他(她)了,无需再添加了</font>', {icon: 2});
						}
					}
				});
			}, function(){
			  
			});
		},
        
		//查看用户资料
		viewUser: function (USERNAME){
			if("admin" == USERNAME){
				layer.alert('不能查看admin用户');
				return;
			}
			layer.open({
			    type: 2,
			    title: '他(她)的资料',
			    shadeClose: true,
			    shade: false,
			    maxmin: false, //开启最大化最小化按钮
			    area: ['300px', '379px'],
			    content: '../user/user_view.html?USERNAME='+USERNAME
			  });
		}

	},
	
	mounted(){
        this.init();
    }
})

//点击好友的头像
layui.use(['layim', 'flow'], function(){
});