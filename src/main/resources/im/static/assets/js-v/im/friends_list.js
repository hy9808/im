var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//好友list
		groupList:[],	//分组列表
		page: [],		//分页类
		pd: [],			//map
		fid: '',
		FUSERNAME: '',
		FGROUP_ID: '',	//分组ID
		httpurl:'',
		showCount: -1,	//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,	//当前页码
		del:false,
		edit:false,
    },
    
    mixins: [vonline],	//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.httpurl = httpurl;
        	this.getDataToOnline();
    		this.getList();
        },
        
        //获取列表
        getList: function(){
        	$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
        		type: "POST",
        		url: httpurl+'friends/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {keywords:this.pd.keywords,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.page = data.page;
        			 vm.pd = data.pd;
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
        
    	//修改
    	goEdit: function(id){
    		 var diag = new Dialog();
    		 diag.Drag=true;
    		 diag.Title ="编辑";
    		 diag.URL = 'friends_edit.html?FRIENDS_ID='+id;
    		 diag.Width = 399;
    		 diag.Height = 155;
    		 diag.Modal = true;				//有无遮罩窗口
    		 diag.CancelEvent = function(){ //关闭事件
    			 var varSon = diag.innerFrame.contentWindow.document.getElementById('showform');
    			 if(varSon != null && varSon.style.display == 'none'){
    				 vm.getList();
    			}
    			diag.close();
    		 };
    		 diag.show();
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
				url: httpurl+'friends/deletefromlist',
		    	data: {FRIENDS_ID:this.fid,tm:new Date().getTime()},
				dataType:'json',
				success: function(data){
					 if("success" == data.result){
						 $(".modal-overlay").removeClass("modal-overlay-visible");
						 $("#bj-tc").hide();
						 layer.msg('删除成功');
						 vm.getList(); 
					 }
				}
			});
    	},
    	
    	//是否拉黑
    	ispullblack: function (Id,FUS){
    		this.fid = Id;
    		this.FUSERNAME = FUS;
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc2").show();
    	},
    	
    	//拉黑
    	pullblack: function (){
    		$.ajax({
	    		xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'friends/pullblackfromlist',
		    	data: {FRIENDS_ID:this.fid,FUSERNAME:this.FUSERNAME,tm:new Date().getTime()},
				dataType:'json',
				success: function(data){
					 if("success" == data.result){
						 $(".modal-overlay").removeClass("modal-overlay-visible");
						 $("#bj-tc2").hide();
						 layer.msg('拉黑成功');
						 vm.getList();
					 }
					 
				}
			});
    	},
        
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'friends:del,friends:edit';
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
        			vm.del = data.friendsfhadmindel;
        			vm.edit = data.friendsfhadminedit;
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