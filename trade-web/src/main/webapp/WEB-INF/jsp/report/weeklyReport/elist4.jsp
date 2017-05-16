<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>金融产品转化率-贷款类</title>
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
						<h3 class="content-title pull-left">金融产品转化率-贷款类</h3>
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
								<th rowspan="2">所属组别</th>
								<th colspan="5">本周过户案件中E+贷款申请量</th>
								<th colspan="5">本月过户案件累计</th>
								<th colspan="2">本月E+贷款申请量</th>
							</tr>
							<tr>
								<th>过户单数</th>
								<th>房屋价格</th>
								<th>E+申请单数</th>
								<th>E+申请金额</th>
								<th>金额转化率</th>
								<th>过户单数</th>
								<th>房屋价格</th>
								<th>E+申请单数</th>
								<th>E+申请金额</th>
								<th>金额转化率</th>
								<th>申请总量</th>
								<th>申请总金额</th>
							</tr>
						</thead>
						<tbody id="eloanList">
							<!-- 模板数据 -->
						</tbody>
					</table>
				   </div>
				</div>
			</div>
			<div style="height: 34px;line-height: 34px;"><i class="icon iconfont icon40 yellow martop20" style="font-size: 30px;float: left;"></i>本月后台E+贷款申请量:<span id="notHaveOwnerVal"></span></div>
			备注：<br>
			①单数转化率 = 过户案件中E+贷款申请数 / 过户总单数<br>
			数据来源：<br>
			①本周过户审批通过案件<br>
			②本周E+贷款申请量 ： 取本月E+贷款的创建时间
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

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
	<script id="template_eloanList" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.ORG_NAME}}</td>
              <td>{{item.GUOHU_NUM_WEEK}}</td>
              <td>{{(item.HOUSE_PRICE_WEEK/10000).toFixed()}}万元</td>
              <td>{{item.PRO_APP_NUM_WEEK}}</td>
              <td>{{(item.PRO_APP_AMOUNT_WEEK/10000).toFixed()}}万元</td>
              <td>{{item.HOUSE_PRICE_WEEK == 0?0:(item.PRO_APP_AMOUNT_WEEK/item.HOUSE_PRICE_WEEK*100).toFixed()}}%</td>
			  <td>{{item.GUOHU_NUM_MONTH}}</td>
              <td>{{(item.HOUSE_PRICE_MONTH/10000).toFixed()}}万元</td>
              <td>{{item.PRO_APP_NUM_MONTH}}</td>
              <td>{{(item.PRO_APP_AMOUNT_MONTH/10000).toFixed()}}万元</td>
              <td>{{item.HOUSE_PRICE_MONTH == 0?0:(item.PRO_APP_AMOUNT_MONTH/item.HOUSE_PRICE_MONTH*100).toFixed()}}%</td>
			  <td>{{item.ELOAN_PRO_APP_NUM_MONTH}}</td>
			  <td>{{item.ELOAN_PRO_APP_AMOUNT_MONTH.toFixed()}}万元</td>
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
				queryId : "queryWeeklyBaseInfoList4",
				startWeekDay : parseInt(startWeekDay),
				endWeekDay : parseInt(endWeekDay),
				belongEndWeekDay : parseInt(weekParamAlter[1]),
				lastMonthStartDay : parseInt(sectionMap[0]),
				lastMonthEndDay : parseInt(sectionMap[1]),
				pagination : false
			}
			var url = ctx+"/quickGrid/findPage";
			var result = initData(url,data,"template_eloanList","eloanList");
			if(result && result.rows){
				var tb2 = 0,tb3 = 0,tb4 = 0,tb5 = 0,tb6 = 0,tb7 = 0,tb8 = 0,tb9 = 0,tb10 = 0,tb11 = 0,tb12 = 0,tb13 = 0;
				
				for(var i in result.rows){
					var row = result.rows[i];
					tb2 += parseInt(row.GUOHU_NUM_WEEK);
					tb3 += Number(row.HOUSE_PRICE_WEEK);
					tb4 += parseInt(row.PRO_APP_NUM_WEEK);
					tb5 += Number(row.PRO_APP_AMOUNT_WEEK);
					tb7 += parseInt(row.GUOHU_NUM_MONTH);
					tb8 += Number(row.HOUSE_PRICE_MONTH);
					tb9 += parseInt(row.PRO_APP_NUM_MONTH);
					tb10 += Number(row.PRO_APP_AMOUNT_MONTH);
					tb12 += parseInt(row.ELOAN_PRO_APP_NUM_MONTH);
					tb13 += parseInt(row.ELOAN_PRO_APP_AMOUNT_MONTH);
				}
				tb6 = tb3 == 0?0:(tb5/tb3*100).toFixed(); 
				tb11 = tb8 == 0?0:(tb10/tb8*100).toFixed();
				tb13 = tb13 == 0?0:tb13.toFixed();
				var trStr = "<tr>";
				trStr += "<td>总计</td>";
				trStr += "<td>"+tb2+"</td>";
				trStr += "<td>"+(tb3/10000).toFixed()+"万元</td>";
				trStr += "<td>"+tb4+"</td>";
				trStr += "<td>"+(tb5/10000).toFixed()+"万元</td>";
				trStr += "<td>"+tb6+"%</td>";
				trStr += "<td>"+tb7+"</td>";
				trStr += "<td>"+(tb8/10000).toFixed()+"万元</td>";
				trStr += "<td>"+tb9+"</td>";
				trStr += "<td>"+(tb10/10000).toFixed()+"万元</td>";
				trStr += "<td>"+tb11+"%</td>";
				trStr += "<td>"+tb12+"</td>";
                trStr += "<td>"+tb13+"万元</td>";
				$("#eloanList").append(trStr);
			}
			
			//查询后台贷款申请总量
			$.ajax({
				url : ctx+"/eloan/queryAppCount",
				method : "post",
				dataType : "json",
				data : {endWeekDay : parseInt(endWeekDay)},
				success : function(data) {
					if(!data || !data.success){
	                    window.parent.wxc.alert("数据加载失败！");
						return;			
					}
					
					$("#notHaveOwnerVal").text(data.content);
				},
				error : function(e, jqxhr, settings, exception) {
					//$.unblockUI();
				}
			});
		}
	</script>
</body>
</html>
