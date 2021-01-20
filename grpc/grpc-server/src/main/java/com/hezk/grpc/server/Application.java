package com.hezk.grpc.server;

import com.hezk.grpc.server.grpc.RPCUserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    public static class GrpcServerRunner implements ApplicationRunner {
        private static final int port = 9999;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            new Thread(() -> {
                try {
                    Server server = ServerBuilder.
                            forPort(port)
                            .addService(new RPCUserServiceImpl())
                            .build().start();
                    System.out.println("grpc服务端启动成功, 端口=" + port);
                    server.awaitTermination();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
