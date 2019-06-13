/** 
 * 获取wind比例
 */
function get_wind_pr() {
	$.post(WAY_URL + "statistical/windowsVersion", function(date) {
		if (date.responseStatus == "N") {
			var responsebody=eval("("+date.responsebody+")"); 
			$(responsebody).each(function(){
				if(this.type=="WIN8.1"){
					$("#WIN8_1_num").html(this.amount);
					$("#WIN8_1_rg").html(this.percent);
				}
				$("#"+this.type+"_num").html(this.amount);
				$("#"+this.type+"_rg").html(this.percent);
			});
		    $('#container').highcharts({
		        title: {
		            text: ''
		        },
		        tooltip: {
		            headerFormat: '{series.name}<br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        series: [{
		            type: 'pie',
		            name: '操作系统占比',
		            data: [
			                ['WIN7', responsebody[0].amount],
			                ['WIN8',  responsebody[1].amount],
			                ['WIN8.1',responsebody[2].amount],
			                ['WIN10',  responsebody[3].amount]
		            ]
		        }]
		    });
		} else {
			alert(date.responseMessage);
			return false;
		}
		
	});
}