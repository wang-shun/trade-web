require.config({
	baseUrl : ctx + '/js',
	paths : {
		queryFilterApi : ctx + '/js/queryFilter/aist.queryFilter.api'
	}
});

define('queryFilterView',['queryFilterApi'],function(queryFilterApi) {
	var modalScript = '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
			+ ' <div class="modal-dialog"> '
			+ '	<div class="modal-content">'
			+ '		<div class="modal-header">'
			+ '			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'
			+ '				&times;'
			+ '			</button>'
			+ '			<h4 class="modal-title" id="myModalLabel">'
			+ '请填写名称'
			+ '			</h4>'
			+ '	</div>'
			+ '	<div class="modal-body">'
			+ '			<label class="control-label sign_left">'
			+ '名称:'
			+ '			</label>'
			+ '			<label> '
			+ '				<input name="filterName" id="filterName" class="sign_right input_type"   placeholder="请输入"/>'
			+ '			</label>'
			+ '		</div>'
			+ '		<div class="modal-footer">'
			+ '			<button type="button" class="btn btn-default" data-dismiss="modal">关闭'
			+ '			</button>'
			+ '		<button type="button" class="btn btn-primary" id="saveQueryFilter">'
			+ '提交更改'
			+ '		</button>'
			+ '		</div>'
			+ '	</div>'
			+ '	</div>' + '</div>';
	var filterScript = '<button id="toSaveQueryFilter" type="button" class="btn btn_blue" data-target="#myModal" data-toggle="modal">'
			+ '<i class="icon iconfont">&#xe635;</i>'
			+ '保存过滤器'
			+ '</button>'
			+ '<select class = "select_control teamcode" id="filterSelect">'
			+ '</select>';
	return {
		init : function(filterContentId,reloadGrid,initData) {
			$("body").append(modalScript);
			$("#"+filterContentId).append(filterScript);
			
			var initFilters = function (data){
	        	var filterSelect = $("#filterSelect");
	        	$("#filterSelect option").remove();
	        	filterSelect.append("<option value=''>请选择过滤器</option>");
	        	$.each(data, function (i, n){
	        		filterSelect.append("<option value="+n.argu +">" +n.filterName+"</option>");
				});
			}
			
			//填充过滤器下拉选框
			queryFilterApi.findFilters(initFilters);
			
			$("#filterSelect").change(function(){
				var params = $(this).val();
				if(params == undefined || params == null || params == ""  ){
					return ;
				}
				var jsonParams = $.parseJSON(params);
				reloadGrid(jsonParams);
			});
			
			$('#saveQueryFilter').click(function() {
				var params = initData(1);
				var filterName = $("#filterName").val();
				queryFilterApi.saveFilter(filterName,params);
				queryFilterApi.findFilters(initFilters);
			});
		}
	};
});
