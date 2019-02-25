package com.example.proxy.statics;

public class FooStaticProxy implements Foo {
    private Foo foo;
    public FooStaticProxy(Foo foo){
        this.foo = foo;
    }
    public void print() {
        System.out.println("before-------");
        foo.print();
        System.out.println("after--------");
    }

    public static void main(String[] args) {
        FooStaticProxy proxy = new FooStaticProxy(new FooImpl());
        proxy.print();
    }
}
