function downLoad(fileId, fileName) {
	window.location.href = ctx+"/attachment/resourcelibrary/downLoadResource.do?&pash="
		+ fileId+"&name="+fileName;
}

function show(fileId) {
	
	window.open(appCtx['shcl-image-web']+"/image/"+fileId);
	
	
//	var picUrl = appCtx['shcl-image-web']+"/image/"+fileId;
	
//	window.open(ctx+'/task/showPicture.jsp?picUrl='+picUrl);
	
//	window.open(ctx+"/task/guohuApprove/showPic?&picUrl="+picUrl);
}


function getShowAttachment() {
	var caseCode = $("#caseCode").val();
	$.ajax({
		type : 'post',
		cache : false,
		async : true,//false同步，true异步
		dataType : 'json',
		url : ctx+'/attachment/quereyAttachment',
		data : [{
			name : 'caseCode',
			value : caseCode
		}],
		dataType : "json",
		success : function(data) {
			dataLength=data.length;
			//将返回的数据进行包装
			var trStr = "";

			var oldType = "";
			$.each(data, function(indexAcc, value){
				if(value.partCode !='property_research'){
					if(value.preFileName!=oldType){
						if(oldType!=""){
							trStr += '</div></div>';
						}
						oldType=value.preFileName;
						trStr += '<div class="row ibox-content">';
						trStr += '<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">'+value.preFileName+'</label>';
						trStr += '<div class="col-sm-10 lightBoxGallery" style="text-align:left">';
					}
					trStr += "<a href='#' onClick='show(\""+value.preFileAdress+"/_f.jpg\")' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
					trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' style='padding-bottom: 5px;padding-top: 5px;'>";
					trStr += "</a>";
				}
			});
			$("#imgShow").append(trStr);
		},
		error : function(errors) {
		}
	});
}