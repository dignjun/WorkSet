package com.example.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib代理是基于继承的
 */
public class ProxyFactory implements MethodInterceptor {
    private Object target;
    public ProxyFactory(Object target){
        this.target = target;
    }
    // 给目标对象创建一个代理对象
    public Object getProxyInstance(){
        // 工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(target.getClass());
        // 设置回调函数
        enhancer.setCallback(this);
        // 创建子类（代理对象）
        return enhancer.create();
    }
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before--------");
        // 执行目标对象的方法
        Object result = method.invoke(target, args);
        System.out.println("after---------");
        return result;
    }

    public static void main(String[] args) {
        ProxyFactory proxy = new ProxyFactory(new Foo());
        Foo foo = (Foo) proxy.getProxyInstance();
        foo.print();
    }
}
