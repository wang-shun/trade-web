var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

/**
 * 信贷员派单流程列表
 */
$(document).ready(function() {

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

function reloadGrid(){
	var data = getParams();
	
	$("#loanerProcessList").reloadGrid({
    	ctx : ctx,
		queryId : "findLoanerProcessList",
	    templeteId : 'template_loanerProcessList',
	    data : data,
	    wrapperData : data
    });
}

/**
 * 查询参数取得
 */

function getParams() {	
	// 设置查询参数
	var params = {};
	
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
	// 信贷员
	var loaner = $("#loaner").val().trim();
	if (loaner == "" || loaner == null) {
		loaner = null;
	}
	// 银行信息
	var bankFinOrg = $("#bankFinOrg").val().trim();
	if (bankFinOrg == "" || bankFinOrg == null) {
		bankFinOrg = null;
	}
	params.bankFinOrg = bankFinOrg;
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.loaner = loaner;
	
	return params;
}
/* 查询按钮查询 */
$('#loanerConditionSearch').click(function() {
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


// 清空
$('#loanerConditionClean').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');
	$("input[name='loaner']").val('');
	
});




// 主办图标选择
$('#MortgageLostListOnclick').click(function() {
	chooseCaseOperator(serviceDepId);
});

// 组织图标选择
$('#MortgageLostListOrganizeOnclick').click(function() {
	orgSelect({
		displayId : 'oriGrpId',
		displayName : 'radioOrgName',
		startOrgId : serviceDepId,
		expandNodeId : serviceDepId, // 添加此属性的作用是展开 组织列表
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		callBack : radioYuCuiOrgSelectCallBack
	})
});

// 选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
		$("#orgName").val(array[0].name);
		$("#orgHierarchy").val(array[0].extendField);
		$("#yuCuiOriGrpId").val(array[0].id);
	} else {
		$("#orgName").val("");
		$("#yuCuiOriGrpId").val("");
	}
}

// 选择组织之后 级联选择主办人信息
function chooseCaseOperator(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();
	console.log("serviceDepId:"+serviceDepId+"expandNodeId:"+serviceDepId+"");
	if (yuCuiOriGrpId != "") {
		userSelect({
			startOrgId : yuCuiOriGrpId,
			expandNodeId : yuCuiOriGrpId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
		// $("#yuCuiOriGrpId").val("");
	} else {
		userSelect({
			startOrgId : serviceDepId,
			expandNodeId : serviceDepId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
	}
}

// 选取人员的回调函数
function loanLostCaseListSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#REAL_NAME").val(array[0].username);
		$("#REAL_NAME").attr('hVal', array[0].userId);

	} else {
		$("#REAL_NAME").val("");
		$("#REAL_NAME").attr('hVal', "");
	}
}


// 案件case_code排序图标变化函数
function caseCodeSort() {
	if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}



/*
 * 取消流程
 * */
function  finishLoanerProcess(instCode,caseCode,taskId){
	if((instCode == null || instCode == "" || instCode == undefined) || (caseCode == null || caseCode == "" || caseCode == undefined) || (taskId == null || taskId == "" || taskId == undefined)){
		window.wxc.alert('结束流程请求参数有误！'); 
		return;
	}
	
	$.ajax({
		url:ctx+"/task/loanerProcessDelete",
		method:"post",
		dataType:"json",
		data:{		
			caseCode:caseCode,
			taskId:taskId,
			processInstanceId:instCode
		},
		beforeSend:function(){
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
			$(".blockOverlay").css({'z-index':'9998'});
		},
		complete: function() {
			$.unblockUI();
			if(status=='timeout'){//超时,status还有success,error等值的情况
				Modal.alert(
						{
							msg:"抱歉，系统处理超时。"
						});
				$(".btn-primary").one("click",function(){
					parent.$.fancybox.close();
				});
			}
		} ,
		success : function(data) {
			if(data.success){
				window.wxc.success("交易顾问派单流程成功结束",{"wxcOk":function(){
					window.close();
					window.opener.callback();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		error : function(errors) {
			window.wxc.error(errors);
		}
	});
	
}

