var systemName = 'WAYWebSiteSystem',
	appName = 'WAYWebSiteApp',
	rpName = 'WayWebSiteSystem',
	devUrl = 'https://localhost:8443/WayFido2Server/wayfido2rest/';
	proUrl = 'https://fido2.whoareyou.live/wayfido2rest/',
	url = '';

$(function() {
	var curPageUrl = window.document.location.href;
	if(curPageUrl.startsWith("https://www.whoareyou.live")) {
		url = proUrl;
	} else {
		url = devUrl;
	}
	
	PNotify.prototype.options.delay == 3000;
	$('.switchery').on("click", function() {
		var input = $(this).previousSibling();
		if (input.val() == 'off') {
			input.val("on");
			input.checked = true;
		} else {
			input.val("off");
			input.checked = on;
		}
	});
	$(".btn-info").click(function() {
		var formId = $(this).attr("id") + "Ajax";
		// 添加安全密钥单击事件取消一下Ajax请求
		if(formId == "addDeviceAjax") {
			return;
		}
		if ($("#" + formId).attr("checkBox")) {
			var checkBoxNames = $("#" + formId).attr("checkBox").split(",");
			for (var i = 0; i < checkBoxNames.length; i++) {
				checkBoxName = checkBoxNames[i];
				if ($("[name='" + checkBoxName + "']").size()&& $("[name='" + checkBoxName + "']")[0].checked == true) {
					$("#" + checkBoxName).attr("name", checkBoxName).attr("value", "on");
				} else {
					$("#" + checkBoxName).attr("name", checkBoxName).attr("value", "off");
				}
			}
		}
		var uri;
		if ($("#" + formId).attr("action") != null) {
			uri = $("#" + formId).attr("action");
		} else {
			uri = 'api/admin/website/update'
		}

		$.post(uri, $("#" + formId).serialize(), function(data) {
			if (data.error == 0) {
				var message;
				if (data.message != null && data.message != '') {
					message = data.message;
				} else {
					message = "操作成功...";
				}
				showInfo("success", message)
			} else {
				var message;
				if (data.message != null && data.message != '') {
					message = data.message;
				} else {
					message = "发生了一些异常...";
				}
				showInfo('error', message);
			}
		});
	});
	// 获取token,并存入cookie
	$.ajax({
		type : "GET",
        url : url + 'token',
		//url : "https://u2f.whoareyou.live/wayfidoapi/v1/app/token",
		//url : "https://localhost:8443/WayU2FServer/wayfidoapi/v1/app/token",
		data : {
			/*systemName : systemName,
			appName : appName*/
            rpName : rpName
		},
		dataType : "json",
		success : function(date) {
			if (date.responseCode != "RP001") {
				showInfo('error', "安全设备操作权限获取失败!");
				return;
			}
			$.cookie("token", date.object);
			getDevice();
		}
	});
});

/**
 * 设备列表
 */
function getDevice() {
	$.ajax({
		headers : {
    		Authorization : "Bearer " + $.cookie("token"),
    	},
        //url : 'htpps://fido2.whoareyou.live/whoareyou/v1/device',
        //url : "https://u2f.whoareyou.live/wayfidoapi/v1/device2/devices",
        url : url + 'v1/device',

        type : "GET",
        //dataType : 'json',
		data : {
			rpName : rpName,
			userId : $("#userId").html()
		},
        success : function (data) {
			$("#deviceList")[0].innerHTML = "";
			var o = '';
			if (data.responseCode == "DEVICE001") {
				// 遍历obj数据集合
				$(data.object).each(function() {
					o = o + '<div style="padding:1%;float:left;width:100%;border-bottom: 1px solid #E5E5E5;">'
							+ ' <div style="font-size:20px;"><b>'
							//+ this.name
							+ this.deviceName + '</b></div>'
							/*+ ' <div><small style="font-size:15px;" >编号：' + this.num + '</small></div>'*/
							+ ' <div><small>添加时间：'
							//+ this.addTime
							+ new Date(this.registrationTime * 1).dateFormat("yyyy-MM-dd hh:mm:ss")
							+ '</small><img style="float:right;margin-right:40%;'
							+ 'cursor:pointer;" src="admin/markdown/images/pen.png" onclick="updateDeviceModal('
							+ this.id + ',\'' + this.deviceName + '\')" />' + '</div>'
							+ ' <div><small>最后认证时间：' + new Date(this.lastAuthTime * 1).dateFormat("yyyy-MM-dd hh:mm:ss")
							+ '</small></div></div><br />';
				});
				$("#deviceList")[0].innerHTML = o;
			}
		}
	});
}

/**
 * 日期格式化方法
 * 方法作用：【格式化时间】
 * 使用方法
 * 示例：
 *      使用方式一：
 *      var now = new Date();
 *      var nowStr = now.dateFormat("yyyy-MM-dd hh:mm:ss");
 *      使用方式二：
 *      new Date().dateFormat("yyyy年MM月dd日");
 *      new Date().dateFormat("MM/dd/yyyy");
 *      new Date().dateFormat("yyyyMMdd");
 *      new Date().dateFormat("yyyy-MM-dd hh:mm:ss");
 * @param format {date} 传入要格式化的日期类型
 * @returns {2015-01-31 16:30:00}
 */
Date.prototype.dateFormat = function (format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}

/**
 * 注册设备
 */
function regDevice() {
	$("#show_div_next_device").hide();
	$("#show_error_massge").html("");
	// 修改图片，修改文字，隐藏提交按钮，隐藏输入框
	$("#show_div_img").attr('src', "admin/markdown/images/show_register.png");
	$("#show_div_title").html("注册您的安全密钥");
	$("#show_div_content").html("按下安全密钥上的按钮或金色圆片（如 有）。");
	var request = null;
	
	// 获取注册前的数据
	$.ajax({
		headers : {
    		Authorization : "Bearer " + $.cookie("token"),
    	},
		type : "GET",
		//url : 'https://u2f.whoareyou.live/wayfidoapi/v1/device2/startRegistWayFido',
		//url : 'https://fido2.whoareyou.live/wayfido2rest/v1/wayStartRegister',
		url : url + 'v1/wayStartRegister',
		dataType : 'json',
		data : {
			userName: $("input[name='userName']").val(),
			rpName:rpName
		},
		success: function (startRegistrationSuccessResult) {
			if ("FIDOREGIST001" == startRegistrationSuccessResult.responseCode) {
                myExecuteRegisterRequest(startRegistrationSuccessResult)
                    .then(webauthn.responseToObject)
                    .then(result => {
                        const response = JSON.stringify(result);
                        $.ajax({
                            headers: {
                                Authorization: "Bearer " + $.cookie("token"),
                            },
                            url: url + 'v1/wayFinishRegistration',
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                requestId: startRegistrationSuccessResult.object.requestId,
                                credential: response,
                                userId: $("#userId").html()
                            },
                            success: function (finishRegistrationSuccessResult) {
                                if (finishRegistrationSuccessResult.responseCode == "FIDOREGIST002") {
                                	// 注册成功：修改图片，修改文字，隐藏提交按钮，隐藏输入框
									$("#updateDeviceId").val("");
									$("#show_div_img").attr('src', "admin/markdown/images/sussces.png");
									$("#show_div_title").html("安全密钥已完成注册");
									$("#show_div_content").html("您的安全密钥已完成注册，您可以为此安全密钥输入一个名称");
									$("#show_div_button").show();
									$("#show_div_input")[0].style.display = "block";
									getDevice();
								} else {
									$("#show_div_img").attr('src', "admin/markdown/images/info.png");
									$("#show_div_title").html("出了点问题！");
									//$("#show_div_content").html(finishRegistrationSuccessResult.responseMessage);
									console.log(finishRegistrationSuccessResult.responseMessage);
								}
                            },
                        });
                    })
                    .catch((err) => {
                        $("#show_div").modal('hide');
                        showInfo('error', JSON.stringify(err.message));
                    })
            }
		}
		
		/*success : function(date) {
			if (date.responseCode == "FIDOU2FREGIST001") { // 注册前数据获取成功
				request = eval("(" + date.object + ")");
				if (request != null) {
					setTimeout(function() {
						u2f.register(
							request.appId,
							request.registerRequests,
							request.registeredKeys,
							function(data) {
								if (data.errorCode) {
									switch (data.errorCode) {
										case 4:
											$("#show_error_div").show();
											$("#show_error_massge").html("此设备已注册。");
										break;
										 case 2: appId和浏览器路径数据不匹配 
										default:
											$("#show_error_div").show();
											$("#show_error_massge").html("注册失败，请重试。");
											setTimeout(regDevice, 1000);
									}
								} else {
									if ($('#show_div').css('display') == 'none') {
										return false;
									}
									$.ajax({
    							    	headers : {
    							    		Authorization : "Bearer " + $.cookie("token"),
    							    	},
    							    	type : "POST",
    							    	dataType :'json',
    							    	url : 'https://u2f.whoareyou.live/wayfidoapi/v1/device2/finishRegistWayFido',
    							    	data : {
    							    		systemName : systemName,
    							    		userUUId : $("#userId").html(),
    							    		tokenResponse : JSON.stringify(data),
    							    	},
    							    	success : function(date) {
	    							    	if (date.responseCode == "FIDOU2FREGIST002") {
	    							    		// 注册成功，修改层效果
												// 修改图片，修改文字，隐藏提交按钮，隐藏输入框
												$("#updateDeviceId").val("");
												$("#show_div_img").attr('src', "admin/markdown/images/sussces.png");
												$("#show_div_title").html("安全密钥已完成注册");
												$("#show_div_content").html("您的安全密钥已完成注册，您可以为此安全密钥输入一个名称");
												$("#show_div_button").show();
												$("#show_div_input")[0].style.display = "block";
												getDevice();
											} else {
												$("#show_div_img").attr('src', "admin/markdown/images/info.png");
												$("#show_div_title").html("出了点问题！");
												$("#show_div_content").html(date.message);
											}
    							    	}
									});
								}
							});
					}, 1000);
				} else {
					showInfo('error', '注册异常1');
				}
			} else {
				showInfo('error', '注册异常2');
			}
		}*/
	});
}
function myExecuteRegisterRequest(request) {
    return webauthn.createCredential(request.object.publicKeyCredentialCreationOptions);
}
/**
 * 弹出信息
 * 
 * @param message
 */
function showInfo(type, message) {
	new PNotify({
		title : message,
		type : type,
		delay : 5000,
		hide : true,
		styling : 'bootstrap3'
	});
}

/**
 * 保存设备名称
 */
function saveName() {
	//https://localhost:8443/WayU2FServer/wayfidoapi/v1/device2
	$.ajax({
		headers: {
            Authorization: "Bearer " + $.cookie("token"),
        },
        type: "PUT",
        dataType: 'json',
        url: url + 'v1/device',
		data : {
			rpName : rpName,
			userId : $("#userId").html(),
			deviceId : $("#updateDeviceId").val(),
			deviceName : $("#show_div_input").val()
		},
		success : function (date) {
			if (date.responseCode == "FIDOUPDATE001") {
				$('#show_div').modal('hide');
				getDevice();
			} else {
				// 修改图片，修改文字，显示提交按钮，显示
				$("#show_div_img").attr('src', "admin/markdown/images/info.png");
				$("#show_div_title").html("出了点问题，请重试");
				$("#show_div_content").html("");
				$("#show_div_button").show();
				$("#show_div_input")[0].style.display = "block";
			}
		}
	});
}

/**
 * 修改设备页面
 * 
 * @param id
 * @param userName
 */
function updateDeviceModal(id, userName) {
	$('#show_div').modal();
	$("#show_div_img").attr('src', "admin/markdown/images/double_device.png");
	$("#updateDeviceId").val(id);
	$("#show_div_input").val(userName);
	$("#show_div_title").html("安全密钥");
	$("#show_div_content").html("");

	$("#show_error_div").hide();
	$("#show_div_input").show();
	$("#show_div_button").show();
	$("#show_div_delect_device").show();
}

/**
 * 确认删除设备方法
 */
function delectDevice() {
	if (confirm("您确定要移除此设备吗？")) {
		$.ajax({
			headers : {
	    		Authorization : "Bearer " + $.cookie("token"),
	    	},
	    	type : "DELETE",
	    	dataType :'json',
	    	//url : 'https://u2f.whoareyou.live/wayfidoapi/v1/device2/' + $("#updateDeviceId").val(),
	    	url: url + 'v1/device/' + $("#updateDeviceId").val(),
	    	success : function(date) {
	    		if (date.responseCode == 'FIDODELETE001') {
	    			$('#show_div').modal('hide');
					$("#show_div_delect_device").hide();
					getDevice();
				} else {
					showInfo('error', "System Error!");
				}
	    	}
		});
	}
}

/**
 * 显示提示插入设备
 */
function showInserted() {
	$('#show_div').modal();
	$("#show_div_next_device").show();
	$("#show_div_delect_device").hide();
	$("#show_error_div").hide();
	$("#show_error_massge").html("");
	// 修改图片，修改文字，隐藏提交按钮，隐藏输入框
	$("#show_div_img").attr('src', "admin/markdown/images/show_register.png");
	$("#show_div_title").html("注册您的安全密钥");
	$("#show_div_content").html(
			"将您的安全密钥插入计算机的usb端口，或使用usb线将其与 <br/>计算机相连，确认连接后点击下一步");
	$("#show_div_button").hide();
	$("#show_div_input")[0].style.display = "none";
}
