	var divIndex = $("div div[name='div_de'").length;
	var flowDivIndex = $("div div[name='div_deflow'").length;
	var delIds = "";
	var delFlowIds = "";
	/*function getDivIndex(){
		alert($("div div[name='div_de'").length);
	}
	getDivIndex();*/
	function addLine() {

	var txt = '<div class="form-group" id="div_'+divIndex+'" name="div_de">';
		txt += '<label class="col-sm-2 control-label" style="padding-right:5px;">解除条件'+(divIndex+1)+'</label>';
		txt	+= '<div class="col-md-2" style="padding-left:10px;width:17%">';
		txt += '<input type="text" class="form-control" id="deCondition_'+divIndex+'" name="deCondition" >';
		txt += '</div>';
		txt += '<label class="col-sm-1 control-label" style="width:11%">解除金额</label>';
		txt += '<div class="col-md-2" style="padding-left:14px;width:16%">';
		txt += '<input type="text" class="form-control" id="deAmount_'+divIndex+'" name="deAmount" onkeyup="checknum(this)">';
		txt += '</div>';
		
		txt += '<label class="col-sm-2 control-label" style="width:11.7777%;" >预计解除日期</label>';
		txt += '<div class="col-sm-3 input-group" style="width:24.33333%">';
		txt += '<div class="input-group date" id="preTime_'+divIndex+'">';

		txt += '<span class="input-group-addon"><i class="fa fa-calendar"></i></span>';
		txt += '<input type="text" class="form-control"  name="dePreTime" value="">';
		txt += '</div>';
		txt += '<span class="input-group-addon"><a href="javascript:removeLine('+divIndex+');">删除</a></span></div>';

		txt += '</div>';
	
		// alert(txt);
		$("#addLine").before(txt);
		$("#preTime_"+divIndex).datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		divIndex++;
	}
	function removeLine(index) {
		var delId = $("#div_"+index).find("input[name='pkid']").val();
		if(delId != null && delId!=""){
			delIds += delId+",";
		}

		$("#div_" + index).remove();
		
	}
	function addFlowLine(){
		var txt = '<div class="form-group" id="deflowdiv_'+flowDivIndex+'" name="div_deflow">';
		txt += '<input type="hidden" name="pkid" value="" />';
		txt += '<input type="hidden" name="flowDirection"  value="" /> ';
		txt += '<label class="col-sm-2 control-label" style="padding-right:5px;">出账金额</label>';
		txt += '<div class="col-sm-2" style="padding-left:10px;width:17%">';
		txt += '<input type="text" class="form-control"  name="flowAmount" value="" onkeyup="checknum(this)">';
		txt += '</div>';
		txt += '<label class="col-sm-1 control-label" style="width:11%">实际出账时间</label>';
		txt += '<div class="col-sm-6 input-group" style="padding-left:12px;width:52%">';
		txt += '<div class="input-group date" id="deflowDate_'+flowDivIndex+'">';
		txt += '<span class="input-group-addon"><i class="fa fa-calendar"></i></span>';
		txt += '<input type="text" class="form-control"  name="flowTime" value="">';
		txt += '</div>';
		txt += '<span class="input-group-addon"><a href="javascript:removeFlowLine('+flowDivIndex+');">删除</a></span></div>';	
		txt += '</div>';
		$("#addFlowLine").before(txt);
		$("#deflowDate_"+flowDivIndex).datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		flowDivIndex++;
		
	}
	
	function removeFlowLine(index) {
		var delFlowId = $("#deflowdiv_"+index).find("input[name='pkid']").val();
		if(delFlowId != null && delFlowId != ""){
			delFlowIds += delFlowId + ",";
		}
		$("#deflowdiv_" + index).remove();		
	}		
	function getSpvCompany(){
		$.ajax({
			url:ctx+"/manage/querySpvCompany",
			method:"post",
			dataType:"json",
			data:{},
			success:function(data){
				var foc = $("#spvInsti");
				foc.html("");
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						foc.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
					}
				}
			}
		});
	}
	function checknum(obj){
		obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
		obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}
	function checkform(){
		if($("#spvCode").val()==""){
			alert("请输入监管协议号！");
			$("#spvCode").focus();
			return false;
		}else if($("#amount").val() == ""){
			alert("请输入监管金额！");
			$("#amount").focus();
			return false;
		}else if($("#signTime").val()==""){
			alert("请选择签约时间！");
			$("#signTime").focus();
			return false;
		}else if($("#flowAmount").val()==""){
			alert("请输入进账金额！");
			$("#flowAmount").focus();
			return false;
		}else if($("#in_flowTime").val()==""){
			alert("请输入进账时间！");
			$("#in_flowTime").focus();
			return false;
		}
		return true;
	}
		$(document).ready(
			function() {
				getSpvCompany();

				$("#saveBtn").click(function(){
					if(!checkform()){
						return;
					}
					deleteAndModify();
					if(checkAttachment()) {

					var toSpvDeCondList = [];
					var deAmount = $("input[name='deAmount']");
					var dePreTime = $("input[name='dePreTime']");
					var toCashFlowList = [];
					//解除条件
					$("input[name='deCondition']").each(function(index){
						var obj = new Object();
						obj.deCondition = $(this).val();
						obj.deAmount = deAmount[index].value;
						obj.pkid = $("#div_"+index).find("input[name='pkid']").val();
						obj.dePreTime = dePreTime[index].value;
						toSpvDeCondList.push(obj);
						
					});
					var flowTime = $("input[name='flowTime']");
					//出账
					 $("input[name='flowAmount']").each(function(index){
						var obj = new Object();
						obj.pkid=$("#deflowdiv_"+index).find("input[name='pkid']").val();
						obj.flowAmount = $(this).val();
						obj.flowTime = flowTime[index].value;
						toCashFlowList.push(obj);
					});
					
					//进账
					var cashFlow = new Object();
					cashFlow.flowAmount = $("#inCashFlow").find("input[name='in_flowAmount']").val();
					cashFlow.flowTime = $("#inCashFlow").find("input[name='in_flowTime']").val();
					cashFlow.pkid = $("#inCashFlow").find("input[name='pkid']").val();

					if(delIds.length > 0){
						delIds = delIds.substring(0,delIds.length-1);
					}
					if(delFlowIds.length > 0){
						delFlowIds = delFlowIds.substring(0,delFlowIds.length-1);
					}
		        	$.ajax({
		        		url:ctx+"/spv/saveSpv",
		        		method:"post",
		        		dataType:"json",
		        		data:{
		        			pkid:$("#pkid").val(),
		        			caseCode:$("#caseCode").val(),
		        			spvCode:$("#spvCode").val(),
		        			amount:$("#amount").val(),
		        			signTime:$("#signTime").val(),
		        			spvInsti:$("#spvInsti").val(),
		        			remark:$("#remark").val(),
		        			spvType:$("#spvType").val(),
		        			toSpvDeCondList:JSON.stringify(toSpvDeCondList),
		        			toCashFlow:JSON.stringify(cashFlow),
		        			toCashFlowList:JSON.stringify(toCashFlowList),
							delIds:delIds,
							delFlowIds:delFlowIds
		        		},
		        		   		        				        		    
		        		success:function(data){
		        			alert(data.message);
		        			if(data.content != null && data.content != ""){
		        				window.location.href=ctx+"/case/caseDetail?caseId="+data.content;
		        			}
		        		}
		        	});
					}
				});
				
				$("#date_1").datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
				
				$("#inDate_0").datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
	/*			$("#deDate_0").datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
				$("#outDate_0").datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});*/
				$("div div[name='div_de'").each(function(index){
					$(this).find("#preTime_"+index).datepicker({
						format : 'yyyy-mm-dd',
						weekStart : 1,
						autoclose : true,
						todayBtn : 'linked',
						language : 'zh-CN'
					});
				});
				$("div div[name='div_deflow'").each(function(index){
					$(this).find("#deflowDate_"+index).datepicker({
						format : 'yyyy-mm-dd',
						weekStart : 1,
						autoclose : true,
						todayBtn : 'linked',
						language : 'zh-CN'
					});
				});
		});