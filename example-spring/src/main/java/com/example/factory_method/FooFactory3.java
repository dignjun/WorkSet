package com.example.factory_method;

public class FooFactory3 implements Factory {
    @Override
    public Foo getInstance() {
        return new FooImpl3();
    }
}
