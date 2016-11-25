$(document).ready(function() {
	cleanForm();
	initradio('lampRadios',$("#colourId").val());
	$("#txt_proOrgId_gb").val($("#orgName1").val());
	$("#txt_proOrgId").val($("#orgName2").val());
	$("#h_proOrgId_gb").val($("#organId").val());
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){ queryOrgs=null; }
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}
	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	var data = {};// 初始化列表
    data.queryId = "queryRedGreenTaskDetailList";
    data.rows = 12;
    data.page = 1;
	aist.sortWrapper({/*加载排序查询组件*/
		reloadGrid : searchMethod
	});
	reloadGrid(data);
});

function initradio(rName,rValue){
    var rObj = document.getElementsByName(rName);
    for(var i = 0;i < rObj.length;i++){
        if(rObj[i].value == rValue){
            rObj[i].checked =  'checked';
        }
    }
}
$('input:radio[name="lampRadios"]').change(function() {
	searchMethod();
});

var config = {// select控件
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
	if(!page) { page = 1; }
	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	params.queryId = "queryRedGreenTaskDetailList";
	reloadGrid(params);
};

var jobNames = "";
function getDatebase(data){
	
    aist.wrap(data);//添加排序
	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;//添加排序
	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var organId = $("#organId").val();
	var colourId = $("#colourId").val();
	var prApplyTime = $("#prApplyTime").val();
	var dtBegin = $("input[name='dtBegin']").val();//页面参数值
	var dtEnd = $("input[name='dtEnd']").val();
	var realName = $("#inTextVal").val();
	var propertyAddr = $.trim($("input[name='search_propertyAddr']").val());
	var taskName = $("input[name='search_taskName']").val();
	var caseCodes = $.trim($("#caseCode").val());
	var lampRadios = $('input[name="lampRadios"]:checked').val();
	var proOrggbName = $("#txt_proOrgId_gb").val();
	var proOrgName =   $("#txt_proOrgId").val();
	if(lampRadios == 0){data.red=1;}
	if(lampRadios == 1){data.yellow=1;}
	var taskDfKey=$("#taskDfKeyid").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){ queryOrgs=null; }
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}
	var start = $('#dtBegin_0').val();
	if(start&&start!=''){ prApplyTime = start; }
	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
    data.argu_queryorgs = orgArray;
    data.organId = organId;
    data.colourId = colourId;
    data.dtBegin = dtBegin;
    data.dtEnd = dtEnd;
    data.realName = realName;
    data.propertyAddr = propertyAddr;
    data.taskName = taskName;
    data.caseCode = caseCodes;
    data.taskDfKey = taskDfKey;
    data.proOrggbName = proOrggbName;
    data.proOrgName = proOrgName;
    data.jobName = jobNames;
}
function reloadGrid(data) {
	getDatebase(data);
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
	      	  var redgreenTaskDetailList = template('template_redgreenTaskDetailList' , data);
			  $("#redgreenTaskDetailList").empty();
			  $("#redgreenTaskDetailList").html(redgreenTaskDetailList);
			  initpage(data.total,data.pagesize,data.page, data.records);// 显示分页 
        },
        error: function (e, jqxhr, settings, exception) { $.unblockUI();    }  
  });
}
function initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){ totalCount = 1500; }
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
		onPageClick: function (event, page) { searchMethod(page); }
	});
}
function getParamsValue() {
	var start = $('#dtBegin_0').val();
	var params = {//设置查询参数
			prApplyTime : start
	};
	return params;
}
function exportToExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	var displayColomn = new Array;//excel导出列
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
		if(isAdminFlag == 'true'){ queryOrgs=null; }
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
	var organId = $("#organId").val();
	params.organId = organId;
	getDatebase(params);
	var queryId = '&queryId=queryRedGreenTaskDetailList';
	var colomns = '&colomns=' + displayColomn;
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}

// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
}

//清空
$('#cleanButton').click(function() {
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[name='search_realName']").val('');
	$("input[name='search_propertyAddr']").val('');
	$("input[name='search_taskName']").val('');
	$("input[name='search_caseCode']").val('');
	$("input[name='taskDfKey']").val('');		
	$('input[name="lampRadios"]:checked').val('');
	$("select").val("");
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
	$('input:radio[name="lampRadios"]').attr("checked",false);
	jobNames = "";
	$("#organId").val('');
});
function radioYuCuiOrgSelectCallBackgb(array){//贵宾服务部
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
function radioYuCuiOrgSelectCallBack(array){//选业务组织的回调函数
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
			userSelect({startOrgId:serviceDepId,expandNodeId:serviceDepId,
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