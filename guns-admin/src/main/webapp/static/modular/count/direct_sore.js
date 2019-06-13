/** 
 * 直达排序
 */
function get_direct_sore() {
	$.post(WAY_URL + "statistical/quickLaunch", function(date) {
		if (date.responseStatus == "N") {
			/* url排序 */
			var urlNum=1;
			/* app排序 */
			var appNum=1;
			$(eval("("+date.responsebody+")")).each(function(){
				if(this.type==1){
					$("#url_num_"+urlNum).html(this.url);
					$("#url_rg_"+urlNum).html(this.percent);
					urlNum++;
				}else{
					$("#app_num_"+appNum).html(this.url);
					$("#app_rg_"+appNum).html(this.percent);
					appNum++;
				}
			});
		} else {
			alert(date.responseMessage);
			return false;
		}
	});
}