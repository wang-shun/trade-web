var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

/**
 * 信贷员派单流程列表
 */
$(document).ready(function() {
	
	// 初始化列表
	var data = {};	
	data.queryId = "findLoanerProcessList";
	data.rows = 10;
	data.page = 1;
	
	reloadGrid(data);

	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : loanerConditionSearchMethod
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
	
	getParentBank($("select[name='loanLostFinOrgName']"),$("select[name='loanLostFinOrgNameYc']"), "", "", "");

	$("select[name='loanLostFinOrgName']").change(function() {
		getBranchBankList($("select[name='loanLostFinOrgNameYc']"), $("select[name='loanLostFinOrgName']").val(), "");

	})
			
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
			var loanerProcessList = template('template_loanerProcessList', data);
			$("#loanerProcessList").empty();
			$("#loanerProcessList").html(loanerProcessList);
			// 显示分页
			initpage(data.total, data.pagesize, data.page, data.records);
			
		},
		error : function(e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}

// 分页
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
			loanerConditionSearchMethod(page);
		}
	});
}
/*function reloadGrid(){
	var data = getParams();
	
	$("#loanerProcessList").reloadGrid({
    	ctx : ctx,
		queryId : "findLoanerProcessList",
	    templeteId : 'template_loanerProcessList',
	    data : data,
	    wrapperData : data
    });
}*/

/**
 * 查询参数取得
 */

function getParams() {	
	// 设置查询参数
	var params = {};
	
	// 案件编号
	var caseCode = $("#caseCode").val().trim();
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}

	// 物业地址
	var propertyAddr = $("#propertyAddr").val().trim();
	if (propertyAddr == "" || propertyAddr == null) {
		propertyAddr = null;
	}
	// 信贷员	
	var loanerId = $("#loanerId").val().trim();	
	if (loanerId == "" || loanerId == null) {
		loanerId = null;
	}
	// 银行信息
	var loanLostFinOrgNameCode = $("#loanLostFinOrgName option:selected").val();
	var loanLostFinOrgNameYcCode = $("#loanLostFinOrgNameYc option:selected").val();
	
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.loanerId = loanerId;		
	
	params.loanLostFinOrgNameCode = loanLostFinOrgNameCode;
	params.loanLostFinOrgNameYcCode = loanLostFinOrgNameYcCode;
	
	return params;
}
/* 查询按钮查询 */
$('#loanerConditionSearch').click(function() {
	loanerConditionSearchMethod()
});

//查询
function loanerConditionSearchMethod(page) {
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "findLoanerProcessList";
	
	reloadGrid(params);
};

// 清空
$('#loanerConditionClean').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');
	
	$("input[name='loanerId']").val('');
	$("input[name='loanerName']").val('');
	
	$("#loanLostFinOrgNameYc").val("");
	$("#loanLostFinOrgName").val("");
	
});

// 案件case_code排序图标变化函数
function caseCodeSort() {
	if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}



/*
 * 取消流程
 * */
function  finishLoanerProcess(instCode,caseCode,taskId,isMainLoanBankProcess,loanerPkid){
	if((instCode == null || instCode == "" || instCode == undefined) || (caseCode == null || caseCode == "" || caseCode == undefined) || (taskId == null || taskId == "" || taskId == undefined)){
		window.wxc.alert('结束流程请求参数有误！'); 
		return;
	}
	window.wxc.confirm("确认取消该信贷员的拍单流程？",{"wxcOk":function(){
	$.ajax({
		url:ctx+"/task/loanerProcessCancle",
		method:"post",
		dataType:"json",
		data:{		
			caseCode:caseCode,
			taskId:taskId,
			processInstanceId:instCode,
			isMainLoanBankProcess:isMainLoanBankProcess,
			loanerPkid:loanerPkid
		},
		beforeSend:function(){
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
			$(".blockOverlay").css({'z-index':'9998'});
		},
		complete: function() {
			$.unblockUI();
			if(status=='timeout'){//超时,status还有success,error等值的情况
				Modal.alert(
						{
							msg:"抱歉，系统处理超时。"
						});
				$(".btn-primary").one("click",function(){
					parent.$.fancybox.close();
				});
			}
		} ,
		success : function(data) {
			if(data.success){
				window.wxc.success("恭喜，取消派单流程成功！",{"wxcOk":function(){
/*					window.close();
					window.opener.callback();*/
					window.location.reload(); 
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		error : function(errors) {
			window.wxc.error(errors);
		}
	});
 }});
}


function loanerClick(){
	userSelect({
		startOrgId : '10B1F16BDC5E7F33E0532429030A8872',//非营业部
		expandNodeId : '10B1F16BDC5E7F33E0532429030A8872',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',		
		jobCode : '',
		callBack : selectLoanerUserBack
	});
	
	
	//选取人员的回调函数
	function selectLoanerUserBack(array) {	
		if (array && array.length > 0) {			
			$("#loanerName").val(array[0].username);	
			$("#loanerId").val(array[0].userId);

		} else {
			$("#loanerName").val("");
			$("#loanerId").val("");		
		}
	}
}



//查询分行信息
function getParentBank(selector, selectorBranch, finOrgCode, tag, flag) {
	var bankHtml = "<option value=''>请选择</option>";
	var param = {
		nowCode : finOrgCode
	};
	// cl 表示入围银行（即和中原合作的银行，范围小一些）; 不加tag=cl 表示临时银行,范围大一些，银行多一些
	// finOrgCode 银行的代码，不加该字段表示查询所有的分行信息
	// flag, "egu" 农业银行 单独处理
	if (tag == 'cl') {
		param.tag = 'cl';
	}
	$
			.ajax({
				cache : true,
				url : ctx + "/manage/queryParentBankList",
				method : "post",
				dataType : "json",
				async : false,
				data : param,
				success : function(data) {
					if (data != null) {
						for (var i = 0; i < data.length; i++) {
							if ((data[i].finOrgCode != '1032900' && data[i].finOrgCode != '3082900')
									|| flag != 'egu') {// 不作农业银行的讯价
								var coLevelStr = '';
								bankHtml += "<option coLevel='"
										+ data[i].coLevel + "' value='"
										+ data[i].finOrgCode + "'>"
										+ data[i].finOrgNameYc + coLevelStr
										+ "</option>";
							}
						}
					}
				},
				error : function(e) {
					window.wxc.error(e);
				}
			});

	selector.find('option').remove();
	selector.append($(bankHtml));
	$.ajax({
		url : ctx + "/manage/queryParentBankInfo",
		method : "post",
		dataType : "json",
		async : false,
		data : {
			finOrgCode : finOrgCode,
			flag : flag
		},
		success : function(data) {
			if (data != null) {
				selector.val(data.content);
			}
		}
	});

	getBranchBankList(selectorBranch, selector.val(), finOrgCode, tag, flag);

	return bankHtml;
}

// 查询 分行对应的支行信息
function getBranchBankList(selectorBranch, pcode, finOrgCode, tag, flag) {
	selectorBranch.find('option').remove();
	selectorBranch[0];
	selectorBranch.append($("<option value=''>请选择</option>"));
	var param = {
		faFinOrgCode : pcode,
		flag : flag,
		nowCode : finOrgCode
	};
	if (tag == 'cl') {
		param.tag = 'cl';
	}
	$.ajax({
		cache : true,
		url : ctx + "/manage/queryBankListByParentCode",
		method : "post",
		dataType : "json",
		async : false,
		data : param,
		success : function(data) {
			if (data != null) {
				for (var i = 0; i < data.length; i++) {
					var coLevelStr = '(' + data[i].coLevelStr + ')';

					// 以下各支行后面有(其他)
					// var option = $("<option coLevel='"+data[i].coLevel+"'
					// value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
					var option = $("<option coLevel='" + data[i].coLevel
							+ "' value='" + data[i].finOrgCode + "'>"
							+ data[i].finOrgNameYc + "</option>");
					if (data[i].finOrgCode == finOrgCode) {
						option.attr("selected", true);
					}

					selectorBranch.append(option);
				}
			}
		}
	});

	return true;
}
