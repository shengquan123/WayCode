var Charts = function() {
	return {
		/** 
		 * @returns
		 */
		initPieCharts : function() {
			$.post(WAY_URL+'query/newAllUserCount',function (data) {
		        $('#container').highcharts('StockChart', {
		            rangeSelector : {
		                selected : 1
		            },
		            title : {
		                text : '注册人数'
		            },
			        rangeSelector: {  
			            buttons: [{// 定义一组buttons,下标从0开始
			            type: 'week',  
			            count: 1,  
			            text: '1周'  
			        },{  
			            type: 'month',  
			            count: 1,  
			            text: '1月'  
			        }, {  
			            type: 'month',  
			            count: 3,  
			            text: '3月'  
			        }, {  
			            type: 'month',  
			            count: 6,  
			            text: '6月'  
			        },{  
			            type: 'year',  
			            count: 1,  
			            text: '1年'  
			        }, {  
			            type: 'all',  
			            text: '全部'  
			        }],  
			            selected: 1// 表示以上定义button的index,从0开始
			        },  		            
		            series : [{
		                name : '注册人数',
		                data : eval(data.responsebody),
		                tooltip: {
		                    valueDecimals: 0
		                }
		            }]
		        });
		    });
		}
		}
}();