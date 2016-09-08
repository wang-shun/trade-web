<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<h2 class="title">监管合约列表</h2>
			<form class="form-inline" id="serachForm">
				<div class="form-row form-rowbot">
					<div class="form-group form-margin form-space-one">
						<label for="" class="lable-one">合约编号</label> <input type="text"
							class="form-control input-one" placeholder="请输入合约编号" name="spvCode">
					</div>
					<div class="form-group form-margin form-space-one ">
						<label class="lable-one"> 时间搜索项 </label> <select
							id="sel_time" class="form-control select-one" style="width: 113px;">
							<option value="applyTime">申请时间</option>
							<option value="signTime">面签时间</option>
							<option value="close">放款时间</option>
						</select>
						<div id="datepicker_0"
							class="input-medium date-picker input-daterange sign_right_speciale"
							data-date-format="yyyy-mm-dd">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control input-one" type="text" value=""
								placeholder="起始日期"> <span class="input-group-addon" style="float:none;">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control input-one"
								type="text" value="" placeholder="结束日期">
						</div>

					</div>
				</div>
				<div class="form-row">
					<div class="form-group form-margin form-space-one">
						<label for="" class="lable-one">协议状态</label> <select
							class="form-control input-one" name="status"  id="status">
							<option value="">请选择</option>
							<option value="0">起草</option>
							<option value="1">申请</option>
							<option value="2">签约</option>
							<option value="3d">完成</option>
						</select>
					</div>
					<div class="form-group form-margin">
						<label for="" class="lable-one">物业地址</label> <input type="text"
							class="form-control input-widest" placeholder="" name="prAddress">
					</div>
				</div>
				<div class="form-btn">
					<div class="btn-left btn-left-space">
						<button type="button" id="btn_search" class="btn btn-success">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="button" onclick="clearForm()" class="btn btn-success">清空</button>
					</div>
					<div class="btn-right">
					<c:if test="${sessionUser.serviceJobCode=='JYFKZY'}">
						<button class="btn btn-success">
						<a href="${ctx}/spv/saveHTML">新建</a></button></c:if>
						<button type="btn" class="btn btn-default">删除</button>
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
                                        <!--     <p>
                                                <i class="tag_sign">c</i>{{item.CTM_CODE}}
                                            </p> -->
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

                <tr>
                                        <td class="text-center">
                                            <input type="checkbox" name="item" />
                                        </td>
                                       
                                        <td>
                                            <p class="big">
                                                <a href="${ctx}/spv/spvDetail?pkid={{item.PKID}}" target="_blank">
								                     {{item.SPV_CODE}}
							                    </a>
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
                                                监管金额<span>{{item.AMOUNT>0?item.AMOUNT/10000:0}}万</span>
                                            </p>
                                            <p class="managerstyle">
                                                入账金额<span>0万</span>
                                            </p>
                                            <p class="managerstyle">
                                                出账金额<span>0万</span>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                              	  {{if item.STATUS==0}}
                                                   <i class="sign_grey">草稿</i>
												  {{/if}}
                                            </p>
                    <p class="smll_sign">                          
					 {{if item.STATUS!=0}}
					 {{if item.applyTime==undefined}}
						<i class="sign_grey">申</i>
                      {{else}}
						<i class="sign_normal">申</i>
                      {{/if}}
					       {{item.applyTime}}
					</p>
					<p class="smll_sign">
					  {{if item.signTime==undefined}}
						<i class="sign_grey">签</i>
                      {{else}}
						<i class="sign_normal">签</i>
                      {{/if}}
					       {{item.signTime}}
					</p>
					<p class="smll_sign">
					 {{if item.closeTime==undefined}}
						<i class="sign_grey">完</i>
                      {{else}}
						<i class="sign_normal">完</i>
                      {{/if}}
					       {{item.releaseTime}}
					</p>
                    {{/if}}
                                        </td>
                                        <td class="center">
                                            <span class="manager">
                                                <a href="#"><em>申请人：{{item.APPLY_USER}}</em></a>
                                            </span>
                                            <span class="manager">
                                                <a href="#"><em>经办人：{{item.CREATE_BY}}</em></a>
                                            </span>
                                        </td>
                                        <td class="text-center">
										  {{if item.STATUS==0}}
                                        <a class="btn btn-success btn-one"style="font-size:10px; padding:2px 10px;"
                                           href="${ctx}/spv/saveHTML?pkid={{item.PKID}}">修改</a>
                                         {{/if}}
                                        </td>
                                        
                                    </tr>
			{{/each}}          
	    </script> <script>
						//初始化数据
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							servicDepId : $("#serviceDepId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val()
						};
						//查询数据
						function clearForm(){  
						 $("#serachForm").find("input").val("")
						 $("#status option:first").prop("selected", 'selected');
						/*  $("#qicao option:first").prop("selected", 'selected'); */
						}
						//选中全部
						function changeAll(){
							var falg=$("#all").prop("checked")
							  var checkboxs = document.getElementsByName("item");  
							  for ( var i = 0; i < checkboxs.length; i+=1) {  
							    checkboxs[i].checked = falg;  
							  }  
						}
					 
						//清空数据
						$("#btn_search")
								.click(
										function() {
											params.search_spvCode=$(
											"input[name='spvCode']")
											.val();
											/* params.search_signNo=$(
											"input[name='signNo']")
											.val() */;
											params.search_startDate=$(
											"input[name='startDate']")
											.val();
											params.search_endDate=$(
											"input[name='endDate']")
											.val();
											if(params.search_endDate!=null && params.search_endDate!=""){
												params.search_endDate+=" 23:59:59";
											}
											params.search_prAddress=$(
											"input[name='prAddress']")
											.val();
											params.search_status=$(
											"select[name='status']")
											.val();
											params.search_applyTimeStart=null;		
											params.search_applyTimeEnd=null;
											params.search_signTimeStart=null;
											params.search_signTimeEnd=null;
											params.search_closeTimeStart=null;
											params.search_closeTimeEnd=null;
											var sel_time = $("#sel_time").val();
											if (sel_time == "applyTime") {
												params.search_applyTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_applyTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											} 
											 else if (sel_time == "signTime") {
												    params.search_signTimeStart = $(
															"input[name='dtBegin']")
															.val();
													params.search_signTimeEnd = $(
															"input[name='dtEnd']")
															.val();
										    }
											else if (sel_time == "closeTime") {
												params.search_closeTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_closeTimeEnd = $(
														"input[name='dtEnd']")
														.val();
												params.search_closeTimeEnd+=" 23:59:59";
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
															colName : "<input type='checkbox' name='spv' onChange='changeAll()' id='all'/>	"
														},
										/* 				{
															colName : ""
														}, */
														{
															colName : "<span>合约编码</span><a href='#'><i class='fa fa-sort-desc fa_down'></i></a>"
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