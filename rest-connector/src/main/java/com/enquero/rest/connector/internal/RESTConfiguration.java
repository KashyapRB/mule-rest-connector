package com.enquero.rest.connector.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.values.OfValues;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across multiple operations since they represent something
 * core from the extension.
 */
@Operations(RESTOperations.class)
@ConnectionProviders(RESTConnectionProvider.class)
public class RESTConfiguration {

	@Parameter
	@OfValues(ProtocolProvider.class)
	public String protocol;

	@Parameter
	public String host;

	@Parameter
	public String basePath;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
