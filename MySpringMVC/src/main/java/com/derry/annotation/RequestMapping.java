package com.derry.annotation;

import java.lang.annotation.*;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 17:17 2019/2/18
 * @Modified By:
 */
@Target({ElementType.METHOD,ElementType.TYPE})//表示在接口、类、方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    //表示url
    String value() default "";
}
