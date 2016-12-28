package com.ruifios.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ruifios.server.RuifiosEnv;

public class Hibernates {
	
	public static void initLazyProperty(Object proxyedPropertyValue) {
		Hibernate.initialize(proxyedPropertyValue);
	}

	/**
	 * 从DataSoure中取出connection, 根据connection的metadata中的jdbcUrl判断Dialect类型.
	 * 仅支持Oracle, H2, MySql, PostgreSql, SQLServer，如需更多数据库类型，请仿照此类自行编写。
	 */
	public static String getDialect(DataSource dataSource) {
		String jdbcUrl = getJdbcUrlFromDataSource(dataSource);

		// 根据jdbc url判断dialect
		if (StringUtils.contains(jdbcUrl, ":h2:")) {
			return H2Dialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			return MySQL5InnoDBDialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
			return Oracle10gDialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
			return PostgreSQL82Dialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":sqlserver:")) {
			return SQLServer2008Dialect.class.getName();
		} else {
			throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
		}
	}

	private static String getJdbcUrlFromDataSource(DataSource dataSource) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException(
						"Connection returned by DataSource [" + dataSource
								+ "] was null");
			}
			return connection.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * 数据库密码解密
	 * @param password
	 * @return
	 */
	public static String getPassword(String password){
		password = EncryptUtil.decrypt(EncryptUtil.KEY, password);
		return password;
	}
	
	/**
	 * 获取hibernate配置信息
	 * @return
	 */
	public static Configuration getConfiguration() {  
	    // 取sessionFactory的时候要加上&  
	    LocalSessionFactoryBean factory = RuifiosEnv.context.getBean("&_sessionFactory", LocalSessionFactoryBean.class); 
	    if(factory != null)
	    	return factory.getConfiguration(); 
	    return null;
    }  
	
	/**
	 * 获取持久化类
	 * @param clazz
	 * @return
	 */
    private static <T> PersistentClass getPersistentClass(Class<T> clazz) {  
        synchronized (clazz) {   
        	Configuration configuration = getConfiguration();
            PersistentClass pc = getConfiguration().getClassMapping(clazz.getSimpleName());   
            if (pc == null) {  
                configuration = configuration.addClass(clazz);  
                pc = configuration.getClassMapping(clazz.getName());  
            }  
            return pc;  
        }  
    }  
  
    /** 
     * 获得实体类对应的表名 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @return 表名 
     */  
    public static String getTableName(Class<?> clazz) {  
        return getPersistentClass(clazz).getTable().getName();  
    }  
  
    /** 
     * 获得实体类对应表的主键字段名称 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @return 主键字段名称 
     */  
    public static String getPKColumnName(Class<?> clazz) {  
        return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();  
    }  
  
    /** 
     * 获得类属性对应的字段名 
     *  
     * @param clazz 
     *            实体类的Class对象 
     * @param propertyName 
     *            实体类的属性名 
     * @return 属性对应的字段名 
     */  
    public static String getColumnName(Class<?> clazz, String propertyName) {  
        String columnName = "";  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Property property = persistentClass.getProperty(propertyName);  
        Iterator<?> iterator = property.getColumnIterator();  
        if (iterator.hasNext()) {  
            Column column = (Column) iterator.next();  
            columnName += column.getName();  
        }  
        return columnName;  
    }  
  
}
