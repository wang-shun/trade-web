<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">

<style type="text/css">

.form-group label {
	text-align: right;
}

.form-control {
	margin-bottom: 5px;
	height:32px;
}

.radio {
	margin-left: 20px;margin-top:-10px
}

.mouseover-color{
	background-color:#B4D5F5;
}

</style>
<script language="javascript">
 	var ctx = "${ctx}";

</script>
</head>
<body>
		<div id="modal-select" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="width: 600px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox " style="min-height:150px;">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>该物业地址对应多个小区，请点击选择一个小区</h5>
							</div>
								
								<div class="ibox-content">

									<div class="form-group">
										<div class="col-sm-12" id="residenceList" style="line-height:26px">

										</div>
									</div>
									
								</div>
						</div>
					</div>
					<div>&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox">
					<div class="ibox-title" id="firBank">
						<h5 id="firstBank">询价申请</h5>
						
					</div>
					<div class="ibox-content" id="first" >							
						<div class="row">
						<form id="addToEguPricingForm">
								<input type="hidden" name="pkid" value="pkid" value="" />
									<!-- <div class="form-group">
										<label class="col-sm-2 control-label">小区编号：</label>
										<div class="col-sm-10">
											<input type="text" name="residence_id"
												id="residence_id" placeholder="小区编号"
												class="form-control" onkeyup="checkInt(this)">
										</div>
									</div> -->
									<div class="form-group">
										<label class="col-sm-2 control-label">物业地址<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="residence_name"
												id="residence_name" placeholder="小区地址（名称）"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款银行：</label>
										<div class="col-sm-10">
											<select class="chosen-select" name="bank_type"
												id="bank_type" >
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">支行<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<select class="chosen-select" name="bank_branch_id"
												id="bank_branch_id">
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">期望价格<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="deal_price" id="deal_price"
												class="form-control" placeholder="万元" onkeyup="checknum(this)">											
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">楼栋号<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="building_no" id="building_no"
												placeholder="楼栋号" class="form-control" >
																						
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">室号<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="room_no" id="room_no"
												placeholder="室号" class="form-control" >
											
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">所在楼层<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="floor" id="floor"
												placeholder="所在楼层" class="form-control" onkeyup="checkInt(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">总楼层<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="total_floor" id="total_floor"
												placeholder="总楼层" class="form-control" onkeyup="checkInt(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">房屋类型<span class="star">*</span>：</label>
										<div class="col-md-10">
											<aist:dict id="prop_type" name="prop_type"
												clazz="form-control" display="select"
												dictType="prop_type" defaultvalue="" onchange="isShowAreaGround(this);"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">建筑面积<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="area" id="area"
												placeholder="建筑面积" class="form-control" onkeyup="checknum(this)">
										</div>
									</div>
									<div id="areaGround" style="display:none;">
									<div class="form-group">
										<label class="col-sm-2 control-label">地上建筑面积：</label>
										<div class="col-md-10">
											<input type="text" name="area_ground" id="area_ground"
												placeholder="建筑地上面积" class="form-control" onkeyup="checknum(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">地下建筑面积：</label>
										<div class="col-md-10">
											<input type="text" name="area_basement"
												id="area_basement" placeholder="建筑地下面积"
												class="form-control" onkeyup="checknum(this)">
										</div>
									</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">房屋结构（室）：</label>
										<div class="col-sm-10">
							<!-- 				<input type="text" name="room" id="room"
												placeholder="室" class="form-control" onkeyup="checkInt(this)"> -->
												<select name="room" id="room" class="form-control">
												  <option value ="0">请选择</option>
												  <option value ="1">1</option>
												  <option value ="2">2</option>
												  <option value="3">3</option>
												  <option value="4">4</option>
												  <option value="5">5</option>
												</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">房屋结构（厅）：</label>
										<div class="col-sm-10">
									<!-- 		<input type="text" name="hall" id="hall"
												placeholder="厅" class="form-control" onkeyup="checkInt(this)"> -->
												<select name="hall" id="hall" class="form-control">
												  <option value ="0">请选择</option>
												  <option value ="1">1</option>
												  <option value ="2">2</option>
												  <option value="3">3</option>
												  <option value="4">4</option>
												  <option value="5">5</option>
												</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">房屋结构（卫）：</label>
										<div class="col-sm-10">
											<!-- <input type="text" name="toilet" id="toilet"
												placeholder="卫" class="form-control" onkeyup="checkInt(this)"> -->
												<select name="toilet" id="toilet" class="form-control">
												  <option value ="0">请选择</option>
												  <option value ="1">1</option>
												  <option value ="2">2</option>
												  <option value="3">3</option>
												  <option value="4">4</option>
												  <option value="5">5</option>
												</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">平面类型：</label>	
										<div class="col-md-10">														
											<aist:dict id="plane_type" name="plane_type"
												display="select" clazz="form-control"
												dictType="flat_type" defaultvalue="" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">朝向：</label>
										<div class="col-sm-10">																																											
											<aist:dict id="towards" name="towards" display="select"
												clazz="form-control m-b" dictType="towards"
												defaultvalue="" />
										</div>
									</div>
									<div style="clear: both"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label">景观类型：</label>
										<div class="col-sm-10">																																											
											<aist:dict id="scape" name="scape" display="radio"
												dictType="scape" defaultvalue="" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">临街类型：</label>
										<div class="col-sm-10">																												
											<aist:dict id="near_street" name="near_street"
												display="radio" dictType="near_street" defaultvalue="" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">备注：</label>
										<div class="col-sm-10">																											
											<input type="text" class="form-control" name="remark"
												id="remark">
										</div>
									</div>
									<div class="form-group">
										<a href="#" class="btn btn-primary" style="width:98%;" id="subApplyBtn" >提交</a>
									</div>
							</form>
							</div>						
						</div>
				</div>
			</div>
		</div>
	</div>

<content tag="local_script"> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
   	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/mobilejs/task/eguPricingApply.js"></script> 	
	 </content>
</body>

</html>