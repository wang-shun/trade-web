
    function getCashFlowList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '案件编号','产证地址', '类型', '流水方向', '金额（万元）','时间'],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60},
                {name: 'CASE_CODE', index: 'CASE_CODE', width: 140},
                {name: 'PROPERTY_ADDR', index: 'PROPERTY_ADDR', width: 180},
                {name: 'CASH_FLOW_TYPE', index: 'CASH_FLOW_TYPE', width: 140},
                {name: 'FLOW_DIRECTION', index: 'FLOW_DIRECTION', width: 140},
                {name: 'FLOW_AMOUNT', index: 'FLOW_AMOUNT', width: 100},
                {name: 'FLOW_TIME', index: 'FLOW_TIME', width: 140}

            ], 
            add: true,
            addtext: 'Add',
            pager: "#pager_list_1",
            viewrecords: true,
            pagebuttions: true,
            hidegrid: false,
            recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
            pgtext : " {0} 共 {1} 页",
            search:false,
            postData:{
            	queryId:"queryCashFlowPage",
            	search_caseCode:$("#caseCode").val(),
            	search_cashFlowType:$("#cashFlowType").val(),
            	search_cashDirection:$("#cashDirection").val(),
            	search_startTime:$("#startTime").val(),
            	search_endTime:$("#endTime").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
			}
            
        });
        $("#table_list_1").navGrid('#pager_list_1',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
        		"#pager_list_1",
        		{
        			caption:"导出excel",	
        			onClickButton: function (){ 
        				exportExcel();
        		}
        });
    }
    
    function exportExcel(){
    		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('CASH_FLOW_TYPE');
		displayColomn.push('FLOW_DIRECTION');
		displayColomn.push('FLOW_AMOUNT');
		displayColomn.push('FLOW_TIME');

		var queryId = '&queryId=queryCashFlowPage';
		var colomns = '&colomns=' + displayColomn;
		var url = ctx+"/quickGrid/findPage?xlsx&";
		url += queryId + colomns;
		url += "&caseCode="+$("#caseCode").val()+"&cashFlowType="+$("#cashFlowType").val();
		url += "&cashDirection="+$("#cashDirection").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();

		$('#excelForm').attr('action', url);
		$('#excelForm').submit();
    	 
    }

    $(function(){

    	getCashFlowList();
  
    });
    
    
    