/**
 * 询价待办任务
 * wbcaiyx
 */
$(document).ready(function() {
	

			var url = "/rapidQuery/findPage";
			var ctx = $("#ctx").val();
			url = ctx + url;
			
			/*var lamp1 = $("#Lamp1").val();
			var lamp2 = $("#Lamp2").val();
			var lamp3 = $("#Lamp3").val();*/
			// 初始化列表
			var data = {};
    	    data.queryId = "queryEvaPricingTaskList";
    	    data.rows = 10;
    	    data.page = 1;
			data.argu_sessionUserId = $("#userId").val();
    	    aist.wrap(data);
    		reloadGrid(data);
    		
			//ie
			if ($.support.msie) {
				$('input:radio').click(function () {
					this.blur();
					this.focus();
				});
			}; 
//			$('input[name="lampRadios"][value=0]').prop("checked", true);
			$('input[name="ownerRadios"][value=0]').prop("checked", true);
			//查询
			/*$('input:radio[name="lampRadios"]').change(function() {
				searchMethod();
			});*/
			$('input:radio[name="ownerRadios"]').click(function() {
				searchMethod();
			});
		});

//select控件
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
var ctx = $("#ctx").val();

//查询
$('#searchButton').click(function() {
	searchMethod();
});

/*var lamp1 = $("#Lamp1").val();
var lamp2 = $("#Lamp2").val();
var lamp3 = $("#Lamp3").val();*/


//search

function initData(page){

	//授权代办
	var ownerType = $('input[name="ownerRadios"]:checked').val();
	var allTypeFlag;
	//全部
	if(ownerType == 0){
		ownerType=null;
		//本身
	}else if(ownerType == 1){
		ownerType=null;
		allTypeFlag = 'true';
		//代办
	}else if(ownerType == 2){
		ownerType='pending';
	}

	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();

	var propertyAddr = "";
	var evaCode = "";

	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '1') {
			//产证地址
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			// 询价编号
			evaCode = inTextVal.trim();
		}
	}
	//询价公司
	var finOrgId = $('#finOrgId').val();
	//询价类型
	var evaType = $('#evaType').val(); 

	if(!page) {
		page = 1;
	}
	var params = {
			argu_sessionUserId : $("#userId").val(),
			search_evaCode : evaCode,
			search_propertyAddr : propertyAddr,
			search_ownerType : ownerType,			
			argu_allType : allTypeFlag,
			argu_finOrgId : finOrgId,
			argu_evaType : evaType,
			queryId : "queryEvaPricingTaskList",
			rows : 10,
			page : page
		};
	return params;
}

function searchMethod(page){
		var params = initData(page);
		aist.wrap(params);
		reloadGrid(params);

}

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
          $.unblockUI();
      	  data.ctx = ctx;
      	  var myTaskList = template('template_evaPricingTaskList' , data);
			  $("#evaPricingTaskList").empty();
			  $("#evaPricingTaskList").html(myTaskList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
              demoPoshytip();
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
	$(function(){
			
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

			
		});
	
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

function demoPoshytip(){
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




