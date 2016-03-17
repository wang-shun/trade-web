<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<title>屏幕首页</title>
	<link rel="stylesheet" href="../../../momedia/css/workload/css/style.css">
	<style type="text/css">
	</style>
</head>
  <body id="workload">
        <div class="container">
            <h2>
                区域：${org.orgName }
            </h2>
            <!-- 委派單數=4 -->
            <div style="display:block;height:82.35%;overflow:hidden;" id="div_main">
            	<div class="item-1 pull-left">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        未派单数
                        <strong class="pull-right" id="unAllocateCount">
                            0
                        </strong>
                    </div>
                    <div class="panel-body">
                    	<ul id="ul_unAlloate"></ul>
                    </div>
                </div>
            </div>
            <div class="item-2">
                <div class="panel panel-red">
                    <div class="panel-heading">
                        签约数
                        <strong class="pull-right" id="signCount">
                            0
                        </strong>
                    </div>
                    <div class="panel-body" id="div_signCount">
                    </div>
                </div>
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        过户数
                        <strong class="pull-right" id="houseTranRankCount">
                            0
                        </strong>
                    </div>
                    <div class="panel-body" id="div_trn_house">
                    </div>
                </div>
                <div class="panel panel-green">
                    <div class="panel-heading">
                        纯公积金数
                        <strong class="pull-right" id="cpfLoanCount">
                            0
                        </strong>
                    </div>
                    <div class="panel-body" id="div_cpf_loan">
                    </div>
                </div>
                <div class="panel panel-blue">
                    <div class="panel-heading">
                        商贷/组合贷数
                        <strong class="pull-right" id="comCount">
                            0
                        </strong>
                    </div>
                    <div class="panel-body" id="div_com">
                       
                    </div>
                </div>
            </div>
            </div>
            <!-- /委派單數=4 -->

           
            <div class="logo pull-right">
            </div>
            
        </div>
   
	<script src="../../../momedia/js/jquery-2.1.1.js"></script>
	    <script src= "../../../momedia/js/template.js" type="text/javascript" ></script>
	    <script id="rankDetils" type= "text/html">
			{{if data.length>0}}
				{{each data as item index}}
					{{if index%4==0}}<ul>{{/if}}
 					<li class="">
                                <div class="feed-element pull-left">
                                    <img class="img-circle" src="{{fileSev}}{{item.empCode}}.jpg" alt="img" onerror="showDefImg();"/>
                                    <span class="badge badge{{if index==0}}-danger{{/if}}{{if index==1}}-warning{{/if}}{{if index==2}}-yellow{{/if}}">
                                        {{index+1}}
                                    </span>
                                </div>
                                <div class="media-body">
                                    <p class="pull-left">
                                        {{item.belongOrgName}}
                                    </p>
                                    <strong class="pull-right text{{if index==0}}-red{{/if}}{{if index==1}}-orange{{/if}}{{if index==2}}-yellow{{/if}}">
                                        {{!item.rankValue?'0':item.rankValue}}
                                    </strong>
                                </div>
                            </li>
					{{if index%4==3}}</ul>{{/if}}
				{{/each}}
				{{if data.length%4!=0}}</ul>{{/if}}
			{{/if}}
		</script>
		<script id="rankDetils1" type= "text/html">
			{{if data.length>0}}
				{{each data as item index}}
 					<li class="">
                                <div class="feed-element pull-left">
                                    <img class="img-circle" src="{{fileSev}}{{item.empCode}}.jpg" alt="img" onerror="showDefImg();"/>
                                    <span class="badge badge{{if index==0}}-danger{{/if}}{{if index==1}}-warning{{/if}}{{if index==2}}-yellow{{/if}}">
                                        {{index+1}}
                                    </span>
                                </div>
                                <div class="media-body">
                                    <p class="pull-left">
                                        {{item.belongOrgName}}
                                    </p>
                                    <strong class="pull-right text{{if index==0}}-red{{/if}}{{if index==1}}-orange{{/if}}{{if index==2}}-yellow{{/if}}">
                                        {{!item.rankValue?'0':item.rankValue}}
                                    </strong>
                                </div>
                            </li>
				{{/each}}
				
			{{/if}}
		</script>
		
		<script>
	
		 
		var ctx="${ctx}";
		var orgId="${orgId}";
		function showDefImg(){
			event.target.src="../../../momedia/img/a5.png";
		}
		function loadData(){
			$.ajax({
				url : "../../../mobile/dashboard/box/workloadData",
				method:"post",
				data: {orgId: orgId},  
				dataType:"json",
				success : function(data) {
					  if(data.staUnallocateRank.length>4){
						  $("#div_main").addClass("box-sm");
					  }else{
						  $("#div_main").removeClass("box-sm");
					  }
					  var fs="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/";
					  $("#unAllocateCount").text(sumCount(data.staUnallocateRank));
					  var staUnallocateRank= template('rankDetils1' , {data:data.staUnallocateRank,fileSev:fs});
	                  $( "#ul_unAlloate").empty();
	                  $( "#ul_unAlloate").html(staUnallocateRank);
	                  
					  var staSignRank= template('rankDetils' , {data:data.staSignRank,fileSev:fs});
					  $("#signCount").text(sumCount(data.staSignRank));
	                  $( "#div_signCount").empty();
	                  $( "#div_signCount").html(staSignRank);
	                  
					  var staHouseTranRank= template('rankDetils' ,{data:data.staHouseTranRank,fileSev:fs});
					  $("#houseTranRankCount").text(sumCount(data.staHouseTranRank));
	                  $( "#div_trn_house").empty();
	                  $( "#div_trn_house").html(staHouseTranRank);
	                  
					  var staCpfLoanRank= template('rankDetils' , {data:data.staCpfLoanRank,fileSev:fs});
					  $("#cpfLoanCount").text(sumCount(data.staCpfLoanRank));
	                  $( "#div_cpf_loan").empty();
	                  $( "#div_cpf_loan").html(staCpfLoanRank);
	                  
					  var staBusinessOrComLoanRank= template('rankDetils' , {data:data.staBusinessOrComLoanRank,fileSev:fs});
					  $("#comCount").text(sumCount(data.staBusinessOrComLoanRank));
	                  $( "#div_com").empty();
	                  $( "#div_com").html(staBusinessOrComLoanRank);
				}
			});
		}
		loadData();
		function sumCount(d){
			var t=0;
			for(var a in d){
				if(d[a].rankValue){
					t+=d[a].rankValue;
				}
			}
			return t;
		}
		
		</script>
</body>
</html>