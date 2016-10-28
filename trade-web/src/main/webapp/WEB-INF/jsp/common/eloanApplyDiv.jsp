<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="modal inmodal in" id="myModalsa" tabindex="-1" role="dialog"  aria-hidden="true" style="display: none">
                           <div class="modal-dialog" style="width: 1070px;">
                               <div class="modal-content animated fadeIn apply_box info_box">
                                   <form action="" class="form_list clearfix" >
                                       <div class="modal_title">
                                           e+产品关联案件
                                       </div>
                                       <div class="line">
					                        <div class="form_content">
					                            <label class="control-label mr10">
					                                   	 案件编码
					                            </label>
					                            <input class="teamcode input_type" value="" placeholder="请输入" id="caseCodet" name="caseCodet" >
					                        </div>
					                        <div class="form_content">
					                            <label class="control-label sign_left">
					                                   	 产证地址
					                            </label>
					                            <input class="input_type" value="" placeholder="请输入" style="width:435px;" id="propertyAddrt" name="propertyAddrt" >
					                        </div>
					                    </div>
				                    	<div class="line">
					                        <div class="form_content">
					                            <label class="control-label mr10">
					                                     	上家姓名
					                            </label>
					                            <input class="teamcode input_type" value="" placeholder="请输入" id="caseNamet" name="caseNamet" >
					                        </div>
					                        <div class="form_content space">
					                            <div class="add_btn">
					                                <button type="button" class="btn btn-success" id="searchButton" onclick="searchButtonClick()" >
					                                <i class="icon iconfont"></i>
					                                   	 查询
					                                </button>
					                            </div>
					                        </div>
				                    	</div>
                                   </form>
                                   <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong">
                                           &#xe60a;
                                       </i></button>
                                   <div class="apply_table">
                                   <table class="table table_blue mt20 table-striped table-bordered table-hover customerinfo" id="editable" name="editable">
                                       <thead>
                                           <tr>

                                               <th>
                                                   案件编码<!-- <span>案件编码</span><a href="#"><i class="fa fa-sort-desc fa_down"></i></a> -->
                                               </th>
                                               <th>
                                                  	 产证地址
                                               </th>
                                               <th>
                                                  	 经办人
                                               </th>
                                               <th>
                                                   	上家
                                               </th>
                                               <th>
                                                   	下家
                                               </th>
                                               <th>
                                                  	 操作
                                               </th>
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
</div>
<script id="queryCastListItemList" type= "text/html">
                          {{each rows as item index}}
	<tr>
    <td>
        <p class="big">
            <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
               <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
            </a>
        </p>
        <p>
            <i class="tag_sign">c</i>
             {{item.ctmCode}}
        </p>
    </td>
    <td>
        <p class="big">
       		<span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
        </p>
        <p class="tooltip-demo">
            <i class="salesman-icon">
            </i>
            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title="">
            	{{item.AGENT_NAME}}/{{item.AGENT_ORG_NAME}}
            </a>
        </p>
    </td>
    <td>
        <p class="name">
            <span>交易顾问：</span><a href="#" class="a_blue" id="modal_processorId{{index}}">{{item.PROCESSOR_ID}}</a>
        </p>
        <p class="name">
            <span>经纪人：</span><a href="#" class="a_blue" id="modal_agentName{{index}}">{{item.AGENT_NAME}}</a>
        </p>
    </td>
    <td class="center">
        <p class="big">
       		<span id="modal_seller{{index}}">{{item.SELLER}}</span>
        </p>
    </td>
    <td class="center">
        <p class="big">
      	   <span id="modal_buyer{{index}}"> {{item.BUYER}}</span>
        </p>
    </td>
    <td class="text-left">
        <button type="button" class="btn btn-success linkCase" name="linkCase" onclick="clickCallback({
PKID:{{item.PKID}},
CASE_CODE:'{{item.CASE_CODE}}',
ctmCode:'{{item.ctmCode}}',
PROPERTY_ADDR:'{{item.PROPERTY_ADDR}}',
AGENT_NAME:'{{item.AGENT_NAME}}',
AGENT_ORG_NAME:'{{item.AGENT_ORG_NAME}}',
PROCESSOR_ID:'{{item.PROCESSOR_ID}}',
AGENT_NAME:'{{item.AGENT_NAME}}',
SELLER:'{{item.SELLER}}',
BUYER:'{{item.BUYER}}'})" id="{{index}}" rep="{{index}}">
                          关联案件
        </button>
    </td>
</tr>
{{/each}}
	</script>
<script>
function changeTaskAssignee(page){
	
	var data = {};
	
	data.rows = 5;
     if(!page) {
    	 data.page = 1;
    } else {
    	data.page = page;
    }  
     
    var propertyAddr = $.trim($("#propertyAddrt").val());
   	var caseCode = $.trim($("#caseCodet").val());
   	var caseName = $.trim($("#caseNamet").val()); 
   	
    data.propertyAddr=propertyAddr;
    data.caseCode=caseCode;
   	data.sname=caseName; 
   	data.queryId="queryCastListItemListdiv";
   	
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/quickGrid/findPage" ,
		dataType : "json",
		data : data,
		beforeSend:function(){  
			var a=1;
         },
		success : function(data) {
			//console.log("数据"+JSON.stringify(data));
			data.ctx = ctx;
	    	var tsAwardBaseList= template('queryCastListItemList' , data);
	        $("#taskListf").empty();
	        $("#taskListf").html(tsAwardBaseList);
	        
	        initpagef(data.total,data.pagesize,data.page, data.records);
		},complete: function() { 
			var a=1;
		},
		error : function(errors) {
			var a=1;
		}
	});
}


function searchButtonClick(){
	changeTaskAssignee(1);
}

function initpagef(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPagef').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
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
			 //console.log(page);
			changeTaskAssignee(page);
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
//关联案件响应事件
function showGlDiv(callback){
	//查询方法
	changeTaskAssignee(1);
	$("#myModalsa").attr("callback",callback);
	//显示 div
	$("#myModalsa").modal("show");
}

//关联案件列表的响应事件
/* function backCase1(data){	

	alert(data.PKID);
	//div返回的数据结构
	/*data.PKID
	data.CASE_CODE
	data.ctmCode
	data.PROPERTY_ADDR
	data.AGENT_NAME
	data.AGENT_ORG_NAME
	data.PROCESSOR_ID
	data.AGENT_NAME
	data.SELLER
	data.BUYER */
	//隐藏div
	//$('#myModalsa').modal('hide');
/*} */
/* 
function backCase2(data){	
	alert(data.PKID);
	//div返回的数据结构
	/*data.PKID
	data.CASE_CODE
	data.ctmCode
	data.PROPERTY_ADDR
	data.AGENT_NAME
	data.AGENT_ORG_NAME
	data.PROCESSOR_ID
	data.AGENT_NAME
	data.SELLER
	data.BUYER */
	//隐藏div
	//$('#myModalsa').modal('hide');
	
/*} */

</script>