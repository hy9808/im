
$(function(){
	$("#guishu-bj-qx").click(function(){
		$(".modal-overlay").removeClass("modal-overlay-visible");
		$("#bj-tc").hide();
		});
	$("#guishu-tj-butt").click(function(){
		vm.pd.CONTENT = '';
		vm.pd.BZ = '';
		$(".modal-overlay").addClass("modal-overlay-visible");
		$(".tj-modal").show();
		});
	$("#guishu-tj-qx").click(function(){
		$(".modal-overlay").removeClass("modal-overlay-visible");
		$(".tj-modal").hide();
		});
})

var vm = new Vue({
	el: '#app',
	
	data:{
		WORKLOG_ID:'',	//主键
		USERNAME:'',	//用户名
		varList: [],	//list
		page: [],		//分页类
		pd: [],			//存放字段参数
		KEYWORDS: '',	//关键词
		fid: '',
		showCount: -1,	//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,	//当前页码
		isMy:false,		//是否只看本人的
		add:false,
		edit:false,		//改
		del:false
    },
    
    mixins: [vonline],		//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.getDataToOnline();
    		this.getList();
        },
        
        //获取列表
        getList: function(){
        	this.TYPE = null == $("#TYPE").val()?'':$("#TYPE").val();
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'worklog/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {KEYWORDS:this.KEYWORDS,isMy:this.isMy,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.USERNAME = data.USERNAME;
        			 vm.page = data.page;
        			 vm.hasButton();
        		 }else if ("exception" == data.result){
        			 alert('后台程序异常');
                 }
        		}
        	}).done().fail(function(){
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
    			url: httpurl+'worklog/delete',
    	    	data: {WORKLOG_ID:this.fid,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				$(".modal-overlay").removeClass("modal-overlay-visible");
    				$("#bj-tc").hide();
    				layer.msg('删除成功');
    				vm.getList();
    			}
    		});
    	},
    	
        //去保存
    	save: function (){
    		
    		if(this.pd.CONTENT  == '' || this.pd.CONTENT == undefined){
    			$("#CONTENT").tips({
    				side:3,
    				msg:'请输入工作日志',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.CONTENT = '';
    			this.$refs.CONTENT.focus();
    		return false;
    		}
    		if(this.pd.BZ  == '' || this.pd.BZ == undefined){
    			$("#BZ").tips({
    				side:3,
    				msg:'请输入备注',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			this.pd.BZ = '';
    			this.$refs.BZ.focus();
    		return false;
    		}
    		
            //发送 post 请求提交保存
            $.ajax({
	            	xhrFields: {
	                    withCredentials: true
	                },
					type: "POST",
					url: httpurl+'worklog/add',
			    	data: {WORKLOG_ID:this.WORKLOG_ID,
			    		CONTENT:this.pd.CONTENT,
					    BZ:this.pd.BZ,
			    		tm:new Date().getTime()},
					dataType:"json",
					success: function(data){
						$(".modal-overlay").removeClass("modal-overlay-visible");
						$(".tj-modal").hide();
                        if("success" == data.result){
                        	layer.msg('保存成功');
                        	vm.getList();
                        }else if ("exception" == data.result){
                        	alert('后台程序异常');
                        }
                    }
				}).done().fail(function(){
					alert('登录失效! 请求服务器无响应,稍后再试');
	                window.location.href = "../login.html";
                });
    	},
    	
    	//是否修改
    	goEdit: function (Id){
    		this.WORKLOG_ID = Id;
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$(".tj-modal").show();
    		this.getData();
    	},
    	
    	//根据主键ID获取数据
    	getData: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'worklog/goEdit',
		    	data: {WORKLOG_ID:this.WORKLOG_ID,tm:new Date().getTime()},
				dataType:"json",
				success: function(data){
                     if("success" == data.result){
                     	vm.pd = data.pd;							//参数map
                     }else if ("exception" == data.result){
                    	 alert('后台程序异常');
                     }
                  }
			}).done().fail(function(){
				alert('登录失效! 请求服务器无响应,稍后再试');
                window.location.href = "../login.html";
               });
    	},
    	
        
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'worklog:add,worklog:del,worklog:edit';
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
        			vm.add = data.worklogfhadminadd;
        			vm.del = data.worklogfhadmindel;
        			vm.edit = data.worklogfhadminedit;
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