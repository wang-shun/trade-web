/**
 * 知识库操作
 * @author yumin1
 */
var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	url = ctx + url;
	
	//jqGrid 初始化
	$("#table_knowledge_list").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'PKID','置顶/取消置顶','推荐/取消推荐','知识编码', '知识标题', '知识正文','发布时间', '发布人', '操作' ],
		colModel : [ {
			name : 'PKID',
			index : 'PKID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'IS_TOP',
			index : 'IS_TOP',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'IS_RECOMMAND',
			index : 'IS_RECOMMAND',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		},{
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
			width : 10
		},{
			name : 'act',
			index : 'act',
			align : "center",
			width : 50,
			resizable : false,
			sortable : false,
			title : false
		}],
		multiselect: true,
		pager : "#pager_knowledge_list",
		sortname:'PB_TIME',
        sortorder:'desc',
		viewrecords : true,
		pagebuttions : true,
		multiselect:false,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		gridComplete:function(){
			var ids = jQuery("#table_knowledge_list").jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) { 
				var rowDatas = jQuery("#table_knowledge_list").jqGrid('getRowData',ids[i] ); //获取当前行
				var knowledgeCode = rowDatas.KNOWLEDGE_CODE;
				var isTop = rowDatas.IS_TOP;
				var isRecommand = rowDatas.IS_RECOMMAND;
				var seting_NUM = 1;
				var resetting_NUM = 0;
				btn1="<button onclick='knowledgeDetail(\""+ctx+"\","+ids[i]+")' class='btn red' >详情</button>&nbsp;&nbsp;";
				btn2="<button onclick='knowledgeUpdate(\""+ctx+"\","+ids[i]+")' class='btn red' >修改</button>&nbsp;&nbsp;";
				btn3="<button onclick='knowledgeDelete(\""+ctx+"\","+ids[i]+",\""+knowledgeCode+"\")' class='btn red' >删除</button>&nbsp;";
				if(isTop == 0 ){
					btn4="<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.TOP'><button onclick='knowledgeTop(\""+ctx+"\","+ids[i]+",\""+seting_NUM+"\")' class='btn red' >置顶</button>&nbsp;</shiro:hasPermission>";	
				}else{
					btn4="<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.TOP'><button onclick='knowledgeTop(\""+ctx+"\","+ids[i]+",\""+resetting_NUM+"\")' class='btn red' >取消置顶</button>&nbsp;</shiro:hasPermission>";	
				}
				if(isRecommand == 0 ){					
					btn5="<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.RECOMM'><button onclick='knowledgeRecom(\""+ctx+"\","+ids[i]+",\""+seting_NUM+"\")' class='btn red' >推荐</button></shiro:hasPermission>";
				}else{
					btn5="<shiro:hasPermission name='TRADE.KNOWLEDGE.RELEASE.RECOMM'><button onclick='knowledgeRecom(\""+ctx+"\","+ids[i]+",\""+resetting_NUM+"\")' class='btn red' >取消推荐</button></shiro:hasPermission>";
				}
				jQuery("#table_knowledge_list").jqGrid('setRowData', ids[i], { act : btn1 + btn2 + btn3 + btn4 + btn5}); 
			} 
		},
		postData : {
			queryId : "knowledgeListQuery"
		}

	});
	$(".ui-jqgrid-bdiv").attr("style","height: 350px;");

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_knowledge_list').setGridWidth(width);
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

/**
 * 修改按钮事件
 */
function knowledgeUpdate(ctx,rowid){
	$('#alertOper').attr("href",ctx+"/knowledge/box/toKnowledgeUpdate?knowledgePkid="+rowid);
	$("#alertOper").trigger('click');
}

/**
 * 删除按钮事件
 * @param ctx  
 * @param rowid  主键pkid
 * @param knowledgeCode 知识编码
 */
function knowledgeDelete(ctx,rowid,knowledgeCode){
	if(confirm("确定要删除吗？")){
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			url :ctx+"/knowledgemtn/knowledgeDelete",
			dataType:"json",
			data : [{
					name : "knowledgePkid",
					value: rowid
				},{
					name :"knowledgeCode",  
					value:knowledgeCode
			}],
			success:function(data){
				alert(data.message);
				jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
			},
			error:function(data){
				alert(data.message);
			}
		});
	}
}
/**
 * 置顶/取消置顶
 * @param ctx
 * @param rowid
 * @param num
 */
function knowledgeTop(ctx,rowid,num){
	if(num == 0 ){		
		if(confirm("确定要取消置顶吗？")){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				url :ctx+"/knowledgemtn/isTop",
				dataType:"json",
				data : [{
						name : "pkid",
						value: rowid
					},{
						name :"isTopNum",
						value:num
				}],
				success:function(data){
					if(data.ajaxResponse.success){
						alert(data.ajaxResponse.message);
					}
					jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
				},
				error:function(data){
					alert("操作失败,请刷新后重试!");
				}
			});
		}
	}else{
		if(confirm("确定要置顶吗？")){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				url :ctx+"/knowledgemtn/isTop",
				dataType:"json",
				data : [{
						name : "pkid",
						value: rowid
					},{
						name :"isTopNum",
						value:num
				}],
				success:function(data){
					if(data.ajaxResponse.success){
						alert(data.ajaxResponse.message);
					}
					jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
				},
				error:function(data){
					alert("操作失败,请刷新后重试!");
				}
			});
		}	
		
	}
		
}
/**
 * 推荐/取消推荐
 * @param ctx
 * @param rowid
 * @param num
 */
function knowledgeRecom(ctx,rowid,num){
	if(num == 0 ){		
		if(confirm("确定要取消推荐吗？")){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				url :ctx+"/knowledgemtn/isRecommand",
				dataType:"json",
				data : [{
					name : "pkid",
					value: rowid
				},{
					name :"isRecomNum",
					value:num
				}],
				success:function(data){
					if(data.ajaxResponse.success){
						alert(data.ajaxResponse.message);
					}
					jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
				},
				error:function(data){
					alert("操作失败,请刷新后重试!");
				}
			});
		}
	}else{
		if(confirm("确定要推荐吗？")){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				url :ctx+"/knowledgemtn/isRecommand",
				dataType:"json",
				data : [{
					name : "pkid",
					value: rowid
				},{
					name :"isRecomNum",
					value:num
				}],
				success:function(data){
					if(data.ajaxResponse.success){
						alert(data.ajaxResponse.message);
					}
					jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
				},
				error:function(data){
					alert("操作失败,请刷新后重试!");
				}
			});
		}	
		
	}
	
}


/**
 * 上传附件
 */
function knowledgeFileUpload(ctx,rowid){
	$('#alertOper').attr("href",ctx+"/knowledge/box/addFiles?caseCode="+rowid);
	$("#alertOper").trigger('click');
}

/**
 *  知识发布按钮 点击跳转添加页面
 */
function knowledgePublish(){
	$('#alertOper').attr("href",ctx+"/knowledge/box/toKnowledgeAdd");
	$("#alertOper").trigger('click');
}

/**
 * 搜索按钮事件
 */
function knowledgeSearchButton(){
	var data = {};
	
	data.search_knowledgeCode =$.trim( $('#knowledgeCode').val() );  //知识编码
	data.search_knowledgeTitle =$.trim( $('#knowledgeTitle').val() );  //知识标题
	data.queryId="knowledgeListQuery";
	
	$("#table_knowledge_list").jqGrid('setGridParam',{
		datatype:'json',
		mtype:'post',
		postData:data
	}).trigger('reloadGrid');

}

/**
 * 清空按钮事件
 */
function knowledgeClearAllButton(){
    $("input[type=text]").attr("value","");
}


