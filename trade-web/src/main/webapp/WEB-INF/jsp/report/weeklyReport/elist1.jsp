<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>贷款流失率</title>
<link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" />
<link rel="stylesheet" href="${ctx}/static/css/style.css" />
<!-- index_css -->
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
<!-- 分页控件 -->
<!-- jQuery UI -->
<link rel="stylesheet"
	href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
<link rel="stylesheet"
	href="${ctx}/static/trans/css/property/popmac.css" />
<link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
</head>
<body style="background-color: #fff;">
	<!--*********************** HTML_main*********************** -->
	<input type="hidden" id="ctx" value="${ctx }" />
	<div>
		<div class="ibox-content" id="base_info">
			<div class="row">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">贷款流失率</h3>
						<div class="calendar-watch clearfix">
							<p class="calendar-year">
								<a href="#" id="subtract"><em>&lt;</em></a> <span>2016年11月</span>
								<a href="#" id="add"><em>&gt;</em></a>
							</p>
							<p class="calendar-month">
								<span>29-05</span><span>06-13</span><span>14-21</span><span>22-29</span>
							</p>
						</div>
					</div>

					<!-- table -->
					<table
						class="table table_blue  table-striped table-bordered table-hover customerinfo">
						<thead>
							<tr>
								<th>主管组别</th>
								<th>流失单数</th>
								<th>收单单数</th>
								<th>总单数</th>
								<th>单数流失率</th>
								<th>11月流失率</th>
								<th>12月流失率</th>
								<th>流失金额</th>
								<th>收单金额</th>
								<th>总金额</th>
								<th>金额流失率</th>
								<th>本月累计</th>
							</tr>
						</thead>
						<tbody id="loanlossList">
							<tr>
								<td>宝山贵宾A组</td>
								<td>1</td>
								<td>12</td>
								<td>13</td>
								<td>8%</td>
								<td>11%</td>
								<td>11%</td>
								<td>57</td>
								<td>1282</td>
								<td>1339</td>
								<td>4%</td>
								<td>10%</td>
							</tr>
						</tbody>
					</table>
						<div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
						<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
				    </div>
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx }/js/jquery-2.1.1.js"></script>
	<script src="${ctx }/js/bootstrap.min.js"></script>
	<script rc="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
		<!-- block UI -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/static/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 排序插件 -->
	<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>	
		<!-- 个人js -->
	<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>
	<script id="template_loanlossList" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.groupName}}</td>
              <td>{{item.lossCount}}</td>
              <td>{{item.successCount}}</td>
              <td>{{item.totalCount}}</td>
              <td>{{item.lossRate}}%</td>
              <td>{{item.oldMonthLossRate}}%</td>
              <td>{{item.monthLossRate}}%</td>
              <td>{{item.lossAmount}}</td>
              <td>{{item.successAmount}}</td>
              <td>{{item.totalAmount}}</td>
              <td>{{item.AmountLossRate}}%</td>
              <td>{{item.MonthAmountLossRate}}%</td>
             </tr>
		{{/each}}
	    </script>

	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
			var week = window.parent.week.split("至");
			var data = {
				startTime : week[0],
				endTime : week[1],
				rows : 8,
				page : 1
				
			};
			var url = ctx+"/js/eachartdata/loanloss.json"
			initData(url,data,"template_loanlossList","loanlossList");
		}
	</script>

</body>
</html>
