<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>e+放款流水清单</title>
        <link href="http://trade.centaline.com:8083/trade-web/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="http://trade.centaline.com:8083/trade-web/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="http://trade.centaline.com:8083/trade-web/static/css/animate.css" rel="stylesheet"/>
        <link href="http://trade.centaline.com:8083/trade-web/static/css/style.css" rel="stylesheet"/>

        <!-- index_css -->
        <link rel="stylesheet" href="http://trade.centaline.com:8083/trade-web/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="http://trade.centaline.com:8083/trade-web/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="http://trade.centaline.com:8083/trade-web/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="http://trade.centaline.com:8083/trade-web/static/iconfont/iconfont.css" ">
        <link rel="stylesheet" href="http://trade.centaline.com:8083/trade-web/static/trans/css/eachartdata/eachartdata.css">
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
                            <div class="plot-rightone relative merge-box">
                                <p class="seize-icon">
                                    <img src="../static/trans/img/zhyuan-big.jpg" width="250" alt="" />
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="http://trade.centaline.com:8083/trade-web/static/js/jquery-2.1.1.js"></script>
        <script src="http://trade.centaline.com:8083/trade-web/static/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="http://trade.centaline.com:8083/trade-web/static/js/echarts.min.js"></script>
        <script src="http://trade.centaline.com:8083/trade-web/static/trans/js/eachartdata/echartsdata.js"></script>
        <script>
            echartData("plotCont1");
        </script>
    </body>
</html>
