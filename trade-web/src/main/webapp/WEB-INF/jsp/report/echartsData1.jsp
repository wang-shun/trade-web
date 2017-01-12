<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>e+放款流水清单</title>
        <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
		<link href="${ctx}/css/animate.css" rel="stylesheet">
	    <link href="${ctx}/css/style.css" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css"/>
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css"/>
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">过户数据统计</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#"><em>&gt;</em></a>
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
                            <div class="plot-rightone">
                                <div id="plotCont2"  style="width:100%;height:300px; ">
                                </div>
                            </div>
                            <div class="plot-righttwo mt10 relative">
                                <div class="total-data">
                                    <h3>11月过户总量</h3>
                                    <ul class="total-list">
                                        <li><i class="iconfont mr5 al-yellow al-icon-22">&#xe643;</i>11月总量<span>1664</span>单</li>
                                        <li><i class="iconfont mr5 al-grey al-icon-22">&#xe643;</i>10月总量<span>1836</span>单</li>
                                        <li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比下降<span>9%</span></li>
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
        <script src="${ctx}/js/jquery-2.1.1.js"></script>
        <script src="${ctx}/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="${ctx}/static_res/js/echarts.min.js"></script>
        <script src="${ctx}/js/eachartdata/echartsdata.js"></script>
        <script src="${ctx }/js/eachartdata/echartCommon.js"></script>
        <script>
		$(function() {
			// 基于准备好的dom，初始化echarts实例
			var myChart1 = echarts.init(document.getElementById('plotCont1'));
			var myChart2 = echarts.init(document.getElementById('plotCont2'));
			// 指定图表的配置项和数据

			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/GuoHu/Count",
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
					var color=["#BFD8FF","#ff9696","#FFD480"];
					var data = [ "无贷款", "纯公积金", "商业贷款" ];
					var option2 = returnPie(data, items, myChart2, color,"过户总单量");
				},
				error:function(){}
			})


			// 使用刚指定的配置项和数据显示图表。
			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/GuoHu/CountDistrict",
				method : "GET",
				dataType : "json",
				success : function(data) {
					if(data==null||data==undefined){
						return;			
					}
					console.info(data);
					var xAxisData=[];
					var oldMonth=[];
					var newMonth=[];
					$.each(data,function(i,item){
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
						name : '数量(单)',
						min : 0,
						interval : 50,
						axisLabel : {
							formatter : '{value}'
						}
					}];
					returnBar(xAxisData,yAxis,legend,datas,type,color,myChart1,"11月与10月过户总量比较");
					},
				error : function() {
				}
			});

		})
				
      /*       echartData("plotCont1");
            echartSet("plotCont2"); */
        </script>

    </body>
</html>
