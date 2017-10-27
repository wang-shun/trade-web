/**
 * 询价列表
 * cyx
 */
$(document).ready(function() {
	
	var url = "/rapidQuery/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	//获取评估公司
	getEvaFinOrg('finOrgId');
	
	// 初始化列表
	var data = {};
    data.queryId = "queryEvaluateItemList";
    data.rows = 10;
    data.page = 1;
	data.argu_sessionUserId = $("#userId").val();
    aist.wrap(data);
	reloadGrid(data);
	
});

/**
 * 获取评估公司 格式化
 * @param finOrgId
 */
function getEvaFinOrg(finOrgId){
	var url = "/manage/queryEvaCompany";
	$.ajax({
		async: true,
		type:'POST',
		url:ctx+url,
		dataType:'json',
		success:function(data){
			var html = '<option value="" selected>请选择</option>';
			if(data != null){
				$.each(data,function(i,item){
					html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
				});
			}					
			$('#'+finOrgId).empty();
			$('#'+finOrgId).append(html);
		},
		error : function(errors) {
		}
	});
}

var ctx = $("#ctx").val();

//查询
$('#searchButton').click(function() {
	searchMethod();
});

$('#cleanButton').click(function(){
	$('#inTextVal').val('');
	$('#finOrgId').val('');
	$('#dateStart').val('');
	$('#dateEnd').val('');
	$('#evaType').val('');
});

$('#dateVal').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

var createStartTime,createEndTime,compStartTime,compEndTime;
//search
function initData(page){
	createStartTime = null;createEndTime = null;compStartTime = null;compEndTime = null;
	
	//产证地址 ，案件编号，申请人
	var inTextVal = $('#inTextVal').val();
	var propertyAddr = "";
	var caseCode = "";
	var applicant = "";
	var hVal = $('#inTextVal').attr('hVal');
	
	if(inTextVal != null && inTextVal.trim() != ''){
		var inTextType = $('#inTextType').val();
		if(inTextType == '0'){
			caseCode = inTextVal.trim();
		}else if(inTextType == '1'){
			propertyAddr = inTextVal.trim();
		}else if(inTextType == '2'){
			applicant = hVal.trim();
		}
	}
	
	//评估公司
	var finOrgId = $('#finOrgId option:selected').val();
	//时间类型
	var dateType = $('#dateType').val();
	var dateStart = $('#dateStart').val();
	var dateEnd = $('#dateEnd').val();
	if(dateEnd && dateEnd !=''){
		dateEnd += ' 23:59:59';
	}
	if(dateStart != ''){
		if(dateType == '0'){
			createStartTime = dateStart;
		}else if(dateType == '1'){
			compStartTime = dateStart;
		}
	}
	if(dateEnd != ''){
		if(dateType == '0'){
			createEndTime = dateEnd;
		}else if(dateType == '1'){
			compEndTime = dateEnd;
		}
	}
	
	//询价类型
	var evaType = $('#evaType option:selected').val();

	if(!page) {
		page = 1;
	}
	var params = {
			argu_sessionUserId : $("#userId").val(),
			argu_caseCode : caseCode,
			argu_propertyAddr : propertyAddr,
			argu_applicant : applicant,
			argu_finOrgId : finOrgId == ''?null:finOrgId,
			argu_createStartTime : createStartTime,
			argu_createEndTime : createEndTime,
			argu_compStartTime : compStartTime,
			argu_compEndTime : compEndTime,
			argu_evaType : evaType == ''?null:evaType,
			queryId : "queryEvaluateItemList",
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
      	  var myTaskList = template('template_evaluateList' , data);
			  $("#evaluateList").empty();
			  $("#evaluateList").html(myTaskList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
              demoPoshytip();
        }
  });
}
//分页
function initpage(totalCount,pageSize,currentPage,records){
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1){
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

function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

//搜索条件中，申请人autocomplete
function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
	if(inTextType=='2'){
		initAutocomplete(ctx+"/labelVal/queryUserInfo");
	}
}

//自动补全插件
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

$('#addNewEvaluate').click(function(){
	window.open(ctx+"/evaPricing/addNewEvaPricing");
});

//操作跳转
function gotoPage(obj){
	var pVal = $(obj).prev().find('select').attr('pval');//明细id
	var instCode = $(obj).prev().find('select').attr('instCode');//流程实例id
	var sVal = $(obj).prev().find('select').val();
	var caseCode = $(obj).prev().find('select').attr('caseCode');
	var evaCode = $(obj).prev().find('select').attr('evaCode');
	
	var url = ctx+"/evaPricing/";
	if(sVal == '0'){//查看明细
		url += "evaPricingDetail?PKID=" + pVal + "&instCode=" + instCode;
		window.open(url);
	}else  if(sVal == '1'){
		if(caseCode != null && caseCode !="" && caseCode != undefined){//已关联交易案件
			//直接给询价编号和caseCode
			window.open(ctx+"/task/eval/apply?evaCode="+evaCode+"&caseCode="+caseCode);
		}else{
			//关联条件置空
			$('#caseCode').val('');
		    $('#propertyAddr').val('');
		    $('#buyerName').val('');
			
			$('#evaPricingId').val(pVal);
			$('#evaCode').val(evaCode);
			evalAppySearch();
			/*var data = {};
		    data.queryId = "queryEvalApplyList";
		    data.rows = 10;
		    data.page = 1;
		    data.argu_pkid = pVal;
		    aist.wrap(data);
			
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:ctx + "/quickGrid/findPage",
				data:data,
				dataType:'json',
				success:function(data){
					$('#evaPricingId').val(pVal);
					$('#evaCode').val(evaCode);
					
					var html = template('template_evalApply',data);
					$('#eval-modal-body').empty();
					$('#eval-modal-body').html(html);
					$('#eval-modal-form').modal('show');
				},
				error:function(){
					window.wxc.error("查询失败!");
				}
			});*/
		}
	}else if(sVal == '2'){//重新发起
		window.open(ctx+"/evaPricing/addNewEvaPricing");
	}else{
		return;
	}
}

var preIndex = -1;
var caseCode = '';
function chooseTr(index){
	if(preIndex == index){
		$('#tr_'+preIndex).css('background','#ffffff');
		preIndex = -1;
		caseCode = '';
	}else{
		$('#tr_'+preIndex).css('background','#ffffff');
		preIndex = index;
		$('#tr_'+index).css('background','#aaaaaa');
		caseCode = $('#p_'+index).text().trim();
	}
}

/**
 * 可关联案件查询
 */
function evalAppySearch(page){
	var data = {};
    data.queryId = "queryEvalApplyList";
    data.rows = 6;
    data.page = 1;
    if(page){
    	data.page = page;
    }
    //取案件编号,产证地址,买家姓名
    var caseCode = $('#caseCode').val();
    var propertyAddr = $('#propertyAddr').val();
    var buyerName = $('#buyerName').val();
    if(caseCode && caseCode.trim() != ''){
    	data.argu_caseCode = caseCode;
    }
    if(propertyAddr && propertyAddr.trim() != ''){
    	data.argu_propertyAddr = propertyAddr;
    }
    if(buyerName && buyerName.trim() != ''){
    	data.argu_buyerName = buyerName;
    }
    
    aist.wrap(data);
	
	$.ajax({
		cache:true,
		async:false,
		type:"POST",
		url:ctx + "/quickGrid/findPage",
		data:data,
		dataType:'json',
		success:function(data){
			var html = template('template_evalApply',data);
			$('#eval-modal-body').empty();
			$('#eval-modal-body').html(html);
			$('#eval-modal-form').modal('show');
			// 显示分页 
            initpageModal(data.total,data.pagesize,data.page, data.records);
		},
		error:function(){
			window.wxc.error("查询失败!");
		}
	});	
}

//分页
function initpageModal(totalCount,pageSize,currentPage,records){
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPageModal').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1){
		$(currentTotalstrong).empty();
		$('#totalPModal').text(0);
		$("#pageBarModal").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalPModal').text(records);
	
	$("#pageBarModal").twbsPagination({
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
			evalAppySearch(page);
	    }
	});
}

/**
 * 案件关联
 * @returns
 */ 
function evalApply(){
	if(caseCode != ''){
		//关联案件
		var pkid = $('#evaPricingId').val();
		var evaCode = $('#evaCode').val();
		
		var data = "&pkid=" + pkid +"&caseCode=" + caseCode;
		$.ajax({
			cache:true,
			async:false,
			type:"POST",
			url:ctx+"/evaPricing/evaPricingRelation",
			data:data,
			dataType:'json',
			success:function(data){
				if(data.content){
					window.location.href= ctx+"/task/eval/apply?evaCode="+evaCode+"&caseCode="+caseCode;
				}else{
					window.wxc.error('关联失败!');
				}
			},
			error:function(){
				window.wxc.error('关联失败!');
			}
		});
	}else{
		window.wxc.info('请选择一条数据!');
	}
	
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