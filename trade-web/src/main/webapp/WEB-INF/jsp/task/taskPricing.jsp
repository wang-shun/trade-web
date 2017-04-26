<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
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
</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<div class="row marginbot">
		<div class="row  border-bottom white-bg ">
			 <div class="row wrapper white-bg new-heading">
	             <div class="pl10">
	                <h2 class="newtitle-big">
	                       	核价
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

	        <div class="ibox-content clearfix space_box noborder">
	            <div class="">
	                <h2 class="newtitle title-mark">完成提醒</h2>
	                <div class="jqGrid_wrapper">
	                    <table id="reminder_list"></table>
						<div id="pager_list_1"></div>	
	                    <button type="button" class="btn btn-icon btn-grey-border mt20" id="sendSMS">
	                        <i class="iconfont icon">&#xe62a;</i> 发送短信提醒
	                    </button>
	                </div>
	            </div>
	        	<div>
	            	<h2 class="newtitle title-mark">填写任务信息</h2>
	            	<form method="get" class="form-horizontal" id="pricingForm">
		           		<%--环节编码 --%>
						<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
						<!-- 交易单编号 -->
						<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
						<!-- 流程引擎需要字段 -->
						<input type="hidden" id="taskId" name="taskId" value="${taskId }">
						<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
						
						<input type="hidden" id="pkid" name="pkid" value="${pricing.pkid}">
			            <div class="form_list">
			                <div class="marinfo">
			                    <div class="line">
			                        <div class="form_content">
			                            <label class="control-label sign_left_small select_style mend_select">
			                               	 <font color=" red" class="mr5" >*</font>核价时间
			                            </label>
			                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
			                            	<input type="text" class="input_type yuanwid datatime" name="pricingTime" id="pricingTime"
										 		onfocus="this.blur()" value="<fmt:formatDate  value='${pricing.pricingTime}' type='both' pattern='yyyy-MM-dd'/>">
			                            </div>
			                        </div>
			                        <div class="form_content">
			                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>税务核定价</label> 
			                            <input type="text" class="input_type yuanwid" id="taxPricing" name="taxPricing" onkeyup="checkNum(this)"
											value="<fmt:formatNumber value='${ pricing.taxPricing}' type='number' pattern='#0.00'/>">
										<span class="date_icon">万元</span>
			                        </div>
			                        <div class="form_content">
			                            <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>房屋性质</label>
			                            <aist:dict clazz="select_control data_style" id="houseProperty" name="houseProperty" display="select" defaultvalue="${pricing.houseProperty}" dictType="house_property" />
			                        </div>
			                    </div>
			                </div>
			            </div>
	            	</form>
	        	</div>
	
	        	<div class="view-content" id="caseCommentList"></div>
	
		         <div class="form-btn">
	                <div class="text-center">
	                    <button  class="btn btn-success btn-space" onclick="save(false)">保存</button>
	                    <button class="btn btn-success btn-space" onclick="submit()" readOnlydata="1" id="btnSubmit">提交</button>
	                </div>
	                <!-- <div id="smsPlatFrom"></div> -->
	            </div>
		    </div>
		    </div>
		</div>
	<content tag="local_script"> 
	<script type="text/javascript">
		var source = "${source}";

		$(document).ready(function() {
			if('caseDetails'==source){
				readOnlyForm();
			}
			
			$("#sendSMS").click(function(){
				var t='';
				var s='/';
				$("#reminder_list").find("input:checkbox:checked").closest('td').next().each(function(){
					t+=($(this).text()+s);
				});
				if(t!=''){
					t=t.substring(0,t.length-1);
				}
				$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
			});
			
			$("#reminder_list").jqGrid({
				url:"${ctx}/quickGrid/findPage",
				datatype : "json",
				height:210,
				multiselect : true,
				autowidth : true,
				shrinkToFit : true,
		        viewrecords:true,
				colNames : [ '提醒事项', '备注' ],
				colModel : [ {
					name : 'REMIND_ITEM',
					index : 'REMIND_ITEM',
					width : 500
				}, {
					name : 'COMMENT',
					index : 'COMMENT',
					width : 500
				}

				],
				viewrecords : false,
				pagebuttions : false,
				hidegrid : false,
				postData:{
		        	queryId:"queryToReminderList",
		        	search_partCode: taskitem
		        },

			});
	
		
			//渲染案件备注信息
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : taskitem
			});		
	});
		
		//提交数据
		function submit() {
			if(checkAttachment()) {
				save(true);
			}
		}

		//保存数据
		function save(b) {
			if(!checkForm()) {
				return;
			}
			var jsonData = $("#pricingForm").serializeArray();
			deleteAndModify();
			
			var url = "${ctx}/task/pricing/savePricing";
			if(b) {
				url = "${ctx}/task/pricing/submitPricing";
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
					if(b) {
						caseTaskCheck();
						if(null!=data.message){
							window.wxc.alert(data.message);
						}
					} else {
						window.wxc.success("保存成功。",{"wxcOk":function(){
							window.close();
							window.opener.callback();
						}});
					}
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
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
		
		//验证控件checkUI();
		function checkForm() {
			if($('input[name=pricingTime]').val()=='') {
				window.wxc.alert("核价时间为必填项!");
                $('input[name=pricingTime]').focus();
                return false;
           }
			if($('input[name=taxPricing]').val()=='') {
				window.wxc.alert("税务核定价为必填项!");
                $('input[name=taxPricing]').focus();
                return false;
           }
			
			if($("#houseProperty").val()=='') {
				window.wxc.alert("房屋性质为必填项!");
                return false;
           }
			return true;
		}
		
		//渲染图片 
		function renderImg(){
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer();
		}
		
		function readOnlyForm(){
			//设置核价时间不可修改
			$("#pricingTime").parent().removeClass("input-daterange");
			$("#pricingTime").removeClass("datatime");
			$("#pricingTime").attr("readonly",true);
			$("#pricingTime").css("background-color","#ccc");
			
			//设置提交按钮隐藏
			$("#btnSubmit").hide();
		}
	</script> 
	
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/transjs/sms/sms.js"></script>
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script>
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>	
    <script src="${ctx}/js/viewer/viewer.min.js"></script>
    
    <!-- 改版引入的新的js文件 -->
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	
	</content>
</body>


</html>