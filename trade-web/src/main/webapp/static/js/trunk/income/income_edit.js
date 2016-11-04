/**
 * 佣金管理 wanggh
 */

$(document).ready(function() {
	var ex_message = $("#ex_message").val();
	if(ex_message!=""){
		alert(ex_message);
	}
					cleanForm();
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var cpDivWidth = $("#case_date").width();
					$('#inTextType').next().css("width", cpDivWidth+16);
					// jqGrid 初始化
					$("#table_list_1").jqGrid(
					{
						url : url,
						mtype : 'POST',
						datatype : "json",
						height : 250,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'id', '案件编号', '产证地址',
								'经纪人', '经办人','收益总数' ,'操作'],
						colModel : [ {
							name : 'PKID',
							index : 'PKID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'CASE_CODE',
							index : 'CASE_CODE',
							width : 80
						}, {
							name : 'PROPERTY_ADDR',
							index : 'PROPERTY_ADDR',
							width : 160
						}, {
							name : 'AGENT_NAME',
							index : 'AGENT_NAME',
							width : 80
						}, {
							name : 'PROCESSOR_NAME',
							index : 'PROCESSOR_NAME',
							width : 80
						}, {
							name : 'INCOME_AMOUNT_COUNT',
							index : 'INCOME_AMOUNT_COUNT',
							width : 80
						},{
							name : 'EDIT',
							index : 'EDIT',
							align : "center",
							width : 80
						}

						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						onSelectRow : function(rowid, status) {
							
						},
						postData : {
							queryId : "queryCastCountAmountList"
						},
						gridComplete: function(){
			                var ids = $("#table_list_1").getDataIDs();//jqGrid('getDataIDs');
			                for(var i=0;i<ids.length;i++){
			                    var id = ids[i];
			                    var inHTML = "<input class='btn btn-primary' type='button' value='编辑' onclick='rowEdit(\""+id+"\")' />";
			                    jQuery("#table_list_1").jqGrid('setRowData',ids[i],{EDIT:inHTML});
			                } 
			            }

					});

					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
					var inType = $("#inType").val();
					if(inType == "")return false;
					var url="income/uploadExcel";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var params = '&inType=' + inType;
					//Excel
					
				});

// select控件
var config = {
	'.chosen-select' : {},
	'.chosen-select-deselect' : {
		allow_single_deselect : true
	},
	'.chosen-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chosen-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chosen-select-width' : {
		width : "95%"
	}
};

for ( var selector in config) {
	$(selector).chosen(config[selector]);
};

// 日期控件
$('#datepicker').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

 //查询
function searchMethod() {
	var params = getParamsValue();
	// jqGrid reload
	$("#table_list_1").setGridParam({
		"postData" : params,
		"page":1 
	}).trigger('reloadGrid');
};
/**
 * 查询参数取得
 * @returns {___anonymous6020_7175}
 */
function getParamsValue() {
	
	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	var guestName = "";
	var agentName = "";
	var proName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			agentName = inTextVal.trim();
		}else if (inTextType == '3') {
			agentOrgName = inTextVal.trim();
		}else if (inTextType == '4') {
			proName = inTextVal.trim();
		}
	}

	//结案日期

	var start = $('#dtBegin').val();
	var end = $('#dtEnd').val();
	//设置查询参数
	var params = {
		
		argu_guestname : guestName,
		search_agentName :agentName,
		search_proName : proName,
		search_agentOrgName : agentOrgName,
		search_propertyAddr : propertyAddr,
		search_approveTimeStart : start,
		search_approveTimeEnd : end
	};
	return params;
}

/**
 * 计件案件导出
 */
function caseTrueCountExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	var params = getParamsValue();
	var queryId = '&queryId=queryCaseTrueCountList';
	var displayColomn = ['CASE_CODE','PROPERTY_ADDR','SRV_CODE','PROCESSOR_NAME','EMPLOYEE_CODE','ClOSE_TIME'];
	var colomns = '&colomns=' + displayColomn;
	url = ctx + url + jQuery.param(params) + queryId + colomns;

	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}

/**
 * 贷款对账导出
 */
function mortageSTExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	var params = getParamsValue();
	var queryId = '&queryId=queryCaseMortageStatementList';
	var displayColomn = ['CASE_CODE','PROPERTY_ADDR','OUT_MORT_TYPE','OUT_SRV_NAME','REAL_PROCESSOR_NAME','FA_ORG_NAME'
	                     ,'BANK_ORG_NAME','MORT_AMOUNT','EVA_ORG_NAME','WT_ORG_NAME'];
	var colomns = '&colomns=' + displayColomn;
	url = ctx + url + jQuery.param(params) + queryId + colomns;

	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}

// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
	$("input[name='dtEnd']").val("");
	$("#inTextVal").val("");
}
var excelInUrl = "";
//Excel modal
function showExcelModal(inType){
	excelInUrl = "";
	switch(inType)
	{
	case 1:
		excelInUrl = "/income/importBaseAmount?";
		break;
	case 2:
		excelInUrl = "/income/importScore?";
		break;
	case 3:
		excelInUrl = "/income/uploadExcelIncomeStatistics?";
		break;
	case 4:
		excelInUrl = "/income/uploadExcelUserMonthCount?";
		break;
	default:
	}
	$('#excel-modal-form').modal("show");
}
// Excel 导入
function excelIn(){
	if(!checkFileTypeExcel())return false;
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	var ctx = $("#ctx").val();
	$('#excelInForm').attr('action', ctx + excelInUrl);
	$('#excelInForm').method="post" ;
	$('#excelInForm').submit();
}

function checkFileTypeExcel()
{
	var obj = $("#file")[0];
    var ErrMsg="";
    var FileMsg="";
    var IsImg=false;
    if(obj.value=="")return false;
    var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
    var AllowExt=".xls .csv .xlsx .ods";
    //判断文件类型是否允许上传
    if(AllowExt!=0&&AllowExt.indexOf(FileExt)==-1) {
        ErrMsg="\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt;
        alert(ErrMsg);
        return false;
  	}

	return true;
}