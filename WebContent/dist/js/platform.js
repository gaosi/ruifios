//计算文档的可见高度
function windowHeight() {
	var de = document.documentElement;
	return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
}

$(function	()	{
	function scaleHeight()
	{
		//如果非pc,不会自动计算高度 $(top)
		if($("#top-nav").width() <= 992)
		{
			return;
		}
		
		//屏幕可见高度
		var height = windowHeight();
		//不需要计算的高度(一般是固定高度)
		var ignore_height = $("#top-nav").height();
		$(".ig-row-md").each(function(){
			ignore_height += $(this).height();
		});
		//需要自动调整的可用高度
		//height = height - ignore_height - 25;
		//设置可变化容器的高度
		$("#main-container").css({'min-height': height + "px"});
		//动态设置row的高度
		$("[class*=row-md-]").each(function(i, d){
			var arr = this.classList;
			if(arr.contains("row-md-1"))
			{
				$(this).height(height * 10 / 100 + "px");
			}
			else if(arr.contains("row-md-2"))
			{
				$(this).height(height * 20 / 100 + "px");
			}
			else if(arr.contains("row-md-3"))
			{
				$(this).height(height * 30 / 100 + "px");
			}
			else if(arr.contains("row-md-4"))
			{
				$(this).height(height * 40 / 100 + "px");
			}
			else if(arr.contains("row-md-5"))
			{
				$(this).height(height * 50 / 100 + "px");
			}
			else if(arr.contains("row-md-6"))
			{
				$(this).height(height * 60 / 100 + "px");
			}
			else if(arr.contains("row-md-7"))
			{
				$(this).height(height * 70 / 100 + "px");
			}
			else if(arr.contains("row-md-8"))
			{
				$(this).height(height * 80 / 100 + "px");
			}
			else if(arr.contains("row-md-9"))
			{
				$(this).height(height * 90 / 100 + "px");
			}
			else if(arr.contains("row-md-10"))
			{
				$(this).height(height + "px");
			}
		});
		//设置row中echart图的合适高度
		$(".row .mycharts").each(function(i,d){
			$(this).height(($(this).parents(".row").height()-10) + "px");
		});
	}
	//页面非script脚本初始化后,自动计算元素高度
	scaleHeight();
	window.onload = window.onresize = function(){
		scaleHeight();
	};
});