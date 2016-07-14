$(document).ready(function() {
					cleanForm();
					//基本信息等高
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var queryOrgFlag = $("#queryOrgFlag").val();
					var isAdminFlag = $("#isAdminFlag").val();
					var queryOrgs = $("#queryOrgs").val();
					var arguUserId=null;
					if(queryOrgFlag == 'true'){
						arguUserId=null;
						if(isAdminFlag == 'true'){
							queryOrgs=null;
						}
					}else{
						queryOrgs= null;
						arguUserId="yes";
					}

					var orgArray = queryOrgs==null?null:queryOrgs.split(",");
					// 初始化列表
					var data = {};
		    	    data.queryId = "queryRedGreenTaskDetailList";
		    	    data.rows = 12;
		    	    data.page = 1;
		    		reloadGrid(data);
				
				});

// select控件
var config = {
	'.chosen-select' : {},
	'.chosen-select-deselect' : {
		allow_single_deselect : true
	},
	'.chosen-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chosen-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chosen-select-width' : {
		width : "95%"
	}
};

for ( var selector in config) {
	$(selector).chosen(config[selector]);
};

// 日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

 //查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	
	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	params.queryId = "queryRedGreenTaskDetailList";
	reloadGrid(params);
	

};

function reloadGrid(data) {
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	
	var organId = $("#organId").val();
	var colourId = $("#colourId").val();
	var prApplyTime = $("#prApplyTime").val();
	
	
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}
	
	var start = $('#dtBegin_0').val();
	if(start&&start!=''){
		prApplyTime = start;
	}
	

	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
    data.argu_queryorgs = orgArray;
    
    data.organId = organId;
    data.colourId = colourId;
	
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
      	  data.ctx = ctx;
      	  var redgreenTaskDetailList = template('template_redgreenTaskDetailList' , data);
			  $("#redgreenTaskDetailList").empty();
			  $("#redgreenTaskDetailList").html(redgreenTaskDetailList);
			  // 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}



function initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
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
			 //console.log(page);
			searchMethod(page);
	    }
	});
}


function getParamsValue() {
	
	var start = $('#dtBegin_0').val();
	//设置查询参数
	var params = {
			prApplyTime : start
	};
	return params;
}

function exportToExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	//excel导出列
	var displayColomn = new Array;
	displayColomn.push('orgName1');
	displayColomn.push('realName1');
	displayColomn.push('orgName2');
	displayColomn.push('realName2');
	displayColomn.push('color1');
	displayColomn.push('CASE_CODE');
	displayColomn.push('TASKNAME');
	displayColomn.push('PROPERTY_ADDR');
	displayColomn.push('REAL_NAME');
	
	displayColomn.push('EST_PART_TIME');
	displayColomn.push('RPROPERTY_ADDR');
	displayColomn.push('IMPORTTIME');

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?'':queryOrgs.split(",");

	var argu_idflag = '&argu_idflag='+arguUserId;
	
	if(arguUserId==null)argu_idflag='&argu_idflag=';
	var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
	if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
	var params = getParamsValue();
	
	var organId = $("#organId").val();
	/*var start = $('#dtBegin_0').val();
	if(start&&start!=''){
		prApplyTime = start;
	}*/
	params.organId = organId;
	/*params.prApplyTime = prApplyTime;*/
	
	var queryId = '&queryId=queryRedGreenTaskExcelItemList';
	var colomns = '&colomns=' + displayColomn;
	
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	
	$('#excelForm').attr('action', url);
	
	$('#excelForm').method="post" ;
	$('#excelForm').submit();

}



// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
}

//清空
$('#cleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("select").val("");
});

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#teamCode").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
		
		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);
	}else{
		$("#teamCode").val("");
		$("#yuCuiOriGrpId").val("");
	}
}

