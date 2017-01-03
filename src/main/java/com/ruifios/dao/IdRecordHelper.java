package com.ruifios.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ruifios.server.RuifiosEnv;
import com.ruifios.system.model.IdRecord;
import com.ruifios.util.Hibernates;

/**
 * 无重复自增主键助手
 * @author ch
 *
 */
public abstract class IdRecordHelper {
	
	private static final Logger logger = Logger.getLogger(IdRecordHelper.class);

	private static Map<String, Object> lockMap = new HashMap<String, Object>();
	
	private static synchronized Object lock(String tblName) { //针对每一张表获取锁，由于表的数量是有限的，因此全部放在内存中可以承受
		Object lock = lockMap.get(tblName);
		if (lock == null) {
			lock = new Object();
			lockMap.put(tblName, lock);
			logger.info(tblName);
		}
		
		return lock;
	}
	
	/**
	 * 获取表ID记录
	 * @param tblName
	 * @return
	 */
	private static IdRecord fetch(String tblName){
		IdRecord record = RuifiosEnv.dao.queryById(IdRecord.class, tblName);
		if(record == null){
			record = new IdRecord();
			record.setName(tblName);
			record.setMaxid(1);
			RuifiosEnv.dao.insert(record);
		}
		return record;
	}
	
	/**
	 * 获取可使用ID，不更新
	 * 
	 * @param tblName
	 * @return
	 */
	public static final long getTblMaxId(String tblName){
		if (tblName == null)
			return -1;
		
		synchronized (lock(tblName)) {
			return fetch(tblName).getMaxid();
		}
	}
	

	/**
	 * 获取可使用ID，不更新
	 * 
	 * @param clazz 与数据库表对应的POJO类对象
	 * @return
	 */
	public static final long getTblMaxId(Class<?> clazz) {
		return getTblMaxId(Hibernates.getTableName(clazz));
	}
	
	/**
	 * 更新ID
	 * 
	 * @param tblName 数据库表名
	 */
	public static final void updateTblMaxId(String tblName) {
		if (tblName == null)
			return;
		
		synchronized(lock(tblName)) {
			IdRecord record = fetch(tblName);
			record.setMaxid(record.getMaxid() + 1);
			RuifiosEnv.dao.update(record);
		}
	}
	

	/**
	 * 更新ID
	 * 
	 * @param tblId 数据库表名对应最大ID值
	 * @param tblName 数据库表名
	 */
	public static final void updateTblMaxId(long tblId, String tblName) {
		if (tblName == null)
			return;
		
		synchronized(lock(tblName)) {
			IdRecord record = fetch(tblName);
			record.setMaxid(tblId);
			RuifiosEnv.dao.update(record);
		}
	}
	
	/**
	 * 更新ID
	 * @param dao
	 * @param entity 代表POJO与数据库表对应关系的Entity对象
	 */
	public static final void updateTblMaxId(Class<?> clazz) {
		updateTblMaxId(Hibernates.getTableName(clazz));
	}
	
	/**
	 * 获取可使用ID，同时更新
	 * 
	 * @param tblName 数据库表名
	 * @return
	 */
	public static final long getTblMaxIdWithUpdate(String tblName, int size) {
		if (tblName == null)
			return -1;
		
		synchronized(lock(tblName)) {
			IdRecord record = fetch(tblName);
			long maxid = record.getMaxid();
			record.setMaxid(maxid+size);
			RuifiosEnv.dao.update(record);
			return maxid;
		}
	}
	
	/**
	 * 获取可使用ID，同时更新
	 * 
	 * @param tblName 数据库表名
	 * @return
	 */
	public static final long getTblMaxIdWithUpdate(String tblName) {
		return getTblMaxIdWithUpdate(tblName, 1);
	}

	/**
	 * 获取可使用ID，同时更新
	 * 
	 * @param entity 代表POJO与数据库表对应关系的Entity对象
	 * @return
	 */
	public static final long getTblMaxIdWithUpdate(Class<?> clazz, int size) {
		return getTblMaxIdWithUpdate(Hibernates.getTableName(clazz), size);
	}
	
	/**
	 * 获取可使用ID，同时更新
	 * 
	 * @param entity 代表POJO与数据库表对应关系的Entity对象
	 * @return
	 */
	public static final long getTblMaxIdWithUpdate(Class<?> clazz) {
		return getTblMaxIdWithUpdate(Hibernates.getTableName(clazz));
	}
	
}
