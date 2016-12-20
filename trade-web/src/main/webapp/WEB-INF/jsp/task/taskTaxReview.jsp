<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>
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
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />

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

		<div class="scroll_box fadeInDown animated marginbot">
			<div class="row wrapper white-bg new-heading ">
				<div class="pl10">
					<h2 class="newtitle-big">
						审税
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
					<div class="form_list">
						<div class="marinfo">
							<div class="line">
								<form method="get" class="form-horizontal" id="tasReviewForm">
									<%--环节编码 --%>
									<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
									<!-- 交易单编号 -->
									<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
									<!-- 流程引擎需要字段 -->
									<input type="hidden" id="taskId" name="taskId" value="${taskId }">
									<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
									<%-- 原有数据对应id --%>
									<input type="hidden" id="pkid" name="pkid" value="${taxReview.pkid}">

								<div class="form_content">

									<label class="control-label sign_left_small select_style mend_select">
										<font color="red" class="mr5">*</font>
										实际审税时间
									</label>
									<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
										<c:if test="${!empty taxReview.taxTime}">
											<input class="input_type yuanwid datatime" disabled="disabled" type="text"  id="taxTime" name="taxTime" value="<fmt:formatDate  value='${taxReview.taxTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</c:if>

										<c:if test="${empty taxReview.taxTime}">
											<input class="input_type yuanwid datatime" type="text"  id="taxTime" name="taxTime" value="<fmt:formatDate  value='${taxReview.taxTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</c:if>

										</div>
								</div>
								<div class="form_content">
									<label class="control-label sign_left_small">是否唯一住房</label>
									<aist:dict clazz="select_control data_style" id="isUniqueHome" name="isUniqueHome" display="select" defaultvalue="${taxReview.isUniqueHome}" dictType="is_unique_home" />
								</div>
								<div class="form_content">
									<label class="control-label sign_left_small">购房年数</label>
									<aist:dict clazz="select_control data_style" id="holdYear" name="holdYear" display="select" defaultvalue="${taxReview.holdYear}" dictType="hold_year" />
								</div>
							</form>
							</div>
						</div>
					</div>
				</div>

				<div class="view-content" id="caseCommentList"> </div>
				<div class="ibox-title" style="height: auto;">
					<c:choose>
						<c:when test="${accesoryList!=null}">
							<h5>上传备件<br>${accesoryList[0].accessoryName }</h5>
							<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
								<div class="" id="fileupload_div_pic">
									<form id="fileupload"
										  action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
										  method="POST" enctype="multipart/form-data">
										<noscript>
											<input type="hidden" name="redirect" value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
											<input type="hidden" id="preFileCode" name="preFileCode" value="${accesory.accessoryCode }">
										</noscript>
										<c:if test="${status.index != 0}">
											<h5 align="left"><br>${accesory.accessoryName }</h5>
										</c:if>
										<div class="row-fluid fileupload-buttonbar">
											<div class="" style="height: auto">
												<div role="presentation" class="table table-striped "
													 style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
													<div id="picContainer${accesory.pkid }" class="files" data-toggle="modal-gallery"
														 data-target="#modal-gallery"></div>
								<span class=" fileinput-button " style="margin-left:10px!important;width:80px;">
								<div id="chandiaotuBtn" class=""
									 style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
									<i class="fa fa-plus"></i>
								</div>
								<input id="picFileupload${accesory.pkid }" type="file" name="files[]" multiple
									   data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
									   data-sequential-uploads="true">
							</span>
												</div>
											</div>
										</div>
									</form>
								</div>

								<div class="row-fluid">
									<div class="">
										<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
											{% for (var i=0, file; file=o.files[i]; i++) { %}
												<div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
													<!--图片缩图  -->
													<div class="preview"><span class="fade"></span></div>
													<!--  错误信息 -->
													{% if (file.error) { %}
														<div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
													{% } else if (o.files.valid && !i) { %}
													<!-- 单个对应的按钮  -->
														<div class="start span1" style="display: none">
														{% if (!o.options.autoUpload) { %}
															<button class="btn">
																<i class="icon-upload icon-white"></i>
																<span>上传</span>
															</button>
														{% } %}
														</div>
													{% } else { %}
														<div class="span1" colspan="2"></div>
													{% } %}
													<div class="cancel" style="margin-top:-172px;margin-left:85%;">
													{% if (!i) { %}
														<button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
															<i class="icon-remove"></i>
														</button>
													{% } %}
													</div>
												</div>
											{% } %}
										</script>
										<script id="templateDownload${accesory.pkid }" type="text/x-tmpl">
											{% for (var i=0, file; file=o.files[i]; i++) { %}
												<div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
													{% if (file.error) { %}
														<div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
													{% } else { %}
														<div class="preview span12">
														<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
														<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
														<input type="hidden" name="picName" value="{%=file.name%}"></input>
														{% if (file.thumbnail_url) { %}
															<img src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId={%=file.id%}" style="width:80px;height:80px;margin-left:10px;">
														{% } %}</div>
														<div class="name" style="display: none">
															<a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
														</div>
													{% } %}
													<div class="delete span2" style="margin-left:85%;margin-top:-130px;">
													   <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId={%=file.id%}" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
															<i class="icon-remove"></i>
														</button>
													</div>
												</div>
											{% } %}
										</script>
									</div>
								</div>
							</c:forEach>

							<div class="row-fluid" style="display:none;">
								<div class="span4">
									<div class="control-group">
										<a class="btn blue start" id="startUpload" style="height: 30px; width: 50px">
											<i class="icon-upload icon-white"></i> <span>上传</span>
										</a>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<h5>上传备件<br>无需上传备件</h5>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-btn">
					<div class="text-center">
						<button  class="btn btn-success btn-space" onclick="save(false)">保存</button>
						<c:if test="${empty taxReview.taxTime}">
							<button class="btn btn-success btn-space" onclick="submit()" readOnlydata="1">提交</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script>

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 上传附件相关 -->
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
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
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

	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>

	<script>
	var source = "${source}";
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
			if(t!='') {
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
		        // rowNum:8,
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
				// pager : "#pager_list_1",
				viewrecords : false,
				pagebuttions : false,
				hidegrid : false,
				// recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
				// pgtext : " {0} 共 {1} 页",
				postData:{
		        	queryId:"queryToReminderList",
		        	search_partCode: taskitem
		        },

			});

			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : false,
				autoclose : true
			});

		/*案件备注信息*/
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
	});


		/**提交数据*/
		function submit() {
			if(checkAttachment()) {
				save(true);
			}
		}

		/**保存数据*/
		function save(b) {
			if(!checkForm()) {
				return;
			}
			var jsonData = $("#tasReviewForm").serializeArray();
			deleteAndModify();


			var url = "${ctx}/task/taxReview/saveTaxReview";
			if(b) {

				url = "${ctx}/task/taxReview/submitTaxReview";
			}

			$.ajax({
				cache : true,
				async : false,//false同步，true异步
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


					if(b) {
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
							alert(data.message);
						}
						//window.location.href = "${ctx }/task/myTaskList";
					} else {
						alert("保存成功。");
						 window.close();
						 window.opener.callback();
					}
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}

		//验证控件checkUI();
		function checkForm() {
			if($('input[name=taxTime]').val()=='') {
                alert("实际审税时间为必填项!");
                return false;
           }
		   if($("#isUniqueHome").val()=='') {
                alert("是否唯一住房为必填项!");
                return false;
           }
		   if($("#holdYear").val()=='') {
               alert("购房年代为必填项!");
               return false;
           }
			/* if($('input[name=commet]').val()=='') {
                alert("备注为必填项!");
                $('input[name=commet]').focus();
                return false;
           } */
			return true;
		}

		//渲染图片
		function renderImg(){
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer({zIndex:15001});
		}
	</script> 
	</content>
</body>


</html>