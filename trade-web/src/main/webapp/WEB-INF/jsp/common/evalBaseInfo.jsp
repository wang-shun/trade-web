<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>

<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
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
				   <div class="sign sign-red"></div>
			       <div class="panel-body">
						<div class="ibox-content-head">
							<h5>评估基本信息</h5>
							<small class="pull-right">交易编号：${caseBaseVO.toCase.caseCode}｜成交报告编号：${caseBaseVO.toCase.ccaiCode}</small>
						</div> 
						<div id="infoDiv infos" class="row">
						           <div class="ibox white_bg">
										<div class="info_box col-lg-11">
											<span>交易信息</span>
											<div class="ibox-conn ibox-text-two">
												    <dl class="dl-horizontal col-sm-5"><dt class="col-sm-6">产证地址</dt><dd>${caseBaseVO.toPropertyInfo.propertyAddr}</dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">层高</dt><dd>${caseBaseVO.toPropertyInfo.locateFloor}／${caseBaseVO.toPropertyInfo.totalFloor}</dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">产证面积</dt><dd>${caseBaseVO.toPropertyInfo.square}平方</dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">竣工年限</dt> <dd><fmt:formatDate value="${caseBaseVO.toPropertyInfo.finishYear}" type="date" pattern="yyyy-MM-dd"/></dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">房屋类型</dt><dd><aist:dict id="propertyType" name="propertyType" display="label" dictType="30014" dictCode="${caseBaseVO.toPropertyInfo.propertyType}" /></dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">房型</dt><dd></dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6" >卖方</dt><dd>${caseBaseVO.buyerSellerInfo.sellerName}</dd></dl>
												    <dl class="dl-horizontal col-sm-3"><dt class="col-sm-6">买方</dt><dd>${caseBaseVO.buyerSellerInfo.buyerName}</dd></dl>
											</div>
										</div>
									</div>
								    <div class="ibox white_bg">
									    <div class="info_box info_box_one col-lg-3">
											<span>经办人信息</span>
											<div class="ibox-conn ibox-text">
												<dl class="dl-horizontal">
													<dt class="col-sm-6">贷款权证</dt><dd><a data-toggle="popover" data-placement="right" data-content="${caseBaseVO.agentManagerInfo.cpMobile}">${caseBaseVO.agentManagerInfo.cpName}</a></dd>
													<dt class="col-sm-6">所属组别</dt><dd>${caseBaseVO.agentManagerInfo.cpOrgName}</dd>
													<dt class="col-sm-6">经纪人</dt><dd>${caseBaseVO.agentManagerInfo.agentName}</dd>
													<dt class="col-sm-6">所属分行</dt><dd>${caseBaseVO.agentManagerInfo.grpName}</dd>
												</dl>
											</div>
										</div>
										
										<c:if test="(${toEvaPricingVo!=null} && ${taskitem==null}) || ${taskitem=='EvalApply'}">
										<div class="info_box info_box_two col-lg-9">
											<span>询价信息</span>
											<div class="ibox-conn  ibox-text">
												<!-- <dl class="dl-horizontal"> -->
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">询价类型</dt><dd><aist:dict id="evalPriceType" name="evalPriceType" display="label" dictType="EVAPRICING_TYPE" dictCode="${toEvaPricingVo.evaType}" /></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估公司</dt> <dd>${toEvaPricingVo.evaCompany}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">合同价</dt> <dd>万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">房龄</dt> <dd>${toEvaPricingVo.houseAge}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">询价时间</dt> <dd>${toEvaPricingVo.evalTime}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">询价值</dt> <dd>${toEvaPricingVo.totalPrice}万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">贷款银行</dt> <dd>${toEvaPricingVo.loanBank}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">原购入价</dt> <dd>${toEvaPricingVo.originPrice}万元</dd></dl>
											</div>
										</div>
										</c:if>
										
										<c:if test="${taskitem == 'EvalReport'}">
										<div class="info_box info_box_two col-lg-9">
											<span>评估申请信息</span>
											<div class="ibox-conn  ibox-text">
												<!-- <dl class="dl-horizontal"> -->
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估类型</dt><dd><aist:dict id="evalType" name="evalType" display="label" dictType="EVAL_TYPE" dictCode="${toEvalReportProcessVo.reportType}"/></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估公司</dt> <dd>${toEvalReportProcessVo.finOrgName}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">合同价</dt> <dd>万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">房龄</dt> <dd>${toEvalReportProcessVo.houseAgeApply}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">申请评估时间</dt> <dd><fmt:formatDate value="${toEvalReportProcessVo.applyDate}" type="date" pattern="yyyy-MM-dd"/></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估值</dt> <dd><%-- ${toEvalReportProcessVo.evalPrice} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">贷款银行</dt> <dd><%-- ${toEvalReportProcessVo.loanBank} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">原购入价</dt> <dd>${toEvalReportProcessVo.ornginPrice}万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估报告份数</dt> <dd>${toEvalReportProcessVo.reportNum}份</dd></dl>
											</div>
										</div>
										</c:if>
										<c:if test="${taskitem == 'EvalIssue'}">
										<div class="info_box info_box_two col-lg-9">
											<span>评估上报信息</span>
											<div class="ibox-conn  ibox-text">
												<!-- <dl class="dl-horizontal"> -->
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估类型</dt><dd><aist:dict id="evalType" name="evalType" display="label" dictType="EVAL_TYPE" dictCode="${toEvalReportProcessVo.reportType}" /></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估公司</dt> <dd>${toEvalReportProcessVo.finOrgName}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">合同价</dt> <dd>万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">房龄</dt> <dd>${toEvalReportProcessVo.houseAgeApply}年</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">申请评估时间</dt> <dd><fmt:formatDate value="${toEvalReportProcessVo.applyDate}" type="date" pattern="yyyy-MM-dd"/></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估值</dt> <dd><%-- ${toEvalReportProcessVo.evalPrice} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">贷款银行</dt> <dd><%-- ${toEvalReportProcessVo.loanBank} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">原购入价</dt> <dd>${toEvalReportProcessVo.ornginPrice}万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估报告份数</dt> <dd>${toEvalReportProcessVo.reportNum}份</dd></dl>
											</div>
										</div>
										</c:if>
										<c:if test="${taskitem == 'EvalUsed'}">
										<div class="info_box info_box_two col-lg-9">
											<span>评估出具信息</span>
											<div class="ibox-conn  ibox-text">
												<!-- <dl class="dl-horizontal"> -->
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估类型</dt><dd><aist:dict id="evalType" name="evalType" display="label" dictType="EVAL_TYPE" dictCode="${toEvalReportProcessVo.reportType}" /></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估公司</dt> <dd>${toEvalReportProcessVo.finOrgName}</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">合同价</dt> <dd>万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">房龄</dt> <dd>${toEvalReportProcessVo.houseAgeApply}年</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">申请评估时间</dt> <dd><fmt:formatDate value="${toEvalReportProcessVo.applyDate}" type="date" pattern="yyyy-MM-dd"/></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估值</dt> <dd><%-- ${toEvalReportProcessVo.evalPrice} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">贷款银行</dt> <dd><%-- ${toEvalReportProcessVo.loanBank} --%></dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">原购入价</dt> <dd>${toEvalReportProcessVo.ornginPrice}万元</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-6">评估报告份数</dt> <dd>${toEvalReportProcessVo.reportNum}份</dd></dl>
												<dl class="dl-horizontal col-sm-4"><dt class="col-sm-8">实际出具评估报告日期</dt> <dd>${toEvalReportProcessVo.issueDate}</dd></dl>
											</div>
										</div>
										</c:if>
							    </div>
							</div>
        </div>
    </div>
</div>

