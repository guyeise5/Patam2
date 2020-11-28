package Interpreter.Commands.Exceptions;

public class InvalidArgumentsException extends CommandException{
    public InvalidArgumentsException(String s) {
        super(s);
    }

    public InvalidArgumentsException() {
        super();
    }
}
