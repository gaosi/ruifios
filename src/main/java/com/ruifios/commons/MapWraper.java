package com.ruifios.commons;

import java.io.Serializable;
import java.util.Map;

/**
 * Map包装类用来接收前台传入map
 * @author 陈华
 *	@RequestMapping("saysth.do")
 *	public void test(MapWraper users) {
 *		for (Map.Entry<String, User> entry : users.getList()) {
 *	        
 *	    }
 *  }
 *	<p>
 *	<tr>
 *		<td><input name="users['x'].firstName" value="aaa" /></td>
 *		<td><input name="users['x'].lastName" value="bbb" /></td>
 *	</tr>
 *	<tr>
 *		<td><input name="users['y'].firstName" value="ccc" /></td>
 *		<td><input name="users['y'].lastName" value="ddd" /></td>
 *	</tr>
 *	<tr>
 *		<td><input name="users['z'].firstName" value="eee" /></td>
 *		<td><input name="users['z'].lastName" value="fff" /></td>
 *	</tr>
 */
public class MapWraper<T> implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, T> map;

	public Map<String, T> getMap() {
		return map;
	}

	public void setMap(Map<String, T> map) {
		this.map = map;
	}

}