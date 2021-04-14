package com.hezk.telnet;

import io.termd.core.readline.Functions;
import io.termd.core.readline.Keymap;
import io.termd.core.readline.Readline;
import io.termd.core.telnet.TelnetTtyConnection;
import io.termd.core.telnet.netty.NettyTelnetBootstrap;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class TermApplication {
    public static void main(String[] args) throws Throwable {
        NettyTelnetBootstrap bootstrap = new NettyTelnetBootstrap();
        try {
            bootstrap.start(() -> new TelnetTtyConnection(true, true, StandardCharsets.UTF_8,
                    connection -> {
                        connection.write("hello telnet\n");
                        ReadlineExample.readline(new Readline(new Keymap(Keymap.class.getResourceAsStream("inputrc"))).addFunctions(Functions.loadDefaults()), connection);
                    })).get(3, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        }

//        NettyTelnetTtyBootstrap bootstrap = new NettyTelnetTtyBootstrap().setOutBinary(true).setHost("localhost").setPort(4000);
//        bootstrap.start(new Consumer<TtyConnection>() {
//            @Override
//            public void accept(TtyConnection conn) {
//                ReadlineExample.handle(conn);
//            }
//        }).get(10, TimeUnit.SECONDS);

        System.in.read();
    }
}
