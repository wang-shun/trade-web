<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <!-- 图标 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" rel="stylesheet">

    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <!-- bootstrap-datapicker3 -->
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
    <!-- owner css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/award/baseAward.css" />
</head>

<body>

<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <p class="month">
            <button type="button" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow">&#xe60d;</i></button>
                <span>2014/01月</span>
            <button class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow">&#xe614;</i></button>
            <span class="explain">此日期为计件奖金的生成日期。如需查看某月过户的案件计件奖金，请按过户日期搜索</span>
        </p>
        <form method="get" class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left">
                            案件编号
                        </label>
                        <input type="text" class="select_control sign_right_one" />
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small select_style mend_select">
                            过户时间
                        </label>
                        <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
                            <input name="" class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
                            <input name="" class="form-control data_style" type="text" value="" placeholder="结束日期">
                        </div>
                    </div>
                </div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left">
                            产证地址
                        </label>
                        <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
                    </div>
                    <div class="form_content space">
                        <div class="add_btn">
                            <button type="button" class="btn btn-success">
                                <i class="icon iconfont"></i>
                                查询
                            </button>
                        </div>
                    </div>
                </div>

            </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="notes">
                <p class="pull-left">我的基金案件奖金</p>
                <p class="pull-right">
                    交易单数：9，环节总数：31.00，交易单加权：9.00
                </p>
            </div>
            <div class="table_content">
                <table class="table table_blue table-hover table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>
                                <span>案件编码</span>
                            </th>
                            <th>
                                产证地址
                            </th>
                            <th>
                                过户时间
                            </th>
                            <th>
                                生产时间
                            </th>
                            <th>
                                基础奖金
                            </th>
                            <th>
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">收起</a>
                            </td>
                        </tr>
                        <tr class="spread_line">
                            <td colspan="7" class="spread_td">
                                <table class="table spread_tab table-bordered">
                                    <thead>
                                        <tr>
                                            <th>服务</th>
                                            <th>基础奖金</th>
                                            <th>环节占比</th>
                                            <th>满意度</th>
                                            <th>满意度占比</th>
                                            <th>金融单数</th>
                                            <th>贷款流失率</th>
                                            <th>绩效系统数</th>
                                            <th>最终奖金</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>总监</td>
                                            <td>40</td>
                                            <td>3/3</td>
                                            <td>22</td>
                                            <td>26/100</td>
                                            <td>22</td>
                                            <td>22.5%</td>
                                            <td>22</td>
                                            <td>22000</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="big">
                                    <a href="javascript:;">
                                        ZY-AJ-201605-0952
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    上海杨浦区平路街道（内环）鞍山八村29号0608室
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    2016-05-30
                                </p>
                            </td>
                            <td>
                                <p class="big">
                                    40
                                </p>
                            </td>
                            <td class="center">
                                <a href="" class="spread">展开</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="text-center page_box">
                    <span id="currentTotalPage">
                        <strong>
                            1/8
                        </strong>
                    </span>
                    <span class="ml15">
                        共
                        <strong id="totalP">
                            144
                        </strong>
                        条
                    </span>
                    &nbsp;
                    <div id="pageBar" class="pagination text-center">
                        <ul class="pagination">
                            <li class="first disabled">
                                <a href="#"><i class="fa fa-step-backward"></i></a>
                            </li>
                            <li class="prev disabled">
                                <a href="#"><i class="fa fa-chevron-left"></i></a>
                            </li>
                            <li class="page active">
                                <a href="#">
                                    1
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    2
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    3
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    4
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    5
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    6
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    7
                                </a>
                            </li>
                            <li class="page">
                                <a href="#">
                                    8
                                </a>
                            </li>
                            <li class="next">
                                <a href="#"><i class="fa fa-chevron-right"></i></a>
                            </li>
                            <li class="last">
                                <a href="#"><i class="fa fa-step-forward"></i></a>
                            </li>
                        </ul>
                        <div class="pagergoto">
                            <span class="go_text">
                                跳转至
                            </span>
                            <span>
                            <input type="text" class="go_input" value="">
                            <input type="button" class="go_btn" value="GO"></span>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--*********************** HTML_main*********************** -->
        </div>
    </div>
<content tag="local_script">    
    <!-- Mainly scripts -->
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>

    <script>
        $(document).ready(function () {

            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true
            });


        });
    </script>
</content>
</body>
</html>
