package com.cloudera.flume.handlers.simplegeo;

import java.io.IOException;

import com.cloudera.flume.conf.Context;
import com.cloudera.flume.conf.SinkFactory.SinkDecoBuilder;
import com.cloudera.flume.core.Event;
import com.cloudera.flume.core.EventSink;
import com.cloudera.flume.core.EventSinkDecorator;
import com.google.common.base.Preconditions;

public class SimpleGeoReplayDecorator<S extends EventSink> extends EventSinkDecorator<S> {
	private EventSender sender;;
	
	public SimpleGeoReplayDecorator(S s, String host, int port) {
		super(s);
		this.sender = new EventSender(host, port);
	}

	@Override
	public void append(Event event) throws IOException {
		// Send the event off to SimpleGeo
		sender.sendEvent(event);
		super.append(event);
	}

	public static SinkDecoBuilder builder() {
	    return new SinkDecoBuilder() {
	      @Override
	      public EventSinkDecorator<EventSink> build(Context context, String... args) {
	          Preconditions.checkArgument(args.length >= 1,
	            "usage: usage: simplegeoReplay(hostname, [port])");

	        String host = args[0];
	        int port = 80;	        
		    if (args.length > 1)
	          port = Integer.parseInt(args[1]);

	        return new SimpleGeoReplayDecorator<EventSink>(null, host, port);
	      }
	    };
	}
}
