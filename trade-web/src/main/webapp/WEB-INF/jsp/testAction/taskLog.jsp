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
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <!-- index_css  -->
    <link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/>
 
    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<%
	response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma","no-cache");
	response.setDateHeader("Expires",0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
	%>
	
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInUp">
	<div class="row">
			<div class="ibox-content border-bottom clearfix space_box">
				<h2 class="title">查询</h2>
				<form method="get" class="form_list">

					<div class="form_content form_nomargin">
						<label class="control-label sign_left">案件编号 </label>  
						<input class="sign_right input_type ml10" id="caseCode"
							placeholder="请输入" value="">
					</div>


					<div class="form_content btn-sign">
						<div class="add_btn">
							<button type="button" class="btn btn-success btn-icon " onclick="initData()">
								<i class="icon iconfont"></i> 查询
							</button>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="ibox"> -->
		</div>
		<div class="row white_bg">
			<div class="bonus-table "></div>
		</div>
		<div class="row white_bg">
			<div class="bonus-table2 "></div>
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
	<script id="hiWorkFlow" type="text/html">
         {{each rows as item index}}
			<tr>
				<td class="text-center">
				    <p>{{item.PKID}}</p>
				</td>
				<td class="text-center">
				    <p>
                      {{item.CASE_CODE}}

                    </p>
                    <p>{{item.BIZ_CODE}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.INST_CODE}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.BUSINESS_KEY}}</p>
                    <p>{{item.PROCESS_DEFINITION_ID}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.PROCESS_OWNER}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.STATUS}}</p>
				</td>
                <td class="text-left">
				    <p><i class="sign_normal">申</i>{{item.CREATE_BY}}{{item.CREATE_TIME}} </p>
                    <p><i class="sign_normal">更</i>{{item.UPDATE_BY}}{{item.UPDATE_TIME}}</p>
				</td>
			</tr>
			{{/each}}          
	    </script> 
	    <script id="taskPeassigntLog" type="text/html">
         {{each rows as item index}}
			<tr>
				<td class="text-center">
				    <p>{{item.PKID}}</p>
				</td>
				<td class="text-center">
				    <p>
                      {{item.CASE_CODE}}
                    </p>
				</td>
                <td class="text-center">
				    <p>{{item.PROC_INST_ID}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.TASK_ID}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.TASK_DEF_KEY}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.ORI_ASSIGNEE}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.NEW_ASSIGNEE}}</p>
				</td>
                <td class="text-left">
				    <p><i class="sign_normal">申</i>{{item.CREATE_BY}}{{item.CREATE_TIME}}</p>
                    <p><i class="sign_normal">更</i>{{item.UPDATE_BY}}{{item.UPDATE_TIME}}</p>
				</td>
			</tr>
			{{/each}}          
	    </script> 
	    
	    <script>
			//加载页面
						function initData() {
				                  var caseCode=$("#caseCode").val();
							$(".bonus-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												url : "/rapidQuery/findPage",
												 rows : 6,
												queryId : 'hiWorkFlow',
												templeteId : 'hiWorkFlow',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : {"caseCode":caseCode	
												},
												columns : [ {
													colName : "PKID"
												}, {
													colName : "案件编号"
												}, {
													colName : "机构编号"
												} , {
													colName : "业务键"
												}, {
													colName : "流程拥有人"
												} , {
													colName : "状态"
												} , {
													colName : "时间"
												} ]

											});
							$(".bonus-table2")
							.aistGrid(
									{
										ctx : "${ctx}",
										url : "/rapidQuery/findPage",
										 rows : 6,
										queryId : 'taskPeassigntLog',
										templeteId : 'taskPeassigntLog',
										gridClass : 'table table_blue table-striped table-bordered table-hover',
										data : {"caseCode":caseCode	
										},
										columns : [ {
											colName : "PKID"
										}, {
											colName : "案件编号"
										}, {
											colName : "机构编号"
										}, {
											colName : "任务编号"
										} , {
											colName : "任务定义"
										}, {
											colName : "前执行人"
										}, {
											colName : "新的执行人"
										} , {
											colName : "时间"
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