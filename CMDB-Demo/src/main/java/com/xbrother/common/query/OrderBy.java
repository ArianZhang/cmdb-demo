/**
 * 
 */
package com.xbrother.common.query;

import java.util.ArrayList;
import java.util.List;

import com.xbrother.common.query.enums.OrderType;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-8-7
 * @version 1.0
 */
public class OrderBy {
	public final static OrderBy ID_ORDER_BY = new OrderBy();
	static {
		ID_ORDER_BY.setKey("id");
		ID_ORDER_BY.setType(OrderType.DESC);
	}
	public final static List<OrderBy> ID_ORDER_BY_LIST = new ArrayList<OrderBy>(1);
	static {
		ID_ORDER_BY_LIST.add(OrderBy.ID_ORDER_BY);
	}

	private String key;
	private OrderType type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

}
