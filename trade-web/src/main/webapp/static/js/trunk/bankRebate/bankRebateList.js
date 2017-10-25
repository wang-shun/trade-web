var ctx = $("#ctx").val();
//清空
$('#myCaseListCleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("#caseStatus").val("");
	$("#applyer").val("");
	$("#finOrgId").val("");
});

var excelInUrl = "";
//Excel modal
function showExcelModal(){
	excelInUrl = "/bankRebate/uploadExcelBankRebate?";
	$('#excel-modal-form').modal("show");
}
//Excel 导入
function excelIn(){
	if(!checkFileTypeExcel())return false;
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	var ctx = $("#ctx").val();
	$('#excelInForm').attr('action', ctx + excelInUrl);
	$('#excelInForm').method="post" ;
	$('#excelInForm').submit();
}

function checkFileTypeExcel()
{
	var obj = $("#file")[0];
    var ErrMsg="";
    var FileMsg="";
    var IsImg=false;
    if(obj.value=="")return false;
    var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
    var AllowExt=".xls .csv .xlsx .ods";
    //判断文件类型是否允许上传
    if(AllowExt!=0&&AllowExt.indexOf(FileExt)==-1) {
        ErrMsg="\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt;
        window.wxc.error(ErrMsg);
        return false;
  	}

	return true;
}
//新增按钮
$("#addNewCase").click(function(){
	window.location.href = ctx + "/bankRebate/newBankRebate";
});

/*条件查询*/
$('#searchButton').click(function(){
    searchMethod(1)
});

//search
function searchMethod(page){
	$("#deleteButton").attr("disabled", true);
	var data = getQueryParams(page);
    aist.wrap(data);
	reloadGrid(data);
}

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	//申请单状态
	var status = $("#caseStatus  option:selected").val().trim();

	//申请日期
	var applyStart = $("#dtBegin_0").val();
	var applyEnd = $("#dtEnd_0").val() ;
	if(applyStart.length>0){
        applyStart +=" 00:00:00.000";
	}
	if(applyEnd.length>0){
        applyEnd+=" 23:59:59.999"
	}
	//申请人
	var applyer = $('#applyer').val();


	//担保公司
	var finOrgID = $('#finOrgId').val();

	var params = {
		search_status : status,
		search_applyPerson : applyer,
		search_guaranteeCompany : finOrgID,
		argu_applyStart : applyStart,
		argu_applyEnd : applyEnd,
		queryId : 'bankRebate',
		rows : 10,
	    page : page
	};
	
	return params;	
}

/*获取银行返利案件列表*/
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
        	var myCaseEvalList = template('bankRebate' , data);
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
        first:'<i class="fa fa-step-backward"></i>',
        prev:'<i class="fa fa-chevron-left"></i>',
        next:'<i class="fa fa-chevron-right"></i>',
        last:'<i class="fa fa-step-forward"></i>',
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
		$("#deleteButton").attr("disabled", false);
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
		}
		$("#deleteButton").attr("disabled", true);
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
		$("#deleteButton").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#deleteButton").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
}

/**
 *批量删除
 */
$('#deleteButton').click(function() {
	window.wxc.confirm("确定删除吗？",{"wxcOk":function(){
		var ids = new Array();
		var checkeds=$('input[name="my_checkbox"]:checked');
		$.each(checkeds, function(i, items){
			var $td = $(items).parent();
			var id = $('input[name="guaranteeCompId"]',$td).val();
			ids.push(id);
		});
		var ctx = $("#ctx").val();
		window.location.href = ctx + "/bankRebate/deleteCompany?guaranteeCompId="+ids;
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
		displayColomn.push('FIN_ORG_ID');
		displayColomn.push('APPLY_DATE');
 		displayColomn.push('ISSUE_DATE');
 		displayColomn.push('EVAL_REAL_CHARGES');
 		displayColomn.push('SETTLE_FEE');
 		displayColomn.push('REJECT_CAUSE');
 		displayColomn.push('STATUS');
 		
 		var colomns = '&colomns=' + displayColomn;
 		var url = "/quickGrid/findPage?xlsx&";
 		var ctx = $("#ctx").val();
 		var queryId = '&queryId=bankRebate';
 		url = ctx + url + jQuery.param(params) + queryId  + colomns;
 		$('#excelForm').attr('action', url);
 		$('#excelForm').method="post" ;
 		$('#excelForm').submit();  
    }

	






