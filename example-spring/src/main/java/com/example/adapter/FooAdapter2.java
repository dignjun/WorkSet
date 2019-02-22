package com.example.adapter;

public class FooAdapter2 {
    private Foo foo;
    public FooAdapter2(Foo foo){
        this.foo = foo;
    }
    public void print(){
        foo.print();
    }
    public void println(){
        System.out.println("换行输出");
    }
}
