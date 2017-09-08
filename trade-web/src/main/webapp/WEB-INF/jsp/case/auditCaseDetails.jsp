<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待接单列表</title>
<!-- jdGrid相关 -->
<%-- <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet"> --%>
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" >

<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/template.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.twbsPagination.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/poshytitle/src/jquery.poshytip.js'/>"></script>
<script src="<c:url value='/js/bootstrap.min.js' />" type="text/javascript"></script>
<!-- <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> -->

<!-- 引入弹出框js文件 -->
<script src="<c:url value='/js/common/xcConfirm.js' />"></script>
<!-- jdGrid相关 -->
<%-- <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> --%> 
    
<script	id="template_attachmentList" type="text/html">
		<thead >
            <th style="border: 1px solid #f3f3f4">附件类型</th>  <th style="border: 1px solid #f3f3f4">附件名称</th> <th style="border: 1px solid #f3f3f4">附件路径</th> <th style="border: 1px solid #f3f3f4">上传时间</th>
        </thead>
         {{each rows as item index}}
    <tr>
        <td>{{item.FILE_CAT}}</td>
		<td>{{item.FILE_NAME}}</td>
		<td>{{item.URL}}</td>
		<td>{{item.UPLOAD_TIME}}</td>		
    </tr>				
		{{/each}}
</script> 

<script type="text/javascript">
$(document).ready(function(){
	var sendData={};
	sendData.rows = 5;
	sendData.page = 1;
	/* loadGrid(sendData);	
	});  */
AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode);
</script> 
<script type="text/javascript">
function my_initpage(data){
	var totalCount=data.totalElements;
	var currentPage=data.totalPages;
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1)
	{
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$("#totalP").html(data.totalElements);
	
	if(!data.page){
		data.page=1;
	}
	
	
	/* 项目原生 */
	$("#pageBar").twbsPagination({
		totalPages:data.totalPages,
		visiblePages:data.totalPages,
		startPage:data.page,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			if(!data) {
				var data={};
						};
					data.page=page;				
					loadGrid(data);
		}
	});	
}

function loadGrid(sendData){
	if(!sendData.rows){
	sendData.rows = 5;		
	}
	var ctx = $("#ctx").val();
	var caseCode = $("#caseCode").val();
	$.ajax({		   
		   type: "POST",
		   data:{rows:sendData.rows,page:sendData.page,caseCode:caseCode},
		   url:ctx+ "/AuditImportCase/attachmentList",
		   async: true,
		   dataType: "json",
		   beforeSend: function () {
			   $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
				$(".blockOverlay").css({'z-index':'9998'});
			}, 
		   success: function(data){
			   console.log(data);
			   $.unblockUI();
			   var rowsData={};
	            rowsData.rows=data.content;
			   var auditCaseList = template('template_attachmentList' , rowsData);
	            $("#attachmentList").empty();
	            $("#attachmentList").html(auditCaseList);
	            data.page=sendData.page;
		   my_initpage(data);
		   }
   		});		
}

function auditSuccess(){
	var caseCode = $("#caseCode").val();
	window.wxc.confirm("请确认审核接单通过？",{"wxcOk":function(){
		window.location.href=ctx+"/AuditImportCase/auditSuccess?caseCode="+caseCode;
	}});
}

function openModal(){
	window.wxc.confirm("<input id='input1' type='text'/>",{"wxcOk":function(){
		var inputval = $("#input1").val();
		alert(inputval);
		//window.location.href=ctx+"/AuditImportCase/auditSuccess?caseCode="+caseCode;
	}});
}

function test(){
	$('#myModal').modal({
		keyboard:false,
		show:true,
		backdrop:true
	})
}

</script>  

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="caseCode" value="${caseCode}" />

	<div>
		<br> <b> 付款方式:</b>&nbsp&nbsp<b>${payType}</b> <br>
	</div>

	<div id="ccaiAttachmentInfo">
		<div>
		<br>
			<b>• CCAI附件信息</b>
		</div>
		<div class="table_content">
			<table id="attachmentList" class="table table_blue table-hover ">
				<thead>
					<td>附件类型</td>
					<td>附件名称</td>
					<td>附件路径</td>
					<td>上传时间</td>
				</thead>
			</table>
		</div>
		
		
		<!-- 更换为jqgrid来显示附件 -->
		<!-- <div class="view-content">
	              	<table id="gridTable" class="table table_blue  table-striped table-bordered table-hover customerinfo nomargin"></table>
	   				<div id="gridPager"></div>
	            </div>
	</div> -->

	<!-- 项目样式 -->
	<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
	</div>
	
    <div class="text-center">
        <button type="button" class="btn btn-success" onclick="auditSuccess()">审核通过</button>&nbsp&nbsp&nbsp&nbsp
        <button type="button" class="btn btn-success">驳回</button>&nbsp&nbsp&nbsp&nbsp
        <button type="button" class="btn btn-success" onclick="openModal()">打开对话框</button>
    </div>
    
    <button class="btn btn-primary btn-lg" onclick="test()">
    开始演示模态框
</button>

       
</div>

	
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    模态框（Modal）标题
                </h4>
            </div>
            <div class="modal-body">
                在这里添加一些文本
                <input type="text" id="input1">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="submit1">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
