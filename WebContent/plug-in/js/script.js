$(function(){
		/*手风琴动画菜单插件 Kwicks*/
		//kwicks begin
		$('#menu').kwicks({
		   min : 60,//max|min:(必须)设定展开和折叠时的宽度、高度。不能同时设定最大值和最小值。
		   spacing : 4,//每个菜单之间的间隔
		   sticky : true,
		   defaultKwick:0,
		   event : 'click'
		});
		$('a').click(function(){
			page=$(this).attr('href');
			if (page.substr(page.indexOf('#'),6)=='#page_') {
				$('.kwicks '+page).click();
				return false;
			}
		});
		// initiate tool tip
		$('.normaltip').aToolTip();
});
