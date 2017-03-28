<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewpoart" content="width=device-width, initial-scale=1.0">

<title>监管合约</title>
<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- index_css  -->
<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet"/>
<link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
<link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/> 
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/stickmenu.css">
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
<link href="${ctx}/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
<link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
<!--弹出框样式  -->
<link href="${ctx}/css/common/xcConfirm.css" rel="stylesheet">
<!-- stickUp fixed css -->
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "SpvApplyApprove";
	var source = "${source}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
	var accTypeOptions = '';
	var accSum = 1;
</script>
<style>
	.borderClass {border:1px solid red!important;resize: none;}
	.borderClass:focus {border:1px solid red!important;resize: none;}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input id="pkid" type="hidden" value="${spvBaseInfoVO.toSpv.pkid }">
	<div id="wrapper">
	<form id="extraInfo">
	    <%-- 流程相关 --%>
		<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		<input type="hidden" id="taskitem" name="taskitem" value="${taskitem }">
		<input type="hidden" id="instCode" name="instCode" value="${instCode}">
		<input type="hidden" id="source" name="source" value="${source}">
		<input type="hidden" id="urlType" name="urlType" value="${urlType}">
		<input type="hidden" name="spvCode" value="${spvBaseInfoVO.toSpv.spvCode }"/>
     </form>
		
		<!-- main Start -->
		<div
			class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
			<ul class="nav navbar-nav">
				<li class="menuItem active"><a href="#base_info">客户信息</a></li>
				<li class="menuItem"><a href="#spvone_info">房产及交易信息</a></li>
				<li class="menuItem"><a href="#spvtwo_info">监管资金及账户信息</a></li>
				<li class="menuItem"><a href="#spvthree_info">资金方案填写</a></li>
				<li class="menuItem"><a href="#spvfour_info">上传备件</a></li>
			</ul>
			<c:if test="${empty handle or handle eq 'SpvApply'}">
			<div class="menu_btn"
				style="margin-left: 960px; margin-top: 7px;">
				<button id="saveBtn" class="btn btn-warning">保存</button>
			</div>
			</c:if>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
			                    <!-- <div class="ibox"> -->
			      <c:if test="${empty handle or handle eq 'SpvApply'}">
                    <div class="ibox-content" id="case_info">
                        <div class="main_titile" style="position: relative;">
                            
                            <h5>关联案件<button type="button" id="link_btn" class="btn btn-success btn-blue" data-toggle="modal" data-target="#myModal" onClick="showPop();">关联案件</button></h5>
						    <div class="case_content" ${empty caseCode?'style="display:none;"':''}>
						    
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>案件编号</em><span class="span_one" id="content_caseCode">${caseInfoMap['caseCode']}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>产证地址</em><span class="span_two" id="content_propertyAddr">${caseInfoMap['propertyAddr']}</span></p>
                               </div>
                           </div>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>交易顾问</em><span class="span_one" id="content_processorId">${caseInfoMap['processorName']}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>上家姓名</em><span class="span_two" id="content_seller">${caseInfoMap['sellerName']}</span></p>
                               </div>
                           </div>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>经纪人</em><span class="span_one" id="content_agentName">${caseInfoMap['agentName']}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>下家姓名</em><span class="span_two" id="content_buyer">${caseInfoMap['buyerName']}</span></p>
                               </div>
                           </div>
                           </div>
                       </div>

                            <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                                 <div class="modal-dialog" style="width: 1070px;">
                                    <div class="modal-content animated fadeIn apply_box ibox-content">
                                        <form action="" class="form_list clearfix">	
                                          <div class="modal_title">
                                                                                                                                           监管合约关联案件
                                            </div>
                                           <div class="line">
					                        <div class="form_content">
					                            <label class="control-label mr10">
					                                   	 案件编码
					                            </label>
					                            <input class="teamcode input_type" value="" placeholder="请输入" id="caseCodet" name="caseCodet" >
					                        </div>
					                        <div class="form_content">
					                            <label class="control-label sign_left">
					                                   	 产证地址
					                            </label>
					                            <input class="input_type" value="" placeholder="请输入" style="width:435px;" id="propertyAddr" name="propertyAddr" >
					                        </div>
					                    </div>
				                    	<div class="line">
					                        <div class="form_content">
					                            <label class="control-label mr10">
					                                     	上家姓名
					                            </label>
					                            <input class="teamcode input_type" value="" placeholder="请输入" id="caseNamet" name="caseNamet" >
					                        </div>
					                        <div class="form_content space">
					                            <div class="add_btn">
					                                <button type="button" class="btn btn-success" id="searchButton"  >
					                                <i class="icon iconfont"></i>
					                                   	 查询
					                                </button>
					                                
					                                <button type="button" class="btn btn-success" id="addNewCase"  >					                                
					                                   	 新增案件
					                                </button>
					                            </div>
					                        </div>
				                    	</div>
                                            
                                        </form>
                                        <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong">
                                                &#xe60a;
                                            </i></button>
                                            <div class="eloanApply-table">
                                            </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<form class="form-inline">
					    <input type="hidden" id="handle" name="handle" value="${handle }">
						<div class="title">买方客户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">							
   						        <input type="hidden" name="spvCustList[0].pkid" value="${spvBaseInfoVO.spvCustList[0].pkid }" />
							    <input type="hidden" name="spvCustList[0].tradePosition" value="BUYER" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input name="spvCustList[0].name" 
								value="${not empty spvBaseInfoVO.spvCustList[0].name?spvBaseInfoVO.spvCustList[0].name:caseInfoMap['buyerName'] }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 手机号码</label> <input name="spvCustList[0].phone"
								    value="${not empty spvBaseInfoVO.spvCustList[0].phone?spvBaseInfoVO.spvCustList[0].phone:caseInfoMap['buyerMobil'] }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one"><i style="color:red;">*</i> 性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input 
										type="radio" name="spvCustList[0].gender" id="sex1" value="1" ${spvBaseInfoVO.spvCustList[0].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input 
								        type="radio" name="spvCustList[0].gender" id="sex2" value="0" ${spvBaseInfoVO.spvCustList[0].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict id="spvCustList[0].idType" name="spvCustList[0].idType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[0].idType }"></aist:dict>
								
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件有效期</label> 
								<input id="date-picker0" name="spvCustList[0].idValiDate" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[0].idValiDate }" pattern="yyyy-MM-dd"/>" placeholder="">
								<button id="longTerm0" type="button" class="btn btn-grey" checkFlag="false">长期有效</button>
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input name="spvCustList[0].idCode"
								value="${spvBaseInfoVO.spvCustList[0].idCode }"
								type="text" class="form-control input-two" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[0].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[0].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 家庭地址</label> <input name="spvCustList[0].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[0].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio1" value="1" ${spvBaseInfoVO.spvCustList[0].hasDele eq '1'?'checked="checked"':'' } > 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio2" value="0" ${spvBaseInfoVO.spvCustList[0].hasDele ne '1'?'checked="checked"':'' }> 否
								</label>
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[0].agentName"
								    value="${spvBaseInfoVO.spvCustList[0].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict  id="spvCustList[0].agentIdType" name="spvCustList[0].agentIdType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[0].agentIdType }"></aist:dict>
								
							</div>
							<div class="form-group form-margin form-space-one buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[0].agentIdCode"
								    value="${spvBaseInfoVO.spvCustList[0].agentIdCode }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
					<form class="form-inline" >
						<div class="title">卖方客户信息</div>

						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="spvCustList[1].pkid" value="${spvBaseInfoVO.spvCustList[1].pkid }" />
							    <input type="hidden" name="spvCustList[1].tradePosition" value="SELLER" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[1].name"
								     value="${not empty spvBaseInfoVO.spvCustList[1].name?spvBaseInfoVO.spvCustList[1].name:caseInfoMap['sellerName'] }"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 手机号码</label> <input name="spvCustList[1].phone"
								    value="${not empty spvBaseInfoVO.spvCustList[1].phone?spvBaseInfoVO.spvCustList[1].phone:caseInfoMap['sellerMobil'] }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one"><i style="color:red;">*</i> 性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input 
										type="radio" name="spvCustList[1].gender" id="sex3" value="1" ${spvBaseInfoVO.spvCustList[1].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input type="radio"
										name="spvCustList[1].gender" id="sex4" value="0" ${spvBaseInfoVO.spvCustList[1].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict id="spvCustList[1].idType" name="spvCustList[1].idType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[1].idType }"></aist:dict>
								
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件有效期</label> 
								<input id="date-picker1" name="spvCustList[1].idValiDate" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[1].idValiDate }" pattern="yyyy-MM-dd"/>" placeholder="">
								<button id="longTerm1" type="button" class="btn btn-grey" checkFlag="false">长期有效</button>
							</div>

						</div>
						<div class="form-row form-rowbot">
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[1].idCode"
								    value="${spvBaseInfoVO.spvCustList[1].idCode }"
									class="form-control input-two" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[1].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[1].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 家庭地址</label> <input name="spvCustList[1].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[1].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>	
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio1" value="1" ${spvBaseInfoVO.spvCustList[1].hasDele eq '1'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio2" value="0" ${spvBaseInfoVO.spvCustList[1].hasDele ne '1'?'checked="checked"':'' }> 否
								</label>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[1].agentName"
								    value="${spvBaseInfoVO.spvCustList[1].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
										<aist:dict  id="spvCustList[1].agentIdType" name="spvCustList[1].agentIdType" clazz="form-control input-one"
									display="select"   dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[1].agentIdType }"></aist:dict>
							</div>
							<div class="form-group form-margin form-space-one sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[1].agentIdCode"
								    value="${spvBaseInfoVO.spvCustList[1].agentIdCode }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvone_info">
					<form class="form-inline">
						<div class="title">房产及交易信息</div>
						<div class="form-row form-rowbot clear">
						<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 房产权利人</label> <input name="toSpvProperty.prOwnerName" type="text"
									class="form-control input-one" placeholder="" value="${not empty spvBaseInfoVO.toSpvProperty.prOwnerName?spvBaseInfoVO.toSpvProperty.prOwnerName:caseInfoMap['sellerName'] }">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvProperty.pkid" value="${spvBaseInfoVO.toSpvProperty.pkid }" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 房产证号</label> <input name="toSpvProperty.prNo" type="text"
									class="form-control input-five" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prNo }">
							</div>
							
							<!-- <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托人姓名</label> <input  type="text"
									class="form-control input-one" placeholder="" value="">
							</div> -->
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 面积</label> <input name="toSpvProperty.prSize" type="text"
									class="form-control input-one" placeholder="" value="${not empty spvBaseInfoVO.toSpvProperty.prSize?spvBaseInfoVO.toSpvProperty.prSize:caseInfoMap['propertySquare'] }"> 
									<span class="date_icon">㎡</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 产证地址</label> <input name="toSpvProperty.prAddr" type="text"
									class="form-control input-five" placeholder="" value="${not empty spvBaseInfoVO.toSpvProperty.prAddr?spvBaseInfoVO.toSpvProperty.prAddr:caseInfoMap['propertyAddr'] }">
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-two">是否偿清</label> <label
									class="radio-inline"> <input type="radio"
									name="toSpvProperty.isMortClear" id="Pledge1" value="1" ${spvBaseInfoVO.toSpvProperty.isMortClear ne '0'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio" 
									name="toSpvProperty.isMortClear" id="Pledge2" value="0" ${spvBaseInfoVO.toSpvProperty.isMortClear eq '0'?'checked="checked"':'' }> 否
								</label> <span class="span-tag pledgeinfo">交易房屋抵押贷信息</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 抵押方</label> <input type="text" name="toSpvProperty.mortgageeName"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.mortgageeName }">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label> <input name="toSpvProperty.mortgageeBank"
								value="${spvBaseInfoVO.toSpvProperty.mortgageeBank }" type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">未偿还金额</label> <input name="toSpvProperty.leftAmount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpvProperty.leftAmount }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""><span
									class="date_icon">万元</span>
							</div>
                            <div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="leftAmountDX"  disabled="disabled"
									class="form-control input-two" placeholder="">
							</div>	 	
						</div>
						<div class="form-row form-rowbot">

							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 网签合同号</label> <input name="toSpvProperty.signNo"
								value="${spvBaseInfoVO.toSpvProperty.signNo }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<!-- <div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div> -->
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 网签金额</label> <input name="toSpvProperty.signAmount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpvProperty.signAmount }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
							<label for="" class="lable-one">金额大写</label> <input type="text" id="signAmountDX"  disabled="disabled"
								class="form-control input-two" placeholder="">
						    </div> 
						</div>	


					</form>
				</div>
				<div class="ibox-content" id="spvtwo_info">
					<form class="form-inline">
					    <input type="hidden" name="toSpv.pkid" value="${spvBaseInfoVO.toSpv.pkid }"/>
					    <input type="hidden" name="toSpv.caseCode" value="${caseCode }"/>
					    <input type="hidden" name="toSpv.spvCode" value="${spvBaseInfoVO.toSpv.spvCode }"/>
					    <input type="hidden" name="toSpv.applyTime" value="${spvBaseInfoVO.toSpv.applyTime }" />
						<div class="title">监管资金及账户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管总金额</label> <input name="toSpv.amount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amount}" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountDX" disabled="disabled"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管产品</label>
									<select name="toSpv.prdCode" class="form-control input-two" value="${spvBaseInfoVO.toSpv.prdCode }">
									<option value="1" ${spvBaseInfoVO.toSpv.prdCode eq 1?'selected="selected"':''}>光大四方资金监管</option>
									<option value="2" ${spvBaseInfoVO.toSpv.prdCode eq 2?'selected="selected"':''}>光大三方资金监管</option>
									</select>
									<%-- <select id="prd" class="form-control input-one"></select>
									<select name="toSpv.prdCode" class="form-control input-two" value="${spvBaseInfoVO.toSpv.prdCode }"></select> --%>
							</div>
						</div>
						<div class="title">监管资金的支付</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 下家付款方式</label>
								<aist:dict id="toSpv.buyerPayment" name="toSpv.buyerPayment" clazz="form-control input-one"
									display="select"  dictType="SPV_BUYER_PAYMENT"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.toSpv.buyerPayment }"></aist:dict>
							</div>
						</div>
						<div class="form-row form-rowbot">	
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">自筹资金</label> <input name="toSpv.amountOwn"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountOwn }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountOwnDX" disabled="disabled"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">贷款资金</label> <input name="toSpv.amountMort"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMort }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder="">
									<span class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortDX"  disabled="disabled"
									class="form-control input-two" placeholder="">
							</div>		
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one" style="margin-left:28px;">
								<label for="" class="rate">其中： 商业贷款</label> <input name="toSpv.amountMortCom"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMortCom }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortComDX"  disabled="disabled"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one" style="margin-left:55px;">
								<label for="" class="rate">公积金贷款</label> <input name="toSpv.amountMortPsf"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMortPsf }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortPsfDX"  disabled="disabled"
									class="form-control input-two" placeholder="">
						    </div>
						</div>
						<div class="title">资金监管账号信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[1].pkid" value="${spvBaseInfoVO.toSpvAccountList[1].pkid }"/>
							    <input type="hidden" name="toSpvAccountList[1].accountType" value="SELLER" />
								<label for="" class="lable-one">卖方收款账号名称</label> <input name="toSpvAccountList[1].name"
								 value="${spvBaseInfoVO.toSpvAccountList[1].name }" onBlur="updateAccTypeOptions()"
								 type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">账号</label> <input name="toSpvAccountList[1].account"
								    value="${spvBaseInfoVO.toSpvAccountList[1].account }"  type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
						<div class="form-group form-margin form-space-one">
							<label for="" class="lable-one">电话</label> <input name="toSpvAccountList[1].telephone"
							    value="${spvBaseInfoVO.toSpvAccountList[1].telephone }" type="text"
								class="form-control input-one" placeholder="">
						</div>
						<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">开户行</label>
									<select id="bank_1" name="toSpvAccountList[1].bank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[1].bank }" ></select>
									<select name="toSpvAccountList[1].branchBank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[1].branchBank }" ></select>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[0].pkid" value="${spvBaseInfoVO.toSpvAccountList[0].pkid }"/>
								<input type="hidden" name="toSpvAccountList[0].accountType" value="BUYER" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 买方退款账号名称</label> <input name="toSpvAccountList[0].name"
								    value="${spvBaseInfoVO.toSpvAccountList[0].name }" onBlur="updateAccTypeOptions()"
									type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input name="toSpvAccountList[0].account"
								    value="${spvBaseInfoVO.toSpvAccountList[0].account }" type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 电话</label> <input name="toSpvAccountList[0].telephone"
								    value="${spvBaseInfoVO.toSpvAccountList[0].telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>	
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label>
									<select id="bank_0" name="toSpvAccountList[0].bank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[0].bank }" ></select>
									<select name="toSpvAccountList[0].branchBank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[0].branchBank }" ></select>
							</div>	
						</div>
						
						<div id="spvAccountDiv" class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[2].pkid" value="${spvBaseInfoVO.toSpvAccountList[2].pkid }"/>
							    <input type="hidden" name="toSpvAccountList[2].accountType" value="SPV" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 托管账户名称</label> 
								<select name="toSpvAccountList[2].name" <%-- value="${spvBaseInfoVO.toSpvAccountList[2].name }" --%> class="form-control input-two">
								<option value="上海中原物业顾问有限公司">上海中原物业顾问有限公司</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input type="text" name="toSpvAccountList[2].account" disabled="disabled"
								    <%-- value="${spvBaseInfoVO.toSpvAccountList[2].account }" --%> value="76310188000148842"
									class="form-control input-two" placeholder="">
									<input type="hidden" name="toSpvAccountList[2].account" value="76310188000148842">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[3].pkid" value="${spvBaseInfoVO.toSpvAccountList[3].pkid }"/>
								<input type="hidden" name="toSpvAccountList[3].accountType" value="FUND" />
								<label for="" class="lable-one">资金方账户名称</label>
								<select name="toSpvAccountList[3].name" value="${spvBaseInfoVO.toSpvAccountList[3].name }" class="form-control input-two">
								<option value="">请选择</option>
								<option value="搜易贷（北京）金融信息服务有限公司" ${spvBaseInfoVO.toSpvAccountList[3].name eq '搜易贷（北京）金融信息服务有限公司'?'selected="selected"':'' }>搜易贷（北京）金融信息服务有限公司</option>
								<option value="上海嘉定及时雨小额贷款股份有限公司" ${spvBaseInfoVO.toSpvAccountList[3].name eq '上海嘉定及时雨小额贷款股份有限公司'?'selected="selected"':'' }>上海嘉定及时雨小额贷款股份有限公司</option>
								</select>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text" name="toSpvAccountList[3].account"  disabled="disabled"
								    value="${spvBaseInfoVO.toSpvAccountList[3].account }"
									class="form-control input-two" placeholder="">
									<input type="hidden" name="toSpvAccountList[3].account" value="${spvBaseInfoVO.toSpvAccountList[3].account }">
							</div>
							<div class="form-group form-margin form-space-one">
							    <label for="" class="lable-one">开户行</label> <input type="text" name="toSpvAccountList[3].branchBank"  disabled="disabled"
								    value="${spvBaseInfoVO.toSpvAccountList[3].branchBank }"
									class="form-control input-three" placeholder="">
									<input type="hidden" name="toSpvAccountList[3].branchBank" value="${spvBaseInfoVO.toSpvAccountList[3].branchBank }">
							</div>
						</div>
							
						<c:if test="${empty handle or handle eq 'SpvApply' }">
						<div class="form-row form-rowbot">
						<div class="form-group form-margin form-space-one">
								<a onClick="getAccTr()">添加账户</a>
						</div>	
						</div>
						</c:if>
						
						<c:if test="${fn:length(spvBaseInfoVO.toSpvAccountList) gt 4}">
						<c:forEach items="${spvBaseInfoVO.toSpvAccountList }" var="toSpvAccount" varStatus="status4">
						<c:if test="${status4.index gt 3 }">
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[${status4.index }].pkid" value="${toSpvAccount.pkid }"/>
								<input type="hidden" name="toSpvAccountList[${status4.index }].accountType" value="CUSTOM_${status4.index }" />
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号名称</label> <input name="toSpvAccountList[${status4.index }].name"
								    value="${toSpvAccount.name }" onBlur="updateAccTypeOptions()"
									type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input name="toSpvAccountList[${status4.index }].account"
								    value="${toSpvAccount.account }" type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 电话</label> <input name="toSpvAccountList[${status4.index }].telephone"
								    value="${toSpvAccount.telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>	
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label>
									<select id="bank_${status4.index }" name="toSpvAccountList[${status4.index }].bank" class="form-control input-two"  value="${toSpvAccount.bank }" ></select>
									<select name="toSpvAccountList[${status4.index }].branchBank" class="form-control input-two" value="${toSpvAccount.branchBank }" ></select>
									<c:if test="${empty handle or handle eq 'apply' }">
									&nbsp;&nbsp;&nbsp;<a onClick="delAccTr(this)">删除账户</a>
									</c:if>
							</div>	
						</div>
						</c:if>
						</c:forEach>
						</c:if>
				
 						<div id="spvAccDiv" class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one">
						        <label for="" class="lable-one"><i style="color:red;">*</i> 风控专员</label>
						        <input type="hidden" id="userName" name="toSpv.riskControlOfficer" value='${spvBaseInfoVO.toSpv.riskControlOfficer }'>
						        <input type="text" id="realName"  style="background-color:#FFFFFF" readonly="readonly" class="form-control" id="txt_proOrgId_gb" onclick="userSelect({startOrgId:'${orgId}',expandNodeId:'${orgId}',
												jobCode:'JYFKZY',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" value='${riskControlOfficerName }'>
							<div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
					    </div> 
					    
						<div class="form-row form-rowbot" id="signDiv" style="display:none;">
						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管合同号</label> <input type="text" name="toSpv.spvConCode"
								    value="${spvBaseInfoVO.toSpv.spvConCode }"
									class="form-control input-two" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 签约时间</label> 
								<input id="date-picker2" name="toSpv.signTime" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>" placeholder="">
							</div>
						</div>
									
					<div class="form-row form-rowbot" id="passOrRefuseReasonForShow" ${handle eq 'SpvApprove' or handle eq 'SpvAudit'?'':'style="display:none;"'}>						
						<div class="form-group form-margin form-space-one">
							<label class="lable-one"  style="text-align: right;"><i style="color:red;">*</i> 审批意见</label>							
							<div class="form-group form-margin form-space-one left-extent" >
								<textarea class="form-control input-five" rows="2"  id="passOrRefuseReason"	name="passOrRefuseReason"></textarea>
							</div>
						</div>
					</div>
					</form>
				</div>
				<div class="ibox-content" id="spvthree_info" >
					<div class="agree-tile">资金出款约定</div>
					<form class="form-inline table-capital">
						<p>
							监管资金次数合计<span id="sum"> ${fn:length(spvBaseInfoVO.toSpvDeDetailList)}</span> 次
						</p>
						<div class="table-box">
							<table class="table table-bordered customerinfo">
								<thead>
									<th>划转条件</th>
									<th>账户</th>
									<th style="width: 16%;">金额</th>
									<th>备注</th>
									<th>操作</th>
								</thead>					
								<tbody id="addTr">
								  <input type="hidden" id="toSpvDeDetailListSize" value="${fn:length(spvBaseInfoVO.toSpvDeDetailList)}" />
								  <input type="hidden" name="ToSpvDe.pkid" value="${spvBaseInfoVO.toSpvDe.pkid}" />
								  <c:forEach items="${spvBaseInfoVO.toSpvDeDetailList }" var="toSpvDeDetail" varStatus="status">
									<tr align="center">
										<td class="text-left">
										<input type="hidden" name="toSpvDeDetailList[${status.index }].pkid" value="${toSpvDeDetail.pkid}" />
										<aist:dict id="toSpvDeDetailList[${status.index }].deCondCode" name="toSpvDeDetailList[${status.index }].deCondCode" clazz="form-control input-one"
									    display="select"  dictType="SPV_DE_COND"  
									    ligerui='none' defaultvalue="${toSpvDeDetail.deCondCode }"></aist:dict>	
										</td>
										<td class="text-left">
									    <select name="toSpvDeDetailList[${status.index }].payeeAccountType" value="${toSpvDeDetail.payeeAccountType }" class="table-select" onChange="this.value" >
									    </select>
										</td>
										<td><input name="toSpvDeDetailList[${status.index }].deAmount" value="<fmt:formatNumber type="number" value="${toSpvDeDetail.deAmount }" pattern="0.00" maxFractionDigits="2"/>" class="table-input-one" type="text"
											placeholder="请输入金额" />万元</td>
										<td class="text-left"><input name="toSpvDeDetailList[${status.index }].deAddition" value="${toSpvDeDetail.deAddition }" class="table-input"
											type="text" placeholder="" /></td>
										<td align="center">
										<c:if test="${empty handle  or handle eq 'SpvApply'}">
										<a href="javascript:void(0)" onClick="getAtr(this)">添加</a>
										<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a>
										</c:if>
									</tr>
								   </c:forEach>
								</tbody>					
							</table>							
						</div>
					</form>
				</div>
				
				<div class="ibox-content" id="spvfour_info" >
				<div style="height: auto;">
				<c:choose>
					<c:when test="${accesoryList!=null}">
						<h5>上传备件</h5>
						<div class="table-box" id="fileUploadContainer"></div>		
					</c:when>
					<c:otherwise>
						<h5>
							上传备件<br>无需上传备件
						</h5>
					</c:otherwise>
				</c:choose>
				</div>
				<div class="ibox-content" id="spvthree_info" >			
							<div class="form-btn">						
							<c:if test="${handle eq 'SpvApply' }">
							    <div>
									<a id="consultantApply" class="btn btn-success">提交申请</a>
									<a onclick="rescCallback()" class="btn btn-default">取消</a>
								</div>
							</c:if>
							
							<c:if test="${handle eq 'SpvAudit' }">
							    <div>
									<a id="riskOfficerApproveY" class="btn btn-success">通过</a>
									<a id="riskOfficerApproveN" class="btn btn-success">驳回</a>
									<a onclick="rescCallback()" class="btn btn-default">取消</a>
								</div>
							</c:if>
							
							<c:if test="${handle eq 'SpvApprove' }">
							    <div>
									<a id="riskDirectorApproveY" class="btn btn-success">通过</a>
									<a id="riskDirectorApproveN" class="btn btn-success">驳回</a>
									<a onclick="rescCallback()" class="btn btn-default">取消</a>
								</div>
							</c:if>
													
							<c:if test="${handle eq 'SpvSign' }">
							    <div>
									<a id="RiskOfficerSign" class="btn btn-success">提交签约</a>
									<a onclick="rescCallback()" class="btn btn-default">取消</a>
								</div>
							</c:if>
							
							<c:if test="${empty handle}">
							    <div>
									<a id="submitBtn" class="btn btn-success">提交申请</a>
									<a onclick="rescCallback()" class="btn btn-default">取消</a>
								</div>
							</c:if>			
							</div>
				</div>
				</div>
			</div>
		</div>
		<!-- main End -->

	</div>

    

	<!-- Mainly scripts -->
	<content tag="local_script"> 
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> <script
		src="${ctx}/static/js/morris/morris.js"></script> <script
		src="${ctx}/static/js/morris/raphael-min.js"></script> <!-- index_js -->
	<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- 上传附件相关 --> <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> <!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
	<script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script> 
	<script src="${ctx}/js/jquery.editable-select.min.js"></script>
	<script src="${ctx}/static/trans/js/spv/spvDetails.js"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
	<script src="${ctx}/static/tbsp/js/userorg/userOrgSelect.js" type="text/javascript"></script>
	<!-- 引入弹出框js文件 -->
    <script src="${ctx}/js/common/xcConfirm.js?v=1.0.1"></script>
	<script src="${ctx}/js/viewer/viewer.min.js"></script>	

		<script id="queryCastListItemList2" type= "text/html">
        {{each rows as item index}}
    	<tr>
        <td>
            <p class="big">
                <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
                   <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
                </a>
            </p>
            <p>
                <i class="tag_sign">c</i>
                 {{item.ctmCode}}
            </p>
        </td>
        <td>
            <p class="big">
           		<span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
            </p>
            <p class="tooltip-demo">
                <i class="salesman-icon">
                </i>
                <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title="">
                	{{item.AGENT_NAME}}/{{item.AGENT_ORG_NAME}}
                </a>
            </p>
        </td>
        <td>
            <p class="name">
                <span>交易顾问：</span><a href="#" class="a_blue" id="modal_processorId{{index}}">{{item.FONT_NAME}}</a>
            </p>
            
        </td>
        <td class="center">
            <p class="big">
           		<span id="modal_seller{{index}}" title="{{item.SELLER}}">{{item.SELLER.substring(0,item.SELLER.length >10?10:item.SELLER.length).concat(item.SELLER.length >10?'...':'')}}</span>
            </p>
        </td>
        <td class="center">
            <p class="big">
          	   <span id="modal_buyer{{index}}" title="{{item.BUYER}}">{{item.BUYER.substring(0,item.BUYER.length >10?10:item.BUYER.length).concat(item.BUYER.length >10?'...':'')}}</span>
            </p>
        </td>
        <td class="text-left">
            <button type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}">
                              关联
            </button>
        </td>
    </tr>
    {{/each}}		
		</script>
		<script>
		/**取最大索引 */
        var accTypeSum;//账户类型 
		
		$(document).ready(function(){
			
			$("select[name='toSpv.prdCode']").change(function(){
				if($(this).val() == 2){
					$("#spvAccountDiv").hide();
				}else{
					$("#spvAccountDiv").show();
				}
			}).change();
			
			longTermSuit(0);
			longTermSuit(1);

			accTypeSum = parseInt('${fn:length(spvBaseInfoVO.toSpvAccountList)}');
			if(accTypeSum == 0 || accTypeSum == 4){
				accTypeSum = 4;
			}else{
				var max = 0;
				$("input[name^='toSpvAccountList'][name$='accountType'][value^='CUSTOM_']").each(function(i,e){
					var index = parseInt($(e).val().replace('CUSTOM_',''));
					if(index > max){
						max = index;
					}
				});
				accTypeSum = max+1;
			}
			 
			 $("select[id^='bank_']").each(function(i,e){
				 initBankList(e);
			 });	

			 //更新账户类型下拉选 
			 updateAccTypeOptions();
	
			 //当前用户标示 前者是风控专员，后者是风控总监
			 var handle = $("#handle").val();										 
			/*签约环节需添加的内容：资金监管协议编号、签约时间
	                         签约环节需可修改的内容：卖方监管账户名称、卖方监管账号、开户行*/
			if($("#handle").val() == 'SpvSign'){
  				$("input[name='toSpvAccountList[1].name']").prop("disabled",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
  				$("input[name='toSpvAccountList[1].account']").prop("disabled",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
  				$("input[name='toSpvAccountList[1].telephone']").prop("disabled",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
  				$("#bank_1").prop("disabled",false);
  				$("input[name='toSpvAccountList[1].bank']").prop("disabled",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
  				$("input[name='toSpvAccountList[1].branchBank']").prop("disabled",false);
  				$("#signDiv").show().find("input").prop("disabled",false);
	         }
			
			$("select[name='toSpvAccountList[3].name']").change(function(){
				var val = $(this).val();
				switch(val){
				case '':
					$("input[name='toSpvAccountList[3].account']").val("");
					$("input[name='toSpvAccountList[3].branchBank']").val("");
					break;
				case '搜易贷（北京）金融信息服务有限公司':
					$("input[name='toSpvAccountList[3].account']").val("137441512010000275");
					$("input[name='toSpvAccountList[3].branchBank']").val("广发银行股份有限公司北京石景山支行");
					break;
				case '上海嘉定及时雨小额贷款股份有限公司':
					$("input[name='toSpvAccountList[3].account']").val("457263590104");
					$("input[name='toSpvAccountList[3].branchBank']").val("中行上海南京西路支行");
					break;
				}
				updateAccTypeOptions();
			}).change();

			$("input[name='toSpv.amount']").blur(function(){
				$("#amountDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpv.amountOwn']").blur(function(){
				$("#amountOwnDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpv.amountMort']").blur(function(){
				$("#amountMortDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpv.amountMortCom']").blur(function(){
				$("#amountMortComDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpv.amountMortPsf']").blur(function(){
				$("#amountMortPsfDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpvProperty.signAmount']").blur(function(){
				$("#signAmountDX").val(DX($(this).val()*10000));
			}).blur();
			$("input[name='toSpvProperty.leftAmount']").blur(function(){
				$("#leftAmountDX").val(DX($(this).val()*10000));
			}).blur();			
	       	
	       	/* getPrdCategory($("#prd"),$("select[name='toSpv.prdCode']"),'${spvBaseInfoVO.toSpv.prdCode }');
	       	$("#prd").change(function(){
	       		getPrdDetail($("select[name='toSpv.prdCode']"),$("#prd option:selected").val());
		    }); */ 

			$(".eloanApply-table").aistGrid({
    			ctx : "${ctx}",
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList2',
    		    rows : '6',
    		    gridClass : 'table table_blue table-striped table-bordered table-hover',
    		    data : '',
    		    wrapperData :{ctx: ctx},
    		    columns : [{
    		    	           colName :"案件编号",
    		    	           sortColumn : "CASE_CODE",
    		    	           sord: "desc",
    		    	           sortActive : true
    		    	      },{
    		    	           colName :"产证地址"
    		    	      },{
   		    	                colName :"工作人员"
		    	          },{
	   		    	           colName :"上家"  	           
			    	      },{
			    	           colName :"下家"
			    	      },{
    		    	           colName :"操作"
    		    	      }]
    		
    		}); 
    		
    	 	// 查询
 			$('#searchButton').click(function() {
 				reloadGrid();
 			});
    	 	
    	 	// 查询
 			$('#addNewCase').click(function() {
 				window.location.href = ctx+"/caseMerge/addCase/spv";
 			});
    	 	
    	 	// 关联案件
   	 		$('.eloanApply-table').on("click",'.linkCase',function(){	 			
   	 			var index = $(this).attr("id");
   	 		$.ajax({
   	      		url:ctx+"/spv/queryByCaseCode",
   	      		method:"post",
   	      		dataType:"json",
   	      		data:{caseCode:$("#modal_caseCode"+index).html()},   		        				        		    
   	       		beforeSend:function(){  
   					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
   					$(".blockOverlay").css({'z-index':'9998'});
   	            },
   		        complete: function() {
   		                 $.unblockUI(); 
   		                 if(status=='timeout'){ //超时,status还有success,error等值的情况
   			          	  Modal.alert(
   						  {
   						    msg:"抱歉，系统处理超时。"
   						  }); 
   				                } 
   				            } ,   
   				success : function(data) {  
   					        if(data.success){
   					        	var caseInfoMap = eval('('+data.content+')');
   					    		$("#caseCode").val(caseInfoMap['caseCode']);
   					    		fileUpload.init({
							    		caseCode : $('#caseCode').val(),
							    		partCode : "SpvApplyApprove",
							    		fileUploadContainer : "fileUploadContainer"
							    });
   					    		
   					    		$("#content_caseCode").html(caseInfoMap['caseCode']);
   					    		$("#content_propertyAddr").html(caseInfoMap['propertyAddr']);
   					    		$("#content_processorId").html(caseInfoMap['processorName']);
   					    		$("#content_seller").html(caseInfoMap['sellerName']);
   					    		$("#content_agentName").html(caseInfoMap['agentName']);
   					    		$("#content_buyer").html(caseInfoMap['buyerName']);
   					    		$("input[name='toSpv.caseCode']").val(caseInfoMap['caseCode']);

   					    		if($("input[name='spvCustList[0].name']").val() == '')
   					    			$("input[name='spvCustList[0].name']").val(caseInfoMap['buyerName'].substr(0,caseInfoMap['buyerName'].indexOf("/") == -1?caseInfoMap['buyerName'].length:caseInfoMap['buyerName'].indexOf("/")));
   					    		if($("input[name='spvCustList[0].phone']").val() == '')
   					    			$("input[name='spvCustList[0].phone']").val(caseInfoMap['buyerMobil']);
   					    		if($("input[name='spvCustList[1].name']").val() == '')
   					    			$("input[name='spvCustList[1].name']").val(caseInfoMap['sellerName'].substr(0,caseInfoMap['sellerName'].indexOf("/") == -1?caseInfoMap['sellerName'].length:caseInfoMap['sellerName'].indexOf("/")));
   					    		if($("input[name='spvCustList[1].phone']").val() == '')
   					    			$("input[name='spvCustList[1].phone']").val(caseInfoMap['sellerMobil']);	    		
   					    		if($("input[name='toSpvProperty.prOwnerName']").val() == '')
   					    			$("input[name='toSpvProperty.prOwnerName']").val(caseInfoMap['sellerName'].substr(0,caseInfoMap['sellerName'].indexOf("/") == -1?caseInfoMap['sellerName'].length:caseInfoMap['sellerName'].indexOf("/")));
   					    		if($("input[name='toSpvProperty.prSize']").val() == '')
   					    			$("input[name='toSpvProperty.prSize']").val(caseInfoMap['propertySquare']);
   					    		if($("input[name='toSpvProperty.prAddr']").val() == '')
   					    			$("input[name='toSpvProperty.prAddr']").val(caseInfoMap['propertyAddr']);
   					    		
   								$('.case_content').show();
   								$(".close").click();
   								$(".modal-backdrop").hide();
   								
   								//$('#myModal').modal('hide');
   					        }else{
   					        	window.wxc.error("案件关联失败！");
   					        }		    		
   						    
							$.unblockUI();
   					},		
   				error : function(errors) {
   						$.unblockUI();   
   						window.wxc.error("数据保存出错！");
   					}  
   	       });
   	     });		   
        });
        
        function showPop(){
        	$("#myModal").show();
			$(".modal-backdrop").show();
        }
        
        function reloadGrid() {
        	var data = {};
        	var propertyAddr = $.trim($("#propertyAddr").val());
           	var caseCode = $.trim($("#caseCodet").val());
           	var caseName = $.trim($("#caseNamet").val()); 
           	
            data.propertyAddr=propertyAddr;
            data.caseCode=caseCode;
           	data.sname=caseName; 
    	    $(".eloanApply-table").reloadGrid({
    	    	ctx : "${ctx}",
    	    	rows : '6',
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList2',
    		    wrapperData :{ctx : ctx},
    		    data : data
    	    })
    	}
		
		
		var sum = parseInt($("#toSpvDeDetailListSize").val());
		var sum_ = parseInt($("#toSpvDeDetailListSize").val());
		if( sum==0){
			getAtr();
		}
		function getAtr() {
		$str = '';
		$str += "<tr align='center'>";
		$str += "<td class='text-left'><aist:dict id='toSpvDeDetailList["+sum_+"].deCondCode' name='toSpvDeDetailList["+sum_+"].deCondCode' clazz='table-select' display='select'  dictType='SPV_DE_COND' ligerui='none' defaultvalue='' ></aist:dict></td>";
		$str += "<td class='text-left'><select name='toSpvDeDetailList["+sum_+"].payeeAccountType' class='form-control input-two' onChange='this.value'>"+getAccTypeOptions()+"</select></td>";
		$str += "<td><input name='toSpvDeDetailList["+sum_+"].deAmount' class='table-input-one' type='text' placeholder='请输入金额'>万</td>";
		$str += "<td class='text-left' ><input name='toSpvDeDetailList["+sum_+"].deAddition' class='table-input' type='text' placeholder='' /></td>";
		$str += "<td class='btn-height'><a href='javascript:void(0)'  onClick='getAtr(this)'>添加</a>";
		if(sum_ != 0){
			$str += "<a onClick='getDel(this)' class='grey' href='javascript:void(0)'>删除</a></td>";
		}
		
		$str += "</tr>";
		$("#addTr").append($str);
		sum++;
		sum_++;
		$("#sum").html(sum);
		}
		
		function getDel(k) {
		$(k).parents('tr').remove();
		sum--;
		if(sum == 0){
			getAtr();
		}
		$("#sum").html(sum);
	}
		
		/**自定义账户 */
		function getAccTr(){
			$str = '';
			$str += '<div class="form-row form-rowbot">';
			$str += '<div class="form-group form-margin form-space-one left-extent">';
			$str +=     '<input type="hidden" name="toSpvAccountList['+accTypeSum+'].pkid"/>';
			$str += 	'<input type="hidden" name="toSpvAccountList['+accTypeSum+'].accountType" value="CUSTOM_'+accTypeSum+'" />';
			$str += 	'<label for="" class="lable-one"><i style="color:red;">*</i> 账号名称</label> <input name="toSpvAccountList['+accTypeSum+'].name" '; 
			$str += 		'type="text" class="form-control input-one" placeholder=""  onBlur="updateAccTypeOptions()">';
			$str += '</div>';
			$str += '<div class="form-group form-margin form-space-one">';
			$str += 	'<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input name="toSpvAccountList['+accTypeSum+'].account" type="text" ';
			$str += 		'class="form-control input-two" placeholder="">';
			$str += '</div>';			
			$str += '</div>';
			$str += '<div class="form-row form-rowbot">';
			$str += '<div class="form-group form-margin form-space-one">';
			$str += 	'<label for="" class="lable-one"><i style="color:red;">*</i> 电话</label> <input name="toSpvAccountList['+accTypeSum+'].telephone" type="text" ';
			$str += 		'class="form-control input-one" placeholder="">';
			$str += '</div>	';
			$str += '<div class="form-group form-margin form-space-one">';
			$str += 	'<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label>';
			$str += 		'<select id="bank_'+accTypeSum+'" name="toSpvAccountList['+accTypeSum+'].bank" class="form-control input-two"></select> ';
			$str += 		'<select name="toSpvAccountList['+accTypeSum+'].branchBank" class="form-control input-two" ></select>&nbsp;&nbsp;';
			$str +=	'&nbsp;&nbsp;&nbsp;<a onClick="delAccTr(this)">删除账户</a>';
			$str +='</div>';
			$str += '</div>';

			$("#spvAccDiv").before($str);				
			initBankList($("#bank_"+accTypeSum));	
			updateAccTypeOptions();
			
			accTypeSum++;
		}
		
		function delAccTr(this_){
			var deleteComfirm = false;
			var accountType = $(this_).parents('.form-rowbot').prev().find('input[name$="accountType"]').val();
			$("select[name^='toSpvDeDetailList'][name$='payeeAccountType']").each(function(i,e){
				var eVal = $(e).val();
				if(eVal == accountType){
					deleteComfirm = true;
				}
			});
			
			if(deleteComfirm){
				window.wxc.confirm("出款约定中已选择该账户类型，是否确定删除?",{"wxcOk":function(){
					$(this_).parents('.form-rowbot').prev().remove();
					$(this_).parents('.form-rowbot').remove();
					updateAccTypeOptions();
				}});
			}else{
				$(this_).parents('.form-rowbot').prev().remove();
				$(this_).parents('.form-rowbot').remove();
				updateAccTypeOptions();
			}
		}
		
		/**
		 * 选择用户
		 * @param param
		 */
		function userSelect(param){
			var options = {
			        dialogId : "selectUserDialog", //指定别名，自定义关闭时需此参数
			        dialog : { 
						height: 463
					   ,width: 756
					   ,title:'选择用户'
					   ,url: appCtx['aist-uam-web']+'/userOrgSelect/userSelect.html'
					   ,data:param
					   ,buttons: [
			                      { text: '确定', onclick: function (item, dialog) { dialog.frame.save();}},
			                      { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			                   ]
					}
			    };
			openDialog(options);
		} 
		
		/**
		 * 更新input的值
		 */
		function selectUserBack(array){
			if(array && array.length >0){
		        $("#realName").val(array[0].username);
				$("#userName").val(array[0].userId);
			}else{
				$("#realName").val("");
				$("#userName").val("");
			}
		}
		
		/**初始化银行列表 */
		function initBankList(e){	
				var index = $(e).attr("id").replace('bank_','');
				var $select = $("select[name='toSpvAccountList["+index+"].branchBank']"); 
				getParentBank($(e),$select,$select.attr("value"),$(e).attr("value"));
 				
 		        //可变select
 		        $(e).editableSelect({
 					effects: 'slide',
 					filter: false,
 					onSelect: function(element){
 						editSelectFunc(element , index );
 					} 
 			    }); 
 		       
 		       $select.editableSelect({
 					effects: 'slide',
 					filter: false
 			    }); 
 		       
 		       //未选择时加‘请选择’提示
 		       var $input1 = $("#bank_"+index).attr("placeholder","请选择");
 		       var $input2 = $("input[name='toSpvAccountList["+index+"].branchBank']").attr("placeholder","请选择");	       
		}
		
		//长期有效
		function longTermSuit(i){	
			var $idValiDate = $("input[name='spvCustList["+i+"].idValiDate']");
			var $longTerm = $("#longTerm"+i);
			
			if($idValiDate.val() == '3000-01-01'){
				$idValiDate.val('长期有效');
				$longTerm.attr({"checkFlag":"true","class":"btn btn-success mr5"});
			} 
			$idValiDate.blur(function(){
				if($longTerm.attr("checkFlag") == "true"){
					$idValiDate.val('长期有效');
				}else{
					$longTerm.attr({"checkFlag":"false","class":"btn btn-grey"});
				}
            });
			
			$longTerm.click(function(){
				if($longTerm.attr("checkFlag") == "true"){
					$idValiDate.val('');
					$longTerm.attr({"checkFlag":"false","class":"btn btn-grey"});
				}else{
					$idValiDate.val('长期有效');
					$longTerm.attr({"checkFlag":"true","class":"btn btn-success mr5"});
				}
			});
		}
	
        

  
/*******************************************************控件相关*********************************************************************/ 
		//图片查看器控件  
		function renderImg(){		
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer({zIndex:15001});
		}
        // 日期控件
        $('#date-picker0').datepicker({
        	format : 'yyyy-mm-dd',
        	weekStart : 1,
        	autoclose : true,
        	todayBtn : 'linked',
        	language : 'zh-CN'
        }).on('changeDate',function(ev){
        	if($("input[name='spvCustList[0].idValiDate']").val() != '长期有效'){
				$("#longTerm0").attr({"checkFlag":"false","class":"btn btn-grey"});	
			}
        });
        
        $('#date-picker1').datepicker({
        	format : 'yyyy-mm-dd',
        	weekStart : 1,
        	autoclose : true,
        	todayBtn : 'linked',
        	language : 'zh-CN'
        }).on('changeDate',function(ev){
        	if($("input[name='spvCustList[1].idValiDate']").val() != '长期有效'){
				$("#longTerm1").attr({"checkFlag":"false","class":"btn btn-grey"});
			}
        });
        
        $('#date-picker2').datepicker({
        	format : 'yyyy-mm-dd',
        	weekStart : 1,
        	autoclose : true,
        	todayBtn : 'linked',
        	language : 'zh-CN'
        });
        
        //转大写函数 
        var DX = function (num) {  
		  var strOutput = "";  
		  var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
		  num += "00";  
		  var intPos = num.indexOf('.');  
		  if (intPos >= 0)  
		    num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
		  strUnit = strUnit.substr(strUnit.length - num.length);  
		  for (var i=0; i < num.length; i++)  
		    strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
		    return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");  
		};
		
        //可变下拉选选择li时回调函数 
       	function editSelectFunc(element , index ) {
				var $nextUL = $(element).parent().next();
				var $input = $("input[name='toSpvAccountList["+index+"].branchBank']");
				$nextUL.empty();
			    $input.val('');	
				$.ajax({
					cache:true,
				    url:ctx+"/manage/queryBankListByParentCode",
				    method:"post",
				    dataType:"json",
					async:false,
				    data:{faFinOrgCode:$(element).val()},
		    	success:function(data){
			    	for(var i = 0;i<data.length;i++){
			    		var $li = $("<li coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</li>");
			    		$li.mouseover(function(){
			    			$(this).addClass("selected");
			    		}).mouseleave(function(){
			    			$(this).removeClass("selected");
			    		}).click(function(){
			    			$input.val($(this).text());
			    		});
			    		$nextUL.append($li);
			    	}	    	
		    	}
		    });
		} 

		</script> 
		</content>
		
		<content tag="local_require">
	       <script>
	       		var fileUpload;
			    require(['main'], function() {
					requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','blockUI','steps','ligerui','aistJquery','poshytip','twbsPagination','bootstrapModal','modalmanager','eselect'],function($,aistFileUpload){
						fileUpload = aistFileUpload;
						var handle = $("#handle").val();
						if(handle == "SpvAudit" || handle == "SpvApprove" || handle == "SpvSign"){
							fileUpload.init({
					    		caseCode : $('#caseCode').val(),
					    		partCode : "SpvApplyApprove",
					    		fileUploadContainer : "fileUploadContainer",
					    		readonly : true
					    	});
						}else if(handle == "" || handle == "SpvApply"){
							if($('#caseCode').val() != ''){
								fileUpload.init({
						    		caseCode : $('#caseCode').val(),
						    		partCode : "SpvApplyApprove",
						    		fileUploadContainer : "fileUploadContainer"
						    	});
							}
						}
				    });
			    });
			</script>
	    </content>     

</body>

</html>