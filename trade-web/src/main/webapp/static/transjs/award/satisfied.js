var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

$(document).ready(function() {
	
	// 初始化列表
	var data = {};	
	var belongMonth = getBlongMonth();
	data.queryId = "kpiCaseListQuery";
	data.rows = 10;
	data.page = 1;
	data.belongMonth = belongMonth;
	reloadGrid(data);
	
	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : satisSearchMethod
	});
	
	isShowSatButton();
});


function reloadGrid(data) {
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId = null;
	if (queryOrgFlag == 'true') {
		arguUserId = null;
		if (isAdminFlag == 'true') {
			queryOrgs = null;
		}
	} else {
		queryOrgs = null;
		arguUserId = "yes";
	}

	var sortcolumn = $('span.active').attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

	var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
	data.argu_idflag = arguUserId;
	data.argu_queryorgs = orgArray;
	aist.wrap(data);

	$.ajax({
		async : true,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		beforeSend : function() {
			$.blockUI({
				message : $("#salesLoading"),
				css : {
					'border' : 'none',
					'z-index' : '9999'
				}
			});
			$(".blockOverlay").css({
				'z-index' : '9998'
			});
		},
		success : function(data) {			
			$.unblockUI();
			data.ctx = ctx;
			var satisList = template('template_satisList', data);
			$("#satisList").empty();
			$("#satisList").html(satisList);
			// 显示分页
			initpage(data.total, data.pagesize, data.page, data.records);
			
		},
		error : function(e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}

//分页
function initpage(totalCount, pageSize, currentPage, records) {
	if (totalCount > 1500) {
		totalCount = 1500;
	}
	var currentTotalstrong = $('#currentTotalPage').find('strong');
	if (totalCount < 1 || pageSize < 1 || currentPage < 1) {
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage + '/' + totalCount);
	$('#totalP').text(records);
	$(function() {
		// top
		$('.demo-top').poshytip({
			className : 'tip-twitter',
			showTimeout : 1,
			alignTo : 'target',
			alignX : 'center',
			alignY : 'top',
			offsetX : 8,
			offsetY : 5,
		});
	});

	$("#pageBar").twbsPagination({
		totalPages : totalCount,
		visiblePages : 9,
		startPage : currentPage,
		first : '<i class="fa fa-step-backward"></i>',
		prev : '<i class="fa fa-chevron-left"></i>',
		next : '<i class="fa fa-chevron-right"></i>',
		last : '<i class="fa fa-step-forward"></i>',
		showGoto : true,
		onPageClick : function(event, page) {
			satisSearchMethod(page);
		}
	});
}

/* 查询按钮查询 */
$('#satisSearch').click(function() {
		satisSearchMethod();
});

/* 清空查询条件 */
$('#satisClean').click(function() {
	$("input[name='caseCode']").val('');
});

function satisSearchMethod(page){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "kpiCaseListQuery";
	
	reloadGrid(params);
}

/**
 * 查询参数取得
 */

function getParams() {	
	// 设置查询参数
	var params = {};
	
	// 员工姓名
	var caseCode = $.trim($("#caseCode").val());
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}
	
	// 计件年月
	//var belongMonth = $("#belongMonth").val();	
	var belongMonth = getBlongMonth();
	params.caseCode = caseCode;
	params.belongMonth = belongMonth;
	
	return params;
}


//导出Excel
function exportStaisToExcel(){

	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	// excel导出列
	var displayColomn = new Array;
	//暂时没有计件月份
	displayColomn.push('CASE_CODE');
	displayColomn.push('SALER_SIGN_SAT');	
	displayColomn.push('SALER_LOAN_SAT');	
	displayColomn.push('SALER_GUOHU_SAT');	
	displayColomn.push('BUYER_SIGN_SAT');	
	displayColomn.push('BUYER_COM_SAT');	
	displayColomn.push('BUYER_PSF_SAT');	
	displayColomn.push('BUYER_GUOHU_SAT');	
	displayColomn.push('SALER_CALLBACK');
	displayColomn.push('BUYER_CALLBACK');
	displayColomn.push('BELONG_MONTH');
	
	var params = getParams();	
	var queryId = '&queryId=kpiCaseListQuery';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + ""
			+ "" + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();
}



//获取计件年月信息
function getBlongMonth(){
	var bm = "";	
	//方式一
	var belongMonth =  $.trim($("#belongMonth",window.parent.document).val());
	//方式二
	//var belongMonth1 = parent.document.getElementById("belongMonth").value;
  if(belongMonth =="" || belongMonth == null || belongMonth == undefined){
  	bm == null;
  }else{
  	bm = belongMonth + "-01";
  }
  return bm;
}


//案件case_code排序图标变化函数
function caseCodeSort() {
	if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}


//同步满意度到KPI表(调用存储过程)
function syncSatisListToKpi(){
	
	var  belongMonth = getBlongMonth();
	window.wxc.confirm("确定同步案件各环节满意度数据？",{"wxcOk":function(){
		$.ajax({
	        url:ctx+ "/award/syncSatisListToKpi" ,
	        method: "get",
	        dataType: "json",
	        data: {"belongMonth":belongMonth},
	        beforeSend: function () {  
	        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
	        },  
	        success: function(data){
	          $.unblockUI();   	 
	          if(data.success){
					 window.wxc.confirm("恭喜,满意度数据同步成功！",{"wxcOk":function(){
						 //TODO 查询数据应该是当前月份的数据；默认显示上月的快照，时间待定
						 satisSearchMethod();
					 }
				   })
				 }else{
					 window.wxc.error("满意度数据同步失败！");
				 } 
	        },
	        error: function (e, jqxhr, settings, exception) {
	        	$.unblockUI();   	 
	        }  
	   })
	 }})
}


function isShowSatButton(){
	var belongMonth = getBlongMonth();
	$.ajax({
        url:ctx+ "/newAward/isShowSatButton" ,
        method: "get",
        dataType: "json",
        data: {"belongMonth":belongMonth},

        success: function(data){        
        	if(data.success == true){			  
        		$("#SatisButton").hide();
        	}
        },
        error: function (e, jqxhr, settings, exception) {
        	 
        }  
   })
}