
$(document).ready(function() {
	
	var data = getParams(1);
    aist.wrap(data);
	reloadGrid(data);
});

function reloadGrid(data) {
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
        	console.log("数据"+JSON.stringify(data));
      	  var myTaskList = template('template_myTaskList' , data);
			  $("#myTaskList").empty();
			  $("#myTaskList").html(myTaskList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
              $.unblockUI(); 
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
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			 //console.log(page);
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
//search
function searchMethod(page){
	var data = getParams(page);
    aist.wrap(data);
	reloadGrid(data);
}
//查询
 $('#searchButton').click(function() {
	searchMethod(1);
}); 

function getParams(page) {
	if(!page) {
		page = 1;
	}
	var propertyAddr = $('#caseAddress').val();
	var caseCode = $("#caseCode").val();
	var ctmCode = $("#ctmCode").val();
	var data = {
			search_propertyAddr: propertyAddr,
			search_caseCode:caseCode,
			search_ctmCode:ctmCode,
			queryId : "queryCastTrackingListItemList",
			rows : 10,
			page : page
		};
    return data; 
}



function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

































