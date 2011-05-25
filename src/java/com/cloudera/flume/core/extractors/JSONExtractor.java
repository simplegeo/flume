/**
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.flume.core.extractors;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.conf.Context;
import com.cloudera.flume.conf.SinkFactory.SinkDecoBuilder;
import com.cloudera.flume.core.Attributes;
import com.cloudera.flume.core.Event;
import com.cloudera.flume.core.EventSink;
import com.cloudera.flume.core.EventSinkDecorator;
import com.google.common.base.Preconditions;

/**
 * This data extractor assumes that the body of the event is a JSON string. In the
 * case that it is a valid JSON string, the extractor will assign JSON key/values
 * to the event itself.
 * 
 * @author Derek Smith (derek@simplegeo.com)
 */
public class JSONExtractor extends EventSinkDecorator<EventSink> {
  private static final Logger LOG = LoggerFactory.getLogger(JSONExtractor.class);
 
  final String[] jsonKeys;

  public JSONExtractor(EventSink snk, String[] jsonKeys) {
    super(snk);
    this.jsonKeys = jsonKeys;
  }

  @Override
  public void append(Event event) throws IOException, InterruptedException {
    String s = new String(event.getBody());
    try {
		JSONObject jsonObject = new JSONObject(s);
		for(String jsonKey : jsonKeys) {
			try {
				Object obj = jsonObject.get(jsonKey);
				if(obj instanceof Double) {
					Attributes.setDouble(event, jsonKey, ((Double)obj).doubleValue());
				} else if(obj instanceof Integer) {
					Attributes.setInt(event, jsonKey, ((Integer)obj).intValue());
				} else if(obj instanceof String) {
					Attributes.setString(event, jsonKey, (String)obj);
				} else if(obj instanceof Boolean) {
					Attributes.setInt(event, jsonKey, ((Boolean)obj).booleanValue() ? 1 : 0);
				} else if(obj instanceof Long) {
					Attributes.setLong(event, jsonKey, ((Long)obj));
				} else {
					LOG.warn("unknown type for "+obj);
				}
			} catch (JSONException e) {
				// Ignore the JSON exception for now
				// since we don't know what to do with missing keys.
			}
		}
		
	} catch (JSONException e) {
		LOG.error("unable to parse JSON from the event body", e);
		LOG.error(s);
	}
    
    super.append(event);
  }

  public static SinkDecoBuilder builder() {
    return new SinkDecoBuilder() {
      @Override
      public EventSinkDecorator<EventSink> build(Context context,
          String... argv) {
        Preconditions.checkArgument(argv.length >= 1, "usage: json(jsonKey [jsonKey1, jsonKey2, ...])");

        return new JSONExtractor(null, argv);
      }
    };
  }
}
