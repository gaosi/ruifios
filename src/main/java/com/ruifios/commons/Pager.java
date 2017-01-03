package com.ruifios.commons;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 单页面显示的条数
	 */
	private int pageSize = 10;
	
	/**
	 * 页面总数
	 */
	private int total;
	
	/**
	 * 当前页面
	 */
	private int currentPage = 1;
	
	/**
	 * 记录总条数
	 */
	private int records;
	
	/**
	 * 数据列表
	 */
	private List<T> data;

	
	/**
	 * 国际位置
	 */
	private String locale = "zh-CN";
	
	/**
	 * 字段排序
	 */
	private String sortOrder = "desc";
	/**
	 * 排序字段名称
	 */
	private String sortName = "";
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage <= 0 ? 1 : currentPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		total = records%pageSize == 0? records/pageSize : records/pageSize+1;
		this.records = records;
	}

	public void setRecords(long records) {
		this.setRecords((int)records);
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getLocale()
	{
		return locale;
	}

	public void setLocale(String locale)
	{
		this.locale = locale;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	
}
