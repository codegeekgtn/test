// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: agent.proto
// Protobuf Java Version: 4.29.3

package com.park.utmstack.service.grpc;

/**
 * Protobuf type {@code agent.Id}
 */
public final class Id extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:agent.Id)
    IdOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 3,
      /* suffix= */ "",
      Id.class.getName());
  }
  // Use Id.newBuilder() to construct.
  private Id(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private Id() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.park.utmstack.service.grpc.AgentManagerGrpc.internal_static_agent_Id_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.park.utmstack.service.grpc.AgentManagerGrpc.internal_static_agent_Id_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.park.utmstack.service.grpc.Id.class, com.park.utmstack.service.grpc.Id.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private long id_ = 0L;
  /**
   * <code>int64 id = 1;</code>
   * @return The id.
   */
  @java.lang.Override
  public long getId() {
    return id_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0L) {
      output.writeInt64(1, id_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, id_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.park.utmstack.service.grpc.Id)) {
      return super.equals(obj);
    }
    com.park.utmstack.service.grpc.Id other = (com.park.utmstack.service.grpc.Id) obj;

    if (getId()
        != other.getId()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getId());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.park.utmstack.service.grpc.Id parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.park.utmstack.service.grpc.Id parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.park.utmstack.service.grpc.Id parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.park.utmstack.service.grpc.Id parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.park.utmstack.service.grpc.Id prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code agent.Id}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:agent.Id)
      com.park.utmstack.service.grpc.IdOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.park.utmstack.service.grpc.AgentManagerGrpc.internal_static_agent_Id_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.park.utmstack.service.grpc.AgentManagerGrpc.internal_static_agent_Id_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.park.utmstack.service.grpc.Id.class, com.park.utmstack.service.grpc.Id.Builder.class);
    }

    // Construct using com.park.utmstack.service.grpc.Id.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      id_ = 0L;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.park.utmstack.service.grpc.AgentManagerGrpc.internal_static_agent_Id_descriptor;
    }

    @java.lang.Override
    public com.park.utmstack.service.grpc.Id getDefaultInstanceForType() {
      return com.park.utmstack.service.grpc.Id.getDefaultInstance();
    }

    @java.lang.Override
    public com.park.utmstack.service.grpc.Id build() {
      com.park.utmstack.service.grpc.Id result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.park.utmstack.service.grpc.Id buildPartial() {
      com.park.utmstack.service.grpc.Id result = new com.park.utmstack.service.grpc.Id(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.park.utmstack.service.grpc.Id result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.id_ = id_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.park.utmstack.service.grpc.Id) {
        return mergeFrom((com.park.utmstack.service.grpc.Id)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.park.utmstack.service.grpc.Id other) {
      if (other == com.park.utmstack.service.grpc.Id.getDefaultInstance()) return this;
      if (other.getId() != 0L) {
        setId(other.getId());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              id_ = input.readInt64();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private long id_ ;
    /**
     * <code>int64 id = 1;</code>
     * @return The id.
     */
    @java.lang.Override
    public long getId() {
      return id_;
    }
    /**
     * <code>int64 id = 1;</code>
     * @param value The id to set.
     * @return This builder for chaining.
     */
    public Builder setId(long value) {

      id_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int64 id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      id_ = 0L;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:agent.Id)
  }

  // @@protoc_insertion_point(class_scope:agent.Id)
  private static final com.park.utmstack.service.grpc.Id DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.park.utmstack.service.grpc.Id();
  }

  public static com.park.utmstack.service.grpc.Id getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Id>
      PARSER = new com.google.protobuf.AbstractParser<Id>() {
    @java.lang.Override
    public Id parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<Id> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Id> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.park.utmstack.service.grpc.Id getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

