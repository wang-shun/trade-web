<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>
<html>


<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />


<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"><a href="#serviceFlow"> 服务流程 </a></li>
				<li class="menuItem"><a href="#aboutInfo"> 相关信息 </a></li>

			</ul>
		</div>
	</nav>
	
<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="scroll_box fadeInDown animated">
			<div class="top12 panel" id="basicInfo">
                	<c:if test="${toCase.caseProperty=='30003001'}">
                  		<div class="sign sign-red" ><span
                  		<c:if test="${toApproveRecord!=''}">
                 		class="hint hint-top" data-hint="${toApproveRecord}"
                 		</c:if> >无效</span></div>

                 	</c:if>
                  	<c:if test="${toCase.caseProperty=='30003002'}">
                  			<div class="sign sign-red">结案</div>
                   </c:if>
                  	<c:if test="${toCase.caseProperty=='30003009'}">
                  			<div class="sign sign-out"> 外单 </div>
                   </c:if>
                	<c:if test="${toCase.caseProperty=='30003005'}">
                 		<div class="sign sign-red ">
                 		<span
                  		<c:if test="${toApproveRecord!=''}">
                 		class="hint hint-top" data-hint="${toApproveRecord}"
                 		</c:if> >爆单</span>

                 		</div>
                 	</c:if>
                  	<c:if test="${toCase.caseProperty=='30003003' || toCase.caseProperty=='30003007' || toCase.caseProperty=='30003008'}">
                  		<div class="sign sign-red">在途</div>
                   <div class="sign sign-blue">
                   	<c:if test="${toCase.status=='30001001'}">
                   		未分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001002'}">
                   		已分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001003'}">
                   		已签约
                   	</c:if>
                   	<c:if test="${toCase.status=='30001004'}">
                   		已过户
                   	</c:if>
                   	<c:if test="${toCase.status=='30001005'}">
                   		已领证
                   	</c:if>
                   	<c:if test="${toCase.status=='30001006'}">
                   		未指定
                   	</c:if>
                   	<c:if test="${toCase.status=='30001007'}">
                   		被合流
                   	</c:if>
                   </div>
                   </c:if>
                  	<c:if test="${toCase.caseProperty=='30003004'}">
                  		<div class="sign sign-red">挂起</div>
                   <div class="sign sign-blue">
                   	<c:if test="${toCase.status=='30001001'}">
                   		未分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001002'}">
                   		已分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001003'}">
                   		已签约
                   	</c:if>
                   	<c:if test="${toCase.status=='30001004'}">
                   		已过户
                   	</c:if>
                   	<c:if test="${toCase.status=='30001005'}">
                   		已领证
                   	</c:if>
                   	<c:if test="${toCase.status=='30001006'}">
                   		未指定
                   	</c:if>
                   	<c:if test="${toCase.status=='30001007'}">
                   		被合流
                   	</c:if>
                   </div>
                   </c:if>
                 	<c:if test="${toCase.caseProperty=='30003006'}">
                 		<div class="sign sign-red">全部</div>
                   <div class="sign sign-blue">
                   	<c:if test="${toCase.status=='30001001'}">
                   		未分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001002'}">
                   		已分单
                   	</c:if>
                   	<c:if test="${toCase.status=='30001003'}">
                   		已签约
                   	</c:if>
                   	<c:if test="${toCase.status=='30001004'}">
                   		已过户
                   	</c:if>
                   	<c:if test="${toCase.status=='30001005'}">
                   		已领证
                   	</c:if>
                   	<c:if test="${toCase.status=='30001006'}">
                   		未指定
                   	</c:if>
                   	<c:if test="${toCase.status=='30001007'}">
                   		被合流
                   	</c:if>
                   </div>
                  	</c:if>
                  	
				<div class="panel-body">
					<div class="ibox-content-head lh24">
						<h5>案件基本信息</h5>
						<p class="star-position" id="subscribe">

							<c:if test="${isSubscribe}">
								<span style="cursor: pointer;" class="starmack subscribe subscribe_detail active"  moduleCode="${toCase.caseCode}" isSubscribe="false">
									<i class="iconfont_s  markstar star_subscribe" status="1">&#xe63e;</i>
									<span class="star_text_1">未关注</span>
									<span class="star_text_2">已关注</span>
								</span>
							</c:if>
							<c:if test="${isSubscribe==false}">
								<span style="cursor: pointer;" class="starmack subscribe subscribe_detail"  moduleCode="${toCase.caseCode}" isSubscribe="true">
								<i class="iconfont_s  markstar star_subscribe" status="1">&#xe644;</i>
								<span class="star_text_1">未关注</span>
								<span class="star_text_2">已关注</span>
								</span>
							</c:if>

						</p>
						<small class="pull-right">交易编号：${toCase.caseCode}｜成家报告编号：${toCase.ccaiCode}</small>
					</div>
					<div id="infoDiv infos" class="row">
						<div class="ibox white_bg">
							<div class="info_box info_box_one col-sm-4 ">
								<span>物业信息</span>
								<div class="ibox-conn ibox-text">
									<dl class="dl-horizontal">
										<dt>产证地址</dt>
										<dd>${toPropertyInfo.propertyAddr}</dd>
										<dt>层高</dt>
										<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
										<dt>产证面积</dt>
										<dd>${toPropertyInfo.square}平方</dd>
										<dt>竣工年限</dt>
										<dd>${toPropertyInfo.finishYear}年</dd>
										<dt>房屋类型</dt>
										<dd>
											<aist:dict id="propertyType" name="propertyType"
												display="label" dictType="30014"
												dictCode="${toPropertyInfo.propertyType}" />
										</dd>
									</dl>
								</div>
							</div>
							
							<div class="info_box info_box_two col-sm-5">
								<span>买卖双方</span>
								<div class="ibox-conn else_conn">
									<dl class="dl-horizontal col-sm-6">
										<dt>买方姓名</dt>
										<dd>
											<div id="buyer"></div>
										</dd>
									</dl>
									<dl class="dl-horizontal col-sm-6">
										<dt>卖方姓名</dt>
										<dd>
											<div id="seller"></div>
										</dd>
									</dl>
								</div>
							    <span>经纪人信息</span>
								<div class="ibox-conn else_conn_two ">
									<dl class="dl-horizontal">
										<dt>姓名</dt>
										<dd>
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.agentMobile}">
												${casePart.agentName}</a>
										</dd>
										<dt>所属分行</dt>
										<dd>${casePart.agentGrpName }</dd>
										<dt>直属经理</dt>
										<dd>
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.mcMobile}">
												${casePart.mcName} </a>
										</dd>
										<dt>分行秘书</dt>
										<dd>
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.msMobile}">${casePart.msName }</a>
										</dd>
									</dl>
								</div>

							</div>
							<div class="info_box info_box_three col-sm-3">
								<span>经办人信息</span>
								<div class="ibox-conn  ibox-text">
									<dl class="dl-horizontal">
										<dt>贷款权证</dt>
										<dd>
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.loanMobile}">
												${casePart.loanName} </a>
										</dd>
										<dt>过户权证</dt>
										<dd>
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.warMobile}">
												${casePart.warName} </a>
										</dd>
										<dt>内勤</dt>
										<dd>												
											<a data-toggle="popover" data-placement="right"
												data-content="${casePart.asMobile}">
												${casePart.asName} </a>
												
										</dd>
									</dl>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



</html>
