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
                                </thead>
                                <tr>
                                    <td class="tabletitle">
                                        <span class="colorBar" style="background-color:#52bdbd;">
                                        </span>
                                        11月总单量
                                    </td>
                                    <td>
                                        206
                                    </td>
                                    <td>
                                        135
                                    </td>
                                    <td>
                                        136
                                    </td>
                                    <td>
                                        162
                                    </td>
                                    <td>
                                        130
                                    </td>
                                    <td>
                                        143
                                    </td>
                                    <td>
                                        208
                                    </td>
                                    <td>
                                        504
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabletitle">
                                        <span class="colorBar" style="background-color:#ff9696;">
                                        </span>
                                        10月总单量
                                    </td>
                                    <td>
                                        260
                                    </td>
                                    <td>
                                        183
                                    </td>
                                    <td>
                                        198
                                    </td>
                                    <td>
                                        137
                                    </td>
                                    <td>
                                        99
                                    </td>
                                    <td>
                                        193
                                    </td>
                                    <td>
                                        207
                                    </td>
                                    <td>
                                        559
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
