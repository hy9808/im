var vm = new Vue({
	el: '#app',
	
	data:{
		USERNAME: '',	//用户名
		PASSWORD: ''	//密码
    },
    
	methods: {
		
		//请求登录
		goLogin: function (){
			
			if (this.USERNAME == '') {
	     		$("#USERNAME").tips({
	     			side : 1,
	     			msg : '用户名不得为空',
	     			bg : '#AE81FF',
	     			time : 3
	     		});
	     		this.$refs.USERNAME.focus();
	     		return false;
              }
              
	         if (this.PASSWORD == '') {
	     		$("#PASSWORD").tips({
	     			side : 1,
	     			msg : '密码不得为空',
	     			bg : '#AE81FF',
	     			time : 3
	     		});
	     		this.$refs.PASSWORD.focus();
	     		return false;
              }
	         
	         $.ajax({
	        	 	xhrFields: {
	                    withCredentials: true
	                },
					type : "POST",
					url : httpurl+'admin/check',
					data : {
						KEYDATA : "qq565263281sy" + this.USERNAME + ",sy," + this.PASSWORD,
						tm : new Date().getTime()
					},
					dataType : 'json',
					success : function(data){
						if("success" == data.result) {
							$("#loginNamePanel").tips({
								side : 1,
								msg : '正在登录 , 请稍后 ...',
								bg : '#68B500',
								time : 10
							});
							window.location.href = "index.html";
						}else if("usererror" == data.result){
							$("#USERNAME").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#USERNAME").focus();
						}else{
							$("#USERNAME").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							$("#USERNAME").focus();
						}
					}
				}).done().fail(function(){
                   $("#loginNamePanel").tips({
						side : 1,
						msg : '登录失败! 请求服务器无响应,稍后再试',
						bg : '#68B500',
						time : 10
					});
                });
			
		},
        
     	//退出登录
        logOut: function (){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'main/logout',
        		data: {tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){}
        	});
        },
		
		//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        },
        
      	//判断登录状态
        islogin: function (){
        	$.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'admin/islogin',
		    	data: {tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                    if ("success" == data.result) {
                    	window.location.href = "index.html";
                    }
                }
			});
        },
        
      	//初始执行
        init() {
        	var msg = this.getUrlKey('msg');	//链接参数
        	if(null != msg){
        		this.logOut();
        	}else{
        		this.islogin();					//判断登录状态
        	}
        }
		
	},
    
    mounted(){
        this.init();
    }

})