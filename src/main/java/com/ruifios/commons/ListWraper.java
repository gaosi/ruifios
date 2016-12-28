package com.ruifios.commons;

import java.io.Serializable;
import java.util.List;

/**
 * list包装类用来接收前台传入集合
 * @author 陈华
 *	@RequestMapping("saysth.do")
 *	public void test(ListWraper users) {
 *		for (User user : users.getList()) {
 *	        
 *	    }
 *  }
 *	<p>
 *	<tr>
 *		<td><input name="users[0].firstName" value="aaa" /></td>
 *		<td><input name="users[0].lastName" value="bbb" /></td>
 *	</tr>
 *	<tr>
 *		<td><input name="users[1].firstName" value="ccc" /></td>
 *		<td><input name="users[1].lastName" value="ddd" /></td>
 *	</tr>
 *	<tr>
 *		<td><input name="users[2].firstName" value="eee" /></td>
 *		<td><input name="users[2].lastName" value="fff" /></td>
 *	</tr>
 * #{@link http://www.cnblogs.com/HD/p/4107674.html}
 */
public class ListWraper<T> implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}