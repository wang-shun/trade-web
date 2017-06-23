
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>贷款列表</title>
	
	<!-- Toastr style -->
    <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox.css' />" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-buttons.css' />" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-thumbs.css' />" />
	
    <!-- Gritter -->
	<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    
	<link href="<c:url value='/css/style.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
		rel="stylesheet">
	<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"
		rel="stylesheet">
	<link
		href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"
		rel="stylesheet">
	<style type="text/css">
	.radio.radio-inline>label {
		margin-left: 10px;
	}
	
	.radio.radio-inline>input {
		margin-left: 10px;
	}
	
	.checkbox.checkbox-inline>div {
		margin-left: 25px;
	}
	
	.checkbox.checkbox-inline>input {
		margin-left: 20px;
	}
	.ui-state-hover{
		cursor:pointer;
	}
	</style>
</head>
<body>
	<!-- title start -->
		
	<!-- title end -->
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="adminOrg" value="${adminOrg}"/>
	<%-- 
	<input type="hidden" id="userId" value="${userId}"/> --%>
	<!-- content start  -->
			
			    
			
		<!-- search start -->
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>贷款列表</h5>
			</div>
			<div class="ibox-content">
				<form method="get" class="form-horizontal">
				
					<div id="dateDiv_0" class="input-group m-b">
						<div id="select_div_0" class="input-group-btn">	
							<select ltype="select" id="case_date_0" name="case_date" class="btn btn-white chosen-select" validate="" onchange="" ligerui="" style="">
								<option value="30005001">创建时间</option>
								<option value="30005002">申请时间</option>
								<option value="30005006">面签时间</option>
								<option value="30005007">放款时间</option>
							</select>
					    </div>
						<div id="datepicker_0"
							class="input-group input-medium date-picker input-daterange"
							data-date-format="yyyy-mm-dd">
							<input id="dtBegin_0" name="dtBegin" class="form-control"
								style="font-size: 13px;" type="text" value=""
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control"
								style="font-size: 13px;" type="text" value=""
								placeholder="结束日期" />
						</div>
					</div>
					<div id="addLine" class="input-group" style="">
						<a class="" href="javascript:addDateDiv();"><font>添加日期检索</font></a>
					</div>

					<div class="hr-line-dashed"></div>
					
					<div id="dateDiv_0" class="input-group m-b">
						<div id="select_div_0" class="input-group-btn">	
							<select ltype="select" id="case_money_0" name="case_money_0" class="btn btn-white chosen-select" validate="" onchange="" ligerui="" style="">
								<option value="30005002">申请金额</option>
								<option value="30005006">面签金额</option>
								<option value="30005007">放款金额</option>
							</select>
					    </div>
						<div id="research_money_div_0" class="input-group input-medium date-picker input-daterange">
							<input type="number" id="start_money_0" name="start_money_0" value="" size="16" class="form-control" style="font-size: 13px;float:left;" placeholder="">
							<span class="input-group-addon">万 </span>						
							<span class="input-group-addon">到</span>
							<input type="number" id="end_money_0" value="" size="16" name="end_money_0" class="form-control" style="font-size: 13px;float:left;" placeholder="">
							<span class="input-group-addon">万</span>
							
						</div>
					</div>
					
				    <div id="addMoneyLine" class="input-group" style="">
						<a class="" href="javascript:addMoneyDiv();"><font>添加金额检索</font></a>
					</div> 

					<div class="hr-line-dashed"></div>

                    <div>
                   		 合作机构 : 
                   		<!--  <aist:dict clazz="span6" id="operOrg" name="operOrg" display="select" dictType = "yu_serv_cat_code_tree" tag="eplus" defaultvalue=""/> -->
            <!--        		<select id="operOrg" class="btn btn-white chosen-select">
                   				<option value="">请选择</option>
								<option value="W0001">安家贷</option>
								<option value="W0002">居易贷</option>
								<option value="W0003">搜易贷</option>
								<option value="W0004">及时雨</option>
						</select> -->
						<select class="btn btn-white "  id="operOrg">
						</select>
						
						
						产品名称 : <aist:dict clazz="btn btn-white chosen-select" id="loanSrvCode" name="loanSrvCode" display="select" dictType = "yu_serv_cat_code_tree" tag="eplus" defaultvalue=""/>
						自填状态 : <aist:dict clazz="btn btn-white chosen-select" id="applyStatus" name="applyStatus" display="select" dictType = "yu_eplus_status" tag="" defaultvalue=""/>
						已确认状态 :<aist:dict clazz="btn btn-white chosen-select" id="confirmStatus" name="confirmStatus" display="select" dictType = "yu_eplus_status" tag="" defaultvalue=""/>
					</div> 
					
					<div class="hr-line-dashed"></div>
					
					<div class="input-group m-b">
						<div id="select_div_1" class="input-group-btn">
							<select id="selectNameType" data-placeholder="搜索条件设定"
								class="btn btn-white chosen-select">
								<option value="0" selected>客户姓名</option>
								<option value="1">产证地址</option>
								<option value="2">业务员姓名</option>
								<option value="3">贷款专员姓名</option>
							</select>
						</div>
						<input id="name" type="text" class="form-control">
					</div>
					
					<span class="input-group-btn ">
						<button id="searchLoanManageButton" type="button"
							class="btn btn-primary pull-right">查询</button>
					</span>
				</form>
			</div>
		</div>
		<!-- search end -->                      
	
		<!-- DataList start -->
		<div class="ibox-content">	
			<table id="gridTable"></table>
		    <div id="gridPager"></div>
	    </div>
	    <form action="#" method="post" id="excelForm">
	    	<a class="btn btn-primary" href="javascript:exportToExcel()">导出</a>
	    </form>	
		<!-- DataList end -->
	<div id="alertOper"></div>
	<!-- content end  -->
	
  	<content tag="local_script">
   		 <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	  	 <script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
		 <script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
		 <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
		 <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		 <script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
		<%--  <script src="<c:url value='/js/trunk/case/mycase_list.js' />"></script>  --%><!-- iCheck --> 
	    <!-- Peity -->
	    <script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	
	    <!-- jqGrid -->
	    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		    
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox.js' />"></script>
			
		<!-- Add Button helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-buttons.js' />"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-thumbs.js' />"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-media.js' />"></script>
          
		<script src="<c:url value='/transjs/loan/loan.manage.list.js' />"></script>

		<script>
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
		// 日期类型
		var createTimeStart, resDateStart, realHtTimeStart, realPropertyGetTimeStart, realConTimeStart, signDateStart, lendDateStart, approveTimeStart,guohuApproveTimeStart;
		var createTimeEnd, resDateEnd, realHtTimeEnd, realPropertyGetTimeEnd, realConTimeEnd, signDateEnd, lendDateEnd, approveTimeEnd,guohuApproveTimeEnd;

		// 日期控件取值
		function getSearchDateValues() {
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
		
		// 添加日期查询条件
		var divIndex = 1;
		var count = $('#case_date_0 option:last').index();
		function addDateDiv() {

			var txt = '<div class="input-group m-b"><div id="dateDiv_' + divIndex + '" class="input-group m-b-xs m-t-xs">';
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
			$("#dateDiv_" + index).remove();
		}
			jQuery(document).ready(function() {
				getBankList('');
				 $(".fancybox").fancybox({
						maxWidth	: 650,
						maxHeight	: 450,
						fitToView	: false,
						width		: '70%',
						height		: '55%',
						autoSize	: false,
						closeClick	: false,
						openEffect	: 'none',
						closeEffect	: 'none'
					});
				 
				 $('#alertOper').fancybox({
						type : 'iframe',
						autoScale : false,
						fitToView : false,
						width : '95%',
						height : '95%',
						autoDimensions : true,
						showCloseButton : true,
						close : true,
						helpers : {
							overlay : {
								closeClick : false
							}
						},
						iframe : {
							preload : false
						},
						openEffect : 'none',
						closeEffect : 'none',
						afterClose : function() {
							$("#gridTable").trigger("reloadGrid");//刷新列表
						}
					});
				 
				   var districtOrgId = $("#adminOrg").val();
				   var districtArray = districtOrgId==null?null:districtOrgId.split(",");
				   LoanManageList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager',districtArray);
				   
				   $('#searchLoanManageButton').click(function() {
					   if (getSearchDateValues() && getSearchMoneyValues()) {
							var params = getParamsValues();
							 
							$("#gridTable").setGridParam({
								"postData" : params,
								"page":1 
							}).trigger('reloadGrid');
						} else {
							window.wxc.alert("请不要选择同样的日期类型或者金额类型！");
						}
				   });
				   
				   $('#cleanButton').click(function() {
					   $("input[type=text]").val();
			    	   $("input[type=select]").attr("value","");
				   });
					$('#datepicker_0').datepicker({
						format : 'yyyy-mm-dd',
						weekStart : 1,
						autoclose : true,
						todayBtn : 'linked',
						language : 'zh-CN'
					});
			});
			
			/**
			 * 查询参数取得
			 * @returns {___anonymous6020_7175}
			 */
			function getParamsValues() {
				// 选择名称类型
				var nameType = $('#selectNameType').val();
				// 姓名
				var name = $('#name').val();
				var customerName = "";
				var propertyAddr = "";
				var agentName = "";
				var loanName = "";
				if (name != null && name.trim() != "") {
					if (nameType == 0) {
						customerName = name;
					} else if (nameType == 1) {
						propertyAddr = name;
					} else if (nameType == 2) {
						agentName = name;
					}else if (nameType == 3) {
						loanName = name;
					}
				}
				var operOrg = $('#operOrg').val();
				var loanSrvCode = $('#loanSrvCode').val();
				var applyStatus = $('#applyStatus').val();
				var confirmStatus = $('#confirmStatus').val();
				
				var districtOrgId = $("#adminOrg").val();
				var districtArray = districtOrgId==null?null:districtOrgId.split(",");
				//设置查询参数
				var params = {
					search_customerName : customerName,
					search_propertyAddr : propertyAddr,
					search_agentName :agentName,
					search_loanName : loanName,
					search_operOrg : operOrg,
					search_loanSrvCode : loanSrvCode,
					search_applyStatus : applyStatus,
					search_confirmStatus : confirmStatus,
					search_createTimeStart : createTimeStart,
					search_resDateStart : resDateStart,
					search_signDateStart : signDateStart,
					search_lendDateStart : lendDateStart,
					search_createTimeEnd : createTimeEnd,
					search_resDateEnd : resDateEnd,
					search_signDateEnd : signDateEnd,
					search_lendDateEnd : lendDateEnd,
					search_applyMoneyStart : applyMoneyStart,
					search_loadMoneyStart : loadMoneyStart,
					search_signMoneyStart : signMoneyStart,
					search_applyMoneyEnd : applyMoneyEnd,
					search_loadMoneyEnd : loadMoneyEnd,
					search_signMoneyEnd : signMoneyEnd,
					search_districtOrgId : districtArray
				};
				return params;
			}
			 
			// 日期控件取值
			var applyMoney,loadMoney,signMoney;
			function getSearchMoneyValues() {
				applyMoneyStart = null;
				loadMoneyStart = null;
				signMoneyStart = null;
				applyMoneyEnd = null;
			 	loadMoneyEnd = null;
			 	signMoneyEnd = null;
			 	var codeStr = "";
			 	for (var r = 0; r < researMoneyIndex; r++) {
			 		var val = $('#case_money_' + r + ' option:selected').val();
			 		if (val == undefined)
			 			continue;
			 		var start = $('#start_money_' + r).val();
			 		var end = $('#end_money_' + r).val();

			 		if (codeStr.indexOf(val) != -1)
			 			return false;
			 		codeStr += val;
			 		if (start != "") {
			 			start=parseFloat(start)*10000;
			 			// 申请
			 			if (val == '30005002') {
			 				applyMoneyStart = start;
			 			// 面签
			 			} else if (val == '30005006') {
			 				signMoneyStart = start;
			 			// 放款
			 			} else if (val == '30005007') {
			 				loadMoneyStart = start;
			 			}
			 		}
			 		if (end != "") {
			 			// 申请
			 			end=parseFloat(end)*10000;
			 			if (val == '30005002') {
			 				applyMoneyEnd = end;
			 			// 面签
			 			} else if (val == '30005006') {
			 				signMoneyEnd = end;
			 			// 放款
			 			} else if (val == '30005007') {
			 				loadMoneyEnd = end;
			 			}
			 		}
			 	}
			 	return true;
			}
			
			// 添加金额查询条件
			var researMoneyIndex = 1;
			function addMoneyDiv() {
				var div = '<div id="money_div_'+ researMoneyIndex +'" class="input-group m-b">';
				div += '<div id="select_moeny_div_'+ researMoneyIndex +'" class="input-group-btn">';	
				div += '<select ltype="select" id="case_money_'+ researMoneyIndex +'" class="btn btn-white chosen-select" validate="" onchange="" ligerui="" style="">';
				div += '<option value="30005002">申请金额</option>';
				div += '<option value="30005006">面签金额</option>';
				div += '<option value="30005007">放款金额</option>';
				div += '</select></div>';
				
				div += '<div id="research_money_div_'+ researMoneyIndex +'" class="input-group input-medium date-picker input-daterange">';
				div	+= '<input type="number" id="start_money_'+ researMoneyIndex +'" value="" size="16" class="form-control" style="font-size: 13px;float:left;" placeholder="">';
				div += '<span class="input-group-addon">万 </span><span class="input-group-addon">到</span>';
			    div += '<input type="number" id="end_money_'+researMoneyIndex+'" value="" size="16" id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;float:left;" placeholder=""><span class="input-group-addon">万 </span>';
				div += '<span class="input-group-addon"><a href="javascript:removeMoneyDiv('
					+ researMoneyIndex + ');"><font>删除</font></a></span></div></div>';

				$("#addMoneyLine").before(div);
				researMoneyIndex++;
			}
			
			 //删除日期控件
			function removeMoneyDiv(index) {
				$("#money_div_" + index).remove();
			}

			
		    function cleanButton() {
	    	   $("input[type=text]").val();
	    	   $("input[type=select]").attr("value","");
		    }
		    
		    //导出Excel
		    function exportToExcel() { 
		    		var ctx = $("#ctx").val();
		    		var url = "/quickGrid/findPage?xlsx&";
		    		
		    		var displayColomn = new Array;
		    		displayColomn.push('loanSrvCode');
		    		displayColomn.push('finOrgName');
		    		displayColomn.push('propertyAddr');
		    		displayColomn.push('custName');
		    		displayColomn.push('executorId');
		    		displayColomn.push('applyStatus');
		    		displayColomn.push('confirmStatus');
		    		
		    		displayColomn.push('loanAmount');
		    		displayColomn.push('applyTime');
		    		displayColomn.push('actualAmount');
		    		displayColomn.push('releaseTime');
		    		displayColomn.push('agentName');
		    		displayColomn.push('jfhjlName');
		    		
		    		displayColomn.push('orgName');
		    		displayColomn.push('jqydsName');
		    		displayColomn.push('belogDistrict');
		    		displayColomn.push('createTime');
		    		displayColomn.push('PARENT_ORG_NAME');
		    		displayColomn.push('yuOrgName');
		    		var params;
		    		if (getSearchDateValues() && getSearchMoneyValues()) {
						params = getParamsValues();
					} else {
						window.wxc.alert("请不要选择同样的日期类型或者金额类型！");
						return false;
					}
		    		var queryId = '&queryId=loanManageList';
		    		var colomns = '&colomns=' + displayColomn;
		    		url = ctx + url + jQuery.param(params) + queryId + colomns;

		    		$('#excelForm').attr('action', url);
		    		$('#excelForm').submit();
		    		//caseDistribute();
		    		//停顿2s后再执行
		    		//var ids = jQuery("#gridTable").jqGrid('getDataIDs');
		    		//setTimeout(function(){changeWarnListExportTime(ctx,ids);},2000);
		    }
		    
		    function changeWarnListExportTime(ctx,ids){
		    	$.ajax({
		    		cache:false,
					async:false,
					type:"GET",
		    		url:ctx+"/eloan/updateWarnListTime?idStr="+ids,
		    		dataType:"json",
		    		success:function(data){
		    			//alert(data.message)
						if(data.success){
							var data = {};
					    	
					    	data.search_propertyAddr =$.trim( $('#propertyAddr').val() );  //知识编码
					    	data.search_loanSrvCode =$.trim( $('#loanSrvCode').val() );  //知识标题
					    	data.queryId="warnListQuery";
					    	
			 		    	$("#gridTable").jqGrid('setGridParam',{
					    		datatype:'json',
					    		mtype:'post',
					    		postData:data
					    	}).trigger('reloadGrid'); 
						}
		    		},
					error : function(errors) {
						window.wxc.error("处理出错,请刷新后再次尝试！");
					}
		    	});
		    }
		    
		    function getBankList(pcode){
				var friend = $("#operOrg");
				friend.empty();
				friend.append("<option value='' selected='selected'>请选择</option>");
				 $.ajax({
				    url:ctx+"/manage/queryFin",
				    method:"post",
				    dataType:"json",
				    data:{"pcode":pcode},
			    	success:function(data){
			    		if(data.bankList != null){
			    			for(var i = 0;i<data.bankList.length;i++){
			    				friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
			    			}
			    		}
			    	}
				  });
			}
		    
		</script>
	</content>

</body>
</html>