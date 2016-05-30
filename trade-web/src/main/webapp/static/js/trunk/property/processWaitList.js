/**
 * 待处理产调
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prStatus = $("#prStatus").val();
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	url = ctx + url;
	//jqGrid 初始化
	$("#table_property_list").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ '行政区域','物业地址', '产调项目','所属分行',
		             '产调申请人',  '产调申请时间',
		             '状态','所属贵宾服务部' ],
		colModel : [{
			name : 'DIST_CODE',
			index : 'DIST_CODE',
			align : "center",
			width : 30
			
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 60
		},{
			name : 'PR_CAT',
			index : 'PR_CAT',
			width : 40
		}, {
			name : 'orgName',
			index : 'orgName',
			width : 40
		},{
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		},  {
			name : 'PR_APPLY_TIME',
			index : 'PR_APPLY_TIME',
			width : 50
		},{
			name : 'PR_STATUS',
			index : 'PR_STATUS',
			width : 20
		}	,{
			name:'prDistrictId',
			index :'prDistrictId',
			width:80,
			formatter:function(cellvalue, options, rawObject){
				var outHtml=
				"<input type=\"text\" class=\"span12 tbsporg org-label-control\" id=\"teamCode\" name=\"teamCode\" readonly=\"readonly\" "
					   +"onclick=\"showOrgSelect("+rawObject.PKID+")\" value='"+rawObject.prDistrictId+"' />";
				if(optTransferRole){return outHtml; }
				else{
					return cellvalue;
				}
			}
		}
		],
		multiselect: false,
		pager : "#pager_property_list",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : true,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		postData : {
			queryId : "queryProcessWaitList",
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus
		},loadComplete: function(){
			var re_records = $("#table_property_list").getGridParam('records');
			if(re_records == 0 || re_records == null){
				$("#expToexcel").attr("disabled", true);
			}else{
				$("#expToexcel").attr("disabled", false);
			}
		}

	});

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

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
		displayColomn.push('PROPERTY_ADDR');//物业地址
		displayColomn.push('PR_CAT');//产调项目
		displayColomn.push('orgName');//分行信息
		displayColomn.push('PR_APPLY_TIME');//分行信息
		
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
		   startOrgId:'ff8080814f459a78014f45a73d820006',departmentHeriarchy:'yucui_district',
		   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,});
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
				$('#table_property_list').jqGrid({
					queryId : "queryProcessWaitList",
					search_prDistrictId : prDistrictId,
					search_prStatus : prStatus
				}).trigger('reloadGrid');
			}
		},
		error : function(errors) {
			alert("处理出错,请刷新后再次尝试！");
			  $.unblockUI();  
		}
	});
}




