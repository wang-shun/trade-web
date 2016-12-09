package com.centaline.parportal.mobile.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * loginController refreshToken ignore TokenAutoUpdateAop
 * 刷新token的时候忽略TokenAutoUpdateAop,直接返回
 * @author yumin3
 * @date 2016-09-22
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RefreshTokenAutoAop {

}
