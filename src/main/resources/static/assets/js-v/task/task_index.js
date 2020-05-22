var vm = new Vue({
	el: '#app',
	mixins: [vonline],		//混入模块
	methods: {
		//初始执行
        init() {
    		this.getDataToOnline();
        },
	},
	
	mounted(){
        this.init();
    }
})