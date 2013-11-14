package com.xbrother.common.dto.ui;

import java.util.List;

import com.xbrother.common.dto.SuperDTO;

/**
 * Description:
 * 
 * @author Arian Zhang
 * @email hzhang@digitnexus.com
 * @Date 2013-7-24 下午2:22:10
 * @since v1.0.0
 */
public class PaginationResponseDTO<T> {

	// for response
	long total;
	List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
