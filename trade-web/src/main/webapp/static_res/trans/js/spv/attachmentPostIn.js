/**
 * 签约
 * 
 */
var checkAtt = true;
var picIdArr;
// 每个图片对应的类型
var picTypeArr;
// 要删除的图片ID
var pkIdArr = [];
//图片Name
var picNameArr;
// 是否关键实勘
var isCrucial;
// 还回数据的长度
var dataLength=0;
// 获取上传成功的图片的信息，包括ID，类型
function getUploadPicOkInfo() {
	// 每次选择的时候清空
	picIdArr = [];
	picTypeArr = [];
	picNameArr = [];
	// 图片的ID(attachId)
	var spans = $("input[name='preFileAdress']");
	$.each(spans, function(i, item) {
		picIdArr.push(spans[i].value);
	});
	// 每个图片对应的类型
/*	var selects = $("input[name='picTag']");
	$.each(selects, function(j, item) {
		picTypeArr.push(selects[j].value);
	});*/
	// 必须上传图片
	if (picIdArr.length <= 0) {
		window.wxc.alert("请先上传图片成功后再提交！");
		return true;
	}
	var picDiv = $("div[name='allPicDiv1']");
	// 所选图片和上传的图片的数目要相同
	if (picDiv.length !== spans.length) {
		window.wxc.alert("请先上传图片成功后再提交！");
		return true;
	}
	var picNames = $("input[name='picName']");
	$.each(picNames, function(j, item) {
		picNameArr.push(item.value);
	});
	return false;
}
//初始化
$(function() {
/*	*//**上传备件初始化*//*
	var updFun = function(e) {
		var that = $(this).data('blueimp-fileupload')
				|| $(this).data('fileupload');
		that._resetFinishedDeferreds();
		that._transition($(this).find('.fileupload-progress'))
				.done(function() {
					that._trigger('started', e);
				});
	};

	$.each(idList, function(index, value){
		AistUpload.init('picFileupload'+value, 'picContainer'+value,
				'templateUpload'+value, 'templateDownload'+value, updFun,'/(gif|jpg|jpeg|bmp|png|tif|tiff)/i');
		*//**监听 div 执行自动上传*//*
		$("#picContainer"+value).bind('DOMNodeInserted', function(e) {
			var picDiv=$("div[name='allPicDiv1']");
			var input=$("input[name='picTag']");
			if(picDiv.length > input.length) {
				index++;
				if(index % 2 == 0) {
					//alert("执行自动上传");
					index = 0;
					$("#startUpload").click();
				}
			}
		});
	});
	*//**上传备件初始化结束*//*
    getExplPicByhouseCode();*/

});

//
function getExplPicByhouseCode() {
	$.ajax({
		type : 'post',
		cache : false,
		async : true,//false同步，true异步
		dataType : 'json',
		url : ctx+'/spv/quereyCashFolwApplyAttachments',
		data : [ {
			name : 'cashFlowApplyCode',
			value : $("#businessKey").val()
		}],
		dataType : "json",
		success : function(attachList) {
//					dataLength=data.attList.length;
			//将返回的数据进行包装
			$.each(attachList, function(index, attach){
				//alert(5);
				//实勘描述
				if(!$("#picContainer"+attach.pkid)[0])return true;
				var trStr = "";
				dataLength = attachList.length;
				trStr+="<div id='picContainers"+attach.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
				trStr+="<div class=\"preview span12\">";
				trStr+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+attach.pkid+"\" />";
				trStr+="<img src='"+appCtx['shcl-image-web'] +"/image/"+attach.attachId+"/80_80_f.jpg' alt='地方'>";
				trStr+="</div>";
				trStr+="<div class=\"delete span2\" style=\"margin-left: 85%; margin-top: -120px;\">";
				trStr+="<button onclick=\"romoveDiv('picContainers',"+attach.pkid+");\" class=\"btn red\""; 
				trStr+="style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
				trStr+="<i class=\"icon-remove\"></i>";
				trStr+="</button>";
				trStr+="</div>";
				trStr+="</div>";					
				$("#picContainer"+attach.pkid).append(trStr);
			});
		},
		error : function(errors) {
			window.wxc.error("加载失败！");
			return false;
		}
	});
}
//添加图片在原来实勘上
function subAddFrom() {
	//获取上传成功的图片的信息，包括ID，类型
	if(getUploadPicOkInfo()){
		return;
	};
	if(picIdArr==''){
		window.wxc.alert("当前没有要新增的图片数据！");
    	return;
	};
	$.ajax({
		cache : true,
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+'/spv/saveSpvReceipt',
		dataType : "json",
		data : [ {
			name : 'pictureNo',
			value : picIdArr
		},{
			name : 'cashFlowCode',
			value : $("#ToSpvCashFlowPkid").val(),
		}
		, {
			name : 'framePart',
			value : picTypeArr
		},{
			name : 'picName',
			value :  picNameArr
		},{
			name : 'pkIdArr',
			value :  pkIdArr
		}],
		success : function(data) {
				if(data){
//					alert("附件已修改。");
				    $(".cancel").hide();
				    $(".btn-primary").one("click",function(){
				    	if(parent && parent.$ && parent.$.fancybox){
					    	if(typeof parent.$.fancybox.close =='function'){
					    		parent.$.fancybox.close();
					    	}
				    	}
				    });
				}else if(!data) {
					Modal.alert({msg:data.message});
				}
		},
		error : function(errors) {
			window.wxc.error("附件添加出错。");
		}
	});
}

//修改图片在原来实勘上
function subUpdFrom() {
	if(pkIdArr==''){
		alert("请选择一张要修改的照片！！！");
    	return;
    }
	//获取上传成功的图片的信息，包括ID，类型
	if(getUploadPicOkInfo()){
		return;
	};
	
		$.ajax({
			cache : true,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+'/spv/saveSpvReceipt',
			dataType : "json",
			data : [{
				name : 'pictureNo',
				value : picIdArr
			},{
				name : 'cashFlowCode',
				value : $("#ToSpvCashFlowPkid").val(),
			}
			, {
				name : 'framePart',
				value : picTypeArr
			},{
				name : 'picName',
				value :  picNameArr
			},{
				name : 'pkIdArr',
				value :  pkIdArr
			}],
			success : function(data) {
					if(data){
//						alert("附件已修改。");
					    $(".cancel").hide();
					    $(".btn-primary").one("click",function(){
					    	parent.$.fancybox.close();
					    });
					}else if(!data) {
						window.wxc.error("附件修改出错"+errors);
					}
			},
			error : function(errors) {
				window.wxc.error("附件修改出错"+errors);
			}
		});
}

/**
 * 删除div
 * type 户型图还是其他等
 * pkid 当前户型图等div里面的某个div的ID（图片ID）
 */
function romoveDiv(type,pkid){
	if(typeof pkid=='number'){
		pkIdArr.push(pkid);
		if(pkIdArr==''){
			window.wxc.alert("删除当前图片失败。");
		} 
	}
	$("#"+type+pkid).remove();
}


//批量删除
function deletePicBatch(){
	if(pkIdArr==''){
		window.wxc.alert("请选择至少一张图片删除!");
		return false;
	}
	var aa = false;
		$.ajax({
			type : 'post',
			cache : false,
			async : false,//false同步，true异步
			dataType : 'json',
			url : ctx+'/attachment/delAttachment',
			data : [{
				name : 'pkIdArr',
				value : pkIdArr
			}],
			success : function(data) {
				if(data.success){
				//	alert('提示:图片已删除。'); 
				    $(".cancel").hide();
				    $(".btn-primary").one("click",function(){
				    	parent.$.fancybox.close();
				    });
				    aa = true;
				}else if(!data) {
					pkIdArr==[];
					window.wxc.error(data.message);
				}
			}
		});
		
		return aa;
	//});
}

function checkAttachment() {
	$.each(idList, function(index, value){
		var length = $("#picContainer"+value).find("img").length;
		if(length == 0) {
			window.wxc.alert("请上传备件！");
			checkAtt = false;
			return false;
		} else {
			checkAtt = true;
		}
	});
	return checkAtt;
}

//保存
function deleteAndModify(){
	//alert("deleteAndModify");
	var picDiv=$("div[name='allPicDiv1']");
	//alert(picDiv);
	//alert(picDiv.length);
    //所选图片和上传的图片的数目要相同
    if(picDiv.length>0){
    	//alert(picDiv.length);
    	//获取已知文件类型的长度   已保存的文件数量
		var input=$("input[name='pic']");
		//图片的ID 新增的文件数量
	    var spans =$("input[name='preFileAdress']");
	    if(spans.length < picDiv.length) {
	    	window.wxc.alert("你有未上传的完成的文件，请稍候再试！");
	    	return false;
	    }
	    //如果原来数据的长度等于复选框的长度--》调用新增的方法
		if(dataLength==input.length && spans.length>0){
			subAddFrom();
		}else
		//如果原来数据的长度大于复选框的长度且有新的图片数据--》调用修改的方法
		if(dataLength>input.length && spans.length>0){
			subUpdFrom();
		}
    } else {
    	//dataLength
    	//获取复选框的长度
    	var input=$("input[name='pic']");
    	//图片的ID
        var spans =$("input[name='preFileAdress']");
        
    	//如果原来数据的长度大于复选框的长度且没有新的图片数据--》调用删除的方法
    	if(dataLength>input.length&&spans.length==0){
    		var flag = deletePicBatch();
    		if(!flag) {
    			return false;
    		}
    	}
    }
	return true;
}
