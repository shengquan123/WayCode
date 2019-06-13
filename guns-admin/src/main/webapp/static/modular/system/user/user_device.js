var systemName = 'WAYAdminServerSystem',
    appName = 'WAYAdminServerApp',
    url = "";

var rpName = 'WAYAdminServerSystem';

/**
 * 页面加载完成
 */
$(function () {
    $.ajax({
        dataType: "json",
        contentType: "application/json",
        type: "get",
        url: "../fidorest/app/enviroment",
        success: function (date) {
            url = date.url;
        }
    });
    getDevice();
});


/**
 * 获取设备列表
 */
function getDevice() {
    $("#show_div_img").css("height", "150px");
    $.get("getFido2Devices", function (data) {
        // 清空层
        $("#deviceList")[0].innerHTML = "";
        var o = '';
        if (data.responseCode == "DEVICE001") {
            // 遍历obj数据集合
            $(data.object).each(function () {
                o = o + '<div style="padding:1%;float:left;width:100%;border-bottom: 1px solid #E5E5E5;">'
                    + ' <div style="font-size:20px;"><b>'
                    + this.deviceName
                    + '</b></div>'
                    + ' <div><small>添加时间：'
                    + new Date(this.registrationTime * 1).dateFormat("yyyy-MM-dd hh:mm:ss")
                    + '</small><img style="float:right;'
                    + 'cursor:pointer;" src="https://www.whoareyou.live/admin/markdown/images/pen.png" onclick="updateDeviceModal('
                    + this.id + ',\'' + this.deviceName + '\')" />'
                    + '</div>'
                    + ' <div><small>最后认证时间：'
                    + new Date(this.lastAuthTime * 1).dateFormat("yyyy-MM-dd hh:mm:ss")
                    + '</small></div>'
                    + ' </div><br/>';
            });
            $("#deviceList")[0].innerHTML = o;
        }
        // 获取token,并存入cookie
        $.ajax({
            type: "GET",
            //url : url + "app/token",
            //url : "https://localhost:8443/WayU2FServer/wayfidoapi/v1/app/token",
            //url : "https://u2f.whoareyou.live/wayfidoapi/v1/app/token",
            url: url + '/token',
            data: {
                rpName: rpName
            },
            dataType: "json",
            success: function (date) {
                if (date.responseCode != 'RP001') {
                    showInfo('error', "安全设备操作权限获取失败!");
                    return;
                }
                $.cookie("token", date.object);
            }
        });
    });
}

/**
 * 显示提示插入设备
 */
function showInserted() {
    $("#reg_success").css("background-color", "");
    $('#show_div').modal();
    $("#show_div_next_device").show();
    $("#show_div_delect_device").hide();
    $("#show_error_div").hide();
    $("#show_error_massge").html("");
    // 修改图片，修改文字，隐藏提交按钮，隐藏输入框
    $("#show_div_img").attr('src', "https://www.whoareyou.live/admin/markdown/images/security_key_laptop.gif");
    $("#show_div_title").html("注册您的安全密钥");
    $("#show_div_content").html("将您的安全密钥插入计算机的usb端口，或使用usb线将其与 <br />计算机相连，确认连接后点击下一步");
    $("#show_div_button").hide();
    $("#show_div_input")[0].style.display = "none";
}

/**
 * 注册设备
 */
function regDevice() {
    $("#show_div_next_device").hide();
    $("#show_error_massge").html("");
    $("#show_div_title").html("注册您的安全密钥");
    $("#show_div_content").html("按下安全密钥上的按钮或金色圆片（如 有）。");
    var request = null;
    // 获取注册前的数据
    $.ajax({
        headers: {
            Authorization: "Bearer " + $.cookie("token"),
        },
        //url : url + 'device2/startRegistWayFido',
        //url : "https://localhost:8443/WayU2FServer/wayfidoapi/v1/device2/startRegistWayFido",
        //url : "https://u2f.whoareyou.live/wayfidoapi/v1/device2/startRegistWayFido",
        url: url + 'v1/wayStartRegister',
        type: "GET",
        dataType: 'JSON',
        data: {
            /*systemName : systemName,
            userUUId : $("#useruuid").html()*/
            userName: $("#account").val(),
            rpName: rpName
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
                                userId: $("#useruuid").html()
                            },
                            success: function (finishRegistrationSuccessResult) {
                                if (finishRegistrationSuccessResult.responseCode == "FIDOREGIST002") {
                                    $("#updateDeviceId").val("");
                                    $("#reg_success").css("background-color", "#4285F4");
                                    $("#show_div_img").css("height", "");
                                    $("#show_div_img").css("margin-left", "");
                                    $("#show_div_img").attr('src', "https://www.whoareyou.live/admin/markdown/images/sussces.png");
                                    $("#show_div_title").html("安全密钥已完成注册");
                                    $("#show_div_content").html("您的安全密钥已完成注册，您可以为此安全密钥输入一个名称");
                                    $("#show_div_button").show();
                                    $("#show_div_input")[0].style.display = "block";
                                    // 注册成功，重新加载设备列表
                                    getDevice();
                                } else {
                                    $("#show_div_img").attr('src', "https://www.whoareyou.live/admin/markdown/images/info.png");
                                    $("#show_div_title").html("出了点问题！");
                                    $("#show_div_content").html(finishRegistrationSuccessResult.message);
                                }
                            },
                        });
                    })
                    .catch((err) => {
                        $("#show_div").modal('hide');
                        console.log('error', JSON.stringify(err.message));
                    })
            }
        }
    });
}

function myExecuteRegisterRequest(request) {
    return webauthn.createCredential(request.object.publicKeyCredentialCreationOptions);
}

/**
 * 保存设备名称
 */
function saveName() {
    $.ajax({
        headers: {
            Authorization: "Bearer " + $.cookie("token"),
        },
        type: "PUT",
        dataType: 'json',
        url: url + 'v1/device',
        //url : url + 'device2/deviceName',
        //url : 'https://localhost:8443/WayU2FServer/wayfidoapi/v1/device2/deviceName',
        //url : "https://u2f.whoareyou.live/wayfidoapi/v1/device2/deviceName",
        data: { // 修改设备名称接口需要传入参数：
            rpName: rpName,
            userId: $("#useruuid").html(),
            deviceId: $("#updateDeviceId").val(), // 设备id(新添加设备获取到的前端id为空)
            deviceName: $("#show_div_input").val()
        },
        success: function (date) {
            if (date.responseCode == "FIDOUPDATE001") {
                $('#show_div').modal('hide');
                getDevice();
            } else {
                // 修改图片，修改文字，显示提交按钮，显示
                $("#show_div_img").attr('src', "https://www.whoareyou.live/admin/markdown/images/info.png");
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
function updateDeviceModal(id, deviceName) {
    $("#show_div_next_device").css("display", "none");
    $('#reg_success').css('background-color', '#4285F4');
    $('#show_div').modal();
    $("#show_div_img").attr('src', "https://www.whoareyou.live/admin/markdown/images/double_device.png");
    $("#updateDeviceId").val(id);
    $("#show_div_input").val(deviceName);
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
            headers: {
                Authorization: "Bearer " + $.cookie("token"),
            },
            type: "DELETE",
            dataType: 'json',
            url: url + 'v1/device/' + $("#updateDeviceId").val(),
            //url : url + 'device2/' + $("#updateDeviceId").val(),
            //url : 'https://localhost:8443/WayU2FServer/wayfidoapi/v1/device2/' + $("#updateDeviceId").val(),
            //url : 'https://u2f.whoareyou.live/wayfidoapi/v1/device2/' + $("#updateDeviceId").val(),
            success: function (date) {
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
 * 弹出信息
 *
 * @param message
 */
function showInfo(type, message) {
    new PNotify({
        title: message,
        type: type,
        delay: 3000,
        hide: true,
        styling: 'bootstrap3'
    });
}