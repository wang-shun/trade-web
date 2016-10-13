<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.json.min.js"></script>
		    
		 <script type="text/javascript">
			function submit(){
				var ctx = $("#ctx").val();
				
				// 1 客户
				/* var tgGuestInfo={
					'guestCode':$('#guestCode').val(),  // 客户编号
					'guestName':$('#guestName').val(),  // 姓名
					'guestPhone':$('#guestPhone').val(),  // 电话
					'identifyType':$('#identifyType').val(),  // 身份证件
					'identifyNumber':$('#identifyNumber').val(),  // 证件号码
					'transPosition':$('#transPosition').val()  // 上下家属性
				}; */
				
				// 2 物业信息
				var propertyAddr = $('#propertyAddr').val();  // 物业地址
				var propetyCode = $('#propetyCode').val();  // 房屋编码
				
				// 3 案件信息
				var ctmCode=$('#ctmCode').val();  // 案件编号
				var agentCode = $('#agentCode').val();  // 成交经纪人编号
				var requireProcessorId = $('#requireProcessorId').val();  // 请求处理人编号[交易顾问ID]
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url : ctx+'/api/ctm/case',
			        dataType:"json",
			        data:[{
			    		name:'ctm_case_code',
			    		value:'BHL-5-201512-0005'
			    	},{
			    		name:'agent_id',
			    		value:'tangmin6'
			    	},{
			    		name:'agent_name',
			    		value:'唐敏'
			    	},{
			    		name:'guestInfoList',
			    		value:"[{'guestName':'王志敏','guestPhone':'18051106816','transPosition':'0'},{'guestCode':'','guestName':'马鹤程','guestPhone':'13817562767','transPosition':'1'}]"
			    	},{
			    		name:'grp_code',
			    		value:'033I666'
			    	},{
			    		name:'grp_name',
			    		value:'ACBHLE.地杰国际城二店五组全体'
			    	},{
			    		name:'property_address',
			    		value:'上海浦东一区御桥片区御桥路2066弄8号0402室'
			    	},{
			    		name:'property_code',
			    		value:'070919165025E5F7F7E02791BDC4234C'
			    	}],
			        success: function(data) {
			        	console.info(data);
						alert(data.status);
			        },
			        error: function(errors) {
			        	alert("信息修改失败，请刷新后再次尝试！");
			        }
				});
			}
		</script>

</head>
<body>

	<input type="hidden" id="ctx" value="${ctx }" />
	
	<table>
		<tr>
			<td>案件编号: <input type="text" name="ctmCode" id="ctmCode" /></td>
			<td>经纪人编号: <input type="text" name="agentCode" id="agentCode" value="ff8080814ffd3de40150026a2d030104" /></td>
			<td>物业地址: <input type="text" name="propertyAddr" id="propertyAddr" /></td>
			<td>房屋编码: <input type="text" name="propetyCode" id="propetyCode" /></td>
			<td>交易顾问ID: <input type="text" name="requireProcessorId" id="requireProcessorId" value="ff8080814f490473014f49c6f1e30009" /></td>
		</tr>
	
		<tr>
			<td>客户编号: <input type="text" name="guestCode" id="guestCode" /></td>
			<td>姓名: <input type="text" name="guestName" id="guestName" /></td>
			<td>电话: <input type="text" name="guestPhone" id="guestPhone" /></td>
			<td>身份证件: <input type="text" name="identifyType" id="identifyType" /></td>
			<td>证件号码: <input type="text" name="identifyNumber" id="identifyNumber" /></td>
		</tr>
		
		<tr>
			<td>上下家属性: <input type="text" name="transPosition" id="transPosition" /></td>
		</tr>
		
		<tr>
			<td>
				<input type="button" value="提交" onclick="submit()" /> 
			</td>
		</tr>
	</table>

</body>
</html>