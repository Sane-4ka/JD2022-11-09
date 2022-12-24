package by.it.cherny.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

abstract class Var implements Operation {
    static final Map<String, Var> vars = new HashMap<>();

    public static Var create(String stringValue) {
        if (stringValue.matches(Patterns.SCALAR)){
            return new Scalar(stringValue);
        }
        if (stringValue.matches(Patterns.VECTOR)){
            return new Vector(stringValue);
        }
        if (stringValue.matches(Patterns.MATRIX)){
            return new Matrix(stringValue);
        }
        return vars.get(stringValue);
    }

    public static Var save(String name, Var value) {
        vars.put(name, value);
        return value;
    }

    @Override
    public String toString() {
        return "unknown variable (abstract stub)";
    }

    @Override
    public Var add(Var other) {
        System.out.printf("Operation %s + %s is imposible\n", this, other);
        return null;
    }

    @Override
    public Var sub(Var other) {
        System.out.printf("Operation %s - %s is imposible\n", this, other);
        return null;
    }

    @Override
    public Var mul(Var other) {
        System.out.printf("Operation %s * %s is imposible\n", this, other);
        return null;
    }

    @Override
    public Var div(Var other) {
        System.out.printf("Operation %s / %s is imposible\n", this, other);
        return null;
    }
}