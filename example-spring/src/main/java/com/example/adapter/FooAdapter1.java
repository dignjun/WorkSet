package com.example.adapter;

public class FooAdapter1 extends Foo implements Target{
    @Override
    public void println() {
        System.out.println("换行输出");
    }
}
