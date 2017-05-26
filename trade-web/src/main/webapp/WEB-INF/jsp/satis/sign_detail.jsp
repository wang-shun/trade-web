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
    <title>回访
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
    <link rel="stylesheet" href="${ctx}/css/transcss/comment/caseComment.css">
    <style>
		.borderClass {border:1px solid red!important;resize: none;}
		.borderClass:focus {border:1px solid red!important;resize: none;}
		.btn-primary {
  			background-color: #f8ac59 !important;
  			border-color: #f8ac59 !important;
  			color: #FFFFFF !important;
		}
	</style>
</head>

<body class="pace-done">
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
                <li class="menuItem"><a href="#"> 服务流程 </a></li>
                <li class="menuItem"><a href="#"> 相关信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
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
                   	<c:if test="${caseDetailVO.loanType=='30004005'}">
                  		<div class="sign sign-yellow">税费卡</div>
                  	</c:if>           
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
                签约回访
            </h2>
            </div>
        </div>

<form>
	<input type="hidden" id="urlType" name="urlType" value="${urlType}">
	<input type="hidden" id="taskId" name="taskId" value="${taskId}">
    <input type="hidden" id="instCode" name="instCode" value="${instCode}">
    <input type="hidden" id="caseCode" name="caseCode" value="${toCaseInfo.caseCode}">
    <input type="hidden" id="status" name="status" value="${satisfaction.status}">
    <input type="hidden" id="readOnly" name="readOnly" value="${readOnly}">
    <input type="hidden" id="pkid" name="pkid" value="${satisfaction.pkid}">
    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div>
            <h2 class="newtitle title-mark">上家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <c:forEach items="${fn:split(caseDetailVO.sellerName,'/')}" var="seller" varStatus="stat">
                            	<span class="mr10">${seller}—${fn:split(caseDetailVO.sellerMobile,'/')[stat.index]}</span>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                            <select class="select_control yuanwid" name="salerPhoneOk" value="${satisfaction.salerPhoneOk}">
                            	<option value="">请选择</option>
                                <option value="1" ${satisfaction.salerPhoneOk eq 1?'selected="selected"':''}>是</option>
                                <option value="0" ${satisfaction.salerPhoneOk eq 0?'selected="selected"':''}>否</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="salerPhoneRes" value="${satisfaction.salerPhoneRes}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label> 
                            <select class="select_control yuanwid" name="salerSignSat" value="${satisfaction.salerSignSat}">
                            		<option value="">请选择</option>
                            	<c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.salerSignSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label> 
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="salerSignCom" value="${satisfaction.salerSignCom}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪还贷评分</label>
                            <select class="select_control yuanwid" name="salerLoancloseSat" value="${satisfaction.salerLoancloseSat}">
                            		<option value="">请选择</option>
                            	<c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.salerLoancloseSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪同还贷意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="salerLoancloseCom" value="${satisfaction.salerLoancloseCom}" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">下家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <c:forEach items="${fn:split(caseDetailVO.buyerName,'/')}" var="buyer" varStatus="stat">
                            	<span class="mr10">${buyer}—${fn:split(caseDetailVO.buyerMobile,'/')[stat.index]}</span>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid" name="buyerPhoneOk" value="${satisfaction.buyerPhoneOk}">
                                 	<option value="">请选择</option>
                                    <option value="1" ${satisfaction.buyerPhoneOk eq 1?'selected="selected"':''}>是</option>
                                    <option value="0" ${satisfaction.buyerPhoneOk eq 0?'selected="selected"':''}>否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="buyerPhoneRes" value="${satisfaction.buyerPhoneRes}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid" name="buyerSignSat" value="${satisfaction.buyerSignSat}">
                            		<option value="">请选择</option>
                            	<c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.buyerSignSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="buyerSignCom" value="${satisfaction.buyerSignCom}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid" name="buyerComloanSat" value="${satisfaction.buyerComloanSat}">
                            		<option value="">请选择</option>
                            	<c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.buyerComloanSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="buyerComloanCom" value="${satisfaction.buyerComloanCom}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid" name="buyerPsfloanSat" value="${satisfaction.buyerPsfloanSat}">
                            		<option value="">请选择</option>
                            	<c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.buyerPsfloanSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="buyerPsfloanCom" value="${satisfaction.buyerPsfloanCom}" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">经纪人回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">${caseDetailVO.agentName}—${caseDetailVO.agentMobile}</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid" name="agentPhoneOk" value="${satisfaction.agentPhoneOk}">
                                 	<option value="">请选择</option>
                                    <option value="1" ${satisfaction.agentPhoneOk eq 1?'selected="selected"':''}>是</option>
                                    <option value="0" ${satisfaction.agentPhoneOk eq 0?'selected="selected"':''}>否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="agentPhoneRes" value="${satisfaction.agentPhoneRes}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid" name="agentSignSat" value="${satisfaction.agentSignSat}">
                            		<option value="">请选择</option>
                                <c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.agentSignSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="agentSignCom" value="${satisfaction.agentSignCom}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid" name="agentComloanSat" value="${satisfaction.agentComloanSat}">
                            		<option value="">请选择</option>
                                <c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.agentComloanSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="agentComloanCom" value="${satisfaction.agentComloanCom}" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid" name="agentPsfloanSat" value="${satisfaction.agentPsfloanSat}">
                            		<option value="">请选择</option>
                                <c:forEach begin="0" end="10" varStatus="stat">
                            		<option value="${stat.index}" ${satisfaction.agentPsfloanSat eq stat.index?'selected="selected"':''}>${stat.index}</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" name="agentPsfloanCom" value="${satisfaction.agentPsfloanCom}" >
                        </div>
                    </div>
                </div>
            </div>
        </div>

		        <div style="height: auto;">
				    <div class="mb15">
			           	<h2 class="newtitle title-mark">上传附件(由交易顾问上传)</h2>
			           	<div class="table-box" id="fileUploadContainer"></div>
			   		</div>	
				</div>		
		        <div>
		           <div id="caseCommentList" class="add_form"></div>
		        </div>
		        <div class="form-btn">
		           <div class="text-center">
		           	   <c:if test="${satisfaction.status eq 2 and !readOnly}">
		           	   		<a class="btn btn-success btn-space" onclick="javascript:doSignPass();" >通过</a>
		               		<a class="btn btn-success btn-space" data-toggle="modal" data-target="#myModal" >打回</a>
		               		<a class="btn btn-success btn-space" onclick="javascript:goBack();">取消</a>
		           	   </c:if>
		               <c:if test="${satisfaction.status ne 2 or readOnly}">
		               		<a class="btn btn-success btn-space" onclick="javascript:goBack();">关闭</a>
		               </c:if>
		           </div>
		        </div>
            </div>
		</form>                   
      </div>
    </div>
</div>

        <!--********** 弹窗页面 **********-->
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width:760px;">
                        <div class="animated fadeIn popup-box" style="background-color: #fff;padding:30px 30px 60px 30px;">
                            <div class="modal_title">
                                回访打回
                            </div>
                            <textarea name="rejectComment" id="rejectComment" autoFocus  class="textarearoom mt10" style="width:100%;max-width: 760px;margin-left:0;max-height: 150px;height: 150px;" ></textarea>
                            <div class="add_btn text-center mt20">
                                <button type="button" class="btn btn-success" onclick="javascript:doSignReject();">
                                    打回
                                </button>
                                <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                    关闭
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!--********** 弹窗页面 **********-->
                
          <content tag="local_script">
        	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        	<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        	<script	src="${ctx}/js/template.js" type="text/javascript"></script>
       		<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
        	<script	src="${ctx}/js/trunk/comment/caseComment.js"></script>
        	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script>
        	<script src="${ctx}/js/viewer/viewer.min.js"></script>
        	<script src="${ctx}/js/common/xcConfirm.js?v=1.0.1"></script>
	        <script type="text/javascript">
	        var caseCode = $("#caseCode").val();
	        var urlType = $("#urlType").val();
	        var status = $("#status").val();
	        var readOnly = $("#readOnly").val();
	        
	        $(function(){
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : "survey_sign"
				});
				
			    $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
		 	    $('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));
		 	    
		 	    if(status != '2' || readOnly == 'true'){
		 	    	readOnlyForm();
		 	    }
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
	        
	        /*签约通过*/
	        function doSignPass(){
	        	if(!checkForSubmit()){
	        		return false;
	        	}
	        	
	        	var data = $("form").serializeArray();

	        	window.wxc.confirm("确定通过吗？",{"wxcOk":function(){
					$.ajax({
						url:ctx+"/satis/doSignPass",
						method:"post",
						dataType:"json",
						data:data,
						beforeSend:function(){  
							$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
							$(".blockOverlay").css({'z-index':'9998'});
				        },
						success:function(data){
							 $.unblockUI();
							 if(data.success){
								 window.wxc.alert("操作成功！");
								 goBack();
							 }else{
								 window.wxc.error("操作失败！\n"+data.message);
							 } 
						 }
					})
				  }
				})
	        }
	        
	        /*签约驳回*/
	        function doSignReject(){
	        	var data = $("form").serializeArray();

	        	window.wxc.confirm("确定打回吗？",{"wxcOk":function(){
		        	//先将回访意见添加到案件跟进
		        	saveCaseComment2();
		        	
					$.ajax({
						url:ctx+"/satis/doSignReject",
						method:"post",
						dataType:"json",
						data:data,
						beforeSend:function(){  
							$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
							$(".blockOverlay").css({'z-index':'9998'});
				        },
						success:function(data){
							 $.unblockUI();
							 if(data.success){
								 window.wxc.confirm("操作成功！",{"wxcOk":function(){
									 goBack();
								   }
						   		 })
							 }else{
								 window.wxc.error("操作失败！\n"+data.message);
							 } 
						 }
					})
				  }
				})
	        }
	        
	        /*页面返回*/
	        function goBack(){
	        	if(urlType == 'list')
					 window.location.href = ctx+"/satis/list";
				 else
					 window.location.href = ctx+"/task/myTaskList";
	        }
	        
	        /*只读表单*/
	        function readOnlyForm(){
	        	$("input,select").prop("disabled",true);
	        }
	        
	    	function changeClass(object){
	    		$(object).focus().addClass("borderClass").blur(function(){
	    			$(this).removeClass("borderClass");
	    		});
	    	}
	        
	        /*提交时验证表单*/
	        function checkForSubmit(){
                /***********************上家START**************************/
                /*是否正确*/
                var $salerPhoneOk = $("select[name='salerPhoneOk']");
                if($salerPhoneOk.val() != '1'){
                	window.wxc.alert("上家电话不正确，无法通过！");
                    changeClass($salerPhoneOk);
                    return false;
                }
                var $salerSignSat = $("select[name='salerSignSat']");
                if($salerSignSat.val() == ''){
                	window.wxc.alert("请选择上家签约评分！");
                    changeClass($salerSignSat);
                    return false;
                }
                var $salerLoancloseSat = $("select[name='salerLoancloseSat']");
                if($salerLoancloseSat.val() == ''){
                	window.wxc.alert("请选择上家陪还贷评分！");
                    changeClass($salerLoancloseSat);
                    return false;
                }
                /***********************上家END**************************/
                /***********************下家START**************************/
                /*是否正确*/
                var $buyerPhoneOk = $("select[name='buyerPhoneOk']");
                if($buyerPhoneOk.val() != '1'){
                	window.wxc.alert("下家电话不正确，无法通过！");
                    changeClass($buyerPhoneOk);
                    return false;
                }
                var $buyerSignSat = $("select[name='buyerSignSat']");
                if($buyerSignSat.val() == ''){
                	window.wxc.alert("请选择下家签约评分！");
                    changeClass($buyerSignSat);
                    return false;
                }
                var $buyerComloanSat = $("select[name='buyerComloanSat']");
                if($buyerComloanSat.val() == ''){
                	window.wxc.alert("请选择下家贷款评分！");
                    changeClass($buyerComloanSat);
                    return false;
                }
                var $buyerPsfloanSat = $("select[name='buyerPsfloanSat']");
                if($buyerPsfloanSat.val() == ''){
                	window.wxc.alert("请选择下家公积金评分！");
                    changeClass($buyerPsfloanSat);
                    return false;
                }
                /***********************下家END**************************/
                
                return true;
	        }
	        </script>
        </content>     
        <content tag="local_require">
	       <script>
	        var caseCode = '${toCaseInfo.caseCode}';
       		var fileUpload;
		    require(['main'], function() {
				requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','blockUI','steps','ligerui','aistJquery','poshytip','twbsPagination','bootstrapModal','modalmanager','eselect'],function($,aistFileUpload){
					fileUpload = aistFileUpload;
						fileUpload.init({
				    		caseCode : caseCode,
				    		partCode : "Survey",
				    		fileUploadContainer : "fileUploadContainer",
				    		readonly : true
				    	});
					})
			    });
			</script>
	    </content>      
    </body>
    </html>