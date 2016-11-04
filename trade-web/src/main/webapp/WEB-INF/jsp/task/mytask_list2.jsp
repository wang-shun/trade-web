<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet"> 
<link href="${ctx}/css/style.css" rel="stylesheet"> 
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  
	
 <!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />


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

</style> 


</head>

<body>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="userId" value="${userId}" />
<input type="hidden" id="Lamp1" value="${Lamp1}" />
<input type="hidden" id="Lamp2" value="${Lamp2}" />
<input type="hidden" id="Lamp3" value="${Lamp3}" />
		
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			 <h2 class="title">
                      	任务筛选
                 </h2>
				<form method="get" class="form_list">
					<div class="form_content">
						<label class="control-label sign_left">红绿灯</label>
						<div class="checkbox i-checks radio-inline sign sign_right">
							<label> 
								<input type="radio" value="0" id="lamp0" name="lampRadios"> 全部
							</label> 
							<label> 
							    <input type="radio" value="1" id="lamp1" name="lampRadios"> <span class="label label-danger">红灯${Lamp[2]}</span>
							</label>
							<label>
							     <input type="radio" value="2" id="lamp2" name="lampRadios"> <span class="label label-warning">黄灯${Lamp[1]}</span>
							</label>
							<label> 
							     <input type="radio" value="3" id="lamp3" name="lampRadios"> <span class="label label-info">绿灯${Lamp[0]}</span>
							</label>
						</div>
					</div>
						
						
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
					      <select id="inTextType"   class="form-control select_control sign_left"  onchange="intextTypeChange()">
							<option value="1" selected>产证地址</option>
							<option value="0" >客户姓名</option>
							<option value="2">经纪人姓名</option>
							<option value="3">所属分行</option>
							<option value="4">案件编号</option>
							<option value="5">CTM编号</option>
						</select>
						<input id="inTextVal" type="text" class="sign_right input_type"   placeholder="请输入">
					</div>
						
					<div class="form_content">
						<label class="control-label sign_left">任务名</label>
						<aist:dict id="taskDfKey" name="taskDfKey" clazz="select_control sign_right_one" display="select" dictType="part_code" defaultvalue="" />
					</div>
					<div class="form_content">
						<label class="control-label sign_left">
							是否关注
						</label>
						<select class="select_control teamcode" id="isSubscribeFilter">
							<option value="">
								请选择
							</option>
							<option value="0">
								已关注
							</option>
							<option value="1">
								未关注
							</option>
						</select>
					</div>
					<div class="form_content space">
                         <div class="add_btn" align="center" id="queryFilter">
                             <button id="searchButton" type="button" class="btn btn_blue"><i class="icon iconfont">&#xe635;</i>
                              	   查询
                             </button>
                         </div>
                     </div>
                     
				</form>
	  </div>
	  
	  <div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					 <th class="text-center light_icon">
                            <i class="iconfont icon_light">
                                &#xe604;
                            </i>
                       </th>
					<th ><span class="sort" sortColumn="CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
					<th >流程环节</th>
					<th >产证地址</th>
					<th ><span class="sort" sortColumn="CREATE_TIME" sord="asc" onclick="createTimeSort();" >创建时间</span><i id="createTimeSorti" class="fa fa-sort-asc fa_up"></i></th>
					<th >上家</th>
					<th >下家</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="myTaskList">
				
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
<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript --> 
<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
<!-- iCheck -->
<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
<script src="${ctx}/js/trunk/task/mytask_list2.js?version=1.1.1"></script> 
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/trunk/case/moduleSubscribe.js?v=1.0.6"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
<!--  
<script data-main="${ctx}/js/trunk/task/mytask_queryFilter.js" src="${ctx}/js/require.js"></script>
-->
<script id="template_myTaskList" type= "text/html">
         {{each rows as item index}}
                 {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						{{if item.DATELAMP < lamp1|| item.DATELAMP==null}}
			 	 <td></td>
			{{else if item.DATELAMP < lamp2}}
                 <td>
                         <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                 		 	<div class="sk-double-bounce1 green_light"></div>
                		 	<div class="sk-double-bounce2 green_light"></div>
                		 </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
                 </td>
			{{else if item.DATELAMP < lamp3}}
				 <td>  <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                			<div class="sk-double-bounce1 orange_light"></div>
                    		<div class="sk-double-bounce2 orange_light"></div>
               		   </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
				  </td>
  			{{else}}
   				 <td>
                      <div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">
                     		<div class="sk-double-bounce1 red_light"></div>
                    		<div class="sk-double-bounce1 red_light"></div>
                 	  </div>
						{{if item.RED_LOCK==1}}
							 <p class="text-center clock clock_red">
                                <i class="icon iconfont clock_icon">&#xe60b;</i>
                             </p>
						{{else}}
                            <p class="text-center clock">
          						 <i class="icon iconfont clock_icon">&#xe60b;</i>
      						 </p>
						{{/if}}
				 </td>
			{{/if}}
				
					<td class="t-left">
						<p class="big">
                       		<a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a>
							{{if item.SUBSCRIBE_COUNT == 0}}
							  	   <span style="cursor: pointer;" class="starmack subscribe"  moduleCode="{{item.CASE_CODE}}" isSubscribe="true">
                             		    <i class="iconfont markstar star_subscribe" status="1"></i>
                         		   </span>
							{{else}}
								   <span style="cursor: pointer;" class="starmack subscribe active"  moduleCode="{{item.CASE_CODE}}" isSubscribe="false">
											<i class="iconfont markstar star_subscribe" status="1"></i>
								   </span>
							{{/if}}
						</p>
						<p>
							<i class="tag_sign">c</i>{{item.CTM_CODE}}
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

{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
<p class="demo-top" title="{{item.PROPERTY_ADDR}}">
{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
{{else}}
</p><p>
{{item.PROPERTY_ADDR}}
{{/if}}						 

						</p>
							<p><i class="salesman-icon"> </i>
 							
{{if item.AGENT_ORG_NAME !="" && item.AGENT_ORG_NAME !=null && item.AGENT_ORG_NAME.length>11}}	
<a class="demo-top" title="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}   经纪人信息：{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME}} "  >
{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}			
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME.substring(0,11)}}...
{{/if}}						
{{else}}
</a>
<a class="demo-top" title="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}    "  >
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME}}
{{/if}}	
						</a></p>
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
						{{if item.EST_PART_TIME!=null}}
						   <p>  
                              <i class="sign_normal">预</i>
                                 {{item.EST_PART_TIME}}          
                          </p>
						{{else}}
                            <p>  
                              <i class="sign_grey">预</i>
                                 {{item.EST_PART_TIME}}          
                           </p>
						{{/if}}
                    </td>
                    <td class="center">
						<p>
                          
{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
{{if item.SELLER.split("/").length-1 >1}}
<a class="demo-top" title="上家信息: {{item.SELLER}}">
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).length)+1)
}}</br></a>
{{/if}}
{{else}}

{{item.SELLER}}
{{/if}}                          </p>
                         
                    </td>
                    <td class="center">
					<p>
                           
{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
{{if item.BUYER.split("/").length-1 >1}}
<a class="demo-left" title="下家信息:{{item.BUYER}}">
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).length)+1)
}}</br>
{{/if}}</a>
{{else}}
{{item.BUYER}}
{{/if}}                                              
                          </p>
                          
                    </td>
					
                    <td class="text-center">
                           <i class="iconfont icon_revise">
                             <a href="{{ctx}}/engine/task/{{item.ID}}/process" target="_blank" >&#xe603;</a>
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
	
<script type="text/javascript">
$(function(){
		//left
		$('.demo-left').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'left',
			alignY: 'center',
			offsetX: 8,
			offsetY: 5,
		});

		//right
		$('.demo-right').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 8,
			offsetY: 5,
		});

		//top
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});

		//bottom
		$('.demo-bottom').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 8,
			offsetY: 5,
		});
	});
</script>
</content>
</body>
</html>
