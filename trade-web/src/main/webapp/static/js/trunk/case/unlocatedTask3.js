function radioYuCuiOrgSelectCallBack(array){
	if(array && array.length >0){
		$("#guestServiceCenter").val(array[0].name);
		$("#groupParentID").val(array[0].id);
	}
	else {
		$("#guestServiceCenter").val("");
		$("#groupParentID").val("");
	}
}

function changeTaskAssignee(page,username){
		 var data = {};
		 data.rows = 5;
		 data.page = page;
	     if(!page) {
	    	 data.page = 1;
	     }
		  data.username = username;
		$.ajax({
			cache : false,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+"/manage/listUser",
			dataType : "json",
			data : data,
			beforeSend:function(){},
			success : function(data) {
				data.ctx = ctx;
		    	var tsAwardBaseList= template('template_taskListf' , data);
		        $("#taskListf").empty();
		        $("#taskListf").html(tsAwardBaseList);
		        initpagef(data.total,data.pagesize,data.page, data.records);
			},
			error : function(errors) {				
			}
		});
}

// 查询
$('#searchButton').click(function() {
	reloadGrid(1);
});

// 分配任务 人员查询
$('#searchUsername').click(function() {

	changeTaskAssignee(1, $.trim($('#username').val()));
});

function initpagef(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPagef').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalPf').text(0);
		$("#pageBarf").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalPf').text(records);
	
	$("#pageBarf").twbsPagination({
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
			changeTaskAssignee(page);
	    }
	});
}
function doLocateTask(candidateId) {
	var taskId=$("#taskId").val();
	$.ajax({
		url : ctx + "/unlocatedTasks/doLocateTask/"+taskId+"/"+candidateId,
		method : "post",
		dataType : "json",
		success : function(data) {
			if(data.sc&&data.sc=='0'){
				window.wxc.success('分配成功！');
			}else{
				window.wxc.error('分配失败！');
			}
			$('#myModal').modal("hide");
			reloadGrid();
			//taskDelGrid.trigger('reloadGrid');
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

function createTimeSort(){
	if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#createTimeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

//选择框选择后拼接caseCode字符串的方法
function editCaseCode(str,thisCaseCode){
	if(''!=thisCaseCode){
		if(thisCaseCode.indexOf(str)>=0){
			if(thisCaseCode.indexOf(str)==0){
				if(thisCaseCode.length>str.length){
					thisCaseCode = thisCaseCode.replace(str+",","");
				}else{
					thisCaseCode = thisCaseCode.replace(str,"");
				}

			}else{
				thisCaseCode = thisCaseCode.replace(","+str,"");
			}

		}else{
			thisCaseCode=thisCaseCode+","+str;
		}
	}else{
		thisCaseCode=str;
	}
	return thisCaseCode;
}
//全选和反选的操作
function checkBoxALL(){
	$("#checkAll").click(function(){
		if($("#checkAll").is(':checked')){
			$("#tab_unlocatedTask input[type='checkbox']").prop("checked", true);
			$("#changCaseCode").val("");
			$("#changTaskId").val("");
			$(".caseCode_choice").each(function(index){
				$("#changCaseCode").val(editCaseCode($(this).text(),$("#changCaseCode").val()));
				$("#changTaskId").val(editCaseCode($(this).attr("tValue"),$("#changTaskId").val()));
			});
		}else{
			$("#tab_unlocatedTask input[type='checkbox']").removeAttr("checked");
			$(".caseCode_choice").each(function(index){
				$("#changCaseCode").val("");
				$("#changTaskId").val("");
			});
		}
	});
}
function cleanForm(){
	$("input[name='leadingProName']").val();
	$("input[name='leadingProId']").val();
	$("input[name='cooperName']").val();
	$("input[name='preProcessorId']").val();
	$("input[name='processorId']").val();
	$("#codeShow").html("");
}
//案件责任人
function leadingProForChangeClick(){
	var orgId='ff8080814f459a78014f45a73d820006';
	var cuserId = $("#cuserId").val();
	if(cuserId==''){
		orgId = $("#orgId").val();
	}
	userSelect({
		startOrgId : orgId,
		expandNodeId : orgId,
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : 'Manager,Consultant',
		callBack : selectLeadingPro
	});

}

//选取责任人的回调函数
function selectLeadingPro(array) {
	if (array && array.length > 0) {
		$("#leadingProName").val(array[0].username);
		$("#leadingProName").attr('hVal', array[0].userId);
		$("#leadingProId").val(array[0].userId);

	} else {
		$("#leadingProName").val("");
		$("#leadingProName").attr('hVal', "");
	}
}
function deleteDomByCaseCode(caseCodes){
	var caseCode = caseCodes.split(",");
	for(var i=0;i<caseCode.length;i++){
		$("#tab_unlocatedTask tr[name='"+caseCode[i]+"']").remove();
	}

}