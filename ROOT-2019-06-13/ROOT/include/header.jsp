<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Map<String, Object> res = (Map<String, Object>) request.getAttribute("_res");
	if (res.get("avatar") == null || "".equals(res.get("avatar"))) {
		res.put("avatar", request.getAttribute("url") + "/images/avatar.gif");
	}
	if (res.get("title") == null) {
		String host = request.getHeader("host");
		System.out.println(host);
		if (host.indexOf(":") != -1) {
			host = host.substring(0, host.indexOf(":"));
		}
		res.put("title", host);
	}
%>
<!DOCTYPE html>
<html lang="zh" class="no-js">
<head>
<jsp:include page="core/core_mate.jsp" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />

<link rel="stylesheet" type="text/css" media="screen"
	href="${templateUrl}/css/common.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${templateUrl}/css/style_2015.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${templateUrl}/css/pager.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${templateUrl}/css/editormd.css" />

<script src="${templateUrl }/js/lib/jquery-1.10.2.min.js"></script>
<script src="${templateUrl}/js/lib/modernizr.custom.16617.js"></script>

<meta charset="UTF-8" />
<meta name="keywords"
	content="指纹,身份认证,FIDO,指纹鼠标,指纹键盘,TUSI,IFAA,PKI,数字证书,U2F,UAF" />
<meta name="description" content="互啊佑-指纹技术应用运营专家" />
<meta http-equiv="X-UA-COMPATIBLE" content="IE=Edge,chrome=1" />
<meta http-equiv="X-UA-COMPATIBLE" content="IE=9" />
<meta name="viewport"
	content="width=device-width , initial-scale=1.0, minimum-scale=0.5, maximum-scale=1.5,user-scalable=yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="HandheldFriendly" content="True" />
<meta name="MobileOptimized" content="320" />
<link rel="shortcut icon" type="image/x-icon" href="images/1.ico" />
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<meta name="format-detection" content="telephone=no, email=no" />
<link href="${templateUrl}/css/bootstrap.min.css"
	tppabs="http://www.5wei.com/css/bootstrap.min.css" rel="stylesheet" />
<link href="${templateUrl}/css/carousel.css"
	tppabs="http://www.5wei.com/css/carousel.css" rel="stylesheet" />
<link href="${templateUrl}/css/animate.css"
	tppabs="http://www.5wei.com/css/animate.css" rel="stylesheet" />
<script src="${templateUrl}/js/jquery-2.2.4.min.js"
	tppabs="http://www.5wei.com/js/jquery-2.2.4.min.js"></script>
<script src="${templateUrl}/js/all.js"
	tppabs="http://www.5wei.com/js/all.js"></script>
<script src="${templateUrl}/js/main.js"
	tppabs="http://www.5wei.com/js/main.js"></script>
<script src="${templateUrl}/js/stickUp.min.js"
	tppabs="http://www.5wei.com/js/stickUp.min.js"></script>
<script src="${templateUrl}/js/modernizr-2.8.3.min.js"
	tppabs="http://www.5wei.com/js/modernizr-2.8.3.min.js"></script>
<link href="${templateUrl}/assets/css/pages/blog.css" rel="stylesheet"
	type="text/css" />
<script
	src="https://s22.cnzz.com/z_stat.php?id=1272998335&web_id=1272998335"
	language="JavaScript"></script>
<link rel="stylesheet" href="${templateUrl}/css/main.css"
	tppabs="http://www.5wei.com/css/main.css" />
<link rel="stylesheet" href="${templateUrl}/css/icons.css"
	tppabs="http://www.5wei.com/css/icons.css" />
<link rel="stylesheet" href="${templateUrl}/css/mobile.css"
	tppabs="http://www.5wei.com/css/mobile.css" />
<title>互啊佑-指纹技术应用运营专家</title>

<script>
!function(e) {
	var c = {
		nonSecure : "8123",
		secure : "8124"
	}, t = {
		nonSecure : "http://",
		secure : "https://"
	}, r = {
		nonSecure : "127.0.0.1",
		secure : "gapdebug.local.genuitec.com"
	}, n = "https:" === window.location.protocol ? "secure" : "nonSecure";
	script = e.createElement("script"), script.type = "text/javascript",
			script.async = !0, script.src = t[n] + r[n] + ":" + c[n]
					+ "/codelive-assets/bundle.js", e
					.getElementsByTagName("head")[0].appendChild(script)
}(document);
</script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"8123",secure:"8124"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<div class="public-header" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-108"
	data-genuitec-path="/zrlog/src/main/webapp/include/templates/default/index.htm">
	<div class="header">
		<div class="navbar navbar-inverse" role="navigation">
			<div class="container-fluid container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar"
						style="margin-top: 16px">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand logo" href="javascript:goToIndex();"> <img
						src="${templateUrl}/images/huAYouImages/PC/index/1_Home_logo.png" />
					</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li class=""><a href="javascript:goToIndex();">首页</a></li>
						<li class=" none-dropdown" style="display: block"><a
							href="${templateUrl}/case/case1.html">产品</a></li>
						<li class="dropdown" id="/feature" style="display: none"><a
							href="" class="dropdown-toggle" data-toggle="dropdown">产品 <span
								style="float: right" class="glyphicon glyphicon-chevron-right"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a class="linkhover"
									href="${templateUrl}/case/case1.html">指纹电脑外设</a></li>
								<li><a class="linkhover"
									href="${templateUrl}/case/case2.html">指纹身份认证&amp;鉴权系统</a></li>
								<li><a class="linkhover"
									href="${templateUrl}/case/case3.html">配套软件及服务</a></li>
							</ul></li>
						<li class=""><a href="${templateUrl}/feature.htm">解决方案</a></li>
						<li class=""><a href="${templateUrl}/dzfw.html">服务定制</a></li>
						<li class=""><a href="${templateUrl}/zwjs.html">指纹技术</a></li>
						<li class="none-dropdown"><a
							href="${templateUrl}/serve/serve.html">客户类型</a></li>
						<li class="dropdown" id="/feature" style="display: none"><a
							href="${templateUrl}/feature.htm" class="dropdown-toggle"
							data-toggle="dropdown">客户类型 <span style="float: right"
								class="glyphicon glyphicon-chevron-right"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a class="linkhover"
									href="${templateUrl}/serve/serve.html">个人用户</a></li>
								<li><a class="linkhover"
									href="${templateUrl}/serve/corporateuser.html">企业用户</a></li>
								<li><a class="linkhover" href="${templateUrl}/product.html">硬件生产厂商</a></li>
							</ul></li>
						<li><a class="about-us active1" href="javascript:goToNews();">新闻中心</a></li>
						<li class=""><a class="about-us"
							href="${templateUrl}/about_us.htm">关于我们</a></li>
						<!--  '${templateUrl}	http://localhost:8080/WayWebSite/include' -->
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<br />
<br class="mobile-rm-br" />
<br />
<br />