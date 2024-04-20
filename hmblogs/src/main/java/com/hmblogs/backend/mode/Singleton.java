package com.hmblogs.backend.mode;

public class Singleton {
    private Singleton() {
        // 私有构造方法
    }

    private static class SingletonHolder {
        private static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
}
