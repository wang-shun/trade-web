/**
 * 誉萃组别关系配置 mouwm
 */

$(document).ready(function() {
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					// jqGrid 初始化
					$("#table_list_1").jqGrid(
					{
						url : url,
						mtype : 'POST',
						datatype : "json",
						height : 600,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'PKID','ORIGIN_ORG_ID','TARGET_ORG_ID','AVAILABLE','前台组','前台组编码','后台组','后台组编码','是否可用','操作'],
						colModel : [ {
							name : 'PKID',
							index : 'PKID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'ORIGIN_ORG_ID',
							index : 'ORIGIN_ORG_ID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'TARGET_ORG_ID',
							index : 'TARGET_ORG_ID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'AVAILABLE',
							index : 'AVAILABLE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'orginName',
							index : 'orginName',
							align : "center",
							width : 50
						}, {
							name : 'orginOrgCode',
							index : 'orginOrgCode',
							align : "center",
							width : 50
						},{
							name : 'backName',
							index : 'backName',
							align : "center",
							width : 50
						}, {
							name : 'backOrgCode',
							index : 'backOrgCode',
							align : "center",
							width : 50
						},{
							name : 'AVAILABLE_NAME',
							index : 'AVAILABLE_NAME',
							align : "center",
							formatter : yesOrNoFormatter,
							width : 30
						},{
							name : 'EDIT',
							index : 'EDIT',
							align : "center",
							width : 50
						},


						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false, 
						cellEdit:true,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						/*onSelectRow : function(rowid, status) {
							window.wxc.alert(rowid);
						},*/
						postData : {
							queryId : "queryTsTeamOrgRelationList"
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
								caption:"新增组别关系配置",
								onClickButton: function (){ 
									// select控件
									getAllTeam();
									$('#teamProperty_chosen').css("width","177px");
									
								}
					}); 

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

function rowEdit(id){
	getAllTeam(id);
}
function getAllTeam(id){
	var url = "/setting/getAllTeamPropertyList";
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
	inHtml+= '选择前台组';
	inHtml+='</label><div class="col-lg-4 select"  >';
	inHtml+='<select id="yuTeamFrontCode" class="btn btn-white chosen-select">';
	var backHtml = '';
	backHtml+='<label class="col-lg-2 control-label">';
	backHtml+= '选择后台组';
	backHtml+='</label><div class="col-lg-4 select"  >';
	backHtml+='<select id="yuTeamBackCode" class="btn btn-white chosen-select">';
	$.each(data,function(i, n){
		if(n.teamProperty == 'yu_front' || n.teamProperty == 'yu_all'){
			if(n.orgCode == '033F046'){
				inHtml+='<option value="'+n.orgId+'" selected>'+n.yuTeamName+'</option>';
			}else{
				inHtml+='<option value="'+n.orgId+'">'+n.yuTeamName+'</option>';
			}
		}else if(n.teamProperty == 'yu_back'){
			if(n.orgCode == '033K715'){
				backHtml+='<option value="'+n.orgId+'" selected>'+n.yuTeamName+'</option>';
			}else{
				backHtml+='<option value="'+n.orgId+'">'+n.yuTeamName+'</option>';
			}
		}
	})
	inHtml+='</select></div>';
	backHtml+='</select></div>';
	$("#teamDiv").html(inHtml);
	$("#teamBackDiv").html(backHtml);
	
	if(id!=null){
	    var row = $("#table_list_1").getRowData(id);
	    $("#pkId").val(row.PKID);
	    $("#yuTeamFrontCode").val(row.ORIGIN_ORG_ID);
	    $("#yuTeamBackCode").val(row.TARGET_ORG_ID);
		$("input[name='available'][value='"+row.AVAILABLE+"']").prop("checked", true);
	}
    
	$('#modal-form').modal("show");
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	};
	$('#teamProperty_chosen').css("width","177px");
	$('#yuTeamFrontCode_chosen').css("width","200px");
	$('#yuTeamBackCode_chosen').css("width","200px");
}
function saveTeamOrgRelation(){
    var originOrgId = trim($("#yuTeamFrontCode").val());
    var targetOrgId = trim($("#yuTeamBackCode").val());
	var pkId = $("#pkId").val();
    var ctx = $("#ctx").val();
	var url='/setting/saveTeamOrgRelation?';
	var params="pkid="+pkId+"&originOrgId="+originOrgId+"&targetOrgId="+targetOrgId;
	url = ctx+url+params;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
		data:$("#editForm").serialize(),
	    beforeSend:function(){  
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        complete: function() {  
            $.unblockUI();   
            if(status=='timeout'){//超时,status还有success,error等值的情况
          	  Modal.alert(
			  {
			    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求"
			  });
            }
	    },
		success : function(data) {
			window.wxc.alert(data.message,{wxcOk:function(){
				if(data.success){
					$('#modal-form').modal("hide");
					$("#table_list_1").setGridParam({datatype:'json', page:1,fromServer:true}).trigger('reloadGrid');
				}
			}});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}

function delRow(id){
	if(id!=null){
		window.wxc.confirm("谨慎操作提示,确认删除组别关系配置?",{"wxcOk":function(){
	    	var ctx = $("#ctx").val();
	    	var url='/setting/delTeamOrgRelation?';
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
					window.wxc.alert(data.message,{wxcOk:function(){
						if(data.success){
							$('#modal-form').modal("hide");
							$("#table_list_1").setGridParam({datatype:'json', page:1,fromServer:true}).trigger('reloadGrid');
						}
					}});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	    }});
	}
}

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
