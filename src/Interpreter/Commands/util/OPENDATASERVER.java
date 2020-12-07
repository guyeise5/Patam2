package Interpreter.Commands.util;

import Interpreter.CalcExpresion;
import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.Fundation.BinaryCommand;
import Interpreter.Commands.Fundation.Command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class OPENDATASERVER extends BinaryCommand<Void> {

    private String frequency;
    private String port;

    public static AtomicBoolean shouldRun = new AtomicBoolean(false);

    @Override
    public Void execute() throws CommandNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException, InvalidArgumentsException, IllegalAccessException, InterpreterException, InvalidConditionFormatException, NoCommandsLeftException, CalculateException {
        OPENDATASERVER.shouldRun.set(true);
        int port = (int) CalcExpresion.calc(this.port);
        int frequency = (int) CalcExpresion.calc(this.frequency);
        CompletableFuture.runAsync(() -> {
            try {
                doServer(port, frequency);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    private void doServer(int port, int frequency) throws IOException {
        try (ServerSocket server = new ServerSocket()) {
            server.bind(new InetSocketAddress("127.0.0.1", port));
            Socket client = server.accept();
            try(InputStream inputStream = client.getInputStream()){
                while(shouldRun.get()) {
                    try {
                        byte[] buffer = new byte[1024];
                        int len = inputStream.read(buffer);
                        System.out.println(new String(buffer,0,len));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void setArgs(String... args) throws InvalidArgumentsException {
        this.port = args[1];
        this.frequency = args[2];
        
    }
}
