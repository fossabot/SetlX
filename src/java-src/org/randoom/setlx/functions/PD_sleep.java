package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Rational;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

// sleep(time_in_ms)             : pause execution for a number of milliseconds

public class PD_sleep extends PreDefinedProcedure {
    public final static PreDefinedProcedure DEFINITION = new PD_sleep();

    private PD_sleep() {
        super();
        addParameter("time_in_ms");
    }

    @Override
    public Value execute(final State state, final List<Value> args, final List<Value> writeBackVars) throws IncompatibleTypeException {
        if (args.get(0).isInteger() == SetlBoolean.FALSE || args.get(0).compareTo(Rational.ONE) < 0 ) {
            throw new IncompatibleTypeException(
                "Time_in_ms-argument '" + args.get(0) + "' is not an integer >= 1."
            );
        }

        try {
            final int n = args.get(0).jIntValue();
            Thread.sleep(n);
        } catch (final Exception e) {
            // don't care if anything happens here...
        }

        return Om.OM;
    }
}

