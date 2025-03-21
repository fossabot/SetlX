package org.randoom.setlx.functions;

import org.randoom.setlx.types.Rational;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

// nCPUs()                       : get number of CPUs in current system

public class PD_nCPUs extends PreDefinedProcedure {
    public final static PreDefinedProcedure DEFINITION = new PD_nCPUs();

    private PD_nCPUs() {
        super();
    }

    @Override
    public Value execute(final State state, final List<Value> args, final List<Value> writeBackVars) {
        return Rational.valueOf(state.getNumberOfCores());
    }
}

