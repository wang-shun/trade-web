var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

$(document).ready(function() {		
	
	//初始化数据
    reloadGrid();
    getPersonBonusTotal();
 	// 查询
	$('#caseDetailsSearch').click(function() {
		reloadGrid();
		getPersonBonusTotal();
	});
	
	//展开、收起查看详细信息
	$(document).on("click",".expand",function(){
		var id = this.id;
			  	if($(this).html() == "展开") {
				  	$(this).html("收起");
				  	//发出请求
				    var data = {};
		    	    // data.queryId = "tsAwardBaseDetailList";//全部
		    	    data.queryId = "tsMyAwardBaseDetailList";//个人的
		    	    data.rows = 58;
		    	    data.page = 1;
		    	    data.search_caseCode = id;
		    	    data.argu_belongMonth = getBlongMonth();
		    	   
		    		$.ajax({
		    			  async: false,
		    	          url:ctx+ "/quickGrid/findPage" ,
		    	          method: "post",
		    	          dataType: "json",
		    	          data: data,
		    	          success: function(data){
		    	        	  var tsAwardSrvList= template('tsAwardSrvList' , data);
		    				  $("#toggle"+id).empty();
		    				  $("#toggle"+id).html(tsAwardSrvList);
		    	          }
		    	     });
 			  } else {
 				  $(this).html("展开");
 			  }
 			  $("#toggle"+id).toggle();
	});

});


function reloadGrid(bm) {	
	if(!bm){		
		bm = getBlongMonth();	
	}else{		
		bm = bm.format('yyyy-MM-dd');
	}
	
	var data1 = {};
    //data1.queryId = "tsAwardBaseList";
    data1.queryId = "tsMyAwardBaseList";
    data1.rows = 12;
    data1.page = 1;
    data1.argu_belongMonth = bm;
    data1.argu_caseCode = $.trim($("#caseCode").val());
    data1.argu_propertyAddr = $("#propertyAddr").val();
    
    var data2 = {
    	belongMonth : bm
    }
    BonusList.init(ctx,data1,data2);

}

function goPage(page) {
	var bm = getBlongMonth();		
	var data1 = {};
    //data1.queryId = "tsAwardBaseList";
    data1.queryId = "tsMyAwardBaseList";
    data1.rows = 12;
    data1.page = page;
    data1.argu_caseCode = $("#caseCode").val();
    data1.argu_propertyAddr = $("#propertyAddr").val();
    data1.argu_belongMonth = bm;
    
    var data2 = {
    	belongMonth : bm
    }
    BonusList.init(ctx,data1,data2);
}

var BonusList = function () {
	 return {
		init : function (_ctx,data1,data2) {
		  ctx = _ctx;
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

		}
	 };
}();


//分页
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
		first : '<i class="fa fa-step-backward"></i>',
		prev : '<i class="fa fa-chevron-left"></i>',
		next : '<i class="fa fa-chevron-right"></i>',
		last : '<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {			
				goPage(page);
	    }
	});
}
//案件奖金汇总导出
function exportBonusExcelButton() {
	 $.exportExcel({
	    	ctx : ctx,
	    	queryId : 'tsAwardBaseList',
	    	colomns : ['CASE_CODE','PROPERTY_ADDR','GUOHU_TIME','CLOSE_TIME','BASE_CASE_AMOUNT'],
	    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : getBlongMonth()}
   }) 
}

//案件环节明细导出
function exportToExcel() {	
	$.exportExcel({
    	ctx : ctx,
    	queryId : 'tsMyAwardBaseDetailList',
    	colomns : ['CASE_CODE','PROPERTY_ADDR','ORG_NAME','PARTICIPANT','SRV_CODE','BASE_AMOUNT','SATISFACTION','SKPI_RATE','SRV_PART',
    	           'MKPI','MKPIV', 'COM_LS_RATE','COM_LS_KPI','SRV_PART_IN','KPI_RATE_SUM','AWARD_KPI_MONEY'],
    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : getBlongMonth(),sord:'AWARD_BASE.CASE_CODE'}
    }) 
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


//查询个人的计件奖金数量
function getPersonBonusTotal(){
	var data={belongM:getBlongMonth()};
	$.ajax({
			  async: true,
	          url:ctx+ "/award/getPersonBonusTotal" ,
	          method: "post",
	          dataType: "json",
	          data: data,
	          success: function(data){
				  $("#awardAmount").html("0.00");
				  if(data.success){
					 if(data.content!=null){
				  		$("#awardAmount").html(data.content);
					 }
				  }else{
					  window.wxc.error(data.message);
				  }
	          }
	     });
}

//清空查询条件
$('#caseDetailsClean').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');	
	$("input[name='belongMonth']").val('');	
});

//日期控件,只能选择月份
$('#datepicker_0').datepicker({
	format: 'yyyy-mm',  
    weekStart: 1,  
    autoclose: true,  
    startView: 'year',
    maxViewMode: 1,
    minViewMode:1,
	todayBtn : 'linked',
	language : 'zh-CN',
});