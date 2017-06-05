<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />

</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
			 服务归属或待办转移功能
         </h2>
		<form method="get" class="form-horizontal form_box">

			<div class="row clearfix">
                <!--筛选条件-->
			</div>
	   </form>
	</div>
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					<th>交易顾问</th>
					<th>组别</th>
					<th>主办归属</th>
					<th>纯公积金贷款服务</th>
					<th>商贷/组合贷服务</th>
					<th>E+归属</th>
					<th>资管归属</th>
					<th>待办归属</th>
				</tr>
			</thead>
			<tbody id="myTaskList">


			</tbody>
		</table>
	</div>
			
	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
	
</div>

<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

<content tag="local_script">
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/inspinia.js"></script>
	<script src="${ctx}/js/plugins/pace/pace.min.js"></script>

	<!-- 选择组织控件 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<!-- iCheck -->
	<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/js/trunk/task/belongAndTransfer.js?version=1.1.1"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>


<script id="template_belongAndTransfer" type= "text/html">
	{{each rows as item index}}
	<tr class="tr-1">
		<td>
			<p class="big">{{item.userName}}</p>
		</td>
		<td>
			<p>{{item.orgName}}</p>
		</td>
		<td>
			<p><a href="${ctx}/case/handler/detail?userId={{item.userId}}&detailCode=processor" target="_blank">{{item.PROCESSOR_COUNT}}</a></p>
		</td>
		<td>
			<p><a href="##">{{item.COM_MORT_COUNT}}</a></p>
		</td>
		<td>
			<p><a href="##">{{item.PRF_MORT_COUNT}}</a></p>
		</td>


		<td>
			<p><a href="##">{{item.ELOAN_COUNT}}</a></p>
		</td>
		<td>
			<p><a href="##">{{item.SPV_COUNT}}</a></p>
		</td>
		<td>
			<p>
			<p><a href="##">{{item.ASSIGNEE_COUNT}}</a></p>
			</p>
		</td>
	</tr>
	{{/each}}
</script>

</content>
<input type="hidden" value="${orgId}" id="userOrg" />
<input type="hidden" value="${selectJobCode}" id="selectJobCode" />

</body>
</html>