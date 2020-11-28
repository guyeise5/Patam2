package Interpreter.Commands.Fundation;

import Interpreter.Commands.util.Variable;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public final class VariablesFactory implements Variables {

    private static VariablesFactory instance;

    public static VariablesFactory getInstance() {
        if (instance == null) {
            instance = new VariablesFactory();
        }
        return instance;
    }

    final private Map<String, Variable> variables;

    /**
     * Variables class is a singleton, do not allow access
     */
    private VariablesFactory() {
        this.variables = new IdentityHashMap<>();
    }

    @Override
    public void createVariable(String variableName) {
        ensureEmptyVariable(variableName);
        Variable reference = new Variable(variableName, null);
        variables.put(variableName, reference);
    }

    @Override
    public void assignValue(String var, double value) {
        ensureExistingVariable(var);
        Variable reference = variables.get(var);
        reference.value = value;
    }

    @Override
    public double getValue(String variable) {
        ensureExistingVariable(variable);
        return variables.get(variable).value;
    }

    @Override
    public void bind(String existingVariable, String newVariable) {
        ensureExistingVariable(existingVariable);
        ensureEmptyVariable(newVariable);
        Variable reference = variables.get(existingVariable);
        variables.put(newVariable, reference);
    }

    @Override
    public Collection<Variable> allVariables() {
        return variables.values();
    }

    public boolean containsVariable(String var) {
        return variables.containsKey(var);
    }

    private void ensureExistingVariable(String var) {
        if (!containsVariable(var)) {
            throw new RuntimeException("no variable " + var + " found");
        }
    }

    private void ensureEmptyVariable(String var) {
        if (containsVariable(var)) {
            throw new RuntimeException("variable " + var + " already exists");
        }
    }


}
