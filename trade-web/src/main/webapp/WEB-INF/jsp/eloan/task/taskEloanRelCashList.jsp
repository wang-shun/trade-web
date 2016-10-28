<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->

<!-- stickUp fixed css -->
<link href="${ctx}/static/trans/css/common/hint.css" rel="stylesheet" />
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/trans/css/common/stickDash.css"
	rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css"">


<!-- 分页控件 -->
<link href="${ctx}/static/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<%
	response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>

</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">
	<input type="hidden" id="orgId" value="${orgId }">

	<input type="hidden" id="orgId"
		value="${sessionUser.serviceCompanyId }">

	<!--*********************** HTML_main*********************** -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">资金流水列表</h2>
			<form method="get" class="form_list" id="searchForm">
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 产品名称 </label>
						<aist:dict id="loanSrvCode" name="loanSrvCode"
							clazz="select_control sign_right_one" display="select"
							dictType="yu_serv_cat_code_tree" tag="Eloan" ligerui='none'
							defaultvalue="${eloanCase.loanSrvCode}"></aist:dict>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 状态 </label> <select
							name="status" class="select_control sign_right_one">
							<option value="">请选择</option>
							<option value="0">未确认</option>
							<option selected="selected" value="1">已确认</option>
							<option value="2">已拒绝</option>
						</select>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> E+编号 </label> <input
							class="teamcode input_type " name="eloanCode" placeholder=""
							value="">
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 合作机构 </label> <select
							class="select_control sign_right_one" name="finOrgCode"
							id="finOrgCode" value="">
							<option selected="selected" value="">请选择</option>
						</select>
					</div>

					<div class="form_content" id="selectUser">
						<label class="control-label sign_left_small">人员</label> <input
							type="text" id="excutorId" name="excutorId"
							class="teamcode input_type" style="width: 165px;"
							placeholder="请选择" readonly="readonly" hVal="" value=""
							onclick="userSelect({startOrgId:'${sessionUser.serviceDepId}',expandNodeId:'${sessionUser.serviceDepId}',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
						<input type="hidden" name="excutor" id="excutor">
						<div class="input-group float_icon organize_icon"
							style="cursor: pointer;"
							onclick="userSelect({startOrgId:'${sessionUser.serviceDepId}',expandNodeId:'${sessionUser.serviceDepId}',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})">
							<i class="icon iconfont">&#xe627;</i>
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small">案件组织</label> <input
							id="teamCode" name="teamCode" class="teamcode input_type"
							placeholder="请选择" readonly="readonly" value="" hVal=""
							onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
							startOrgId:'${sessionUser.serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})" />
						<input type="hidden" name="excutorTeam" id="excutorTeam">

						<div class="input-group float_icon organize_icon"
							style="cursor: pointer;"
							onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
							startOrgId:'${sessionUser.serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})">
							<i class="icon iconfont">&#xe61b;</i>
						</div>
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 产证地址 </label> <input
							class="teamcode input_type" style="width: 461px;" name="address"
							placeholder="请输入" value="">
					</div>

				</div>
				<div class="line">
					<div class="form_content mr30">
						<label
							class="control-label sign_left_small select_style mend_select">
							放款时间 </label>
						<div class="input-group sign-right dataleft input-daterange"
							data-date-format="yyyy-mm-dd">
							<input name="startTime" class="form-control data_style"
								type="text" value="" placeholder="起始日期"> <span
								class="input-group-addon">到</span> <input name="endTime"
								class="form-control data_style" type="text" value=""
								placeholder="结束日期">
						</div>
					</div>
					<div>
						<button type="button" class="btn btn-success btn-icon"
							id="SearchButton">
							<i class="icon iconfont"></i> 查询
						</button>
						<button type="button" id="mortgageInfoToExcel"
							class="btn btn-success"
							onclick="javascript:mortgageInfoToExcel()">导出列表</button>
						<button type="button" class="btn  btn-icon btn-toggle"
							id="TypeBtn">
							<i class="iconfont icon">&#xe63d;</i> 机构放款金额分析
						</button>
						<button type="button" class="btn  btn-icon btn-toggle "
							id="TypeBtn2">
							<i class="iconfont icon">&#xe63f;</i> 放款金额时间分析
						</button>
						<input type="reset" class="btn btn-grey" id="CleanButton"
							value="清空">
					</div>
					<!--图表-->
					<div class="row charone"
						style="margin-top: 40px; padding-top: 10px; border-top: 1px solid #f4f4f4; display:none">
						<div class="col-md-6">
							<div id="TypeOne" style="width: 100%; height: 400px;"></div>
						</div>
						<div class="col-md-6">
							<div id="TypeTwo" style="width: 100%; height: 400px;"></div>
						</div>
					</div>
					<div class="row chartwo"
						style="margin-top: 40px; padding-top: 10px; border-top: 1px solid #f4f4f4;display:none">
						<div class="col-md-12">
							<div id="Cont" style="width: 100%; height: 300px;"></div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="bonus-table "></div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->
	<!-- main End -->

	<content tag="local_script"> <!-- Peity --> <!-- 组织控件 --> <%@include
		file="/WEB-INF/jsp/tbsp/common/userorg.jsp"%>
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  --> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <!-- 模板 -->
	<!-- ECharts.js --> <script src="${ctx}/static/js/echarts.min.js"></script>
	<script src="${ctx}/static/trans/js/eloan/eloan.js"></script> <!-- index_js -->
	<script id="queryEloanCash" type="text/html">
         {{each rows as item index}}
			    <tr class="tr-1">
                                        <td>
                                            <p class="big">
                                                <a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.pkId}}">
                                                    {{item.ELOAN_CODE}}
                                                </a>
                                            </p>
                                            <p>
                                                {{item.PROPERTY_ADDR}}
                                            </p>
                                        </td>
                                        <td>
                                           <p>
                                                <i class="sign_blue">
                                                 {{item.LOAN_SRV_CODE}}
                                                </i>
                                            </p>
                                           <p class="big">
                                         {{item.FIN_ORG_CODE}}
                                           </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                          {{item.CUST_NAME}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign big">
                                                                                                                                       金额 ：{{item.RELEASE_AMOUNT}}万
                                            </p>
                                            <p class="smll_sign">
                                                                                                                                       时间：{{item.RELEASE_TIME}}
                                            </p>
                                        </td>


                                        <td>
                                            <p class="smll_sign bigt">
                                               {{item.EXCUTOR_ID}}
                                            </p>
                                            <p class="smll_sign big">
                                               {{item.EXCUTOR_TEAM}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                         {{if item.CONFIRM_STATUS==0}}待确认{{/if}}
										 {{if item.CONFIRM_STATUS==1}}已确认{{/if}}
									     {{if item.CONFIRM_STATUS==2}}已拒绝{{/if}}
                                           </p>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
						$(document).ready(function() {
							//图标toggle切换显示隐藏JS
							$(".charone,.chartwo").hide();
							$("#TypeBtn").click(function() {
								$(".charone").toggle();
								$(".chartwo").hide();
								$("#TypeBtn").addClass('btn-bg');
								$("#TypeBtn2 ").removeClass('btn-bg');
								if ($(".charone").is(":hidden")) {
									$("#TypeBtn").removeClass('btn-bg');
								}else{
									reloadStatus2();
								}
							});

							$("#TypeBtn2").click(function() {
								
								$(".chartwo").toggle();
								$(".charone").hide();
								$("#TypeBtn2 ").addClass('btn-bg');
								$("#TypeBtn ").removeClass('btn-bg');
								if ($(".chartwo").is(":hidden")) {
									$("#TypeBtn2").removeClass('btn-bg');
								}else{
									reloadStatus()
								}
								
							});

							$('.input-daterange').datepicker({
								keyboardNavigation : false,
								forceParse : false,
								autoclose : true
							});
							getBankList('');
						});

						//初始化数据
						var ctx = $("#ctx").val();
						serviceJobCode = $("#serviceJobCode").val();
						if (serviceJobCode == 'consultant') {

							$("#selectUser").attr("disabled", true);
						}
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceOrgId : $("#orgId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val(),
							eloanCode : '',
							loanSrvCode : "",
							status : 1,
							startTime : "",
							endTime : "",
							teamCode : "",
							excutorId : "",
							address : "",
							finOrgCode : "",

						};

						/*获取银行列表*/
						function getBankList(pcode) {
							var fiCode = $("#finOrgCode").attr("value");
							var friend = $("#finOrgCode");

							$
									.ajax({
										url : ctx + "/manage/queryFin",
										method : "post",
										dataType : "json",
										data : {
											"pcode" : pcode
										},
										success : function(data) {
											if (data.bankList != null) {
												for (var i = 0; i < data.bankList.length; i++) {
													if (fiCode == data.bankList[i].finOrgCode) {
														friend
																.append("<option value='"+data.bankList[i].finOrgCode+"'>"
																		+ data.bankList[i].finOrgName
																		+ "</option>");
													} else {
														friend
																.append("<option value='"+data.bankList[i].finOrgCode+"'>"
																		+ data.bankList[i].finOrgName
																		+ "</option>");
													}
												}
												friend
														.find(
																"option[value='${loanAgent.finOrgCode }']")
														.attr("selected", true);
											}
										}
									});
						}

						//选业务组织的回调函数
						function radioYuCuiOrgSelectCallBack(array) {
							if (array && array.length > 0) {
								$("#teamCode").val(array[0].name);
								$("#excutorTeam").val(array[0].id);

							} else {
								$("#teamCode").val("");
								$("#excutorTeam").val("");
							}
						}

						function selectUserBack(array) {
							if (array && array.length > 0) {
								$("#excutorId").val(array[0].username);
								$("#excutor").val(array[0].userId);

							} else {
								$("#excutorId").val("");
								$("#excutor").val();
							}
						}

						//查询数据
						$("#SearchButton")
								.click(
										function() {
											var jsonData = $("#eloanApplyForm")
													.serializeArray();
											params.eloanCode = $(
													"input[name='eloanCode']")
													.val();
											params.loanSrvCode = $(
													"select[name='loanSrvCode']")
													.val();
											params.status = $(
													"select[name='status']")
													.val();
											params.startTime = $(
													"input[name='startTime']")
													.val();
											params.endTime = $(
													"input[name='endTime']")
													.val();
											params.teamCode = $(
													"input[name='excutorTeam']")
													.val();
											params.excutorId = $(
													"input[name='excutor']")
													.val();
											params.address = $(
													"input[name='address']")
													.val();
											params.finOrgCode = $(
													"select[name='finOrgCode']")
													.val();
											initData();
											if (!$(".chartwo").is(":hidden")) {
												reloadStatus();
											}
											if (!$(".charone").is(":hidden")) {
												reloadStatus2();
											}
											
											
										})
						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : ctx,
												url : "/rapidQuery/findPage",
												queryId : 'queryEloanCashList',
												templeteId : 'queryEloanCash',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : params,
												columns : [ {
													colName : "E+编号"
												}, {
													colName : "合作机构"
												}, {
													colName : "借款人"
												}, {
													colName : "放款"
												}, {
													colName : "贷款专员"
												}, {
													colName : "状态"
												} ]

											});
						}

						//初始化
						jQuery(document).ready(function() {
							initData();
						});
						//
						function reloadStatus() {
							params.queryId = "queryLoanSpv";
							params.pagination = false;
							params.startDate = new Date();
							var startMonth = new Date().getMonth() + 1;
							$.ajax({
								async : true,//异步请求
								url : ctx + "/rapidQuery/findPage",
								method : "post",
								dataType : "json",
								data : params,
								success : function(data) {
									var all = data.rows;
									var kas = [];
									var dais = [];
									var xAxis = [];
									$.each(all, function(i, item) {
										if (item.mm == 0) {
											xAxis.push(startMonth + "月以前");
										} else {
											xAxis.push(item.mm + "月");
										}
										var ka={
												num:item.ka,
												value:item.kaAmount
											};
											var dai={
												num:item.dai,
												value:item.daiAmount
											};
										kas.push(ka);
										dais.push(dai);
									});
									StatusEchart1(kas,dais,
											xAxis);
								},
								error : function() {
									$("#Cont").addClass("nullData");
								}
							});
						}
						function reloadStatus2() {
							params.queryId = "queryLoanSpv2";
							params.pagination = false;
							$.ajax({
								async : true,//异步请求
								url : ctx + "/rapidQuery/findPage",
								method : "post",
								dataType : "json",
								data : params,
								success : function(data) {
									var all = data.rows;
									var  numbers=[];
									var amounts=[];
									var finOrgNames=[];
									$.each(all, function(i, item) {
										var number={
											name:item.finOrgName,
											value:item.num
										};
										var amount={
											name:item.finOrgName,
											value:item.RELEASE_AMOUNT
										};
										finOrgNames.push(item.finOrgName);
										numbers.push(number);
										amounts.push(amount);
										
									});
									StatusEchart2(finOrgNames,numbers,amounts);
								},
								error : function() {
									/* $("#Cont").addClass("nullData"); */
								}
							});
						}
					</script> </content>
</body>
</html>