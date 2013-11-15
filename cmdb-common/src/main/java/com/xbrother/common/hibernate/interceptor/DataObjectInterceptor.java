package com.xbrother.common.hibernate.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.entity.BaseEntity;
import com.xbrother.common.entity.enums.RowStatus;



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
			if (propertyName.equals("createTime")) {
				state[i] = new Date();
				baseEntity.setCreateTime((Date) state[i]);
				LOGGER.debug("set createTime onSave");
			} else if (propertyName.equals("creator")) {
				state[i] = "1";
				baseEntity.setCreator("1");
				LOGGER.debug("set createby onSave");
			} else if (propertyName.equals("updateTime")) {
				state[i] = new Date();
				baseEntity.setUpdateTime((Date) state[i]);
				LOGGER.debug("set updateTime onSave");
			} else if (propertyName.equals("updator")) {
				state[i] = "1";
				baseEntity.setUpdator("1");
				LOGGER.debug("set updateby onSave");
			} else if (propertyName.equals("rowStatus")) {
				state[i] = RowStatus.valid.value;
				baseEntity.setRowStatus(RowStatus.valid.value);
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
			if (propertyName.equals("updateTime")) {
				currentState[i] = new Date();
				baseEntity.setUpdateTime((Date) currentState[i]);
				LOGGER.debug("set updateTime onFlushDirty");
			} else if (propertyName.equals("updator")) {
				currentState[i] = "1";
				baseEntity.setCreator("1");
				LOGGER.debug("set updateby onFlushDirty");
			}
		}
		return true;
	}
}
