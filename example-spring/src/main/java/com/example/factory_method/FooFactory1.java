package com.example.factory_method;

public class FooFactory1 implements Factory {
    @Override
    public Foo getInstance() {
        return new FooImpl1();
    }
}
