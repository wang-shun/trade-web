<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
<link href="${ctx}/css/common/base.css" rel="stylesheet">

<script type="text/javascript">
	var ctx = "${ctx}";

</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	

			<!--*********************** HTML_main*********************** -->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content border-bottom clearfix space_box">
					<h2 class="title">
						业绩目标设定
						<div class="pull-right">
						<!-- <span id="btnSelected">
								<button type="button" cdata='C' class="btn btn-success selected">本月</button>
								<button type="button" cdata='N' class="btn btn-grey">下月</button>
							</span> -->
							<button type="button" class="btn btn-success sub_achieve ">提交业绩</button>
						</div>
					</h2>
					<form method="get" class="form_list">
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small">考核对象</label> <select id="sel_team"
									class="select_control sign_right_one">
									<option value="">全部小组</option>
									<c:forEach items="${orgs}"  var="item">
					        			<option value="${ item.id}">${item.orgName }</option>
					        		</c:forEach>
								</select> 
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small">业绩状态</label> 
								
								
								<aist:dict id="sel_status" name="sel_status"
										clazz="select_control sign_right_one" display="select"
										dictType="PERF_GOAL_STATUS" defaultvalue="" />
							</div>
							<div class="form_content ml5">
								<div class="add_btn">

									<button type="button" class="btn btn-grey set_target">
										批量设定</button>
								</div>
							</div>
						</div>


					</form>
					<div class="row">
						<div class="col-md-12">
							<div class="table_content">
								
								
							</div>
						</div>
					</div>
				</div>
			</div>

			<!--*********************** HTML_main*********************** -->

			<!-- 模态框（Modal） -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 500px;">
					<div class="modal-content">
						<div class="modal-header" style="border: none;">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="myModalLabel">业绩目标</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" id="div_user">
								<label for="marget" class="control-label pull-left"
									style="margin: 1px 40px 0 0;">考核人员</label> <span id="lab_user"></span>
							</div>
							<div class="form-group">
								<label for="marget" class="control-label pull-left"
									style="margin: 7px 15px 0 0;">填写业绩目标</label> <input type="text" id='txt_perfGoal' name="perfGoal"
									class="form-control pull-left" style="width: 270px;"
									id="marget" placeholder="请输入业绩目标"><span
									style="margin: 7px 0 0 7px; display: inline-block;">万</span>
							</div>
						</div>
						<div class="modal-footer"
							style="text-align: center; margin: 20px 0 40px; border: none;">
							<button type="button" class="btn btn-success" id="btn_setPerfGoal">确定</button>
							<button type="button" class="btn btn-grey" data-dismiss="modal">关闭</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->

			<!-- 模态框（Modal） -->
			<div class="modal fade" id="subAchieve" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 500px;">
					<div class="modal-content">
						<div class="modal-header" style="border: none;">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="myModalLabel">提交业绩</h4>
						</div>
						<div class="modal-body">
							<span id="lab_notset">10</span>人业绩未设定，是否提交
						</div>
						<div class="modal-footer"
							style="text-align: center; margin: 20px 0 40px; border: none;">
							<button type="button" class="btn btn-success" id="btn_doCommit">确定</button>
							<button type="button" class="btn btn-grey" data-dismiss="modal">关闭</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->


	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> </script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/static/media/js/jquery.twbsPagination.min.js"></script> <script
			src="${ctx}/static/js/template.js" type="text/javascript"></script>
	<script src="${ctx}/static_res/js/plugins/aist/aist.jquery.custom.js"></script>
	 <jsp:include
		page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	</script>
	<script src="${ctx}/static_res/trans/js/performance/perfGoal.js?v=1.0.5" type="text/javascript"></script> 
	<script
		id="template_successList" type="text/html">
	{{each rows as item index}}
    	<tr>
			<td><input type="checkbox" value="" name="items">
				<input type="hidden" value="{{item.uojId}}" name="uojId">
				<input type="hidden" value="{{item.pkid}}" name="pkid">
			</td>
			<td>{{item.orgName}}</td>
			<td>{{item.real_name}}</td>
			<td>{{item.belong_month}}</td>
			<td>
				{{if item.goal_perf != null}}{{item.goal_perf/10000}}万
				{{/if}}
			</td>
			<td>{{item.create_by}}</td>
			<td>{{item.create_time}}</td>
			<td>{{item.status == '' ? '未设定' :item.status}}</td>
			<td>{{if item.main_status != '2'}}
					<a href="javascript:void(0);" onclick="setGoal(this,'{{item.real_name}}');" class="sum_editor">设定</a>
				{{/if}}
			</td>
		</tr>
	{{/each}}
     </script> <script>
     //主表状态
     var mainStatus='${mainStatus}';
     var belongMonth= "${belongMonth}";
     var pkids=[];
     var uojIds=[];
     $(document).ready(function () {
    	 initData();
         //批量设定
         $('.set_target').click(batchSetGoal);
         //组别选择框
         $("#sel_team").change(reloadGrid);
         //状态选择框
         $("#sel_status").change(reloadGrid);
         //提交业绩目标按钮
         $('.sub_achieve').click(commitGoal );
         //提交目业绩 标确定按钮
         $("#btn_doCommit").click(doCommitGoal);
     /*     //点击本月下月
         $('#btnSelected .btn').click(function() {
             $(this).addClass("btn-success selected").removeClass("btn-grey ")
                     .siblings().addClass("btn-grey").removeClass("btn-success selected");
             reloadGrid();
         }) */
         //设定业绩的提交按钮
         $("#btn_setPerfGoal").click(setPerfGoal);
         $("#ckb_checkall").click(function(){
       		 $("input[name=items]").prop("checked",$(this).prop('checked'));	 

         });
     });
     //设置业绩目标
	 function setPerfGoal(){
		 if($('#txt_perfGoal').val()==''){
			 window.wxc.alert("请输入业绩目标");
    		 return false;
		 }
		 if(isNaN($('#txt_perfGoal').val())){
			 window.wxc.alert("请输入正确的业绩目标");
    		 return false;
		 }
		 $.ajax({
				url : ctx + "/perf/setPerfGoal",
				method : "post",
				dataType : "json",
				traditional:true,
				data : {
					perfGoal : $('#txt_perfGoal').val()*10000,
					pkids : pkids,
					uojIds : uojIds,
					belongMonth : belongMonth
				},
				success : function(data) {
					if(data.success){
						window.wxc.alert('设置成功');						
						 $('#myModal').modal('hide');
						 reloadGrid();	
					}else{
						window.wxc.alert(data.message);
					}
					 
				}
			});
	 }
     //弹窗模态框点击触发
     function showSetModal(){
    	 $('#txt_perfGoal').val('');
    	 $('#myModal').modal('show');
     }
     //置空ID数组
     function reSetIdArrary(){
    	 pkids=[];
		 uojIds=[];
     } 
     //设置目标调用方法
	 function setGoal(element,userName){
		 reSetIdArrary();
		 putId(element);
		 $('#lab_user').text(userName||'');
		 $('#div_user').show();//显示人的名字
		 showSetModal();
	 }
     ///将要操作的pkid或者orgjobId封装进数组
     function putId(obj){
    	 pkid = $(obj).closest('tr').find('input[name=pkid]').val();
    	 if(pkid !='' ){
    		 pkids.push(pkid);	 
    	 }else{
    		 uojIds.push($(obj).closest('tr').find('input[name=uojId]').val());
    	 }
     }
     //点击指设置处理方法
	 function batchSetGoal(){
    	 if($("input[name='items']:checked").size()==0){
    		 window.wxc.alert("请先选择要批量设置的数据");
    		 return false;
    	 }
		 reSetIdArrary();
		 $("input[name='items']:checked").each(function(e,i){
			 putId(this);
		 });
		 $('#div_user').hide();
		 showSetModal();
	 }
	//提交业绩绑定函数
	 function commitGoal(){
		if(mainStatus=='1'){
			window.wxc.alert('业绩目标已经提交,请不要重复提交');	
			return false;
		}
		 $.ajax({
				url : ctx + "/perf/getNotSetCount",
				method : "post",
				dataType : "json",
				data : {belongMonth : belongMonth},
				success :function (data){
					if(!data.success){
						window.wxc.alert('请求失败');	
						return false;
					}
					if(!!~~data.content){
						$("#lab_notset").text(data.content);
						$('#subAchieve').modal('show');
					}else{
						window.wxc.confirm("确定要提交业绩目标?",{"wxcOk":function(){
			    			doCommitGoal();
			    		}});
					}
				}
			});
	 }
	//提交业绩
	 function doCommitGoal(){
		 $.ajax({
				url : ctx + "/perf/commitPerfGoal",
				method : "post",
				dataType : "json",
				data : {belongMonth : belongMonth},
				success :function (data){
					if(!data.success){
						window.wxc.alert(data.message);	
						return false;
					}
					window.wxc.alert('提交成功');						
					window.location.reload();
				}
			});
	 }

     </script> </content>
</body>