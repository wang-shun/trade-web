<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<input type="hidden" name="pkId" id="pkId" />
<input type="hidden" name="divCaseName" id="divCaseName" />
<input type="hidden" name="propertyCode" id="propertyCode" />
<input type="hidden" name="caseCode_" id="caseCode_" />
<div id="myModalsa" class="modal inmodal in" >
       <div class="modal-dialog" style="width: 1070px;">
           <div class="modal-content animated fadeIn apply_box info_box">
               <div  class="form_list clearfix">
                   <div class="modal_title" >
                       	<span class="stanwidth" name="divCaseName_" id="divCaseName_"></span>
                   </div>
                   <div class="pop-list">
                       <div class="line">
                           <p><em>案件编号</em><span class="stanwidth"><a  onclick="caseDetail()" target="_blank" name="caseCode" id="caseCode"></a></span></p>
                           <p><em>房源地址</em><span  name="propertyAddr" id="propertyAddr" ></span></p>
                       </div>
                       <div class="line">
                           <p><em>经纪人</em><span class="stanwidth" name="agentName" id="agentName"></span></p>
                           <p><em>手机</em><span class="stanwidth" name="agentPhone" id="agentPhone"	></span></p>
                           <p><em>所在门店</em><span class="stanwidth" name="agentOrgName" id="agentOrgName"></span></p>
                       </div>
                       <div class="line">
                           <p><em>上家</em><span class="stanwidth" name="seller" id="seller"></span></p>
                           <p><em>下家</em><span name="buyer" id="buyer"></span></p>
                       </div>
                   </div>
               </div>
               <button type="button" class="close close_blue" data-dismiss="modal" onclick="closef()" >
                   <i class="iconfont icon_rong">
                       &#xe60a;
                   </i>
               </button>
               <div class="apply_table">
                   <div class="form_list table-capital">
                           <div class="table-box">
                               <p class="pink"><i class="iconfont">&#xe653;</i> 系统检测到可能重复案件!</p>
                               <table class="table table_blue mt20 table-striped table-bordered table-hover customerinfo" id="editable" name="editable">
		                       <thead>
		                           <tr>
		                               <th></th>
		                               <th>案件编码</th>
		                               <th>状态</th>
		                               <th>客户</th>
		                               <th>经纪人</th>
		                               <th>交易顾问</th>
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
                <div class="text-center mt10 mb30" name="buttonDiv" id="buttonDiv" >
                </div>
           </div>
       </div>
   </div>

<script id="queryCastListItemList" type= "text/html">
 {{each rows as item index}}
	<tr>
		<td>
			<p class="big">
				{{ if item.status == "被合流" }}
<input type="hidden"  value="{{item.pkId}}" status="{{item.status}}" id="mergePkid" name="mergePkid" disabled="disabled" qfType="{{item.toQfType}}"  />
{{else}}
<input type="radio" value="{{item.pkId}}" status="{{item.status}}" id="mergePkid" name="mergePkid" />
{{/if}}
			</p>
		</td>
		<td>
			<p class="big">
			   <a href="{{ctx}}/case/caseDetail?caseId={{item.pkId}}"  target="_blank">{{item.CASE_CODE}}</a>
			{{if item.caseOrigin == 'INPUT'}}
			 <i class="sign_blue ml10">
			自录单
			</i>
			{{/if}}
			{{if item.caseOrigin == 'CTM'}}
			 <i class="sign_blue ml10">
			导入单
			</i>
			{{/if}}
			{{if item.caseOrigin == 'MERGE'}}
			 <i class="sign_blue ml10">
			合流单
			</i>
			{{/if}}
			</p>
			<p>
				 {{item.propertyAddr}}
			</p>
		</td>
		   realOrgName
		<td>
			<p class="big">
				{{if item.status != null}}
				<span class="yes_color">
			   {{item.status}}
				</span>
				{{/if}}
			</p>
			<p>
			{{if item.caseProperty != null}}
				<span class="no_color">
				 {{item.caseProperty}}
				</span>
			{{/if}}
			</p>
		</td>
		<td>
			<p class="big">
			   {{item.SELLER}}
			</p>
			<p>
				  {{item.BUYER}}
			</p>
		</td>
		<td>
			<p class="big">
			   {{item.agentName}}:{{item.agentPhone}}
			</p>
			<p>
				 {{item.grpName}}
			</p>
		</td>
		<td>
			<p class="big">
			   {{item.realName}}
			</p>
			<p>
				 {{item.realOrgName}}
			</p>
		</td>
	</tr>
{{/each}}
</script>
<script>
var urlType='';
var distriType = false;
/* 第一次进入关联页面对页面上的表头值进行设置  **/
function init(pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,inputType){
	$("#pkId").val("");
	$("#pkId").val(pkId);
	$("#caseCode").html(caseCode);
	$("#propertyAddr").html(propertyAddr);
	$("#agentName").html(agentName);
	$("#agentPhone").html(agentPhone);
	$("#agentOrgName").html(agentOrgName);
	$("#seller").html(seller);
	$("#buyer").html(buyer);
	$("#divCaseName").val(inputType);
	if(inputType=="CTM"){
		$("#divCaseName_").html("导入案件");
	}
	if(inputType=="INPUT"){
		$("#divCaseName_").html("自建案件");
	}
	
}
/* 调用关联案件方法  **/
function merge(){
	if(undefined == $('input[name="mergePkid"]:checked').val()){alert("请选择一条案件！");return;}
    if(!confirm("确定申请合流案件吗！")){ return false; }
    var inputType = $("#divCaseName").val();
    var mergePkid;
    var pkId;
    if("CTM" == inputType){
    	mergePkid = $("#pkId").val();
        pkId = $('input[name="mergePkid"]:checked').val();
    }
    if("INPUT" == inputType){
    	mergePkid = $('input[name="mergePkid"]:checked').val();
        pkId = $("#pkId").val();
    }
	var data = {};
	data.mergePkid= mergePkid;
	data.pkId= pkId;
	data.inputType= inputType;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/caseMerge/mergeCase",
		dataType : "json",
		data : data,
		beforeSend:function(){ },
		success : function(data) {
			if(data.success){ 
				if(distriType && undefined != urlType && '' != urlType){
					alert("合流申请成功！"); 
					$("#myModalsa").modal("hide");
					caseDistributeType();
					window.location.reload();
				}else{
					alert("合流申请成功！"); 
					$("#myModalsa").modal("hide");
					window.location.reload();
				}
			}else{ alert("合流申请失败！"+data.message);  }
		},complete: function() {  },
		error : function(errors) {
		}
	});
}
/* 调用拆分案件方法  **/
function qfMerge(){
	
	var userData = $('input[name="mergePkid"]').attr("qfType");
	if(null != userData && "" != userData){}else{alert("对不起你没有拆分这个案件的权限！");return true;}
	var arr = userData.split(';'); 
	if(null != arr && arr.length>2){
		if(null != arr[0] && "" != arr[0] && arr[0] == $("#userId").val() ){
		if(null != arr[1] && "" != arr[1] && arr[1] == $("#serviceDepId").val() ){}}
		else{alert("对不起你没有拆分这个案件的权限！");return true;}
			
	}else{
		alert("对不起你没有拆分这个案件的权限！");return true;
	}
	
    if(!confirm("确定拆分合流案件吗！")){ return false; }
    var inputType = $("#divCaseName").val();
    var mergePkid;
    var pkId;
    mergePkid = $('input[name="mergePkid"]:checked').val();
    pkId = $("#pkId").val();
	var data = {};
	data.mergePkid= mergePkid;
	data.pkId= pkId;
	data.inputType= inputType;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/caseMerge/qfMergeCase",
		dataType : "json",
		data : data,
		beforeSend:function(){ },
		success : function(data) {
			if(data.success){ 
				if(distriType && undefined != urlType && '' != urlType){
					alert("拆分成功！"); 
					$("#myModalsa").modal("hide");
					caseDistributeType();
					window.location.reload();
				}else{
					alert("拆分成功！"); 
					$("#myModalsa").modal("hide");
					window.location.reload();
				}
			}else{ alert("拆分失败！"+data.message);  }
		},complete: function() {  },
		error : function(errors) {
		}
	});
}
/* 取消关联案件页面  **/
function closef(){ 
	$("#myModalsa").modal("hide");
	if(distriType && undefined != urlType && '' != urlType){
		caseDistributeType();
	}else{
		if(undefined != urlType && '' != urlType ){ 
			window.open(urlType);
			}
	}
}
	
/* 查询案件基本信息   **/
function caseDetail(){ window.open("${ctx}/case/caseDetail?caseId="+$("#pkId").val()); }
/* 查询可关联案件列表   **/
function changeTaskAssignee(page,propertyCode){
	var data = {};
    if(!page) {data.page = 1;} else {data.page = page;}  
    data.propertyCode=propertyCode;
    data.inputType=$("#divCaseName").val();
    
    var callback = $("#myModalsa").attr("callback");
	if("backCaseMERGE"==callback){ data.queryId="queryQfCastListListdiv";
								   data.caseCode=$("#caseCode_").val(); 
							}else{ data.queryId="queryGlCastListListdiv";}
    
	
   	data.rows = 5;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/quickGrid/findPage" ,
		dataType : "json",
		data : data,
		beforeSend:function(){},
		success : function(data) {
			if(data.page>0){
				data.ctx = ctx;
		    	var tsAwardBaseList= template('queryCastListItemList' , data);
		        $("#taskListf").empty();
		        $("#taskListf").html(tsAwardBaseList);
		        initpagef(data.total,data.pagesize,data.page, data.records);
		        $("#myModalsa").modal("show");
			}else{
				
				var callback = $("#myModalsa").attr("callback");
				if("backCaseMERGE"==callback){
					alert("没有找到可以拆分的案件！");
				}else{
					
					if(distriType && undefined != urlType && '' != urlType){
					}else{ if(undefined != urlType && '' != urlType ){  }else{ alert("没有找到可以合流的案件！");} }
				}
				closef();
			}
		},complete: function() { },
		error : function(errors) { }
	});
}
/* 查询按钮方法  **/
function searchButtonClick(){changeTaskAssignee(1);}
/* 分页   **/
function initpagef(totalCount,pageSize,currentPage,records) {
	var currentTotalstrong=$('#currentTotalPagef').find('strong');
	if(totalCount>1500){ totalCount = 1500; }
	if (totalCount<1 || pageSize<1 || currentPage<1) {
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
			changeTaskAssignee(page,$("#propertyCode").val());
	    }
	});
}

function clickCallback(data){
	var callback = $("#myModalsa").attr("callback");
	if(callback){
		var funCallback = eval(callback);
		if($.isFunction(funCallback)){
			funCallback(data);
		}
	}
}
/** 关联案件响应事件 distriType为true是从分配页面过来的 **/
function showGlDiv(callback,pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,propertyCode,inputType,urlType_,distriType_){
	urlType = urlType_;
	distriType = distriType_;
	$("#myModalsa").attr("callback",callback);
	$("#propertyCode").val(propertyCode);  
	$("#caseCode_").val(caseCode); 
	init(pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,inputType);
	//if(distriType_){}else{
	//$("#myModalsa").modal("show");/**显示 div**/}
	/**查询方法**/
	changeTaskAssignee(1,propertyCode); 
	showButton();
}
function showButton(){
	$("#mergeId").remove();
	$("#qfId").remove();
	$("#closeId").remove();
	$("#closeId_").remove();
	var str = '<button type="button" name ="mergeId" id ="mergeId" class="btn btn-success mr5" onclick="merge()">合流</button> <button type="button" name ="closeId" id ="closeId" class="btn btn-grey"  onclick="closef()">取消</button> ';
	var str1 = '<button type="button"  name ="qfId" id ="qfId"  class="btn btn-success mr5" onclick="qfMerge()">拆分 </button> <button type="button" name ="closeId_" id ="closeId_" class="btn btn-grey"  onclick="closef()">取消</button> ';
	var callback = $("#myModalsa").attr("callback");
	if("backCaseMERGE"==callback){
		$("#buttonDiv").append(str1);
	}else{$("#buttonDiv").append(str);}
}

</script>