<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>允许结佣</title>
    <!-- 上传相关 -->
    <link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
    <link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
    <link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet">
    <!-- 展示相关 -->
    <link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />" rel="stylesheet">
    <link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />" rel="stylesheet">
    <link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />" rel="stylesheet">
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <!-- 备件相关结束 -->
    <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

    <!-- jdGrid相关 -->
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

    <link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
    <!-- 新调整页面样式 -->
    <link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
    <link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
    <link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
    <link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
    <link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
    <link href="<c:url value='/css/common/table.css' />" rel="stylesheet">

    <!-- 图片查看CSS -->
    <link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />


    <script type="text/javascript">
        var ctx = "${ctx}";
        /**记录附件div变化，%2=0时执行自动上传并清零*/
        var index = 0;
        var taskitem = "${taskitem}";
        var caseCode = "${caseCode}";
        var processInstanceId = "${processInstanceId}";
        var approveType = "${approveType }";
        if ("${idList}" != "") {
            var idList = eval("(" + "${idList}" + ")");
        } else {
            var idList = [];
        }
    </script>
    <style type="text/css">
        .radio.radio-inline>label {
            margin-left: 10px;
        }

        .radio.radio-inline>input {
            margin-left: 10px;
        }

        .checkbox.checkbox-inline>div {
            margin-left: 25px;
        }

        .checkbox.checkbox-inline>input {
            margin-left: 20px;
        }

        #notApproves label {
            font-weight: normal;
            margin: 0;
        }

        #notApproves {
            padding: 20px 0px;
        }

        #notApproves .col-sm-4 {
            margin: 5px 0px;
        }

        #notApproves input[type=checkbox], input[type=radio] {
            margin: 0px 0 0;
            line-height: normal;
        }

        .form_sign .sign {
            margin-top: 3px;
            margin-bottom: 3px;
        }
        .other_reason_title{
            float: left;
            width: 76px;
            margin-left: 24px;
            text-align: right;
            color: #808080;
            font-size: 14px;
        }
        .other_reason{
            float: left;
            padding-left: 15px;
            color: #333;
            width: 725px;
        }
    </style>
    <content tag="pagetitle">允许结佣</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<!-- 服务流程 -->
<div class="row wrapper white-bg new-heading " id="serviceFlow">
    <div class="pl10">
        <h2 class="newtitle-big">
            过户完结确认
        </h2>
        <div class="mt20">
            <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                <i class="iconfont icon">&#xe600;</i> 在途单列表
            </button>
            <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                <i class="iconfont icon">&#xe63f;</i> 案件视图
            </button>
        </div>
    </div>
</div>

<div class="ibox-content border-bottom clearfix space_box noborder">
	<form method="get" class="form-horizontal" id="KnotCommissionForm">
	<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
			<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
    <div class="clearfix" id="aboutInfo">
        <h2 class="newtitle title-mark">佣金折返评估费</h2>
        <div class="text_list">
            <ul class="textinfo">
                <li>
                    <em>应收评估费：</em><span class="yuanwid">2000元</span>
                </li>
                <li>
                    <em>应收中介费：</em>
                            <span class="yuanwid">
                           30000元
                 		    </span>
                </li>
                <li>
                    <em>调整金额：</em>
                            <span class="yuanwid">
                            500元
                 		    </span>
                </li>
                 <li>
                    <em>实收评估费：</em>
                            <span class="yuanwid">
                            1000元
                 		    </span>
                </li>
                
                 <li>
                    <em>实收中介费：</em>
                            <span class="yuanwid">
                            25000元
                 		    </span>
                </li>
                </ul>
                <ul class="textinfo">
                 <li>
                    评估费折扣比例：
                            <span class="yuanwid">
                            30%
                 		    </span>
                </li>
                 <li>
                    <em>中介折扣比例：</em>
                            <span class="yuanwid">
                            0.5%
                 		    </span>
                </li>
            	</ul>
            	   <ul class="textinfo">
                 <li>
                    <em>审批状态：</em>
                            <span class="yuanwid">
                            	已审批完成
                 		    </span>
                </li>
                 <li>
                    <em>审批人员：</em>
                            <span class="yuanwid">
                           	 王霞
                 		    </span>
                </li>
                      <li>
                    <em>审批时间：</em>
                            <span class="yuanwid">
                            2017-09-10
                 		    </span>
                </li>
            	</ul>
        </div>
    </div>
    <div class="clearfix">
        <h2 class="newtitle title-mark">自办评估审批</h2>
        <div class="text_list">
            <ul class="textinfo">
                <li>
                    自办评估审批原因：<span class="infolong pull-right">评估公司评值不够</span>
                </li>
                <li>
                    自办评估公司名称：
                            <span class="infolong pull-right">
                           		爱华评估公司
                 		    </span>
                </li>
                </ul>
                <ul class="textinfo">
                <li>
                    <em>审批状态：</em>
                            <span class="yuanwid">
                           	审批中
                 		    </span>
                </li>
                
                 <li>
                    <em>审批人员：</em>
                            <span class="yuanwid">
                            	王明
                 		    </span>
                </li>
                
                 <li>
                    <em>审批时间：</em>
                            <span class="yuanwid">
                            2017-9-10
                 		    </span>
                </li>
            </ul>
        </div>
        <div class="clearfix">
        <h2 class="newtitle title-mark">自办贷款的审批</h2>
        <div class="text_list">
            <ul class="textinfo">
                <li>
                    自办贷款的原因：<span class="infolong pull-right">银行贷款不够</span>
                </li>
                </ul>
                <ul class="textinfo">
                <li>
                    <em>自办银行名称：</em>
                            <span class="yuanwid">
                           		光大银行
                 		    </span>
                </li>
                <li>
                    贷款(流失)金额：
                            <span class="yuanwid">
                           	50000万
                 		    </span>
                </li>
                </ul>
                <ul class="textinfo">
                 <li>
                    <em>审批状态：</em>
                            <span class="yuanwid">
                            	已审批完成
                 		    </span>
                </li>
                
                 <li>
                    <em>审批人员：</em>
                            <span class="yuanwid">
                            	王利
                 		    </span>
                </li>
                   <li>
                    <em>审批时间：</em>
                            <span class="yuanwid">
                            2017-09-10
                 		    </span>
                </li>
            </ul>
        </div>
    </div>
     </form>
   <div class="form-btn">
			<div class="text-center">
				<!-- <a href="#" class="btn btn-success btn-space" onclick="save(false)">保存</a> -->
				<a href="#" class="btn btn-success btn-space" onclick="submit()" readOnlydata="1">提交</a>
			</div>
		</div>
   
</div>

<content tag="local_script">
    <!-- Peity -->
    <script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
    <!-- jqGrid -->
    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>

    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
    <!-- Data picker -->
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>


    <!-- 上传附件相关 -->
    <%--<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>

    <script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script>
    <script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>--%>
    <script src="<c:url value='/js/stickUp.js' />"></script>
    <!-- 上传附件 结束 -->
    <!-- 附件保存修改相关 -->
    <script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
    <script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>

    <!-- 改版引入的新的js文件 -->
    <script src="<c:url value='/js/common/textarea.js' />"></script>
    <script src="<c:url value='/js/common/common.js' />"></script>


    <!-- 必须JS -->
    <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
    <!--公共信息-->
    <script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
    <script>
	//提交数据
	function submit() {
        save();
	} 

	//保存数据
	function save() {
		var jsonData = $("#KnotCommissionForm").serializeArray();
        url = "${ctx}/task/KnotCommission/submitKnotCommission";
		$.ajax({
			cache : true,
			async : false,
			type : "POST",
			url : url,
			dataType : "json",
			data : jsonData,
            timeout:10000,
            beforeSend : function() {
                 $.blockUI({
                     message : $("#salesLoading"),
                     css : {
                         'border' : 'none',
                         'z-index' : '9999'
                     }
                 });
                 $(".blockOverlay").css({
                     'z-index' : '9998'
                 });
             },
             complete : function() {
                 $.unblockUI();
                 $.blockUI({
                     message : $("#salesLoading"),
                     css : {
                         'border' : 'none',
                         'z-index' : '9999'
                     }
                 });
                 $(".blockOverlay").css({
                     'z-index' : '9998'
                 });
                 if (status == 'timeout') {//超时,status还有success,error等值的情况
                     Modal.alert({
                         msg : "抱歉，系统处理超时。"
                     });
                     $(".btn-primary").one("click", function() {
                         parent.$.fancybox.close();
                     });
                 }
             },
             success : function(data) {
                 if(data.data){
                     caseTaskCheck();
                     if (null != data.message) {
                         window.wxc.success(data.message,{"wxcOk":function () {
                             $.unblockUI();
                         }})
                     }
                    /* window.wxc.success(data.message,{"wxcOk":function () {
                         window.location.href = "${ctx }/task/myTaskList";
                     }
                     })*/
                 }else {
                     window.wxc.error(data.message,{"wxcOk":function () {
                         window.location.href = "${ctx }/task/myTaskList";
                     }
                     })
                 }
             },
             error : function(errors) {
                 window.wxc.error("提交失败");
             }
		});
	}
    </script> </content>
</body>
</html>
