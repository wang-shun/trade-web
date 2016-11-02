function openUploadDialog(data){
	var url = appCtx['shcl-filesvr-web'];
	var option = {
			dialogId : "uploadDialog",
			dialog:{
    	 	width:780,
    	 	height:375, 
    	 	//allowClose:false,
    	 	title: "上传文件", 
    	 	url:url+"/filesvr/openUpload.html?data="+ new Date(),
    	 	buttons: [
                  { text: '确定', onclick: function (item, dialog) { dialog.frame.save();}}
               ]
     }};
	if(data){ if(data.callBack){option.callBack = data.callBack;} option.dialog.data = data;}
	openDialog(option);
}
function downLoad(id){
	var url = appCtx['shcl-filesvr-web'];
	window.location.href = url+"/filesvr/downLoad?id="+id;
}
function downLoadZip(data){
	var url = appCtx['shcl-filesvr-web']+"/filesvr/downLoadZip";
	if (url && data) {
		var inputs = '';
		$.each(data, function(i) {
			var pair = this;
			inputs += '<input type="hidden" name="files['+i+'].id" value="'+ pair.id + '" />';
			if(pair.name){
				inputs += '<input type="hidden" name="files['+i+'].name" value="'+ pair.name + '" />';
			}
		});
		$('<form action="' + url + '" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	}
}
