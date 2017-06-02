<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>评估转化率</title>
<link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet"
	href="${ctx}/static/font-awesome/css/font-awesome.css" />
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
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
<link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
<style type="text/css">
th, td {
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
								<a href="#" id="subtract"><em>&lt;</em></a> <span>
									<!-- 当前年月 -->
								</span> <a href="#" id="add"><em>&gt;</em></a>
							</p>
							<p class="calendar-month">
								<!-- 每周的span -->
							</p>
						</div>
					</div>

				<div class="table-scroll" style="height:500px;overflow: auto">
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
								<th>评估费应收</th>
								<th>评估费实收</th>
								<th>收益率</th>
								<th>本月累计</th>
							</tr>
						</thead>
						<tbody id="assessmentList">
							<!-- 模板数据 -->
						</tbody>
					</table>
				  </div>
				备注：<br>
				①收单案件是指公司办理的商贷案件，不含客户自办案件<br>
				②有评估费单数是指收单案件中收到评估费的单数<br>
				③评估转化率 = 有评估费单数 / 收单案件总数<br>
				④收益率 = 评估费实收 / 收单案件合同价 × 1‰
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx }/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/js/trunk/report/overwriteAlgorithm.js"></script>
	<script src="${ctx }/js/bootstrap.min.js"></script>
	<script
		src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- 分页控件  -->
	<script
		src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/static/js/template.js" type="text/javascript"></script>
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
              <td>{{(item.EVA_AMOUNT_WEEK).toFixed()}}元</td>
              <td>{{(item.EVA_ACT_AMOUNT_WEEK).toFixed()}}元</td>
			  <td>{{item.EVA_AMOUNT_WEEK == 0?0:(item.EVA_ACT_AMOUNT_WEEK/item.EVA_AMOUNT_WEEK*100).toFixed()}}%</td>
              <td>{{item.EVA_AMOUNT_MONTH == 0?0:(item.EVA_ACT_AMOUNT_MONTH/item.EVA_AMOUNT_MONTH*100).toFixed()}}%</td>
             </tr>
		{{/each}}
	    </script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid(page) {
			
			var weekParamOrigin = window.parent.weekParam;
			var startWeekDay = weekParamOrigin[0];
			var endWeekDay = weekParamOrigin[1];
			var weekParamAlter = parent.getMinWeek(weekParamOrigin);
			var sectionMap = parent.getFirstAndLastDay(startWeekDay.substr(0,4),startWeekDay.substr(4,2));
			
			var data = {
				queryId : "queryWeeklyBaseInfoList2",
				startWeekDay : parseInt(startWeekDay),
				endWeekDay : parseInt(endWeekDay),
				belongEndWeekDay : parseInt(weekParamAlter[1]),
				lastMonthStartDay : parseInt(sectionMap[0]),
				lastMonthEndDay : parseInt(sectionMap[1]),
				pagination : false
			}
			var url = ctx + "/quickGrid/findPage";
			var result = initData(url, data, "template_assessmentList", "assessmentList");
			if(result && result.rows){
				var tb2 = 0,tb3 = 0,tb4 = 0,tb5 = 0,tb6 = 0,tb7 = 0,tb8 = 0,tb9 = 0;
				var eva_num_month = 0,rec_num_month = 0,eva_act_amount_month = 0,eva_amount_month = 0;
				
				for(var i in result.rows){
					var row = result.rows[i];
					tb2 += parseInt(row.REC_NUM_WEEK);
					tb3 += parseInt(row.EVA_NUM_WEEK);
					tb6 += Number(row.EVA_AMOUNT_WEEK);
					tb7 += Number(row.EVA_ACT_AMOUNT_WEEK);
					eva_num_month += parseInt(row.EVA_NUM_MONTH);
					rec_num_month += parseInt(row.REC_NUM_MONTH);
					eva_act_amount_month += Number(row.EVA_ACT_AMOUNT_MONTH);
					eva_amount_month += Number(row.EVA_AMOUNT_MONTH);
				}
				tb4 = tb2 == 0?0:(tb3/tb2*100).toFixed();
				tb5 = rec_num_month == 0?0:(eva_num_month/rec_num_month*100).toFixed();
				tb8 = tb6 == 0?0:(tb7/tb6*100).toFixed();
				tb9 = eva_amount_month == 0?0:(eva_act_amount_month/eva_amount_month*100).toFixed();
				var trStr = "<tr>";
				trStr += "<td>总计</td>";
				trStr += "<td>"+tb2+"</td>";
				trStr += "<td>"+tb3+"</td>";
				trStr += "<td>"+tb4+"%</td>";
				trStr += "<td>"+tb5+"%</td>";
				trStr += "<td>"+tb6+"元</td>";
				trStr += "<td>"+tb7+"元</td>";
				trStr += "<td>"+tb8+"%</td>";
				trStr += "<td>"+tb9+"%</td>";
				$("#assessmentList").append(trStr);
			}
		}
	</script>
</body>
</html>
