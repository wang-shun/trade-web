<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
                            <div class="col-lg-9">
                                <div class="row" id="">
                                    <div class="col-lg-12">
                                        <div class="m-b-md">
                                            <h4>
                                            				<aist:dict id="loanSrvCode" name="loanSrvCode"
											clazz="select_control sign_right_two" display="onlyLabel"
											dictType="yu_serv_cat_code_tree" tag="Eloan"
											dictCode="${eloanCase.loanSrvCode}" ligerui='none'></aist:dict>
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="">
							<div class="col-lg-5" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>E+编号</dt>
									<dd>
										<a herf="#">${eloanCase.eloanCode }</a>
									</dd>
									<dt>产权地址</dt>
									<dd>${info.propertyAddr}</dd>
									<dt>合作机构</dt>
									<dd>${info.finOrgName}</dd>
									
									<c:if test="${eloanCase.finOrgCode == 'W0003' && eloanCase.month <= 12}">
										<dt>手续费</dt>
										<dd>${eloanCase.chargeAmount}</dd>
									</c:if>
									
									<c:if test="${eloanCase.chargeAmount / (eloanCase.applyAmount * 10000) > 0.02 }">
										<dt>情况说明</dt>
										<dd>${eloanCase.remark}</dd>
									</c:if>
								</dl>
							</div>
							<div class="col-lg-4" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>申请时间</dt>
									<dd>${info.applyTime}</dd>
									<dt>申请期数</dt>
									<dd>${eloanCase.month}</dd>
								</dl>
							</div>
							<div class="col-lg-3">
								<dl class="dl-horizontal">
									<dt>借款人</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right"
											data-content="手机：${eloanCase.custPhone}">${eloanCase.custName}</a>
									</dd>
									<dt>贷款专员</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：${info.excutorPhone}">${info.excutorName}</a>
									</dd>
									<dt>贷款转介人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.zjPhone}" --%>>${eloanCase.zjName}</span>
									</dd>
									<dt>合作信贷员</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.coName}</span>
									</dd>
									<dt>产品部合作人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.pdName}</span>
									</dd>
								</dl>
							</div>
						</div>
						<%-- <div class="row">
							<div class="col-lg-12">
								<div class="row bs-wizard"
									style="border-bottom: 0; margin-left: 15px">
									<div
										class="col-lg-4 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
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
													<strong>申请</strong>
													${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									<div
										class="col-lg-5 bs-wizard-step 
											   <c:choose>  
										    <c:when test="${info.status=='sign'}"> active
										   </c:when>  
										    <c:when test="${info.status=='confirmSign'||info.status=='release'}"> complete
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
													<strong>签约</strong>
													${eloanCase.signAmount>0?eloanCase.signAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									

									<div
										class="col-lg-2 bs-wizard-step 
										<c:choose>  
  
										   <c:when test="${info.status=='release'}"> complete
										　　complete
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
													<strong>放款</strong>
													${info.releaseAmount>0?info.releaseAmount:0} 万
												</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div> --%>
                            </div>
                            <div class="col-lg-3">
                                <div class="row">
									<div class="col-lg-12">
										<div class="m-b-md">
											<h4>关联案件</h4>
										</div>
									</div>
								</div>
								<div class="feed-activity-list">
									<div class="feed-element">
										<div class="pull-lef contract-icon-block">
											<div class="icon icon_blue iconfont">&#xe607;</div>
										</div>
										<div class="media-body">
											<strong><a>${eloanCase.caseCode }</a></strong><br />经办人 <strong>${info.jingbanName }</strong>
											<br />
											<!-- <small class="text-muted">2016-06-14 15:30 网签</small> -->
										</div>
									</div>
								</div>
                            </div>
                        </div>
