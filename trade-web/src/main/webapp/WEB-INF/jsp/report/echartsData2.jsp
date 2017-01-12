<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>过户有贷款流失-单数</title>
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
                            <h3 class="content-title pull-left">过户有贷款流失-单数</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
<<<<<<< HEAD
                                    <a href="#"  id="add"><em>&gt;</em></a>
=======
                                    <a href="#" id="add"><em>&gt;</em></a>
>>>>>>> branch 'develop' of http://git.centaline.com/trade-group/trade-web.git
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
                                <p class="zhyu-icon"><img src="${ctx }/css/images/zhongyuan.png" alt="" /></p>
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
        <script src="${ctx }/js/eachartdata/select_month.js"></script>
        <script src="${ctx }/js/eachartdata/echartCommon.js"></script>
        <script>
		$(function() {
			reload();
		})
		
		function reload(){
			// 基于准备好的dom，初始化echarts实例
			var myChart1 = echarts.init(document.getElementById('plotCont1'));
			var myChart2 = echarts.init(document.getElementById('plotCont2'));
			// 指定图表的配置项和数据

			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/GuoHu/ComLoan",
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
					var color=["#BFD8FF","#ff9696"];
					var data = [ "收单", "流失" ];
					var option2 = returnPie(data, items, myChart2, color,"商贷总单数");
				},
				error:function(){}
			})


			// 使用刚指定的配置项和数据显示图表。
			$.ajax({
				url : "http://10.4.19.211:3001/rest/v1/report/GuoHu/ComLoanDistrict",
				method : "GET",
				dataType : "json",
				success : function(data) {
					if(data==null||data==undefined){
						return;			
					}
					var xAxisData=[];
					var total=[];
					var loss=[];
					var lossRate=[];
					var oldLossRate=[];
					$.each(data,function(i,item){
						xAxisData.push(item.name);
						total.push(item.total);
						loss.push(item.loss);
						lossRate.push(Number(item.lossRate));
						oldLossRate.push(Number(item.oldLossRate));
					})
					var datas=[total,loss,lossRate,oldLossRate];
					var legend= ["商贷总单数","流失单数","流失率","上月流失率"];
					var type=["bar","bar","line","line"];
					var yAxis =[ {
						type : 'value',//左边
						name : '数量(单)',
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
					returnBar(xAxisData,yAxis,legend,datas,type,null,myChart1,"各贵宾中心商贷比较");
					},
				error : function() {
				}
			});

		}
/*             echartData("plotCont1");
            echartSet("plotCont2"); */
        </script>
    </body>
</html>
