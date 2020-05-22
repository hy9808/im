
var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//list
		page: [],		//分页类
		ID_: '',		//任务ID
		ASSIGNEE_: '',	//代办人
		KEYWORDS: '',	//检索条件
		showCount: -1,	//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,	//当前页码
		Delegate:false,	//委派按钮权限
		edit:false,		//办理权限
    },
    
    mixins: [vonline,formatDate],		//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	isRuntask = true;
    		this.getList();
    		this.getDataToOnline();
        },
        
        //获取列表
        getList: function(){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'rutask/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {KEYWORDS:this.KEYWORDS,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.page = data.page;
        			 vm.hasButton();
        		 }else if ("exception" == data.result){
        			 alert('后台程序异常');
                 }
        		}
        	}).done().fail(function(){
        		alert('登录失效! 请求服务器无响应,稍后再试');
                window.location.href = "../login.html";
            });
        },
    	
    	//委派
    	setDelegate: function (ID_){
    		this.ID_ = ID_;
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc").show();
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
    	
        //去保存
    	toDelegate: function (){
    		if(this.ASSIGNEE_  == ''){
    			$("#ASSIGNEE_").tips({
    				side:3,
    				msg:'请选择委派对象',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.ASSIGNEE_ = '';
    			$("#ASSIGNEE_").focus();
    		return false;
    		}
            //发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'ruprocdef/delegate',
			    	data: {ID_:this.ID_,ASSIGNEE_:this.ASSIGNEE_,tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
                        if("success" == data.result){
                        	vm.fhtaskmsg(data.ASSIGNEE_);	//通知代理人去办理任务
                        	$("#ASSIGNEE_").tips({
                				side:3,
                				msg:'委派成功',
                	            bg:'#AE81FF',
                	            time:2
                	        });
                        	$(".modal-overlay").removeClass("modal-overlay-visible");
                			$("#bj-tc").hide();
                        	vm.getList();
                        }else if ("exception" == data.result){
                        	 alert("后台程序异常");//显示异常
                        }
                    }
				}).done().fail(function(){
                   alert("登录失效!请求服务器无响应,稍后再试");
                   window.location.href = "../login.html";
                });
    	},
    	
    	//办理任务
    	handle: function (PROC_INST_ID_,ID_,FILENAME){
    		window.location.href='rutask_handle.html?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
    	},
    	
    	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'rutask:edit,Delegate';
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
        			vm.edit = data.rutaskfhadminedit;		//办理权限
        			vm.Delegate = data.Delegate;			//委派按钮权限
        		 }
        		}
        	})
        },
        
      	//去通知任务待办人有新任务
        fhtaskmsg: function (USERNAME){
        	fwebsocket.send('[fhtask]'+USERNAME);//发送新任务通知
        },
        
        formatDate: function (time){
        	let date = new Date(time);
        	return this.formatDateUtil(date, "yyyy-MM-dd hh:mm:ss");
        },
        
      	//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        },
        
        //-----分页必用----start
        nextPage: function (page){
        	this.currentPage = page;
        	this.getList();
        },
        changeCount: function (value){
        	this.showCount = value;
        	this.getList();
        },
        toTZ: function (){
        	var toPaggeVlue = document.getElementById("toGoPage").value;
        	if(toPaggeVlue == ''){document.getElementById("toGoPage").value=1;return;}
        	if(isNaN(Number(toPaggeVlue))){document.getElementById("toGoPage").value=1;return;}
        	this.nextPage(toPaggeVlue);
        }
       //-----分页必用----end
		
	},
	
	mounted(){
        this.init();
    }
})
