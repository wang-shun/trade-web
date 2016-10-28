/**
 * 满意度列表
 * zhoujp
 */
$(document).ready(function() {
			var url = "/quickGrid/findPage";
			var ctx = $("#ctx").val();
			url = ctx + url;
			
			var search_caseCode = $('#caseCode').val();
			var search_srvCode = $('#srvCode').val();
			var argu_belongMonth = new Date().format('yyyy-MM-dd');
			// 初始化列表
			var params = {
					argu_belongMonth : argu_belongMonth,
					search_caseCode : search_caseCode,
		    	    search_srvCode : search_srvCode,
					queryId : "kpiList",
					rows : 10,
					page : 1,
					sortname : "CASE_CODE",
					sortorder : "DESC"
				};
    	    
    	    aist.wrap(params);
    		reloadGrid(params);
    		
			//ie
			if ($.support.msie) {
				$('input:radio').click(function () {
					this.blur();
					this.focus();
				});
			}; 
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

//search
function searchMethod(page){

	var search_caseCode = $('#caseCode').val();
	var search_srvCode = $('#srvCode').val();
	var argu_belongMonth = new Date().format('yyyy-MM-dd');
	var yuCuiOriGrpDeptId = $('#yuCuiOriGrpDeptId').val();
	var yuCuiOriGrpId = $('#yuCuiOriGrpId').val();
	
	
	if(!page) {
		page = 1;
	}
	var params = {
			argu_belongMonth : argu_belongMonth,
			search_caseCode : search_caseCode,
			search_srvCode : search_srvCode,
			yuCuiOriGrpDeptId : yuCuiOriGrpDeptId,
			yuCuiOriGrpId : yuCuiOriGrpId,
			queryId : "kpiList",
			rows : 10,
			page : page
		};
	
	aist.wrap(params);
	reloadGrid(params);
}

function reloadGrid(data) {
	$.ajax({
		async: false,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
      	  data.ctx = ctx;
      	  var satisfactionDegreeList = template('template_satisfactionDegreeList' , data);
			  $("#satisfactionDegreeList").empty();
			  $("#satisfactionDegreeList").html(satisfactionDegreeList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
        }
  });
}
function reloadGrid1(data) {
	if (!data) {
		data = monthSel.getDate().format('yyyy-MM-dd');
	} else {
		data = data.format('yyyy-MM-dd');
	}
	var search_caseCode = $('#caseCode').val();
	var search_srvCode = $('#srvCode').val();
	var argu_belongMonth = new Date().format('yyyy-MM-dd');
	// 初始化列表
	var params = {
			argu_belongMonth : data,
			search_caseCode : search_caseCode,
    	    search_srvCode : search_srvCode,
			queryId : "kpiList",
			rows : 10,
			page : 1,
			sortname : "CASE_CODE",
			sortorder : "DESC"
		};
	
	$.ajax({
		async: false,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: params,
        success: function(data){
      	  data.ctx = ctx;
      	  var satisfactionDegreeList = template('template_satisfactionDegreeList' , data);
			  $("#satisfactionDegreeList").empty();
			  $("#satisfactionDegreeList").html(satisfactionDegreeList);
			  // 显示分页 
              initpage(data.total,data.pagesize,data.page, data.records);
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
			searchMethod(page);
	    }
	});
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

function srvCodeSort(){
	if($("#serCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#serCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#serCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#serCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#serCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function orgNameSort(){
	if($("#orgNameSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#orgNameSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#orgNameSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#orgNameSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#orgNameSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function orgdNameSort(){
	if($("#orgdNameSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#orgdNameSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#orgdNameSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#orgdNameSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#orgdNameSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

$("#importButton").click(
		function() {
			//iframe层
			layer
					.open({
						type : 1,
						title : '个人案件KPI导入 ',
						shadeClose : true,
						shade : 0.8,
						area : [ '50%', '40%' ],
						content : $('#excelImport'), //捕获的元素
						btn : [ '提交', '关闭' ],
						yes : function(index) {
							if (checkFileTypeExcel()) {
								$
										.blockUI({
											message : $("#salesLoading"),
											css : {
												'border' : 'none',
												'z-index' : '9999'
											}
										});
								$(".blockOverlay").css({
									'z-index' : '9998'
								});
								$("#excelInForm")
										.attr(
												"action",
												ctx
														+ "/award/doImport");
								$("#excelInForm")
										.find(
												"input[name='currentMonth']")
										.remove();
								$("#excelInForm")
										.append(
												$("<input>")
														.attr(
																{
																	type : 'hidden',
																	name : 'currentMonth'
																})
														.val(
																!sw
																		.bootstrapSwitch('state')));
								$("#excelInForm").attr(
										"method", "POST");
								$('#excelInForm').submit();

								layer.close(index);
							}
						},
						cancel : function(index) {
							layer.close(index);
							//layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
						}
					});
		});





