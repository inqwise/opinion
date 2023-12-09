package com.inqwise.opinion.infrastructure.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex
 * @description Change null value to default
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Substitute {
	 SubstitudeStrategy strategy() default SubstitudeStrategy.NoneBut;
	 SubstitudeValue[] values() default {SubstitudeValue.Null};
}
