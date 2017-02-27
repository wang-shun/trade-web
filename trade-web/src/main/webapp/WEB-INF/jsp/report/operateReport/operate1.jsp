<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>贷款签约与过户对比</title>
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link href="${ctx}/static/css/animate.css" rel="stylesheet" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css">
</head>
<body style="background-color: #fff;">
	<!--*********************** HTML_main*********************** -->
	<div>
		<div class="ibox-content" id="base_info">
			<div class="row chartwo">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">贷款签约与过户对比</h3>
						<div class="calendar-watch clearfix">
							<p class="calendar-year">
								<a href="#" id="subtract"><em>&lt;</em></a> <span>2016</span> <a
									href="#" id="add"><em>&gt;</em></a>
							</p>
							<input type="hidden" value="${ctx}" id="ctx">
						</div>
					</div>

					<!-- table -->
					<table
						class="table table_blue  table-striped table-bordered table-hover customerinfo">
						<thead>
							<tr>
								<th>类型</th>
								<th>贷款类型</th>
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
								<td rowspan="3">过户数据（签贷款）</td>
								<td>商贷金额</td>
							</tr>
							<tr>
								<td>公积金金额</td>
							</tr>
							<tr>
								<td>总计</td>
							</tr>
							<tr>
								<td rowspan="3">贷款签约数据</td>
								<td>商贷金额</td>
							</tr>
							<tr>
								<td>公积金金额</td>
							</tr>
							<tr>
								<td>总计</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx}/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1
				
			};
        	data.choiceYear = year;
			var url = ctx+"/js/eachartdata/loanloss.json"
			$.ajax({
				async : true,
				url : url,
				method : "get",
				dataType : "json",
				data : data,
				success : function(data) {
					if(data==null||data==undefined){
	                    window.parent.wxc.alert("数据加载失败！");
						return;			
					}
					data.ctx = ctx;
					debugger;
					var trHtml="";
					$.each(data.rows,function(i,item){
					    if(i>5){return};
						if(i==0)trHtml+="<tr ><td rowspan='3'>过户数据（签贷款</td><td>商贷金额</td>";
						if(i==3)trHtml+="<tr><td rowspan='3'>贷款签约数据</td><td>商贷金额</td>";
						if(i==1||i==4)trHtml+="<tr><td>公积金金额</td>"
						if(i==2||i==5)trHtml+="<tr><td>总计</td>"
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.lossCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
							trHtml+="<td>"+item.successCount+"</td>";
                            trHtml+="</tr>";
						
					})
					$("#tableTemplate").html(trHtml);
					// 显示分页 
		},error : function(e, jqxhr, settings, exception) {
			//$.unblockUI();
		}
			})
		}
	</script>
</body>
</html>
