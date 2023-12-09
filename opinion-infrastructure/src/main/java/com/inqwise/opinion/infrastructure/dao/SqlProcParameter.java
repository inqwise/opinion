package com.inqwise.opinion.infrastructure.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SqlProcParameter {
	String name() default StringUtils.EMPTY;
}
