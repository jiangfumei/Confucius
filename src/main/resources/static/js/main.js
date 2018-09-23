
$(function(){

	// 点击一级
	$('.menu_list').on('click','.level1>li>a',function(){

		$('.sel_first_level').removeClass('sel_first_level');		
		$(this).addClass('sel_first_level');
		$(this).find('i').addClass('bottom_arrow');

		var $ul = $(this).next('ul');
		var is_same = $ul.is(':hidden');
		
		if(is_same){
			$ul.slideDown("1000");	
		}else{
			$(this).find('i').removeClass('bottom_arrow');
			$ul.slideUp("fast");
			$(this).removeClass('sel_first_level');
		}	
	});

	// 点击二级
	$('.menu_list').on('click','.level2>li>a',function(){	
		$('.sel_second_level').removeClass('sel_second_level');
		$(this).addClass('sel_second_level');
	})

	// 点击用户显示退出登录和设置
	$('.user_operate').click(function(event){
		if($('.user_operate_list').is(':hidden')){
			$('.user_operate_list').slideDown('1000');
		}else{
			$('.user_operate_list').slideUp('fast');
		}
		// 停止
		event.stopPropagation();
	})
	// 隐藏退出登录和设置
	$(document).click(function(){		
		$('.user_operate_list').slideUp('fast');
	});

	// 隐藏左侧列表
	$('.left_operat_icon').click(function(){
		console.log($('.menu_main').width());
		if($('.menu_main').width()<10){
			$('.menu_main').width(220);
		}else{
			$('.menu_main').width(0);
		}
		
	});

	$('.menu_list').on('click','.level2>li>a',function(){
		$('.content').attr('src',$(this).attr('title'));
	});
});

