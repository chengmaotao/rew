<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="blank"/><c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}"><link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
    <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script></c:if>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 4px 0 6px;}
		#header {margin:0 0 8px;position:static;} #header li {font-size:14px;_font-size:12px;}
		#header .navbar-inner{height:50px;background:url(${ctxStatic}/zkr/cxs/images/nav_bg_2.png) repeat;}
		#header #menu>li>a{padding-left:20px;padding-right:10px;font-weight:bold;}
		#header #menu>li>a:hover{background-color:transparent;}
		#header #menu>li.activ>a{padding: 16px 10px 10px 20px;}
		#header #menu>li.activ>a{border-bottom: 5px solid #f98a00;}
		#header #menu>li.activ>a:hover{border-bottom: 5px solid #f98a00;}
		#header #menu>li li>a{color:#333;text-shadow:none;}
		#header #menu>li li.active > a,
		#header #menu>li li > a:hover{background:url(${ctxStatic}/zkr/cxs/images/nav_hover.png) repeat;border-radius: 6px;color:#fff;}	
		#header #userControl>li,
		#header #menu>li{height: 50px;}
		#header .brand img {margin:-6px 20px 0 25px;}
		#header .brand {width:332px;font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:22px;font-weight:bold;margin-top:-8px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} #left{overflow-x:hidden;overflow-y:auto;} #left .collapse{position:static;}
		#userControl>li>a{/*color:#fff;*/text-shadow:none;} #userControl>li>a:hover, #user #userControl>li.open>a{background:transparent;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			//初始化导航样式
			var liss=$("#header #menu li li");
			for(var i=0;i<liss.length;i++){
				if(/active/g.test(liss[i].className)){
					liss[i].parentNode.parentNode.className+=' activ';
					break;
				}
			}
		
			// <c:if test="${tabmode eq '1'}"> 初始化页签
			$.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: { 'height': $('#right').height() - tabTitleHeight },
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>
			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				// 左侧区域隐藏
				if ($(this).attr("target") == "mainFrame"){
					$("#left,#openClose").hide();
					wSizeWidth();
					// <c:if test="${tabmode eq '1'}"> 隐藏页签
					$(".jericho_tab").hide();
					$("#mainFrame").show();//</c:if>
					return true;
				}
				// 左侧区域显示
				$("#left,#openClose").show();
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
				// 显示二级菜单
				var menuId = "#menu-" + $(this).attr("data-id");
				if ($(menuId).length > 0){
					$("#left .accordion").hide();
					$(menuId).show();
					// 初始化点击第一个二级菜单
					if (!$(menuId + " .accordion-body:first").hasClass('in')){
						$(menuId + " .accordion-heading:first a").click();
					}
					if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
						$(menuId + " .accordion-body a:first i").click();
					}
					// 初始化点击第一个三级菜单
					$(menuId + " .accordion-body li:first li:first a:first i").click();
				}else{
					// 获取二级菜单数据
					$.get($(this).attr("data-href"), function(data){
						if (data.indexOf("id=\"loginForm\"") != -1){
							alert('未登录或登录超时。请重新登录，谢谢！');
							top.location = "${ctx}";
							return false;
						}
						$("#left .accordion").hide();
						$("#left").append(data);
						// 链接去掉虚框
						$(menuId + " a").bind("focus",function() {
							if(this.blur) {this.blur()};
						});
						// 二级标题
						$(menuId + " .accordion-heading a").click(function(){
							$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
							if(!$($(this).attr('data-href')).hasClass('in')){
								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
							}
						});
						// 二级内容
						$(menuId + " .accordion-body a").click(function(){
							$(menuId + " li").removeClass("active");
							$(menuId + " li i").removeClass("icon-white");
							$(this).parent().addClass("active");
							$(this).children("i").addClass("icon-white");
						});
						// 展现三级
						$(menuId + " .accordion-inner a").click(function(){
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							// <c:if test="${tabmode eq '1'}"> 打开显示页签
							return addTab($(this)); // </c:if>
						});
						// 默认选中第一个菜单
						$(menuId + " .accordion-body a:first i").click();
						$(menuId + " .accordion-body li:first li:first a:first i").click();
					});
				}
				// 大小宽度调整
				wSizeWidth();
				return false;
			});
			// 初始化点击第一个一级菜单
			//$("#menu a.menu:first span").click();
			
			// 用户管理
			
			if(${isGyly}){
				$("#menu a.menu:first span").click();
			}else{
			 	if(document.getElementById("e85140975ade477989127f9ae4a77fb7")){
					// 评审专家
					$("#e85140975ade477989127f9ae4a77fb7").click(); //成茂涛
				}else if(document.getElementById("1bf8d6ed096d405e890ac6e7e45f210b")){
					$("#1bf8d6ed096d405e890ac6e7e45f210b").click(); //复审第一轮投票
				}else{
					$("#menu a.menu:first span").click();
				}
			}
			
			// <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
			$("#userInfo .dropdown-menu a").mouseup(function(){
				return addTab($(this), true);
			});// </c:if>
			// 鼠标移动到边界自动弹出左侧菜单
			$("#openClose").mouseover(function(){
				if($(this).hasClass("open")){
					$(this).click();
				}
			});
			
			//在点击子导航时设置父导航的样式
			$("#header #menu li li").click(function(){
				var lis=$('#menu>li');
				for(var i=0;i<lis.length;i++){
					lis[i].className=lis[i].className.replace(' activ','');
				}
				this.parentNode.parentNode.className+=' activ';
			});
		});
		// <c:if test="${tabmode eq '1'}"> 添加一个页签
		function addTab($this, refresh){
			$(".jericho_tab").show();
			$("#mainFrame").hide();
			$.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
			return false;
		}// </c:if>
	</script>
</head>
<body>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top navbar-default">
			<div class="navbar-inner">
				<div class="brand"><img src="${ctxStatic}/zkr/cxs/images/logo3.png"><span>海洋工程科学技术奖</span></div>
				<ul id="userControl" class="nav pull-right">
					<li id="userInfo" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<i class="icon-chevron-down"></i></a>
						<%--<ul class="dropdown-menu">
							<li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
							<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
						</ul>--%>
					</li>
					<li><a href="${ctx}/logout" title="退出登录">退出</a></li>
					<li>&nbsp;</li>
				</ul>
				
				
				
				<div class="nav-collapse collapse">
				
				<ul id="menu" class="nav navbar-nav navbar-right" style="*white-space:nowrap;float:none;">
					 <!-- 初审  开始 -->
					  <c:if test="${csmenu}">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">初审 <i class="icon-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<c:set var="firstMenu" value="true"/>
								<c:forEach items="${fns:getMenuList1()}" var="menu" varStatus="idxStatus">
									<c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
									<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
										<c:if test="${empty menu.href}">
											<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}"><span id="${menu.id}">${menu.name}</span></a>
										</c:if>
										<c:if test="${not empty menu.href}">
											<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame"><span id="${menu.id}">${menu.name}</span></a>
										</c:if>
									</li>
									<c:if test="${firstMenu}">
										<c:set var="firstMenuId" value="${menu.id}"/>
									</c:if>
									<c:set var="firstMenu" value="false"/>
									</c:if>
								
									<c:if test="${!idxStatus.last }">
										<li role="separator" class="divider"></li>
									</c:if>	
								</c:forEach>	
							</ul>
						</li>
					</c:if>
				  <!-- 初审  结束 -->
				
				 <!-- 复审  开始 -->
				 <c:if test="${fsmenu}">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">复审 <i class="icon-chevron-down"></i></a>
						<ul class="dropdown-menu">
							<c:set var="firstMenu" value="true"/>
							<c:forEach items="${fns:getMenuList2()}" var="menu" varStatus="idxStatus">
								<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
									<c:if test="${empty menu.href}">
										<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}"><span id="${menu.id}">${menu.name}</span></a>
									</c:if>
									<c:if test="${not empty menu.href}">
										<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame"><span id="${menu.id}">${menu.name}</span></a>
									</c:if>
								</li>
									<c:if test="${firstMenu}">
										<c:set var="firstMenuId" value="${menu.id}"/>
									</c:if>
									<c:set var="firstMenu" value="false"/>
									<c:if test="${!idxStatus.last}"> 
										<li role="separator" class="divider"></li>
									</c:if>
								</c:if>
							</c:forEach>
						</ul>
					</li>
				</c:if>
				<!-- 复审  结束 -->
				</ul>
				</div>
			</div>
	 	</div>
	    <div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left"><%-- 
					<iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe> --%>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
				</div>
			</div>		
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = 160; // 左侧窗口大小
		var tabTitleHeight = 33; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
			mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
			$("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}"> 
			$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
			wSizeWidth();
		}
		function wSizeWidth(){
			if (!$("#openClose").is(":hidden")){
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
				$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			}else{
				$("#right").width("100%");
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b){
			$.fn.jerichoTab.resize();
		} // </c:if>
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>