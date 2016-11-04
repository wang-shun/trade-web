
define('queryFilterApi',[],function() {
	return {
		saveFilter: function(filterName,params){
			//保存filter
			if(filterName == undefined || filterName == null || filterName == "" ){
				alert("filterName不能为空");
			}
			$.ajax({
				async: false,
		        url:ctx+ "/rapidQuery/saveQueryFilter",
		        method: "post",
		        dataType: "json",
		        data: {"params":JSON.stringify(params),"filterName":filterName},
		        success: function(data){
		        	if(data.success){
		        		alert("保存成功！");
		        		$("#myModal").modal("hide");
		        		initFilterSelect();
		        	}else{
		        		alert(data.message);
		        	}
		        }
			});
		},
		findFilters:function(callBack){
			var filters ;
			$.ajax({
		        url:ctx+ "/rapidQuery/findQueryFilter",
		        method: "post",
		        dataType: "json",
		        data: {"queryId":"queryTaskListItemList"},
		        success: function(data){
		        	callBack(data);
		        }
			});
		}
	
	};
});