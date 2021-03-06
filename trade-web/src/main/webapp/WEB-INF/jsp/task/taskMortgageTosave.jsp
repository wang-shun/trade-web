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
<title>自办贷款挽回</title>
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
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">


<script type="text/javascript">
	var ctx = "${ctx}";
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}

#corss_area{padding:0;}
#corss_area select{float:right;height:34px;border-radius:2px;margin-left:20px;}
.pb10{padding-bottom:15px;}
.select_control{
	color: inherit;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
 <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

	<div class="">
		<div class="row wrapper white-bg new-heading ">
			<div class="pl10">
				<h2 class="newtitle-big">
					自办贷款挽回
				</h2>
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

			<form method="get" class="form-horizontal" id="mortgageTosave">
			<%--环节编码 --%>
			<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
			<!-- 交易单编号 -->
			<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
			<!-- 流程引擎需要字段 -->
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">

			<input type="hidden" id="pkid" name="pkid" value="${loanReleasePlan.pkid}">
			
			<input type="hidden" id="service"  value="${service.mortgageServive}">
			
			<input type="hidden" id="bank"  value="${tosave.bank_type}">
			
			<input type="hidden" id="bank_zhi"  value="${tosave.finOrgCode}">
			
			<h2 class="newtitle title-mark">填写任务信息</h2>
			<div class="form_list">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">贷款方式</label>
									<div class="radio i-checks radio-inline">
									<c:if test="${tosave.selfMort == '1'}">
  											<label> <input type="radio" checked="checked" value="1" id="self" name="selfMort"> 自办
									</label> 
									&nbsp;&nbsp;&nbsp;
									<label> <input type="radio"  value="0" id="noself" name="selfMort"> 代办
									</label>
									</c:if>
									<c:if test="${tosave.selfMort == '0'}">
  											<label> <input type="radio"  value="1" id="self" name="selfMort"> 自办
									</label> 
									&nbsp;&nbsp;&nbsp;
									<label> <input type="radio" checked="checked" value="0" id="noself" name="selfMort"> 代办
									</label>
									</c:if>
									<c:if test="${tosave.selfMort == null}">
  											<label> <input type="radio"  value="1" id="self" name="selfMort"> 自办
									</label> 
									&nbsp;&nbsp;&nbsp;
									<label> <input type="radio" checked="checked" value="0" id="noself" name="selfMort"> 代办
									</label>
									</c:if>
									 
									</div>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">客户贷款需求</label>
							<aist:dict clazz="select_control" id="mortageService" name="mortageService"
									   display="select" defaultvalue="0" dictType="mortage_service" />
						</div>
			
					</div>
					<div class ="line">
					<div class="form_content">
                        <label class="control-label sign_left_small"><span class="star">*</span>贷款银行</label>
                        <select name="bank_type" class="select_control  " >
                        </select>
                        <select name="finOrgCode" class=" select_control  " >
                        </select>
                     </div> 
					</div>
	
					<div class="line">
							<div class="form_content">
                                <label class="control-label sign_left_small">贷款银行</label>
					 			<input type="text" name="bankName" id="bank" value='${tosave.bankName }' class="input_type data_style">
                            </div>
					</div>
					<div class="line">
							
							<label class="control-label sign_left_small"><span class="star">*</span>流失原因</label>
								  <textarea style="width: 80%;height:85px;border:1px solid #ccc;resize: none;padding:7px;"  name="content" id="">${tosave.content }</textarea>
					</div>
							
					</div>

		
				</div>
			
			</form>

			<div class="view-content" id="caseCommentList"> </div>

			<div class="form-btn">
				<div class="text-center">
					<button class="btn btn-success btn-space" onclick="saveMort()">保存</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>



<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
	<!-- jqGrid -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script> 
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 

	<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>

    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<!-- bank select -->
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	
	<script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	
	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>
	<script src="<c:url value='/js/trunk/case/caseBaseInfo.js'/>"></script>
	<script>
		$(document).ready(function(){
			$("#mortageService").find("option").eq(0).remove();
			$("#mortageService").find("option").eq(1).remove();
			
			
			var service = $("#service").val();
			var bank = $("#bank").val();
			//var bank_zhi = $("#bank_zhi").val();
			if(service){
				$("#mortageService").val(service);
			}
			
			f = $("#mortgageTosave");
			var cl;
			var finOrgCode = null;
			//alert(bank)
			getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),finOrgCode,cl,null,bank);
	
			f.find("select[name='bank_type']").unbind('change').bind('change',function(){
				getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),finOrgCode,'cl');
		    });
			if($("input[type=radio]").eq(1).is(":checked")){
				var f = $("#mortgageTosave");
				f.find(".line").eq(1).show();
				f.find(".line").eq(2).show();
				f.find(".line").eq(3).hide();
				f.find(".line").eq(4).hide();
			}
			
			if($("input[type=radio]").eq(0).is(":checked")){
			/* 	getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),finOrgCode,'cl');
				if(bank_zhi){
					f.find("select[name='finOrgCode']").val(bank_zhi);
				} */
				var f = $("#mortgageTosave");
				f.find(".line").eq(3).show();
				f.find(".line").eq(4).show();
				f.find(".line").eq(1).hide();
				f.find(".line").eq(2).hide();
			}
			
			$("input[type=radio]").eq(0).change(function(){
				if($("input[type=radio]").eq(0).is(":checked")){
					var f = $("#mortgageTosave");
					f.find(".line").eq(3).show();
					f.find(".line").eq(4).show();
					f.find(".line").eq(1).hide();
					f.find(".line").eq(2).hide();
				}
			}
			);
			
			$("input[type=radio]").eq(1).change(function(){
				if($("input[type=radio]").eq(1).is(":checked")){
			
					var f = $("#mortgageTosave");
					f.find(".line").eq(1).show();
					f.find(".line").eq(2).show();
					f.find(".line").eq(3).hide();
					f.find(".line").eq(4).hide();
				}
			}
			);
			


			$('#div_releasePlan .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				autoclose : true
			});
			
		});
	
		
 
        /**
        *代办检查非空
        */
        function checkDaiban(f){
        	if(f.find("select[name='bank_type']").val() == ""){
        		window.wxc.alert("贷款银行必填项！");
    			f.find("select[name='bank_type']").css("border-color","red");
    			return false;
        	}
        	if(f.find("select[name='finOrgCode']").val() == ""){
        		window.wxc.alert("贷款支行必填项！");
    			f.find("select[name='finOrgCode']").css("border-color","red");
    			return false;
        	}
        	return true;
        }
        
        /**
        *自办检查非空
        */
        function checkZiban(f){
        	if(f.find("textarea[name='content']").val() == ""){
        		window.wxc.alert("流失原因必填项！");
    			f.find("textarea[name='content']").css("border-color","red");
    			return false;
        	}
        	return true;
        }
        
        function saveMort(){
        	var jsonData = $("#mortgageTosave").serializeArray();
			var f = $("#mortgageTosave");
			var flag = false;
 			if(f.find("input[name='selfMort']").eq(1).is(":checked")){
				 flag = checkDaiban($("#mortgageTosave"));
			}else if(f.find("input[name='selfMort']").eq(0).is(":checked")){
				flag = checkZiban($("#mortgageTosave"))
			}
			var url = "${ctx}/task/mortgageTosave/save";
			if(flag){
			 $.ajax({
				cache : true,
				async : true,//false同步，true异步
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
						window.wxc.success("保存成功！",{"wxcOk":function(){
							 window.close();
	                         window.opener.callback();
						}});
						
						//$('#case-task-modal-form').modal("show");
				},
				error : function(errors) {
					 window.wxc.error("数据保存出错");
					 $.unblockUI();
				}
			}); 
        }
        }
        

        
        /**保存数据*/
		function save(b) {
			var jsonData = $("#mortgageTosave").serializeArray();
			var f = $("#mortgageTosave");
			var flag = false;
 			if(f.find("input[name='selfMort']").eq(1).is(":checked")){
				 flag = checkDaiban($("#mortgageTosave"));
			}else if(f.find("input[name='selfMort']").eq(0).is(":checked")){
				flag = checkZiban($("#mortgageTosave"))
			}
			var url = "${ctx}/task/mortgageTosave/submit";
			if(flag){
			 $.ajax({
				cache : true,
				async : true,//false同步，true异步
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
						window.wxc.success("提交成功！",{"wxcOk":function(){
							 window.close();
	                         window.opener.callback();
						}});
						
						//$('#case-task-modal-form').modal("show");
				},
				error : function(errors) {
					 window.wxc.error("数据保存出错");
					 $.unblockUI();
				}
			}); 
        }
		}
			
		
		/**提交数据*/
		function submit() {
			if($("input[type=radio]").eq(1).is(":checked")){
				var f = $("#mortgageTosave");
				f.find(".line").eq(3).empty();
				f.find(".line").eq(4).empty();
			}
			if($("input[type=radio]").eq(0).is(":checked")){
				var f = $("#mortgageTosave");
				f.find(".line").eq(1).empty();
				f.find(".line").eq(2).empty();
			}
			save(true);
		}
		//查询分行信息
		function getParentBank(selector,selectorBranch,finOrgCode,tag,flag,bank){
			var bankHtml = "<option value=''>请选择</option>";
			var param = {nowCode:finOrgCode};
			if(tag == 'cl'){
				param.tag = 'cl';
			}
		    $.ajax({
		    	cache:true,
		    	url:ctx+"/manage/queryParentBankList",
				method:"post",
				dataType:"json",
				async:false,
				data:param,
				success:function(data){
					if(data != null){
						for(var i = 0;i<data.length;i++){
							//if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
								var coLevelStr='';
								bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
							//}
						}
					}
					
				},
		       error:function(e){
		    	   window.wxc.error(e);
		       }
		     });
		    selector.find('option').remove();
			 selector.append($(bankHtml));
			
			 $.ajax({
				    url:ctx+"/manage/queryParentBankInfo",
				    method:"post",
				    dataType:"json",
					async:true,
				    data:{finOrgCode:finOrgCode,flag:flag},
				    success:function(data){
			    		if(data != null){
			    			selector.val(data.content);
			    			 if(bank){
			    				 //alert(111)
			    					selector.val(bank);
			    				}
			    		}
			    	}
				});
			
			 //selector.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
			// f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),finOrgCode,'cl'

			 getBranchBankList(selectorBranch,bank,finOrgCode,'cl');

			 return bankHtml;
		}
		//查询支行信息
		function getBranchBankList(selectorBranch,pcode,finOrgCode,tag,flag){
		var bank_zhi = $("#bank_zhi").val();
			selectorBranch.find('option').remove();
			f=selectorBranch.closest('form');
			
			selectorBranch[0];
			selectorBranch.append($("<option value=''>请选择</option>"));
			var param = {faFinOrgCode:pcode,flag:flag,nowCode:finOrgCode};
			if(tag == 'cl'){
				param.tag = 'cl';
			}
			$.ajax({
				cache:true,
			    url:ctx+"/manage/queryBankListByParentCode",
			    method:"post",
			    dataType:"json",
				async:true,
			    data:param,
			    	success:function(data){
			    		if(data != null){
			    			for(var i = 0;i<data.length;i++){
								var coLevelStr='('+data[i].coLevelStr+')';
					
								var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
								if(data[i].finOrgCode==finOrgCode){
									option.attr("selected",true);
								}
								
								selectorBranch.append(option);
								if(bank_zhi){
								selectorBranch.val(bank_zhi)
								}
			    			}
			    		}
			    		//finOrgCodeChange(f);
			    	}
			 });
			return true;
		}
	</script> 
	</content>
</body>

</html>