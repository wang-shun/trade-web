<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>评估转化率</title>
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
<style type="text/css">
th,td {
 text-align: center;
} 
</style>
</head>
<body style="background-color: #fff;">
	<!--*********************** HTML_main*********************** -->
	<input type="hidden" id="ctx" value="${ctx }" />
	<div>
		<div class="ibox-content" id="base_info">
			<div class="row chartwo">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">评估转化率</h3>
						<div class="calendar-watch clearfix">
							<p class="calendar-year">
								<a href="#" id="subtract"><em>&lt;</em></a> 
								<span><!-- 当前年月 --></span>
								<a href="#" id="add"><em>&gt;</em></a>
							</p>
							<p class="calendar-month">
								<!-- 每周的span -->
							</p>
						</div>
					</div>

					<!-- table -->
					<table
						class="table table_blue  table-striped table-bordered table-hover customerinfo">
						<thead>
							<tr>
								<th>主管组别</th>
								<th>收单案件总量</th>
								<th>有评估费单数</th>
								<th>评估转化率</th>
								<th>本月累计</th>
								<th>评估费应收（元）</th>
								<th>评估费实收（元）</th>
								<th>收益率</th>
								<th>本月累计</th>
							</tr>
						</thead>
						<tbody id="assessmentList">
							<!-- 模板数据 -->
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
	<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
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
	<script id="template_assessmentList" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.ORG_NAME}}</td>
              <td>{{item.REC_NUM_WEEK}}</td>
              <td>{{item.EVA_NUM_WEEK}}</td>
              <td>{{item.REC_NUM_WEEK == 0?0:(item.EVA_NUM_WEEK/item.REC_NUM_WEEK*100).toFixed()}}%</td>
              <td>{{item.REC_NUM_MONTH == 0?0:(item.EVA_NUM_MONTH/item.REC_NUM_MONTH*100).toFixed()}}%</td>
              <td>{{item.EVA_AMOUNT_WEEK}}</td>
              <td>{{item.EVA_ACT_AMOUNT_WEEK}}</td>
			  <td>{{item.EVA_AMOUNT_WEEK == 0?0:(item.EVA_ACT_AMOUNT_WEEK/item.EVA_AMOUNT_WEEK*100).toFixed()}}%</td>
              <td>{{item.EVA_AMOUNT_MONTH == 0?0:(item.EVA_ACT_AMOUNT_MONTH/item.EVA_AMOUNT_MONTH*100).toFixed()}}%</td>
             </tr>
		{{/each}}
	    </script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
			var weekParamArr = window.parent.weekParam;
			var data = {
				queryId : "queryList2",
				startWeekDay : weekParamArr[0],
				endWeekDay : weekParamArr[1],
				rows : 10,
				page : 1
				
			}
			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_assessmentList","assessmentList");
		}
	</script>
</body>
</html>
