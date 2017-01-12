<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签约贷款银行支行流向统计</title>
        <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
		<link href="${ctx}/css/animate.css" rel="stylesheet">
	    <link href="${ctx}/css/style.css" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css" ">
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                          <div class="clearfix mb30">
                            <h3 class="content-title pull-left">签约贷款银行支行流向统计</h3>
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
                        </div>3>
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
                                    <h3>签约贷款银行支行流向统计</h3>
                                    <ul class="total-list">
                                        <li><i class="iconfont mr5 al-yellow al-icon-22">&#xe643;</i>11月总量<span>1664</span>单</li>
                                        <li><i class="iconfont mr5 al-grey al-icon-22">&#xe643;</i>10月总量<span>1836</span>单</li>
                                        <li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比下降<span>9%</span></li>
                                    </ul>
                                    <hr />
                                    <table class="table table-bordered text-center else-table">
                                            <thead>
                                                <tr>
                                                    <th class="text-center" colspan="3">其他类：40090万元
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th class="text-center">前十银行</th>
                                                    <th class="text-center">总金额</th>
                                                    <th class="text-center">是否入围</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>工商长宁</td><td>1237</td><td class="ok-blue">是</td>
                                                </tr>
                                                <tr>
                                                    <td>光大隆昌</td><td>1062</td><td>否</td>
                                                </tr>
                                                <tr>
                                                    <td>建设松江</td><td>1062</td><td class="ok-blue">是</td>
                                                </tr>
                                                <tr>
                                                    <td>工商浦东</td><td>1062</td><td class="ok-blue">是</td>
                                                </tr>
                                                <tr>
                                                    <td>招商联洋</td><td>1062</td><td>否</td>
                                                </tr>
                                                <tr>
                                                    <td>农商普陀</td><td>1062</td><td class="ok-blue">是</td>
                                                </tr>
                                                <tr>
                                                    <td>工商虹口</td><td>1062</td><td class="ok-blue">是</td>
                                                </tr>
                                                <tr>
                                                    <td>农商杨思</td><td>1062</td><td>否</td>
                                                </tr>
                                                <tr>
                                                    <td>中信沪西</td><td>1062</td><td>否</td>
                                                </tr>
                                                <tr>
                                                    <td>上海浦西</td><td>1062</td><td>否</td>
                                                </tr>

                                            </tbody>
                                    </table>
                                    
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
<<<<<<< HEAD
        /**
         * 案件统计详情
         */
     	var ctx = $("#ctx").val();
        $(document).ready(function() {        	
        	// 初始化列表
        	var data = {};
        	data.queryId = "querySignBranchBankList";	
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
            	
            	var totalAmountArr = [];
            	var totalAmount = 0;
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	//1.
            	$.each(data.rows,function(i,item){
					xAxisData.push(item.FIN_ORG_NAME_YC.substring(0,2));
					totalAmountArr.push(item.CONTRACT_AMOUNT);
					span1Text = accAdd(Number(span1Text),Number(item.CONTRACT_AMOUNT));				
					totalNumArr.push(item.SIGN_NUM);
					span2Text = accAdd(Number(span2Text),Number(item.SIGN_NUM));
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
                }
				];
            	//3.
            	legend = ['总金额','总单量'];
            	//4.
            	datas = [totalAmountArr];
            	//5.
            	type = ["bar"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "11月签约贷款银行支行流向统计";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	//填充span数据 
            	$("#span1").text(span2Text);
            	$("#span2").text(span1Text);
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>
    </body>
</html>