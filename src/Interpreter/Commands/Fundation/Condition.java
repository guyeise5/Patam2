package Interpreter.Commands.Fundation;

import Expression.Expression;
import Interpreter.CalcExpresion;
import Interpreter.Commands.Exceptions.CalculateException;
import Interpreter.Commands.Exceptions.InvalidConditionFormatException;
import Interpreter.Commands.Exceptions.ParseException;

public class Condition {
    private String text;

    private Expression left, right;
    private Operator operator;

    public Boolean calculate() {
        double c_left = left.calculate();
        double c_right = right.calculate();
        return switch (operator) {
            case GT -> c_left > c_right;
            case LT -> c_left < c_right;
            case EQ -> c_left == c_right;
            case NE -> c_left != c_right;
            case GE -> c_left >= c_right;
            case LE -> c_left <= c_right;
        };
    }
    
    public static Condition parse(String text) throws InvalidConditionFormatException, ParseException {
        Operator operator  = null;
        for (Operator opr: Operator.values() ) {
            if(text.contains(opr.value)) {
                operator = opr;
                break;
            }
        }

        if(operator == null) {
            throw new InvalidConditionFormatException();
        }

        String[] expression = text.split(operator.getValue());

        Condition ret = new Condition();
        ret.left = CalcExpresion.parseExpression(expression[0]);
        ret.right = CalcExpresion.parseExpression(expression[1]);
        ret.operator = operator;

        return ret;
    }

    // The operators are sorted meaning
    // you don't have to worry about
    // find '<' before '<='.
    private enum Operator {
        GE(">="),
        LE("<="),
        NE("!="),
        GT(">"),
        LT("<"),
        EQ("=");


        private final String value;
        Operator(String s) {
            this.value = s;
        }

        public String getValue() {
            return this.value;
        }
    }
    
}
