var	 HousComleteInfo=function(){
	
	
	var info={
		lname:"楼层1名称",
		lcengshu:[
			{
				id:1,cname:"层数名称1",
				hu:[{id:1,hushuNo:""},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:"户数编号1001"},
					{id:5,hushuNo:"户数编号1001"},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:"户数编号1001"},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:2,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:""},
					{id:5,hushuNo:"户数编号1001"},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:"户数编号1001"},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:3,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:"户数编号1001"},
					{id:5,hushuNo:""},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:""},
					{id:8,hushuNo:"户数编号1001"},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:4,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:""},
					{id:4,hushuNo:""},
					{id:5,hushuNo:""},
					{id:6,hushuNo:""},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:""},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:""}]
			},{
				id:5,cname:"层数名称1",
				hu:[{id:1,hushuNo:""},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:""},
					{id:4,hushuNo:""},
					{id:5,hushuNo:""},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:""},
					{id:9,hushuNo:""},
					{id:10,hushuNo:"户数编号1001"}]
			}
			
		]		
	}
	var info2={
		lname:"楼层2名称",
		lcengshu:[
			{
				id:1,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:""},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:""},
					{id:5,hushuNo:"户数编号1001"},
					{id:6,hushuNo:""},
					{id:7,hushuNo:""},
					{id:8,hushuNo:""},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:2,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:""},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:""},
					{id:5,hushuNo:""},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:""},
					{id:8,hushuNo:""},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:3,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:""},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:""},
					{id:5,hushuNo:""},
					{id:6,hushuNo:"户数编号1001"},
					{id:7,hushuNo:""},
					{id:8,hushuNo:"户数编号1001"},
					{id:9,hushuNo:""},
					{id:10,hushuNo:"户数编号1001"}]
			},{
				id:4,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:""},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:""},
					{id:5,hushuNo:"户数编号1001"},
					{id:6,hushuNo:""},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:""},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:""}]
			},{
				id:5,cname:"层数名称1",
				hu:[{id:1,hushuNo:"户数编号1001"},
					{id:2,hushuNo:"户数编号1001"},
					{id:3,hushuNo:"户数编号1001"},
					{id:4,hushuNo:"户数编号1001"},
					{id:5,hushuNo:"户数编号1001"},
					{id:6,hushuNo:""},
					{id:7,hushuNo:"户数编号1001"},
					{id:8,hushuNo:"户数编号1001"},
					{id:9,hushuNo:"户数编号1001"},
					{id:10,hushuNo:"户数编号1001"}]
			}
			
		]		
	}
	
	var dong={
		id:1,
		name:"小区名字",
		lous:[
			{
				id:1,
				name:"1栋",
				lou:info
			},
			{
				id:2,
				name:"2栋",
				lou:info2
			}
		] 
	}
	
	var tableId="ComleteInfo_table";
	
	var budingClass={
	 	tabClass:"budingTabClass",
	 	trClass:"budingTrClass",
	 	tdClass:"budingTdClass",
		tdClassActive:"budingTdClass active"
	}
	
		function createTable(id,info){
			
				var rowCount=info.length;
			
				var tableStr="<table id='"+tableId+"' class='"+budingClass.tabClass+"'></table>";
				
				$("#"+id).append(tableStr);
				
				var table=$("#"+tableId);
				for (var index = 0; index < rowCount; index++) {
					var lcengshu=info[index];
					
					var i=index+1;//行数
					var trId="tr_"+i;
					
					_createTr(trId);
					//每行的列数
					var colCount=lcengshu.hu.length;
					
					//var tdStyle="width:50px;height:35px;";
					
					//_createTd(trId,"td"+i,"第"+i+"层",tdStyle);
						
					
					for (var j = 0; j < colCount; j++) {
						var tdId="td_"+j;
						//
						var hu=lcengshu.hu[j]
						var c=j+1;
						
						_createTd(trId,tdId,c,hu.hushuNo!=""&&hu.hushuNo!=null?budingClass.tdClassActive:budingClass.tdClass);
						
					}
				}
				
				function _createTr(trId){
					table.append("<tr id='"+trId+"' class='"+budingClass.trClass+"'   ></tr>");
				}
				function _createTd(trId,tdId,str,tdClass){
					$('#'+trId).append("<td  id="+tdId+" class='"+tdClass+"' >"+str+"</td>");
				}
			
	};
	return {
		
		init:function(selectTag,divTag,budingClass){
			//获取楼盘信息
			/*$.ajax({    
	        	//url : '${ctx}/api/hou/advHouListAjax',  
           	    url : url, 
	            dataType:"json",    
	            global:false,     
	            success: function(data){
	            	
	            },
	             
	            error: function (data) {
	            	
                }
	       });*/
	       
	       	//初始化 select
			for (var index = 0; index < dong.lous.length; index++) {
				var a=dong.lous[index];	
	      		 var selectOption="<option value='"+a.id+"'>"+a.name+"</option>"
	       		$('#'+selectTag).append(selectOption);
			}
	       
			$('#'+selectTag).change( function () {
					var selectVal=$(this).val();
				console.log(selectVal);
				//移除table	
				$("#"+tableId).remove();
				
				var selectVal=selectVal-1;
				
				createTable(divTag,dong.lous[selectVal].lou.lcengshu);
			});
			console.log(dong.lous[0].lou.lcengshu.length);
			
			createTable(divTag,dong.lous[0].lou.lcengshu);
			
		}
		
	};	
}();