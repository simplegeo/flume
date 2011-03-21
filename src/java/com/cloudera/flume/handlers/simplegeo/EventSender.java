package com.cloudera.flume.handlers.simplegeo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.core.Event;

public class EventSender {
	public static final Logger LOG = LoggerFactory.getLogger(EventSender.class);
	private HttpClient client;
	
	private HttpHost host;
	
	public EventSender(String host, int port) {
		this.client = new DefaultHttpClient();
		this.host = new HttpHost(host, port);
	}

	public void sendEvent(Event event) {
		JSONObject jsonObject = new JSONObject(event.getBody()); 
		String body;
		try {
			body = jsonObject.getString("body");
			// Make sure we have a well formed JSON object
			body.replaceAll("\\+\"", "\"");
			jsonObject = new JSONObject(body);
			sendJSON(jsonObject);
		} catch (JSONException e) {
			LOG.warn("Unable to parse JSON event body", e);
		}
	}
	
	public void sendJSON(JSONObject jsonObject) throws JSONException {
		BasicHttpRequest request = new BasicHttpRequest(getMethod(jsonObject), getUri(jsonObject));
		if(request != null) {
			Map<String, String> headers = getHeaders(jsonObject);
			if(headers != null)
				for(String name : headers.keySet())
					request.setHeader(name, headers.get(name));
				
			Map<String, String> arguments = getArguments(jsonObject);
			if(arguments != null) {
				HttpParams params = new BasicHttpParams();
				for(String name : arguments.keySet())
					params.setParameter(name, arguments.get(name));
					
				request.setParams(params);
			}
				
			try {
				this.client.execute(host, request);
			} catch (ClientProtocolException e) {
				LOG.warn("Connection error", e);
			} catch (IOException e) {
				LOG.warn("Connection error", e);
			}
		} else {
			LOG.info("Unable to create request for JSON event " +jsonObject.toString());
		}			
	}
	
	private Map<String, String> getHeaders(JSONObject jsonObject) throws JSONException {
		return getMap(jsonObject, "headers");
	}
	
	private Map<String, String> getArguments(JSONObject jsonObject) throws JSONException {
		return getMap(jsonObject, "arguments");
	}
	
	private Map<String, String> getMap(JSONObject jsonObject, String keyName) throws JSONException {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<String> keyIterator = (Iterator<String>)jsonObject.keys();
		while(!keyIterator.hasNext()) {
			String key = keyIterator.next();
			String[] components = key.split(":");
			if(components.length > 1 && components[0].equals(keyName)) {
				map.put(components[1], jsonObject.getString(key));
			}
		}

		return map;
	}
	
	private String getUri(JSONObject jsonObject) throws JSONException {
		return jsonObject.getString("path");
	}
	
	private String getMethod(JSONObject jsonObject) throws JSONException {
		return jsonObject.getString("method");
	}
}
