/**
 * 功能：查询自建案件列表
 * 编写人：hjf
 * 编写时间：2017年4月13日9:44:16
 * 逻辑：根据登录用户查询出登录用户自建的所有自建案件
 * 版本：1.0
 */
$(document).ready(function() {
	searchMethod(1);
});
/**
 * 查询列表方法
 * @param 2017年4月13日9:45:34
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
              cssDivPoshytip();
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
/***
 *  回调刷新的方法
 */
function callback() {
	setTimeout('searchMethod()',1000); 
}
/**
 * 查询方法
 * @param page 页码
 */
function searchMethod(page){
	var data = getParams(page);
    aist.wrap(data);
	reloadGrid(data);
}
/**
 * 点击查询调用方法
 */
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
	var propertyAddr =  $.trim($('#caseAddress').val());
	var caseCode = $.trim($("#caseCode").val());
	var ctmCode = $.trim($("#ctmCode").val());
	var uId = $("#userId").val();
	var dtBegin_0 = $("#dtBegin_0").val();
	var dtEnd_0 =   $("#dtEnd_0").val();
	if(null != dtBegin_0 && "" != dtBegin_0){
		dtBegin_0=dtBegin_0+" 00:00:00";
	}
	if(null != dtEnd_0 && "" != dtEnd_0){
		dtEnd_0=dtEnd_0+" 23:59:59";
	}
	
	var data = {
			search_propertyAddr: propertyAddr,
			search_caseCode:caseCode,
			search_ctmCode:ctmCode,
			dtEnd_0:dtEnd_0,
			dtBegin_0:dtBegin_0,
			queryId : "queryNewWDCaseList",
			uId : uId,
			rows : 10,
			page : page
		};
    return data; 
}
/**
 * 鼠标点击文字title样式显示
 */
function cssDivPoshytip(){
	$('.demo-left').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'left',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
	});
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});
}
/**
 * 排序方法
 */
function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}
