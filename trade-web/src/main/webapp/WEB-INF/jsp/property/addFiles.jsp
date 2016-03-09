 <!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
 <html>
 <head>

     <!-- Add fancyBox main JS and CSS files -->
     <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
	
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"	rel="stylesheet">
<style type="text/css">
 	body{background-color: #fff !important;}
 	.del-btn{margin-left: 50px;}
</style>
</head>

<body>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="prCode" value="${prCode}"/>

<div class="alert-error error" style="padding: 8px;">提示: 最大文件上传大小是 6.00 MB。如果文件比较大请先压缩文件。</div>
<div class="row-fluid">

	<div class="span12" id="fileupload_div_pic">

	<!-- The file upload form used as target for the file upload widget -->

    <form id="fileupload"  action="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload" method="POST" enctype="multipart/form-data">


		<!-- Redirect browsers with JavaScript disabled to the origin page -->

		<noscript><input type="hidden" name="redirect" value="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"></noscript>

		<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->

		<div class="row-fluid fileupload-buttonbar">

			<div class="span7">

				<!-- The fileinput-button span is used to style the file input field as button -->

				<span class="btn green fileinput-button">

				<i class="icon-plus icon-white"></i>

				<span>选择图片...</span>

				<input id="pic-fileupload"  type="file" name="files[]" multiple 
					data-url="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
								data-sequential-uploads="true" >

				</span>

				<button type="button" class="btn blue start" id="startUpload">

				<i class="icon-upload icon-white"></i>

				<span>开始上传</span>

				</button>

				<button type="reset" class="btn yellow cancel">

				<i class="icon-ban-circle icon-white"></i>

				<span>取消上传</span>

				</button>

				<button type="button" class="btn red delete">

				<i class="icon-trash icon-white"></i>

				<span>删除</span>

				</button>

				<!-- <input type="checkbox" class="toggle fileupload-toggle-checkbox"> -->

			</div>

			<!-- The global progress information -->

			<div class="span5 fileupload-progress fade">

				<!-- The global progress bar -->

				<div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">

					<div class="bar" style="width:0%;"></div>

				</div>

				<!-- The extended global progress information -->

				<div class="progress-extended">&nbsp;</div>

			</div>

		</div>

		<!-- The loading indicator is shown during file processing -->

		<div class="fileupload-loading"></div>

		<br>

		<!-- The table listing the files available for upload/download -->

		<div   role="presentation" class="table table-striped">
			<div  id="pic-Container" class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></div>
		</div>
	</form>
</div>
	
</div>
	<div class="row-fluid">
		<div class="span12">
			<script id="template-upload-pic" type="text/x-tmpl">

							{% for (var i=0, file; file=o.files[i]; i++) { %}

							    <div name="allPicDiv" class="template-upload fade row-fluid">
									
									<!--图片缩图  -->			
							        <div class="preview span2">
										<span class="fade"></span></div>
									
									<!--图片名称 -->
							        <div class="name span2"><span>{%=file.name%}</span></div>
									<!-- 图片文件大小 -->	
							        <div class="size span1"><span>{%=o.formatFileSize(file.size)%}</span></div>
									
									<!--  错误信息 -->
							        {% if (file.error) { %}

							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>

							        {% } else if (o.files.valid && !i) { %}
									<!--上传进度条  -->
							            <div class="span2">

							                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>

							            </div>
									<!-- 单个对应的按钮  -->
							            <div class="start span2 offset1">{% if (!o.options.autoUpload) { %}

							                <button class="btn">

							                    <i class="icon-upload icon-white"></i>

							                    <span onclick="hideImg();">上传</span>

							                </button>

							            {% } %}</div>

							        {% } else { %}

							            <div class="span1" colspan="2"></div>

							        {% } %}

							        <div class="cancel">{% if (!i) { %}

							            <button class="btn red">

							                <i class="icon-ban-circle icon-white"></i>

							                <span>取消</span>

							            </button>

							        {% } %}</div>

							    </div>

							{% } %}

						</script>
						
			<!-- The template to display files available for download -->
				<script id="template-download-pic" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}

							    <div name="allPicDiv" class="template-download fade row-fluid">

							        {% if (file.error) { %}

							            <!--
										<div class="span2"></div>
										-->	

							            <div class="name span2"><span>{%=file.name%}</span></div>

							            <div class="size span1"><span>{%=o.formatFileSize(file.size)%}</span></div>

							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>

							        {% } else { %}

							            <div class="preview span2">

							            {% if (file.thumbnail_url) { %}

							                <div class="fancybox-button" data-rel="fancybox-button" href="javascript:initPicRegDetailInfo('{%=file.url%}')" title="{%=file.name%}">
 											  {% if (((file.name).substring((file.name).lastIndexOf(".")+1))=='tif') { %}
							               		 <img src="${ctx }/img/tif.png" alt="" width="80px" height="80px">
                                              {% } else { %}
 												 <img src="${imgHost}/image/{%=file.id%}/175_90_f.jpg" style="width:175px;height:90px">
  											  {% } %}
							                

							                </div>

							            <div class="name span2">

							                <div name="names" title="{%=file.name%}" class="{%=file.id%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</div>

							            </div>

							            <div class="size span2"><span>{%=o.formatFileSize(file.size)%}</span></div>
							            {% } %}</div>

									
							        {% } %}
									<div class="span5" align="right"> </div>
									<!-- <div class="span5"><input type="text" id="picDesc" name="picDesc" class="m-wrap span12"  placeholder="图片描述"></div> -->
							        <div class="delete span2">

							            <button class="btn red" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>

							                <i class="icon-trash icon-white"></i>

							                <span>删除</span>

							            </button>

							            <input type="checkbox" class="fileupload-checkbox hide" name="delete" value="1">

							        </div>

							    </div>

							{% } %}
					</script>
		</div>
	</div>
	<!-- 显示上传图片 toAttachmentList-->
	<c:if test="${toAttachmentListLength > 0}">
		<hr>
		<div><h4>已上传的图片</h4></div>
		<hr>
		<c:forEach items="${toAttachmentList}" var="toAttachment">
			<div id="${toAttachment.pkid}">
			<c:choose>
			       <c:when test="${fn:endsWith(toAttachment.fileName, '.tif')}">
 						<img src="${ctx }/img/tif.png" alt="" width="175px" height="90px">
			       </c:when>
			       <c:otherwise>
  						<img src="${imgHost}/image/${toAttachment.preFileAdress}/175_90_f.jpg"/>
			       </c:otherwise>
			</c:choose>
			    &nbsp;&nbsp;${toAttachment.fileName}<a class="btn btn-primary del-btn" onclick="delPic('${toAttachment.pkid}')">删除</a></div>
			<hr>
		</c:forEach>
	</c:if>
<hr>
	<div class="row-fluid">
		<a class="btn btn-primary" id="addFiles" onclick="commitFiles();">确定</a>
		<a class="btn btn-primary" href="#" onclick="closeThisWin();" >取消</a>
	</div>
<hr>


<content tag="local_script">

	<!-- BEGIN PAGE LEVEL PLUGINS -->
		  
	<script src="${ctx}/js/jquery.fancybox.pack.js"></script>
			
	
			<!-- BEGIN:File Upload Plugin JS files-->
		
			<script src="${ctx}/js/jquery.ui.widget.js"></script>
		
			<!-- The Templates plugin is included to render the upload/download listings -->
		
			 <script src="${ctx}/js/tmpl.min.js"></script>
		
			<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
		
			<script src="${ctx}/js/load-image.min.js"></script>
		
			<!-- The Canvas to Blob plugin is included for image resizing functionality -->
		
			<script src="${ctx}/js/canvas-to-blob.min.js"></script>
		
			<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
		
			<script src="${ctx}/js/jquery.iframe-transport.js"></script>
		
			<!-- The basic File Upload plugin -->
		
			
		
			<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
		
			<!--[if gte IE 8]><script src="${ctx}/js/jquery.xdr-transport.js"></script><![endif]-->
			
			
	<script type="text/javascript" src="${ctx}/js/clockface.js"></script>


	<script type="text/javascript" src="${ctx}/js/jquery.inputmask.bundle.min.js"></script>   

	<script type="text/javascript" src="${ctx}/js/jquery.input-ip-address-control-1.0.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/jquery.multi-select.js"></script>   

	<script src="${ctx}/js/bootstrap-modal.js" type="text/javascript" ></script>

	<script src="${ctx}/js/bootstrap-modalmanager.js" type="text/javascript" ></script> 

	
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
	<!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>
	
	
	<script type="text/javascript" src="${ctx}/js/aist.upload.js"></script>
	<script src="${ctx}/js/jquery.fileupload.js"></script>
		
	<!-- The File Upload file processing plugin -->
	<script src="${ctx}/js/jquery.fileupload-fp.js"></script>

	<!-- The File Upload user interface plugin -->
	<script src="${ctx}/js/jquery.fileupload-ui.js"></script>
	
	<!-- Form-fileupload  -->
	<script src="${ctx}/js/form-fileupload.js"></script>
	
	<!-- app JS -->
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	<script src="${ctx}/js/app.js"></script>
	<script src="${ctx}/js/property/addFiles.js"></script>
	
	<script>
		jQuery(document).ready(function() {
			 App.init();
			 AistUpload.initHouTapeInfoUpload();  
			 FormFileUpload.init();
		});
		//取消(关闭窗口)
		  $("#colseForm").click(function(){
			 parent.$.fancybox.close();
		  });	
	</script>

</content>
</body>
</html>