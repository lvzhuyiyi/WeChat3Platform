$(function(){
		/*�ַ��ٶ����˵���� Kwicks*/
		//kwicks begin
		$('#menu').kwicks({
		   min : 60,//max|min:(����)�趨չ�����۵�ʱ�Ŀ�ȡ��߶ȡ�����ͬʱ�趨���ֵ����Сֵ��
		   spacing : 4,//ÿ���˵�֮��ļ��
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
