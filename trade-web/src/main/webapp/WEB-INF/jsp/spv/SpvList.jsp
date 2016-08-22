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
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- Data Tables -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="../static/iconfont/iconfont.css"">
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
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">

	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">贷款申请任务</h2>
			<form class="form-inline" id="serachForm">
				<div class="form-row form-rowbot">
					<div class="form-group form-margin form-space-one">
						<label for="" class="lable-one">案件编号</label> <input type="text"
							class="form-control input-one" placeholder="请输入案件编号" name="caseCode">
					</div>
					<div class="form-group form-margin form-space-one">
						<label for="" class="lable-one">网签编号</label> <input type="text"
							class="form-control input-one" placeholder="请输入网签编号" name="signNo">
					</div>
					<div class="form-group form-margin form-space-one">
						<label for="" class="lable-one">协议起草</label> <select
							class="form-control select-one" name="qicao">
							<option>最新协议</option>
							<option>最新协议</option>
							<option>最新协议</option>
							<option>最新协议</option>
							<option>最新协议</option>
						</select>
					</div>
					<div class="form-group form-margin">
						<label for="" class="lable-one">交易状态</label> <select
							class="form-control select-one" name="status">
							<option>已完成</option>
							<option>已完成</option>
							<option>已完成</option>
						</select>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group form-margin toright">
						<label class="font-noraml lable-one">申请时间</label>
						<div class="input-daterange input-group"
							data-date-format="yyyy-mm-dd" id="datepicker_0">
							<input type="text" class="form-control date-width" name="startDate"
								value=""> <span class="input-group-addon">到</span> <input
								type="text" class="form-control date-width" name="endDate" value="">
						</div>
					</div>
					<div class="form-group form-margin">
						<label for="" class="lable-one">物业地址</label> <input type="text"
							class="form-control input-widest" placeholder="" name="prAddress">
					</div>
				</div>
				<div class="form-btn">
					<div class="btn-left btn-left-space">
						<button type="submit" id="btn_search" class="btn btn-success">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="submit" onclick="clearForm()" class="btn btn-success">清空</button>
					</div>
					<div class="btn-right">
						<button type="submit" class="btn btn-success">
							<a href="${ctx}/spv/saveHTML">新建</a>
						</button>
						<button type="btn" class="btn btn-success">删除</button>
						<button type="btn" class="btn btn-success">入账</button>
						<button type="btn" class="btn btn-success">出账</button>
						<button type="btn" class="btn btn-success">中止／结束</button>
					</div>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="table_content">

					<div class="bonus-table "></div>
				</div>
			</div>
		</div>
	</div>

	<!-- main End -->

	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
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
	<script id="queryMortgageApproveLost" type="text/html">
         {{each rows as item index}}

                 {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
                    <td class="text-center">
                          <input type="checkbox" name="item" value="{{}}"/>
                    </td>


						{{if item.DATELAMP < lamp1|| item.DATELAMP==null}}
			 	 <td></td>
			{{else if item.DATELAMP < lamp2}}
                 <td>
                         <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                 		 	<div class="sk-double-bounce1 green_light"></div>
                		 	<div class="sk-double-bounce2 green_light"></div>
                		 </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
                 </td>
			{{else if item.DATELAMP < lamp3}}
				 <td>  <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                			<div class="sk-double-bounce1 orange_light"></div>
                    		<div class="sk-double-bounce2 orange_light"></div>
               		   </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
				  </td>
  			{{else}}
   				 <td>
                      <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                     		<div class="sk-double-bounce1 red_light"></div>
                    		<div class="sk-double-bounce1 red_light"></div>
                 	  </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
				 </td>
			{{/if}}
                                        <td>
                                            <p class="big">
                                                <a href="${ctx}/case/caseDetail?caseId={{item.caseId}}" target="_blank">
								                     {{item.CASE_CODE}}
							                    </a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>{{item.CTM_CODE}}
                                            </p>
                                        </td>
                                        <td>
                                                <p class="big">
                       		                     {{item.PR_ADDR}}
												</p>
						 						<span >
												  <i class="salesman-icon"></i>
 							                      <a class="hint hint-top" data-hint="直管经理: {{item.MANAGER_INFO.realName==null?"":item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}} "  >{{item.AGENT_NAME}}<span class="slash">/</span>{{item.MOBILE}}<span class="slash">/</span>{{item.AGENT_ORG_NAME}}</a>						 
						                      </span>
                                        </td>
                                        <td class="center">
                                            <p class="managerstyle">
                                                监管金额<span>{{item.AMOUNT}}万</span>
                                            </p>
                                            <p class="managerstyle">
                                                入账金额<span>{{item.ru}}万</span>
                                            </p>
                                            <p class="managerstyle">
                                                出账金额<span>{{item.chu}}万</span>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">申</i>
                                                {{item.CREATE_TIME}}
                                            </p>
                                        </td>
                                        <td class="center">
                                            <span class="manager">
                                                <a href="#"><em>申请人：</em>{{item.CREATE_BY}}</a>
                                            </span>
                                            <span class="manager">
                                                <a href="#"><em>经办人：</em>{{item.CREATE_BY}}</a>
                                            </span>
                                        </td>
                                        <td class="text-center">
                                           <button class="btn btn-success">查看</button>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
						//初始化数据
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val()
						};
						//查询数据
						function clearForm(){  
						 $("#serachForm").find().val("")
						}
					 
						//清空数据
						$("#btn_search")
								.click(
										function() {
											/* params.search_status = $("#sel_applyStatus").val() */
											var sel_time = $("#sel_time").val();
											if (sel_time == "applyTime") {
												params.search_applyTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_applyTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											} else if (sel_time == "releaseTime") {
												params.search_releaseTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_releaseTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											} else {
												params.search_signTimeStart = $(
														"input[name='dtBegin']")
														.val("");
												params.search_signTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											}
											var sel_caseInfo = $(
													"#sel_caseInfo").val();
											if (sel_caseInfo == "addr") {
												params.search_propertyAddr = $(
														"#txt_caseInfo").val();
											} else if (sel_caseInfo == "custName") {
												params.search_custName = $(
														"#txt_caseInfo").val();
											}

											initData();
										})

						// 日期控件
						$('#datepicker_0').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked'
						})
						$("#sel_time").change(function() {
							$("input[name='dtBegin']").val("");
							$("input[name='dtEnd']").val("");
						})

						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												queryId : 'ToSpvCaseListQuery',
												templeteId : 'queryMortgageApproveLost',
												gridClass : 'table table_blue table-striped table-bordered table-hover ',
												data : params,
												columns : [
														{
															colName : "<input type='checkbox' name='spv'  id='all'/>	"
														},
														{
															colName : ""
														},
														{
															colName : "<span>案件编码</span><a href='#'><i class='fa fa-sort-desc fa_down'></i></a>"
														}, {
															colName : "产证地址"
														}, {
															colName : "资金管理"
														}, {
															colName : "时间"
														}, {
															colName : "办理人"
														}, {
															colName : "操作"
														} ]

											});
						}

						//初始化
						jQuery(document).ready(function() {
							initData();
						});
					</script> </content>
</body>
</html>