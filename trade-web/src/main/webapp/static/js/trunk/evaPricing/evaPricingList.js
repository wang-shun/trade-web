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
	var url = "/evaPricing/getEvaFinOrg";
	$.ajax({
		async: true,
		type:'POST',
		url:ctx+url,
		dataType:'json',
		success:function(data){
			console.log(data.content);
			var html = '<option value="" selected>请选择</option>';
			if(data.content && data.content.length >0){
				$.each(data.content,function(i,item){
					html += '<option value="'+item.id+'">'+item.name+'</option>';
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
	var pVal = $(obj).prev().find('select').attr('pval');
	var sVal = $(obj).prev().find('select').val();
	var url = ctx+"/evaPricing/";
	if(sVal == '0'){//查看明细
		url += "evaPricingDetail?PKID="+pVal;
		window.open(url);
	}else if(sVal == '1'){//记录
		url += "evaPricingEnter?PKID="+pVal;
		window.location.href = url;
	}else if(sVal == '2'){//取消
		url += "evaPricingCancel?PKID="+pVal;
		window.wxc.confirm(
				'确认询价取消?',
				{
					onOk:function(){
						console.log(url);
						$.ajax({
							cache:true,
							async:false,
							type:"POST",
							url:url,
//							data:jsonData,
							dataType:"json",
							success:function(data){
								window.wxc.success("取消成功!",{"wxcOk":function(){
									searchMethod();
								}});
							},
							error : function(errors) {
								window.wxc.error("取消失败!");
							}
						});	
					}
				}
		);
		
	}else if(sVal == '3'){//发起评估申请
		url += "";
	}else{
		return;
	}
}
