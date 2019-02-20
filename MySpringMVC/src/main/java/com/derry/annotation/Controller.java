package com.derry.annotation;

import java.lang.annotation.*;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 17:14 2019/2/18
 * @Modified By:
 */

@Target(ElementType.TYPE)//表示在接口、类、枚举、注解上
@Retention(RetentionPolicy.RUNTIME)//表示注解存在于class字节码文件中，在运行时可以通过反射获取到
@Documented
public @interface Controller {
    String value() default "";
}
