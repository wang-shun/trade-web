<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">

<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/case/myCaseList.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<!-- index_css -->

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

<link rel="stylesheet" href="<c:url value='/css/workflow/myCaseList.css' />" />
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />


</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">
                        案件人员变更
        </h2>
	    <form method="get" class="form-horizontal form_box">
			<div class="row clearfix">
                  <div class="form_content">
						<label class="sign_left control-label">案件编号</label>
						<input type="text" class="form-control" placeholder="" id="caseCodeForSelect" name="caseCodeForSelect" style="width:180px;">
					</div>
					<div class="form_content">
						<label class="sign_left control-label">产证地址</label>
						<input type="text" class="form-control" placeholder="" id="propertyAddr" name="propertyAddr" style="width:380px;">
					</div>
             </div>
			<div class="row m-t-sm">
				<div class="form_content">
					<div class="more_btn">						
						<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
						<button id="caseForChangeCleanButton" type="button" class="btn btn-grey">清空</button>&nbsp;
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table class="table table_blue table-striped table-bordered table-hover " >
						<thead>
							<tr>
								<th ><span class="sort" sortColumn="B.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th >案件状态</th>
								<th >产证地址</th>
								<th >上家</th>
								<th >下家</th>
								<th >经办人</th>
								<th >操作 </th>
							</tr>
						</thead>
						<tbody id="caseForChangeList">

						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
		    </div> 	
	</div>
	
	
	<div class="modal inmodal in" id="leadingProForChang" tabindex="-1" role="dialog" aria-hidden="true">
       <div class="modal-dialog" style="width: 800px;">
           <div class="modal-content animated fadeIn popup-box">
               <div class="modal_title">变更责任人</div>
               <form method="get" class="form_list">
                   <div class="line">
                       <div class="form_content">
                           <label class="control-label sign_left_small">案件编号</label>
                           <input type="text" class="teamcode input_type" placeholder="" id="caseCodeForChange" name="caseCodeForChange"  value=""  readonly>
                       </div>
                   </div>
                   <div class="line">
                       <div class="form_content">
                           <label class="control-label sign_left_small">责任人</label>
                           <input class="teamcode input_type" placeholder="" value=""  name="leadingProName" id="leadingProName"
							hVal="" onclick="leadingProForChangeClick()" />							
							<div class="input-group float_icon organize_icon" id="materialRefundUser">
                               <i class="icon iconfont">&#xe627;</i>
                            </div>
                             <input type="hidden"   name="leadingProId" id="leadingProId"  value=""/>
                       </div>
                   </div>

                   <div class="line">
                       <div class="add_btn text-center" style="margin:15px 126px;">
                           <button type="button" class="btn btn-success" id="leadingProSubmit" >确定</button>
                           <button type="reset" class="btn btn-grey" id="leadingProClose">关闭</button>
                       </div>
                   </div>
               </form>
           </div>
       </div>
   </div>
   
   
   
   	<div class="modal inmodal in" id="cooperForChang" tabindex="-1" role="dialog" aria-hidden="true">
       <div class="modal-dialog" style="width: 800px;">
           <div class="modal-content animated fadeIn popup-box">
               <div class="modal_title">变更合作人</div>
               <form method="get" 	class="form_list">
                   <div class="line">
                       <div class="form_content">
                           <label class="control-label sign_left_small">案件编号</label>
                           <input type="text" class="teamcode input_type" placeholder="" id="cooperCaseCode" name="cooperCaseCode"  value=""  readonly>
                       </div>
                   </div>  
                                                 
                   <div class="line" id="change-modal-data-show"></div>						
                   <div class="line">
                       <div class="add_btn text-center" style="margin:15px 126px;">
                           <span id="cooperForShow"><button type="button" class="btn btn-success" id="cooperSubmit" >确定</button></span>
                           <button type="reset" class="btn btn-grey" id="cooperClose">关闭</button>
                       </div>
                   </div>
               </form>
           </div>
       </div>
   </div>
</div>

<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
<script src="<c:url value='/js/trunk/case/caseForChange.js' />"></script>

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

<script id="template_caseForChangeList" type="text/html">

      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						
						<td >
 							<p class="big">
								<a href="${ctx}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a>
							</p>
 							<p>
								<i class="tag_sign">c</i>{{item.CTM_CODE}}
							</p>
						</td>
						<td class="center">
 							<p >
								  <i class="sign_blue">{{item.STATUS}}</i>
							</p>
 							<p >
								{{item.CASE_PROPERTY_SHOW}}
							</p>
						</td>
						<td >						
							{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
								<p class="demo-top" title="{{item.PROPERTY_ADDR}}">	{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
							{{else}}
								<p>	{{item.PROPERTY_ADDR}}
							{{/if}}					 
								</p>
 							<p> 
 <i class="salesman-icon"> </i>
								 
{{if item.AGENT_ORG_NAME !="" && item.AGENT_ORG_NAME !=null && item.AGENT_ORG_NAME.length>11 }}		
<a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}" >
{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}			
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME.substring(0,11)}}...
{{/if}}
{{else}}
</a><a>
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}
{{/if}}	
								 </a>
							</p>
						</td>
						
						<td class="center">
                          <p>{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
							{{if item.SELLER.split("/").length-1 >1}}<a  class="demo-top" title="上家信息:{{item.SELLER}}" >
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).length)+1)
}}</br>
</a></p><p>
{{/if}}
{{else}}
{{ if item.SELLER.length>5}}<a  class="demo-top" title="上家信息:{{item.SELLER}}" >
{{item.SELLER.substring(0,5)}}...</a>
{{else}}
{{item.SELLER}}
{{/if}}
{{/if}}
						</p>
                         
                       </td>
                       <td class="center">
                         <p>
{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
{{if item.BUYER.split("/").length-1 >1}}
<a class="demo-left" title="下家信息:{{item.BUYER}}" >
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).length)+1)
}}</br>
</a></p><p>
{{/if}}
{{else}}
{{ if item.BUYER.length>5}}<a class="demo-left" title="下家信息:{{item.BUYER}}" >
{{item.BUYER.substring(0,5)}}...</a>
{{else}}
{{item.BUYER}}
{{/if}}
{{/if}}
                          </p>
                          
                        </td>
						<td class="center" id="tdId{{item.PKID}}">
							<p id='FONT_NAME'>交易顾问：{{item.FONT_NAME}}</p>
							<p id='PROCESSOR_NAME'>合作顾问：{{item.PROCESSOR_ID}}</p>
							
						</td>
						
				<td class="text-center">					
                      <div class="btn-group">
                         <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作
                             <span class="caret"></span>
                         </button>
                               <ul class="dropdown-menu" role="menu" style="left:-95px;">
                                      <li><a href="javascript:changeProfessor('{{item.CASE_CODE}}')">变更责任人</a></li>
									  <li><a href="javascript:changeCooper('{{item.CASE_CODE}}','tdId{{item.PKID}}')">变更合作人</a></li>                                                                          
                               </ul>
                      </div>
                </td>
				  </tr>
       {{/each}}
</script> 
</content>
</body>
</html>
