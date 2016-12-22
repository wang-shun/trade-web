var readyType = 0;
$(document).ready(function() {
	document.getElementById("zb").style.display="none";
	document.getElementById("zg").style.display="none";
	readyType = 1;
	readyReload(1,"queryRedGreenTaskCountGbList",false);
});
function readyReload(page,queryId,type){
	if(!page) { page = 1; }
	var data = {};
    data.queryId = queryId;
    data.rows = 12;
    data.page = page;
    data.redDelaytime = parseInt($("#redDelaytime").val());
    data.yellowDelaytime = parseInt($("#yellowDelaytime").val());
	if(type){
		reloadGrid(data);
	}else{
		data.pagination = false; 
		reloadGridGb(data);
	}
}
$('#searchButton').click(function() {
	searchMethod();
	searchMethodGb();
});
$('#gbli').click(function() {//查询贵宾服务部
	 document.getElementById("zb").style.display="none";
	 document.getElementById("zg").style.display="none";
	 searchGbcleanForm();
	 $("#guibinPager").show();
	 $("#zubiePager").hide();
	 $("#ZbList").hide();
	 $("#setGbList").show();
});
$('#zbli').click(function() {//查询组别
	 document.getElementById("zb").style.display="block";
	 document.getElementById("zg").style.display="block";
	 $("#zubiePager").show();
	 $("#guibinPager").hide();
	 $("#ZbList").show();
	 $("#setGbList").hide();
	 if(readyType==1){
		 readyReload(1,"queryRedGreenTaskCountList",true);
		 readyType = 0;
	 }
});
$('#setZb').click(function() {// 查询组别
	 document.getElementById("setZbList").style.display="block";
});
function searchMethod(page) {
	readyReload(page,"queryRedGreenTaskCountList",true);
};
function searchMethodGb(page) {
	readyReload(page,"queryRedGreenTaskCountGbList",false);
};
function reloadGrid(data) {
    getDatabase(data,true);
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
            initpage(data.total,data.pagesize,data.page, true,data.records);// 显示分页
        },
        error: function (e, jqxhr, settings, exception) { $.unblockUI();  }  
  });
}
function reloadGridGb(data) {
	getDatabase(data,false);
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
			var redgreenTaskListGb = template('template_redgreenTaskListGb' , data);
			$("#redgreenTaskListGb").empty();
			$("#redgreenTaskListGb").html(redgreenTaskListGb);	  
			initpage(data.total,data.pagesize,data.page, false,data.records);
		},
		error: function (e, jqxhr, settings, exception) { $.unblockUI();    }  
	});
}

function getDatabase(data,type){
	var proOrggbName = $("#txt_proOrgId_gb").val();
	var proOrgName =   $("#txt_proOrgId").val();
	var TextValName = $("#inTextVal").val();
	if(type){
	    data.proOrggbName = proOrggbName;
	    data.TextValName = TextValName;
	    data.proOrgName = proOrgName;
	}else{
		data.proOrggbName = proOrggbName;
	}
}

function initpage(totalCount,pageSize,currentPage,type,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong="";
	if(type){
		currentTotalstrong=$('#currentTotalPage').find('strong');
	}else{
		currentTotalstrong=$('#currentTotalPageGb').find('strong')
	}
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		if(type){
			$('#totalP').text(0);
			$("#pageBar").empty();
		}else{
			$('#totalPGb').text(0);
			$("#pageBarGb").empty();
			$('#totalPGb').text(records);
		}
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	var pageString = "";
	if(type){
		$('#totalP').text(records);
		pageString = "pageBar";
	}else{
		$('#totalPGb').text(records);
		pageString = "pageBarGb";
	}
	
	$("#"+pageString).twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			if(type)
				searchMethod(page);
			else
				searchMethodGb(page);
	    }
	});
}

function exportTExcel(){
	if(document.getElementById("setGbList").style.display == 'none'){
		exportTExcelZb();
	}else{
		exportToExcelGb();
	}
}

function exportTExcelZb() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	var params = {};
	var displayColomn = new Array;//excel导出列
	displayColomn.push('orgName1');
	displayColomn.push('realName1');
	displayColomn.push('orgName2');
	displayColomn.push('realName2');
	displayColomn.push('red');
	displayColomn.push('yellow');
	displayColomn.push('allcolor');
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
	var queryId = '&queryId=queryRedGreenTaskCountList';
	var colomns = '&colomns=' + displayColomn;
	params.redDelaytime = $("#redDelaytime").val();
	params.yellowDelaytime = $("#yellowDelaytime").val();
	getDatabase(params,true);
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();

}
function exportToExcel(organId,orgName1,orgName2) {
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
	var params = {};
	var start = $('#dtBegin_0').val();
	var queryId = '&queryId=queryRedGreenTaskDetailList';
	var colomns = '&colomns=' + displayColomn;
	params.organId = organId;
	params.proOrggbName = orgName1;
	params.gbName = orgName1;
	params.proOrgName = orgName2;
	params.redDelaytime = $("#redDelaytime").val();
	params.yellowDelaytime = $("#yellowDelaytime").val();
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}


function exportToExcelGb(organId) {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	var displayColomn = new Array;//excel导出列
	displayColomn.push('orgName1');
	displayColomn.push('realName1');
	displayColomn.push('redall');
	displayColomn.push('yellowall');
	displayColomn.push('allcolor');
	displayColomn.push('importtime');
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
	var params = {};
	params.orgName1 = organId;
	params.redDelaytime = $("#redDelaytime").val();
	params.yellowDelaytime = $("#yellowDelaytime").val();
	getDatabase(params,false);
	var queryId = '&queryId=queryRedGreenTaskCountGbList';
	var colomns = '&colomns=' + displayColomn;
	url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
	$('#excelForm').attr('action', url);
	$('#excelForm').method="post" ;
	$('#excelForm').submit();
}
function queryRedGreenTaskDetailColour(id,colourId,orgName1,orgName2,yellowDelaytime,redDelaytime){
	window.open(ctx+"/report/redgreenTaskDetailColour?organId="+id+"&colourId="+colourId+"&orgName1="+orgName1+"&orgName2="+orgName2
			+"&yellowDelaytime="+yellowDelaytime+"&redDelaytime="+redDelaytime);
}

$('#cleanButton').click(function() {//清空
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
			userSelect({startOrgId:serviceDepIda,expandNodeId:serviceDepIda,jobCode:'Manager,Senior_Manager,director',
						nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
		}else{
			userSelect({startOrgId:serviceDepId,expandNodeId:serviceDepId,jobCode:'Manager,Senior_Manager,director',
						nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
		}
	}else{
			userSelect({startOrgId:serviceDepId,expandNodeId:serviceDepId,jobCode:'Manager,Senior_Manager,director',
						nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack});
	}
}

function selectUserBack(array){
	if(array && array.length >0){
		$("#inTextVal").val(array[0].username);
		$("#inTextVal").attr('hVal',array[0].userId);
	}else{
		$("#inTextVal").val("");
		$("#inTextVal").attr('hVal',"");
	}
}
function searchGbcleanForm(){
	$("#h_proOrgId").val('');
	$("#txt_proOrgId").val('');
	$("#txt_proOrgId").attr("serviceDepIdOld",'');
	$("#txt_proOrgId").attr("serviceDepId",'');
	$("#inTextVal").val("");
	$("#inTextVal").attr('hVal',"");
}
