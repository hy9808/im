
var fwebsocket;	//websocket对象

var vm = new Vue({
	el: '#app',
	
	data:{
		user: '',			//用于即时通讯（ 当前登录用户）
		userPhoto: '',		//用户头像
		NAME: '',			//姓名
		fhsmsCount: 0,		//站内信未读数量
		taskCount: 0,		//待办任务
		indexActive: true,		//底部菜单选中状态(首页)
		fhsmsActive: false,		//底部菜单选中状态(站内信)
		myimActive: false,		//底部菜单选中状态(我的通讯)
		usercenterActive: false//底部菜单选中状态(用不中心)
    },
    
    mixins: [vonline],			//混入模块
    
	methods: {
        //初始执行
        init() {
        	this.goIndex();
        	
        	var FMSG = this.getUrlKey('FMSG');
        	if('usercenter' == FMSG){
        		this.indexActive = false;
        		this.fhsmsActive = false;
        		this.myimActive = false;
        		this.usercenterActive = true;
        	}else if('myim' == FMSG){
        		this.indexActive = false;
        		this.fhsmsActive = false;
        		this.myimActive = true;
        		this.usercenterActive = false;
        	}else if('fhsms' == FMSG){
        		this.indexActive = false;
        		this.fhsmsActive = true;
        		this.myimActive = false;
        		this.usercenterActive = false;
        	}
        	isTwolevel = false;
        },
        
        //进入首页
        goIndex: function(){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
            	type: "POST",
            	url: httpurl+'main/index?tm='+new Date().getTime(),
            	data: encodeURI(""),
            	dataType:'json',
            	success: function(data){
            		if('success' == data.result){
            			vm.getNowUser();
            		}else if('errer' == data.result){
            			alert('登录失效! 请求服务器无响应,稍后再试');
            			vm.logOut();
            		}else if('exception' == data.result){
            			alert('后台程序异常');
            		}
            	}
            }).done().fail(function(){
                alert('登录失效! 请求服务器无响应,稍后再试');
                vm.logOut();
            });
        },
        
        //基本信息
        getNowUser: function(){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
            	type: "POST",
            	url: httpurl+'head/getInfo?tm='+new Date().getTime(),
            	data: encodeURI(""),
            	dataType:'json',
            	success: function(data){
            		if('success' == data.result){
            			vm.user = data.USERNAME;						//用户名
                		vm.NAME = data.NAME;							//姓名
                		vm.fhsmsCount = data.fhsmsCount;				//站内信未读数量
                		vm.userPhoto = httpurl+data.userPhoto;			//用户头像
                		vm.getTaskCount();								//获取待办任务数量
                		vm.getDataToOnline();
            		}else if('errer' == data.result){
            			alert('登录失效! 请求服务器无响应,稍后再试');
            			vm.logOut();
            		}else if('exception' == data.result){
            			alert('后台程序异常');
            		}
            	}
            }).done().fail(function(){
                alert('登录失效! 请求服务器无响应,稍后再试');
                vm.logOut();
            });
        },

        //获取待办任务数量
        getTaskCount: function (){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'rutask/getTaskCount?tm='+new Date().getTime(), //待办任务数量
            	data: encodeURI(""),
        		dataType:'json',
        		success: function(data){
        			if('success' == data.result){
        				 vm.taskCount = Number(data.taskCount);
            			 if(vm.taskCount > 0){
            				 $("#taskCount").tips({
            						side:3,
            			            msg:'有待办任务',
            			            bg:'#AE81FF',
            			            time:16
            			     });
            			 }
            		}else if('exception' == data.result){
            			alert('后台程序异常');
            		}
        		}
        	});
        },
        
        //跳转到登录页面
        goLoginPage: function (){
        	if(isTwolevel){
				window.location.href = "../login.html";
			}else{
				window.location.href = "login.html";
			}
        },
        
        //根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        }
		
	},
	
	mounted(){
        this.init();
    }

})
