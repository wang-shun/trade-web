/**
 * 
 */

//初始化数据
                        ctx=$("#ctx").val();
						var params = {
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val()
						};

					 	$('#exportBtn').click(function(){					    	
					    	$.exportExcel({
					    		ctx : ctx,
					    		method:"post",
					    		queryId : "ToSpvCaseFlowListQuery",
					    		colomns : ['CASHFLOW_APPLY_CODE','SPV_CODE','amount',
					    		           'USAGE', 'PR_ADDR', 'applyerName',
					    		            'applyAuditorName',
					    		           'ftPreAuditorName', 'ftPostAuditorName','STATUS'],
					    		data:""
					    		});
					    	
					    }); 
						//清空数据
						//清空数据
						function clearForm(){  
						 $("#serachForm").find("input").val("")
						 $("#status option:first").prop("selected", 'selected');
						/*  $("#qicao option:first").prop("selected", 'selected'); */
						}
						//查询
						$("#btn_search").click(function() {
							          params.search_spvCode=$("input[name='spvCode']").val();
							          params.search_usage=$("select[name='usage']").val();
							          params.search_applier=$("input[name='applier']").val();
							          params.search_prAddress=$("input[name='prAddress']").val();
											initData();
											
						})
						function show(pkid){
							var hide=$("#cashFlow"+pkid).css("display");
							$("#cashFlow"+pkid).slideToggle("fast");
							$("#caozuo"+pkid).html(hide=="none"?"收起":"展开");
							if(hide!="none")return;
							$.ajax({
								url:ctx+"/quickGrid/findPage",
								 method:"post",
								 dataType:"json",
								 data:{
									    page : 1,
										rows : 12,
										queryId:'ToSpvCashFlowById',
										id:pkid
								 },
								 success:function(data){
									 var tbodyhtml=$("#tbody"+pkid);
									 tbodyhtml.html("");
									 var htmlText="";
									$.each(data.rows,function(i,item){
										htmlText+="<tr><td>"+item.VOUCHER_NO+"</td>";
										htmlText+="<td> <p class='big'>"+(item.AMOUNT>0?item.AMOUNT/10000:0)+"万元</p></td>";
										htmlText+="<td> <p class='big'>"+item.DIRECTION+"</p></td>";
										htmlText+="<td> <p>"+item.PAYER+"&nbsp;&nbsp;"+item.PAYER_ACC+"/"+item.PAYER_BANK+"</p></td>";
										htmlText+="<td> <p>"+item.RECEIVER+"&nbsp;&nbsp;"+item.RECEIVER_ACC+"/"+item.RECEIVER_BANK+"</p></td></tr>";
									})
									 tbodyhtml.html(htmlText);	
							
							}
						  })
						}
						//加载流水申请页面
						function initData() {
							$(".bonus-table")
									.aistGrid({
												ctx : ctx,
												queryId : 'ToSpvCaseFlowApplyListQuery',
												   rows : '12',
												templeteId : 'querSpvCaseFlowApplyList',
												gridClass : 'table table_blue table-striped table-bordered table-hover ',
												data : params,
												wrapperData : {job : $("#serviceJobCode").val()},
												columns : [
														{
																   colName :"<span style='color:#ffffff' onclick='caseCodeSort();' >流水申请编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
	/* 								    		    	           sortColumn : "SPV_CODE",
									    		    	           sord: "desc",
									    		    	           sortActive : true */
														},{
															colName : "监管合约编号"
														},{
															colName : "金额"
														},{
															colName : "类别"
														}, {
															colName : "物业地址"
														}, {
															colName : "创建"
														}, {
															colName : "审批状态"
														} , {
															colName : "操作"
														}  ]

											});
						}
						//加载流水页面
						function initFlowListData() {
							$(".bonus-table")
									.aistGrid({
												ctx : ctx,
												queryId : 'ToSpvCaseFlowListQuery',
												   rows : '12',
												templeteId : 'querSpvCaseFlowList',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : params,
												wrapperData : {job : $("#serviceJobCode").val()},
												columns : [
														{
																   colName :"<span style='color:#ffffff' onclick='caseCodeSort();' >流水申请编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
	/* 								    		    	           sortColumn : "SPV_CODE",
									    		    	           sord: "desc",
									    		    	           sortActive : true */
														},{
															colName : "金额"
														},{
															colName : "账户信息"
														}, {
															colName : "审批状态"
														}, {
															colName : "物业地址"
														}  ]

											});
						}
						//排序
						function caseCodeSort(){
							if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
								$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
							}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
								$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
							}else{
								$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
							}
						}
						/**
						 * 选择用户
						 * @param param
						 */
						function userSelect(param){
							var options = {
							        dialogId : "selectUserDialog", //指定别名，自定义关闭时需此参数
							        dialog : { 
										height: 463
									   ,width: 756
									   ,title:'选择用户'
									   ,url: appCtx['aist-uam-web']+'/userOrgSelect/userSelect.html'
									   ,data:param
									   ,buttons: [
							                      { text: '确定', onclick: function (item, dialog) { dialog.frame.save();}},
							                      { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
							                   ]
									}
							    };
							openDialog(options);
						} 
						/**
						 * 更新input的值
						 */
						function selectUserBack(array){
							if(array && array.length >0){
								 $("#realName").val(array[0].username);
								$("#userName").val(array[0].userId);
							}else{
								 $("#realName").val("");
								$("#userName").val("");
							}
						}

						