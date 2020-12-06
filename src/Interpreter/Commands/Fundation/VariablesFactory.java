package Interpreter.Commands.Fundation;

import Interpreter.Commands.util.Variable;

import java.util.*;

public final class VariablesFactory implements Variables {

    private static VariablesFactory instance;

    public static VariablesFactory getInstance() {
        if (instance == null) {
            instance = new VariablesFactory();
        }
        return instance;
    }

    private Map<String, Variable> variables;

    /**
     * Variables class is a singleton, do not allow access
     */
    private VariablesFactory() {
        clean();
    }

    @Override
    public synchronized void createVariable(String variableName) {
        ensureEmptyVariable(variableName);
        Variable reference = new Variable(variableName, null);
        variables.put(variableName, reference);
    }

    @Override
    public synchronized void assignValue(String var, double value) {
        ensureExistingVariable(var);
        Variable reference = variables.get(var);
        reference.value = value;
    }

    @Override
    public synchronized double getValue(String variable) {
        return getVariable(variable).value;
    }

    @Override
    public synchronized Variable getVariable(String variable) {
        ensureExistingVariable(variable);
        return variables.get(variable);
    }

    @Override
    public synchronized void bind(String existingVariable, String newVariable) {
        ensureExistingVariable(existingVariable);
        ensureEmptyVariable(newVariable);
        Variable reference = variables.get(existingVariable);
        variables.put(newVariable, reference);
    }

    @Override
    public synchronized Collection<Variable> allVariables() {
        return variables.values();
    }


    public synchronized boolean containsVariable(String var) {
        return variables.containsKey(var);
    }

    @Override
    public synchronized void clean() {
        this.variables = new HashMap<>();
    }

    private synchronized void ensureExistingVariable(String var) {
        if (!containsVariable(var)) {
            throw new RuntimeException("no variable " + var + " found");
        }
    }

    private synchronized void ensureEmptyVariable(String var) {
        if (containsVariable(var)) {
            throw new RuntimeException("variable " + var + " already exists");
        }
    }


}
