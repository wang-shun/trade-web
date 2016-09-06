<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>押卡</title>

    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <link rel="stylesheet" href="${ctx}/static/css/animate.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">

    <link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
    <link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
    <!-- index_css  -->

        <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">

    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
</head>

<body>
<div id="wrapper">
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
                    </div>

                    <div class="ibox-content ibox-space">
                        <form method="get" class="form_list">
                            <div class="modal_title">
                                押卡信息登记
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        押卡对象
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">房东A</option>
                                        <option value="">房东B</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        押卡时间
                                    </label>
                                    <input type="text" placeholder="" class="select_control sign_right_one">
                                    <span class="date_icon">

                                    </span>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        银行卡密码
                                    </label>
                                    <input name="status" type="checkbox" data-size="small" checked="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label mr10" style="margin-left: 51px;">
                                            修改后到账手机号
                                    </label>
                                    <input class="sign_right_one input_type" value="" placeholder="请输入">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        卡证管理员
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">小林</option>
                                        <option value="">小黄</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line" id="addTr">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品类别
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">身份证</option>
                                        <option value="">银行卡</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品名称
                                    </label>
                                    <input type="text" placeholder="" class="select_control teamcode">
                                </div>
                                <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a>
                            </div>

                        </form>
                        <div class="status_btn text-center mt15">
                            <button class="btn btn-success btn-space">提交</button>
                            <button class="btn btn-default" data-dismiss="modal" data-toggle="modal" data-target="#myModal">关闭</button>
                        </div>
                        <button type="button" class="close close_blue" style="display:none;" data-dismiss="modal">
                            <i class="iconfont icon_rong">&#xe60a;
                            </i>
                        </button>
                    </div>

                </div>
            </div>
            <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-body" style="background:#fff;">
                            <p class="text-center" style="font-size: 20px;">点击删除，将丢失本次填写信息！选择保存按钮可保存本次填写信息！</p>
                        </div>
                        <div class="modal-footer" style="text-align:center;">
                            <button type="button" class="btn btn-save">确认保存</button>
                            <button type="button" class="btn btn-success">我要删除</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>






            <!-- main End -->
        </div>
    </div>
            
	<content tag="local_script"> 
	   <script src="${ctx}/js/inspinia.js"></script> 
	   <script src="${ctx}/js/plugins/pace/pace.min.js"></script> 
	   <!-- 开关按钮js -->
       <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
       <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
       <script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script>
	</content>
</body>

</html>