package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.exceptions.UndefinedOperationException;
import org.randoom.setlx.types.SetlDouble;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

/**
 *  mathConst(name_of_constant) : get the value of a mathematical constant (currently pi, e and infinity)
 */
public class PD_mathConst extends PreDefinedProcedure {
    /** Definition of the PreDefinedProcedure `mathConst'. */
    public final static PreDefinedProcedure DEFINITION = new PD_mathConst();

    private PD_mathConst() {
        super();
        addParameter("name_of_constant");
    }

    @Override
    public Value execute(final State state, final List<Value> args, final List<Value> writeBackVars)
        throws UndefinedOperationException, IncompatibleTypeException
    {
        final Value name = args.get(0);
        if (       name.getUnquotedString().equalsIgnoreCase( "e"        )) {
            return SetlDouble.E;
        } else if (name.getUnquotedString().equalsIgnoreCase( "pi"       )) {
            return SetlDouble.PI;
        } else if (name.getUnquotedString().equalsIgnoreCase( "infinity" )) {
            return SetlDouble.POSITIVE_INFINITY;
        } else {
            throw new IncompatibleTypeException("Name-argument '" + name + "' is not a known constant or not a string.");
        }
    }
}

