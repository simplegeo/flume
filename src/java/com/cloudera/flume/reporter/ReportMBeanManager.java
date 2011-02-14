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
package com.cloudera.flume.reporter;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportMBeanManager {
	
	  static final Logger LOG = LoggerFactory.getLogger(ReportMBeanManager.class);

	  final static ReportMBeanManager manager = new ReportMBeanManager();
	  
	  private Map<String, ReportableMBean> beans = new HashMap<String, ReportableMBean>();

	  public static ReportMBeanManager get() {
	    return manager;
	  }

	  public void putReports(Map<String, ReportEvent> reports) throws JMException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		for(String name : reports.keySet()) {
		  ReportableMBean bean = beans.get(name);
		  ReportEvent report = reports.get(name);
		  if(bean == null) {
		    bean = new ReportableMBean(name, report);
			mbs.registerMBean(bean, bean.getName());
			beans.put(name, bean);
		  } else
		    bean.setReport(report);
		}
	  }
	  
	  private class ReportableMBean implements DynamicMBean {

		private ReportEvent report;
		private String name;

		public ReportableMBean(String name, ReportEvent report) {
			this.name = name;
			LOG.info(name);
			setReport(report);
		}
		
		public void setReport(ReportEvent report) {
			this.report = report;
		}
		
		public ObjectName getName() throws MalformedObjectNameException, NullPointerException {
			return new ObjectName("com.cloudera.flume.reporter:type="+name);
		}
		
		@Override
		public Object getAttribute(String key)
				throws AttributeNotFoundException, MBeanException,
				ReflectionException {
			// We need to grab the entire mapping for each type
			// in order to create a thread-safe environment
			Object value = report.getAllDoubleMetrics().get(key);
			
			if(value == null)
			  value = report.getAllLongMetrics().get(key);
			
			if(value == null)
			  value = report.getAllStringMetrics().get(key);

			return value;
		}

		@Override
		public AttributeList getAttributes(String[] keys) {
			AttributeList list = new AttributeList();
			
			Map<String, Long> longMetrics = report.getAllLongMetrics();
			Map<String, String> stringMetrics = report.getAllStringMetrics();
			Map<String, Double> doubleMetrics = report.getAllDoubleMetrics(); 
			
			for(String key : keys) {
				Object value = null;
				value = doubleMetrics.get(key);

				if(value == null)
					value = stringMetrics.get(key);

				if(value == null)
					value = longMetrics.get(key);

				list.add(value);
			}

			return list;
		}

		@Override
		public MBeanInfo getMBeanInfo() {
			
			LinkedList<MBeanAttributeInfo> infoList = new LinkedList<MBeanAttributeInfo>();
			for(String key : report.getAllDoubleMetrics().keySet())
			  infoList.add(new MBeanAttributeInfo(key, Double.class.getName(), key + " for " + name, true, false, false));

			for(String key : report.getAllLongMetrics().keySet())
			  infoList.add(new MBeanAttributeInfo(key, Long.class.getName(), key + " for " + name, true, false, false));

			for(String key : report.getAllStringMetrics().keySet())
			  infoList.add(new MBeanAttributeInfo(key, String.class.getName(), key + " for " + name, true, false, false));
				
			return new MBeanInfo(Reportable.class.getName(), "A report for "+name, infoList.toArray(new MBeanAttributeInfo[]{}), null, null, null);
		}

		@Override
		public Object invoke(String arg0, Object[] arg1, String[] arg2)
				throws MBeanException, ReflectionException {
			return null;
		}

		@Override
		public void setAttribute(Attribute arg0)
				throws AttributeNotFoundException,
				InvalidAttributeValueException, MBeanException,
				ReflectionException {
			// We don't allowing setting an attribute just yet.
			throw new AttributeNotFoundException();
		}

		@Override
		public AttributeList setAttributes(AttributeList arg0) {			
			return null;
		}
	  }
}
