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
<html lang="zh-CN" class="no-js">
<head>
<jsp:include page="core/core_mate.jsp" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
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
</head>
<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-108"
	data-genuitec-path="/zrlog/src/main/webapp/include/templates/default/index.htm">
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
						<a class="navbar-brand logo" href="javascript:goToIndex();" tppabs="http://www.5wei.com/">
							<img
							src="${templateUrl}/images/huAYouImages/PC/index/1_Home_logo.png" />
						</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav navbar-right">
							<li class=" active1"><a href="javascript:goToIndex();">首页</a></li>
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
									<li><a class="linkhover"
										href="${templateUrl}/product.html">硬件生产厂商</a></li>
								</ul></li>
							<li><a class="about-us" href="javascript:goToNews();">新闻中心</a></li>
							<li class=""><a class="about-us"
								href="${templateUrl}/about_us.htm">关于我们</a></li>
							<!--  '${templateUrl}	http://localhost:8080/WayWebSite/include' -->
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="nav-banner">
		<div class="intro-header">
			<div class="container" style="margin-top: 9%;">
				<div class="row">
					<div class="col-lg-12">
						<div class="intro-message">
							<div id="index_001" class="img-title">
								<img style="display: block; max-width: 100%; height: auto;"
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_pic_01_1920.png">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="intro-header2">
			<div class="container pc-container mobile-container">
				<div class="row">
					<div class="col-md-12">
						<div class="intro-message2 top-220 mobile-intro2">
							<p class="sub-title_48 title_1366_1">联合知名电脑外设品牌商推出新一代指纹产品</p>
							<p class="sub-title_18 scr_1366_768_text16">由互啊佑（Who Are
								You）全面提供指纹技术支持</p>
							<div></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="intro-header3">
			<div class="container div_vertical div_vertical_sub1">
				<!-- 垂直居中 -->
				<div class="row div_vertical div_vertical_sub2">
					<div class="col-md-12">
						<div class="intro-message2 mobile-intro2_2">
							<p class="sub-title2 fias title_1366_1" style="color: #ffffff;">自主研发互啊佑（Who
								Are You）指纹身份认证&amp;鉴权系统（FIAS）</p>
							<p class="sub-title3 scr_1366_768_text16" style="color: #ffffff;">支持并可集成众多服务/应用，以满足个人用户、企业用户与开发商的各类需求</p>
						</div>
						<div class="img-title1">
							<img
								src="${templateUrl}/images/huAYouImages/PC/index/1_Home_pic_03.png" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="page2 div_vertical div_vertical_sub1 mobile-bottom2 1366_height">
		<div class="div_vertical div_vertical_sub2 1366_height">
			<div class="page2-title">
				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="intro-message2" style="padding: 0;">
							<p class="intro-p1 sub-title2 fias title_1366_1"
								style="color: #333333;">应用场景</p>
							<p class="sub-title3 pp-top scr_1366_768_text16"
								style="color: #333333;">让人们的生活、娱乐、工作变得更加安全便捷、充满乐趣！</p>
						</div>
					</div>
				</div>
			</div>
			<div class="container">
				<div class="row clearfix" style="margin-bottom: -10px;">
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div class="col-xs-12 col-md-12 sm-title wow bounceInRight"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_01.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_01.png" />
								<p class="title_1366_1">生活服务</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-3 mobile-bottom icon2">
						<div class="row">
							<div class="col-xs-12 col-md-12 sm-title wow bounceInRight"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_02.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_02.png" />
								<p class="title_1366_1">公共安全</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div class="col-xs-12 col-md-12 sm-title wow bounceInRight"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_03.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_03.png" />
								<p class="title_1366_1">民生建设</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-3 mobile-bottom icon2">
						<div class="row">
							<div class="col-xs-12 col-md-12 sm-title wow bounceInRight"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_04.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_04.png" />
								<p class="title_1366_1">金融系统</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div class="col-xs-12 col-md-12 sm-title wow bounceInRight"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_05.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_05.png" />
								<p class="title_1366_1">文化娱乐</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div
								class="col-xs-12 col-md-12 sm-title wow bounceInRight div_margbottom_none"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_06.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_06.png" />
								<p class="title_1366_1">智能设备</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-3 mobile-bottom icon2">
						<div class="row">
							<div
								class="col-xs-12 col-md-12 sm-title wow bounceInRight div_margbottom_none"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_07.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_07.png" />
								<p class="title_1366_1">物联网</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div
								class="col-xs-12 col-md-12 sm-title wow bounceInRight div_margbottom_none"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_08.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_08.png" />
								<p class="title_1366_1">区块链</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-3 mobile-bottom icon2">
						<div class="row">
							<div
								class="col-xs-12 col-md-12 sm-title wow bounceInRight div_margbottom_none"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_09.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_09.png" />
								<p class="title_1366_1">企业服务</p>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-md-2 mobile-bottom icon2">
						<div class="row">
							<div
								class="col-xs-12 col-md-12 sm-title wow bounceInRight div_margbottom_none"
								data-wow-duration="2s" data-wow-delay="0s">
								<img
									src="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_10.png"
									tppabs="${templateUrl}/images/huAYouImages/PC/index/1_Home_icon_10.png" />
								<p class="title_1366_1">更多</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="intro-header3 partner-intro"
		style="background-color: #f2f2f2;">
		<div class="container div_vertical div_vertical_sub1">
			<div class="row div_vertical div_vertical_sub2">
				<div class="col-md-12">
					<div class="intro-message2">
						<p class="sub-title2 partner title_1366_1" style="color: #333333;">部分合作伙伴</p>
						<img class="pc-img-show"
							src="${templateUrl}/images/huAYouImages/PC/index/1_Home_pic_10.png" />
						<img class="mobile-img-show"
							src="${templateUrl}/images/huAYouImages/Mobile/index/1_Home_logo_1.png"
							style="display: none;" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="footer">
		<div class="container index-footer">
			<div class="row">
				<div
					class="col-xs-5 col-xs-offset-1 col-sm-4 footer-contract mobile-foot1">
					<div>
						<p>咨询、购买请联系客户经理</p>
					</div>
					<div class="mobile-foot2">
						<p class="footer-tel">
							<span class="icon-index-tel" style="color: #26e5ea;"></span>400-960-7980
						</p>
					</div>
					<div>
						<p class="footer-email">
							<span class="icon-index-email" style="color: #26e5ea;"></span>bd@whoareyou.live
						</p>
					</div>
				</div>
				<div class=" col-xs-5 col-xs-offset-1 col-sm-2  footer-qrcode">
					<img class="img-responsive"
						src="${templateUrl}/images/huAYouImages/PC/index/1_Home_pic_04.png" />
				</div>
				<div class="col-sm-1 line"></div>
				<div class="col-xs-12 col-sm-5 col-md-5 footer-agent">
					<div>
						<div class="aaaaa mobile-foot">互啊佑期待与您的合作</div>
						<div class="aaaaa">如果您希望与我们建立市场合作</div>
						<div class="aaaaa">请登记您的信息</div>
					</div>
					<a style="margin-top: 15px; border: none;"
						class="btn btn-lg btn-danger btn-footer"
						href="http://cn.mikecrm.com/ha6nYlc">开始登记</a>
				</div>
			</div>
		</div>
		<div class="footer-com">&copy;2014-2018 互啊佑智能科技有限公司 版权所有</div>
	</div>

</body>
</html>