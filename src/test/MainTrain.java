package test;

import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.Fundation.CodeBlock;
import Interpreter.Commands.Fundation.Command;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class MainTrain {

	public static void main(String[] args) {
		// prodMain(args);
		try {
			testCodeBlock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testCodeBlock() {
		String code = "";
		code += "var x=3\n";
		// code += "x=4\n"; // Not working
		code += "while 3 < 5 {\n";
		code += "	return 2\n";
		code += "	return 1\n";
		code += "}\n";
		code += "return 2";

		CodeBlock x = new CodeBlock(code);

		try {
			Command<?> cmd = x.pop();  // var x=3
			cmd=x.pop(); // while (...) { ... }
			cmd=x.pop(); // return 2
			cmd=x.pop(); // Exception
		}
		catch (InterpreterException | CommandNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | InvalidArgumentsException | InvalidConditionFormatException | NoCommandsLeftException e) {
			e.printStackTrace();
		}


	}
	public static void testMain(String[] args)  {
		try {
			Command.parse("return 3 * 5 - 8+2").execute();
		} catch (CommandNotFoundException e) {
			System.out.println("command not found");
		} catch (InvalidArgumentsException e) {
			System.out.println("impossible arguments");
			System.out.println(e.getMessage());
		} catch (NoCommandsLeftException e) {
			e.printStackTrace();
		} catch (InterpreterException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InvalidConditionFormatException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}

	public static void prodMain(String[] args) {
		Random r=new Random();
		int port=r.nextInt(1001)+5000;
		Simulator sim=new Simulator(port); // sim_client on port+1, sim_server on port
		
		int rand=r.nextInt(1000);
		
		String[] test1={
				"return "+rand+" * 5 - (8+2)"	
		};
		
		if(MyInterpreter.interpret(test1)!=rand*5-(8+2))
			System.out.println("failed test1 (-20)");

		String[] test2={
				"var x",	
				"x="+rand,	
				"var y=x+3",	
				"return y"	
		};
		
		if(MyInterpreter.interpret(test2)!=rand+3)
			System.out.println("failed test2 (-20)");

		String[] test3={
				"openDataServer "+(port+1)+" 10",
				"connect 127.0.0.1 "+port,
				"var x",
				"x = bind simX",
				"var y = bind simX",	
				"x = "+rand*2,
				"disconnect",
				"return y"	
		};
		
		if(MyInterpreter.interpret(test3)!=rand*2)
			System.out.println("failed test3 (-20)");

		String[] test4={
				"openDataServer "+ (port+1)+" 10",
				"connect 127.0.0.1 "+port,
				"var x = bind simX",
				"var y = bind simY",	
				"var z = bind simZ",	
				"x = "+rand*2,
				"disconnect",
				"return x+y*z"	
		};
		
		if(MyInterpreter.interpret(test4)!=sim.simX+sim.simY*sim.simZ)
			System.out.println("failed test4 (-20)");
				
		String[] test5={
				"var x = 0",
				"var y = "+rand,
				"while x < 5 {",
				"	y = y + 2",
				"	x = x + 1",
				"}",
				"return y"	
		};
		
		if(MyInterpreter.interpret(test5)!=rand+2*5)
			System.out.println("failed test5 (-20)");
		
		sim.close();
		System.out.println("done");
	}

}
