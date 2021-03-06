<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新建自录单</title>


<link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/select2.min.css' />">
<link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">

<!-- aist列表样式 -->
<link href="<c:url value='/css/common/aist.grid.css' />" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />" rel="stylesheet">

<!-- stickUp fixed css -->
<link rel="stylesheet" href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/stickmenu.css' />">

<!-- index_css  -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet" href="<c:url value='/static_res/trans/css/common/report.css' />">

<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
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
	                  <select class="select_control sign_right_one select2 span3" name="blocksSelect"  id="blocksSelect"  style="height:33px;width:250px;"></select>
	                  		                
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
       
       
       
       <div  class="form_list mt20"  style="display: none"  id="houseInfo">
          <div class="line">
	              <div class="form_content">
	                  <label class="control-label sign_left_small">房屋类型</label>	                  
	                  <aist:dict clazz="select_control sign_right_one" id="propertyType"	name="propertyType" display="select"  defaultvalue="" dictType="30014" />
	              </div>
              	<div class="form_content">
                  <label class="control-label sign_left_small">产证地址</label>
                  
                  <input type="hidden"  name="propertyCode"  id="propertyCode" value="">	        
                  <input class="input_type"  style="width:460px;" placeholder="请输入" value="" name="propertyAddr"  id="propertyAddr">
                </div>
              </div>
                 <div class="line">
                     <div class="form_content">
                         <label class="control-label sign_left_small">产证面积</label> 
                         <input class="select_control sign_right_one" placeholder=""  name="square"  id="square" value="">
                         <span class="date_icon">平米</span>
                     </div>
                     <div class="form_content">
                         <label class="control-label sign_left_small">所在楼层</label>
                         <input type="text" class="select_control sign_right_one" name="floor"  id="floor" value="">
                     </div>
                     <div class="form_content">
                         <label class="control-label sign_left_small">总层高</label>
                         <input type="text" class="select_control sign_right_one" name="totalFloor"  id="totalFloor"  value="">
                     </div>
                 </div>
                 <div class="line">
                     <div class="form_content">
                         <label class="control-label sign_left_small">所在区域</label>
                         <aist:dict clazz="select_control sign_right_one" id="distCode" name="distCode" display="select" defaultvalue="" dictType="yu_shanghai_district" />
                     </div>
                     <div class="form_content">
                         <label class="control-label sign_left_small">竣工年份</label>
                          <select class="select_control sign_right_one" name="finishYear" id="finishYear"></select>
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
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 


		
<script src="<c:url value='/js/plugins/autocomplete/select2.min.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/i18n/zh-CN.js' />"></script> 
<script src="<c:url value='/js/trunk/case/addCase.js' />"></script>
<script src="<c:url value='/js/jquery.json.min.js' />"></script>

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

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
