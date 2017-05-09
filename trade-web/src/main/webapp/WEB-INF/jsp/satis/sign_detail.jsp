<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>回访
    </title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.css" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/caseDetail.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/details.css" >
</head>

<body class="pace-done"><div class="pace  pace-inactive"><div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
  <div class="pace-progress-inner"></div>
</div>
<div class="pace-activity"></div></div>
    <div id="wrapper">
    <!-- 右侧页面主体内容 -->
    <input type="hidden" id="ctx" value="http://trade.centaline.com:8083/trade-web">
    <input type="hidden" id="ctm" value="CBC-1-201608-0014">
    <input type="hidden" id="Lamp1" value="-1">
    <input type="hidden" id="Lamp2" value="1">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="activityFlag" value="30003003">
    <input type="hidden" id="caseCode" value="ZY-SH-201608-0095">
    <input type="hidden" id="instCode" value="">
    <input type="hidden" id="srvCodes" value="30004009,30004015">
    <input type="hidden" id="processDefinitionId" value="">
    <div id="salesLoading" style="display: none">
        <div id="loading-center">
            <div id="loading-center-absolute">
                <div class="object" id="object_one"></div>
                <div class="object" id="object_two" style="left: 20px;"></div>
                <div class="object" id="object_three" style="left: 40px;"></div>
                <div class="object" id="object_four" style="left: 60px;"></div>
                <!--<div class="object" id="object_five" style="left:80px;"></div>-->
            </div>
        </div>
        <div id="loading-center-absolute-second">系统正在处理，请稍后...</div>
    </div>
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
                <li class="menuItem"><a href="#"> 服务流程 </a></li>
                <li class="menuItem"><a href="#"> 相关信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
                <div class="top12 panel" id="basicInfo">
                    <div class="sign sign-red">
                        结案
                    </div>
                    <div class="sign sign-blue">
                        已过户
                    </div>
                    <div class="sign sign-yellow">
                        税费卡
                    </div>
                    <div class="panel-body">
                        <div class="ibox-content-head">
                            <h2 class="title">
                                案件基本信息
                            </h2>
                            <small class="pull-right">誉萃编号：ZY-SH-201608-0095｜中原编号：CBC-1-201608-0014</small>
                        </div>
                        <div id="infoDiv infos" class="row">
                            <div class="ibox white_bg">
                                <div class="info_box info_box_one col-sm-4 ">
                                    <span>物业信息</span>
                                    <div class="ibox-conn ibox-text">
                                        <dl class="dl-horizontal">
                                            <dt>CTM地址</dt>
                                            <dd>上海浦东新区洋泾片区羽山路100弄5号0301室</dd>
                                            <dt>产证地址</dt>
                                            <dd>上海浦东新区洋泾片区羽山路100弄5号0301室</dd>
                                            <dt>层高</dt>
                                            <dd>3／18</dd>
                                            <dt>产证面积</dt>
                                            <dd>103.72平方</dd>
                                            <dt>房屋类型</dt>
                                            <dd>

                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                                <div class="info_box info_box_two col-sm-5">
                                    <span>买卖双方</span>
                                    <div class="ibox-conn else_conn">
                                        <dl class="dl-horizontal col-sm-6">
                                            <dt>上家姓名</dt>
                                            <dd>
                                                <div id="seller"><a data-toggle="popover" data-placement="right" data-content="" data-original-title="" title="">赵晓明</a>&nbsp;</div>
                                            </dd>
                                        </dl>
                                        <dl class="dl-horizontal col-sm-6">
                                            <dt>下家姓名</dt>
                                            <dd>
                                                <div id="buyer"><a data-toggle="popover" data-placement="right" data-content="13921981251" data-original-title="" title="">贾先生</a>&nbsp;</div>
                                            </dd>
                                        </dl>
                                    </div>
                                    <span>经纪人信息</span>
                                    <div class="ibox-conn else_conn_two ">
                                        <dl class="dl-horizontal">
                                            <dt>姓名</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13701867479" data-original-title="" title="">
                                                    阮俊</a>
                                            </dd>
                                            <dt>所属分行</dt>
                                            <dd>ACCBCA.南丹分行一组全体</dd>
                                            <dt>直管经理</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13817585505" data-original-title="" title="">
                                                    邵雷 </a>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                                <div class="info_box info_box_three col-sm-3">
                                    <span>经办人信息</span>
                                    <div class="ibox-conn ibox-text">
                                        <dl class="dl-horizontal">
                                            <dt>交易顾问</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13918843797" data-original-title="" title="">
                                                    陶晨 </a>
                                            </dd>
                                                    <dt>合作顾问</dt>
                                                    <dd>
                                                        <a data-toggle="popover" data-placement="right" data-content="13916540926" data-original-title="" title="">
                                                            顾新峰 </a>
                                                    </dd>

                                                    <dt>合作顾问</dt>
                                                    <dd>
                                                        <a data-toggle="popover" data-placement="right" data-content="13817789952" data-original-title="" title="">
                                                            许庆祺 </a>
                                                    </dd>


                                            <dt>助理</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="" data-original-title="" title="">
                                                     </a>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            <div class="row wrapper white-bg new-heading ">
            <div class="pl10">
            <h2 class="newtitle-big">
                签约回访
            </h2>
            </div>
        </div>

    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div>
            <h2 class="newtitle title-mark">上家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">赵晓明—189898989898</span>
                            <span >赵晓明2—189898989898</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                            <select class="select_control yuanwid">
                                <option value="">是</option>
                                <option value="">否</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label> 
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label> 
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪还贷评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪同还贷意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">下家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">贾先生—13555555555</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">经纪人回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">阮俊—13666666666</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <div class="title title-mark">
                <strong>附件信息</strong>
            </div>
            <div class="view-content">
            </div>
        </div>
        <div>
            <div class="title title-mark mb15">
            <strong>案件跟进</strong>
            </div>
            <div class="view-content">
                <div class="view-box">
                    <p class="text-center">
                        <img src="${ctx}/static/trans/img/false.png" height="100" alt="" />
                    </p>
                </div>
                <div class="form_list clearfix">
                   <input class="input_type pull-left" placeholder="" value="" style="width:93%;">
                   <button class="btn btn_more pull-right" style="width:60px;">跟进</button>
                </div>
            </div>
        </div>
        
                        <div class="form-btn">
                            <div class="text-center">
                                <button  class="btn btn-success btn-space">保存</button>
                                <button class="btn btn-success btn-space" data-toggle="modal" data-target="#myModal">回访打回</button>
                                <button class="btn btn-success btn-space">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--********** 弹窗页面 **********-->
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width:760px;">
                        <div class="animated fadeIn popup-box" style="background-color: #fff;padding:30px 30px 60px 30px;">
                            <div class="modal_title">
                                回访打回
                            </div>
                            <textarea name="" id="" class="textarearoom mt10" style="width:100%;max-width: 760px;margin-left:0;max-height: 150px;height: 150px;" >电话多次打不通，提示已关机！需要经纪人配合。。。
                            </textarea>
                            <div class="add_btn text-center mt20">
                                <button type="button" class="btn btn-success">
                                    打回
                                </button>
                                <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                    关闭
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!--********** 弹窗页面 **********-->
    </body>
    </html>