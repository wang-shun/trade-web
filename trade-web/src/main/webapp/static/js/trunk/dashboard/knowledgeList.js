/**
 * 知识库操作
 * @author yumin1
 */

var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	url = ctx + url;
	
	//jqGrid 初始化
	$("#table_knowledge_lists").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 280,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'PKID','知识编码', '知识标题', '知识正文','发布时间', '发布人', '操作' ],
		colModel : [ {
			name : 'PKID',
			index : 'PKID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'KNOWLEDGE_CODE',
			index : 'KNOWLEDGE_CODE',
			//align:'center',
			resizable : true,
			width : 20
		},{
			name : 'TITLE',
			index : 'TITLE',
			//align:'center',
			resizable : true,
			width : 20
		}, {
			name : 'CONTENT',
			index : 'CONTENT',
			sortable : false,
			resizable : true,
			width : 40
		}, {
			name : 'PB_TIME',
			index : 'PB_TIME',
			align:'center',
			resizable : true,
			width : 20
		}, {
			name : 'PUBLISHER',
			index : 'PUBLISHER',
			//align:'center',
			resizable : true,
			width : 20
		},{
			name : 'act',
			index : 'act',
			align : "center",
			width : 20,
			resizable : false,
			sortable : false,
			title : false
		}],
		multiselect: true,
		pager : "#pager_knowledge_lists",
		sortname:'PB_TIME',
        sortorder:'desc',
		viewrecords : true,
		pagebuttions : true,
		multiselect:false,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		gridComplete:function(){
			var ids = jQuery("#table_knowledge_lists").jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) { 
				var rowDatas = jQuery("#table_knowledge_lists").jqGrid('getRowData',ids[i] ); //获取当前行
				var knowledgeCode = rowDatas.KNOWLEDGE_CODE;
				btn1="<button onclick='knowledgeDetail(\""+ctx+"\","+ids[i]+")' class='btn red' >详情</button>&nbsp;&nbsp;";
				jQuery("#table_knowledge_lists").jqGrid('setRowData', ids[i], { act : btn1}); 
			} 
		},
		postData : {
			queryId : "knowledgeListQuery"
		}

	});

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_knowledge_lists').setGridWidth(width);
	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

/**
 * 详情按钮事件
 * @param ctx
 * @param rowid
 */
function knowledgeDetail(ctx,rowid){
	$('#alertOper').attr("href",ctx+"/knowledge/box/toKnowledgeDetail?knowledgePkid="+rowid);
	$("#alertOper").trigger('click');
}


