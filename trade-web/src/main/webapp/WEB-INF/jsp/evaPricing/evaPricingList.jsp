<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

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


</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="userId" value="${userId}" />
	
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
								<option value="" selected>请选择</option>
								<option value="1">A</option>
								<option value="2">B</option>
								<option value="3">C</option>
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
							<aist:dict id="evaType" name="evaType"  display="select" dictType="eva_type"  clazz="select_control form-control"/>
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
<script src="<c:url value='/js/trunk/evaPricing/evaPricingList.js' />"></script>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>


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
				<p>
                	{{item.RESIDENCE_NAME}}
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
					
			<td class="text-center">
            	<div class="float_left big_pad">
					<select pval="{{item.PKID}}" class="form-control select_control" style="width:120px">
						<option value="0">查看</option>
						<option value="1">记录</option>
						<option value="2">无效</option>
						<option value="3">发起评估申请</option>
					</select>
				</div>
				<a  href="#" onclick="gotoPage(this)" class="float_left_three"><i class="iconfont icon_revise">&#xe603;</i></a>						
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

</content>
</body>
</html>
