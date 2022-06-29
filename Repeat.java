/**
 * Derived class that represents a repeat statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class Repeat extends Statement {
    private Expression expr;
    private Compound body;  
    
    /**
     * Reads in a repeat statement from the specified stream
     *   @param input the stream to be read from
     */
    public Repeat(TokenStream input) throws Exception {
        if (!input.next().toString().equals("repeat")) {
            throw new Exception("SYNTAX ERROR: Malformed repeat statement");
        }
        this.expr = new Expression(input);   
        this.body = new Compound(input);
    }

    /**
     * Executes the current repeat statement.
     */
    public void execute() throws Exception {
        DataValue repVal = this.expr.evaluate();
        int numReps = ((Integer) repVal.getValue());
        for (int i = 0; i < numReps; i++) {
            this.body.execute();
        }
    }

    /**
     * Converts the current repeat statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "repeat " + this.expr + "\n" + this.body;
    }
}