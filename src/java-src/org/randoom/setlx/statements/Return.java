package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.utilities.ReturnMessage;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/**
 * Implementation of the return statement.
 *
 * grammar rule:
 * statement
 *     : [...]
 *     | 'return' anyExpr? ';'
 *     ;
 *
 * implemented here as:
 *                =======
 *                result
 */
public class Return extends Statement {
    // functional character used in terms
    private final static String FUNCTIONAL_CHARACTER = generateFunctionalCharacter(Return.class);

    private final Expr result;

    /**
     * Create a new return statement.
     *
     * @param result Expression to evaluate before returning.
     */
    public Return(final Expr result) {
        this.result = result;
    }

    @Override
    public ReturnMessage execute(final State state) throws SetlException {
        if (result != null) {
            // increase callStackDepth
            ++(state.callStackDepth);

            return ReturnMessage.createMessage(result.eval(state));
        } else {
            return ReturnMessage.OM;
        }
    }

    @Override
    public void collectVariablesAndOptimize (
        final List<String> boundVariables,
        final List<String> unboundVariables,
        final List<String> usedVariables
    ) {
        if (result != null) {
            result.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
        }
    }

    /* string operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        state.appendLineStart(sb, tabs);
        sb.append("return");
        if (result != null){
            sb.append(" ");
            result.appendString(state, sb, 0);
        }
        sb.append(";");
    }

    /* term operations */

    @Override
    public Term toTerm(final State state) {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 1);
        if (this.result != null) {
            result.addMember(state, this.result.toTerm(state));
        } else {
            result.addMember(state, new SetlString("nil"));
        }
        return result;
    }

    /**
     * Convert a term representing an return statement into such a statement.
     *
     * @param term                     Term to convert.
     * @return                         Resulting return statement.
     * @throws TermConversionException Thrown in case of an malformed term.
     */
    public static Return termToStatement(final Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            Expr expr = null;
            if (! term.firstMember().equals(new SetlString("nil"))) {
                expr = TermConverter.valueToExpr(term.firstMember());
            }
            return new Return(expr);
        }
    }
}

