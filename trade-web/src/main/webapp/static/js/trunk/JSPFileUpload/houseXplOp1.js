//图片的ID
		var picIdArr;
		//每个图片对应的类型
		var picTypeArr;
		//要删除的图片ID
		var pkIdArr=[];
		//是否关键实勘
		var isCrucial;
		//还回数据的长度
		var dataLength;
	//获取上传成功的图片的信息，包括ID，类型
	function getUploadPicOkInfo() {
		 //每次选择的时候清空
		picIdArr = [];
		picTypeArr = [];
	 	//图片的ID
	    var spans =$("input[name='uploadPicId']");
	    $.each(spans, function(i, item) {
	    	picIdArr.push(spans[i].value);	    	
	    });
	    //每个图片对应的类型
	    var selects=$("input[name='picTag']");
	    $.each(selects, function(j, item) {
	    	 picTypeArr.push(selects[j].value);
	    });
	    //必须上传图片
	    if(picIdArr.length<=0){
	    	$("#repoertTip1").show();
	    	 var tip="请先上传图片成功后再提交！";
	    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
	    	 return true;
	    }
	    var picDiv=$("div[name='allPicDiv1']");
	    //所选图片和上传的图片的数目要相同
	    if(picDiv.length!==spans.length){
	    	$("#repoertTip1").show();
	    	var tip="请成功上传所有的图片后再提交！";
	    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
	    	 return true;
	    }
	    return false;
	}
	//初始化所有当前实勘的图片
	$(function() {
		getExplPicByhouseCode();
	});
	//根据房屋编号查询所有的钥匙信息
	function getExplPicByhouseCode() {
		$.ajax({
					type : 'post',
					cache : false,
					async : true,//false同步，true异步
					dataType : 'json',
					url : ctx+'/houExpl/ExplPicShouByExplCode',
					data : [{
						name : 'explCode',
						value : explCode
					} ],
					success : function(data) {
						dataLength=data.content.length;
						isCrucial=data.content[0].isCrucial;
						//实勘描述
						$("#oldDesc").attr("value",data.content[0].desc);
						$("#newDesc").attr("value",data.content[0].desc);
						var trStr = "",trStr1 = "",trStr2 = "",trStr3 = "",trStr4 = "",trStr5 = "",trStr6 = "",trStr7 = "";
						//将返回的数据进行包装
						$.each(data.content,function(index, value) {
							if(data.content[index].framePart==100531001){
								trStr+="<div id='picContainer"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr+="<div class=\"preview span12\">";
								trStr+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_400_f.jpg' alt=''style='width:175px;height:120px'>";
								trStr+="</div>";
								trStr+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr+="<button onclick=\"romoveDiv('picContainer',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr+="<i class=\"icon-remove\"></i>";
								trStr+="</button>";
								trStr+="</div>";
								trStr+="</div>";
							}
							if(data.content[index].framePart==100531002){
								trStr1+="<div id='picContainer1"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr1+="<div class=\"preview span12\">";
								trStr1+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr1+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr1+="</div>";
								trStr1+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr1+="<button onclick=\"romoveDiv('picContainer1',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr1+="<i class=\"icon-remove\"></i>";
								trStr1+="</button>";
								trStr1+="</div>";
								trStr1+="</div>";
							}
							if(data.content[index].framePart==100531003){
								trStr2+="<div id='picContainer2"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr2+="<div class=\"preview span12\">";
								trStr2+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr2+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr2+="</div>";
								trStr2+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr2+="<button onclick=\"romoveDiv('picContainer2',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr2+="<i class=\"icon-remove\"></i>";
								trStr2+="</button>";
								trStr2+="</div>";
								trStr2+="</div>";
							};
							if(data.content[index].framePart==100531004){
								trStr3+="<div id='picContainer3"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr3+="<div class=\"preview span12\">";
								trStr3+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr3+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr3+="</div>";
								trStr3+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr3+="<button onclick=\"romoveDiv('picContainer3',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr3+="<i class=\"icon-remove\"></i>";
								trStr3+="</button>";
								trStr3+="</div>";
								trStr3+="</div>";
							}
							if(data.content[index].framePart==100531005){
								trStr4+="<div id='picContainer4"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr4+="<div class=\"preview span12\">";
								trStr4+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr4+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr4+="</div>";
								trStr4+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr4+="<button onclick=\"romoveDiv('picContainer4',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr4+="<i class=\"icon-remove\"></i>";
								trStr4+="</button>";
								trStr4+="</div>";
								trStr4+="</div>";
							}
							if(data.content[index].framePart==100531006){
								trStr5+="<div id='picContainer5"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr5+="<div class=\"preview span12\">";
								trStr5+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr5+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr5+="</div>";
								trStr5+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr5+="<button onclick=\"romoveDiv('picContainer5',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr5+="<i class=\"icon-remove\"></i>";
								trStr5+="</button>";
								trStr5+="</div>";
								trStr5+="</div>";
							}
							if(data.content[index].framePart==100531007){
								trStr6+="<div id='picContainer6"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr6+="<div class=\"preview span12\">";
								trStr6+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr6+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr6+="</div>";
								trStr6+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr6+="<button onclick=\"romoveDiv('picContainer6',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr6+="<i class=\"icon-remove\"></i>";
								trStr6+="</button>";
								trStr6+="</div>";
								trStr6+="</div>";
							}
							if(data.content[index].framePart==100531008){
								trStr7+="<div id='picContainer7"+data.content[index].pkId+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:120px;border:1px solid #ccc;margin-bottom:20px;line-height:120px;text-align:center;border-radius:4px;float:left;\">";
								trStr7+="<div class=\"preview span12\">";
								trStr7+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+data.content[index].pkId+"\" />";
								trStr7+="<img src='"+imageweb+"/image/"+data.content[index].pictureNo+"/300_300_f.jpg' alt='' style='width:175px;height:120px'>";
								trStr7+="</div>";
								trStr7+="<div class=\"delete span2\" style=\"margin-left:85%;margin-top:-179px;\">";
								trStr7+="<button onclick=\"romoveDiv('picContainer7',"+data.content[index].pkId+");\" class=\"btn red\" style=\"margin-top:-340px;line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;margin-left:570%;\">";
								trStr7+="<i class=\"icon-remove\"></i>";
								trStr7+="</button>";
								trStr7+="</div>";
								trStr7+="</div>";
							}
						});
						$("#picContainer").append(trStr);
						$("#picContainer1").append(trStr1);
						$("#picContainer2").append(trStr2);
						$("#picContainer3").append(trStr3);
						$("#picContainer4").append(trStr4);
						$("#picContainer5").append(trStr5);
						$("#picContainer6").append(trStr6);
						$("#picContainer7").append(trStr7);
					},
					error : function(errors) {
						Modal.alert({msg:"实勘查询失败"});
						return false;
					}
				});
	}
	
	//批量删除
	function deletePicBatch(){
		if(pkIdArr==''){
			$("#repoertTip1").show();
	    	 var tip="请选择至少一张图片删除!";
	    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
			return;
		}
			$.ajax({
				type : 'post',
				cache : false,
				async : true,//false同步，true异步
				dataType : 'json',
				url : ctx+'/houExpl/updPicStatus',
				data : [{
					name : 'houseId',
					value : houseId
				},{
					name : 'delCode',
					value : delCode
				},{
					name : 'explCode',
					value : explCode
				},{
					name : 'pkid',
					value : pkIdArr
				},{
					name : 'isCrucial',
					value : isCrucial
				},{
					name : 'isValid',
					value : isValid
				},{
					name : 'status',
					value : status
				},{
					name : 'oldDesc',
					value : $("#oldDesc").val()
				},{
					name : 'newDesc',
					value : $("#newDesc").val()
				} ],
				success : function(data) {
					if(data.success){
						Modal.confirm({
						    msg: data.message,
						    title: '提示',
						    btnok: '确定'
						  }); 
					    $(".cancel").hide();
					    $(".btn-primary").one("click",function(){
					    	parent.$.fancybox.close();
					    });
					}else if(!data.success) {
						pkIdArr==[];
						Modal.alert({msg:data.message});
					}
				}
			});
		//});
	}
	//添加图片在原来实勘上
	function subAddFrom() {
		//获取上传成功的图片的信息，包括ID，类型
		if(getUploadPicOkInfo()){
			return;
		};
		if(picIdArr==''){
			$("#repoertTip1").show();
	    	 var tip="当前没有要新增的图片数据！";
	    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
	    	return;
		};
		$.ajax({
			cache : true,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+'/houExpl/insertOldExpl',
			dataType : "json",
			data : [ {
				name : 'delCode',
				value : delCode
			},{
				name : 'explCode',
				value : explCode
			}, {
				name : 'houseId',
				value : houseId
			}, {
				name : 'pictureNo',
				value : picIdArr
			}, {
				name : 'framePart',
				value : picTypeArr
			},{
				name : 'isCrucial',
				value : isCrucial
			},{
				name : 'isValid',
				value : isValid
			},{
				name : 'status',
				value : status
			},{
				name : 'oldDesc',
				value : $("#oldDesc").val()
			},{
				name : 'newDesc',
				value : $("#newDesc").val()
			}],
			success : function(data) {
					if(data.success){
						Modal.confirm({
						    msg: data.message,
						    title: '提示',
						    btnok: '确定'
						  }); 
					    $(".cancel").hide();
					    $(".btn-primary").one("click",function(){
					    	parent.$.fancybox.close();
					    });
					}else if(!data.success) {
						Modal.alert({msg:data.message});
					}
			},
			error : function(errors) {
				Modal.alert({msg:"原来实勘添加出错"+errors});
			}
		});
	}
	//修改图片在原来实勘上
	function subUpdFrom() {
		if(pkIdArr==''){
			$("#repoertTip1").show();
	    	 var tip="请选择一张要修改的照片！！！";
	    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
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
				url : ctx+'/houExpl/updatePic',
				dataType : "json",
				data : [{
					name : 'pkid',
					value : pkIdArr
				},{
					name : 'isCrucial',
					value : isCrucial
				} ,{
					name : 'isValid',
					value : isValid
				},{
					name : 'status',
					value : status
				},{
					name : 'delCode',
					value : delCode
				},{
					name : 'explCode',
					value : explCode
				}, {
					name : 'houseId',
					value : houseId
				}, {
					name : 'pictureNo',
					value : picIdArr
				}, {
					name : 'framePart',
					value : picTypeArr
				},{
					name : 'oldDesc',
					value : $("#oldDesc").val()
				},{
					name : 'newDesc',
					value : $("#newDesc").val()
				}],
				success : function(data) {
						if(data.success){
							Modal.confirm({
							    msg: data.message,
							    title: '提示',
							    btnok: '确定'
							  }); 
						    $(".cancel").hide();
						    $(".btn-primary").one("click",function(){
						    	parent.$.fancybox.close();
						    });
						}else if(!data.success) {
							pkIdArr==[];
							Modal.alert({msg:data.message});
						}
				},
				error : function(errors) {
					Modal.alert({msg:"原来实勘修改出错"+errors});
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
				$("#repoertTip1").show();
		    	 var tip="删除当前图片失败。";
		    	 $("#repoertTip1").html("<img src=\"/sales-web/static/images/icons/cry.png\" style=\"margin-right:10px;\">"+tip);
				return;
			}
		}
		$("#"+type+pkid).remove();
	}
	
	
	//保存
	function deleteAndModify(){
		var picDiv=$("div[name='allPicDiv1']");
	    //所选图片和上传的图片的数目要相同
	    if(picDiv.length>0){
	    	//获取复选框的长度
			var input=$("input[name='pic']");
			//图片的ID
		    var spans =$("input[name='uploadPicId']");
		    //如果原来数据的长度等于复选框的长度--》调用新增的方法
			if(dataLength==input.length && spans.length>0){
				subAddFrom();
			}else
			//如果原来数据的长度大于复选框的长度且有新的图片数据--》调用修改的方法
			if(dataLength>input.length && spans.length>0){
				subUpdFrom();
			}
	    }
		if(juage()){
			return;
		}
		//dataLength
		//获取复选框的长度
		var input=$("input[name='pic']");
		//图片的ID
	    var spans =$("input[name='uploadPicId']");
	    var oldDesc=$("#oldDesc").val();
		var newDesc=$("#newDesc").val();
	    
		//如果原来数据的长度大于复选框的长度且没有新的图片数据--》调用删除的方法
		if(dataLength>input.length&&spans.length==0){
			deletePicBatch();
		}if(dataLength==input.length && spans.length==0&&trim(oldDesc)!=trim(newDesc)){
			modifyExplDescr();
		}else{
			Modal.alert({msg:"当前无操作！"});
		}
	}
	function addAndUpload(){
		//dataLength
		//获取复选框的长度
		var input=$("input[name='pic']");
		//图片的ID
	    var spans =$("input[name='uploadPicId']");
	    var picDiv=$("div[name='allPicDiv1']");
	    //所选图片和上传的图片的数目要相同
	    if(picDiv.length<=0){
	    	 return;
	    }
	    //如果原来数据的长度等于复选框的长度--》调用新增的方法
		if(dataLength==input.length && spans.length>0){
			subAddFrom();
		}else
		//如果原来数据的长度大于复选框的长度且有新的图片数据--》调用修改的方法
		if(dataLength>input.length && spans.length>0){
			subUpdFrom();
		}
	}
	function juage(){
		var fg;
		 var picDiv=$("div[name='allPicDiv1']");
		 if(picDiv.length>0){
			 fg=true;
		 }else{
			 fg=false;
		 }
		 return fg;
	}
	//去空格
	function trim(str){  
		return str.replace(/(^\s*)|(\s*$)/g, "");  
	}
	
	//修改实勘描述
	function modifyExplDescr(){
		$.ajax({
			cache : true,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+'/houExpl/modifyExplDescr',
			dataType : "json",
			data : [{
				name : 'explCode',
				value : explCode
			},{
				name : 'newDesc',
				value : $("#newDesc").val()
			},{
				name : 'isCrucial',
				value : isCrucial
			},{
				name : 'isValid',
				value : isValid
			}],
			success : function(data) {
					if(data.success){
						Modal.confirm({
						    msg: data.message,
						    title: '提示',
						    btnok: '确定'
						  }); 
					    $(".cancel").hide();
					    $(".btn-primary").one("click",function(){
					    	parent.$.fancybox.close();
					    });
					}else if(!data.success) {
						Modal.alert({msg:data.message});
					}
			},
			error : function(errors) {
				Modal.alert({msg:"实勘修改出错"+errors});
			}
		});
	}
