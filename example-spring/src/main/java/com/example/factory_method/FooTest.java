package com.example.factory_method;

public class FooTest {
    public static void main(String[] args) {
        Factory f1 = new FooFactory1();
        Foo i1 = f1.getInstance();
        i1.print();

        Factory f2 = new FooFactory2();
        Foo i2 = f2.getInstance();
        i2.print();

        Factory f3 = new FooFactory3();
        Foo i3 = f3.getInstance();
        i3.print();
    }
}
