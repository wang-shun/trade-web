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
        <title>
            案件回访
        </title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
        <!-- Data Tables -->
        <link href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet"/>
         <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" />
        <link rel="stylesheet" href="${ctx}/css/satis/casevist.css" />
    </head>
    <body>
        <div id="wrapper">
                <!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                           案件回访
                        </h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        案件编号
                                    </label>
                                    <input class="teamcode input_type" placeholder="请输入" value="" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证地址
                                    </label>
                                    <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="" />
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        客服
                                    </label>
                                    <input class="teamcode input_type" placeholder="请输入" value="" />
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
                                </div>
                                <div class="form_content mr10">
                                    <label class="control-label sign_left_small">
                                        回访状态
                                    </label>
                                    <select class="select_control sign_right_one">
                                        <option value="">
                                            请选择
                                        </option>
                                        <option value="">
                                            已回访
                                        </option>
                                        <option value="">
                                            未分单
                                        </option>
                                        <option value="">
                                            过户打回
                                        </option>
                                        <option value="">
                                            签约打回
                                        </option>
                                        <option value="">
                                            签约回访
                                        </option>
                                        <option value="">
                                            过户回访
                                        </option>
                                    </select>
                                </div>
                                <div class="add_btn">
                                    <button type="button" class="btn btn-success mr5">
                                        <i class="icon iconfont">&#xe635;</i>
                                        查询
                                    </button>
                                    <a href="${ctx}/workflow/caseDistribute.html"><button type="button" class="btn btn-success mr5">
                                        派单
                                    </button></a>
                                    <button type="button" class="btn btn-grey mr5">
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
                                        <th><input type="checkbox" class="" name="split"></th>
                                        <th>回访状态
                                        </th>
                                        <th>
                                            <span>案件编码</span><a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                        </th>
                                        <th>
                                            当前任务
                                        </th>
                                        <th>
                                            签建时间
                                        </th>
                                        <th>
                                            上家
                                        </th>
                                        <th>
                                            下家
                                        </th>
                                        <th>
                                            客服
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <i class="color_visited grey_visited">
                                                    已回访
                                                </i>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">
                                                    ZY-AJ-201605-0952
                                                </a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>
                                                BKS-2-451341-2154
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                上海杨浦区平路街道（内环）鞍山八村29号0608室
                                            </p>
                                            <p class="tooltip-demo">
                                                <i class="salesman-icon">
                                                </i>
                                                <a class="salesman-info" href="javascript:;" title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体" data-toggle="tooltip" data-placement="top" >
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                张春伟
                                            </p>
                                            <p class="big">
                                                张伟伟
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                陈一财
                                            </p>
                                            <p class="big">
                                                陈一财
                                            </p>
                                        </td>
                                        <td class="text-center">
                                            赵信
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <a href="${ctx}/satis/guohuReturn">
                                                <i class="color_visited red_visited">
                                                    过户打回
                                                </i>
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">ZY-AJ-201605-0958</a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>BKS-2-451341-2150
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">上海杨浦区平路街道（内环）鞍山八村29号0608室</p>
                                            <p>
                                                <i class="salesman-icon"></i>
                                                <a class="salesman-info" href="javascript:;" title="hahahah">
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>

                                        </td>
                                        <td>
                                            <p class="big">张春伟</p>
                                            <p class="big">张伟伟</p>
                                        </td>
                                        <td>
                                            <p class="big">陈一财</p>
                                            <p class="big">陈一财</p>
                                        </td>
                                        <td class="text-center">
                                            赵信
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <a href="${ctx}/satis/signReturn">
                                                <i class="color_visited red_visited">
                                                    签约打回
                                                </i>
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">ZY-AJ-201605-0958</a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>BKS-2-451341-2150
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">上海杨浦区平路街道（内环）鞍山八村29号0608室</p>
                                            <p>
                                                <i class="salesman-icon"></i>
                                                <a class="salesman-info" href="javascript:;" title="hahahah">
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>

                                        </td>
                                        <td>
                                            <p class="big">张春伟</p>
                                            <p class="big">张伟伟</p>
                                        </td>
                                        <td>
                                            <p class="big">陈一财</p>
                                            <p class="big">陈一财</p>
                                        </td>
                                        <td class="text-center">
                                            赵信
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <a href="${ctx}/satis/guohuDetail">
                                                <i class="color_visited blue_visited">
                                                    过户回访
                                                </i>
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">
                                                    ZY-AJ-201605-0952
                                                </a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>
                                                BKS-2-451341-2154
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                上海杨浦区平路街道（内环）鞍山八村29号0608室
                                            </p>
                                            <p class="tooltip-demo">
                                                <i class="salesman-icon">
                                                </i>
                                                <a class="salesman-info" href="javascript:;" title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体" data-toggle="tooltip" data-placement="top" >
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>

                                        </td>
                                        <td>
                                            <p class="big">
                                                张春伟
                                            </p>
                                            <p class="big">
                                                张伟伟
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                陈一财
                                            </p>
                                            <p class="big">
                                                陈一财
                                            </p>
                                        </td>
                                        <td class="text-center">
                                            赵信
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <a href="${ctx}/satis/signDetail">
                                                <i class="color_visited blue_visited">
                                                    签约回访
                                                </i>
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">ZY-AJ-201605-0958</a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>BKS-2-451341-2150
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">上海杨浦区平路街道（内环）鞍山八村29号0608室</p>
                                            <p class="tooltip-demo">
                                                <i class="salesman-icon">
                                                </i>
                                                <a class="salesman-info" href="javascript:;" title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体" data-toggle="tooltip" data-placement="top" >
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>

                                        </td>
                                        <td>
                                            <p class="big">张春伟</p>
                                            <p class="big">张伟伟</p>
                                        </td>
                                        <td>
                                            <p class="big">陈一财</p>
                                            <p class="big">陈一财</p>
                                        </td>
                                        <td class="text-center">
                                            赵信
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input type="checkbox" class="" name="split"></td>
                                        <td>
                                            <p>
                                                <i class="color_visited red_visited">
                                                    未分单
                                                </i>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">ZY-AJ-201605-0958</a>
                                            </p>
                                            <p>
                                                <i class="tag_sign">c</i>BKS-2-451341-2150
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">上海杨浦区平路街道（内环）鞍山八村29号0608室</p>
                                            <p>
                                                <i class="salesman-icon"></i>
                                                <a class="salesman-info" href="javascript:;" title="hahahah">
                                                    张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">签</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">过</i>
                                                2016-05-30
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_grey">完</i>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">张春伟</p>
                                            <p class="big">张伟伟</p>
                                        </td>
                                        <td>
                                            <p class="big">陈一财</p>
                                            <p class="big">陈一财</p>
                                        </td>
                                        <td class="text-center">
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
    </body>
</html>
