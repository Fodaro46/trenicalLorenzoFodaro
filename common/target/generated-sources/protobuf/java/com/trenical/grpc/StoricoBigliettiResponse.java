// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: trenical.proto

// Protobuf Java Version: 3.25.3
package com.trenical.grpc;

/**
 * Protobuf type {@code trenical.StoricoBigliettiResponse}
 */
public final class StoricoBigliettiResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:trenical.StoricoBigliettiResponse)
    StoricoBigliettiResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use StoricoBigliettiResponse.newBuilder() to construct.
  private StoricoBigliettiResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private StoricoBigliettiResponse() {
    biglietti_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new StoricoBigliettiResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.trenical.grpc.TrenicalProto.internal_static_trenical_StoricoBigliettiResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.trenical.grpc.TrenicalProto.internal_static_trenical_StoricoBigliettiResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.trenical.grpc.StoricoBigliettiResponse.class, com.trenical.grpc.StoricoBigliettiResponse.Builder.class);
  }

  public static final int BIGLIETTI_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<com.trenical.grpc.BigliettoInfo> biglietti_;
  /**
   * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.trenical.grpc.BigliettoInfo> getBigliettiList() {
    return biglietti_;
  }
  /**
   * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.trenical.grpc.BigliettoInfoOrBuilder> 
      getBigliettiOrBuilderList() {
    return biglietti_;
  }
  /**
   * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
   */
  @java.lang.Override
  public int getBigliettiCount() {
    return biglietti_.size();
  }
  /**
   * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
   */
  @java.lang.Override
  public com.trenical.grpc.BigliettoInfo getBiglietti(int index) {
    return biglietti_.get(index);
  }
  /**
   * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
   */
  @java.lang.Override
  public com.trenical.grpc.BigliettoInfoOrBuilder getBigliettiOrBuilder(
      int index) {
    return biglietti_.get(index);
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
    for (int i = 0; i < biglietti_.size(); i++) {
      output.writeMessage(1, biglietti_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < biglietti_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, biglietti_.get(i));
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
    if (!(obj instanceof com.trenical.grpc.StoricoBigliettiResponse)) {
      return super.equals(obj);
    }
    com.trenical.grpc.StoricoBigliettiResponse other = (com.trenical.grpc.StoricoBigliettiResponse) obj;

    if (!getBigliettiList()
        .equals(other.getBigliettiList())) return false;
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
    if (getBigliettiCount() > 0) {
      hash = (37 * hash) + BIGLIETTI_FIELD_NUMBER;
      hash = (53 * hash) + getBigliettiList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.trenical.grpc.StoricoBigliettiResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.trenical.grpc.StoricoBigliettiResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.trenical.grpc.StoricoBigliettiResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.trenical.grpc.StoricoBigliettiResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code trenical.StoricoBigliettiResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:trenical.StoricoBigliettiResponse)
      com.trenical.grpc.StoricoBigliettiResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.trenical.grpc.TrenicalProto.internal_static_trenical_StoricoBigliettiResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.trenical.grpc.TrenicalProto.internal_static_trenical_StoricoBigliettiResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.trenical.grpc.StoricoBigliettiResponse.class, com.trenical.grpc.StoricoBigliettiResponse.Builder.class);
    }

    // Construct using com.trenical.grpc.StoricoBigliettiResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      if (bigliettiBuilder_ == null) {
        biglietti_ = java.util.Collections.emptyList();
      } else {
        biglietti_ = null;
        bigliettiBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.trenical.grpc.TrenicalProto.internal_static_trenical_StoricoBigliettiResponse_descriptor;
    }

    @java.lang.Override
    public com.trenical.grpc.StoricoBigliettiResponse getDefaultInstanceForType() {
      return com.trenical.grpc.StoricoBigliettiResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.trenical.grpc.StoricoBigliettiResponse build() {
      com.trenical.grpc.StoricoBigliettiResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.trenical.grpc.StoricoBigliettiResponse buildPartial() {
      com.trenical.grpc.StoricoBigliettiResponse result = new com.trenical.grpc.StoricoBigliettiResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(com.trenical.grpc.StoricoBigliettiResponse result) {
      if (bigliettiBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          biglietti_ = java.util.Collections.unmodifiableList(biglietti_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.biglietti_ = biglietti_;
      } else {
        result.biglietti_ = bigliettiBuilder_.build();
      }
    }

    private void buildPartial0(com.trenical.grpc.StoricoBigliettiResponse result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.trenical.grpc.StoricoBigliettiResponse) {
        return mergeFrom((com.trenical.grpc.StoricoBigliettiResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.trenical.grpc.StoricoBigliettiResponse other) {
      if (other == com.trenical.grpc.StoricoBigliettiResponse.getDefaultInstance()) return this;
      if (bigliettiBuilder_ == null) {
        if (!other.biglietti_.isEmpty()) {
          if (biglietti_.isEmpty()) {
            biglietti_ = other.biglietti_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBigliettiIsMutable();
            biglietti_.addAll(other.biglietti_);
          }
          onChanged();
        }
      } else {
        if (!other.biglietti_.isEmpty()) {
          if (bigliettiBuilder_.isEmpty()) {
            bigliettiBuilder_.dispose();
            bigliettiBuilder_ = null;
            biglietti_ = other.biglietti_;
            bitField0_ = (bitField0_ & ~0x00000001);
            bigliettiBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBigliettiFieldBuilder() : null;
          } else {
            bigliettiBuilder_.addAllMessages(other.biglietti_);
          }
        }
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
            case 10: {
              com.trenical.grpc.BigliettoInfo m =
                  input.readMessage(
                      com.trenical.grpc.BigliettoInfo.parser(),
                      extensionRegistry);
              if (bigliettiBuilder_ == null) {
                ensureBigliettiIsMutable();
                biglietti_.add(m);
              } else {
                bigliettiBuilder_.addMessage(m);
              }
              break;
            } // case 10
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

    private java.util.List<com.trenical.grpc.BigliettoInfo> biglietti_ =
      java.util.Collections.emptyList();
    private void ensureBigliettiIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        biglietti_ = new java.util.ArrayList<com.trenical.grpc.BigliettoInfo>(biglietti_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.trenical.grpc.BigliettoInfo, com.trenical.grpc.BigliettoInfo.Builder, com.trenical.grpc.BigliettoInfoOrBuilder> bigliettiBuilder_;

    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public java.util.List<com.trenical.grpc.BigliettoInfo> getBigliettiList() {
      if (bigliettiBuilder_ == null) {
        return java.util.Collections.unmodifiableList(biglietti_);
      } else {
        return bigliettiBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public int getBigliettiCount() {
      if (bigliettiBuilder_ == null) {
        return biglietti_.size();
      } else {
        return bigliettiBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public com.trenical.grpc.BigliettoInfo getBiglietti(int index) {
      if (bigliettiBuilder_ == null) {
        return biglietti_.get(index);
      } else {
        return bigliettiBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder setBiglietti(
        int index, com.trenical.grpc.BigliettoInfo value) {
      if (bigliettiBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBigliettiIsMutable();
        biglietti_.set(index, value);
        onChanged();
      } else {
        bigliettiBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder setBiglietti(
        int index, com.trenical.grpc.BigliettoInfo.Builder builderForValue) {
      if (bigliettiBuilder_ == null) {
        ensureBigliettiIsMutable();
        biglietti_.set(index, builderForValue.build());
        onChanged();
      } else {
        bigliettiBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder addBiglietti(com.trenical.grpc.BigliettoInfo value) {
      if (bigliettiBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBigliettiIsMutable();
        biglietti_.add(value);
        onChanged();
      } else {
        bigliettiBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder addBiglietti(
        int index, com.trenical.grpc.BigliettoInfo value) {
      if (bigliettiBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBigliettiIsMutable();
        biglietti_.add(index, value);
        onChanged();
      } else {
        bigliettiBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder addBiglietti(
        com.trenical.grpc.BigliettoInfo.Builder builderForValue) {
      if (bigliettiBuilder_ == null) {
        ensureBigliettiIsMutable();
        biglietti_.add(builderForValue.build());
        onChanged();
      } else {
        bigliettiBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder addBiglietti(
        int index, com.trenical.grpc.BigliettoInfo.Builder builderForValue) {
      if (bigliettiBuilder_ == null) {
        ensureBigliettiIsMutable();
        biglietti_.add(index, builderForValue.build());
        onChanged();
      } else {
        bigliettiBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder addAllBiglietti(
        java.lang.Iterable<? extends com.trenical.grpc.BigliettoInfo> values) {
      if (bigliettiBuilder_ == null) {
        ensureBigliettiIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, biglietti_);
        onChanged();
      } else {
        bigliettiBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder clearBiglietti() {
      if (bigliettiBuilder_ == null) {
        biglietti_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        bigliettiBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public Builder removeBiglietti(int index) {
      if (bigliettiBuilder_ == null) {
        ensureBigliettiIsMutable();
        biglietti_.remove(index);
        onChanged();
      } else {
        bigliettiBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public com.trenical.grpc.BigliettoInfo.Builder getBigliettiBuilder(
        int index) {
      return getBigliettiFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public com.trenical.grpc.BigliettoInfoOrBuilder getBigliettiOrBuilder(
        int index) {
      if (bigliettiBuilder_ == null) {
        return biglietti_.get(index);  } else {
        return bigliettiBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public java.util.List<? extends com.trenical.grpc.BigliettoInfoOrBuilder> 
         getBigliettiOrBuilderList() {
      if (bigliettiBuilder_ != null) {
        return bigliettiBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(biglietti_);
      }
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public com.trenical.grpc.BigliettoInfo.Builder addBigliettiBuilder() {
      return getBigliettiFieldBuilder().addBuilder(
          com.trenical.grpc.BigliettoInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public com.trenical.grpc.BigliettoInfo.Builder addBigliettiBuilder(
        int index) {
      return getBigliettiFieldBuilder().addBuilder(
          index, com.trenical.grpc.BigliettoInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .trenical.BigliettoInfo biglietti = 1;</code>
     */
    public java.util.List<com.trenical.grpc.BigliettoInfo.Builder> 
         getBigliettiBuilderList() {
      return getBigliettiFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.trenical.grpc.BigliettoInfo, com.trenical.grpc.BigliettoInfo.Builder, com.trenical.grpc.BigliettoInfoOrBuilder> 
        getBigliettiFieldBuilder() {
      if (bigliettiBuilder_ == null) {
        bigliettiBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.trenical.grpc.BigliettoInfo, com.trenical.grpc.BigliettoInfo.Builder, com.trenical.grpc.BigliettoInfoOrBuilder>(
                biglietti_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        biglietti_ = null;
      }
      return bigliettiBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:trenical.StoricoBigliettiResponse)
  }

  // @@protoc_insertion_point(class_scope:trenical.StoricoBigliettiResponse)
  private static final com.trenical.grpc.StoricoBigliettiResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.trenical.grpc.StoricoBigliettiResponse();
  }

  public static com.trenical.grpc.StoricoBigliettiResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<StoricoBigliettiResponse>
      PARSER = new com.google.protobuf.AbstractParser<StoricoBigliettiResponse>() {
    @java.lang.Override
    public StoricoBigliettiResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<StoricoBigliettiResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<StoricoBigliettiResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.trenical.grpc.StoricoBigliettiResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

