package com.k2j.bargains.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description: 手机号码的校验注解
 * @author: Sakura
 * @date: 4/6/20
 **/
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Documented
@Constraint(validatedBy = {IsMobileValidator.class}) // 这个注解的参数指定用于校验工作的是哪个类
public @interface IsMobile {

    /**
     * @description: 默认手机号码不可为空
     * @author: Sakura
     * @date: 4/6/20
     * @return: boolean
     **/
    boolean required() default true;

    /**
     * @description: 如果校验不通过时的提示信息
     * @author: Sakura
     * @date: 4/6/20
     * @return: java.lang.String
     **/
    String message() default "手机号码格式有误！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
