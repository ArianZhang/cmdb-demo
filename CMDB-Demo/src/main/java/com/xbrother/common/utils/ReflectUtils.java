package com.xbrother.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.UtilsExceptionCode;

public class ReflectUtils {

	private final static Logger LOG = LoggerFactory.getLogger(ReflectUtils.class);

	private final static Class<?>[] FIELD_GET_METHOD_PARM_TYPE = new Class<?>[] {};

	public static Method getFieldGetMethod(Class<?> clazz, Field field) {
		Method method = null;
		try {
			StringBuilder methodName = new StringBuilder("get");
			methodName.append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1));
			method = clazz.getMethod(methodName.toString(), FIELD_GET_METHOD_PARM_TYPE);
		} catch (Exception e) {
			throw new BizsException(e, UtilsExceptionCode.REFLECT_ERROR);
		}
		return method;
	}

	public static Method getFieldSetMethod(Class<?> clazz, Field field) {
		Method method = null;
		try {
			StringBuilder methodName = new StringBuilder("set");
			methodName.append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1));
			method = clazz.getMethod(methodName.toString(), field.getType());
		} catch (Exception e) {
			throw new BizsException(e, UtilsExceptionCode.REFLECT_ERROR);
		}
		return method;
	}

	public static Object getFieldValue(Field field, Object originObject) {
		try {
			return getFieldGetMethod(originObject.getClass(), field).invoke(originObject);
		} catch (Exception e) {
			throw new BizsException(e, UtilsExceptionCode.REFLECT_ERROR);
		}
	}

	public static void setFieldValue(Field field, Object originObject, Object newFieldValue) {
		try {
			getFieldSetMethod(originObject.getClass(), field).invoke(originObject, newFieldValue);
		} catch (Exception e) {
			throw new BizsException(e, UtilsExceptionCode.REFLECT_ERROR);
		}
	}

	public static <T> void setObjectFieldValuesBySelective(T targetObject, T sourceObject) {
		Object newFieldValue;
		Method getFieldValueMethod;
		try {
			for (Method method : targetObject.getClass().getMethods()) {
				if (method.getName().startsWith("set")) {
					getFieldValueMethod = sourceObject.getClass().getMethod(method.getName().replaceFirst("s", "g"),
							FIELD_GET_METHOD_PARM_TYPE);
					newFieldValue = getFieldValueMethod.invoke(sourceObject);
					if (newFieldValue == null) {
						continue;
					}
					if (LOG.isDebugEnabled()) {
						LOG.debug("call method " + method.getName() + " to set new value. the old value is "
								+ getFieldValueMethod.invoke(targetObject) + ", the new value is " + newFieldValue);
					}
					method.invoke(targetObject, newFieldValue);
				}
			}

		} catch (Exception e) {
			throw new BizsException(e, UtilsExceptionCode.REFLECT_ERROR);
		}
	}
	
	public static Field[] getAllFields(Class<?> clazz){
		Field[] array = new Field[]{};
		Set<Field> list = new HashSet<Field>();
		if(clazz==null){
			return new Field[]{};
		}
		else{
			Field[] fields = clazz.getDeclaredFields();
			Collections.addAll(list, fields);
			Collections.addAll(list, getAllFields(clazz.getSuperclass()));
			return list.toArray(array); 
		}
	}
	
}
