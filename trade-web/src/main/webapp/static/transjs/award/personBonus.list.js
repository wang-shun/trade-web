var PersonBonus = function () {
	 return {
		init : function (_ctx,data1) {
		  ctx=_ctx;
		  $.ajax({
  			  async: true,
  	          url:ctx+ "/quickGrid/findPage" ,
  	          method: "post",
  	          dataType: "json",
  	          data: data1,
	  	      beforeSend: function () {  
  	        	 $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
  				 $(".blockOverlay").css({'z-index':'9998'});
	  	      },  
  	          success: function(data){
  	        	  $.unblockUI();
  	        	  var tsAwardBaseList= template('tsAwardBaseList' , data);
	                  $("#TsAwardBaseList").empty();
	                  $("#TsAwardBaseList").html(tsAwardBaseList);
	                  
	                 // 显示分页 
	                 initpage(data.total,data.pagesize,data.page, data.records);
  	          }
  	     });
  		
		}
	 };
}();

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
			goPage(page);
	    }
	});
}