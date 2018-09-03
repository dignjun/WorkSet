package com.chinatvpay.type.hander;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class GenericTypeHandler<E extends MyObject> extends BaseTypeHandler<E> {
	@SuppressWarnings("unused")
	private Class<E> type;
	public GenericTypeHandler(Class<E> type) {
		this.type = type;
	}
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
	}
	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}
	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}
	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
}
