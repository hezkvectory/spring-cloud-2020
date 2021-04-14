package com.hezk.telnet;

import io.termd.core.function.Consumer;
import io.termd.core.readline.Functions;
import io.termd.core.readline.Keymap;
import io.termd.core.readline.Readline;
import io.termd.core.tty.TtyConnection;
import io.termd.core.util.Helper;

import java.io.InputStream;

/**
 * Shows how to use async Readline.
 */
public class ReadlineExample {

    public static void handle(TtyConnection conn) {
        InputStream inputrc = Keymap.class.getResourceAsStream("inputrc");
        Keymap keymap = new Keymap(inputrc);
        Readline readline = new Readline(keymap);
        for (io.termd.core.readline.Function function : Helper.loadServices(Thread.currentThread().getContextClassLoader(), io.termd.core.readline.Function.class)) {
            readline.addFunction(function);
        }
        conn.write("Welcome to Term.d shell example\n\n");

//        readline(new Readline(Keymap.getDefault()).addFunctions(Functions.loadDefaults()), conn);

        readline(readline, conn);
    }

    public static void readline(final Readline readline, final TtyConnection conn) {
        readline.readline(conn, "hezhengkui% ", new Consumer<String>() {
            @Override
            public void accept(String line) {
                if (line == null) {
                    conn.write("Logout").close();
                } else {
                    conn.write("User entered " + line + "\n");

                    // Read line again
                    readline(readline, conn);
                }
            }
        });
    }
}
