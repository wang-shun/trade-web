<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待接单列表</title>
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" >
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/template.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.twbsPagination.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/poshytitle/src/jquery.poshytip.js'/>"></script>
<!-- 引入弹出框js文件 -->
<script src="<c:url value='/js/common/xcConfirm.js' />"></script>

<!-- <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
 -->   
<script	id="template_auditCaseList" type="text/html">
		<thead>
<th style="border: 1px solid #f3f3f4">案件编号</th>
<th style="border: 1px solid #f3f3f4">产证地址</th>
<th style="border: 1px solid #f3f3f4">贷款权证</th>
<th style="border: 1px solid #f3f3f4">过户权证</th>
<th style="border: 1px solid #f3f3f4">业务员姓名</th>
<th style="border: 1px solid #f3f3f4">转入时间</th>
<th style="border: 1px solid #f3f3f4">操作</th>
        </thead>
         {{each rows as item index}}
    <tr>
        <td>{{item.CASE_CODE}}</td>
		<td>{{item.PROPERTY_ADDR}}</td>
		<td>{{item.loan}}</td>
		<td>{{item.warrant}}</td>
		<td>{{item.agent}}</td>
		<td>{{item.IMPORT_TIME}}</td>
		<td><a href="javascript:void(0)" onclick="auditCase('{{item.CASE_CODE}}')">审核案件详情</a></td>
    </tr>				
		{{/each}}
</script> 

<script type="text/javascript">
$(document).ready(function(){
	var ctx = $("#ctx").val();
	var month=$('#monthSelection option:selected') .val();
	var data={};
	data.rows = 5;
    data.page = 1;
	loadGrid(data,month);
	});
/*
 * 使用说明:
 * window.wxc.Pop(popHtml, [type], [options])
 * popHtml:html字符串
 * type:window.wxc.xcConfirm.typeEnum集合中的元素
 * options:扩展对象
 * 用法:
 * 1. window.wxc.xcConfirm("我是弹窗<span>lalala</span>");
 * 2. window.wxc.xcConfirm("成功","success");
 * 3. window.wxc.xcConfirm("请输入","input",{onOk:function(){}})
 * 4. window.wxc.xcConfirm("自定义",{title:"自定义"})
 */
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
					var month=$('#monthSelection option:selected') .val();
					data.month=month;
					loadGrid(data,data.month);
		}
	});	
}

function loadGrid(sendData,month){
	var mypage=sendData.page;
	$.ajax({		   
		   type: "POST",
		   data:{month:month,page:mypage},
		   url:ctx+ "/AuditImportCase/listData",
		   async: true,
		   dataType: "json",
		   beforeSend: function () {
				//$.blockUI();
			   $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
				$(".blockOverlay").css({'z-index':'9998'});
			},
		   success: function(data){
			   console.log(data);
			   $.unblockUI();
			   var rowsData={};
	            rowsData.rows=data.content;
			   var auditCaseList = template('template_auditCaseList' , rowsData);
	            $("#auditCaseList").empty();
	            $("#auditCaseList").html(auditCaseList);
	            $("#month").html(data.month);
	            data.page=sendData.page;
		   my_initpage(data);
		   }
   		});		
}

function submitMonth(month){
	month=$('#monthSelection option:selected') .val();
	window.location.href=ctx+ "/AuditImportCase/list?month="+month;
}

function auditCase(caseCode){
	window.location.href=ctx+ "/AuditImportCase/details?caseCode="+caseCode;
}
</script>   
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="month" value="${month}" />
	<div><br> 
    <table>
        <tr>
            <td><b>月度：&nbsp&nbsp</b></td>
            <td>
            <c:if test="${!empty month}">
            <select id="monthSelection">
            	<option value="1" <c:if test="${month==1}">selected</c:if> >1月</option>
                <option value="2" <c:if test="${month==2}">selected</c:if> >2月</option>
                <option value="3" <c:if test="${month==3}">selected</c:if> >3月</option>
                <option value="4" <c:if test="${month==4}">selected</c:if> >4月</option>
                <option value="5" <c:if test="${month==5}">selected</c:if> >5月</option>
                <option value="6" <c:if test="${month==6}">selected</c:if> >6月</option>
                <option value="7" <c:if test="${month==7}">selected</c:if> >7月</option>
                <option value="8" <c:if test="${month==8}">selected</c:if> >8月</option>
                <option value="9" <c:if test="${month==9}">selected</c:if> >9月</option>
                <option value="10" <c:if test="${month==10}">selected</c:if> >10月</option>
                <option value="11" <c:if test="${month==11}">selected</c:if> >11月</option>
                <option value="12" <c:if test="${month==12}">selected</c:if> >12月</option>
            </select>
            
            </c:if>
            
            </td>
            <td>&nbsp&nbsp<!-- <input onclick="submitMonth()" id="button1" type="button" value="查询"> -->
            <button onclick="submitMonth()" id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
            </td>
        </tr>
    </table>
</div>
<br>

<div>
    <table>
        <tr>
            <td>接单数统计:</td><td>未接单数:<span>${INVALIDCOUNT }</span></td><td>已接单数:<span>${VALIDCOUNT }</span></td>
        </tr>
    </table>
</div>

<br>
<div>
    <table id="auditCaseList" class="table table_blue table-hover ">
        <thead>
            <td>案件编号</td>  <td>产证地址</td> <td>贷款权证</td> <td>过户权证</td> <td>业务员姓名</td> <td>转入时间
            </td><td>操作</td>
        </thead>
    </table>
</div>
    
    	<!-- 项目样式 -->
	<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
	</div>




</body>
</html>