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
    <title>回访打回
    </title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.css" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/caseDetail.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/details.css" >
    <link rel="stylesheet" href="${ctx}/css/satis/addOutlist.css">
    <link rel="stylesheet" href="${ctx}/css/transcss/comment/caseComment.css">
</head>

<body class="pace-done">

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<div class="pace  pace-inactive"><div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
  <div class="pace-progress-inner"></div>
</div>
</div>
    <div id="wrapper">
    <!-- 右侧页面主体内容 -->
    <input type="hidden" id="ctx" value="http://trade.centaline.com:8083/trade-web">
    <input type="hidden" id="ctm" value="CBC-1-201608-0014">
    <input type="hidden" id="Lamp1" value="-1">
    <input type="hidden" id="Lamp2" value="1">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="activityFlag" value="30003003">
    <input type="hidden" id="caseCode" value="ZY-SH-201608-0095">
    <input type="hidden" id="instCode" value="">
    <input type="hidden" id="srvCodes" value="30004009,30004015">
    <input type="hidden" id="processDefinitionId" value="">
    <div id="salesLoading" style="display: none">
        <div id="loading-center">
            <div id="loading-center-absolute">
                <div class="object" id="object_one"></div>
                <div class="object" id="object_two" style="left: 20px;"></div>
                <div class="object" id="object_three" style="left: 40px;"></div>
                <div class="object" id="object_four" style="left: 60px;"></div>
                <!--<div class="object" id="object_five" style="left:80px;"></div>-->
            </div>
        </div>
        <div id="loading-center-absolute-second">系统正在处理，请稍后...</div>
    </div>
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
                <div class="top12 panel" id="basicInfo">
                    <div class="sign sign-red">
                        结案
                    </div>
                    <div class="sign sign-blue">
                        已过户
                    </div>
                    <div class="sign sign-yellow">
                        税费卡
                    </div>
                    <div class="panel-body">
                        <div class="ibox-content-head">
                            <h2 class="title">
                                案件基本信息
                            </h2>
                            <small class="pull-right">誉萃编号：${toCaseInfo.caseCode}｜中原编号：${toCaseInfo.ctmCode}</small>
                        </div>
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
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
											<dt>上家姓名</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
									<span>经纪人信息</span>
									<div class="ibox-conn else_conn_two ">
										<dl class="dl-horizontal">
											<dt>姓名</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${toCaseInfo.agentPhone}">
													${caseDetailVO.agentName}</a>
											</dd>
											<dt>所属分行</dt>
											<dd>${toCaseInfo.grpName }</dd>
											<dt>直管经理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.mcMobile}">
													${caseDetailVO.mcName} </a>
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.cpMobile}">
													${caseDetailVO.cpName} </a>
											</dd>
											<c:if test="${empty caseDetailVO.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseDetailVO.proList}">
												<c:forEach items="${caseDetailVO.proList}" var="pro">
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
													data-content="${caseDetailVO.asMobile}">
													${caseDetailVO.asName} </a>
											</dd>
										</dl>
									</div>
								</div>

							</div>
						</div>
                    </div>
                </div>

        <div class="row wrapper white-bg new-heading ">
            <div class="pl10">
            <h2 class="newtitle-big">
                过户打回
            </h2>
            </div>
        </div>

    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div class="mb15">
            <h2 class="newtitle title-mark">上传附件</h2>
            <div class="form_list">
                <div class="add-file"></div>
            </div>
        </div>
        
<!--         <div class="title title-mark mb15">
            <strong>案件跟进</strong>
            </div> -->
			<div id="caseCommentList" class="add_form"></div>
<!--             <div class="view-content ">
                <div class="view-box">
                    <div class="view clearfix">
                        <p>
                            <span class="auditor">打回人：<em>赵信（客服）</em></span>
                            <span class="sign_blue">签约打回</span>
                            <span class="time">打回日期:<em>2016-9-12</em></span>
                        </p>
                        <p>
                            <span class="auditing">打回原因</span>
                            <em class="view_content">上家电话号码错误，请求修改！</em>
                        </p>
                    </div>
                    <div class="view clearfix">
                        <p>
                            <span class="auditor">打回人：<em>赵信（客服）</em></span>
                            <span class="sign_blue">过户打回</span>
                            <span class="time">打回日期:<em>2016-9-12</em></span>
                        </p>
                        <p>
                            <span class="auditing">打回原因</span>
                            <em class="view_content">上家电话号码错误，请求修改！,上家电话号码错误，请求修改！,上家电话号码错误，请求修改！,上家电话号码错误，请求修改！</em>
                        </p>
                    </div>

                    <div class="view clearfix">
                        <p>
                            <span class="auditor">打回人：<em>赵信（客服）</em></span>
                            <span class="sign_blue">签约打回</span>
                            <span class="time">打回日期:<em>2016-9-12</em></span>
                        </p>
                        <p>
                            <span class="auditing">打回原因</span>
                            <em class="view_content">上家电话号码错误，请求修改！</em>
                        </p>
                    </div>
                </div>
                <div class="form_list clearfix">
                   <input class="input_type pull-left" placeholder="" value="" style="width:93%;">
                   <button class="btn btn_more pull-right" style="width:60px;">跟进</button>
                </div>
            </div> -->

                     <div class="form-btn">
                            <div class="text-center">
                                <button  class="btn btn-success btn-space">保存</button>
                                <button class="btn btn-success btn-space">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <content tag="local_script">
        	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        	<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        	<script	src="${ctx}/js/template.js" type="text/javascript"></script>
       		<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
        	<script	src="${ctx}/js/trunk/comment/caseComment.js"></script>
	        <script type="text/javascript">
	        $(function(){
				$("#caseCommentList").caseCommentGrid({
					caseCode : '${toCaseInfo.caseCode}',
					srvCode : null
				});
				
			    $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
		 	    $('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));
	        })

	 	     /*动态生成上下家*/
 			function generateSellerAndBuyer(name, phone){
 	 			var nameArr = name.split('/');
 	 			var phoneArr = phone.split('/');
 	 			var str='';
 	 			for (var i=0; i<nameArr.length; i++) {
 	 				if(i%2==0){
 	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a>&nbsp;';
 	 				}else{
 	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a><br/>';
 	 				}
 	 			}
 	 			return str;
 	 		}
	        </script>
        </content>
    </body>
    </html>