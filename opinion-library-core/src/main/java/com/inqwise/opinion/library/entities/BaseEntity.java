package com.inqwise.opinion.library.entities;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public abstract class BaseEntity extends BaseOperationResult {
	static ApplicationLog logger = ApplicationLog.getLogger(BaseEntity.class);
	
	
}
