
var vm = new Vue({
	el: '#app',
	
	data:{
		fhsmsCount: 0,		//站内信未读数量
		varList: [],		//list
		page: [],			//分页类
		TYPE: '1',			//类型
		KEYWORDS: '',		//关键词
		USERNAME: '',		//用户名
		fid: '',			//ID，删除时用
		title: '',			//TITILE
		ftitle: '',
		stitle: '',
		CONTENT: '',		//查看信件内容
		TOCONTENT:'',		//发送信件内容
		showCount: -1,		//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,		//当前页码
		fhSms:false
    },
    
    mixins: [vonline],		//混入模块
    
	methods: {
        //初始执行
        init() {
        	this.TYPE = this.getUrlKey('TYPE');	//链接参数
        	if('1' == this.TYPE){
				 this.title = "收信箱";
				 this.ftitle = "发信人";
				 this.stitle = "收信人";
			 }else{
				 this.title ="发信箱";
				 this.ftitle = "收信人";
				 this.stitle = "发信人";
			 }
    		this.getList();
    		this.getDataToOnline();
        	this.getFhsmsCount();
        },
        
        //获取站内信未读总数
        getFhsmsCount: function (){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
            	type: "POST",
            	url: httpurl+'fhsms/getFhsmsCount?tm='+new Date().getTime(),
            	data: encodeURI(""),
            	dataType:'json',
            	success: function(data){
        			vm.fhsmsCount = data.fhsmsCount;
        			if('1' == vm.TYPE){
        				$("#fhsmstss").tips({
            				side:1,
            	            msg:'您有'+vm.fhsmsCount+'条未读消息',
            	            bg:'#AE81FF',
            	            time:3
            	        });
        			}
            	}
            })
        },

        //去通知收信人有站内信接收
        fhsmsmsg: function (USERNAME){
        	var arrUSERNAME = USERNAME.split(';');
        	for(var i=0;i<arrUSERNAME.length;i++){
        		fwebsocket.send('[fhsms]'+arrUSERNAME[i]);//发送站内信通知
        	}
        },

        //获取列表
        getList: function(){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'fhsms/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {KEYWORDS:this.KEYWORDS,TYPE:this.TYPE,STATUS:'',tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.page = data.page;
        			 vm.hasButton();
        			 if('1' == vm.TYPE){
        				 $("title").html("收信箱");
        			 }else{
        				 $("title").html("发信箱");
        			 }
        		 }else if ("exception" == data.result){
        			 alert('后台程序异常');
                 }
        		}
        	}).done().fail(function(){
        		alert('登录失效! 请求服务器无响应,稍后再试');
                window.location.href = "../login.html";
            });
        },
        
    	//发站内信
    	sendFhsms: function (username){
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#addfhsms").show();
    		this.USERNAME = username;
    	},
    	
    	//保存
    	toSendFhsms: function (){
    		if(this.USERNAME == ""){
    			$("#USERNAME").tips({
    				side:3,
    	            msg:'请输入用户名',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#USERNAME").focus();
    		return false;
    		}
    		if(this.TOCONTENT == ""){
    			$("#TOCONTENT").tips({
    				side:3,
    	            msg:'请输入内容',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#TOCONTENT").focus();
    		return false;
    		}
    		$.ajax({
    			xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+"fhsms/send?tm="+new Date().getTime(),
    	    	data: {USERNAME:this.USERNAME,CONTENT:this.TOCONTENT},
    			dataType:'json',
    			success: function(data){
    				 if(data.result == 'success'){
    					 var count = data.count;
    					 var ecount = data.ecount;
    					 $("#taskCount").tips({
    						side:3,
    			            msg:'成功发出'+count+'条,失败'+ecount+'条',
    			            bg:'#68B500',
    			            time:3
    				      });
    				 }
    				 $(".modal-overlay").removeClass("modal-overlay-visible");
    				 $(".tj-modal").hide();
    				 vm.getList();
    				 vm.fhsmsmsg(vm.USERNAME);
    			}
    		});
    	},
		
		//是否删除
		isdel: function (Id){
			this.fid = Id;
			$(".modal-overlay").addClass("modal-overlay-visible");
			$("#bj-tc").show();
		},
		
		//删除
		goDel: function (){
			$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+'fhsms/delete',
    	    	data: {FHSMS_ID:this.fid,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				$(".modal-overlay").removeClass("modal-overlay-visible");
    				$("#bj-tc").hide();
    				layer.msg('删除成功');
    				vm.getList();
    			}
    		});
		},
		
		//查看信件
		viewx: function (ztid,STATUS,type,Id,SANME_ID){
			$(".modal-overlay").addClass("modal-overlay-visible");
			$("#v-tc").show();
			if(type == "1" && STATUS == '2'){
				$("#"+ztid).html('已读');
			}
			 $.ajax({
				 	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'fhsms/goView',
			    	data: {FHSMS_ID:Id,TYPE:type,SANME_ID:SANME_ID,STATUS:STATUS,tm:new Date().getTime()},
					dataType:'json',
					success: function(data){
						vm.CONTENT = data.pd.CONTENT;
					}
				});
		},
		
		//根据url参数名称获取参数值
        getUrlKey: function (name) {
            return decodeURIComponent(
                (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
        },
		
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'fhSms';
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
        			vm.fhSms = data.fhSms;			//站内信按钮权限		
        		 }
        		}
        	})
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
