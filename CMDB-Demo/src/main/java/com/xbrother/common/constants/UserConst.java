/**
 * 
 */
package com.xbrother.common.constants;

import com.xbrother.common.context.UserInfo;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
public interface UserConst {
	UserInfo SUPER_ADMIN = new UserInfo() {
		{
			setId(0);
			setUsername("superadmin");
			setPassword("888888");
		}
	};
	UserInfo CUSTOMER_ACCOUNT = new UserInfo() {
		{
			setId(-1);
			setUsername("customer_anonymous");
		}
	};
}
