package com.hmblogs.backend.mode.factory;

/***
 * @Date(时间)2023-05-31
 * @Author heming
 *
 * 简单工厂模式类
 */
public class SimpleFactoryPattern {
    /**
     * 简单的工厂
     * @param name 需要创建对象的名称
     * @return
     */
    public static Phone create(String name) {
        //根据输入对象名称判断返回相匹配的对象
        if("IPhone".equals(name)) {
            //返回对象
            return new IPhone();
        }else if("MiPhone".equals(name)) {
            return new MiPhone();
        }else if("HuaweiPhone".equals(name)) {
            return new HuaweiPhone();
        }

        return null;
    }

}
