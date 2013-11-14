/**
 * 
 */
package com.xbrother.common.query;

import com.xbrother.common.entity.enums.Status;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
public class Condition {

	public final static Condition HIDDEN_SUPER = new Condition("id", ">=", "1");

	public final static Condition VALID_STATUS = new Condition("status", "=", Status.valid.value());

	String name;
	String symbol;
	String value;

	public Condition() {
	}

	public Condition(String name, String symbol, String value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
