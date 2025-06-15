package com.trenical.client.controller;

import com.trenical.grpc.LoginRequest;
import com.trenical.grpc.LoginResponse;
import com.trenical.grpc.TrenicalServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private static ManagedChannel channel;
    private static TrenicalServiceGrpc.TrenicalServiceBlockingStub stub;

    @BeforeAll
    static void setup() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        stub = TrenicalServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    static void teardown() {
        channel.shutdown();
    }

    @Test
    void testLoginSuccess() {
        String testEmail = "mario.rossi@email.it";

        LoginRequest request = LoginRequest.newBuilder()
                .setEmail(testEmail)
                .build();

        LoginResponse response = stub.login(request);

        assertNotNull(response);
        assertNotNull(response.getUserId());
        assertFalse(response.getUserId().isEmpty());
        assertTrue(response.getMessage().contains(testEmail));
    }
}
