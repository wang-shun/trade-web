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
                                        押卡对象
                                    </label>
                                    <div id="cardPersonParent" style="display:inline-block;"> 
	                                    <select name="cardPerson" id="cardPerson" class="select_control sign_right_one">
	                                     <!--<option value="">房东A</option>
	                                         <option value="">房东B</option> -->
	                                         	<c:if test="${toRcMortgageCardVO.toRcMortgageCard.cardPerson !=null}">
													<option value="${toRcMortgageCardVO.toRcMortgageCard.cardPerson }" selected="selected">${toRcMortgageCardVO.toRcMortgageCard.cardPerson }</option>
												</c:if>
	                                    </select>
                                    </div>
                                </div>
                                <div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_small">
                                        押卡时间
                                    </label>
                                    <input class="input_type sign_right_two" value="<fmt:formatDate value="${toRcMortgageCardVO.toRcMortgageCard.cardTime}" pattern="yyyy-MM-dd" />" name="cardTime" id="cardTime" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        银行卡密码
                                    </label>
                                    <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='0' || toRcMortgageCardVO.toRcMortgageCard.isModifyPhone == null}">
										<input name="isModifyPhone" type="checkbox" checked="" data-size="small"  id="isModifyPhone">	
									</c:if>
                                    <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='1'}">
										<input name="isModifyPhone" type="checkbox" data-size="small"  id="isModifyPhone">	
									</c:if>
                                </div>
                                <div class="form_content">
                                    <label class="control-label mr10" style="margin-left: 51px;">
                                            修改后到账手机号
                                    </label>
                                    <input class="sign_right_one input_type" value="${toRcMortgageCardVO.toRcMortgageCard.phoneNumber }" placeholder="请输入" id="phoneNumber" name="phoneNumber">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                                       备注
                                    </label>
                                    <input class="sign_right_one input_type" value="${toRcMortgageCardVO.rcRiskControl.riskComment }" id="riskComment" name="riskComment">
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
                            </div>
                        </form>
                        <div class="status_btn text-center mt15">
                            <button class="btn btn-success btn-space submit_btn">提交</button>
                            <button class="btn btn-default" data-dismiss="modal" data-toggle="modal" data-target="#myModal">关闭</button>
                        </div>
                        <button type="button" class="close close_blue" style="display:none;" data-dismiss="modal">
                            <i class="iconfont icon_rong">&#xe60a;
                            </i>
                        </button>
                    </div>

                </div>
            </div>
            <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-body" style="background:#fff;">
                            <p class="text-center" style="font-size: 20px;">点击删除，将丢失本次填写信息！选择保存按钮可保存本次填写信息！</p>
                        </div>
                        <div class="modal-footer" style="text-align:center;">
                            <button type="button" class="btn btn-save submit_btn">确认保存</button>
                            <!-- <button type="button" class="btn btn-success">我要删除</button> -->
                            <button type="button" class="btn close_btn" data-dismiss="modal">取消</button>
                        </div>
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
        	$('.input-daterange').datepicker({
            	format : 'yyyy-mm-dd',
        		weekStart : 1,
        		autoclose : true,
        		todayBtn : 'linked',
        		language : 'zh-CN'
            });
        	$('#cardPerson').editableSelect({
        		effects: 'slide',
        		filter: false
        	});
    	    //初始化押卡对象
    	    var caseCode = "${eloanCase.caseCode }";
        	getCustomerName(caseCode);
        	
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
             	
             	var isModifyPhone = '0';
             	if($("#isModifyPhone").val() == 'on') {
             		isModifyPhone = '0';
             	} else {
             		isModifyPhone = '1';
             	}
             	
             	var toRcMortgageCard = {
             		cardPerson : $('#cardPerson').val(),
             		cardTime : $('#cardTime').val(),
             		isModifyPhone : isModifyPhone,
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
		function getCustomerName(case_code){
			$.ajax({
				url:ctx+"/eloan/getCaseDetails",
				method:"post",
				dataType:"json",
				data:{"caseCode":case_code},
				success:function(data){
					var txt='';
					$.each(data, function(i, item){
						if(i==0) {
							txt +=  "<option value='"+ item.guestName+"' selected>" + item.guestName+"</option>";
						} else {
							txt +=  "<option value='"+ item.guestName+"'>" + item.guestName+"</option>";
						} 
					});
					$('#cardPerson').empty().append(txt);	
				}
			});				
		}
       </script>
	</content>
</body>

</html>