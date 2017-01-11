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
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css" ">
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
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
                                <thead>
                                    <td class="tabletitle"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </thead>
                                <tr>
                                    <td class="tabletitle">
                                        <span class="colorBar" style="background-color:#52bdbd;">
                                        </span>
                                        蒸发量
                                    </td>
                                    <td>
                                        1月
                                    </td>
                                    <td>
                                        2月
                                    </td>
                                    <td>
                                        3月
                                    </td>
                                    <td>
                                        4月
                                    </td>
                                    <td>
                                        5月
                                    </td>
                                    <td>
                                        6月
                                    </td>
                                    <td>
                                        7月
                                    </td>
                                    <td>
                                        8月
                                    </td>
                                    <td>
                                        9月
                                    </td>
                                    <td>
                                        10月
                                    </td>
                                    <td>
                                        11月
                                    </td>
                                    <td>
                                        12月
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabletitle">
                                        <span class="colorBar" style="background-color:#ff9696;">
                                        </span>
                                        降水量
                                    </td>
                                    <td>
                                        1月
                                    </td>
                                    <td>
                                        2月
                                    </td>
                                    <td>
                                        3月
                                    </td>
                                    <td>
                                        4月
                                    </td>
                                    <td>
                                        5月
                                    </td>
                                    <td>
                                        6月
                                    </td>
                                    <td>
                                        7月
                                    </td>
                                    <td>
                                        8月
                                    </td>
                                    <td>
                                        9月
                                    </td>
                                    <td>
                                        10月
                                    </td>
                                    <td>
                                        11月
                                    </td>
                                    <td>
                                        12月
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabletitle">
                                        <i class="iconfont al-iconbt ml5 al-blue">&#xe687;</i>
                                        <p class="al-text">温度</p>
                                    </td>
                                    <td>
                                        1月
                                    </td>
                                    <td>
                                        2月
                                    </td>
                                    <td>
                                        3月
                                    </td>
                                    <td>
                                        4月
                                    </td>
                                    <td>
                                        5月
                                    </td>
                                    <td>
                                        6月
                                    </td>
                                    <td>
                                        7月
                                    </td>
                                    <td>
                                        8月
                                    </td>
                                    <td>
                                        9月
                                    </td>
                                    <td>
                                        10月
                                    </td>
                                    <td>
                                        11月
                                    </td>
                                    <td>
                                        12月
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabletitle">
                                        <i class="iconfont al-iconbt ml5 al-yellow">&#xe687;</i>
                                        <p class="al-text">温差</p>
                                    </td>
                                    <td>
                                        1月
                                    </td>
                                    <td>
                                        2月
                                    </td>
                                    <td>
                                        3月
                                    </td>
                                    <td>
                                        4月
                                    </td>
                                    <td>
                                        5月
                                    </td>
                                    <td>
                                        6月
                                    </td>
                                    <td>
                                        7月
                                    </td>
                                    <td>
                                        8月
                                    </td>
                                    <td>
                                        9月
                                    </td>
                                    <td>
                                        10月
                                    </td>
                                    <td>
                                        11月
                                    </td>
                                    <td>
                                        12月
                                    </td>
                                </tr>
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
        <script src="${ctx }/js/jquery-2.1.1.js"></script>
        <script src="${ctx }/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="${ctx }/static_res/js/echarts.min.js"></script>
        <script src="${ctx }/js/eachartdata/echartsdata.js"></script>
        <script>
            echartData("plotCont1");
            echartSet("plotCont2");
        </script>

    </body>
</html>
