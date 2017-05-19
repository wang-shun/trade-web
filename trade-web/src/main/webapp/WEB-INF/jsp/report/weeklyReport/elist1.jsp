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
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
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
			<div class="row">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">贷款流失率</h3>
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

				<div class="table-scroll" style="height:500px;overflow: auto">
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
								<th>本月单数流失率</th>
								<th>流失金额</th>
								<th>收单金额</th>
								<th>总金额</th>
								<th>金额流失率</th>
								<th>本月金额流失率</th>
							</tr>
						</thead>
						<tbody id="loanLoseList">
							<!-- 模板数据 -->
						</tbody>
					</table>
				  </div>
				备注：<br>
				①总金额是指商业贷款的金额，不包含公积金金额<br>
				数据来源：<br>
				①一周过户审批通过案件(含有商贷的案件、包含公司办理，客户自办)
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx }/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/js/trunk/report/overwriteAlgorithm.js"></script>
	<script src="${ctx }/js/bootstrap.min.js"></script>
	<script rc="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/static/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 排序插件 -->
	<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>	
	<!-- 个人js -->
	<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>	
	<script id="template_loanLoseList" type="text/html">
        {{each rows as item index}}
		    <tr>
              <td>{{item.ORG_NAME}}</td>
              <td>{{item.LOSE_NUM_WEEK}}</td>
              <td>{{item.REC_NUM_WEEK}}</td>
              <td>{{item.TOTAL_NUM_WEEK}}</td>
              <td>{{item.TOTAL_NUM_WEEK == 0?0:(item.LOSE_NUM_WEEK/item.TOTAL_NUM_WEEK*100).toFixed()}}%</td>
              <td>{{item.TOTAL_NUM_MONTH == 0?0:(item.LOSE_NUM_MONTH/item.TOTAL_NUM_MONTH*100).toFixed()}}%</td>
 			  <td>{{(item.LOSE_AMOUNT_WEEK/10000).toFixed()}}万元</td>
			  <td>{{(item.REC_AMOUNT_WEEK/10000).toFixed()}}万元</td>
              <td>{{(item.TOTAL_AMOUNT_WEEK/10000).toFixed()}}万元</td>
              <td>{{item.TOTAL_AMOUNT_WEEK == 0?0:(item.LOSE_AMOUNT_WEEK/item.TOTAL_AMOUNT_WEEK*100).toFixed()}}%</td>
              <td>{{item.TOTAL_AMOUNT_MONTH == 0?0:(item.LOSE_AMOUNT_MONTH/item.TOTAL_AMOUNT_MONTH*100).toFixed()}}%</td>
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
				queryId : "queryWeeklyBaseInfoList1",
				startWeekDay : parseInt(startWeekDay),
				endWeekDay : parseInt(endWeekDay),
				belongEndWeekDay : parseInt(weekParamAlter[1]),
				lastMonthStartDay : parseInt(sectionMap[0]),
				lastMonthEndDay : parseInt(sectionMap[1]),
				pagination : false	
			}
			var url = ctx+"/quickGrid/findPage";
			var result = initData(url,data,"template_loanLoseList","loanLoseList");
			if(result && result.rows){
				var tb2 = 0,tb3 = 0,tb4 = 0,tb5 = 0,tb6 = 0,tb7 = 0,tb8 = 0,tb9 = 0,tb10 = 0,tb11 = 0;
				var lose_num_month = 0,total_num_month = 0,lose_amount_month = 0,total_amount_month = 0;
				
				for(var i in result.rows){
					var row = result.rows[i];
					tb2 += parseInt(row.LOSE_NUM_WEEK);
					tb3 += parseInt(row.REC_NUM_WEEK);
					tb4 += parseInt(row.TOTAL_NUM_WEEK);
					tb7 += Number(row.LOSE_AMOUNT_WEEK);
					tb8 += Number(row.REC_AMOUNT_WEEK);
					tb9 += Number(row.TOTAL_AMOUNT_WEEK);
					lose_num_month += parseInt(row.LOSE_NUM_MONTH);
					total_num_month += parseInt(row.TOTAL_NUM_MONTH);
					lose_amount_month += Number(row.LOSE_AMOUNT_MONTH);
					total_amount_month += Number(row.TOTAL_AMOUNT_MONTH);
				}
				tb5 = tb4 == 0?0:(tb2/tb4*100).toFixed();
				tb6 = total_num_month == 0?0:(lose_num_month/total_num_month*100).toFixed();
				tb10 = tb9 == 0?0:(tb7/tb9*100).toFixed();
				tb11 = total_amount_month == 0?0:(lose_amount_month/total_amount_month*100).toFixed();
				var trStr = "<tr>";
				trStr += "<td>总计</td>";
				trStr += "<td>"+tb2+"</td>";
				trStr += "<td>"+tb3+"</td>";
				trStr += "<td>"+tb4+"</td>";
				trStr += "<td>"+tb5+"%</td>";
				trStr += "<td>"+tb6+"%</td>";
				trStr += "<td>"+(tb7/10000).toFixed()+"万元</td>";
				trStr += "<td>"+(tb8/10000).toFixed()+"万元</td>";
				trStr += "<td>"+(tb9/10000).toFixed()+"万元</td>";
				trStr += "<td>"+tb10+"%</td>";
				trStr += "<td>"+tb11+"%</td>";
				$("#loanLoseList").append(trStr);
			}
		}
	</script>

</body>
</html>
