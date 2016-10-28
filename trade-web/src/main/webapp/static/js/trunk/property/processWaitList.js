/**
 * 待处理产调
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prStatus = $("#prStatus").val();

$(document).ready(function() {
	
	// 初始化列表
	var data = {};
    data.search_prDistrictId = prDistrictId;
    data.search_prStatus = prStatus;
    data.optTransferRole = optTransferRole;
    
    $("#processWaitList").aistGrid({
		ctx : ctx,
		queryId : 'queryProcessWaitList',
	    templeteId : 'template_processWaitList',
	    data : data,
	    wrapperData : data,
	    columns : [{
	    	           colName :"行政区域"
	    	      },{
	    	           colName :"产证地址"
	    	      },{
	    	           colName :"区域分行"
	    	      },{
	    	           colName :"产调申请时间"
    	          },{
		    	       colName :"产调申请"
	    	      },{
	    	           colName :"操作"
	    	      }]
	
	}); 
	
	if($('#processWaitList table tbody').children().length > 0){
		$("#expToexcel").attr("disabled", false);
	}else{
		$("#expToexcel").attr("disabled", true);
	}
	
	$("#searchButton").click(function(){
		reloadGrid();
	});
	
	$('#processWaitList table').addClass("apply-table");
});

function reloadGrid() {
	var data = getParams();
    $("#processWaitList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryProcessWaitList',
	    templeteId : 'template_processWaitList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	var distCode = $("#distCode").val();
	var data = {};
	data.search_prDistrictId = prDistrictId;
	data.search_prStatus = prStatus;
	data.optTransferRole = optTransferRole;
	data.search_distCode = distCode;
    return data;
} 

/* 处理产调
 * 1.改变未处理产调状态
 * 2.导出 表格
 */
function caseDistribute(){
		var pkidList ;
		pkidList = jQuery("#table_property_list").jqGrid('getGridParam', 'selarrrow');
		$.ajax({
				cache : false,
				type : "POST",
				url : ctx+'/property/updateProcessWaitListStatus',
				dataType : "json",
				data : [{
					name : 'pkidList',
					value : pkidList
				}],
				success : function(data) {
					$.unblockUI();  
					alert(data.message)
					if(data.success){
						location.reload();
					}
				},
				error : function(errors) {
					alert("处理出错,请刷新后再次尝试！");
					  $.unblockUI();  
				}
		});
	
}

//导出Excel
function exportToExcel() {
		if(!confirm('是否导出/处理?')){
			return false;
		}
		var pkid ;
		pkid = jQuery("#table_property_list").jqGrid('getGridParam', 'selarrrow');
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		var displayColomn = new Array;
		displayColomn.push('PROPERTY_ADDR');// 物业地址
		displayColomn.push('PR_CAT');  // 产调项目
		displayColomn.push('orgName');  // 产调项目
		displayColomn.push('applyOrgName'); // 区域分行信息
		displayColomn.push('orgMgr'); // 区蕫信息
		displayColomn.push('PR_APPLY_TIME');// 分行信息
		
		var params = getParamsValue(pkid);
		var queryId = '&queryId=queryProcessWaitList';
		var colomns = '&colomns=' + displayColomn;
		url = ctx + url + jQuery.param(params) + queryId + colomns;

		$('#excelForm').attr('action', url);
		$('#excelForm').submit();
		alert("产调导出至 Excel成功");
		$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
		$(".blockOverlay").css({'z-index':'9998'});
		//caseDistribute();
		//停顿2s后再执行
		setTimeout(function(){caseDistribute();},2000);
		 
}

//获取参数(查询条件)
function getParamsValue(pkid) {
	
	var params = {
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus,
			search_pkid : pkid
	};
	return params;
}

var optPkid='';

function checkOrg(o){
	if(o.extendField!='yucui_district'){
		alert('请选择一个贵宾服务总进行转组');
		return false;
	}
	return true;
}
function showOrgSelect(id){
	optPkid=id;
	orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
		   startOrgId:'ff8080814f459a78014f45a73d820006',
		   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,departmentHeriarchy:'yucui_team'});
}
function radioYuCuiOrgSelectCallBack(array){
	if(array && array.length >0){
		if(checkOrg(array[0])){
			if(confirm('是否确认转组')){
		        
				$("#yuCuiOriGrpId").val(array[0].id);		
				doTransfer(optPkid,array[0].id,array[0].name);
			}
		}else{
			return false;
		}
	}
}
function doTransfer(pkid,districtId,orgName){
	var transferData={pkid:pkid,districtId:districtId};
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	$.ajax({
		cache : false,
		type : "POST",
		url : ctx+'/property/doChangePrDistrictId',
		dataType : "json",
		data :transferData,
		success : function(data) {
			alert(data.message)
			$.unblockUI();
			if(data.success){
				
				reloadGrid();
				
//				$('#table_property_list').jqGrid({
//					queryId : "queryProcessWaitList",
//					search_prDistrictId : prDistrictId,
//					search_prStatus : prStatus
//				}).trigger('reloadGrid');
			}
		},
		error : function(errors) {
			alert("处理出错,请刷新后再次尝试！");
			  $.unblockUI();  
		}
	});
}




