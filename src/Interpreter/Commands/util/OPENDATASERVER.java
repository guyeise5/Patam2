package Interpreter.Commands.util;

import Interpreter.CalcExpresion;
import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.Fundation.BinaryCommand;
import Interpreter.Commands.Fundation.Command;
import Interpreter.Commands.Fundation.Variables;
import Interpreter.Network.Client;
import Interpreter.Network.Server;
import test.MyInterpreter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collector;

public class OPENDATASERVER extends BinaryCommand<Void> {

    public static Integer frequency;
    public static Integer port;

    public static AtomicBoolean shouldRun = new AtomicBoolean(false);

    @Override
    public Void execute() throws CommandNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException, InvalidArgumentsException, IllegalAccessException, InterpreterException, InvalidConditionFormatException, NoCommandsLeftException, CalculateException {

        OPENDATASERVER.shouldRun.set(true);
        port = (int) CalcExpresion.calc(this.getArgs()[1]);
        frequency = (int) CalcExpresion.calc(this.getArgs()[2]);

        Server.getInstance().setPort(port);
        Client.getInstance().setFrequency(frequency);
        Server.getInstance().start();

        return null;
    }
}
