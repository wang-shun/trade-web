<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>金融产品转化率-卡类</title>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/static/font-awesome/css/font-awesome.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" />
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />" />
<!-- index_css -->
<link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
<!-- 分页控件 -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" />
<link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />">
<style type="text/css">
th, td {
	text-align: center;
}
</style>
</head>
<body style="background-color: #fff;">
	<!--*********************** HTML_main*********************** -->
	<input type="hidden" id="ctx" value="${ctx}" />
	<div>
		<div class="ibox-content" id="base_info">
			<div class="row chartwo">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">金融产品转化率-卡类</h3>
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
								<th colspan="3">本周过户案件中E+卡申请量</th>
								<th colspan="5">本月过户案件累计</th>
								<th colspan="2">本月E+卡申请量</th>
							</tr>
							<tr>
								<th>过户单数</th>
								<th>卡申请单数</th>
								<th>单数转化率</th>
								<th>过户单数</th>
								<th>卡申请单数</th>
								<th>单数转化率</th>
								<th>刷卡单数</th>
								<th>刷卡率</th>
								<th>申请总量</th>
								<th>申请总金额</th>
							</tr>
						</thead>
						<tbody id="LoankaList">
							<!-- 模板数据 -->
						</tbody>
					</table>
				   </div>
				</div>
			</div>
			<div style="height: 34px;line-height: 34px;"><i class="icon iconfont icon40 yellow martop20" style="font-size: 30px;float: left;"></i>本月后台E+卡申请量:<span id="notHaveOwnerVal"></span></div>
			数据来源：<br>
			①本周过户审批通过案件<br>
			②本月E+卡申请量 ： 取本月E+卡的创建时间<br>
			③单数转化率 = 过户案件中E+卡申请数 / 过户总单数
			④刷卡率 = 刷卡单数  / 过户总单数
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script src="<c:url value='/js/trunk/report/overwriteAlgorithm.js' />"></script>
	<script src="<c:url value='/js/bootstrap.min.js' />"></script>
	<script
		src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<!-- 分页控件  -->
	<script
		src="<c:url value='/static/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/static/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 排序插件 -->
	<script src="<c:url value='/static/js/plugins/jquery.custom.js' />"></script>
	<!-- 个人js -->
	<script src="<c:url value='/js/trunk/report/getTemplateData.js' />"></script>
	<script id="template_LoankaList" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.ORG_NAME}}</td>
              <td>{{item.GUOHU_NUM_WEEK}}</td>
              <td>{{item.KA_APP_NUM_WEEK}}</td>
			  <td>{{item.GUOHU_NUM_WEEK == 0?0:(item.KA_APP_NUM_WEEK/item.GUOHU_NUM_WEEK*100).toFixed()}}%</td>
              <td>{{item.GUOHU_NUM_MONTH}}</td>
              <td>{{item.KA_APP_NUM_MONTH}}</td>
		      <td>{{item.GUOHU_NUM_MONTH == 0?0:(item.KA_APP_NUM_MONTH/item.GUOHU_NUM_MONTH*100).toFixed()}}%</td>
			  <td>{{item.KA_NUM_MONTH}}</td>
              <td>{{item.KA_APP_NUM_MONTH == 0?0:(item.KA_NUM_MONTH/item.KA_APP_NUM_MONTH*100).toFixed()}}%</td>
              <td>{{item.ELOAN_KA_APP_NUM_MONTH}}</td>
			  <td>{{item.ELOAN_KA_APP_AMOUNT_MONTH.toFixed()}}万元</td>
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
				queryId : "queryWeeklyBaseInfoList3",
				startWeekDay : parseInt(startWeekDay),
				endWeekDay : parseInt(endWeekDay),
				belongEndWeekDay : parseInt(weekParamAlter[1]),
				lastMonthStartDay : parseInt(sectionMap[0]),
				lastMonthEndDay : parseInt(sectionMap[1]),
				pagination : false
			}
			var url = ctx+"/quickGrid/findPage";
			var result = initData(url,data,"template_LoankaList","LoankaList");
			if(result && result.rows){
			var tb2 = 0,tb3 = 0,tb4 = 0,tb5 = 0,tb6 = 0,tb7 = 0,tb8 = 0,tb9 = 0,tb10 = 0,tb11 = 0;
			
			for(var i in result.rows){
				var row = result.rows[i];
				tb2 += parseInt(row.GUOHU_NUM_WEEK);
				tb3 += parseInt(row.KA_APP_NUM_WEEK);
				tb5 += parseInt(row.GUOHU_NUM_MONTH);
				tb6 += parseInt(row.KA_APP_NUM_MONTH);
				tb8 += parseInt(row.KA_NUM_MONTH);
				tb10 += parseInt(row.ELOAN_KA_APP_NUM_MONTH);
				tb11 += parseInt(row.ELOAN_KA_APP_AMOUNT_MONTH);
			}
			tb4 = tb2 == 0?0:(tb3/tb2*100).toFixed();
			tb7 = tb5 == 0?0:(tb6/tb5*100).toFixed();
			tb9 = tb5 == 0?0:(tb8/tb5*100).toFixed();
			tb11 = tb11 == 0?0:tb11.toFixed();
			var trStr = "<tr>";
			trStr += "<td>总计</td>";
			trStr += "<td>"+tb2+"</td>";
			trStr += "<td>"+tb3+"</td>";
			trStr += "<td>"+tb4+"%</td>";
			trStr += "<td>"+tb5+"</td>";
			trStr += "<td>"+tb6+"</td>";
			trStr += "<td>"+tb7+"%</td>";
			trStr += "<td>"+tb8+"</td>";
			trStr += "<td>"+tb9+"%</td>";
			trStr += "<td>"+tb10+"</td>";
			trStr += "<td>"+tb11+"万元</td>";
			$("#LoankaList").append(trStr);
			}
			
			//查询后台卡申请总量
			$.ajax({
				url : ctx+"/eloan/queryKaCount",
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
