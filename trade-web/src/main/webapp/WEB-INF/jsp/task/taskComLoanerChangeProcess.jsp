<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<!-- datepikcer -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">

<link rel="stylesheet" href="${ctx}/css/common/step-mend.css" >
<style type="text/css">
.wizard-big.wizard>.content {
	min-height: 450px;
}

.wizard>.content {
	min-height: 550px;
}

.wizard>.content>.body label {
	text-align: right
}

.form-control {
	margin-bottom: 5px;
	height:32px;
}
#addToEguPricingForm > .col-sm-10{
	height:37px;
}
.form-group label {
	text-align: right;
}

.radio {
	margin-left: 20px;margin-top:-10px
}

.wizard.vertical > .steps{
	width:16%
}

.wizard > .steps > ul > li{
    width: 16%;
}
.mouseover-color{
	background-color:#B4D5F5;
}
.star{color:red}
/* .chosen-drop {
    height: 120px;
    overflow-y: scroll;
} */
.wizard > .content > .body {
    width:100%;
}

.wizard > .content > .body input {
    display: inline-block;
}
</style>
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
	<!-- 服务流程 -->
		<div class="panel " id="serviceFlow">
        <div class="row wrapper white-bg new-heading ">
             <div class="pl10">
                 <h2 class="newtitle-big">信贷员变更</h2>
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
							<h3>贷款签约</h3>
							<section>
							<div class="step-content" style="margin-top: -30px;">
								<div class="ibox" style="width:988px;">
								<div class="ibox-content">
								<form id="mortgageForm" class="form_list">
								    <input type="hidden" name="pkid" id="pkid"  value="${mortgage.pkid}"/>
									<input type="hidden" name="caseCode" value="${caseCode}">									
		                            <div class="marinfo">
		                                    <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 主贷人<span class="star">*</span></label> 
													 <select name="custCode" id="custCode" class="input_type yuanwid">
													 </select>
													 <input type="hidden" name="custName" id="custName" />
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 贷款总额<span class="star">*</span></label>
		                                             <input type="text" name="mortTotalAmount" id="mortTotalAmount" class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">贷款类型<span class="star">*</span></label>
														<aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="" />
		                                         </div>
		                                     </div>    
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">主贷人单位</label>
													 <input type="text" name="custCompany" id="custCompany"
														class="input_type mendwidth">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">认定套数</label>
													 <input type="text" name="houseNum" id="houseNum"
															class="input_type data_style" onkeyup="checkInt(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷金额<span class="star">*</span></label>
													 <input type="text" name="comAmount" id="comAmount"
															class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷利率折扣<span class="star">*</span></label>
													 <input type="text" name="comDiscount" id="comDiscount" placeholder="0.50~1.50之间"
															class="input_type yuanwid"  onkeyup="autoCompleteComDiscount(this)">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷年限<span class="star">*</span></label>
													 <input type="text" name="comYear" id="comYear"
														    class="input_type data_style" onkeyup="checknum(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款金额</label>
													 <input type="text" name="prfAmount" id="prfAmount"
															class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>		
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款年限</label>
													 <input type="text" name="prfYear" id="prfYear"
															class="input_type data_style" onkeyup="checknum(this)">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left">放款方式<span class="star">*</span></label>
													 <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
															display="select" dictType="30017" defaultvalue="" />
		                                         </div>
		                                     </div>
	
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 信贷员 <span class="star">*</span></label>
													 <input  type="text" readonly='readonly' name="loanerName" id="loanerName" placeholder="" class="input_type yuanwid"  onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',
															nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})">
													 <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage"  id="loanerNameImage" name ="loanerNameImage"  onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',
														nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})" >&#xe627;</i>
													 </input>			
													 <input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
													 <input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
													 <input type="hidden"  id="loanerId" name="loanerId" />
													 <div class="input-group float_icon organize_icon" ></div>	
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
													 <input type="text" name="loanerPhone" id="loanerPhone"
															placeholder="联系方式" class="input_type data_style">
		                                         </div>
		                                         <div class="form_content" style="margin-top:8px;">
		                                             <label class="control-label sign_left">信贷员到场</label> 
		                                             <div class="controls" >
											              <label class="radio inline"> <input type="radio" value="1" name="isLoanerArrive">是</label> 
								                          <label class="radio inline"> <input type="radio" value="0" name="isLoanerArrive" checked="checked">否</label>
		                                             </div>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small select_style mend_select">签约时间<span class="star" >*</span>
		                                             </label>
		                                             <div class="input-group sign-right dataleft input-daterange pull-left" id="date_4">
                                                            <input class="input_type yuanwid" type="text" placeholder="" name="signDate" id="signDate" readonly>
                                                     </div>										
		                                         </div>
		                                         <div class="form_content radio-seat" style="margin-top:7px;">
		                                             <label class="control-label sign_left_small">需要放款前报告</label>							             
										             <div class="controls">
                                                         <label class="radio inline"> <input type="radio" value="1" name="ifReportBeforeLend">是</label>
                                                         <label class="radio inline"> <input type="radio" value="0" name="ifReportBeforeLend" checked="checked">否</label>
                                                     </div>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content radio-seat" style="margin-top:5px;">
		                                             <label class="control-label sign_left_small">是否临时银行</label>
													 <div class="controls ">
                                                        <label class="radio inline"> <input type="radio" value="1" id="isTmpBank" name="isTmpBank" ${empty source?'':'readonly="true"' }>是</label>
                                                        <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" ${empty source?'':'readonly="true"' } checked="checked">否</label>
                                                     </div>
		                                         </div>
		                                         <div class="form_content" style="margin-left:-40px;">
		                                             <label class="control-label sign_left_small" style="width: 170px;">推荐函编号<span class="star">*</span></label>
													 <input type="text" name="recLetterNo" id="recLetterNo" class="input_type yuanwid">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content tmpBankReasonDiv">
		                                             <label class="control-label sign_left_small">临时银行原因<span class="star">*</span></label>
													 <input type="text" name="tmpBankReason" class="input_type optionwid">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">贷款银行</label>																										
													 <select  name="bank_type" class="select_control" id="bank_type" ></select>	
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small" style="width: 204px;">贷款支行<span class="star">*</span></label>
													 <select  name="finOrgCode" class="select_control" id="finOrgCode" ></select>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">补件名称</label>
											         <input type="text" class="input_type yuanwid" name="supContent" id="supContent">										
		                                         </div>
		                                         <div class="form_content" style="margin-left:-28px;">
		                                             <label class="control-label sign_left select_style mend_select" style="width: 204px;">补件时间</label>										             
										             <div class="input-group sign-right dataleft input-daterange pull-left" id="date_1" data-date-format="yyyy-mm-dd">
                                                            <input class="input_type yuanwid datatime" type="text" name="remindTime" id="remindTime" readonly>
                                                     </div>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">备注</label>
													 <input type="text" name="remark" id="remark" class="input_type optionwid">
		                                         </div>
		                                     </div>		                                     
		                               </div>
									</form>
								</div>
							</div>
						</div>
						
						
						<div class="form-btn">
		                    <div class="text-center">
		                        <button  class="btn btn-success btn-space" onclick="save(false)" id="btnSave">保存</button>
		                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
		                    </div>
		                </div>
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
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js?v=1.01"></script> 
<!-- 上传附件结束 -->
<script src="${ctx}/transjs/task/taskComLoanProcess.js?v=1.4.9"></script> 
<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
<script src="${ctx}/transjs/sms/sms.js"></script>	
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/stickUp.js"></script>
<!-- 改版引入的新的js文件 --> 
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script>
var afterTimeFlag=${afterTimeFlag};
var popInited=false;
//点击tab页面触发函数   -----From Bootstrap 标签页（Tab）插件
$(".nav-tabs").find("a").on('shown.bs.tab', function (e) {
	  var id = e.target.id;
	  if(id == 'tab1'){
		  //点击主选银行
		  $("#isMainLoanBank").val(1);
		  $("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(1);
	  }else if(id == 'tab2'){
		  //点击候选银行
		  $("#isMainLoanBank").val(0);
		  $("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(0);
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
	var step1 = ${step1};

	var evaCode = "${evaCode}";
	if(evaCode != ""){
		$("#eva_code").val(evaCode);
	}
 	var index = 0;
 	
	jQuery(document).ready(function() {
		$("#mortgageForm1").find("select[name='custCode']").change(guestCompanyReadOnly);
		$("select[name='mortType']").change(function(){
			var f=$(this).closest('form');
			if($(this).val()=='30016001'){
				f.find("input[name='prfAmount']").val('').prop('disabled',true);
    			f.find("input[name='prfYear']").val('').prop('disabled',true);
			}else{
				f.find("input[name='prfAmount']").prop('disabled',false);
    			f.find("input[name='prfYear']").prop('disabled',false);
			}
		});
		
		$("#sendSMS").click(function(){
					var t='';
					var s='/';
					$("#table_list_2").find("input:checkbox:checked").closest('td').next().each(function(){
						t+=($(this).text()+s);
					});
					if(t!=''){
						t=t.substring(0,t.length-1);
					}
					$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
		});
		
		$("#sendSMS1").click(function(){
					var t='';
					var s='/';
					$("#table_list_5").find("input:checkbox:checked").closest('td').next().each(function(){
						t+=($(this).text()+s);
					});
					if(t!=''){
						t=t.substring(0,t.length-1);
					}
					$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
		});
		
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
			$(this).find("option[value='30016003']").remove();
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
		//getPricingList("table_list_1","pager_list_1");
		//设置初始操作步骤
		getPricingList("table_list_3","pager_list_3",0);
		getPricingList("table_list_1","pager_list_1",1);
		 //银行下拉列表
		getParentBank($("#addToEguPricingForm").find("select[name='bank_type']"),$("#bank_branch_id"),"",null,"egu");
		
		$("#addToEguPricingForm").find("select[name='bank_type']").change(function(){
			/*$("#bank_branch_id").chosen("destroy");*/
    	getBranchBankList($("#bank_branch_id"),$("#addToEguPricingForm").find("select[name='bank_type']").val(),"",null,"egu");
        });
		 document.getElementById('optionsRadios1').checked=true;
            if($("input[name='optionsRadios']:checked").val()==0){
                $("#direct_launch_div").hide();
            }else{
                $("#direct_launch_div").show();
                 $("#addToEguPricingForm").find("input").each(function(){
                     $(this).removeAttr("disabled");
                     if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios" &&$(this).attr("type")!="hidden"){
                         $(this).val("");
                     }
                 });
                 $("#addToEguPricingForm").find("select").each(function(){
                     $(this).removeAttr("disabled");
                     if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios" &&$(this).attr("type")!="hidden"){
                         $(this).val("");
                     }
                 });
            }
		if(popInited)return true;
		popInited=true;
		$("input[name='optionsRadios']").click(function(){
			 if($(this).val()==0){
				 $("#direct_launch_div").hide();
				 $("#addToEguPricingForm").find("input").each(function(){
					 if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios"){
       					 $(this).attr("disabled","disabled");
					 }
   				 });
				 $("#addToEguPricingForm").find("select").each(function(){
   					 $(this).attr("disabled","disabled");
   				 });
			 }else{
				 $("#direct_launch_div").show();
				 $("#addToEguPricingForm").find("input").each(function(){
       				 $(this).removeAttr("disabled");
   				 });
				 $("#addToEguPricingForm").find("select").each(function(){
       				 $(this).removeAttr("disabled");
   				 });
			 }
		 });
			
		if(step1 == 1){
 			getReminderList("table_list_5","pager_list_5");
		}else if(step1 == 2||step1==3){
	 		getMortgageInfo($("#caseCode").val(),0);
		}else if(step1 == 4){
			getReportList("table_list_6","pager_list_6",0);
		}else if(step1 == 5){
			getCompleteMortInfo(0);
		}

			$("#isMainLoanBank").val("1");
			$("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(1);
			
			if(step == 1){
	 			getReminderList("table_list_2","pager_list_2");
			}else if(step == 2||step==3){
		 		getMortgageInfo($("#caseCode").val(),1);
			}else if(step == 4){
				getReportList("table_list_4","pager_list_4",1);
			}else if(step == 5){
				getCompleteMortInfo(1);
			}

	 	$(".myDataToggle").click(function(){
	 		$("#isMainLoanBank").val($(this).attr('data-m'));
	 		$("#addToEguPricingForm").find("input[name='isMainLoanBank']").val($(this).attr('data-m'));
	 		if($("#isMainLoanBank").val() == "0"){
	 			$("#first").removeClass("in");
	 		}else{
	 			$("#second").removeClass("in");
	 		}
		});
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
		
		 $(window).bind('resize', function () {
             var width = $('.jqGrid_wrapper').width();
             $('#table_list_1').setGridWidth(width);
             $('#table_list_2').setGridWidth(width);
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
	
	function startTmpBankWorkFlow(){
		//'我要修改'页面不触发流程 
		if(source != null && source !=''){
			return;
		}
		
		if(!$("#isTmpBank").is(':checked')){
			return;
		}

	 	$.ajax({
		    url:ctx+"/mortgage/tmpBankAudit/start",
		    async:false,
	    	method:"post",
	    	dataType:"json",
	    	data:{caseCode:$("#caseCode").val()},
	    	success:function(data){
	    		window.wxc.success(data.message);
	    	}
	 	});

	}
	function selectLoanerUser(array) {
		selectLoanerUserCom(array,$("#mortgageForm"));
	}
	
	function selectLoanerUser_(array) {
		selectLoanerUserCom(array,$("#mortgageForm1"));
	}
	
	function selectLoanerUserCom(array,$form){
		if (array && array.length > 0) {
			$form.find("#loanerName").val(array[0].username);
			$.ajax({
				url : ctx + "/eloan/LoanerCode",
				method : "post",
				dataType : "json",
				data : {
					"userId" : array[0].userId
				},
				success : function(data) {
					$form.find("#loanerNameImage").css("color","#52cdec");
					$form.find("#loanerPhone").val(data.user.mobile);
					$form.find("#loanerId").val(data.user.id);
					$form.find("#loanerOrgCode").val(data.user.orgName);
					$form.find("#loanerOrgId").val(data.user.orgId);
				}
			})
		} else {
			$form.find("#loanerName").val("");
			$form.find("#loanerOrgCode").val("");
			$form.find("#loanerOrgId").val("");
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
		    		partCode : "ComLoanProcess",
		    		preFileCode : "_letter_first",
		    		fileUploadContainer : "comLoanProcessfileUploadContainer"
		    	});
				
				fileUpload.init({
			    		caseCode : $('#caseCode').val(),
			    		partCode : "ComLoanProcess",
			    		preFileCode : "_letter_sec",
			    		fileUploadContainer : "comLoanProcess1fileUploadContainer"
			    	});
				
			    
		    });
	    });
	    
	    
	</script>
	</content>
</body>
</html>
