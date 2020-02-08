package org.evanframework.rabbitmq.common.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.common.util.FastJsonConvertUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author BuFanxueyuan
 * @since 2018年5月15日 下午2:35:54
 */
public class MessageJsonTypeHandler extends BaseTypeHandler<RabbitMessage> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RabbitMessage parameter,
            JdbcType jdbcType) throws SQLException {

        ps.setString(i, FastJsonConvertUtil.convertObjectToJSON(parameter));
    }

    @Override
    public RabbitMessage getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
    	String value = rs.getString(columnName);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnName), RabbitMessage.class);
    	}
    	return null;
    }

    @Override
    public RabbitMessage getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    	String value = rs.getString(columnIndex);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnIndex), RabbitMessage.class);
    	}
    	return null;
    }

    @Override
    public RabbitMessage getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    	String value = cs.getString(columnIndex);
    	if(null != value && !StringUtils.isBlank(value)) {
    		return FastJsonConvertUtil.convertJSONToObject(cs.getString(columnIndex), RabbitMessage.class);
    	}
    	return null;   
    }

}