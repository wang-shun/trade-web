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
<link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
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
								<a href="#" id="subtract"><em>&lt;</em></a> <span>2016年11月</span>
								<a href="#" id="add"><em>&gt;</em></a>
							</p>
						</div>
					</div>

					<!-- table -->
					<table
						class="table table_blue  table-striped table-bordered table-hover customerinfo">
						<thead>
							<tr>
								<th>过户数据</th>
								<th>1月</th>
								<th>2月</th>
								<th>3月</th>
								<th>4月</th>
								<th>5月</th>
								<th>6月</th>
								<th>7月</th>
								<th>8月</th>
								<th>9月</th>
								<th>10月</th>
								<th>11月</th>
								<th>12月</th>
							</tr>
						</thead>
						<tbody id="tableTemplate">
							<tr>
								<td>宝山贵宾A组</td>
								<td>12</td>
								<td>4</td>
								<td>33%</td>
								<td>38%</td>
								<td>35102</td>
								<td>6300</td>
								<td>19%</td>
								<td>27%</td>
							</tr>
						</tbody>
					</table>
					<div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条
						</span>&nbsp;
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
	<script
		src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
			var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1

			};
			data.choiceYear = year;
			var url = ctx + "/js/eachartdata/loanloss.json"
			$.ajax({
				async : true,
				url : url,
				method : "get",
				dataType : "json",
				data : data,
				success : function(data) {
					if (data == null || data == undefined) {
						window.parent.wxc.alert("数据加载失败！");
						return;
					}
					var title = [ "无贷款单数", "公积金单数", "商贷单数", "商贷单数占比", "过户房价",
							"商贷金额", "公积金金额", "杠杆率", "有商贷案件房价", "贷款金额占比",
							"商贷收单（商贷）", "流失单数（商贷）", "单数流失率（商贷）", "收单金额（商贷）",
							"流失金额（商贷）", "金额流失率（商贷）" ];
					var trHtml = "";
					$.each(data.rows, function(i, item) {
						trHtml += "<tr><td>" + title[i] + "</td>"
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.lossCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "</tr>";

					})
					$("#tableTemplate").html(trHtml);
				}
			})
		}
	</script>
</body>
</html>
