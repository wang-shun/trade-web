$(document).ready(function() {
					$("#productType").hide();
					$("#more").click(function() {
						$("#productType").toggle();
					});
					
	
					cleanForm();
					 aist.sortWrapper({
			    			reloadGrid : searchMethod
			    	});
					 
					//基本信息等高
					var cpDivWidth = $("#case_date_0").next().width();
					$('#inTextType').next().css("width", cpDivWidth);
					// 初始化列表
					var data = {};
		    	    data.queryId = "queryCastListItemList";
		    	    data.rows = 10;
		    	    data.page = 1;
		    	    data.argu_isNotResearchCloseCase = "true";
					data.argu_sessionUserId = $("#userId").val();
		    	    aist.wrap(data);
		    		reloadGrid(data);

					//$("input:checkbox[name='srvCode'][value='30004010']").parent().parent().parent().hide();
					$("span[name='srvCode']").click(function(){
						var id = $(this).attr("id");
						$("span[id='"+id+"']").changeSelect();
					});
				});

jQuery.fn.changeSelect = function(params){							 	
	if(this.hasClass("selected")) {
		this.removeClass("selected");
	} else {
		this.addClass("selected");
	}
};

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
$('#datepicker_0').datepicker({
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
$('#addNewCase').click(function() {	
	window.location.href = ctx+"/caseMerge/addCase/case";
});
/**
 * 新建外单案件
 */
$('#addNewWdCase').click(function() {	
	window.location.href = ctx+"/caseMerge/addWdCase";
});
// 添加日期查询条件
var divIndex = 1;
var count = $('#case_date_0 option:last').index();
function addDateDivTwo() {

	var txt = '<div class="row clearfix add_group"><div id="dateDiv_' + divIndex + '" class="input-group">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
			+ divIndex
			+ '" class="btn btn-white chosen-select chosen_space" name="case_date" type="select" >';
	txt += $("#case_date_0").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
			+ divIndex
			+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
			+ divIndex
			+ '" name="dtBegin" class="form-control" style="font-size: 13px;border-radius: 0px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
			+ divIndex
			+ '" name="dtEnd" class="form-control" style="font-size: 13px;border-radius: 0px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
			+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$(".date-info").after(txt);
	// 日期控件
	$('#datepicker_' + divIndex).datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	// 设置初始选中
	var selIndex = findFirstNoCheckVal();
	$("#case_date_" + divIndex).get(0).selectedIndex = selIndex;
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	;

	divIndex++;
}

function addDateDiv() {

	var txt = '<div class="row clearfix add_group"><div id="dateDiv_' + divIndex + '" class="input-group">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
			+ divIndex
			+ '" class="btn btn-white chosen-select chosen_space" name="case_date" type="select" >';
	txt += $("#case_date_0").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
			+ divIndex
			+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
			+ divIndex
			+ '" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
			+ divIndex
			+ '" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
			+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$(".date-info").after(txt);
	// 日期控件
	$('#datepicker_' + divIndex).datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	// 设置初始选中
	var selIndex = findFirstNoCheckVal();
	$("#case_date_" + divIndex).get(0).selectedIndex = selIndex;
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	;

	divIndex++;
}

// 查看未被选择的日期类型
function findFirstNoCheckVal() {

	var onUseArray = new Array();
	if (divIndex > count)
		return 0;
	for (var r = 0; r < divIndex; r++) {
		var onUse = $('#case_date_' + r + ' option:selected').index();
		onUseArray.push(onUse);

	}
	for (var s = 0; s < count; s++) {
		var onUseFlag = false;
		for (var m = 0; m < onUseArray.length; m++) {
			if (s == onUseArray[m]) {
				onUseFlag = true;
				break;
			}
		}
		if (!onUseFlag)
			return s;
	}
	return 0;
}
 //删除日期控件
function removeDateDiv(index) {
	$("#dateDiv_" + index).parent().remove();
}

 //查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 10;
		params.queryId = "queryCastListItemList";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}

};

function reloadGrid(data) {
	//添加排序-----
    aist.wrap(data);
	
	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;
	//添加排序----------
	
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var serviceDepId = $("#serviceDepId").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
    data.argu_queryorgs = orgArray;
    data.rows = 10;
    
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
          $.unblockUI();   	 
      	  data.ctx = ctx;
      	  var myCaseList = template('template_myCaseList' , data);
			  $("#myCaseList").empty();
			  $("#myCaseList").html(myCaseList);
			  // 显示分页 
            mycase_initpage(data.total,data.pagesize,data.page, data.records);
            
            $('.demo-left').poshytip({
    			className: 'tip-twitter',
    			showTimeout: 1,
    			alignTo: 'target',
    			alignX: 'left',
    			alignY: 'center',
    			offsetX: 8,
    			offsetY: 5,
    		});

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
			$("#myCaseList").subscribeToggle({
				moduleType:"1001",
				subscribeType:"2001"
			});
    		
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}



function mycase_initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);
	
	$("#pageBar").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {			 
			searchMethod(page);
	    }
	});
}


//获取查询参数
function getParamsValue() {
		
	// 案件类型
	var caseProperty = $('#caseProperty option:selected').val().trim();
	// 案件性质
	var caseStatus = $('#status option:selected').val().trim();	
	//案件是否关注
	
	var isSubscribeFilter = $('#isSubscribeFilter option:selected').val();
	if(isSubscribeFilter==null || isSubscribeFilter=='') {
		isSubscribeFilter = -1;
	}
	var caseOriginType = $('#caseOriginType option:selected').val();
	if(caseOriginType==null || caseOriginType=='') {
		caseOriginType = null;
	}
	
	// 贷款需求选择
	var mortageType = $('#mortageService option:selected').val();
	if(mortageType==null || mortageType=='') {
		mortageType = -1;
	}	
	// 贷款需求选择
	var mortageType = $('#mortageService option:selected').val();
	if(mortageType==null || mortageType=='') {
		mortageType = -1;
	}

	
	// 服务阶段选择
	var status = $('#status option:selected').val();
	var yuCuiOriGrpId =  $('#yuCuiOriGrpId').val();
	// 全选情况
	if (caseProperty == '30003006')
		caseProperty = null;
	
	// 如果不是选择结案
	var isNotResearchCloseCase;
	if(caseProperty != '30003002') {
		isNotResearchCloseCase = "true";
	}
	if (status == '30001006')
		status = null;

	// 产品类型
	getCheckBoxValues("srvCode");
	
	
	

	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	var hVal = $('#inTextVal').attr('hVal');
	
	var guestName = "";
	var agentName = "";
	var proName = "";
	var propertyAddr = "";
	var agentOrgName = "";
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
			agentName = inTextVal.trim();
		}else if (inTextType == '3') {
			agentOrgName = hVal.trim();
		}else if (inTextType == '4') {
			proName = hVal.trim();
		}else if (inTextType == '5') {
			caseCode = inTextVal.trim();
		}else if (inTextType == '6') {
			ctmCode = inTextVal.trim();
		}
	}

	//设置查询参数
	//argu_  where条件参数；   seach_ searchCondition参数
	var params = {
		argu_sessionUserId : $("#userId").val(),
		argu_isSubscribeFilter : isSubscribeFilter,
		argu_caseOriginType : caseOriginType,
		argu_mortageType : mortageType,	
		
		argu_caseCode : caseCode,
		argu_ctmCode : ctmCode,
		argu_caseProperty : caseProperty,
		argu_status : status,
		
		argu_guestname : guestName, //客户姓名
		argu_agentName :agentName, //经纪人姓名  直接姓名，不是id
		
		argu_proName : proName,  //交易顾问的userid
		argu_agentOrgName : agentOrgName, //所属分行  orgid
		
		argu_propertyAddr : propertyAddr,
		argu_srvCode : srvCode,
		argu_srvCode1 : srvCode1,
		argu_srvCode2 : srvCode2,
		argu_srvCode3 : srvCode3,
		argu_srvCode4 : srvCode4,
		argu_srvCode5 : srvCode5,
		argu_srvCode6 : srvCode6,
		argu_srvCode7 : srvCode7,
		argu_srvCode8 : srvCode8,
		argu_srvCode9 : srvCode9,
		argu_srvCode11 : srvCode11,
		argu_srvCode12 : srvCode12,
		argu_srvCode13 : srvCode13,
		argu_srvCode14 : srvCode14,
		argu_srvCode15 : srvCode15,
		
		argu_createTimeStart : createTimeStart,
		argu_resDateStart : resDateStart,
		argu_approveTimeStart : approveTimeStart,
		argu_createTimeEnd : createTimeEnd,
		argu_resDateEnd : resDateEnd,
		argu_approveTimeEnd : approveTimeEnd,
		
		argu_realConTimeStart : realConTimeStart,
		argu_realConTimeEnd : realConTimeEnd,
		argu_realConTime : (realConTimeStart == null && realConTimeEnd == null ? null : true),
		
		argu_realHtTimeStart : realHtTimeStart,
		argu_realHtTimeEnd : realHtTimeEnd,
		argu_realHtTime : (realHtTimeStart == null && realHtTimeEnd == null ? null : true),

		argu_realPropertyGetTimeStart : realPropertyGetTimeStart,
		argu_realPropertyGetTimeEnd : realPropertyGetTimeEnd,
		argu_realPropertyGetTime : (realPropertyGetTimeStart == null && realPropertyGetTimeEnd == null ? null : true),

		argu_signDateStart : signDateStart,
		argu_signDateEnd : signDateEnd,
		argu_lendDateStart : lendDateStart,
		argu_lendDateEnd : lendDateEnd,
		argu_signlendDate : (signDateStart == null && signDateEnd == null && lendDateStart == null && lendDateEnd == null ? null : true),
		
		argu_guohuApproveTimeStart : guohuApproveTimeStart,
		argu_guohuApproveTimeEnd : guohuApproveTimeEnd,
		argu_guohuApproveTime : (guohuApproveTimeStart == null && guohuApproveTimeEnd == null ? null : true),
		
		
		argu_yuCuiOriGrpId : yuCuiOriGrpId,
		argu_isNotResearchCloseCase : isNotResearchCloseCase

	};
	return params;
}




function oldGetParamsValue() {
	var isSubscribeFilter = $('#isSubscribeFilter option:selected').val();
	if(isSubscribeFilter==null || isSubscribeFilter=='') {
		isSubscribeFilter = -1;
	}
	var caseOriginType = $('#caseOriginType option:selected').val();
	if(caseOriginType==null || caseOriginType=='') {
		caseOriginType = null;
	}
	
	// 贷款需求选择
	var mortageType = $('#mortageService option:selected').val();
	if(mortageType==null || mortageType=='') {
		mortageType = -1;
	}
	// 案件类型
	var caseProperty = $('#caseProperty option:selected').val();
	// 服务阶段选择
	var status = $('#status option:selected').val();
	var yuCuiOriGrpId =  $('#yuCuiOriGrpId').val();
	// 全选情况
	if (caseProperty == '30003006')
		caseProperty = null;
	// 如果不是选择结案
	var isNotResearchCloseCase;
	if(caseProperty != '30003002') {
		isNotResearchCloseCase = "true";
	}
	if (status == '30001006')
		status = null;

	// 产品类型
	getCheckBoxValues("srvCode");

	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	var hVal = $('#inTextVal').attr('hVal');
	var guestName = "";
	var agentName = "";
	var proName = "";
	var propertyAddr = "";
	var agentOrgName = "";
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
			agentName = inTextVal.trim();
		}else if (inTextType == '3') {
			agentOrgName = hVal.trim();
		}else if (inTextType == '4') {
			proName = hVal.trim();
		}else if (inTextType == '5') {
			caseCode = inTextVal.trim();
		}else if (inTextType == '6') {
			ctmCode = inTextVal.trim();
		}
	}

	//设置查询参数
	var params = {
		argu_sessionUserId : $("#userId").val(),
		argu_isSubscribeFilter : isSubscribeFilter,
		caseOriginType : caseOriginType,
		argu_mortageType : mortageType,
		search_caseCode : caseCode,
		search_ctmCode : ctmCode,
		search_caseProperty : caseProperty,
		search_status : status,
		argu_guestname : guestName,
		search_agentName :agentName,
		argu_proName : proName,
		argu_agentOrgName : agentOrgName,
		search_propertyAddr : propertyAddr,
		argu_srvCode : srvCode,
		argu_srvCode1 : srvCode1,
		argu_srvCode2 : srvCode2,
		argu_srvCode3 : srvCode3,
		argu_srvCode4 : srvCode4,
		argu_srvCode5 : srvCode5,
		argu_srvCode6 : srvCode6,
		argu_srvCode7 : srvCode7,
		argu_srvCode8 : srvCode8,
		argu_srvCode9 : srvCode9,
		argu_srvCode11 : srvCode11,
		argu_srvCode12 : srvCode12,
		argu_srvCode13 : srvCode13,
		argu_srvCode14 : srvCode14,
		argu_srvCode15 : srvCode15,
		search_createTimeStart : createTimeStart,
		search_resDateStart : resDateStart,
		search_approveTimeStart : approveTimeStart,
		search_createTimeEnd : createTimeEnd,
		search_resDateEnd : resDateEnd,
		search_approveTimeEnd : approveTimeEnd,
		
		argu_realConTimeStart : realConTimeStart,
		argu_realConTimeEnd : realConTimeEnd,
		argu_realConTime : (realConTimeStart == null && realConTimeEnd == null ? null : true),
		
		argu_realHtTimeStart : realHtTimeStart,
		argu_realHtTimeEnd : realHtTimeEnd,
		argu_realHtTime : (realHtTimeStart == null && realHtTimeEnd == null ? null : true),

		argu_realPropertyGetTimeStart : realPropertyGetTimeStart,
		argu_realPropertyGetTimeEnd : realPropertyGetTimeEnd,
		argu_realPropertyGetTime : (realPropertyGetTimeStart == null && realPropertyGetTimeEnd == null ? null : true),

		argu_signDateStart : signDateStart,
		argu_signDateEnd : signDateEnd,
		argu_lendDateStart : lendDateStart,
		argu_lendDateEnd : lendDateEnd,
		argu_signlendDate : (signDateStart == null && signDateEnd == null && lendDateStart == null && lendDateEnd == null ? null : true),
		
		argu_guohuApproveTimeStart : guohuApproveTimeStart,
		argu_guohuApproveTimeEnd : guohuApproveTimeEnd,
		argu_guohuApproveTime : (guohuApproveTimeStart == null && guohuApproveTimeEnd == null ? null : true),
		
		argu_yuCuiOriGrpId : yuCuiOriGrpId,
		argu_isNotResearchCloseCase : isNotResearchCloseCase

	};
	return params;
}
// 日期类型
var createTimeStart, resDateStart, realHtTimeStart, realPropertyGetTimeStart, realConTimeStart, signDateStart, lendDateStart, approveTimeStart,guohuApproveTimeStart;
var createTimeEnd, resDateEnd, realHtTimeEnd, realPropertyGetTimeEnd, realConTimeEnd, signDateEnd, lendDateEnd, approveTimeEnd,guohuApproveTimeEnd;


function oldGetSearchDateValues() {
	createTimeStart = null;
	resDateStart = null;
	realHtTimeStart = null;
	realPropertyGetTimeStart = null;
	realConTimeStart = null;
	signDateStart = null;
	lendDateStart = null;
	approveTimeStart = null;
	createTimeEnd = null;
	resDateEnd = null;
	realHtTimeEnd = null;
	realPropertyGetTimeEnd = null;
	realConTimeEnd = null;
	signDateEnd = null;
	lendDateEnd = null;
	approveTimeEnd = null;
	guohuApproveTimeStart = null;
	guohuApproveTimeEnd = null;
	var codeStr = "";
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if (codeStr.indexOf(val) != -1)
			return false;
		codeStr += val;
		if (start != "") {
			if (val == '30005001') {
				createTimeStart = start;
			} else if (val == '30005002') {
				resDateStart = start;
			} else if (val == '30005003') {
				realConTimeStart = start;
			} else if (val == '30005004') {
				realHtTimeStart = start;
			} else if (val == '30005005') {
				realPropertyGetTimeStart = start;
			} else if (val == '30005006') {
				signDateStart = start;
			} else if (val == '30005007') {
				lendDateStart = start;
			} else if (val == '30005008') {
				approveTimeStart = start;
			} else if (val == '30005009') {
				guohuApproveTimeStart = start;
			}
		}
		if (end != "") {
			if (val == '30005001') {
				createTimeEnd = end;
			} else if (val == '30005002') {
				resDateEnd = end;
			} else if (val == '30005003') {
				realConTimeEnd = end;
			} else if (val == '30005004') {
				realHtTimeEnd = end;
			} else if (val == '30005005') {
				realPropertyGetTimeEnd = end;
			} else if (val == '30005006') {
				signDateEnd = end;
			} else if (val == '30005007') {
				lendDateEnd = end;
			} else if (val == '30005008') {
				approveTimeEnd = end;
			} else if (val == '30005009') {
				guohuApproveTimeEnd = end;
			}
		}
	}
	return true;
}


// 日期控件取值
function getSearchDateValues() {
	
	createTimeStart = null;
	createTimeEnd = null;
	
	resDateStart = null;
	resDateEnd = null;
	
	realHtTimeStart = null;
	realHtTimeEnd = null;
	
	realPropertyGetTimeStart = null;
	realPropertyGetTimeEnd = null;
	
	realConTimeStart = null;
	realConTimeEnd = null;
	
	lendDateStart = null;
	lendDateEnd = null;
	
	signDateStart = null;
	signDateEnd = null;
	
	approveTimeStart = null;
	approveTimeEnd = null;
	
	guohuApproveTimeStart = null;
	guohuApproveTimeEnd = null;
	
	var codeStr = "";
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if (codeStr.indexOf(val) != -1)
			return false;
		codeStr += val;
		if (start != "") {
			if (val == '30005001') {
				//派单日期  CASE_CREATE_TIME
				createTimeStart = start;
			} else if (val == '30005002') {
				//分单日期  CASE_DISPATCH_TIME
				resDateStart = start;
			} else if (val == '30005003') {
				//签约日期  CASE_REAL_CON_TIME
				realConTimeStart = start;
			} else if (val == '30005004') {
				//过户日期  TRANSFER_REAL_HT_TIME
				realHtTimeStart = start;
			} else if (val == '30005005') {
				//领证日期  CASE_REAL_PROPERTY_GET_TIME
				realPropertyGetTimeStart = start;
			} else if (val == '30005006') {
				//签约日期（贷款）  MORT_SIGN_DATE
				signDateStart = start;
			} else if (val == '30005007') {
				//放款日期（贷款）  MORT_LEND_DATE
				lendDateStart = start;
			} else if (val == '30005008') {
				//结案日期  CASE_CLOSE_TIME
				approveTimeStart = start;
			} else if (val == '30005009') {
				//过户审批日期 TRANSFER_APP_TIME
				guohuApproveTimeStart = start;
			}
		}
		if (end != "") {
			if (val == '30005001') {
				createTimeEnd = end;
			} else if (val == '30005002') {
				resDateEnd = end;
			} else if (val == '30005003') {
				realConTimeEnd = end;
			} else if (val == '30005004') {
				realHtTimeEnd = end;
			} else if (val == '30005005') {
				realPropertyGetTimeEnd = end;
			} else if (val == '30005006') {
				signDateEnd = end;
			} else if (val == '30005007') {
				lendDateEnd = end;
			} else if (val == '30005008') {
				approveTimeEnd = end;
			} else if (val == '30005009') {
				guohuApproveTimeEnd = end;
			}
		}
	}
	return true;
}

	// 产品类型
	var srvCode;
	var srvCode1;
	var srvCode2;
	var srvCode3;
	var srvCode4;
	var srvCode5;
	var srvCode6;
	var srvCode7;
	var srvCode8;
	var srvCode9;
	var srvCode11;
	var srvCode12;
	var srvCode13;
	var srvCode14;
	var srvCode15;
function getCheckBoxValues(name) {
	srvCode = "";
	srvCode1 = "";
	srvCode2 = "";
	srvCode3 = "";
	srvCode4 = "";
	srvCode5 = "";
	srvCode6 = "";
	srvCode7 = "";
	srvCode8 = "";
	srvCode9 = "";
	srvCode11 = "";
	srvCode12 = "";
	srvCode13 = "";
	srvCode14 = "";
	srvCode15 = "";
	//$("input[name=" + name + "].selected").each(function() {
	$("span[name='srvCode'].selected").each(function() {
		var val = $(this).attr('value');
		srvCode += val + ",";
		
		if (val == '30004001') {
			srvCode1 = val;
		} else if (val == '30004002') {
			srvCode2 = val;
		} else if (val == '30004003') {
			srvCode3 = val;
		} else if (val == '30004004') {
			srvCode4 = val;
		} else if (val == '30004005') {
			srvCode5 = val;
		} else if (val == '30004006') {
			srvCode6 = val;
		} else if (val == '30004007') {
			srvCode7 = val;
		} else if (val == '30004008') {
			srvCode8 = val;
		} else if (val == '30004009') {
			srvCode9 = val;
		} else if (val == '30004011') {
			srvCode11 = val;
		} else if (val == '30004012') {
			srvCode12 = val;
		} else if (val == '30004013') {
			srvCode13 = val;
		} else if (val == '30004014') {
			srvCode14 = val;
		} else if (val == '30004015') {
			srvCode15 = val;
		}
	});
}

// 清空表单
function cleanForm() {
	$("input[name='case_property'][value=30003006]").prop("checked", true);
	$("input[name='case_status'][value=30001006]").prop("checked", true);
	$("input[name='srvCode']").removeAttr("checked");
	$("input[name='dtBegin']").val("");
	$("input[name='dtEnd']").val("");
	$("#inTextVal").val("");
	checkAllItem();
}
//全选
function checkAllItem() {
		$(".excel_in").prop("checked", true);
		 $("#checkAll").hide();
		 $("#unCheckAll").show();
}
//反选
function unCheckAllItem() {
		$(".excel_in").removeAttr("checked");
		 $("#unCheckAll").hide();
		 $("#checkAll").show();
		
}
//show modal
function showExcelIn() {
	checkAllItem();
	$('#modal-form').modal("show");
}

//show modal
function realShowExcelIn() {
	checkAllItem();
	$('#realModal-form').modal("show");
}

//Excel显示列
var oldColNames = {
	30010001 : [ 'AGENT_NAME','AGENT_PHONE','AGENT_EMP_CODE','AGENT_ORG', 'AGENT_FHJL', 'JFHJL_MGR_CODE','AGENT_QYJL','JQYJL_MGR_CODE',
		 			'AGENT_QYZJ','JQYZJ_MGR_CODE','AGENT_QYDS','JQYDS_MGR_CODE','AGENT_QDORG' ],
	30010002 : [ 'OP_NAME', 'OP_CODE', 'OP_ORG', 'OP_MANAGER','OP_AS',
	             'OP_PARENT_ORG','SRV_STR','PROCESSOR_ID','PROCESSOR_ORG_NAME'],
	30010003 : [ 'PROPERTY_ADDR', 'SELLER', 'SELLER_PHONE', 'BUYER',
			'BUYER_PHONE' ],
	30010004 : 'CREATE_TIME',
	30010005 : 'RES_DATE',
	30010006 : 'CASE_PROPERTY',
	30010007 : 'STATUS',
	30010008 : 'AGENT_QDORG',
	30011001 : ['LOAN_REQ','MORT_TYPE2','LOAN_LOST_TYPE'],
	//30011009 : ,
	30011002 : [ 'MORT_TOTAL_AMOUNT', 'COM_AMOUNT', 'PRF_AMOUNT' ],
	30011003 : [ 'BANK_NAME', 'PARENT_BANK_NAME' ],
	30011004 : [ 'LOANER_NAME', 'IS_LOANER_ARRIVE' ],
	30011005 : 'EVA_NAME',
	30011006 : [ 'CUST_NAME', 'CUST_PHONE' ],
	30011007 : [ 'SIGN_DATE', 'APPR_DATE', 'LEND_DATE' ],
	30011008 : 'COM_DISCOUNT',
	30011010 : 'REC_LETTER_NO',
	30012001 : [ 'REAL_PRICE', 'CON_PRICE','CHECK_PRICE','UNIT_PRICE' ],
	30012002 : [ 'REAL_CON_TIME', 'TAX_TIME', 'PRICING_TIME', 'REAL_PLS_TIME',
		'REAL_HT_TIME', 'GUOHU_CREATE_TIME','GUOHU_COMMENT','REAL_PROPERTY_GET_TIME','CLOSE_TIME','HOUSE_QUANTITY','TRANSFER_HOUSE_HODING_TAX','TRANSFER_PERSONAL_INCOME_TAX','TRANSFER_BUSINESS_TAX','TRANSFER_CONTRACT_TAX','TRANSFER_LAND_INCREMENT_TAX','TAX_HOUSE_HODING_TAX','TAX_PERSONAL_INCOME_TAX','TAX_BUSINESS_TAX','TAX_CONTRACT_TAX','TAX_LAND_INCREMENT_TAX'],
	30013001 : 'SPV_TYPE',
	30013002 : 'AMOUNT',
	30013003 : 'SIGN_TIME',
	30012003:['GUOHU_APPROVER','GUOHU_APPROVER_TIME']
};


function exportToExcel() {
	if (oldGetSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('CTM_CODE');
		displayColomn.push('AGENT_FHJL_CODE');
		$("input[name='basic_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		$("input[name='mortage_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		/*新增确认函编号*/
		displayColomn.push('REC_LETTER_NO');
		displayColomn.push('LOANLOST_CONFIRM_CODE');		
		displayColomn.push('SELF_DEL_REASON');
		displayColomn.push('loanlost_apply_reason');
		$("input[name='trade_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		$("input[name='fund_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		displayColomn.push('EVAL_FEE');
		displayColomn.push('RECORD_TIME');

		var queryOrgFlag = $("#queryOrgFlag").val();
		var isAdminFlag = $("#isAdminFlag").val();
		var queryOrgs = $("#queryOrgs").val();
		var arguUserId=null;
		if(queryOrgFlag == 'true'){
			arguUserId=null;
			if(isAdminFlag == 'true'){
				queryOrgs=null;
			}
		}else{
			queryOrgs= null;
			arguUserId="yes";
		}

		var orgArray = queryOrgs==null?'':queryOrgs.split(",");

		var argu_idflag = '&argu_idflag='+arguUserId;
		
		if(arguUserId==null)argu_idflag='&argu_idflag=';
		var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
		if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
		var params = oldGetParamsValue();
		var queryId = '&queryId=queryCaseExcelItemList';
		var colomns = '&colomns=' + displayColomn;
		
		
		url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
		//url+= "&_s(earch=true";
		//url= decodeURI(url);
//		alert(url);
		$('#excelForm').attr('action', url);
		
		$('#excelForm').method="post" ;
		$('#excelForm').submit();
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
}

/*
 * 2017-04-11
   newExcel显示列
*/
var newColNames = {
	30010001 : [ 'AGENT_NAME', 'AGENT_PHONE','AGENT_EMPLOYEE_CODE','GRP_NAME', 'JFHJL_NAME', 'JFHJL_EMPLOYEE_CODE','JQYJL_NAME','JQYJL_EMPLOYEE_CODE',
		 		 'JQYZJ_NAME','JQYZJ_EMPLOYEE_CODE','JQYDS_NAME','JQYDS_EMPLOYEE_CODE','BA_NAME' ],		 	   
	30010002 : [ 'CASE_REAL_NAME_F', 'CASE_EMPLOYEE_CODE_F', 'CASE_ORG_NAME_F', 'CASE_MANAGER_REAL_NAME','CASE_ASSISTANT_REAL_NAME','CASE_DISTRICT_NAME','CASE_SRV_NAME','CASE_SRV_REAL_NAME','CASE_SRV_ORG_NAME'],
		
	30010003 : [ 'HOUSR_PROPERTY_ADDR', 'GUEST_NAME_UP', 'GUEST_PHONE_UP', 'GUEST_NAME_DOWN','GUEST_PHONE_DOWN' ],	
	
	30010004 : 'CASE_CREATE_TIME',
	30010005 : 'CASE_DISPATCH_TIME',
	30010006 : 'CASE_PROPERTY_CN', //英文
	30010007 : 'CASE_STATUS_CN',
	30010008 : 'AGENT_QDORG',
	
	30011001 : ['CASE_LOAN_REQ_CN','MORT_TYPE_CN','IS_DELEGATE_YUCUI_CN'],
	//30011009 : ,
	30011002 : [ 'MORT_TOTAL_AMOUNT', 'MORT_COM_AMOUNT', 'MORT_PRF_AMOUNT' ],
	30011003 : [ 'MORT_FIN_BRANCH_NAME', 'MORT_FIN_SUB_BRANCH_NAME' ],
	30011004 : [ 'MORT_LOANER_NAME', 'IS_LOANER_ARRIVE_CN' ],
	30011005 : 'CASE_EVA_COMPANY',
	30011006 : [ 'MORT_CUST_NAME', 'MORT_CUST_PHONE' ],
	30011007 : [ 'MORT_SIGN_DATE', 'MORT_APPR_DATE', 'MORT_LEND_DATE' ],
	30011008 : 'MORT_COM_DISCOUNT',
	30011010 : 'LOAN_REC_LETTER_NO',
	30012001 : [ 'CASE_REAL_PRICE', 'CASE_CON_PRICE','CASE_TAX_PRICING','CASE_HOUSE_UNIT_PRICE' ],
	30012002 : [ 'CASE_REAL_CON_TIME', 'CASE_TAX_TIME', 'CASE_PRICING_TIME', 'CASE_REAL_PLS_TIME','TRANSFER_REAL_HT_TIME','TRANSFER_CREATE_TIME','TRANSFER_COMMENT','CASE_REAL_PROPERTY_GET_TIME','CASE_CLOSE_TIME','SIGN_HOUSE_QUANTITY_CN','TRANSFER_HOUSE_HODING_TAX','TRANSFER_PERSONAL_INCOME_TAX','TRANSFER_BUSINESS_TAX','TRANSFER_CONTRACT_TAX','TRANSFER_LAND_INCREMENT_TAX','TAX_HOUSE_HODING_TAX','TAX_PERSONAL_INCOME_TAX','TAX_BUSINESS_TAX','TAX_CONTRACT_TAX','TAX_LAND_INCREMENT_TAX'],
	30013001 : 'SPV_TYPE_CN',
	30013002 : 'SPV_AMOUNT',
	30013003 : 'SPV_SIGN_TIME',
	30012003:['TRANSFER_APP_OPERATOR_NAME','TRANSFER_APP_PASS_TIME']
};


function newExportToExcel() {
	if (getSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('CTM_CODE');		
		$("input[name='basic_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		$("input[name='mortage_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		
		/*新增确认函编号*/
		displayColomn.push('LOAN_LOST_CONFIRM_CODE');
		displayColomn.push('LOAN_SELF_DEL_REASON');		
		displayColomn.push('LOAN_LOST_APPLY_REASON');	
		
		$("input[name='trade_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		$("input[name='fund_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		displayColomn.push('EVA_EVAL_FEE');
		displayColomn.push('EVA_RECORD_TIME');

		var queryOrgFlag = $("#queryOrgFlag").val();
		var isAdminFlag = $("#isAdminFlag").val();
		var queryOrgs = $("#queryOrgs").val();
		var arguUserId=null;
		if(queryOrgFlag == 'true'){
			arguUserId=null;
			if(isAdminFlag == 'true'){
				queryOrgs=null;
			}
		}else{
			queryOrgs= null;
			arguUserId="yes";
		}

		var orgArray = queryOrgs==null?'':queryOrgs.split(",");

		var argu_idflag = '&argu_idflag='+arguUserId;
		
		if(arguUserId==null)argu_idflag='&argu_idflag=';
		var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
		if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
		var params = getParamsValue();
		var queryId = '&queryId=newQueryCaseExcelItemList';//queryCaseExcelItemList
		var colomns = '&colomns=' + displayColomn;
		
		
		url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
		$('#excelForm').attr('action', url);
		
		$('#excelForm').method="post" ;
		$('#excelForm').submit();
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
}


function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
	if(inTextType=='4'){
		initAutocomplete(ctx+"/labelVal/queryUserInfo");
	}else if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
}

//自动补全插件
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

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
		if (array && array.length > 0) {
				$("#teamCode").val(array[0].name);
				$("#yuCuiOriGrpId").val(array[0].id);
		} else {
				$("#teamCode").val("");
				$("#yuCuiOriGrpId").val("");
		}
}
//清空
$('#myCaseListCleanButton').click(function() {
	//$("input[id='inTextVal']").val('');
	//$("input[name='teamCode']").val('');
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("span[name='srvCode']").removeClass("selected");
	//$("select").val("");
	$("#caseProperty").val("");
	$("#status").val("");
	$("#mortageService").val("");
	$("#inTextVal").val("");
	
});


function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function ts(status){
	window.wxc.alert(status+"案件不能拆分案件！");
}

function hlts(){
	window.wxc.alert("没有找到可以合流的案件！");
}