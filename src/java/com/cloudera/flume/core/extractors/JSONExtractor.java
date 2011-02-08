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

public class JSONExtractor extends EventSinkDecorator<EventSink> {
  private static final Logger LOG = LoggerFactory.getLogger(JSONExtractor.class);
  final String attr;
  final String key;

  public JSONExtractor(EventSink snk, String key, String attr) {
    super(snk);
    this.attr = attr;
    this.key = key;
  }

  @Override
  public void append(Event event) throws IOException {
    String s = new String(event.getBody());
    try {
		JSONObject jsonObject = new JSONObject(s);
		String val = jsonObject.getString(this.key);
		Attributes.setString(event, attr, val);
	} catch (JSONException e) {
		LOG.error("unable to parse JSON from the event body", e);
	}
    
    super.append(event);
  }

  public static SinkDecoBuilder builder() {
    return new SinkDecoBuilder() {
      @Override
      public EventSinkDecorator<EventSink> build(Context context,
          String... argv) {
        Preconditions.checkArgument(argv.length == 2,
            "usage: json(jsonKey, dstAttr)");

        String jsonKey = argv[0];
        String attr = argv[1];

        EventSinkDecorator<EventSink> snk = new JSONExtractor(null, jsonKey, attr);
        return snk;
      }
    };
  }
}
