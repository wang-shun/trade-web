/**
 * 知识库发布 
 * @author yumin1
 * 
 */

/**
 * 确认 提交按钮事件
 */
function knowledgeSubmit(ctx){
	var isTop = 0;
	var recomm = 0;
	if($("#isTop").attr('checked')=='checked')isTop = 1;
	if($("#recom").attr('checked')=='checked')recomm =1;
	var knowledgeRepo = {
		"knowledgeCode" : $("#knowledgeCode").val(),
		"title" : $("#knowledgeTitle").val(),
		"content" : $("#knowledgeContent").val(),
		"isRecommand" : recomm,
		"isTop" :isTop
	};
	
	//判断是否上传了附件
	var pics=getAddFilesIsOk();
	if(pics==null){
		return;
	};
	var params={
    		"knowledgeRepo":knowledgeRepo,   
    		"knowledgeRepoAttachmentList" : pics
    };
	
	knowledgeRepoAddVO = $.toJSON(params); 
	//console.info(knowledgeRepoAddVO);
	
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
			
			/*if(data.success){
				location.reload();
				
			}*/
		},
		error : function(data) {
			alert(data.message);  
			parent.$.fancybox.close();
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
 * 取消按钮事件
 */
function closeFancybox() {
	if (confirm("确定要取消吗?")) {
		//返回父窗口
		parent.$.fancybox.close();
	}
}
