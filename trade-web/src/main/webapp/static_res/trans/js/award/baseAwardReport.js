var BonusList = function () {
	 return {
		init : function (_ctx,data1,data2) {
		  ctx=_ctx;
		  $.ajax({
  			  async: false,
  	          url:ctx+ "/quickGrid/findPage" ,
  	          method: "post",
  	          dataType: "json",
  	          data: data1,
  	          success: function(data) {
	        	  var tsAwardBaseList= template('template_tsAwardBaseList' , data);
	              $("#tsAwardBaseList").empty();
	              $("#tsAwardBaseList").html(tsAwardBaseList);
	                  
	              // 显示分页 
	              initpage(data.total,data.pagesize,data.page, data.records);
  	          }
  	     });
		}
	 };
}();

function initpage(totalCount,pageSize,currentPage,records) {
	if(totalCount>1500) {
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
			goPage(page);
	    }
	});
}