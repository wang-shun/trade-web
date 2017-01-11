<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->

<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css"/>
<link rel="stylesheet" href="${ctx}/static/trans/css/common/echartCommon.css"/>
<style>

</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<!--*********************** HTML_main*********************** -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content" id="base_info">
			<div class="row chartwo"
				style="margin-top: 40px; padding-top: 10px; border-top: 1px solid #f4f4f4;">
				<div class="col-md-12" style="width: 1020px">
					<div id="Cont1"
						style="width: 100%; height: 300px; margin-left: 20px;"></div>
					<div id="Cont"
						style="width: 100%; height: 500px; margin-left: 20px;"></div>
					<table class="echarsTable" style="margin-top: -60px;">
						
						
					</table>
				</div>
			</div>
		</div>
	</div>

	<!--*********************** HTML_main*********************** -->
	<content tag="local_script"> <!-- Peity --> <!-- 组织控件 --> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <!-- ECharts.js -->
	<script src="${ctx}/static/js/echarts.min.js"></script> 
	<script src="${ctx}/static/trans/js/common/echartCommon.js"></script>
	<script>
		$(function() {
			// 基于准备好的dom，初始化echarts实例
			var myChart1 = echarts.init(document.getElementById('Cont1'));
			var myChart2 = echarts.init(document.getElementById('Cont'));
			// 指定图表的配置项和数据

			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/type1/GuoHuCount",
				method : "GET",
				dataType : "json",
				success : function(data) {
					if(data==null||data==undefined){
						return;			
					}
					var items = [];
					$.each(data,function(i,item){
						items.push(item);
					});
					var data = [ "无贷款", "纯公积金", "商业贷款" ];
					var option2 = returnPie(data, items, myChart1, null);
				},
				error:function(){}
			})


			// 使用刚指定的配置项和数据显示图表。
			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/type1/GuoHuCountBar",
				method : "GET",
				dataType : "json",
				success : function(data) {
					if(data==null||data==undefined){
						return;			
					}
					var xAxisData=[];
					var oldMonth=[];
					var newMonth=[];
					$.each(data.rows,function(i,item){
						xAxisData.push(item.name);
						newMonth.push(item.currentMonth);
						oldMonth.push(item.oldMonth);
					})
					var datas=[oldMonth,newMonth];
					var legend= ["10月总量","11月总量"];
					var type=["bar","bar"];
					var color=["#52bdbd","#ff9696"];
					var yAxis =[ {
						type : 'value',//左边
						name : '数量',
						min : 0,
						interval : 50,
						axisLabel : {
							formatter : '{value} 单'
						}
					}];
					returnBar(xAxisData,yAxis,legend,datas,type,color,myChart2);
					},
				error : function() {
				}
			});

		})
		
	</script> </content>
</body>
</html>