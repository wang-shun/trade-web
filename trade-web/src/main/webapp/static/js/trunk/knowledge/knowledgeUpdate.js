/**
 * 知识库 详情显示
 * @author yumin1
 */

/**
 * 初始化 加载数据
 */
function knowledgeUpdateLoadInit(knowledgePkid,ctx,imgweb){
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
				divPic += "<div>"+fileName+"</div><hr>";
			});
			$("#divPic").html(divPic);
		},
		error : function(data) {
			alert(data.message);  
		}
	 });
}
/**
 * 获取上传成功的文件的信息
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
	
	//必须上传文件
	if(pics.length<=0){
		 alert("请先上传文件成功后再提交！");
		 return null;
	}
	var picDiv=$("div[name='allPicDiv']");
	//所选图片和上传的图片的数目要相同
	if(picDiv.length!==pics.length){
		 alert("请成功上传所有的文件后再提交！");
		 return null;
	}
	return pics;
}

/**
 * 确认 提交按钮事件
 */
function knowledgeSubmit(knowledgePkid,ctx){

	var pics = getAddFilesIsOk();
	if(pics==null){
		return;
	}
	var knowledgeRepo = {
		"pkid" : knowledgePkid,
		"knowledgeCode" : $("#knowledgeCode").val(),
		"title" : $("#knowledgeTitle").val(),
		"content" : $("#knowledgeContent").val()
	};

	/*var knowledgeRepoAttachmentList={
			"knowledgeCode" : $("#knowledgeCode").val(),
			"fileType" : null,
			"knowledgeFileCode": spans[i].className,
			"fileName": spans[i].title
	};*/
	
	var params={
    		"knowledgeRepo":knowledgeRepo,
    		"knowledgeRepoAttachmentList":pics
    };
	
	knowledgeRepoAddVO = $.toJSON(params); 
	console.info(knowledgeRepoAddVO);
	
	//ajax提交
	$.ajax({
		cache : true,
		async : false,
		type : "POST",
		url : ctx+'/knowledgemtn/knowledgeUpdate',
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
 * 取消按钮事件
 */
function closeFancybox() {
	if (confirm("确定要取消吗?")) {
		//返回父窗口
		parent.$.fancybox.close();
	}
}