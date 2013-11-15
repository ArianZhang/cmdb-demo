package com.xbrother.common.dao.utils;

import java.lang.reflect.Field;
import java.util.Set;

import com.xbrother.common.entity.UUIDEntity;
import com.xbrother.common.utils.CollectionUtils;
import com.xbrother.common.utils.GUIDUtils;
import com.xbrother.common.utils.ReflectUtils;

public class UUIDSetterUtils {

	public static <T extends UUIDEntity> void generateUUIDAndSet(T entity){
		entity.setUid(GUIDUtils.generate());
		for(Field field : ReflectUtils.getAllFields(entity.getClass())){
			if(field.getType().equals(Set.class)){
				Set<UUIDEntity> subEntitys =  (Set<UUIDEntity>) ReflectUtils.getFieldValue(field, entity);
				if(!CollectionUtils.isEmpty(subEntitys)){
					for(UUIDEntity subEntity : subEntitys){
						generateUUIDAndSet(subEntity);
					}
				}
			}
		}
	}
}
