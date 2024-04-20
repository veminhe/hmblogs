package com.hmblogs.backend.mode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 实现InvocationHandler接口
class MyInvocationHandler implements InvocationHandler {
    // 声明一个私有变量
    private Object target;

    // 构造函数
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    //  实现InvocationHandler接口的invoke方法，该方法在代理对象调用方法时被触发。
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理前置操作");
        Object result = method.invoke(target, args);
        System.out.println("动态代理后置操作");
        return result;
    }
}


