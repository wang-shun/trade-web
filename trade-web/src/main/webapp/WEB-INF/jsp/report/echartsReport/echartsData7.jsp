<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签约贷款银行分布</title>
        <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"/>
	    <link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet"/>
		<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
	    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
        <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />" />
    </head>
    <body style="background-color:#fff;">
        <input type="hidden" id="ctx" value="${ctx }"/>
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                         <div class="clearfix mb30">
                            <h3 class="content-title pull-left">签约贷款银行分布</h3>
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
                        </div>
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                    <h3>贷款银行分布</h3>
                                    <ul class="data-list">
                                        <li><em>商贷贷款单数</em><span id="span1"></span>单</li>
                                        <li><em>商贷金额</em><span id="span2"></span>万元</li>
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
        	// 初始化列表
        	var data = {};
        	data.queryId = "querySignBankList";	
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
            	var totalNumArr = [];
            	var totalAmount = 0;
            	var totalNum = 0;
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	//其他 
            	var otherAmountI = 0;
            	var otherNumI = 0;
            	//1.
            	if(data.rows.length > 0){
            		$.each(data.rows,function(i,item){
    					span1Text = accAdd(span1Text,accDiv(parseInt(item.CONTRACT_AMOUNT),10000));	
    					span2Text += parseInt(item.SIGN_NUM);
            			if(i < 14){
        					xAxisData.push(item.FA_FIN_ORG_NAME_YC == ""?"未选择":item.FA_FIN_ORG_NAME_YC.substring(0,2));
                			totalAmountArr.push(Math.round(accDiv(parseInt(item.CONTRACT_AMOUNT),10000)));
        					totalNumArr.push(parseInt(item.SIGN_NUM));
            			}else{
            				otherAmountI = accAdd(otherAmountI,accDiv(parseInt(item.CONTRACT_AMOUNT),10000));
            				otherNumI += parseInt(item.SIGN_NUM);
            			}
    				})
            	} 	
            	
            	if(data.rows.length > 14){
            		xAxisData.push("其他");
            		totalAmountArr.push(Math.round(otherAmountI));
            		totalNumArr.push(otherNumI);
            	}
            	//2.
            	yAxis =[ 
            	{
                    type: 'value',//左边
                    name: '金额',
                    min:0,
                    max:80000,
                    axisLabel: {
                        formatter: '{value}万 '
                    }
                },
                {
                    type: 'value',//右边
                    name: '单数',
                    min:0,
                    max:600,
                    axisLabel: {
                        formatter: '{value} '
                    }
                }
				];
            	//3.
            	legend = data.rows.length>0?['总金额','总单量']:[];
            	//4.
            	datas = [totalAmountArr,totalNumArr];
            	//5.
            	type = ["bar","line"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "签约贷款银行分布";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	//填充span数据 
            	$("#span1").text(span2Text);
            	$("#span2").text(Math.round(span1Text));
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>
    </body>
</html>
