var ctx = $("#ctx").val();//"${ctx}";
var serviceDepId = $("#serviceDepId").val();//"${serviceDepId}";

$(function(){	
	reloadGrid();
	$('.wrapper-content').viewer();
	
	//top
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});	

});

// 查询
$('#searchButton').click(function() {
		reloadGrid();
		
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});			
});

function materialManagermentSearch(page){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "findMaterialInfoList";
	reloadGrid(params);
}


function reloadGrid(){
	var data = getParams();
	
	$("#materialInfoList").reloadGrid({
    	ctx : ctx,
		queryId : "findMaterialInfoList",
	    templeteId : 'template_materialInfoList',
	    data : data,
	    wrapperData : data
    });
}


function getParams() {
	
	var inputTimeStart = null;
	var inputTimeEnd = null;
	var outputTimeStart = null;
	var outputTimeEnd = null;	
	var backTimeStart = null;
	var backTimeEnd = null;
	var actionTimeStart = null;
	var actionTimeEnd = null;

	
	// 设置查询参数
	var params = {};
	var start = $('#dtBegin_0').val();
	var end = $('#dtEnd_0').val();
	if (end && end != '') {
		end = end + ' 23:59:59';
	}
	
	// 案件编号
	var caseCode = $("#caseCode").val().trim();
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}

	// 物业地址
	var propertyAddr = $("#propertyAddr").val().trim();
	if (propertyAddr == "" || propertyAddr == null) {
		propertyAddr = null;
	}
	// 提交人
	var createBy = $("#createBy").val().trim();
	if (createBy == "" || createBy == null) {
		createBy = null;
	}
	// 保管人
	var itemManager = $("#itemManager").val().trim();
	if (itemManager == "" || itemManager == null) {
		itemManager = null;
	}
	
	//var timeSelect = $("#loanLostCaseListTimeSelect").val();
	var itemCategory=$("#itemCategory option:selected").val();
	var itemStatus=$("#itemStatus option:selected").val();
	
	
	// 获取select 选中时间的值
	var timeSelect = $("#timeSelect option:selected").val();
	if (timeSelect == "ITEM_INPUT_TIME") {
		inputTimeStart = start;
		inputTimeEnd = end;
		params.inputTimeStart = inputTimeStart;
		params.inputTimeEnd = inputTimeEnd;
	} else if (timeSelect == "ITEM_OUTPUT_TIME") {
		outputTimeStart = start;
		outputTimeEnd = end;
		params.outputTimeStart = outputTimeStart;
		params.outputTimeEnd = outputTimeEnd;
	} else if (timeSelect == "ACTION_PRE_DATE") {
		actionTimeStart = start;
		actionTimeEnd = end;
		params.actionTimeStart = actionTimeStart;
		params.actionTimeEnd = actionTimeEnd;
	} else if (timeSelect == "ITEM_BACK_TIME") {
		backTimeStart = start;
		backTimeEnd = end;
		params.backTimeStart = backTimeStart;
		params.backTimeEnd = backTimeEnd;
	}
	
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.createBy = createBy;
	params.itemManager = itemManager;
	params.itemCategory = itemCategory;
	params.itemStatus = itemStatus;
	
	return params;
}

//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

$('#datepicker_1').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});


//通过复选框 设置全选 和  全不选
function mycheck(a) {
 	 var temp = $("[name=materialCheck]:checkbox");//document.getElementsByName("love");
	 if (a.checked == true) {
  		for ( var i = 0; i < temp.length; i++) {
   			var val = temp[i];
   			val.checked = true;
  			}
 	 }else{
  		for ( var i = 0; i < temp.length; i++) {
   		var val = temp[i];
   		val.checked = false;
  	}
  }
}
//判断复选框是否选中
function getCheck(){
	var pkids = ''; 
	var flag = 0; 	
	var ids="";	
	$("input[type=checkbox][name='materialCheck']").each(function(){ 
		if ($(this).prop("checked") == true) {			
			if($(this).attr("value")){				
				ids +=$(this).attr("value")+","; 
			}			
			flag += 1; 
		} 
	}); 	
	if(flag > 0) { 
		pkids=ids.substring(0,ids.length-1);	
		return pkids; 
	}else { 
		alert('请至少选择一条记录！'); 
		return null; 
	} 
}

//操作 提取pkid数组
function getPkidsArray(){
	var pkids = '';
	var ids="";	
	$("input[type=checkbox][name='materialCheck']").each(function(){ 
		if ($(this).prop("checked") == true) {			
			if($(this).attr("value")){				
				ids +=$(this).attr("value")+","; 
			}			
			
		} 
	}); 
	pkids = ids.substring(0,ids.length-1);	
	return pkids; 
}
//物品入库
$("#materialStorage").click(function(){	
	var pkids = getCheck();		
	if(!caseCodeTheSameCheck()){
		return false;
	}
	if(!statusFlagCheck()){
		return false;
	}		
	if(pkids){	
		//填充表单，请求后端数据
		$("#pkids").val(pkids);		
		$("#materialStorgaeForm").submit();
	}
})



//验证勾选的复选框的caseCode必须一样
function caseCodeTheSameCheck(){	
	var flag=true;
	$("input[type=checkbox][name='materialCheck']:checked").each(function(i,e){	
		flag = compareCaseCode(this,i);
		if(flag == false){
			return false;
		}
	})			
	return flag;
}

function compareCaseCode(a,b){	
	var caseCodeFlag = true;
	var caseCodeArray = $("input[type=checkbox][name='materialCheck']:checked");	
	for(var i=0 ;i < caseCodeArray.length; i++){
		  if(i != b){
			  if($(caseCodeArray[i]).attr("kkk") != $(a).attr("kkk")){
				caseCodeFlag = false;
				alert("单次操作请选择相同CaseCode对应的物品！");
			  }
		  }
		 if(caseCodeFlag==false){				
			return  false;
		 }
	}
	return caseCodeFlag;
}


//借用弹出按钮判断
$("#materialBorrow").click(function(){
	var pkids = getCheck();	
	if(pkids){
		 if(!statusInstockCheck()){
			 return false;
		 }
		 if(!caseCodeTheSameCheck()){
				return false;
		 }
		 $("#myModal").show();	
		 				
	}	
})

$("#materialBorrowClose").click(function(){	
	$("#myModal").hide();
})

//在库状态的物品才可借用、退还
function statusInstockCheck(){	
	var flag=true;
	var statusFlagArray = $("input[type=checkbox][name='materialCheck']:checked");
	$("input[type=checkbox][name='materialCheck']:checked").each(function(index,statusFlag){
		//statusFlag.value  js对象
		if($(statusFlag).attr("statusFlag") != "instock"){
			flag = false;
			alert("只有在库状态的物品才可借用！");
		}
		
		if(flag == false){
			return false;
		}
	})			
	return flag;
}

//借用div提交信息
$("#materialBorrowSubmit").click(function(){	
	 var pkids = getPkidsArray();
	 //var mmIoBatch = {}; 
	 var actionUser = $("#actionUser").attr('hVal');//$("input[name='actionUser']").val();
	 var actionPreDate = $("input[name='actionPreDate']").val();
	 var actionReason = $("input[name='actionReason']").val();
	 var actionRemark = $("#actionRemark").val();
	 logActionBorrowSubmit(pkids,actionUser,actionPreDate,actionReason,actionRemark);
	 
})
function logActionBorrowSubmit(pkids,actionUser,actionPreDate,actionReason,actionRemark){			
		$.ajax({
			url:ctx+"/material/materialBorrowSave",
			method:"post",
			dataType:"json",
			data:{"pkids" : pkids,"actionUser" : actionUser,"actionPreDate":actionPreDate,"actionReason":actionReason,"actionRemark":actionRemark},//"mmIoBatch" : mmIoBatch,
			success:function(data){ 
			console.log("Result=====" +JSON.stringify(data));
				if(data != null ){
					if(data.success){
						$("#myModal").hide();
						alert(data.message);						
						window.location.reload();
					}else{
						$("#myModal").hide();
						alert(data.message);
						window.location.reload();
					}
				}	
			},       
			error:function(e){
		    	 alert(e);
		   }
		});
	}


//归还弹出按钮判断
$("#materialReturn").click(function(){
	var pkids = getCheck();	
	if(pkids){
		 if(!statusBorrowCheck()){
			 return false;
		 }
		 if(!caseCodeTheSameCheck()){
				return false;
		 }
		 $("#Return").show();	
					
	}	
})
//归还提交信息
$("#materialReturnSubmit").click(function(){	
	 var pkids = getPkidsArray();
	 //var mmIoBatch = {}; 
	 var actionUser =  $("#returnActionUser").attr('hVal');//$("input[name='returnActionUser']").val();
	 var actionRemark = $("#returnActionRemark").val();
	 var flag = true;
	 logActionReturnSubmit(pkids,actionUser,actionRemark,flag);
	 
})
$("#materialReturnClose").click(function(){	
	$("#Return").hide();
})

//在库状态的物品才可借用
function statusBorrowCheck(){	
	var flag=true;
	var statusFlagArray = $("input[type=checkbox][name='materialCheck']:checked");
	$("input[type=checkbox][name='materialCheck']:checked").each(function(index,statusFlag){
		//statusFlag.value  js对象
		if($(statusFlag).attr("statusFlag") != "borrow"){
			flag = false;
			alert("只有在外借状态下的物品才可归还！");
		}
		
		if(flag == false){
			return false;
		}
	})			
	return flag;
}
//归还、退还共有的提交方法
function logActionReturnSubmit(pkids,actionUser,actionRemark,flag){			
	$.ajax({
		url:ctx+"/material/materialReturnSave",
		method:"post",
		dataType:"json",
		data:{"pkids" : pkids,"actionUser" : actionUser,"actionRemark":actionRemark,"flag" : flag},//"mmIoBatch" : mmIoBatch,
		success:function(data){ 
		console.log("Result=====" +JSON.stringify(data));
			if(data != null ){
				if(data.success){
					$("#Return").hide();
					alert(data.message);						
					window.location.reload();
				}else{
					$("#Return").hide();
					alert(data.message);
					window.location.reload();
				}
			}	
		},       
		error:function(e){
	    	 alert(e);
	   }
	});
}

//退还弹出按钮判断
$("#materialRefund").click(function(){
	var pkids = getCheck();	
	if(pkids){
		 if( !statusInstockCheck() ){
			 return false;
		 }
		 if(!caseCodeTheSameCheck()){
				return false;
		 }
		 $("#GiveBack").show();	
					
	}	
})

//归还提交信息
$("#materialRefundSubmit").click(function(){	
	 var pkids = getPkidsArray();
	 var flag = false;	
	 var actionUser = $("#refundActionUser").attr('hVal');//$("input[name='refundActionUser']").val();
	 var actionRemark = $("#refundActionRemark").val();
	 logActionReturnSubmit(pkids,actionUser,actionRemark,flag);
	 
})
$("#materialRefundClose").click(function(){	
	$("#GiveBack").hide();
})

//物品删除
$("#materialDelete").click(function(){
	var pkids = getCheck();	
	if(pkids){
		 if(!statusFlagCheck()){
			 return false;
		 }
 		if(confirm("确定要删除您选中的物品信息吗？")){
			$.ajax({
				url:ctx+"/material/materialDelete",
				method:"post",
				dataType:"json",
				data:{"pkids" : pkids},
				success:function(data){ 
				//console.log("Result=====" +JSON.stringify(data));
					if(data != null ){						
						if(data.success){								
							alert(data.message);						
							window.location.reload();
						}else{								
							alert(data.message);
							window.location.reload();
						}
					}	
				},       
				error:function(e){
			    	 alert(e);
			   }
			});
		}		 				
	}	
})

//验证勾选的复选框是否是"待入库"状态
function statusFlagCheck(){	
	var flag=true;
	var statusFlagArray = $("input[type=checkbox][name='materialCheck']:checked");
	$("input[type=checkbox][name='materialCheck']:checked").each(function(index,statusFlag){
		//statusFlag.value  js对象
		if($(statusFlag).attr("statusFlag") != "stay"){
			flag = false;
			alert("待入库状态的物品才可入库或者删除！");
		}
		
		if(flag == false){
			return false;
		}
	})			
	return flag;
}

//借用组织图
 function chooseBorrowOperator(id){		
			userSelect({
				startOrgId : id,
				expandNodeId : id,
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
	/*			jobCode : 'Manager,Senior_Manager',*/
				jobCode : 'consultant',
				callBack : selectBorrowUserBack
			});
	
 }

//选取人员的回调函数
function selectBorrowUserBack(array) {	
	if (array && array.length > 0) {
		$("#actionUser").val(array[0].username);
		$("#actionUser").attr('hVal', array[0].userId);

	} else {
		$("#actionUser").val("");
		$("#actionUser").attr('hVal', "");
	}
}
//主办图标选择
$('#materialBorrowUser').click(function() {
	chooseBorrowOperator(serviceDepId);
});



//归还人员组织
function chooseReturnOperator(id){		
	userSelect({
		startOrgId : id,
		expandNodeId : id,
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
/*			jobCode : 'Manager,Senior_Manager',*/
		jobCode : 'consultant',
		callBack : selectReturnUserBack
	});

}

//选取人员的回调函数
function selectReturnUserBack(array) {	
	if (array && array.length > 0) {
		$("#returnActionUser").val(array[0].username);
		$("#returnActionUser").attr('hVal', array[0].userId);	
	} else {
		$("#returnActionUser").val("");
		$("#returnActionUser").attr('hVal', "");
	}
}
//主办图标选择
$('#materialReturnUser').click(function() {
	chooseReturnOperator(serviceDepId);
});

//退还人员组织
function chooseRefundOperator(id){		
	userSelect({
		startOrgId : id,
		expandNodeId : id,
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
/*			jobCode : 'Manager,Senior_Manager',*/
		jobCode : 'consultant',
		callBack : selectRefundUserBack
	});

}

//选取人员的回调函数
function selectRefundUserBack(array) {	
	if (array && array.length > 0) {
		$("#refundActionUser").val(array[0].username);
		$("#refundActionUser").attr('hVal', array[0].userId);	
	} else {
		$("#refundActionUser").val("");
		$("#refundActionUser").attr('hVal', "");
	}
}
//主办图标选择
$('#materialRefundUser').click(function() {
	chooseRefundOperator(serviceDepId);
});