
var index = layer.load(1, {shade: false});

var vm = new Vue({
	el: '#app',
	
	data:{
		serverurl: '',			//后台地址
		hitaskList: [],			//历史任务节点列表
		varList: [],			//流程变量列表
		PROC_INST_ID_: '',		//流程实例ID
		ID_: '',				//任务ID
		OPINION: '',			//审批意见
		ASSIGNEE_: '',			//待办人
		messagetext: '',		//作废缘由
		FILENAME: '',			//流程图文件名ID
		imgSrc: '',				//流程图base64数据
		msg:'',					//批准or驳回
		Reject:false,			//驳回按钮权限
		Abolish:false,			//作废按钮权限
		NextASSIGNEE_:false		//指定下一办理人按钮权限
    },
    
    mixins: [vonline,formatDate],		//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.serverurl = httpurl;
        	
        	this.PROC_INST_ID_ = this.getUrlKey('PROC_INST_ID_');	//链接参数
        	this.ID_ = this.getUrlKey('ID_');						//链接参数
        	this.FILENAME = this.getUrlKey('FILENAME');				//链接参数
        	
        	this.getDataToOnline();
       		this.getData();
       		this.hasButton();
        },
        
    	//进入页面获取数据
    	getData: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'rutask/getHandleData',
		    	data: {PROC_INST_ID_:this.PROC_INST_ID_,ID_:this.ID_,FILENAME:this.FILENAME,tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                     if("success" == data.result){
                    	 vm.hitaskList = data.hitaskList;
                    	 vm.varList = data.varList;
                    	 vm.imgSrc = data.imgSrc;
                    	 layer.close(index);
                     }else if ("exception" == data.result){
                    	 layer.msg('后台程序异常');
                     }
                  }
			}).done().fail(function(){
				alert("登录失效!请求服务器无响应,稍后再试");
                window.location.href = "../login.html";
               });
    	},

    	//选择办理人
    	getUser: function (){
    		 var diag = new Dialog();
    		 diag.Drag=true;
    		 diag.Title ="选择办理人";
    		 diag.URL = '../user/window_user_list.html';
    		 diag.Width = 700;
    		 diag.Height = 545;
    		 diag.Modal = true;				//有无遮罩窗口
    		 diag.CancelEvent = function(){ //关闭事件
    			 var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
    			 if("" != USERNAME){
    				 vm.ASSIGNEE_ = USERNAME;
    			 }
    			diag.close();
    		 };
    		 diag.show();
    	},
    	
    	//选择角色
    	getRole: function (){
    		 var diag = new Dialog();
    		 diag.Drag=true;
    		 diag.Title ="选择角色";
    		 diag.URL = '../user/window_role_list.html';
    		 diag.Width = 700;
    		 diag.Height = 545;
    		 diag.Modal = true;				//有无遮罩窗口
    		 diag.CancelEvent = function(){ //关闭事件
    			 var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
    			 if("" != RNUMBER){
    				 vm.ASSIGNEE_ = RNUMBER;
    			 }
    			diag.close();
    		 };
    		 diag.show();
    	},
    	
    	//驳回
    	isbohui: function (msg){
    		this.msg = msg;
    		$("#sptitle").html("驳回");
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc2").show();
    	},
    	
    	//批准
    	ispizhun: function (msg){
    		this.msg = msg;
    		$("#sptitle").html("批准");
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc2").show();
    	},

        //办理任务
        handle: function (){
        	if(this.OPINION == ''){
    			$("#OPINION").tips({
    				side:3,
    	            msg:'请输入审批意见',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#OPINION").focus();
    		return false;
    		}
           	$.ajax({
           		xhrFields: {
                    withCredentials: true
                },
           		type: "POST",
           		url: httpurl+'rutask/handle',
               	data: {msg:this.msg,ID_:this.ID_,ASSIGNEE_:this.ASSIGNEE_,PROC_INST_ID_:this.PROC_INST_ID_,OPINION:this.OPINION,tm:new Date().getTime()},
           		dataType:'json',
           		success: function(data){
           			if("success" == data.result){
           				if('null' != data.ASSIGNEE_){
           					vm.fhtaskmsg(data.ASSIGNEE_);
           				}
           				if(undefined != data.FHSMS){
           					vm.fhsmsmsg(data.FHSMS); 		
           				}
                       	layer.msg('审批完成');
                       	setTimeout(function(){
                       		window.location.href="rutask_list.html";
                           },1000);
                       }else if ("exception" == data.result){
                    	   layer.msg('后台程序异常');
                       }
           		}
           	}).done().fail(function(){
           		alert("登录失效!请求服务器无响应,稍后再试");
                window.location.href = "../login.html";
                });
        },
        
      	//去通知任务待办人有新任务
        fhtaskmsg: function (USERNAME){
        	fwebsocket.send('[fhtask]'+USERNAME);//发送新任务通知
        },
        
      	//去通知收信人有站内信接收
        fhsmsmsg: function (USERNAME){
        	var arrUSERNAME = USERNAME.split(';');
        	for(var i=0;i<arrUSERNAME.length;i++){
        		fwebsocket.send('[fhsms]'+arrUSERNAME[i]);//发送站内信通知
        	}
        },

    	//是否作废
    	iszuofei: function (){
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc").show();
    	},
    	
    	//提交作废
    	zuofei: function (){
    		if('' == this.messagetext){
    			$("#messagetext").tips({
    				side:3,
    	            msg:'还未写作废缘由!',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#messagetext").focus();
    			return false;
    		}
    		$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'ruprocdef/delete?',
        		data: {PROC_INST_ID_:this.PROC_INST_ID_,reason:encodeURI(encodeURI(this.messagetext)),tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 layer.msg('操作完成');
                    	setTimeout(function(){
                    		window.location.href="rutask_list.html";
                        },1000);
        		 }else if ("exception" == data.result){
        			 layer.msg('后台程序异常');
                 }
        		}
        	});
    	},
        
    	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'Reject,Abolish,NextASSIGNEE_';
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'head/hasButton',
        		data: {keys:keys,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			vm.Reject = data.Reject;				//驳回按钮权限
        			vm.Abolish = data.Abolish;				//作废按钮权限
        			vm.NextASSIGNEE_ = data.NextASSIGNEE_;	//指定下一办理人按钮权限
        		 }
        		}
        	})
        },
        
        formatDate: function (time){
        	let date = new Date(time);
        	return this.formatDateUtil(date, "yyyy-MM-dd hh:mm:ss");
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