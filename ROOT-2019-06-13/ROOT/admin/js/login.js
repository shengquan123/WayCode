MD5 = function(e) {
    function h(a, b) {
        var c, d, e, f, g;
        e = a & 2147483648;
        f = b & 2147483648;
        c = a & 1073741824;
        d = b & 1073741824;
        g = (a & 1073741823) + (b & 1073741823);
        return c & d ? g ^ 2147483648 ^ e ^ f: c | d ? g & 1073741824 ? g ^ 3221225472 ^ e ^ f: g ^ 1073741824 ^ e ^ f: g ^ e ^ f
    }

    function k(a, b, c, d, e, f, g) {
        a = h(a, h(h(b & c | ~b & d, e), g));
        return h(a << f | a >>> 32 - f, b)
    }

    function l(a, b, c, d, e, f, g) {
        a = h(a, h(h(b & d | c & ~d, e), g));
        return h(a << f | a >>> 32 - f, b)
    }

    function m(a, b, d, c, e, f, g) {
        a = h(a, h(h(b ^ d ^ c, e), g));
        return h(a << f | a >>> 32 - f, b)
    }

    function n(a, b, d, c, e, f, g) {
        a = h(a, h(h(d ^ (b | ~c), e), g));
        return h(a << f | a >>> 32 - f, b)
    }

    function p(a) {
        var b = "",
        d = "",
        c;
        for (c = 0; 3 >= c; c++) d = a >>> 8 * c & 255,
        d = "0" + d.toString(16),
        b += d.substr(d.length - 2, 2);
        return b
    }
    var f = [],
    q,
    r,
    s,
    t,
    a,
    b,
    c,
    d;
    e = function(a) {
        a = a.replace(/\r\n/g, "\n");
        for (var b = "",
        d = 0; d < a.length; d++) {
            var c = a.charCodeAt(d);
            128 > c ? b += String.fromCharCode(c) : (127 < c && 2048 > c ? b += String.fromCharCode(c >> 6 | 192) : (b += String.fromCharCode(c >> 12 | 224), b += String.fromCharCode(c >> 6 & 63 | 128)), b += String.fromCharCode(c & 63 | 128))
        }
        return b
    } (e);
    f = function(b) {
        var a, c = b.length;
        a = c + 8;
        for (var d = 16 * ((a - a % 64) / 64 + 1), e = Array(d - 1), f = 0, g = 0; g < c;) a = (g - g % 4) / 4,
        f = g % 4 * 8,
        e[a] |= b.charCodeAt(g) << f,
        g++;
        a = (g - g % 4) / 4;
        e[a] |= 128 << g % 4 * 8;
        e[d - 2] = c << 3;
        e[d - 1] = c >>> 29;
        return e
    } (e);
    a = 1732584193;
    b = 4023233417;
    c = 2562383102;
    d = 271733878;
    for (e = 0; e < f.length; e += 16) q = a,
    r = b,
    s = c,
    t = d,
    a = k(a, b, c, d, f[e + 0], 7, 3614090360),
    d = k(d, a, b, c, f[e + 1], 12, 3905402710),
    c = k(c, d, a, b, f[e + 2], 17, 606105819),
    b = k(b, c, d, a, f[e + 3], 22, 3250441966),
    a = k(a, b, c, d, f[e + 4], 7, 4118548399),
    d = k(d, a, b, c, f[e + 5], 12, 1200080426),
    c = k(c, d, a, b, f[e + 6], 17, 2821735955),
    b = k(b, c, d, a, f[e + 7], 22, 4249261313),
    a = k(a, b, c, d, f[e + 8], 7, 1770035416),
    d = k(d, a, b, c, f[e + 9], 12, 2336552879),
    c = k(c, d, a, b, f[e + 10], 17, 4294925233),
    b = k(b, c, d, a, f[e + 11], 22, 2304563134),
    a = k(a, b, c, d, f[e + 12], 7, 1804603682),
    d = k(d, a, b, c, f[e + 13], 12, 4254626195),
    c = k(c, d, a, b, f[e + 14], 17, 2792965006),
    b = k(b, c, d, a, f[e + 15], 22, 1236535329),
    a = l(a, b, c, d, f[e + 1], 5, 4129170786),
    d = l(d, a, b, c, f[e + 6], 9, 3225465664),
    c = l(c, d, a, b, f[e + 11], 14, 643717713),
    b = l(b, c, d, a, f[e + 0], 20, 3921069994),
    a = l(a, b, c, d, f[e + 5], 5, 3593408605),
    d = l(d, a, b, c, f[e + 10], 9, 38016083),
    c = l(c, d, a, b, f[e + 15], 14, 3634488961),
    b = l(b, c, d, a, f[e + 4], 20, 3889429448),
    a = l(a, b, c, d, f[e + 9], 5, 568446438),
    d = l(d, a, b, c, f[e + 14], 9, 3275163606),
    c = l(c, d, a, b, f[e + 3], 14, 4107603335),
    b = l(b, c, d, a, f[e + 8], 20, 1163531501),
    a = l(a, b, c, d, f[e + 13], 5, 2850285829),
    d = l(d, a, b, c, f[e + 2], 9, 4243563512),
    c = l(c, d, a, b, f[e + 7], 14, 1735328473),
    b = l(b, c, d, a, f[e + 12], 20, 2368359562),
    a = m(a, b, c, d, f[e + 5], 4, 4294588738),
    d = m(d, a, b, c, f[e + 8], 11, 2272392833),
    c = m(c, d, a, b, f[e + 11], 16, 1839030562),
    b = m(b, c, d, a, f[e + 14], 23, 4259657740),
    a = m(a, b, c, d, f[e + 1], 4, 2763975236),
    d = m(d, a, b, c, f[e + 4], 11, 1272893353),
    c = m(c, d, a, b, f[e + 7], 16, 4139469664),
    b = m(b, c, d, a, f[e + 10], 23, 3200236656),
    a = m(a, b, c, d, f[e + 13], 4, 681279174),
    d = m(d, a, b, c, f[e + 0], 11, 3936430074),
    c = m(c, d, a, b, f[e + 3], 16, 3572445317),
    b = m(b, c, d, a, f[e + 6], 23, 76029189),
    a = m(a, b, c, d, f[e + 9], 4, 3654602809),
    d = m(d, a, b, c, f[e + 12], 11, 3873151461),
    c = m(c, d, a, b, f[e + 15], 16, 530742520),
    b = m(b, c, d, a, f[e + 2], 23, 3299628645),
    a = n(a, b, c, d, f[e + 0], 6, 4096336452),
    d = n(d, a, b, c, f[e + 7], 10, 1126891415),
    c = n(c, d, a, b, f[e + 14], 15, 2878612391),
    b = n(b, c, d, a, f[e + 5], 21, 4237533241),
    a = n(a, b, c, d, f[e + 12], 6, 1700485571),
    d = n(d, a, b, c, f[e + 3], 10, 2399980690),
    c = n(c, d, a, b, f[e + 10], 15, 4293915773),
    b = n(b, c, d, a, f[e + 1], 21, 2240044497),
    a = n(a, b, c, d, f[e + 8], 6, 1873313359),
    d = n(d, a, b, c, f[e + 15], 10, 4264355552),
    c = n(c, d, a, b, f[e + 6], 15, 2734768916),
    b = n(b, c, d, a, f[e + 13], 21, 1309151649),
    a = n(a, b, c, d, f[e + 4], 6, 4149444226),
    d = n(d, a, b, c, f[e + 11], 10, 3174756917),
    c = n(c, d, a, b, f[e + 2], 15, 718787259),
    b = n(b, c, d, a, f[e + 9], 21, 3951481745),
    a = h(a, q),
    b = h(b, r),
    c = h(c, s),
    d = h(d, t);
    return (p(a) + p(b) + p(c) + p(d)).toLowerCase()
};

var systemName = 'WAYWebSiteSystem',
	appName = 'WAYWebSiteApp',
	rpName = 'WayWebSiteSystem',
	devUrl = 'https://localhost:8443/WayFido2Server/wayfido2rest/';
	proUrl = 'https://fido2.whoareyou.live/wayfido2rest/',
	fido2Url = '';
var userId;
//登录类型:1密码登录 2免密登录 默认1
var loginTyle = 1;
$(function() {
	
	var curPageUrl = window.document.location.href;
	if(curPageUrl.startsWith("https://www.whoareyou.live")) {
		fido2Url = proUrl;
	} else {
		fido2Url = devUrl;
	}
	
    var baseUrl = $("base").attr("href");
    $("#userName").focus();
    /**
     * 登录按钮单击事件
     */
    function login() {
    	var userName = $("#userName").val();
    	if(userName==null || userName ==""){
    		showInfo("error","帐户不能为空");
    		return false;
    	}
        var url = $("#login_form").attr("action");  //${url}/api/admin/login
        var key = new Date().getTime();
        if(loginTyle == "1") { // 密码登录
        	var password = $("#password").val();
        	if(password==null || password ==""){
        		showInfo("error","请输入密码");
        		return false;
        	}
        	$.ajax({
            	type : "POST",
            	url : url,
            	data : {
            		key : key,
            		userName : userName,
            		password : MD5(key + ":" + MD5(password)),
            		loginTyle : loginTyle
            	},
            	success : function (data) {
            		if (!data.error) { // 登录成功，跳转至主页
            			var redirectTo = "";
                        if ($("#redirectFrom").val().length != 0) {
                            redirectTo = $("#redirectFrom").val();
                        } else {
                            var baseUrl = $("base").attr("href");
                            redirectTo = baseUrl + "admin/index";
                        }
                        location.href = redirectTo;
            		} else {
            			showInfo("error", data.message);
            		}
            	}
            });
        } else { // 免密登录
            /** 获取token */
            $.ajax({
                type : "GET",
                //url : "https://u2f.whoareyou.live/wayfidoapi/v1/app/token",
                url : fido2Url + 'token',
                data : {
                    rpName : rpName
                },
                success : function(date) {
                    if (date.responseCode == "RP001") {
                        $.cookie("token", date.object);
                        //userId = data.obj;
                        $.ajax({ /* 认证1 */
                            headers : {
                                Authorization : "Bearer " + $.cookie("token")
                            },
                            type : "GET",
                            url : fido2Url + 'v1/wayStartAuthentication',
                            dataType : 'json',
                            data : {
                                userName: userName,
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
                                                url: fido2Url + 'v1/wayFinishAuthentication',
                                                type: 'PUT',
                                                dataType: 'json',
                                                data: {
                                                    requestId: mystartAuthenticationSuccessResult.object.requestId,
                                                    credential: response
                                                },
                                                success: function (myFinishAuthenticationSuccessResult) {
                                                    if (myFinishAuthenticationSuccessResult.responseCode == "FIDOVALIDATE003") {
                                                        $.ajax({// 账号存入后台adminToken, 跳转至主页
                                                            type : "POST",
                                                            //url : "https://www.whoareyou.live/api/admin/login",//"api/admin/login",
                                                            url : 'api/admin/login',
                                                            data : {
                                                                userName : $("#userName").val(),
                                                                loginTyle : loginTyle
                                                            },
                                                            success : function (data) {
                                                                if (!data.error) {
                                                                    var redirectTo = "";
                                                                    if ($("#redirectFrom").val().length != 0) {
                                                                        redirectTo = $("#redirectFrom").val();
                                                                    } else {
                                                                        var baseUrl = $("base").attr("href");
                                                                        redirectTo = baseUrl + "admin/index";
                                                                    }
                                                                    location.href = redirectTo;
                                                                } else {
                                                                    showInfo('error', data.message);
                                                                }
                                                            }
                                                        });
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
                        showInfo("error", "系统权限异常，无法使用安全密钥登录");
                    }
                }
            });
        	/*======================================*/

        	/*$.ajax({
        		type : "POST",
        		url : "api/admin/getUserIdByUserName",
        		data : {
        			userName : userName
        		},
        		success : function(data) {
        			if (!data.error) {
        				*//** 获取token *//*
        				$.ajax({
        					type : "GET",
        					//url : "https://u2f.whoareyou.live/wayfidoapi/v1/app/token",
							url : url + '/token',
        					data : {
        						rpName : rpName
        					},
        					success : function(date) {
        						if (date.responseCode == "RP001") {
									$.cookie("token", date.object.token);
			        				userId = data.obj;
			        				$.ajax({  认证1 
			        					headers : {
			        						Authorization : "Bearer " + $.cookie("token")
			        					},
			        					type : "POST",
			        					//url : "https://u2f.whoareyou.live/wayfidoapi/v1/device2/startSignature",
										//url : 'https://fido2.whoareyou.live/wayfidoapi/v1/wayStartAuthentication',
										url : url + '/v1/wayStartAuthentication',
										dataType : 'json',
			        					data : {
                                            userName: $("input[name=username]").val(),
                                            rpName: rpName
			        					},
			        					success : function(date) {
			        						if (date.responseCode == "FIDOU2FVALIDATE101") {
			        							$('#show_div').modal();
			        							$("#show_error_massge").html("");
			        							$("#show_error_div").hide();
			        							verification(eval("(" + date.object + ")"));
			        						} else {
			        							showInfo('error', date.responseMessage);
			        						}
			        					}
			        				});
								} else {
									showInfo("error", "系统权限异常，无法使用安全密钥登录");
								}
        					}
        				});
					} else {
						showInfo("error", data.message);
					}
        		}
        	});*/
        }
    }
    $("#login_btn").click(function(e) {
        login();
    });
    $("body").keypress(function(e) {
        if (e.which == 13) {
            login();
        }
    });
    
    //免密登录按钮点击效果
	  $("#login_update_btn").click(function(){
		  $("input[name='password']").toggle(1000);
		  loginTyle = (loginTyle==1?2:1);
	  });
});

function myExecuteAuthenticateRequest(request) {
    return webauthn.getAssertion(request.object.publicKeyCredentialRequestOptions);
}

/**
 * 检测U2F设备
 * 
 * @param request
 */
/*function verification(request) {
    if (request.signRequests.length > 0) {
        u2f.sign(request.appId, request.challenge, request.signRequests, function(data) {
            if (data.errorCode) {
                switch (data.errorCode) {
                case 4:
                	$("#show_error_div").show();
                	$("#show_error_massge").html("登录失败，此帐号尚未注册此设备。");
                    break;
                 case 2: bad APPID 
                default:
                	$("#show_error_div").show();
                	$("#show_error_massge").html("登录失败，请重试。");
                	//setTimeout(verification(request), 1000);
                }
                return;
            } else {
            	if($('#show_div').css('display')=='none'){
            		return false;
            	}
            	//  /WayWebSite/api/admin/U2Flogin
            	// 此账号注册过此U2F设备，通过U2F登录，U2F验证：
            	$.ajax({
            		headers : {
            			Authorization : "Bearer " + $.cookie("token")
            		},
            		type : "POST",
            		url : "https://u2f.whoareyou.live/wayfidoapi/v1/device2/finishSignature",
            		data : {
            			systemName : systemName,
            			userUUId : userId,
            			tokenResponse : JSON.stringify(data)
            		},
            		success : function (date) {
            			if (date.responseCode == "FIDOU2FVALIDATE202") {
							// u2f设备认证通过
            				$.ajax({// 账号存入后台adminToken, 跳转至主页
            					type : "POST",
            					url : "https://www.whoareyou.live/api/admin/login",//"api/admin/login",
            					data : {
            						userName : $("#userName").val(),
            						loginTyle : loginTyle
            					},
            					success : function (data) {
            						if (!data.error) {
            							var redirectTo = "";
            	                        if ($("#redirectFrom").val().length != 0) {
            	                            redirectTo = $("#redirectFrom").val();
            	                        } else {
            	                            var baseUrl = $("base").attr("href");
            	                            redirectTo = baseUrl + "admin/index";
            	                        }
            	                        location.href = redirectTo;
            						} else {
            							showInfo('error', data.message);
            						}
            					}
            				});
						} else {
							showInfo('error', "安全密钥登录失败");
						}
            		}
            	});
            }
        });
    }
}*/

/**
 * 弹出信息
 * @param message
 */
function showInfo(type,message){
    new PNotify({
        title: message,
        type: type,
        delay: 5000,
        hide: true,
        styling: 'bootstrap3'
    });
}