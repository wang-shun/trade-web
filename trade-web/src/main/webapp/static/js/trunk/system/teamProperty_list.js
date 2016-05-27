/**
 * 誉萃组别配置 wanggh
 */

$(document).ready(function() {
					// Examle data for jqGrid
					// Configuration for jqGrid Example 1
					// /$("#case_date").addClass('btn btn-white chosen-select');
	var saveMsg = $("#saveMsg").val();
	if(saveMsg!=""){
		alert(saveMsg);
	}
					cleanSearch();
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					// jqGrid 初始化
					$("#table_list_1").jqGrid(
					{
						url : url,
						mtype : 'POST',
						datatype : "json",
						height : 250,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'PKID','IS_RESPONSE_TEAM','TEAM_PROPERTY','FREE_SELECT','组别编码', '组别名称','主组别','自行选择','组别类型','操作'],
						colModel : [ {
							name : 'PKID',
							index : 'PKID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'IS_RESPONSE_TEAM',
							index : 'IS_RESPONSE_TEAM',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'TEAM_PROPERTY',
							index : 'TEAM_PROPERTY',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'FREE_SELECT',
							index : 'FREE_SELECT',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'YU_TEAM_CODE',
							index : 'YU_TEAM_CODE',
							align : "center",
							width : 60
							
						}, {
							name : 'ORG_NAME',
							index : 'ORG_NAME',
							align : "center",
							width : 120
						}, {
							name : 'IS_RESPONSE_TEAM_NAME',
							index : 'IS_RESPONSE_TEAM_NAME',
							width : 30,
							formatter : yesOrNoFormatter
						}, {
							name : 'FREE_SELECT_NAME',
							index : 'FREE_SELECT_NAME',
							width : 30,
							formatter : yesOrNoFormatter
						}, {
							name : 'TEAM_PROPERTY_NAME',
							index : 'TEAM_PROPERTY_NAME',
							width : 60
						}, {
							name : 'EDIT',
							index : 'EDIT',
							align : "center",
							width : 60
						},


						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false, 
						cellEdit:true,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						onSelectRow : function(rowid, status) {
							alert(rowid);
						},
						postData : {
							queryId : "queryTsTeamPropertyList"
						},
						gridComplete: function(){
			                var ids = $("#table_list_1").getDataIDs();//jqGrid('getDataIDs');
			                for(var i=0;i<ids.length;i++){
			                    var id = ids[i];
			                    var inHTML = "<input class='btn btn-primary' type='button' value='编辑' onclick='rowEdit(\""+id+"\")' />";
			                    inHTML += "<input class='btn btn-primary' type='button' style='margin-left:2px;' value='删除' onclick='delRow(\""+id+"\")' />";
			                    jQuery("#table_list_1").jqGrid('setRowData',ids[i],{EDIT:inHTML});
			                } 
			            }

					});
					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
					$("#table_list_1").navGrid('#pager_list_1',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
							"#pager_list_1",
							{
								caption:"新增配置",
								onClickButton: function (){ 
									cleanForm();
									// select控件
									getAllTeam();
									$('#teamProperty_chosen').css("width","177px");
									
								}
					}); 

});
//查询
$('#searchButton').click(function() {
	searchMethod();
});
/**
 * 主组别Formate
 * @param cellvalue
 * @returns {String}
 */
function yesOrNoFormatter(cellvalue) {

	var reValue ='';
	if (cellvalue == '0'){
		reValue = '否';
	}else if (cellvalue == '1'){
		reValue = '是';
	}
	return reValue;
}
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

 //查询
function searchMethod() {
		var params = getParamsValue();
		// jqGrid reload
		$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 
		}).trigger('reloadGrid');

};

/**
 * 查询参数取得
 * @returns {___anonymous6020_7175}
 */
function getParamsValue() {
	// 组别编码
	var yuTeamCode = $('#search_yuTeamCode').val();
	// 组别名称
	var orgName = $('#search_orgName').val();
	// 主组别
	var isResponseTeam = $('input[name="search_isResponseTeam"]:checked').val();
	// 自行选择
	var freeSelect = $('input[name="search_freeSelect"]:checked').val();
	// 组别编码
	var teamProperty = $('#search_teamProperty').val();
	
	if(isResponseTeam == -1)isResponseTeam=null;
	if(freeSelect == -1)freeSelect=null;
	//设置查询参数
	var params = {
		search_yuTeamCode : yuTeamCode,
		search_orgName : orgName,
		search_isResponseTeam : isResponseTeam,
		search_freeSelect : freeSelect,
		search_teamProperty : teamProperty
		
	};
	return params;
}

function rowEdit(id){
	getAllTeam(id);
}
function getAllTeam(id){
	var url = "/case/getAllTeamList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
	    data : "", 
		success : function(data) {
			showTeamModal(id,data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}

function showTeamModal(id,data){
	var inHtml = '';
	inHtml+='<label class="col-lg-2 control-label">';
	inHtml+= '选择组别';
	inHtml+='</label><div class="col-lg-4 select"  >';
	inHtml+='<select id="yuTeamCode" class="btn btn-white chosen-select">';
	$.each(data,function(i, n){
		if(n.orgCode == '033F046'){
			inHtml+='<option value="'+n.orgCode+'" selected>'+n.orgName+'</option>';
		}else{
			inHtml+='<option value="'+n.orgCode+'">'+n.orgName+'</option>';
		}
	})
	inHtml+='</select></div>';
	$("#teamDiv").html(inHtml);
	
	if(id!=null){
	    var row = $("#table_list_1").getRowData(id);

	    $("#pkId").val(row.PKID);
	    $("#yuTeamCode").val(row.YU_TEAM_CODE);
	    $("#orgName").val(row.ORG_NAME);
		$("input[name='isResponseTeam'][value='"+row.IS_RESPONSE_TEAM+"']").prop("checked", true);
		$("input[name='freeSelect'][value='"+row.FREE_SELECT+"']").prop("checked", true);


	    $("#teamProperty").val(row.TEAM_PROPERTY);
	}
    
	$('#modal-form').modal("show");
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	};
	$('#teamProperty_chosen').css("width","177px");
	$('#yuTeamCode_chosen').css("width","177px");
}
function saveTeamPropertyItem(){
    var yuTeamCode = trim($("#yuTeamCode").val());
   
	var pkId = $("#pkId").val();
    var ctx = $("#ctx").val();
	var url='/setting/saveTeamPropertyItem?';
	var params="pkid="+pkId+"&yuTeamCode="+yuTeamCode;
	url = ctx+url+params;

	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	$('#editForm').attr('action', url);
	$("#editForm").submit();
}

function delRow(id){
	if(id!=null){
	    if(confirm('谨慎操作提示,确认删除组别配置?')){
	    	var ctx = $("#ctx").val();
	    	var url='/setting/delTeamPropertyItem?';
	    	var params="pkid="+id;
	    	url = ctx+url+params;
	    	$.ajax({
				cache : false,
				async:true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout: 10000,
			    data : params, 
			    beforeSend:function(){  
					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
					$(".blockOverlay").css({'z-index':'9998'});
	            },  
	            complete: function() {  
	                $.unblockUI();   
	                if(status=='timeout'){//超时,status还有success,error等值的情况
		          	  Modal.alert(
					  {
					    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
					  });
			  		 $(".btn-primary").one("click",function(){
			  				parent.$.fancybox.close();
			  			});	 
			                }
			            } ,
				success : function(data) {
					alert(data.message);
					if(data.success){
						$('#modal-form').modal("hide");
						//jqGrid reload
						$("#table_list_1").trigger('reloadGrid');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	    }
	}
   
}

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
// 清空表单
function cleanSearch() {
    $("#search_yuTeamCode").val("");
    $("#search_orgName").val("");
	$("input[name='search_isResponseTeam'][value=-1]").prop("checked", true);
	$("input[name='search_freeSelect'][value=-1]").prop("checked", true);
//	$("#teamProperty").val("");
//    alert($("#teamProperty").val());
}
function cleanForm() {
	$("#pkId").val("");
	$("input[name='isResponseTeam'][value=1]").prop("checked", true);
	$("input[name='freeSelect'][value=1]").prop("checked", true);
    $("#teamProperty").val("yu_all");
}