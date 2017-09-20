/**
 * 提醒清单配置 wanggh
 */
/**
 * @Author wbzhouht
 * 新增备件管理
 */
$(document).ready(function() {
					// Examle data for jqGrid
					// Configuration for jqGrid Example 1
					// /$("#case_date").addClass('btn btn-white chosen-select');
//					cleanForm();
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var partCode = $("#partCode").val();
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
						colNames : [ 'PKID','PART_CODE','ACCESSORY_CODE', '备件名称','操作'],
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
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'ACCESSORY_CODE',
							index : 'ACCESSORY_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'ACCESSORY_NAME',
							index : 'ACCESSORY_NAME',
							width : 180
						},  {
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
							window.wxc.alert(rowid);
						},
						postData : {
							queryId : "queryToAccesoryList",
							search_partCode:partCode
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
					$('#partCode').change(function() {
						searchMethod();
					});
					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
					$("#table_list_1").navGrid('#pager_list_1',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
							"#pager_list_1",
							{
								caption:"新增备件",
								onClickButton: function (){ 
									cleanForm();
									$('#modal-form').modal("show");
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
	// 环节编码
	var partCode = $('#partCode').val();
	//alert(partCode)
	//设置查询参数
	var params = {
		search_partCode : partCode
	};
	return params;
}

function rowEdit(id){
	if(id!=null){

        $("#accessoryCodeList").find("option:selected").attr("selected",false);
        $("#accessoryCodeList").attr("autocomplete","off");
	    var row = $("#table_list_1").getRowData(id);
	    $("#pkidList").val(row.PKID);
	    $("#accessoryCodeList").val(row.ACCESSORY_CODE);
        $("#accessoryCodeList").find("option[value='"+row.ACCESSORY_CODE+"']").prop("selected",true);
}
	$('#modal-form').modal("show");
}
//优化了一下提醒清单列表刷新的问题，只刷新数据，不刷新页面
function saveReminderItem(){
	if($("#accessoryCodeList").val()==""){
        window.wxc.alert("备件信息为必输项！");
        return;
	}
	$("#pkidList").val();
    var ctx = $("#ctx").val();
    var partCode1= $("#partCode").val();
    $("#partCode1").val(partCode1);
    $("#accessoryCodeList").val();
	var url=ctx+'/setting/saveToAccsoryList';
    var jsonData = $("#editForm").serializeArray();
	$.ajax({
        cache : true,
        async : false,//false同步，true异步
        type : "POST",
        url : url,
        dataType : "json",
        data : jsonData,
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
            if(data.success){
                window.wxc.success(data.message,{"wxcOk":function(){
                    $('#modal-form').modal("hide");
                    //jqGrid reload
                    $("#table_list_1").trigger('reloadGrid');
                }});
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function delRow(id){
	if(id!=null){
		window.wxc.confirm("谨慎操作提示,确认删除备件单?",{"wxcOk":function(){
	    	var ctx = $("#ctx").val();
	    	var url='/setting/deleteToAccsoryList?';
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
					if(data.success){
						window.wxc.success(data.message,{"wxcOk":function(){
							$('#modal-form').modal("hide");
							//jqGrid reload
							$("#table_list_1").trigger('reloadGrid');
						}});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	    }});
	}
   
}
// 清空表单
function cleanForm() {
	$("#pkidList").val("");
    $("#accessoryCodeList").val("");
}