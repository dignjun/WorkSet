package com.chinatvpay.type.hander;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
/**
 * 自定义的类型处理器
 * 使用这个的类型处理器将会覆盖已经存在的处理 Java 的 String 类型属性和 VARCHAR 参数及结果的类型处理器。
 * 要注意 MyBatis 不会窥探数据库元信息来决定使用哪种类型，所以你必须在参数和结果映射中指明那是 VARCHAR 类型的字段， 以使其能够绑定到正确的类型处理器上。
 * 这是因为：MyBatis 直到语句被执行才清楚数据类型。
 * 
 * @author DINGJUN
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)//指定与其关联的jdbc类型列表配置文件中指定数据类型也是可以的，同时指定，注解方式将被忽略
//@MappedTypes(value=String.class)//指定与其关联的java类型列表，如果同时在配置文件中的typeHandler元素的javaType属性上也指定，此时注解是不起作用的。
public class MyTypeHandler extends BaseTypeHandler<String> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter);
	}
	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}
}
