/**
 * Derived class that represents an assignment statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class Assignment extends Statement {
    private Token vbl;
    private Expression expr;
    
    /**
     * Reads in a assignment statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Assignment(TokenStream input) throws Exception {
        this.vbl = input.next();
        if (this.vbl.getType() != Token.Type.IDENTIFIER) {
	    throw new Exception("SYNTAX ERROR: Illegal lhs of assignment statement (" + this.vbl + ")");
        } 
        
        if (!input.next().toString().equals("=")) {
	    throw new Exception("SYNTAX ERROR: Malformed assignment statement (expecting '=')");
        } 

        this.expr = new Expression(input);
        if (!input.next().toString().equals(";")) {
	    throw new Exception("SYNTAX ERROR: Missing ';' in assignment to " + this.vbl);            
        }
    }
    
    /**
     * Executes the current assignment statement.
     */
    public void execute() throws Exception {
    	if (Interpreter.MEMORY.isDeclared(vbl)) {
    		
	        DataValue newValue = this.expr.evaluate();
	        if (newValue.getType() == Interpreter.MEMORY.getType(vbl)) {
	        	Interpreter.MEMORY.setValue(this.vbl, this.expr.evaluate());
	        } else {
	        	throw new Exception("RUN TIME ERROR: assignment type mismatch");
	        }
	        
    	} else {
    		throw new Exception("RUN TIME ERROR: variable in assignment not declared");
    	}
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return this.vbl + " = " + this.expr + " ;";
    }
}