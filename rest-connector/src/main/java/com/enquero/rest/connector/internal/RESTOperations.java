package com.enquero.rest.connector.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class RESTOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(RESTOperations.class);

	@MediaType(value = ANY, strict = false)
	@Alias("getCall")
	public String getCall(@Config RESTConfiguration config) {

		LOGGER.info("Sending a get request....");

		String response = null;
		String protocol = config.protocol.equals("HTTPS") ? "https://" : "http://";
		try {
			URL url = new URL(protocol + config.getHost() + config.getBasePath());
			LOGGER.info("URL:" + url);
			URLConnection con = url.openConnection();
			con.addRequestProperty("User-Agent", "Chrome");
			LOGGER.info("Response recieved.........");
			response = getHTTPResponse(con);
			LOGGER.info("payload:" + response);
			
		} catch (IOException e) {
			LOGGER.error("Error occured");
			e.printStackTrace();
		}

		return response;
	}

	private String getHTTPResponse(URLConnection con) throws UnsupportedEncodingException, IOException {
		StringBuilder response = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
		}
		return response.toString();
	}
}
