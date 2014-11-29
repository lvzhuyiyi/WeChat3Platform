$(function(){
	
	$.fn.rTabs = function(options){
		
		//Ĭ��ֵ
		var defaultVal = {
			btnClass:'.public-box',	/*��ť�ĸ���Class*/
			conClass:'.content-right',	/*���ݵĸ���Class*/
			bind:'hover',	/*�¼����� click,hover*/
			animation:'0',	/*�������� left,up,fadein,0 Ϊ�޶���*/
			speed:300, 	/*�����˶��ٶ�*/
			delay:100,	/*Tab�ӳ��ٶ�*/
			auto:false,	/*�Ƿ����Զ����� true,false*/
			
		};
		//ȫ�ֱ���
		var obj = $.extend(defaultVal, options),
			evt = obj.bind,
			btn = $(this).find(obj.btnClass),
			con = $(this).find(obj.conClass),
			anim = obj.animation,
			conWidth = con.width(),
			conHeight = con.height(),
			len = con.children("div").length,
			sw = len * conWidth,
			sh = len * conHeight,
			i = 0,
			len,t,timer;
		return this.each(function(){		
			//�ж϶�������
			function judgeAnim(){
				var w = i * conWidth,
					h = i * conHeight;
				btn.children().removeClass('current').eq(i).addClass('current');
				switch(anim){
					case '0':
					con.children("div").hide().eq(i).show();
					break;
					case 'left':
					con.css({position:'absolute',width:sw}).children().css({float:'left',display:'block'}).end().stop().animate({left:-w},obj.speed);
					break;
					case 'up':
					con.css({position:'absolute',height:sh}).children().css({display:'block'}).end().stop().animate({top:-h},obj.speed);
					break;
					case 'fadein':
					con.children().hide().eq(i).fadeIn();
					break;
				}
			}		
			//�ж��¼�����
			if(evt == "hover"){
				btn.children().hover(function(){
					var j = $(this).index();
					function s(){
						i = j;
						judgeAnim();
					}
					timer=setTimeout(s,obj.delay);
				}, function(){
					clearTimeout(timer);
				})
			}else{
				btn.children().bind(evt,function(){
					i = $(this).index();
					judgeAnim();
				})
			}			
		})
		
	} 
});