<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>
            交易变更列表
        </title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/css/style.css" rel="stylesheet"/>
        <!-- Data Tables -->
        <link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

         <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/css/common/base.css" />
        <link rel="stylesheet" href="${ctx}/css/common/table.css" />
        <link rel="stylesheet" href="${ctx}/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" ">

        <!-- 提示 -->
        <link rel="stylesheet" href="${ctx}/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" />
    </head>
    <body>
        <div id="wrapper">
            <div id="page-wrapper" class="gray-bg">
                <!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            交易变更列表
                        </h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        案件编号
                                    </label>
                                    <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        所在组
                                    </label>
                                    <input class="teamcode input_type" placeholder="" value="" />
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        回访标记
                                    </label>
                                    <select name="visitRemark" id="visitRemark" class="select_control selwidth ">
                                        <option value="">异常</option>
                                        <option value="">正常</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        变更人
                                    </label>
                                    <input class="teamcode input_type" placeholder="" value="" />
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        变更时间
                                    </label>
                                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                                        <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${defaultDate }" placeholder="起始时间"> <span class="input-group-addon">到</span>
                                        <input  name="changeTimeEnd" name="changeTimeEnd" class="form-control data_style" type="text" value="${defaultDate }" placeholder="结束日期">
                                    </div>
                                </div>
                            </div>

                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        环节名称
                                    </label>
                                    <select name="partCode" id="partCode" class="select_control selwidth ">
                                        <option value="">请选择</option>
                                        <option value="">e+金融贷款</option>
                                    </select>
                                </div>

                                 <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证地址
                                    </label>
                                    <input class="teamcode input_type" style="width:435px;" placeholder="" value="" />
                                </div>
                            </div>

                            <div class="line">
                                <div class="add_btn" style="margin-left:126px;">
                                    <button type="button" class="btn btn-success">
                                        <i class="icon iconfont">&#xe635;</i>
                                        查询
                                    </button>
                                    <button type="button" class="btn btn-success">
                                        导出列表
                                    </button>
                                    <button type="reset" class="btn btn-grey">
                                        清空
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table_content">
                                <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                                    <thead>
                                        <tr>
                                            <th>
                                                <span>案件编码</span><a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>

                                            <th>
                                                产证地址
                                            </th>
                                            <th>
                                                客户姓名
                                            </th>
                                            <th>
                                                预计时间
                                            </th>
                                            <th>
                                                变更
                                            </th>
                                            <th>
                                                所在组
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
                                                <span class="red_color">异常</span><a href="#"><i class="icon iconfont" style="font-size: 20px;color:#808080"></i></a>
                                            </td>
                                            <td>
                                             <p>
                                                    <i class="sign_blue">
                                                        总监审批
                                                    </i>
                                                </p>
                                                <p class="big">
                                                    上海杨浦区平路街道（内环）鞍山八村29号060室
                                                </p>
                                            </td>
                                            <td>
                                                <p class="manager"><span>上家:</span><a href="#" class="mr5">金娇娇</a></p>
                                                <p class="manager"><span>上家:</span><a href="#" class="mr5">金娇娇</a></p>
                                            </td>
                                            <td>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">原预计</i>
                                                    2016-08-21
                                                </p>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">新预计</i>
                                                    2016-08-22
                                                </p>
                                            </td>
                                            <td>

                                                <p class="manager"><i class="sign_normal">原因</i><span class="demo-top" title="原因有许多，需要哪一条？来看看，原因有许多，需要哪一条？来看看">原因有许多，需要</span></p>
                                                <p class="smll_sign">
                                                    2016-08-22
                                                </p>
                                            </td>
                                            <td>
                                                 <p class="manager"><span>变更人:</span><a href="#" class="mr5">金娇娇</a></p>
                                                <p>虹口杨浦贵宾服务部A组</p>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-success" data-toggle="modal" data-target="#myModal">处理</button>
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
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
                    <div class="modal-dialog" style="width: 980px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                                案件回访
                            </div>
                            <div class="mt15">
                                    <div class="info_content">
                                        <div class="line">
                                            <p>
                                                <label>
                                                   案件编号
                                                </label>
                                                <span class="info_one">SH20160906001</span>
                                            </p>
                                            <p>
                                                <label>
                                                    案件地址
                                                </label>
                                                <span>上海市长宁区延安西路889号1弄202</span>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    变更人
                                                </label>
                                                <span class="info_one">李明<em class="ml5 blue-text">13468868686</em></span>
                                            </p>
                                            <p>
                                                <label>
                                                    环节
                                                </label>
                                                <span>过户</span>
                                            </p>
                                            <p>
                                                <label>
                                                    贵宾服务部
                                                </label>
                                                <span class="info">杨浦区贵宾服务部A组</span>
                                            </p>

                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    变更原因
                                                </label>
                                                <span>变更原因变更原因变更原因变更原因变更原因变更原因变更原因变更原因变更原因变更原因</span>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    上家
                                                </label>
                                                <span class="mr10">赵水法<em class="ml5 blue-text">1587515787</em></span>
                                                <span class="mr10">宋公明<em class="ml5 blue-text">1587515787</em></span>
                                                <span class="mr10">石阡<em class="ml5 blue-text">1587515787</em></span>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    下家
                                                </label>
                                                <span class="mr10">柘城<em class="ml5 blue-text">1385515787</em></span>
                                                <span class="mr10">郑爽<em class="ml5 blue-text">1385515787</em></span>
                                                <span class="mr10">李松<em class="ml5 blue-text">1385515787</em></span>

                                            </p>
                                        </div>
                                    </div>
                                </div>
                            <form method="get" class="form_list">
                                <div class="line">
                                    <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        回访标记
                                    </label>
                                    <div class="checkbox i-checks radio-inline sign sign_right">
                                        <label>
                                            <input type="radio" value="1" name="status" checked="">
                                                正常
                                        </label>
                                        <label>
                                            <input type="radio" value="2" name="status">
                                                异常
                                        </label>
                                    </div>
                                </div>

                                </div>
                                <div class="line clearfix">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left">
                                            回访跟进
                                        </label>
                                        <textarea style="width:590px;max-width:590px;height:100px;display:inline;margin-left:5px;" class="pull-left textarea" name="" id="" cols="30" rows="10"></textarea>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="add_btn text-center" style="margin:15px 126px;">
                                        <button type="button" class="btn btn-success">
                                            提交
                                        </button>
                                        <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                            关闭
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
                <!--*********************** HTML_main*********************** -->
            </div>
        </div>
<style>
    .add {
        border: 1px solid red;
    }
</style>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>

        <!-- 提示 -->
        <script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        <script src="${ctx}/js/poshytitle/src/jquery.poshytipuser.js"></script>


        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <script>
            $(document).ready(function () {

                $('.input-daterange').datepicker({
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
            });

        </script>

    </body>
</html>
