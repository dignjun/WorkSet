package com.example.simple_factory;

public class FooFactory {
    public static Foo getInstance(int type){
        Foo foo = null;
        switch(type) {
            case 1: foo = new FooImpl1();
            break;
            case 2: foo = new FooImpl2();
            break;
            default:
                System.out.println("未正确指定类型");
        }
        return foo;
    }
}
