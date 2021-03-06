<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签贷款数据</title>
        <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet"/>
        <link href="<c:url value='/static/css/animate.css' />" rel="stylesheet"/>
        <link href="<c:url value='/static/css/style.css' />" rel="stylesheet"/>
        <!-- index_css -->
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
        <link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />">
        
    </head>
    <body style="background-color:#fff;">
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
                        <div class="table-scroll" style="height:550px;overflow: auto">
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
                                    <th>总计</th>
                                </tr>
                            </thead>
                            <tbody id="tableTemplate">
                                
                            </tbody>
                        </table>
                       
                        </div><br>
                         <div>
                        	备注<br/>
                        	①：商贷案件占比=商贷签约量/签约量（买卖）<br/>
	  						②：公积金金额：包含组合贷款中的公积金和纯公积金贷款
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Mainly scripts -->
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script src="<c:url value='/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/trunk/report/calculation_main.js' />"></script> 
       	<script type="text/javascript">
		
       	var ctx = $("#ctx").val();
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
					if (data == null || data == undefined || !(data.ajaxResponse.success)) {
						window.parent.wxc.alert("数据加载失败！");
						return;
					}
					data.ctx = ctx;
					$('#tableTemplate').empty();
					var tbHtml = "";
					var tr1Html =  "<tr><td>派单量 		</td>";			
					var tr2Html =  "<tr><td>签约量（买卖）	</td>";              
					var tr3Html =  "<tr><td>过户量		</td>";      
					var tr4Html =  "<tr><td>商贷签约量		</td>";      
					var tr5Html =  "<tr><td>公积金签约量	</td>";      
					var tr6Html =  "<tr><td>商贷案件占比	</td>";      
					var tr7Html =  "<tr><td>纯公积金案件占比</td>";          
					var tr8Html =  "<tr><td>签贷合同价(万元)</td>";    
					var tr9Html =  "<tr><td>商贷金额(万元)</td>";      
					var tr10Html = "<tr><td>公积金金额	(万元)</td>";     
					var tr11Html = "<tr><td>商贷金额占比	</td>";     
					var tr12Html = "<tr><td>公积金金额占比  	</td>";    
					var tempTd = "<td>0</td>";
					var list = data.voList[0];
					var voListToPdl = data.voListToPdl[0];
					var voListToCyl = data.voListToCyl[0];
					var voListToghl = data.voListToghl[0];
					var listSize = list.length;
					var voListToPdlSize = voListToPdl.length;
					var voListToCyllistSize = voListToCyl.length;
					var voListToghllistSize = voListToghl.length;
					 for(var month = 1;month<=13;month++){
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
						for(var i = 0;i< voListToPdlSize;i++){
							 var row = voListToPdl[i];
							 	if(parseInt(row.month)==(month)){
							 		td1Html = "<td>"+getNum(row.dispatchSum)+"</td>";		                                        /*1派单量**/ 
							 		break;   
							 	}
						 }
						 for(var i = 0;i< voListToCyllistSize;i++){
							 var row = voListToCyl[i];
							 	if(parseInt(row.month)==(month)){
							 		td2Html = "<td>"+getNum(row.realConSum)+"</td>";                                                /*2签约量（买卖）**/   
							 		 for(var j = 0;j< listSize;j++){
										 var row2 = list[j];
									 	if(parseInt(row2.month)==(month)){
									 		if(getNum(row2.comSum) == 0 || getNum(row.realConSum)==0) td6Html = "<td>"+0.00+"%</td>";        /*6商贷案件占比**/      
									 		else td6Html = "<td>"+accMul(accDiv(getNum(row2.comSum),getNum(row.realConSum)),100)+"%</td>";   /*6商贷案件占比**/      
									 		if(getNum(row2.prfSum)==0 || getNum(row.realConSum)==0)td7Html = "<td>"+0.00+"%</td>";           /*7纯公积金案件占比**/      
									 		else td7Html = "<td>"+accMul(accDiv(getNum(row2.prfSum),getNum(row.realConSum)),100)+"%</td>";   /*7纯公积金案件占比**/   
									 		break; 
									 	}
							 		 }
							 		break;   
							 	}
							 
						 }
						 for(var i = 0;i< voListToghllistSize;i++){
							 var row = voListToghl[i];
							 	if(parseInt(row.month)==(month)){
							 		td3Html = "<td>"+getNum(row.transferAppPassSum)+"</td>";                                        /*3过户量**/      
							 		break;   
							 	}
						 }
						 for(var i = 0;i< listSize;i++){
							 var row = list[i];
						 	if(parseInt(row.month)==(month)){
						 		var num1 = getNum(row.mortComAmount);
						 		var num2 = getNum(row.mortPrfAmount);
						 		var numA = sum(num1,num2);                                                                     
						 		td4Html = "<td>"+getNum(row.comSum)+"</td>";                                                    /*4商贷签约量**/      
						 		td5Html = "<td>"+getNum(row.prfSum)+"</td>";                                                    /*5公积金签约量**/      
						 		   
						 		td8Html = "<td>"+accDivN(getNum(row.conPrice),10000)+"</td>";                                    /*8签贷合同价**/      
						 		td9Html = "<td>"+accDivN(num1,10000)+"</td>";                                                    /*9商贷金额**/      
						 		td10Html = "<td>"+accDivN(num2,10000)+"</td>";                                                   /*10公积金金额**/      
						 		if(num1==0 || getNum(row.conPrice)==0) td11Html = "<td>"+0+"%</td>";                         /*11商贷金额占比**/      
						 		else td11Html = "<td>"+accMul(accDiv(num1,getNum(row.conPrice)),100)+"%</td>";                  /*11商贷金额占比**/      
						 		if(num2==0 || getNum(row.conPrice)==0) td12Html = "<td>"+0+"%</td>";                         /*12公积金金额占比**/      
						 		else td12Html = "<td>"+accMul(accDiv(num2,getNum(row.conPrice)),100)+"%</td>";                  /*12公积金金额占比**/      
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
				}
			})
		}
	</script>
    </body>
</html>
