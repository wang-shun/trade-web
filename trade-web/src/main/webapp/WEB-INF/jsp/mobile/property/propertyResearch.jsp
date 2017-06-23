<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<!-- 上传相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/scrollpagination/scrollpagination.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/list.css' />" rel="stylesheet">

<style type="text/css">
.form-group label {
	text-align: right;
}

.form-control {
	margin-bottom: 5px;
	height: 32px;
}

.col-sm-10 {
	height: 37px;
}

.col-md-2 {
	width: 12%
}

.list li span:first-child {
	color: #555;
	width: 70%
}

.list li span:nth-child(2) {
	width: 30%;
	text-align:right;
}

.list li span:nth-child(3) {
	width: 32%;
	text-align: right;
}
#div_f{
display: none;
}
.list li .addr{float:left;width:74%;}
.list li .check{float:right;width:10%;}
.list li .check button{color:#fff;}
input[name="prStatus"]{margin-left:7px;}
input[name="isScuess"]{margin-left:7px;}
.ml-10{margin-left:10px;}
.files .delete{width:25px;}
img{ border-image-width:0px;}
</style>
<script type="text/javascript">
	var idList = [ 1 ];
	var pkid ='';
	var taskitem = "";
	var caseCode = "";
	var prCode = '';
</script>
</head>

<body>
	<input type="hidden" name="orgId" id="orgId" value="${orgId }">
	<input type="hidden" name="caseCode" id="caseCode" value="" />
	<input type="hidden" name="prStatus" id="prStatus" value="1" />

	<div id="modal-form" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="ibox ">
						<div class="ibox-title">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h5>处理产调</h5>
						</div>
						<div class="ibox-content">
							<div class='row'>
								<div class='pull-left'>
									<h5>产调项目:</h5>
								</div>
								<div class='pull-left'>
									<lable id="prCat"></lable>
								</div>
							</div>
							<div class='row'>
								<div class='pull-left'>
									<h5>是否有效:</h5>
								</div>
								<div class='pull-left'>
									<input type="radio" name="isScuess" value="0">无效<input
										type="radio" name="isScuess" value="1" checked="checked">有效
								</div>
							</div>

							<div id='div_f'>
								<div class='row'>
									<div class='col-xs-2'>
										<h5>无效原因:</h5>
									</div>
									<div class='col-xs-10'>
										<textarea rows="7" cols="25" name="unSuccessReason" id='unSuccessReason'></textarea>
									</div>
								</div>
							</div>
							<div id='div_s'>
								<div class='row'>
									<div>
										<h5>上传产调:</h5>
									</div>
								</div>
								<div>
									<div class="form-group" style="margin-left:-25px;">
										<div class="" id="fileupload_div_pic">
											<form id="fileupload"
												action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
												method="POST" enctype="multipart/form-data">
												<noscript>
													<input type="hidden" name="redirect"
														value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
													<input type="hidden" id="preFileCode" name="preFileCode"
														value="property_research_letter">
												</noscript>

												<div class="row-fluid fileupload-buttonbar">
													<div class="" style="height: auto">
														<div role="presentation" class="table table-striped "
															style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
															<div id="picContainer1" class="files"
																data-toggle="modal-gallery" data-target="#modal-gallery"></div>
															<span class=" fileinput-button "
																style="margin-left: 10px !important; width: 80px;">
																<div id="chandiaotuBtn" class=""
																	style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
																	<i class="fa fa-plus"></i>
																</div> <input id="picFileupload1" type="file" name="files[]"
																multiple
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
												<script id="templateUpload1" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-right:5px;line-height:80px;text-align:center;border-radius:4px;float:left;">
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
							        <div class="cancel" style="margin-top:-115px;margin-left:90%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
												<script id="templateDownload1" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="property_research_letter"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://aimg.sh.centanet.com/salesweb/image/{%=file.id%}/80_80_f.jpg" style="width:80px;height:80px;">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-93px;line-height:0;">
							           <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:25px;padding:0;height:25px;text-align:center;border-radius:25px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>

											</div>
										</div>

										<div class="row-fluid" style="display: none;">
											<div class="span4">
												<div class="control-group">
													<a class="btn blue start" id="startUpload"
														style="height: 30px; width: 50px"> <i
														class="icon-upload icon-white"></i> <span>上传</span>
													</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<input type="button" class="btn btn-warning" id="btn_save"
						value="保存"> <input type="button" class="btn btn-warning"
						id="reportSubBtn" value="完成">

				</div>
			</div>
		</div>
	</div>


	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">产调状态</label>
							<div class="col-sm-10">
								<input type="radio" value="" name="prStatus" checked="checked">全部
								<input type="radio" value="0" name="prStatus">待处理 <input
									type="radio" value="1" name="prStatus">已受理 <input
									type="radio" value="2" name="prStatus">已完成
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">申请时间</label>
							<div class="col-sm-10">
								<input type="date" name="sTime" id="sTime"
									class="form-control">
							</div>
							<div class="col-sm-2">
								到
							</div>
							<div class="col-sm-10">
								<input type="date" name="eTime" id="eTime"
									 class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">物业地址</label>
							<div class="col-sm-10">
								<input type="text" name="propertyAddr" id="propertyAddr"
									placeholder="" class="form-control">
							</div>
						</div>
						<div class="form-group ">

							<div class="col-sm-12">
								<button id="searchButton" type="button" class="btn btn-primary"
									style="width: 100%">搜索</button>
							</div>

						</div>
					</form>
				</div>
			</div>
		</div>

	</div>
	<a href="javascript:;" class="top"><i class="pic-top"></i></a>

	<article class="list">
		<ul id="content">

		</ul>
		<div style="display: block; height: 45px;">
			<div class="loading" id="loading">
				<div class="spinner" style="float: left;">
					<div class="spinner-container container1">
						<div class="circle1"></div>
						<div class="circle2"></div>
						<div class="circle3"></div>
						<div class="circle4"></div>
					</div>
					<div class="spinner-container container2">
						<div class="circle1"></div>
						<div class="circle2"></div>
						<div class="circle3"></div>
						<div class="circle4"></div>
					</div>
					<div class="spinner-container container3">
						<div class="circle1"></div>
						<div class="circle2"></div>
						<div class="circle3"></div>
						<div class="circle4"></div>
					</div>
				</div>
				<span class="" style="float: left;">正在加载数据...</span>
			</div>
			<div class="loading" id="nomoreresults">没有更多数据</div>
		</div>
	</article>

	<content tag="local_script"> <script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> <script
		src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> <script
		src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script> <!-- 上传附件相关 -->
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> <script
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
	<!-- 附件保存修改相关 --> <script src="<c:url value='/js/trunk/task/attachment.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> <script
		src="<c:url value='/js/plugins/scrollpagination/scrollpagination.js' />"></script>
	<script id="knowledgeListStyle1" type="text/html">
					{{if rows.length>0}}
						{{ each rows as item }}
						<li>
                        	<div>
                            	<span  ><i class="icon-person"></i>{{item.PR_APPLIANT}}</span><span><i class="icon-calendar"></i>{{item.PR_APPLY_TIME}}</span>
                       		 </div>
							<div>
								<p class="addr"><span>{{item.DIST_CODE}}&nbsp;&nbsp;&nbsp;</span><i class="icon-address"></i>{{item.PROPERTY_ADDR}} </p>
								<div class='text-left check'><button onclick="doProcess('{{item.CASE_CODE}}','{{item.PR_CODE}}','{{item.PART_CODE}}','{{item.PKID}}','{{item.PR_STATUS}}','{{item.IS_SUCCESS}}','{{item.UNSUCCESS_REASON}}','{{item.PR_CAT}}');" class="btn btn-warning btn-xs">{{if item.PR_STATUS==0}}受理{{/if}}{{if item.PR_STATUS==1}}处理{{/if}}{{if item.PR_STATUS==2}}查看{{/if}}</button></div>
							</div>
                        	
                    	</li>
						{{/each}}
					{{/if}}
	</script> <script>
		var ctx = "${ctx}";
		function doProcess(cd, pr, pc, id, st,isS,uns,prCat) {
			if (st == '0') {
				acceptance(id);
			} else if (st == '1') {
				showAttchBox(cd, pr, pc, id,isS,uns,prCat);
			} else if (st == '2') {
				location.href = ctx + '/mobile/property/box/show?prCode=' + pr;
			}
		}
		function acceptance(id) {
			$.ajax({
				cache : false,
				type : "POST",
				url : ctx + '/property/updateProcessWaitListStatus',
				dataType : "json",
				data : [ {
					name : 'pkid',
					value : id
				} ],
				success : function(data) {
					alert(data.message)
					if (data.success) {
						initScrollPaggination();
					}
				},
				error : function(errors) {
					alert("处理出错,请刷新后再次尝试！");
				}
			});

		}

		function showAttchBox(cd, pr, pc, id,isS,uns,prCat) {
			if (cd == null || cd == "") {
				$("#caseCode").val(pr);
			} else {
				$("#caseCode").val(cd);
			}
			$("#caseCode").val(pr);
			caseCode = pr;
			prCode = pr;
			pkid = id;
			taskitem = pc;
			getAttchInfo();
			$("input[name='isScuess'][value='"+isS+"']").attr('checked',true).click();
			$('#unSuccessReason').val(uns);
			$('#prCat').text(prCat);
			$("#modal-form").modal("show");
		}

		function getAttchInfo() {
			var caseCode = $("#caseCode").val();
			$
					.ajax({
						url : ctx + "/attachment/quereyAttachments",
						method : "post",
						dataType : "json",
						data : {
							caseCode : caseCode
						},
						success : function(data) {

							//将返回的数据进行包装
							$("#picContainer1").html("");
							var trStr = "";
							dataLength = 0;
							//实勘描述
							$
									.each(
											data.attList,
											function(index, value) {
												dataLength++;
												
												trStr += "<div id='picContainers"+value.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
												trStr += "<div class=\"preview span12\">";
												trStr += "<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+value.pkid+"\" />";

												trStr += "<img src='"+appCtx['img-centanet'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' alt=''>";
												trStr += "</div>";
												trStr += "<div class=\"delete span2\" style=\"margin-left: 85%; margin-top: -120px;\">";
												trStr += "<button onclick=\"romoveDiv('picContainers',"
														+ value.pkid
														+ ");\" class=\"btn red\"";
												trStr += "style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
												trStr += "<i class=\"icon-remove\"></i>";
												trStr += "</button>";
												trStr += "</div>";
												trStr += "</div>";

											});
							$("#picContainer1").append(trStr);

						}

					});
		}

		var postData = {
			queryId : "queryPropertyResearchPage",
			argu_queryorgs : '${orgId}',
			rows : 10,
			page : 1
		};
		function initScrollPaggination() {
			postData.search_propertyAddr = $("#propertyAddr").val();
			postData.page = 1;
			postData.search_prStatus = $('input[name="prStatus"]:checked ')
					.val();
			postData.search_sTime = $('#sTime').val();
			var eTime=$('#eTime').val();
			postData.search_eTime = (eTime==''?'':eTime+' 23:59:59');
			$('#content').empty();
			$('#content').scrollPagination(
					{
						'contentPage' : ctx + '/quickGrid/findPage',
						'contentData' : postData, // these are the variables you can pass to the request, for example: children().size() to know which page you are
						'scrollTarget' : $(window), // who gonna scroll? in this example, the full window
						'heightOffset' : 10, // it gonna request when scroll is 10 pixels before the page ends
						'beforeLoad' : function() { // before load function, you can display a preloader div
							$('#loading').fadeIn();
						},
						'dataType' : 'json',
						'afterLoad' : function(elementsLoaded) { // after loading content, you can use this function to animate your new elements
							$('#loading').fadeOut();
							if ($.isEmptyObject(elementsLoaded)
									|| elementsLoaded.length == 0) {
								$('#nomoreresults').show();
								setTimeout(nomoreresultsOut, 2000);
							} else {
								postData.page += 1;
							}
							$(elementsLoaded).fadeInWithDelay();

						},
						render : function(data) {
							var html = template('knowledgeListStyle1', data);
							return html;
						}
					});
		}
		function nomoreresultsOut() {
			$('#nomoreresults').fadeOut()
		}
		function checkForm(){
			if(!~~$('input[name="isScuess"]:checked ').val()){
				if($('#unSuccessReason').val()==''){
					alert('请输入无效原因！');
					return false;
				}
			}
			return true;
		}
		function save(isSubmit){
			if(isSubmit&&!!~~$('input[name="isScuess"]:checked ').val()){
				if(!checkAttachment()){
					return false;
				}
			}
			if(!checkForm()){
				return false;
			}
			if(!!~~$('input[name="isScuess"]:checked ').val()){
				if(!deleteAndModify()){
					return false;
				}
			}
			if(isSubmit){
				$.ajax({
					cache : false,
					type : "GET",
					url : ctx + '/property/isExistFile?prCodeArray='
							+ caseCode,
					dataType : "json",
					data : "",
					success : function(data) {
						if (data.success == false) {
							alert(data.message);
						} else {
							commitDispose(isSubmit);
						}
					},
					error : function(errors) {
						alert("处理出错,请刷新后再次尝试！");
					}
				});
			}else{
				commitDispose(isSubmit);
			}
		}
		//产调完成
		function commitDispose(isSubmit) {
			var isScuess=$('input[name="isScuess"]:checked ').val();
			$.ajax({
				cache : false,
				type : "POST",
				url : ctx + '/property/saveProcessingList',
				dataType : "json",
				data :  {
					pkid:pkid,
					isScuess :isScuess ,
					unSuccessReason:$("#unSuccessReason").val(),
					isSubmit:!!isSubmit
				} ,
				success : function(data) {
					alert(data.message)
					if (data.success) {
						$("#modal-form").modal("hide");
						initScrollPaggination();
					}
				},
				error : function(errors) {
					alert("处理出错,请刷新后再次尝试！");
				}
			});
		}
		$(function() {
			$('.top').hide();
			$(window).scroll(function() {
				if ($(window).scrollTop() > 100) {
					$('.top').fadeIn();
				} else {
					$('.top').fadeOut();
				}
			});
			$('.top').click(function() {
				$('html,body').animate({
					scrollTop : 0
				}, 500);
			});
			initScrollPaggination();
			$("#btn_save").click(function() {
				save(false);

			});
			
			$("#reportSubBtn").click(
					function() {
						save(true);
					});
			$("#searchButton").click(function() {
				initScrollPaggination();
			});
			$("input[name='isScuess']").on('click',function(){
				if(!!~~$(this).val()){
					$("#div_s").show();
					$("#div_f").hide();
					$('#unSuccessReason').val('');
				}else{
					$("#div_f").show();
					$("#div_s").hide();
				}
			});
		});
	</script> </content>
</body>
</html>
