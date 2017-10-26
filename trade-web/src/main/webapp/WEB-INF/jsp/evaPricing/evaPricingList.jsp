<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<title>询价列表</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css' />" >
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />

<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" >
<link rel="stylesheet" href="<c:url value='/css/plugins/pager/centaline.pager.css' />"  />
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />


<style type="text/css">

.sign_left {
	margin-left:50px;
  	float: left;
}
.sign_left_two {
	margin-top:7px;
	float: left;
}
.float_left {
  float: left;
  margin-left:3px;
}
.float_left_two {
  float: left;
  margin-left:40px;
}
.float_left_three {
  float: left;
  margin-top:5px;
}
.big_pad {
  width: 130px;
}
.margin_left{
	margin-left:41px;
}
.margin_all{
	margin-left:300px;
	margin-top:40px;
	float:left;
}
</style> 

<content tag="pagetitle">询价列表</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="userId" value="${userId}" />

	<!-- 评估申请modal -->
	<div id="eval-modal-form" class="modal fade" role="dialog" aria-labelledby="plan-modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 1000px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="plan-modal-title">评估申请关联</h4>
				</div>
				<input id="evaPricingId" type="hidden">
				<input id="evaCode" type="hidden">
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="javascript:evalApply()">关联</button>
					<button type="button" class="btn btn-default"
						data-dismiss="modal">关闭</button>
				</div>
				<table class="table table_blue table-striped  table-hover  " >
					<thead>
						<tr>
							<th></th>
							<th>案件编号</th>
							<th>案件状态</th>
							<th>物业地址</th>
							<th>询价产证地址</th>
						</tr>
					</thead>
					<tbody id="eval-modal-body">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
		 	<h1 class="title">
				评估询价列表
        	</h1>
			<form method="get" class="form_list">
				<div class="row clearfix">
					<div class="form_content">
						<div class="sign_left">
							<select id="inTextType" class="form-control" onchange="intextTypeChange()">
								<option value="1" selected>产证地址</option>
								<option value="0">案件编号</option>
								<option value="2">申请人</option>
							</select>
						</div>
						<div class="float_left" style="width:340px;">
				 			<input id="inTextVal" type="text" class="form-control pull-left">
				 		</div>
					</div>
							
					<div class="form_content">
						<label class="control-label sign_left_two margin_left">评估公司</label>
						<div class="float_left big_pad">
							<select  id="finOrgId" class="form-control select_control">
							</select>
						</div>
					</div>
				</div>

				<div class="row clearfix">
					<div class="form_content">
						<div class="sign_left">
				      		<select id="dateType" class="form-control">
								<option value="0" selected>询价创建时间</option>
								<option value="1" >询价完成时间</option>
							</select>
						</div>
						<div id="dateVal" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
							<input id="dateStart" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
								<span class="input-group-addon">到</span> 
							<input id="dateEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
						</div>
		
					</div>
						
					<div class="form_content ">
						<label class="sign_left_two control-label margin_left">询价类型</label>
						<div class="float_left big_pad">
							<aist:dict id="evaType" name="evaType"  display="select" dictType="evapricing_type"  clazz="select_control form-control"/>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="margin_all">
		                <div class="more_btn">
		                    <button id="searchButton" type="button" class="btn btn_blue float_left_two">
		                  	 	<i class="icon iconfont">&#xe635;</i>查询 
		                  	 </button>
		                    <button id="cleanButton" type="button" class="btn btn-grey float_left_two">清空</button>
		                    <button id="addNewEvaluate"  type="button" class="btn btn-success float_left_two">新增询价</button>
		                </div>
	                </div>
				</div>   
			</form>
		</div>
	  
	  	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped  table-hover ">
			<thead>
				<tr>
			 		<th></th>
					<th><span class="sort" sortColumn="CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
					<th>产证地址</th>
					<th>评估公司</th>
					<th>申请人</th>
					<th>创建时间/完成时间</th>
					<th>询价类型</th>
					<th>询价结果</th>
					<th>操作</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody id="evaluateList">
				
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
	
	
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script src="<c:url value='/js/trunk/evaPricing/evaPricingList.js' />"></script>

<script id="template_evaluateList" type= "text/html">
	{{each rows as item index}}
		{{if index%2 == 0}}
			<tr class="tr-1">
		{{else}}
			<tr class="tr-2">
		{{/if}}

			<td></td>
	
			<td class="t-left">
				<p class="big">
					{{item.CASE_CODE}}				
				</p>
			</td>

			<td>
				<p class="demo-top" title="{{item.RESIDENCE_NAME}}">
						{{item.RESIDENCE_NAME.substring(0,10)}}...
                </p>                
			</td>

			<td class="t-left">
				<p>			
					{{item.EVA_COMPANY}}				 
				</p>
			</td>
				
			<td>
				<p>
					{{item.ARISER_NAME}}
				</p>	
			</td>	
			<td>
            	{{if item.CREATE_TIME!=null}}
					<p>  
                    	<i class="sign_normal">创</i>
                        {{item.CREATE_TIME}}          
                	</p>
				{{else}}
                	<p>  
                    	<i class="sign_grey">创</i>
                    	{{item.CREATE_TIME}}          
                	</p>
				{{/if}}

				{{if item.COMPLETE_TIME!=null}}
					<p>  
                        <i class="sign_normal">完</i>
                        {{item.COMPLETE_TIME}}          
                    </p>
				{{else}}
                    <p>  
                        <i class="sign_grey">完</i>
                       	{{item.COMPLETE_TIME}}          
                    </p>
				{{/if}}
			</td>
			
			<td>
				{{item.EVA_TYPE_NAME}}
			</td>
			<td >
            	<p>
					{{item.TOTAL_PRICE == null? null:item.TOTAL_PRICE/10000}}&nbsp;万元
				</p>
				<p>
					{{item.HOUSE_AGE}}&nbsp;年
				</p>
			</td> 
                     	
			<td>
				<p>
					{{item.STATUS_NAME}}
				</p>
			</td>
	
			<td class="text-center">
            	<div class="float_left" style="width:105px;">
					<select pval="{{item.PKID}}" evaCode="{{item.EVA_CODE}}" caseCode="{{item.CASE_CODE}}" instCode="{{item.INST_CODE}}" class="form-control select_control" style="width:100px">
						<option value="0">查看</option>
						{{if item.STATUS == 1}}
							<option value="1">评估申请</option>
						{{/if}}
						{{if item.STATUS == 2}}
							<option value="2">重新发起</option>
						{{/if}}
					</select>
				</div>
				<a  href="#" onclick="gotoPage(this)" class="float_left_three"><i class="iconfont icon_revise">&#xe603;</i></a>						
			</td>

			
		</tr>
				
	{{/each}}
</script> 

<script id="template_evalApply" type="text/html">

	{{each rows as item index}}
		<tr onclick="chooseTr({{index}})" id="tr_{{index}}">
			<td>
				
			</td>
			<td>
				<p id="p_{{index}}">
					{{item.caseCode}}
				</p>
			</td>
			<td>
				<p>
					{{item.caseStatus}}
				</p>
			</td>
			<td>
				<p>
					{{item.addr}}
				</p>
			</td>
			<td>
				<p>
					{{item.residenceName}}
				</p>
			</td>
		</tr>
	{{/each}}

</script>
 <script>
 	//排序用,aist.jquery.custom.js
 	aist.sortWrapper({
		reloadGrid : searchMethod
	}); 
 
 </script>
	<script>
	$(document).ready(function(){
		$('.abc').find('p.cas').text();
	})
	</script>
</content>
</body>
</html>
