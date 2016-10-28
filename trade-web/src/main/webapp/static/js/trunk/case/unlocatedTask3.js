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
		     if(!page) {
		    	 data.page = 1;
		    } else {
		    	data.page = page;
		    }  
		  //data.username = parameters.username;   
		  data.username = username;   
		$.ajax({
			cache : false,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+"/manage/listUser",
			dataType : "json",
			data : data,
			beforeSend:function(){  
				var a=1;
	         },
			success : function(data) {
				//console.log("数据"+JSON.stringify(data));
				data.ctx = ctx;
		    	var tsAwardBaseList= template('template_taskListf' , data);
		        $("#taskListf").empty();
		        $("#taskListf").html(tsAwardBaseList);
		        
		        initpagef(data.total,data.pagesize,data.page, data.records);
			},complete: function() { 
				var a=1;
			},
			error : function(errors) {
				var a=1;
			}
		});
	}




// 查询
$('#searchButton').click(function() {
	reloadGrid(1);
});
// 查询
$('#searchUsername').click(function() {
	/*var parameters ={};
	parameters.username = $.trim($('#username').val());*/
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
				alert('分配成功！');
			}else{
				alert('分配失败！');
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