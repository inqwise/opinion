package com.inqwise.opinion.infrastructure.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlProc {

}
