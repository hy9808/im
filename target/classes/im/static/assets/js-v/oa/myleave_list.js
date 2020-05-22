var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//list
		page: [],		//分页类
		pd: [],			//存放字段参数
		KEYWORDS: '',	//关键词
		TYPE:'',
		fid: '',
		showCount: -1,	//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,	//当前页码
		add:false,
		del:false
    },
    
    mixins: [vonline],		//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.getDataToOnline();
    		this.getList();
    		this.getDic();
        },
        
        //获取列表
        getList: function(){
        	this.TYPE = null == $("#TYPE").val()?'':$("#TYPE").val();
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'myleave/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
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
    			url: httpurl+'myleave/delete',
    	    	data: {MYLEAVE_ID:this.fid,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				$(".modal-overlay").removeClass("modal-overlay-visible");
    				$("#bj-tc").hide();
    				layer.msg('删除成功');
    				vm.getList();
    			}
    		});
    	},
    	
    	//调用数据字典(请假类型)
    	getDic: function(){
    		$.ajax({
    			xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+'dictionaries/getLevels',
    	    	data: {DICTIONARIES_ID:'6d30b170d4e348e585f113d14a4dd96d',tm:new Date().getTime()},//请假类型
    			dataType:'json',
    			success: function(data){
    				$("#TYPE").html('<option value="">请假类型</option>');
    				 $.each(data.list, function(i, dvar){
    					 $("#TYPE").append("<option value="+dvar.NAME+">"+dvar.NAME+"</option>");
    				 });
    			}
    		});
    	},
    	
        //去保存
    	save: function (){
    		
    		if(this.pd.STARTTIME  == '' || this.pd.STARTTIME == undefined){
    			$("#STARTTIME").tips({
    				side:3,
    				msg:'请输入开始时间',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.STARTTIME = '';
    			this.$refs.STARTTIME.focus();
    		return false;
    		}
    		if(this.pd.ENDTIME  == '' || this.pd.ENDTIME == undefined){
    			$("#ENDTIME").tips({
    				side:3,
    				msg:'请输入结束时间',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.ENDTIME = '';
    			this.$refs.ENDTIME.focus();
    		return false;
    		}
    		if(this.TYPE  == ''){
    			$("#TYPE").tips({
    				side:3,
    				msg:'请选择请假类型',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.TYPE = '';
    			this.$refs.TYPE.focus();
    		return false;
    		}
    		if(this.pd.WHENLONG  == '' || this.pd.WHENLONG == undefined){
    			$("#WHENLONG").tips({
    				side:3,
    				msg:'请输入时长',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.WHENLONG = '';
    			this.$refs.WHENLONG.focus();
    		return false;
    		}
    		if(this.pd.REASON  == '' || this.pd.REASON == undefined){
    			$("#REASON").tips({
    				side:3,
    				msg:'请输入事由',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.REASON = '';
    			this.$refs.REASON.focus();
    		return false;
    		}
    		
            //发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'myleave/add',
			    	data: {STARTTIME:this.pd.STARTTIME,
			    		ENDTIME:this.pd.ENDTIME,
			    		TYPE:this.TYPE,
			    		WHENLONG:this.pd.WHENLONG,
			    		REASON:this.pd.REASON,
			    		tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
						$(".modal-overlay").removeClass("modal-overlay-visible");
						$(".tj-modal").hide();
                        if("success" == data.result){
                        	vm.fhtaskmsg(data.ASSIGNEE_); //websocket即时通讯用于给待办人发送新任务消息 ，fhtaskmsg()函数 在 WebRoot\assets\js-v\index.js
                        	layer.msg('保存成功');
                        }else if ("errer" == data.result){
                        	layer.msg('审批流启动失败! 请联系管理员部署相应业务流程!');
                        }else if ("exception" == data.result){
                        	alert('后台程序异常');
                        }
                    }
				}).done().fail(function(){
					alert('登录失效! 请求服务器无响应,稍后再试');
	                window.location.href = "../login.html";
                });
    	},
    	
    	//去通知任务待办人有新任务
        fhtaskmsg: function (USERNAME){
        	fwebsocket.send('[fhtask]'+USERNAME);//发送新任务通知
        },
        
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'myleave:add,myleave:del';
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
        			vm.add = data.myleavefhadminadd;
        			vm.del = data.myleavefhadmindel;
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