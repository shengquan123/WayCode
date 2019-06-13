/** 本地RESTAPI访问地址 */
var WAY_URL = "";

if (window.location.href.indexOf("39.104.18.117") >= 0) {
	// 测试环境
	WAY_URL = "http://39.104.18.117:8080/guns-admin/wayapi/v1/accessory/";
} else if (window.location.href.indexOf("whoareyou.live") >= 0) {
	// 生产环境
	WAY_URL = "https://clientadmin.whoareyou.live/wayapi/v1/accessory/";
} else {
	// local环境 Tomcat
	WAY_URL = "http://localhost:8080/guns-admin/wayapi/v1/accessory/";
	// local环境 SpringBoot Main
	//WAY_URL="http://localhost:8080/wayapi/v1/accessory/";
}