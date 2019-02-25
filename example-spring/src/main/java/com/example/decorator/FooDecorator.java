package com.example.decorator;

/**
 * 抽象的装饰者
 */
public abstract class FooDecorator implements Foo{
    protected Foo foo;
    public FooDecorator(Foo foo){
        this.foo = foo;
    }
    public void print(){
        foo.print();
    }
}
