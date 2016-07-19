$(document).ready(function() {
					cleanForm();
					//基本信息等高
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var queryOrgFlag = $("#queryOrgFlag").val();
					var isAdminFlag = $("#isAdminFlag").val();
					var queryOrgs = $("#queryOrgs").val();
					var arguUserId=null;
					if(queryOrgFlag == 'true'){
						arguUserId=null;
						if(isAdminFlag == 'true'){
							queryOrgs=null;
						}
					}else{
						queryOrgs= null;
						arguUserId="yes";
					}

					var orgArray = queryOrgs==null?null:queryOrgs.split(",");
					// 初始化列表
					var data = {};
		    	    data.queryId = "queryRedGreenTaskCountList";
		    	    data.rows = 12;
		    	    data.page = 1;
		    		reloadGrid(data);
				
				});

// select控件
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

// 日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

 //查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	
	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	params.queryId = "queryRedGreenTaskCountList";
	reloadGrid(params);
	

};
var jobNames = "";
function reloadGrid(data) {
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	
	var proOrggbName = $("#txt_proOrgId_gb").val();
	var proOrgName =   $("#txt_proOrgId").val();
	var TextValName = $("#inTextVal").val();
	
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
    data.argu_queryorgs = orgArray;
    
    data.proOrggbName = proOrggbName;
    data.proOrgName = proOrgName;
    data.jobName = jobNames;
    if(""!=jobNames)
    	jobNames = jobNames.substring(jobNames.length-2)
    if(jobNames == '总监'){
    	data.TextValNameZj = TextValName;
    	data.TextValName = null;
    }
    if(jobNames == '主管'){
    	data.TextValName = TextValName;
    	data.TextValNameZj = null;
    }
    
	
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
      	  var redgreenTaskList = template('template_redgreenTaskList' , data);
			  $("#redgreenTaskList").empty();
			  $("#redgreenTaskList").html(redgreenTaskList);
			  // 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
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
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			 //console.log(page);
			searchMethod(page);
	    }
	});
}


function getParamsValue() {
	
	var start = $('#dtBegin_0').val();
	//设置查询参数
	var params = {
			prApplyTime : start
	};
	return params;
}

function exportTExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	//excel导出列
	var displayColomn = new Array;
	displayColomn.push('orgName1');
	displayColomn.push('realName1');
	displayColomn.push('orgName2');
	displayColomn.push('realName2');
	displayColomn.push('yellow');
	displayColomn.push('red');
	displayColomn.push('importtime');
	
	displayColomn.push('EVAL_FEE');
	displayColomn.push('RECORD_TIME');

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?'':queryOrgs.split(",");

	var argu_idflag = '&argu_idflag='+arguUserId;
	
	if(arguUserId==null)argu_idflag='&argu_idflag=';
	var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
	if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
	var params = getParamsValue();
	var queryId = '&queryId=queryRedGreenTaskCountExcelList';
	var colomns = '&colomns=' + displayColomn;
	
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	
	$('#excelForm').attr('action', url);
	
	$('#excelForm').method="post" ;
	$('#excelForm').submit();

}

function exportToExcel(organId) {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	//excel导出列
	var displayColomn = new Array;
	displayColomn.push('orgName1');
	displayColomn.push('realName1');
	displayColomn.push('orgName2');
	displayColomn.push('realName2');
	displayColomn.push('color1');
	displayColomn.push('CASE_CODE');
	displayColomn.push('TASKNAME');
	displayColomn.push('PROPERTY_ADDR');
	displayColomn.push('REAL_NAME');
	
	displayColomn.push('EST_PART_TIME');
	displayColomn.push('RPROPERTY_ADDR');
	displayColomn.push('IMPORTTIME');

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?'':queryOrgs.split(",");

	var argu_idflag = '&argu_idflag='+arguUserId;
	
	if(arguUserId==null)argu_idflag='&argu_idflag=';
	var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
	if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
	var params = getParamsValue();
	
	/*var start = $('#dtBegin_0').val();
	if(start&&start!=''){
		prApplyTime = start;
	}*/
	params.organId = organId;
	/*params.prApplyTime = prApplyTime;*/
	
	var queryId = '&queryId=queryRedGreenTaskExcelItemList';
	var colomns = '&colomns=' + displayColomn;
	
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	
	$('#excelForm').attr('action', url);
	
	$('#excelForm').method="post" ;
	$('#excelForm').submit();

}


function queryRedGreenTaskDetail(id){
	//var start = $('#dtBegin_0').val();
	window.open(ctx+"/report/redgreenTaskDetail?organId="+id+"&colourId="+1);
	
}
//添加颜色参数
function queryRedGreenTaskDetailColour(id,colourId,orgName1){
	//var start = $('#dtBegin_0').val();
	window.open(ctx+"/report/redgreenTaskDetailColour?organId="+id+"&colourId="+colourId+"&orgName1="+orgName1);
	
}

// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
}

//清空
$('#cleanButton').click(function() {
	$("input[name='dtBegin']").val('');
	$("select").val("");
	
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');

	$("#txt_proOrgId_gb").val('');
	$("#txt_proOrgId").val('');
	$("#inTextVal").val("");
	
	$("#h_proOrgId_gb").val('');
	$("#h_proOrgId").val('');
	$("#txt_proOrgId").val('');
	$("#txt_proOrgId_gb").val('');
	$("#txt_proOrgId_gb").val('');
	$("#txt_proOrgId").attr("serviceDepIdOld",'');
	$("#txt_proOrgId").attr("serviceDepId",'');
	
	jobNames = "";
	
});



//贵宾服务部
function radioYuCuiOrgSelectCallBackgb(array){
	$("#h_proOrgId").val('');
	
  if(array && array.length >0){
        $("#txt_proOrgId_gb").val(array[0].name);
		$("#h_proOrgId_gb").val(array[0].id);
		$("#inTextVal").val("");
		
		var serviceDepId = array[0].id;
		$("#txt_proOrgId").val('');
		
		$("#txt_proOrgId").attr("serviceDepId",serviceDepId);
		
	}else{
		$("#txt_proOrgId").val("");
		$("#h_proOrgId").val("");
		$("#txt_proOrgId").attr("serviceDepId",$("#txt_proOrgId").attr('serviceDepIdOld'));
	}
}
//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
	$("#h_proOrgId_gb").val("");
if(array && array.length >0){
	    $("#txt_proOrgId").val(array[0].name);
		$("#h_proOrgId").val(array[0].id);
		
		$("#inTextVal").val("");
	}else{
		 $("#txt_proOrgId").val(array[0].name);
		 $("#h_proOrgId").val(array[0].id);
	}
}


function userSelect_back(){
	
	serviceDepId = $("#h_proOrgId").val();
	
	if(serviceDepId != null || serviceDepId != ""){
		
		if(($("#h_proOrgId_gb").val() != "" || $("#h_proOrgId_gb").val() !=null)&&(!(serviceDepId != null) || serviceDepId == "")){
			serviceDepIda = $("#h_proOrgId_gb").val();
			userSelect({startOrgId:serviceDepIda,expandNodeId:serviceDepIda,
				nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
		}else{
			userSelect({startOrgId:serviceDepId,expandNodeId:serviceDepId,jobCode:'Manager,Senior_Manager,director',
				nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
		}
		
	}else{
		userSelect({startOrgId:serviceDepId,expandNodeId:serviceDepId,
			nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
	}
}

function selectUserBack(array){
	if(array && array.length >0){
      $("#inTextVal").val(array[0].username);
		$("#inTextVal").attr('hVal',array[0].userId);
		jobNames = array[0].jobName;
				
	}else{
		$("#inTextVal").val("");
		$("#inTextVal").attr('hVal',"");
	}
}


