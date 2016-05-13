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
  	          success: function(data){
  	        	  var tsAwardBaseList= template('tsAwardBaseList' , data);
	                  $("#TsAwardBaseList").empty();
	                  $("#TsAwardBaseList").html(tsAwardBaseList);
	                  
	                 // 显示分页 
	                 initpage(data.total,data.pagesize,data.page, data.records);
  	          }
  	     });
  		
		 $.ajax({
  			  async: true,
  	          url:ctx+ "/kpi/getTsAwardKpiPayByProperty" ,
  	          method: "post",
  	          dataType: "json",
  	          data: data2,
  	          success: function(data){
  	        	  //console.log(data);
  	        	  var d = data.content;
  	        	  if(!d || $.trim(d) === "") {
  	        		  $("#caseCount").html(0);
	    	        	  $("#userCount").html(0);
	    	        	  $("#awardAmount").html(0);
  	        	  } else {
  	        		  if(!d.caseCount || $.trim(d.caseCount) === ""){
  	        			  $("#caseCount").html(0);
  	        		  } else {
  	        			  $("#caseCount").html(d.caseCount);
  	        		  }
						  if(!d.userCount || $.trim(d.userCount) === ""){
							  $("#userCount").html(0);	    	        			  
						  } else {
							  $("#userCount").html(d.userCount);
						  }
						  if(!d.awardKpiSum || $.trim(d.awardKpiSum) === ""){
							  $("#awardAmount").html(0);
						  } else {
							  $("#awardAmount").html(d.awardKpiSum);
						  }
  	        	  }
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