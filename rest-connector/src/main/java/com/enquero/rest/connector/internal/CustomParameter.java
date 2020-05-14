package com.enquero.rest.connector.internal;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class CustomParameter {

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "bharath")
	private String firstName;

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "developer")
	private String job;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
