package Interpreter.Commands.Fundation;

import Interpreter.Commands.util.Variable;

import java.util.Collection;
import java.util.Set;

public interface Variables {

    void createVariable(String variableName);

    void assignValue(String var, double value);

    boolean containsVariable(String variableName);

    double getValue(String variable);

    Variable getVariable(String variable);

    void bind(String existingVariable, String newVariable);

    Collection<Variable> allVariables();

    void dropAllVariables();

}
