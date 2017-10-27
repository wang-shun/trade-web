$(document).ready(function() {

	var ctx = "${ctx}";
	getPlanDate();

});
var allPartCodes;
var planDates;
var changeData;
function getPlanDate(){
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
					allPartCodes = obj.allPartCodes;
					var html = "";
					//已存在的计划时间数据
					var planTimes = obj.planTimes;
					planDates = planTimes;
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
						html += '<input id="'+pn.partCode+'T" name="'+pn.partCode+'time"  class="form-control data_style" type="text" value="'+TimeToDate(estTime)+'"></div></div>';
						html += '<div class="form_content"><label class="control-label sign_left_small">变更理由</label>';
						html += '<input id="'+pn.partCode+'M" name="'+pn.partCode+'remark"  class="teamcode input_type" value="'+checkNull(remark)+'" />';
						html += '</div></div>';
					});
					$('#dataBody').empty();
					$('#dataBody').html(html);
					
					/**
					 * 2017/10/26
					 */
					//disbale判断
					var status = obj.ransomStatus;
					if(status =='3' || status == '4'){
						$.each(obj.allPartCodes,function(i,pn){
							$("#"+pn.partCode+"T").prop("disabled", "disabled");
							$("#"+pn.partCode+"M").prop("disabled", "disabled");
						});
					}else if(status == '2'){
						 /*if(obj.diyaNum == 1){
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
						 }*/
						
						/**
						 * 以上想复杂了，可以通过查询已经完成的所有任务流节点直接判断
						 * @author wbcaiyx 2017/10/27
						 * 
						 */
						var tasks =  obj.tasks;
						if(tasks && tasks.length > 0){
							$.each(obj.allPartCodes,function(i,pn){
								$.each(tasks,function(k,tk){
									if(tk.taskDefinitionKey == pn.partCode){
										$("#"+pn.partCode+"T").prop("disabled", "disabled");
										$("#"+pn.partCode+"M").prop("disabled", "disabled");
									}
								});
							});
						}				
					}
					 $('.input-daterange').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked',
							language : 'zh-CN'
						});
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
 * 计划时间更新
 * @author wbcaiyx
 */
function submitChangeRecord(){

	var ransomCode = $("#ransomCode").val();
	changeData = new Array();
	if(checkFormAndgetChangeData()){
		//新增的计划时间数据
		var newData = getnewParams();
		var data = {};
		data.newData = newData;
		data.changeData = changeData;
		data.ransomCode = ransomCode;
		
		$.ajax({
			url: ctx + "/ransomList/updateRansomPlanTime",
			dataType:"json",
			data:{ransomVo:JSON.stringify(data)},
			type:"POST",
			success: function(data){
				if(data.success){
					window.wxc.alert("赎楼计划时间修改保存成功！");
					window.location.href = ctx + "/ransomList/ransomDetail?ransomCode=" + ransomCode;
				}else{
					window.wxc.error(data.exception);
				}
				
			},
			error: function(data){
				window.wxc.error(data.message);
			}
		});
	}
	
}

function checkChangeRecord(){
	var ransomCode = $("#ransomCode").val();
	window.location.href = ctx + "/ransomList/ransomChangeRecord?ransomCode=" + ransomCode;
}

/**
 * 检查已有的数据是否被变更，变更需要理由
 * @author wbcaiyx
 */
function  checkFormAndgetChangeData(){
	//先挑选可以修改部分，再判断原先是否有数据，最后获取数据更新
	var flag = true;
	$.each(planDates,function(i,pd){
		var disabled = $('#'+pd.partCode+'T').attr('disabled');
		if(!disabled || disabled == undefined){
			var newDate = $('#'+pd.partCode+'T').val();
			var reason = $('#'+pd.partCode+'M').val();
			if(newDate && newDate.length > 0 && newDate != TimeToDate(pd.estPartTime)){
				if(reason ==null || reason == ''){
					window.wxc.alert("变更理由为必填项!");
					$('#'+pd.partCode+'M').focus();
					$('#'+pd.partCode+'M').css('border-color',"red");
					flag = false;
					return false;
				}else{	
					//取原已有数据的更新数据
					var resJson = {
							partCode : pd.partCode,	
							oldEstPartTime:pd.estPartTime,
							newEstPartTime:new Date(newDate),
							estPartTime:new Date(newDate),
							reason:reason
						}
					changeData.push(resJson);
				}	
			}
		}
	});
	return flag;
}

/**
 * 新增的计划时间数据
 *	@author wbcaiyx
 */
function getnewParams(){
	var newTimeData = new Array();
	$.each(allPartCodes,function(i,pd){
		var disabled = $('#'+pd.partCode+'T').attr('disabled');
		var timeVal = $('#'+pd.partCode+'T').val();
		if((!disabled || disabled == undefined) && timeVal && timeVal.length > 0){
			var flag = true;
			$.each(planDates,function(p,pda){
				if(pda.partCode == pd.partCode){
					flag = false;
					return false;
				}
			});
			//原已有计划时间的数据不要
			if(flag){
				var resJson = {
						partCode : pd.partCode,	
						estPartTime:new Date(timeVal)
					}
				newTimeData.push(resJson);
			}
		}
	});
	return newTimeData;
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
			reason:remark
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
	
	
	function TimeToDate(dateTime){
	    if(dateTime){
	    	var date = new Date(dateTime);
	 	    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	    }
	    return '';
	}
	
	function checkNull(data){
		if(data){
			return data;
		}
		return '';
	}