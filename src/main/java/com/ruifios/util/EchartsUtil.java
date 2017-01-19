package com.ruifios.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.style.TextStyle;

/**
 * Echarts 工具类
 * @author ch
 * http://blog.csdn.net/yihaoawang/article/details/50638199
 */
public class EchartsUtil {

	private static String DEFAULTLEGEND = "--";
	
	public static GsonOption getGsonOption(String title, List<String> legend)
	{
		GsonOption option = new GsonOption();
		option.title().text(title).x(X.left).textStyle(new TextStyle().color("black").fontSize(12).fontWeight(700).fontFamily("'Open Sans', sans-serif"));
		option.grid().x(50).y(50).x2(30).y2(50);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().x(X.center).y(Y.bottom).orient(Orient.horizontal).padding(2).data().addAll(legend);
		//option.axisPointer().type("shadow");
		option.toolbox().show(true).feature(Tool.saveAsImage);//Tool.restore,
		
		return option;
	}
	
	public static void initDefault(List<String> xdata, ArrayList<String> temp, int fragment){
		xdata.clear();
		
	}
	
	public static GsonOption getLineOption(String title, List<String> legend, List<String> xdata, String unit, List<Line> lines){
		GsonOption option = getGsonOption(title, legend);
		if(legend.isEmpty())
		{
			legend.add(DEFAULTLEGEND);
			ArrayList<String> temp = new ArrayList<String>();
			xdata.clear();
			long starttime =  DateUtils.getFragmentInHours(new Date(), 1);
			long endtime = DateUtils.getFragmentInHours(new Date(), 1);
			// 初始化时间轴 
			int length = 24;
			long interval = (endtime - starttime)/length;
			for (int i = 0; i < length; i++) 
			{
				long s = (starttime + (i * interval));
				String time = DateFormatUtils.format(s, "%1$tH:%1$tM");
				xdata.add(time);
				temp.add("0.0");
			}

			Line line = new Line(DEFAULTLEGEND);
			line.itemStyle().normal().label().show(false);
			line.itemStyle().emphasis().label().show(false);
			line.data().addAll(temp);
			lines.add(line);
		}

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.type(AxisType.category).name("时间").splitLine().show(false);
		xAxis.axisLabel().show(true).rotate(45);
		xAxis.data().addAll(xdata);
		option.xAxis(xAxis);
		
		ValueAxis yAxis = new ValueAxis().name(unit);
		yAxis.type(AxisType.value).axisLabel().formatter("{value}");
		option.yAxis(yAxis);
		
		option.series().addAll(lines);
		
		return option;
	}
	
	public static void test(int rangeStyle) {
		Iterator<Calendar> iterator = DateUtils.iterator(new Date(), rangeStyle);
		while (iterator.hasNext()) {
			Calendar calendar = iterator.next();
			System.out.println(DateFormatUtils.ISO_DATETIME_FORMAT.format(calendar));
		}
	}
	
	public static void main(String[] args) {
		test(1);
		System.err.println("-----------------"+ 1 +"-------");
		test(2);
		System.err.println("-----------------"+ 2 +"-------");
		test(3);
		System.err.println("-----------------"+ 3 +"-------");
		test(4);
		System.err.println("-----------------"+ 4 +"-------");
		test(5);
		System.err.println("-----------------"+ 5 +"-------");
		test(6);
		System.err.println("-----------------"+ 6 +"-------");
	}
}
