function init(){
	
	//日期控件
	$('#datepicker_1').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});

	// 日期控件
	$('#datepicker_0').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	
	var faFinOrgCode = $("#selMainBank option:selected").attr("id");
	
	getBranchBankList(faFinOrgCode);
	
}

function reloadGrid(){
	var depHierarchy = $("#depHierarchy").val();
	var data = getParams();
	
	var queryId = "";
	if(depHierarchy == "yucui_team"){
		queryId = "queryTmpBankCastListItemListByTeam";
	}
	else {
		queryId = "queryTmpBankCastListItemListByDistinct";
	}
	
	$("#tmpBankCastList").reloadGrid({
    	ctx : ctx,
		queryId : queryId,
	    templeteId : 'template_tmpBankCaseList',
	    data : data,
	    wrapperData : data
    });
}

function selectUserBack(array){
	if(array && array.length >0){
        $("#realName").val(array[0].username);
		$("#realName").attr('hVal',array[0].userId);

	}else{
		$("#realName").val("");
		$("#realName").attr('hVal',"");
	}
}

function getParams() {
	var userLoginName = $.trim($("#userLoginName").val());
	var currentOrgId = $.trim($("#currentOrgId").val());
	var caseCode = $.trim($("#caseCode").val());
	var propertyAddress = $.trim($("#propertyAddress").val());
	var status = $("#selStatus option:selected").val();
	var mainBank = $("#selMainBank option:selected").val();
	var branchBank = $("#selBranchBank option:selected").val();
	var beginApplyDatetime = $("#beginApplyDatetime").val();
	var endApplyDatetime = $("#endApplyDatetime").val();
	var beginAuditDatetime = $("#beginAuditDatetime").val();
	var endAuditDateime = $("#endAuditDateime").val();
	var currentTeamId = $("#yuCuiOriGrpId").val();
	var applicantId = $("#realName").attr("hval");
	
	var data = {};
	data.username = userLoginName;
	data.applicantId = applicantId;
	data.currentOrgId = currentOrgId;
	data.caseCode = caseCode;
	data.propertyAddress = propertyAddress;
	data.status = status;
	data.mainBank = mainBank;
	data.branchBank = branchBank;
	data.currentTeamId = currentTeamId;
	data.beginApplyDatetime = beginApplyDatetime;
	data.endApplyDatetime = endApplyDatetime?(endApplyDatetime+' 23:59:59'):endApplyDatetime;
	data.beginAuditDatetime = beginAuditDatetime;
	data.endAuditDateime = endAuditDateime?(endAuditDateime+' 23:59:59'):endAuditDateime;
	
	return data;
}

function getBranchBankList(faFinOrgCode){
	var strHtml = "<option value=''>请选择</option>";
	$.ajax({
		cache:false,
		async:true,
		type:"POST",
		dataType:"json",
		url:ctx+"/manage/queryBankListByParentCode",
		data:{faFinOrgCode:faFinOrgCode},
		success:function(data){
			for(var i=0;i<data.length;i++){
				strHtml += "<option value='" + data[i].finOrgNameYc + "' id='" + data[i].finOrgCode + "'>" + data[i].finOrgNameYc + "(" + data[i].coLevelStr + ")</option>";
			}
			
			$("#selBranchBank").html(strHtml);
		}
	});
}

function exportExcel(){
	var depHierarchy = $("#depHierarchy").val();
	
	var queryId = "";
	if(depHierarchy == "yucui_team"){
		queryId = "queryTmpBankCastListItemListByTeam";
	}
	else {
		queryId = "queryTmpBankCastListItemListByDistinct";
	}
	
	var data = getParams();
	
	$.exportExcel({
		ctx : "..",
		queryId : queryId,
		colomns : ['caseCode','propertyAddress','realName','orgName','parentOrgName','mainBankName','branchBankName','tmpBankStatus1','approver','auditDateTime','applyDateTime','currentProcess1'],
		data:data
	});
}

$(document).ready(function() {
	
	init();
	
	reloadGrid();
	
	$("#submitButton").click(function(){
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
	
	$("#selMainBank").change(function(){
		var faFinOrgCode = $(this).children('option:selected').attr("id");
		
		getBranchBankList(faFinOrgCode);
	});
	
	$("#clearButton").click(function(){
		$("#caseCode").val('');
		$("#propertyAddress").val('');
		$("#selStatus").val('');
		$("#selMainBank").val('');
		$("#selBranchBank").val('');
		$("#beginApplyDatetime").val('');
		$("#endApplyDatetime").val('');
		$("#beginAuditDatetime").val('');
		$("#endAuditDateime").val('');
		$("#yuCuiOriGrpId").val('');
		$("#realName").attr("hval","");
		$("#teamCode").val('');
		$("#realName").val('');
	});
	
});