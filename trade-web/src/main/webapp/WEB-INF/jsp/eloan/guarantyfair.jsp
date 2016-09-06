<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>强制公正</title>

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
           <!-- main Start -->
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
                    </div>

                    <div class="ibox-content ibox-space">
                        <form method="get" class="form_list">
                            <div class="modal_title">
                                强制公正信息登记
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押合同编号
                                    </label>
                                    <input type="text" placeholder="" class="select_control sign_right_one">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押登记时间
                                    </label>
                                    <input type="text" placeholder="" class="select_control sign_right_one">
                                    <span class="date_icon">
                                       时间
                                    </span>
                                </div>
                                <div class="form_content space">
                                    <div class="add_btn">
                                        <button type="button" class="btn btn-success">
                                        <i class="icon iconfont">&#xe635;</i>
                                            查询
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <button type="button" class="close close_blue" style="display:none;" data-dismiss="modal">
                                <i class="iconfont icon_rong">&#xe60a;
                                </i>
                            </button>
                            <table class="table table_blue ">
                                    <thead>
                                        <tr>
                                            <th>
                                                公证书
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <ul class="filelist">
                                                    <li id="WU_FILE_0">
                                                        <p class="imgWrap">
                                                            <img src="${ctx}/static/trans/img/uplody01.png">
                                                        </p>
                                                        <div class="file-panel" >
                                                            <span class="file-name">公证书1</span>
                                                            <span class="cancel pull-right">删除</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <p class="imgWrap fileposition">
                                                            <img src="${ctx}/static/trans/img/uplody02.png">
                                                            <input type="file" name="file" class="webupload_file" multiple="multiple" accept="image/*">
                                                        </p>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                        </form>
                        <div class="status_btn text-center">
                            <button class="btn btn-success btn-space">保存</button>
                            <button class="btn btn-default" data-dismiss="modal" data-toggle="modal" data-target="#myModal">关闭</button>
                        </div>
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
<content tag="local_script"> 
    <!-- Mainly scripts -->
<!--<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> -->

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/trans/js/plugins/poshytip/jquery.poshytip.js"></script>

    <!-- 开关按钮js -->
    <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
    <script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script>
</content>
</body>

</html>