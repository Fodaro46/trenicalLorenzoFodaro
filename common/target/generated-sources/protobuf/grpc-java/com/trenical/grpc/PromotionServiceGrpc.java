package com.trenical.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: trenical.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PromotionServiceGrpc {

  private PromotionServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "trenical.PromotionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.GetOffertaRequest,
      com.trenical.grpc.OffertaResponse> getGetOffertaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOfferta",
      requestType = com.trenical.grpc.GetOffertaRequest.class,
      responseType = com.trenical.grpc.OffertaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.GetOffertaRequest,
      com.trenical.grpc.OffertaResponse> getGetOffertaMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.GetOffertaRequest, com.trenical.grpc.OffertaResponse> getGetOffertaMethod;
    if ((getGetOffertaMethod = PromotionServiceGrpc.getGetOffertaMethod) == null) {
      synchronized (PromotionServiceGrpc.class) {
        if ((getGetOffertaMethod = PromotionServiceGrpc.getGetOffertaMethod) == null) {
          PromotionServiceGrpc.getGetOffertaMethod = getGetOffertaMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.GetOffertaRequest, com.trenical.grpc.OffertaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOfferta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.GetOffertaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.OffertaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PromotionServiceMethodDescriptorSupplier("GetOfferta"))
              .build();
        }
      }
    }
    return getGetOffertaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PromotionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PromotionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PromotionServiceStub>() {
        @java.lang.Override
        public PromotionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PromotionServiceStub(channel, callOptions);
        }
      };
    return PromotionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PromotionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PromotionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PromotionServiceBlockingStub>() {
        @java.lang.Override
        public PromotionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PromotionServiceBlockingStub(channel, callOptions);
        }
      };
    return PromotionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PromotionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PromotionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PromotionServiceFutureStub>() {
        @java.lang.Override
        public PromotionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PromotionServiceFutureStub(channel, callOptions);
        }
      };
    return PromotionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getOfferta(com.trenical.grpc.GetOffertaRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.OffertaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffertaMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PromotionService.
   */
  public static abstract class PromotionServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PromotionServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PromotionService.
   */
  public static final class PromotionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PromotionServiceStub> {
    private PromotionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PromotionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PromotionServiceStub(channel, callOptions);
    }

    /**
     */
    public void getOfferta(com.trenical.grpc.GetOffertaRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.OffertaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffertaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PromotionService.
   */
  public static final class PromotionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PromotionServiceBlockingStub> {
    private PromotionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PromotionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PromotionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.trenical.grpc.OffertaResponse getOfferta(com.trenical.grpc.GetOffertaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffertaMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PromotionService.
   */
  public static final class PromotionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PromotionServiceFutureStub> {
    private PromotionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PromotionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PromotionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.OffertaResponse> getOfferta(
        com.trenical.grpc.GetOffertaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffertaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OFFERTA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OFFERTA:
          serviceImpl.getOfferta((com.trenical.grpc.GetOffertaRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.OffertaResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetOffertaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.GetOffertaRequest,
              com.trenical.grpc.OffertaResponse>(
                service, METHODID_GET_OFFERTA)))
        .build();
  }

  private static abstract class PromotionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PromotionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.trenical.grpc.TrenicalProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PromotionService");
    }
  }

  private static final class PromotionServiceFileDescriptorSupplier
      extends PromotionServiceBaseDescriptorSupplier {
    PromotionServiceFileDescriptorSupplier() {}
  }

  private static final class PromotionServiceMethodDescriptorSupplier
      extends PromotionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PromotionServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PromotionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PromotionServiceFileDescriptorSupplier())
              .addMethod(getGetOffertaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
