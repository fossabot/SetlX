package org.randoom.setlx.types;

import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.UndefinedOperationException;
import org.randoom.setlx.utilities.State;

/**
 * This base class provides some functionality for all numeric values.
 */
public abstract class NumberValue extends Value {

    /* arithmetic operations */

    @Override
    public abstract NumberValue absoluteValue(final State state);

    @Override
    public abstract NumberValue ceil(final State state) throws UndefinedOperationException;

    @Override
    public abstract Value       difference(final State state, final Value subtrahend) throws SetlException;

    @Override
    public abstract NumberValue floor(final State state) throws UndefinedOperationException;

    @Override
    public abstract NumberValue minus(final State state) throws SetlException;

    @Override
    public          Value       power(final State state, final Value exponent) throws SetlException {
        if (exponent.isInteger() == SetlBoolean.TRUE && exponent.jIntConvertable()) {
            return this.power(state, exponent.jIntValue());
        } else if (exponent.isRational() == SetlBoolean.TRUE) {
            return this.power(state, exponent.jDoubleValue());
        } else if (exponent.isDouble() == SetlBoolean.TRUE) {
            return this.power(state, exponent.jDoubleValue());
        } else if (exponent instanceof SetlSet && this.equalTo(Rational.TWO)) {
            return ((SetlSet) exponent).powerSet(state);
        } else if (exponent instanceof Term) {
            return ((Term) exponent).powerFlipped(state, this);
        } else {
            throw new IncompatibleTypeException(
                "Exponent of '" + this + " ** " + exponent + "' is not a number."
            );
        }
    }

    /**
     * Raise this value to the power of another.
     *
     * @param state          Current state of the running setlX program.
     * @param exponent       Value to raise by.
     * @return               This raised by the power of exponent.
     * @throws SetlException Thrown in case of some (user-) error.
     */
    protected abstract NumberValue power(final State state, final int    exponent) throws SetlException;
    /**
     * Raise this value to the power of another.
     *
     * @param state          Current state of the running setlX program.
     * @param exponent       Value to raise by.
     * @return               This raised by the power of exponent.
     * @throws SetlException Thrown in case of some (user-) error.
     */
    protected abstract NumberValue power(final State state, final double exponent) throws SetlException;

    @Override
    public    abstract Value       product(final State state, final Value multiplier) throws SetlException;

    @Override
    public    abstract Value       quotient(final State state, final Value divisor) throws SetlException;

    @Override
    public    abstract NumberValue round(final State state) throws SetlException;

    @Override
    public    abstract Value       sum(final State state, final Value summand) throws SetlException;

    /* comparisons */

    @Override
    public final    SetlBoolean isLessThan(final State state, final Value other) throws IncompatibleTypeException {
        if (this == other) {
            return SetlBoolean.FALSE;
        } else if (other instanceof NumberValue) {
            return SetlBoolean.valueOf(this.compareTo(other) < 0);
        } else {
            throw new IncompatibleTypeException(
                "Right-hand-side of '" + this + " < " + other + "' is not a number."
            );
        }
    }

    @Override
    public SetlBoolean isNumber() {
        return SetlBoolean.TRUE;
    }

}

