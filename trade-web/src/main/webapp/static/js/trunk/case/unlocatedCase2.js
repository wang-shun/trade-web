

$(document).ready(function() {
	loadGrid(1);
	/*$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list_1').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });*/
					
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
$('#datepicker').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});



/*获取未分配案件列表*/
function loadGrid(page) {
	$("#caseChangeTeamButton").attr("disabled", true);
	var data = getQueryParams(page);
	var ctx = $("#ctx").val();
	
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
        	var myCaseList = template('template_myCaseList' , data);
        	$("#myCaseList").empty();
        	$("#myCaseList").html(myCaseList);
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
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}

/*分页栏*/
function initpage(totalCount,pageSize,currentPage,records) {
	
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
			loadGrid(page);
	    }
	});
}

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	
	var params = {
		search_proAddr:$.trim($("#txt_prd_addr").val()),
		distCode:$("#distCode").val(),
		search_dtBegin:$("#dtBegin_0").val(),
		search_dtEnd:$("#dtEnd_0").val()?($("#dtEnd_0").val()+' 23:59:59'):$("#dtEnd_0").val(),
		argu_oriGrpId:$("#oriGrpId").val(),
		queryId : 'queryUnlocatedCase',
		rows : 10,
	    page : page
	};
	
	return params;	
}


function loadGrid2(){
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	//jqGrid 初始化
	$("#table_list_1").jqGrid("GridUnload");
	$("#table_list_1").jqGrid({
		url : url,
		mtype : 'POST',
		datatype : "json",
		height : 600,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 20,
		/*   rowList: [10, 20, 30], */
		colNames : [ '案件编号', '案件编号', '产证地址', '经纪人', '组别', '区经/区总','导入时间'],
		colModel : [ {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			width : 60
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 250
		}, {
			name : 'AGENT_NAME',
			index : 'AGENT_NAME',
			width : 30
		}, {
			name : 'ORG_NAME',
			index : 'ORG_NAME',
			width : 90
		}, {
			name : 'LEADER',
			index : 'LEADER',
			width : 30
		}, {
			name : 'IMPORT_TIME',
			index : 'IMPORT_TIME',
			width : 40
		},
		],
		multiselect: true,
		pager : "#pager_list_1",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		onSelectRow : function(rowid,status) {
			if(status){
				$("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_list_1").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseChangeTeamButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	        	$("#caseChangeTeamButton").attr("disabled", true);
    		}
		},
		postData : {
			queryId : "queryUnlocatedCase",
			search_proAddr:$("#txt_prd_addr").val(),
			distCode:$("#distCode").val(),
			search_dtBegin:$("#dtBegin_0").val(),
			search_dtEnd:$("#dtEnd_0").val()?($("#dtEnd_0").val()+' 23:59:59'):$("#dtEnd_0").val(),
			argu_oriGrpId:$("#oriGrpId").val()
		}

	});
}

function clean(){
	$("input[name='txt_prd_addr']").val('');
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("input[name='radioOrgName']").val('');
	$("input[name='oriGrpId']").val('');
	$("#distCode ").get(0).selectedIndex=0;
}
/**
 * 案件转组初始化
 */
function caseChangeTeam(){
	showTeamModal();
	}

/**
 * 选择组别modal
 * @param data
 */
function showTeamModal(data){
	$('#team-modal-form').modal("show");
}


//选组织的回调函数
function radioOrgSelectCallBack1(array){
    if(array && array.length >0){
        $("#radioOrgName1").val(array[0].name);
		$("#oriGrpId1").val(array[0].id);
	}else{
		$("#radioOrgName1").val("");
		$("#oriGrpId1").val("");
	}
}
//选组织的回调函数
function radioOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#radioOrgName").val(array[0].name);
		$("#oriGrpId").val(array[0].id);
	}else{
		$("#radioOrgName").val("");
		$("#oriGrpId").val("");
	}
}

/*全选框绑定全选/全不选属性*/
$('#checkAllNot').click(function(){
	var my_checkboxes = $('input[name="ckb_task"]');
	//var parE=$(event.target).closest('td');
	if($(this).prop('checked')){
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="ckb_task"]:eq('+i+')').prop('checked',true);
			//parE.find("input[name='taskIds']").attr("disabled",true);
			//parE.find("input[name='caseCodes']").attr("disabled",true);
			$("#caseChangeTeamButton").attr("disabled", false);
		}
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="ckb_task"]:eq('+i+')').prop('checked',false);
			//parE.find("input[name='taskIds']").removeAttr("disabled");
			//parE.find("input[name='caseCodes']").removeAttr("disabled");
			$("#caseChangeTeamButton").attr("disabled", true);
		}
	}
});




function ckbChange(){
	
	$("#caseChangeTeamButton").attr("disabled", false);
	var parE=$(event.target).closest('td');
	if($(event.target).attr('checked')){
		parE.find("input[name='taskIds']").attr("disabled",true);
		parE.find("input[name='caseCodes']").attr("disabled",true);
	}else{
		parE.find("input[name='taskIds']").removeAttr("disabled");
		parE.find("input[name='caseCodes']").removeAttr("disabled");	
	}
	
}