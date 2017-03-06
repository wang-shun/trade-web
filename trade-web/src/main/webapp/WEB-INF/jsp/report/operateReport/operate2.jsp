<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
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
						<h3 class="content-title pull-left">过户数据</h3>
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
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="${ctx }/js/jquery-2.1.1.js"></script>
	<script src="${ctx }/js/bootstrap.min.js"></script>
	<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/trunk/report/calculation_main.js"></script> 
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
			var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1

			};
			data.year = year;
        	var url = ctx+"/operateData/operateTwo"
			$.ajax({
				async : true,
				url : url,
				method : "get",
				dataType : "json",
				data : data,
				success : function(data) {
					if (data == null || data == undefined || !(data.ajaxResponse.success)) {
						window.parent.wxc.alert("数据加载失败！");
						return;
					}
					data.ctx = ctx;
					$('#tableTemplate').empty();
					var tbHtml = "";
					var tr1Html = "<tr><td>无贷款单数</td>";
					var tr2Html = "<tr><td>公积金单数</td>";
					var tr3Html = "<tr><td>商贷单数</td>";
					var tr4Html = "<tr><td>商贷单数占比</td>";
					var tr5Html = "<tr><td>过户房价</td>";
					var tr6Html = "<tr><td>商贷金额</td>";
					var tr7Html = "<tr><td>公积金金额</td>";
					var tr8Html = "<tr><td>杠杆率</td>";
					var tr9Html = "<tr><td>有商贷案件房价</td>";
					var tr10Html = "<tr><td>贷款金额占比</td>";
					var tr11Html = "<tr><td>商贷收单（商贷）</td>";
					var tr12Html = "<tr><td>流失单数（商贷）</td>";
					var tr13Html = "<tr><td>单数流失率（商贷）</td>";
					var tr14Html = "<tr><td>收单金额（商贷）</td>";
					var tr15Html = "<tr><td>流失金额（商贷）</td>";
					var tr16Html = "<tr><td>金额流失率（商贷）</td>";
					var tempTd = "<td>0</td>";
					var list = data.voList[0];
					var listSize = list.length;
					 for(var month = 1;month<=12;month++){
						var td1Html = "";
						var td2Html = "";
						var td3Html = "";
						var td4Html = "";
						var td5Html = "";
						var td6Html = "";
						var td7Html = "";
						var td8Html = "";
						var td9Html = "";
						var td10Html = "";
						var td11Html = "";
						var td12Html = "";
						var td13Html = "";
						var td14Html = "";
						var td15Html = "";
						var td16Html = "";
						 for(var i = 0;i< listSize;i++){
							 var row = list[i];
						 	if(parseInt(row.month)==(month)){
						 		var num1 = accDiv(getNum(row.mortComAmount),10000);
						 		var num2 = accDiv(getNum(row.mortPrfAmount),10000);
						 		var numA = sum(num1,num2);
						 		td1Html = "<td>"+getNum(row.loanReqNum)+"</td>";
						 		td2Html = "<td>"+getNum(row.prfNum)+"</td>";
						 		td3Html = "<td>"+getNum(row.comNum)+"</td>";
						 		td4Html = "<td>"+accMul(accDiv(row.comNum,row.allNum),100)+'%'+"</td>";
						 		td5Html = "<td>"+accDiv(getNum(row.allRealPrice),10000)+"</td>";
						 		td6Html = "<td>"+num1+"</td>";
						 		td7Html = "<td>"+num2+"</td>";
						 		td8Html = "<td>"+accMul(accDiv(accAdd(getNum(row.mortComAmount),getNum(row.mortPrfAmount)),getNum(row.allRealPrice)),100)+"%</td>";
						 		td9Html = "<td>"+accDiv(getNum(row.realPrice),10000)+"</td>";
						 		td10Html = "<td>"+accMul(accDiv(getNum(row.mortPrfAmount),getNum(row.realPrice)),100)+"%</td>";
						 		td11Html = "<td>"+getNum(row.comRec)+"</td>";
						 		td12Html = "<td>"+getNum(row.lsRec)+"</td>";
						 		if(getNum(row.lsRec)==0 || getNum(row.comNum)==0) td13Html = "<td>"+0.00+"%</td>";
						 		else td13Html = "<td>"+accMul(accDiv(getNum(row.lsRec),getNum(row.comNum)),100)+"%</td>";
						 		td14Html = "<td>"+accDiv(getNum(row.sdAmount),10000)+"</td>";
						 		td15Html = "<td>"+accDiv(getNum(row.lsAmount),10000)+"</td>";
						 		if(getNum(row.lsAmount)==0 || getNum(row.mortPrfAmount)==0) td16Html = "<td>"+0.00+"%</td>";
						 		else td16Html = "<td>"+accMul(accDiv(getNum(row.lsAmount),getNum(row.mortPrfAmount)),100)+"%</td>";
						 		break;
						 	}
						 }
						 tr1Html+= (td1Html=="")?tempTd:td1Html;
						 tr2Html+= (td2Html=="")?tempTd:td2Html;
						 tr3Html+= (td3Html=="")?tempTd:td3Html;
						 tr4Html+= (td4Html=="")?tempTd:td4Html;
						 tr5Html+= (td5Html=="")?tempTd:td5Html;
						 tr6Html+= (td6Html=="")?tempTd:td6Html;
						 tr7Html+= (td7Html=="")?tempTd:td7Html;
						 tr8Html+= (td8Html=="")?tempTd:td8Html;
						 tr9Html+= (td9Html=="")?tempTd:td9Html;
						 tr10Html+= (td10Html=="")?tempTd:td10Html;
						 tr11Html+= (td11Html=="")?tempTd:td11Html;
						 tr12Html+= (td12Html=="")?tempTd:td12Html;
						 tr13Html+= (td13Html=="")?tempTd:td13Html;
						 tr14Html+= (td14Html=="")?tempTd:td14Html;
						 tr15Html+= (td15Html=="")?tempTd:td15Html;
						 tr16Html+= (td16Html=="")?tempTd:td16Html;
					} 
					tr1Html +="</tr>";
					tr2Html +="</tr>";
					tr3Html +="</tr>";
					tr4Html +="</tr>";
					tr5Html +="</tr>";
					tr6Html +="</tr>";
					tr7Html +="</tr>";
					tr8Html +="</tr>";
					tr9Html +="</tr>";
					tr10Html +="</tr>";
					tr11Html +="</tr>";
					tr12Html +="</tr>";
					tr13Html +="</tr>";
					tr14Html +="</tr>";
					tr15Html +="</tr>";
					tr16Html +="</tr>";
					tbHtml+=tr1Html;
					tbHtml+=tr2Html;
					tbHtml+=tr3Html;
					tbHtml+=tr4Html;
					tbHtml+=tr5Html;
					tbHtml+=tr6Html;
					tbHtml+=tr7Html;
					tbHtml+=tr8Html;
					tbHtml+=tr9Html;
					tbHtml+=tr10Html;
					tbHtml+=tr11Html;
					tbHtml+=tr12Html;
					tbHtml+=tr13Html;
					tbHtml+=tr14Html;
					tbHtml+=tr15Html;
					tbHtml+=tr16Html;
					$("#tableTemplate").append(tbHtml);
				}
			})
		}
	</script>
</body>
</html>
