package com.example.simple_factory;

public class FooTest {
    public static void main(String[] args) {
        System.out.println(FooFactory.getInstance(1));
        System.out.println(FooFactory.getInstance(2));
    }
}
