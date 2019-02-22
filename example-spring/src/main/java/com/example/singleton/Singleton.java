package com.example.singleton;

public class Singleton {
    private static volatile Singleton instance = null;
    private Singleton(){
    }
    public Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
