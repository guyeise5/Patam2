package test;

import Interpreter.Commands.Exceptions.CommandNotFoundException;
import Interpreter.Commands.Exceptions.InvalidArgumentsException;
import Interpreter.Commands.Fundation.Command;
import Interpreter.Commands.Fundation.Variables;
import Interpreter.Commands.Fundation.VariablesFactory;
import Interpreter.Commands.util.ReturnCommand;
import Interpreter.Commands.util.Variable;

import java.util.Collection;
import java.util.stream.Stream;

public class MyInterpreter {

	Variables variables;

	private MyInterpreter() {
		variables = VariablesFactory.getInstance();
	}

	private static MyInterpreter instance = new MyInterpreter();

	public static MyInterpreter getInstance() {
		return instance;
	}

	public static final int DEFAULT_RETURN_STATUS = 0;

	public String assignVariableValues(String command) {
		String replacedVariableWithValueCommand = command;
		for (Variable variable : variables.allVariables()) {
			replacedVariableWithValueCommand.replace(variable.name, String.valueOf(variable.value));
		}
		return replacedVariableWithValueCommand;
	}

	public String[] assignVariableValues(String... commands) {
		return Stream.of(commands)
				.map(this::assignVariableValues)
				.toArray(String[]::new);

	}

	public Variables getVariablesFactory() {
		return variables;
	}

	public static int interpret(String[] lines){
		Object returnedCommandValue;
		for (String line : lines) {

			try {
				Command command = Command.parse(line);
				returnedCommandValue = command.execute();
				if (command instanceof ReturnCommand) {
					return (int)returnedCommandValue;
				}

			} catch (InvalidArgumentsException | CommandNotFoundException e) {
				System.out.println("Unable to run command: " + line);
				System.out.println(e.getMessage());
			}
		}

		return DEFAULT_RETURN_STATUS;
	}
}
