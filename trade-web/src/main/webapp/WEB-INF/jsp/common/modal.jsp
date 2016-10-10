<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link href="${ctx}/static/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

<script src="${ctx}/static/js/aist-modal.js"  type="text/javascript"></script>
<script src="${ctx}/static/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bootstrap-modalmanager.js" type="text/javascript"></script>

<!-- 模态框（Modal） -->
<div class="modal fade" id="mymodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="background-color:#eaeaea;height:50px;">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">
			        <i class="icon-bell"></i> [Title]
				</h4>
			</div>
			<div class="modal-body">
			    <p>[Message]</p>
			</div>
			<div class="modal-footer" style="height:70px;">
				<button type="button" class="btn btn-default" data-dismiss="modal">[BtnCancel]</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal"> [BtnOk] </button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
