
/**
 * 功能：查询KPI列表
 * 编写人：hjf
 * 编写时间：2017年5月23日14:17:28
 * 逻辑：KPI
 * 版本：1.0
 */
$(document).ready(function() {
	searchMethod(1);
});
/**
 * 查询方法
 * @param page 页码
 */
function searchMethod(page){
	var data = getParams(page);
   // aist.wrap(data);
	reloadGrid(data);
}
$('#searchButton').click(function() {
	searchMethod(1);
});
/**
 * 查询参数封装方法
 * @param page
 * @returns data 
 */
function getParams(page) {
	if(!page) {
		page = 1;
	}
	var belongMonth = $("#belongMonthf").val()
	//var belongMonth = $.trim($("#belongMonth",window.parent.document).val());alert(belongMonth);
	if(null != belongMonth && "" != belongMonth){
		belongMonth=belongMonth+"-01 00:00:00.000";
	}
	var data = {
			search_teamId :$.trim( $('#yuCuiOriGrpId').val() ),
			search_participant :$.trim( $('#userName').val() ), 
			argu_belongMonth : belongMonth,
			queryId:"monthKpiList",
			rows : 10,
			page : page
		};
    return data; 
}


/**
 * 查询
 * @author hejf10
 * @date 2017年5月23日13:52:42
 */
function reloadGrid(data) {
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
      	      var myTaskList = template('template_myTaskList' , data);
      	      $("#myTaskList").empty();
   			  $("#myTaskList").html(myTaskList);
   			  initpage(data.total,data.pagesize,data.page, data.records);
              $.unblockUI();  
        },
        error: function (e, jqxhr, settings, exception) { $.unblockUI();    } 
  });
}


function test() {
	 var prCodeArray = new Array();
	 var pkidList = jQuery("#gridTable").jqGrid('getGridParam', 'selarrrow');
	 for(var i = 0;i<pkidList.length;i++){
		 var item=$("#gridTable").jqGrid('getRowData',pkidList[i]);
		 prCodeArray.push(item.PKID);
	 }
	 window.wxc.alert(prCodeArray);
}

/**
 * 分页功能
 * @param totalCount
 * @param pageSize
 * @param currentPage
 * @param records
 */
function initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){ totalCount = 1500; }
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
		first:'<i class="fa fa-step-backward"></i>',
		prev :'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			searchMethod(page);
	    }
	});
}
