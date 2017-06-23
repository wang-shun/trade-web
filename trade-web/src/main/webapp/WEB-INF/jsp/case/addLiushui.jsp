<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增实收</title>
	<link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />">
	<link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
	<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value='/static/css/style.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
	<!-- stickUp fixed css -->
	<!-- index_css  -->
	<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />">
	<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
	<link rel="stylesheet" href="<c:url value='/static_res/trans/css/common/report.css' />">
	<link rel="stylesheet" href="<c:url value='/static/trans/css/addOutlist.css' />">
	<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
	
	<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
	<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
</head>

<body>
<input type="hidden" id="caseCode_" value="${caseCode_}" />
<input type="hidden" id="allAmount" value="${allAmount}" />
  <div class="wrapper wrapper-content animated fadeInUp">
      <div class="ibox-content" id="reportFive" style="  margin-top: 15px; ">
      <div class="panel-body">
						<div class="ibox-content-head lh24">
							<h5>案件基本信息</h5>
							
							<small class="pull-right">誉萃编号：${toCase.caseCode}｜中原编号：${toCase.ctmCode}</small>
						</div>
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
											<dt>房屋类型</dt>
											<dd>
												<aist:dict id="propertyType" name="propertyType"
													display="label" dictType="30014"
													dictCode="${toPropertyInfo.propertyType}" />
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_two col-sm-5">
									<span>买卖双方</span>
									<div class="ibox-conn else_conn">
										<dl class="dl-horizontal col-sm-6">
											<dt>上家姓名</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
								        <span>推荐人信息</span>
										<div class="ibox-conn else_conn_two ">
											<dl class="dl-horizontal">
												<dt>姓名</dt>
												<dd>
													<a data-toggle="popover" data-placement="right" data-content="${toCaseInfo.recommendPhone}"> ${toCaseInfo.recommendUsername}</a>
												</dd>
												<dt>手机号</dt>
												<dd>${toCaseInfo.recommendPhone }</dd>
											</dl>
										</div>
								</div>
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.cpMobile}">
													${caseDetailVO.cpName} </a>
											</dd>
											<c:if test="${empty caseDetailVO.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseDetailVO.proList}">
												<c:forEach items="${caseDetailVO.proList}" var="pro">
													<dt>合作顾问</dt>
													<dd>
														<a data-toggle="popover" data-placement="right"
															data-content="${pro.processorMobile}">
															${pro.processorName} </a>
													</dd>
												</c:forEach>
											</c:if>
											<dt>助理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.asMobile}">
													${caseDetailVO.asName} </a>
											</dd>
										</dl>
									</div>
								</div>

							</div>
						</div>
					</div>
      </div>
      
      <div class="ibox-content" id="reportFive" style=" margin-top: 15px; ">
     	 <form  id="saveCaseInfo">
     	  <input type="hidden" name="distCode" id="distCode" value="${caseCode_}">
     	  <input type="hidden" id="caseCode" name="caseCode" value="${caseCode}" />
         
          <div style=" font-size: 14px; font-weight: bold; margin: 15px 0 0 33px; "> 新增实收流水 </div>
        
          <div  class="form_list mt20" id="addVoucher">
              <div class="line">
                  <div class="form_content">
                      <label class="control-label sign_left_small">付款金额</label> 
                      <input class="select_control sign_right_one" placeholder="" name="paymentAmount" id="paymentAmount" value="">
                     <span class="date_icon">元</span>
                  </div>
                  <div class="form_content">
                      <label class="control-label sign_left_small">
                          	付款人
                      </label>
                      <input type="text" class="select_control sign_right_one" id="payer" name="payer" value="">
                  </div>
                  <div class="form_content">
                      <label class="control-label sign_left_small">
                          	付款时间
                      </label>
                      <input id="paymentDate" name="paymentDate" class="select_control sign_right_one" type="text"  placeholder="付款时间">
                  </div>
              </div>
              <div class="line">
                <div>
                     <div class="ibox-title" style="height: auto;border:0;padding-left:0;" id="aboutInfo">
						<c:choose>
							<c:when test="${accesoryList!=null}">
								<label class="control-label sign_left_small">上传附件</label>
								<div class="table-box" id="addWdCaseContract" style="margin-left: 127px;margin-top: -27px;"></div>
							</c:when>
							<c:otherwise>
								<label class="control-label sign_left_small pull-left">上传附件</label>
								<p style=" float: left;  margin-left: 5px; font-weight: bold; ">无需上传备件</p>
							</c:otherwise>
						</c:choose>
					</div>
                </div>
            </div>
          </div>
          <div class="text-center mt40 mb30" style=" margin-top: 15px;  padding-bottom: 15px; ">
               <a onclick="sumbitRe()" class="btn btn-success">创建</a>
             <!--  <button type="reset" class="btn btn-grey" data-toggle="modal" >清空</button> -->
          </div>
          </form>
      </div>
      <div class="ibox-content" style="height:250px;background:#f3f3f4;border-top:none;">
      </div>
  </div>
 
<content tag="local_require">
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
<script>

$('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
$('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));
/*动态生成上下家*/
function generateSellerAndBuyer(name, phone){
		var nameArr = name.split('/');
		var phoneArr = phone.split('/');
		var str='';
		for (var i=0; i<nameArr.length; i++) {
			if(i%2==0){
				str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a>&nbsp;';
			}else{
				str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a><br/>';
			}
		}
		return str;
	}

/**
 * 日期控件
 */
$('#paymentDate').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

/**
* 添加附件
*/
var fileUpload;
 require(['main'], function() {
 	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
 		fileUpload = aistFileUpload;
 		
 		fileUpload.init({
  		caseCode : $('#caseCode_').val(),
  		partCode : "AddLiushui",
  		fileUploadContainer : "addWdCaseContract"
  	}); 
  });
 });
 
 /**
  * 页面提交
  */
 function sumbitRe(){	
	 
	if(!checkForm()){
		return false;		
	}
	
 	var data = [];
 	$("form").each(function(){
 		var obj = $(this).serializeArray();
 		for(var i in obj){
 			data.push(obj[i]);
 		}
 	}); 

 	var url = ctx+"/caseMerge/saveLiushui";
 	$.ajax({
 		cache : false,
 		async : false,
 		type : "post",
 		url : url,
 		dataType : "json",
 		data : data,
 		beforeSend:function(){  
          },
 		success : function(data){
 			if(data.success){
 				window.wxc.success("新增实收流水成功！",{"wxcOk":function(){
 					window.location.href=ctx+"/case/caseDetail?caseId="+'${toCase.pkid}';
 				}});
 			}else{
 				window.wxc.error("新增实收流水失败！"+data.message); 
 			}
 			
 		}
 	});
 	
 }
 
 function checkForm(){
		var formSubmitFlag = true;
		
		if ($('input[name=paymentAmount]').val() == '') {
			window.wxc.alert("付款金额不能为空!");
			$('input[name=paymentAmount]').focus();
			return false;
		}else{
			var allamo = Number($("#allAmount").val());
			if(Number($('input[name=paymentAmount]').val())>allamo){
				alert("收取金额不允许大于应收金额减去已收取金额之和！");
				return false;
			}
		}
			
		
		var payerAmountFlag = true;
		var payerAmountEle;
		$("input[name$='paymentAmount']").each(function(i,e){
			if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber($(e).val()))){
				payerAmountFlag = false;
				payerAmountEle = $(e);
				
				return false;
			}
		});
		if(!payerAmountFlag){
				window.wxc.alert("请填写有效的金额！");
			    changeClass(payerAmountEle);
				return false;
		}
		
		if ($('input[name=payer]').val() == '') {
			window.wxc.alert("付款人不能为空!");
			$('input[name=payer]').focus();
			return false;
		}
		
		var payerNameFlag = true;
		var payerNameEle;
		$("input[name='payer']").each(function(i,e){
			if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isName($(e).val()))){
				payerNameFlag = false;
				payerNameEle = $(e);
				 return false;
				 }
			});
		
		 if(!payerNameFlag){
			 window.wxc.alert("请填写有效的付款人姓名！");
		    changeClass(payerNameEle);
			return false;
		 }
		 
		 
		 var cashFlowCreateTimeFlag = true;
			var cashFlowCreateTimeEle;
			$("input[name='paymentDate']").each(function(i,e){
				if(($(e).val() == null || $(e).val() == '')){
					cashFlowCreateTimeFlag = false;
					cashFlowCreateTimeEle = $(e);
					return false;
				}
			});
			if(!cashFlowCreateTimeFlag){
				window.wxc.alert("请选择有效的付款时间！");
				changeClass(cashFlowCreateTimeEle);
				return false;
			}
		
		if ( ($("#addLiushui_contract_pic_list li").length == undefined || $("#addLiushui_contract_pic_list li").length == 0) ) {
			window.wxc.alert("请上传附件信息！");
			formSubmitFlag = false;
			return false;
		}
		return formSubmitFlag;
		
 }
 
 function changeClass(object){
		$(object).focus();
		$(object).addClass("borderClass").blur(function(){
			$(this).removeClass("borderClass");
		});	;
	}
 
 /**
  * 金额验证(两位小数)
  */
 function isNumber(num){
 	var reg=/^([1-9]{1}\d*)(\.\d{1,2})?$/;
 	if(!reg.test(num)){
 		return false;
 	}
 	return true;
 }
 
 /**
  * 姓名验证(汉字和英文大小写)
  */
 function isName(name){
 	name = name.replace(/\s/g,"");
 	reg = /((^[\u4E00-\u9FA5]{1,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
 	if (!reg.test(name)) {
        return false; 
    }
    return true;
 }
     
</script>
</content>
</body>
</html>