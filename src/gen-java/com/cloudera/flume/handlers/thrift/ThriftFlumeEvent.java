/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.cloudera.flume.handlers.thrift;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

public class ThriftFlumeEvent implements TBase<ThriftFlumeEvent._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ThriftFlumeEvent");

  private static final TField TIMESTAMP_FIELD_DESC = new TField("timestamp", TType.I64, (short)1);
  private static final TField PRIORITY_FIELD_DESC = new TField("priority", TType.I32, (short)2);
  private static final TField BODY_FIELD_DESC = new TField("body", TType.STRING, (short)3);
  private static final TField NANOS_FIELD_DESC = new TField("nanos", TType.I64, (short)4);
  private static final TField HOST_FIELD_DESC = new TField("host", TType.STRING, (short)5);
  private static final TField FIELDS_FIELD_DESC = new TField("fields", TType.MAP, (short)6);

  public long timestamp;
  /**
   * 
   * @see Priority
   */
  public Priority priority;
  public byte[] body;
  public long nanos;
  public String host;
  public Map<String,byte[]> fields;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    TIMESTAMP((short)1, "timestamp"),
    /**
     * 
     * @see Priority
     */
    PRIORITY((short)2, "priority"),
    BODY((short)3, "body"),
    NANOS((short)4, "nanos"),
    HOST((short)5, "host"),
    FIELDS((short)6, "fields");

    private static final Map<Integer, _Fields> byId = new HashMap<Integer, _Fields>();
    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byId.put((int)field._thriftId, field);
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      return byId.get(fieldId);
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TIMESTAMP_ISSET_ID = 0;
  private static final int __NANOS_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new EnumMap<_Fields, FieldMetaData>(_Fields.class) {{
    put(_Fields.TIMESTAMP, new FieldMetaData("timestamp", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I64)));
    put(_Fields.PRIORITY, new FieldMetaData("priority", TFieldRequirementType.DEFAULT, 
        new EnumMetaData(TType.ENUM, Priority.class)));
    put(_Fields.BODY, new FieldMetaData("body", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    put(_Fields.NANOS, new FieldMetaData("nanos", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I64)));
    put(_Fields.HOST, new FieldMetaData("host", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    put(_Fields.FIELDS, new FieldMetaData("fields", TFieldRequirementType.DEFAULT, 
        new MapMetaData(TType.MAP, 
            new FieldValueMetaData(TType.STRING), 
            new FieldValueMetaData(TType.STRING))));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(ThriftFlumeEvent.class, metaDataMap);
  }

  public ThriftFlumeEvent() {
  }

  public ThriftFlumeEvent(
    long timestamp,
    Priority priority,
    byte[] body,
    long nanos,
    String host,
    Map<String,byte[]> fields)
  {
    this();
    this.timestamp = timestamp;
    setTimestampIsSet(true);
    this.priority = priority;
    this.body = body;
    this.nanos = nanos;
    setNanosIsSet(true);
    this.host = host;
    this.fields = fields;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThriftFlumeEvent(ThriftFlumeEvent other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.timestamp = other.timestamp;
    if (other.isSetPriority()) {
      this.priority = other.priority;
    }
    if (other.isSetBody()) {
      this.body = new byte[other.body.length];
      System.arraycopy(other.body, 0, body, 0, other.body.length);
    }
    this.nanos = other.nanos;
    if (other.isSetHost()) {
      this.host = other.host;
    }
    if (other.isSetFields()) {
      Map<String,byte[]> __this__fields = new HashMap<String,byte[]>();
      for (Map.Entry<String, byte[]> other_element : other.fields.entrySet()) {

        String other_element_key = other_element.getKey();
        byte[] other_element_value = other_element.getValue();

        String __this__fields_copy_key = other_element_key;

        byte[] __this__fields_copy_value = new byte[other_element_value.length];
        System.arraycopy(other_element_value, 0, __this__fields_copy_value, 0, other_element_value.length);

        __this__fields.put(__this__fields_copy_key, __this__fields_copy_value);
      }
      this.fields = __this__fields;
    }
  }

  public ThriftFlumeEvent deepCopy() {
    return new ThriftFlumeEvent(this);
  }

  @Deprecated
  public ThriftFlumeEvent clone() {
    return new ThriftFlumeEvent(this);
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public ThriftFlumeEvent setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    setTimestampIsSet(true);
    return this;
  }

  public void unsetTimestamp() {
    __isset_bit_vector.clear(__TIMESTAMP_ISSET_ID);
  }

  /** Returns true if field timestamp is set (has been asigned a value) and false otherwise */
  public boolean isSetTimestamp() {
    return __isset_bit_vector.get(__TIMESTAMP_ISSET_ID);
  }

  public void setTimestampIsSet(boolean value) {
    __isset_bit_vector.set(__TIMESTAMP_ISSET_ID, value);
  }

  /**
   * 
   * @see Priority
   */
  public Priority getPriority() {
    return this.priority;
  }

  /**
   * 
   * @see Priority
   */
  public ThriftFlumeEvent setPriority(Priority priority) {
    this.priority = priority;
    return this;
  }

  public void unsetPriority() {
    this.priority = null;
  }

  /** Returns true if field priority is set (has been asigned a value) and false otherwise */
  public boolean isSetPriority() {
    return this.priority != null;
  }

  public void setPriorityIsSet(boolean value) {
    if (!value) {
      this.priority = null;
    }
  }

  public byte[] getBody() {
    return this.body;
  }

  public ThriftFlumeEvent setBody(byte[] body) {
    this.body = body;
    return this;
  }

  public void unsetBody() {
    this.body = null;
  }

  /** Returns true if field body is set (has been asigned a value) and false otherwise */
  public boolean isSetBody() {
    return this.body != null;
  }

  public void setBodyIsSet(boolean value) {
    if (!value) {
      this.body = null;
    }
  }

  public long getNanos() {
    return this.nanos;
  }

  public ThriftFlumeEvent setNanos(long nanos) {
    this.nanos = nanos;
    setNanosIsSet(true);
    return this;
  }

  public void unsetNanos() {
    __isset_bit_vector.clear(__NANOS_ISSET_ID);
  }

  /** Returns true if field nanos is set (has been asigned a value) and false otherwise */
  public boolean isSetNanos() {
    return __isset_bit_vector.get(__NANOS_ISSET_ID);
  }

  public void setNanosIsSet(boolean value) {
    __isset_bit_vector.set(__NANOS_ISSET_ID, value);
  }

  public String getHost() {
    return this.host;
  }

  public ThriftFlumeEvent setHost(String host) {
    this.host = host;
    return this;
  }

  public void unsetHost() {
    this.host = null;
  }

  /** Returns true if field host is set (has been asigned a value) and false otherwise */
  public boolean isSetHost() {
    return this.host != null;
  }

  public void setHostIsSet(boolean value) {
    if (!value) {
      this.host = null;
    }
  }

  public int getFieldsSize() {
    return (this.fields == null) ? 0 : this.fields.size();
  }

  public void putToFields(String key, byte[] val) {
    if (this.fields == null) {
      this.fields = new HashMap<String,byte[]>();
    }
    this.fields.put(key, val);
  }

  public Map<String,byte[]> getFields() {
    return this.fields;
  }

  public ThriftFlumeEvent setFields(Map<String,byte[]> fields) {
    this.fields = fields;
    return this;
  }

  public void unsetFields() {
    this.fields = null;
  }

  /** Returns true if field fields is set (has been asigned a value) and false otherwise */
  public boolean isSetFields() {
    return this.fields != null;
  }

  public void setFieldsIsSet(boolean value) {
    if (!value) {
      this.fields = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TIMESTAMP:
      if (value == null) {
        unsetTimestamp();
      } else {
        setTimestamp((Long)value);
      }
      break;

    case PRIORITY:
      if (value == null) {
        unsetPriority();
      } else {
        setPriority((Priority)value);
      }
      break;

    case BODY:
      if (value == null) {
        unsetBody();
      } else {
        setBody((byte[])value);
      }
      break;

    case NANOS:
      if (value == null) {
        unsetNanos();
      } else {
        setNanos((Long)value);
      }
      break;

    case HOST:
      if (value == null) {
        unsetHost();
      } else {
        setHost((String)value);
      }
      break;

    case FIELDS:
      if (value == null) {
        unsetFields();
      } else {
        setFields((Map<String,byte[]>)value);
      }
      break;

    }
  }

  public void setFieldValue(int fieldID, Object value) {
    setFieldValue(_Fields.findByThriftIdOrThrow(fieldID), value);
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TIMESTAMP:
      return new Long(getTimestamp());

    case PRIORITY:
      return getPriority();

    case BODY:
      return getBody();

    case NANOS:
      return new Long(getNanos());

    case HOST:
      return getHost();

    case FIELDS:
      return getFields();

    }
    throw new IllegalStateException();
  }

  public Object getFieldValue(int fieldId) {
    return getFieldValue(_Fields.findByThriftIdOrThrow(fieldId));
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case TIMESTAMP:
      return isSetTimestamp();
    case PRIORITY:
      return isSetPriority();
    case BODY:
      return isSetBody();
    case NANOS:
      return isSetNanos();
    case HOST:
      return isSetHost();
    case FIELDS:
      return isSetFields();
    }
    throw new IllegalStateException();
  }

  public boolean isSet(int fieldID) {
    return isSet(_Fields.findByThriftIdOrThrow(fieldID));
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThriftFlumeEvent)
      return this.equals((ThriftFlumeEvent)that);
    return false;
  }

  public boolean equals(ThriftFlumeEvent that) {
    if (that == null)
      return false;

    boolean this_present_timestamp = true;
    boolean that_present_timestamp = true;
    if (this_present_timestamp || that_present_timestamp) {
      if (!(this_present_timestamp && that_present_timestamp))
        return false;
      if (this.timestamp != that.timestamp)
        return false;
    }

    boolean this_present_priority = true && this.isSetPriority();
    boolean that_present_priority = true && that.isSetPriority();
    if (this_present_priority || that_present_priority) {
      if (!(this_present_priority && that_present_priority))
        return false;
      if (!this.priority.equals(that.priority))
        return false;
    }

    boolean this_present_body = true && this.isSetBody();
    boolean that_present_body = true && that.isSetBody();
    if (this_present_body || that_present_body) {
      if (!(this_present_body && that_present_body))
        return false;
      if (!java.util.Arrays.equals(this.body, that.body))
        return false;
    }

    boolean this_present_nanos = true;
    boolean that_present_nanos = true;
    if (this_present_nanos || that_present_nanos) {
      if (!(this_present_nanos && that_present_nanos))
        return false;
      if (this.nanos != that.nanos)
        return false;
    }

    boolean this_present_host = true && this.isSetHost();
    boolean that_present_host = true && that.isSetHost();
    if (this_present_host || that_present_host) {
      if (!(this_present_host && that_present_host))
        return false;
      if (!this.host.equals(that.host))
        return false;
    }

    boolean this_present_fields = true && this.isSetFields();
    boolean that_present_fields = true && that.isSetFields();
    if (this_present_fields || that_present_fields) {
      if (!(this_present_fields && that_present_fields))
        return false;
      if (!this.fields.equals(that.fields))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      _Fields fieldId = _Fields.findByThriftId(field.id);
      if (fieldId == null) {
        TProtocolUtil.skip(iprot, field.type);
      } else {
        switch (fieldId) {
          case TIMESTAMP:
            if (field.type == TType.I64) {
              this.timestamp = iprot.readI64();
              setTimestampIsSet(true);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case PRIORITY:
            if (field.type == TType.I32) {
              this.priority = Priority.findByValue(iprot.readI32());
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case BODY:
            if (field.type == TType.STRING) {
              this.body = iprot.readBinary();
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case NANOS:
            if (field.type == TType.I64) {
              this.nanos = iprot.readI64();
              setNanosIsSet(true);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case HOST:
            if (field.type == TType.STRING) {
              this.host = iprot.readString();
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case FIELDS:
            if (field.type == TType.MAP) {
              {
                TMap _map0 = iprot.readMapBegin();
                this.fields = new HashMap<String,byte[]>(2*_map0.size);
                for (int _i1 = 0; _i1 < _map0.size; ++_i1)
                {
                  String _key2;
                  byte[] _val3;
                  _key2 = iprot.readString();
                  _val3 = iprot.readBinary();
                  this.fields.put(_key2, _val3);
                }
                iprot.readMapEnd();
              }
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
        }
        iprot.readFieldEnd();
      }
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    oprot.writeFieldBegin(TIMESTAMP_FIELD_DESC);
    oprot.writeI64(this.timestamp);
    oprot.writeFieldEnd();
    if (this.priority != null) {
      oprot.writeFieldBegin(PRIORITY_FIELD_DESC);
      oprot.writeI32(this.priority.getValue());
      oprot.writeFieldEnd();
    }
    if (this.body != null) {
      oprot.writeFieldBegin(BODY_FIELD_DESC);
      oprot.writeBinary(this.body);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(NANOS_FIELD_DESC);
    oprot.writeI64(this.nanos);
    oprot.writeFieldEnd();
    if (this.host != null) {
      oprot.writeFieldBegin(HOST_FIELD_DESC);
      oprot.writeString(this.host);
      oprot.writeFieldEnd();
    }
    if (this.fields != null) {
      oprot.writeFieldBegin(FIELDS_FIELD_DESC);
      {
        oprot.writeMapBegin(new TMap(TType.STRING, TType.STRING, this.fields.size()));
        for (Map.Entry<String, byte[]> _iter4 : this.fields.entrySet())
        {
          oprot.writeString(_iter4.getKey());
          oprot.writeBinary(_iter4.getValue());
        }
        oprot.writeMapEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ThriftFlumeEvent(");
    boolean first = true;

    sb.append("timestamp:");
    sb.append(this.timestamp);
    first = false;
    if (!first) sb.append(", ");
    sb.append("priority:");
    if (this.priority == null) {
      sb.append("null");
    } else {
      sb.append(this.priority);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("body:");
    if (this.body == null) {
      sb.append("null");
    } else {
        int __body_size = Math.min(this.body.length, 128);
        for (int i = 0; i < __body_size; i++) {
          if (i != 0) sb.append(" ");
          sb.append(Integer.toHexString(this.body[i]).length() > 1 ? Integer.toHexString(this.body[i]).substring(Integer.toHexString(this.body[i]).length() - 2).toUpperCase() : "0" + Integer.toHexString(this.body[i]).toUpperCase());
        }
        if (this.body.length > 128) sb.append(" ...");
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nanos:");
    sb.append(this.nanos);
    first = false;
    if (!first) sb.append(", ");
    sb.append("host:");
    if (this.host == null) {
      sb.append("null");
    } else {
      sb.append(this.host);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fields:");
    if (this.fields == null) {
      sb.append("null");
    } else {
      sb.append(this.fields);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

