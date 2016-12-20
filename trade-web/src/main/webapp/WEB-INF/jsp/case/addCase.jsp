<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新建自录单</title>

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/css/select2.min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">

<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">

<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet" href="${ctx}/static_res/trans/css/common/report.css">

<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/case/case_filter.css" rel="stylesheet">
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
</head>


<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="appCtx" value="${appCtx['sales-web']}" />

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content" id="reportFive">
       <form  action="${ctx}/caseMerge/saveCaseInfo/${flag}"  method="post"  id="saveCaseInfo">
	       <div  class="form_list">
	          <div class="title"> 新建自录单</div>
	          <div class="line">
	              <div class="form_content">
	                  <label class="control-label sign_left_small">房屋地址</label>
<!-- 	      		  <input type="text" placeholder="房屋搜索"  class="select_control sign_right_one select2 span3" name="blocksSelect" id="blocksSelect" value="">  --> 
	                  
	                  <select class="select_control sign_right_one select2 span3" name="blocksSelect" id="blocksSelect"  style="height:33px;"></select>
	                  		                
	                  <select id="buildingsSelect" class="select_control sign_right_one  select2  span3"><!-- ml20 -->
	                      <option value="">请选择楼栋</option>
	                  </select>
	                  <select id="floorSelect"  class="select_control sign_right_one  select2  span3">
	                      <option value="">请选择楼层</option>
	                  </select>
	                  <select id="roomSelect"  class="select_control sign_right_one  select2 span3">
	                      <option value="">请选择房屋</option>
	                  </select>                  
	              </div>
	          </div>
	          <input type="hidden"  name="blockId"  id="blockId"  value=""> <!-- 产证区域 -->
	          <input type="hidden"  name="blockName"  id="blockName"  value=""> <!-- 产证区域 -->	 	        
	          <input type="hidden"  name="propertyCode"  id="propertyCode" value="140603093859B9126A9A39DD0B831A23">
	          <input type="hidden"  name="propertyAddr"  id="propertyAddr" value="上海虹口区外滩6666666号"> <!-- 产证地址待定 -->
	          <input type="hidden"  name="distCode"  id="distCode"  value=""> <!-- 产证区域 -->
	          <input type="hidden"  name="square"  id="square"  value=""> <!-- 面积 -->
        </div>
       
       <div class="form_list table-capital"  id="isRepeatCase" style="display:none">
           <div class="table-box">
               <p class="pink"><i class="iconfont">&#xe653;</i> 该房屋地址已经有如下案件进行，请确认是否要新建？</p>
               <table class="table table-bordered customerinfo">
                   <thead>
                       <tr>
                           <th>案件编号</th>
                           <th>状态</th>
                           <th>客户</th>
                           <th>经济人</th>
                           <th>交易顾问</th>
                       </tr>
                   </thead>
                   <tbody id="addCaseList"></tbody>
               </table>               
           </div>
			<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
		    </div> 	
       </div>
       <div class="form_list" >
           <div class="line">
               <div class="form_content">
                   <label class="control-label sign_left_small">经纪人</label>
                   <input type="text" class="select_control sign_right_one"  name="agentName" id="agentName" hVal="" onclick="addCaseFindAgent()"  value=""/>		
                   <div class="input-group float_icon organize_icon" id="newCaseAgent">
                        <i class="icon iconfont">&#xe627;</i>
                   </div>
                   <input type="hidden"   name="agentCode" id="agentCode"  value=""/>
               </div>
               <div class="form_content">
                   <label class="control-label sign_left_small">经纪人组别</label>
                   <input type="text" class="select_control sign_right_one"  name="agentOrgName" id="agentOrgName" value="" readonly >
                   <input type="hidden" class="select_control sign_right_one"  name="agentOrgId" id="agentOrgId" value="" >
                   <input type="hidden" class="select_control sign_right_one"  name="agentOrgCode" id="agentOrgCode" value="" >
               </div>
               <div class="form_content">
                   <label class="control-label sign_left_small">经纪人电话</label>
                   <input type="text" class="select_control sign_right_one" name="agentPhone" id="agentPhone" value="">
               </div>
           </div>
           <div class="line" id="topHome">
               <div class="form_content">
                   <label class="control-label sign_left_small">上家姓名</label>
                   <input type="hidden" class="select_control sign_right_one" name="pkidUp" id="pkidUp" value="">
                   <input type="text" class="select_control sign_right_one" name="guestNameUp" id="guestNameUp" value="">
               </div>
               <div class="form_content">
                   <label class="control-label sign_left_small">上家电话</label>
                   <input type="text" class="select_control sign_right_one" name="guestPhoneUp" id="guestPhoneUp" value="">
               </div>
           </div>
           <div class="line mb20">
               <a href="javascript:void(0)" style="margin-left:126px;" onclick="getAtr(this)">添加</a>
           </div>
           
           <div class="line" id="downHome">
               <div class="form_content">
                   <label class="control-label sign_left_small">下家姓名</label>
                   <input type="hidden" class="select_control sign_right_one" name="pkidDown" id="pkidUp" value="">
                   <input type="text" class="select_control sign_right_one" name="guestNameDown" id="guestNameDown" value="">
               </div>
               <div class="form_content">
                   <label class="control-label sign_left_small">下家电话</label>
                   <input type="text" class="select_control sign_right_one" name="guestPhoneDown" id="guestPhoneDown" value="">
               </div>
           </div>
           <div class="line mb20">
               <a href="javascript:void(0)" style="margin-left:126px;"  onclick="getNext(this)">添加</a>
           </div>
       </div>
       <div class="text-center mt40 mb30">
           <button type="button" class="btn btn-success mr5" id="newCaseInfoSubmit">创建</button>
           <button type="button" class="btn btn-grey" id="caseInfoClean">清空</button>
       </div>       
       </form>
   </div>
   
   <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content animated fadeIn">
                <div class="modal-body" style="background:#fff;">
                    <p class="text-center" style="font-size: 20px;">点击删除，将丢失本次填写信息！选择保存按钮可保存本次填写信息！</p>
                </div>
                <div class="modal-footer" style="text-align:center;">
                    <button type="button" class="btn btn-save" id="newCaseInfoSave">确认保存</button>
                    <button type="button" class="btn btn-success" id="newCaseInfoDelete">我要删除</button>
                    <button type="button" class="btn btn-white" id="newCaseInfoCancel">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<content tag="local_script"> 
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
<script src="${ctx}/js/plugins/jquery.custom.js"></script> 

<script src="${ctx}/js/plugins/autocomplete/select2.min.js"></script> 
<script src="${ctx}/js/trunk/case/addCase.js"></script>
<script src="${ctx}/js/jquery.json.min.js"></script>

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 

<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<script id="template_addCaseList" type="text/html">
{{each rows as item index}}
  		{{if index%2 == 0}}
 			<tr class="tr-1">
        {{else}}
            <tr class="tr-2">
        {{/if}}
	
					<td >
					        <p class="big"><a href="${ctx}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a></p>
                            
							{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
								<p class="demo-top" title="{{item.PROPERTY_ADDR}}">	{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
							{{else}}
								<p>	{{item.PROPERTY_ADDR}}
							{{/if}}					 
							</p>
					</td>
                    <td>
                               <p>
                                   <span class="yes_color">{{item.CASE_PROPERTY_ESC}}</span>
                               </p>
                               <p>
                                   <span class="no_color">{{item.STATUS_ESC}}</span>
                               </p>
                    </td>

                    <td>
                               <span class="manager">上家：{{item.SELLER}}</span>
                               <span class="manager">下家：{{item.BUYER}}</span>
                     </td>

                     <td>
                               <span class="manager"><a href="#"><em>{{item.AGENT_NAME}}：</em>{{item.AGENT_PHONE}}</a></span>
                               <span class="manager">{{item.GRP_NAME}}</span>
                     </td>

                    <td>
                               <p class="big">{{item.LEADING_PROCESS_ID_ESC}}</p>
                               <p class="big tint_grey">{{item.ORG_NAME}}</p>
                    </td>
	     </tr>
{{/each}}
</script> 
</content>
</body>
</html>
