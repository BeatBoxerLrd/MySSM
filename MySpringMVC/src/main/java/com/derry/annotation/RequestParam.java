package com.derry.annotation;

import java.lang.annotation.*;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 17:19 2019/2/18
 * @Modified By:
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value();
}
