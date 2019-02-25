package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Queue;

public class MyInvocationHandler implements InvocationHandler {
    private Object target;
    public MyInvocationHandler(Object object){
        target = object;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before------");
        Object result = method.invoke(target, args);
        System.out.println("after-------");
        return result;
    }
    public Object getProxy(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

//    public static void main(String[] args) {
//        FooImpl foo = new FooImpl();
//        MyInvocationHandler handler = new MyInvocationHandler(foo);
//        Foo fooImpl = (Foo) handler.getProxy();
//        fooImpl.print();
//    }
}
