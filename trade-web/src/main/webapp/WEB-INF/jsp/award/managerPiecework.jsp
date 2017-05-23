<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- Gritter -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />

	
 <!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/trans/css/award/baseAward.css' />" />

<style type="text/css">

</style>
</head>
<body>
<!--*********************** HTML_main*********************** -->
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        可计件环节奖金列表
                    </h2>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            <table class="table table_blue table-hover table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>
                                            职位
                                        </th>
                                        <th>
                                            对应组别
                                        </th>
                                        <th>
                                            具体环节
                                        </th>
                                        <th>
                                            基础奖金（元）
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="managerPieceInfoList">
                                </tbody>
                            </table>
                            
                        </div>
                    </div>
                </div>
            </div>
            </div>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 
<input type="hidden" id="ctx" value="${ctx}"/>
<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"> </script>
<script src="<c:url value='/transjs/award/managerRewardList.js?v=1.1' />"> </script>

<script id="template_managerPieceInfo" type="text/html">
		{{each rows as item index}}
		    <tr>
              <td>{{item.JOB_NAME}}</td>
              <td>
					{{ if item.SRV_ITEM_CODE == 'TransSign' || item.SRV_ITEM_CODE == 'ComLoanProcess' }}
					前台组
					{{else item.SRV_ITEM_CODE == 'Guohu' || item.SRV_ITEM_CODE == 'PSFSign' || item.SRV_ITEM_CODE == 'LoanClose'}}
					后台组
					{{/if}}
			  </td>
              <td>{{item.SRV_ITEM_NAME}}</td>
              <td>{{item.SRV_FEE}}</td>
             </tr>
		{{/each}}
</script>
<script type="text/javascript">
$(function(){
	var ctx = $("#ctx").val();
	//页面初始化
	reloadGrid();
});
function reloadGrid(){
	var data = {};
	data.pagination = false; 
	$("#managerPieceInfoList").reloadGrid({
    	ctx : ctx,
		queryId : "managerPieceInfoList",
	    templeteId : 'template_managerPieceInfo',
	    data : data,
	    wrapperData : data
    });
}
</script>
</content>
</body>
</html>