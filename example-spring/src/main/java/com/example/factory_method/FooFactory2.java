package com.example.factory_method;

public class FooFactory2 implements Factory {
    @Override
    public Foo getInstance() {
        return new FooImpl2();
    }
}
