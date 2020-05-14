package com.enquero.rest.connector.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			response = getHttpResponse(con);
			LOGGER.info("payload:" + response);

		} catch (IOException e) {
			LOGGER.error("Error occured");
			e.printStackTrace();
		}

		return response;
	}

	@MediaType(value = ANY, strict = false)
	@Alias("postCall")
	public String postCall(@Config RESTConfiguration c, @ParameterGroup(name = "CustomParams") CustomParameter param) {

		String response = null;
		String protocol = c.getProtocol().equals("HTTPS") ? "https://" : "http://";

		try {
			URL url = new URL(protocol + c.getHost() + c.getBasePath());
			URLConnection con = url.openConnection();
			String jsonString = "{\"name\": \"" + param.getFirstName() + "\", \"job\":\"" + param.getJob() + "\"}";
			con.setDoOutput(true);
			con.addRequestProperty("User-Agent", "Chrome");

			if (c.getProtocol().equals("HTTPS")) {
				LOGGER.info("Processing HTTPS request");
				HttpsURLConnection https = (HttpsURLConnection) con;
				https.setRequestMethod("POST");
				https.setRequestProperty("Content-Type", "application/json; utf-8");
				try (OutputStream os = con.getOutputStream()) {
					byte[] input = jsonString.getBytes("utf-8");
					os.write(input, 0, input.length);
				}
				response = getHttpResponse(https);
			} else {
				LOGGER.info("Processing HTTP request");
				HttpURLConnection http = (HttpURLConnection) con;
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type", "application/json; utf-8");
				try (OutputStream os = con.getOutputStream()) {
					byte[] input = jsonString.getBytes("utf-8");
					os.write(input, 0, input.length);
				}
				response = getHttpResponse(http);
			}
			LOGGER.info("Response received.");

		} catch (Exception e) {
			LOGGER.error("Error occured");
			e.printStackTrace();
		}
		return response;

	}

	private String getHttpResponse(URLConnection con) throws UnsupportedEncodingException, IOException {
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
