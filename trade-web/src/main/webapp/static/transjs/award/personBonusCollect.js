var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

$(document).ready(function() {
	
	// 初始化列表
	var data = {};	
	data.queryId = "findPersonBonusCollectList";
	data.rows = 10;
	data.page = 1;
	
	reloadGrid(data);
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
			var personCollectList = template('template_personCollectList', data);
			$("#personCollectList").empty();
			$("#personCollectList").html(personCollectList);
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
			personBonusCollectSearchMethod(page);
		}
	});
}


/* 查询按钮查询 */
$('#personBonusCollectSearch').click(function() {
	 personBonusCollectSearchMethod()
});

/* 清空查询条件 */
$('#personBonusCollectClean').click(function() {
	$("input[name='realName']").val('');
	$("input[name='orgId']").val('');
	$("input[name='orgName']").val('');	
	$("input[name='belongMonth']").val('');

});

function personBonusCollectSearchMethod(page){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "findPersonBonusCollectList";
	
	reloadGrid(params);
}

/**
 * 查询参数取得
 */

function getParams() {	
	// 设置查询参数
	var params = {};
	
	// 员工姓名
	var realName = $.trim($("#realName").val());
	if ("" == realName || null == realName) {
		realName = null;
	}

	// 员工姓名组织Id
	var userOrgId = $.trim($("#userOrgId").val());
	if (userOrgId == "" || userOrgId == null) {
		userOrgId = null;
	}
	
	// 计件年月
	var belongMonth = $("#belongMonth").val();	
	
	params.realName = realName;
	params.userOrgId = userOrgId;	
	params.belongMonth = belongMonth;
	
	return params;
}


//导出Excel
function exportPersonBonusToExcel(){
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	// excel导出列
	var displayColomn = new Array;
	//暂时没有计件月份
	displayColomn.push('REAL_NAME');
	displayColomn.push('MOBILE');
	displayColomn.push('EMPLOYEE_CODE');
	displayColomn.push('ORG_NAME');
	displayColomn.push('SUMMONEY');
	
	var params = getParams();	
	var queryId = '&queryId=findPersonBonusCollectList';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + ""
			+ "" + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();
}
