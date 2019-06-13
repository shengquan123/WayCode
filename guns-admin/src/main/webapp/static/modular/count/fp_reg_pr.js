//指纹数据集合
var avgFingerprintList = null;

/** 
 * 获取实时用户最新数据 
 */
function get_fp_reg_pr() {
	$.post(WAY_URL + "statistical/userFinger", function(date) {
		if (date.responseStatus == "N") {
			var responsebody = eval("(" + date.responsebody + ")");
			$("#userAmout").html(responsebody.userAmout);
			$("#fingerprintAmout").html(responsebody.fingerprintAmout);
			$("#avgFingerprintAmout").html(responsebody.avgFingerprintAmout);
			//列表数据
			$(responsebody.avgFingerprintList).each(function(){
				$("#fp_reg_num_"+this.type).html(this.amount);
				$("#fp_reg_rg_"+this.type).html(this.percent+"%");
			});
			avgFingerprintList=responsebody.avgFingerprintList;
		} else {
			alert(date.responseMessage);
			return false;
		}

	});
}


/**
 * 鼠标移动到地址产生的事件
 * 
 * @param target
 *            产品id
 */
function showTooltip(target) {
	var o = document.createElement("div");
	$("#tooltip" + target)[0].innerHTML = "";
	$("#tooltip" + target)[0].appendChild(o);
	var es = "";
	
	//遍历集合显示数据
	if(avgFingerprintList== null){
		alert("暂无数据");
	}
	$(avgFingerprintList).each(function(){
		if(this.type==target){
			es += "<span>数量：" + this.amount +"</span><br/>" +
					"<span>占比：" + this.percent +"%</span>";
			return;
		}
	});
	o.innerHTML = es;
	document.getElementById("tooltip" + target).style.display = 'block';
}

/**
 * 鼠标移开地址产生的事件
 * 
 * @param target
 */
function hideTooltip(target) {
	document.getElementById("tooltip" + target).style.display = 'none';

}







