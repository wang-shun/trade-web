<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<title>询价待办任务</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet"> 
<link href="<c:url value='/css/style.css' />" rel="stylesheet"> 
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<!-- Morris -->
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/task/myTaskList.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet" />  
	
 <!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/workflow/newRecordpop.css' />" />
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />


<style type="text/css">
#selectDiv {
	width: 480px;
}

#inTextType_chosen {
	margin-left: 40px;
}
.ui-state-hover{
	cursor:pointer;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
.text-center{text-align:center;}

#inTextType_chosen{margin-left:0}
.chosen-container{float:left;margin-right:10px}

.case-num,.case-task { text-decoration: underline !important;}
.case-num:HOVER,case-task:HOVER{
text-decoration: underline !important;
}
.case-num:visited,case-task:visited{
 text-decoration: underline !important;
}
.slash{font-weight:bold !important;}

.table_content .big a{
	min-width: 140px;
	display: inline-block;
}
.float_left {
    float: left;
}
.sign_left_two {
    margin-top: 7px;
    float: left;
}
</style> 

<content tag="pagetitle">询价待办列表</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="userId" value="${userId}" />
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
		 	<h1 class="title">
				询价待办任务
        	</h1>
			<form method="get" class="form_list">
						
				<div class="form_content">
					<label class="control-label sign_left">授权代办</label>
					<div class="checkbox i-checks radio-inline sign sign_right">
						<label> 
						      <input type="radio" value="0" id="owner0" name="ownerRadios"> 全部
						</label> 
						<label> 
							 <input type="radio" value="1" id="owner1" name="ownerRadios">  本身
						</label> 
						<label>
						     <input type="radio" value="2" id="owner2" name="ownerRadios"> 代办
						</label>
					</div>
				</div>				
				
				<div class="form_content">
				      <select id="inTextType"   class="form-control select_control sign_left"  >
						<option value="1" selected>产权地址</option>
						<option value="2">询价编号</option>
					</select>
					<input id="inTextVal" type="text" class="sign_right input_type"   placeholder="请输入">
				</div>		
				
				<div class="row clearfix">
					<div class="form_content" style="margin-left:65px;">
						<label class="control-label sign_left_two margin_left">评估公司</label>
						<div class="float_left big_pad">
							<select  id="finOrgId" class="form-control select_control">
							</select>
						</div>
					</div>
					<div class="form_content" style="margin-left:150px;">
						<label class="sign_left_two control-label">询价类型</label>
						<div class="float_left big_pad">
							<aist:dict id="evaType" name="evaType"  display="select" dictType="evapricing_type"  clazz="select_control" />	
						</div>		
					</div>	
				</div>
				<div align="center" style="margin-right:100px;">
                        <div class="add_btn"  id="queryFilter">
                            <button id="searchButton" type="button" class="btn btn_blue">
                          	 	<i class="icon iconfont">&#xe635;</i>查询
                            </button>
                        </div>
                  	</div> 
			</form>
	  </div>
	  
	  <div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					<th >询价编号</th>
					<th >流程环节</th>
					<th >产证地址</th>
					<th >评估公司</th>
					<th >询价类型</th>
					<th >询价时间</th>
					<th >询价值</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="evaPricingTaskList">
				
			</tbody>
		</table>
	</div>
			
	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
	  
    </div>
    
		    
	<content tag="local_script"> 

	
<!-- Peity -->
<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> <!-- jqGrid -->
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> <!-- Custom and plugin javascript --> 
<script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<!-- iCheck -->
<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
<script src="<c:url value='/js/trunk/evaPricing/evaPricingTaskList.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script>
	$(window).ready(function(){
		getEvaFinOrg('finOrgId');
	});
	/**
	 * 获取评估公司 格式化
	 * @param finOrgId
	 */
	function getEvaFinOrg(finOrgId){
		var url = "/manage/queryEvaCompany";
		$.ajax({
			async: true,
			type:'POST',
			url:ctx+url,
			dataType:'json',
			success:function(data){
				var html = '<option value="" selected>请选择</option>';
				if(data != null){
					$.each(data,function(i,item){
						html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
					});
				}					
				$('#'+finOrgId).empty();
				$('#'+finOrgId).append(html);
			},
			error : function(errors) {
			}
		});
	}
</script>


<script id="template_evaPricingTaskList" type= "text/html">
	{{each rows as item index}}
		{{if index%2 == 0}}
			<tr class="tr-1">
		{{else}}
			<tr class="tr-2">
		{{/if}}
				
			<td class="t-left">
				<p>
                	{{item.EVA_CODE}}
				</p>
			</td>

			<td>
				<p>
                	<i class="sign_blue">
						<a  href="{{ctx}}/engine/task/{{item.ID}}/process" target="_blank" >{{item.NAME}}</a>									
					</i>
				</p>
				<p>
                	{{item.WFE_NAME}}
                </p>                
			</td>

			<td class="t-left">
				<p>			
					{{item.PROPERTY_ADDR}}				 
				</p>
			</td>
			<td>
				<p>
					{{item.EVA_COMPANY}}
				</p>
			</td>
					
			<td>
				<p>
					{{item.EVA_TYPE_NAME}}
				</p>
			</td>

			<td>
				<p>
					{{item.EVA_TIME}}
				</p>
			</td>

			<td>
				<p>
					{{if item.EVA_PRICE !=null}}
						{{item.EVA_PRICE/10000}} 万元
					{{/if}}
				</p>
			</td>
					
			<td class="text-center">
            	<i class="iconfont icon_revise">
					<a  href="{{ctx}}/engine/task/{{item.ID}}/process" target="_blank" >&#xe603;</a>							
				</i>
			</td>
		</tr>
				
		{{/each}}
	 </script> 
	 <script>
	 	aist.sortWrapper({
			reloadGrid : searchMethod
		}); 
	 </script>
	

</content>
</body>
</html>
