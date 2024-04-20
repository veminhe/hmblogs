package com.hmblogs.backend.mode.factory;

/**
 * @author heming
 * @date 2024/5/31
 */
public class IPhone implements Phone {

    @Override
    public void call() {
        System.out.println("使用Iphone打电话");
    }
}
