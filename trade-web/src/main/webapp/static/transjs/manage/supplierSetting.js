  function checkform(){
    	if($("#supCat").val() == ""){
    		$("#supCat").css("border-color","red");
    		return false;
    	}else if($("#finOrgCode").val() == ""){
    		$("#finOrgCode").css("border-color","red");
    		return false;
    	}else if($("#finOrgName").val() == ""){
    		$("#finOrgName").css("border-color","red");
    		return false;
    	}

    	return true;
    }
  function getSelectPkid(){
  	var id=$("#table_list_1").jqGrid('getGridParam','selrow');
  	var rowData = $("#table_list_1").jqGrid('getRowData', id);
		$("#pkid").val(rowData['PKID']);
		return rowData['PKID'];
  }
    function getSupList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '供应商编号', '供应商名称', '联系人姓名', '联系人电话','供应商类型','合作等级','标签'],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60,hidden : true},
                {name: 'FIN_ORG_CODE', index: 'FIN_ORG_CODE', width: 140},
                {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', width: 140},
                {name: 'CONTACT_NAME', index: 'CONTACT_NAME', width: 140},
                {name: 'CONTACT_PHONE', index: 'CONTACT_PHONE', width: 140},
                {name: 'SUP_CAT', index: 'SUP_CAT', width: 140},
                {name: 'CO_LEVEL', index: 'CO_LEVEL', width: 140},
                {name: 'TAGS', index: 'TAGS', width: 140}
            ], 
            add: true,
            addtext: 'Add',
            pager: "#pager_list_1",
            viewrecords: true,
            cellEdit:true,
            pagebuttions: true,
            hidegrid: false,
            recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
            pgtext : " {0} 共 {1} 页",
            search:false,
            postData:{
            	queryId:"querySupPage",
            	search_supCat:$("#yuSupCat").val(),
            	search_supCode:$("#supCode").val(),
            	search_supName:$("#supName").val()
            },
            gridComplete : function() { 

            }
            
        });
    }
    function saveSup(){
    	
    	if(checkform()){
        	$.ajax({
        		url:ctx+"/setting/saveTsSup",
        		method:"post",
        		dataType:"json",
        		data:$("#addOrModifyForm").serialize(),
        		success:function(data){
        			if(data.success){
        				window.wxc.success(data.message,{"wxcOk":function(){
        					$("#modal-addOrModifyForm").modal("hide");
            		    	getSupList();
        				}});
        			}
        			$("#pkid").val("");
        		}
        	});
    	}
    }
    function delSup(){
    	$.ajax({
    		url:ctx+"/setting/delTsSup",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
    			window.wxc.success(data.message,{"wxcOk":function(){
    				getSupList();
    				$("#pkid").val("");
    			}});
    		}
    	});
    }
    function getSupInfo(){
       	$.ajax({
    		url:ctx+"/setting/getTsSupInfo",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
    			if(data.success){
    				$("#modal-addOrModifyForm").modal("show");
    				$("#finOrgCode").val(data.content.finOrgCode);
    				$("#finOrgName").val(data.content.finOrgName);
    				$("#supCat").val(data.content.supCat);
    				$("#contactName").val(data.content.contactName);
    				$("#contactPhone").val(data.content.contactPhone);
    				$("#relFinOrgCode").val(data.content.relFinOrgCode);
    				$("#relFinOrgCode").val(data.content.relFinOrgCode);
    				$("#coLevel").val(data.content.coLevel);
    				$("#tags").val(data.content.tags);
    			}else{
    				window.wxc.error(data.message);
    			}
    		}
    	});
    }
    var finOrgs = null;
    function getFinOrgs(){
    	$.ajax({
            url: ctx+"/manage/queryAllFinOrg",
            dataType: "json",
    		async:false,
            data:{           
            },
            success: function( data ) {
            	finOrgs = data;
            }
        });
    }
//    this.setSelectedFinOrg = function (finOrg) {
//        $("#finOrgCode").val(finOrg.finOrgCode);
//    };

    $(function(){

    	getFinOrgs();
    	
       	$("#finOrgName").typeahead({
            source: finOrgs,
            display: "finOrgName",    
            val: "finOrgCode",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#finOrgName").val(text);

            	$("#finOrgCode").val(val);
            },sorter: function (items) {
            var that = this,
                beginswith = [],
                ignoreCase =[],
                caseSensitive = [],
                caseInsensitive = [],
                item;

            while (item = items.shift()) {
            	if(this.query == item[that.options.display]){//full match
            		beginswith.push(item);
            	}
            	else if (!item[that.options.display].toLowerCase().indexOf(this.query.toLowerCase())) {
                	ignoreCase.push(item);
                }
                else if (~item[that.options.display].indexOf(this.query)) {
                    caseSensitive.push(item);
                }
                else {
                    caseInsensitive.push(item);
                }
            }

            return beginswith.concat(ignoreCase,caseSensitive, caseInsensitive);
        }
       	});
    	
  /*  	$("#finOrgName").typeahead({
            source: function(query, process ) {
           	  var results = $.map(finOrgs, function (finOrg) {
                    return finOrg.finOrgName + "";
                }); 
                process(results);
            },
	       	updater: function (id) {
	       		var finOrg = null;
	       		for(var i = 0;i<finOrgs.length;i++){
	       			if(id == finOrgs[i].finOrgName){
	       				finOrg = finOrgs[i];
	       				break;
	       			}
	       		}
	       		setSelectedFinOrg(finOrg);	
	            return finOrg.finOrgName;
	        }
   	});*/
    	$("input").each(function(){
    		$(this).blur(function(){
    			if($(this).val() != ""){
					$(this).css("border-color","#e5e6e7");
    			}
    		});
    	});
    	$("#supCat").blur(function(){
			if($(this).val() != ""){
				$(this).css("border-color","#e5e6e7");
			}
    	});
    	$("#searchButton").click(function(){
    		getSupList();
    	});
    	getSupList();
    	$("#addBtn").click(function(){
    		$("#modal-addOrModifyForm input[type='text']").val("");
    		$("#modal-addOrModifyForm input[type='hidden']").val("");

    		$("#modal-addOrModifyForm select").val("");
    		$("pkid").val("");
    		$("#finOrgCode").removeAttr('readonly');
    		$("#modal-addOrModifyForm").modal("show");
    	});
    	$("#modifyBtn").click(function(){
    		getSelectPkid();
    		if($("#pkid").val()==""){
    			window.wxc.alert("请选择要修改的记录！");
    			return;
    		}
    		$("#finOrgCode").attr('readonly','readonly');
    		getSupInfo();

    	});
    	$("#delBtn").click(function(){
    		getSelectPkid();
    		if($("#pkid").val() == ""){
    			window.wxc.alert("请选择要删除的记录！");
				return;
    		}
    		
    		window.wxc.confirm("确定要删除该供应商？",{"wxcOk":function(){
    			delSup();
    		}});
    	});
    	$("#saveOrModifyBtn").click(function(){

    		saveSup();
    	});

    });
    