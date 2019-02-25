package com.example.decorator;

public class FooDecoratorImpl extends FooDecorator {
    private Foo foo;
    public FooDecoratorImpl(Foo foo) {
        super(foo);
    }

    public void print(){
        super.print();
        println();
    }
    public void println(){
        System.out.print("\r\n");
        System.out.println("this is decorator impl test.");
    }

    public static void main(String[] args) {
        FooDecoratorImpl fooDecorator = new FooDecoratorImpl(new FooImpl());
        fooDecorator.print();
    }
}
