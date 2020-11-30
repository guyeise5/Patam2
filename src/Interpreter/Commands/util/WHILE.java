package Interpreter.Commands.util;

import Interpreter.Commands.Fundation.Command;
import Interpreter.Commands.Fundation.ConditionalCommand;

public class WHILE extends ConditionalCommand<Void> {
    @Override
    public Void execute() {
        while(this.getCondition().calculate()) {
            this.getCommands().forEach(Command::execute);
        }

        return null;
    }
}
