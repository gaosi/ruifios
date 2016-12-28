package com.ruifios.jetty;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Profiles {

	public static final String ACTIVE_PROFILE = "spring.profiles.active";
	
	public static final String DEFAULT_PROFILE = "spring.profiles.default";

	public static final String PRODUCTION = "production";
	
	public static final String DEVELOPMENT = "development";
	
	public static final String UNIT_TEST = "test";
	
	public static final String WEB_INF = System.getProperty("user.dir")+"/WebContent/WEB-INF/";
	
	/**
	 * 在Spring启动前，设置profile的环境变量
	 */
	static {
		// 设定Log4j的profile
		System.setProperty("catalina.base", WEB_INF);
		PropertyConfigurator.configure(WEB_INF+"/classes/log4j.properties");

		System.setProperty(ACTIVE_PROFILE, PRODUCTION);
	}
	
	/**
	 * 在Spring启动前，设置profile的环境变量
	 */
	public static void setProfileAsSystemProperty(String profile) {
		System.setProperty(ACTIVE_PROFILE, profile);
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
        String password = "admin123"; 
		String hashedPassword = passwordEncoder.encode(password);
		boolean result = BCrypt.checkpw(password, hashedPassword);
		
		System.err.println(hashedPassword+ " result:" +result);
	}
}
