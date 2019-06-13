﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String scheme = com.fzb.blog.web.util.WebTools.getRealScheme(request);
String basePath = scheme+"://"+request.getHeader("host")+path+"/";
request.setAttribute("url", scheme+"://"+request.getHeader("host")+request.getContextPath());
%>
<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${init.webSite.title}- ${_res.login}</title>
<link rel="shortcut icon" href="${cacheFile['/favicon.ico']}" />
<link href="${cacheFile['/assets/css/bootstrap.min.css']}"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="${cacheFile['/assets/css/font-awesome.min.css']}"
	rel="stylesheet">
<!-- Custom Theme Style -->
<link href="${cacheFile['/assets/css/custom.min.css']}" rel="stylesheet">

<link rel="stylesheet" href="${cacheFile['/assets/css/pnotify.css']}" />
<!-- jQuery -->
<script src="${cacheFile['/assets/js/jquery.min.js']}"></script>
<script src="${cacheFile['/assets/js/bootstrap.min.js']}"></script>
<script src="${cacheFile['/assets/js/jquery.cookie.js']}"></script>
<script src="${cacheFile['/admin/js/u2f-api-1.1.js']}"></script>

<script src="${cacheFile['/admin/js/fido2/base64js-1.3.0.min.js']}"></script>
<script src="${cacheFile['/admin/js/fido2/base64url.js']}"></script>
<script src="${cacheFile['/admin/js/fido2/webauthn.js']}"></script>

<script src="${cacheFile['/assets/js/pnotify.js']}"></script>
<script src="${cacheFile['/admin/js/login.js']}"></script>


<script>!function(e){var c={nonSecure:"8123",secure:"8124"},t={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=t[n]+r[n]+":"+c[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document);</script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"8123",secure:"8124"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body class="login" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-65"
	data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-66">

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="show_div" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">设备登录</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<!-- 设备登录的显示 -->
					<span id="show_user_name" style="font-size: 20px;">用户名称</span>
					<div id="show_error_div">
					<br />
					<small id="show_error_massge" style="color: red;" ></small>
					<br />
					</div>
					<div>
						<img
							src="${cacheFile['/admin/markdown/images/security_key_laptop.gif']}" style="height: 150px;margin-left:-50px;" />
						<br />
					</div>
					<div>
						<br /> 两步验证：使用您的安全密钥登录<br />将您的安全密钥插入计算机的usb端口，<br />然后按上面的按钮（如果有的话）。
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div>

	<div data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-65"
		data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-66">
		<div class="login_wrapper">
			<div class="animate form login_form">
				<section class="login_content">
					<form id="login_form" action="${url}/api/admin/login">
						<input type="hidden" id="redirectFrom" name="redirectFrom"
							value="${param.redirectFrom}">
              			<h1>${_res.userNameAndPassword}</h1>
						<div>
							<input id="userName" name="userName" type="text"
								class="form-control" placeholder="${_res.userName}" required="" />
						</div>
						<div>
							<input id="password" name="password" type="password" class="form-control"
								placeholder="${_res.password}" required="" />
						</div>
						
						<div class='checkbox' > 
						<input type='checkbox' id='login_update_btn'> 
						<label for='login_update_btn' style="padding-left: 0px;">使用免密登录</label> </div>

						<div>
							<button class="btn btn-default" id="login_btn" type="button">
								  <i class="fa fa-sign-in"></i> ${_res.login}
							</button>	
						</div>
						<div class="separator">
							<div class="clearfix"></div>
							<br />
							<div>
								<p>
									<strong>${_res.copyright}</strong> ${init.webSite.title} All
									Rights Reserved.
								</p>
							</div>
						</div>
					</form>
				</section>
			</div>
		</div>

	</div>
</body>
</html>
