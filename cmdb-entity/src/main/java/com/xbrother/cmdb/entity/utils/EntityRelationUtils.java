package com.xbrother.cmdb.entity.utils;

import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.cmdb.entity.ConfigurationItemAttr;
import com.xbrother.cmdb.entity.ConfigurationItemSpot;
import com.xbrother.cmdb.entity.ConfigurationItemSpotSegment;
import com.xbrother.common.utils.CollectionUtils;

public class EntityRelationUtils {
	/**
	 * 初始化CI与属性的关系
	 * @param ci
	 */
	public static void initialCIRelation(ConfigurationItem ci) {
		if (!CollectionUtils.isEmpty(ci.getAttributes())) {
			for (ConfigurationItemAttr attr : ci.getAttributes()) {
				attr.setCi(ci);
			}
		}
		if (!CollectionUtils.isEmpty(ci.getMonitorSpots())) {
			for (ConfigurationItemSpot spot : ci.getMonitorSpots()) {
				spot.setCi(ci);
				if (!CollectionUtils.isEmpty(spot.getThresholdSegments())) {
					for (ConfigurationItemSpotSegment segment : spot.getThresholdSegments()) {
						segment.setSpot(spot);
					}
				}
			}
		}
	}
}
