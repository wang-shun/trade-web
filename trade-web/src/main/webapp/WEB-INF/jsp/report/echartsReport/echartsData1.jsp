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
                    <div class="plot-rightone">
                        <div id="plotCont2"  style="width:100%;height:300px; ">
                        </div>
                    </div>
                    <div class="plot-righttwo mt10 relative">
                        <div class="total-data">
                            <h3 id="list_title"></h3>
                            <ul class="total-list" id="list_chart">

                            </ul>
                        </div>
                        <p class="zhyu-icon"><img src="${ctx }/css/images/zhongyuan.png" alt="" /></p>
                    </div>
                    <input type="hidden" value="${ctx}" id="ctx">

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
<script src="${ctx }/static_res/js/echarts-all.js"></script>
<script src="${ctx }/js/eachartdata/echartCommon.js"></script>
<script src="${ctx }/js/eachartdata/select_month.js"></script>
<script src="${ctx}/static/trans/js/dataEcharts/FirstGuohuForm.js"></script>
<script>

    function reloadGrid(){
        var year = window.parent.yearDisplay;
        var month = parseInt(window.parent.monthDisplay)+1;
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('plotCont1'));
        var myChart2 = echarts.init(document.getElementById('plotCont2'));
        window.ECHART_LOAD_DATA.init(year,month);
        window.ECHART_LOAD_DATA.getDistrict();//初始化区域
        window.ECHART_LOAD_DATA.buildBarChart(myChart1);//生成柱状报表
        window.ECHART_LOAD_DATA.buildPieChart(myChart2);//生成饼图报表
        window.ECHART_LOAD_DATA.buildListChart('list_chart');//生成饼图报表
    }
</script>

</body>
</html>
