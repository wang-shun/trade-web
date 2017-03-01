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
		function getNum(num){
			if(isNaN(num)){
				return 0;
			}
			//return num/1000;
			return num;
		}
		function sum(num1,num2){
			if(isNaN(num1)){
				return 0
			}
			if(isNaN(num2)){
				return 0
			}
			return num1 + num2;
		}
		//乘法函数，用来得到精确的乘法结果
		//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
		//调用：accMul(arg1,arg2)
		//返回值：arg1乘以arg2的精确结果
		function accMul(arg1,arg2)
		{
		    var m=0,s1=arg1.toString(),s2=arg2.toString();
		    try{m+=s1.split(".")[1].length}catch(e){}
		    try{m+=s2.split(".")[1].length}catch(e){}
		    return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(2);
		}
		//除法函数，用来得到精确的除法结果
		//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
		//调用：accDiv(arg1,arg2)
		//返回值：arg1除以arg2的精确结果
		function accDiv(arg1,arg2){
		    var t1=0,t2=0,r1,r2;
		    try{t1=arg1.toString().split(".")[1].length}catch(e){}
		    try{t2=arg2.toString().split(".")[1].length}catch(e){}
		    with(Math){
		        r1=Number(arg1.toString().replace(".",""));
		        r2=Number(arg2.toString().replace(".",""));
		        return ((r1/r2)*pow(10,t2-t1)).toFixed(2);
		    }
		}
		//加法函数，用来得到精确的加法结果
		//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
		//调用：accAdd(arg1,arg2)
		//返回值：arg1加上arg2的精确结果
		function accAdd(arg1,arg2){
		    var r1,r2,m;
		    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		    m=Math.pow(10,Math.max(r1,r2));
		    return ((arg1*m+arg2*m)/m).toFixed(2);
		}
		
		
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1
				
			};
        	data.year = year;
        	var url = ctx+"/operateData/operateOne"
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
					//data.rows = 6;
					$('#tableTemplate').empty();
					var tbHtml = "";
					var tr1Html = "<tr><td rowspan='3'>过户数据（签贷款）</td><td>商贷金额</td>";
					var tr2Html = "<tr><td>公积金金额</td>";
					var tr3Html = "<tr><td>总计</td>";
					var tempTd = "<td>0</td>";
					var list = data.voList[0];
					var listSize = list.length;
					 for(var month = 1;month<=12;month++){
						var td1Html = "";
						var td2Html = "";
						var td3Html = "";
						console.log("month==="+month);
						 for(var i = 0;i< listSize;i++){
							 var row = list[i];
						 	if(parseInt(row.month)==(month)){
						 		var num1 = accDiv(getNum(row.mortComAmount),10000);
						 		var num2 = accDiv(getNum(row.mortPrfAmount),10000);
						 		var numA = accAdd(num1,num2);
						 		td1Html = "<td>"+num1+"</td>";
						 		td2Html = "<td>"+num2+"</td>";
						 		td3Html = "<td>"+numA+"</td>";
						 		break;
						 	}
						 }
						 tr1Html+= (td1Html=="")?tempTd:td1Html;
						 tr2Html+= (td2Html=="")?tempTd:td2Html;
						 tr3Html+= (td3Html=="")?tempTd:td3Html;
					} 
					tr1Html +="</tr>";
					tr2Html +="</tr>";
					tr3Html +="</tr>";
					tbHtml+=tr1Html;
					tbHtml+=tr2Html;
					tbHtml+=tr3Html;
					$("#tableTemplate").append(tbHtml);
					
					tbHtml = "";
					tr1Html = "<tr><td rowspan='3'>贷款签约数据</td><td>商贷金额</td>";
					tr2Html = "<tr><td>公积金金额</td>";
					tr3Html = "<tr><td>总计</td>";
					list = data.voList[1];
					listSize = list.length;
					 for(var month = 1;month<=12;month++){
						var td1Html = "";
						var td2Html = "";
						var td3Html = "";
						console.log("month==="+month);
						 for(var i = 0;i< listSize;i++){
							 var row = list[i];
						 	if(parseInt(row.month)==(month)){
						 		var num1 = accDiv(getNum(row.dkmortComAmount),10000);
						 		var num2 = accDiv(getNum(row.dkmortPrfAmount),10000);
						 		var numA = accAdd(num1,num2);
						 		td1Html = "<td>"+num1+"</td>";
						 		td2Html = "<td>"+num2+"</td>";
						 		td3Html = "<td>"+numA+"</td>";
						 		break;
						 	}
						 }
						 tr1Html+= (td1Html=="")?tempTd:td1Html;
						 tr2Html+= (td2Html=="")?tempTd:td2Html;
						 tr3Html+= (td3Html=="")?tempTd:td3Html;
					} 
					tr1Html +="</tr>";
					tr2Html +="</tr>";
					tr3Html +="</tr>";
					tbHtml+=tr1Html;
					tbHtml+=tr2Html;
					tbHtml+=tr3Html;
					$("#tableTemplate").append(tbHtml);
					// 显示分页 
		},error : function(e, jqxhr, settings, exception) {
			//$.unblockUI();
		}
			})
		}
	</script>
</body>
</html>
