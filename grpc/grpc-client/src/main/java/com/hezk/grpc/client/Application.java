package com.hezk.grpc.client;

import com.hezk.grpc.api.RPCDateRequest;
import com.hezk.grpc.api.RPCDateResponse;
import com.hezk.grpc.api.RPCUserServiceApiGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Application {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext().build();
        try {
            RPCUserServiceApiGrpc.RPCUserServiceApiBlockingStub rpcDateService = RPCUserServiceApiGrpc.newBlockingStub(managedChannel);
            RPCDateRequest rpcDateRequest = RPCDateRequest
                    .newBuilder()
                    .setUserName("hezk")
                    .build();
            RPCDateResponse rpcDateResponse = rpcDateService.getDate(rpcDateRequest);
            System.out.println(rpcDateResponse.getServerDate());
        } finally {
            managedChannel.shutdown();
        }
    }
}
