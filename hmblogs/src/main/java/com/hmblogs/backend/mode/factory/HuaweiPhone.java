package com.hmblogs.backend.mode.factory;


/**
 * @author heming
 * @date 2024/5/31
 */
public class HuaweiPhone implements Phone {

    @Override
    public void call() {
        System.out.println("使用华为手机打电话");
    }
}
