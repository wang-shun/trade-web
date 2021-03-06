<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签约贷款金额占比</title>
        <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"/>
	    <link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet"/>
		<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
	    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
        <link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />">
    </head>
    <body style="background-color:#fff;">
        <input type="hidden" id="ctx" value="${ctx }"/>
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                          <div class="clearfix mb30">
                            <h3 class="content-title pull-left">签约贷款金额占比</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <p class="calendar-month">
		                            <span value="1">1月</span>
		                            <span value="2">2月</span>
		                            <span value="3">3月</span>
		                            <span value="4">4月</span>
		                            <span value="5">5月</span>
		                            <span value="6">6月</span>
		                            <span value="7">7月</span>
		                            <span value="8">8月</span>
		                            <span value="9">9月</span>
		                            <span value="10">10月</span>
		                            <span value="11">11月</span>
		                            <span value="12">12月</span>
                                </p>
                            </div>
                        </div>
                        <div class="left-content">
                            <div id="plotCont1" class="plot-leftone">
                            </div>
                            <table class="echarsTable">
                            </table>
                            <div>
                                                                      备注：公积金金额包含纯公积金金额和组合贷款中的公积金金额<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商贷金额占比 = 商贷金额 / 签贷合同价<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公积金金额占比 = 公积金金额 / 签贷合同价                                           
                            </div>
                        </div>                     
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                    <h3>数据统计</h3>
                                    <ul class="data-list">
                                        <li><i class='colorBar' style='background-color:#295aa5'></i><em>签贷合同价</em><span id="span1"></span>万元</li>
                                        <li><i class='colorBar' style='background-color:#f784a5'></i><em>商贷金额</em><span id="span2"></span>万元</li>
                                        <li><i class='colorBar' style='background-color:#ffad6b'></i><em>公积金金额</em><span id="span3"></span>万元</li>
                                    </ul>
                                    <ul class="data-list data-border">
                                        <li><em>商贷金额占比</em><span id="span4" class="red"></span></li>
                                        <li><em>公积金金额占比</em><span id="span5" class="red"></span></li>
                                    </ul>
                                </div>
                                <p class="zhyu-icon"><img src="${ctx }/css/images/zhongyuan.png" alt="" /></p>
                            </div>
                        </div>     
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script src="<c:url value='/js/bootstrap.min.js' />"></script>
        <!-- ECharts.js -->
        <script src="<c:url value='/static/js/echarts-all.js' />"></script>
        <script src="<c:url value='/static/trans/js/common/echartCommon.js' />"></script>
        <script>
     	var ctx = $("#ctx").val();
     	
        function reloadGrid() {
        	//完整的区(8)
        	var districtIDArr = [];
        	var districtNameArr = [];
        	//获取所有大区
            var data = {
                    queryId: "queryDistrict",
                    pagination : false
                }
            $.ajax({
                url : ctx+"/quickGrid/findPage",
                method : "GET",
                data : data,
                dataType : "json",
                async:false,
                success : function(data) {
                    $.each(data.rows,function(i,item){
                    	districtIDArr.push(item.DISTRICT_ID);
                        districtNameArr.push(item.DISTRICT_NAME.substring(0,2));
                    })
                },
                error:function(){}
            });
        	
        	var data = {};
        	data.queryId = "queryDispatchSignList";	
        	data.pagination = false;
        	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_);
        	data.choiceMonth = year + "-" + month;
            data.belongMoth  = getBelongMonth(year + "-" + month),
        	$.ajax({
        		async: true,
                url: ctx+"/quickGrid/findPage",
                method: "post",
                dataType: "json",
                data: data,
                success: function(data){
				if(data==null||data==undefined){
                    window.parent.wxc.alert("数据加载失败！");
					return;			
				}
				
				var xAxisData=[];
				var yAxis = [];
            	var legend = [];
            	var datas = [];
            	var type= [];
            	var color = [];
            	var myChart = null;
            	
            	var totalAmountArr = [];
            	var comAmountArr = [];
            	var prfAmountArr = [];
            	var comPercentArr = [];
            	var prfPercentArr = [];
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	var span3Text = 0;
            	var span4Text = 0;
            	var span5Text = 0;
            	//1.				
				for(var i in districtIDArr){
        			var flag = false;
        			if(data.rows.length > 0){
            			for(var j in data.rows){
            				item = data.rows[j];
            				if(districtIDArr[i] == item.DISTRICT_ID){
            					xAxisData[i] = districtNameArr[i];
            					totalAmountArr[i] = Math.round(accDiv(parseInt(item.SIGN_CON_PRICE),10000));
            					span1Text = accAdd(span1Text,accDiv(parseInt(item.SIGN_CON_PRICE),10000));
            					comAmountArr[i] = Math.round(accDiv(parseInt(item.COM_AMOUNT),10000));
            					span2Text = accAdd(span2Text,accDiv(parseInt(item.COM_AMOUNT),10000));
            					prfAmountArr[i] = Math.round(accDiv(parseInt(item.PRF_AMOUNT),10000));
            					span3Text = accAdd(span3Text,accDiv(parseInt(item.PRF_AMOUNT),10000));
            					comPercentArr[i] = accMul(accDiv(parseInt(item.COM_AMOUNT),parseInt(item.SIGN_CON_PRICE)),100).replace(".00","");
            					prfPercentArr[i] = accMul(accDiv(parseInt(item.PRF_AMOUNT),parseInt(item.SIGN_CON_PRICE)),100).replace(".00","");
            					flag = true;
            					}
            				}
        			}

        			if(!flag){
        				xAxisData[i] = districtNameArr[i];
        				totalAmountArr[i] = 0;
        				comAmountArr[i] = 0;
        				prfAmountArr[i] = 0;
        				comPercentArr[i] = 0;
        				prfPercentArr[i] = 0;
        			}
        			}
				
            	span4Text = accMul(accDiv(parseInt(span2Text),parseInt(span1Text)),100).replace(".00","");
            	span5Text = accMul(accDiv(parseInt(span3Text),parseInt(span1Text)),100).replace(".00","");
            	//2.
            	yAxis =[ 
            	{
                    type: 'value',//左边
                    name: '金额',
                    min:0,
                    max:150000,
                    axisLabel: {
                        formatter: '{value}万 '
                    }
                },
                {
                    type: 'value',//右边
                    name: '百分比',
                    min:0,
                    max:100,
                    axisLabel: {
                        formatter: '{value}%'
                    }
                }
				];
            	//3.
            	legend = ['签贷合同价','商贷金额','公积金金额','商贷金额占比','公积金金额占比'];
            	//4.
            	datas = [totalAmountArr,comAmountArr,prfAmountArr,comPercentArr,prfPercentArr];
            	//5.
            	type = ["bar","bar","bar","line","line"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "签约贷款金额占比";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	//填充span数据 
            	$("#span1").text(Math.round(span1Text));
            	$("#span2").text(Math.round(span2Text));
            	$("#span3").text(Math.round(span3Text));
            	$("#span4").text(span4Text+"%");
            	$("#span5").text(span5Text+"%");
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>
    </body>
</html>
