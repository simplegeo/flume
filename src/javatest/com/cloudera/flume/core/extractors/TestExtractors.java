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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.TimeZone;

import org.junit.Test;

import com.cloudera.flume.core.Attributes;
import com.cloudera.flume.core.Event;
import com.cloudera.flume.core.EventImpl;
import com.cloudera.flume.handlers.debug.MemorySinkSource;

/**
 * Tests the behavior of the split and regex extractors.
 */
public class TestExtractors {

  @Test
  public void testRegexExtractor() throws IOException, InterruptedException {
    MemorySinkSource mem = new MemorySinkSource();
    mem.open();
    RegexExtractor re1 = new RegexExtractor(mem, "(\\d:\\d)", 1, "colon");
    RegexExtractor re2 = new RegexExtractor(re1, "(.+)oo(.+)", 1, "oo");
    RegexExtractor re3 = new RegexExtractor(re2, "(.+)oo(.+)", 0, "full");
    RegexExtractor re4 = new RegexExtractor(re3, "(blah)blabh", 3, "empty");
    RegexExtractor re = new RegexExtractor(re4, "(.+)oo(.+)", 3, "outofrange");

    re.open();
    re.append(new EventImpl("1:2:3.4foobar5".getBytes()));
    re.close();

    mem.close();
    mem.open();
    Event e1 = mem.next();
    assertEquals("1:2", Attributes.readString(e1, "colon"));
    assertEquals("1:2:3.4f", Attributes.readString(e1, "oo"));
    assertEquals("1:2:3.4foobar5", Attributes.readString(e1, "full"));
    assertEquals("", Attributes.readString(e1, "empty"));
    assertEquals("", Attributes.readString(e1, "outofrange"));
  }

  @Test
  public void testSplitExtractor() throws IOException, InterruptedException {
    MemorySinkSource mem = new MemorySinkSource();
    SplitExtractor re1 = new SplitExtractor(mem, "\\.", 1, "dot");
    SplitExtractor re2 = new SplitExtractor(re1, ":", 1, "colon");
    SplitExtractor re3 = new SplitExtractor(re2, "foobar", 1, "foobar");
    SplitExtractor re4 = new SplitExtractor(re3, "#", 1, "empty");
    SplitExtractor re5 = new SplitExtractor(re4, "foobar", 2, "outofrange");
    SplitExtractor re = new SplitExtractor(re5, "\\.|:|foobar", 3, "disj");

    re.open();
    re.append(new EventImpl("1:2:3.4foobar5".getBytes()));
    re.close();

    mem.close();
    mem.open();
    Event e1 = mem.next();
    assertEquals("4foobar5", Attributes.readString(e1, "dot"));
    assertEquals("2", Attributes.readString(e1, "colon"));
    assertEquals("5", Attributes.readString(e1, "foobar"));
    assertEquals("4", Attributes.readString(e1, "disj"));
    assertEquals("", Attributes.readString(e1, "empty"));
    assertEquals("", Attributes.readString(e1, "outofrange"));
  }

  @Test
  public void testDateExtractor() throws IOException, InterruptedException {
    // date gets when converted back assumes local time zone. This forces it to
    // the time zone expected by this test.
    TimeZone tz = TimeZone.getTimeZone("America/Denver");
    TimeZone.setDefault(tz);

    MemorySinkSource mem = new MemorySinkSource();
    EventImpl e = new EventImpl("Test Event 26/Jul/2010:11:48:05 -0600".getBytes());
    Attributes.setString(e, "date", "26/Jul/2010:11:48:05 -0600");
    Attributes.setDouble(e, "created", 1300750243);

    // Test Default flow
    DateExtractor d = new DateExtractor(mem, "date", "dd/MMM/yyyy:HH:mm:ss Z");
    // Test custom prefix
    DateExtractor d1 = new DateExtractor(d, "date", "dd/MMM/yyyy:HH:mm:ss Z", "test_");
    // Test custom prefix with no zero padding
    DateExtractor d2 = new DateExtractor(d1, "date", "dd/MMM/yyyy:HH:mm:ss Z", "test2_", false);
    // Test timestamp
    DateExtractor d3 = new DateExtractor(d2, "created", "timestamp", "t_");

    d3.open();
    d3.append(e);
    d3.close();

    mem.close();
    mem.open();
    Event e1 = mem.next();
    assertEquals("26", Attributes.readString(e1, "dateday"));
    assertEquals("07", Attributes.readString(e1, "datemonth"));
    assertEquals("2010", Attributes.readString(e1, "dateyear"));
    assertEquals("11", Attributes.readString(e1, "datehr"));
    assertEquals("48", Attributes.readString(e1, "datemin"));
    assertEquals("05", Attributes.readString(e1, "datesec"));

    assertEquals("26", Attributes.readString(e1, "test_day"));
    assertEquals("07", Attributes.readString(e1, "test_month"));
    assertEquals("2010", Attributes.readString(e1, "test_year"));
    assertEquals("11", Attributes.readString(e1, "test_hr"));
    assertEquals("48", Attributes.readString(e1, "test_min"));
    assertEquals("05", Attributes.readString(e1, "test_sec"));

    assertEquals("26", Attributes.readString(e1, "test2_day"));
    assertEquals("7", Attributes.readString(e1, "test2_month"));
    assertEquals("2010", Attributes.readString(e1, "test2_year"));
    assertEquals("11", Attributes.readString(e1, "test2_hr"));
    assertEquals("48", Attributes.readString(e1, "test2_min"));
    assertEquals("5", Attributes.readString(e1, "test2_sec"));

    assertEquals("21", Attributes.readString(e1, "t_day"));
    assertEquals("03", Attributes.readString(e1, "t_month"));
    assertEquals("2011", Attributes.readString(e1, "t_year"));
    assertEquals("17", Attributes.readString(e1, "t_hr"));
    assertEquals("30", Attributes.readString(e1, "t_min"));
    assertEquals("43", Attributes.readString(e1, "t_sec"));
  }

  @Test
  public void testJSONExtractor() throws IOException, InterruptedException {
	  MemorySinkSource mem = new MemorySinkSource();
	  EventImpl e = new EventImpl("{\"service\":\"money\", \"number\": 0, \"dontdoit\": \"okay\", \"long\": 10000, \"double\": 12.1112, \"boolean\": true}".getBytes());

      //Test Default flow
      JSONExtractor j1 = new JSONExtractor(mem, new String[]{"service"});
      JSONExtractor j2 = new JSONExtractor(j1, new String[]{"number"});
      JSONExtractor j3 = new JSONExtractor(j2, new String[]{"double", "boolean"});

      j3.open();
      j3.append(e);
      j3.close();

      mem.close();
      mem.open();

      Event e1 = mem.next();
      assertEquals("money", Attributes.readString(e1, "service"));
      assertEquals(0, Attributes.readInt(e1, "number").intValue());
      assertEquals(1, Attributes.readInt(e1, "boolean").intValue());
      assertTrue(12.1112 == Attributes.readDouble(e1, "double").doubleValue());
      assertNull(Attributes.readString(e1, "dontdoit"));
    }
}