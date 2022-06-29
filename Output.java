import java.util.ArrayList;//

/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class Output extends Statement {
    private ArrayList<Expression> exprs = new ArrayList<Expression>();
    
    /**
     * Reads in an output statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Output(TokenStream input) throws Exception {
        if (!input.next().toString().equals("output")) {
            throw new Exception("SYNTAX ERROR: Malformed output statement");
        }
    
        while(true) {
        	if(input.lookAhead().toString().equals(";")) {
        		input.next();
        		break;
        	}
        	if(this.exprs.size() > 0) {
        		if(input.lookAhead().toString().equals(",")) {
        			input.next();
        		}else {
        			throw new Exception("SYNTAX ERROR: comma missing to separate expressions");
        		}
        	}
        	this.exprs.add(new Expression(input));
        }
 
    }

    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
    	for (int i=0;i<exprs.size();i++) {
    		Expression expr = this.exprs.get(i);
	        DataValue result = expr.evaluate();
	       
		        if (result.getType() == DataValue.Type.STRING_VALUE) {
		            String str = (String)(result.getValue());
		            System.out.println(str.substring(1, str.length()-1));
		        }
		        else {
		            System.out.println(result.getValue());
		        }
    	}
    }

    /**
     * Converts the current output statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
    	String out = "";
    	for (int i = 0; i < this.exprs.size(); i++) {
    		out += exprs.get(i).toString();
    	}
        return "output " + out +  ";";
    }
}