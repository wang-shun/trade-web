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
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css" />
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
        <input type="hidden" id="ctx" value="${ctx }"/>
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                        <h3 class="content-title">过户数据统计</h3>
                        <div class="left-content">
                            <div id="plotCont1" class="plot-leftone">
                            </div>
                            <table class="echarsTable">
                            <!-- 图表 -->
                            </table>
                        </div>
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                    <h3>数据统计</h3>
                                    <ul class="data-list">
                                        <li><em>欠贷合同价</em><span>1664</span>单</li>
                                        <li><em>商贷金额</em><span>1836</span>万元</li>
                                        <li><em>公积金金额</em><span>943</span></li>
                                    </ul>
                                    <ul class="data-list data-border">
                                        <li><em>商贷签贷占比</em><span class="red">57%</span></li>
                                        <li><em>公积金金额占比</em><span class="red">20%</span></li>
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
        <script src="${ctx }/js/jquery-2.1.1.js"></script>
        <script src="${ctx }/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="${ctx }/static_res/js/echarts.min.js"></script>
        <script src="${ctx}/static/trans/js/common/echartCommon.js"></script>
        <script>
        /**
         * 案件统计详情
         */
     	var ctx = $("#ctx").val();
        $(document).ready(function() {        	
        	// 初始化列表
        	var data = {};
        	data.queryId = "queryDispatchSignList";	
        	data.rows = 12;
        	data.page = 1;
        	
        	reloadGrid(data);
        });
        
        function reloadGrid(data) {
        	$.ajax({
        		async: true,
                url: ctx+"/quickGrid/findPage",
                method: "post",
                dataType: "json",
                data: data,
                success: function(data){
				if(data==null||data==undefined){
					return;			
				}
				var xAxisData=[];
				var yAxis = [];
            	var legend = [];
            	var datas = [];
            	var type= [];
            	var color = [];
            	var myChart = null;
            	
            	var dispatchNumArr = [];
            	var signNumArr = [];
            	var guohuNumArr = [];
            	var comNumArr = [];
            	var prfNumArr = [];
            	var comPercentArr = [];
            	var prfPercentArr = [];
            	//1.
            	$.each(data.rows,function(i,item){
					xAxisData.push(item.DISTRICT_NAME.substring(0,2));
					dispatchNumArr.push(item.DISPATCH_NUM);
					signNumArr.push(item.SIGN_NUM);
					guohuNumArr.push(item.GUOHU_NUM);
					comNumArr.push(item.COM_NUM);
					prfNumArr.push(item.PRF_NUM);
					comPercentArr.push(item.COM_NUM/item.SIGN_NUM);
					prfPercentArr.push(item.PRF_NUM/item.SIGN_NUM);
				})
            	//2.
            	yAxis =[ 
            	{
                    type: 'value',//左边
                    name: '金额(万)',
                    min: 0,
                    //max: 250,
                    //interval: 50,
                    axisLabel: {
                        formatter: '{value} '
                    }
                },
                {
                    type: 'value',//右边
                    name: '百分比',
                    min: 0,
                    //max: 30,
                    //interval: 6,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
				];
            	//3.
            	legend = ['派单量','签约量','过户量','商贷量','纯公积金量','商贷签贷占比','纯公积金签贷占比'];
            	//4.
            	datas = [dispatchNumArr,signNumArr,guohuNumArr,comNumArr,prfNumArr,comPercentArr,prfPercentArr];
            	//5.
            	type = ["bar","bar","bar","bar","bar","line","line"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart);
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>

    </body>
</html>
