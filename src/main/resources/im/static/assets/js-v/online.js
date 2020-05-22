
var fwebsocket;	//websocket对象
var oladress;	//在线管理和站内信服务器IP和端口
var user;		//用于即时通讯（ 当前登录用户）
var fhsmsSound;	//提示声音
var isTwolevel = true;	//是否二级目录引入
var isRuntask = false;	//是否待办任务页面引入
var vonline = {
		
		methods: {

			//基本信息
			getDataToOnline: function (){
				$.ajax({
					xhrFields: {
			            withCredentials: true
			        },
			    	type: "POST",
			    	url: httpurl+'head/getDataToOnline?tm='+new Date().getTime(),
			    	data: encodeURI(""),
			    	dataType:'json',
			    	success: function(data){
			    		if('success' == data.result){
			    			user = data.USERNAME;						//用户名
			        		oladress = data.onlineAdress;				//在线管理和站内信服务器IP和端口
			        		fhsmsSound = data.fhsmsSound;				//提示声音
			        		vm.online();
			    		}else if('exception' == data.result){
			    			alert('后台程序异常');
			    		}
			    	}
			    }).done().fail(function(){
			        alert('登录失效! 请求服务器无响应,稍后再试');
			        vm.goLoginPage();
			    });
			},
			
			//加入在线列表
			online: function (){
				if (window.WebSocket) {
					fwebsocket = new WebSocket(encodeURI('ws://'+oladress)); //oladress在main.jsp页面定义
					fwebsocket.onopen = function() {
						//连接成功
						fwebsocket.send('[join]mobile-'+user);
					};
						fwebsocket.onerror = function() {
						//连接失败
					};
						fwebsocket.onclose = function() {
						//连接断开
					};
					//消息接收
					fwebsocket.onmessage = function(message) {
						var message = JSON.parse(message.data);
						if(message.type == 'goOut'){
							$("body").html("");
							vm.goOut("1");
						}else if(message.type == 'thegoout'){
							$("body").html("");
							vm.goOut("2");
						}else if(message.type == 'senFhsms'){
	        				if(!isTwolevel){
	        					vm.fhsmsCount = Number(vm.fhsmsCount)+1;
	        					$("#fhsmstss").tips({
		        					side:1,
		        		            msg:'您有新消息',
		        		            bg:'#AE81FF',
		        		            time:3
		        		        });
	        				}else{
	        					$("#fhsmstss").tips({
		        					side:3,
		        		            msg:'您有新消息',
		        		            bg:'#AE81FF',
		        		            time:3
		        		        });
	        				}
	        				vm.fhsmsobj();
	        				setTimeout(function(){
	        					vm.goFhsms();
	                        },3000);
	        			}else if(message.type == 'fhtask'){
	        				if(isRuntask)vm.getList();
	        				if(message.RNUMBER == 'no'){
	        					vm.fhsmsobj();
	        					$("#taskCount").tips({
	        						side:3,
	        			            msg:'您有新任务',
	        			            bg:'#AE81FF',
	        			            time:3
	        			        });
	        					setTimeout(function(){
	        						if(!isRuntask)vm.goTask();
	                            },3000);
	        				}else{
	        					$.ajax({
	        						xhrFields: {
	        		                    withCredentials: true
	        		                },
	        						type: "POST",
	        						url: httpurl+'head/isNowRole',
	        				    	data: {RNUMBER:message.RNUMBER,tm:new Date().getTime()},
	        						dataType:'json',
	        						success: function(data){
	        							 if('yes' == data.msg){
	        								vm.fhsmsobj();
	        								$("#taskCount").tips({
	        									side:1,
	        						            msg:'您有新任务',
	        						            bg:'#AE81FF',
	        						            time:3
	        						        });
	        								setTimeout(function(){
	        									vm.goTask();
	        	                            },3000);
	        							 }
	        						}
	        					});
	        				}
	        			}
					};
				}
			},

			//下线
			goOut: function (msg){
				alert("被迫下线!");
				this.logOut();
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
					success: function(data){
						vm.goLoginPage();
					}
				}).done().fail(function(){
					vm.goLoginPage();
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
	        
	        //跳转到待办任务页面
	        goTask: function (){
	        	if(isTwolevel){
	        		window.location.href = "../task/rutask_list.html";
				}else{
					window.location.href = "task/rutask_list.html";
				}
	        },
	        
	        //跳转到站内信页面
	        goFhsms: function (){
	        	if(isTwolevel){
	        		window.location.href='../fhsms/fhsms_list.html?TYPE=1';
				}else{
					window.location.href='fhsms/fhsms_list.html?TYPE=1';
				}
	        },
	        
	        //声音提醒
	        fhsmsobj: function (){
	        	if(isTwolevel){
	        		$("#fhsmsobj").html('<audio style="display: none;" id="fhsmstsy" src="../../assets/sound/'+fhsmsSound+'.mp3" autoplay controls></audio>');
				}else{
					$("#fhsmsobj").html('<audio style="display: none;" id="fhsmstsy" src="../assets/sound/'+fhsmsSound+'.mp3" autoplay controls></audio>');
				}
	        },
			
		}

	};
