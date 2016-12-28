package com.ruifios;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ruifios.jetty.Profiles;

/**
 * 初始化数据库
 * @author ch
 * org.hibernate.tool.hbm2ddl.SchemaUpdate
 * org.hibernate.mapping.Table
 * org.hibernate.mapping.Table.sqlCreateString(Dialect, Mapping, String, String)
 */
public class InitDB extends Profiles
{
	private static ComboPooledDataSource dataSource = null;
	
	@SuppressWarnings("resource")
	public InitDB()
	{
		ApplicationContext context = new FileSystemXmlApplicationContext(new String[] {WEB_INF+"/classes/applicationContext.xml"});
		dataSource = (ComboPooledDataSource) context.getBean("dataSourceBean");
	}
	
	/**
	 * 清空数据库
	 */
	public void clear()
	{		
		try {
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			List<String> list = new ArrayList<String>();
			//删除临时表
			ResultSet rs = stmt.executeQuery("show tables like 'z\\_%'");
			while(rs.next())
			{
				String name = (String)rs.getObject(1);
				list.add("`"+name+"`");
			}
			for(String name: list)
			{
				stmt.execute("drop table "+name);
			}
			list.clear();
			//删除系统默认数据
			rs = stmt.executeQuery("show tables");
			while(rs.next())
			{
				String name = (String)rs.getObject(1);
				list.add("`"+name+"`");
			}
			rs.close();
			
			for(String name: list)
			{
				stmt.execute("truncate "+name);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据库
	 */
	public void init()
	{
		Path mysqlpath = new File(System.getProperty("user.dir")).toPath().resolve("dll").resolve("mysql");
		File[] files = mysqlpath.toFile().listFiles();
		LinkedList<File> list = new LinkedList<File>(); 
		for(File file: files){
			if(file.isDirectory()){
				list.addAll(scanFile(file));
			}else if(file.getName().endsWith(".sql")){
				if(file.getName().startsWith("schema")){
					list.addFirst(file);
				}else{
					list.add(file);
				}
			}
		}
		
		exeSqlFile(list);
	}
	
	/**
	 * 扫描文件夹
	 * @param sqlFile
	 * @return
	 */
	private LinkedList<File> scanFile(File sqlFile){
		File[] files = sqlFile.listFiles();
		LinkedList<File> list = new LinkedList<File>(); 
		for(File file: files){
			if(file.isDirectory()){
				list.addAll(scanFile(file));
			}else if(file.getName().endsWith(".sql")){
				if(file.getName().startsWith("schema")){
					list.addFirst(file);
				}else{
					list.add(file);
				}
			}
		}
		return list;
	}
	
	/**
	 * 执行sql文件
	 * @param sqlFile
	 * ScriptUtils.executeSqlScript(Connection connection, EncodedResource resource)
	 * 
	 */
	private void exeSqlFile(List<File> list)
	{
		if(list.size() > 0)
		{
			try
			{
				Connection conn = dataSource.getConnection();

				for(File sqlFile: list){
					Resource resource = new FileSystemResource(sqlFile);
					ScriptUtils.executeSqlScript(conn, new EncodedResource(resource));
				}
				
				conn.close();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("Sql File NOT Exist : ");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		InitDB init = new InitDB();
		init.clear();
		init.init();
	}

}
