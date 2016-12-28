package com.hawkeye.common;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地资源
 * @author 陈华
 *
 */
public class LocaleResource {
	
	private static Logger logger = LoggerFactory.getLogger(LocaleResource.class);
	
	private ResourceBundle res;
	private Locale loc;

	public LocaleResource(String lang, String country) {
		this.loc = new Locale(lang, country);
		this.res = ResourceBundle.getBundle("log4j", this.loc);
	}

	public String getString(String str) {
		try {
			return this.res.getString(str);
		} catch (MissingResourceException e) {
			logger.error("no res for:" + str, e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return str;
	}

	public String getLang() {
		return this.loc.getLanguage();
	}

	public String getCountry() {
		return this.loc.getCountry();
	}
}
