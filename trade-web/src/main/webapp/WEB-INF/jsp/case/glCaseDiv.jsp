<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<input type="hidden" name="pkId" id="pkId" />
<input type="hidden" name="propertyCode" id="propertyCode" />
<div id="myModalsa" class="modal inmodal in" id="malPopup">
       <div class="modal-dialog" style="width: 1070px;">
           <div class="modal-content animated fadeIn apply_box info_box">
               <div  class="form_list clearfix">
                   <div class="modal_title">
                       	自录案件
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
               <button type="button" class="close close_blue" data-dismiss="modal">
                   <i class="iconfont icon_rong">
                       &#xe60a;
                   </i>
               </button>
               <div class="apply_table">
                   <div class="form_list table-capital">
                           <div class="table-box">
                               <p class="pink"><i class="iconfont">&#xe653;</i> 系统检测到可能重复案件，请选择案件合并!</p>
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
                <div class="text-center mt10 mb30">
                    <button type="button" class="btn btn-success mr5" onclick="merge()">合流</button>
                    <button type="button" class="btn btn-grey"  onclick="closef()">取消</button>
                </div>
           </div>
       </div>
   </div>

<script id="queryCastListItemList" type= "text/html">
 {{each rows as item index}}
	<tr>
		<td>
			<p class="big">
				<input type="radio" value="{{item.pkId}}" status="{{item.status}}" id="mergePkid" name="mergePkid" />
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
/* 第一次进入关联页面对页面上的表头值进行设置  **/
function init(pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer){
	$("#pkId").val("");
	$("#pkId").val(pkId);
	$("#caseCode").html(caseCode);
	$("#propertyAddr").html(propertyAddr);
	$("#agentName").html(agentName);
	$("#agentPhone").html(agentPhone);
	$("#agentOrgName").html(agentOrgName);
	$("#seller").html(seller);
	$("#buyer").html(buyer);
}
/* 调用关联案件方法  **/
function merge(){
	if(undefined == $('input[name="mergePkid"]:checked').val()){alert("请选择一条案件！");return;}
    if(!confirm("确定申请合流案件吗！")){ return false; }
	var mergePkid = $('input[name="mergePkid"]:checked').val();
	var pkId = $("#pkId").val();
	var data = {};
	data.mergePkid= mergePkid;
	data.pkId= pkId;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/caseMerge/mergeCase",
		dataType : "json",
		data : data,
		beforeSend:function(){ },
		success : function(data) {
			if(data.success){ alert("合流申请成功！"); 
			$("#myModalsa").modal("hide");
			window.location.reload();}else{ alert("合流申请失败！"+data.message);  }
		},complete: function() {  },
		error : function(errors) {
		}
	});
}
/* 取消关联案件页面  **/
function closef(){ $("#myModalsa").modal("hide"); }
/* 查询案件基本信息   **/
function caseDetail(){ window.open("${ctx}/case/caseDetail?caseId="+$("#pkId").val()); }
/* 查询可关联案件列表   **/
function changeTaskAssignee(page,propertyCode){
	var data = {};
    if(!page) {data.page = 1;} else {data.page = page;}  
    data.propertyCode=propertyCode;
   	data.queryId="queryGlCastListListdiv";
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
			data.ctx = ctx;
	    	var tsAwardBaseList= template('queryCastListItemList' , data);
	        $("#taskListf").empty();
	        $("#taskListf").html(tsAwardBaseList);
	        initpagef(data.total,data.pagesize,data.page, data.records);
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
/** 关联案件响应事件 **/
function showGlDiv(callback,pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,propertyCode){
	init(pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer);
	/**查询方法**/
	changeTaskAssignee(1,propertyCode); 
	$("#myModalsa").attr("callback",callback);
	$("#propertyCode").val(propertyCode);  
	$("#myModalsa").modal("show");/**显示 div**/
}
</script>