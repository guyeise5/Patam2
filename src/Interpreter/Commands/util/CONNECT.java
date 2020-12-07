package Interpreter.Commands.util;

import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.Fundation.BinaryCommand;
import Interpreter.Commands.Fundation.Command;

import java.lang.reflect.InvocationTargetException;

public class CONNECT extends BinaryCommand<Void> {
    @Override
    public Void execute() throws CommandNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException, InvalidArgumentsException, IllegalAccessException, InterpreterException, InvalidConditionFormatException, NoCommandsLeftException, CalculateException {
        return null;
    }
}
