<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">


	<!-- 上传相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet">

<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />" rel="stylesheet">
<%--<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">--%>
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
	<style type="text/css">
		.userHead1{
			width: 80px;
			height: 80px;
			display: inline-block;
			border-radius: 50%;
			background-size: 80px 108px;
			vertical-align: middle;
			background-image:url(../../../img/a5.png);

		}
	</style>
	<script type="text/javascript">
		var ctx = "${ctx}";
		/**记录附件div变化，%2=0时执行自动上传并清零*/
		var index = 0;
		var taskitem = "${taskitem}";
		var caseCode = "${caseCode}";
		if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
		} else {
			var idList = [];
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<div class="">
	<!-- 服务流程 -->
	<div class="row wrapper white-bg new-heading " id="serviceFlow">
		<div class="pl10">
			<h2 class="newtitle-big">
				缴税
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
		<div>
			<h2 class="newtitle title-mark">填写任务信息</h2>
			<div class="form_list">
				<form method="post" class="form-horizontal" id="ratePaymentForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"
						   value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode"
						   value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						   name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid"
						   value="${houseTransfer.pkid}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType"
						   value="${approveType }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<div class="marinfo">
						<div class="line">
							<div class="form_content">
							<label class="control-label sign_left_small select_style mend_select">
								缴税时间<font color=" red" class="mr5" >*</font>
							</label>
							<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
								<input type="text" class="input_type yuanwid datatime" name="paymentTime" id="paymentTime"
									   onfocus="this.blur()" value="<fmt:formatDate  value='${ratePayment.paymentTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>
							<div class="form_content">
								<label class="control-label sign_left_small">个人所得税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="personalIncomeTax"
									   name="personalIncomeTax" <%--onkeyup="this.value=this.value.replace(/[^\d+(.0-9{2})]/g,'')"--%> onkeyup="checkNoNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.personalIncomeTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
							<div class="form_content">
								<label class="control-label sign_left">卖方增值税及附加<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="businessTax"
									   name="businessTax" onkeyup="checkNoNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.businessTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>

							</div>
							<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small">买方契税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="contractTax"
									   name="contractTax" onkeyup="checkNoNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.contractTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
								<div class="form_content">
									<label class="control-label sign_left_small">核税价<font color=" red" class="mr5" >*</font></label>
									<input type="text" class=" input_type yuanwid" id="pricingTax"
										   name="pricingTax" onkeyup="checkNoNum(this)"
										   value="<fmt:formatNumber value='${ ratePayment.landIncrementTax}' type='number' pattern='#0.00' />">
									<span class="date_icon">万元</span>
								</div>
							<div class="form_content">
								<label class="control-label sign_left">土地增值税及附加</label>
								<input type="text" class=" input_type yuanwid" id="landIncrementTax"
									   name="landIncrementTax" <%--onkeyup="checkNoNum(this)"--%>
									   value="<fmt:formatNumber value='${ ratePayment.landIncrementTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>

							</div>
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small">实际操作人</label>
								<input type="hidden" id="userId" name="autualOperatorId" value="${ratePayment.autualOperatorId}">
								<input class=" input_type yuanwid" name="autualOperatorName" id="ActualOperator" placeholder="请点击选择"   type="text" value="${ratePayment.autualOperatorName}"/>
							</div>
						</div>
							<div class="line">
							<div class="form_content">
									<label class="control-label sign_left_small">备注</label>
									<input class=" input_type yuanwid" name="comment" id="comment" placeholder="" value="${ratePayment.comment }"  type="text" style="width:800px;"/></div>
										   </div>
					</div>
				</form>
			</div>
		</div>
		<!-- 案件跟进 -->
		<div class="view-content" id="caseCommentList"> </div>
		<div class="mt30 clearfix" id="aboutInfo">
			<c:choose>
				<c:when test="${accesoryList!=null}">
					<h5 class="newtitle title-mark">上传备件</h5><br>

						<div class="table-box" id="ratePaymentfileUploadContainer"></div>

				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="form-btn">
			<div class="text-center">
				<a href="#" class="btn btn-success btn-space" onclick="save(false)">保存</a>
				<a href="#" class="btn btn-success btn-space" onclick="submit()" readOnlydata="1" id="btnSubmit">提交</a>
			</div>
		</div>

	</div>
</div>


<div class="wrapper wrapper-content  animated fadeInRight">
	<div id="modal-form" class="modal fade" aria-labelledby="modal-title"
		 aria-hidden="true">
		<div class="modal-dialog" style="width: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
					<h4 class="modal-title" id="modal-title">请选择过户权证</h4>
				</div>
				<div class="modal-body">
					<div class="row" style="height: 450px;overflow:auto; ">
						<div class="col-lg-12 ">
							<h3 class="m-t-none m-b"></h3>
							<div class="wrapper wrapper-content animated fadeInRight">
								<div id="modal-data-show" class="row"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
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
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
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
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script src="<c:url value='/js/trunk/task/attachment3.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/transjs/sms/sms.js' />"></script>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<!-- 审批记录 -->
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>

	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>

	<!-- 必须JS -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<!--公共信息-->
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>

	<script>
	var source = "${source}";


    $(document).ready(function() {
        /**日期组件*/
        var picker = $('#data_1 .input-group.date').datepicker({
            todayBtn : "linked",
            keyboardNavigation : false,
            forceParse : false,
            calendarWeeks : false,
            autoclose : true
        });
       // alert(taskitem)
		//渲染案件备注信息
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
        $("#ActualOperator").click(function () {
            //console.log("aaaaaaaaaaaaaaaa")
            caseDistribute();
        });
		/*if($("#paymentTime").val()!=''){
            $("#paymentTime").attr("disabled","false");
		}*/
       if('caseDetails'==source){
            readOnlyForm();
        }
     /*   setTimeout(function(){
//            $('.blockUI').hide();
        },2000);*/
});

	//提交数据
	function submit() {
			save(true);
	}

	//保存数据
	function save(b) {
		if(!checkForm()) {
			return;
		}
        //alert(id);
		$("#userId").val(id);

		$("#ActualOperator").val(userName);
		var jsonData = $("#ratePaymentForm").serializeArray();

		deleteAndModify();
		
		var url = "${ctx}/task/ratePayment/saveRatePayment";
		if(b) {
			url = "${ctx}/task/ratePayment/submitRatePayment";
		}
		
		$.ajax({
			cache : true,
			async : false,
			type : "POST",
			url : url,
			dataType : "json",
			data : jsonData,
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
            	$.unblockUI();  
            	if(b){ 
                    $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
				    $(".blockOverlay").css({'z-index':'1900'});
            	}   
                 if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                } 
		            } ,  
			success : function(data) {
				if(b) {
					caseTaskCheck();
					if(null!=data.message){
						window.wxc.alert(data.message);
					}
				} else {
					window.wxc.success("保存成功。",{"wxcOk":function(){
						window.close();
						window.opener.callback();
					}});
				}
			},
			error : function(errors) {
				window.wxc.error("数据保存出错");
			}
		});
	}
	
	//double 验证
    function checkNoNum(obj) {

		//先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d.]/g,"");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,"");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,".");
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}

	//验证控件checkUI();
	function checkForm() {
			$("input").css("border-color","#ccc");

			if ($('input[name=paymentTime]').val() == '') {
				window.wxc.alert("缴税时间为必填项!");
				$('input[name=paymentTime]').focus();
				$('input[name=paymentTime]').css("border-color","red");
				return false;
			}
			if ($('input[name=taxPricing]').val() == '') {
				window.wxc.alert("核税价为必填项!");
				$('input[name=taxPricing]').focus();
				$('input[name=taxPricing]').css("border-color","red");
				return false;
			}
			/* if($('input[name=commet]').val()=='') {
			 alert("备注为必填项!");
			 $('input[name=commet]').focus();
			 return false;
			 } */
			if ($('input[name=personalIncomeTax]').val() == '') {
				window.wxc.alert("个人所得税为必填项!");
				$('input[name=personalIncomeTax]').focus();
				$('input[name=personalIncomeTax]').css("border-color","red");

				return false;
			}
			if ($('input[name=businessTax]').val() == '') {
				window.wxc.alert("卖方增值税及附加为必填项!");
				$('input[name=businessTax]').focus();
				$('input[name=businessTax]').css("border-color","red");
				return false;
			}
			if ($('input[name=contractTax]').val() == '') {
				window.wxc.alert("买方契税为必填项!");
				$('input[name=contractTax]').focus();
				$('input[name=contractTax]').css("border-color","red");

				return false;
			}
			/*if ($('input[name=landIncrementTax]').val() == '') {
				window.wxc.alert("土地增值税及附加为必填项!");
				$('input[name=landIncrementTax]').focus();
				$('input[name=landIncrementTax]').css("border-color","red");
				return false;
			}*/
			if ($('input[name=pricingTax]').val() == '') {
				window.wxc.alert("核税价为必填项!");
				$('input[name=pricingTax]').focus();
				$('input[name=pricingTax]').css("border-color","red");
				return false;
			}
			if ($("#added_value_tax_pic_list li").length == undefined
				|| $("#added_value_tax_pic_list li").length == 0 ) {
				window.wxc.alert("增值税发票未上传!");
				return false;
			}

        //验证上传文件是否全部上传
        var isCompletedUpload = fileUpload.isCompletedUpload();

        if(!isCompletedUpload){
            window.wxc.alert("增值税发票还未全部上传!");
            return false;
        }
			return true;
		}
	
	//渲染图片 
	function renderImg(){
		$('.wrapper-content').viewer('destroy');
		$('.wrapper-content').viewer();
	}
	
	function readOnlyForm(){
		//设置缴税时间不可修改
        $("#paymentTime").attr("disabled","false");
		
		//设置提交按钮隐藏
		$("#btnSubmit").hide();
	}
    function caseDistribute(){
        console.log("=======================")
        var url = "/case/getUserOrgCpUserList";
        var ctx = $("#ctx").val();
        url = ctx + url;
        var data={operation:"ratePayment"};
        $.ajax({
            cache : false,
            async:true,
            type : "POST",
            url : url,
            dataType : "json",
            timeout: 10000,
            data :data,
            beforeSend:function(){
                $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
                $(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {
                $.unblockUI();
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    Modal.alert(
                        {
                            msg:"抱歉，系统处理超时。"
                        });
                    $(".btn-primary").one("click",function(){
                        parent.$.fancybox.close();
                    });
                }
            } ,
            success : function(data) {
                showModal(data);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    function showModal(data){
        var addHtml = '';
        var ctx = $("#ctx").val();
        $.each(data,function(i, n){
            addHtml += '<div class="col-lg-4"><div class="contact-box">';
            addHtml += '<a href="javascript:distributeCase('+i+')">';
            addHtml += '<div class="col-sm-5"><div class="text-center">';

            addHtml+='<span class="userHead1">';
            if(n.imgUrl!=null){
                addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'"/>';
            }
            addHtml+='</span>';

            addHtml += '<div class="m-t-xs font-bold">过户权证</div></div></div>';
            addHtml += '<div class="col-sm-7">';
            addHtml += '<input id="user_'+i+'" type="hidden" value="'+n.id+'">';
            addHtml += '<input id="userName_'+i+'" type="hidden" value="'+n.realName+'">';
            addHtml += '<h3><strong>'+n.realName+'</strong></h3>';
            addHtml += '<input id="mobile_'+i+'" type="hidden" value="联系电话：'+n.mobile+'">'+'联系电话：'+n.mobile;
            addHtml += '<p>当前单数：'+n.userCaseCount+'</p>';
            addHtml += '<p>本月接单：'+n.userCaseMonthCount+'</p>';
            addHtml += '<p>未过户单：'+n.userCaseUnTransCount+'</p>';
            addHtml += '</div><div class="clearfix"></div></a>';
            addHtml += '</div></div>';
        })
        $("#modal-data-show").html(addHtml);

        $('.contact-box').each(function() {
            animationHover(this, 'pulse');
        });
        $('#modal-form').modal("show");
    }

    function imgLoad(img){
        img.parentNode.style.backgroundImage="url("+img.src+")";
        //alert(img.src)
    }
    var userName;
    var id;
    function distributeCase(index) {
       	id=$("#user_"+index).val();
		userName=$("#userName_"+index).val();
		$("#userId").val(id);
		$("#ActualOperator").val(userName);
        $('#modal-form').modal("hide");
    }
	</script>
</content>

<content tag="local_require">
	<script>
        var fileUpload;
        require(['main'], function() {
            requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
                fileUpload = aistFileUpload;

                fileUpload.init({
                    caseCode : $('#caseCode').val(),
                    partCode : "RatePayment",
                    fileUploadContainer : "ratePaymentfileUploadContainer"
                });
            });
        });
	</script>
</content>

</body>
</html>