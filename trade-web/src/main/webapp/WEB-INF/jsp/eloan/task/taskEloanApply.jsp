<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>e+产品</title>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">

<!-- stickUp fixed css -->
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/trans/css/common/stickDash.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- index_css  -->

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
</head>


<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<!-- main Start -->
		<div class="row wrapper border-bottom nav_heading">
			<ul class="nav_bar">
				<li class="active">贷款申请任务</li>
				<li class="">面签任务</li>
				<li class="">贷款放款任务</li>
			</ul>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<div class="main_titile" style="position: relative;">
						<h5>
							关联案件
							<button type="button" id="link_btn"
								class="btn btn-success btn-blue" data-toggle="modal"
								data-target="#myModal">关联案件</button>
						</h5>
						<c:if test="${taskId==null}">
							<div class="case_content" style="display: none;">
						</c:if>
						<c:if test="${taskId!=null}">
							<div class="case_content">
						</c:if>
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>案件编号</em><span class="span_one" id="content_caseCode">${caseCode}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>产权地址</em><span class="span_two" id="content_propertyAddr">${propertyAddr}</span>
								</p>
							</div>
						</div>
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>交易顾问</em><span class="span_one" id="content_processorId">${processorName}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>上家姓名</em><span class="span_two" id="content_seller">${sellerName}</span>
								</p>
							</div>
						</div>
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>经纪人</em><span class="span_one" id="content_agentName">${agentName}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>下家姓名</em><span class="span_two" id="content_buyer">${buyerName}</span>
								</p>
							</div>
						</div>
					</div>

					<div class="modal inmodal in" id="myModal" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" style="width: 1070px;">
							<div class="modal-content animated fadeIn apply_box info_box">
								<form action="" class="form_list clearfix">
									<div class="modal_title">e+产品关联案件</div>
									<!-- <div class="form_tan">
                                                <label class="control-label" style="width:60px;">
                                                    	产证地址
                                                </label>
                                                <input class="sign_right input_type" placeholder="请输入" value="" id="propertyAddr" name="propertyAddr">
                                            </div>
                                            <div class="form_tan tan_space">
                                                <div class="add_btn">
                                                    <button type="button" class="btn btn-success" id="searchButton">
                                                        <i class="icon iconfont">&#xe635;</i>&nbsp;查询
                                                    </button>
                                                </div>
                                            </div> -->

									<div class="line">
										<div class="form_content">
											<label class="control-label mr10"> 案件编码 </label> <input
												class="teamcode input_type" value="" placeholder="请输入"
												id="caseCodet" name="caseCodet">
										</div>
										<div class="form_content">
											<label class="control-label sign_left"> 产证地址 </label> <input
												class="input_type" value="" placeholder="请输入"
												style="width: 435px;" id="propertyAddr" name="propertyAddr">
										</div>
									</div>
									<div class="line">
										<div class="form_content">
											<label class="control-label mr10"> 上家姓名 </label> <input
												class="teamcode input_type" value="" placeholder="请输入"
												id="caseNamet" name="caseNamet">
										</div>
										<div class="form_content space">
											<div class="add_btn">
												<button type="button" class="btn btn-success"
													id="searchButton">
													<i class="icon iconfont"></i> 查询
												</button>
												
												<button type="button" class="btn btn-success"
													id="addNewCase">新增案件
												</button>
											</div>
										</div>
									</div>

								</form>
								<button type="button" class="close close_blue"
									data-dismiss="modal">
									<i class="iconfont icon_rong"> &#xe60a; </i>
								</button>
								<div class="eloanApply-table"></div>

							</div>
						</div>
					</div>
				</div>

			</div>

			<div class="ibox-content" id="zj_info">
				<form method="get" class="form_list" id="eloanApplyForm">
					<input type="hidden" name="caseCode" id="caseCode" value="${caseCode}" /> 
					<input type="hidden" id="excutorId" name="excutorId" value="${excutorId}" />
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"> 
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<input type="hidden" id="eloanCode" name="eloanCode" value="${eloanCase.eloanCode}">
					<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${eloanCase.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" >
					<input type="hidden" id="createBy" name="createBy" value="${eloanCase.createBy}">
					
					<ul class="form_lump">
						<div class="modal_title title-mark">
                                	产品信息
                        </div>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									style="color:red">* </i> 产品类型
								</label>
								<aist:dict id="loanSrvCode" name="loanSrvCode"
									clazz="select_control sign_right_two" display="select"
									dictType="yu_serv_cat_code_tree" tag="eplus,Eloan" ligerui='none'
									defaultvalue="${eloanCase.loanSrvCode}"></aist:dict>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									style="color:red">* </i>合作机构
								</label> <select class="select_control sign_right_two" name="finOrgCode"
									id="finOrgCode" value="${eloanCase.finOrgCode}">
								</select>
							</div>
						</li>
						
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 申请金额</label> 
								<input class="input_type sign_right_two" value="${eloanCase.applyAmount}" name="applyAmount" id="applyAmount">
								<div class="input-group date_icon">
									<span class="danwei">万</span>
								</div>
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 申请时间</label> 
								<input class="input_type sign_right_two" value="<fmt:formatDate value="${eloanCase.applyTime}" pattern="yyyy-MM-dd" />" name="applyTime" id="applyTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 申请期数</label> 
								<input class="input_type sign_right_two" value="${eloanCase.month}" name="month" id="month">
								<div class="input-group date_icon">
									<span class="danwei">月</span>
								</div>
							</div>
						</li>
						
						<li>
							<div class="form_content" style="position: relative;">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 信贷员</label> 
								<input type="text" name="loanerName" id="loanerName" onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})"
									style="background-color: #FFFFFF;" readonly="readonly" class="sign_right_two input_type" id="txt_proOrgId_gb" value='${eloanCase.loanerName}' >
									<i style=" position: absolute; top: 5px; right: 5px; color:#52cdec; " id="loanerNameImage" name ="loanerNameImage" class="icon iconfont"
										onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})">&#xe627;</i>
								 </input>
								 <input value="${eloanCase.loanerOrgCode}" type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
								 <input value="${eloanCase.loanerOrgId}" type="hidden" id="loanerOrgId" name ="loanerOrgId" />
								 <input value="${eloanCase.loanerId}" type="hidden"  id="loanerId" name="loanerId" />
								 <input value="${eloanCase.loanerUserName}" type="hidden"  id="loanerUserName" name="loanerUserName" />
							</div>
							 <div class="form_content">
								<label class="control-label sign_left_two"> 信贷员电话 </label> 
								<input class="input_type  sign_right_two" value="${eloanCase.loanerPhone}" name="loanerPhone" id="loanerPhone"></input>
							</div> 
						</li>
						
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 办卡人姓名</label>
								<div id="custNameParent" style="display: inline-block;">
									<select class="select_control sign_right_two" id="custName"	name="custName">
										<c:if test="${eloanCase.custName !=null}">
											<option value="${eloanCase.custName }" selected="selected">${eloanCase.custName }</option>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i>办卡人电话</label> 
								<input class="input_type sign_right_two" value="${eloanCase.custPhone}" name="custPhone" id="custPhone">
							</div> 
							<shiro:hasPermission name="TRADE.LOAN.SUBMIT.BELONG">
								<div class="form_content">
									<label class="control-label sign_left_two"><i style="color:red">* </i> 案件归属</label> 
									<input type="text" id="excutorName" name="excutorName" class="form-control tbspuser" style="width: 170px; display: inline-block;"
											hVal="${excutorId}" value="${excutorName}" readonly="readonly"
											onclick="userSelect({startOrgId:'${orgId}',expandNodeId:'${orgId}',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
								</div>
							</shiro:hasPermission>
						</li>
						
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i>证件类型</label>  							
								<aist:dict id="custCardType"  name="custCardType" clazz="select_control sign_right_two" display="select" dictType="CERT_TYPE"  tag="forEloanApply"  ligerui='none'  defaultvalue="${eloanCase.custCardType}"></aist:dict>												
							</div>
							
							<div class="form_content">
								<label class="control-label sign_left_two"> <i  style="color:red">* </i> 办卡人证件号
								</label> <input class="input_type sign_right_two" style="width: 172px;"
									value="${eloanCase.custPaper}" name="custPaper" id="custPaper">
							</div> 
						</li>
						
						<li id="liChargeAndRemark" style="display: none;">
							<div class="form_content" id="divCharge">
								<label class="control-label sign_left_two"> <i style="color:red">* </i>手续费</label> 
								<input class="input_type sign_right_two" value="" name="chargeAmount" id="chargeAmount">
							</div>
							<div class="form_content" id="divRemark">
								<label class="control-label sign_left_two"> <i class="red"></i>情况说明</label> 
								<input class="input_type sign_right_two" value="" name="remark" id="remark" style="width: 465px;">
							</div>
						</li>


						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 转介人姓名</label> 
								<input type="text" name="zjName" id="zjName" style="background-color: #FFFFFF;" readonly="readonly"
									class="sign_right_two input_type" id="txt_proOrgId_gb"
									onclick="userSelect({startOrgId:'1D29BB468F504774ACE653B946A393EE',expandNodeId:'1D29BB468F504774ACE653B946A393EE',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectZjUser})"
									value='${eloanCase.zjName}'>
								<div class="input-group float_icon organize_icon">
									<i class="icon iconfont">&#xe627;</i>
								</div>
								<input type="hidden" id="caseOrigin"  name="caseOrigin"/>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> 转介人员工编号 </label> <input
									class="input_type sign_right_two" readonly="readonly"
									value="${eloanCase.zjCode}" name="zjCode" id="zjCode">
							</div>
						</li>
						
						<div class="modal_title title-mark">
                                	业绩拆分信息
                        </div>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									style="color:red">* </i> 产品部姓名
								</label> <input type="text" name="pdName" id="pdName"
									style="background-color: #FFFFFF;" readonly="readonly"
									class="sign_right_two input_type" id="txt_proOrgId_gb"
									onclick="userSelect({startOrgId:'419B20D1643F4CAB8521DB9BEF963C7E',expandNodeId:'419B20D1643F4CAB8521DB9BEF963C7E',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectPdUser})"
									value='${eloanCase.pdName}'>
								<div class="input-group float_icon organize_icon">
									<i class="icon iconfont">&#xe627;</i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> 产品部员工编号 </label> <input
									class="input_type  sign_right_two" value="${eloanCase.pdCode}"
									readonly="readonly" name="pdCode" id="pdCode">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> 产品部分成比例 </label> <input
									class="input_type sign_right_two" value="${eloanCase.pdPart == null?10:eloanCase.pdPart}"
									readonly="readonly" name="pdPart" id="pdPart">
								<div class="input-group date_icon">
									<span class="danwei">%</span>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> 合作人姓名 </label> 
								<input class="input_type sign_right_two" value="${eloanCase.coName}"
									name="coName" id="coName" readonly="readonly"
									onclick="chooseAllUsers('ff8080814f459a78014f45a73d820006')" >
								<div class="input-group float_icon organize_icon">
									<i class="icon iconfont">&#xe627;</i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> 合作人员工编号 </label> <input
									class="input_type sign_right_two" value="${eloanCase.coCode}"
									name="coCode" id="coCode" readonly="readonly">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> 合作人分配比例 </label> <input
									class="input_type sign_right_two" value="${eloanCase.coPart}"
									name="coPart" id="coPart">
								<div class="input-group date_icon">
									<span class="danwei">%</span>
								</div>
							</div>
						</li>
						<li>
                            <div class="form_content">
                                <label class="control-label sign_left_two">
                                   	 归属人
                                </label>
                                <input class="input_type form-control sign_right_two" id="owerName" value="${excutorName }" disabled="disabled" readonly="readonly">
                            </div>
                            <div class="form_content">
                                <label class="control-label  sign_left_two">
                                       	归属人编号
                                </label>
                                <input class="input_type form-control sign_right_two" id="owerCode" value="${excutorCode }" disabled="disabled" readonly="readonly">
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_two">
                                   	 归属人业绩比例
                                </label>
                                <input class="input_type form-control sign_right_two" id="ownerPart" value="50" disabled="disabled" readonly="readonly">
                                <div class="input-group date_icon">
                                    <span class="danwei">%</span>
                                </div>
                            </div>
                        </li>
						
						<li>
							<div class="form_content" id="eapplyPassOrRefuseReasonForShow">
								<label class="control-label sign_left_two pull-left">驳回原因</label>
								<textarea class="input_type sign_right pull-left" rows="2"
									id="eContent" name="eContent"
									style="margin-left: 4px; width: 757px; height: 71px; resize: none;">${toApproveRecord.content }</textarea>
							</div>
						</li>
						<c:if test="${taskId!=null}">
						<!-- 相关信息 -->
						<li>
						<div id="caseCommentList" class="view-content"></div>
						</li>
						<li>
							<div class="form_content">
									<input type="hidden" name="pkId" id="pkId" value="${eloanCase.pkid}"/>
									<label class="control-label sign_left_two pull-left">作废原因</label>
									<textarea class="input_type sign_right pull-left"  rows="2"  id="ezfContent"	name="ezfContent" style="margin-left: 4px;width: 757px;
    											height: 71px;resize:none;"></textarea>
							</div>
						</li>
						</c:if>
					</ul>
					
					<p class="text-center">
						<c:if test="${taskId==null}">
							<input type="button" class="btn btn-success submit_From" value="提交"> 
						</c:if>
						<c:if test="${taskId!=null}">
							<input type="button" class="btn btn-success" id="invalidEloan" value="作废">
						</c:if>
						<a type="button" href="${ctx}/eloan/Eloanlist" class="btn btn-grey ml5">关闭</a>
					</p>
					
				</form>
			</div>

		</div>
	</div>
	<!-- main End -->
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script">
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
    <script src="<c:url value='/js/eloan/eloancommon.js' />"></script>
	<script id="queryCastListItemList" type="text/html">
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
        <p class="name">
            <span>经纪人：</span><a href="#" class="a_blue" id="modal_agentName{{index}}">{{item.AGENT_NAME}}</a>
        </p>
            <input type="hidden" id="modal_agentCode{{index}}"  value="{{item.AGENT_CODE}}">
           <input type="hidden" id="modal_employeeCode{{index}}"  value="{{item.EMPLOYEE_CODE}}">
            <input type="hidden" id="modal_caseOrigin{{index}}"  value="{{item.CASE_ORIGIN}}">
    </td>
    <td class="center">
        <p class="big">
       		<span id="modal_seller{{index}}">{{item.SELLER}}</span>
        </p>
    </td>
    <td class="center">
        <p class="big">
      	   <span id="modal_buyer{{index}}"> {{item.BUYER}}</span>

        </p>
    </td>
    <td class="text-left">
        <button type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}">
                          关联案件
        </button>
    </td>
</tr>
{{/each}}
	</script> <script>
		$(document)
				.ready(
						function() {
							//初始化归属人业绩比例
							initRate();
							
							//驳回原因显示问题
							var eContent = $("#eContent").val();
							if (eContent == '' || eContent == null) {
								$("#eapplyPassOrRefuseReasonForShow").hide();
							} else {
								$("#eContent").attr("disabled", true);
							}
							
							//初始化归属人业绩比例
							function initRate(){
								var finOrgCode = $("#finOrgCode option:selected").val();
								
								var pdPart = 0;
								//安家贷、365小贷以外金融产品为20%(产品部),其他均为10%(产品部)
								//W0001是安家贷编码
								if(finOrgCode == "W0001"){
									pdPart = 20;
								}
								else {
									pdPart = 10;
								}
								
								$("#pdPart").val(pdPart);
								
								//计算归属人分成比例
								calOwerPart();
							}
							
							//计算归属人分成比例
							function calOwerPart(){
								//产品部分成比例
								var pdPart = Number($("#pdPart").val());
								
								//合作人分成比例
								var coPart = Number($("#coPart").val());
								
								//计算归属人分成比例
								var ownerPart = 100 - pdPart - coPart;
								
								$("#ownerPart").val(ownerPart);
							}
							
							//显示手续费
							function showAndHide(loanSrvCode, finOrgCode, month) {
								if (loanSrvCode == "30004014"
										&& finOrgCode == "W0003" && month != ""
										&& month <= 12) {
									$("#liChargeAndRemark").show();
									$("#divCharge").show();
									$("#divRemark").show();
								} else {
									$("#liChargeAndRemark").hide();
									$("#divCharge").hide();
								}
							}
							
							//合作人分配比例市区焦点事件
							$("#coPart").blur(function(){
								var coPart = Number(this.value);
								var pdPart = Number($("#pdPart").val());
								
								var ownerPart = 100 - coPart - pdPart;
								$("#ownerPart").val(ownerPart);
							});
							
							//产品类型 选择
							$("#loanSrvCode").change(function() {
										var value = this.value;
										var finOrgCode = $(
												"#finOrgCode option:selected").val();
										var month = $("#month").val();									
										if(value=="30004005" ||value=="30004015"){
											getBankList("sfk");
										}else{
											getBankList("loan");
										}
										
										showAndHide(value, finOrgCode, month);
									});

							$("#month").blur(
									function() {
										var loanSrvCode = $("#loanSrvCode option:selected").val();
										var finOrgCode = $("#finOrgCode option:selected").val();
										var month = this.value;
										showAndHide(loanSrvCode, finOrgCode,
												month);
									});
							
							//贷款机构选择变化时 ，产品部分成比例变化
							$("#finOrgCode").change(function() {
										var loanSrvCode = $("#loanSrvCode option:selected").val();
										var value = this.value;
										var month = $("#month").val();

										showAndHide(loanSrvCode, value, month);
										
										//更新产品部分成比例
										changeProPart(value);
									});

							$("#chargeAmount").blur(function() {
										var value = $.trim(this.value);
										var applyAmount = $.trim($(
												"#applyAmount").val());

										var reg = new RegExp(
												"^[0-9]+(.[0-9]{2})?$", "g");
										if (value != "" && !reg.test(value)) {
											window.wxc.alert("请输入一个数字，最多只能有两位小数！");
											$(this).focus().select();
										}
									});

							/* $('#custName').editableSelect({
								effects: 'slide',
								filter: false
							}); */

							$('.input-daterange').datepicker({
								format : 'yyyy-mm-dd',
								weekStart : 1,
								autoclose : true,
								todayBtn : 'linked',
								language : 'zh-CN'
							});

							// 初始化银行列表
							var getTag=$("#loanSrvCode").val();
							if(getTag=="30004005" ||getTag=="30004015"){
								getBankList("sfk");
							}else if(getTag!=""){
								getBankList("loan");
							}else{
								getBankList(null);
							}


							$('.submit_From').click(function() {
										//关联案件必须填写
										if (!checkForm()) {
											return;
										}
										if (!$.isBlank($("#excutorName").attr('hVal'))) {
											$("#excutorId").val($("#excutorName").attr('hVal'));
										}
										if ($.isBlank($("#caseCode").val())) {
											window.wxc.alert('请选择关联案件');
											return false;
										}
										if (validateEloanApply()) {
											saveEloanApply();
										} else {
											window.wxc.alert('该案件的产品已经存在，不允许重复添加');
											return false;
										}
									})
								
							//更新产品部分成比例
							function changeProPart(finOrgCode){
								var proPart = 0;
								
								if(finOrgCode == "W0001"){
									proPart = 20;
								}	
								else {
									proPart = 10;
								}
								
								$("#pdPart").val(proPart);
								calOwerPart();
							}

							function checkChargeAndRemark(applyAmount) {
								var charge = $("#chargeAmount").val();

								if (charge == "") {
									window.wxc.alert("请填写手续费！");
									return false;
								}

								charge = Number(charge);
								applyAmount = Number(applyAmount);
								var num = charge / (applyAmount * 10000);

								if (!(num >= 0.01 && num <= 0.02)) {
									var remark = $("#remark").val();

									if (remark == "") {
										window.wxc.alert("请填写情况说明！");
										return false;
									}
								}

								return true;
							}
							//必填项
							function checkForm() {
								var ds = $('.case_content').css('display');
								if (ds == 'none') {
									window.wxc.alert("请选择关联案件");
									return false;
								}
								var loanSrvCode = $("#loanSrvCode").val();
								if (loanSrvCode == null || loanSrvCode == '') {
									window.wxc.alert("请选择产品类型");
									return false;
								}
								var applyAmount = $("#applyAmount").val();
								if (applyAmount == null || applyAmount == '') {
									window.wxc.alert("请填写申请金额");
									return false;
								}	
								
								var date = $("#applyTime").val();
								if (date == null || date == '') {
									window.wxc.alert("请选择申请时间");
									return false;
								}
								
								var month = $('#month').val();
								if (month == null || month == '') {
									window.wxc.alert("请填写申请期数");
									return false;
								}
								
								var loanerName = $('#loanerName').val();
								if (loanerName == null || loanerName == '') {
									window.wxc.alert("请填写信贷员");
									return false;
								}
								var loanerPhone = $('#loanerPhone').val();
								if (loanerPhone == null || loanerPhone == '') {
									window.wxc.alert("请填写信贷员电话");
									return false;
								}
								
								var custCardType = $("#custCardType").val();
								if (custCardType == null || custCardType == '') {
									window.wxc.alert("请选择办卡人证件类型");
									return false;
								}
								var custPaper = $("#custPaper").val();
								if (custPaper == null || custPaper == '') {
									window.wxc.alert("请填写办卡人证件");
									return false;
								}
								
								var loanSrvCode = $("#loanSrvCode option:selected").val();
								var finOrgCode = $("#finOrgCode option:selected").val();
								
								var isVerifySuccess = true;
								if (loanSrvCode == "30004014"
										&& finOrgCode == "W0003" && month <= 12) {
									isVerifySuccess = checkChargeAndRemark(applyAmount);
								}

								if (!isVerifySuccess) {
									return false;
								}
								
								var zjName = $('#zjName').val();
								if (zjName == null || zjName == '') {
									if($("#caseOrigin").val() == "WD"){}else{
										window.wxc.alert("请填写转介人");
										return false;
										}
								}
								
								pdName
								var pdName = $('#pdName').val();
								if (pdName == null || pdName == '') {
									window.wxc.alert("请填写产品部姓名");
									return false;
								}
								
								var coName = $("#coName").val();
								if(coName != ""){
									var coPart = $("#coPart").val();
									
									if(coPart == ""){
										window.wxc.alert("请填写合作人分配比例！");
										return false;
									}
								}
								
								return true;
							}

							$(".eloanApply-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												queryId : 'queryCastListItemListdiv',
												templeteId : 'queryCastListItemList',
												rows : '6',
												gridClass : 'table table_blue mt20 table-striped table-bordered table-hover customerinfo',
												data : '',
												wrapperData : {
													ctx : ctx
												},
												columns : [
														{
															colName : "<span class='sort'  onclick='caseCodeSort();'' >案件编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
															sortColumn : "CASE_CODE",
															sord : "desc",
															sortActive : true
														}, {
															colName : "产证地址"
														}, {
															colName : "工作人员"
														}, {
															colName : "上家"
														}, {
															colName : "下家"
														}, {
															colName : "操作"
														} ]

											});

							// 查询
							$('#searchButton').click(function() {
								reloadGrid();
							});
							
							// 查询
							$('#addNewCase').click(function() {
								window.location.href = ctx+"/caseMerge/addCase/eloan";
							});
							
							

							// 关联案件
							$('.eloanApply-table').on("click",'.linkCase',function() {
												//
												var index = $(this).attr("id");
												$("#content_caseCode").html($("#modal_caseCode" + index).html());
												$("#caseCode").val($("#modal_caseCode"+ index).html());
												$("#content_propertyAddr").html($("#modal_propertyAddr"+ index).html());
												$("#content_processorId").html($("#modal_processorId"+ index).html());
												$("#content_buyer").html($("#modal_buyer"+ index).html());
												$("#content_agentName").html($("#modal_agentName"+ index).html());
												$("#content_seller").html($("#modal_seller"+ index).html());
												$("#content_agentName").html($("#modal_agentName"+ index).html());
												$("#zjName").val($("#modal_agentName"+ index).html());
												$("#zjCode").val($("#modal_employeeCode"+ index).val());
												$("#caseOrigin").val($("#modal_caseOrigin"+ index).val());
												$('.case_content').show();
												$('#myModal').modal('hide');

												getCustomerNameAndTel($("#caseCode").val());
											});
							//跟进信息
							$("#caseCommentList").eloanCaseCommentGrid(
									{
										eloanCode : $("#eloanCode").val(),
										source : 'EPLUS'
										//type : 'TRACK'
									}	   
								   );

						});

		function reloadGrid() {
			//var propertyAddr = $("#propertyAddr").val();
			var data = {};
			var propertyAddr = $.trim($("#propertyAddr").val());
			var caseCode = $.trim($("#caseCodet").val());
			var caseName = $.trim($("#caseNamet").val());

			data.propertyAddr = propertyAddr;
			data.caseCode = caseCode;
			data.sname = caseName;

			$(".eloanApply-table").reloadGrid({
				ctx : "${ctx}",
				rows : '6',
				queryId : 'queryCastListItemListdiv',
				templeteId : 'queryCastListItemList',
				wrapperData : {
					ctx : ctx
				},
				data : data
			})
		}
		
		//选择誉萃投资所有人员
		function chooseAllUsers(orgId){
			userSelect({
				startOrgId : orgId,
				expandNodeId : orgId,
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
				callBack : selectCoUser
			});
		}

		function checkChargeAndRemark(applyAmount) {
			var charge = $("#chargeAmount").val();

			if (charge == "") {
				window.wxc.alert("请填写手续费！");
				return false;
			}

			charge = Number(charge);
			applyAmount = Number(applyAmount);
			var num = charge / (applyAmount * 10000);

			if (!(num >= 0.01 && num <= 0.02)) {
				var remark = $("#remark").val();

				if (remark == "") {
					window.wxc.alert("请填写情况说明！");
					return false;
				}
			}

			return true;
		}
		//必填项
		function checkForm() {
			var ds = $('.case_content').css('display');
			if (ds == 'none') {
				window.wxc.alert("请选择关联案件");
				return false;
			}
			var loanSrvCode = $("#loanSrvCode").val();
			if (loanSrvCode == null || loanSrvCode == '') {
				window.wxc.alert("请选择产品类型");
				return false;
			}
			var custPhone = $('#custPhone').val();
			if (custPhone == null || custPhone == '') {
				window.wxc.alert("请填写客户电话");
				return false;
			}
			var applyAmount = $("#applyAmount").val();
			if (applyAmount == null || applyAmount == '') {
				window.wxc.alert("请填写申请金额");
				return false;
			}
			var date = $("#applyTime").val();
			if (date == null || date == '') {
				window.wxc.alert("请选择申请时间");
				return false;
			}
			var month = $('#month').val();
			if (month == null || month == '') {
				window.wxc.alert("请填写申请期数");
				return false;
			}
			pdName
			var pdName = $('#pdName').val();
			if (pdName == null || pdName == '') {
				window.wxc.alert("请填写产品部姓名");
				return false;
			}
			var zjName = $('#zjName').val();
			if (zjName == null || zjName == '') {
				if($("#caseOrigin").val() == "WD"){}else{
				window.wxc.alert("请填写转介人");
				return false;
				}
			}
			var loanerName = $('#loanerName').val();
			if (loanerName == null || loanerName == '') {
				window.wxc.alert("请填写信贷员");
				return false;
			}
			var loanerPhone = $('#loanerPhone').val();
			if (loanerPhone == null || loanerPhone == '') {
				window.wxc.alert("请填写信贷员电话");
				return false;
			}
			var loanSrvCode = $("#loanSrvCode option:selected").val();
			var finOrgCode = $("#finOrgCode option:selected").val();

			var isVerifySuccess = true;
			if (loanSrvCode == "30004014" && finOrgCode == "W0003"
					&& month <= 12) {
				isVerifySuccess = checkChargeAndRemark(applyAmount);
			}

			if (!isVerifySuccess) {
				return false;
			}

			return true;
		}

		/*获取银行列表*/
		function getBankList(tag) {
			var fiCode = $("#finOrgCode").attr("value");
			var friend = $("#finOrgCode");
			friend.empty();
			$
					.ajax({
						url : ctx + "/manage/queryFin",
						method : "post",
						dataType : "json",
						data : {
							"tag" : tag
						},
						success : function(data) {
							if (data.bankList != null) {
								for (var i = 0; i < data.bankList.length; i++) {
									if (fiCode == data.bankList[i].finOrgCode) {
										friend
												.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"
														+ data.bankList[i].finOrgName
														+ "</option>");
									} else {
										friend
												.append("<option value='"+data.bankList[i].finOrgCode+"'>"
														+ data.bankList[i].finOrgName
														+ "</option>");
									}
								}
								friend
										.find(
												"option[value='${loanAgent.finOrgCode }']")
										.attr("selected", true);
							}
						}
					});
		}
		/**
		根据银行选择产品分成比例
		 */
		$("#finOrgCode").change(function() {
			//安家贷分成比例为10%    其他的全部为10%
			var finOrgCode = $("#finOrgCode").val();
			if (finOrgCode == "W0001") {
				$("#pdPart").val(10);
			} else{        // if (finOrgCode == "W0003" || finOrgCode == "W0004") {
				$("#pdPart").val(10);
			}
		})
		function validateEloanApply() {
			var flag = false;
			var jsonData = $("#eloanApplyForm").serializeArray();
			var url = "${ctx}/eloan/validateEloanApply";
			$.ajax({
				cache : false,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : jsonData,
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					flag = data.content;
				},
				error : function(errors) {
					window.wxc.error("检测CaseCode绑定案件信息出错");
				}
			});
			return flag;
		}

		function saveEloanApply() {
				
			var jsonData = $("#eloanApplyForm").serializeArray();
			console.log("===Result==="+JSON.stringify(jsonData));			
			var url = "${ctx}/eloan/saveEloanApply";			
			
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : jsonData,
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					window.wxc.success(data.message,{"wxcOk":function(){
						var bohui = $("#processInstanceId").val();
						if (bohui != null && bohui != '') {
							window.close();
							window.opener.callback();
						} else {
							window.location.href = ctx + "/eloan/Eloanlist";
						}
					}});
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		/*绑定时获取客户名和客户电话号码并生成下拉框*/
		function getCustomerNameAndTel(case_code) {
			$
					.ajax({
						url : ctx + "/eloan/getCaseDetails",
						method : "post",
						dataType : "json",
						data : {
							"caseCode" : case_code
						},
						success : function(data) {
							var cusPhone = $('#custPhone');
							var txt = '<select class="select_control sign_right_two" id="custName" name="custName">';

							$
									.each(
											data,
											function(i, item) {
												if (i == 0) {
													txt += "<option value='"+ item.guestName+"' selected>"
															+ item.guestName
															+ "</option>";
													cusPhone
															.val(data[i].guestPhone);
												} else {
													txt += "<option value='"+ item.guestName+"'>"
															+ item.guestName
															+ "</option>";
												}
											});
							$('#custNameParent').empty().append(
									txt + '</select>');

							$('#custName').editableSelect({
								effects : 'slide',
								filter : false
							 ,
							onSelect: function (element) {
								var myIndex = $(element).index();
								if(myIndex>=0){
									cusPhone.val(data[myIndex].guestPhone);
								}
							} 
							});
						}
					})
		}
		function selectUserBack(array) {
			if (array && array.length > 0) {
				$("#excutorName").val(array[0].username);
				$("#excutorName").attr('hVal', array[0].userId);
				$("#owerName").val(array[0].username);
				
				//获取员工编号
				var employeeCode = getEmployeeCode(array[0].userId);
				$("#owerCode").val(employeeCode);
			} else {
				$("#executorName").val("");
				$("#executorName").attr('hVal', "");
				$("#owerName").val("");
				$("#owerCode").val("");
			}
		}
		function caseCodeSort() {
			if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
				$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
			} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
				$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
			} else {
				$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
			}
		}
		
		function getEmployeeCode(userId){
			var employeeCode = '';
			
			$.ajax({
				url : ctx + "/eloan/EmployeeCode",
				method : "post",
				async: false,
				dataType : "json",
				data : {
					"userId" : userId
				},
				success : function(data) {
					employeeCode = data.user.employeeCode;
				}
			});
			
			return employeeCode;
		}
		
		
		/**
		 * 选择用户
		 * @param param
		 */
		function userSelect(param) {
			var options = {
				dialogId : "selectUserDialog", //指定别名，自定义关闭时需此参数
				dialog : {
					height : 463,
					width : 756,
					title : '选择用户',
					url : appCtx['aist-uam-web']
							+ '/userOrgSelect/userSelect.html',
					data : param,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.save();
						}
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				}
			};
			openDialog(options);
		}
		
		//选择合作人回调事件,将合作人姓名和合作人编号填充到input
		function selectCoUser(array) {
			if (array && array.length > 0) {
				$("#coName").val(array[0].username);
				
				$.ajax({
					url : ctx + "/eloan/EmployeeCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$("#coCode").val(data.user.employeeCode);
					}
				})
			} else {
				$("#coName").val("");
				$("#coCode").val("");
			}
		}
		
		/**
		 * 更新input的值
		 */
		function selectPdUser(array) {
			if (array && array.length > 0) {
				$("#pdName").val(array[0].username);
				$.ajax({
					url : ctx + "/eloan/EmployeeCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$("#pdCode").val(data.user.employeeCode);
					}
				})
			} else {
				$("#pdName").val("");
				$("#pdCode").val("");
			}
		}
		function selectLoanerUser(array) {
			if (array && array.length > 0) {
				$("#loanerName").val(array[0].username);
				$.ajax({
					url : ctx + "/eloan/LoanerCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$("#loanerPhone").val(data.user.mobile);
						$("#loanerNameImage").css("color","#52cdec");
						$("#loanerId").val(data.user.id);
						$("#loanerOrgCode").val(data.user.orgName);
						$("#loanerOrgId").val(data.user.orgId);
						$("#loanerUserName").val(data.user.username);
					}
				})
			} else {
				$("#loanerName").val("");
				$("#loanerOrgCode").val("");
				$("#loanerOrgId").val("");
				$("#loanerUserName").val("");
				$("#loanerPhone").val("");
				$("#loanerId").val("");
			}
		}

		function selectZjUser(array) {
			if (array && array.length > 0) {
				$("#zjName").val(array[0].username);
				$.ajax({
					url : ctx + "/eloan/EmployeeCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$("#zjCode").val(data.user.employeeCode);
					}
				})

			} else {
				$("#zjName").val("");
				$("#pdCode").val("");
			}
		}
		function returnEmployeeCode(userId) {

		}
		function onkeyuploanerName(){
			$("#loanerNameImage").css("color","#676A6C");
			$("#loanerId").val("");
			$("#loanerOrgCode").val("");
			$("#loanerOrgId").val("");
		}
		
		$("#invalidEloan").click(function(){  
     	   if($("#ezfContent").val()==null||$("#ezfContent").val()==""){
     		   window.wxc.alert("请填写作废原因")
     		   return;
     	   }
     	   
     	   window.wxc.confirm("确定要作废这条数据吗？",{"wxcOk":function(){
     		   saveEloanInfoForUpdate();
     	   }});
        })
        function saveEloanInfoForUpdate(){
			$.ajax({
				type : "POST",
			    url:ctx+"/eloan/deteleItem",
				data:{pkid:$("#pkId").val(),action:"aban",content:$("#ezfContent").val()},
				dataType : "json",
				success : function(data) {	
					if(data.success == true){
						 window.wxc.success("数据保存成功",{"wxcOk":function(){
							 window.close();
							 window.opener.callback();
						 }});
					}else{
						window.wxc.error("数据保存出错");
					} 
				},
				error : function(errors) {
					$.unblockUI();    
					window.wxc.error("数据保存出错");
				}
			});
		}

	</script> </content>


</body>

</html>