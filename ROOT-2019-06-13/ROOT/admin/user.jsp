<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="include/menu.jsp" />

<script src="${cacheFile['/admin/js/fido2/base64js-1.3.0.min.js']}"></script>
<script src="${cacheFile['/admin/js/fido2/base64url.js']}"></script>
<script src="${cacheFile['/admin/js/fido2/webauthn.js']}"></script>

<script type="text/javascript" src="${cacheFile['/admin/js/set_update.js']}"
	data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-91"
	data-genuitec-path="/zrlog/src/main/webapp/admin/user.jsp"
	data-genuitec-lp-enabled="false" data-genuitec-file-id="wc2-92" data-genuitec-path="/zrlog/src/main/webapp/admin/user.jsp"></script>
<script type="text/javascript" src="${cacheFile['/admin/js/u2f-api-1.1.js']}"></script>
<script src="${cacheFile['/assets/js/jquery.liteuploader.min.js']}"></script>
<script src="${cacheFile['/assets/js/jquery.cookie.js']}"></script>

<script>
	$(document).ready(function() {
		$('.fileUpload').liteUploader({
			script : 'api/admin/upload/?dir=image'
		}).on('lu:success', function(e, response) {
			$('.file-name').attr("data-title", response.url)
			$("#logo").val(response.url);
			$("a .remove").remove();
		});
	});
</script>
<div class="page-header">
	<h3>${_res['admin.user.info']}</h3>
	<span id="userId" style="display:none;">${user.userId}</span>
</div>
<!-- PAGE CONTENT BEGINS -->

<!-- 模态框（Modal） -->
<div class="modal fade" id="show_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">设备注册</h4>
			</div>
			<input type="hidden" id="updateDeviceId" value=""/>
			<div class="modal-body" style="text-align: center;">
				<div id="show_error_div">
					<small id="show_error_massge" style="color: red;"></small><br/><br/>
				</div>
				<!-- 设备登录的显示 -->
				<div style="background-color: #4285F4; width: 100%; padding: 20px;">
					<img src="" id="show_div_img"/><br/>
				</div>
				<div>
					<br/><b><span style="font-size: 20px;" id="show_div_title">注册您的安全密钥</span></b><br />
					<br/><span id="show_div_content">将您的安全密钥插入计算机的usb端口，或使用usb线将其与
						<br/>计算机相连，然后按下安全密钥上的按钮或金色圆片（如 有）。
					</span> <br/> <br/>
					<input type="text" class="form-control" id="show_div_input" placeholder="安全密钥名称" style="display: none;" maxlength="20" />
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" style="display: none;" id="show_div_button" onclick="saveName();">完成</button>
				<button type="button" class="btn btn-primary" style="display: none;" id="show_div_delect_device" onclick="delectDevice();">拔出此安全密钥</button>
				<button type="button" class="btn btn-primary" style="display: none;" id="show_div_next_device" onclick="regDevice();">下一步</button>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12" id="hide_div">
		<form role="form" method="post" class="form-horizontal" id="userAjax" action="api/admin/update">
			<div class="form-group">
				<label for="form-field-1" class="col-sm-3 control-label no-padding-right"> ${_res.userName} </label>
				<div class="col-sm-3">
					<input type="text" name="userName" value="${user.userName }" class="form-control col-xs-12 col-sm-6" placeholder="" id="form-field-1">
				</div>
			</div>
			<div class="form-group">
				<label for="form-field-1" class="col-sm-3 control-label no-padding-right">${_res['email']} </label>
				<div class="col-sm-3">
					<input type="text" name="email" value="${user.email }" class="form-control col-xs-12 col-sm-6" placeholder="" id="form-field-1">
				</div>
			</div>
			<div class="form-group">
				<label for="form-field-1" class="col-sm-3 control-label no-padding-right">${_res['headPortrait']}</label>
				<div class="col-sm-9">
					<input id="logo" class="col-xs-6 col-sm-6" id="form-field-1" name="header" value="${user.header}">
					<input type="file" class="col-xs-6 fileUpload" id="form-field-1" name="imgFile" value="上传" />
				</div>
			</div>
			<div class="ln_solid"></div>

			<div class="form-group">
				<div class="col-md-offset-3 col-md-9">
					<button id="user" type="button" class="btn btn-info">
						<i class="fa fa-check bigger-110"></i> ${_res['submit']}
					</button>
				</div>
			</div>
		</form>
		<h3>安全密钥列表</h3>
		<div class="row">
			<div class="col-xs-12" style="border-top: 1px solid #E5E5E5; border-bottom: 1px solid #E5E5E5;">
				<div class="col-xs-3">&nbsp;</div>
				<div class="col-xs-9" style="border-left: 5px solid #4285F4; left: -36px;">
					<div class="col-xs-1">
						<img src="${cacheFile['/admin/markdown/images/device.png']}" style="margin-top: 15px;">
					</div>
					<div class="col-xs-11" id="deviceList"></div>
					<div class="col-xs-11" style="float: right; left: 6px; top: -5px;">
						<br/>
						<button id="addDevice" type="button" class="btn btn-info" onclick="showInserted();">添加安全密钥</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- PAGE CONTENT ENDS -->
<jsp:include page="include/footer.jsp" />