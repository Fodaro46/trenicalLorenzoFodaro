package com.trenical.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new TrenicalServiceImpl() )
                .build()
                .start();

        System.out.println("✅ gRPC Server avviato sulla porta 50051");
        server.awaitTermination();
    }
}
