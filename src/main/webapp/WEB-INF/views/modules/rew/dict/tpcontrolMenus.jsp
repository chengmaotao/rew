<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<title>补投控制管理</title>
	<script src="${ctxStatic}/zkr/trcolor.js" type="text/javascript"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			trCorlor();
			if(${message != ''}){
				top.$.jBox.tip("${message}","1",{persistent:true,opacity:0});
			}
			
			$("#contentTable").on("click","tr",function(e){

				if(e.target.type != 'checkbox'&& e.target.type != 'select-one'){
					var cbx = $(this).find("input[type=checkbox]");
					if($(cbx).attr("checked")){
						$(cbx).removeAttr("checked");
					}else{
						$(cbx).attr("checked",true);
					}
				}
			});

		});
	
	</script>
</head>
<body>
	<form action="${ctx}/dict/dmlb/tpmenuSave" method="post">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th>菜单</th>
					<th>可用</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${tpcontrols}" var="tmenu" varStatus="s">
				<tr>				    
				    <td>${tmenu.name } 
				        <input type="hidden" value="${tmenu.id }" name="menus[${s.index }].id"> 
				        <input type="hidden" value="${tmenu.name }" name="menus[${s.index }].name">
				    </td>
				    
					<td>
					   <c:choose>
					   	<c:when test="${tmenu.enable == '1' }">
					   		<input type="checkbox" value="1" name="menus[${s.index }].enable" checked="checked">
					   	</c:when>
					   	<c:otherwise>
					   		<input type="checkbox" value="1" name="menus[${s.index }].enable">
					   	</c:otherwise>
					   </c:choose>

					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<input id="btnSubmit" class="btn btn-primary saveBtn" type="submit" value="保 存"/>
	</form>
</body>
</html>