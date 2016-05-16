/**
 * 待办任务
 * wanggh
 */
$(document).ready(function() {
	

			var url = "/quickGrid/findPage";
			var ctx = $("#ctx").val();
			url = ctx + url;

			//jqGrid初始化
			$("#table_list_1").jqGrid(
					{
						url : url,
						datatype : "json",
						mtype : "POST",
						height : 550,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 20,
						/* rowList: [10, 20, 30], */
						colNames : [ 'TASKID','红绿灯', '红灯记录','案件编号','CTM编号','PARTCODE','INSTCODE' , '任务名', '产证地址', '经纪人','手机',
								'所属分行','上家', '下家', '预计执行时间' ,'操作'],
						colModel : [ {
							name : 'ID',
							index : 'ID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'DATELAMP',
							index : 'DATELAMP',
							width : 30,
							editable : true,
							formatter : dateLampFormatter
						}, {
							name : 'RED_LOCK',
							index : 'RED_LOCK',
							width : 30,
							editable : true,
							formatter : isRedFormatter
						},{
							name : 'CASE_CODE',
							index : 'CASE_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							formatter : function(cellvalue, options, rawObject){
								var a=("<a class='aline' href='"+ctx+"/case/caseDetail?caseId="+rawObject.PKID+"' target='_blank'>"+cellvalue+"</a>");
								return a;
							}
						},{
							name : 'CTM_CODE',
							index : 'CTM_CODE',
							align : "center",
							width : 0,
							resizable : false,
							
						},{
							name : 'PART_CODE',
							index : 'PART_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'INST_CODE',
							index : 'INST_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'NAME',
							index : 'NAME',
							width : 65
						}, {
							name : 'PROPERTY_ADDR',
							index : 'PROPERTY_ADDR',
							width : 160
						}, {
							name : 'AGENT_NAME',
							index : 'AGENT_NAME',
							width : 40
						}, {
							name : 'MOBILE',
							index : 'MOBILE',
							width : 65
						}, {
							name : 'AGENT_ORG_NAME',
							index : 'AGENT_ORG_NAME',
							width : 110
						},{
							name : 'SELLER',
							index : 'SELLER',
							width : 60
						}, {
							name : 'BUYER',
							index : 'BUYER',
							width : 60
						}, {
							name : 'EST_PART_TIME',
							index : 'EST_PART_TIME',
							width : 50
						},{
							width : 30,formatter:function(cellvalue, options, rawObject){
								var url = ctx+"/task/"+rawObject.PART_CODE+
								"?&taskId="+rawObject.ID+"&caseCode="+rawObject.CASE_CODE+"&instCode="+rawObject.INST_CODE;
								return "<a href='"+url+"'>处理</a>";
							}
						}

						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						// rowid为grid中的行顺序
						onSelectRow : function(rowid) {
							/*var rowData = $("#table_list_1").jqGrid('getRowData',rowid);
							var url = ctx+"/task/"+rowData.PART_CODE+
							"?&taskId="+rowData.ID+"&caseCode="+rowData.CASE_CODE+"&instCode="+rowData.INST_CODE;
//							alert(url);
							window.location.href = url;*/
						},
						postData : {
							queryId : "queryTaskListItemList"
						}

					});

			// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#table_list_1').setGridWidth(width);
			});
			//ie
			if ($.support.msie) {
				$('input:radio').click(function () {
					this.blur();
					this.focus();
				});
			}; 
			$('input[name="lampRadios"][value=0]').prop("checked", true);
			$('input[name="ownerRadios"][value=0]').prop("checked", true);
			//查询
			$('input:radio[name="lampRadios"]').change(function() {
				searchMethod();
			});
			$('input:radio[name="ownerRadios"]').click(function() {
				searchMethod();
			});
		});
//select控件
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


//查询
$('#searchButton').click(function() {
	searchMethod();
});
$('#orderByButton').click(function() {
	//延迟天数范围
	var minDateLamp=null;
	var maxDateLamp=null;
	var ownerType;
	var dateLamp = $('input[name="lampRadios"]:checked').val();
	if(dateLamp!=0){
		//红灯
		if(dateLamp == 1){
			minDateLamp = lamp3;
			//黄灯
		}else if(dateLamp == 2){
			minDateLamp = lamp2;
			maxDateLamp = lamp3;
			//绿灯
		}else if(dateLamp == 3){
			minDateLamp = lamp1;
			maxDateLamp = lamp2;
		}
	}
	//授权代办
	var ownerType = $('input[name="ownerRadios"]:checked').val();
	var allTypeFlag;
	//全部
	if(ownerType == 0){
		ownerType=null;
		//本身
	}else if(ownerType == 1){
		ownerType=null;
		allTypeFlag = 'true';
		//代办
	}else if(ownerType == 2){
		ownerType='pending';
	}

	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	var hVal = $('#inTextVal').attr('hVal');
	var guestName = "";
	var agentName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			// 经纪人姓名
			agentName = hVal.trim();
		}else if (inTextType == '3') {
			
			agentOrgName = inTextVal.trim();
		}
	}

	var params = {
			search_minDateLamp : minDateLamp,
			search_maxDateLamp : maxDateLamp,
			search_ownerType : ownerType,
			argu_guestname : guestName,
			search_agentName : agentName,
			search_agentOrgName : agentOrgName,
			search_propertyAddr : propertyAddr,
			argu_allType: allTypeFlag
		};

		//jqGrid reload
		$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 ,
			"sortname": "ID", // 表示用于排序的列名的参数名称 
			"sortorder": "ASC" // 表示采用的排序方式的参数名称 
		}).trigger('reloadGrid');
});
var lamp1 = $("#Lamp1").val();
var lamp2 = $("#Lamp2").val();
var lamp3 = $("#Lamp3").val();
/**
 * 红绿灯Formate
 * @param cellvalue
 * @returns {String}
 */
function dateLampFormatter(cellvalue) {

	var color='';
	if (cellvalue < lamp1||cellvalue==null)
		return "";
	if (cellvalue < lamp2) {
		color='green';
	} else if (cellvalue < lamp3) {
		color='yellow';
	} else {
		color='red';
	}
	var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
	outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
	outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
	outDiv+='</div>';
	return outDiv;
}
function isRedFormatter(cellvalue) {

	var reStr='无';
	if (cellvalue =='1')
		reStr='有';
	return reStr;
}
//search
function searchMethod(){
	//延迟天数范围
	var minDateLamp=null;
	var maxDateLamp=null;
	var ownerType;
	var dateLamp = $('input[name="lampRadios"]:checked').val();
	if(dateLamp!=0){
		//红灯
		if(dateLamp == 1){
			minDateLamp = lamp3;
			//黄灯
		}else if(dateLamp == 2){
			minDateLamp = lamp2;
			maxDateLamp = lamp3;
			//绿灯
		}else if(dateLamp == 3){
			minDateLamp = lamp1;
			maxDateLamp = lamp2;
		}
	}
	//授权代办
	var ownerType = $('input[name="ownerRadios"]:checked').val();
	var allTypeFlag;
	//全部
	if(ownerType == 0){
		ownerType=null;
		//本身
	}else if(ownerType == 1){
		ownerType=null;
		allTypeFlag = 'true';
		//代办
	}else if(ownerType == 2){
		ownerType='pending';
	}

	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	var hVal = $('#inTextVal').attr('hVal');
	var guestName = "";
	var agentName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	var taskDfKey=$("#taskDfKey").val();
	// caseCode与ctmCode
	var caseCode =  "";
	var ctmCode = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			// 经纪人姓名
			agentName = hVal.trim();
		}else if (inTextType == '3') {
			agentOrgName = inTextVal.trim();
		}else if (inTextType == '4') {
			caseCode = inTextVal.trim();
		}else if (inTextType == '5') {
			ctmCode = inTextVal.trim();
		}
	}

	var params = {
			search_caseCode : caseCode,
			search_ctmCode : ctmCode,
			search_minDateLamp : minDateLamp,
			search_maxDateLamp : maxDateLamp,
			search_ownerType : ownerType,
			argu_guestname : guestName,
			search_agentName : agentName,
			search_agentOrgName : agentOrgName,
			search_propertyAddr : propertyAddr,
			argu_allType: allTypeFlag,
			search_taskDfKey:taskDfKey
		};

		//jqGrid reload
		$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 
		}).trigger('reloadGrid');
}

function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
	if(inTextType=='2'){
		initAutocomplete(ctx+"/labelVal/queryUserInfo");
	}else if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
}
function initAutocomplete(url){
	$("#inTextVal").AutoComplete({
		data:url,
		'itemHeight': 20,
        'width': 280,
        maxItems:10,
        ajaxType:'POST',
        beforeLoadDataHandler:function(){
        	$("#inTextVal").attr('hVal','');
        	return true;
        },
        afterSelectedHandler:function(data){ 
        	if(data&&data.value){
        		$("#inTextVal").attr('hVal',data.value);
        	}else{
        		$("#inTextVal").attr('hVal','');
        	}
		}
    }).AutoComplete('show');
}
