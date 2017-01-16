<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>派单签约量统计</title>
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
        <input type="hidden" id="ctx" value="${ctx }"/>
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                         <div class="clearfix mb30">
                            <h3 class="content-title pull-left">派单、签约量统计</h3>
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
                            <!-- 图表 -->
                            </table>
                        </div>
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                    <h3>数据统计</h3>
                                    <ul class="data-list">
                                        <li><em>派单量</em><span id="span1"></span>单</li>
                                        <li><em>签约量</em><span id="span2"></span>单</li>
                                        <li><em>过户量</em><span id="span3"></span>单</li>
                                        <li><em>商贷量</em><span id="span4"></span>单</li>
                                        <li><em>纯公积金量</em><span id="span5"></span>单</li>
                                    </ul>
                                    <ul class="data-list data-border">
                                        <li><em>商贷签贷占比</em><span id="span6" class="red"></span></li>
                                        <li><em>纯公积金签贷占比</em><span id="span7" class="red"></span></li>
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
        <script src="${ctx }/js/eachartdata/select_month.js"></script>
        <script>
        /**
         * 案件统计详情
         */
     	var ctx = $("#ctx").val();

        $(document).ready(function() {    	
        	reloadGrid();
        });
        
        function reloadGrid() {
        	// 初始化列表
        	var data={};
        	data.queryId = "queryDispatchSignList";	
        	data.pagination = false;
        	var year = $(".calendar-year span").html();
        	var month = $(".calendar-month span").hasClass(".select-blue").html().substring(0,1);
        	data.choiceMonth = year + "-" + month;

        	$.ajax({
        		async: true,
                url: ctx+"/quickGrid/findPage",
                method: "post",
                dataType: "json",
                data: data,
                success: function(data){
				if(data==null||data==undefined){
					alert("数据加载失败！");
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
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	var span3Text = 0;
            	var span4Text = 0;
            	var span5Text = 0;
            	var span6Text = 0;
            	var span7Text = 0;
            	//1.
            	$.each(data.rows,function(i,item){
					xAxisData.push(item.DISTRICT_NAME.length>2?item.DISTRICT_NAME.substring(0,2):item.DISTRICT_NAME);
					dispatchNumArr.push(item.DISPATCH_NUM);
					span1Text += parseInt(item.DISPATCH_NUM);
					signNumArr.push(item.SIGN_NUM);
					span2Text += parseInt(item.SIGN_NUM);
					guohuNumArr.push(item.GUOHU_NUM);
					span3Text += parseInt(item.GUOHU_NUM);
					comNumArr.push(item.COM_NUM);
					span4Text += parseInt(item.COM_NUM);
					prfNumArr.push(item.PRF_NUM);
					span5Text += parseInt(item.PRF_NUM);
					comPercentArr.push(accDiv(item.COM_NUM,item.SIGN_NUM)*100);
					prfPercentArr.push(accDiv(item.PRF_NUM,item.SIGN_NUM)*100);			
				})
            	span6Text = accDiv(span4Text,span2Text)*100+"%";
            	span7Text = accDiv(span5Text,span2Text)*100+"%";
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
                    max: 100,
                    //interval: 6,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
				];
            	//3.
            	legend = ['派单量','签约量','过户量','商贷量','纯公积金量','商贷签贷占比','纯公积金占比'];
            	//4.
            	datas = [dispatchNumArr,signNumArr,guohuNumArr,comNumArr,prfNumArr,comPercentArr,prfPercentArr];
            	//5.
            	type = ["bar","bar","bar","bar","bar","line","line"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "月派单、签约量统计";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	console.dir(myChart);
            	//填充span数据 
            	$("#span1").text(span1Text);
            	$("#span2").text(span2Text);
            	$("#span3").text(span3Text);
            	$("#span4").text(span4Text);
            	$("#span5").text(span5Text);
            	$("#span6").text(span6Text);
            	$("#span7").text(span7Text);
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>

    </body>
</html>
