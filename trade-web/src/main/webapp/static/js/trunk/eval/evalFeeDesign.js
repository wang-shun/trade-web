/**
 * 评估费核实 wanggh
 */

$(document).ready(function() {
					// Examle data for jqGrid
					// Configuration for jqGrid Example 1
					// /$("#case_date").addClass('btn btn-white chosen-select');
					cleanForm();
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var queryOrg = $("#queryOrg").val();
					// jqGrid 初始化
					$("#table_list_1").jqGrid(
					{
						url : url,
						mtype : 'POST',
						datatype : "json",
						height : 450,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'id', 'EVALID','案件编号', '产证地址',
								'经纪人', '上家', '下家', '评估费','足额收取','最后收取时间','操作'],
						colModel : [ {
							name : 'ID',
							index : 'ID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'EVALID',
							index : 'EVALID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'CASE_CODE',
							index : 'CASE_CODE',
							width : 90
						}, {
							name : 'PROPERTY_ADDR',
							index : 'PROPERTY_ADDR',
							width : 180
						}, {
							name : 'AGENT_NAME',
							index : 'AGENT_NAME',
							width : 60
						}, {
							name : 'SELLER',
							index : 'SELLER',
							width : 60
						}, {
							name : 'BUYER',
							index : 'BUYER',
							width : 60
						}, {
							name : 'EVAL_FEE',
							index : 'EVAL_FEE',
							width : 40
						},
						 {
							name : 'IS_EVAL_FEE_GET',
							index : 'IS_EVAL_FEE_GET',
							width : 60
						},
						 {
							name : 'RECORD_TIME',
							index : 'RECORD_TIME',
							width : 90
						},{
							name : 'EDIT',
							index : 'EDIT',
							align : "center",
							width : 40
						},


						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false, 
						cellEdit:true,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						onSelectRow : function(rowid, status) {
							alert(rowid);
						},
						postData : {
							queryId : "queryEvalItemList",
							argu_queryorg:queryOrg,
							search_caseOrgId:queryOrg
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
					
					

});


//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

function rowEdit(id){
    var row = $("#table_list_1").getRowData(id);

    var inHtml = '<input type="hidden" id="evalId" value="'+row.EVALID+'">';
    inHtml += '<input type="hidden" id="caseCode" value="'+row.CASE_CODE+'">';
    inHtml += '<div class="form-group">';
    inHtml += '<label class="col-sm-4 control-label">评估费：</label>';
    inHtml += '<label class="col-sm-6"><input id="evalFee" name="evalFee" type="text" class="form-control" value="'+row.EVAL_FEE+'"></label>';
    inHtml += '</div><div class="form-group">';
    inHtml += '<label class="col-sm-4 control-label">是否足额收取：</label>';
    inHtml += '<div class="radio i-checks radio-inline">';
    var isAllFlag = row.IS_EVAL_FEE_GET;
    if(isAllFlag=='是'){
    	inHtml += '<label style="margin-left:12px;"> <input type="radio"  value="0" id="isAll0" name="isEvaFeeGet" > 否 </label>';
        inHtml += '<label style="margin-left:12px;"> <input type="radio"  value="1" id="isAll1" name="isEvaFeeGet" checked> 是</label>';
    }else{
    	inHtml += '<label style="margin-left:12px;"> <input type="radio"  value="0" id="isAll0" name="isEvaFeeGet" checked> 否 </label>';
        inHtml += '<label style="margin-left:12px;"> <input type="radio"  value="1" id="isAll1" name="isEvaFeeGet" > 是</label>';
    }
    inHtml += '</div></div>';
    $("#modal-data-show").html(inHtml);
    $("#modal-title").html("案件编号:"+row.CASE_CODE+"-评估费核实");
    
	$('#modal-form').modal("show");
}
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
	var propertyAddr = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			agentName = inTextVal.trim();
		}
	}

	//案件编号
	var caseId = $("#caseId").val().trim();
	//是否足额收取
	var isEvalFeeGet=$('input[name="ownerRadios"]:checked').val();
	//最后收取时间
	var dtBegin =$("#dtBegin_0").val()?($("#dtBegin_0").val()+' 00:00:00'):$("#dtEnd_0").val().trim();
	var dtEnd= $("#dtEnd_0").val()?($("#dtEnd_0").val()+' 23:59:59'):$("#dtEnd_0").val().trim();	
	//设置查询参数
	var params = {
		argu_guestname : guestName,
		search_agentName : agentName,
		search_propertyAddr : propertyAddr,
		search_caseId : caseId,
		argu_isEvalFeeGet : isEvalFeeGet,
		search_dtBegin : dtBegin,
		search_dtEnd : dtEnd
	};
	return params;
}

function saveEvalItem(){
	
	var ctx = $("#ctx").val();
	var url='/eval/saveEvalItem?';
	var params="pkid="+$("#evalId").val()+"&caseCode="+$("#caseCode").val()+"&isEvalFeeGet="+$("input:radio[name='isEvaFeeGet']:checked").val();
	url = ctx+url+params;
    
	//$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	//$(".blockOverlay").css({'z-index':'9998'});
	$('#editForm').attr('action', url);
	$('#commit-form').modal("show");
	//$("#editForm").submit();
}
function commitItem(){
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	//$('#editForm').attr('action', url);
	$('#commit-form').modal("hide");
	$("#editForm").submit();
}
// 清空表单
function cleanForm() {
	$("#inTextVal").val("");
}