<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>知识修改</title>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    
    
    <link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
    <link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet" />
    <link href="${ctx}/css/knowledge.css" rel="stylesheet" />
</head>
  
<body>	
	<div class="animated fadeInRight">
		<input type="hidden" name="knowledgePkid" id="knowledgePkid" value="${knowledgePkid }" >
		<div class="row">
                <div class="col-lg-12">
                    <div class="float-e-margins">
                        <div class="ibox-title">
                            <h5>知识修改</h5>
                        </div>
                        <div class="ibox-content">
                            <form method="get" class="form-horizontal form-m">
                                <div class="form-group">
                                	<label class="col-sm-2 control-label">知识编码</label>
                                    <div class="col-sm-10">
                                    	<input type="text" id="knowledgeCode" name="knowledgeCode"  disabled="disabled" class="form-control">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                	<label class="col-sm-2 control-label">知识标题</label>
                                    <div class="col-sm-10">
                                    	<input type="text" id="knowledgeTitle" name="knowledgeTitle"  class="form-control"> 
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                	<label class="col-sm-2 control-label">知识正文</label>
                                    <div class="col-sm-10">
                                    	<textarea rows="5" cols="5" id="knowledgeContent" name="knowledgeContent"  class="form-control"></textarea> 
                                    </div>
                                </div>
                                
                                <div class="hr-line-dashed"></div>
	                                <div>
                               			<div><h4>已上传的文件</h4></div>
                               			<hr>
                                		<div id="divPic"></div>
                                	</div>
                                
                                <div class="form-group"> 
                                	<jsp:include page="../knowledge/addFiles.jsp"></jsp:include>
                                </div>
                                <div class="hr-line-dashed"></div> 
                            </form>
							<div class="row-fluid">
								<a class="btn btn-primary" onclick="knowledgeSubmit('${knowledgePkid }','${ctx}')">确定</a>
								<a class="btn btn-primary" onclick="closeFancybox()" >取消</a>
							</div>
                        </div>
                    </div>
                </div>
           </div>  
	</div>
	
	
	
	
	
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
			
		<script src="${ctx}/js/trunk/knowledge/knowledgeUpdate.js"></script>
		<script>
			jQuery(document).ready(function() {
				 knowledgeUpdateLoadInit('${knowledgePkid}','${ctx}','${imgweb}');
				 App.init();
				 AistUpload.initHouTapeInfoUpload();  
				 FormFileUpload.init();
			});
		</script>
	</content>        
</body>
</html>