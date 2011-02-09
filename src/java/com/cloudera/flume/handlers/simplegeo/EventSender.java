package com.cloudera.flume.handlers.simplegeo;

import java.io.IOException;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.core.Event;
import com.simplegeo.client.AbstractSimpleGeoClient;
import com.simplegeo.client.callbacks.SimpleGeoCallback;
import com.simplegeo.client.handler.SimpleGeoJSONHandler;

public class EventSender {
	public static final Logger LOG = LoggerFactory.getLogger(EventSender.class);
	private StupidlySimpleGeoClient client;
	
	public EventSender(String host, int port) {
		this.client = new StupidlySimpleGeoClient(host, port);
	}
	
	public void sendEvent(Event event) {
		// Send the SimpleGeo request here.
		JSONObject jsonObject = new JSONObject(event.getBody());
		
		try {
			String body = jsonObject.getString("body");
			// Make sure we have a well formed JSON object
			body.replaceAll("\\+\"", "\"");
			jsonObject = new JSONObject(body);
			
			String path = jsonObject.getString("path");
			String method = jsonObject.getString("method");
			String oauthKey = jsonObject.getString("oauth_consumer_key");

			this.client.getHttpClient().setToken(oauthKey, oauthKey);
			HttpUriRequest request = null;
			if(method.equals("GET")) {
				
			} else if(method.equals("PUT")) {
				
			} else if(method.equals("DELETE")) {
				
			} else if(method.equals("POST")) {
				
			}
			
		} catch (JSONException e) {
			LOG.warn("Unable to parse JSON event body", e);
		}		
	}
	
	private class StupidlySimpleGeoClient extends AbstractSimpleGeoClient {

		public StupidlySimpleGeoClient(String host, int port) {
			super(host, ""+port, "dontmatter");
		}
		
		@Override
		protected Object executeDelete(String uri, SimpleGeoJSONHandler handler)
				throws IOException {
			;
			return null;
		}

		@Override
		protected void executeDelete(String uri, SimpleGeoJSONHandler handler,
				SimpleGeoCallback callback) throws IOException {
			;
		}

		@Override
		protected Object executeGet(String uri, SimpleGeoJSONHandler handler)
				throws IOException {
			;
			return null;
		}

		@Override
		protected void executeGet(String uri, SimpleGeoJSONHandler handler,
				SimpleGeoCallback callback) throws IOException {
			;
		}

		@Override
		protected Object executePost(String uri, String jsonPayload,
				SimpleGeoJSONHandler handler) throws IOException {
			return null;
		}

		@Override
		protected void executePost(String uri, String jsonPayload,
				SimpleGeoJSONHandler handler, SimpleGeoCallback callback)
				throws IOException {
			;
		}

		@Override
		protected Object executePut(String uri, String jsonPayload,
				SimpleGeoJSONHandler handler) throws IOException {
			;
			return null;
		}

		@Override
		protected void executePut(String uri, String jsonPayload,
				SimpleGeoJSONHandler handler, SimpleGeoCallback callback)
				throws IOException {
			;
		}
		
	}
}
