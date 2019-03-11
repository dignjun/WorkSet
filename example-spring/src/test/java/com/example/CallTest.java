package com.example;

public class CallTest {
    public static void main(String[] args) {
        A a = new A();
        B b = new B(a);
        a.setB(b);
        a.print(b);
    }
}

class A {
    private B b;
    public A(){}
    public A(B b){
        this.b = b;
    }
    public String print(B b){
        System.out.println(b.print(this));
        return "a";
    }
    public void setB(B b){
        this.b = b;
    }
}

class B {
    private A a;
    public B(){}
    public B(A a){
        this.a = a;
    }
    public String print(A a){
        System.out.println(a.print(this));
        return "b";
    }
}
