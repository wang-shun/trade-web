<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>标记无效</title>
	
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">
     <!-- Add fancyBox main JS and CSS files -->
	
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
    <!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox.css' />" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-buttons.css' />" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-thumbs.css' />" />
	
    <link href="<c:url value='/css/knowledge.css' />" rel="stylesheet" />
</head>
  
<body>	
	<div class="animated fadeInRight">
		<div class="row">
                <div class="col-lg-12"> 
                    <div class="float-e-margins">
                        <div class="ibox-title">
                            <h5>标记无效</h5>
                        </div>
                        <div class="ibox-content">
                                <div class="form-group">
                                	<label class="col-sm-2 control-label">无效原因 : </label>
                                	<input type="hidden" name="caseCode" id="caseCode" value="${caseCode}"/>
                                	<input type="hidden" name="tagId" id="tagId" value="${id}"/>
                                    <div class="col-sm-10">
                                    	<textarea rows="7" cols="5" id="unSuccessReason" name="unSuccessReason"  class="form-control"></textarea> 
                                    </div>
                                </div>
                            <div class="row-fluid">
								<a class="btn btn-primary" onclick="tagSubmit('${ctx}')">确定</a>
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
			
			<!-- app JS -->
			<script type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
			<script src="<c:url value='/js/app.js' />"></script>
	
			<!-- nullityTag.js -->
			<script type="text/javascript" src="<c:url value='/js/trunk/property/nullityTag.js' />"></script>
	</content>        
</body>
</html>