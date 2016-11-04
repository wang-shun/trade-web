<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>押卡</title>

    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <link rel="stylesheet" href="${ctx}/static/css/animate.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">

    <link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
    <link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
    <!-- index_css  -->
    
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

        <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">

    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
    <link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<div id="wrapper">
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
                    </div>

                    <div class="ibox-content ibox-space">
                        <form method="get" class="form_list">
                            <input type="hidden" id="riskControlId" name="riskControlId" value="${toRcMortgageCardVO.toRcMortgageCard.rcId }">
                            <div class="modal_title">
                                押卡信息登记
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>押卡对象
                                    </label>
                                    <div id="cardPersonParent" style="display:inline-block;"> 
	                                    <select disabled="disabled" name="cardPerson" id="cardPerson" class="select_control sign_right_one">
	                                     <!--<option value="">房东A</option>
	                                         <option value="">房东B</option> -->
	                                         	<c:if test="${toRcMortgageCardVO.toRcMortgageCard.cardPerson !=null}">
													<option value="${toRcMortgageCardVO.toRcMortgageCard.cardPerson }" selected="selected">${toRcMortgageCardVO.toRcMortgageCard.cardPerson }</option>
												</c:if>
	                                    </select>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>押卡时间
                                    </label>
                                    <span class="input-daterange">
                                    <input disabled="disabled" class="input_type sign_right_two" value="<fmt:formatDate value="${toRcMortgageCardVO.toRcMortgageCard.cardTime}" pattern="yyyy-MM-dd" />" name="cardTime" id="cardTime" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    </span>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content mt6">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>银行卡密码
                                    </label>
                                    
                                    <div class="checkbox i-checks radio-inline sign ">
                                        <label>
                                            <input disabled="disabled" type="radio" value="1" id="isModifyPhone" name="isModifyPhone" <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='1'}">checked="checked"</c:if>>
                                            已修改
                                        </label>
                                        <label>
                                            <input disabled="disabled" type="radio" value="0" id="isModifyPhone" name="isModifyPhone" <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='0' || toRcMortgageCardVO.toRcMortgageCard.isModifyPhone == null}">checked="checked"</c:if>>
                                            未修改
                                        </label>
                                    </div>
                                </div>
                                <div class="form_content">
                                     <label class="control-label sign_left width125">
                                        修改到账后手机号
                                    </label>
                                    <input disabled="disabled" class="select_control sign_right_one" value="${toRcMortgageCardVO.toRcMortgageCard.phoneNumber }" placeholder="请输入" id="phoneNumber" name="phoneNumber">
                                </div>
                            </div>
                        
                           <!--  <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        卡证管理员
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">小林</option>
                                        <option value="">小黄</option>
                                    </select>
                                </div>
                            </div> -->
                            
                            <%-- <div id="mortgageList">
                              <c:forEach items="${toRcMortgageCardVO.toRcMortgageInfoList}" var="item">
	                            <div class="line" id="addTr">
	                                <div class="form_content"> 
	                                    <label class="control-label sign_left_small">
	                                        抵押物品类别
	                                    </label>
	                                          <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
							display="select"  dictType="MORTGAGE_TYPE" tag="card"
							ligerui='none' defaultvalue="${item.mortgageCategory}"></aist:dict>
	                                </div>
	                                <div class="form_content">
	                                    <label class="control-label sign_left_small">
	                                        抵押物品名称
	                                    </label>
	                                    <input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="${item.mortgageName}">
	                                </div>
	                                <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a>
	                            </div>
	                            </c:forEach>
	                            <c:if test="${empty toRcMortgageCardVO.toRcMortgageInfoList}">
									<div class="line" id="addTr">
	                                <div class="form_content">
	                                    <label class="control-label sign_left_small">
	                                        抵押物品类别
	                                    </label>
				                  <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
								display="select"  dictType="MORTGAGE_TYPE" tag="card"
								ligerui='none' defaultvalue=""></aist:dict>
	                                </div>
	                                <div class="form_content">
	                                    <label class="control-label sign_left_small">
	                                        抵押物品名称
	                                    </label>
	                                    <input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="">
	                                </div>
	                                <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a>
	                            	</div>					
								</c:if>
	                            <div class="line" id="hideAddTr" style="display:none;">
	                                <div class="form_content"> 
	                                    <label class="control-label sign_left_small">
	                                        抵押物品类别
	                                    </label>
	                                          <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
							display="select"  dictType="MORTGAGE_TYPE" tag="card"
							ligerui='none' defaultvalue=""></aist:dict>
	                                </div>
	                                <div class="form_content">
	                                    <label class="control-label sign_left_small">
	                                        抵押物品名称
	                                    </label>
	                                    <input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName">
	                                </div>
	                                <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a>
	                                <a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>
	                            </div>
                            </div> --%>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                                       物品信息备注
                                    </label>
                                    <textarea disabled="disabled" class="sign_right_two" id="riskComment" name="riskComment" style="width:400px;height:100px;">${toRcMortgageCardVO.rcRiskControl.riskComment }</textarea>
                                </div>
                            </div> 
                        </form>
                    </div>

                </div>
            </div>

            <!-- main End -->
        </div>
    </div>
            
	<content tag="local_script"> 
	   <script src="${ctx}/js/inspinia.js"></script> 
	   <script src="${ctx}/js/plugins/pace/pace.min.js"></script> 
	   <script src="${ctx}/static/trans/js/plugins/poshytip/jquery.poshytip.min.js"></script>
	   <!-- 开关按钮js -->
       <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
       <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
       <script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script>
         <script src="${ctx}/js/jquery.editable-select.min.js"></script>
             <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
             <script src="${ctx}/js/jquery.blockui.min.js"></script>
       <script>
        $(document).ready(function () {
        	$('#cardPerson').editableSelect({
        		effects: 'slide',
        		filter: false
        	});
        	
        	/**日期组件*/
        	$('.input-daterange').datepicker({
            	format : 'yyyy-mm-dd',
        		weekStart : 1,
        		autoclose : true,
        		todayBtn : 'linked',
        		language : 'zh-CN'
            });
  
    	    //初始化押卡对象
    	    var caseCode = "${eloanCase.caseCode }";
    	    var person = "${toRcMortgageCardVO.toRcMortgageCard.cardPerson}";
    	    if(person == null || person=="") {
    	    	 getCustomerNameAndTel(caseCode);
    	    }
        	
        	var eloanCode =  "${eloanCase.eloanCode }";
        	var pkid = "${pkid}";
        	
        	
        	 $('.submit_btn').click(function(){
				 var cardPerson = $("#cardPerson").val();
				 var cardTime = $("#cardTime").val();
				 if(cardPerson == ""){
					 alert("请填写押卡对象！");
					 return false;
				 }
				 if(cardTime == ""){
					 alert("请填写押卡时间！");
					 return false;
				 }
				 var phoneNumber = $("#phoneNumber").val();
				 if(phoneNumber!=null & phoneNumber!=''){
					 checkGuestPhone = checkContactNumber(phoneNumber);
					 if (!checkGuestPhone) {
							return false;
					 }
				 }
			
             	var toRcMortgageInfoList = new Array();
             	/* $("#mortgageList .line").each(function(i){
             		if(this.style.display == 'none')
                    {
                        return true;
                    }
             		var mortgageCategory = $(this).find("#mortgageCategory").val();
             		var mortgageName = $(this).find("#mortgageName").val();
             		
             		var toRcMortgageInfo = {
             			mortgageCategory : mortgageCategory,
             			mortgageName : mortgageName
             		}
             		toRcMortgageInfoList.push(toRcMortgageInfo);
             	}) */
             	
             	var toRcMortgageCard = {
             		cardPerson : $('#cardPerson').val(),
             		cardTime : $('#cardTime').val(),
             		isModifyPhone : $('input[name="isModifyPhone"]:checked').val(),
             		phoneNumber :$('#phoneNumber').val()
             	}
            	var rcRiskControl = {
             	    pkid : $('#riskControlId').val(),
             		eloanCode : eloanCode,
             		riskComment : $('#riskComment').val(),
             		riskType : 'card'
                }
             	var toRcMortgageCardVO = {
             		toRcMortgageInfoList : toRcMortgageInfoList,
             		toRcMortgageCard : toRcMortgageCard,
             		rcRiskControl : rcRiskControl
             	}
             	//console.log(JSON.stringify(toRcMortgageCardVO));
             	
             	var url = "${ctx}/riskControl/saveRcMortgageCard";
     			$.ajax({
     				cache : true,
     				async : false,//false同步，true异步
     				type : "POST",
     				url : url,
     				dataType : "json",
     				contentType:"application/json",  
     				data : JSON.stringify(toRcMortgageCardVO),
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
     				},
     				success : function(data) {
     					alert(data.message);
     					window.close();
     					//window.opener.callback();
     					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
     				},
     				error : function(errors) {
     					alert("数据保存出错");
     				}
     			}); 
             })
             
             $(".close_btn").click(function(){
            		window.close();
 					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
             })
             
        });
        /*绑定时获取客户名和客户电话号码并生成下拉框*/
		function getCustomerNameAndTel(case_code){
			$.ajax({
				url:ctx+"/eloan/getCaseDetails",
				method:"post",
				dataType:"json",
				data:{"caseCode":case_code},
				success:function(data){
					//var cusPhone = $('#custPhone');
					var txt='<select class="select_control sign_right_one" id="cardPerson" name="cardPerson">';
								
					$.each(data, function(i, item){
						if(i==0) {
							txt +=  "<option value='"+ item.guestName+"' selected>" + item.guestName+"</option>";
							/*cusPhone.val(data[i].guestPhone);*/
						} else {
							txt +=  "<option value='"+ item.guestName+"'>" + item.guestName+"</option>";
						} 
					});
					$('#cardPersonParent').empty().append(txt+'</select>');	
								
					$('#cardPerson').editableSelect({
						effects: 'slide',
						filter: false
						/* ,
						onSelect: function (element) {
							var myIndex = $(element).index();
							if(myIndex>=0){
								cusPhone.val(data[myIndex].guestPhone);
							}
						} */
					});
				}
			});				
		}
		//验证手机和电话号码
		function checkContactNumber(ContactNumber) {
			
			var mobile = $.trim(ContactNumber);				
			var number=/^[0-9]*$/;	//数字			
			var isValid = true;
			
			if(!number.exec(mobile)){					
				alert("电话号码只能由数字组成！");
				isValid = false;
				return isValid;
			}
			if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13)){				
				alert("电话号码只能由是8位、11位或者13位的数字组成！");
				isValid = false;
				return isValid;
			}
			
			if(isUniqueChar(mobile)){
				alert("电话号码不能为全部相同的数字！");
				isValid = false;
				return isValid;
			}
			return isValid;
		}
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
       </script>
	</content>
</body>

</html>