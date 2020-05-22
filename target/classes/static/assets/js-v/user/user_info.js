var vm = new Vue({
	el: '#app',
	
	data:{
		USER_ID: '',	//ID
		ROLE_ID: '',	//角色ID
		USERNAME: '',	//用户名
		NUMBER: '',		//编码
		ROLE_IDS: '',	//副职角色
		NAME: '',		//姓名
		PASSWORD: '',	//密码
		chkpwd: '',		//确认密码
		PHONE: '',		//手机
		EMAIL: '',		//邮箱
		BZ: ''			//备注
    },
    
	methods: {
		
		//初始执行
        init() {
        	this.getData();
        },
        
        goSave: function (){
        	if('' == this.NAME){
        		$("#NAME").tips({
        			side:3,
                    msg:'输入姓名',
                    bg:'#AE81FF',
                    time:3
                });
        		$("#NAME").focus();
        		return false;
        	}
        	if('' == this.USER_ID && '' == this.PASSWORD){
        		$("#PASSWORD").tips({
        			side:3,
                    msg:'输入密码',
                    bg:'#AE81FF',
                    time:2
                });
        		$("#PASSWORD").focus();
        		return false;
        	}
        	if(this.PASSWORD != this.chkpwd){
        		$("#chkpwd").tips({
        			side:3,
                    msg:'两次密码不相同',
                    bg:'#AE81FF',
                    time:3
                });
        		$("#chkpwd").focus();
        		return false;
        	}
        	var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
        	if('' == this.PHONE){
        		$("#PHONE").tips({
        			side:3,
                    msg:'输入手机号',
                    bg:'#AE81FF',
                    time:3
                });
        		$("#PHONE").focus();
        		return false;
        	}else if(this.PHONE.length != 11 && !myreg.test(this.PHONE)){
    			$("#PHONE").tips({
    				side:3,
    	            msg:'手机号格式不正确',
    	            bg:'#AE81FF',
    	            time:3
    	        });
    			$("#PHONE").focus();
    			return false;
    		}
        	if('' == this.EMAIL){
        		$("#EMAIL").tips({
        			side:3,
                    msg:'输入邮箱',
                    bg:'#AE81FF',
                    time:3
                });
        		$("#EMAIL").focus();
        		return false;
        	}else if(!this.ismail(this.EMAIL)){
        		$("#EMAIL").tips({
        			side:3,
                    msg:'邮箱格式不正确',
                    bg:'#AE81FF',
                    time:3
                });
        		$("#EMAIL").focus();
        		return false;
        	}
        	
        	//发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'user/editUserOwn',
			    	data: {
			    		USER_ID:this.USER_ID,
			    		ROLE_ID:this.ROLE_ID,
			    		USERNAME:this.USERNAME,
			    		ROLE_IDS:this.ROLE_IDS,
			    		NUMBER:this.NUMBER,
			    		PASSWORD:this.PASSWORD,
			    		NAME:this.NAME,
			    		PHONE:this.PHONE,
			    		EMAIL:this.EMAIL,
			    		BZ:this.BZ,
			    		tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
                        if("success" == data.result){
                        	layer.msg('保存成功');
                        	setTimeout(function(){
                        		window.location.href='../index.html?FMSG=usercenter'
                            },1000);
                        }else if ("exception" == data.result){
                        	alert('后台程序异常');
                        }
                    }
				}).done().fail(function(){
					alert('登录失效! 请求服务器无响应,稍后再试');
	                window.location.href = "../login.html";
                });
        },
        
      	//获取数据
    	getData: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'user/goEditMyInfo',
		    	data: {tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                     if("success" == data.result){
                     	vm.USER_ID = data.pd.ROLE_ID;
                     	vm.ROLE_ID = data.pd.ROLE_ID;
                     	vm.USERNAME = data.pd.USERNAME;
                     	vm.NUMBER = data.pd.NUMBER;
                     	vm.ROLE_IDS = data.pd.ROLE_IDS;
                     	vm.NAME = data.pd.NAME;
                     	vm.PHONE = data.pd.PHONE;
                     	vm.EMAIL = data.pd.EMAIL;
                     	vm.BZ = data.pd.BZ;
                     }else if ("exception" == data.result){
                    	 alert('后台程序异常');
                     }
                  }
			}).done().fail(function(){
				alert('登录失效! 请求服务器无响应,稍后再试');
                window.location.href = "../login.html";
               });
    	},
    	
    	ismail: function (mail){
    		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
    	},

    	//判断邮箱是否存在
    	hasEMAIL: function (USERNAME){
    		$.ajax({
    			xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+'user/hasEmail',
    	    	data: {EMAIL:this.EMAIL,USERNAME:this.USERNAME,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				 if("success" != data.result){
    					 $("#EMAIL").tips({
    							side:3,
    				            msg:'邮箱 '+vm.EMAIL+' 已存在',
    				            bg:'#AE81FF',
    				            time:3
    				        });
    					 vm.EMAIL = '';
    				 }
    			}
    		});
    	}

	},
	
	mounted(){
        this.init();
    }
	
})