package Interpreter.Commands.Fundation;

import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.util.AssignVariableCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CodeBlock {
    private String code;


    public CodeBlock(String code) {
        this.code = code;
    }

    // Returns the first command in the block.
    public Command<?> pop() throws NoCommandsLeftException, CommandNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InvalidArgumentsException, InterpreterException, InvalidConditionFormatException {
        if(this.code.equals("")) {
            throw new NoCommandsLeftException();
        }
        String commandName = shiftWord();
        Class<? extends Command<?>> type = CommandTranslator.getInstance().translate(commandName);
        Command<?> cmd = type.getDeclaredConstructor().newInstance();
        if(UnaryCommand.class.isAssignableFrom(type) || AssignVariableCommand.class.isAssignableFrom(type)) {
            cmd.setArgs(commandName, shiftLine());
        }
        if(ConditionalCommand.class.isAssignableFrom(type)) {
            Condition con = Condition.parse(shiftTo('{'));
            CodeBlock cob = new CodeBlock(shiftBlock('{', '}'));
            ((ConditionalCommand)cmd).setCondition(con);
            ((ConditionalCommand)cmd).setCodeBlock(cob);
        }

        return cmd;
    }

    public void execute() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, InvalidArgumentsException, CommandNotFoundException, InterpreterException, InvalidConditionFormatException, NoCommandsLeftException {
        String code_backup = this.code;
        while(!this.code.equals("")) {
            pop().execute();
        }
        this.code = code_backup;
    }

    private String shiftWord(){
        String[] tmp = code.split("[ +\n]", 2); // Find the first space or \n
        String ret = tmp[0];
        if(tmp.length > 1) {
            this.code = tmp[1];
        }else {
            this.code = "";
        }
        return ret;
    }

    private  String shiftBlock(char opener, char closer) throws InterpreterException {
        String ret = null;
        int openers = 1;
        int startIndex = code.indexOf(opener);
        int endIndex = -1;
        for (int i = code.indexOf(opener)+1; i < code.length(); i++) {
            if(code.charAt(i) == opener) {
                openers++;
            } else if(code.charAt(i) == closer){
                openers--;
            }
            if(openers == 0) {
                endIndex = i;
                break;
            }
        }

        if(endIndex == -1) {
            throw new InterpreterException();
        }
        ret = code.substring(startIndex + 1, endIndex);
        code = code.substring(endIndex + 1);

        return ret;
    }

    private String shiftLine() {
        String[] tmp = code.split("\n", 2);
        String ret = tmp[0];
        if(tmp.length > 1) {
            this.code = tmp[1];
        }else {
            this.code = "";
        }
        return ret;
    }

    private String shiftTo(char c) {
        int idx = code.indexOf(c);
        String ret = code.substring(0, idx);
        code = code.substring(idx);
        return ret;
    }


}
