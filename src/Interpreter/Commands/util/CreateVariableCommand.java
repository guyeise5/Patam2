package Interpreter.Commands.util;

import Interpreter.Commands.Exceptions.InvalidArgumentsException;
import Interpreter.Commands.Fundation.Command;
import test.MyInterpreter;

public class CreateVariableCommand extends Command<Double> {

    public CreateVariableCommand() {
        super("var");
    }

    @Override
    public void setArgs(String... args) throws InvalidArgumentsException {
        if (args.length <= 1) {
            throw new InvalidArgumentsException("Impossible syntax creating variable");
        } else if (!args[0].equals(getName())) {
            throw new InvalidArgumentsException("Command must start with 'var'");
        } else {
            String variableName = args[1];
            if (!isLegalVariableName(variableName)){
                throw new InvalidArgumentsException("Impossible variable name: " + variableName);
            }
            MyInterpreter.getInstance().getVariablesFactory().createVariable(variableName);
        }

        super.setArgs(args);
    }

    @Override
    public Double execute() {
        return 0.0d;
    }

    public static boolean isLegalVariableName(String optionalVariableName) {
        if (!optionalVariableName.matches("^[a-zA-Z]+$")) {
            return false;
        }
        if (MyInterpreter.getInstance().getVariablesFactory().containsVariable(optionalVariableName)) {
            return false;
        }

        return true;
    }
}
