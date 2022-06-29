/**
 * Derived class that represents a while statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class While extends Statement {
    private Expression expr;
    private Compound body;  
    
    /**
     * Reads in a while statement from the specified stream
     *   @param input the stream to be read from
     */
    public While(TokenStream input) throws Exception {
        Token keyword = input.next();
        if (!keyword.toString().equals("while")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        this.expr = new Expression(input);       
        this.body = new Compound(input);
    }

    /**
     * Executes the current while statement.
     */
    public void execute() throws Exception {
        while (((Boolean) (this.expr.evaluate().getValue()))) {
            this.body.execute();
        }
    }

    /**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "while " + this.expr + "\n" + this.body;
    }
}