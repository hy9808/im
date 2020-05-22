var obUrl = '';
var vm = new Vue({
	el: '#app',
	
	data:{
    },
    
	methods: {
        
        save: function (){
        	if('' == obUrl){
        		$("#bgl").tips({
        			side:3,
                    msg:'还未选择照片!',
                    bg:'#AE81FF',
                    time:3
             	});
        	}else{
        		$.ajax({
        			xhrFields: {
                        withCredentials: true
                    },
        			type: "POST",
        			url: httpurl+'photo/saveUserPhoto',
        	    	data: {PHOTODATA:obUrl,tm:new Date().getTime()},
        			dataType:'json',
        			success: function(data){
        				window.location.href='../index.html?FMSG=usercenter';
        			}
        		});
        	}
        }

	}
	
})

$("#clipArea").photoClip({
	width: 200,
	height: 200,
	file: "#file",
	view: "#view",
	ok: "#clipBtn",
	loadStart: function() {
		//console.log("照片读取中");
	},
	loadComplete: function() {
		//console.log("照片读取完成");
	},
	clipFinish: function(dataURL) {
		//console.log(dataURL);
		obUrl = dataURL;
	}
});

$(function(){
$("#logox").click(function(){
$(".htmleaf-container").show();
});
$("#clipBtn").click(function(){
	$("#logox").empty();
	$('#logox').append('<img src="' + imgsource + '" align="absmiddle">');
	$(".htmleaf-container").hide();
});
});

//取消
function quxiao(){
	$(".htmleaf-container").hide();
}