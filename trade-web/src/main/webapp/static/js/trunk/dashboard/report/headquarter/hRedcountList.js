/**
 * 红绿灯统计报表
 * @author liaohail
 */

var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/report/headquarter/getRedcountList";
	url = ctx + url;
	 
		  $.ajax({
				cache : false,
				async : false,
				type : "POST",
				url : url,
				dataType : "json",
				height : 650,
				data :"",
				success : function(data) {
					jQuery("#table_redcount_lists").jqGrid("clearGridData");
					jQuery("#table_redcount_lists").jqGrid({
						datatype: "local", 
						 colNames : ['区域', '红灯案件数'],
						 colModel : [ 
				                     {name : 'orgName',index : 'orgName',width : 50,align : "left",resizable : true},
				                     {name : 'redNum',index : 'redNum',width : 50,align : "left",resizable : true}, 
				                   ],
						rowNum:10,
						rowList:[ 10,20,30 ],
						pager:"#pager_redcount_lists",
						viewrecords:true,
						multiselect:false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
					}).navGrid("#pager_redcount_lists",{
						edit:false,
						add:false,
						del:false,
						search : false
					});
					for(var k=0;k<data.length;k++){
				        jQuery("#table_redcount_lists").jqGrid('addRowData',k,data[k]);
				        //jQuery("#table_report_lists").jqGrid('addRowData',data);
					}
				    
				    $("#table_redcount_lists").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
					jQuery("#table_redcount_lists").jqGrid('navGrid','#pager_redcount_lists',{edit:false,add:false,del:false}); 
					jQuery("#table_redcount_lists").setGridWidth($(window).width()*0.37);
					jQuery("#table_redcount_lists").setGridHeight($(window).width()*0.2);
				},
				error : function(data) {
					alert(data.message);  
				}
			 });
});
//区域统计搜索
function searchRedcount(){
	var numStr = $("#numStr").val();
	var numEnd = $("#numEnd").val();
	var url = "/report/headquarter/getRedcountList?strNum="+numStr+"&endNum="+numEnd;
	url = ctx + url;
		  $.ajax({
				cache : false,
				async : false,
				type : "GET",
				url : url,
				dataType : "json",
				height : 650,
				success : function(data) {
					jQuery("#table_redcount_lists").jqGrid("clearGridData");
					jQuery("#table_redcount_lists").jqGrid({
						datatype: "local", 
						colNames : ['区域', '红灯案件数'],
						 colModel : [ 
				                     {name : 'orgName',index : 'orgName',width : 50,align : "left",resizable : true},
				                     {name : 'countJDS',index : 'countJDS',width : 50,align : "left",resizable : true}, 
				                   ],
						rowNum:10,
						rowList:[ 10,20,30 ],
						pager:"#pager_redcount_lists",
						viewrecords:true,
						multiselect:false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
					}).navGrid("#pager_redcount_lists",{
						edit:false,
						add:false,
						del:false,
						search : false
					});
					for(var k=0;k<data.length;k++){
				        jQuery("#table_redcount_lists").jqGrid('addRowData',k,data[k]);
				        //jQuery("#table_report_lists").jqGrid('addRowData',data);
					}
				    
				    $("#table_redcount_lists").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
					jQuery("#table_redcount_lists").jqGrid('navGrid','#pager_redcount_lists',{edit:false,add:false,del:false}); 
					jQuery("#table_redcount_lists").setGridWidth($(window).width()*0.37);
					jQuery("#table_redcount_lists").setGridHeight($(window).width()*0.2);
				},
				error : function(data) {
					alert(data.message);  
				}
			 });
}
