package com.ruifios.commons;

/**
 * 默认查询范围实现，支持绝对时间和相对时间
 */
public class DefaultQueryRange extends QueryRange.Range implements QueryRange {

	private static final long serialVersionUID = 5159130268193055400L;

	/**
	 * 绝对时间
	 */
	protected Type type = Type.absolute;

	/**
	 * 用来指定相对时间长度单位，默认为天。
	 */
	protected QueryUnit queryunit = QueryUnit.DAY;

	/**
	 * 相对时间的长度
	 */
	protected int last = 1;
	
	public DefaultQueryRange() {
		super();
	}

	public DefaultQueryRange(long start, long stop) {
		super(start, stop);
	}
	
	@Override
	public Range getRange() {
		if(Type.absolute != type){
			long stop = System.currentTimeMillis();
			long start = stop - last*queryunit.value;
			return new Range(start, stop);
		}
		return this;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public QueryUnit getQueryunit() {
		return queryunit;
	}

	public void setQueryunit(QueryUnit queryunit) {
		this.queryunit = queryunit;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

}
