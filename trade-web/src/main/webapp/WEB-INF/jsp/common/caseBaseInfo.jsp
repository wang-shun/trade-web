<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>

<link href="${ctx}/static/trans/css/workflow/caseDetail.css" rel="stylesheet" />
<link href="${ctx}/static/trans/css/workflow/details.css" rel="stylesheet" />
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
</style>


<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"> <a href="#basicInfo"> 服务流程  </a></li>
				<li class="menuItem"> <a href="#basicInfo"> 相关信息 </a> </li>

			</ul>
		</div>
	</nav>

 <div class="wrapper wrapper-content" id="basicInfo">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
				<div class="top12 panel" id="basicInfo">
			       	<c:if test="${caseBaseVO.loanType=='30004005'}">
			          	<div class="sign sign-yellow">税费卡</div>
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
											<dt>助理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseBaseVO.agentManagerInfo.asMobile}">
													${caseBaseVO.agentManagerInfo.asName} </a>
											</dd>
										</dl>
									</div>
								</div>
							</div>
							</div>
                       
		   <c:if test="${caseBaseVO.toCase.caseProperty == 30003001}">
           	<div class="mark-wuxiao"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003002}">
           	<div class="mark-jiean"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003003}">
           	<div class="mark-zaitu"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003004}">
           	<div class="mark-guaqi"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003005}">
           	<div class="mark-baodan"></div>
           </c:if>
        </div>
    </div>
</div>

