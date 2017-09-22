<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>
<style type="text/css">
.radio.radio-inline > label{margin-left:10px;}
.radio.radio-inline > input{margin-left:10px;}
.checkbox.checkbox-inline > div{margin-left:25px;}
.checkbox.checkbox-inline > input{margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.ibox-content-task{padding-bottom:40px !important;}
#corss_area{padding:0 8px 0 0;margin-left:369px;}
#corss_area select{height:34px;border-radius:2px;margin-left:20px;}
</style>
<style>
[class^=mark]{position:absolute;top:8px;left:130px;width:56px;height:37px;z-index:0; background-position:left center;background-repeat:no-repeat}
.mark-baodan{background-image:url(../img/mark-baodan.png);}
.mark-guaqi{background-image:url(../img/mark-guaqi.png);}
.mark-jiean{background-image:url(../img/mark-jiean.png);}
.mark-wuxiao{background-image:url(../img/mark-wuxiao.png);}
.mark-zaitu{background-image:url(../img/mark-zaitu.png);}
.bb {
	white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width:178.15px;
}
.hint {position: relative; display: inline-block;}

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}
/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}
#basicInfo .sign {
	position: absolute;
	width: 55px;
	height: 40px;
	padding-top: 5px;
	color: #fff;
	text-align: center;
	font-family: "Microsoft Yahei";
	font-size: 14px;
}
#basicInfo .top12 .sign-red {
	background: url(${ctx}/static/trans/img/qizi-01-d998d141a485f8229a079346aa6e472c.png) no-repeat;
	top: 0px;
	left: 130px;
	width: 36px;
	height: 35px;
}
#basicInfo .top12 .sign-blue {
	background: url(${ctx}/static/trans/img/qizi-02-99c539708b71e1eeadc726209f6838ea.png) no-repeat;
	top: 0px;
	left: 190px;
	width: 52px;
	height: 35px;
}

#basicInfo .top12 .sign-yellow {
	background: url(${ctx}/static/trans/img/qizi-03-9576a537898fbd4e7491406d35c6482a.png) no-repeat;
	top: 0px;
	left: 265px;
	width: 52px;
	height: 35px;
}
</style>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script type="text/javascript">
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";

	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>


<script type="text/javascript">
//显示附件图片
function showAttachment(url){
	window.open(url);
	
}
//提交数据
function submit() {					
	save(true);
}

//保存数据
function save(b) {
	if(b){
		if (!checkForm()) {
			return;
		}													
	}
	
	var jsonData = $("#evalForm").serializeArray();

	var url = "${ctx}/eval/addEval";
	
	$.ajax({
        cache : true,
        async : false,
        type : "POST",
        url : url,
        dataType : "json",
        data : jsonData,
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
        success: function(data){
            $.unblockUI();
            console.log(data);
            if (b) {
                if (data) {
                    window.wxc.alert("提交成功"+data);
                }
                var ctx = $("#ctx").val();
                window.location.href=ctx+ "/task/myTaskList";
            }else{
            	if (data.message) {
                    window.wxc.alert("提交成功"+data);
                }
            }
        },
        error:function(){
        	window.wxc.alert("提交信息出错。。");
        }
    });
}

//double验证
function checkNum(obj) {
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
			.replace("$#$", ".");
}

//验证控件checkUI();
function checkForm() {
	var caseCode=$("#caseCode").val();

	if(!$("#changeChargesType").val()){
		window.wxc.alert("调佣类型为必填项!");
		$("#changeChargesType").focus();
		$("#changeChargesType").css("border-color","red");
		return false;
	}
	
	if(!$("#changeChargesCause").val()){
		window.wxc.alert("调佣事由为必填项!");
		$("#changeChargesCause").focus();
		$("#changeChargesCause").css("border-color","red");
		return false;
	}
	
	if(!caseCode){
		window.wxc.alert("缺少caseCode,请以正确的方式进入系统!");
		return false;
	}
	
		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});

</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<%-- <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include> --%>
<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"> <a href="#serviceFlow"> 服务流程  </a></li>
				<li class="menuItem"> <a href="#aboutInfo"> 相关信息 </a> </li>

			</ul>
		</div>
	</nav>

 <div class="wrapper wrapper-content" id="basicInfo">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
				<div class="top12 panel">
			       	<c:if test="${caseBaseVO.loanType=='30004005'}">
			          	<div class="sign sign-yellow">税费卡</div>
			        </c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003001'}">
						<div class="sign sign-red" ><span
								<c:if test="${toApproveRecord!=''}">
									class="hint hint-top" data-hint="${toApproveRecord}"
								</c:if> >无效</span></div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003002'}">
						<div class="sign sign-red">结案</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003005'}">
						<div class="sign sign-red ">
                  		<span
								<c:if test="${toApproveRecord!=''}">
									class="hint hint-top" data-hint="${toApproveRecord}"
								</c:if> >爆单</span>

						</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003004'}">
						<div class="sign sign-red">挂起</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>
					<c:if test="${toCase.caseProperty=='30003006'}">
						<div class="sign sign-red">全部</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003003'}">
						<div class="sign sign-red">在途</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>

			       <div class="panel-body">
						<div class="ibox-content-head">
							<h5>案件基本信息</h5>
							<small class="pull-right">誉萃编号：${caseBaseVO.toCase.caseCode}｜中原编号：${caseBaseVO.toCase.ctmCode}</small>
						</div> 
						
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${caseBaseVO.toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${caseBaseVO.toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${caseBaseVO.toPropertyInfo.locateFloor}／${caseBaseVO.toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${caseBaseVO.toPropertyInfo.square}平方</dd>
											<dt>房屋类型</dt>
											<dd>
												<aist:dict id="propertyType" name="propertyType"
													display="label" dictType="30014"
													dictCode="${caseBaseVO.toPropertyInfo.propertyType}" />
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
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.buyerSellerInfo.sellerMobile}">
													${caseBaseVO.buyerSellerInfo.sellerName}</a>
												
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.buyerSellerInfo.buyerMobile}">
													${caseBaseVO.buyerSellerInfo.buyerName}</a>
											</dd>
										</dl>
									</div>
									
									<span>经纪人信息</span>
									<div class="ibox-conn else_conn_two ">
										<dl class="dl-horizontal">
											<dt>姓名</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.agentManagerInfo.agentPhone}">
													${caseBaseVO.agentManagerInfo.agentName }</a>
											</dd>
											<dt>所属分行</dt>
											<dd>${caseBaseVO.agentManagerInfo.grpName }</dd>
											<dt>直管经理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.agentManagerInfo.mcMobile}">
													${caseBaseVO.agentManagerInfo.mcName} </a>
											</dd>
										</dl>
									</div>
								</div>
								
								
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div id="cpDiv" class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.agentManagerInfo.cpMobile}">
													${caseBaseVO.agentManagerInfo.cpName} </a>
											</dd>
											<c:if test="${empty caseBaseVO.agentManagerInfo.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseBaseVO.agentManagerInfo.proList}">
												<c:forEach items="${caseBaseVO.agentManagerInfo.proList}" var="pro">
													<dt>合作顾问</dt>
													<dd>
														<a data-toggle="popover" data-placement="right"
															data-content="${pro.processorMobile}">
															${pro.processorName} </a>
													</dd>
												</c:forEach>
											</c:if>
											<dt>交易助理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.agentManagerInfo.asMobile}">
													${caseBaseVO.agentManagerInfo.asName} </a>
											</dd>
										</dl>
									</div>
								</div>
								
							<!-- 调佣信息 -->
								<div  class="info_box info_box_two col-sm-12" >
									<span>调佣信息</span>
									<div  class="ibox-conn else_conn_two">
										<table style="width:100%">
											<tr style="height:30px">
												<td>合同价：</td>
												<td>1</td>
												<td>评估价：</td>
												<td>1</td>
												<td>评估公司：</td>
												<td>1</td>
											</tr>
											
											<tr style="height:30px">
												<td>出具评估报告的份数：</td>
												<td>1</td>
												<td>评估报告回收份数：</td>
												<td>1</td>
												<td>评估报告领取人：</td>
												<td>1</td>
											</tr>
											
											<tr style="height:30px">
												<td>评估报告领取时间：</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
											</tr>
									</table>
									</div>
								</div>
								
							</div>
							</div>                      
        </div>
    </div>
</div>
	<div class="">
		<div class="row wrapper white-bg new-heading" id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        	评估公司变更
                 </h2>
             </div>
        </div>

        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
            <h2 class="newtitle title-mark">填写房产信息</h2>
			<form method="get" class="form_list" id="evalForm"
				style="overflow: visible;">
				<input type="hidden" id="ctx" value="${ctx }" />
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"
					value="${caseCode}">

				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>调佣事项 :</label> <input type="text"
								class="input_type yuanwid" id="" name="changeChargesItem"
								onkeyup="checkNum(this)"  value="评估公司变更" maxlength="32">
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>调佣类型： </label> <input type="text"
								class="input_type yuanwid" id="changeChargesType" maxlength="16"
								name="changeChargesType" >
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>调佣事由 </label> <input type="text"
								class="input_type mendwidth" maxlength="150" name="changeChargesCause">
						</div>
					</div>

					<div class="line">
						<div class="form_content mt3">
							<label
								class="control-label sign_left_small select_style mend_select">
								中介费折评估费总金额 </label> 
								
							<input type="text" placeholder="成交价" class="input_type yuanwid" id="agEvalAmount" name="agEvalAmount" onkeyup="checkNum(this)"
										value="<fmt:formatNumber value="" type='number' pattern='#0.00'/>"/> 
						</div>

					</div>

					<div class="line">
						<div class="form_content mt3">
							<label class="control-label sign_left_small"> 调佣对象 </label> <input
								type="text" class="input_type mendwidth" maxlength="32"
								name="changeChargesTargat">
						</div>
					</div>

					<div class="line">
						<div class="form_content mt3">
							<label class="control-label sign_left_small"> 调佣金额 </label> 
								<input type="text" placeholder="成交价" class="input_type yuanwid" id="changeChargesAmount" name="changeChargesAmount" onkeyup="checkNum(this)"
										value="<fmt:formatNumber value="" type='number' pattern='#0.00'/>"> 
						</div>
					</div>
				</div>
				<div class="hr"></div>
			</form>



			<div class="form-btn">
	                    <div class="text-center">
	                        <button  class="btn btn-success btn-space" onclick="save(false)" id="btnSave">保存</button>
	                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
	                    </div>
	                </div>
	            </div>
			</div>
	
			<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
			<content tag="local_script"> 
				<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
				<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
				<%-- <script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script> --%>
				<%-- <script src="<c:url value='/transjs/task/showAttachment.js' />"></script>  --%>
				<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
				<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
				<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
				<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
				<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
				<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
				<script src="<c:url value='/static/js/jquery.json.min.js' />"></script>
				<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
				<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
				<script src="<c:url value='/js/trunk/task/taskFirstFollow.validate.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
				<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
				<script src="<c:url value='/js/template.js' />"></script>
				<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
				<script src="<c:url value='/js/stickUp.js' />"></script>
				<!-- 改版引入的新的js文件 -->
				<script src="<c:url value='/js/common/textarea.js' />"></script>
				<script src="<c:url value='/js/common/common.js' />"></script>
				<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
						var caseCode=$("#caseCode").val();
						
						$("#caseCommentList").caseCommentGrid({
							caseCode : caseCode,
							srvCode : 'caseRecvFlow'
						});

						
					//设置div显示或隐藏
					function isShow(divName, stats) {
					    var div_array = document.getElementsByName(divName);   
					    for(i=0;i<div_array.length;i++)  
					    {  
						    div_array[i].style.display = stats; 
					    }  
					}
					
					
			      
		            $("[name=businessLoanWarn]").click(function(){
		                if($(this).val()=='1'){
		                    $("#divContent").css("display","inherit");
		                }else{
		                    $("#divContent").css("display","none");
		                }
		            });
				
					})//end ready function
					
				</script> 
			</content>
	</body>
</html>