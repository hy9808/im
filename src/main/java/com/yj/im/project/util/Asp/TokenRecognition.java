package com.yj.im.project.util.Asp;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 登录需要的验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TokenRecognition {

    String value() default "";

}
