var index = layer.load(1, {shade: false});

var vm = new Vue({
	el: '#app',
	
	data:{
		hitaskList: [],			//历史任务节点列表
		varList: [],			//流程变量列表
		PROC_INST_ID_: '',		//流程实例ID
		ID_: '',				//任务ID
		FILENAME: '',			//流程图文件名ID
		imgSrc: ''				//流程图base64数据
    },
    
    mixins: [formatDate],	//混入模块
    
	methods: {
		
        //初始执行
        init() {
        	this.PROC_INST_ID_ = this.getUrlKey('PROC_INST_ID_');	//链接参数
        	this.ID_ = this.getUrlKey('ID_');						//链接参数
        	this.FILENAME = this.getUrlKey('FILENAME');				//链接参数
       		this.getData();
        },
        
    	//进入页面获取数据
    	getData: function(){
    		//发送 post 请求
            $.ajax({
            	xhrFields: {
                    withCredentials: true
                },
				type: "POST",
				url: httpurl+'hiprocdef/view',
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
