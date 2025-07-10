package com.trenical.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: trenical.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TrenicalServiceGrpc {

  private TrenicalServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "trenical.TrenicalService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.TicketRequest,
      com.trenical.grpc.TicketResponse> getGetTicketInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTicketInfo",
      requestType = com.trenical.grpc.TicketRequest.class,
      responseType = com.trenical.grpc.TicketResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.TicketRequest,
      com.trenical.grpc.TicketResponse> getGetTicketInfoMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.TicketRequest, com.trenical.grpc.TicketResponse> getGetTicketInfoMethod;
    if ((getGetTicketInfoMethod = TrenicalServiceGrpc.getGetTicketInfoMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getGetTicketInfoMethod = TrenicalServiceGrpc.getGetTicketInfoMethod) == null) {
          TrenicalServiceGrpc.getGetTicketInfoMethod = getGetTicketInfoMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.TicketRequest, com.trenical.grpc.TicketResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTicketInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.TicketRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.TicketResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("GetTicketInfo"))
              .build();
        }
      }
    }
    return getGetTicketInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.LoginRequest,
      com.trenical.grpc.LoginResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = com.trenical.grpc.LoginRequest.class,
      responseType = com.trenical.grpc.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.LoginRequest,
      com.trenical.grpc.LoginResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.LoginRequest, com.trenical.grpc.LoginResponse> getLoginMethod;
    if ((getLoginMethod = TrenicalServiceGrpc.getLoginMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getLoginMethod = TrenicalServiceGrpc.getLoginMethod) == null) {
          TrenicalServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.LoginRequest, com.trenical.grpc.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.BigliettoRequest,
      com.trenical.grpc.BigliettoResponse> getAcquistaBigliettoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AcquistaBiglietto",
      requestType = com.trenical.grpc.BigliettoRequest.class,
      responseType = com.trenical.grpc.BigliettoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.BigliettoRequest,
      com.trenical.grpc.BigliettoResponse> getAcquistaBigliettoMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.BigliettoRequest, com.trenical.grpc.BigliettoResponse> getAcquistaBigliettoMethod;
    if ((getAcquistaBigliettoMethod = TrenicalServiceGrpc.getAcquistaBigliettoMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getAcquistaBigliettoMethod = TrenicalServiceGrpc.getAcquistaBigliettoMethod) == null) {
          TrenicalServiceGrpc.getAcquistaBigliettoMethod = getAcquistaBigliettoMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.BigliettoRequest, com.trenical.grpc.BigliettoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AcquistaBiglietto"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.BigliettoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.BigliettoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("AcquistaBiglietto"))
              .build();
        }
      }
    }
    return getAcquistaBigliettoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.NotificheRequest,
      com.trenical.grpc.Notifica> getStreamNotificheMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamNotifiche",
      requestType = com.trenical.grpc.NotificheRequest.class,
      responseType = com.trenical.grpc.Notifica.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.NotificheRequest,
      com.trenical.grpc.Notifica> getStreamNotificheMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.NotificheRequest, com.trenical.grpc.Notifica> getStreamNotificheMethod;
    if ((getStreamNotificheMethod = TrenicalServiceGrpc.getStreamNotificheMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getStreamNotificheMethod = TrenicalServiceGrpc.getStreamNotificheMethod) == null) {
          TrenicalServiceGrpc.getStreamNotificheMethod = getStreamNotificheMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.NotificheRequest, com.trenical.grpc.Notifica>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamNotifiche"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.NotificheRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.Notifica.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("StreamNotifiche"))
              .build();
        }
      }
    }
    return getStreamNotificheMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.UserIdRequest,
      com.trenical.grpc.StoricoBigliettiResponse> getGetStoricoBigliettiMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStoricoBiglietti",
      requestType = com.trenical.grpc.UserIdRequest.class,
      responseType = com.trenical.grpc.StoricoBigliettiResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.UserIdRequest,
      com.trenical.grpc.StoricoBigliettiResponse> getGetStoricoBigliettiMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.UserIdRequest, com.trenical.grpc.StoricoBigliettiResponse> getGetStoricoBigliettiMethod;
    if ((getGetStoricoBigliettiMethod = TrenicalServiceGrpc.getGetStoricoBigliettiMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getGetStoricoBigliettiMethod = TrenicalServiceGrpc.getGetStoricoBigliettiMethod) == null) {
          TrenicalServiceGrpc.getGetStoricoBigliettiMethod = getGetStoricoBigliettiMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.UserIdRequest, com.trenical.grpc.StoricoBigliettiResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStoricoBiglietti"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.UserIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.StoricoBigliettiResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("GetStoricoBiglietti"))
              .build();
        }
      }
    }
    return getGetStoricoBigliettiMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.CercaTratteRequest,
      com.trenical.grpc.CercaTratteResponse> getCercaTratteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CercaTratte",
      requestType = com.trenical.grpc.CercaTratteRequest.class,
      responseType = com.trenical.grpc.CercaTratteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.CercaTratteRequest,
      com.trenical.grpc.CercaTratteResponse> getCercaTratteMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.CercaTratteRequest, com.trenical.grpc.CercaTratteResponse> getCercaTratteMethod;
    if ((getCercaTratteMethod = TrenicalServiceGrpc.getCercaTratteMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getCercaTratteMethod = TrenicalServiceGrpc.getCercaTratteMethod) == null) {
          TrenicalServiceGrpc.getCercaTratteMethod = getCercaTratteMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.CercaTratteRequest, com.trenical.grpc.CercaTratteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CercaTratte"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.CercaTratteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.CercaTratteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("CercaTratte"))
              .build();
        }
      }
    }
    return getCercaTratteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trenical.grpc.UpdateTrattaRequest,
      com.google.protobuf.Empty> getUpdateTrattaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateTratta",
      requestType = com.trenical.grpc.UpdateTrattaRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trenical.grpc.UpdateTrattaRequest,
      com.google.protobuf.Empty> getUpdateTrattaMethod() {
    io.grpc.MethodDescriptor<com.trenical.grpc.UpdateTrattaRequest, com.google.protobuf.Empty> getUpdateTrattaMethod;
    if ((getUpdateTrattaMethod = TrenicalServiceGrpc.getUpdateTrattaMethod) == null) {
      synchronized (TrenicalServiceGrpc.class) {
        if ((getUpdateTrattaMethod = TrenicalServiceGrpc.getUpdateTrattaMethod) == null) {
          TrenicalServiceGrpc.getUpdateTrattaMethod = getUpdateTrattaMethod =
              io.grpc.MethodDescriptor.<com.trenical.grpc.UpdateTrattaRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateTratta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trenical.grpc.UpdateTrattaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TrenicalServiceMethodDescriptorSupplier("UpdateTratta"))
              .build();
        }
      }
    }
    return getUpdateTrattaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TrenicalServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceStub>() {
        @java.lang.Override
        public TrenicalServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrenicalServiceStub(channel, callOptions);
        }
      };
    return TrenicalServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TrenicalServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceBlockingStub>() {
        @java.lang.Override
        public TrenicalServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrenicalServiceBlockingStub(channel, callOptions);
        }
      };
    return TrenicalServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TrenicalServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrenicalServiceFutureStub>() {
        @java.lang.Override
        public TrenicalServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrenicalServiceFutureStub(channel, callOptions);
        }
      };
    return TrenicalServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getTicketInfo(com.trenical.grpc.TicketRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.TicketResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTicketInfoMethod(), responseObserver);
    }

    /**
     */
    default void login(com.trenical.grpc.LoginRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.LoginResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    default void acquistaBiglietto(com.trenical.grpc.BigliettoRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.BigliettoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAcquistaBigliettoMethod(), responseObserver);
    }

    /**
     */
    default void streamNotifiche(com.trenical.grpc.NotificheRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.Notifica> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamNotificheMethod(), responseObserver);
    }

    /**
     */
    default void getStoricoBiglietti(com.trenical.grpc.UserIdRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.StoricoBigliettiResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStoricoBigliettiMethod(), responseObserver);
    }

    /**
     */
    default void cercaTratte(com.trenical.grpc.CercaTratteRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.CercaTratteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCercaTratteMethod(), responseObserver);
    }

    /**
     */
    default void updateTratta(com.trenical.grpc.UpdateTrattaRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateTrattaMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TrenicalService.
   */
  public static abstract class TrenicalServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TrenicalServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TrenicalService.
   */
  public static final class TrenicalServiceStub
      extends io.grpc.stub.AbstractAsyncStub<TrenicalServiceStub> {
    private TrenicalServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrenicalServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrenicalServiceStub(channel, callOptions);
    }

    /**
     */
    public void getTicketInfo(com.trenical.grpc.TicketRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.TicketResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTicketInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void login(com.trenical.grpc.LoginRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.LoginResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void acquistaBiglietto(com.trenical.grpc.BigliettoRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.BigliettoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAcquistaBigliettoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamNotifiche(com.trenical.grpc.NotificheRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.Notifica> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamNotificheMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStoricoBiglietti(com.trenical.grpc.UserIdRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.StoricoBigliettiResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStoricoBigliettiMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cercaTratte(com.trenical.grpc.CercaTratteRequest request,
        io.grpc.stub.StreamObserver<com.trenical.grpc.CercaTratteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCercaTratteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateTratta(com.trenical.grpc.UpdateTrattaRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateTrattaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TrenicalService.
   */
  public static final class TrenicalServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TrenicalServiceBlockingStub> {
    private TrenicalServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrenicalServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrenicalServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.trenical.grpc.TicketResponse getTicketInfo(com.trenical.grpc.TicketRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTicketInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trenical.grpc.LoginResponse login(com.trenical.grpc.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trenical.grpc.BigliettoResponse acquistaBiglietto(com.trenical.grpc.BigliettoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAcquistaBigliettoMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.trenical.grpc.Notifica> streamNotifiche(
        com.trenical.grpc.NotificheRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamNotificheMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trenical.grpc.StoricoBigliettiResponse getStoricoBiglietti(com.trenical.grpc.UserIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStoricoBigliettiMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trenical.grpc.CercaTratteResponse cercaTratte(com.trenical.grpc.CercaTratteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCercaTratteMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateTratta(com.trenical.grpc.UpdateTrattaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateTrattaMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TrenicalService.
   */
  public static final class TrenicalServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<TrenicalServiceFutureStub> {
    private TrenicalServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrenicalServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrenicalServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.TicketResponse> getTicketInfo(
        com.trenical.grpc.TicketRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTicketInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.LoginResponse> login(
        com.trenical.grpc.LoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.BigliettoResponse> acquistaBiglietto(
        com.trenical.grpc.BigliettoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAcquistaBigliettoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.StoricoBigliettiResponse> getStoricoBiglietti(
        com.trenical.grpc.UserIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStoricoBigliettiMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trenical.grpc.CercaTratteResponse> cercaTratte(
        com.trenical.grpc.CercaTratteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCercaTratteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateTratta(
        com.trenical.grpc.UpdateTrattaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateTrattaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TICKET_INFO = 0;
  private static final int METHODID_LOGIN = 1;
  private static final int METHODID_ACQUISTA_BIGLIETTO = 2;
  private static final int METHODID_STREAM_NOTIFICHE = 3;
  private static final int METHODID_GET_STORICO_BIGLIETTI = 4;
  private static final int METHODID_CERCA_TRATTE = 5;
  private static final int METHODID_UPDATE_TRATTA = 6;

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
        case METHODID_GET_TICKET_INFO:
          serviceImpl.getTicketInfo((com.trenical.grpc.TicketRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.TicketResponse>) responseObserver);
          break;
        case METHODID_LOGIN:
          serviceImpl.login((com.trenical.grpc.LoginRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.LoginResponse>) responseObserver);
          break;
        case METHODID_ACQUISTA_BIGLIETTO:
          serviceImpl.acquistaBiglietto((com.trenical.grpc.BigliettoRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.BigliettoResponse>) responseObserver);
          break;
        case METHODID_STREAM_NOTIFICHE:
          serviceImpl.streamNotifiche((com.trenical.grpc.NotificheRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.Notifica>) responseObserver);
          break;
        case METHODID_GET_STORICO_BIGLIETTI:
          serviceImpl.getStoricoBiglietti((com.trenical.grpc.UserIdRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.StoricoBigliettiResponse>) responseObserver);
          break;
        case METHODID_CERCA_TRATTE:
          serviceImpl.cercaTratte((com.trenical.grpc.CercaTratteRequest) request,
              (io.grpc.stub.StreamObserver<com.trenical.grpc.CercaTratteResponse>) responseObserver);
          break;
        case METHODID_UPDATE_TRATTA:
          serviceImpl.updateTratta((com.trenical.grpc.UpdateTrattaRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getGetTicketInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.TicketRequest,
              com.trenical.grpc.TicketResponse>(
                service, METHODID_GET_TICKET_INFO)))
        .addMethod(
          getLoginMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.LoginRequest,
              com.trenical.grpc.LoginResponse>(
                service, METHODID_LOGIN)))
        .addMethod(
          getAcquistaBigliettoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.BigliettoRequest,
              com.trenical.grpc.BigliettoResponse>(
                service, METHODID_ACQUISTA_BIGLIETTO)))
        .addMethod(
          getStreamNotificheMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.trenical.grpc.NotificheRequest,
              com.trenical.grpc.Notifica>(
                service, METHODID_STREAM_NOTIFICHE)))
        .addMethod(
          getGetStoricoBigliettiMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.UserIdRequest,
              com.trenical.grpc.StoricoBigliettiResponse>(
                service, METHODID_GET_STORICO_BIGLIETTI)))
        .addMethod(
          getCercaTratteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.CercaTratteRequest,
              com.trenical.grpc.CercaTratteResponse>(
                service, METHODID_CERCA_TRATTE)))
        .addMethod(
          getUpdateTrattaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.trenical.grpc.UpdateTrattaRequest,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_TRATTA)))
        .build();
  }

  private static abstract class TrenicalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TrenicalServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.trenical.grpc.TrenicalProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TrenicalService");
    }
  }

  private static final class TrenicalServiceFileDescriptorSupplier
      extends TrenicalServiceBaseDescriptorSupplier {
    TrenicalServiceFileDescriptorSupplier() {}
  }

  private static final class TrenicalServiceMethodDescriptorSupplier
      extends TrenicalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TrenicalServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (TrenicalServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TrenicalServiceFileDescriptorSupplier())
              .addMethod(getGetTicketInfoMethod())
              .addMethod(getLoginMethod())
              .addMethod(getAcquistaBigliettoMethod())
              .addMethod(getStreamNotificheMethod())
              .addMethod(getGetStoricoBigliettiMethod())
              .addMethod(getCercaTratteMethod())
              .addMethod(getUpdateTrattaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
