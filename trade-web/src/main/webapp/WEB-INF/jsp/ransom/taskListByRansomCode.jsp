<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet"> --%>
<style type="text/css">
	.test {
	width: 500px!important;}
</style>

<!-- 案件任务列表 -->
<div id="case-task-modal-form" class="modal fade case_task"
	role="dialog" aria-labelledby="case-task-modal-title"
	aria-hidden="true">
	<div class="modal-dialog" style="width:900px">
		<div class="modal-content" style="margin:0 auto;width:550px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="case-task-modal-title">同案件下待办任务</h4>
			</div>
			<div class="modal-body">
				<div class="row ">
					<div class="col-lg-12 test">
						<table id="table_list_case_task"></table>
						<div id="pager_list_case_task"></div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					onclick="javascript:returnTaskPage()">返回任务列表</button>
			</div>
		</div>
	</div>
</div>
