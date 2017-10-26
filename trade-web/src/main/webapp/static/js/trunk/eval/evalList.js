$(document).ready(function() {
	getEvaCompanyList();
	cleanForm();
	aist.sortWrapper({
		reloadGrid : searchMethod
	});

	//基本信息等高
	var cpDivWidth = $("#eval_date").next().width();
	$('#inTextType').next().css("width", cpDivWidth);
	// 初始化列表
	var data = {};
	data.queryId = "queryEvalItemListFortj";
	data.rows = 10;
	data.page = 1;
	data.argu_sessionUserId = $("#userId").val();
	aist.wrap(data);//格式化，添加排序字段及排序正反序
	reloadGrid(data);
	
	$('#checkAllNot').click(function(){
		var my_checkboxes = $('input[name="my_checkbox"]');
		if($(this).prop('checked')){
			for(var i=0; i<my_checkboxes.length; i++){
				$('input[name="my_checkbox"]:eq('+i+')').prop('checked',true);
			}
		}else{
			for(var i=0; i<my_checkboxes.length; i++){
				$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
			}
		}
	});

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
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	//查询日期是否都已OK
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 10;
		params.queryId = "queryEvalItemListFortj";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}

};

function reloadGrid(data) {
	//添加排序-----
	aist.wrap(data);

	var arguUserId=null;
	data.argu_idflag = arguUserId;
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
			var myCaseList = template('template_evalCaseList',data);
			$("#evalList").empty();
			$("#evalList").html(myCaseList);
			// 显示分页
			mycase_initpage(data.total,data.pagesize,data.page, data.records);
			demoPoshytip();
			$("#evalList").subscribeToggle({
				moduleType:"2001",
				subscribeType:"2001"
			});

		},
		error: function (e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}
/**
 * 页面title显示
 */
function demoPoshytip(){
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});
}

//分页
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

	//评估是否关注
	var isSubscribeFilter = $('#isSubscribeFilter option:selected').val();
	if(isSubscribeFilter==null || isSubscribeFilter=='') {
		isSubscribeFilter = -1;
	}
	//评估状态
	var status = $('#eval_status option:selected').val();
	var finOrgCode = $("#finOrgId option:selected").val();
	
	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	//条件为权证专员及所属分行时，AutoComplete自动查询补全数据,intextTypeChange()
	var hVal = $('#inTextVal').attr('hVal');

	var agentName = "";
	var propertyAddr = "";
	var caseCode =  "";
	var buyer="";var seller="";var loan="";var handler="";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '1') {
			propertyAddr = inTextVal.trim();//产权地址
		} else if (inTextType == '2') {
			caseCode = inTextVal.trim();//案件编号
		}else if (inTextType == '3') {
			buyer = inTextVal.trim();//买方
		}else if (inTextType == '4') {
			seller = inTextVal.trim();//卖方
		}else if (inTextType == '5') {
			agentName = inTextVal.trim();//经纪人姓名
		}else if (inTextType == '6') {
			loan = inTextVal.trim();//贷款申请人
		}else if(inTextType == '7'){
			handler = inTextVal.trim();//办理人
		}
	}

	//设置查询参数
	//argu_  where条件参数；   seach_ searchCondition参数
	var params = {
		argu_sessionUserId : $("#userId").val(),
		argu_isSubscribeFilter : isSubscribeFilter,
		argu_caseCode : caseCode,
		argu_evalStatus : status,
		argu_finOrgId:finOrgCode,
		argu_propertyAddr:propertyAddr,
		argu_buyer:buyer,
		argu_seller:seller,
		argu_propertyAddr:propertyAddr,
		argu_agentName:agentName, //经纪人姓名  直接姓名，不是id
		argu_loan:loan,
		argu_handler:handler,
		argu_createTimeStart : createTimeStart,
		argu_finshTimeStart : resDateStart,
		argu_createTimeEnd:createTimeEnd,
		argu_resDateEnd:resDateEnd
	};
	return params;
}

//待dict确认再算
// 日期类型
var createTimeStart, resDateStart,createTimeEnd,resDateEnd;

// 日期控件取值
function getSearchDateValues() {

		var start = $('#dtBegin').val();
		var end = $('#dtEnd').val();
		var val = $('#eval_date').val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if (start != "") {
			if (val == '600001001') {
				//评估上报日期
				createTimeStart = start;
			} else if (val == '600001002') {
				//评估完成日期
				resDateStart = start;
		}
		if (end != "") {
			if (val == '600001001') {
				createTimeEnd = end;
			} else if (val == '600001002') {
				resDateEnd = end;
			}
		}
	}
	return true;
}


// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
	$("input[name='dtEnd']").val("");
}

//搜索条件中，权证专员及所属分行数据补全
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
//清空
$('#myEvalListCleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("#status").val("");
	$("#inTextVal").val("");
	$("#isSubscribeFilter").val('');
});

//结算
$('#settleButton').click(function() {
	   var evalCodeArr = [];
	  $("input[name='my_checkbox']:checked").each(function(i){//把所有被选中的复选框的值存入数组
		  evalCodeArr[i] =$(this).val();
	  });
	
	$.ajax({
		async: true,
		url:ctx+ "/eval/toSettle" ,
		method: "post",
		dataType: "json",
		data: {evals:JSON.stringify(evalCodeArr)},
		beforeSend: function () {
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
			$(".blockOverlay").css({'z-index':'9998'});
		},
		complete: function() {  
            if(status=='timeout'){//超时,status还有success,error等值的情况
          	  Modal.alert(
			  {
			    msg:"抱歉，系统处理超时。"
			  });
	        }
	   } , 
		success: function(data){
			$.unblockUI();
			if(!data.success){
				$.unblockUI();   
				window.wxc.error(data.message);
			}else{
				window.location.href=ctx+"/eval/list";
			}
		},
		error: function (e, jqxhr, settings, exception) {
			$.unblockUI();
			window.wxc.error("系统异常");
		}
	});
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

/*获取评估公司列表*/
function getEvaCompanyList(pcode){
	var friend = $("#finOrgId");
	friend.empty();
	friend.append("<option value=''>请选择</option>");
	 $.ajax({
	    url:ctx+"/manage/queryEvaCompany",
	    method:"post",
	    dataType:"json",
	    data:{"pcode":pcode},
    	success:function(evaComList){
    		if(evaComList != null){
    			for(var i = 0;i<evaComList.length;i++){
    					friend.append("<option value='"+evaComList[i].finOrgCode+"'>"+evaComList[i].finOrgName+"</option>");
    			}
    		}
    	}
	  });
}
