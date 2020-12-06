package test;

import Interpreter.Commands.Fundation.CodeBlock;
import Interpreter.Commands.Fundation.Variables;
import Interpreter.Commands.Fundation.VariablesFactory;
import Interpreter.Commands.util.Variable;

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
			replacedVariableWithValueCommand =
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
		getInstance().variables.clean();
		CodeBlock cb = new CodeBlock(String.join("\n", lines));
		try {
			return cb.execute();
		} catch (Exception ignored) {
			return 1;
		}
	}
}
