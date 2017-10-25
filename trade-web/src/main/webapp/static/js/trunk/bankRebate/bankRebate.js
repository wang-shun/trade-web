//清空
$('#myCaseListCleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("#caseStatus").val("");
	$("#applyer").val("");
	$("#finOrgId").val("");
});

var ctx = $("#ctx").val();
var index = 1;
function addOrg() {
	var txt='<tr id="trId' + index + '"><td><input name="loanMoney"';
	txt +='	id="loanMoney" type="text" class="form-control input-one" placeholder="请在此输入成交编号">';
	txt += $("#loanMoney").html()
    txt += '</input></td>';
    txt +='<td >'
    txt+= '<a href="">查看成交</a></td>';
	txt +='<td ><aist:dict id="diyaType" name="diyaType" clazz=" select_control yuanwid " display="select" dictType="71015" defaultvalue="" /></td>';
	txt +='<td><input id="rebateMoney" name="loanMoney" value="" type="text" class="form-control input-one" placeholder="" value="" /></td>';
	txt +='<td><input id="warrantMoney" name="restMoney" value="" type="text" class="form-control input-one" placeholder="" value="${tailinsVo.restMoney } /></td>';
	txt +='<td><input id="businessMoney" name="" value="" type="text" class="form-control input-one" placeholder="" value="${tailinsVo.restMoney } /></td>';
	txt +='<td><a href="javascript:removeTr('+ index + ');">删除</a></td></tr></tbody>';
	$("#addInput").before(txt);
	$("#addMoneyLine").css("display","none");
	index++;
}

function removeTr(index) {
	$("#trId" + index).remove();
	$("#addMoneyLine").css("display","block");
	
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

	//结算费用
	var settleFee = $("#endfee").val();

	//费用调整类型
	var feeChangeReason = $('#costUpdateType option:selected').val();


	//评估公司
	var finOrgID = $('#finOrgId').val();

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
        	var myCaseEvalList = template('evalWaitAccountList' , data);
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
	
	window.location.href = ctx + "/eval/settle/majorAppro?caseCodes="+ids;
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
	var queryId = '&queryId=queryEvalWaitEndList';
	url = ctx + url + jQuery.param(params) + queryId  + colomns;
	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}
/**
 * 计算返利分成和合计
 */
function cal(){
    var total = parseFloat($('#rebateMoney').val());
    var count = 0;
    $('#info').find("tr:gt(0)").each(function(index,tr){
        if(tr.attr(id)=='add')  return true;//跳过add行的计算
        var rebateMoneyi = $(tr).find('td:eq(3)').find('input');
        var rebateWarranti = $(tr).find('td:eq(4)').find('input');
        var rebateBusinessi = $(tr).find('td:eq(5)').find('input');

        if(isNaN($(rebateMoneyi).val())){
            window.wxc.alert("请输入正确的返利金额！");
            return;
        }else{
            var rebateMoney = parseFloat($(rebateMoneyi).val());
            rebateWarranti.val((rebateMoney* 0.3).toFixed(2));
            rebateBusinessi.val((rebateMoney* 0.7).toFixed(2));
            count += rebateMoney;
        }
    });
    $("#enteringMoney").val(count.toFixed(2));
    $("#differenceMoney").val((total-count).toFixed(2));
}