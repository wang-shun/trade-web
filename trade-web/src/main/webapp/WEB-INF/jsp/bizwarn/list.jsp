<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- 日期空间样式 -->
    <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	
	<!-- 分页控件样式 -->
	<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	
	<!-- 绘制列表表格样式 -->
	<link href="${ctx}/css/common/drawtable.css" rel="stylesheet" />
</head>
<body>

	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins" style="margin-top: 10px;margin-bottom: 5px;">
				<div class="ibox-title">
					<h4>商贷流失预警案件列表</h4>
				</div>
				<div class="ibox-content" style="padding-bottom: 5px;">
					<form action="${ctx }/quickGrid/findPage?xlsx" class="form-horizontal" method="post" id ='myForm'>
						<input type="hidden" name="currentOrgId" id="currentOrgId" value="${currentUser.serviceCompanyId }" /> 
						<input type="hidden" name="currentDepHierarchy" id="currentDepHierarchy" value="${currentUser.serviceDepHierarchy }"
					    <div class="jqGrid_wrapper">
					    	<input type="hidden" id="ctx" value="${ctx}"/>
					    	<div class="row form-group">
			    				<label class="col-md-1  control-label">预警类型</label>
			    				<div class="col-md-3">
			    					<select id="warnType" class="form-control pull-left">
			    						<option value="">请选择</option>
			    						<option value="LOANLOSS">贷款流失</option>
			    					</select>
			    				</div>
			    				<label class="col-md-1  control-label">状态</label>
			    				<div class="col-md-3">
			    					<select id="status" class="form-control pull-left">
			    						<option value="">请选择</option>
			    						<option value="0" <c:if test="${status == '0' }">selected</c:if> >生效</option>
			    						<option value="1" <c:if test="${status == '1' }">selected</c:if> >解除</option>
			    					</select> 
			    				</div>
				    		</div> 
					        <div class="row form-group">
			        			<label class="col-md-1  control-label">案件编号</label>
			    				<div class="col-md-3">
			    					<input type="text" id="caseCode" name="search_caseCode" class="form-control"/>
			    				</div>
			
			        			<label class="col-md-1  control-label">产证地址 </label>
			    				<div class="col-md-3">
			    					<input type="text" id="addr" name="search_propertyAddr" class="form-control"/>
			    				</div>
							</div>
							<div class="row form-group">
			        			<label class="col-md-1  control-label">预警时间</label>
		    					<div class="col-md-3" style='margin-left: 0px'>
			    					<div id="datepicker_0"
										class="input-group input-medium date-picker input-daterange pull-left"
										data-date-format="yyyy-mm-dd">
										<input id="warnTimeStart" name="search_warnTimeStart" class="form-control date-picker-input"
											style="font-size: 13px;" type="text" value=""
											placeholder="起始日期"> <span class="input-group-addon">到</span>
										<input id="warnTimeEnd"  class="form-control date-picker-input"
											style="font-size: 13px;" type="text" value=""
											placeholder="结束日期" />
											<input type="hidden" name='search_warnTimeEnd' id='search_completeTimeEnd' value=''>
									</div>
								</div>
								<div class="col-md-4">
									<a class='btn btn-primary mr5' id='addrSearchButton'>搜索</a>
									<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
								</div>
							</div>
					     </div>                         
					</form>
				</div>
			</div>
		</div>
    
	<div class="main">
		<div class="apply-wrap">
			<div class="table">
				<div id="bizwarnList"></div>
			</div>
		</div>
	</div>
	
	<content tag="local_script">
	    <!-- 日期控件 -->
		<script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	
		<script src="${ctx}/js/trunk/bizwarn/list.js?v=1.0.3"></script>
		
		<!-- 分页控件  -->
	    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		
		<script id="template_bizwarnList" type="text/html">
         	{{each rows as item index}}
                 {{if index%2 == 0}}
 				    <tr class="tr-1">
                 {{else}}
                    <tr class="tr-2">
                 {{/if}}
						<td><a href="../case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank" style="text-decoration:underline;">{{item.caseCode}}</a></td>
						<td>{{item.propertyAddress}}</td>
						<td>{{item.warnType1}}</td>
						<td>{{item.status1}}</td>
						<td>{{item.warnDatetime}}</td>
						<td>{{item.relieveDatetime}}</td>
						<td class="btn-g">
							<a href="javascript:relieve('{{item.caseCode}}','{{item.status}}')" class="btn-y" target='_blank'>解除</a>
						</td>
					</tr>
			{{/each}}
	 	</script> 
	</content>
</body>
</html>
