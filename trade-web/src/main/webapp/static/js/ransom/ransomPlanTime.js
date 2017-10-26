$(document).ready(function() {
	$('.input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true
	});
	var ctx = "${ctx}";
	debugger;
	getPlanDate();

});

function getPlanDate(){
	debugger;
	var ransomCode = $('#ransomCode').val();
	var url = "/ransomList/getPlanTimeDate?ransomCode="+ransomCode;
	$.ajax({
		async: true,
		type:'POST',
		url:ctx+url,
		dataType:'json',
		success:function(data){
			if(data.success){						
				var obj = data.content;
				//所有排列
				if(obj.allPartCodes){
					var html = "";
					//已存在的计划时间数据
					var planTimes = obj.planTimes;
					$.each(obj.allPartCodes,function(i,pn){
						var estTime = null;
						var remark = null;
						$.each(planTimes,function(pi,plan){
							if(plan.partCode == pn.partCode){
								estTime = plan.estPartTime;
								remark = plan.remark;
								return false;
							}
						});
						html += '<div class="line"><div class="form_content">';
						html += '<label class="control-label sign_left_small select_style mend_select" id="partCode5">'+pn.name+'时间</label>';
						html += '<div class="input-group sign-right dataleft input-daterange"  >';
						html += '<input id="'+pn.partCode+'T" name="'+pn.partCode+'time"  class="form-control data_style" type="text" value="'+estTime+'"></div></div>';
						html += '<div class="form_content"><label class="control-label sign_left_small">变更理由</label>';
						html += '<input id="'+pn.partCode+'M" name="'+pn.partCode+'remark"  class="teamcode input_type" value="'+remark+'" />';
						html += '</div></div>';
					});
					$('#dataBody').empty();
					$('#dataBody').html(html);
					
					//disbale判断
					var status = obj.ransomStatus;
					if(status =='3' || status == '4'){
						$.each(obj.allPartCodes,function(i,pn){
							$("#"+pn.partCode+"T").prop("disabled", "disabled");
							$("#"+pn.partCode+"M").prop("disabled", "disabled");
						});
					}else if(status == '2'){
						 if(obj.diyaNum == 1){
							 //一抵申请无需操作
							 if(obj.partOrders[0].partCode !='APPLY'){
								 //当前任务顺序下标
								 var index = obj.partOrders[0].order;
								 //每个下标小于当前任务环节下标，disabled
								 $.each(obj.allPartCodes,function(i,pn){
									 if(pn.order < index){
										 $("#"+pn.partCode+"T").prop("disabled", "disabled");
										 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
									 }
								 });
							 }
						 }else if(obj.diyaNum == 2 ){
							 //只有一个任务:申请，面签，回款结清,小于的都disabled
							if(obj.partOrders.length == 1){
								var index = obj.partOrders[0].order;
								if(obj.partOrders[0].partCode == 'SIGN' || obj.partOrders[0].partCode == 'PAYCLEAR'){
									$.each(obj.allPartCodes,function(i,pn){
										 if(pn.order < index){
											 $("#"+pn.partCode+"T").prop("disabled", "disabled");
											 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
										 }
									 });
								}
								//2个任务
							} else if(obj.partOrders.length == 2){
								var partCode = obj.partOrders[0].partCode;
								//TODO 判断第一个任务是否是二抵，是，另一个即一抵，不是，这个则是一抵，另一个是二抵
								var flag = false;
								$.each(obj.twoPartCodes,function(i,tp){
									if(tp.partCode == partCode){
										flag = true;
										return false;
									}
								});
								//第一个任务是二抵，操作
								if(flag){
									if(partCode != 'PAYLOAN_TWO'){
										//第一个任务下标
										var index = obj.partOrders[0].order;
										$.each(obj.twoPartCodes,function(i,pn){
											 if(pn.order < index){
												 $("#"+pn.partCode+"T").prop("disabled", "disabled");
												 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
											 }
										 });	
									}
									var partCode1 = obj.partOrders[1].partCode;
									//第二个任务下标
									var index = obj.partOrders[1].order;
									$.each(obj.onePartCodes,function(i,pn){
										 if(pn.order < index){
											 $("#"+pn.partCode+"T").prop("disabled", "disabled");
											 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
										 }
									 });
								}else{//第一个任务不是二抵，操作
									var index = obj.partOrders[0].order;
									$.each(obj.onePartCodes,function(i,pn){
										 if(pn.order < index){
											 $("#"+pn.partCode+"T").prop("disabled", "disabled");
											 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
										 }
									 });
									//第二个任务下标
									var index2 = obj.partOrders[1].order;
									var partCode2 = obj.partOrders[1].partCode;
									if(partCode2 != 'PAYLOAN_TWO'){
										$.each(obj.twoPartCodes,function(i,pn){
											 if(pn.order < index2){
												 $("#"+pn.partCode+"T").prop("disabled", "disabled");
												 $("#"+pn.partCode+"M").prop("disabled", "disabled"); 										 
											 }
										 });
									}
								}
							}
						 }
					}
				}	
			}
		},
		error : function(error) {
			window.wxc.error(error);
		}
	});
}

/**
 * 加载赎楼变更时间信息,已完成环节控件不可更改
 * @returns
 */
function loadTimeInfo(){
	debugger;
	//赎楼环节
	var property = $("#taskCode").val();
	
	for(var i = 0; i < $(".form_content").length/2; i++){
		
		
	}
}

/**
 * 保存 i=1 ，查看变更记录 i = 2
 * @param i
 * @returns
 */
function submitChangeRecord(i){
	debugger;
	var ransomCode = $("#ransomCode").val();
	var count = $("#count").val();
	var ransomVo = getParams();
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:{ransomVo:JSON.stringify(ransomVo),flag:i,ransomCode:ransomCode,count:count},
		type:"POST",
		success: function(data){
			
			if(data.code == "1"){
				window.wxc.alert("赎楼计划时间修改保存成功！");
				window.location.href = ctx + "/ransomList/ransomDetail?ransomCode=" + ransomCode;
			}else if(data.code == "2"){
				window.location.href = ctx + "/ransomList/ransomChangeRecord?ransomCode=" + ransomCode;
			}
		},
		error: function(data){
			window.wxc.error(data.message);
		}
	});
}

/**
 * 获取参数
 * @returns
 */
function getParams(){
	
	var count = $("#count").val();
	debugger;
	var array = new Array();
	for(var i = 0;i < $(".line input").length / 2;i++){
		var estPartTime = $("input[name='estPartTime" + i + "']").val();
		var remark = $("input[name='remark" + i + "']").val();
		var partCode = $("#partCode" + i + "").text();
		
		if(partCode == '申请时间'){
			partCode = 'APPLY';
		}else if(partCode == '面签时间'){
			partCode = 'SIGN';
		}else if(partCode == '陪同还贷时间' && count == 0){
			partCode = 'PAYLOAN_ONE';
		}else if(partCode == '陪同还贷时间' && count == 1){
			partCode = 'PAYLOAN_TWO';
		}else if(partCode == '注销抵押时间' && count == 0){
			partCode = 'CANCELDIYA_ONE';
		}else if(partCode == '注销抵押时间' && count == 1){
			partCode = 'CANCELDIYA_TWO';
		}else if(partCode == '领取产证时间' && count == 0){
			partCode = 'RECEIVE_ONE';
		}else if(partCode == '领取产证时间' && count == 1){
			partCode = 'RECEIVE_TWO';
		}else if(partCode == '回款结清时间'){
			partCode = 'PAYCLEAR';
		}
		
		var resJson = {
//			ransomCode:ransomCode,
			partCode : partCode,	
			estPartTime:estPartTime,
			remark:remark
		}
		
		array.push(resJson);
	}
	
	return array;
}
	/**
	 * 根据日期字符串转换成日期
	 * 控件字符串转换成日期格式 "2017-10-11"
	 * @returns
	 */
	function strToDate(str){
		
		var regEx = new RegExp("\\-","gi");
		str=str.replace(regEx,"-");
		
		return str;
	}