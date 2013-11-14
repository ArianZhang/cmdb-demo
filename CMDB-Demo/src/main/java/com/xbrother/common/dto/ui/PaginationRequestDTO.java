/**
 * 
 */
package com.xbrother.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

import com.xbrother.common.query.Condition;
import com.xbrother.common.query.OrderBy;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-25
 * @version 1.0
 */
public class PaginationRequestDTO {
	int page;
	int rows;
	List<Condition> conditions;
	List<OrderBy> orderBies;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<Condition> getConditions() {
		if (conditions == null) {
			conditions = new ArrayList<Condition>();
		}
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<OrderBy> getOrderBies() {
		if (orderBies == null) {
			orderBies = OrderBy.ID_ORDER_BY_LIST;
		}
		return orderBies;
	}

	public void setOrderBies(List<OrderBy> orderBies) {
		this.orderBies = orderBies;
	}

}
