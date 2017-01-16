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
                        <p class="seize-icon">
                            <img src="${ctx }/css/images/zhongyuan.png" width="250" alt="" />
                        </p>
                    </div>
                </div>
                <input type="hidden" value="${ctx}" id="ctx">
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
<script src="${ctx}/static/trans/js/dataEcharts/FirstMortAmountForFinance.js"></script>
<script>
    $(function() {
        reloadGrid(window.ECHART_LOAD_DATA.getCurrentYear(),window.ECHART_LOAD_DATA.getCurrentMonth());

    })
    function reloadGrid(year,month){
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('plotCont1'));
        // 使用刚指定的配置项和数据显示图表。
        window.ECHART_LOAD_DATA.init(year,month);
        // 指定图表的配置项和数据
        window.ECHART_LOAD_DATA.buildBarChart(myChart1);//生成柱状报表

    }

</script>
</body>
</html>
