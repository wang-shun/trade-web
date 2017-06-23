<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>知识发布</title>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    
    <!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox.css' />" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-buttons.css' />" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-thumbs.css' />" />
	
	
    <link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
    <link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet" />
    <link href="<c:url value='/css/knowledge.css' />" rel="stylesheet" />
</head>
  
<body>	
	<div class="animated fadeInRight">
		<div class="row">
                <div class="col-lg-12"> 
                    <div class="float-e-margins">
                        <div class="ibox-title">
                            <h5>知识发布</h5>
                        </div>
                        <div class="ibox-content">
                            <form method="get" class="form-horizontal form-m">
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
                                <div class="form-group">
                                	<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.TOP.CHECKBOX'>
                                		<label class="col-sm-2 control-label">置顶</label>
                                		<input type="checkbox" id="isTop" name="isTop"  class="col-sm-1">
                                	</shiro:hasPermission>
                                	<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.RECOMM.CHECKBOX'> 
                                		<label class="col-sm-2 control-label">推荐</label>
                                		<input type="checkbox" id="recom" name="recom"  class="col-sm-1"> 
                                	</shiro:hasPermission> 
                                </div>
                                
                                <div class="hr-line-dashed"></div>
                                
                                <div class="form-group"> 
                                	<jsp:include page="../knowledge/addFiles.jsp"></jsp:include>
                                </div>
   
                                <div class="hr-line-dashed"></div>
                            </form>
                            <div class="row-fluid">
								<a class="btn btn-primary" onclick="knowledgeSubmit('${ctx}')">确定</a>
								<a class="btn btn-primary" onclick="closeFancybox()" >取消</a>
							</div>
                        </div>
                    </div>
              </div>
           </div>  
	</div> 
	
	<content tag="local_script">
	
		<!-- BEGIN PAGE LEVEL PLUGINS -->
		  
			<script src="<c:url value='/js/jquery.fancybox.pack.js' />"></script>
			
	
			<!-- BEGIN:File Upload Plugin JS files-->
		
			<script src="<c:url value='/js/jquery.ui.widget.js' />"></script>
		
			<!-- The Templates plugin is included to render the upload/download listings -->
		
			 <script src="<c:url value='/js/tmpl.min.js' />"></script>
		
			<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
		
			<script src="<c:url value='/js/load-image.min.js' />"></script>
		
			<!-- The Canvas to Blob plugin is included for image resizing functionality -->
		
			<script src="<c:url value='/js/canvas-to-blob.min.js' />"></script>
		
			<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
		
			<script src="<c:url value='/js/jquery.iframe-transport.js' />"></script>
		
			<!-- The basic File Upload plugin -->
			<script type="text/javascript" src="<c:url value='/js/clockface.js' />"></script>
		
			<script type="text/javascript" src="<c:url value='/js/jquery.inputmask.bundle.min.js' />"></script>   
		
			<script type="text/javascript" src="<c:url value='/js/jquery.input-ip-address-control-1.0.min.js' />"></script>
		
			<script type="text/javascript" src="<c:url value='/js/jquery.multi-select.js' />"></script>   
		
			<script src="<c:url value='/js/bootstrap-modal.js' />" type="text/javascript" ></script>
		
			<script src="<c:url value='/js/bootstrap-modalmanager.js' />" type="text/javascript" ></script> 
			
			<!-- Add fancyBox main JS and CSS files -->
			<script type="text/javascript" src="<c:url value='/js/jquery.fancybox.js' />"></script>
			<!-- Add Button helper (this is optional) -->
			<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-buttons.js' />"></script>
			<!-- Add Thumbnail helper (this is optional) -->
			<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-thumbs.js' />"></script>
			<!-- Add Media helper (this is optional) -->
			<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-media.js' />"></script>
			
			<script type="text/javascript" src="<c:url value='/js/aist.upload.js' />"></script>
			<script src="<c:url value='/js/jquery.fileupload.js' />"></script>
				
			<!-- The File Upload file processing plugin -->
			<script src="<c:url value='/js/jquery.fileupload-fp.js' />"></script>
		
			<!-- The File Upload user interface plugin -->
			<script src="<c:url value='/js/jquery.fileupload-ui.js' />"></script>
			
			<!-- Form-fileupload  -->
			<script src="<c:url value='/js/form-fileupload.js' />"></script>
			
			<!-- app JS -->
			<script type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
			<script src="<c:url value='/js/app.js' />"></script>
			<script src="<c:url value='/js/property/addFiles.js' />"></script>
	
		<script src="<c:url value='/js/trunk/knowledge/knowledgeAdd.js' />"></script>
		<script>
			jQuery(document).ready(function() {
				 App.init();
				 AistUpload.initHouTapeInfoUpload();  
				 FormFileUpload.init();
				 
			});
		</script>
	</content>        
</body>
</html>