package Interpreter.Commands.util;

import Interpreter.Commands.Fundation.Command;
import Interpreter.Commands.Fundation.ConditionalCommand;

public class IF extends ConditionalCommand<Void> {
    @Override
    public Void execute() {
        if(this.getCondition().calculate()) {
            this.getCommands().forEach(Command::execute);
        }
        return null;
    }
}
