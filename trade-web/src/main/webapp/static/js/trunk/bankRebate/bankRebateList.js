var ctx = $("#ctx").val();
//清空
$('#myCaseListCleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("#caseStatus").val("");
	$("#applyer").val("");
	$("#finOrgId").val("");
});

//Excel modal
function showExcelModal(){
	$('#excel-modal-form').modal("show");
}
// Excel 导入
function excelIn(){
    if(!checkFileTypeExcel()) return;
    var url = ctx+"/bankRebate/uploadExcelBankRebate";
    var formData = new FormData();
    formData.append("fileupload",$("#file")[0].files[0]);
    var data = $("#excelInForm").serializeArray();
    for(var i=0;i<data.length;i++){
    	var value = data[i].value;
    	if(!value || value.length==0){
    		if(i==0){
                window.wxc.error("请选择担保公司!");
                return;
			} else if(i==1){
                window.wxc.error("请填写返利总金额");
                return;
			}
		}else{
    		if(i==1&&isNaN(value)){
                window.wxc.error("请正确填写返利总金额");
                return;
			}else{
                formData.append(data[i].name,value);
			}
		}
	}
    $.ajax({
        async: false,
        type: "POST",
        url: url,
        dataType: "json",
        data: formData,
        processData : false,// 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        beforeSend: function () {
            $.blockUI({
                message: $("#salesLoading"),
                css: {
                    'border': 'none',
                    'z-index': '9999'
                }
            });
            $(".blockOverlay").css({
                'z-index': '9998'
            });
        },
        success: function (data) {
            $.unblockUI();
            if (data.success) {
                window.wxc.alert("导入成功",{"wxcOk":function(){
                    window.location.href = ctx + "/bankRebate/bankRebateList";
                }});
            } else {
                if (data.message) {
                    window.wxc.error("导入失败:<br/> " + data.message);
                }
            }
        },
        error: function () {
            window.wxc.error("提交信息出错");
        }
    });
}
function checkFileTypeExcel()
{
    var obj = $("#file");
    if(!obj || obj.val().length==0){
        window.wxc.error("请选择导入文件!");
        return false;
	}
	var fileName = obj.val();
    var FileExt= fileName.substr(fileName.lastIndexOf(".")).toLowerCase();
    var AllowExt=".xls .xlsx";
    //判断文件类型是否允许上传
    if(AllowExt.indexOf(FileExt)==-1) {
        window.wxc.error("\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt);
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
function searchMethod(){
    searchMethod(1);
}
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
//模板下载
function showExcelIn(){
    window.location.href = ctx + "/bankRebate/exportMatrixLeaderSheet"
}
/**
 * 提交银行返利申请
 * @param compId
 */
function submitBankRebate(compId){
    window.wxc.confirm("确定提交到财务审批？",{"wxcOk":function(){
        var url = ctx + "/bankRebate/submitCcai";
        $.ajax({
            async: false,
            type: "POST",
            url: url,
            dataType: "json",
            data: {guaranteeCompId:compId},
            beforeSend: function () {
                $.blockUI({
                    message: $("#salesLoading"),
                    css: {
                        'border': 'none',
                        'z-index': '9999'
                    }
                });
                $(".blockOverlay").css({
                    'z-index': '9998'
                });
            },
            success: function (data) {
                $.unblockUI();
                if (data.success) {
                    window.wxc.alert("提交成功",{"wxcOk":function(){
                        var ctx = $("#ctx").val();
                        window.location.href = ctx + "/bankRebate/bankRebateList";
                    }});
                } else {
                    if (data.message) {
                        window.wxc.alert("提交失败:<br/>" + data.message);
                    }
                }
            },
            error: function () {
                $.unblockUI();
                window.wxc.alert("提交信息出错");
            }
        });
    }});
}