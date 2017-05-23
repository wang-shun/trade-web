
var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();
//获取初始化计件年月
var belongMonth =  $("#belongMonth").val();


$(document).ready(function() {	
	//初始化数据
    reloadGrid();
 	// 查询
	$('#caseDetailsSearch').click(function() {
		reloadGrid();
	});
	
	//展开、收起查看详细信息
	$(document).on("click",".expand",function(){
		var id = this.id;
			  	if($(this).html() == "展开") {
				  	$(this).html("收起");
				  	//发出请求
				    var data = {};
		    	    data.queryId = "tsAwardBaseDetailList";
		    	    data.rows = 58;
		    	    data.page = 1;
		    	    data.search_caseCode = id;
		    	 	data.argu_belongMonth = monthSel.getDate().format('yyyy-MM-dd');
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
	
	
	//TODO  暂时没有提交按钮
	//页面提交按钮，更新总表的状态
	$("#submitButton").click(function(){
		 layer.confirm('提交之后不可修改,是否确定提交？', {
			  type: 1,
        	  title: '提醒',
        	  shadeClose: true,
        	  shade: 0.8,
        	  area: ['20%', '15%'],	
			  btn: ['确定','取消'] //按钮
		}, function(index){
			$.ajax({
	    			  async: false,
	    			  url:ctx+ "/award/updateTsAwardKpiPayStatus" ,
	    	          method: "post",
	    	          dataType: "json",
	    	          data: {belongMonth:monthSel.getDate().format('yyyy-MM-dd')},
	    	          success: function(data){
	    	        	  if(data.success) {
	    	        		  $("#submitButton").attr("disabled",true);
	    	        		  layer.msg('绩效奖金生成成功', {icon: 1, time: 1000});
	    	        	  } else {
	    	        		  layer.msg('绩效奖金生成失败', {icon: 1, time: 1000});
	    	        	  }
	    	          }
	    	     });
		
			  layer.close(index);
			  
    	}, function(){
    	});
	 });
});


function reloadGrid(bm) {
	if(!bm){
		bm = belongMonth + "-01";
	}else{
		bm = bm.format('yyyy-MM-dd');
	}
	
	var data1 = {};
    data1.queryId = "tsAwardBaseList";
    data1.rows = 12;
    data1.page = 1;
    data1.argu_belongMonth = bm;
    data1.argu_caseCode = $("#caseCode").val();
    data1.argu_propertyAddr = $("#propertyAddr").val();
    
    var data2 = {
    	belongMonth : bm
    }
    BonusList.init(ctx,data1,data2);
    // 如果已经提交，则不能再次提交
    $.ajax({
		  async: true,
          url:ctx+ "/award/getTsAwardKpiPayByStatus" ,
          method: "post",
          dataType: "json",
          data: {belongMonth:bm},
          success: function(data){
        	  if(data.success && data.content != null) {
        		  $("#submitButton").attr("disabled",true);
        	  } else {
        		  $("#submitButton").removeAttr("disabled");
        	  }
          }
	});
}

function goPage(page) {
	var bm=monthSel.getDate().format('yyyy-MM-dd');	
	var data1 = {};
    data1.queryId = "tsAwardBaseList";
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

function exportToExcel() {
	$.exportExcel({
    	ctx : "${ctx}",
    	queryId : 'tsAwardBaseDetailList',
    	colomns : ['CASE_CODE','PROPERTY_ADDR','ORG_NAME','PARTICIPANT','SRV_CODE','BASE_AMOUNT','SATISFACTION','SKPI_RATE','SRV_PART',
    	           'MKPI','MKPIV', 'COM_LS_RATE','COM_LS_KPI','SRV_PART_IN','KPI_RATE_SUM','AWARD_KPI_MONEY'],
    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd'),sord:'AWARD_BASE.CASE_CODE'}
    }) 
 }

 function exportBonusExcelButton() {
	 $.exportExcel({
	    	ctx : "${ctx}",
	    	queryId : 'tsAwardBaseList',
	    	colomns : ['CASE_CODE','PROPERTY_ADDR','GUOHU_TIME','CLOSE_TIME','BASE_CASE_AMOUNT'],
	    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd')}
    }) 
 }

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
  	          url:ctx+ "/award/getTsAwardKpiPayByProperty" ,
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