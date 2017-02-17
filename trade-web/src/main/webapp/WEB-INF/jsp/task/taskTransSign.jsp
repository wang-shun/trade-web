<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"rel="stylesheet" />
<!-- aist列表样式 -->
<link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
<!-- 备注信息 -->
<link href="${ctx}/css/transcss/comment/caseComment_new.css" rel="stylesheet">
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var source = "${source}";
	var finishYear = "${transSign.finishYear}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
<style type='text/css'>
.divider {position: relative}
.divider label {position: absolute;left: 0;top: -17px}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<div class="marginbot">
		<!-- 服务流程 -->
		<div class="panel " id="serviceFlow">
		  <div class="row wrapper white-bg new-heading">
	            <div class="pl10">
	                <h2 class="newtitle-big">
	                       	签约
	                   </h2>
	               <div class="mt20">
	                   <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
	                   	<i class="iconfont icon">&#xe640;</i> 在途单列表
	                   </button>
	                   <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
	                       <i class="iconfont icon">&#xe642;</i>案件视图
	                   </button>
	               </div>
	            </div>
	       </div>
	
	       <div class="ibox-content border-bottom clearfix space_box noborder">
	       <div class="">
	            <h2 class="newtitle title-mark">完成提醒</h2>
	            <div class="jqGrid_wrapper">
	                <table id="reminder_list"></table>
					<div id="pager_list_1"></div>
	                <button type="button" class="btn btn-icon btn-grey-border mt20" id="sendSMS">
	                    <i class="iconfont icon">&#xe62a;</i> 发送短信提醒
	                </button>
	            </div>
	       </div>
	
		   <form method="post" class="form-horizontal" id="transSignForm">
				<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
				<%-- 原有数据对应id --%>
				<input type="hidden" id="propertyPkid" name="propertyPkid" value="${transSign.propertyPkid}"> 
				<input type="hidden" id="initPayPkid" name="initPayPkid" value="${transSign.initPayPkid}"> 
				<input type="hidden" id="secPayPkid" name="secPayPkid" value="${transSign.secPayPkid}">
				<input type="hidden" id="lastPayPkid" name="lastPayPkid" value="${transSign.lastPayPkid}"> 
				<input type="hidden" id="compensatePayPkid" name="compensatePayPkid" value="${transSign.compensatePayPkid}"> 
				<input type="hidden" id="signPkid" name="signPkid" value="${transSign.signPkid}"> 
				<input type="hidden" id="housePkid" name="housePkid" value="${houseTransfer.pkid}">
				
	        	<div>
	            	<h4 class="title-mark">填写任务信息</h4>
	            	<div class="form_list">
	                	<div class="marinfo">
	                    	<div class="line">
	                        	<div class="form_content mt3">
		                            <label class="control-label sign_left_small select_style mend_select">
		                               	 <font color=" red" class="mr5" >*</font>实际签约时间
		                            </label>
		                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                                <input type="text" class="input_type yuanwid datatime" id="realConTime" name="realConTime"
													value="<fmt:formatDate value='${transSign.realConTime}' type='both' pattern='yyyy-MM-dd'/>"
													onfocus="this.blur()">
		                            </div>
	                        	</div>
	                        	
	                        	<div class="form_content">
	                           		<label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>成交价 </label> 
	                           		<input type="text" placeholder="成交价"
														value="<fmt:formatNumber value='${ transSign.realPrice/10000}' type='number' pattern='#0.00' />"
														class="input_type yuanwid" id="realPrice" name="realPrice"
														onkeyup="checkNum(this)"> 
							   		<span class="date_icon">万元</span>
	                        	</div>
	                        	
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>合同价 </label> 
		                            
		                            <input type="text" placeholder="合同价"
															value="<fmt:formatNumber value='${ transSign.conPrice/10000}' type='number' pattern='#0.00' />"
															class="input_type yuanwid" id="conPrice" name="conPrice"
															onkeyup="checkNum(this)"> 
		                            <span class="date_icon">万元</span>
		                        </div>
	                    	</div>
	
		                    <div class="line" id="divDiYaAndChaxiangou">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>抵押情况 </label>
		                            <div class="controls ismortgage " style="width: 180px;margin-left: 0px;">
		                              	  <select class="select_control data_style" readOnlydata='1' name="isLoanClose" id="diya">
												<option value="">请选择</option>
												<option value="true" ${transSign.isLoanClose=="1"?'selected':''}>有抵押</option>
												<option value="false" ${transSign.isLoanClose=="0"?'selected':''}>无抵押</option>
								 		  </select>
		                            </div>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>查限购 </label>
		                            <div class="controls isnowid" style="margin-left: 0px;">
		                               <select class="select_control data_style" readOnlydata='1' name="isPerchaseReserachNeed" id="chaxiangou">
												<option value="">请选择</option>
												<option value="true" ${transSign.isPerchaseReserachNeed=="1"?'selected':''}>是</option>
												<option value="false" ${transSign.isPerchaseReserachNeed=="0"?'selected':''}>否</option>
										</select>
		                            </div>
		                        </div>
		                    </div>
	                	</div>
	            	</div>
	        	</div>
	        	
	        	<div>
	            	<h2 class="newtitle title-mark">付款信息</h2>
	            	<div class="form_list">
	                	<div class="marinfo">
	                    	<div class="line">
	                        	<div class="form_content">
	                            	<label class="control-label sign_left_small"> 首付付款 </label>
	                            	<input type="hidden" value="首付付款" id="initPayName" name="initPayName">
	                            	<input type="text"
												value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />"
												class="input_type yuanwid" id="initAmount" name="initAmount"
												onkeyup="checkNum(this)"> 
	                            	<span class="date_icon">万元</span>
	                        	</div>
	                        	
		                        <div class="form_content mt3">
		                            <label class="control-label sign_left_small select_style mend_select">
		                               	 时间
		                            </label>
		                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                            	<input type="text"
													value="<fmt:formatDate  value='${transSign.initPayTime }' type='both' pattern='yyyy-MM-dd' />"
													class="input_type yuanwid datatime" id="initPayTime" name="initPayTime"
													onfocus="this.blur()">
		                            </div>
		                        </div>
		                        
		                        <div class="form_content">
		                            <label class="control-label sign_left_small">方式 </label>
		                            	<aist:dict clazz="select_control data_style" id="initPayType"
													name="initPayType" display="select"
													defaultvalue="${transSign.initPayType}" dictType="30015" />
		                        </div>
	                    	</div>
	                    	
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small">二期付款</label> 
		                            <input type="hidden" value="二期付款" id="secPayName" name="secPayName">
		                            <input type="text"
													value="<fmt:formatNumber value='${transSign.secAmount }' type='number' pattern='#0.00' />"
													class="input_type yuanwid" id="secAmount" name="secAmount"
													onkeyup="checkNum(this)"> 
		                            <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content mt3">
		                            <label class="control-label sign_left_small select_style mend_select">
		                                	时间
		                            </label>
		                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                            	<input type="text"
													value="<fmt:formatDate  value='${transSign.secPayTime }' type='both' pattern='yyyy-MM-dd' />"
													class="input_type yuanwid datatime" id="secPayTime" name="secPayTime"
													onfocus="this.blur()">
		                            </div>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small">方式 </label>
		                            <aist:dict clazz="select_control data_style" id="secPayType"
													name="secPayType" display="select"
													defaultvalue="${transSign.secPayType}" dictType="30015" />
		                        </div>
		                    </div>
		                    
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small">尾款付款</label> 
		                            <input type="hidden" value="尾款付款" id="lastPayName" name="lastPayName">
		                            <input type="text"
													value="<fmt:formatNumber value='${transSign.lastAmount}' type='number' pattern='#0.00' />"
													class="input_type yuanwid" id="lastAmount" name="lastAmount"
													onkeyup="checkNum(this)"> 
		                            <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content mt3">
		                            <label class="control-label sign_left_small select_style mend_select">
		                                	时间
		                            </label>
		                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                            	<input type="text"
													value="<fmt:formatDate  value='${transSign.lastPayTime }' type='both' pattern='yyyy-MM-dd' />"
													class="input_type yuanwid datatime" id="lastPayTime" name="lastPayTime"
													onfocus="this.blur()">
		                            </div>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"> 方式 </label>
		                            <aist:dict clazz="select_control data_style" id="lastPayType"
													name="lastPayType" display="select"
													defaultvalue="${transSign.lastPayType}" dictType="30015" />
		                        </div>
		                    </div>
		                    
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small">装修补偿款</label>
		                            <input type="hidden" value="装修补偿款" id="compensatePayName" name="compensatePayName">
		                            <input type="text"
													value="<fmt:formatNumber value='${transSign.compensateAmount}' type='number' pattern='#0.00' />"
													class="input_type yuanwid" id="compensateAmount"
													name="compensateAmount" onkeyup="checkNum(this)"> 
		                           <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content mt3">
		                            <label class="control-label sign_left_small select_style mend_select">
		                               	 时间
		                            </label>
		                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                            	<input type="text"
													value="<fmt:formatDate  value='${transSign.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />"
													class="input_type yuanwid datatime" id="compensatePayTime"
													name="compensatePayTime" onfocus="this.blur()">
		                            </div>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"> 方式 </label>
		                            	<aist:dict clazz="select_control data_style" id="compensatePayType"
													name="compensatePayType" display="select"
													defaultvalue="${transSign.compensatePayType}"
													dictType="30015" />
		                        </div>
		                    </div>
	                	</div>
	            	</div>
	        	</div>
	
		        <div>
		            <h2 class="newtitle title-mark">上家信息</h2>
		            <div class="form_list">
		                <div class="marinfo" id="topHome">
		                    <div id="guestUpDiv"></div>
		                </div>
		            </div> 
		            
		            <div class="clear add-member">
						<a href="javascript:addDateDivUp();">添加上家</a>
					</div>
		        </div>
		        
		        <div>
		            <h2 class="newtitle title-mark">下家信息</h2>
		            <div class="form_list">
		                <div class="marinfo" id="downHome">
		                	<div id="guestDownDiv"></div>
		                </div>
		            </div>
		            <div class="clear add-member">
		            	<a href="javascript:addDateDivDown();">添加下家</a>
		            </div>
		        </div>
		        
		        <div id="guestDelDiv"></div>
	
		        <div>
		            <h2 class="newtitle title-mark">产证信息</h2>
		            <div class="form_list">
		                <div class="marinfo">
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>产证面积 </label>
		                            <input type="text" class="input_type data_style" id="square" name="square"
										onkeyup="checkNum(this)"
										value="<fmt:formatNumber value='${transSign.square}' type='number' pattern='#0.00' />">
		                           <span class="date_icon">平方米</span>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>产证地址 </label> 
		                            <input type="text" class="input_type mendwidth" id="propertyAddr"
										name="propertyAddr" value="${transSign.propertyAddr}">
		                        </div>
		
		                    </div>
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>所在楼层 </label> 
		                            <input type="text" class="input_type data_style" id="locateFloor"
									name="locateFloor" onkeyup="checkNum2(this)"
									value="${transSign.locateFloor}">
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>总层高</label> 
		                            <input type="text" class="input_type data_style" id="totalFloor"
									name="totalFloor" onkeyup="checkNum2(this)"
									value="${transSign.totalFloor}">
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>竣工年份 </label>
		                            <select class="select_control data_style" name="finishYear" id="finishYear"></select>
		                        </div>
		                    </div>
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>房屋类型 </label>
		                            <aist:dict clazz="select_control data_style" id="propertyType"
									name="propertyType" display="select"
									defaultvalue="${transSign.propertyType}" dictType="30014" />
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>合同公证 </label>
		                            <aist:dict clazz="select_control data_style" id="isConCert" name="isConCert"
									display="select" defaultvalue="${transSign.isConCert}"
									dictType="gongzheng_need" />
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>房屋有户口</label>
		                            	<aist:dict clazz="select_control data_style" id="isHukou" name="isHukou"
										display="select" defaultvalue="${transSign.isHukou}"
										dictType="hukou_remain" />
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	
		        <div>
		            <h2 class="newtitle title-mark">预估税费</h2>
		            <div class="form_list">
		                <div class="marinfo">
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>房产税 </label> 
		                            <input type="text" class="input_type yuanwid" id="houseHodingTax"
									name="houseHodingTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.houseHodingTax}' type='number' pattern='#0.00' />">
		                            <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>个人所得税 </label> 
		                            <input type="text" class="input_type yuanwid" id="personalIncomeTax"
									name="personalIncomeTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.personalIncomeTax}' type='number' pattern='#0.00' />">
		                           <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>上家营业税 </label>
		                            <input type="text" class="input_type yuanwid" id="businessTax"
									name="businessTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.businessTax}' type='number' pattern='#0.00' />"> 
		                            <span class="date_icon">万元</span>
		                        </div>
		                    </div>
		                    <div class="line">
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>下家契税 </label>
		                            <input type="text" class="input_type yuanwid" id="contractTax"
									name="contractTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.contractTax}' type='number' pattern='#0.00' />">
		                           <span class="date_icon">万元</span>
		                        </div>
		                        <div class="form_content">
		                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>土地增值税 </label>
		                            <input type="text" class="input_type yuanwid" id="landIncrementTax"
									name="landIncrementTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.landIncrementTax}' type='number' pattern='#0.00' />">
		                           <span class="date_icon">万元</span>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </form>
	        <!-- 相关信息 -->
			<div id="caseCommentList" class="view-content"></div>
	
            <div class="ibox-title" style="height: auto;border:0;padding-left:0;" id="aboutInfo">
			<c:choose>
				<c:when test="${accesoryList!=null}">
					<h5 class="title-mark">
						上传备件</h5><br>
					<div class="table-box" id="transSignfileUploadContainer"></div>
				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
			</div>
			<div class="form-btn clear pt20">
	        <div class="text-center">
	            <button  class="btn btn-success btn-space" onclick="save(false)">保存</button>
	            <button class="btn btn-success btn-space" onclick="submit()" id="btnSubmit">提交</button>
	        </div>
	    </div>
	        </div>
	        </div>
	</div>
 </div>

	<content tag="local_script"> 
	<script>
			//判断是否有重复字符
			function isUniqueChar(value){
				if(!value){
					return false;
				}
				var uniqueMap   = {};
				for(i=0;i<value.length;i++){
					var val = value.charAt(i);
					uniqueMap[val]=val;
				}
				var result = ""
				for(var key in uniqueMap){
					result +=key;
				}
				return (result.length==1);
			}
			
			//验证手机和电话号码
			function checkContactNumber(ContactNumber) {
				var mobile = $.trim(ContactNumber);				
				var number=/^[0-9]*$/;						
				var isValid = true;
				
				if(!number.exec(mobile)){					
					window.wxc.alert("电话号码只能由数字组成！");
					isValid = false;
					return isValid;
				}
				if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13 || mobile.length ==14)){				
					window.wxc.alert("电话号码只能由是8位、11位、13位或者14位的数字组成！");
					isValid = false;
					return isValid;
				}
				
				if(isUniqueChar(mobile)){
					window.wxc.alert("电话号码不能为全部相同的数字！");
					isValid = false;
					return isValid;
				}
				return isValid;
			}

			$("#sendSMS").click(function() {
				var t = '';
				var s = '/';
				
				$("#reminder_list").find("input:checkbox:checked").closest('td').next().each(function() {
					t += ($(this).text() + s);
				});
				
				if (t != '') {
					t = t.substring(0, t.length - 1);
				}
				
				$("#smsPlatFrom").smsPlatFrom({
					ctx : '${ctx}',
					caseCode : $('#caseCode').val(),
					serviceItem : t
				});
			});

			$(function() {
				var caseCode = $('#caseCode').val();
				var srvCode = 'TransSign';
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : srvCode
				});
				
				TaskTransSignValidate.init("transSignForm", "");
				if ('caseDetails' == source) {
					readOnlyForm();
				}

				$("#reminder_list").jqGrid({
					url : "${ctx}/quickGrid/findPage",
					datatype : "json",
					height : 210,
					multiselect : true,
					autowidth : true,
					shrinkToFit : true,
					viewrecords : true,
					colNames : [ '提醒事项', '备注' ],
					colModel : [ {
						name : 'REMIND_ITEM',
						index : 'REMIND_ITEM',
						width : 500
					}, {
						name : 'COMMENT',
						index : 'COMMENT',
						width : 500
					}

					],
					viewrecords : false,
					pagebuttions : false,
					hidegrid : false,
					postData : {
						queryId : "queryToReminderList",
						search_partCode : taskitem
					},
				});
				
				$(window).bind('resize', function() {
					var width = $('.jqGrid_wrapper').width();
					$('#reminder_list').setGridWidth(width);

				});

				//日期组件
				$('#data_1 .input-group.date').datepicker({
					todayBtn : "linked",
					keyboardNavigation : false,
					forceParse : false,
					calendarWeeks : false,//显示年日历周
					autoclose : true
				});

				//年份选择初始化
				initSelectYear("finishYear", finishYear);

				$(window).bind('resize', function() {
					var width = $('.jqGrid_wrapper').width();
					$('#table_list_1').setGridWidth(width);
					$('#table_list_2').setGridWidth(width);

				});

				initGuestInfo("30006002");
				initGuestInfo("30006001")

			});

			//初始化select
			function initSelectYear(id, value) {
				var d = new Date();
				var endYear = d.getFullYear();
				var starYear = 1900;
				var friend = $("#" + id);
				friend.empty();
				for (var i = endYear; i >= starYear; i--) {
					if (value == null || value == '' || value == undefined
							|| value > endYear || value < starYear) {
						value = endYear;
					}
					if (value == i) {
						friend
								.append("<option value='"+i+"'  selected='selected'>"
										+ i + "</option>");
					} else {
						friend.append("<option value='"+i+"'>" + i
								+ "</option>");
					}

				}
			}

			//提交数据
			function submit() {
				save(true);
			}
			
			//验证控件checkUI();
			function checkForm() {
				var checkGuest = true;
				
				if ($('input[name=realConTime]').val() == '') {
					window.wxc.alert("实际签约时间为必填项!");
					$('input[name=realConTime]').focus();
					return false;
				}
				
				if ($('input[name=realPrice]').val() == '') {
					window.wxc.alert("成交价为必填项!");
					$('input[name=realPrice]').focus();
					return false;
				}
				
				if (Number($('input[name=realPrice]').val()) <= 0) {
					window.wxc.alert("成交价必须大于0!");
					$('input[name=realPrice]').focus();
					return false;
				}
				
				if ($('input[name=conPrice]').val() == '') {
					window.wxc.alert("合同价为必填项!");
					$('input[name=conPrice]').focus();
					return false;
				}
				
				if (Number($('input[name=conPrice]').val()) <= 0) {
					window.wxc.alert("合同价必须大于0!");
					$('input[name=conPrice]').focus();
					return false;
				}

				if ($('select[name=isLoanClose]').val() == '') {
					window.wxc.alert("抵押情况为必选项!");
					$('select[name=isLoanClose]').focus();
					return false;
				}
				
				
				if ($('select[name=isPerchaseReserachNeed]').val() == '') {
					window.wxc.alert("查限购为必选项!");
					$('select[name=isPerchaseReserachNeed]').focus();
					return false;
				}
				
				if($("#topHome").children().length == 1){
					window.wxc.alert("上家信息为必填项!");
					return false;
				}
				
				if($("#downHome").children().length == 1){
					window.wxc.alert("下家信息为必填项!");
					return false;
				}
				
				if (!upAndDownCheck()) {					
					return false;
				}
				
				if (!phoneUpAndphoneDownCheck()) {					
					return false;
				}	
				
				if ($('input[name=square]').val() == '') {
					window.wxc.alert("产证面积为必填项!");
					$('input[name=square]').focus();
					return false;
				}
				
				if ($('input[name=propertyAddr]').val() == '') {
					window.wxc.alert("产证地址为必填项!");
					$('input[name=propertyAddr]').focus();
					return false;
				}
				
				if ($('input[name=locateFloor]').val() == '') {
					window.wxc.alert("所在楼层为必填项!");
					$('input[name=locateFloor]').focus();
					return false;
				}
				
				if ($('input[name=totalFloor]').val() == '') {
					window.wxc.alert("总层高为必填项!");
					$('input[name=totalFloor]').focus();
					return false;
				}
				
				if ($('select[name=propertyType]').val() == '') {
					window.wxc.alert("房屋类型为必选项!");
					$('select[name=propertyType]').focus();
					return false;
				}
				
				if ($('select[name=isConCert]').val() == '') {
					window.wxc.alert("合同公证为必选项!");
					$('select[name=isConCert]').focus();
					return false;
				}
				
				if ($('select[name=isHukou]').val() == '') {
					window.wxc.alert("房屋有户口为必选项!");
					$('select[name=isHukou]').focus();
					return false;
				}
				
				if ($('input[name=houseHodingTax]').val() == '') {
					window.wxc.alert("房产税为必填项!");
					$('input[name=houseHodingTax]').focus();
					return false;
				}
				
				if ($('input[name=personalIncomeTax]').val() == '') {
					window.wxc.alert("个人所得税为必填项!");
					$('input[name=personalIncomeTax]').focus();
					return false;
				}
				
				if ($('input[name=businessTax]').val() == '') {
					window.wxc.alert("上家营业税为必填项!");
					$('input[name=businessTax]').focus();
					return false;
				}
				
				if ($('input[name=contractTax]').val() == '') {
					window.wxc.alert("下家契税为必填项!");
					$('input[name=contractTax]').focus();
					return false;
				}
				
				if ($('input[name=landIncrementTax]').val() == '') {
					window.wxc.alert("土地增值税为必填项!");
					$('input[name=landIncrementTax]').focus();
					return false;
				}
			
				if ($("#property_research_letter_pic_list li").length == undefined
						|| $("#property_research_letter_pic_list li").length == 0 ) {
					window.wxc.alert("产调附件未上传!");

					return false;
				}
				
				//验证上传文件是否全部上传
				var isCompletedUpload = fileUpload.isCompletedUpload();
				
				if(!isCompletedUpload){
					window.wxc.alert("产调附件还未全部上传!");
					return false;
				}
				
				return true;
			}
			

			//保存数据
			function save(b) {	
				
				if (!checkForm()) {
					return false;
				}	
				
				if (!$("#transSignForm").valid()) {
					return false;
				}
				var jsonData = $("#transSignForm").serializeArray();

				deleteAndModify();

				var url = "${ctx}/task/sign/saveSign";
				if (b) {
					url = "${ctx}/task/sign/submitSign";
				}

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
					complete : function() {
						$.unblockUI();
						if (b) {
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
						}
						
						//超时,status还有success,error等值的情况
						if (status == 'timeout') {
							Modal.alert({
								msg : "抱歉，系统处理超时。"
							});
							$(".btn-primary").one("click", function() {
								parent.$.fancybox.close();
							});
						}
					},
					success : function(data) {
						if (b) {
							caseTaskCheck();
							if (null != data.message) {
								window.wxc.alert(data.message);
							}
						} else {
							window.wxc.success("保存成功。",{"wxcOk":function(){
								window.close();
								window.opener.callback();
							}});
						}
					},
					error : function(errors) {
						window.wxc.error("数据保存出错");
					}
				});
			}
			//double 验证
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

			function checkNum2(obj) {
				//先把非数字的都替换掉，除了数字和.
				obj.value = obj.value.replace(/[^\d.]/g, "");
				//必须保证第一个为数字而不是.
				obj.value = obj.value.replace(/^\./g, "");
			}

			
			//上下家电话相同验证
			function phoneUpAndphoneDownCheck() {
				var checkGuestPhone= true;
				var selectsPhoneDown = $("input[name='guestPhoneDown']");
				var selectsPhoneUp = $("input[name='guestPhoneUp']");				
				
				$.each(selectsPhoneUp, function(j, item) {
					if (item.value == '') {
						window.wxc.alert("上家电话为必填项!");
						selectsPhoneUp[j].focus();
						checkGuestPhone = false;						
					} else {						
						checkGuestPhone = checkContactNumber(item.value);						
						if (!checkGuestPhone) {								
							selectsPhoneUp[j].focus();
							return false;
						}
					}
				});
				
				if (!checkGuestPhone || selectsPhoneUp == null) {
					return false;
				}
				
				//验证下家电话号码
				$.each(selectsPhoneDown, function(j, item) {
					if (item.value == '') {
						window.wxc.alert("下家电话为必填项!");
						selectsPhoneDown[j].focus();
						checkGuestPhone = false;
					} else {
						checkGuestPhone = checkContactNumber(item.value);
						if (!checkGuestPhone) {
							selectsPhoneDown[j].focus();
							return false;
						}
					}
				});
				
				if (!checkGuestPhone || selectsPhoneDown == null) {
					return false;
				}
				
				$.each(selectsPhoneUp,function(i, itemPhoneUp) {
						if (itemPhoneUp.value != '') {
							$.each(selectsPhoneDown,function(j,	itemPhoneDown) {
								if (itemPhoneDown.value != '') {
									if (itemPhoneUp.value.trim() == itemPhoneDown.value.trim()) {
												window.wxc.alert("上下家电话不能填写一样!");
												checkGuestPhone=false;
												return checkGuestPhone;
									}
								}
							})							
					    }
				if(checkGuestPhone==false) {return  false;}
				});
				
				return checkGuestPhone;
			}
			
			function  upAndDownCheck(){				
				var checkGuest = true;
				var selectsUp = $("input[name='guestNameUp']");
				$.each(selectsUp, function(j, item) {
					if (item.value == '') {
						window.wxc.alert("上家姓名为必填项!");
						selectsUp[j].focus();
						checkGuest = false;
						return false;
					} else if (item.value.trim().indexOf(" ") > -1) {
						window.wxc.alert("上家姓名中不能包含空格!");
						selectsUp[j].focus();
						checkGuest = false;
						return false;
					} else if (item.value.indexOf("先生") > 0
							|| item.value.indexOf("小姐") > 0
							|| item.value.indexOf("叔叔") > 0
							|| item.value.indexOf("阿姨") > 0
							|| item.value.indexOf("女士") > 0) {
						window.wxc.alert("上家姓名中不能包含先生、小姐、叔叔、阿姨、女士!");
						selectsUp[j].focus();
						checkGuest = false;
						return false;
					} else {
						$(selectsUp[j]).val(item.value.trim());
						checkGuest = true;
					}
				});
				if (!checkGuest || selectsUp == null) {
					return false;
				}	

				selectsDown = $("input[name='guestNameDown']");
				$.each(selectsDown, function(j, item) {
					if (item.value == '') {
						window.wxc.alert("下家姓名为必填项!");
						selectsDown[j].focus();
						checkGuest = false;
						return false;
					} else if (item.value.trim().indexOf(" ") > -1) {
						window.wxc.alert("下家姓名中不能包含空格!");
						selectsDown[j].focus();
						checkGuest = false;
						return false;
					} else if (item.value.indexOf("先生") > 0
							|| item.value.indexOf("小姐") > 0
							|| item.value.indexOf("叔叔") > 0
							|| item.value.indexOf("阿姨") > 0
							|| item.value.indexOf("女士") > 0) {
						window.wxc.alert("下家姓名中不能包含先生、小姐、叔叔、阿姨、女士!");
						selectsDown[j].focus();
						checkGuest = false;
						return false;
					} else {
						$(selectsDown[j]).val(item.value.trim());
						checkGuest = true;
					}					
				});
				if (!checkGuest || selectsDown == null) {
					return false;
				}			
				return checkGuest;
			}
			
			var divIndexDown = 1;
			function addDateDivDown() {
				var txt = "<div class='line' id=dateDivD_" + divIndexDown + ">";
				txt += "<div class='form_content'>";
				txt += "<input type='hidden' name='pkidDown' value='0'/>";
				txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' placeholder='' value='' name='guestNameDown'>";
				txt += "</div>";
				txt += "<div class='form_content'>";
				txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='' name='guestPhoneDown'>";
				txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDateDiv('dateDivD_" + divIndexDown + "')\">删除</a>";
				txt += "</div>"
				
				$("#guestDownDiv").before(txt);
				divIndexDown++;
			}

			var divIndexUp = 1;
			function addDateDivUp() {
				var txt = "<div class='line' id=dateDivU_" + divIndexUp + ">";
				txt += "<div class='form_content'>";
				txt += "<input type='hidden' name='pkidUp' value='0'/>";
				txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' name='guestNameUp' placeholder='' value=''>";
				txt += "</div>";
				txt += "<div class='form_content'>";
				txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='' name='guestPhoneUp'>";
				txt += "</div>"; 
				txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDateDiv('dateDivU_" + divIndexUp + "')\">删除</a>";
                txt += "</div>";        

				$("#guestUpDiv").before(txt);
				divIndexUp++;
			}

			function removeDateDiv(index) {
				$("#" + index).remove();
			}

			function removeDiv(id, pkid) {
				var txt = "<input type='hidden' name='guestPkid' value='"+pkid+"'/>";
				$("#guestDelDiv").before(txt);
				$("#" + id).remove();
			}

			function initGuestInfo(transPosition) {
				$.ajax({
							cache : true,
							async : false,
							type : "POST",
							url : "${ctx}/task/sign/queryGuestInfo",
							dataType : "json",
							data : [ {
								name : "transPosition",
								value : transPosition
							}, {
								name : "caseCode",
								value : caseCode
							} ],
							success : function(data) {
								var length = data.length;
								if (length > 0) {
									$.each(data,function(index, value) {
														if (transPosition == "30006002") {
															var txt = "<div class='line' id=dateDivD_" + divIndexDown + ">";
															txt += "<div class='form_content'>";
															txt += "<input type='hidden' name='pkidDown' value='"+value.pkid+"'/>";
															txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' placeholder='' value='" + value.guestName + "' name='guestNameDown'>";
															txt += "</div>";
															txt += "<div class='form_content'>";
															txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='" + value.guestPhone + "' name='guestPhoneDown'>";
															txt += "</div>";
															txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDiv('dateDivD_" + divIndexDown + "','" + value.pkid + "')\">删除</a>";
															txt += "</div>";
															
															$("#guestDownDiv").before(txt);
															divIndexDown++;
														} else if (transPosition == "30006001") {
															var txt = "<div class='line' id=dateDivU_" + divIndexUp + ">";
															txt += "<div class='form_content'>";
															txt += "<input type='hidden' name='pkidUp' value='" + value.pkid +"'/>";
															txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' name='guestNameUp' placeholder='' value='" + value.guestName + "'>";
															txt += "</div>";
															txt += "<div class='form_content'>";
															txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='" + value.guestPhone + "' name='guestPhoneUp'>";
															txt += "</div>";    
															txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDiv('dateDivU_" + divIndexUp + "','" + value.pkid + "')\">删除</a>";
															txt += "</div>";
															
															$("#guestUpDiv").before(txt);
															divIndexUp++;
														}
													});
								} else {
									if (transPosition == "30006002") {
										var txt = "<div class='line' id=dateDivD_" + divIndexDown + ">";
										txt += "<div class='form_content'>";
										txt += "<input type='hidden' name='pkidDown' value='0'/>";
										txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' placeholder='' value='" + value.guestName + "' name='guestNameDown'>";
										txt += "</div>";
										txt += "<div class='form_content'>";
										txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='" + value.guestPhone + "' name='guestPhoneDown'>";
										txt += "</div>";
										txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDiv('dateDivD_" + divIndexDown + "','" + value.pkid + "')\">删除</a>";
										txt += "</div>";
										
										$("#guestDownDiv").before(txt);
										divIndexDown++;
									} else if (transPosition == "30006001") {
										var txt = "<div class='line' id=dateDivU_" + divIndexUp + ">";
										txt += "<div class='form_content'>";
										txt += "<input type='hidden' name='pkidUp' value='0'/>";
										txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>姓名</label><input class='input_type yuanwid' name='guestNameUp' placeholder='' value='" + value.guestName + "'>";
										txt += "</div>";
										txt += "<div class='form_content'>";
										txt += "<label class='control-label sign_left_small'><font color='red' class='mr5'>*</font>电话</label><input class='input_type yuanwid' placeholder='' value='" + value.guestPhone + "' name='guestPhoneUp'>";
										txt += "</div>";    
										txt += "<a href='javascript:void(0)' class='add_space' onclick=\"removeDiv('dateDivU_" + divIndexUp + "','" + value.pkid + "')\">删除</a>";
										txt += "</div>";
										
										$("#guestUpDiv").before(txt);
										divIndexUp++;
									}
								}
							},
							error : function(errors) {
								window.wxc.error("上下家加载失败！");
							}
						});
			}
			
			function readOnlyForm() {
				//设置实际签约时间不可修改 
				$("#realConTime").parent().removeClass("input-daterange");
				$("#realConTime").removeClass("datatime");
				$("#realConTime").attr("readonly",true);
				$("#realConTime").css("background-color","#ccc");		
				//设置提交按钮隐藏
				$("#btnSubmit").hide();
			}
			
			//渲染图片 
			function renderImg(){
				$('.wrapper-content').viewer('destroy');
				$('.wrapper-content').viewer();
			}
		</script> 
		<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
		<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
		<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
		<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
		<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
		<script src="${ctx}/js/stickUp.js"></script>
		<script src="${ctx}/js/trunk/task/attachment3.js"></script>
		<script src="${ctx}/js/jquery.blockui.min.js"></script> 
		<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
		<script src="${ctx}/js/plugins/validate/common/additional-methods.js"></script>
		<script src="${ctx}/js/plugins/validate/common/messages_zh.js"></script>
		<script src="${ctx}/js/trunk/task/taskTransSign.validate.js?v=1.1.0"></script>
		<script src="${ctx}/js/plugins/layer/layer.js"></script>
		<script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script> 
		<script src="${ctx}/transjs/sms/sms.js"></script> 
		<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
		<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script> 
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src="${ctx}/js/template.js" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script src="${ctx}/js/viewer/viewer.min.js"></script>
		<!-- 改版引入的新的js文件 --> 
		<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
		<script src="${ctx}/js/common/common.js?v=1.0.1"></script> 
	</content>
	<content tag="local_require">
    <script>
   		var fileUpload;
    
	    require(['main'], function() {
	    	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
	    		fileUpload = aistFileUpload;
	    		
	    		fileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "TransSign",
		    		fileUploadContainer : "transSignfileUploadContainer"
		    	}); 
		    });
	    });
	</script>
	</content>
</body>

</html>