package org.randoom.setlx.statementBranches;

import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.statements.Block;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/**
 * The default case in a switch statement.
 *
 * grammar rule:
 * statement
 *     : [...]
 *     | 'switch' '{' ('case' condition ':' block)* ('default' ':' block)? '}'
 *     ;
 *
 * implemented here as:
 *                                                                 =====
 *                                                               statements
 */
public class SwitchDefaultBranch extends SwitchAbstractBranch {
    // functional character used in terms
    private final static String FUNCTIONAL_CHARACTER = generateFunctionalCharacter(SwitchDefaultBranch.class);

    private final Block statements;

    /**
     * Create new default-branch.
     *
     * @param statements Statements to execute.
     */
    public SwitchDefaultBranch(final Block statements) {
        this.statements = statements;
    }

    @Override
    public boolean evalConditionToBool(final State state) {
        return true;
    }

    @Override
    public Block getStatements() {
        return statements;
    }

    @Override
    public void collectVariablesAndOptimize (
        final List<String> boundVariables,
        final List<String> unboundVariables,
        final List<String> usedVariables
    ) {
        statements.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
    }

    /* string operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        state.appendLineStart(sb, tabs);
        sb.append("default:");
        sb.append(state.getEndl());
        statements.appendString(state, sb, tabs + 1);
        sb.append(state.getEndl());
    }

    /* term operations */

    @Override
    public Term toTerm(final State state) {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 1);
        result.addMember(state, statements.toTerm(state));
        return result;
    }

    /**
     * Convert a term representing a default-branch into such a branch.
     *
     * @param term                     Term to convert.
     * @return                         Resulting branch.
     * @throws TermConversionException Thrown in case of an malformed term.
     */
    public static SwitchDefaultBranch termToBranch(final Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Block block = TermConverter.valueToBlock(term.firstMember());
            return new SwitchDefaultBranch(block);
        }
    }

    /**
     * Get the functional character used in terms.
     *
     * @return functional character used in terms.
     */
    /*package*/ static String getFunctionalCharacter() {
        return FUNCTIONAL_CHARACTER;
    }
}

