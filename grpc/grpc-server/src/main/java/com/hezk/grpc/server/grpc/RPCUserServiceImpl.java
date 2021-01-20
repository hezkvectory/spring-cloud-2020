package com.hezk.grpc.server.grpc;

import com.hezk.grpc.api.RPCDateRequest;
import com.hezk.grpc.api.RPCDateResponse;
import com.hezk.grpc.api.RPCUserServiceApiGrpc;
import io.grpc.stub.StreamObserver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RPCUserServiceImpl extends RPCUserServiceApiGrpc.RPCUserServiceApiImplBase {
    @Override
    public void getDate(RPCDateRequest request, StreamObserver<RPCDateResponse> responseObserver) {
        System.out.println(request.getUserName());

        RPCDateResponse rpcDateResponse = null;
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("今天是" + "yyyy年MM月dd日 E kk点mm分");
        String nowTime = simpleDateFormat.format(now);
        try {
            rpcDateResponse = RPCDateResponse
                    .newBuilder()
                    .setServerDate("Welcome " + request.getUserName() + ", " + nowTime)
                    .build();
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(rpcDateResponse);
        }
        responseObserver.onCompleted();
    }
}
