package com.hmblogs.backend.mode.factory;


/**
 * @author heming
 * @date 2023/5/31
 */
public class MiPhone implements Phone {

    @Override
    public void call() {
        System.out.println("使用MiPhone打电话");
    }
}