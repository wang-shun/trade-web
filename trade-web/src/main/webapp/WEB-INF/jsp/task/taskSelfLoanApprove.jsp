<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">

<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
	var custCode = "${SelfLoan.custCode}";
	var finOrgCode = "${SelfLoan.lastLoanBank}";
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>自办贷款审批</h2>				
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="selfLoanForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<input type="hidden" id ="lastLoanBank" name="lastLoanBank" value="${SelfLoan.lastLoanBank}">
					
					<input type="hidden" id="pkid" name="pkid" value="${SelfLoan.pkid}">
					<div class="form-group">
						<label class="col-sm-2 control-label">贷款类型：</label>
						<div class="col-sm-2">
							<aist:dict id="mortType" name="mortType"
								clazz="form-control m-b" display="select" dictType="30016"
								defaultvalue="${SelfLoan.mortType }" />

						</div>
						   
                       <label class="col-sm-2 control-label">主贷人：${custName}</label>
                       <label class="col-sm-2 control-label">主贷人单位：${custCompany}</label>
                   
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">贷款总额<font color="red">*</font></label>
						<div class="col-md-2">
						<div class="input-group">
							<input type="text" name="mortTotalAmount" id="mortTotalAmount" class="form-control"  onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${SelfLoan.mortTotalAmount/10000}' type='number' pattern='#0.00' />">
								<span class="input-group-addon">万</span>
								</div>
						</div>
						<label class="col-sm-2 control-label">商贷部分金额<font color="red">*</font></label>
						<div class="col-md-2">
							<div class="input-group">
							<input type="text" name="comAmount" id="comAmount" class="form-control" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${SelfLoan.comAmount/10000}' type='number' pattern='#0.00' />">
								<span class="input-group-addon">万</span>
								</div>
						</div>
						<label class="col-sm-2 control-label">商贷部分年限<font color="red">*</font></label>
						<div class="col-md-2">
							<input type="text" name="comYear" id="comYear" class="form-control" value="${SelfLoan.comYear}" onkeyup="checkNum2(this)">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商贷部分利率折扣<font color="red">*</font></label>
						<div class="col-md-2">
							<input type="text" name="comDiscount" id="comDiscount" class="form-control" onkeyup="checkNum(this)" placeholder="例如: 0.8或0.95"
								value="<fmt:formatNumber value='${SelfLoan.comDiscount}' type='number' pattern='#0.00' />">

						</div>
						<label class="col-sm-2 control-label">公积金贷款金额</label>
						<div class="col-md-2">
							<div class="input-group">
							<input type="text" name="prfAmount" id="prfAmount" class="form-control" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${SelfLoan.prfAmount/10000}' type='number' pattern='#0.00' />">
								<span class="input-group-addon">万</span>
								</div>
						</div>
						<label class="col-sm-2 control-label">公积金贷款年限</label>
						<div class="col-md-2">
							<input type="text" name="prfYear" id="prfYear" class="form-control" value="${SelfLoan.prfYear}" onkeyup="checkNum2(this)">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">认定套数<font color="red">*</font></label>
						<div class="col-md-2">
							<input type="text" name="houseNum" id="houseNum" class="form-control" value="${SelfLoan.houseNum}" onkeyup="checkNum2(this)">
						</div>
						
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">承办银行<font color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control m-b chosen-select" name="bank" id="bank">
							</select>
						</div>
						
						<label class="col-sm-2 control-label">支行名称<font color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control m-b chosen-select" name="finOrgCode" id="finOrgCode">
							</select>
						</div>
					</div>
					
				</form>

			</div>
		</div>
		
		<div class="ibox-title">
			<a href="#" class="btn" onclick="save(false)">保存</a>
			<a href="#" class="btn btn-primary" onclick="submit()" readOnlydata='1'>提交</a>
		</div>
	</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

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
	
	/*校验自办贷输入的折扣值*/
	/*function checkInputNum(obj){
		var inputVal = obj.value;
		if(inputVal!=''){
			if(inputVal>1||inputVal<=0){
			obj.value='';
			alert('商贷利率折扣应该在0~1之间, 最大值可以为1');
			}else if(inputVal==1){
			}else if(inputVal>0&&inputVal<1){
				reg= /^[0]{1}\.{1}(\d{1,2})?$/;
				if(!reg.test(inputVal)){
					obj.value='';
					alert('商贷利率折扣应该为小数点后一到两位小数, 例如:0.8或者0.95');
				}
			}	
		}
	}*/
	
		$(document).ready(function() {
			if('caseDetails'==source){
				readOnlyForm();
			}
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : true,
				autoclose : true
			});
			
			/*银行初始化*/
			 $("#bank").change(function(){
				 var selectValue = $("#bank").val(); 
				 getBranchBankList(selectValue)
			 });

			 getBankList(finOrgCode);
			 /*结束*/
		});
		
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
						friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
	    				if(pcode == null || pcode == "" || pcode == undefined) {
	    					getBranchBankList(data.bankList[0].finOrgCode);
	    				} else {
	    					getBranchBankList(data.bankCode);
	    				}
		    		}
		    	}
			  });
		}
		
		/*获取支行列表*/
		function getBranchBankList(pcode){
			var friend = $("#finOrgCode");
			if(document.getElementById("finOrgCode")["options"].length > 0) {
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
						friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
		    		}
		    	}
			  });
		}
		
		/**提交数据*/
		function submit() {
			save(true);
		}

		/**保存数据*/
		function save(b) {
			if(!checkForm()) {
				return;
			}
			$("#lastLoanBank").val($("#finOrgCode").val());
			var jsonData = $("#selfLoanForm").serializeArray();
			
			var url = "${ctx}/task/mortgage/saveSelfLoanApprove";
			if(b) {
				url = "${ctx}/task/mortgage/submitSelfLoanApprove";
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
                	if(!b){
                        $.unblockUI();   
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
						 window.close();
						 if(window.opener)
					     {
							 window.opener.callback();
					     } 
						 //window.location.href = "${ctx }/task/myTaskList";
				},
				error : function(errors) {
					alert("数据保存出错" + errors);
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
		
	    function checkNum2(obj) {
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
		}
		
		//验证控件checkUI();
		function checkForm() {
			if($('input[name=mortType]').val()=='') {
                alert("贷款类型为必填项!");
                $('input[name=mortType]').focus();
                return false;
           }
			if($('input[name=mortTotalAmount]').val()=='') {
                alert("贷款总额为必填项!");
                $('input[name=mortTotalAmount]').focus();
                return false;
           }
			/* if($('input[name=commet]').val()=='') {
                alert("备注为必填项!");
                $('input[name=commet]').focus();
                return false;
           } */
			if($('input[name=comAmount]').val()=='') {
                alert("商贷部分金额为必填项!");
                $('input[name=comAmount]').focus();
                return false;
           }
			if($('input[name=comYear]').val()=='') {
                alert("商贷部分年限为必填项!");
                $('input[name=comYear]').focus();
                return false;
           }
			if($('input[name=comDiscount]').val()=='') {
                alert("商贷部分利率折扣为必填项!");
                $('input[name=comDiscount]').focus();
                return false;
/* 			}else if(isNaN($('input[name=comDiscount]').val())){
                alert("请输入0~1之间的合法数字");
                $('input[name=comDiscount]').focus();				
                return false;
            }else if($('input[name=comDiscount]').val()>1 || $('input[name=comDiscount]').val()<=0){
        		alert('商贷利率折扣应该在0~1之间, 最大值可以为1');
            	$('input[name=comDiscount]').focus();
        		return false;
        	}else if($('input[name=comDiscount]').val()>0&&$('input[name=comDiscount]').val()<1){
        		var reg= /^[0]{1}\.{1}(\d{1,2})?$/;
        		if(!reg.test($('input[name=comDiscount]').val())){
        			alert('商贷利率折扣应该为小数点后一到两位小数, 例如:0.8或者0.95');
        			$('input[name=comDiscount]').focus();
        			return false; 
        		}*/
           	}
			
			/* if($('input[name=prfAmount]').val()=='') {
                alert("公积金贷款金额为必填项!");
                $('input[name=prfAmount]').focus();
                return false;
           } */
		/* 	if($('input[name=prfYear]').val()=='') {
                alert("公积金贷款年限为必填项!");
                $('input[name=prfYear]').focus();
                return false;
           } */
			if($('input[name=houseNum]').val()=='') {
                alert("认定套数为必填项!");
                $('input[name=houseNum]').focus();
                return false;
           }
			if($('input[name=finOrgCode]').val()=='') {
                alert("支行名称为必填项!");
                $('input[name=finOrgCode]').focus();
                return false;
           }
			var prfAmoutStr=$("#prfAmount").val();
			var prfAmount=prfAmoutStr==''?0:parseFloat(prfAmoutStr);
			var mortTotalAmount=parseFloat($("#mortTotalAmount").val());
			var comAmount=parseFloat($("#comAmount").val());
			if((mortTotalAmount-prfAmount).toFixed(5)!=comAmount){
				alert('贷款总额必须等于商贷和公积金之和');
		    	return false;
			}
			
			
			return true;
		}
	</script> 
	</content>
</body>


</html>