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

    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />" >
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

    <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />">

    <link rel="stylesheet" href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
    <!-- index_css  -->
    
    <link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

        <!-- stickUp fixed css -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/stickmenu.css' />">

    <!-- index_css  -->
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input2.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/uplodydome.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">

    <link rel="stylesheet" href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />" type="text/css" />
    <link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
    
    <!-- 上传相关 -->
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet">
	<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
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
                        	<input type="hidden" id="caseCode" value="${eloanCase.caseCode }"/>
                            <input type="hidden" id="riskControlId" name="riskControlId" value="${toRcMortgageCardVO.toRcMortgageCard.rcId }">
                            <div class="modal_title title-mark">
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
                                            <input disabled="disabled" type="radio" value="1" id="isModifyPhone1" name="isModifyPhone" <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='1'}">checked="checked"</c:if>>
                                            已修改
                                        </label>
                                        <label>
                                            <input disabled="disabled" type="radio" value="0" id="isModifyPhone0" name="isModifyPhone" <c:if test="${toRcMortgageCardVO.toRcMortgageCard.isModifyPhone =='0' || toRcMortgageCardVO.toRcMortgageCard.isModifyPhone == null}">checked="checked"</c:if>>
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
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small"style="float:left">
                                                       物品信息备注
                                    </label>
                                    <textarea disabled="disabled" class=" select_control" id="riskComment" name="riskComment" >${toRcMortgageCardVO.rcRiskControl.riskComment }</textarea>
                                </div>
                            </div>
                            
                            <div class="modal_title title-mark">
                                物品信息登记
                            </div>
                          <!--   <div class="line">
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
                            <c:forEach items="${toRcMortgageCardVO.toRcMortgageInfoList}" var="item" varStatus="status">
                            <div class="line" id="addTr">
                           		<input type="hidden" id="pkid" name="pkid" value="${item.pkid}">
                           		<input type="hidden" id="mortgageCode" name="mortgageCode" value="${item.mortgageCode}">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品类别
                                    </label>
                                    <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one mark"
							              display="select"  dictType="MORTGAGE_TYPE" tag="card"
							                ligerui='none' defaultvalue="${item.mortgageCategory}" onchange="change(this)"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品名称
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="${item.mortgageName}">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>保管人
                                    </label>
	                                <input disabled="disabled" id="itemManager" name="itemManager" class="teamcode input_type" value="${item.itemManagerName}" hVal="${item.itemManager}" onclick="chooseItemManager(this)" readonly="readonly"/>
	                                <div class="input-group float_icon organize_icon managerOnclick">
									 	<i class="icon iconfont">&#xe627;</i>
									</div>
                                </div>
                                <!-- <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a> -->
                                <c:if test="${status.first==false}"><!-- <a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a> --></c:if>
                                <c:if test="${item.mortgageCategory=='carded'}">
                                <div class="entry">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            姓名
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="${item.referName}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            身份证号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode" value="${item.referCode}">
                                    </div>
                                </div>
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            户名
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            银行卡号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode" value="">
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${item.mortgageCategory=='bankCard'}">
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            姓名
                                        </label> 
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            身份证号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode" value="">
                                    </div>
                                </div>
                                <div class="entry">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            户名
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="${item.referName}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            银行卡号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode" value="${item.referCode}">
                                    </div>
                                </div>
                                </c:if>
                            </div> 
                            </c:forEach>
                            <c:if test="${empty toRcMortgageCardVO.toRcMortgageInfoList}">
                            <div class="line" id="addTr">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品类别
                                    </label>
                                    <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one mark"
							              display="select"  dictType="MORTGAGE_TYPE" tag="card"
							                ligerui='none' defaultvalue="" onchange="change(this)"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品名称
                                    </label>
                                    <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>保管人
                                    </label>
	                                <input disabled="disabled" id="itemManager" name="itemManager" class="teamcode input_type" value="" hVal="" onclick="chooseItemManager(this)" readonly="readonly"/>
	                                <div class="input-group float_icon organize_icon managerOnclick">
									 	<i class="icon iconfont">&#xe627;</i>
									</div>
                                </div>
                                <!-- <a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a> -->
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            姓名
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            身份证号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode">
                                    </div>
                                </div>
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            户名
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            银行卡号
                                        </label>
                                        <input disabled="disabled" type="text" placeholder="" class="select_control teamcode" id="referCode" name="referCode">
                                    </div>
                                </div>
                            </div> 
                            </c:if>
                            </div>
         </form>                    
         <div class="ibox-title" style="height: auto;">
			<c:choose>
				<c:when test="${accesoryList!=null}">
					<h5>上传备件</h5></br>
					<div class="table-box" id="fileUploadContainer"></div>
				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
		<!-- </div>
                        <div class="status_btn text-center mt15">
                            <button class="btn btn-success btn-space submit_btn">保存</button>
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
                            <p class="text-center" style="font-size: 20px;">选择保存按钮可保存本次填写信息！</p>
                          <p class="text-center" style="font-size: 20px;">请确认填写的信息是否保存，确认后将不保存信息！</p>
                        </div>
                        <div class="modal-footer" style="text-align:center;">
                            <button type="button" class="btn btn-success submit_btn" style="margin-left:0px;">确认保存</button>
                            <button type="button" class="btn btn-success">我要删除</button>
                            <button type="button" class="btn  btn-success close_btn" data-win="modal">确定</button>
                             <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div> -->






            <!-- main End -->
        </div>
    </div>
            
	<content tag="local_script"> 
	   <script src="<c:url value='/js/inspinia.js' />"></script> 
	   <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script> 
	   <script src="<c:url value='/static/trans/js/plugins/poshytip/jquery.poshytip.min.js' />"></script>
	   <!-- 开关按钮js -->
       <script src="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js' />"></script>
       <script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
       <script src="<c:url value='/static/trans/js/eloan/eloan_guarant.js' />"></script>
         <script src="<c:url value='/js/jquery.editable-select.min.js' />"></script>
             <script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
             <script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
              	<!-- 上传附件相关 --> <script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> <!-- 上传附件 结束 -->
 	<!-- 选择组织控件 --> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <!-- 附件保存修改相关 --> <script src="<c:url value='/js/trunk/task/attachment2.js' />"></script>
       <script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
       <script>
       if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
		} else {
			var idList = [];
		}
   	    var taskitem = "RiskControl_Card";
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
				 var isModifyPhone=$("input[name='isModifyPhone']:checked").val();
				  var phoneNumber = $("#phoneNumber").val();
				 if(cardPerson == ""){
					 window.wxc.alert("请填写押卡对象！");
					 return false;
				 }
				 if(cardTime == ""){
					 window.wxc.alert("请填写押卡时间！");
					 return false;
				 }
				 if(isModifyPhone==1&&phoneNumber== ""){
					 window.wxc.alert("请填写修改到账后手机号！");
					 return false;
				 }
				 if(phoneNumber!=null & phoneNumber!=''){
					 checkGuestPhone = checkContactNumber(phoneNumber);
					 if (!checkGuestPhone) {
							return false;
					 }
				 }
			
             	var toRcMortgageInfoList = new Array();
             	
             	var validataCard = false;
             	var validataItemManager = false;
             	$("#mortgageList .line").each(function(i){
             		if($(this).is(":hidden")) {
             			var isWillDeleted = 'Y';
             		} else {
             			var isWillDeleted = 'N';
             		}
             		var pkid = $(this).children("input[name='pkid']").val()==undefined?null:$(this).children("input[name='pkid']").val();
             		var mortgageCategory = $(this).find("#mortgageCategory").val();
             		var mortgageCode = $(this).find("#mortgageCode").val()==undefined?null:$(this).find("#mortgageCode").val();
             		var mortgageName = $(this).find("#mortgageName").val();
             		var itemManager = $(this).find("#itemManager").attr('hVal');
             		var referName = $(this).find(".entry:visible").find("#referName").val();
             		var referCode = $(this).find(".entry:visible").find("#referCode").val();
             		// 校验身份证
	 				var reg1 = /^\d{15}$|^\d{17}([0-9]|X|x)$/;
             		if(isWillDeleted == 'N' && 'carded' == mortgageCategory && !reg1.test(referCode)) {
             			 validataCard = true;
             		}
             		// 校验保管人必选项
             		if(itemManager=='') {
             			validataItemManager = true;
             		}
             		
             		var toRcMortgageInfo = {
             			pkid : pkid,
             			mortgageCategory : mortgageCategory,
             			mortgageCode : mortgageCode,
             			mortgageName : mortgageName,
             			itemManager : itemManager,
             			referName : referName,
             			referCode : referCode,
             			isWillDeleted : isWillDeleted
             		}
             		toRcMortgageInfoList.push(toRcMortgageInfo);
             	})
             	
             	if(validataCard) {
             		window.wxc.alert("请填写正确的身份证号码！");
					return false;
             	}
            	if(validataItemManager) {
            		window.wxc.alert("请选择保管人！");
					return false;
             	}
             	
             	var toRcMortgageCard = {
             		cardPerson : $('#cardPerson').val(),
             		cardTime : $('#cardTime').val(),
             		isModifyPhone : $('input[name="isModifyPhone"]:checked').val(),
             		phoneNumber :$('#phoneNumber').val()
             	}
            	var rcRiskControl = {
             	    pkid : $('#riskControlId').val(),
             		eloanCode : eloanCode,
             		caseCode : caseCode,
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
     					window.wxc.success(data.message);
     					// 保存附件相关信息
     		     		deleteAndModify();
     					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
     				},
     				error : function(errors) {
     					window.wxc.error("数据保存出错");
     				}
     			}); 
             })
             
             $('select.select_control').attr('disabled', 'disabled');
             
             $(".close_btn").click(function(){
 					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
             })
 
            /*  $("#mortgageList").live("click",".managerOnclick",function(){
            	 chooseItemManager(this);
             }) */
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
				window.wxc.alert("电话号码只能由数字组成！");
				isValid = false;
				return isValid;
			}
			if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13)){				
				window.wxc.alert("电话号码只能由是8位、11位或者13位的数字组成！");
				isValid = false;
				return isValid;
			}
			
			if(isUniqueChar(mobile)){
				window.wxc.alert("电话号码不能为全部相同的数字！");
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
		
		//渲染图片 
		function renderImg(){		
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer({zIndex:15001});
		}

		//选择组织之后 级联选择主办人信息
		var o;
		function chooseItemManager(obj) {
			o = obj;
			userSelect({
				startOrgId : 'ff8080814f459a78014f45a73d820006',
				expandNodeId : '',
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
				//jobCode : 'consultant',
				callBack : selectUserBack
			});
		}
		//选取人员的回调函数
		function selectUserBack(array) {
			if (array && array.length > 0) {
				$(o).attr('value',array[0].username);
				$(o).attr('hVal', array[0].userId);
			} else {
				$(o).val("");
				$(o).attr('hVal', "");
			}
		}
       </script>
	</content>
	
	<content tag="local_require">
       <script>
		    require(['main'], function() {
				requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','valid','ligerui','bootstrapModal','modalmanager'],function($,aistFileUpload){
				    aistFileUpload.init({
			    		caseCode : $('.form_list #caseCode').val(),
			    		partCode : "RiskControl_Card",
			    		fileUploadContainer : "fileUploadContainer",
			    		readonly : true
			    	}); 
			    });
		    });
		</script>
    </content>
</body>

</html>