<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参数列表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>

	
</head>
<body>
	<%-- <sys:message content="${message}"/> --%>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">参数列表<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		  </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		
		
		<div id="right">
			<iframe id="dmlbContent" src="${ctx}/dict/rewawardlevel/list" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
	
	function fontCorlor(){};
	    	    
		    function getFont(treeId, node) {
		      if(node.colorFlag == 0){
		    	  fontCorlor.prototype.color = "";
		      }else{
		    	  fontCorlor.prototype.color = "red";
		      }  
		    	return new fontCorlor() ? new fontCorlor()  : {};   
		    }  
	
	
		var setting = {
	    view: {  
               nameIsHTML: true  
           },
			data:{simpleData:{enable:true,idKey:"id"}},
				
			callback:{onClick:function(treeId, treeNode){			            
                    var treeObj = $.fn.zTree.getZTreeObj(treeNode);

                    var selectedNode = treeObj.getSelectedNodes()[0];
                    
                    var selectId = selectedNode.id;
                    if("1" == selectId){
                    	// 奖励等级
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewawardlevel/list");
                    }else if("2" == selectId){
                    	// 专业组
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewgroup/list");
                    }else if("3" == selectId){
                    	// 小组设置
                    	//$('#dmlbContent').attr("src","${ctx}/dict/rewgroupson/list");
                    } else if("4" == selectId){
                    	// 分类设置
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewcategory/list"); 
                    } else if("5" == selectId){
                    	// 评价指标
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewscopekpi/list"); 
                    } else if("6" == selectId){
                    	// 项目
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewproject/list"); 
                    }else if("7" == selectId){
                    	// 菜单可用控制
                    	$('#dmlbContent').attr("src","${ctx}/dict/dmlb/initSyscontrol"); 
                    } else if("14" == selectId){
                    	// 复审项目
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewFsdatasrc/list"); 
                    	
                    	
                    }else if("9" == selectId){
                    	// 评审年度
                    	$('#dmlbContent').attr("src","${ctx}/dict/dmlb/currentyear"); 
                    }else if("10" == selectId){
                    	// 初审复审菜单显示
                    	$('#dmlbContent').attr("src","${ctx}/dict/dmlb/cfcontrol"); 
                    }else if("11" == selectId){
                    	// 历年投票数目
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewsysval"); 
                    }else if("12" == selectId){
                    	// 项目流程
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewProgress/indexProgress"); 
                    } else if("13" == selectId){
                    	// 项目流程结果
                    	$('#dmlbContent').attr("src","${ctx}/dict/rewRes/"); 
                    }                                  
				}
			}
		};
		
		
		function refreshTree(){
			$.getJSON("${ctx}/dict/dmlb/list",function(data){
				$.fn.zTree.init($("#ztree"), setting, data);
			});
		}
		 refreshTree(); 
		 
		var leftWidth = 180;
		     // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
		

		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>

</body>
</html>