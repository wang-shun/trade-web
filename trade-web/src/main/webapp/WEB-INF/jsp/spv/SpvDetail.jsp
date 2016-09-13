<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>监管合约详情</title>
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/stickmenu.css">
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />


</head>
<body>

	<!-- main Start -->

	<div class="row">
		<div class="wrapper wrapper-content animated fadeInUp">
			<!-- <div class="ibox"> -->
			<div class="ibox-content elan_detail">
				<div class="row">
					<div class="col-lg-9">
						<div class="row" id="">
							<div class="col-lg-12">
								<div class="m-b-md">
									<h4>监管合约详情</h4>
								</div>

							</div>
						</div>
						<div class="row" id="">
							<div class="col-lg-6" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>合约编号</dt>
									<dd>
										<a herf="#">${spvBaseInfoVO.toSpv.spvCode}</a>
									</dd>
									<dt>产权地址</dt>
									<dd>${spvBaseInfoVO.toSpvProperty.prAddr}</dd>
									<dt>合作机构</dt>
									<dd id="pcode">${spvBaseInfoVO.toSpv.prdCode}</dd>
									<dt>监管合同号</dt>
									<dd >${spvBaseInfoVO.toSpv.spvConCode}</dd>
								</dl>
							</div>
							<div class="col-lg-3" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>监管金额</dt>
									<dd>${spvBaseInfoVO.toSpv.amount>0?spvBaseInfoVO.toSpv.amount:0}万元</dd>
									<dt>已转入金额</dt>
									<dd>0万元</dd>
									<dt>已转出金额</dt>
									<dd>0万元</dd>
								</dl>
							</div>
							<div class="col-lg-3">
								<dl class="dl-horizontal">
									<!-- <dt>申请人</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：137 9541 0234">--</a>
									</dd> -->
									<dt>风控专员</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：${createPhone}">${spvBaseInfoVO.toSpv.createBy}</a>
									</dd>
									<dt>风控总监</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：${zj.mobile }">${zj.realName }</a>
									</dd>
								</dl>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="row bs-wizard"
									style="border-bottom: 0; margin-left: 15px">
									
									<div class="col-lg-4 bs-wizard-step 
									<c:choose>  
										    <c:when test="${spvBaseInfoVO.toSpv.status>=1}"> complete
										   </c:when>    
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
									">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<span>申请（时间：<fmt:formatDate value="${spvBaseInfoVO.toSpv.applyTime}" pattern="yyyy-MM-dd" />）</span> 
												</dd>
											</dl>
										</div>
									</div>
									<div class="col-lg-5 bs-wizard-step 
										<c:choose>  
										    <c:when test="${spvBaseInfoVO.toSpv.status>=2}"> complete
										   </c:when>    
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	>
									">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<span>签约（时间：<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime}" pattern="yyyy-MM-dd" />）</span> 
												</dd>
											</dl>
										</div>
									</div>
									<div class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${spvBaseInfoVO.toSpv.status==3}"> complete
										   </c:when>    
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
									">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<span>结束（时间：<fmt:formatDate value="${spvBaseInfoVO.toSpv.closeTime}" pattern="yyyy-MM-dd" />）</span>
												</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="row">
							<div class="col-md-12">
								<div class="m-b-md">
									<h4>关联合约</h4>
								</div>
							</div>
						</div>
						<div class="feed-activity-list">
							<div class="feed-element">
								<div class="pull-lef contract-icon-block">
									<div class="icon icon_blue iconfont">&#xe607;</div>
								</div>
								<div class="media-body">
									<strong><a>${spvBaseInfoVO.toSpv.caseCode}</a></strong><br />经办人
									<strong>${jingban}</strong> <br /> 
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="ibox-content clearfix" id="base_info">
			<c:if test="${spvBaseInfoVO.toSpv.status==0 }">
			<shiro:hasPermission name="TRADE.SPV.UPDATE">
            		<a style="float: right; margin-right: 0px; margin-top: 0px;" href="${ctx}/spv/saveHTML?pkid=${spvBaseInfoVO.toSpv.pkid}">我要修改</a>
		    </shiro:hasPermission>
            </c:if> 
				<div class="panel blank-panel">
					<div class="panel-heading">
						<div class="panel-options">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab-1" data-toggle="tab"
									aria-expanded="true">买卖双方</a></li>
								<li class=""><a href="#tab-2" data-toggle="tab"
									aria-expanded="false">房产及交易</a></li>
								<li class=""><a href="#tab-4" data-toggle="tab">资金监管及账户</a>
								</li>
								<li class=""><a href="#tab-3" data-toggle="tab">
										监管资金 </a></li>
								<li class=""><a href="#tab-5" data-toggle="tab">资金放款方案</a>
								</li>
								<li class=""><a href="#tab-6" data-toggle="tab">驳回原因</a>
								</li>
							</ul>
						</div>
					</div>

					<div class="panel-body">
						<div class="tab-content">
							<div class="tab-pane active" id="tab-1">
								<div class="panel-body">
									<div id="infoDiv infos" class="row">
										<div class="ibox white_bg row">
											<div class="info_box info_box_one col-md-6 ">
												<span>买家信息</span>
												<div class="ibox-conn ibox-text">

													<dl class="dl-horizontal" style="height:auto">
														<dt>客户姓名</dt>
														<dd>${spvBaseInfoVO.spvCustList[0].name }</dd>
														<dt>性别</dt>
														<dd>${spvBaseInfoVO.spvCustList[0].gender eq '1'?'男':'' }
														${spvBaseInfoVO.spvCustList[0].gender eq '0'?'女':'' }
														</dd>
														<dt>家庭住址</dt>
														<dd>${spvBaseInfoVO.spvCustList[0].homeAddr }</dd>
														<dt><aist:dict id="idType0" name="idType0" clazz="form-control input-one"
									                        display="onlyLabel"  dictType="CERT_TYPE"  
									                        ligerui='none' dictCode="${spvBaseInfoVO.spvCustList[0].idType}"></aist:dict></dt>
														<dd>${spvBaseInfoVO.spvCustList[0].idCode }</dd>
														<dt>手机号</dt>
														<dd>${spvBaseInfoVO.spvCustList[0].phone }</dd>
														<dt>证件有效期</dt>
														<dd><fmt:formatDate value="${spvBaseInfoVO.spvCustList[0].idValiDate }" pattern="yyyy-MM-dd"/></dd>
													</dl>
												</div>
											</div>
											<div class="info_box info_box_one col-md-6 ">
												<span>卖家信息</span>
												<div class="ibox-conn ibox-text">
													<dl class="dl-horizontal" style="height:auto">
														<dt>客户姓名</dt>
														<dd>${spvBaseInfoVO.spvCustList[1].name }</dd>
														<dt>性别</dt>
														<dd>${spvBaseInfoVO.spvCustList[1].gender eq '1'?'男':'' }
														${spvBaseInfoVO.spvCustList[1].gender eq '0'?'女':'' }
														</dd>
														<dt>家庭住址</dt>
														<dd>${spvBaseInfoVO.spvCustList[1].homeAddr }</dd>
														<dt><aist:dict id="idType1" name="idType1" clazz="form-control input-one"
															display="onlyLabel"  dictType="CERT_TYPE"  
															ligerui='none'  dictCode="${spvBaseInfoVO.spvCustList[1].idType}"></aist:dict>
									                   </dt>
														<dd>${spvBaseInfoVO.spvCustList[1].idCode }</dd>
														<dt>手机号</dt>
														<dd>${spvBaseInfoVO.spvCustList[1].phone }</dd>
														<dt>证件有效期</dt>
														<dd><fmt:formatDate value="${spvBaseInfoVO.spvCustList[1].idValiDate }" pattern="yyyy-MM-dd"/></dd>
													</dl>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div class="tab-pane" id="tab-2">
								<div class="row" id="">
									<div class="col-md-3">
										<ul class="real-estate">
											<li>
												<p>
													<em>房产证号</em> <span>${spvBaseInfoVO.toSpvProperty.prNo }</span>
												</p>
											</li>
											<li>
												<p>
													<em>房产权利人</em> <span>${spvBaseInfoVO.toSpvProperty.prOwnerName }</span>
												</p>
											</li>
											<li>
												<p>
													<em>网签合同号</em> <span>${spvBaseInfoVO.toSpvProperty.signNo }</span>
												</p>
											</li>
										</ul>
									</div>
									<div class="col-md-6">
										<ul class="real-estate">
											<li>
												<p>
													<em>面积</em> <span>${spvBaseInfoVO.toSpvProperty.prSize }平方米</span>
												</p>
											</li>
											<li>	
												<p>
													<em>网签金额</em> <span title="">${spvBaseInfoVO.toSpvProperty.signAmount>0?spvBaseInfoVO.toSpvProperty.signAmount:0}万元</span>
												</p>
											</li>
											  <li>
												
												<p>
													<em>产证地址</em> <span>${spvBaseInfoVO.toSpvProperty.prAddr }</span>
												</p>
											</li> 
										</ul>
									</div>
									<div class="col-md-3">
										<ul class="real-estate">
											<li>
												<p>
													<em>下家付款方式</em> <span><aist:dict id="toSpv.buyerPayment" name="toSpv.buyerPayment" clazz="form-control input-one"
									display="onlyLabel"  dictType="SPV_BUYER_PAYMENT"  
									 ligerui='none'  dictCode="${spvBaseInfoVO.toSpv.buyerPayment}"></aist:dict></span>
												</p>
											</li>
											<li>
												<p>
													<em>金额大写</em> <span name="DX" title="">${spvBaseInfoVO.toSpvProperty.signAmount>0?spvBaseInfoVO.toSpvProperty.signAmount:0}</span>
												</p>
											</li>
										   
										</ul>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="tab-3">
								<div class="row" id="">
									<div class="col-md-4">
										<ul class="real-estate">
										  <li>
												<p>
													<em>监管总金额</em> <span>${spvBaseInfoVO.toSpv.amount>0?spvBaseInfoVO.toSpv.amount:0}万元</span>
												</p>
											</li>
											<li>
												<p>
													<em>自筹资金</em> <span>${spvBaseInfoVO.toSpv.amountOwn>0?spvBaseInfoVO.toSpv.amountOwn:0}万元</span>
												</p>
											</li>
											<li>
												<p>
													<em>买方贷款总金额</em> <span>${spvBaseInfoVO.toSpv.amountMort>0?spvBaseInfoVO.toSpv.amountMort:0}万元</span>
												</p>
											</li>
	                                           <li>
												<p>
													<em>商业贷款金额</em> <span>${spvBaseInfoVO.toSpv.amountMortCom>0?spvBaseInfoVO.toSpv.amountMortCom:0}万元</span>
												</p>
											</li>
											<li>
												<p>
													<em>公积金贷款金额</em> <span>${spvBaseInfoVO.toSpv.amountMortPsf>0?spvBaseInfoVO.toSpv.amountMortPsf:0}万元</span>
												</p>
											</li>
										</ul>
									</div>
									<div class="col-md-5">
										<ul class="real-estate">
										<li>
												<p>
													<em>监管总金额大写</em> <span name="DX">${spvBaseInfoVO.toSpv.amount>0?spvBaseInfoVO.toSpv.amount:0}</span>
												</p>
											</li>
											<li>
												<p>
													<em>自筹资金大写</em> <span name="DX">${spvBaseInfoVO.toSpv.amountOwn>0?spvBaseInfoVO.toSpv.amountOwn:0}</span>
												</p>
											</li>
											<li>
												<p>
													<em>买方贷款总金额大写</em> <span name="DX">${spvBaseInfoVO.toSpv.amountMort>0?spvBaseInfoVO.toSpv.amountMort:0}</span>
												</p>
											</li>
	                                           <li>
												<p>
													<em>商业贷款金额大写</em> <span name="DX">${spvBaseInfoVO.toSpv.amountMortCom>0?spvBaseInfoVO.toSpv.amountMortCom:0}</span>
												</p>
											</li>
											<li>
												<p>
													<em>公积金贷款金额大写</em> <span name="DX">${spvBaseInfoVO.toSpv.amountMortPsf>0?spvBaseInfoVO.toSpv.amountMortPsf:0}</span>
												</p>
											</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="tab-pane" id="tab-4">
								<div class="row">
									<div class="panel-body">
										<div class="row">
											<div class="info_box info_box_one col-md-6 ">
												<span>收款账户</span>
												<div class="ibox-conn ibox-text">
													<dl class="dl-horizontal account">
														<dt>姓名</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[1].name }</dd>
														<%-- <dt>归属地</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[1].bank }</dd> --%>
														<dt>开户行</dt>
														<dd id="bank1">${spvBaseInfoVO.toSpvAccountList[1].bank }</dd>
														<dt>账号</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[1].account }</dd>
														<dt>电话</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[1].telephone }</dd>
													</dl>
												</div>
											</div>
											<div class="info_box info_box_one col-md-6 ">
												<span>退款账户</span>
												<div class="ibox-conn ibox-text">
													<dl class="dl-horizontal account">
														<dt>姓名</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[0].name }</dd>
														<%-- <dt>归属地</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[1].bank }</dd> --%>
														<dt>开户行</dt>
														<dd id="bank0">${spvBaseInfoVO.toSpvAccountList[0].bank }</dd>
														<dt>账号</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[0].account }</dd>
														<dt>电话</dt>
														<dd>${spvBaseInfoVO.toSpvAccountList[0].telephone }</dd>
													</dl>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="tab-5">
							<p>
							监管资金次数合计<span id="sum">${spvBaseInfoVO.toSpvDeDetailList.size() }</span> 次
						    </p>
								<table
									class="table table-bordered table-hover customerinfo col-md-6">
									<thead>
										<tr>
										   <th>划转条件</th> 
										    <th>账户</th>
											<th>划转金额</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${spvBaseInfoVO.toSpvDeDetailList}" var="item">
										<tr>
											<td>
												<aist:dict id="${item.deCondCode}" name="deCondCode" clazz="form-control input-one"
									    display="onlyLabel"  dictType="SPV_DE_COND"  
									    ligerui='none' dictCode="${item.deCondCode }"></aist:dict>	
											</td>
											<td>
											
											<c:choose>  
										    <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[0].pkid}">
										              买方
										   </c:when>  
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[1].pkid}">
										              卖方
										   </c:when> 
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[2].pkid}">
										            监管账户      
										   </c:when> 
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[3].pkid}">
										           资金方       
										   </c:when>  
										</c:choose>	
											</td>
											<td>${item.deAmount>0?item.deAmount:0}万元</td>
										</tr>
										</c:forEach>
										
									</tbody>
								</table>
							</div>
							<div class="tab-pane" id="tab-6">
							<div class="info_box info_box_one col-md-8 ">

												
												<p><strong>驳回原因</strong> <p>
												<div style="margin-left:50px;"class="ibox-conn ibox-text">
                                              ${toApproveRecord.content} 
												</div>
                            	</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- main End -->


	<content tag="local_script"> <script
		src="${ctx}/js/inspinia.js"></script> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <script>
			//点击浏览器任何位置隐藏提示信息
			$("body").bind("click", function(evt) {
				if ($(evt.target).attr("data-toggle") != 'popover') {
					$('a[data-toggle="popover"]').popover('hide');
				}
			});
	        //转大写
	        var DX = function (num) {  
			  var strOutput = "";  
			  var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
			  num += "00";  
			  var intPos = num.indexOf('.');  
			  if (intPos >= 0)  
			    num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
			  strUnit = strUnit.substr(strUnit.length - num.length);  
			  for (var i=0; i < num.length; i++)  
			    strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
			    return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");  
			};  
			
			/*获取银行列表*/
			function getBank(pcode){
				var bank = $("#"+pcode);
				 $.ajax({
					    url:ctx+"/manage/queryParentBankName",
					    method:"post",
					    dataType:"json",
						async:false,
					    data:{finOrgCode:bank.html()},
					    success:function(data){
				    		if(data != null){
				    			bank.html(data.content)
				    		}
				    	}
					});
			}
			/*获取产品列表*/
			function getPcode(pcode){
				var pcode = $("#"+pcode);
				if(pcode.html()==1){
					pcode.html("光大四方资金监管");
					return;
				}
				 $.ajax({
					    url:ctx+"/spv/queryPrdcCodeByProdCode",
					    method:"post",
					    dataType:"json",
						async:false,
					    data:{prodCode:pcode.html()},
					    success:function(data){
				    		if(data != null){
				    			pcode.html(data.prodName)
				    		}
				    	}
					});
			}
			$(document).ready(function(){
				$("span[name='DX']").each(function(index,element){
					$(element).html(DX($(element).html()*10000));
				});
				getPcode("pcode");
				getBank("bank0");
				getBank("bank1");
			})
			queryPrdcCodeByProdCode
					</script> </content>
</body>
</html>



