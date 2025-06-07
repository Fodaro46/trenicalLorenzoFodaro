package com.trenical.server;

import com.trenical.server.service.TrattaServiceImpl;
import com.trenical.server.service.TrenicalServiceImpl;
import com.trenical.server.service.PromotionServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new TrattaServiceImpl())
                .addService(new TrenicalServiceImpl())
                .addService(new PromotionServiceImpl())
                .build()
                .start();

        System.out.println("✅ gRPC Server avviato sulla porta 50051");
        server.awaitTermination();
    }
}
