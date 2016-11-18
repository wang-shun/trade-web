function downLoad(fileId, fileName) {
	window.location.href = ctx+"/attachment/resourcelibrary/downLoadResource.do?&pash="
		+ fileId+"&name="+fileName;
}

function show(fileId) {
	window.open(appCtx['shcl-image-web'] + "/image/"+fileId);
}
		
function getExplPicByhouseCode() {
	$.ajax({
		type : 'post',
		cache : false,
		async : true,//false同步，true异步
		dataType : 'json',
		url : ctx+'/attachment/quereyAttachment',
		data : [{
			name : 'caseCode',
			value : caseCode
		}, {
			name : 'partCode',
			value : attachmentCode
		}],
		dataType : "json",
		success : function(data) {
			dataLength=data.length;
			//将返回的数据进行包装
			var trStr = "";
			$.each(data, function(indexAcc, value){
				trStr += "<a href='#' onClick='downLoad(\""+value.preFileAdress+"/__f.jpg\",\""+value.fileName+"\")' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
				trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' style='padding-bottom: 5px;padding-top: 5px;'>";
				trStr += "</a>";
			});
			$("#imgShow").append(trStr);
		},
		error : function(errors) {
			alert("产调加载失败");
			return false;
		}
	});
}

function getShowAttachment() {
	$.ajax({
		type : 'post',
		cache : false,
		async : true,//false同步，true异步
		dataType : 'json',
		url : ctx+'/attachment/quereyAttachment',
		data : [{
			name : 'caseCode',
			value : caseCode
		}, {
			name : 'partCode',
			value : attachmentCode
		}],
		dataType : "json",
		success : function(data) {
			dataLength=data.length;
			//将返回的数据进行包装
			var trStr = "";
			$.each(data, function(indexAcc, value){
				//trStr += "<a href='#' onClick='show(\""+value.preFileAdress+"/_f.jpg\")' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
				trStr += "<a href='#' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
				//trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' style='padding-bottom: 5px;padding-top: 5px;'>";
				trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/_f.jpg' style='padding-bottom: 5px;padding-top: 5px;width:100px;'>";
				trStr += "</a>";
			});
			$("#imgShow").append(trStr);
			$("#imgShow").viewer();
		},
		error : function(errors) {
			alert("产调加载失败");
			return false;
		}
	});
}