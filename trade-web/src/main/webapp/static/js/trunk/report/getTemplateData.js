function initData(url,data,templateId,tbodyId) {
			aist.wrap(data);
			var sortColumn=$('span.active').attr("sortColumn");
			var sortgz=$('span.active').attr("sord");
			data.sidx=sortColumn;
			data.sord=sortgz;
			$.ajax({
				async : true,
				url : url,
				method : "post",
				dataType : "json",
				data : data,
				success : function(data) {
					if(data==null||data==undefined){
	                    window.parent.wxc.alert("数据加载失败！");
						return;			
					}
					data.ctx = ctx;
					var templateData = template(templateId, data);
					$("#"+tbodyId).empty();
					$("#"+tbodyId).html(templateData);
					// 显示分页 
			        initpage(data.total,data.pagesize,data.page, data.records,tbodyId);
				},
				error : function(e, jqxhr, settings, exception) {
					//$.unblockUI();
				}
			});
		}
		function initpage(totalCount,pageSize,currentPage,records,tbodyId) {
			
			if(totalCount>1500){
				totalCount = 1500;
			}
			var currentTotalstrong=$('#currentTotalPage').find('strong');
			if (totalCount<1 || pageSize<1 || currentPage<1){
				$(currentTotalstrong).empty();
				$('#totalP').text(0);
				$("#pageBar").empty();
				return;
			}
			$(currentTotalstrong).empty();
			$(currentTotalstrong).text(currentPage+'/'+totalCount);
			$('#totalP').text(records);
			
			
			$("#pageBar").twbsPagination({
				totalPages:totalCount,
				visiblePages:9,
				startPage:currentPage,
				first:'<i class="fa fa-step-backward"></i>',
				prev:'<i class="fa fa-chevron-left"></i>',
				next:'<i class="fa fa-chevron-right"></i>',
				last:'<i class="fa fa-step-forward"></i>',
				showGoto:true,
				onPageClick: function (event, page) {
					if(tbodyId=="tableTemplate1"){
						reloadGrid1(page)
					}else if(tbodyId=="tableTemplate2"){
						reloadGrid2(page)
					}else{
					    reloadGrid(page);
					}
			    }
			});
		}
		//得到数据快照月份
		function getBelongMonth(choiceMonth){
			var minBelongMoth = 201702;
			if(choiceMonth==''||choiceMonth=='undefined'||choiceMonth==undefined){
				return minBelongMoth
			}
			try{
				var belongMoth = parseInt(choiceMonth.replace('-',''));
				if(belongMoth<minBelongMoth){
					belongMoth =  minBelongMoth
				}
				return belongMoth;
			}catch(e){
				return minBelongMoth
			}
		}