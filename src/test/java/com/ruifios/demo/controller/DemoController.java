package com.ruifios.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Symbol;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Map;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.AreaStyle;
import com.ruifios.server.AbstractController;

@Controller
@RequestMapping("/demo")
public class DemoController extends AbstractController
{

    @RequestMapping("/viewIndex")
    public ModelAndView viewIndex()
    {
	return new ModelAndView("blank");
    }

    @RequestMapping("/viewLeft")
    public ModelAndView viewLeft()
    {
	return new ModelAndView("demo/left");
    }

    @RequestMapping("/viewForm")
    public ModelAndView viewForm()
    {
	return new ModelAndView("demo/form");
    }

    @RequestMapping("/viewButton")
    public ModelAndView viewButton()
    {
	return new ModelAndView("demo/button");
    }

    @RequestMapping("/viewTab")
    public ModelAndView viewTab()
    {
	return new ModelAndView("demo/tab");
    }

    @RequestMapping("/viewTable")
    public ModelAndView viewTable()
    {
	return new ModelAndView("demo/table");
    }

    @RequestMapping("/viewTimeLine")
    public ModelAndView viewTimeLine()
    {
	return new ModelAndView("demo/timeLine");
    }

    @RequestMapping("/viewUi")
    public ModelAndView viewUi()
    {
	return new ModelAndView("demo/ui");
    }

    @RequestMapping("/viewValidate")
    public ModelAndView viewValidate()
    {
	return new ModelAndView("demo/validate");
    }

    @RequestMapping("/viewWidget")
    public ModelAndView viewWidget()
    {
	return new ModelAndView("demo/widget");
    }

    @RequestMapping("/viewChart")
    public ModelAndView viewChart()
    {
	return new ModelAndView("demo/echarts");
    }

    /**
     * 堆积图
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBarChart")
    public String getBarChart()
    {
	// 地址：http://echarts.baidu.com/doc/example/bar1.html
	GsonOption option = new GsonOption();
	option.title().text("湿度");
	option.tooltip().trigger(Trigger.axis);
	option.legend("蒸发量", "降水量");
	option.toolbox()
		.show(true)
		.feature(new MagicType(Magic.line, Magic.bar).show(true),
			Tool.saveAsImage);
	option.calculable(true);
	// x轴值
	option.xAxis(new CategoryAxis().data("1月", "2月", "3月", "4月", "5月",
		"6月", "7月", "8月", "9月", "10月", "11月", "12月"));
	option.yAxis(new ValueAxis().splitArea(new SplitArea().show(true)));
	// y轴值
	Bar bar = new Bar("蒸发量");
	bar.stack("总量");
	bar.data(2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0,
		6.4, 3.3);
	bar.itemStyle().normal().label(new Label().show(false));
	// y轴值
	Bar bar2 = new Bar("降水量");
	bar2.data(2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8,
		6.0, 2.3);
	bar2.stack("总量");
	bar2.itemStyle().normal().label(new Label().show(false));

	option.series(bar, bar2);

	return option.toString();
    }

    /**
     * 线型图
     */
    @ResponseBody
    @RequestMapping("/getLineChart")
    public String getLineChart(String x)
    {
	// 例子：http://echarts.baidu.com/doc/example/line.html
	GsonOption option = new GsonOption();
	option.title().text("广告");
	option.tooltip().trigger(Trigger.axis);
	option.legend("邮件营销", "联盟广告");
	option.toolbox()
		.show(true)
		.feature(new MagicType(Magic.line, Magic.bar).show(true),
			Tool.saveAsImage);
	option.calculable(true);
	option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二",
		"周三", "周四", "周五", "周六", "周日"));
	option.yAxis(new ValueAxis().splitArea(new SplitArea().show(true)));
	option.series(new Line()
		.smooth(true)
		.name("邮件营销")
		.stack("总量")
		.symbol(Symbol.none)
		.data(120, 132, 301, 134, new LineData(90, Symbol.droplet, 5),
			230, 210));

	// 实现不了js的这个效果
	LineData lineData = new LineData(201, Symbol.star, 15);
	lineData.itemStyle().normal()
		.areaStyle(new AreaStyle().type("default"));
	lineData.itemStyle().normal().label().show(true).textStyle()
		.fontSize(20).fontFamily("微软雅黑").fontWeight("bold");
	option.series(new Line()
		.smooth(true)
		.name("联盟广告")
		.stack("总量")
		.symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png")
		.symbolSize(8)
		.data(120, 82, lineData, new LineData(134, Symbol.none), 190,
			new LineData(230, Symbol.emptypin, 8), 110));
	return option.toString();
    }

    /**
     * 面积图
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAreaChart")
    public String getAreaChart()
    {
	// 例子：http://echarts.baidu.com/doc/example/line.html
	GsonOption option = new GsonOption();
	option.title("某楼盘销售情况");
	option.tooltip().trigger(Trigger.axis);
	option.legend("意向", "预购", "成交");
	option.toolbox()
		.show(true)
		.feature(new MagicType(Magic.line, Magic.bar).show(true),
			Tool.saveAsImage);
	option.calculable(true);
	option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二",
		"周三", "周四", "周五", "周六", "周日"));
	option.yAxis(new ValueAxis().splitArea(new SplitArea().show(true)));

	Line l1 = new Line("成交");
	l1.smooth(true).itemStyle().normal().areaStyle().typeDefault();
	l1.data(10, 12, 21, 54, 260, 830, 710);

	Line l2 = new Line("预购");
	l2.smooth(true).itemStyle().normal().areaStyle().typeDefault();
	l2.data(30, 182, 434, 791, 390, 30, 10);

	Line l3 = new Line("意向");
	l3.smooth(true).itemStyle().normal().areaStyle().typeDefault();
	l3.data(1320, 1132, 601, 234, 120, 90, 20);

	option.series(l1, l2, l3);
	return option.toString();
    }

    /**
     * 饼图
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPieChart")
    public String getPieChart()
    {
	GsonOption option = new GsonOption();
	option.title("某楼盘销售情况");
	option.tooltip().trigger(Trigger.item)
		.formatter("{a} <br/>{b} : {c} ({d}%)");
	option.toolbox()
		.show(true)
		.feature(new MagicType(Magic.line, Magic.bar).show(true),
			Tool.saveAsImage);
	option.legend().data("成交", "预购", "意向");
	Pie pie = new Pie()
		.name("浏览器（数据纯属虚构）")
		.data(new PieData("成交", 3 * 128 + 80),
			new PieData("预购", 2 * 64 + 160),
			new PieData("意向", 1 * 32 + 320)).clickable(true);
	option.series(pie);
	return option.toString();
    }

    @ResponseBody
    @RequestMapping("/getMap")
    public String getMap()
    {
	// //地址：http://echarts.baidu.com/doc/example/map.html
	// GsonOption option = new GsonOption();
	// Map map = new Map("Map");
	// //map.mapLocation(new MapLocation(X.left, Y.top, 500));
	// map.selectedMode(SelectedMode.multiple);
	// map.itemStyle().normal().borderWidth(2)
	// .borderColor("lightgreen").color("orange")
	// .label().show(true);
	// map.itemStyle().emphasis()
	// .borderWidth(2).borderColor("#fff").color("#32cd32")
	// .label().show(true)
	// .textStyle().color("#fff");
	// Data data = new Data();
	// map.data(data);
	//
	// option.series(map);
	//
	// 地址：http://echarts.baidu.com/doc/example/map.html
	GsonOption option = new GsonOption();
	Map map = new Map("Map");

	map.setName("World Population (2010)");
	map.setMapType("world");
	map.itemStyle().emphasis().label().show(true);
	Data data = new Data();
	map.data(data);

	option.series(map);
	return option.toString();
    }

    public static void main(String[] args)
    {
	// 地址：http://echarts.baidu.com/doc/example/map.html
	GsonOption option = new GsonOption();
	Map map = new Map("Map");

	map.setName("World Population (2010)");
	map.setMapType("world");
	map.itemStyle().emphasis().label().show(true);
	Data data = new Data("china");
	data.value(Math.round(Math.random() * 1000));
	data.itemStyle().normal().color("#32cd32").label().show(true)
		.textStyle().color("#fff").fontSize(15);
	data.itemStyle().emphasis().borderColor("yellow").color("#cd5c5c")
		.label().show(false).textStyle().color("blue");

	map.data(data);

	option.series(map);

	System.out.println(option.toString());
    }

    @Override
    protected String getModule()
    {
	return "Demo";
    }
}
