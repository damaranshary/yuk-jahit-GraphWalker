package com.yukjahit.runners;

import com.yukjahit.modelImplementations.*;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;
import org.graphwalker.websocket.WebSocketServer;

import java.io.IOException;


/**
 * @author Nils Olsson
 */
public class WebSocketApplication {

    public static void main(String[] args) throws IOException {
        Executor executor = new TestExecutor(LoginTest.class);

        WebSocketServer server = new WebSocketServer(8887, executor.getMachine());
        server.start();

        Result result = executor.execute(true);
        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }
        System.out.println("Done: [" + result.getResults().toString(2) + "]");
    }
}
