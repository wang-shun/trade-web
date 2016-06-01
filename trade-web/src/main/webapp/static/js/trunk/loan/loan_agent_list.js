$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	//initAutocomplete(ctx+"/labelVal/queryUserInfo");
	
	searchMethod();
	reloadGrid(params);
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

//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

function reloadGrid(data) {
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
      	  var loanAgentList = template('template_loanAgentList' , data);
		  $("#loanAgentList").empty();
		  $("#loanAgentList").html(loanAgentList);
		  // 显示分页 
          initpage(data.total,data.pagesize,data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
   });
}

function initpage(totalCount,pageSize,currentPage,records)
{
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
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			 //console.log(page);
			searchMethod(page);
	    }
	});
}

//查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	var params = {};
	params.page = page;
	params.rows = 12;
	params.queryId = "loanAgentListQuery";
	
	params.search_caseCode = $('#caseCode').val();
	params.search_propertyAddr = $('#propertyAddr').val();
	// 日期查询
	var applyTimeStart = null, applyTimeEnd = null, signTimeStart = null, signTimeEnd = null, releaseTimeStart = null,releaseTimeEnd = null;
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if(start&&start!=''){
			start=start;
		}
		if (start != "") {
			if (val == '1') {
				applyTimeStart = start;
			} else if (val == '2') {
				signTimeStart = start;
			}else if (val == '3') {
				releaseTimeStart = start;
			}
		}
		if (end != "") {
			if (val == '1') {
				applyTimeEnd = end;
			} else if (val == '2') {
				signTimeEnd = end;
			}else if (val == '3') {
				releaseTimeEnd = start;
			}
		}
	}
	params.search_applyTimeStart = applyTimeStart;
	params.search_applyTimeEnd = applyTimeEnd;
	params.search_signTimeStart = signTimeStart;
	params.search_signTimeEnd = signTimeEnd;
	params.search_releaseTimeStart = releaseTimeStart;
	params.search_releaseTimeEnd = releaseTimeEnd;
	params.search_realName = 	$("#realName").val();
	params.argu_yuCuiOriGrpId = 	$("#yuCuiOriGrpId").val();

	reloadGrid(params);
};

//查询
$('#searchButton').click(function() {
	searchMethod();
});

//添加日期查询条件
var divIndex = 1;
var count = $('#case_date_0 option:last').index();
function addDateDiv() {

	var txt = '<div class="col-md-12 form-group"><label class="col-md-1  col-sm-2 control-label"></label><div id="dateDiv_' + divIndex + '" class="input-group m-b-xs m-t-xs">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
			+ divIndex
			+ '" class="btn btn-white chosen-select" name="case_date" ltype="select" >';
	txt += $("#case_date_0").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
			+ divIndex
			+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
			+ divIndex
			+ '" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
			+ divIndex
			+ '" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
			+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$("#addLine").before(txt);
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

//查看未被选择的日期类型
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
	$("#dateDiv_" + index).remove();
}

function initAutocomplete(url){
	$("#executorId").AutoComplete({
		data:url,
		'itemHeight': 20,
        'width': 280,
        maxItems:10,
        ajaxType:'POST',
        beforeLoadDataHandler:function(){
        	$("#executorId").attr('value','');
        	return true;
        },
        afterSelectedHandler:function(data){ 
        	if(data&&data.value){
        		$("#executorId").attr('value',data.value);
        	}else{
        		$("#executorId").attr('value','');
        	}
		}
    }).AutoComplete('show');
}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#orgName").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
		
	/*	var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);*/
	}else{
		$("#orgName").val("");
		$("#yuCuiOriGrpId").val("");
	}
}