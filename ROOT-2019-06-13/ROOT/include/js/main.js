jQuery(function($) {
	$(document)
			.ready(
					function() {
						var navHeight = $('.public-header').height();
						$('.nav-banner').css('margin-top', navHeight);
						$('.rm-margin-top').css('margin-top', 0); // 删除产品2页137margin-top属性设置
						$('.navbar-right .contact-us').on('click', function() {
							$('#navbar').removeClass('in');
							$(this).css('color', 'rgb(157,157,157)');
							$('html, body').stop().animate({
								scrollTop : $(document).height()
							}, 700);
						});

						$('.navbar-right .addres-us').on('click', function() {
							$('#navbar').removeClass('in');
							$(this).css('color', 'rgb(157,157,157)');
							$('html, body').stop().animate({
								scrollTop : $(document).height()
							}, 700);
						});

						/** ********** add by shengquan ********************* */
						$("body").find("a").first().css("display", "none"); // 隐藏网站统计标签

						$(".product-float img").next().css("width",
								$(".product-float img").css("width"));

						var SQ_screenHeight = window.screen.height; // 屏幕分辨率的高
						var SQ_screenWidth = window.screen.width; // 屏幕分辨率的宽
						// 不同分辨率使用不同背景图片: 排除手机端
						if (!/Android|webOS|iPhone|iPod|BlackBerry/i
								.test(navigator.userAgent)) {
							if (SQ_screenHeight / SQ_screenWidth == 768 / 1366) {
								$(".intro-header")
										.css("background-image",
												"url(include/images/index/new/1_Home_pic_01_1_1366_768.png)");
								$("#div_aaa1").css("margin-top", "2%");
								$(".title_1366_1").css("font-size", "24px"); // 1366*768一级标题
								$(".title_1366_2").css("font-size", "20px"); // 1366*768二级标题
								$(".scr_1366_768_text16").css("font-size",
										"16px"); // 1366*768正文
								$("#index_001")
										.css("width", "383px")
										.css("height", "257px")
										.css("background-image",
												"url('include/images/huAYouImages/PC/index/1_Home_pic_01_1366.png')");
							}
							if (SQ_screenHeight > 900) {
								$("#main_function_p")
										.css("margin-bottom", "6%");
								$(".line-height").css("line-height", "1.8");
							}
							SQ_screenHeight = window.screen.height - 199; // 根据屏幕分辨率设置高度
							// 背景图片div根据屏幕分辨率不同适配相应高度
							$(
									".intro-header, .intro-header2, .intro-header3, .page2, .nav-banner-logo")
									.css("height", SQ_screenHeight);
							$(".product1").css("height", SQ_screenHeight - 50);
							$(".about_logo").css("height",
									SQ_screenHeight * 0.8);
							/* 垂直居中:需设置div高度值 */
							$(".div_vertical").css("height", SQ_screenHeight);
							$(".pro-height").css("height",
									SQ_screenHeight * 0.9);
							$(".case3-div2").css("height",
									SQ_screenHeight / 2 - 100);

							$(".mobile-wid-410").remove();

							$(".footer .footer-agent").css("width", "25%");
							
							if (window.screen.height / window.screen.width == 768 / 1366) {
								$(".1366_height").css("height", 650);
							}
						}
						
						

						/* 手机端调试 */
						if (/Android|webOS|iPhone|iPod|BlackBerry/i
								.test(navigator.userAgent)) {

							$(".title_1366_1").css("font-size", "18px"); // 手机端一级标题
							$(".title_1366_2").css("font-size", "16px").css(
									"font-weight", "400"); // 手机端二级标题
							$(".scr_1366_768_text16").css("font-size", "14px"); // 手机端正文

							$("#open-service").css("margin-bottom", "0");
							$(".about-develop").attr("src",
									"images/index/7_About_pic_01_02.png");

							var div_imgs = $(".abc"); // 获取图片父div节点集合
							var imgs = $(".abc .efg"); // 获取图片img节点集合
							for (var i = 0; i < div_imgs.length; i++) {
								var img = new Image();
								img.src = $(imgs[i]).attr("src"); // 原始图片
								$(div_imgs[i]).css("width", 80);
								$(div_imgs[i]).css("height",
										img.height * 80 / img.width);
							}

							var product_img_divs = $(".pro-img-div");
							var product_imgs = $(".pro-img-div img");
							for (var i = 0; i < product_img_divs.length; i++) {
								var img = new Image();
								img.src = $(product_imgs[i]).attr("src");
								$(product_imgs[i]).css("width",
										$(product_img_divs[i]).width);
								$(product_imgs[i]).css(
										"height",
										img.height
												* $(product_img_divs[i]).width
												/ img.width);
							}

							$(".mobile-rm-br").remove();

							if (screen.width >= 410) {
								$(".mobile-wid-410").css("display", "none");
								$(".mobile-foot2").css("margin-left", "16%");
							}
							if (screen.width <= 404) {
								$(".mobile-rm-br404").remove();
							}
							if (screen.width > 404) {
								$(".mobile-rm-da404").remove();
							}
							if (screen.width < 418) {
								$(".mobile-rm-br418").css("display", "none");
							}
						}
						/** ********** add by shengquan ********************* */

						// 浏览器窗口大小改变事件
						$(window).resize(
								function() {
									var screenwidth = $(window).width();
									if (screenwidth < 768) {
										$('.none-dropdown').css('display',
												'none');
										$('.dropdown').css('display', 'block');
										$('.index-card').removeClass('wow');
										$('.footer .line').css('display',
												'none');
										$('.case-line').css('display', 'none')

									} else {
										$('.none-dropdown').css('display',
												'block');
										$('.dropdown').css('display', 'none');

										$(".intro-header").css("height",
												SQ_screenHeight);
										// $('.public-header').pin()
									}
								});
						var screenwidth = $(window).width();
						if (screenwidth < 768) {
							$('.none-dropdown').css('display', 'none');
							$('.dropdown').css('display', 'block');
							$('.index-card').removeClass('wow');
							$('.footer .line').css('display', 'none');
							$('.case-line').css('display', 'none');
							$('.sm-title').removeClass('wow')
						} else {
							// $('.public-header').pin()
						}
						wow = new WOW({
							animateClass : 'animated',
							offset : 100,
							callback : function(box) {
								console.log("WOW: animating <"
										+ box.tagName.toLowerCase() + ">")
							}
						});
						wow.init();
						$('.play-title a').on('click', function() {
							$('.j_pop_video').removeClass('hide');
							$('body').addClass('noscroll');
							videoInit();
						});

						$('.glyphicon-remove-circle').on('click', function() {
							$('body').removeClass('noscroll');
							$('video').get(0).pause();
							$('.j_pop_video').addClass('hide');
						});
						function videoInit(resize) {
							var wraper = $('.j_pop_video_wraper');
							var v = document.createElement('video');
							var can_play = false;
							var video_tag = wraper.find('.j_tag_video');
							var iframe_tag = wraper.find('.j_iframe_video');
							var min_height = $(window).height();
							var min_width = min_height * 16 / 9;
							wraper
									.width(Math.min($(window).width(),
											min_width));
							wraper.height(Math.min(wraper.width() * 9 / 16,
									min_height));
							if (v.canPlayType
									&& v.canPlayType('video/mp4').replace(/no/,
											'')) { // 检测是否支持播放
								can_play = true;
							}
							if (resize) {
								return;
							}
							if (can_play) {
								if (!video_tag.attr('src')) {
									video_tag[0].src = video_tag
											.attr('data-src');
								}
								video_tag[0].play();
								video_tag.removeClass('hide');
								iframe_tag.addClass('hide');
							} else {
								if (!iframe_tag.attr('src')) {
									iframe_tag.attr('src', iframe_tag
											.attr('data-src'));
									console.info('iframe_tag', iframe_tag
											.attr('src'));
								}
								video_tag.addClass('hide');
								iframe_tag.removeClass('hide');
							}
						}

						function GetRequest() {
							var url = location.search;
							if (url.indexOf("?") != -1) {
								var str = url.substr(1);
								strs = str.split("=");
								if (strs[1]) {
									$('.public-header').css('display', 'none');
									$('.footer').css('display', 'none');
									var navHeight = $('.public-header')
											.height();
									console.log(navHeight);
									$('.nav-banner').css('margin-top', 0)
								} else {

								}
							}
						}
						GetRequest();

						$(window).on('resize', true, videoInit);
						var len = (window.location.href.split('/')).length;
						var str = (window.location.href.split('/'))[len - 1];
						$(".navbar-right li a[href='/" + str + "']").css(
								'color', '#ffffff');
						if (str == '5wei' || str == 'U' || str == 'youta'
								|| str == 'guofu' || str == 'douyue') {
							$(
									".navbar-right li a[href='../case.htm'/*tpa=http://www.5wei.com/case*/]")
									.css('color', '#ffffff');
							$(".nav-second li a[href='/case/" + str + "']")
									.css('color', '#000')
						}
						if (str == 'marketing' || str == 'flow'
								|| str == 'report' || str == 'manager'
								|| str == 'print' || str == 'tv'
								|| str == 'mobile') {
							$(
									".navbar-right li a[href='../feature.htm'/*tpa=http://www.5wei.com/feature*/]")
									.css('color', '#ffffff');
							$(".nav-second li a[href='/feature/" + str + "']")
									.css('color', '#000')
						}
						if (str == "" || str == 'price') {
							// $('.header').css('z-index', '100');
							// $('.header').pin()
						}
					});
});

function goToIndex() {
	if("localhost:8080"==window.location.host || "192.168.1.106:8080" == window.location.host){
		window.location.href = "http://" + window.location.host + "/WayWebSite/";
	} else {
		window.location.href="http://"+window.location.host; 
	}
}

/**
 * 跳转新闻页面方法 
 */
function goToNews(){
	
	if("localhost:8080"==window.location.host || "192.168.1.106:8080" == window.location.host){
		window.location.href = "http://" + window.location.host + "/WayWebSite/post";
	} else {
		window.location.href="http://"+window.location.host + "/post"; 
	}
}


