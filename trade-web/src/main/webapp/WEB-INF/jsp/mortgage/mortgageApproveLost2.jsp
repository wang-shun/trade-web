<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>流失案件</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/style.css" rel="stylesheet" />
<!-- Data Tables -->
<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<style type="text/css">
.hint {
	position: relative;
	display: inline-block;
}

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}

.hint:hover:before, .hint:hover:after {
	opacity: 1;
}

.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}

.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}

/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}

.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}

.hint-top:hover:before {
	margin-bottom: -10px;
}

.hint-top:hover:after {
	margin-bottom: 2px;
}
.goods_type  .selected {
	border-color: #1c84c6;
}
/* top */
.hint-top1:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}

.hint-top1:after {
	bottom: 100%;
	margin-bottom: 2px;
	width: 80px !important;
	white-space: normal !important;
	word-break: break-all !important;
}

.hint-top1:hover:before {
	margin-bottom: -10px;
}

.hint-top1:hover:after {
	margin-bottom: 2px;
	width: 80px !important;
	white-space: normal !important;
	word-break: break-all !important;
}
/* top */
.hint-top2:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}

.hint-top2:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}

.hint-top2:hover:before {
	margin-bottom: -10px;
}

.hint-top2:hover:after {
	margin-bottom: 2px;
	width: 280px !important;
	white-space: normal !important;
	word-break: break-all !important;
}
</style>
</head>
<body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">贷款流失筛选</h2>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">案件编号 </label> <input
							class="teamcode input_type" 
							placeholder="请输入" value="" id="caseCode" name="caseCode" />
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 产证地址 </label> <input
							class="teamcode input_type" style="width: 435px;"
							placeholder="请输入" value="" id="propertyAddr" name="propertyAddr" />
					</div>
				</div>


				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">主办 </label> <input
							id="caseoperator" name="caseoperator" class="teamcode input_type"
							value="" hVal="" placeholder="请输入" class="teamcode input_type"							
							onclick="chooseCaseOperator('${serviceDepId}')" />
						<div class="input-group float_icon organize_icon"
							id="managerOnclick">
							 <i class="icon iconfont">&#xe627;</i>
						</div>
					</div>
					<div class="form_content">
						<label
							class="control-label sign_left_small select_style mend_select">审批日期</label>
						<div class="input-group sign-right dataleft input-daterange"
							data-date-format="yyyy-mm-dd" id="datepicker_0">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" value="${startTime}"
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text" value="${endTime}" placeholder="结束日期">
						</div>
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 案件组织 </label> <input
							class="teamcode input_type" placeholder="请输入" id="orgName"
							name="orgName" value=""
							onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', 
						orgType:'',departmentType:'',departmentHeriarchy:'',expandNodeId:'${serviceDepId}',						
						chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})">
						<input type="hidden" id="yuCuiOriGrpId" value="">
						<div class="input-group float_icon organize_icon"
							id="organizeOnclick">
							<i class="icon iconfont">&#xe61b;</i>
						</div>
					</div>
					<div class="form_content">
						<div class="goods_type themelist">
							<label class="control-label sign_left_small"> 贷款类型 </label> 
						<span
								id="srvCode_Null_OR_Empty" name="srvCode" class="btn btn-white" onclick=""
								value="NULL"> 未选择 </span> <span id="srvCode_Singleness" name="srvCode"
								class="btn btn-white" onclick="" value="30016001"> 纯商贷 </span> <span
								id="srvCode_Mixture" name="srvCode" class="btn btn-white" onclick=""
								value="30016002"> 组合贷 </span> 
						</div>
					</div>
				</div>
			
				<div class="form_content space" style="margin-left:122px;">
					<div class="add_btn">
						<button id="searchButton" type="button" class="btn btn-success">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="button" id="loanLostExportExcelButton"
							class="btn btn-success"
							onclick="javascript:loanLostCaseExportToExcel()" >导出列表</button>
						<button type="button" id="loanLostCleanButton"
							class="btn btn-grey">清&nbsp;&nbsp;空</button>
					</div>
				</div>
			</form>			
	</div>
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0"
			class="table table_blue table-striped table-bordered table-hover "
			id="editable">
			<thead>
				<tr>
					<th><span class="sort" sortColumn="CASE_CODE" sord="desc"
						onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti"
						class="fa fa-sort-desc fa_down"></i></th>
					<th>案件地址</th>
					<th><span class="sort" sortColumn="START_TIME_" sord="desc"
						onclick="createTimeSort();">审批日期</span><i id="createTimeSorti"
						class="fa fa-sort-asc fa_up"></i></th>
					<th>经办人</th>
					<th>贷款类型</th>
				</tr>
			</thead>
			<tbody id="myMortgageApproveLostList"></tbody>
		</table>
	</div>

	<div class="text-center page_box">
		<span id="currentTotalPage"><strong></strong></span> <span
			class="ml15">共<strong id="totalP"></strong>条
		</span>&nbsp;
		<div id="pageBar" class="pagergoto"></div>
	</div>
	</div>
	<!-- End page wrapper-->
	<!-- Mainly scripts -->
	<content tag="local_script"> <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Custom and plugin javascript --> 
	<script		src="${ctx}/js/inspinia.js"></script> 
	<script		src="${ctx}/js/plugins/pace/pace.min.js"></script>
	 <!-- 选择组织控件 --> 
	<jsp:include	page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	<!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
	<script		src="${ctx}/js/plugins/jquery.custom.js"></script> 
	<script		id="template_myMortgageApproveLostList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
                       <a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">
							{{item.CASE_CODE}}
						</a>
				 </p>
				 <p>
						<i class="tag_sign">c</i>{{item.CTM_CODE}}
				 </p>
			</td>
			<td class="t-left">
					<p class="big">{{item.PROPERTY_ADDR}}</p>
					<span >
						<i class="salesman-icon"></i>
						{{if item.MANAGER_INFO!=null}}
 							<a class="hint  hint-top2"  data-hint="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}"  data-toggle="tooltip" data-placement="top" >{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_PHONE}}<span class="slash">/</span>{{item.GRP_NAME}}</a>					 
						{{/if}}	
					</span>
			</td>                
             <td>						
					<p>  
                        <i class="sign_normal">审</i>{{item.END_TIME_}}          
                    </p>
			</td> 
			
			<td class="center">
              	 <span class="manager"><a href="#"><em>主办人：</em>{{item.CASEOPERATOR}}</a></span>
                 <span class="manager"><a href="#"><em>经纪人：</em>{{item.AGENT_NAME}}</a></span>
                 <span class="manager"><a href="#"><em>审批人：</em>{{item.real_name}}</a></span>
            </td> 
			<td>
				<p>
                     <i class="sign_blue">{{item.MORT_TYPE==""?"未选择":item.MORT_TYPE}}</i>
                </p>       
			</td>
				
                   
              </tr>
		{{/each}}
	    </script> <script>
						var ctx = "${ctx}";
						var serviceDepId = "${serviceDepId}";
						
						jQuery(document).ready(function() {
							//情况查询条件
							cleanSearchForm();
							// 初始化列表		
							var startDate = $("#dtBegin_0").val();
							var endDate = '';
							if (!$.isBlank($("#dtEnd_0").val())) {
								endDate = $("#dtEnd_0").val() + " 23:59:59";
							}
							var data = {};
							data.startDate = startDate;
							data.endDate = endDate;
							data.queryId = "queryMortgageApproveLost";
							data.rows = 10;
							data.page = 1;

							/* 加载排序查询组件 */
							aist.sortWrapper({
								reloadGrid : loanLostApproveSearchMethod
							});

							reloadGrid(data);
							
							$("span[name='srvCode']").click(function(){								
								var id = $(this).attr("id");								
								$("span[id='"+id+"']").changeSelect();
							});

						});

						// 查询
						$('#searchButton').click(function() {
							loanLostApproveSearchMethod();
						});

						//组织图标选择
						$('#organizeOnclick').click(function() {
							orgSelect({
								displayId : 'oriGrpId',
								displayName : 'radioOrgName',
								startOrgId : serviceDepId,
								expandNodeId : serviceDepId, //添加此属性的作用是展开 组织列表
								orgType : '',
								departmentType : '',
								departmentHeriarchy : '',
								chkStyle : 'radio',
								callBack : radioYuCuiOrgSelectCallBack
							})
						});
						//主办图标选择
						$('#managerOnclick').click(function() {
							chooseCaseOperator(serviceDepId);
						});

						// 查询
						function loanLostApproveSearchMethod(page) {
							if (!page) {
								page = 1;
							}
							var params = getParams();
							params.page = page;
							params.rows = 10;
							params.queryId = "queryMortgageApproveLost";
							reloadGrid(params);
						};

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

							var sortcolumn = $('span.active')
									.attr("sortColumn");
							var sortgz = $('span.active').attr("sord");
							data.sidx = sortcolumn;
							data.sord = sortgz;

							var orgArray = queryOrgs == null ? null : queryOrgs
									.split(",");
							data.argu_idflag = arguUserId;
							//data.argu_queryorgs = orgArray;
							data.argu_queryorgs = serviceDepId;
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
											var myMortgageApproveLostList = template(
													'template_myMortgageApproveLostList',
													data);
											$("#myMortgageApproveLostList")
													.empty();
											$("#myMortgageApproveLostList")
													.html(
															myMortgageApproveLostList);
											// 显示分页
											initpage(data.total, data.pagesize,
													data.page, data.records);
										},
										error : function(e, jqxhr, settings,
												exception) {
											$.unblockUI();
										}
									});
						}
						//分页
						function initpage(totalCount, pageSize, currentPage,
								records) {
							if (totalCount > 1500) {
								totalCount = 1500;
							}
							var currentTotalstrong = $('#currentTotalPage')
									.find('strong');
							if (totalCount < 1 || pageSize < 1
									|| currentPage < 1) {
								$(currentTotalstrong).empty();
								$('#totalP').text(0);
								$("#pageBar").empty();
								return;
							}
							$(currentTotalstrong).empty();
							$(currentTotalstrong).text(
									currentPage + '/' + totalCount);
							$('#totalP').text(records);

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
									loanLostApproveSearchMethod(page);
								}
							});
						}
						//获取参数					
						function getParams() {
							var startDate = $("#dtBegin_0").val();
							var endDate = '';
							if (!$.isBlank($("#dtEnd_0").val())) {
								endDate = $("#dtEnd_0").val() + " 23:59:59";
							}

							var data = {};//getCheckBoxValues2("srvCode");	
							//todo  查询条件需要补充完善
							data.startDate = startDate;
							data.endDate = endDate;
							data.caseCode = $("#caseCode").val().trim();;							
							data.propertyAddr = $("#propertyAddr").val();
							data.caseoperator = $("#caseoperator").val();
							// 获取组织或者人员
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
							var orgArray = queryOrgs == null ? null : queryOrgs
									.split(",");
							data.argu_idflag = arguUserId;							
							var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();//案件组织id

							if ($.isBlank(yuCuiOriGrpId)) {
								data.argu_queryorgs = serviceDepId;
							} else {
								data.argu_queryorgs = yuCuiOriGrpId;
							}
							
							// 贷款类型选择
							var srvCode = getCheckBoxValues("srvCode");								
							//遍历数组
 /* 						$.each(srvCode, function(index, item) { 								
								if (item == 0) {									
									data.srvCode="";	
								}else if (item == 1) {									
									data.srvCode="30016001";
								}else if (item == 2) {								
									data.srvCode="30016002";
								}
							});  */

							data.srvCode=srvCode;
							return data;
						}

						//选业务组织的回调函数
						function radioYuCuiOrgSelectCallBack(array) {
							if (array && array.length > 0) {
								$("#orgName").val(array[0].name);
								$("#yuCuiOriGrpId").val(array[0].id);
							} else {
								$("#orgName").val("");
								$("#yuCuiOriGrpId").val("");
							}
						}
						//日期控件
						$('#datepicker_0').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked',
							language : 'zh-CN'
						});

						//案件excel导出
 						function loanLostCaseExportToExcel() {
							var data = getParams();
							aist.exportExcel({
								ctx : "${ctx}",
								queryId : 'queryMortgageApproveLost',
								colomns : [ 'CASE_CODE', 'PROPERTY_ADDR',
										'LEADING_PROCESS_ID','ORG_NAME','ORG_ORG_NAME',
										'AGENT_ORG_NAME','AGENT_NAME', 
										'real_name','END_TIME_','MORT_TYPE', 'MORT_TOTAL_AMOUNT','COM_AMOUNT','PRF_AMOUNT',
										'FIN_ORG_NAME','FIN_ORG_NAME_YC',
										'loanlost_apply_reason' ],
								data : data
							})
						} 
						
						//选取人员的回调函数
						function loanLostCaseSelectUserBack(array) {
							if (array && array.length > 0) {
								$("#caseoperator").val(array[0].username);
								$("#caseoperator")
										.attr('hVal', array[0].userId);

							} else {
								$("#caseoperator").val("");
								$("#caseoperator").attr('hVal', "");
							}
						}

						//选择组织之后 级联选择主办人信息
						function chooseCaseOperator(id) {
							var serviceDepId = id;
							var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();

							if (yuCuiOriGrpId != "") {
								userSelect({
									startOrgId : yuCuiOriGrpId,
									expandNodeId : yuCuiOriGrpId,
									nameType : 'long|short',
									orgType : '',
									departmentType : '',
									departmentHeriarchy : '',
									chkStyle : 'radio',
									jobCode : 'consultant',
									callBack : loanLostCaseSelectUserBack
								});
								//$("#yuCuiOriGrpId").val("");
							} else {
								userSelect({
									startOrgId : serviceDepId,
									expandNodeId : serviceDepId,
									nameType : 'long|short',
									orgType : '',
									departmentType : '',
									departmentHeriarchy : '',
									chkStyle : 'radio',
									jobCode : 'consultant',
									callBack : loanLostCaseSelectUserBack
								});
							}
						}
						//案件创建时间排序图标变化函数
						function createTimeSort() {
							if ($("#createTimeSorti").attr("class") == "fa fa-sort-desc fa_down") {
								$("#createTimeSorti").attr("class",
										'fa fa-sort-asc fa_up');
							} else if ($("#createTimeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
								$("#createTimeSorti").attr("class",
										'fa fa-sort-asc fa_up');
							} else {
								$("#createTimeSorti").attr("class",
										'fa fa-sort-desc fa_down');
							}
						}

						//案件case_code排序图标变化函数
						function caseCodeSort() {
							if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
								$("#caseCodeSorti").attr("class",
										'fa fa-sort-asc fa_up ');
							} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
								$("#caseCodeSorti").attr("class",
										'fa fa-sort-asc fa_up');
							} else {
								$("#caseCodeSorti").attr("class",
										'fa fa-sort-desc fa_down');
							}
						}
						//页面初始化的时候清空表单
						function cleanSearchForm() {
							//$("select").val("");
							$("input[name='caseCode']").val("");
							$("input[name='orgName']").val("");
							$("input[name='caseoperator']").val("");
							$("input[name='propertyAddr']").val("");
						}

						//点击清除按钮的时候清空表单
						$('#loanLostCleanButton').click(
								function() {
									$("input[name='dtBegin']").datepicker(
											'update', '');
									$("input[name='dtEnd']").datepicker(
											'update', '');
									$("input[name='caseCode']").val("");
									$("input[name='orgName']").val("");
									$("input[name='caseoperator']").val("");
									$("input[name='propertyAddr']").val("");
									//清空产品名称
									$("span[name='srvCode'].selected").each(function(){
										$(this).click();
									});
									//$("select").val("");
									//$("#yuCuiOriGrpId").val("");
								});						
					
						//产品类型
						function getCheckBoxValues(name) {
							var srvCode = [];
							$("span[name='srvCode'].selected").each(function() {
								var val = $(this).attr('value');							
								srvCode.push(val);
							});							
							return srvCode;
						}
						function getCheckBoxValues2(name) {
							
							var srvCode = {};
							$("span[name='srvCode'].selected").each(function() {
								var id = $(this).attr('id');
								var val = $(this).attr('value');							
								srvCode[id]=val;
							}); 
							//alert(JSON.stringify(data));							
							return srvCode;
						}
						
						 jQuery.fn.changeSelect = function(params){							 	
								if(this.hasClass("selected")) {
									this.removeClass("selected");
								} else {
									this.addClass("selected");
								}
						};
					</script> </content>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
</body>
</html>