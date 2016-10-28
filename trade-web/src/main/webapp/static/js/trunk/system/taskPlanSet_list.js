/**
 * 提醒清单配置 wanggh
 */

$(document).ready(function() {
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					//var partCode = $("#partCode").val();
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
						colNames : [ 'PKID','提醒事项CODE', '提醒任务','计划天数','是否人工设置','创建时间','操作'],
						colModel : [ {
							name : 'PKID',
							index : 'PKID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						},{
							name : 'PART_CODE',
							index : 'PART_CODE',
							align : "center",
							width : 0,
							resizable : false,
							hidden : true
						}, {
							name : 'PART_NAME',
							index : 'PART_NAME',
							align : "center",
							width : 180,
							resizable : false
						}, {
							name : 'PLAN_DAYS',
							index : 'PLAN_DAYS',
							align : "center",
							width : 180
						}, {
							name : 'IS_MANUAL_SET',
							index : 'IS_MANUAL_SET',
							align : "center",
							width : 100,
							formatter:function(value,options,rowData){
			                    if(value==0){
			                       return '否';
			                    }else{
			                        return '是';
			                    }
			                }
						}, {
							name : 'CREATE_TIME',
							index : 'CREATE_TIME',
							align : "center",
							width : 180
						},{
							name : 'EDIT',
							index : 'EDIT',
							align : "center",
							width : 60
						},


						],
						pager : "#pager_list_1",
						sortname:'CREATE_TIME',
		    	        sortorder:'desc',
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
							queryId : "queryTaskPlanSetList"
							//search_partCode:partCode
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
					$("#table_list_2").jqGrid(
							{
								url : url,
								mtype : 'POST',
								datatype : "json",
								height : 250,
								autowidth : true,
								shrinkToFit : true,
								rowNum : 10,
								/* rowList: [10, 20, 30], */
								colNames : [ 'PK_ID','提醒灯', '延迟天数','操作'],
								colModel : [ {
									name : 'PK_ID',
									index : 'PK_ID',
									align : "center",
									width : 0,
									key : true,
									resizable : false,
									hidden : true
								}, {
									name : 'COLOR',
									index : 'COLOR',
									align : "center",
									width : 180,
									resizable : false,
									formatter:function(value,options,rowData){
					                    if( value==='0' ){
					                       return '红灯';
					                    }else if( value==='1' ) {
					                       return '黄灯';
					                    }else{
					                       return '绿灯';
					                    }
					                }
								}, {
									name : 'DELAYTIME',
									index : 'DELAYTIME',
									align : "center",
									width : 180
								},{
									name : 'EDIT',
									index : 'EDIT',
									align : "center",
									width : 60,
									hidden : true
								}],
								pager : "#pager_list_2",
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
									queryId : "queryLampRuleList"
								},
								gridComplete: function(){
					                var ids = $("#table_list_2").getDataIDs();
					                for(var i=0;i<ids.length;i++){
					                    var id = ids[i];
					                    var inHTML = "<input class='btn btn-primary' type='button' value='编辑' onclick='rowEdit(\""+id+"\")' />";
					                    inHTML += "<input class='btn btn-primary' type='button' style='margin-left:2px;' value='删除' onclick='delRow(\""+id+"\")' />";
					                    jQuery("#table_list_1").jqGrid('setRowData',ids[i],{EDIT:inHTML});
					                } 
					            }

							});
					/*$('#partCode').change(function() {
						searchMethod();
					});*/
					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
					$("#table_list_1").navGrid('#pager_list_1',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
							"#pager_list_1",
							{
								caption:"新增任务项配置",
								onClickButton: function (){ 
									cleanForm();
									$('#modal-form').modal("show");
								}
					}); 
					$("#table_list_2").navGrid('#pager_list_2',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
							"#pager_list_2",
							{
								caption:"新增红灯提醒配置",
								onClickButton: function (){
									cleanLampRuleForm();
									$('#modal-form-lampRule').modal("show");
								}
					}); 

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

 //查询
function searchMethod() {
		//var params = getParamsValue();
		var params = '';
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
	// 环节编码
	var partCode = $('#partCode').val();
	
	//设置查询参数
	var params = {
		search_partCode : partCode
	};
	return params;
}

function rowEdit(id){
	if(id!=null){
	    var row = $("#table_list_1").jqGrid('getRowData',id);

	    $("#pkid").val(row.PKID);
	    
	    $("#partCode option[selected='selected']").attr('selected',false);
	    $("#partCode option[value='"+row.PART_CODE+"']").attr("selected", true);
	    $("#partCode").chosen('destroy');
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
	    //$(".chosen-select").attr('disabled', true).trigger("chosen:updated");
	    $(".chosen-select").attr('disabled', true);
	     
	    $("#planDays").val(row.PLAN_DAYS);
	    
	    if(row.IS_MANUAL_SET == '否') {
	    	$("#isManualSet").val(0);
	    } else {
	    	$("#isManualSet").val(1);
	    }
	    
	    
	    $('#modal-form').modal("show");
	} else {
		alert('请选择相应的配置项!');
	}
}

function saveLampRule(){
	var ctx = $("#ctx").val();
	var url= ctx+'/setting/saveLampRule';
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
		data:$("#editLampRuleForm").serialize(),
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
			alert(data.message);
			if(data.success){
				$('#modal-form-lampRule').modal("hide");
				$("#table_list_2").trigger('reloadGrid');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}

function saveTaskPlanSet(){
    var partCode = $("#partCode").val();
    var planDays = $("#planDays").val();
    var isManualSet =$("#isManualSet").val();
	if(planDays ==""){
		alert("计划天数为必输项！");
		return;
	}
	
	var ctx = $("#ctx").val();
	var url= ctx+'/setting/saveTaskPlanSet';
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
			alert(data.message);
			if(data.success){
				$('#modal-form').modal("hide");
				$("#table_list_1").trigger('reloadGrid');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}

function delRow(id){
	if(id!=null){
	    if(confirm('谨慎操作提示,确认删除任务项配置?')){
	    	var ctx = $("#ctx").val();
	    	var url='/setting/delTaskPlanSet?';
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
						$("#table_list_1").trigger('reloadGrid');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	    }
	}else {
	   alert('请选择删除项!');
	}
   
}

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
// 清空表单
function cleanForm() {
	$(".chosen-select").attr('disabled', false);
	$("#partCode").chosen('destroy');
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
	$("#pkid").val("");
    $("#partCode").val("");
    $("#planDays").val("");
    $("#isManualSet").val("");
}
function cleanLampRuleForm(){
	$("#color").val("");
	$("#delaytime").val("");
}