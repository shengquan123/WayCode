var systemName = 'WAYAdminServerSystem',
    appName = 'WAYAdminServerApp',
    url = "",
    flag = true;

var rpName = 'WAYAdminServerSystem';

$(function () {
    /* 免密登录，隐藏密码和验证码 */
    $("#login_pwdless").click(function () {
        $("#password").toggle(1000);
        $("#kaptcha_div").toggle(1000);

        if (flag) {
            $("#remember").hide();
            $("#login_pwdless").css("margin-left", "0");
            $("#label_pwdless").css("margin-left", "0");
            flag = false;
        } else {
            $("#remember").show();
            $("#login_pwdless").css("margin-left", "135px");
            $("#label_pwdless").css("margin-left", "135px");
            flag = true;
        }
    });

    $.ajax({
        dataType: "json",
        contentType: "application/json",
        type: "get",
        url: "fidorest/app/enviroment", // 配置中过滤掉此请求的拦截
        success: function (date) {
            url = date.url;
        }
    });
});

/**
 * 登录按钮单击事件
 * 取消form的自动提交
 * 密码登录-->手动提交; 免密登录-->请求接口认证
 * @returns {Boolean}
 */
function formSubmit() {
    $("#error_h4").html("");
    var fidoFlag = $("#login_pwdless").is(':checked');
    if (fidoFlag) { // 免密登录
        $.ajax({
            /* 获取token */
            type: "GET",
            url: url + 'token',
            data: {
                rpName: rpName
            },
            dataType: "json",
            success: function (date) {
                if (date.responseCode != "RP001") {
                    $("#show_error_div").show();
                    $("#show_error_massge").html("应用权限问题，请联系管理员！");
                    return;
                }
                $.cookie("token", date.object);
                $.ajax({
                    /*认证1*/
                    type: "GET",
                    url: "startLogin", // 配置中过滤掉此请求的拦截
                    data: {
                        userName: $("input[name=username]").val()
                    },
                    success: function (date) {
                    	if(date.responseCode == "FIDOFIDO2VALIDATE101") {
                    		$.ajax({
                    			headers: {
                                	Authorization: "Bearer " + $.cookie("token"),
                            	},
                            	url: url + '/v1/wayStartAuthentication',
                    			type: 'GET',
                    			dataType: "json",
                    			data: {
                    				userName: $("input[name=username]").val(),
                    				rpName: rpName
                    			},
                    			success: function(mystartAuthenticationSuccessResult) {
                    				if ("FIDOVALIDATE001" == mystartAuthenticationSuccessResult.responseCode) {
                    					if(mystartAuthenticationSuccessResult.object.publicKeyCredentialRequestOptions.allowCredentials.length==0) {
                    						showInfo('error', '当前用户未添加设备!');
                    						return;
                    					}
                    					myExecuteAuthenticateRequest(mystartAuthenticationSuccessResult)
                    					.then(webauthn.responseToObject)
                    					.then(result => {
                    						const response = JSON.stringify(result);
                    						$.ajax({
                    							headers: {
                                                	Authorization: "Bearer " + $.cookie("token"),
                                            	},
                    	                        url: url + '/v1/wayFinishAuthentication',
                    	                        type: 'PUT',
                    	                        dataType: 'json',
                    	                        data: {
                    	                            requestId: mystartAuthenticationSuccessResult.object.requestId,
                    	                            credential: response
                    	                        },
                    	                        success: function (myFinishAuthenticationSuccessResult) {
                    	                            if (myFinishAuthenticationSuccessResult.responseCode == "FIDOVALIDATE003") {
                    	                                $.ajax({
                    	                                	type: "GET",
                    	                                	url: "finishLogin", // 配置中过滤掉此请求的拦截
                    	                                	data: {
                    	                                		userName: $("input[name=username]").val()
                    	                                	},
                    	                                	success: function(finishLoginResult) {
                    	                                		if(finishLoginResult.responseCode == "FIDOFIDO2LOGIN001") {
                    	                                			window.location.href = 'login';//重定向
                    	                                		}
                    	                                	}
                    	                                })
                    	                            }
                    	                        }
                    	                    });
                    					}).catch((err) => {
                                            console.log('error', JSON.stringify(err.message));
                                        })
                    				} else {
                    					showInfo('error', JSON.stringify(mystartAuthenticationSuccessResult.responseMessage));
                    				}
                    			}
                    		});
                    	} else {
                    		showInfo('error',date.responseMessage);
        				}
                    }
                })
            }
        });
    } else { // 密码登录
        $("form").submit();
    }
    return false; // 取消form的自动提交，改为手动提交
}

/**
 * U2F设备认证
 * 删除
 * @param request
 */
function verification(request) {
    if (request.signRequests.length > 0) {
        u2f.sign(request.appId, request.challenge, request.signRequests, function (data) {
            if (data.errorCode) {
                switch (data.errorCode) {
                    case 4:
                        $("#show_error_div").show();
                        $("#show_error_massge").html("登录失败，此帐号尚未注册此设备。");
                        break;

                    default:
                        $("#show_error_div").show();
                        $("#show_error_massge").html("登录失败，请重试。");
                }
                return;
            } else {
                $.ajax({
                    headers: {
                        Authorization: "Bearer " + $.cookie("token"),
                    },
                    dataType: "json",
                    type: "post",
                    url: "finishLogin", // 配置中过滤掉此请求的拦截
                    data: {
                        systemName: systemName,
                        userName: $("input[name=username]").val(),
                        tokenResponse: JSON.stringify(data)
                    },
                    success: function (date) {
                        $('#show_div').modal('hide');
                        if (date.responseCode == "FIDOU2FVALIDATE202") {
                            window.location.href = 'login';//重定向
                        } else {
                            $("#error_h4").html(date.responseMessage);
                        }
                    }
                });
            }
        });
    }
}

function myExecuteAuthenticateRequest(request) {
    return webauthn.getAssertion(request.object.publicKeyCredentialRequestOptions);
}

function showInfo(type, message) {
    new PNotify({
        title: message,
        type: type,
        delay: 3000,
        hide: true,
        styling: 'bootstrap3'
    });
}