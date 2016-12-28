/**
 * 待办任务
 * wanggh
 */
$(document).ready(function() {
	

			var url = "/rapidQuery/findPage";
			var ctx = $("#ctx").val();
			url = ctx + url;
			
			var lamp1 = $("#Lamp1").val();
			var lamp2 = $("#Lamp2").val();
			var lamp3 = $("#Lamp3").val();
			// 初始化列表
			var data = {};
    	    data.queryId = "queryTaskListItemList";
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
			$('input[name="lampRadios"][value=0]').prop("checked", true);
			$('input[name="ownerRadios"][value=0]').prop("checked", true);
			//查询
			$('input:radio[name="lampRadios"]').change(function() {
				searchMethod();
			});
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
$('#orderByButton').click(function() {
	//延迟天数范围
	var minDateLamp=null;
	var maxDateLamp=null;
	var ownerType;
	var dateLamp = $('input[name="lampRadios"]:checked').val();
	if(dateLamp!=0){
		//红灯
		if(dateLamp == 1){
			minDateLamp = lamp3;
			//黄灯
		}else if(dateLamp == 2){
			minDateLamp = lamp2;
			maxDateLamp = lamp3;
			//绿灯
		}else if(dateLamp == 3){
			minDateLamp = lamp1;
			maxDateLamp = lamp2;
		}
	}
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
	var hVal = $('#inTextVal').attr('hVal');
	var guestName = "";
	var agentName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			// 经纪人姓名
			agentName = inTextVal.trim();
		}else if (inTextType == '3') {
			
			agentOrgName = inTextVal.trim();
		}
	}

	var params = {
			search_minDateLamp : minDateLamp,
			search_maxDateLamp : maxDateLamp,
			search_ownerType : ownerType,
			argu_guestname : guestName,
			search_agentName : agentName,
			search_agentOrgName : agentOrgName,
			search_propertyAddr : propertyAddr,
			argu_allType: allTypeFlag,
			queryId : "queryTaskListItemList",
			rows : 10,
			page : 1,
			sortname : "ID",
			sortorder : "ASC"
		};

		//jqGrid reload
	/*	$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 ,
			"sortname": "ID", // 表示用于排序的列名的参数名称 
			"sortorder": "ASC" // 表示采用的排序方式的参数名称 
		}).trigger('reloadGrid');*/
	 reloadGrid(params);
});
var lamp1 = $("#Lamp1").val();
var lamp2 = $("#Lamp2").val();
var lamp3 = $("#Lamp3").val();
/**
 * 红绿灯Formate
 * @param cellvalue
 * @returns {String}
 */
function dateLampFormatter(cellvalue) {

	var color='';
	if (cellvalue < lamp1||cellvalue==null)
		return "";
	if (cellvalue < lamp2) {
		color='green';
	} else if (cellvalue < lamp3) {
		color='yellow';
	} else {
		color='red';
	}
	var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
	outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
	outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
	outDiv+='</div>';
	return outDiv;
}

function isRedFormatter(cellvalue) {

	var reStr='无';
	if (cellvalue =='1')
		reStr='有';
	return reStr;
}
//search

function initData(page){
	var isSubscribeFilter = $('#isSubscribeFilter option:selected').val();
	if(isSubscribeFilter==null || isSubscribeFilter=='') {
		isSubscribeFilter = -1;
	}
	//延迟天数范围
	var minDateLamp=null;
	var maxDateLamp=null;
	var ownerType;
	var dateLamp = $('input[name="lampRadios"]:checked').val();
	if(dateLamp!=0){
		//红灯
		if(dateLamp == 1){
			minDateLamp = lamp3;
			//黄灯
		}else if(dateLamp == 2){
			minDateLamp = lamp2;
			maxDateLamp = lamp3;
			//绿灯
		}else if(dateLamp == 3){
			minDateLamp = lamp1;
			maxDateLamp = lamp2;
		}
	}
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
	var hVal = $('#inTextVal').attr('hVal');
	var guestName = "";
	var agentName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	var taskDfKey=$("#taskDfKey").val();
	// caseCode与ctmCode
	var caseCode =  "";
	var ctmCode = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			// 经纪人姓名
			agentName = inTextVal.trim();
		}else if (inTextType == '3') {
			agentOrgName = inTextVal.trim();
		}else if (inTextType == '4') {
			caseCode = inTextVal.trim();
		}else if (inTextType == '5') {
			ctmCode = inTextVal.trim();
		}
	}

	if(!page) {
		page = 1;
	}
	var params = {
			argu_sessionUserId : $("#userId").val(),
			argu_isSubscribeFilter : isSubscribeFilter,
			search_caseCode : caseCode,
			search_ctmCode : ctmCode,
			search_minDateLamp : minDateLamp,
			search_maxDateLamp : maxDateLamp,
			search_ownerType : ownerType,
			argu_guestname : guestName,
			search_agentName : agentName,
			search_agentOrgName : agentOrgName,
			search_propertyAddr : propertyAddr,
			argu_allType: allTypeFlag,
			search_taskDfKey:taskDfKey,
			queryId : "queryTaskListItemList",
			rows : 10,
			page : page
		};
	return params;
}

function searchMethod(page){
		var params = initData(page);
		aist.wrap(params);
		reloadGrid(params);

		//jqGrid reload
		/*$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 
		}).trigger('reloadGrid');*/
}

function reloadGrid(data) {
	$.ajax({
		async: false,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
      	  data.lamp1 = lamp1;
      	  data.lamp2 = lamp2;
      	  data.lamp3 = lamp3;
      	  data.ctx = ctx;
      	  var myTaskList = template('template_myTaskList' , data);
			  $("#myTaskList").empty();
			  $("#myTaskList").html(myTaskList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
			$("#myTaskList").subscribeToggle({
				moduleType:"1001",
				subscribeType:"2001"
			});
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
			//left
			$('.demo-left').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'left',
				alignY: 'center',
				offsetX: 8,
				offsetY: 5,
			});

			//right
			$('.demo-right').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'right',
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

			//bottom
			$('.demo-bottom').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
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

function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();

	if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
}
function initAutocomplete(url){
	$("#inTextVal").AutoComplete({
		data:url,
		'itemHeight': 20,
        'width': 280,
        maxItems:10,
        ajaxType:'POST',
        beforeLoadDataHandler:function(){
        	$("#inTextVal").attr('hVal','');
        	return true;
        },
        afterSelectedHandler:function(data){ 
        	if(data&&data.value){
        		$("#inTextVal").attr('hVal',data.value);
        	}else{
        		$("#inTextVal").attr('hVal','');
        	}
		}
    }).AutoComplete('show');
}

/***
 *  回调刷新的方法
 */
function callback() {
	//var url = ctx +'/task/myTaskList';
	//window.location.href = url;
	setTimeout('searchMethod()',1000); 
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

function createTimeSort(){
	if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#createTimeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function processfu(pkId,propertyCode,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,inputType,id){
	showGlDiv('backCase1',pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,propertyCode,inputType,ctx +"/engine/task/"+id+"/process",false);
}
/*
function processfu(pkId,propertyCode,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,inputType){
	if(null !=propertyCode && ""!=propertyCode){}else{window.open(ctx +'/engine/task/'+pkId+'/process');}
	var data = {};
	if(!page) {data.page = 1;} else {data.page = page;}  
	data.propertyCode=propertyCode;
	data.queryId="queryMergeType";
	data.rows = 5;
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url:ctx+ "/quickGrid/findPage" ,
		dataType : "json",
		data : data,
		beforeSend:function(){},
		success : function(data) {debugger;
		if(data.cu>1){
			showGlDiv('backCase1',pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,propertyCode,inputType);
		}else{
			window.open(ctx +'/engine/task/'+pkid+'/process');
		}
		},complete: function() { },
		error : function(errors) { }
	});
	
}
*/



