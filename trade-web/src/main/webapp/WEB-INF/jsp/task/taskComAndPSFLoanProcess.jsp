<!DOCTYPE html>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/steps/jquery.steps.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet" />
<!-- datepikcer -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
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

<link rel="stylesheet" href="<c:url value='/css/common/step-mend.css' />" >
<style type="text/css">
/*
* @Author: anchen
* @Date:   2016-11-30 11:15:22
* @Last Modified by:   MIUu
* @Last Modified time: 2016-12-29 10:33:45
*/
.checker1{
	float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;
	}
.checker {
	float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;
}
.sign_left_small1 {
  display: inline-block;
  width: 120px;
  margin-right: 20px;
  color: #333;
  text-align: right;
}

.sign_left_small2{
 display: inline-block;
  width: 120px;
  margin-right: 20px;
  color: #333;
  text-align: right;
}
.wizard > .steps .current a, .wizard > .steps .current a:hover, .wizard > .steps .current a:active {
    background: #52cdec;
    color: #fff;
    cursor: default;
}
.wizard > .content > .body input {
    border: 1px solid #e5e5e5;
    display:inline-block!important;
}
.wizard > .steps .done a, .wizard > .steps .done a:hover, .wizard > .steps .done a:active {
    background: #87ddf3;
    color: #fff;
}
.wizard > .actions a, .wizard > .actions a:hover, .wizard > .actions a:active {
    background: #52cdec;
    color: #fff;
    display: block;
    padding: 0.5em 1em;
    text-decoration: none;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .steps .disabled a, .wizard > .steps .disabled a:hover, .wizard > .steps .disabled a:active {
    background: #f4f4f4;
    color: #aaa;
    cursor: default;
}
.wizard > .content {
    background: #fff;
    display: block;
    margin: 8px 0 0;
    min-height: 700px;
    overflow: hidden;
    position: relative;
    width: auto;
    padding-top: 10px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .content > .body {
    float: left;
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 0;
}
.wizard > .content .jqGrid_wrapper {
    margin: 5px 0 0;
}
.wizard > .actions {
    position: relative;
    display: block;
    text-align: right;
    width: 100%;
    margin-top: 20px;
    text-align: center;
}
.wizard > .steps a, .wizard > .steps a:hover, .wizard > .steps a:active {
    display: block;
    width: auto;
    margin: 0 0.5em 0.5em 0;
    padding: 8px;
    text-decoration: none;
    /* -webkit-border-radius: 5px; */
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .steps > ul > li {
    width: 15%;
}
.wizard > .content > .body > h4 {
    margin-bottom: 16px;
}
 .grey_visited {
            background-color: #ccc;
        }
        .blue_visited {
            background-color: #4bccec;
        }
        .red_visited {
            background-color: #ed5565;
        }
        .color_visited {
            display: inline-block;
            color: #fff;
            font-size: 14px;
            font-style: normal;
            padding: 1px 10px;
            margin-top: 2px;
            margin-right: 5px;
            border-radius: 3px;
        }

        .show_info span {
           display: inline-block;
        }
        .show_info .form_content {
            margin: 5px 10px;
        }
        .wizard > .content {
            min-height: 820px;
        }
        .grey_visited {
            background-color: #ccc;
        }
        .blue_visited {
            background-color: #4bccec;
        }
        .red_visited {
            background-color: #ed5565;
        }
        .color_visited {
            display: inline-block;
            color: #fff;
            font-size: 14px;
            font-style: normal;
            padding: 1px 10px;
            margin-top: 2px;
            margin-right: 5px;
            border-radius: 3px;
        }

        .show_info span {
           display: inline-block;
        }
        .show_info .form_content {
            margin: 5px 10px;
        }
        .radio_disabled em {
            float: left;
        }
        .pay_list_c1 {
            width: 24px;
            height: 18px;
            float: left;
            padding-top: 3px;
            cursor: pointer;
            text-align: center;
            margin-right: 3px;
            background-image: url(../static/trans/img/inputradio.gif);
            background-repeat: no-repeat;
            background-position: -24px 0;
        }
        .on {
            background-position: 0 0;
        }
        .on_check {
            background-position: -26px 0px;
        }

        .piaochecked {
            width: 20px;
            height: 20px;
            float: left;
            cursor: pointer;
            margin-left: 10px;
            text-align: center;
            background-image: url(images/checkbox_01.gif);
            background-repeat: no-repeat;
            background-position: 0 0;
        }

        .ibox-content{
            border-top-width: 0px;
        
        }
       input[readonly], select[disabled] {
		  background-color: #eee!important;
	}
</style>
<%
	response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<script language="javascript">
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var mainLoanBank = "1";

	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}

</script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

<%--环节编码 --%>
<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">

<!-- 交易单编号 -->
<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
<!-- 流程引擎需要字段 -->
<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
<input type="hidden" id="isMainLoanBank" name="isMainLoanBank" value="1"/>
<!-- 临时银行审批 -->
<input type="hidden" id="tmpBankStatus" name="tmpBankStatus"/>
<input type="hidden" id="serviceJobCode" value="${sessionUser.serviceJobCode }">
<input type="hidden" id="adminLoanerProcess"  name="adminLoanerProcess">
<!-- 案件合同价 -->
<input type="hidden" id="conPrice" name="conPrice" value="${caseBaseVO.sign.conPrice }">
	<!-- 服务流程 -->
		<div class="panel " id="serviceFlow">
	        <div class="row wrapper white-bg new-heading ">
	             <div class="pl10">
	                 <h2 class="newtitle-big">按揭贷款审批</h2>
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
            <div class="">
                    <div class="panel blank-panel">
                        <div class="panel-heading" style="padding:0;">
                            <div class="panel-options">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a id="tab1" data-toggle="tab" href="#tab-1">主选银行</a></li>
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body no-padding">
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                   <div class="ibox float-e-margins">
                                        <div>
                                        
                          <div id="wizard">
                          
                          	<h3>预约结果页签</h3>
                          	<section>
                          		<form id="mortgageForm0">
                          		<input type="hidden" name="pkid" id="pkid0"/>
                          		<input type="hidden" name="caseCode" value="${caseCode}">
								<input type="hidden" name="isMainLoanBank" value="1"/>
								
								<div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;" id='div_order'>
                                          <div class="marinfo">
                                              <div class="line">
                                                   <div class="form_content">
                                                      <label class="control-label sign_left_small">预约结果<span class="star">*</span></label>
                                                           <aist:dict id="bookResult" name="bookResult"
																clazz="select_control data_style" display="select" dictType="800001"
																defaultvalue="" />
                                                  </div>
                                                  <div class="form_content">
                                                      <label class="control-label sign_left_small"> 预约人<span class="star">*</span></label> 
                                                      <input name="bookPerson" id="bookPerson" class="input_type yuanwid"/>
                                                  </div>
                                              </div>
                                             </div>
                                            </div>
                          		</form>
                          	</section>
							<h3>评估结果页签</h3>
							<section>
							<h4>评估结果查看</h4>
								<form id="view">
										<input type="hidden" name="pkid" />
										<input type="hidden" name="caseCode" value="${caseCode}">
										<input type="hidden" name="isMainLoanBank" value="1"/>								
								</form>
								 <!-- <div class="jqGrid_wrapper">
	                                <table id="table_list_1"></table>
	                                <div id="pager_list_1"></div>
	                             </div> -->
							</section>
							<!-- 派单Tab开始（主) -->
                          <h3>组合贷款申请页签</h3>
                                                <section style="background-color: #fff">
                                                	<form id="mortgageForm" >
                                                		<input type="hidden" name="pkid" />
														<input type="hidden" name="caseCode" value="${caseCode}">
														<input type="hidden" name="isMainLoanBank" value="1"/>	
														<input type="hidden" name="bankOrgId" id="bankOrgId1" value=""/>
														<input type="hidden" name='custName' id="custName1" value="">
                                                     <h4>贷款信息填写</h4>
                                                    <div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;" id='div_order'>
                                                        
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 主贷人<span class="star">*</span></label> 
                                                                    <select name="custCode" id="custCode" class="input_type yuanwid">
                                                                    </select>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 共贷人<span class="star">*</span></label> 
                                                                    <input name="coLender" id="coLender" class="input_type yuanwid"/>
                                                                  
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 贷款总额<span class="star">*</span></label> 
                                                                    <select name="mortTotalAmount" id="mortTotalAmount" class="input_type yuanwid">
                                                                    </select>
                                                                </div>
                                                               
                                                                </div>
                                                            
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">认定套数</label> <input name='houseNum' onkeyup="checkInt(this)" class=" input_type data_style" placeholder="" value="" >
                                                                </div>
                                                                 <div class="form_content">
                                                                    <label class="control-label sign_left_small2">贷款类型<span class="star">*</span></label>
                                                                         <aist:dict id="mortType" name="mortType"
																		clazz="select_control data_style" display="select" dictType="30016"
																	defaultvalue="" />
                                                            </div>
                                                            </div>
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">按揭贷款金额<span class="star">*</span></label> <input name="comAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">按揭贷款年限<span class="star">*</span></label> <input name="comYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small1">按揭贷款利率折扣<span class="star">*</span></label> 
                                                                    <input name='comDiscount' onkeyup="autoCompleteComDiscount(this)" class=" input_type data_style" placeholder="0.50~1.5之间" value="" >
                                                                   <span class="date_icon"></span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">公积金贷款金额<span class="star">*</span></label> <input name="prfAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">公积金贷款年限<span class="star">*</span></label> <input name="prfYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                           
                                                            <div class="line">
                                                            	   <div class="form_content">
                                                                    <label class="control-label sign_left_small2">放款方式<span class="star">*</span></label>
                                                                    <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
																display="select" dictType="30017" defaultvalue="" />
                                                            		</div>
                                                                	<div class="form_content">
	                                                                    <label class="control-label sign_left_small2 select_style mend_select">
	                                                                        	实际面签时间<span class="star">*</span>
	                                                                    </label>
	                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                                                        <input name="signDate" class="input_type yuanwid datatime" type="text" value="" placeholder="">
	                                                                    </div>
                                                                </div>
        
                                                            </div>
                                                            
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">贷款银行<span class="star">*</span></label>
                                                                    <select name="bank_type" class="select_control  ">
                                                                    </select>
                                                                    <select name="finOrgCode" class=" select_control  ">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                             <div class="line">
	                                         	 				<input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
												 				<input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
												 				<input type="hidden"  id="loanerId" name="loanerId" />
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">信贷员<span class="star">*</span></label> <input class="input_type yuanwid" placeholder="" value="" name='loanerName'>
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" ></i>
                                                                </div>
                                                                <div class="form_content">
					                                             <label class="control-label sign_left_small2">信贷员电话<span class="star">*</span></label>
																 <input type="text" name="loanerPhone" id="loanerPhone" placeholder="联系方式" class="input_type data_style">
		                                         				</div>
                                                            </div>
                                                      
                                                    </div>
                                                    </form>
                                                </section>
                          <!-- 按揭贷款申请页签（结束) -->
							<h3>组合贷款补件页签</h3>
							<section>
						    <div style="width:100%" >
									<!-- 	<h4>备件列表</h4>
										<a class="btn btn-primary pull-right" href="#" id="sendSMS" style="clear:both;margin-top:-31px">发送短信提醒</a>
 -->						<form id=mortgageForm_b>	
 							<input type="hidden" name="pkid" id="pkid_bujian"/>
					
							<input type="hidden" name="isMainLoanBank" value="1"/>		
							<div class="line">			
								<div class="form_content" id="combujian">
									<label class="control-label sign_left_small">是否补件</label><span class="star">*</span>&nbsp;&nbsp;&nbsp;
									<div class="radio i-checks radio-inline">
									 <label> <input type="radio" checked="checked" value="1" id="ispatch" name="isPatch"> 是
									</label> 
									&nbsp;&nbsp;&nbsp;
									<label> <input type="radio" value="0" id="ispatch" name="isPatch"> 否
									</label>
									</div>
								</div>
							</div>
								<div class="row" id="form_check">
											<div class="col-md-6">
												<div>
													<div class="checker">
														<span class="checked">
														<input type="checkbox" id="bujian_b"  class="" value="700001" validate="" >
														</span>
													</div>
													<span style="font-weight:bold">买方材料</span>
												</div>
                                    		<aist:dict id="bujian_bb" name="buyPatch"
										 	display="checkbox"
											dictType="700001" ligerui='none'></aist:dict>
											</div>
											
											<div class="col-md-6">
												<div>
													<div class="checker">
														<span class="checked">
														<input type="checkbox" id="bujian_s"  class="" value="700002" validate="" >
														</span>
													</div>
													<span style="font-weight:bold">卖方材料</span>
												</div>
                                    		<aist:dict id="bujian_ss" name="sellPatch"
										 	display="checkbox"
											dictType="700002" ligerui='none'></aist:dict>
											</div>
								</div>
								 <div class="marinfo">
									<div class="line">
											<div class="form_content col-md-6">
                                                <label class="control-label sign_left_small select_style mend_select">
                                                    	补件时间<span class="star">*</span>
                                                </label>
                                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                    <input name="patchTimeBuy" class="form-control input-one date-picker" type="text" value="" placeholder="">
                                                </div>
                                             </div>
											<div class="form_content col-md-6">
                                                <label class="control-label sign_left_small select_style mend_select">
                                                    	补件时间<span class="star">*</span>
                                                </label>
                                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                    <input name="patchTimeSell" class="form-control input-one date-picker" type="text" value="" placeholder="">
                                                </div>
                                             </div>
                                     </div>
                                     </div>
                                  </form>  	
							</div>
							</section>
							
							<h3>上传附件页签</h3>
							<section>
							<input type="hidden" name = "accesoryCount" id="accesoryCount" value="${fn:length(accesoryList)} "/>
							<c:choose>  
						    <c:when test="${accesoryList!=null}">  
							<h4>上传附件<br><div class="table-box" id="comAndPSFLoanProcessfileUploadContainer"></div>
						
						    </c:when>  
						    <c:otherwise> 
							<h5>上传附件<br>无需上传附件</h5>
						    </c:otherwise>  
							</c:choose> 
							</section>
							
				
					<h3>完成页签</h3>
					<section>
					<form  class="form_list" name="completeForm" id="completeForm" style="margin:0">
							<input type="hidden" name="pkid" />
							<input type="hidden" id="isLoanerArrive"/>
							<input type="hidden" name="loanerStatus">
							<input type="hidden" name="caseCode" value="${caseCode }">
							<input type="hidden" name="custName">
							<input type="hidden" name='isMainLoanBank' id='isMainLoanBank' value="1">
							<input type="hidden" name="tmpBankStatus">
                                                        <h2 class="newtitle title-mark">贷款信息填写</h2>
                                                       <div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;" id='div_order'>
                                                        
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 主贷人<span class="star">*</span></label> 
                                                                    <select name="custCode" id="custCode" class="input_type yuanwid">
                                                                    </select>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 共贷人<span class="star">*</span></label> 
                                                                    <input name="coLender" id="coLender" class="input_type yuanwid"/>
                                                                  
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2"> 贷款总额<span class="star">*</span></label> 
                                                                    <select name="mortTotalAmount" id="mortTotalAmount" class="input_type yuanwid">
                                                                    </select>
                                                                </div>
                                                               
                                                                </div>
                                                            
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">认定套数</label> <input name='houseNum' onkeyup="checkInt(this)" class=" input_type data_style" placeholder="" value="" >
                                                                </div>
                                                                 <div class="form_content">
                                                                    <label class="control-label sign_left_small2">贷款类型<span class="star">*</span></label>
                                                                         <aist:dict id="mortType" name="mortType"
																		clazz="select_control data_style" display="select" dictType="30016"
																	defaultvalue="" />
                                                            </div>
                                                            </div>
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">按揭贷款金额<span class="star">*</span></label> <input name="comAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">按揭贷款年限<span class="star">*</span></label> <input name="comYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small1">按揭贷款利率折扣<span class="star">*</span></label> 
                                                                    <input name='comDiscount' onkeyup="autoCompleteComDiscount(this)" class=" input_type data_style" placeholder="0.50~1.5之间" value="" >
                                                                   <span class="date_icon"></span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                            	<div class="form_content">
                                                                    <label class="control-label sign_left_small2">公积金贷款金额<span class="star">*</span></label> <input name="prfAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">公积金贷款年限<span class="star">*</span></label> <input name="prfYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                           
                                                            <div class="line">
                                                            	   <div class="form_content">
                                                                    <label class="control-label sign_left_small2">放款方式<span class="star">*</span></label>
                                                                    <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
																display="select" dictType="30017" defaultvalue="" />
                                                            		</div>
                                                                	<div class="form_content">
	                                                                    <label class="control-label sign_left_small2 select_style mend_select">
	                                                                        	实际面签时间<span class="star">*</span>
	                                                                    </label>
	                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                                                        <input name="signDate" class="input_type yuanwid datatime" type="text" value="" placeholder="">
	                                                                    </div>
                                                                </div>
        
                                                            </div>
                                                            
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">贷款银行<span class="star">*</span></label>
                                                                    <select name="bank_type" class="select_control  ">
                                                                    </select>
                                                                    <select name="finOrgCode" class=" select_control  ">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                             <div class="line">
	                                         	 				<input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
												 				<input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
												 				<input type="hidden"  id="loanerId" name="loanerId" />
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small2">信贷员<span class="star">*</span></label> <input class="input_type yuanwid" placeholder="" value="" name='loanerName'>
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" ></i>
                                                                </div>
                                                                <div class="form_content">
					                                             <label class="control-label sign_left_small2">信贷员电话<span class="star">*</span></label>
																 <input type="text" name="loanerPhone" id="loanerPhone" placeholder="联系方式" class="input_type data_style">
		                                         				</div>
                                                            </div>
                                                            <div class="line">
	                                         	 					<div class="form_content">
		                                         	 					<label class="control-label sign_left_small1 select_style mend_select">
	                                                                        	实际审批完成时间<span class="star">*</span>
	                                                                    </label>
	                                         	 						<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        	<input name="apprCompleTime" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    	</div>
	                                         	 					</div>
	                                         	 					<div class="form_content">
		                                         	 					<label class="control-label sign_left_small1 select_style mend_select">
	                                                                        	出具借款合同时间<span class="star">*</span>
	                                                                    </label>
	                                         	 						<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        	<input name="loanContraTime" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    	</div>
	                                         	 					</div>
                                                            	</div>
                                                    </div>
                                                      
                                                    </form>
						</section>
						</div>
                                        </div>
                                    </div>
                                </div>

                                     
                              </div>
                         </div>
                     </div>
                 </div>
             </div>
         </div>

            
            <div id="aboutInfo">   
               <div class="view-content" id="caseCommentList"> </div>
            </div>
      

    <div class="modal inmodal fade in file-popup" id="myFile" tabindex="-1" role="dialog" aria-hidden="false" >
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">附件库</h4>
                </div>
                <div class="modal-body">
                    <div class="tab">
                        <div class="tab_menu">
                            <ul>
                                <li class="selected">分类1</li>
                                <li>分类2</li>
                            </ul>
                        </div>
                        <div class="tab_box">
                            <div>
                                <table class="table table-file" >
                                    <thead>
                                        <tr>
                                            <th class="text-center">
                                                <input type="checkbox" id="CheckedAll" />
                                            </th>
                                            <th>
                                                文件名
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                类型
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                上传时间
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-center">
                                                <input type="checkbox" name="items" />
                                            </td>
                                            <td>
                                               20161103test1.jpg
                                            </td>
                                            <td>
                                                类型1
                                            </td>
                                            <td>
                                                2016-12-06 15:30:21
                                            </td>
                                            <td>
                                                <a href="javascript:void(0)" class="fa-blue mr10">查看</a>
                                                <a href="javascript:void(0)" class="fa-pink">删除</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="text-center">
                                                <input type="checkbox" name="items" />
                                            </td>
                                            <td>
                                               20161103test1.jpg
                                            </td>
                                            <td>
                                                类型1
                                            </td>
                                            <td>
                                                2016-12-06 15:30:21
                                            </td>
                                            <td>
                                                <a href="javascript:void(0)" class="fa-blue mr10">查看</a>
                                                <a href="javascript:void(0)" class="fa-pink">删除</a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="text-center page_box">
                                    <span id="currentTotalPage">
                                        <strong>
                                            1/8
                                        </strong>
                                    </span>
                                    <span class="ml15">
                                        共
                                        <strong id="totalP">
                                            144
                                        </strong>
                                        条
                                    </span>
                                    &nbsp;
                                    <div id="pageBar" class="pagination text-center">
                                        <ul class="pagination">
                                            <li class="first disabled">
                                                <a href="#"><i class="fa fa-step-backward"></i></a>
                                            </li>
                                            <li class="prev disabled">
                                                <a href="#"><i class="fa fa-chevron-left"></i></a>
                                            </li>
                                            <li class="page active">
                                                <a href="#">
                                                    1
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    2
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    3
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    4
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    5
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    6
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    7
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    8
                                                </a>
                                            </li>
                                            <li class="next">
                                                <a href="#"><i class="fa fa-chevron-right"></i></a>
                                            </li>
                                            <li class="last">
                                                <a href="#"><i class="fa fa-step-forward"></i></a>
                                            </li>
                                        </ul>
                                        <div class="pagergoto">
                                            <span class="go_text">
                                                跳转至
                                            </span>
                                            <span>
                                            <input type="text" class="go_input" value="">
                                            <input type="button" class="go_btn" value="GO"></span>
                                        </div>
                                    </div>
                                </div>
                             </div>
                             <div class="cover">
                                 <table class="table table-file" >
                                    <thead>
                                        <tr>
                                            <th class="text-center">
                                                <input type="checkbox" id="CheckedAll" />
                                            </th>
                                            <th>
                                                文件名
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                文件类型
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                上传时间
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                        </tr>
                                    </tbody>
                                </table>
                             </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="file" class="btn btn-danger">上传新附件</button>
                    <button type="button" class="btn btn-success">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

<content tag="local_script"> 
<script>
var source = "${source}";

//‘我要修改’时不可修改签约时间和审批时间
if('caseDetails'==source){
	readOnlyForm();
}

function readOnlyForm(){
	$("input[name='signDate']").attr('readOnly',true).css("background-color","#eee").parent().removeClass("input-daterange");
	$("input[name='apprDate']").attr('readOnly',true).css("background-color","#eee").parent().removeClass("input-daterange");
}
</script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/staps/jquery.steps.min.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<!-- 上传附件相关 --> 
<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> 
<!-- 上传附件结束 -->
<script src="<c:url value='/transjs/task/taskComAndPSFLoanProcess.js' />?6"></script> 
<script	src="<c:url value='/js/trunk/task/attachment.js' />"></script> 
<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
<script src="<c:url value='/transjs/sms/sms.js' />"></script>	
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom1.js' />"></script>
<script src="<c:url value='/js/stickUp.js' />"></script>
<!-- 改版引入的新的js文件 --> 
<script src="<c:url value='/js/common/textarea.js' />"></script>
<script src="<c:url value='/js/common/common.js' />"></script>
<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script>
//var afterTimeFlag=${afterTimeFlag};
var popInited=false;
var operForm;//用于定位当前操作的是哪个Form
/* var MORT_ORDER_1 = new mortageRender("#orderform",'O');
var MORT_ORDER_0 = new mortageRender("#orderform1",'O'); */
var MORT_SIGN_1 = new mortageRender('#mortgageForm','S');
var MORT_SIGN_2 = new mortageRender('#mortgageForm_b','T');
var MORT_SIGN_3 = new mortageRender('#mortgageForm0','Y');
var MORT_VIEW = new mortageRender("#view",'V');
var MORT_COMPLETE_1 = new mortageRender('#completeForm','C');


var tradeCenter;


//点击tab页面触发函数   -----From Bootstrap 标签页（Tab）插件
$(".nav-tabs").find("a").on('shown.bs.tab', function (e) {
	  var id = e.target.id;
	  if(id == 'tab1'){
		  //点击主选银行
		  var ctx = "${ctx}";
		  var step = ${step};
		 if(step == 0){
			 loadMortgageInfo(1,MORT_SIGN_3); 
	    }else if(step==1){
			loadMortgageInfo(1,MORT_VIEW);
		}else if(step == 2){
			loadMortgageInfo(1,MORT_SIGN_1);
		}else if(step ==3){
			loadMortgageInfo(1,MORT_SIGN_2);
		}else if(step == 4){
			loadMortgageInfo(1,MORT_SIGN_1);
		}else if(step == 5){
			loadMortgageInfo(1,MORT_COMPLETE_1);
		}
	  }
	})

function checknum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

/*贷款折扣自动补全*/
function autoCompleteComDiscount(obj){
	
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	
	var inputVal = obj.value;
 	if(inputVal>=0.5 && inputVal<=1.5){
		var reg =/^[01]{1}\.{1}\d{3,}$/;
		if(reg.test(inputVal)){
			obj.value = inputVal.substring(0,4);
		}
	}
}

function checkInt(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");  
}

 	var ctx = "${ctx}";
	var step = ${step};
	//var step1 = ${step1};

	var evaCode = "${evaCode}";
	if(evaCode != ""){
		$("#eva_code").val(evaCode);
	}
 	var index = 0;
 	/**评估费用修改后计算费用总计和业绩*/
	function evalFeeChanged(){
 		var f =$(this).closest('form');
 		var conPrice=parseFloat($('#conPrice').val());
		var evaFee = parseFloat(f.find('[name=evaFee]').val()||0);
		var serviceFee = parseFloat(f.find('[name=serviceFee]').val()||0);
		var evaFeeCost = parseFloat(f.find('[name=evaFeeCost]').val()||0);
		var feeTotal = evaFee + serviceFee ;
		
		
		var evaFeeReal = conPrice / 1000 ; 
		evaFeeReal = Math.min(evaFeeReal,feeTotal) ;
		evaFeeReal = Math.max(evaFeeReal,evaFee) ;
		var perfTotal = feeTotal - evaFeeCost ;
		var evaFeePerf = evaFeeReal-evaFeeCost;
		var serviceFeePerf = perfTotal - evaFeePerf ;
		if(serviceFeePerf <=0){
			serviceFeePerf ='';
		}
		if(evaFeePerf<=0){
			evaFeePerf='';
		}
		f.find('[name=feeTotal]').val(feeTotal);
		f.find('[name=evaFeePerf]').val(evaFeePerf);
		f.find('[name=serviceFeePerf]').val(serviceFeePerf);
	}
 	
 	
 	
 	
 	jQuery(document).ready(function() {
 		//按揭贷款金额失去焦点事件
 		$("#mortgageForm").find("input[name=comAmount]").blur(function(){
 			$("#mortgageForm").find("select[name=mortTotalAmount]").empty();
 			var _comAmount = Number($("#mortgageForm").find("input[name=comAmount]").val());
 			var _prfAmount = Number($("#mortgageForm").find("input[name=prfAmount]").val());
 			if(_comAmount == "" || _comAmount == null || _comAmount == undefined){
 				_comAmount = 0;
 			}
 			if(_prfAmount == "" || _prfAmount == null || _prfAmount == undefined){
 				_prfAmount = 0;
 			}
 			var total = _comAmount+_prfAmount;
 			$("#mortgageForm").find("select[name=mortTotalAmount]").append("<option value='"+total+"'>"+total+"</option>");
 		});
 		//公积金贷款金额失去焦点事件
 		$("#mortgageForm").find("input[name=prfAmount]").blur(function(){
 			$("#mortgageForm").find("select[name=mortTotalAmount]").empty();
 			var _comAmount = Number($("#mortgageForm").find("input[name=comAmount]").val());
 			var _prfAmount = Number($("#mortgageForm").find("input[name=prfAmount]").val());
 			if(_comAmount == "" || _comAmount == null || _comAmount == undefined){
 				_comAmount = 0;
 			}
 			if(_prfAmount == "" || _prfAmount == null || _prfAmount == undefined){
 				_prfAmount = 0;
 			}
 			var total = _comAmount+_prfAmount;
 			$("#mortgageForm").find("select[name=mortTotalAmount]").append("<option value='"+total+"'>"+total+"</option>");
 		});
 		/**
 		*	点击是否补件让内容显示或隐藏
 		*/
 		$($("#combujian").find("input")[0]).change(function(){
 			if($($("#combujian").find("input")[0]).is(':checked')){
 				$("#form_check").show();
 				$("#form_check").next("div.marinfo").show();
 			}
 		});
 		
		$($("#combujian").find("input")[1]).change(function(){
			if($($("#combujian").find("input")[1]).is(':checked')){
 				$("#form_check").hide();
 				$("#form_check").next("div.marinfo").hide();
 			}
 		});
		
 		
 		/*
 	 	*买方材料点击选中买方材料全选
 	 	*/
 	 	
 	 	$("#bujian_b").change(function(){
 			if($("#bujian_b").is(':checked')){
 				var $div = $("#bujian_b").parent().parent().parent().siblings("div");
 				for( var i = 0;i < $div.length; i++){
 					$($div[i]).find("input").prop("checked",true);
 				}
 			}else{
 				var $div = $("#bujian_b").parent().parent().parent().siblings("div");
 				for( var i = 0;i < $div.length; i++){
 					$($div[i]).find("input").prop("checked",false);
 				}
 			}
 	 	});
 	
 		/**
 		*卖方材料点击选中卖方材料全选
 		*/
 		$("#bujian_s").change(function(){
 			if($("#bujian_s").is(':checked')){
 				var $div = $("#bujian_s").parent().parent().parent().siblings("div");
 				for( var i = 0;i < $div.length; i++){
 					$($div[i]).find("input").prop("checked",true);
 				}
 			}else{
 				var $div = $("#bujian_s").parent().parent().parent().siblings("div");
 				for( var i = 0;i < $div.length; i++){
 					$($div[i]).find("input").prop("checked",false);
 				}
 			}
 		});
 		
 		
 		
 		//checkbox 去掉float-left
 		var checkbox_div = $("#form_check").find("div");
 		for(var i = 0; i<checkbox_div.length;i++){
 			$(checkbox_div[i]).prop("style","");
 			//默认不选中
 			$($(checkbox_div[i]).find("input")[0]).prop("checked",false);
 		}
 		
 	

 		$('#loanerNameImage').click(selectLoanerByOrgId);
 		
		
		
		var updFun = function(e) {
			var that = $(this).data('blueimp-fileupload')
					|| $(this).data('fileupload');
			that._resetFinishedDeferreds();
			that._transition($(this).find('.fileupload-progress'))
					.done(function() {
						that._trigger('started', e);
					});
		};
		
		for(var i=1;i<4;i++){
			AistUpload.init('picFileupload'+i, 'picContainer'+i,
					'templateUpload'+i, 'templateDownload'+i,
					updFun);
			$("#picContainer"+i).bind('DOMNodeInserted', function(e) {
				var picDiv=$("div[name='allPicDiv2']");
				var input=$("input[name='picTag1']");
				if(picDiv.length > input.length) {
					index++;
					if(index % 2 == 0) {
						index = 0;
						$("#startUpload").click();
					}
				}
			});
		}
		
		$.each(idList, function(index, value){
			AistUpload.init('picFileupload'+value, 'picContainer'+value,
					'templateUpload'+value, 'templateDownload'+value, updFun);
			/**监听 div 执行自动上传*/
			$("#picContainer"+value).bind('DOMNodeInserted', function(e) {
				var picDiv=$("div[name='allPicDiv1']");
				var input=$("input[name='picTag']");
				if(picDiv.length > input.length) {
					index++;
					if(index % 2 == 0) {
						//alert("执行自动上传");
						index = 0;
						$("#startUpload1").click();
					}
				}
			});
		});
		
		$("#code").inputmask({"mask":"999999-999999-999"});
		
	 	$("select[name='mortType']").each(function(){
			$(this).find("option[value='30016001']").remove();
			$(this).find("option[value='30016003']").remove();
			$(this).find("option[value='30016002']")[0].selected = true;
		}); 
		
		$("select[name='mortType']").blur(function(){
			if($(this).val()!=""){
				$(this).css("border-color","#e5e6e7");
			}
		});

		$('.input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : false,//显示年日历周
			autoclose : true
		});
	
		$("select[name='finOrgCode']").change(finOrgCodeChange);
		 if(step == 0){
			 loadMortgageInfo(1,MORT_SIGN_3); 
	    }else if(step==1){
			loadMortgageInfo(1,MORT_VIEW);
		}else if(step == 2){
			loadMortgageInfo(1,MORT_SIGN_1);
		}else if(step ==3){
			loadMortgageInfo(1,MORT_SIGN_2);
		}else if(step == 4){
			loadMortgageInfo(1,MORT_SIGN_1);
		}else if(step == 5){
			loadMortgageInfo(1,MORT_COMPLETE_1);
		}

		 $("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : taskitem
		});
		
	});
	
	var attachmentList = null;
	var files = null;
	var attachmentIds = null;
	var preFileAdress = null;
	var picTag = null;
	var picName = null;
	function getUploadPicInfo() {
		attachmentIds =[];
		preFileAdress = [];
		picTag = [];
		picName = [];

		// 图片的ID
		$("div[name='reportPic']").each(function(){
			$(this).find("input[name='preFileAdress1']").each(function(){
				preFileAdress.push($(this).val());
			});
			$(this).find("input[name='picTag1']").each(function(){
				picTag.push($(this).val());
			});
			$(this).find("input[name='picName1']").each(function(){
				picName.push($(this).val());
			});
			
		});

		$("input[name='attachmentId']").each(function(){
			attachmentIds.push($(this).val());
		});
		
		return false;
	}

	//封装临时银行参数
	function getTmpParams(f){
		var data={};
		data.tmpBankReason=f.find('[name=tmpBankReason]').val();
		data.finOrgCode =f.find('[name=finOrgCode]').val();
		data.loanerPhone =f.find('[name=loanerPhone]').val();
		data.loanerName =f.find('[name=loanerName]').val();
		data.loanerId = f.find("input[name=loanerId]").val();
		data.loanerOrgCode = f.find("input[name=loanerOrgCode]").val();
		data.loanerOrgId = f.find("input[name=loanerOrgId]").val();
		data.caseCode= $("input[name=caseCode]").val();
		return data;
	}
	function saveTmpBankCheck(data){
		if(data.tmpBankReason==''){
			window.wxc.alert('请输入临时银行原因');
			return false;
		}
		if(data.finOrgCode==''){
			window.wxc.alert('请选择银行');
			return false;
		}
		if(data.loanerName==''){
			window.wxc.alert('请输入信贷员');
			return false;
		}
		if(data.loanerPhone==''){
			window.wxc.alert('请输入信贷员电话');
			return false;
		}
		return true;
	}
	//保存临时银行填写的数据
	function saveTmpBank(f){
		var data = getTmpParams(f);
		if(!saveTmpBankCheck(data)){
			return false;
		}
		$.ajax({
		    url:ctx+"/mortgage/tmpBankAudit/saveTmpBank",
		    async:false,
	    	method:"post",
	    	dataType:"json",
	    	data:data,
	    	success:function(data){
	    		if(data.success){
		
	    		}else{
	    			window.wxc.error(data.message);
	    		}
	    		
	    	}
	 	});
	}
	function startTmpBankWorkFlow(){
		//'我要修改'页面不触发流程 	
		if(source != null && source !=''){
			return;
		}
		var f =$('#orderform');
		var data=getTmpParams(f);
		if(!saveTmpBankCheck(data)){
			return false;
		}
	
	 	$.ajax({
		    url:ctx+"/mortgage/tmpBankAudit/start",
		    async:false,
	    	method:"post",
	    	dataType:"json",
	    	data:data,
	    	success:function(data){
	    		if(data.success){
	    			window.wxc.success(data.message);
	    			loadMortgageInfo(1,MORT_ORDER_1);
	    		}else{
	    			window.wxc.error(data.message);
	    		}
	    		
	    	}
	 	});

	}
	function selectLoanerUser(array) {
		//operForm 在弹出选择用户已经确定
		selectLoanerUserCom(array,operForm);
	}
	
	function selectLoanerUserCom(array,$form){		
		var isMainLoanBank = $("#isMainLoanBank").val();
		if (array && array.length > 0) {
				if(isMainLoanBank == 1){
					$form.find("input[name='loanerName']").val(array[0].username);
					//$form.find("input[name='loanerName']").val(array[0].username);
				}else if(isMainLoanBank == 0){
					$form.find("input[name='loanerName']").val(array[0].username);
				}				
				$.ajax({
					url : ctx + "/eloan/LoanerCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$form.find("input[name=loanerNameImage]").css("color","#52cdec");
						$form.find("input[name=loanerPhone]").val(data.user.mobile);
						$form.find("input[name=loanerId]").val(data.user.id);
						$form.find("input[name=loanerOrgCode]").val(data.user.orgName);
						$form.find("input[name=loanerOrgId]").val(data.user.orgId);
					}
				})
			} else {
				if(isMainLoanBank == 1){
					$form.find("input[name='loanerName']").val("");
				}
				$form.find("input[name='loanerName']").val("");				
				$form.find("input[name=loanerOrgCode]").val("");
				$form.find("input[name=[loanerOrgId]").val("");
			}
	}
	
	//渲染图片 
	function renderImg(){
		$('.wrapper-content').viewer('destroy');
		$('.wrapper-content').viewer();
	}
 	</script> 
 </content>
 <content tag="local_require">
    <script>
    	var fileUpload;
    
	    require(['main'], function() {
			requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
				fileUpload = aistFileUpload; 
				
				fileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "ComLoanAndPSFLoanProcess",
		    		preFileCode : "_letter_first",
		    		fileUploadContainer : "comAndPSFLoanProcessfileUploadContainer"
		    	});
				
				/* fileUpload.init({
			    		caseCode : $('#caseCode').val(),
			    		partCode : "ComLoanProcess",
			    		preFileCode : "_letter_sec",
			    		fileUploadContainer : "comLoanProcess1fileUploadContainer"
			    	}); */
				
			    
		    });
	    });
	    
	    
	</script>
	</content>
</body>
</html>
