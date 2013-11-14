package com.xbrother.common.hibernate.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.constants.UserConst;
import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.context.UserInfo;
import com.xbrother.common.entity.BaseEntity;
import com.xbrother.common.entity.enums.Status;

/**
 * Updates created by, created date, client id, updated by and updated date
 * properties of {@link BaseEntity}
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
public class DataObjectInterceptor extends EmptyInterceptor {

	private final static Logger LOGGER = LoggerFactory.getLogger(DataObjectInterceptor.class);

	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (!(entity instanceof BaseEntity)) {
			return false;
		}
		BaseEntity baseEntity = (BaseEntity) entity;
		for (int i = 0; i < propertyNames.length; i++) {
			String propertyName = propertyNames[i];
			if (propertyName.equals("createtime")) {
				state[i] = new Date();
				baseEntity.setCreatetime((Date) state[i]);
				LOGGER.debug("set createtime onSave");
			} else if (propertyName.equals("createby")) {
				UserInfo user = CurrentContext.getCurrentUser();
				if (user != null) {
					state[i] = user.getId();
					baseEntity.setCreateby(user.getId());
				}else{
					state[i] = UserConst.CUSTOMER_ACCOUNT.getId();
					baseEntity.setCreateby(UserConst.CUSTOMER_ACCOUNT.getId());
				}
				LOGGER.debug("set createby onSave");
			} else if (propertyName.equals("updatetime")) {
				state[i] = new Date();
				baseEntity.setUpdatetime((Date) state[i]);
				LOGGER.debug("set updatetime onSave");
			} else if (propertyName.equals("updateby")) {
				UserInfo user = CurrentContext.getCurrentUser();
				if (user != null) {
					state[i] = user.getId();
					baseEntity.setUpdateby(user.getId());
				}else{
					state[i] = UserConst.CUSTOMER_ACCOUNT.getId();
					baseEntity.setCreateby(UserConst.CUSTOMER_ACCOUNT.getId());
				}
				LOGGER.debug("set updateby onSave");
			} else if (propertyName.equals("status")) {
				state[i] = Status.valid.value;
				baseEntity.setStatus(Status.valid.value);
				LOGGER.debug("set status onSave");
			}
		}
		return true;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (!(entity instanceof BaseEntity)) {
			return false;
		}
		BaseEntity baseEntity = (BaseEntity) entity;
		for (int i = 0; i < propertyNames.length; i++) {
			String propertyName = propertyNames[i];
			if (propertyName.equals("updatetime")) {
				currentState[i] = new Date();
				baseEntity.setUpdatetime((Date) currentState[i]);
				LOGGER.debug("set updatetime onFlushDirty");
			} else if (propertyName.equals("updateby")) {
				UserInfo user = CurrentContext.getCurrentUser();
				if (user != null) {
					currentState[i] = user.getId();
					baseEntity.setUpdateby(user.getId());
				}else{
					currentState[i] = UserConst.CUSTOMER_ACCOUNT.getId();
					baseEntity.setCreateby(UserConst.CUSTOMER_ACCOUNT.getId());
				}
				LOGGER.debug("set updateby onFlushDirty");
			}
		}
		return true;
	}
}
