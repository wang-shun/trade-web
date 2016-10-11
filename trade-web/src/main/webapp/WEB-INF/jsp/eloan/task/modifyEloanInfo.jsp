<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>E+贷款详情</title>

<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_guaranty.css">

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- aist列表样式 -->
<%-- <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet"> --%>
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
                            <h5>关联案件</h5>
                            <div class="case_content">
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>案件编号</em><span class="span_one">${eloanCase.caseCode }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>产权地址</em><span class="span_two">${info.propertyAddr}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>交易顾问</em><span class="span_one">${info.jingbanName }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>上家姓名</em><span class="span_two">${info.sellerName }</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>经纪人</em><span class="span_one">${info.agentName }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>下家姓名</em><span class="span_two">${info.buyerName }</span></p>
                                   </div>
                                </div>
                            </div>
                        </div>
                        <div class="main_titile" style="position: relative;">
                            <h5>E+产品</h5>
                            <div class="case_content">
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>产品类型</em><span class="span_one">
                                       <aist:dict id="loanSrvCode" name="loanSrvCode" clazz="select_control sign_right_two"
										display="onlyLabel"  dictType="yu_serv_cat_code_tree" tag="Eloan" dictCode="${eloanCase.loanSrvCode}"
										ligerui='none'></aist:dict></span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>合作机构</em><span class="span_one">${info.finOrgName}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>客户姓名</em><span class="span_one">${eloanCase.custName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>客户电话</em><span class="span_one">${eloanCase.custPhone}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>申请金额</em><span class="span_one">${eloanCase.applyAmount}万</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>申请时间</em><span class="span_one"><fmt:formatDate value="${eloanCase.applyTime}" pattern="yyyy-MM-dd" /></span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>申请期数</em><span class="span_one">${eloanCase.month}月</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>转介人姓名</em><span class="span_one">${eloanCase.zjName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>转介人员工编号</em><span class="span_one">${eloanCase.zjCode}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>产品部姓名</em><span class="span_one">${eloanCase.pdName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>产品部员工编号</em><span class="span_one">${eloanCase.pdCode}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>分成比例贷款</em><span class="span_one">${eloanCase.pdPart}%</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>合作人姓名</em><span class="span_one">${eloanCase.coName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>合作人员工编号</em><span class="span_one">${eloanCase.coCode}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>分成比例贷款</em><span class="span_one">${eloanCase.coPart}%</span></p>
                                   </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ibox-content" id="loan_info">
                        <div class="main_titile">
                            <h5>可修改内容</h5>
                        </div>
                        <form method="get" class="form_list" id="eloanInfoForUpdate">                        
                        <input type="hidden" name="caseCode" id="caseCode" value="${eloanCase.caseCode}"/>
					    <input type="hidden" id="eloanCode" name="eloanCode" value="${eloanCase.eloanCode}">
                        <ul class="form_lump loan_ul">
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">客户姓名 </label>
                                    <input class="input_type sign_right_two" name="custName" id="custName" value="${eloanCase.custName}" >
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">客户电话</label>
                                    <input class="input_type sign_right_two"  name="custPhone" id="custPhone" value="${eloanCase.custPhone}" >
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">申请金额</label>
                                    <input class="input_type sign_right_two" name="applyAmount" id="applyAmount"  value="${eloanCase.applyAmount}" >
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">申请期数</label>
                                    <input class="input_type sign_right_two" name="month" id="month" value="${eloanCase.month}">
                                    <div class="input-group date_icon">
                                        <span class="danwei">月</span>
                                    </div>
                                </div>
                                <div class="form_content"  id="signAmountForShow">
                                    <label class="control-label sign_left_two">面签金额</label>
                                    <input class="input_type sign_right_two" name="signAmount" id="signAmount" value="${eloanCase.signAmount}" >
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                            </li> 
                          	</ul>
                            <ul class=" form_lump releaseInfoForShow"> 
                            <c:forEach items="${eloanRelList}" var="item">
 
                            <li id="releaseInfoForShow">    
                            	<input type="hidden" value="${item.pkid}"  name="pkid" id="pkid">  
                            	<input type="hidden" value="${item.confirmStatus}"  name="confirmStatus" id="confirmStatus">         		
                           		<div class="form_content">
                                    <label class="control-label sign_left_two">放款金额</label>
                                    <input class="input_type sign_right_two" value="${item.releaseAmount}"  name="releaseAmount" id="releaseAmount">
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                                <div class="form_content form_nomargin input-daterange">
                                    <label class="control-label sign_left_two">放款时间</label>
                                    <input class="input_type sign_right_two"  value="<fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd" />"  name="releaseTime" id="releaseTime"/>
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>                                 
                            </li>
                            </c:forEach>
                        </ul>
                        <div class="line">
                                    <div class="add_btn" style="float:left;margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="updateEloanCaseInfo">提交</button>
                                        <button type="reset" class="btn btn-grey" data-dismiss="modal" id="backEloanList">关闭</button>
                                    </div>
                                </div>

                        </form>
                    </div>

                </div>
            </div>
            <!-- main End -->
    </div>
	<!-- main End -->
	<content tag="local_script"> 
	   <script src="${ctx}/js/inspinia.js"></script> 
	   <script src="${ctx}/js/plugins/pace/pace.min.js"></script> 
	   <!-- 开关按钮js -->
       <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
       <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
       <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
       <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>   
           <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>  
	   <script>
		   jQuery(document).ready(function() {
			   var signAmount = $("#signAmount").val();
			   if(signAmount =="" || signAmount == null){
				   $("#signAmountForShow").hide();
				   $("#signAmountForShow").attr("class","abc");
			   }			   
			   var releaseAmount = $("#releaseAmount").val();
			   var releaseTime = $("#releaseTime").val();
			   if(releaseAmount == "" || releaseAmount == null){
				   $("#releaseInfoForShow").hide();
			   }
			   $("input[name='releaseTime']").attr("disabled", true);
			   
	        	$('.input-daterange').datepicker({
	              	format : 'yyyy-mm-dd',
	          		weekStart : 1,
	          		autoclose : true,
	          		todayBtn : 'linked',
	          		language : 'zh-CN'
	            });
		   }) 
		   
           $("#updateEloanCaseInfo").click(function(){          	  
        	    if(!eloanInfoValidate()){
        	    	return;
        	    }
           		saveEloanInfoForUpdate();
           })
           
           $("#backEloanList").click(function(){
           	 window.location.href = "${ctx}/eloan/Eloanlist";
           })
 
 			function saveEloanInfoForUpdate(){
			   
	           	var eloanRelList = [];
	        	var eloanCode =  $('#eloanCode').val();	        	
	            var custName = $('#custName').val();
	        	var custPhone = $('#custPhone').val();            	
	        	var applyAmount = $('#applyAmount').val();
	        	var month = $('#month').val();
	        	var signAmount = $('#signAmount').val();
	        	
			    var toEloanCase = {};
	           	toEloanCase.eloanCode = eloanCode;
	           	toEloanCase.custName = custName;
	           	toEloanCase.custPhone = custPhone;
	           	toEloanCase.applyAmount = applyAmount;
	           	toEloanCase.month = month;
	           	toEloanCase.signAmount = signAmount;
	           	
	           	var sumAmount = 0;

             	$(".releaseInfoForShow li").each(function(){ 
            		var releaseAmount = $(this).find("#releaseAmount").val();
            		sumAmount+=Number(releaseAmount);
            		var releaseTime = $(this).find("#releaseTime").val();
            		var pkid = $(this).find("#pkid").val();
            		var confirmStatus = $(this).find("#confirmStatus").val();
            		
            		var eloanRel = {
             			releaseAmount : releaseAmount,
            			releaseTime : releaseTime,
            			eloanCode : eloanCode, 
            			pkid : pkid,
            			confirmStatus : confirmStatus
            		}             		
            		eloanRelList.push(eloanRel);
            	}) 
			   if(sumAmount > signAmount){
				   alert("放款总金额不能大于面签金额");
				   return;
			   }
            	
				var eloanRelListVO = {
             		eloanRelList : eloanRelList,
             		toEloanCase : toEloanCase 
            	} 
				
				var jsonData = JSON.stringify(eloanRelListVO);
				var url = "${ctx}/eloan/modifyEloanCaseInfo";
				console.log("===Result==="+JSON.stringify(eloanRelListVO));	
				
				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					data : jsonData,//{"eloanRelList":JSON.stringify(eloanRelList),"toEloanCase":JSON.stringify(toEloanCase)},  
    				dataType : "json",
    				contentType : 'application/json;charset=utf-8',
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
					success : function(data) {	
						if(data.success == true){
							 alert("数据修改成功");
							 window.location.reload(); 
						} 
					},
					error : function(errors) {
						$.unblockUI();    
						alert("数据保存出错");
					}
				});
			}
		   
		   
		   function rescCallbocak(){			   
			   window.location.href = "${ctx}/eloan/Eloanlist";
			}
		   
		   function eloanInfoValidate(){			   
			    var flag = true;	        	        	
	            var custName = $('#custName').val();
	        	var custPhone = $('#custPhone').val();            	
	        	var applyAmount = $('#applyAmount').val();
	        	var month = $('#month').val();
	        	var signAmount = $('#signAmount').val();				
	        	var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
	        	var mobile = $.trim(custPhone);	
	        	
				if (custName == null || custName == '') {
					flag = false;					
					alert("请填写客户姓名");					
				}	
				if (custPhone == null || custPhone == '') {
					flag = false;					
					alert("请填写客户电话");					
				}		
				
				if(!isMobile.exec(mobile)){					
					alert("手机号码格式不正确");
					flag = false;					
				}
				
				if (applyAmount == null || applyAmount == '') {					
					flag = false;
					alert("请填写申请金额");
				}			
				if (month == null || month == '') {
					flag = false;
					alert("请填写申请期数");					
				}
				if($('#signAmountForShow').attr("class")!= "abc"){
					if (signAmount == null || signAmount == '') {
						flag = false;
						alert("请填写面签金额");					
					}
				}				
				return flag;
			}
	   </script> 
	</content>
</body>
</html>



