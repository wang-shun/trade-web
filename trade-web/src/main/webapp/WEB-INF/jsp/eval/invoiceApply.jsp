<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>e+产品</title>
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
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
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<!-- main Start -->
		
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<div class="main_titile" style="position: relative;">
						<h5>
							关联评估单
							<button type="button" id="link_btn"
								class="btn btn-success btn-blue" data-toggle="modal"
								data-target="#myModal">关联评估单</button>
						</h5>
<!-- 模态框 -->
					<div class="modal inmodal in" id="myModal" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" style="width: 1070px;">
							<div class="modal-content animated fadeIn apply_box info_box" style="width:1070px">								
								<form id="jvForm" method="get" class="form_list" >
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">评估公司：</label> <input
							type="text" class="input_type yuanwid" id="evaCompany"
							name="evaCompany"  >
					</div>		
					
					<div class="form_content">
						<label class="control-label sign_left_small">案件编号：</label> <input
							type="text" class="input_type yuanwid" id="innerCaseCode" value="${caseCode}"
							name="caseCode" readonly="readonly" >
					</div>	
					
					<div class="form_content">
						<label class="control-label sign_left_small">产证地址：</label> <input
							type="text" class="input_type yuanwid" id="propertyAddr"
							name="propertyAddr"  >
					</div>				
				</div>
				
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">评估编号：</label> <input
							type="text" class="input_type yuanwid" id="evaCode"
							name="evaCode"  >
					</div>		
					
					<div class="form_content">
						<label class="control-label sign_left_small">买方姓名：</label> <input
							type="text" class="input_type yuanwid" id="guestName"
							name="guestName"  >
					</div>	
					
					<div class="form_content">
						<label class="control-label sign_left_small">经纪人：</label> <input
							type="text" class="input_type yuanwid" id="agentName"
							name="agentName"  >
					</div>				
				</div>
				
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">贷款权证：</label> <input
							type="text" class="input_type yuanwid" id="loanName"
							name="loanName"  >
					</div>		
					
					<div class="form_content">
						
					</div>	
					
					<div class="form_content">
						<!-- <button id="reloadButton" onclick="initMyJqgrid()" type="button" class="btn btn_blue">
                           	 	<i class="icon iconfont">&#xe635;</i>查询
                             </button> -->
                             <button id="reloadButton" type="button" class="btn btn_blue">
                           	 	<i class="icon iconfont">&#xe635;</i>查询
                             </button>
					</div>				
				</div>

					<div class="form_content space">
                         <div class="add_btn" align="center" id="queryFilter">
                         </div>
                   	</div>
                     
				</form>		
								<button type="button" class="close close_blue"
									data-dismiss="modal">
									<i class="iconfont icon_rong"> &#xe60a; </i>
								</button>
								<div class="eloanApply-table"></div>
								
									<div class="view-content">
										<table id="evalGridTable" class=""></table>
										<div id="evalGridPager"></div>
									</div>


								</div>
						</div>
					</div>
					<!-- 模态框 -->
				</div>
			</div>
		</div>
	</div>
	<!-- main End -->
	</div>


<div class="">
        <!-- 关联评估模态框 -->
<div class="modal inmodal in" id="myModal" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" style="width: 1070px;">
							<div class="modal-content animated fadeIn apply_box info_box">
								<form action="" class="form_list clearfix">
									<div class="modal_title">关联案件</div>
									<div class="line">
										<div class="form_content">
											<label class="control-label mr10"> 案件编码 </label> <input
												class="teamcode input_type" value="" placeholder="请输入"
												id="caseCode" name="caseCode">
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
					<!-- 关联评估模态框 -->
        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
        <h2 class="newtitle-big">
                        	开票申请
                 </h2>
        <h2 class="newtitle title-mark">评估单编号</h2>
        	<div style="padding-left: 10px" id="evalContainer">
        		<table  width="100%">
        		<tr style="height: 40px;">
        				<td colspan="6"> <label class="control-label sign_left_small">产证地址：</label></td><td></td>     				      				
        			</tr>
        			<tr style="height: 40px;">       				
        				<td><label class="control-label sign_left_small">评估公司：</label></td><td></td>
        				<td><label class="control-label sign_left_small">评估值：</label></td><td></td>
        				<td><label class="control-label sign_left_small">评估费实收金额：</label></td><td></td>        				
        			</tr>
        			<tr style="height: 40px;">
        				<td><label class="control-label sign_left_small">买方：</label></td><td></td>
        				<td><label class="control-label sign_left_small">贷款权证：</label></td><td></td>
        				<td><label class="control-label sign_left_small">经纪人：</label></td><td></td>    				
        			</tr>
        		</table>
        	</div>
        
            <h2 class="newtitle title-mark">业务部申请的开票信息</h2>
            	<form method="get" class="form_list" id="firstFollowform" style="overflow: visible;">
            		<input type="hidden" id="ctx" value="${ctx }" />
            	
            		<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">				
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<input type="hidden" id="evaPointer" name="evaPointer" value="${evaPointer}">	            
		            <div class="marinfo">
		                <div class="line">		                 
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">发票类型：</label>
		                        <input type="text" class="input_type yuanwid" id="invoiceType" name="invoiceType" readonly="readonly"
										> 
		                    </div>
		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">开票抬头：</label> 
		                        <input type="text" class="input_type yuanwid" id="invoiceHeader" name="invoiceHeader"  readonly="readonly"
										>
		                    </div>	
		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">发票金额：</label> 
		                        <input type="text"  class="input_type yuanwid" id="invoiceAmount" name="invoiceAmount"  readonly="readonly"
										value="<fmt:formatNumber value='${caseRecvVO.toSign.conPrice}' type='number' pattern='#0.00'/>">
		                        <span class="date_icon">万元</span>
		                    </div>	                     
		                </div>
		                
		                <div class="line">		                 
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">开票地址：</label>
		                        <input type="text"  class="input_type yuanwid" id="invoiceAddress" name="invoiceAddress"  readonly="readonly"
										 type='number' pattern='#0.00'/>
		                    </div>
		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">税号：</label> 
		                        <input type="text"  class="input_type yuanwid" id="taxNum" name="taxNum"  readonly="readonly"
										 type='number' pattern='#0.00'/>
		                    </div>			                  	                     
		                </div>
					<hr>
				</div>
	                <!-- <div class="line clearfix" id="hzxm" style="overflow:visible;"></div> -->
            	
            	
            <h2 class="newtitle title-mark">填写开票任务信息</h2>
        	<div style="padding-left: 10px">
        	<form id="invoiceForm" method="get">
        		<div class="line">		                 
		                    <input type="hidden" name="pkid" id="pkid">
		                    <div class="form_content mt3">
		                        <label class="control-label sign_left_small select_style mend_select">
		                           	<font color=" red" class="mr5" >*</font>申请日期：
		                        </label>
		                        <div class="input-group sign-right dataleft input-daterange pull-left" id="estFinishTime" data-date-format="yyyy-mm-dd">
		                        	<input type="text" class="input_type yuanwid datatime" id="applyDate" name="applyDate" onfocus="this.blur()"
												 type='both' pattern='yyyy-MM-dd'/>
		                        </div> 
		                    </div>
		                    
		                    <div class="form_content mt3">
		                        <label class="control-label sign_left_small select_style mend_select">
		                           	<font color=" red" class="mr5" >*</font>预计开票完成时间：
		                        </label>
		                        <div class="input-group sign-right dataleft input-daterange pull-left" id="estFinishTime" data-date-format="yyyy-mm-dd">
		                        	<input type="text" class="input_type yuanwid datatime" id="toFinshDate" name="toFinshDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${firstFollow}' type='both' pattern='yyyy-MM-dd'/>">
		                        </div> 
		                    </div>		                  	                     
		                </div>
		                </form>
        	</div>
        		</form>
        	<hr>
        	
	            <div class="title title-mark" id="aboutInfo">
	               <strong style="font-weight:bold;">开票审批记录</strong>
	            </div>	            
	            <div class="view-content">
	              	<table id="gridTable" class=""></table>
	   				<div id="gridPager"></div>
	            </div>	            
	            <div class="form-btn">
	                    <div class="text-center">
	                        <button  class="btn btn-success btn-space" onclick="javascript:window.close()" id="btnSave">关闭</button>
	                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
	                    </div>
	                </div>
	            </div>
			</div>
	<!-- Mainly scripts -->
	<content tag="local_script">
	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <script src="<c:url value='/js/eloan/eloancommon.js' />"></script>
    <script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
	<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	   <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
	<script src="<c:url value='/static/js/jquery.json.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	   <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	   <script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/js/common/textarea.js' />"></script>
    <script type="text/javascript">
var AttachmentList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 125,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 3,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '审批人','审批时间','审批结果','审批意见'],
    			colModel : [ {
    				name : 'OPERATOR',
    				index : 'OPERATOR',
    				align : "center",
    				width : 25,
    				resizable : false
    			},{
    				name : 'OPERATOR_TIME',
    				index : 'OPERATOR_TIME',
    				align : "center",
    				width : 25,
    				resizable : false
    			}, {
    				name : 'NOT_APPROVE',
    				index : 'NOT_APPROVE',
    				align : "center",
    				width : 25,
    				resizable : false
    				//formatter : linkhouseInfo
    			}, {
    				name : 'CONTENT',
    				index : 'CONTENT',
    				align : "center",
    				width : 25,
    				resizable : false
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			//sortname:'UPLOAD_DATE',
    	        //sortorder:'desc',
    			viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",
    			gridComplete:function(){
    				var ids = jQuery("#"+gridTableId).jqGrid('getDataIDs');
    				for (var i = 0; i < ids.length; i++) {
	    				var id = ids[i];
	    				var rowDatas = jQuery("#"+gridTableId).jqGrid('getRowData', ids[i]); // 获取当前行
	    				
	    				var auditResult = rowDatas['NOT_APPROVE'];
	    				var auditResultDisplay = null;
	    				if(!auditResult){
	    					auditResultDisplay="审批通过"
	    				}else{
	    					auditResultDisplay=auditResult;
	    				}	    				    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { NOT_APPROVE: auditResultDisplay});
    				}
    			},
    			postData : {
    				queryId : "queryEvalInvoiceAuditComment",
    				//caseCode : caseCode
    				caseCode : caseCode,
    				partCode : 'evalInvoiceAudit'
    			}
    			 
    		});
       }   
    };    
})(); 
</script>
<script id="template_myTaskList" type= "text/html">
	{{each rows as item index}}
		<table  width="100%">
<tr style="height: 40px;">
        				<td> <label class="control-label sign_left_small">产证地址：</label></td><td colspan="6" align="left">{{item.PROPERTY_ADDR}}</td>   				      				
        			</tr>
        			<tr style="height: 40px;">       				
        				<td><label class="control-label sign_left_small">评估公司：</label></td><td>{{item.FIN_ORG_NAME}}</td>
        				<td><label class="control-label sign_left_small">评估值：</label></td><td>{{item.EVA_PRICE}}</td>
        				<td><label class="control-label sign_left_small">评估费实收金额：</label></td><td>{{item.EVAL_REAL_CHARGES}}</td>        				
        			</tr>
        			<tr style="height: 40px;">
        				<td><label class="control-label sign_left_small">买方：</label></td><td>{{item.GUEST_NAME}}</td>
        				<td><label class="control-label sign_left_small">贷款权证：</label></td><td>{{item.LOAN}}</td>
        				<td><label class="control-label sign_left_small">经纪人：</label></td><td>{{item.AGENT}}</td>       				
        			</tr>
        		</table>
		{{/each}}
	 </script> 
<script type="text/javascript">
var EvalReportList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode,postData){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'POST',
    			datatype : "json",
    			height : 230,
    			autowidth :true,
    			shrinkToFit : true,
    			rowNum : 4,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '案件编号','评估单编号','产权地址','评估公司','评估值','评估费实收','买方姓名','贷款权证','经纪人','操作'],
    			colModel : [ {
    				name : 'CASE_CODE',
    				index : 'CASE_CODE',
    				align : "center",
    				width : 120,
    				resizable : true
    			},{
    				name : 'EVA_CODE',
    				index : 'EVA_CODE',
    				align : "center",
    				width : 120,
    				resizable : true
    			}, {
    				name : 'PROPERTY_ADDR',
    				index : 'PROPERTY_ADDR',
    				align : "center",
    				width : 180,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'FIN_ORG_NAME',
    				index : 'FIN_ORG_NAME',
    				align : "center",
    				width : 80,
    				resizable : true
    			}, {
    				name : 'EVA_PRICE',
    				index : 'EVA_PRICE',
    				align : "center",
    				width : 85,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'EVAL_REAL_CHARGES',
    				index : 'EVAL_REAL_CHARGES',
    				align : "center",
    				width : 85,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'GUEST_NAME',
    				index : 'GUEST_NAME',
    				align : "center",
    				width : 80,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'LOAN',
    				index : 'LOAN',
    				align : "center",
    				width : 80,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'AGENT',
    				index : 'AGENT',
    				align : "center",
    				width : 65,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'OPERATION',
    				index : 'OPERATION',
    				align : "center",
    				width : 80,
    				resizable : true
    				//formatter : linkhouseInfo
    			}
				],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			//sortname:'UPLOAD_DATE',
    	        //sortorder:'desc',
    			viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",
    			gridComplete:function(){
    				var ids = jQuery("#"+gridTableId).jqGrid('getDataIDs');
    				for (var i = 0; i < ids.length; i++) {
	    				var id = ids[i];
	    				var rowDatas = jQuery("#"+gridTableId).jqGrid('getRowData', ids[i]); // 获取当前行
	    				var link = "<button  class='btn btn_blue' onclick='linkCase(\""+rowDatas['EVA_CODE']+"\")'>关联案件</a>";    				    				    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { OPERATION: link});
    				}
    			},
    			/* postData : {
    				queryId : "queryConnectEvalReport",
    				caseCode : caseCode
    			} */
    			postData : postData
    		});
       }   
    };    
})(); 
</script>
<script type="text/javascript">
//关联评估单
function linkCase(evaCode){
	var data={};
	data.rows = 10;
    data.page = 1;
	data.evaCode=evaCode;
	data.queryId = "queryConnectEvalReport";
	console.log(evaCode);
	//关联评估单信息
		$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data, 
        success: function(data){
      	  var myTaskList = template('template_myTaskList' , data);
			  $("#evalContainer").empty();
			  $("#evalContainer").html(myTaskList);
        }
  		});
		//关联发票信息
		var postEvaCode={};
		postEvaCode.evaCode=evaCode;
		$.ajax({
		async: true,
        url:ctx+ "/eval/getInvoiceInfo" ,
        method: "post",
        dataType: "json",
        data: postEvaCode, 
        success: function(data){
			console.log(data);
			$('#invoiceType').val(data.invoiceType);
			$('#invoiceHeader').val(data.invoiceHeader);
			$('#invoiceAmount').val(data.invoiceAmount/10000);
			$('#invoiceAddress').val(data.invoiceAddress);
			$('#taxNum').val(data.taxNum);
			$('#pkid').val(data.pkid);
			$('#evaPointer').val(data.evaPointer);
			
        }
  		});
	$('#myModal').modal('hide');
}


function toConnectEvalReport(){
	var caseCode=$("#caseCode").val();
	var ctx = $("#ctx").val();
	var url=ctx+'/eval/invoiceConnectEval?caseCode='+caseCode;
	window.open(url);
}
//显示附件图片
function showAttachment(url){
	window.open(url);
	
}
//提交数据
function submit() {	
	var pkid=$("#pkid").val();
	if(!pkid){
	 window.wxc.alert("请以正确的方式进入该页面,并关联评估单");
	return;		
	}
	save(true);
}

//保存数据
function save(b) {	
	if(b){
		if (!checkForm()) {
			return;
		}													
	} 
	var jsonData={};
	var pkid=$('#pkid').val();
	var applyDate=$('#applyDate').val();
	var toFinshDate=$('#toFinshDate').val();
	var taskId=$('#taskId').val();
	var processInstanceId=$('#processInstanceId').val();
	var caseCode=$('#caseCode').val();
	var evaPointer=$('#evaPointer').val();
	
	jsonData.pkid=pkid;
	jsonData.applyDate=applyDate;
	jsonData.toFinshDate=toFinshDate;
	jsonData.taskId=taskId;
	jsonData.processInstanceId=processInstanceId;
	jsonData.caseCode=caseCode;
	jsonData.evaPointer=evaPointer;
	
	var url = "${ctx}/eval/submitIssueInvoice";
	
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
        success: function(data){
            $.unblockUI();
            console.log(data);
            if (b) {
                if (data.message) {
                    window.wxc.alert("提交成功"+data.message);
                }
                var ctx = $("#ctx").val();
                window.location.href=ctx+ "/task/myTaskList";
            }else{
            	if (data.message) {
                    window.wxc.alert("提交成功"+data.message);
                }
            }
        },
        error:function(){
        	window.wxc.alert("提交信息出错。。");
        }
    });
}

//double验证
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

//验证控件checkUI();
function checkForm() {
	if(!$("#applyDate").val()){
		window.wxc.alert("申请日期为必填项!");
		$("#applyDate").focus();
		$("#applyDate").css("border-color","red");
		return false;
	}	
	if(!$("#toFinshDate").val()){
		window.wxc.alert("预计开票完成时间为必填项!");
		$("#toFinshDate").focus();
		$("#toFinshDate").css("border-color","red");
		return false;
	}
		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});

</script>
<style type="text/css">
.btn_blue {
  background-color: #428fcc !important;
  color: #fff;
}
.radio.radio-inline > label{margin-left:10px;}
.radio.radio-inline > input{margin-left:10px;}
.checkbox.checkbox-inline > div{margin-left:25px;}
.checkbox.checkbox-inline > input{margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.ibox-content-task{padding-bottom:40px !important;}
#corss_area{padding:0 8px 0 0;margin-left:369px;}
#corss_area select{height:34px;border-radius:2px;margin-left:20px;}
</style>
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
	</script> 
	
	<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
						var caseCode=$("#caseCode").val();
						AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode);
						//开启EvalReportList
						var postData={};
						postData.queryId="queryConnectEvalReport";
						postData.caseCode=caseCode;
						EvalReportList.init('${ctx}','/quickGrid/findPage','evalGridTable','evalGridPager','${ctmCode}',caseCode,postData);
						
						//日历控件
					    $('.input-daterange').datepicker({
					        keyboardNavigation: false,
					        forceParse: false,
					        autoclose: true
					    });
						
					//设置div显示或隐藏
					function isShow(divName, stats) {
					    var div_array = document.getElementsByName(divName);   
					    for(i=0;i<div_array.length;i++)  
					    {  
						    div_array[i].style.display = stats; 
					    }  
					}
			      
		            $("[name=businessLoanWarn]").click(function(){
		                if($(this).val()=='1'){
		                    $("#divContent").css("display","inherit");
		                }else{
		                    $("#divContent").css("display","none");
		                }
		            });
		            
					//日期组件
			        $('#invoiceApplyTime').datepicker({
			            todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: false,
			            autoclose: true
			        });
					
			      //日期组件
			        $('#invoiceFinishTime').datepicker({
			            todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: false,
			            autoclose: true
			        });
		            
			        $('#reloadButton').on('click', function() {     //页面上的button按钮的click事件，重新获取参数后发送参数，然后重新加载数据。  
				 		  //获取输入框内容  
				  		var caseCode=$('#innerCaseCode').val();
				  		var propertyAddr=$('#propertyAddr').val();
				  		var evaCode=$('#evaCode').val();
				  		var guestName=$('#guestName').val();
				  		var agentName=$('#agentName').val();
				  		var loanName=$('#loanName').val();
				  		var evaCompany=$('#evaCompany').val();
				  		var postData={};
				  		if(caseCode){
				  			postData.caseCode=caseCode;
				  		}else{
				  			postData.caseCode='';
				  		}
				  		if(propertyAddr){
				  			postData.propertyAddr=propertyAddr;
				  		}else{
				  			postData.propertyAddr='';
				  		}
				  		if(evaCode){
				  			postData.evaCode=evaCode;
				  		}else{
				  			postData.evaCode='';
				  		}
				  		if(guestName){
				  			postData.guestName=guestName;
				  		}else{
				  			postData.guestName='';
				  		}
				  		if(agentName){
				  			postData.agentName=agentName;
				  		}else{
				  			postData.agentName='';
				  		}
				  		if(loanName){
				  			postData.loanName=loanName;
				  		}else{
				  			postData.loanName='';
				  		}
				  		if(evaCompany){
				  			postData.evaCompany=evaCompany;
				  		}else{
				  			postData.evaCompany='';
				  		}
				  		
				  		postData.queryId="queryConnectEvalReport";
				 		 
				 	        $("#evalGridTable").jqGrid('setGridParam',{  
				 	        	postData :postData //发送数据  	             
				 	        }).trigger("reloadGrid"); //重新载入  
				 	});
			        
					})//end ready function
					
				</script> 
	</content>
</body>
</html>