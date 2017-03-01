<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签贷款数据</title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
        <link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">签贷款数据</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <input type="hidden" value="${ctx}" id="ctx">
                            </div>
                        </div>

                        <!-- table -->
                        <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                            <thead>
                                <tr>
                                    <th>签约贷款数据</th>
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
		
       	
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1
				
			};
			data.year = year;
        	var url = ctx+"/operateData/operateThree"
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
					
					data.ctx = ctx;
					//data.rows = 6;
					$('#tableTemplate').empty();
					var tbHtml = "";
					var tr1Html =  "<tr><td>派单量 		</td>";			
					var tr2Html =  "<tr><td>签约量（买卖）		</td>";              
					var tr3Html =  "<tr><td>过户量	</td>";      
					var tr4Html =  "<tr><td>商贷签约量		</td>";      
					var tr5Html =  "<tr><td>商贷签约量		</td>";      
					var tr6Html =  "<tr><td>商贷案件占比		</td>";      
					var tr7Html =  "<tr><td>纯公积金案件占比	</td>";          
					var tr8Html =  "<tr><td>签贷合同价    </td>";    
					var tr9Html =  "<tr><td>商贷金额	</td>";      
					var tr10Html = "<tr><td>公积金金额</td>";     
					var tr11Html = "<tr><td>商贷金额占比</td>";     
					var tr12Html = "<tr><td>公积金金额占比  </td>";    
					var tempTd = "<td>0</td>";
					var list = data.voList[0];
					var listSize = list.length;debugger;
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
						 for(var i = 0;i< listSize;i++){
							 var row = list[i];
						 	if(parseInt(row.month)==(month)){
						 		var num1 = getNum(row.mortComAmount);
						 		var num2 = getNum(row.mortPrfAmount);
						 		var numA = sum(num1,num2);
						 		td1Html = "<td>"+num1+"</td>";
						 		td2Html = "<td>"+num2+"</td>";
						 		td3Html = "<td>"+numA+"</td>";
						 		td4Html = "<td>"+numA+"</td>";
						 		td5Html = "<td>"+numA+"</td>";
						 		td6Html = "<td>"+numA+"</td>";
						 		td7Html = "<td>"+numA+"</td>";
						 		td8Html = "<td>"+numA+"</td>";
						 		td9Html = "<td>"+numA+"</td>";
						 		td10Html = "<td>"+numA+"</td>";
						 		td11Html = "<td>"+numA+"</td>";
						 		td12Html = "<td>"+numA+"</td>";
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
					$("#tableTemplate").append(tbHtml);
					
					/* var title = [
					             "派单量",
					             "签约量（买卖）",
					             "过户量",
					            " 商贷签约量",
					            " 公积金签约量",
					            " 商贷案件占比",
					             "纯公积金案件占比",
					             "签贷合同价",
					             "商贷金额",
					             "公积金金额",
					             "商贷金额占比",
					             "公积金金额占比"
 						];
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
					$("#tableTemplate").html(trHtml); */
				}
			})
		}
	</script>
    </body>
</html>
