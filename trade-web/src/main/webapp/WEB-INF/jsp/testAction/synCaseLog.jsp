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
		<div class="row white_bg">
			<div class="bonus-table "></div>
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
	<script id="caseLog" type="text/html">
         {{each rows as item index}}
			<tr>
				<td class="text-center">
				    <p>{{item.id}}</p>
				</td>
				<td class="text-center">
				    <p>
                      {{item.start_time}}
                    </p>
				</td>
                <td class="text-center">
				    <p>{{item.end_time}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.case_count}}</p>
				</td>
                <td class="text-center">
				    <p>{{item.is_manual}}</p>
				</td>
			</tr>
			{{/each}}          
	    </script> <script>
			//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												url : "/rapidQuery/findPage",
												queryId : 'caseLogList',
												templeteId : 'caseLog',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : "",
												columns : [ {
													colName : "ID"
												}, {
													colName : "开始时间"
												}, {
													colName : "结束时间"
												}, {
													colName : "案件数"
												} , {
													colName : "手动"
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