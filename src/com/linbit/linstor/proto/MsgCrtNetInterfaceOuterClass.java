// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/MsgCrtNetInterface.proto

package com.linbit.linstor.proto;

public final class MsgCrtNetInterfaceOuterClass {
  private MsgCrtNetInterfaceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgCrtNetInterfaceOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.linbit.linstor.proto.MsgCrtNetInterface)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    boolean hasNodeName();
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    java.lang.String getNodeName();
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    com.google.protobuf.ByteString
        getNodeNameBytes();

    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    boolean hasNetIf();
    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface getNetIf();
    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder getNetIfOrBuilder();
  }
  /**
   * <pre>
   * linstor - Create network interface
   * </pre>
   *
   * Protobuf type {@code com.linbit.linstor.proto.MsgCrtNetInterface}
   */
  public  static final class MsgCrtNetInterface extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.linbit.linstor.proto.MsgCrtNetInterface)
      MsgCrtNetInterfaceOrBuilder {
    // Use MsgCrtNetInterface.newBuilder() to construct.
    private MsgCrtNetInterface(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgCrtNetInterface() {
      nodeName_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MsgCrtNetInterface(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              nodeName_ = bs;
              break;
            }
            case 18: {
              com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder subBuilder = null;
              if (((bitField0_ & 0x00000002) == 0x00000002)) {
                subBuilder = netIf_.toBuilder();
              }
              netIf_ = input.readMessage(com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(netIf_);
                netIf_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000002;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.class, com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.Builder.class);
    }

    private int bitField0_;
    public static final int NODE_NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object nodeName_;
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    public boolean hasNodeName() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    public java.lang.String getNodeName() {
      java.lang.Object ref = nodeName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          nodeName_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNodeNameBytes() {
      java.lang.Object ref = nodeName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        nodeName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int NET_IF_FIELD_NUMBER = 2;
    private com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface netIf_;
    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    public boolean hasNetIf() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    public com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface getNetIf() {
      return netIf_ == null ? com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.getDefaultInstance() : netIf_;
    }
    /**
     * <pre>
     * Network interface name
     * </pre>
     *
     * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
     */
    public com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder getNetIfOrBuilder() {
      return netIf_ == null ? com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.getDefaultInstance() : netIf_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasNodeName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasNetIf()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getNetIf().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, nodeName_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeMessage(2, getNetIf());
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, nodeName_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, getNetIf());
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface)) {
        return super.equals(obj);
      }
      com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface other = (com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface) obj;

      boolean result = true;
      result = result && (hasNodeName() == other.hasNodeName());
      if (hasNodeName()) {
        result = result && getNodeName()
            .equals(other.getNodeName());
      }
      result = result && (hasNetIf() == other.hasNetIf());
      if (hasNetIf()) {
        result = result && getNetIf()
            .equals(other.getNetIf());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasNodeName()) {
        hash = (37 * hash) + NODE_NAME_FIELD_NUMBER;
        hash = (53 * hash) + getNodeName().hashCode();
      }
      if (hasNetIf()) {
        hash = (37 * hash) + NET_IF_FIELD_NUMBER;
        hash = (53 * hash) + getNetIf().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
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
     * <pre>
     * linstor - Create network interface
     * </pre>
     *
     * Protobuf type {@code com.linbit.linstor.proto.MsgCrtNetInterface}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.linbit.linstor.proto.MsgCrtNetInterface)
        com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterfaceOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.class, com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.Builder.class);
      }

      // Construct using com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getNetIfFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        nodeName_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        if (netIfBuilder_ == null) {
          netIf_ = null;
        } else {
          netIfBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor;
      }

      public com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface getDefaultInstanceForType() {
        return com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.getDefaultInstance();
      }

      public com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface build() {
        com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface buildPartial() {
        com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface result = new com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.nodeName_ = nodeName_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        if (netIfBuilder_ == null) {
          result.netIf_ = netIf_;
        } else {
          result.netIf_ = netIfBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface) {
          return mergeFrom((com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface other) {
        if (other == com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface.getDefaultInstance()) return this;
        if (other.hasNodeName()) {
          bitField0_ |= 0x00000001;
          nodeName_ = other.nodeName_;
          onChanged();
        }
        if (other.hasNetIf()) {
          mergeNetIf(other.getNetIf());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasNodeName()) {
          return false;
        }
        if (!hasNetIf()) {
          return false;
        }
        if (!getNetIf().isInitialized()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object nodeName_ = "";
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public boolean hasNodeName() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public java.lang.String getNodeName() {
        java.lang.Object ref = nodeName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            nodeName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNodeNameBytes() {
        java.lang.Object ref = nodeName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          nodeName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public Builder setNodeName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        nodeName_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public Builder clearNodeName() {
        bitField0_ = (bitField0_ & ~0x00000001);
        nodeName_ = getDefaultInstance().getNodeName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 1;</code>
       */
      public Builder setNodeNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        nodeName_ = value;
        onChanged();
        return this;
      }

      private com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface netIf_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder> netIfBuilder_;
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public boolean hasNetIf() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface getNetIf() {
        if (netIfBuilder_ == null) {
          return netIf_ == null ? com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.getDefaultInstance() : netIf_;
        } else {
          return netIfBuilder_.getMessage();
        }
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public Builder setNetIf(com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface value) {
        if (netIfBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          netIf_ = value;
          onChanged();
        } else {
          netIfBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public Builder setNetIf(
          com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder builderForValue) {
        if (netIfBuilder_ == null) {
          netIf_ = builderForValue.build();
          onChanged();
        } else {
          netIfBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public Builder mergeNetIf(com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface value) {
        if (netIfBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002) &&
              netIf_ != null &&
              netIf_ != com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.getDefaultInstance()) {
            netIf_ =
              com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.newBuilder(netIf_).mergeFrom(value).buildPartial();
          } else {
            netIf_ = value;
          }
          onChanged();
        } else {
          netIfBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public Builder clearNetIf() {
        if (netIfBuilder_ == null) {
          netIf_ = null;
          onChanged();
        } else {
          netIfBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder getNetIfBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getNetIfFieldBuilder().getBuilder();
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      public com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder getNetIfOrBuilder() {
        if (netIfBuilder_ != null) {
          return netIfBuilder_.getMessageOrBuilder();
        } else {
          return netIf_ == null ?
              com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.getDefaultInstance() : netIf_;
        }
      }
      /**
       * <pre>
       * Network interface name
       * </pre>
       *
       * <code>required .com.linbit.linstor.proto.NetInterface net_if = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder> 
          getNetIfFieldBuilder() {
        if (netIfBuilder_ == null) {
          netIfBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterface.Builder, com.linbit.linstor.proto.NetInterfaceOuterClass.NetInterfaceOrBuilder>(
                  getNetIf(),
                  getParentForChildren(),
                  isClean());
          netIf_ = null;
        }
        return netIfBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.linbit.linstor.proto.MsgCrtNetInterface)
    }

    // @@protoc_insertion_point(class_scope:com.linbit.linstor.proto.MsgCrtNetInterface)
    private static final com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface();
    }

    public static com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<MsgCrtNetInterface>
        PARSER = new com.google.protobuf.AbstractParser<MsgCrtNetInterface>() {
      public MsgCrtNetInterface parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MsgCrtNetInterface(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MsgCrtNetInterface> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgCrtNetInterface> getParserForType() {
      return PARSER;
    }

    public com.linbit.linstor.proto.MsgCrtNetInterfaceOuterClass.MsgCrtNetInterface getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036proto/MsgCrtNetInterface.proto\022\030com.li" +
      "nbit.linstor.proto\032\030proto/NetInterface.p" +
      "roto\"_\n\022MsgCrtNetInterface\022\021\n\tnode_name\030" +
      "\001 \002(\t\0226\n\006net_if\030\002 \002(\0132&.com.linbit.linst" +
      "or.proto.NetInterface"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.linbit.linstor.proto.NetInterfaceOuterClass.getDescriptor(),
        }, assigner);
    internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_linstor_proto_MsgCrtNetInterface_descriptor,
        new java.lang.String[] { "NodeName", "NetIf", });
    com.linbit.linstor.proto.NetInterfaceOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
