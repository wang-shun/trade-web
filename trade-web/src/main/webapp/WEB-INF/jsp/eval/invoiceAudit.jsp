<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script type="text/javascript">
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";

	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
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
<script type="text/javascript">
//显示附件图片
function showAttachment(url){
	window.open(url);
	
}
//提交数据
function submit() {	
	var caseCode=$("#caseCode").val();
	if(!caseCode){
	 window.wxc.alert("请以正确的方式进入该页面！");
	return;		
	}
	save(true);
}

//保存数据
function save(b) {
	var caseCode=$("#caseCode").val();
	if(!caseCode){
	 window.wxc.alert("请以正确的方式进入该页面！");
	return;		
	}
	
	if(b){
		if (!checkForm()) {
			return;
		}													
	}
	
	var jsonData = $("#evalInvoiceAuditform").serializeArray();

	var url = "${ctx}/eval/submitInvoiceAudit";

	
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
	if($("#distCode").val()==0){
		window.wxc.alert("区域为必选项!");
		$("#distCode").focus();
		$("#distCode").css("border-color","red");
		return false;
	}
		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});

</script>
<style type="text/css">
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
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="wrapper white-bg new-heading" id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        	接单跟进
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

        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
            <h2 class="newtitle title-mark">开票信息</h2>
            	<form method="get" class="form_list" id="firstFollowform" style="overflow: visible;">
            		<input type="hidden" id="ctx" value="${ctx }" />
            	
            		<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${toEvaInvoice.caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="caseId" name="caseId" value="${firstFollow.caseId }">
					<input type="hidden" id="tTLId" name="tTLId" value="${firstFollow.tTLId }">
					<input type="hidden" id="signId" name="signId" value="${firstFollow.signId}">
					<input type="hidden" id="propertyInfoId" name="propertyInfoId" value="${firstFollow.propertyInfoId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<%-- T_TO_FIRST_FOLLOW 表  --%>
					<input type="hidden" id="firstfollowId" name="firstfollowId" value="${firstFollow.firstfollowId}" />

	            
		            <div class="marinfo">
		                <div class="line">		                 
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">评估公司：</label>
		                        <input type="text" class="input_type yuanwid" value="${toEvaInvoice.evaCompanyName}" readonly="readonly"> 
		                    </div>
		                    
		                    <div class="form_content">
		                        <!-- <label class="control-label sign_left_small">申请日期：</label> -->                  
		                        <label class="control-label sign_left_small select_style mend_select">申请日期：</label>
		                        <input class="input_type yuanwid" type="text" readonly="readonly" value="<fmt:formatDate value='${toEvaInvoice.applyDate}' pattern='yyyy-MM-dd'/>">
		                    </div>	
		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">发票金额：</label> 
		                        <input type="text"  class="input_type yuanwid" value="<fmt:formatNumber value='${toEvaInvoice.invoiceAmount}' type='number' pattern='#0.00'/> "  readonly="readonly">		                        
		                    	<span>元</span>/>
		                    </div>	                     
		                </div>
		                
		                <div class="line">		                 
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">发票种类：</label>
		                        <input type="text"  class="input_type yuanwid" value="${toEvaInvoice.invoiceType}"  readonly="readonly"> 
		                    </div>
		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small">开票抬头：</label> 
		                        <input type="text"  class="input_type yuanwid" value="${toEvaInvoice.invoiceHeader}"  readonly="readonly">
		                    </div>			                  	                     
		                </div>
					<hr>	          

				</div>	                
	                <!-- <div class="line clearfix" id="hzxm" style="overflow:visible;"></div> -->
            	</form>

	            <div class="title title-mark" id="aboutInfo">
	               <strong style="font-weight:bold;">开票审批记录</strong>
	            </div>
	            
	            <div class="view-content">
	              	<table id="gridTable" class=""></table>
	   				<div id="gridPager"></div>
	            </div>
	            <br><hr>
	            <div class="line">	
		            <div class="title title-mark" id="aboutInfo">
		               <strong style="font-weight:bold;">填写审批任务</strong>
		            </div>
	            </div>
	            <form method="get" class="form_list" id="evalInvoiceAuditform" style="overflow: visible;">
	            <input type="hidden" name="partCode" value="evalInvoiceAudit">
	            <input type="hidden" id="caseCode1" name="caseCode" value="${toEvaInvoice.caseCode}">
	            <div class="line">		                 
		                    <div class="form_content">
		                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>审批结果：</label>
		                        <input type="radio" name="status" value="1"><span>通过</span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    							<input type="radio" name="status" value="0"><span>驳回</span>
		                    </div>
			                  	                     
		                </div>
		        
		        <div class="line">		                 		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>审批意见：</label> 
		                        <input type="text"  class="input_type mendwidth" id="content" name="content" 
										value="" maxlength="64">
		                    </div>			                  	                     
		                </div>        	          	            
			</form>
	            <div class="form-btn">
	                    <div class="text-center">
	                        <button  class="btn btn-success btn-space" onclick="javascript:window.close()" id="btnSave">关闭</button>
	                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
	                    </div>
	                </div>
	            </div>
			</div>
	
			<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
			<content tag="local_script"> 
				<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
				<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
				<%-- <script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script> --%>
				<%-- <script src="<c:url value='/transjs/task/showAttachment.js' />"></script>  --%>
				<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
				<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
				<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
				<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
				<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
				<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
				<%-- <script src="<c:url value='/static/js/jquery.json.min.js' />"></script> --%>
				<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
				<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
				<script src="<c:url value='/js/trunk/task/taskFirstFollow.validate.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
				<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
				<script src="<c:url value='/js/template.js' />"></script>
				<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
				<script src="<c:url value='/js/stickUp.js' />"></script>
				<!-- 改版引入的新的js文件 -->
				<script src="<c:url value='/js/common/textarea.js' />"></script>
				<script src="<c:url value='/js/common/common.js' />"></script>
				<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
/* 						if(!$("#caseCode").val()){
							$("#caseCode").val("ZY-TJ-2017080038");						
						} */
						var caseCode=$("#caseCode").val();
						AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode);
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
		            
					})//end ready function
					
				</script> 
			</content>
	</body>
</html>
