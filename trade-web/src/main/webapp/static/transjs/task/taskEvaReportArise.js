	var attachmentList = null;
	var files = null;
	var attachmentIds = null;
	var preFileAdress = null;
	var picTag = null;
	var picName = null;
	function getUploadPicInfo() {
		attachmentIds =[];
		preFileAdress = [];
		picTag = [];
		picName = [];

		// 图片的ID
		$("input[name='preFileAdress']").each(function(){
			preFileAdress.push($(this).val());
		});
		
		$("input[name='picTag']").each(function(){
			picTag.push($(this).val());
		});
		$("input[name='picName']").each(function(){
			picName.push($(this).val());
		});
		$("input[name='attachmentId']").each(function(){
			attachmentIds.push($(this).val());
		});
		
		return false;
	}
	//查询评估公司
	function getEvaCompany(){
		$.ajax({
			url:ctx+"/manage/queryEvaCompany",
			method:"post",
			dataType:"json",
			data:{},
			success:function(data){
				if(data != null && data.length > 0){
					var foc = $("#reportForm").find("select[name='finOrgCode']");
					foc.html("");
					for(var i=0;i<data.length;i++){
						foc.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
					}
				}
			}
		});
	}
	
	//完成提交报告单流程
	function completeReport(){
		$.ajax({
			url:ctx+"/task/completeEvaReport",
			method:"post",
			dataType:"json",
			data:{caseCode:$("#caseCode").val(),taskId:$("#taskId").val(),processInstanceId:$("#processInstanceId").val()},
			success:function(data){
				if(data != null && data.message != ""){
//					alert(data.message);
//					window.location.href=ctx+"/task/myTaskList?"+new Date().getTime();
					caseTaskCheck();
				}
			}
		});
	}
	function getReportList(){
		$("#table_list_1").jqGrid("GridUnload");
		$("#table_list_1").jqGrid({
	    	url:ctx+"/quickGrid/findPage",
	        datatype: "json",
	        height: 150,
	        autowidth: true,
	        shrinkToFit: true,
	        rowNum: 8,
	        colNames: ['序号', '评估编号', '报告序列号', '报告类型', '当前状态','状态时间', '目标银行','是否最终报告', '是否接受报告结果'],
	        colModel: [
	            {name: 'PKID', index: 'PKID',  width: 60},
	            {name: 'EVA_CODE', index: 'EVA_CODE', width: 140},
	            {name: 'SERIAL_NUMBER', index: 'SERIAL_NUMBER', width: 140},
	            {name: 'REPORT_TYPE', index: 'REPORT_TYPE', width: 140},
	            {name: 'FEEDBACK', index: 'FEEDBACK', width: 140},
	            {name: 'REPORT_RESPONSE_TIME', index: 'REPORT_RESPONSE_TIME', width: 140},
	            {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', width: 240},
	            {name: 'IS_FINAL_REPORT', index: 'IS_FINAL_REPORT', width: 0,hidden:true},
	            {name: 'accept', index: 'accept',align:'center',width: 150}
	           
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
	        	queryId:"queryToEvaReportPage",
	        	search_evaCode:$("#eva_code").val(),
	        	search_caseCode:$("#caseCode").val()
	        },
	        gridComplete : function() { 
	        	var ids = jQuery("#table_list_1").jqGrid('getDataIDs'); 
	        						
	        	for (var i=0; i<ids.length; i++) { 
	        		var rowDatas = $("#table_list_1").jqGrid('getRowData', ids[i]); // 获取当前行
	        		var sub="<button  class='btn red' id='report_btn_'"+rowDatas['PKID']+"' onclick='confirmReport(\""+rowDatas['PKID']+"\")' style='width:90px;'>确认</button>";
	        		if(rowDatas['IS_FINAL_REPORT'] == "1"){
	            		 sub="<button  class='btn red' disabled='disabled' style='width:90px;'>已确认</button>";
	        		}					
	        		jQuery("#table_list_1").jqGrid('setRowData', ids[i], {accept : sub}); 
	        			
	        	}
	        }
	        
	    });
	    $("#table_list_1").navGrid('#pager_list_1',{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
	    		"#pager_list_1",
	    		{
	    			caption:"新增报告",	
	    			onClickButton: function (){ 
	    				getEvaCompany();
	    				getAttchInfo();
	    				$("#modal-form-report").modal('show');
	    		}
	    });
	}
	function checkReportAtt(){
		var flag = true;
		for(var i=1;i<4;i++){
			var length = $("#picContainer"+i).find("img").length;
			if(length == 0) {
				alert("备件未完全上传！");
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		return flag;
	}
	var index = 0;
	$(document).ready(function () {
		getReportList();
		$("#sub").click(function(){
			completeReport();
		});
		
		var updFun = function(e) {
			var that = $(this).data('blueimp-fileupload')
					|| $(this).data('fileupload');
			that._resetFinishedDeferreds();
			that._transition($(this).find('.fileupload-progress'))
					.done(function() {
						that._trigger('started', e);
					});
		};
		
		for(var i=1;i<4;i++){
			AistUpload.init('picFileupload'+i, 'picContainer'+i,
					'templateUpload'+i, 'templateDownload'+i,
					updFun);
			$("#picContainer"+i).bind('DOMNodeInserted', function(e) {
				var picDiv=$("div[name='allPicDiv1']");
				var input=$("input[name='picTag']");
				if(picDiv.length > input.length) {
					index++;
					if(index % 2 == 0) {
						index = 0;
						$("#startUpload").click();
					}
				}
			});
		}
	    
	    $("#reportSubBtn").click(function(){
	    	if(getUploadPicInfo()){
	    		return;
	    	};
	    	if(attachmentIds.length==0&&preFileAdress.length==0){
	    		alert("图片数据不能为空！");
	        	return;
	    	}
	       	var picDiv=$("div[name='allPicDiv1']");
		    var spans =$("input[name='preFileAdress']");
		    if(spans.length < picDiv.length) {
		    	alert("你有未上传的完成的文件，请稍候再试！");
		    	return;
		    }
	    	if(!checkReportAtt()){
	    		return;
	    	}
	    	var reportType = $("#reportType").val();
	    	var evaCode = $("#eva_code").val();
	    	var caseCode = $("#caseCode").val();
	    	var url = "";
	    	var finOrgCode = $("#orgPricing").val();
	    	if(finOrgCode!=null && finOrgCode=="P00021"){
	        	if(reportType == "1"){  //预估单
	        		url = ctx+"/remote/egu/"+evaCode+"/report";
	        	}else if(reportType == "2"){ //询价单
	        		url = ctx+"/remote/egu/"+evaCode+"/inquiryreport";
	        	}else if(reportType == "3"){   //评估报告
	        		url = ctx+"/remote/egu/"+evaCode+"/prereport";
	        	}
	        	$.ajax({
	        		url:url,
	        		method:"post",
	        		dataType:"json",
	        		data:[
	        		    {name:"caseCode",value:caseCode},
	        			{name:"finOrgCode",value:finOrgCode},
	        			{name:"reportType",value:reportType},
	        			{name:"evaCode",value:evaCode},
	        			{name:"preFileAdress",value:preFileAdress},
	        			{name:"picTag",value:picTag},
	        			{name:"picName",value:picName},
	        			{name:"attachmentIds",value:attachmentIds}
	        		],  
	    		    beforeSend:function(){  
	    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	    				$(".blockOverlay").css({'z-index':'9998'});
	                },
	                complete: function() {  
	                    $.unblockUI();  
	                    if(status=='timeout'){//超时,status还有success,error等值的情况
	    	          	  Modal.alert(
	    				  {
	    				    msg:"抱歉，系统处理超时。"
	    				  });
	    		  		 $(".btn-primary").one("click",function(){
	    		  				parent.$.fancybox.close();
	    		  			});	 
	    		                }
	    		            } , 
	        		success:function(data){
	        			alert(data.message);
	    				$("#modal-form-report").modal('hide');
	    				getReportList();
	        		}
	        	});
	    	}else{
	    		if(confirm("点击该按钮将会启动线下发起报告单流程，请确认输入正确！")){

		        	$.ajax({
		        		url:ctx+"/task/submitEvaReport",
		        		method:"post",
		        		dataType:"json",
		        		data:[
		        		    {name:"caseCode",value:caseCode},
		        			{name:"finOrgCode",value:finOrgCode},
		        			{name:"reportType",value:reportType},
		        			{name:"evaCode",value:evaCode},
		        			{name:"preFileAdress",value:preFileAdress},
		        			{name:"picTag",value:picTag},
		        			{name:"picName",value:picName},
		        			{name:"attachmentIds",value:attachmentIds},
		        			{name:"taskId",value:$("#taskId").val()},
		        			{name:"processInstanceId",value:$("#processInstanceId").val()}
		        		],
		    		    beforeSend:function(){  
		    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
		    				$(".blockOverlay").css({'z-index':'9998'});
		                },
		                complete: function() {  
		                    $.unblockUI();   
		                    if(status=='timeout'){//超时,status还有success,error等值的情况
		    	          	  Modal.alert(
		    				  {
		    				    msg:"抱歉，系统处理超时。"
		    				  });
		    		  		 $(".btn-primary").one("click",function(){
		    		  				parent.$.fancybox.close();
		    		  			});	 
		    		                }
		    		            } ,    
		        		success:function(data){
		        			alert(data.message);
		    				$("#modal-form-report").modal('hide');
		    				getReportList();
	
		        		}
		        	});
	    		}
	    	}
	    });
	    
	});
	//加载已上传的附件信息
	function getAttchInfo(){
		var caseCode = $("#caseCode").val();
		 $.ajax({
		    url:ctx+"/attachment/quereyAttachments",
		    method:"post",
		    dataType:"json",
		    data:{caseCode:caseCode,partCode:"ToEvaReport"},
		    	success:function(data){
				//	dataLength=data.attList.length;

					//将返回的数据进行包装
					for(var i=1;i<4;i++){
						var trStr = "";
						$("#picContainer"+i).html("");
						//实勘描述
						$.each(data.attList,function(index, value) {
							if(value.preFileCode==i){
								trStr+="<div id='picContainer"+value.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
								trStr+="<div class=\"preview span12\">";
								trStr+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+i+"\" />";
								trStr+="<input type=\"hidden\" name=\"attachmentId\" value=\""+value.pkid+"\" />";

								trStr+="<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' alt=''>";
								trStr+="</div>";
								trStr+="<div class=\"delete span2\" style=\"margin-left: 85%; margin-top: -120px;\">";
								trStr+="<button onclick=\"romoveDiv('picContainer',"+value.pkid+");\" class=\"btn red\""; 
								trStr+="style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
								trStr+="<i class=\"icon-remove\"></i>";
								trStr+="</button>";
								trStr+="</div>";
								trStr+="</div>";
								
							}
						});
						$("#picContainer"+i).html(trStr);

					}
		    	}
		  });
	}