var startList=0;//判断是不是应该显示列表

$(document).ready(function() {
	
	aist.sortWrapper({
		reloadGrid : searchMethod
	}); 
	
	var data = getParams(1);
    aist.wrap(data);
    startList=0;
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
        	//console.log("Result==="+JSON.stringify(data));
      	  var caseForChangeList = template('template_caseForChangeList' , data);
      	    $("#caseForChangeList").empty();
      	    
      	     if(startList == 1){      	      
   			  $("#caseForChangeList").html(caseForChangeList);
   			  // 显示分页 
   			  	initpage(data.total,data.pagesize,data.page, data.records);
                 $('.demo-left').poshytip({
           			className: 'tip-twitter',
           			showTimeout: 1,
           			alignTo: 'target',
           			alignX: 'left',
           			alignY: 'center',
           			offsetX: 8,
           			offsetY: 5,
           		});

           		//top
           		$('.demo-top').poshytip({
           			className: 'tip-twitter',
           			showTimeout: 1,
           			alignTo: 'target',
           			alignX: 'center',
           			alignY: 'top',
           			offsetX: 8,
           			offsetY: 5,
           		});
           		startList=0;
      	     }else{
			 
      	     }
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
	var propertyAddr =  $.trim($('#propertyAddr').val());
	var caseCode = $.trim($("#caseCode").val());

	if(propertyAddr.length>0|| caseCode.length>0 ){
		startList=1;
	}else{
		startList=0;
	}
	var data = {
			search_propertyAddr: propertyAddr,
			search_caseCode:caseCode,			
			queryId : "queryCastListItemList",
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
