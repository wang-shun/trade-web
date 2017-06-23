<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>案件记录</title>
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet" />
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
<link href="<c:url value='/css/animate.css' />" rel="stylesheet" />
<link href="<c:url value='/css/style.css' />" rel="stylesheet" />
<!-- Data Tables -->
<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />"	rel="stylesheet" />
<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />"	rel="stylesheet" />
<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />"	rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" />
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />"	rel="stylesheet" />
<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />

<link rel="stylesheet" href="<c:url value='/css/workflow/myCaseList.css' />" />
<link rel="stylesheet" href="<c:url value='/css/workflow/newRecordpop.css' />" />
<link rel="stylesheet" href="<c:url value='/css/workflow/caseRecord.css' />" />
</head>
<body class="pace-done">
<input type="hidden" id="userid" name="userid" value="${userid}" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
                    <div class="tab_menu">
                        <ul>
                            <li class="tab-selected">我的申请</li>
                            <li>我的审批</li>
                            <li>全部案件</li>
                        </ul>
                    </div>
					<form method="get" class="form-horizontal form_box">
                           <div class="row clearfix">
                               <div id="select_div_1" class="form_content">
                                   <div class="sign_left">
                                       <select class="form-control" id="inTextType" >
                                           <option value="1" selected="selected">
                                               	案件地址
                                           </option>
                                           <option value="2">
                                               	经纪人姓名
                                           </option><!-- 
                                           <option value="4">
                                               	交易顾问
                                           </option> -->
                                       </select>
                                   </div>
                                   <div class="sign_right intextval">
                                       <input type="text" id ="seachValue" name = "seachValue" class="form-control pull-left">
                                   </div>
                               </div>
                               <div class="form_content">
                                   <label class="sign_left_two control-label">
                                       	操作时间
                                   </label>
                                   <div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left"
                                       data-date-format="yyyy-mm-dd">
                                       <input name="dtBegin" id="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;text-align:left;"
                                       type="text" value="" >
                                   </div>
                               </div>
                           </div>
                           <div class="row clearfix">
                               <div class="form_content" style="margin-left: 186px;">
                                   <div class="checkbox i-checks radio-inline sign sign_right">
                                       <label class="mr10">
                                           <input type="radio" value="0"  name="radio" checked="checked"> 全部
                                       </label>
                                       <label class="mr10">
                                           <input type="radio" value="1"  name="radio"> 合并
                                       </label>
                                       <label class="mr10">
                                           <input type="radio" value="2" name="radio"> 拆分
                                       </label>
                                   </div>
                               </div>
                               <div class="form_content ml20">
                                   <button id="searchButton" type="button" class="btn btn-success mr5 btn-icon"><i class="icon iconfont">&#xe635;</i>
                                       	查询
                                   </button>
                                   <button id="cleanButton" type="reset" class="btn btn-grey" >
                                       	清空
                                   </button>
                               </div>
                           </div>
                       </form>	
				</div>
				 <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            <table class="table table-merge  table-bordered"  >
                                <thead>
                                    <tr>
		                              <th>操作纪录 </th>
		                              <th>案件编号 </th>
		                              <th>地址 </th>
		                              <th>经纪人 </th>
		                              <th>交易顾问 </th>
		                              <th>操作人  </th>
		                              <th class="text-center yiban" >操作 </th>
		                          </tr>
		                      </thead>
		                      <tbody>
		                      <tbody id="taskListf">
						   </tbody>
						</table>
						<div class="text-center page_box">
							<span id="currentTotalPagef"><strong ></strong></span>
							<span class="ml15">共<strong  id="totalPf"></strong>条</span>&nbsp;
							<div id="pageBarf" class="pagination text-center"></div>  
					    </div>
					</div>
				</div>
			</div>
		</div>
	
<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
<script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
<script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
<script	src="<c:url value='/js/inspinia.js' />"></script> 
<script	src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
<script	src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script	id="queryCastListItemList" type="text/html">
         {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	 <tr class="tr-1">
                  	{{else}}
                       <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
			{{if item.operato != null}}
				{{if item.operato == "1"}}
					<i class="sign_yellow">合流 </i>
				{{/if}}
				{{if item.operato == "2"}}
					<i class="sign_yellow">拆分 </i>
				{{/if}}
			{{/if}}
			</br>
				{{if item.status == "0"}}
				<i class="sign_blue">
				申请
				{{/if}}
				{{if item.status == "1"}}
				<i class="sign_blue">
				确认
				{{/if}}
				{{if item.status == "2"}}
				<i class="sign_brown">
				拒绝
				{{/if}}
				</i>
				 </p>
			</td>
			<td class="t-left">
				 <p class="big"><i class="mend-sign mend-green">自录</i>
					<a href="${ctx}/case/caseDetail?caseId={{item.pkid}}"  target="_blank">{{item.caseCode}}</a>
				 </p>
				 <p class="big"><i class="mend-sign mend-grey"> 导入 </i>
					<a href="${ctx}/case/caseDetail?caseId={{item.ctmpkid}}"  target="_blank">{{item.cCaseCode}}</a>
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.propertyAddr}}
				 </p>
				 <p class="big">
					{{item.cpropertyAddr}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.agentName}}
				 </p>
				 <p class="big">
					{{item.cAgentName}}
				 </p>
			</td>
			<td class="t-left">
				 <p >
					{{item.leadingProcessName}}&nbsp;
				 </p>
				 <p >
					{{item.ctmleadingProcessName}}&nbsp;
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.applierName}}
				 </p>
				 <p class="big">
					{{item.operatorTime}}
				 </p>
			</td>
			{{if index_ != null}}
				{{if index_ == "1" || index_ == "2"}}
			<td class="t-left yiban"  id="operato" name="operato">
				 <p class="big">
					{{if item.operato == "2" || item.status == "1" || item.status == "2" || userId != item.ctmleadingProcess ||index_ == "2"}}
						<button class="btn btn-success mr5" disabled="disabled" >确认</button>
					{{/if}}
					{{if item.operato == "1" && item.status == "0" && userId == item.ctmleadingProcess && index_ != "2" }}
						<button class="btn btn-success mr5"  onclick="merge({{item.id}},'1')" >确认 </button>
					{{/if}}
					{{if item.status != "0" || userId != item.ctmleadingProcess ||  index_ == "2"}}
                    	<button class="btn btn-grey" disabled="disabled">驳回</button>
					{{/if}}
					{{if item.status == "0" && userId == item.ctmleadingProcess && index_ != "2"}}
                    	<button class="btn btn-grey" onclick="merge({{item.id}},'0')">驳回</button>
					{{/if}}
				 </p>
			</td>
				{{/if}}
			{{/if}}
 			</tr>
		{{/each}}
</script> 

<script>
var index = 0;/*用来记录页面class 为tab-selected的 li的状态 **/
$(function(){/* 点击li时候设置调用方法 **/
    var $div_li =$("div.tab_menu ul li");
    $div_li.click(function(){
        $(this).addClass("tab-selected") .siblings().removeClass("tab-selected");
        var indexf =  $div_li.index(this);
        $("div.tab_box > div").eq(indexf).show().siblings().hide();
        index = indexf;
        liClick(index);
    })
})
/*判断li的值来设置调用方法 **/
function liClick(index){
    if(index == 0 ||index == 1 ){ reloadGrid(getParams("queryCaseRecordList",1,$("#userid").val(),index)); }
    if(index == 2){ reloadGrid(getParams("queryCaseRecordList",1,null,index)); }
	if(index == 0) { $("th.yiban").hide(); }else{ $("th.yiban").show(); }
}

jQuery(document).ready(function() {
	reloadGrid(getParams("queryCaseRecordList",1,$("#userid").val(),0));
	$("th.yiban").hide();
});
/*qId,page,orgid，index--快速查询ID,分页参数,部门id，页面li*/
function getParams(qId,page,orgid,index) {
	var data = {};
	if(!page) { data.page = 1; } else { data.page = page; } 
	var textType = $('#inTextType').val();
	if(textType == "1"){ data.propertyAddr = $.trim($('#seachValue').val()); }
	if(textType == "2"){ data.agentName = $.trim($('#seachValue').val()); }
	if(textType == "0"){  }
	if(textType == "4"){ }
	if (!$.isBlank($("#dtBegin").val())) { data.operatorTime= $("#dtBegin").val() + " 23:59:59"; } 
	if($('input[name="radio"]:checked').val()=="0"){}else{data.operato=$('input[name="radio"]:checked').val();}
	data.queryId=qId;/*pageBarf类型为第一次进入页面时候的设置**/
	if(orgid == "pageBarf"){
		if(index == 0){data.sqid=$("#userid").val();}
		if(index == 1){data.orgid=$("#userid").val();data.type=index;}
		if(index == 2){}
	}else{
		if(null == index){}else{
			if(index == 0){if(null ==orgid ){}else{data.sqid=orgid;}}
			if(index == 1){if(null ==orgid ){}else{data.orgid=orgid;}}
			if(index == 2){}
		}
	}
	data.rows = 10;
	return data;
}
/* 调用查询方法 **/
$("#searchButton").click(function() {  liClick(index); });
/* 调用查询方法 **/
function reloadGrid(data) {
	aist.wrap(data);
	$.ajax({
			async : true,
			url : ctx + "/quickGrid/findPage",
			method : "post",
			dataType : "json",
			data : data,
			beforeSend : function() {
				$.blockUI({ message : $("#salesLoading"), css : { 'border' : 'none', 'z-index' : '9999' } });
				$(".blockOverlay").css({ 'z-index' : '9998' });
			},
			success : function(data) {
				$.unblockUI();
				data.index_=index;
				data.userId=$("#userid").val();
				var tsAwardBaseList= template('queryCastListItemList' , data);
		        $("#taskListf").empty();
		        $("#taskListf").html(tsAwardBaseList);
		        initpagef(data.total,data.pagesize,data.page, data.records);
			},
			error : function(e, jqxhr, settings,exception) {$.unblockUI();}
		});
}
/** * 确认  */
function merge(pkId,type){
	if(type == "1"){
		window.wxc.confirm("确定合流案件吗,合流后案件流程信息会被删除，请谨慎操作！",{"wxcOk":function(){
			mergeAndReject(); 
		}});
	}
	
	if(type == "0"){
		window.wxc.confirm("确定驳回合流案件吗！",{"wxcOk":function(){
			mergeAndReject(); 
		}});
	}
}

function mergeAndReject(){
	var data = {};
	data.id = pkId;
	data.type = type;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/caseMerge/updateMergeCase",
		dataType : "json",
		data : data,
		beforeSend:function(){},
		success : function(data) {
			if(data.success){ if(data.content){ 
					window.wxc.success("合流成功！",{"wxcOk":function(){
						window.location.reload();
					}});
				} else{ 
					window.wxc.success("驳回成功！",{"wxcOk":function(){
						window.location.reload();
					}});
				} 
			}else{
				if(data.content){ 
					window.wxc.error("合流失败！"+data.message,{"wxcOk":function(){
						window.location.reload();
					}});
				} else{ 
					window.wxc.error("驳回失败！"+data.message,{"wxcOk":function(){
						window.location.reload();
					}});
				}
			}
			
		},complete: function() {  },
		error : function(errors) { }
	});
}

/* 分页 **/
function initpagef(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){ totalCount = 1500; }
	var currentTotalstrong=$('#currentTotalPagef').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalPf').text(0);
		$("#pageBarf").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalPf').text(records);
	
	$("#pageBarf").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			reloadGrid(getParams("queryCaseRecordList",page,"pageBarf",index));
	    }
	});
}

/* 日期控件 **/
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});
/* 页面title提示**/
$('.demo-top').poshytip({
	className: 'tip-twitter',
	showTimeout: 1,
	alignTo: 'target',
	alignX: 'center',
	alignY: 'top',
	offsetX: 8,
	offsetY: 5,
});

</script>
</content>
</body>
</html>