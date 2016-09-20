<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>抵押</title>

    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <link rel="stylesheet" href="${ctx}/static/css/animate.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">

    <link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
    <link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
    <!-- index_css  -->

        <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">

    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
            <!-- main Start -->
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
                    </div>

                    <div class="ibox-content ibox-space">
                        <form method="get" class="form_list" id="mortgageForm">
                         	<input type="hidden" id="riskControlId" name="riskControlId" value="${toRcMortgageVO.toRcMortgage.rcId }">
                            <div class="modal_title">
                                押卡信息登记
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押合同编号
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" name="mortgageContractCode" id="mortgageContractCode" value="${toRcMortgageVO.toRcMortgage.mortgageContractCode }">
                                </div>
                                <div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_small">
                                        抵押登记时间
                                    </label>
                                    <input disabled="disabled" class="input_type sign_right_two" value="<fmt:formatDate value="${toRcMortgageVO.toRcMortgage.mortgageTime}" pattern="yyyy-MM-dd" />" name="mortgageTime" id="mortgageTime" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物业地址
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgagePropertyAddress" name="mortgagePropertyAddress" value="${toRcMortgageVO.toRcMortgage.mortgagePropertyAddress }">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产权人姓名
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="propertyName" name="propertyName" value="${toRcMortgageVO.toRcMortgage.propertyName }">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证编号
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="propertyCode" name="propertyCode" value="${toRcMortgageVO.toRcMortgage.propertyCode }">
                                    <span class="date_icon">

                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        他证编号
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="otherCode" name="otherCode" value="${toRcMortgageVO.toRcMortgage.otherCode }">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                                       备注
                                    </label>
                                    <input disabled="disabled" class="sign_right_one input_type" value="${toRcMortgageVO.rcRiskControl.riskComment }" id="riskComment" name="riskComment">
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
                             <div id="mortgageList">
                            <c:forEach items="${toRcMortgageVO.toRcMortgageInfoList}" var="item">
                            <div class="line" id="addTr">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品类别
                                    </label>
			                  <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
							display="select"  dictType="MORTGAGE_TYPE" tag="mortgage"
							ligerui='none' defaultvalue="${item.mortgageCategory}"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品名称
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="${item.mortgageName}">
                                </div>
                            </div>
                             </c:forEach>
                            <c:if test="${empty toRcMortgageVO.toRcMortgageInfoList}">
								<div class="line" id="addTr">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品类别
                                    </label>
			                  <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
							display="select"  dictType="MORTGAGE_TYPE" tag="mortgage"
							ligerui='none' defaultvalue=""></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        抵押物品名称
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="">
                                </div>
                            </div>					
							</c:if>
                             <div class="line" id="hideAddTr" style="display:none;">
	                                <div class="form_content"> 
	                                    <label class="control-label sign_left_small">
	                                        抵押物品类别
	                                    </label>
	                                          <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"
							display="select"  dictType="MORTGAGE_TYPE" tag="mortgage"
							ligerui='none' defaultvalue=""></aist:dict>
	                                </div>
	                                <div class="form_content">
	                                    <label class="control-label sign_left_small">
	                                        抵押物品名称
	                                    </label>
	                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName">
	                                </div>
	                            </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- main End -->
    </div>
<content tag="local_script"> 
    <!-- Mainly scripts -->
<!-- <script src="../static/js/jquery-2.1.1.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <script src="../static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="../static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> -->

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${ctx}/static/trans/js/plugins/poshytip/jquery.poshytip.js"></script>
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <!-- 开关按钮js -->
    <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
    <script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script>
    <script>
	    $(document).ready(function () {
	    	$('.input-daterange').datepicker({
	        	format : 'yyyy-mm-dd',
	    		weekStart : 1,
	    		autoclose : true,
	    		todayBtn : 'linked',
	    		language : 'zh-CN'
	        });
	    	
	    	var eloanCode =  "${eloanCase.eloanCode }";
        	var pkid = "${pkid}";
        	
        	
        	 $('.submit_btn').click(function(){
             	var toRcMortgageInfoList = new Array();
             	$("#mortgageList .line").each(function(i){
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
             	})
             	
             	var toRcMortgage = {
             		mortgageContractCode : $('#mortgageContractCode').val(),
             		mortgageTime : $('#mortgageTime').val(),
             		mortgagePropertyAddress : $('#mortgagePropertyAddress').val(),
             		propertyName : $('#propertyName').val(),
             		propertyCode : $('#propertyCode').val(),
             		otherCode :$('#otherCode').val()
             	}
            	var rcRiskControl = {
             		pkid : $('#riskControlId').val(),
             		eloanCode : eloanCode,
             		riskComment : $('#riskComment').val(),
             		riskType : 'mortgage'
                }
             	var toRcMortgageVO = {
             		toRcMortgageInfoList : toRcMortgageInfoList,
             		toRcMortgage : toRcMortgage,
             		rcRiskControl : rcRiskControl
             	}
             	
             	var url = "${ctx}/riskControl/saveRcMortgage";
     			$.ajax({
     				cache : true,
     				async : false,//false同步，true异步
     				type : "POST",
     				url : url,
     				dataType : "json",
     				contentType:"application/json",  
     				data : JSON.stringify(toRcMortgageVO),
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
	    })
	    
	    var divIndex = 1;
		function getAtr(i){
		    var addTr=$('#hideAddTr').clone();
		    addTr.show();
		    $("#mortgageList").append(addTr);
		    divIndex++;
		}
		
		function getDel(k){
		    $(k).parents('.line').remove();
		    divIndex--;
		}
    </script>
</content>
</body>
</html>