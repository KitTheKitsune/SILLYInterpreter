/**
 * Class that represents a term (subexpression) in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class Term {
    private Token tok;
    private Expression expr;
    
    /**
     * Creates a term from the specified TokenStream.
     *   @param input the TokenStream from which the program is read
     */
    public Term(TokenStream input) throws Exception {
        this.tok = input.next();
        if (tok.toString().equals("(")) {
            this.expr = new Expression(input);
            if (!input.next().toString().equals(")")) {
                throw new Exception("SYNTAX ERROR: malformed expression");
            }
        }        
        else if (this.tok.getType() != Token.Type.IDENTIFIER &&
                  this.tok.getType() != Token.Type.INTEGER_LITERAL &&    
                  this.tok.getType() != Token.Type.STRING_LITERAL &&
                  this.tok.getType() != Token.Type.BOOLEAN_LITERAL) {
            throw new Exception("SYNTAX ERROR: malformed expression");
        }
    }
    
   /**
     * Evaluates the current term.
     *   @return the value represented by the expression
     */
    public DataValue evaluate() throws Exception {
        if (this.expr != null) {
            return this.expr.evaluate();
        }
        else if (tok.getType() == Token.Type.IDENTIFIER) {
            return Interpreter.MEMORY.getValue(tok);
        } else if (tok.getType() == Token.Type.INTEGER_LITERAL) {
            return new IntegerValue(Integer.parseInt(tok.toString()));
        } else if (tok.getType() == Token.Type.STRING_LITERAL) {
            return new StringValue(tok.toString());
        } else if (tok.getType() == Token.Type.BOOLEAN_LITERAL) {
            return new BooleanValue(Boolean.valueOf(tok.toString()));
        }
        return null;
    }

    /**
     * Converts the current term into a String.
     *   @return the String representation of this expression
     */
    public String toString() {
        if (this.expr != null) {
            return "( " + this.expr.toString() + " )";
        }
        else {
            return this.tok.toString();
        }
    }
}
    