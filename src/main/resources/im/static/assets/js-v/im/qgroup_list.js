var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//好友list
		groupList:[],	//分组列表
		page: [],		//分页类
		pd: [],			//map
		fid: '',		//群ID,删除时用
		imgpath:'',
		QID: '',		//群ID,新增时用
		httpurl:'',
		showCount: -1,	//每页显示条数(这个是系统设置里面配置的，初始为-1即可，固定此写法)
		currentPage: 1,	//当前页码
		add:false,
		del:false,
		edit:false
    },
    
    mixins: [vonline],	//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.httpurl = httpurl;
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
        		url: httpurl+'qgroup/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {keywords:this.pd.keywords,tm:new Date().getTime()},
        		dataType:"json",
        		success: function(data){
        		 if("success" == data.result){
        			 vm.varList = data.varList;
        			 vm.page = data.page;
        			 vm.pd = data.pd;
        			 vm.QID = data.QID;
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
		
		//新增
		goAdd: function (){
			 var diag = new Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = 'qgroup_edit.html?QID='+this.QID;
			 diag.Width = 450;
			 diag.Height = 300;
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
		
		//修改
		goEdit: function (Id){
			 var diag = new Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = 'qgroup_edit.html?QGROUP_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 300;
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
		
		//退出群
		goDel:function (){
			$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+'qgroup/delete',
    	    	data: {TYPE:'goOut',QGROUP_ID:this.fid,PATH:this.imgpath,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				$(".modal-overlay").removeClass("modal-overlay-visible");
    				$("#bj-tc").hide();
    				layer.msg('退出成功');
    				vm.getList();
    			}
    		});
		},
		
		//是否退群
		isdel: function (Id,value){
			this.fid = Id;
			this.imgpath = value;
			$(".modal-overlay").addClass("modal-overlay-visible");
			$("#bj-tc").show();
		},
        
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'qgroup:add,qgroup:del,qgroup:edit';
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
        			vm.add = data.qgroupfhadminadd;
        			vm.del = data.qgroupfhadmindel;
        			vm.edit = data.qgroupfhadminedit;
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