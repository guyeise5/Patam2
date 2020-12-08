package test;

import Interpreter.Commands.Fundation.CodeBlock;
import Interpreter.Commands.Fundation.Variables;
import Interpreter.Commands.Fundation.VariablesFactory;
import Interpreter.Commands.util.DISCONNECT;
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
		for (String variableName : variables.allVariableNames()) {
			Double value = variables.getValue(variableName);
			if (value != null) {
				replacedVariableWithValueCommand =
						replacedVariableWithValueCommand.replace(variableName, String.valueOf(value));
			}
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
		try {
			new DISCONNECT().execute();
		} catch (Exception ignored) {}
		String code = String.join("\n", lines);
		CodeBlock cb = new CodeBlock(code);
		try {
			return cb.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}
