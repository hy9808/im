var vm = new Vue({
	el: '#app',
	
	data:{
		varList: [],	//list
		fid:	'',		//主键ID
		page: [],		//分页类
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
        		url: httpurl+'fgroup/list?showCount='+this.showCount+'&currentPage='+this.currentPage,
        		data: {tm:new Date().getTime()},
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

		//新增
		goAdd: function (){
			 var diag = new Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = 'fgroup_edit.html';
			 diag.Width = 350;
			 diag.Height = 144;
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
		goEdit: function(id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = 'fgroup_edit.html?FGROUP_ID='+id;
			 diag.Width = 600;
			 diag.Height = 131;
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
    	
    	//删除
    	goDel: function (){
    		$.ajax({
        		xhrFields: {
                    withCredentials: true
                },
    			type: "POST",
    			url: httpurl+'fgroup/delete',
    	    	data: {FGROUP_ID:this.fid,tm:new Date().getTime()},
    			dataType:'json',
    			success: function(data){
    				$(".modal-overlay").removeClass("modal-overlay-visible");
    				$("#bj-tc").hide();
    				layer.msg('删除成功');
    				vm.getList();
    			}
    		});
    	},
    	
    	//是否删除
    	isdel: function (Id){
    		this.fid = Id;
    		$(".modal-overlay").addClass("modal-overlay-visible");
    		$("#bj-tc").show();
    	},
    	
      	//判断按钮权限，用于是否显示按钮
        hasButton: function(){
        	var keys = 'fgroup:add,fgroup:del,fgroup:edit';
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
        			vm.add = data.fgroupfhadminadd;
        			vm.del = data.fgroupfhadmindel;
        			vm.edit = data.fgroupfhadminedit;
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