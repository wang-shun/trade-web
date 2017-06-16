
/**
 * 功能：流失率
 * 编写人：hjf
 * 编写时间：2017年6月9日11:13:18
 * 逻辑：
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
	var belongMonth = getBlongMonth();
	var participant = $("#userId").val();
	var data = {
			argu_belongMonth : belongMonth,
			argu_participant:participant,
			queryId:"managerStepThree",
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
        	console.log(data);
      	      var myTaskListf = template('template_myTaskListf' , data);
      	      $("#myTaskListf").empty();
   			  $("#myTaskListf").html(myTaskListf);
   			  initpage(data.total,data.pagesize,data.page, data.records);
              $.unblockUI();  
        },
        error: function (e, jqxhr, settings, exception) { $.unblockUI();    } 
  });
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

//获取计件年月信息
function getBlongMonth(){
	var bm = "";	
	//方式一
	var belongMonth =  $.trim($("#belongMonth",window.parent.document).val());
	//方式二
	//var belongMonth1 = parent.document.getElementById("belongMonth").value;
  if(belongMonth =="" || belongMonth == null || belongMonth == undefined){
  	bm == null;
  }else{
  	bm = belongMonth + "-01";
  }
  return bm;
}
