package com.chinatvpay.object.factory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * 自己的对象工厂实现
 * 
 * @author DINGJUN
 *
 */
public class ExampleObjectFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = 2097991752364598114L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object create(Class type) {
		return super.create(type);
	}

//	public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
//		return super.create(type, constructorArgTypes, constructorArgs);
//	}
	
	@Override
	public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
		// TODO Auto-generated method stub
		return super.create(type, constructorArgTypes, constructorArgs);
	}


	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}


	public <T> boolean isCollection(Class<T> type) {
		return Collection.class.isAssignableFrom(type);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
