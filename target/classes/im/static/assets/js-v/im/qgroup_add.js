var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//list
		keywords: '',	//检索关键词
		httpurl: '',
		loading:false			//加载状态
    },
    
	methods: {
		
        //初始执行
        init() {
        	$("#keywords").tips({
				side:3,
	            msg:'群名',
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
        		url: httpurl+'qgroup/search',
        		data: {fcount:3,keywords:this.keywords,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.loading = false;
        		 }else if ("exception" == data.result){
                 	layer.alert("群检索异常");//显示异常
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
			layer.confirm('<font color="black">您是要申请加群吗?</font>', {
			  btn: ['申请','<font color="black">取消</font>'] //按钮
			}, function(){
			  $.ajax({
				  	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'iminterface/addQGroup',	//添加群接口
			    	data: {QGROUP_ID:uid,BZ:'申请加群',tm:new Date().getTime()},
					dataType:'json',
					success: function(data){
						if('01' == data.result){
							layer.msg('<font color="black">添加成功,等待群主同意</font>', {icon: 1});
						}else{
							layer.msg('<font color="black">您已经在此群了,无需再添加了</font>', {icon: 2});
						}
					}
				});
			}, function(){
			  
			});
		}
        
	},
	
	mounted(){
        this.init();
    }
})

layui.use(['layim', 'flow'], function(){
});