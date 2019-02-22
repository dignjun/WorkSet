package com.example.simple_factory;

public class FooImpl2 implements Foo {
    public void print() {
        System.out.println("Foo类型2");
    }
    @Override
    public String toString() {
        return "FooImpl2{}";
    }
}
