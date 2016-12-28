package com.ruifios.system.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.ruifios.server.RuifiosEnv;
import com.ruifios.system.model.SystemLogs;

public class SysLogServiceEngine extends Thread {
	
	private static Logger logger = Logger.getLogger(SysLogServiceEngine.class);

	private static SysLogServiceEngine _instance = new SysLogServiceEngine();
	
	private List<SystemLogs> tempList = new ArrayList<SystemLogs>();
	
	private long lastFlushTime = System.currentTimeMillis();
	
	private LinkedBlockingQueue<List<SystemLogs>> queue = new LinkedBlockingQueue<List<SystemLogs>>();
	
	private SysLogServiceEngine(){
		this.setDaemon(true);
		this.setName(SysLogServiceEngine.class.getSimpleName());
	}
	 
	public static SysLogServiceEngine getInstance() {
		if(_instance == null){
			_instance = new SysLogServiceEngine();
		}
		return _instance;
	}

	public void addRecord(SystemLogs log) {
		tempList.add(log);
		long now = System.currentTimeMillis();
		if(tempList.size()>30||now-lastFlushTime>5*1000)
		{
			lastFlushTime = now;
			queue.offer(tempList);
			tempList = new ArrayList<>();
		}
	}

	@Override
	public void run() {
		while(true)
		{
			try
			{
				Thread.sleep(10*1000);
			}
			catch (InterruptedException e)
			{
				logger.error("sleep error: ", e);
			}
			
			List<SystemLogs> list = queue.poll();
			if(list!=null)
			{
				RuifiosEnv.dao.batchInsert(list);
			}
		}
	}

}
