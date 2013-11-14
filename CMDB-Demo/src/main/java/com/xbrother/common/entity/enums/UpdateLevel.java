/**
 * 
 */
package com.xbrother.common.entity.enums;

import com.xbrother.common.utils.LocaleUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013年9月23日
 * @version 1.0
 */
public enum UpdateLevel implements DisplayValueSupport {
	necessary("1"), optional("0"), ;
	public final String value;

	UpdateLevel(String value) {
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
