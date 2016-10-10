
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<div class="alert-error error" style="padding: 8px;">提示: 最大文件上传大小是 6.00 MB。如果文件比较大请先压缩文件。</div>
<div class="row-fluid">

	<div class="span12" id="fileupload_div_pic">

	<!-- The file upload form used as target for the file upload widget -->

    <form id="fileupload"  action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" method="POST" enctype="multipart/form-data">


		<!-- Redirect browsers with JavaScript disabled to the origin page -->

		<noscript><input type="hidden" name="redirect" value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"></noscript>

		<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->

		<div class="row-fluid fileupload-buttonbar">

			<div class="span7">
				<!-- The fileinput-button span is used to style the file input field as button -->
				<span class="btn green fileinput-button">
				<i class="icon-plus icon-white"></i>
				<span>选择文件...</span>

				<input id="pic-fileupload"  type="file" name="files[]" multiple 
					data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
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
									
									
									<!--文件名称 -->
							        <div class="name span2"><span>{%=file.name%}</span></div>
									<!-- 文件大小 -->	
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

							                <button class="btn btn-primary">
							                    <i class="icon-upload icon-white"></i>
							                    <span onclick="hideImg();">上传</span>
							                </button>

							            {% } %}</div>

							        {% } else { %}

							            <div class="span1" colspan="2"></div>

							        {% } %}

							        <div class="cancel">{% if (!i) { %}

							            <button class="btn btn-primary">

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

							            <div class="name span2">

							                <div name="names" title="{%=file.name%}" class="{%=file.id%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</div>

							            </div>

							            <div class="size span2"><span>{%=o.formatFileSize(file.size)%}</span></div>
							            {% } %}</div>


							        {% } %}
									<div class="span5" align="right"> </div>
							        <div class="delete span2">
							            <button class="btn btn-primary" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
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


 