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
                    <div style="height: 23px;line-height: 23px;"><i class="icon iconfont icon40 yellow martop20" style="font-size: 30px;float: left;"></i>备注:
                        <span>收单率=收单金额/商贷总额</span>
                    </div>
                    <div style="height: 23px;line-height: 23px;">&nbsp;&nbsp;&nbsp;&nbsp;
                        <span style="margin-left: 42px;">数据来源:过户审批通过中含商贷的案件（包含公司办理的商贷和客户自办的贷款，不包括一次性，纯公积金案件）</span>
                    </div>
                </div>
                  <div class="pull-left">
                    <div class="plot-rightone">
                        <div class="total-data">
                            <h3 id="list_title"></h3>
                            <ul class="total-list" id="list_chart">
                                <li style="width:300px"><i class="iconfont mr5 al-yellow al-icon-22"></i>商贷总额<span id="list_com_mort">0</span>万元</li>
                                <li style="width:300px"><i class="iconfont mr5 al-grey al-icon-22"></i>收单金额<span id="list_shou_mort">0</span>万元</li>
                                <li style="width:300px"><i class="iconfont mr5 al-maize  al-icon-22"></i>收单率<span id="list_shou_lv">0</span>%</li>
                            </ul>
                        </div>
                    </div>
                    <div class="plot-righttwo mt10 relative">
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
<script src="${ctx }/static/js/echarts-all.js"></script>
<script src="${ctx }/static/trans/js/common/echartCommon.js"></script>
<script src="${ctx}/static/trans/js/dataEcharts/FirstMortAmountForFinance.js"></script>
<script>
   /*  $(function() {
        window.ECHART_D4_.turnDate();
        reloadGrid(window.ECHART_D4_.getCurrentYear(),window.ECHART_D4_.getCurrentMonth());
    }) */
    function reloadGrid(year,month){
        // 基于准备好的dom，初始化echarts实例
    	var year = window.parent.yearDisplay;
        var month= parseInt(window.parent.monthDisplay)+1;
        // 使用刚指定的配置项和数据显示图表。
        window.ECHART_D4_.init(year,month);
        // 指定图表的配置项和数据
        window.ECHART_D4_.buildBarChart();//生成柱状报表

    }

</script>
</body>
</html>
