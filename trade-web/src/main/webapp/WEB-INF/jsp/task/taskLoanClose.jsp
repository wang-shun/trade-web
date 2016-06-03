<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<!-- jdGrid相关 -->

<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<!-- datepikcer -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var finOrgCode = "${loanClose.mortgageBank}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<div class="row">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>贷款结清</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>完成提醒</h5>
			<a class="btn btn-primary pull-right" href="#" id="sendSMS">发送短信提醒</a>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="loanCloseForm">
					<%--环节编码 --%>
					<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid" value="${loanClose.pkid}">
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">还款时间<font color="red">*</font></label>
						<div class="input-group date readOnly_date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" id="loanCloseCode" name="loanCloseCode" style="width:127px"
								value="<fmt:formatDate  value='${loanClose.loanCloseCode}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">上手抵押金额<font color="red">*</font></label>
						<div class="col-sm-2" style="padding-left: 0px;">
						<div class="input-group">
							<input type="text" class="form-control" id="uncloseMoney" name="uncloseMoney" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ loanClose.uncloseMoney}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div>
						</div>
						<label class="col-sm-2 control-label">还款资金来源<font color="red">*</font></label>
						<div class="col-sm-2 input-group">
							<aist:dict clazz="form-control m-b" id="closeType" name="closeType" display="select" defaultvalue="${loanClose.closeType}" dictType="LoanCloseMethod" />
						</div>
					</div>
					
					<div class="form-group">
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">抵押银行<font color="red">*</font></label>
						<div class="col-sm-4"  style="padding-left: 0px;">
							<select class="form-control m-b chosen-select" name="bank" id="bank">
							</select>
						</div>
						
						<label class="col-sm-2 control-label">支行名称<font color="red">*</font></label>
						<div class="col-sm-4 input-group">
							<select class="form-control m-b chosen-select" name="mortgageBank" id="mortgageBank">
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10 input-group">
							<input type="text" class="form-control" id="comment" name="comment" value="${loanClose.comment}">
						</div>
					</div>
				</form>

			</div>
		</div>

		<div class="ibox-title">
			<a href="#" class="btn" onclick="save(false)">保存</a>
			<a href="#" class="btn btn-primary" onclick="submit()" readOnlydata="1">提交</a>
		</div>
		<div id="smsPlatFrom"></div>
	</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<script src="${ctx}/transjs/sms/sms.js"></script> 
		<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script> 
	<script>
	var source = "${source}";
	function readOnlyForm(){
		$(".readOnly_date").removeClass('date');
		$(".readOnly_date input").attr('readOnly',true);
		$("select[readOnlydata=1]").closest('.row').hide();
		$("[readOnlydata=1]").attr('readonly',true);
		$("[readOnlydata=1]").each(function(){
			if($(this).is('a')){
				$(this).hide();
			}
		});
	}
		$(document).ready(
			function() {
				if('caseDetails'==source){
					readOnlyForm();
				}
				$("#sendSMS").click(function(){
					var t='';
					var s='/';
					$("#reminder_list").find("input:checkbox:checked").closest('td').next().each(function(){
						t+=($(this).text()+s);
					});
					if(t!=''){
						t=t.substring(0,t.length-1);
					}
					$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
				});
				$("#reminder_list").jqGrid({
					url:"${ctx}/quickGrid/findPage",
					datatype : "json",
					height:210,
					multiselect : true,
					autowidth : true,
					shrinkToFit : true,
			        // rowNum:8,
			        viewrecords:true,
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
					// pager : "#pager_list_1",
					viewrecords : false,
					pagebuttions : false,
					hidegrid : false,
					// recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					// pgtext : " {0} 共 {1} 页",
					postData:{
			        	queryId:"queryToReminderList",
			        	search_partCode: taskitem
			        },
	
				});
	
				$('#data_1 .input-group.date').datepicker({
					todayBtn : "linked",
					keyboardNavigation : false,
					forceParse : false,
					calendarWeeks : false,
					autoclose : true
				});
				
				$("#bank").change(function(){
					 var selectValue = $("#bank").val(); 
					 getBranchBankList(selectValue)
				 });
				
				getBankList(finOrgCode);

		});
		
		/**提交数据*/
		function submit() {
			save(true);
		}

		/**保存数据*/
		function save(b) {
			if(!checkForm()) {
				return;
			}
			var jsonData = $("#loanCloseForm").serializeArray();
			
			var url = "${ctx}/task/closeLoan/saveToCloseLoan";
			if(b) {
				url = "${ctx}/task/closeLoan/submitCloseLoan";
			}
			
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
    		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  

                	$.unblockUI();  
                	if(b){ 
                        $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
    				    $(".blockOverlay").css({'z-index':'1900'});
                	}   

                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                } 
    		            } ,  
				success : function(data) {
					if(b) {
						caseTaskCheck();
						if(null!=data.message){
							alert(data.message);
						}
						//window.location.href = "${ctx }/task/myTaskList";
					} else {
						alert("保存成功。");
						 window.close();
						 window.opener.callback();
					}
				},
				error : function(errors) {
					alert("数据保存出错。");
				}
			});
		}

		/*double 验证*/
	    function checkNum(obj) { 
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
	        //保证只有出现一个.而没有多个.
	        obj.value = obj.value.replace(/\.{2,}/g,".");
	        //保证.只出现一次，而不能出现两次以上
	        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     }  
		//验证控件checkUI();
		function checkForm() {
			if($('input[name=loanCloseCode]').val()=='') {
                alert("还款时间为必填项!");
                $('input[name=loanCloseCode]').focus();
                return false;
           }
			if($('input[name=uncloseMoney]').val()=='') {
                alert("上手抵押金额为必填项!");
                $('input[name=uncloseMoney]').focus();
                return false;
           }
			if($('input[name=closeType]').val()=='') {
                alert("还款资金来源为必填项!");
                $('input[name=closeType]').focus();
                return false;
           }
			/* if($('input[name=commet]').val()=='') {
                alert("备注为必填项!");
                $('input[name=commet]').focus();
                return false;
           } */
			return true;
		}
		
		/*获取银行列表*/
		function getBankList(pcode){
			var friend = $("#bank");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryBankListByPcode",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":pcode},
		    	success:function(data){
		    		if(data.bankList != null){
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(data.bankCode == data.bankList[i].finOrgCode) {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"+data.bankList[i].finOrgName+"</option>");
		    				} else {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
		    				}
		    			}
	    				if(pcode == null || pcode == "" || pcode == undefined) {
	    					getBranchBankList(data.bankList[0].finOrgCode);
	    				} else {
	    					getBranchBankList(data.bankCode);
	    				}
						friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
		    		}
		    	}
			  });
		}
		
		/*获取支行列表*/
		function getBranchBankList(pcode){
			var friend = $("#mortgageBank");
			if(document.getElementById("mortgageBank")["options"].length > 0) {
				friend.chosen('destroy');
			}
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryBankListByParentCode",
			    method:"post",
			    dataType:"json",
			    data:{faFinOrgCode:pcode},
		    	success:function(data){
		    		if(data != null){
		    			for(var i = 0;i<data.length;i++){
		    				if(finOrgCode == data[i].finOrgCode) {
		    					friend.append("<option value='"+data[i].finOrgCode+"' selected='selected'>"+data[i].finOrgName+"</option>");
		    				} else {
		    					friend.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
		    				}
		    			}
		    		}
					friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
		    	}
			  });
		}
	</script> 
	</content>
</body>


</html>