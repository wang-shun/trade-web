/**
 * 已受理产调
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	url = ctx + url;
	//jqGrid 初始化
	$("#table_property_list").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'PKID','行政区域','交易单编号','产调编号','物业地址', '产调项目','所属分行',
		             '产调申请人', '产调申请时间',
		             '产调受理时间','状态','附件','是否有效','无效原因','操作'],
		colModel : [ {
			name : 'PKID',
			index : 'PKID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		},{
			name : 'DIST_CODE',
			index : 'DIST_CODE',
			align : "center",
			width : 30
			
		},{
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			hidden : true
		},{
			name : 'PR_CODE',
			index : 'PR_CODE',
			align : "center",
			width : 0,
			hidden : true
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 60
		}, {
			name : 'PR_CAT',
			index : 'PR_CAT',
			width : 40
		},  {
			name : 'orgName',
			index : 'orgName',
			width : 40
		},{
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		}, {
			name : 'PR_APPLY_TIME',
			index : 'PR_APPLY_TIME',
			width : 50
		}, {
			name : 'PR_ACCPET_TIME',
			index : 'PR_ACCPET_TIME',
			width : 50
		},{
			name : 'PR_STATUS',
			index : 'PR_STATUS',
			width : 20
		},{
			name : 'act',
			index : 'act',
			align : "center",
			width : 40,
			resizable : false,
			sortable : false,
			title : false
		},{
			name : 'IS_SUCCESS',
			index : 'IS_SUCCESS',
			width : 30
		},{
			name : 'UNSUCCESS_REASON',
			index : 'UNSUCCESS_REASON',
			width : 30
		},{
			name : 'nullityTag',
			index : 'nullityTag',
			align : "center",
			width : 40,
			formatter : nullityTag
			
		},
		
		],
		multiselect: true,
		pager : "#pager_property_list",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		onSelectRow : function(rowid,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_property_list").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseDistributeButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	        	$("#caseDistributeButton").attr("disabled", true);
    		}
		},
		gridComplete:function(){
			var ids = jQuery("#table_property_list").jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) { 
				var rowDatas = $("#table_property_list").jqGrid('getRowData', ids[i]); //获取当前行
				btn2="<button type='button' onclick='addFiles(\""+ctx+"\","+rowDatas.PKID+",\""+rowDatas.PR_CODE+"\");' class='btn red' >上传附件</button>";
				jQuery("#table_property_list").jqGrid('setRowData', ids[i], { act : btn2}); 
			} 
		},
		postData : {
			queryId : "queryProcessingList",
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus
		}
	});
	function nullityTag(cellvalue, options, rawObject){
		var nullityTag = "<button type='button' onclick='nullityTag(\""+rawObject.CASE_CODE+"\","+rawObject.PKID+");' class='btn red' >标记无效</button>";
			return nullityTag;
	}
	
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

//上传附件
function addFiles(ctx,rowid,prCode){
	$('#addFiles').attr("href",ctx+"/property/box/addFiles?id="+rowid+"&prCode="+prCode);
	$("#addFiles").trigger('click');
}
//标记无效
function nullityTag(caseCode,pkid){
	$('#nullityTag').attr("href",ctx+"/property/box/nullityTag?id="+pkid+"&caseCode="+caseCode);
	$("#nullityTag").trigger('click');
}
/**
 * 处理已受理产调
 */
var pkidLsit ;
function propertyDispose(){
	if(!confirm('是否确认处理产调')){return ;}
	 var prCodeArray = new Array();
	
	 pkidList = jQuery("#table_property_list").jqGrid('getGridParam', 'selarrrow');
	 for(var i = 0;i<pkidList.length;i++){
		 var list=$("#table_property_list").jqGrid('getRowData',pkidList[i]);
		 prCodeArray.push(list.PR_CODE);
	 }
	/* //查询是否上传附件
	 var ids = jQuery("#table_property_list").jqGrid('getDataIDs');
	 for ( var i = 0; i < ids.length; i++) { 
			var rowDatas = $("#table_property_list").jqGrid('getRowData', ids[i]); //获取当前行
			caseArray.push(rowDatas.CASE_CODE);
		} */
	 
	 $.ajax({
			cache : false,
			type : "GET",
			url : ctx+'/property/isExistFile?prCodeArray='+prCodeArray,
			dataType : "json",
			data :"",
			success : function(data) {
				if(data.success == false){
					alert(data.message);
				}else{
					commitDispose();
				}
			},
			error : function(errors) {
				alert("处理出错,请刷新后再次尝试！");
			}
		});
	}
	//处理产调
	function commitDispose(){
		$.ajax({
			cache : false,
			type : "POST",
			url : ctx+'/property/updateProcessingListStatus',
			dataType : "json",
			data : [{
				name : 'pkidList',
				value : pkidList
			}],
			success : function(data) {
				alert(data.message)
				if(data.success){
					location.reload();
				}
			},
			error : function(errors) {
				alert("处理出错,请刷新后再次尝试！");
			}
		});
	}