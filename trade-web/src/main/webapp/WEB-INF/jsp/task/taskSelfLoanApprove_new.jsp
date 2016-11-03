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
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
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
	<div class="row wrapper white-bg new-heading ">
            <div class="pl10">
                <h2 class="newtitle-big">
                    	自办贷款审批
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
            <h2 class="newtitle">填写任务信息</h2>
            <form method="get" class="form_list flowVisiable" id="selfLoanForm">
				<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
				<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
				<input type="hidden" id ="lastLoanBank" name="lastLoanBank" value="${SelfLoan.lastLoanBank}">
				<input type="hidden" id="pkid" name="pkid" value="${SelfLoan.pkid}">
				
	            <div class="marinfo">
	                <div class="line">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"> 主贷人 </label> 
	                        <input class=" input_type yuanwid" placeholder="" value="${custName}" readonly="readonly" style="background-color:#ccc;">
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>贷款总额 </label> 
	                        <input type="text" name="mortTotalAmount" id="mortTotalAmount" class="input_type yuanwid"  onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${SelfLoan.mortTotalAmount/10000}' type='number' pattern='#0.00' />">
	                       <span class="date_icon">万元</span>
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>贷款类型</label>
	                        <aist:dict id="mortType" name="mortType"
									clazz="select_control data_style" display="select" dictType="30016"
									defaultvalue="${SelfLoan.mortType }" />
	                    </div>
	                </div>
	                <div class="line">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small">主贷人单位</label> 
	                        <input class="input_type mendwidth" placeholder="" value="${custCompany}" readonly="readonly" style="background-color:#ccc;">
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>认定套数</label> 
	                        <input type="text" name="houseNum" id="houseNum" class="input_type data_style" value="${SelfLoan.houseNum}" onkeyup="checkNum2(this)">
	                    </div>
	                </div>
	                <div class="line">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>商贷部分金额</label>
	                        <input type="text" name="comAmount" id="comAmount" class="input_type yuanwid" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${SelfLoan.comAmount/10000}' type='number' pattern='#0.00' />"> 
	                       <span class="date_icon">万元</span>
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>商贷利率折扣</label> 
	                        <input type="text" name="comDiscount" id="comDiscount" class="input_type yuanwid" onkeyup="autoCompleteComDiscount(this)" placeholder="0.50~1.50之间"
								value="<fmt:formatNumber value='${SelfLoan.comDiscount}' type='number' pattern='#0.00' />">
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>商贷部分年限</label> 
	                        <input type="text" name="comYear" id="comYear" class="input_type data_style" value="${SelfLoan.comYear}" onkeyup="checkNum2(this)">
	                    </div>
	                </div>
	                <div class="line">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small">公积金贷款金额</label> 
	                        <input type="text" name="prfAmount" id="prfAmount" class="input_type yuanwid" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${SelfLoan.prfAmount/10000}' type='number' pattern='#0.00' />">
	                       <span class="date_icon">万元</span>
	                    </div>
	                    <div class="form_content">
	                        <label class="control-label sign_left_small">公积金贷款年限</label> 
	                        <input type="text" name="prfYear" id="prfYear" class="input_type data_style" value="${SelfLoan.prfYear}" onkeyup="checkNum2(this)">
	                    </div>
	
	                </div>
	                <div class="line flowVisiable clearfix">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>承办银行 </label> 
	                        <select class="select_control data_style" name="bank" id="bank"></select>
	                    </div>
	                </div>
	                <div class="line flowVisiable clearfix">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>支行名称 </label> 
	                        <select class="select_control data_style" name="finOrgCode" id="finOrgCode">
							</select>
	                    </div>
	                </div>
	            </div>
	        </form>
	        
	        <div class="view-content" id="caseCommentList"></div>
	
		    <div class="form-btn">
	        	<div class="text-center">
	                <button  class="btn btn-success btn-space" onclick="save(false)">保存</button>
	                <button class="btn btn-success btn-space" onclick="submit()" readOnlydata='1'>提交</button>
	            </div>
		    </div>
	</div>

	<content tag="local_script"> 
		<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
		<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
		<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
		<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
		<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<!-- 改版引入的新的js文件 --> 
		<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
		<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
		
		<script>
			var source = "${source}";
			
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
				
				 //银行初始化
				 $("#bank").change(function(){
					 var selectValue = $("#bank").val(); 
					 getBranchBankList(selectValue)
				 });
	
				 getBankList(finOrgCode);
				 //结束
				 
				 //案件备注信息
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : taskitem
				});
			});
			
			//获取银行列表
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
							friend.chosen({no_results_text:"未找到该选项",width:"495px",search_contains:true,disable_search_threshold:10});
		    				if(pcode == null || pcode == "" || pcode == undefined) {
		    					getBranchBankList(data.bankList[0].finOrgCode);
		    				} else {
		    					getBranchBankList(data.bankCode);
		    				}
			    		}
			    	}
				  });
			}
			
			//获取支行列表
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
							friend.chosen({no_results_text:"未找到该选项",width:"495px",search_contains:true,disable_search_threshold:10});
			    		}
			    	}
				  });
			}
			
			//提交数据
			function submit() {
				save(true);
			}
	
			//保存数据
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
					async : false,
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
	                	//超时,status还有success,error等值的情况
	                	if(status=='timeout'){
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
					},
					error : function(errors) {
						alert("数据保存出错" + errors);
					}
				});
			}
			
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
	
			//贷款折扣自动补全
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
			
			//double 验证
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
				if($('#mortType').val()=='') {
	                alert("贷款类型为必填项!");
	                $('input[name=mortType]').focus();
	                return false;
	            }
				
				if($('input[name=mortTotalAmount]').val()=='') {
	                alert("贷款总额为必填项!");
	                $('input[name=mortTotalAmount]').focus();
	                return false;
	            }
	           
	            var _mortType = $('#mortType').find(':selected').val();
	           
				if($('input[name=comAmount]').val()==''&&_mortType!='30016003') {
	                alert("商贷部分金额为必填项!");
	                $('input[name=comAmount]').focus();
	                return false;
	            }
				
				if($('input[name=comYear]').val()==''&&_mortType!='30016003') {
	                alert("商贷部分年限为必填项!");
	                $('input[name=comYear]').focus();
	                return false;
	            }
				
				if($('input[name=comDiscount]').val()==''&&_mortType!='30016003') {
	                alert("商贷部分利率折扣为必填项!");
	                $('input[name=comDiscount]').focus();
	                return false;
	 			} 
	
				if($('input[name=comDiscount]').val()!=''&&_mortType!='30016003') {
					if(isNaN($('input[name=comDiscount]').val())){
		                alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
		                $('input[name=comDiscount]').focus();				
		                return false;
		            }else if($('input[name=comDiscount]').val()>1.5 || $('input[name=comDiscount]').val()<0.5){
		        		alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		            	$('input[name=comDiscount]').focus();
		        		return false;
		        	}else if($('input[name=comDiscount]').val()<=1.5 || $('input[name=comDiscount]').val()>=0.5){
		        		var reg =/^[01]{1}\.{1}\d{3,}$/;
		        		if(reg.test($('input[name=comDiscount]').val())){
		        			alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		        			$('input[name=comDiscount]').focus();
		        			return false;
		        		}
		        	}
	 			} 			
				
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
				 
				var comAmount1 = isNaN(comAmount)?0:comAmount;
				
				if((mortTotalAmount-prfAmount).toFixed(5)!=comAmount1){
					alert('贷款总额必须等于商贷和公积金之和');
			    	return false;
				}
				
				return true;
			}
		</script> 
	</content>
</body>
</html>