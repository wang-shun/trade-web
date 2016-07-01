<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	
	
	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
	
    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/list.css" rel="stylesheet">
    
    <!-- 上传相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
	
	<!-- 分页控件 -->
	<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
	
	<script>
		var optTransferRole=false;
		<shiro:hasPermission name="TRADE.PRSEARCH.TRANSFER">
			optTransferRole=true;
		</shiro:hasPermission>
	</script>
    
    <style>
	    
	    .ui-jqgrid .ui-jqgrid-bdiv{
	    	overflow-x: hidden;
	    }
	    
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
		#propertyAddr{font-weight: bolder;}
/* 		#addrSearchButton{background-color:#f8ac59;color:#fff;padding:2px 10px;border:0;border-radius:3px;} */
    
    </style>
    
    <style>
		*{padding:0;margin:0;box-sizing:border-box;}
		.main{background-color:#f3f3f3;}
		.apply-wrap{background-color:#fff;font-family:'微软雅黑','Arial';}
		.apply-wrap .table{font-size:14px;}
		.apply-wrap .table{width:100%;background-color:#fff;}
		.apply-wrap .apply-table{width:100%;border:1px solid #eaeaea;border-right:0;}
		.apply-wrap .apply-table th{font-weight:normal;color:#fff;height:35px;background-color:#52cdec;}
		.apply-table th,.apply-table td{text-align:left;padding-left:10px;border-right:1px solid #eaeaea;}
		.apply-table tr td{color:#333;}
		.apply-table .tr-1{background-color:#fff;}
		.apply-table .tr-2{background-color:#f4f4f4;}
		.apply-table tr {height:32px!important;}
		.apply-table tr:nth-child(odd) td{padding:8px 0 3px 10px;}
		.apply-table tr:nth-child(even) td{padding:3px 0 12px 10px;}
		.apply-table tr .color-666{color:#666;}
		.apply-table tr .fs12{font-size:12px;}
		.apply-table tr .btn-y{color:#fff;padding:2px 10px;border-radius:3px;background-color:#f9ad58;text-decoration:none;}
		.triangle-up,.triangle-down{width:0;height:0;border-left:7px solid transparent;border-right:7px solid transparent;position:relative;left:8px;top:16px;}
		.triangle-up{border-bottom:10px solid #fff;top:-13px;}
		.triangle-down {border-top:10px solid #fff;}
		.fs12 i,.sqr i{color:#fff;font-size:12px;font-style:normal;padding:0px 2px;margin-right:5px;border-radius:2px;background-color:#52cdec;}
		.fs12 i.valid-label{background-color:#00cb1d;}
		.fs12 i.invalid-label{background-color:#ccc;}
		.sqr i.jl-label{background-color:#ee6384;margin-left:5px;}
		.sqr i.yc-label{background-color:#52cdec;margin-left:5px;}
		.fs12 span{display:block;padding:1px 0;}
		.apply-table tr .sq-state{padding:1px 0 1px 10px !important;}
		.apply-table tr:nth-child(odd) td.btn-g{padding:8px 0 4px 8px;}
		.apply-table tr:nth-child(even) td.btn-g{padding:5px 0 10px 8px;}
		.apply-table tr td.invalid{width:150px;padding-right:10px !important;}
		.apply-table tr em{font-style:normal;font-size:13px;line-height:18px;}
		.apply-table tbody a{color:#1a5f8e;}
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
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins" style="margin-top: 10px;margin-bottom: 5px;">
				<div class="ibox-title">
					<h4>已受理产调</h4>
				</div>
				<div class="ibox-content" style="padding-bottom: 5px;">
					<form action="${ctx }/quickGrid/findPage?xlsx" class="form-horizontal" method="post" id='myForm' >
						<input type="hidden" name="queryId" value="queryProcessingList">
						<input type="hidden" id="ctx" value="${ctx}"/>
						<input type="hidden" name="search_prDistrictId" value="${prDistrictId}">
						<input type="hidden" name="search_prStatus" value="1"/>
						<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
						<input type="hidden" id="prStatus" value="1"/>
						<input type="hidden" name="colomns" value="DIST_CODE,PROPERTY_ADDR,PR_CAT,orgName,applyOrgName,orgMgr,PR_APPLIANT,PR_APPLY_TIME,PR_ACCPET_TIME,PR_STATUS,IS_SUCCESS,UNSUCCESS_REASON">
						
						<div class="row form-group">
		    				<label class="col-md-1  control-label" style="text-align: center;padding-top: 7px;">行政区域</label>
		    				<div class="col-md-2">
		    					<aist:dict id="distCode" clazz="form-control pull-left" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
		    				</div>
		        			<label class="col-md-1  control-label" style="text-align: center;">产证地址 </label>
		    				<div class="col-md-3">
		    					<input type="text" id="addr" name="search_propertyAddr" class="form-control"/>
		    				</div>
		    				<div class="col-md-3">
								<button id="addrSearchButton" type="button" class="btn btn-warning">查询</button>
								<button id="exportExcelButton" type="button" class="btn btn-primary" onclick="document.getElementById('myForm').submit();return false">导出产调至Excel</button>
		    				</div>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="main">
		<div class="apply-wrap">
			<div class="table">
				<div id="processingList"></div>
			</div>
		</div>
	</div>
	                 
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
									<h5>物业地址:</h5>
								</div>
								<div class='pull-left' style="margin-top: 3px;">
									<span id="address"></span>
								</div>
							</div>
							
							<div class='row'>
								<div class='pull-left'>
									<h5>产调项目:</h5>
								</div>
								<div class='pull-left' style="margin-top: 3px;">
									<span id="prcat"></span>
								</div>
							</div>
							
							<div class='row'>
								<div class='pull-left'>
									<h5>所属分行:</h5>
								</div>
								<div class='pull-left' style="margin-top: 3px;">
									<span id="applyOrgName"></span>
								</div>
							</div>
							
							<div class='row'>
								<div class='pull-left'>
									<h5>区蕫:</h5>
								</div>
								<div class='pull-left' style="margin-top: 3px;">
									<span id="orgMgr"></span>
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
							

							<div id='div_f' class="row">
			
									<div class='pull-left'>
										<h5>无效原因:</h5>
									</div>
									<div class='col-xs-10' style="margin-top: 5px;">
										<textarea rows="7" cols="25" name="unSuccessReason" id='unSuccessReason'></textarea>
									</div>
							</div>
							<div id='div_s' class='row'>
									<div class="pull-left">
										<h5>上传产调:</h5>
									</div>
								<div style="padding-top: 30px;">
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
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-right:5px;line-height:80px;text-align:center;border-radius:4px;float:left;">
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
							        <div class="delete span2" style="margin-left:75%;margin-top:-93px;line-height:0;">
							           <button data-url="<aist:appCtx appName='aist-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:25px;padding:0;height:25px;text-align:center;border-radius:25px!important;">
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
						id="btn_done" value="完成">

				</div>
			</div>
		</div>
	</div>
	
	
  <content tag="local_script">
    <!-- Mainly scripts -->

    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
    
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
		
	<!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>
	
	<!-- toJSON -->
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	
	<!-- 上传附件相关 -->
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="${ctx}/js/trunk/task/attachment.js"></script>
	
	<script src="${ctx}/js/trunk/property/processingList.js?v=1.2.03"></script>
	<script src="${ctx}/js/trunk/property/propertyByaddr.jqgridSearch.js?v=1.0.2"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	
	<!-- 分页控件  -->
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	
<!-- 	<span><i class="invalid-label">完</i></span> -->
	<script id="template_processingList" type="text/html">
         	{{each rows as item index}}
                 {{if index%2 == 0}}
 				    <tr class="tr-1">
                 {{else}}
                    <tr class="tr-2">
                 {{/if}}
						<td>{{item.DIST_CODE}}</td>
						<td>{{item.PROPERTY_ADDR}}</td>
						<td>{{item.applyOrgName}}</td>
						<td rowspan="2" class="fs12 sq-state">
							<span><i class="sq-label">申</i>{{item.PR_APPLY_TIME}}</span>
							<span><i class="sl-label">受</i>{{item.PR_ACCPET_TIME}}</span>
						</td>
						{{if item.IS_SUCCESS == '是'}}
							<td class="fs12 sq-state"><i class="valid-label">是</i></td>
						{{else if item.IS_SUCCESS == '否'}}
							<td rowspan="2" class="fs12 sq-state invalid"><i class="invalid-label">否</i><em style="word-break:break-all">{{item.UNSUCCESS_REASON}}</em></td>
						{{else}}
							<td></td>
                 		{{/if}}
						<td class="sqr">申请人：{{item.PR_APPLIANT}}
							{{if item.CHANNEL == '经纪人'}}
								<i class="jl-label">经</i>
							{{else}}
                 				<i class="yc-label">誉</i>
							{{/if}}
						</td>
						<td>
							{{if wrapperData.optTransferRole}}
                 				<button type="button" class="btn btn-warning btn-xs" id="teamCode" name="teamCode" readonly="readonly" onclick="showOrgSelect({{item.PKID}})" value='' >转组</button>
							{{else}}
                 			{{/if}}
						</td>
					</tr>
				{{if index%2 == 0}}
 				    <tr class="tr-1">
                {{else}}
                    <tr class="tr-2">
                {{/if}}
						<td></td>
						<td></td>
						<td>区董：{{item.orgMgr}}</td>
						{{if item.IS_SUCCESS == '是'}}
							<td></td>
						{{else if item.IS_SUCCESS == '否'}}、
						{{else}}
							<td></td>
                 		{{/if}}
						<td>执行人：</td>
						<td>
							<button type='button' onclick="showAttchBox('{{item.CASE_CODE}}','{{item.PR_CODE}}','{{item.PART_CODE}}','{{item.PKID}}','{{item.IS_SUCCESS}}','{{item.UNSUCCESS_REASON?item.UNSUCCESS_REASON:''}}','{{item.PROPERTY_ADDR}}','{{item.PR_CAT}}','{{item.applyOrgName}}','{{item.orgMgr}}');" class='btn btn-warning btn-xs'>处理</button>
						</td>
					</tr>
			{{/each}}
	 </script> 

</content>
</body>
</html>
