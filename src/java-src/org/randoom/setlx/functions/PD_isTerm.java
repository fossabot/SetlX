package org.randoom.setlx.functions;

import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

// isTerm(value)           : test if value-type is term

public class PD_isTerm extends PreDefinedProcedure {
    public final static PreDefinedProcedure DEFINITION = new PD_isTerm();

    private PD_isTerm() {
        super();
        addParameter("value");
    }

    @Override
    public Value execute(final State state, final List<Value> args, final List<Value> writeBackVars) {
        return args.get(0).isTerm();
    }
}

