	//初始化数据
						var ctx = $("#ctx").val();
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceOrgId : $("#orgId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val(),
							eloanCode : '',
							loanSrvCode : "",
							status : 1,
							startDate:"",
							startTime : "",
							endTime : "",
							teamCode : "",
							excutorId : "",
							address : "",
							finOrgCode : "",

						};

						/*获取银行列表*/
						function getBankList(pcode) {
							var fiCode = $("#finOrgCode").attr("value");
							var friend = $("#finOrgCode");

							$
									.ajax({
										url : ctx + "/manage/queryFin",
										method : "post",
										dataType : "json",
										data : {
											"pcode" : pcode
										},
										success : function(data) {
											if (data.bankList != null) {
												for (var i = 0; i < data.bankList.length; i++) {
													if (fiCode == data.bankList[i].finOrgCode) {
														friend
																.append("<option value='"+data.bankList[i].finOrgCode+"'>"
																		+ data.bankList[i].finOrgName
																		+ "</option>");
													} else {
														friend
																.append("<option value='"+data.bankList[i].finOrgCode+"'>"
																		+ data.bankList[i].finOrgName
																		+ "</option>");
													}
												}
												friend
														.find(
																"option[value='${loanAgent.finOrgCode }']")
														.attr("selected", true);
											}
										}
									});
						}

						//选业务组织的回调函数
						function radioYuCuiOrgSelectCallBack(array) {
							if (array && array.length > 0) {
								$("#teamCode").val(array[0].name);
								$("#excutorTeam").val(array[0].id);

							} else {
								$("#teamCode").val("");
								$("#excutorTeam").val("");
							}
						}

						function selectUserBack(array) {
							if (array && array.length > 0) {
								$("#excutorId").val(array[0].username);
								$("#excutor").val(array[0].userId);

							} else {
								$("#excutorId").val("");
								$("#excutor").val();
							}
						}
						
						function getDateRange(){
							
							var dateRange = {startDate1:"",endDate1:"",startDate2:"",endDate2:""};
							var startTime = $("input[name='startTime']").val();
							var  endTime = $("input[name='endTime']").val();
					        var initTime=$("#startDate").val();
							//都为空
							if(!startTime && !endTime){
								dateRange.startDate1 = initTime;//
								dateRange.endDate2 = initTime;//
								return dateRange;
							}	
							//开始不为空
							if(startTime !="" && endTime==""){
								dateRange.startDate1=startTime;
								dateRange.endDate2=initTime;
								return dateRange;
								var date=new Date();
								if(date.getMonth()+1-5==startMonth){//开始时间小于当前时间5个月								
									dateRange.startDate1=initTime;
									dateRange.startDate2=startTime;
									dateRange.endDate2=initTime;
									dateRange.endDate1=endTime;
								}
								return dateRange;
							}
							//结束不为空
							if(startTime==""&&endTime!=""){
								var date=new Date(endTime);
								var dateString= date.getFullYear()+"-"+ (date.getMonth()+1-5)+"-"+ date.getDate();
								dateRange.startDate1=dateString;
								dateRange.endDate2=dateRange.startDate1;
								dateRange.endDate1=endTime;
								return dateRange;
							}//都不为空
							if(startTime!="" &&endTime!=""){
								var startMouth=new Date(startTime).getMonth()+1;
								var date=new Date(endTime);
								if((date.getMonth()+1)-startMouth>=5){
									dateRange.startDate1=date.getFullYear()+"-"+ (date.getMonth()+1-5)+"-"+ date.getDate();
									dateRange.startDate2=startTime;
									dateRange.endDate1=endTime;
									dateRange.endDate2=dateRange.startDate1;
								}else{
									dateRange.startDate1=startTime
									dateRange.startDate2=startTime;
									dateRange.endDate1=endTime;
									dateRange.endDate2=endTime;
									
								}
								return dateRange;
							}
						}
								
						//gei params 得到值
						function getParams(){
								params.eloanCode = $("input[name='eloanCode']").val();
								params.loanSrvCode = $("select[name='loanSrvCode']").val();
								params.status = $("select[name='status']").val();
								params.startTime = $("input[name='startTime']").val();
								params.endTime = $("input[name='endTime']").val();
								params.teamCode = $("input[name='excutorTeam']").val();
								params.excutorId = $("input[name='excutor']").val();
								params.address = $("input[name='address']").val();
								params.finOrgCode = $("select[name='finOrgCode']").val();
						}
						//查询数据
						$("#SearchButton").click(function() {
								getParams();
								initData();
								if (!$(".chartwo").is(":hidden")) {
									var RangeDate=getDateRange();
									params.startDate1=RangeDate.startDate1;
									params.startDate2=RangeDate.startDate2;
									params.endDate1=RangeDate.endDate1;
									params.endDate2=RangeDate.endDate2;
									reloadStatus();
								}
								if (!$(".charone").is(":hidden")) {
									reloadStatus2();
								}
											
											
						})
										
						//加载页面
						function initData() {
							params.pagination = true;
							$(".bonus-table")
								.aistGrid(
									{
										ctx : ctx,
										url : "/rapidQuery/findPage",
										queryId : 'queryEloanCashList',
										templeteId : 'queryEloanCash',
										gridClass : 'table table_blue table-striped table-bordered table-hover',
										data : params,
										columns : [ {
											colName : "E+编号"
										}, {
											colName : "合作机构"
										}, {
											colName : "借款人"
										}, {
											colName : "放款"
										}, {
											colName : "贷款专员"
										}, {
											colName : "状态"
										} ]

									});
						}
						//导出
						function exportToExcel (){
					    		var ctx = $("#ctx").val();
					    		var url = "/rapidQuery/findPage?xlsx&";
					    		var displayColomn = new Array;
					    		displayColomn.push('loanCode');
					    		displayColomn.push('LOAN_SRV_CODE');
					    		displayColomn.push('releaseAmout');
					    		displayColomn.push('RELEASE_TIME');
					    		displayColomn.push('CONFIRM_STATUS');
					    		displayColomn.push('PROPERTY_ADDR');
					    		displayColomn.push('CUST_NAME');
					    		
					    		displayColomn.push('FIN_ORG_CODE');
					    		displayColomn.push('ecutorId');
					    		displayColomn.push('ecutorTeam');
					    		getParams();
					    		params.queryId='queryEloanCashList';
					    		var queryId = 'queryId=queryEloanCashList';
					    		var colomns = '&colomns=' + displayColomn;
					    		url = ctx + url + jQuery.param(params) + queryId + colomns;

					    		$('#toexcelForm').attr('action', url);
					    		$('#toexcelForm').submit();
							 
						};
						function reloadStatus() {
							params.queryId = "queryLoanSpv";
					
							var startMonth = new Date(params.startDate1).getMonth()+1;
							$.ajax({
								async : true,//异步请求
								url : ctx + "/rapidQuery/findPage",
								method : "post",
								dataType : "json",
								data : params,
								success : function(data) {
									var all = data.rows;
									var kas = [];
									var dais = [];
									var xAxis = [];
									$.each(all, function(i, item) {
										var ka;
										var dai;
										if(i>0){
											if(all[i-1].mm==item.mm){
												return;
											}	
										}
									if(i<all.length-1){
									if(all[i+1].mm==all[i].mm){
										if (item.mm == 0) {
											xAxis.push(startMonth + "月以前");
										}else{
									    xAxis.push(item.mm + "月");}
									    ka={
											num:item.ka+all[i+1].ka,
											value:item.kaAmount+all[i+1].kaAmount
										  };
									    dai={
											num:item.dai+all[i+1].dai,
											value:item.daiAmount+all[i+1].daiAmount
										};
										kas.push(ka);
										dais.push(dai);
										return;
									}}
									if (item.mm == 0) {
										xAxis.push(startMonth + "月以前");
									}else{
								        xAxis.push(item.mm + "月");
								        }
										ka={
												num:item.ka,
												value:item.kaAmount
											};
									    dai={
												num:item.dai,
												value:item.daiAmount
											};
											kas.push(ka);
											dais.push(dai);
								

									});
									StatusEchart1(kas,dais,
											xAxis);
								},
								error : function() {
									$("#Cont").addClass("nullData");
								}
							});
						}
						function reloadStatus2() {
							params.queryId = "queryLoanSpv2";
				
							$.ajax({
								async : true,//异步请求
								url : ctx + "/rapidQuery/findPage",
								method : "post",
								dataType : "json",
								data : params,
								success : function(data) {
									var all = data.rows;
									var  numbers=[];
									var amounts=[];
									var finOrgNames=[];
									$.each(all, function(i, item) {
										var number;
										var amount;
										if(i>0){
											if(all[i-1].finOrgName==item.finOrgName){
												return;
											}	
										}
										if(i<all.length-1){
											if(all[i+1].finOrgName==all[i].finOrgName){
												number={
														name:item.finOrgName,
														value:item.num+all[i+1].num
													};
												amount={
														name:item.finOrgName,
														value:item.amount+all[i+1].amount
													};
													finOrgNames.push(item.finOrgName);
													numbers.push(number);
													amounts.push(amount);
													return;
											}	
										}
										 number={
											name:item.finOrgName,
											value:item.num
										};
										 amount={
											name:item.finOrgName,
											value:item.amount
										};
										finOrgNames.push(item.finOrgName);
										numbers.push(number);
										amounts.push(amount);
										
									});
									StatusEchart2(finOrgNames,numbers,amounts);
								},
								error : function() {
									/* $("#Cont").addClass("nullData"); */
								}
							});
						}
						

