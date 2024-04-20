package com.hmblogs.backend.mode.factory;

/***
 * @Date(时间)2023-06-01
 * @Author heming
 */
public class Mains {
    public static void main(String[] args) {
        Phone phone = SimpleFactoryPattern.create("IPhone");
        phone.call();

        Phone phone2 = SimpleFactoryPattern.create("MiPhone");
        phone2.call();

        Phone phone3 = SimpleFactoryPattern.create("HuaweiPhone");
        phone3.call();
    }
}
