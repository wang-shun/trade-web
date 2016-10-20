var ctx = $("#ctx").val();//"${ctx}";
var serviceDepId = $("#serviceDepId").val();//"${serviceDepId}";

$(function(){	
	reloadGrid();
	 //全选
    $("#CheckedAll").click(function(){
        var isChecked = $(this).prop("checked");
        $('input[name=items]').prop("checked", isChecked );
        $("#work").prop("checked", false );

    });
    
    $('input[type=checkbox][name=items]').click(function(){
        var flag=true;
        $('input[type=checkbox][name=items]').each(function(){
            if(!$(this).prop("checked") == true){
                flag = false;
            }
        });
        if( flag ){
            $('#CheckedAll').prop('checked', true );
        }else{
            $('#CheckedAll').prop('checked', false );
        }
    });
    
    //工作日选择
    $("#work").click(function() {
        var isChecked = $(this).prop("checked");
        if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
            $('.work1').prop("checked", isChecked );
            $(".zhoumo").prop("checked", false );
            $("#CheckedAll").prop("checked", false );
        }else{
            $('.work1').prop("checked", false );
        }
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

});

// 查询
$('#searchButton').click(function() {
		reloadGrid();
		
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});			
});



function materialManagermentSearch(page){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "findMaterialInfoList";
	reloadGrid(params);
}


function reloadGrid(){
	var data = getParams();
	
	$("#materialInfoList").reloadGrid({
    	ctx : ctx,
		queryId : "findMaterialInfoList",
	    templeteId : 'template_materialInfoList',
	    data : data,
	    wrapperData : data
    });
}


function getParams() {
	
	var inputTimeStart = null;
	var inputTimeEnd = null;
	var outputTimeStart = null;
	var outputTimeEnd = null;	
	var backTimeStart = null;
	var backTimeEnd = null;
	var actionTimeStart = null;
	var actionTimeEnd = null;

	
	// 设置查询参数
	var params = {};
	var start = $('#dtBegin_0').val();
	var end = $('#dtEnd_0').val();
	if (end && end != '') {
		end = end + ' 23:59:59';
	}
	
	// 案件编号
	var caseCode = $("#caseCode").val().trim();
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}

	// 物业地址
	var propertyAddr = $("#propertyAddr").val().trim();
	if (propertyAddr == "" || propertyAddr == null) {
		propertyAddr = null;
	}
	// 提交人
	var createBy = $("#createBy").val().trim();
	if (createBy == "" || createBy == null) {
		createBy = null;
	}
	// 保管人
	var itemManager = $("#itemManager").val().trim();
	if (itemManager == "" || itemManager == null) {
		itemManager = null;
	}
	
	//var timeSelect = $("#loanLostCaseListTimeSelect").val();
	var itemCategory=$("#itemCategory option:selected").val();
	var itemStatus=$("#itemStatus option:selected").val();
	
	
	// 获取select 选中时间的值
	var timeSelect = $("#timeSelect option:selected").val();
	if (timeSelect == "ITEM_INPUT_TIME") {
		inputTimeStart = start;
		inputTimeEnd = end;
		params.inputTimeStart = inputTimeStart;
		params.inputTimeEnd = inputTimeEnd;
	} else if (timeSelect == "ITEM_OUTPUT_TIME") {
		outputTimeStart = start;
		outputTimeEnd = end;
		params.outputTimeStart = outputTimeStart;
		params.outputTimeEnd = outputTimeEnd;
	} else if (timeSelect == "ACTION_PRE_DATE") {
		actionTimeStart = start;
		actionTimeEnd = end;
		params.actionTimeStart = actionTimeStart;
		params.actionTimeEnd = actionTimeEnd;
	} else if (timeSelect == "ITEM_BACK_TIME") {
		backTimeStart = start;
		backTimeEnd = end;
		params.backTimeStart = backTimeStart;
		params.backTimeEnd = backTimeEnd;
	}
	
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.createBy = createBy;
	params.itemManager = itemManager;
	params.itemCategory = itemCategory;
	params.itemStatus = itemStatus;
	
	return params;
}

//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

//通过复选框 设置全选 和  全不选
function mycheck(a) {
 	 var temp = $("[name=materialCheck]:checkbox");//document.getElementsByName("love");
	 if (a.checked == true) {
  		for ( var i = 0; i < temp.length; i++) {
   			var val = temp[i];
   			val.checked = true;
  			}
 	 }else{
  		for ( var i = 0; i < temp.length; i++) {
   		var val = temp[i];
   		val.checked = false;
  	}
  }
}
//判断复选框是否选中
function getCheck(){
	var pkids = ''; 
	var flag = 0; 	
	var ids="";	
	$("input[type=checkbox][name='materialCheck']").each(function(){ 
		if ($(this).prop("checked") == true) {			
			if($(this).attr("value")){				
				ids +=$(this).attr("value")+","; 
			}			
			flag += 1; 
		} 
	}); 	
	if(flag > 0) { 
		pkids=ids.substring(0,ids.length-1);	
		return pkids; 
	}else { 
		alert('请至少选择一条记录！'); 
		return null; 
	} 
}

$("#storage").click(function(){	

	var pkids = getCheck();	
	
	if(!caseCodeTheSameCheck()){
		return false;
	}	
	
	//请求后端数据
	if(pkids){
		var url = ctx+"/material/materialStorgae";
		$.ajax({
			cache : false,
			async : false,//false同步，true异步
			type : "POST",
			url : url,
			dataType : "json",
			data : { "pkids" : pkids},
/*			beforeSend : function() {
				$.blockUI({
					message : $("#salesLoading"),
					css : {
						'border' : 'none',
						'z-index' : '9999'
					}
				});
				$(".blockOverlay").css({
					'z-index' : '9998'
				});
			},*/
			success : function(data) {
				//$.unblockUI();
				console.log("===Result==="+JSON.stringify(data));
			},
			error : function(errors) {
			}
			
		});
	}
})
//验证勾选的复选框的caseCode
function caseCodeTheSameCheck(){	
	var flag=true;
	$("input[type=checkbox][name='materialCheck']:checked").each(function(i,e){	
		flag = compareCaseCode(this,i);
		if(flag == false){
			return false;
		}
	})			
	return flag;
}

function compareCaseCode(a,b){	
	var caseCodeFlag = true;
	var caseCodeArray = $("input[type=checkbox][name='materialCheck']:checked");	
	for(var i=0 ;i < caseCodeArray.length; i++){
		  if(i != b){
			  if($(caseCodeArray[i]).attr("kkk") != $(a).attr("kkk")){
				caseCodeFlag = false;
				alert("单次入库，请选择相同CaseCode对应的资料入库！");
			  }
		  }
		 if(caseCodeFlag==false){				
			return  false;
		 }
	}
	return caseCodeFlag;
}

