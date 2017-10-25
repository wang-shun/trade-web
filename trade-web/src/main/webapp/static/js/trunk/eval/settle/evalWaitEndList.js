/*加载待结算评估案件*/
$(document).ready(function(){
	
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	
	var data = getQueryParams(1);
	
    aist.wrap(data);
	//添加排序------------
	reloadGrid(data);
	
	//获取评估公司
	getEvaFinOrg('finOrgId');
});

var ctx = $("#ctx").val();
/**
 * 获取评估公司 格式化
 * @param finOrgId
 */
function getEvaFinOrg(finOrgId){
	var url = "/manage/queryEvaCompany";
	$.ajax({
		async: true,
		type:'POST',
		url:ctx+url,
		dataType:'json',
		success:function(data){
			var html = '<option value="" selected>请选择</option>';
			if(data != null){
				$.each(data,function(i,item){
					html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
				});
			}					
			$('#'+finOrgId).empty();
			$('#'+finOrgId).append(html);
		},
		error : function(errors) {
		}
	});
}


/*条件查询*/
$('#searchButton').click(function(){
    searchMethod(1)
});
//search
function searchMethod(page){
	$("#batchappro").attr("disabled", true);
	$("#caseAdd").attr("disabled", true);
	var data = getQueryParams(page);
    aist.wrap(data);
	reloadGrid(data);
}

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	//案件状态
	var status = $("#caseStatus  option:selected").val().trim();
//	if(caseStatus==""){
//		caseStatus=null;
//	}
	//结算费用
	var settleFee = $("#endfee").val();
//	if(endFee==""){
//		endFee=null;
//	}
	
	//费用调整类型
	var feeChangeReason = $('#costUpdateType option:selected').val();
//	if(costUpdateType==""){
//		costUpdateType=null;
//	}
	//贷款权证
	//var loadWarrant = $('#loadWarrant').val();
//	if(loadWarrant==""){
//		loadWarrant=null;
//	}
	//评估公司
	var finOrgID = $('#finOrgId').val();
//	if(evalOrg==""){
//		evalOrg=null;
//	}
	var params = {
		search_status : status,
		search_settleFee : settleFee,
		//search_loadWarrant : loadWarrant,
		search_feeChangeReason : feeChangeReason,
		search_finOrgID : finOrgID,
		queryId : 'queryEvalWaitEndList',
		rows : 10,
	    page : page
	};
	
	return params;	
}

/*获取评估待结算的案件列表*/
function reloadGrid(data) {
	var ctx = $("#ctx").val();
	
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
        	$.unblockUI();
        	var myCaseEvalList = template('evalWaitAccountList' , data);
        	$("#t_body_data_contents").empty();
        	$("#t_body_data_contents").html(myCaseEvalList);
			// 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
//            $('.demo-left').poshytip({
//    			className: 'tip-twitter',
//    			showTimeout: 1,
//    			alignTo: 'target',
//    			alignX: 'left',
//    			alignY: 'center',
//    			offsetX: 8,
//    			offsetY: 5,
//    		});
//
//    		//top
//    		$('.demo-top').poshytip({
//    			className: 'tip-twitter',
//    			showTimeout: 1,
//    			alignTo: 'target',
//    			alignX: 'center',
//    			alignY: 'top',
//    			offsetX: 8,
//    			offsetY: 5,
//    		});
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}

/*分页栏*/
function initpage(totalCount,pageSize,currentPage,records) {
	
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1){
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);
	
	
	$("#pageBar").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			//reloadGrid(page);
			searchMethod(page);
	    }
	});
}


/*全选框绑定全选/全不选属性*/
$('#checkAllNot').click(function(){
	var my_checkboxes = $('input[name="my_checkbox"]');
	if($(this).prop('checked')){
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',true);
		}
		$("#batchappro").attr("disabled", false);
		$("#caseAdd").attr("disabled", false);
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
		}
		$("#batchappro").attr("disabled", true);
		$("#caseAdd").attr("disabled", true);
	}
});

/*单选框*/
/*function _checkbox(){
	var my_checkboxes = $('input[name="my_checkbox"]');
	var flag =false;
	var count=0;
	$.each(my_checkboxes, function(j, item){
		if($('input[name="my_checkbox"]:eq('+j+')').prop('checked')){
			flag=true;
			++count;
		}
	});
	if(flag){
		$("#batchappro").attr("disabled", false);
		$("#caseAdd").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
			
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#batchappro").attr("disabled", true);
		$("#caseAdd").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
}*/

/*单选框*/
function _checkbox(){
	var my_checkboxes = $('input[name="my_checkbox"]');
	var flag =false;
	var count=0;
	$.each(my_checkboxes, function(j, item){
		if($('input[name="my_checkbox"]:eq('+j+')').prop('checked')){
			flag=true;
			++count;
		}
	});
	if(flag){
		$("#batchappro").attr("disabled", false);
		$("#caseAdd").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#batchappro").attr("disabled", true);
		$("#caseAdd").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
}

/**
 * 导入EXCEL表格(2013之前)
 * @returns
 */

/*$('#exportBtn').click(function exportExcel(){
	var data1=packgeData();
//	var data1=getQueryParams(page);
	$.exportExcel({
	ctx : $("#ctx").val(),
	colomns : [PROPERTY_ADDR,FEE_CHANGE_REASON,FIN_ORG_ID,APPLY_DATE,ISSUE_DATE,EVAL_REAL_CHARGES,SETTLE_FEE,REJECT_CAUSE,STATUS],//<table-row>里面的name
	data:data1
	});
});

function packgeData(page){
	var data1 = getQueryParams(page);
	return data1;
}*/

/**
 * 新增结算单
 */

function caseAdd(){
	var ids = new Array();
	var checkeds=$('input[name="my_checkbox"]:checked');
	$.each(checkeds, function(i, items){
		var $td = $(items).parent();
		var id = $('input[name="case_code"]',$td).val();
		ids.push(id);
	});
	var ctx = $("#ctx").val();
	window.location.href = ctx + "/eval/settle/newEndForm?caseCodes="+ids;
	
}


/**
 * 批量审批
 */
$('#batchappro').click(function() {
	var ids = new Array();
	var checkeds=$('input[name="my_checkbox"]:checked');
	$.each(checkeds, function(i, items){
		var $td = $(items).parent();
		var id = $('input[name="case_code"]',$td).val();
		ids.push(id);
	});
	var ctx = $("#ctx").val();
	
	window.wxc.confirm("确定发起批量审批吗？",{"wxcOk":function(){
		window.location.href = ctx + "/eval/settle/majorAppro?caseCodes="+ids;
	}});
});

/**
 * 导出excel（2013年之后版本）
 */

	function exportToExcel() {
 		var params = {};
 		params.search_status = $("#caseStatus  option:selected").val().trim();
 		params.search_settleFee = $("#endfee").val();
 		params.search_feeChangeReason = $('#costUpdateType option:selected').val();
 		params.search_finOrgID = $('#finOrgId').val();

 		var displayColomn = new Array;
 		displayColomn.push('caseCode');
 		displayColomn.push('PROPERTY_ADDR');
 		displayColomn.push('FEE_CHANGE_REASON');
		displayColomn.push('EVA_COMPANY');
		displayColomn.push('APPLY_DATE');
 		displayColomn.push('ISSUE_DATE');
 		displayColomn.push('EVAL_REAL_CHARGES');
 		displayColomn.push('SETTLE_FEE');
 		displayColomn.push('REJECT_CAUSE');
 		displayColomn.push('STATUS');
 		
 		var colomns = '&colomns=' + displayColomn;
 		var url = "/quickGrid/findPage?xlsx&";
 		var ctx = $("#ctx").val();
 		var queryId = '&queryId=queryEvalWaitEndList';
 		url = ctx + url + jQuery.param(params) + queryId  + colomns;
 		$('#excelForm').attr('action', url);
 		$('#excelForm').method="post" ;
 		$('#excelForm').submit();  
    }







/**
 * 案件分配
 * @param index
 */
/*function distributeCase(index){
		var userName =$("#userName_"+index).val();
		var mobile=$("#mobile_"+index).val();
		var checkeds=$('input[name="my_checkbox"]:checked');
		var ids = new Array();
		$.each(checkeds, function(i, items){
			var id = $('input[name="my_checkbox"]:checked:eq('+i+')').next('input[name="case_code"]').val();
			ids.push(id);
		});
		var userId =$("#user_"+index).val();
		
		var url = "/case/isTransferOtherDistrict";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params='&userId='+userId+'&caseCodes='+ids;
		
		$.ajax({	
			cache : false,
			async: false,
			type : "POST",
			url : url,
			dataType : "json",
			timeout: 10000,
		    data : params, 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },  
            complete: function() {  
                $.unblockUI();   
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
            
			success : function(data) {
				 var confrimMsg = '';
				 if(data.content == false){
					 confrimMsg = "您是否确认分配给"+userName+"?"+"业务员"+mobile;
				 } else {
					 confrimMsg = "案件所属区域与主办或合作对象不匹配,您是否确认分配给"+userName+"?";
				 }
				 
				 window.wxc.confirm(confrimMsg,{"wxcOk":function(){
		            $("#myCaseList").html("");
					var url = "/case/bindCaseDist";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var params='&userId='+userId+'&caseCodes='+ids;
					 $.ajax({
							cache : false,
							async: true,
							type : "POST",
							url : url,
							dataType : "json",
							timeout: 10000,
						    data : params, 
						    beforeSend:function(){  
								$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
								$(".blockOverlay").css({'z-index':'9998'});
				            },  
				          complete: function() {  
				                $.unblockUI();   
				                if(status=='timeout'){//超时,status还有success,error等值的情况
					          	  Modal.alert(
								  {
								    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
								  });
						  		 $(".btn-primary").one("click",function(){
						  				parent.$.fancybox.close();
						  			});	 
						                }
						            } , 
				            
							success : function(data) {
								if(data.success){
									window.wxc.success("分配成功",{"wxcOk":function(){
										$('#modal-form').modal("hide");
										//jqGrid reload
										reloadGrid(1);
									}});
								}else{
									window.wxc.error(data.message);
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
							}
						});
				 }});
			}
		}); 
}*/
/**
 * 案件转组
 * @param index
 */
/*function changeCaseTeam(){
	//var orgName =$('input[name="teamRadio"]:checked').parent().text();
	var orgName =$('select[name="yuTeamCode"]').find("option:selected").text();
	
	window.wxc.confirm("您是否确认分配给"+orgName+"?",{"wxcOk":function(){
    	//var orgId =$('input[name="teamRadio"]:checked').val();
		var orgId =$('select[name="yuTeamCode"]').val();
		var url = "/case/bindCaseTeam";
		var ctx = $("#ctx").val();
		url = ctx + url;
		//var caseCodes=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		//var params='&orgId='+orgId+'&caseCodes='+caseCodes;
		
		var caseInfoList = new Array();
		var checkeds=$('input[name="my_checkbox"]:checked');
		$.each(checkeds, function(i, items){
			var $myCheckbox = $('input[name="my_checkbox"]:checked:eq('+i+')');
			var $caseCode = $myCheckbox.next('input[name="case_code"]');
			var $grpCode  = $caseCode.next('input[name="yu_team_code"]');
			var $leadingProcessId  = $grpCode.next('input[name="leading_process_id"]');
			
			var toCaseInfo = {
				caseCode : $caseCode.val(),
				grpCode : $grpCode.val(),
				requireProcessorId:$leadingProcessId.val()
			}
			caseInfoList.push(toCaseInfo);
			
		});
		
		var teamTransferVO = {
		   caseInfoList	: caseInfoList,
		   orgId : orgId
		}
		teamTransferVO = $.toJSON(teamTransferVO);
		$.ajax({
			cache : false,
			async:true,
			type : "POST",
			url : url,
			dataType : "json",
			contentType: "application/json; charset=utf-8" ,
			timeout: 10000,
		    data : teamTransferVO, 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },  
            complete: function() {  
            	$("#myCaseList").html("");
                $.unblockUI();   
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
			success : function(data) {
				if(data.success){
					window.wxc.success("分配成功",{"wxcOk":function(){
						$('#team-modal-form').modal("hide");
						//jqGrid reload
						reloadGrid(1);
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}});
}*/