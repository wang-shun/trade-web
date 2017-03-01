<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>签约数据</title>
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
			<div class="row">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">签约数据</h3>
						<div class="calendar-watch clearfix">
							<p class="calendar-year">
								<a href="#" id="subtract"><em>&lt;</em></a> <span>2016</span> <a
									href="#" id="add"><em>&gt;</em></a>
							</p>
							<input type="hidden" value="${ctx}" id="ctx">
							<p class="calendar-month">
								<span value="1">1月</span> <span value="2">2月</span> <span
									value="3">3月</span> <span value="4">4月</span> <span value="5">5月</span>
								<span value="6">6月</span> <span value="7">7月</span> <span
									value="8">8月</span> <span value="9">9月</span> <span value="10">10月</span>
								<span value="11">11月</span> <span value="12">12月</span>
							</p>
						</div>
					</div>
					<div class="sign-content row clearfix">
						<div id="signChart" class="col-md-6" style="height: 480px;"></div>
						<div class="col-md-6">
							<table
								class="table table_blue  table-striped table-bordered table-hover customerinfo mt100">
								<thead>
									<tr>
										<th>类型</th>
										<th>单数</th>
										<th>合同价</th>
										<th>占比</th>
									</tr>
								</thead>
								<tbody id="tableTemplate">
								</tbody>
							</table>
						</div>
					</div>

					<!-- table -->

				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx}/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src= "${ctx}/static/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 排序插件 -->
	<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>	
	<!-- ECharts.js -->
	<script src="${ctx }/static/js/echarts-all.js"></script>
	<script src="${ctx}/js/eachartdata/elistdata.js"></script>
	<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>
	<script id="template_table" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.groupName}}</td>
              <td>{{item.caseCount}}</td>
              <td>{{item.htAmount}}</td>
              <td>{{item.caseCountRate}}%</td>
             </tr>
		{{/each}}
		{{if rows.length > 0}}
			<tr>
              <td>总计</td>
              <td>{{rows[rows.length-1].totalCaseCount}}</td>
              <td>{{rows[rows.length-1].totalHtAmount}}</td>
              <td>{{rows[rows.length-1].totalCaseCountRate}}%</td>
             </tr>
		{{else}}
			<tr>
              <td>总计</td>
              <td>0</td>
              <td>0</td>
              <td>0%</td>
             </tr>
		{{/if}}
	    </script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		var url = ctx + "/quickGrid/findPage";
		
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
	        var searchDateTime = year + "-" + month;
			var searchBelongMonth = getBelongMonth(searchDateTime);
			
			var data = {
				rows : 8,
				page : 1,
				searchDateTime : searchDateTime,
				queryId : "signStatisticsQuery",
				url : url,
				pagination : false,
				searchBelongMonth : searchBelongMonth
			};
	        
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
					echartData("signChart",data);
					var templateData = template("template_table", data);
					$("#tableTemplate").empty();
					$("#tableTemplate").html(templateData);
				}
        	}) 
		}
	</script>
</body>
</html>
