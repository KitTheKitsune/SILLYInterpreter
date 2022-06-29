
public class If extends Statement {
	private Expression expr;
	private Compound ifBod;
	private Compound elseBod;
	
	public If(TokenStream input) throws Exception {
		Token keyword = input.next();
        if (!keyword.toString().equals("if")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        this.expr = new Expression(input);       
        this.ifBod = new Compound(input);
        keyword = input.next();
        if (!keyword.toString().equals("else")) {
        	System.out.println(input.toString());
        	throw new Exception("SYNTAX ERROR: Malformed else statement");
        }
        this.elseBod = new Compound(input);
	}
	

	@Override
	public void execute() throws Exception {
		if (((Boolean) (this.expr.evaluate().getValue()))) {
            this.ifBod.execute();
        }else {
        	this.elseBod.execute();
        }
	}

	@Override
	public String toString() {
		return null;
	}

}
