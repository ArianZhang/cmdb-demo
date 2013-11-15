/**
 * 
 */
package com.xbrother.common.query.utils;

import java.util.List;

import com.xbrother.common.entity.UUIDEntity;
import com.xbrother.common.query.Condition;
import com.xbrother.common.query.OrderBy;
import com.xbrother.common.utils.CollectionUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-8-3
 * @version 1.0
 */
public class QueryUtils {

	public static final String ALIAS = " entity";

	public static <T extends UUIDEntity> String dynamicWhereSql(List<Condition> conditions, Class<T> classOfEntity) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(classOfEntity.getName()).append(ALIAS);
		if (conditions != null) {
			Condition condition = null;
			for (int i = 0, j = conditions.size() - 1; i <= j; i++) {
				if (i == 0) {
					sql.append(" where ");
				}
				condition = conditions.get(i);
				if ("like".equals(condition.getSymbol())) {
					sql.append(ALIAS).append(".").append(condition.getName()).append(" ").append(condition.getSymbol())
							.append(" ").append("'%").append(condition.getValue()).append("%'");
				} else {
					sql.append(ALIAS).append(".").append(condition.getName()).append(" ").append(condition.getSymbol())
							.append(" ").append("'").append(condition.getValue()).append("'");
				}
				if (i < j) {
					sql.append(" and ");
				}
			}
		}
		return sql.toString();
	}

	public static String dynamicOrderBySqlParts(List<OrderBy> orderBies) {
		if (orderBies != null) {
			StringBuilder sb = new StringBuilder(" order by ");
			OrderBy orderBy = null;
			for (int i = 0, j = orderBies.size() - 1; i <= j; i++) {
				orderBy = orderBies.get(i);
				sb.append(ALIAS).append(".").append(orderBy.getKey()).append(" ").append(orderBy.getType()).append(" ");
				if (i < j) {
					sb.append(" , ");
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public static String convertToSqlInTips(String leftExpress, List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(leftExpress + " in (");
		for (Integer id : ids) {
			sb.append("'").append(id).append("'");
		}
		sb.append(")");
		return sb.toString();
	}
}
