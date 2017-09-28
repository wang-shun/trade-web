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
			
           		<div v-if="caseProperty=='30003001'" class="sign sign-red" >
            		<span >无效</span>
           		</div>
         		<div v-if="caseProperty=='30003002'" class="sign sign-red">结案</div>
          		<div v-if="caseProperty=='30003009'" class="sign sign-out"> 外单 </div>
          		<div v-if="caseProperty=='30003005'" class="sign sign-red ">
           			<span>爆单</span>
          		</div>

           		<div v-if="caseProperty==30003003 || caseProperty==30003007 || caseProperty==30003008" class="sign sign-red">在途</div>
           		<div class="sign sign-blue">
             		<p v-if="status == '30001001' ">未分单</p>
             		<p v-if="status == '30001002' ">已分单</p>
             		<p v-if="status == '30001003' ">已签约</p>
             		<p v-if="status == '30001004' ">已过户</p>
             		<p v-if="status == '30001005' ">已领证</p>
             		<p v-if="status == '30001006' ">未指定</p>
             		<p v-if="status == '30001007' ">被合流</p>
           		</div>
            		
           		<div v-if="caseProperty=='30003004'" class="sign sign-red">挂起</div>
           		<div class="sign sign-blue">
             		<p v-if="status == '30001001' ">未分单</p>
             		<p v-if="status == '30001002' ">已分单</p>
             		<p v-if="status == '30001003' ">已签约</p>
             		<p v-if="status == '30001004' ">已过户</p>
             		<p v-if="status == '30001005' ">已领证</p>
             		<p v-if="status == '30001006' ">未指定</p>
             		<p v-if="status == '30001007' ">被合流</p>
           	 	</div>
           	 	
          		<div v-if="caseProperty=='30003006'" class="sign sign-red">全部</div>
           		<div class="sign sign-blue">
            		<p v-if="status == '30001001' ">未分单</p>
            		<p v-if="status == '30001002' ">已分单</p>
            		<p v-if="status == '30001003' ">已签约</p>
            		<p v-if="status == '30001004' ">已过户</p>
            		<p v-if="status == '30001005' ">已领证</p>
            		<p v-if="status == '30001006' ">未指定</p>
            		<p v-if="status == '30001007' ">被合流</p>
           		</div>
                  	
				<div class="panel-body">
					<div class="ibox-content-head lh24">
						<h5>案件基本信息</h5>

						<small class="pull-right">交易编号：{{caseCode}}｜成家报告编号：{{ccaiCode}}</small>
					</div>
					<div id="infoDiv infos" class="row">
						<div class="ibox white_bg">
							<div class="info_box info_box_one col-sm-4 ">
								<span>物业信息</span>
								<div class="ibox-conn ibox-text">
									<dl class="dl-horizontal">
										<dt>产证地址</dt>
										<dd>{{toPropertyInfo.propertyAddr}}</dd>
										<dt>层高</dt>
										<dd>{{toPropertyInfo.locateFloor}}／{{toPropertyInfo.totalFloor}}</dd>
										<dt>产证面积</dt>
										<dd>{{toPropertyInfo.square}}平方</dd>
										<dt>竣工年限</dt>
										<dd>{{toPropertyInfo.finishYear}}年</dd>
										<dt>房屋类型</dt>
										<dd>{{toPropertyInfo.propertyTypeName}}</dd>
									</dl>
								</div>
							</div>
							
							<div class="info_box info_box_two col-sm-5">
								<span>买卖双方</span>
								<div class="ibox-conn else_conn">
									<dl class="dl-horizontal col-sm-6">
										<dt>买方姓名</dt>
										<dd>
											<a id="buyer" data-toggle="popover" data-placement="right" 
												data-content="">{{buyerName}}</a>
										</dd>
									</dl>
									<dl class="dl-horizontal col-sm-6">
										<dt>卖方姓名</dt>
										<dd>
											<a id="seller" data-toggle="popover" data-placement="right" 
												data-content="">{{sellerName}}</a>
										</dd>
									</dl>
								</div>
							    <span>经纪人信息</span>
								<div class="ibox-conn else_conn_two ">
									<dl class="dl-horizontal">
										<dt>姓名</dt>
										<dd>
											<a id="agent" data-toggle="popover" data-placement="right" 
												data-content="">{{agentName}}</a>
										</dd>
										<dt>所属分行</dt>
										<dd>{{agentGrpName}}</dd>
										<dt>直属经理</dt>
										<dd>
											<a id="manager" data-toggle="popover" data-placement="right"
												data-content="">{{mcName}} </a>
										</dd>
										<dt>分行秘书</dt>
										<dd>
											<a id="ms" data-toggle="popover" data-placement="right"
												data-content="">{{msName}}</a>
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
											<a id="loan" data-toggle="popover" data-placement="right"
												data-content="">{{loanName}} </a>
										</dd>
										<dt>过户权证</dt>
										<dd>
											<a id="warr" data-toggle="popover" data-placement="right"
												data-content="">{{warName}} </a>
										</dd>
										<dt>内勤</dt>
										<dd>												
											<a id="as" data-toggle="popover" data-placement="right"
												data-content="">{{asName}} </a>
												
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
