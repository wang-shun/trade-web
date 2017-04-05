<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>知识库列表</title>
	
	<!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
	
    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <style type="text/css">
    	.table > tbody > tr:FIRST-CHILD > td{
    		border-top: none;
    	}.userHead{
	width: 27px;
		  height: 27px;
		  display: inline-block;
		  border-radius: 50%;
		  background-size: 27px 40.73px;
		  vertical-align: middle;
		  background-image:url(../img/a5.png);
	
}
</style>
<script type="text/javascript">
  	 function imgLoad(img){
	   		 img.parentNode.style.backgroundImage="url("+img.src+")";
	   	 }
</script>
   
</head>
<body>
	<!-- title start -->
		
	<!-- title end -->
	
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="userId" value="${userId}"/>
	<!-- content start  -->
		<!-- search start -->
		<div class="ibox">
		<div class="ibox-title">
                                    
			<div class="row">
				<div class="col-lg-12">
			        <div class="input-group">
			        <input type="text" placeholder="搜索" class="input-sm form-control" id="txt_input">
			        	<span class="input-group-btn">
	                    <button type="button" class="btn btn-sm btn-primary" id="knowledgeSearchButton"> 搜索</button> </span>
			        </div>
		        </div>
			</div>  
			</div>
		<!-- search end -->                      
				<!--begin page content -->

		<!-- DataList start -->
			<div  id="jqList" class="ibox-content" >
				<div class='class="m-b-lg"'></div>
				<div id="knowledgeListDiv" class="allhouse-list row-fluid">
                    <div class="house-cont"></div>
                	<div id="hiddenDataId" style="height:20px;">&nbsp;</div>
                </div>
                <div id='blockShow-9' class="my-layer my-loading" style="display:none">
			    	<div class="box">
			        	<div class="caseFonce">
				 			<div class="spinner"></div>
						</div>
			        </div>
			    </div>
				<div id="picShowDiv" style="display:none"></div>  
				<div id="pageBar" class="pagination-sm my-pagination text-center m0"></div>
			</div>
		<!-- DataList end -->
		
	<!-- content end  -->
	</div>
	<script id="knowledgeList" type="text/html">
<div class="feed-activity-list">
	{{if rows.length>0}}
		{{each rows as value}}
			<div class="feed-element">
				<a href="#" class="pull-left">
					<span class="userHead">
	                    			<img alt="image" class="himg"  style="margin:auto" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/{{value.employeeCode}}.jpg" onload="javascript:imgLoad(this)">
	                    		</span>
				   </br>
				   {{value.pubisher}}                                                   
 				</a>
				<div class="media-body ">
                       {{if value.isTop=='1'}}<small class="pull-right text-navy">置顶</small>{{/if}}
						{{if value.isRecommand==1}}<small class="pull-right text-navy">推荐</small>{{/if}}
                        <strong>{{value.TITLE}}</strong><br>
                        <small class="text-muted">{{value.PB_TIME}}</small>
					{{if value.isLiked ==null}}
					<div class="actions">
						<a class="btn btn-xs btn-white" data='1' onClick="doLike('{{value.PKID}}')"><i class="fa fa-thumbs-up"></i> 赞 </a>
					</div>
					{{/if}}
					{{if value.isLiked !=null}}
					<div class="actions">
						<a class="btn btn-xs btn-white" data='0' onClick="doLike('{{value.PKID}}')"><i class="fa fa-thumbs-up"></i> 取消赞 </a>
					</div>
					{{/if}}
                </div>
			</div>
		{{/each}}
	{{/if}}
</div>
</script>
<script id="knowledgeListStyle1" type="text/html">
<div class="table-responsive">
                            <table class="table table-hover issue-tracker">
                                <tbody>
	{{if rows.length>0}}
		{{each rows as value}}
                                <tr>
                                    <td class="">
                                        <a href="{{ctx}}/knowledge/detail?id={{value.PKID}}" target='_blank' style='font-size:16px;'>
                                            {{value.TITLE}}
                                        </a>
										</br>
                                        <small>
                                            {{value.CONTENT}}
                                        </small>
                                    </td>
                                    <td style="width:130px">
										<span class="userHead">
	                    					<img alt="image" class="himg"  style="margin:auto" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/{{value.employeeCode}}.jpg" onload="javascript:imgLoad(this)">
	                    				</span>{{value.pubisher}}
                                        
                                    </td>
                                    <td style="width:180px">
                                        {{value.PB_TIME}}
                                    </td>

                                    <td class="text-right" style="width:124px">
										{{if value.isTop=='1'}}
                                        	<button class="btn btn-white btn-xs">置顶</button>
										{{/if}}
										{{if value.isRecommand=='1'}}
                                        	<button class="btn btn-white btn-xs">推荐</button>
										{{/if}}
                                    </td>
                                    <td class="text-left" width='224px;'>
										<a class="btn btn-xs btn-white"  onClick="javascript:return false;"><i class="fa fa-flag-checkered"></i> 点击量 {{if value.clickSum!=null}}({{value.clickSum}}){{/if}}</a>
										{{if value.isLiked ==null}}
                                        	<a class="btn btn-xs btn-white" data='1' likeSum="{{if value.likeSum!=null}}{{value.likeSum}}{{/if}}" onClick="doLike('{{value.PKID}}')"><i class="fa fa-thumbs-up"></i> 赞 {{if value.likeSum!=null}}({{value.likeSum}}){{/if}}</a>
										{{/if}}
										{{if value.isLiked !=null}}
											<a class="btn btn-xs btn-white" data='0' likeSum="{{if value.likeSum!=null}}{{value.likeSum}}{{/if}}" onClick="doLike('{{value.PKID}}')"><i class="fa fa-thumbs-up"></i> 取消赞 {{if value.likeSum!=null}}({{value.likeSum}}){{/if}}</a>
										{{/if}}
										
                                    </td>
                        </tr>
					{{/each}}
				{{/if}}
   		   </tbody>
     </table>
 </div>
</script>
  	<content tag="local_script">
	    <!-- Mainly scripts -->
	    <%-- <script src="${ctx}/js/jquery-2.1.1.js"></script> --%>
	    	
	    <!-- Peity -->
	    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	
	    <!-- jqGrid -->
	    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	    
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
			
		<!-- Add Button helper (this knowledgeListis optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>
		<script src="${ctx}/js/template.js" type="text/javascript"></script>
		<script src="${ctx}/js/jquery.twbsPagination.min.js" type="text/javascript"></script>
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script src="${ctx}/js/trunk/knowledge/read.js"></script>

		<script>
	/* 		jQuery(document).ready(function() {
				 $(".fancybox").fancybox({
						maxWidth	: 650,
						maxHeight	: 450,
						fitToView	: false,
						width		: '70%',
						height		: '55%',
						autoSize	: false,
						closeClick	: false,
						openEffect	: 'none',
						closeEffect	: 'none'
					});
				 
				 $('#alertOper').fancybox({
						type: 'iframe',
						padding : 0,
						margin:0,
						autoScale:false,
						fitToView	: false,
					 	width		: '70%', 
						height		: '55%',
						autoDimensions:true,
						showCloseButton:true,
						close:true,
						helpers: {
			                overlay: {
			                    closeClick: false
			                }
			            },
						iframe:{preload:false},
						openEffect	: 'none',
						closeEffect	: 'none',
						afterClose:function(){
							jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
						}
					});

			}); */
	
		</script>
	</content>

</body>
</html>



