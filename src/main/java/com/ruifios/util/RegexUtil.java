package com.ruifios.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author ch
 * #{@see http://deerchao.net/tutorials/regex/regex.htm}
 */
public abstract class RegexUtil {
	
	/**
	 * IPV4单个范围 0-255 
	 */ 
	public static String ipcell = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"; 
	
	/**
	 * IPV4
	 */
	public static String ipv4 = "^("+ipcell+"\\.){3}"+ipcell+"$"; 
  
	/**
	 * IPV6
	 */
	public static String ipv6 = "^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$";
	
	/**
	 * 只能为数字
	 */
	public static String digits = "^\\d+$";

	/**
	 * 只能为字母数字
	 */
	public static String alphanum = "^\\w+$";
	
	/**
	 * 只能为数值
	 * 正负，整数或小数
	 */
	public static String number = "^(\\-)?\\d+(\\.\\d+)?$";
	
	/**
	 * 邮政编码
	 */
	public static String postcode = "^\\d{6}$";
	
	/**
	 * 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 
     * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏", 
     * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
     * 41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南", 
     * 50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏", 
     * 61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",
     * 71:"台湾",
     * 81:"香港",82:"澳门",
     * 91:"国外"
     * (1[1-6]|2[2-3]|3[3-7]|4[1-6]|5[0-4]|6[1-5]|71|8[1-2]|91)
	 * 身份证15位编码规则：dddddd yymmdd xx p
	 * 	次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位）
	 * 
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y
	 * 	次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位）
	 * 
	 */ 
	public static String idcard = "^(1[1-6]|2[2-3]|3[3-7]|4[1-6]|5[0-4]|6[1-5]|71|8[1-2]|91)\\d{4}((\\d{2})|([1-9]\\d{3}))(1[0-2]|([1-9]))((3[0-1])|[0-2]\\d)(\\d{3})(\\d|X|x)?";
	
	/**
	 * 手机号码格式
	 * 只允许以13、15、18开头的号码
	 * 如：13012345678、15929224344、18201234676
	 */
	public static String mobile = "^1[3,5,8]\\d{9}$";
	
	/**
	 * 固定电话号码格式
	 * 因为固定电话格式比较复杂，情况比较多，主要验证了以下类型
	 * 如：010-12345678、0912-1234567、(010)-12345678、(0912)1234567、(010)12345678、(0912)-1234567、01012345678、09121234567
	 */
	public static String phone = "^(^0\\d{2}-?\\d{8}$)|(^0\\d{3}-?\\d{7}$)|(^0\\d{2}-?\\d{8}$)|(^0\\d{3}-?\\d{7}$)$";
	
	/**
	 * Email邮箱
	 * 如：zhangsan@163.com、li-si@236.net、wan_gwu999@SEED.NET.TW
	 */
	public static String email = "^([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+(\\.[a-zA-Z]{2,3})+$";
	
	/**
	 * 网址
	 * 只允许http、https、ftp这三种
	 * 如：http://www.baidu.com
	 */
	public static String web = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";

	/**
	 * 只能是中文汉字
	 */
	public static String chinese = "^[\u4e00-\u9fa5]+$";

	/**
	 * 用户名
	 * 只能是字母数字下划线，并且以字母开头(5-16位)
	 */
	public static String username = "^[a-zA-Z]\\w{4,15}$";
	
	/**
	 * 日期格式验证
	 * 因为日期格式比较多，主要验证了以下类型
	 * 2012-05-14、2012/05/6、2012.5.14、20120528
	 */
	public static String date = "^[1-9]\\d{3}([-|\\/|\\.])?((0\\d)|([1-9])|(1[0-2]))\\1(([0|1|2]\\d)|([1-9])|3[0-1])$";
	
	/**
	 * 正则表达式匹配
	 * @param regex
	 * @param arg
	 * @return
	 */
	public static final boolean isValid(String regex, String arg) {
		Pattern a = Pattern.compile(regex);
		return a.matcher(arg).matches();
	}
	
	/**
	 * 身份证15位编码规则：dddddd yymmdd xx p
	 * dddddd：6位地区编码
	 * yymmdd: 出生年(两位年)月日，如：910215
	 * xx: 顺序编码，系统产生，无法确定
	 * p: 性别，奇数为男，偶数为女
	 * 
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y
	 * dddddd：6位地区编码
	 * yyyymmdd: 出生年(四位年)月日，如：19910215
	 * xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
	 * y: 校验码，该位数值可通过前17位计算获得
	 * 
	 * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
	 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
	 * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
	 * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
	 * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
	 */
	public static final boolean validateIdCard(String arg){
		//15位和18位身份证号码的正则表达式
		boolean flag = isValid(idcard, arg);

		//如果通过该验证，说明身份证格式正确，但准确性还需计算
		if(flag){
			if(arg.length() == 18){
				int[] idCardWi = new int[]{ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; //将前17位加权因子保存在数组里
				int[]  idCardY = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 }; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
				int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
				for(int i=0;i<17;i++){
					idCardWiSum += Integer.parseInt(arg.substring(i,i+1)) * idCardWi[i];
				}

				int idCardMod = idCardWiSum%11;//计算出校验码所在数组的位置
				String idCardLast = arg.substring(17);//得到最后一位身份证号码

				//如果等于2，则说明校验码是10，身份证号码最后一位应该是X
				if(idCardMod==2){
					if(idCardLast=="X"||idCardLast=="x"){
						return true;
					}
				}else{
					//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
					int idCardLastInt = Integer.parseInt(idCardLast);
					if(idCardLastInt == idCardY[idCardMod]){
						return true;
					}
				}
				return false;
			}
			// 15位身份证
			return true;
		} else{
			return false;
		}
	}
}
