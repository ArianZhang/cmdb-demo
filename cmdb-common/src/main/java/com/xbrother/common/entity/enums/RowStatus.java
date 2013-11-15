/**
 * 
 */
package com.xbrother.common.entity.enums;

import com.xbrother.common.utils.LocaleUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
public enum RowStatus implements DisplayValueSupport{
	valid("1"), invalid("-1"), ;
	public final String value;

	RowStatus(String value) {
		this.value = value;
	}

	public String displayValue() {
		return LocaleUtils.getResourceProperty(getClass(), toString());
	}

	@Override
	public String value() {
		return this.value;
	}
}
