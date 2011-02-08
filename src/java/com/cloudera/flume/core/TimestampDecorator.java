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
package com.cloudera.flume.core;

import java.io.IOException;
import java.util.Map.Entry;

import com.cloudera.flume.conf.Context;
import com.cloudera.flume.conf.SinkFactory.SinkDecoBuilder;
import com.google.common.base.Preconditions;

public class TimestampDecorator<S extends EventSink> extends
    EventSinkDecorator<S> {

  final String attr;
  final boolean milli;

  public TimestampDecorator(String attr, boolean milli) {
    super(null);
    Preconditions.checkNotNull(attr);
    this.attr = attr;
    this.milli = milli;
  }

  public void append(Event e) throws IOException {
    Preconditions.checkNotNull(e);
    long timestamp = Long.parseLong(new String(e.get(attr)));
    if( !milli )
    	timestamp *= 1000;

    Event e2 = new EventImpl(e.getBody(), timestamp, e.getPriority(), e.getNanos(), e.getHost());
    
    // Update known attributes
    for (Entry<String, byte[]> entry : e.getAttrs().entrySet()) {
      e2.set(entry.getKey(), entry.getValue());
    }

    super.append(e2);
  }

  public static SinkDecoBuilder builder() {
    return new SinkDecoBuilder() {
      @Override
      public EventSinkDecorator<EventSink> build(Context context,
          String... argv) {
        Preconditions.checkArgument(argv.length >= 1, "usage: timestamp(attr [milli])");
        boolean milli = false;
        if(argv.length > 1){
          milli = argv[1].equals("true");
        }

        return new TimestampDecorator<EventSink>(argv[0], milli);
      }
    };
  }
}
