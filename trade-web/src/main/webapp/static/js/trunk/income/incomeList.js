
    function getIncomeList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 200,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['收入名目', '归属案件地址', '收入总数', '获得比例','最终金额','记佣归属月份'],
            colModel: [
                {name: 'INCOME_ITEM', index: 'INCOME_ITEM',  width: 200},
                {name: 'PROPERTY_ADDR', index: 'PROPERTY_ADDR', width: 200},
                {name: 'INCOME_AMOUNT', index: 'INCOME_AMOUNT', width: 120},
                {name: 'OR_PAR', index: 'OR_PAR', width: 140},
                {name: 'OR_AMOUNT', index: 'OR_AMOUNT', width: 140},
                {name: 'INCOME_BELONG_DAY', index: 'INCOME_BELONG_DAY', width: 140},

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
            	queryId:"incomeDetailList",
            	search_orOwnverId:$("#orOwnverId").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
			}
            
        });
    }
    
    function getAwardDispatchList(){
    	$("#table_list_2").jqGrid("GridUnload");
    	$("#table_list_2").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 200,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['案件地址', '基本奖金', 'KPI', '最终金额','记佣归属月份'],
            colModel: [
                {name: 'PROPERTY_ADDR', index: 'PROPERTY_ADDR',  width: 200},
                {name: 'BASE_AMOUNT', index: 'BASE_AMOUNT', width: 140},
                {name: 'KPI', index: 'KPI', width: 120},
                {name: 'FINAL_AMOUNT', index: 'FINAL_AMOUNT', width: 140},
                {name: 'DISPATCH_TIME', index: 'DISPATCH_TIME', width: 140}
            ], 
            add: true,
            addtext: 'Add',
            pager: "#pager_list_2",
            viewrecords: true,
            pagebuttions: true,
            hidegrid: false,
            recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
            pgtext : " {0} 共 {1} 页",
            search:false,
            postData:{
            	queryId:"queryAwardDispatchList",
            	search_participant:$("#orOwnverId").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
			}
            
        });
    }
    
    $(function(){
    	getIncomeList();
    	getAwardDispatchList();

    	$("#searchButton").click(function(){
    	//	getFinOrgList();
    	});    	

    });
    