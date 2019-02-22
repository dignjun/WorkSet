package com.example.simple_factory;

public class FooImpl1 implements Foo {
    public void print() {
        System.out.println("Foo类型1");
    }

    @Override
    public String toString() {
        return "FooImpl1{}";
    }
}
