<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>过户有贷款流失-贷款银行分布</title>
        <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
		<link href="${ctx}/css/animate.css" rel="stylesheet">
	    <link href="${ctx}/css/style.css" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css">
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                          <div class="clearfix mb30">
                            <h3 class="content-title pull-left">过户有贷款流失-贷款银行分布</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <p class="calendar-month">
                                    <span >1月</span><span>2月</span><span>3月</span><span>4月</span><span>5月</span><span>6月</span><span>7月</span><span>8月</span><span>9月</span><span>10月</span><span>11月</span><span>12月</span>
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
                                <p class="seize-icon">
                                    <img src="${ctx }/css/images/zhongyuan.png" width="250" alt="" />
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="${ctx }/js/jquery-2.1.1.js"></script>
        <script src="${ctx }/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="${ctx }/static_res/js/echarts.min.js"></script>
        <script src="${ctx }/js/eachartdata/echartsdata.js"></script>
        <script src="${ctx }/js/eachartdata/echartCommon.js"></script>
        <script>
		$(function() {
			// 基于准备好的dom，初始化echarts实例
			var myChart1 = echarts.init(document.getElementById('plotCont1'));
			// 使用刚指定的配置项和数据显示图表。
			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/GuoHu/BankLoan",
				method : "GET",
				dataType : "json",
				success : function(data) {
					if(data==null||data==undefined){
						return;			
					}
					var xAxisData=[];
					var totalAmount=[];
					var acceptAmount=[];
					var acceptAmountRate=[];
					$.each(data,function(i,item){
						xAxisData.push(item.name);
						totalAmount.push(item.totalAmount);
					    acceptAmount.push(item.acceptAmount);
						acceptAmountRate.push(Number(item.acceptAmountRate));
					})
					var datas=[totalAmount,acceptAmount,acceptAmountRate];
					var legend= ["总金额(万元)","收单金额(金额)","收单率"];
					var type=["bar","bar","line"];
					var yAxis =[ {
						type : 'value',//左边
						name : '金额(万元)',
						axisLabel : {
							formatter : '{value}'
						}
					},{
						type : 'value',//右边
						name : '比率',
						axisLabel : {
							formatter : '{value}'
						}
					}
					
					];
					returnBar(xAxisData,yAxis,legend,datas,type,null,myChart1,"贷款银行分配情况");
					},
				error : function() {
				}
			});

		})
/*             echartData("plotCont1");
            echartSet("plotCont2"); */
        </script>
    </body>
</html>
