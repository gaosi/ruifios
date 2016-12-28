package com.ruifios.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ruifios.commons.ValueWraper;

/**
 * 系统工具，主要是跟系统操作相关
 * @author Administrator
 *
 */
public class SystemUtil {

	private static Logger log = Logger.getLogger(SystemUtil.class);
	
	/**
	 * 单位(M)
	 */
	public static final long UNIT = 1024*1024;
	
	/**
	 * 日期格式化
	 */
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String tcpdump_cmd = "tcpdump -i %s %s %s -s0 -c %d -w %s";
	public static final String tcpdump_cmd_exp_ip_port = "and ( src %s and src port %d ) or ( dst %s and dst port %d ) ";
	public static final String tcpdump_cmd_exp_ip = " and (src %s or dst %s )";
	public static final String tcpdump_cmd_exp_port = " and (src port %d or dst port %d) ";
	
	public static String DataformatToString(Date date)
	{
		String datetime = format.format(date);
		return datetime;
	}

	/**
	 * 时间格式化
	 * @param time
	 * @param format
	 * @return
	 */
	public static String DataformatToString(long time, String format) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
	
	public static String DataformatToString(Date date, String format) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date StringparseToDate(String source)
	{
		try {
			return format.parse(source);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static int updateTime(long time){
		int exitValue = -1;
		if(checkOs()){
			String datetime = format.format(new Date(time));
			String command = "date -s '" + datetime + "' && clock -w";
			exitValue = SystemUtil._execCommand(command);
		}
		return exitValue;
	}
	
	/**
	 * 获得主机名
	 * @return
	 */
	public static String getHostName(){
		String hostname = "";
		if(checkOs()){
			try {
				hostname = SystemUtil.execCommand("hostname");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			InetAddress a;
			try {
				a = InetAddress.getLocalHost();
				hostname = a.getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return hostname;
	}
	
	/**
	 * 获得cpu信息
	 * @return
	 */
	public static String getCpuInfo(){
		String cpuinfo = "";
		if(checkOs()){
			try {
				cpuinfo = SystemUtil.execCommand("grep 'cpu MHz' /proc/cpuinfo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cpuInfo(cpuinfo);
	}
	
	/**
	 * 读取CUP主频信息
	 * @param str
	 * @return
	 */
	private static String cpuInfo(String str){
		StringBuffer cpuinfo = new StringBuffer();
		try {
			String[] cpuArr = str.split("cpu MHz\t\t: ");
			for(String c : cpuArr){
				c = c.trim();
				if(!"".equals(c)){
					double cpuMHz = Float.parseFloat(c);
					cpuinfo.append(new BigDecimal(cpuMHz/1024).setScale(2, 
							BigDecimal.ROUND_HALF_UP)).append("GHz ");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return cpuinfo.toString();
	}
	
	/**
	 * 获得内存信息
	 * @return
	 */
	public static String getMemoryInfo(){
		String memoryinfo = "";
		if(checkOs()){
			try {
				memoryinfo = SystemUtil.execCommand("grep MemTotal /proc/meminfo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memInfo(memoryinfo);
	}
	
	/**
	 * 读取物理内存
	 * @return
	 */
	private static String memInfo(String str) {
		if (!StringUtils.isEmpty(str)) {
			if(str.startsWith("MemTotal:")){
				int start = "MemTotal:".length() ;
				str = str.substring(
						start, str.indexOf("kB")).trim();
				
				double gb = Double.parseDouble(str)/1024/1024;
				DecimalFormat df = new DecimalFormat("#.##"); 
				str =  df.format(gb) + " GB";
			}
		}
		return str;
	}
	
	/**
	 * 获取内存利用率
	 * @return
	 */
	public static String getMemUsage(){
		String[] memoryusag = null;
		if(checkOs()){
			try {
				memoryusag = SystemUtil.execCmd("/usr/bin/free -o");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memUsage(memoryusag);
	}
	
	private static String memUsage(String[] memoryusag){
		String result = "0";
		if(memoryusag!=null && memoryusag.length>0){
			String mem = memoryusag[1];
			String[] stringarray=mem.split("\\s+");  
			List<String> list = new ArrayList<String>();
			//// 在每个空格字符处进行分解  
			for(String stemp:stringarray){  
				if(!StringUtils.isEmpty(stemp)){
					list.add(stemp);
				}
			}
			if(list.size()>0){
				long total = Long.parseLong(list.get(1));
				long used = Long.parseLong(list.get(2));
				long memusage = (long)(100*((double)used/(double)total));
				result = String.valueOf(memusage);
			}
		}
		return result;
	}
	
	/**
	 * 检查操作系统是否是linux
	 * @return
	 */
	public static boolean checkOs(){
		boolean b = false;
		String osname = System.getProperty("os.name");
		if(!StringUtils.isEmpty(osname) && osname.equalsIgnoreCase("linux")){
			b = true;
		}
		return b;
	}
	
	/**
	 * 获取cpu利用率
	 * @return
	 */
	public static String getCpuUsage(){
		String[] cpuusag = null;
		if(checkOs()){
			try {
				cpuusag = SystemUtil.execCmd("vmstat 1 2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cpuUsage(cpuusag);
	}
	
	private static String cpuUsage(String[] cpuusag){
		String result = "0";
		if(cpuusag!=null && cpuusag.length>0){
			String cpu = cpuusag[3];
			String[] stringarray= cpu.split("\\s+");  
			List<String> list = new ArrayList<String>();
			//// 在每个空格字符处进行分解  
			//int k = 0;
			for(String stemp:stringarray){  
				if(!StringUtils.isEmpty(stemp)){
					list.add(stemp);
					//k++;
				}
			}
			if(list.size()>0){
				long cpuusage = 100-Long.parseLong(list.get(14));
				result = String.valueOf(cpuusage);
			}
		}
		return result;
	}
	
	/**
	 * 获取硬盘大小(单位：B)
	 * @return
	 */
	public static List<ValueWraper> getDiskSize(){
		String[] diskusag = null;
		if(checkOs()){
			try {
				diskusag = SystemUtil.execCmd("df -m | awk '{if(NF>1) print $NF,$(NF-3),$(NF-2)}'");
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return diskInfo(diskusag);
	}
	
	private static List<ValueWraper> diskInfo(String[] diskusag){
		List<ValueWraper> result = new ArrayList<ValueWraper>();
		if(diskusag!=null && diskusag.length>0){
			for(int i=1;i<diskusag.length;i++){
				String disk_info = diskusag[i];
				String[] stringarray=disk_info.split("\\s+");  
				List<String> list = new ArrayList<String>();
				//// 在每个空格字符处进行分解  
				for(String stemp:stringarray){  
					if(!StringUtils.isEmpty(stemp)){
						list.add(stemp);
					}
				}
				if(list.size()>0){
					ValueWraper valueWraper = new ValueWraper();
					valueWraper.setName(list.get(0));
					valueWraper.setIdle(Long.parseLong(list.get(2))*UNIT);
					valueWraper.setUsed(Long.parseLong(list.get(1))*UNIT);
					result.add(valueWraper);
				}
			}
		}else{
			File[] roots = File.listRoots();//获取磁盘分区列表
	        for (File file : roots) {
	        	ValueWraper valueWraper = new ValueWraper();
	        	valueWraper.setName(file.getPath());//分区名称
	        	valueWraper.setIdle(file.getFreeSpace());//可用空间
				valueWraper.setUsed(file.getUsableSpace());//以用空间
				valueWraper.setTotal(file.getTotalSpace());//总容量
				result.add(valueWraper);
	        }
		}
		return result;
	}
	
	/**
	 * 获取交换分区大小(单位：B)
	 * @return
	 */
	public static List<ValueWraper> getSwapSize(){
		String[] swapusag = null;
		if(checkOs()){
			try {
				swapusag = SystemUtil.execCmd("/sbin/swapon -s");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return swapInfo(swapusag);
	}
	
	private static List<ValueWraper> swapInfo(String[] swapusag){
		List<ValueWraper> result = new ArrayList<ValueWraper>();
		if(swapusag!=null && swapusag.length>0){
			for(int i=1;i<swapusag.length;i++){
				String sapinfo = swapusag[i];
				String[] stringarray= sapinfo.split("\\s+");  
				List<String> list = new ArrayList<String>();
				//// 在每个空格字符处进行分解 
				for(String stemp:stringarray){  
					if(!StringUtils.isEmpty(stemp)){
						list.add(stemp);
					}
				}
				if(list.size()>0){
					ValueWraper valueWraper = new ValueWraper();
					valueWraper.setName(list.get(0));
					long total = Long.parseLong(list.get(2))*UNIT;
					long used = Long.parseLong(list.get(3))*UNIT;
					valueWraper.setTotal(total);
					valueWraper.setUsed(used);
					valueWraper.setIdle(total-used);
					result.add(valueWraper);
				}
			}
		}else{
			ValueWraper valueWraper = new ValueWraper();
			valueWraper.setName("--");
			valueWraper.setTotal(0);
			valueWraper.setUsed(0);
			valueWraper.setIdle(0);
			result.add(valueWraper);
		}
		return result;
	}
	
	/**
	 * 获取服务器当前的时间
	 * @return
	 */
	public static Date getSystemDate(){
		Date date = null;
		if(checkOs()){
			String dateStr = SystemUtil.execCommand("date '+%Y-%m-%d %T'");
			if(!StringUtils.isEmpty(dateStr)){
				try {
					date = format.parse(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return date;
	}
	
	/**
	 * 重启设备	
	 */
	public static int reBoot() {
		int boot = -1;
		if(checkOs()){
			boot = _execCommand("reboot");
		}
		return boot;
	}
	
	/**
	 * 系统启动时间
	 * @return
	 */
	public static String getBootTime(){
		String time = "";
		if(checkOs()){
			time = execCommand("date -d \"$(awk -F. '{print $1}' /proc/uptime) second ago\" +\"%Y/%m/%d %H:%M:%S\"");
		}
		return time;
	}
	
	/**
	 * 运行多长时间
	 * @return
	 */
	public static String getRunTime(){
		String time = "";
		if(checkOs()){
			String cmd = "cat /proc/uptime| awk -F. '{run_days=$1 / 86400;run_hour=($1 % 86400)/3600;run_minute=($1 % 3600)/60;run_second=$1 % 60;printf(\"%d天%d时%d分%d秒\",run_days,run_hour,run_minute,run_second)}'";
			time = execCommand(cmd);
		}
		return time;
	}
	
	public static String getHostIps(){
		StringBuilder sb = new StringBuilder();
		try {
			List<String> ipList = fetchIpList();
			for(String localIp:ipList){
				sb.append("[").append(localIp).append("]");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static List<String> fetchIpList() throws SocketException
	{
		String localip = "127.0.0.1";// 本地IP，如果没有配置外网IP则返回它
		
		List<String> ipList = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface
				.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded)
		{
			try
			{
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements())
				{
					try
					{
						ip = address.nextElement();
						
						if(!ip.getHostAddress().equals(localip))
						{
							String ipStr = ip.getHostAddress();
							String[] array = ipStr.trim().split("\\.");
							if(array.length==4&&!ipStr.equals("0.0.0.0"))
							{
								ipList.add(ip.getHostAddress());
							}
							
						}
					}
					catch(Exception e)
					{
						
					}
					
				}
			}
			catch(Exception e)
			{
				
			}
			
		}
		return ipList;
	}
	
	/**
	 * 执行的是返回字符串的命令
	 * @param command
	 * @return
	 */
	public static String[] execCmd(String command){
		String result[] = null;
		if(!StringUtils.isEmpty(command)){
			String[] cmds = new String[3];
			//linux系统
			cmds = new String[]{"/bin/sh","-c",command};
			result = execCmd(cmds);
		}
		return result;
	}
	
	/**
	 * 执行的是返回字符串的命令
	 * @param command
	 * @return
	 */
	private static String[] execCmd(String[] cmds){
		List<String> list = new ArrayList<String>();
		Process process = null;
		try {
			if(cmds!=null && cmds.length>0){
				try {
					process = Runtime.getRuntime().exec(cmds);
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";
					while((line=reader.readLine())!=null){
						list.add(line);
					}
					process.getOutputStream().flush();   //刷新输出流
					process.getOutputStream().close();   //关闭输出流
					process.waitFor();
				} catch (InterruptedException e) {
					log.error("exec command["+cmds[2]+"] error",e);
				}
			}else{
				return list.toArray(new String[0]);
			}
		} catch (IOException e) {
			log.error("exec command["+cmds[2]+"] error",e);
		}finally{
			if(process!=null){
				try {
					process.destroy();
				} catch (Exception e) {
					e.printStackTrace();	
				}
			}
		}
		log.info("execute [" + cmds[2] + "] return result size=" + list.size());
		return list.toArray(new String[0]);
	}
	
	/**
	 * 执行的是返回字符串的命令
	 * @param command
	 * @return
	 */
	public static String execCommand(String command){
		String result = "";
		if(checkOs() && !StringUtils.isEmpty(command)){
			String[] cmds = new String[3];
			//linux系统
			cmds = new String[]{"/bin/sh","-c",command};
			result = exec(cmds);
		}
		return result;
	}
	
	/**
	 * 执行的是返回字符串的命令
	 * @param command
	 * @return
	 */
	public static String exec(String[] cmds){
		StringBuilder sb = new StringBuilder();
		Process process = null;
		try {
			if(cmds!=null && cmds.length>0){
				try {
					process = Runtime.getRuntime().exec(cmds);
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";
					while((line=reader.readLine())!=null){
						sb.append(line).append("\n");
					}
					process.getOutputStream().flush();   //刷新输出流
					process.getOutputStream().close();   //关闭输出流
					process.waitFor();
				} catch (InterruptedException e) {
					log.error("exec command["+cmds[2]+"] error",e);
				}
			}else{
				return sb.toString();
			}
		} catch (IOException e) {
			log.error("exec command["+cmds[2]+"] error",e);
		}finally{
			if(process!=null){
				try {
					process.destroy();
				} catch (Exception e) {
					e.printStackTrace();	
				}
			}
		}
		log.info("execute [" + cmds[2] + "] return [" + sb.toString() + "]" );
		return sb.toString();
	}
	
	
	public static int _execCommand(String command){
		int result = -1;
		if(!StringUtils.isEmpty(command)){
			String[] cmds = new String[3];
			//linux系统
			cmds = new String[]{"/bin/sh","-c",command};
			log.info("linux command:"+command);
			result = _exec(cmds);
		}
		return result;
	}
	/**
	 * 返回的是执行命令是否成功的状态
	 * @param command
	 * @return
	 */
	public static int _exec(String[] cmds){
		int result = -1;
		Process process = null;
		try {
			if(cmds!=null && cmds.length>0){
				process = Runtime.getRuntime().exec(cmds);
				Thread t1 = new Thread(new StreamWraper(process.getInputStream()));//输出标准输出流
				Thread t2 = new Thread(new StreamWraper(process.getErrorStream()));//数据标准错误数据流
				t1.start();
				t2.start();
				t1.join();
				process.getOutputStream().flush();//刷新标准输出流
				process.getOutputStream().close();//关闭标准输出流
				result = process.waitFor();
				log.info("run command state:" + result);
			}else{
				return result;
			}
		} catch (Exception e) {
			log.error("exec command["+cmds[2]+"] error",e);
		}finally{
			if(process!=null){
				try {
					process.destroy();
				} catch (Exception e) {
					e.printStackTrace();	
				}
			}
		}
		log.info("execute [" + cmds[2] + "] return [" + result + "]" );
		return result;
	}
	
	/**
	 * tcpdump 抓包
	 * 
	 * @param eth
	 * 		网口名称
	 * @param protL4
	 * 		4层协议
	 * @param ip
	 * 		单个地址
	 * @param port
	 * 		单个端口
	 * @param packetCount
	 * 		抓包个数
	 * @param path
	 * 		抓包存放路径
	 * @return
	 * @throws IOException
	 */
	public static Process excTcpdump(String eth, String protL4, String ip, int port, int packetCount, String path) throws IOException{
		if(StringUtils.isEmpty(eth)){
			throw new NullPointerException("eth can't be empty.");
		}
		if(StringUtils.isEmpty(protL4)){
			throw new NullPointerException("4 layer protocol can not be empty.");
		}
		if(packetCount <= 0){
			throw new IllegalArgumentException("Packet number cannot be less than zero.");
		}
		if(StringUtils.isEmpty(path)){
			throw new NullPointerException("path can't be empty.");
		}
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdir();
		}
		String exp = "";
		if(!StringUtils.isEmpty(ip) && port > 0){
			exp = String.format(tcpdump_cmd_exp_ip_port, ip, port, ip, port);
		}else if(!StringUtils.isEmpty(ip)){
			exp = String.format(tcpdump_cmd_exp_ip, ip, ip);
		}else if(port > 0){
			exp = String.format(tcpdump_cmd_exp_port, port, port);
		}
		String cmd = String.format(tcpdump_cmd, new Object[]{eth, protL4.toLowerCase(), exp, packetCount, path});
		
		if(checkOs()){
			log.info(cmd);
			Process process = Runtime.getRuntime().exec(cmd);
			return process ;
		}
		return null;
	}
	
	/**
	 * 获得本机网卡名称
	 * 
	 * @return
	 */
	public static List<String> getEth(){
		String ethstr = exec(new String[]{"/bin/sh","-c", "/etc/init.d/network status | awk 'NR==4 {print}'"});
		String[] etharray = ethstr.split(" ");
		List<String> ethlist = new ArrayList<String>(etharray.length);
		for(String eth : etharray){
			if(eth.equals("lo") || eth.startsWith("eth")){
				ethlist.add(eth);
			}
		}
		return ethlist;
	}
	
	public static void main(String[] args) {
////		String str = execCommand("cmd /c wscript ");
////		System.out.println(str);
//		
//		System.out.println(getHostIps());
//		System.out.println(memInfo("MemTotal:         1012704 kB"));
//		String[] s = new String[3];
//		s[0]="        total       used       free     shared    buffers     cached";
//		s[1]="Mem:       1012704     353232     659472          0      11160     140908";
//		s[2]="Swap:      2031608          0    2031608                                 ";
//
//		System.out.println(memUsage(s));
//		
//		s = new String[4];
//		s[0]="procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----";
//		s[1]=" r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st";
//		s[2]=" 1  0      0 658728  11800 141052    0    0    18     3   41   64  0  0 99  0  0";
//		s[3]=" 0  0      0 658704  11800 141052    0    0     0     0   37   60  0  0 100  0  0";
//		System.out.println(cpuUsage(s));
//		
//		s = new String[4];
//		s[0]="on Available Use%";
//		s[1]="/ 993 15818";
//		s[2]="/dev/shm 0 495";
//		s[3]="/boot 40 420";
//
//		List<ValueWraper> list = diskInfo(s);
//		for(ValueWraper obj:list){
//			System.out.println("name="+obj.getName()+",total="+obj.getTotal()+",used="+obj.getUsed()+",idle="+obj.getIdle());
//		}
//		
//		s = new String[2];
//		s[0]="Filename                          Type          Size    Used    Priority";
//		s[1]="/dev/dm-1                               partition       2031608 0       -1";
//
//		list = swapInfo(s);
//		for(ValueWraper obj:list){
//			System.out.println("name="+obj.getName()+",total="+obj.getTotal()+",used="+obj.getUsed()+",idle="+obj.getIdle());
//		}
////		int a = _execCommand("mmc");
////		System.out.println(a);
	}
}

class StreamWraper implements Runnable {
	private Logger log = Logger.getLogger(StreamWraper.class);
	
	private InputStream ins;
	
	public StreamWraper(InputStream ins){
		this.ins = ins;
	}
	
	public void run() {
		if(ins!=null){
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
				String line = "";
				while((line=reader.readLine())!=null){
					log.info(line);
				}
			} catch (IOException e) {
				log.error("Stream error",e);
			}finally{
				if(ins!=null){
					try {
						ins.close();
					} catch (IOException e) {
						log.error("Stream close error", e);
					}
				}
			}
		}
	}
}