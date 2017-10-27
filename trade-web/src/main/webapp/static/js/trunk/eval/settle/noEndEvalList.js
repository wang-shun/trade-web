/*加载待结算评估案件*/
$(document).ready(function(){
	/*加载排序查询组件*/
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
					html += '<option value="'+item.finOrgCode+'">'+item.finOrgName+'</option>';
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
	$("#renewbatEnd").attr("disabled", true);
	var data = getQueryParams(page);
    aist.wrap(data);
	reloadGrid(data);
}

//清空
$('#myCaseListCleanButton').click(function() {
	$("#noEnd").val("");
	$("#inTextVal").val('');
	$('#finOrgId').val("")
});

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	//无需结算原因
	var settleNotReason = $("#noEnd  option:selected").val().trim();
	//产证地址
	var propertyAddr = "";
	var caseCode =  "";
	var evalCode =  "";
	//产证地址,案件编号,评估单编号
	var inTextVal = $("#inTextVal").val();
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			caseCode = inTextVal.trim();
		} else if (inTextType == '1') {
			//产证地址
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			//评估单编号
			evalCode = inTextVal.trim();
		}
	}
	//评估公司
	var finOrgID = $('#finOrgId').val();
	var params = {
		search_settleNotReason : settleNotReason,
		search_propertyAddr : propertyAddr,
		search_caseCode : caseCode,
		search_evalCode : evalCode,
		search_finOrgID : finOrgID,
		queryId : 'evalNoAccount',
		rows : 10,
	    page : page
	};
	
	return params;	
}

/*获取评估无需结算的案件列表*/
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
        	var myCaseEvalList = template('evalNoListTemp' , data);
        	$("#t_body_data_contents").empty();
        	$("#t_body_data_contents").html(myCaseEvalList);
			// 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
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
		$("#renewbatEnd").attr("disabled", false);

	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
		}
		$("#renewbatEnd").attr("disabled", true);
	}
});

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
		$("#renewbatEnd").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#renewbatEnd").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
}

/**
 * 重新结算
 */
function reNewAccount(){
	var ids = new Array();
	var checkeds=$('input[name="my_checkbox"]:checked');
	$.each(checkeds, function(i, items){
		var $td = $(items).parent();
		var id = $('input[name="case_code"]',$td).val();
		ids.push(id);
	});
	var ctx = $("#ctx").val();
	 window.wxc.confirm('确定要重新结算？',{"wxcOk":function(){
		 window.location.href = ctx + "/eval/settle/reEvalEndList?caseCodes="+ids;
	 }});
	
}
/*function reNewAccount(){
	var checkeds=$('input[name="my_checkbox"]:checked');
	var ids = new Array();
	$.each(checkeds, function(i, items){
		var id = $('input[name="my_checkbox"]:checked:eq('+i+')').next('input[name="case_code"]').val();
		ids.push(id);
	});
	
	var url = "/eval/settle/evalEndList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var params='&caseCodes='+ids;
	
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
					if(data.success){
						window.wxc.success("分配成功",{"wxcOk":function(){
							$('#modal-form').modal("hide");
							//jqGrid reload
							
							reloadGrid(1);
							$("#checkAllNot").attr('checked',false);
							searchMethod(1);
						}});
					}else{
						window.wxc.error(data.message);
					}
				}
	}); 
}*/

/*function getMergeCount2(){
	
	var data = {};
	if($('input[name="my_checkbox"]:checked').length>=1){data.caseCode=$('input[name="my_checkbox"]:checked').val();};
	var url = "/eval/settle/reNewAccount";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 20000,
	    data : data, 
		success : function(data) {
			window.location.href = ctx+"/eval/settle/evalEndUpdate"
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}*/



