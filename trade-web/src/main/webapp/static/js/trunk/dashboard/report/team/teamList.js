/**
 * 统计报表
 * @author liaohail
 */

var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/report/team/countData";
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
					jQuery("#table_report_lists").jqGrid("clearGridData");
					jQuery("#table_report_lists").jqGrid({
						datatype: "local", 
						 colNames : ['个人', '接单数', '签约数','过户数', '结案数' ],
						 colModel : [ 
				                     {name : 'orgName',index : 'orgName',width : 60,align : "left",resizable : true},
				                     {name : 'countJDS',index : 'countJDS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countQYS',index : 'countQYS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countGHS',index : 'countGHS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countJAS',index : 'countJAS',width : 30,align : "left",resizable : true}
				                   ],
						rowNum:10,
						rowList:[ 10,20,30 ],
						pager:"#pager_report_lists",
						viewrecords:true,
						multiselect:false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
					}).navGrid("#pager_report_lists",{
						edit:false,
						add:false,
						del:false,
						search : false
					});
					for(var k=0;k<data.length;k++){
				        jQuery("#table_report_lists").jqGrid('addRowData',k,data[k]);
				        //jQuery("#table_report_lists").jqGrid('addRowData',data);
					}
				    
				    $("#table_report_lists").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
					jQuery("#table_report_lists").jqGrid('navGrid','#pager_report_lists',{edit:false,add:false,del:false}); 
					jQuery("#table_report_lists").setGridWidth($(window).width()*0.34);
					jQuery("#table_report_lists").setGridHeight($(window).width()*0.2);
				},
				error : function(data) {
					alert(data.message);  
				}
			 });
});
//区域统计搜索
function searchCount(){
	var startDate = $("#dtBegin_0").val();
	var endDate = $("#dtEnd_0").val();
	var url = "/report/team/countData?startDate="+startDate+"&endDate="+endDate;
	url = ctx + url;
		  $.ajax({
				cache : false,
				async : false,
				type : "GET",
				url : url,
				dataType : "json",
				height : 650,
				success : function(data) {
					jQuery("#table_report_lists").jqGrid("clearGridData");
					jQuery("#table_report_lists").jqGrid({
						datatype: "local", 
						 colNames : ['个人', '接单数', '签约数','过户数', '结案数' ],
						 colModel : [ 
				                     {name : 'orgName',index : 'orgName',width : 60,align : "left",resizable : true},
				                     {name : 'countJDS',index : 'countJDS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countQYS',index : 'countQYS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countGHS',index : 'countGHS',width : 30,align : "left",resizable : true}, 
				                     {name : 'countJAS',index : 'countJAS',width : 30,align : "left",resizable : true}
				                   ],
						rowNum:10,
						rowList:[ 10,20,30 ],
						pager:"#pager_report_lists",
						viewrecords:true,
						multiselect:false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
					}).navGrid("#pager_report_lists",{
						edit:false,
						add:false,
						del:false,
						search : false
					});
					for(var k=0;k<data.length;k++){
				        jQuery("#table_report_lists").jqGrid('addRowData',k,data[k]);
				        //jQuery("#table_report_lists").jqGrid('addRowData',data);
					}
				    
				    $("#table_report_lists").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
					jQuery("#table_report_lists").jqGrid('navGrid','#pager_report_lists',{edit:false,add:false,del:false}); 
					jQuery("#table_report_lists").setGridWidth($(window).width()*0.34);
					jQuery("#table_report_lists").setGridHeight($(window).width()*0.2);
				},
				error : function(data) {
					alert(data.message);  
				}
			 });
}

//加载报表数据
function initMorris(){
	var pkid  = $("#pkid").val();
	var events = [];
	$.ajax({
		url:ctx+'/report/team/getCaseCount?id='+pkid,
		data: "",
		type: "GET",
		dataType: "json",
		async : false,
		success: function(data) {
			var voList = data.voList;
			for (var i = 0; i < voList.length; i++) {
				var period = ""+voList[i].createTime+"";
				if(undefined != voList[i].countJDS)	var a = voList[i].countJDS;
				else var a = 0;
				
				if(undefined != voList[i].countQYS) var b = voList[i].countQYS; 
				else var b = 0;
				
				if(undefined != voList[i].countGHS) var c = voList[i].countGHS;
				else var c = 0;
				
				if(undefined != voList[i].countJAS ) var d = voList[i].countJAS;
				else var d = 0;
				
				events.push({
					"period": period,
					"a": a,
					"b": b,
					"c": c,
					"d": d
				});
			}
	}
 });
	return events;
}








