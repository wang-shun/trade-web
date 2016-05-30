/**
 * 知识库 详情显示
 * @author yumin1
 */

/**
 * 初始化 加载数据
 */
function knowledgeDetailLoadInit(knowledgePkid,ctx,imgweb){
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : ctx+'/knowledgemtn/knowledgeDetail',
		dataType : "json",
		data :[{
			name : 'knowledgePkid',
			value : knowledgePkid
		}],
		success : function(data) {
			//console.info(data);
			$("#knowledgeCode").val(data.knowledgeRepo.knowledgeCode);
			$("#knowledgeTitle").val(data.knowledgeRepo.title);
			$("#knowledgeContent").val(data.knowledgeRepo.content);
			
			var list = data.knowledgeRepoAttachmentList; 
			
			var divPic = "";
			$.each(list,function(s,b){
				var fileName = b.fileName;
				divPic += "<div><a href='"+imgweb+"/filesvr/downLoad?id="+b.knowledgeFileCode+"' title='点击下载' download="+fileName+">"+fileName+"</a></div><hr>";
			});
			$("#divPic").html(divPic);
			
			
		},
		error : function(data) {
			alert(data.message);  
		}
	 });
}

/**
 * 确认 提交按钮事件
 */
function knowledgeSubmit(ctx){

	var knowledgeRepo = {
		"knowledgeCode" : $("#knowledgeCode").val(),
		"title" : $("#knowledgeTitle").val(),
		"content" : $("#knowledgeContent").val()
	};
	
	//判断是否上传了附件
	var pics=getAddFilesIsOk();
	var params={
    		"knowledgeRepo":knowledgeRepo,   
    		"knowledgeRepoAttachmentList" : pics
    };
	
	knowledgeRepoAddVO = $.toJSON(params); 
	console.info(knowledgeRepoAddVO);
	
	//ajax提交
	$.ajax({
		cache : true,
		async : false,
		type : "POST",
		url : ctx+'/knowledgemtn/knowledgeAdd',
		dataType : "json",
		contentType: "application/json; charset=utf-8",
		data:knowledgeRepoAddVO,
		success : function(data) {
			alert(data.message); 
			parent.$.fancybox.close();
		},
		error : function(data) {
			alert(data.message);  
			parent.$.fancybox.close();
		}
	});
}

/**
 * 获取上传成功的图片的信息
 */
function getAddFilesIsOk() {
	//图片的ID
	var pics=[];
	var spans = $("div[name='names']");
	$.each(spans, function(i, item) {
		var pic={
				"knowledgeCode" : $("#knowledgeCode").val(),
				"fileType" : null,
				"knowledgeFileCode": spans[i].className,
				"fileName": spans[i].title
		}
		pics.push(pic);
	});
	
	//必须上传图片
	if(pics.length<=0){
		 alert("请先上传图片成功后再提交！");
		 return null;
	}
	var picDiv=$("div[name='allPicDiv']");
	//所选图片和上传的图片的数目要相同
	if(picDiv.length!==pics.length){
		 alert("请成功上传所有的图片后再提交！");
		 return null;
	}
	return pics;
}


/**
 * 取消按钮事件
 */
function closeFancybox() {
	//返回父窗口
	parent.$.fancybox.close();
}