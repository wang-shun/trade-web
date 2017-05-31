
$(document).ready(function() {
	//运营主管权限设置问题
	if(serviceJobCode == "YCYYZG"){
		$("#forOperationManager").show();
	}
	//初始化数据
	var data = getParams(1);
    aist.wrap(data);
	reloadGrid(data);
});




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
        	data.l1 = Lamp1;
        	  data.l2 = Lamp2;
        	  data.l3 = Lamp3;
        	  data.ctx = ctx;
      	  var myTaskList = template('template_myTaskList' , data);
			  $("#myTaskList").empty();
			  $("#myTaskList").html(myTaskList);
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
	
	//执行人
	var realName = $("#REAL_NAME").attr('hVal');	
	//所属组织
	var orgHierarchy = $("#orgHierarchy").val();
	var organizeOrgId = $("#organizeOrgId").val();	
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
	var data = {
			search_caseCode : caseCode,
			search_ctmCode : ctmCode,
			argu_guestname : guestName,
			argu_realName : realName,
			argu_organizeOrgId : organizeOrgId,
			argu_orgHierarchy : orgHierarchy,
			search_agentName : agentName,
			search_agentOrgName : agentOrgName,
			search_propertyAddr : propertyAddr,
			search_taskDfKey:taskDfKey,
			queryId : "queryUncompleteTask",
			rows : 10,
			page : page
		};

    return data; 
}
function showOptUsers(taskId,cc){
	if(taskId && cc){
		optTaskId=taskId;
		caseCode=cc;
	}else{
		var chks=$("input[name='ckb_task']:checked");
		if(chks.length==0){
			window.wxc.alert('请至少选择一个任务');
			return ;
		}
	}	
	if(serviceJobCode == "YCYYZG"){
		sDepId = "ff8080814f459a78014f45a73d820006";
	}
	
	userSelect({startOrgId:sDepId,expandNodeId:sDepId,
		nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:taskUserSelectBack});
}
function taskUserSelectBack(array){
	if(array && array.length >0){
		var selectUserId=array[0].userId;
		var selectUserRName=array[0].username;
		//alert(selectUserId+"==="+selectUserRName);
		
		window.wxc.confirm('是否确定将任务分配给"'+selectUserRName+'"?',{"wxcOk":function(){
			$("#h_userId").val(selectUserId);
			if(optTaskId){
				var sendData={'taskIds[0]':optTaskId,userId:selectUserId,'caseCodes[0]':caseCode};
				changeTaskAssignee(sendData);
			}else{
				changeTaskAssignee();
			}
		}});
	}
}
function changeTaskAssignee(sendData){
	if(!sendData){
		sendData=$('#form1').serialize();
	}
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/case/changeTaskAssignee",
		dataType : "json",
		data : sendData,
		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
         },
		success : function(data) {
			if(data.success){
				window.wxc.success("变更成功",{"wxcOk":function(){
					$("#myTaskList").empty();
					searchMethod(1);
				}});
				//reloadGrid();
				/*var data = getParams(1);
			    aist.wrap(data);
				reloadGrid(data);*/
			}else{
				window.wxc.error(data.message);
			}
		},complete: function() { 
			 $.unblockUI(); 
			 optTaskId='';
		},
		error : function(errors) {
			window.wxc.error("数据保存出错");
			 $.unblockUI();
		}
	});
}

/*全选框绑定全选/全不选属性*/
$('#checkAllNot').click(function(){
	
	var my_checkboxes = $('input[name="ckb_task"]');
	//var parE=$(event.target).closest('td');
	if($(this).prop('checked')){
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="ckb_task"]:eq('+i+')').prop('checked',true);
			$("#caseDistributeButton").attr("disabled", false);
			//parE.find("input[name='taskIds']").attr("disabled",true);
			//parE.find("input[name='caseCodes']").attr("disabled",true);
		}
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$("#caseDistributeButton").attr("disabled", true);
			$('input[name="ckb_task"]:eq('+i+')').prop('checked',false);
			//parE.find("input[name='taskIds']").removeAttr("disabled");
			//parE.find("input[name='caseCodes']").removeAttr("disabled");
		}
	}
});



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



function ckbChange(){
	
	$("#caseDistributeButton").attr("disabled", false);
	var parE=$(event.target).closest('td');
	if($(event.target).attr('checked')){
		parE.find("input[name='taskIds']").attr("disabled",true);
		parE.find("input[name='caseCodes']").attr("disabled",true);
	}else{
		parE.find("input[name='taskIds']").removeAttr("disabled");
		parE.find("input[name='caseCodes']").removeAttr("disabled");	
	}
	
}


// 组织图标选择
$('#backlogOrgNameOnclick').click(function() {
	orgSelect({
		displayId : 'oriGrpId',
		displayName : 'radioOrgName',
		startOrgId : '1D29BB468F504774ACE653B946A393EE',
		expandNodeId : '1D29BB468F504774ACE653B946A393EE', // 添加此属性的作用是展开 组织列表
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		callBack : radioYuCuiOrgSelectCallBack
	})
});

// 选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
		$("#orgName").val(array[0].name);
		$("#orgHierarchy").val(array[0].extendField);
		$("#organizeOrgId").val(array[0].id);
	} else {
		$("#orgName").val("");
		$("#organizeOrgId").val("");
	}
}



//主办图标选择
$('#backlogOnclick').click(function() {
	chooseCaseOperator(serviceJobCode,serviceDepId);
});


// 选择组织之后 级联选择主办人信息
function chooseCaseOperator(code,id) {	
	var serviceDepId = id;
	if(code =="YCYYZG"){
		serviceDepId = "ff8080814f459a78014f45a73d820006";
	}	
	var yuCuiOriGrpId = $("#organizeOrgId").val();		
	console.log("serviceDepId:"+serviceDepId+"expandNodeId:"+serviceDepId+"");
	if (yuCuiOriGrpId != "") {
		userSelect({
			startOrgId : yuCuiOriGrpId,
			expandNodeId : yuCuiOriGrpId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
		// $("#yuCuiOriGrpId").val("");
	} else {
		userSelect({
			startOrgId : serviceDepId,
			expandNodeId : serviceDepId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
	}
}

// 选取人员的回调函数
function loanLostCaseListSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#REAL_NAME").val(array[0].username);
		$("#REAL_NAME").attr('hVal', array[0].userId);

	} else {
		$("#REAL_NAME").val("");
		$("#REAL_NAME").attr('hVal', "");
	}
}


//清空
$('#backlogCleanButton').click(function() {
	$("input[name='orgName']").val('');	
	$("input[name='REAL_NAME']").val('');
	$("#REAL_NAME").attr('hVal','');
	$("#organizeOrgId").val("");
	$("#orgHierarchy").val("");
	$("#taskDfKey").val("");
	$("#inTextVal").val("");	

});
