/**
 * Class that declares (and assigns) a global variable.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class VarDeclaration extends Statement {
    private Token varTypeToken;
    private DataValue.Type varType;
    private Token variable;
    private Assignment assign;

    /**
     * Constructs am object representing a global variable declaration. 
     *   @param input the stream to be read from
     */
    public VarDeclaration(TokenStream input) throws Exception {
        this.varTypeToken = input.next();
        if (this.varTypeToken.toString().equals("int")) {
            this.varType = DataValue.Type.INTEGER_VALUE;
        }
        else if (this.varTypeToken.toString().equals("str")) {
            this.varType = DataValue.Type.STRING_VALUE;
        }
        else if (this.varTypeToken.toString().equals("boo")) {
            this.varType = DataValue.Type.BOOLEAN_VALUE;
        }
        else {
            throw new Exception("SYNTAX ERROR: Invalid variable declaration (unknown type)");
        }
        this.variable = input.lookAhead();
        this.assign = new Assignment(input);
    }
    
    /**
     * Executes the current assignment statement.
     */
    public void execute() throws Exception {
    	if (!(Interpreter.MEMORY.isDeclared(this.variable))) {
        Interpreter.MEMORY.declare(this.variable, this.varType);
        this.assign.execute();
    	} else {
    		throw new Exception("RUN TIME ERROR: variable in declaration already declared");
    	}
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return varTypeToken + " " + this.assign;
    }
}
