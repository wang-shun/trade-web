/**
 * 佣金管理 wanggh
 */

$(document).ready(function() {
	var ex_message = $("#ex_message").val();
	if(ex_message!=""){
		window.wxc.alert(ex_message);
	}
	if(!!hasError){
		$('#error-modal-form').modal("show");
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
						colNames : [ 'id', '人员', '员工编号',
								'所在组织', '岗位','完成单数' ,'归属月份'],
						colModel : [ {
							name : 'pkid',
							index : 'pkid',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'userNmae',
							index : 'userNmae',
							width : 80
						}, {
							name : 'employeeCode',
							index : 'employeeCode',
							width : 160
						}, {
							name : 'orgName',
							index : 'orgNmae',
							width : 80
						}, {
							name : 'jobName',
							index : 'jboNmae',
							width : 80
						}, {
							name : 'orders',
							index : 'orders',
							width : 80
						},{
							name : 'belongMonth',
							index : 'belongMonth',
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
						postData : {
							queryId : "eplusQuery"
						}

					});

					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
				});


// 日期控件
$('#datepicker').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});
//日期控件
$('#belongMonth_div').datepicker({
	format : 'yyyy-mm',
	startView: 1, maxViewMode: 1,minViewMode:1,
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
	var userName = $('#userName').val();
	var orgName = $('#orgName').val();
	
	//结案日期

	var start = $('#dtBegin').val();
	var end = $('#dtEnd').val();
	//设置查询参数
	var params = {
		search_userName :userName,
		search_orgName : orgName,
		search_bmStart : start,
		search_bmEnd : end,
		queryId : "eplusQuery"
	};
	return params;
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
	$("#userName").val("");
	$("#orgName").val("");
}
var excelInUrl = "";
//Excel modal
function showExcelModal(inType){
	$('#excel-modal-form').modal("show");
}
function showCalculatedModal(){
	$('#calculated-modal-form').modal("show");
}
// Excel 导入
function excelIn(){
	if(!checkFileTypeExcel())return false;
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	$('#excelInForm').method="post" ;
	$('#excelInForm').submit();
}

function calculatedIn(){
	if($("#belongMonth").val()=='')
	{window.wxc.alert('请先选择计算月份！');return false;}
	$.ajax({
		cache:false,
		async:true,
		type:"POST",
		url:ctx+"/perform/doCalculated",
		dataType:'json',
		data:{belongMonth:$("#belongMonth").val()+'-01'},
		success:function(data){
			if(data.success){
				window.wxc.success('计算成功');
				$('#calculated-modal-form').modal("close");
			}else{
				window.wxc.error('计算失败');
			}			
		}
	});
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
        window.wxc.alert(ErrMsg);
        return false;
  	}

	return true;
}