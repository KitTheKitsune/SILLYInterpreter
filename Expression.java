/**
 * Derived class that represents an expression in the SILLY language.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class Expression {
    private Term term1;
    private Token op;
    private Term term2;

    /**
     * Creates an expression from the specified TokenStream.
     *   @param input the TokenStream from which the program is read
     */
    public Expression(TokenStream input) throws Exception {
        if (input.lookAhead().getType() == Token.Type.UNARY_OP) {
            this.op = input.next();
            this.term2 = new Term(input);
        } else {
            this.term1 = new Term(input);
            if (input.lookAhead().getType() == Token.Type.BINARY_OP) {
                this.op = input.next();
                this.term2 = new Term(input);
            }
        }
    }

    /**
     * Evaluates the current expression.
     *   @return the value represented by the expression
     */
    public DataValue evaluate() throws Exception {
        if (this.op == null) {
            return this.term1.evaluate();
        } else if (this.op.getType() == Token.Type.UNARY_OP) {
            DataValue rhs = this.term2.evaluate();
            
            if (this.op.toString().equals("#chars")) {
                if (rhs.getType() == DataValue.Type.STRING_VALUE) {
                    String s2 = rhs.toString();
                    return new IntegerValue(s2.length() - 2);
                }
            }
            if (this.op.toString().equals("not")) {
            	if (rhs.getType() == DataValue.Type.BOOLEAN_VALUE) {
            		return new BooleanValue(!(boolean)rhs.getValue());
            	}
            }
        } else if (this.op.getType() == Token.Type.BINARY_OP) {
            DataValue lhs = this.term1.evaluate();
            DataValue rhs = this.term2.evaluate();

            if (lhs.getType() == DataValue.Type.STRING_VALUE
                    && rhs.getType() == DataValue.Type.INTEGER_VALUE) {
                if (op.toString().equals("@index")) {
                    String str = lhs.toString();
                    int index = ((Integer) (rhs.getValue()));
                    if (index >= 0 || index < str.length() - 2) {
                        return new StringValue("\"" + str.substring(index + 1, index + 2) + "\"");
                    }
                }
            } else if (lhs.getType() == rhs.getType()) {
                if (op.toString().equals("==")) {
                    return new BooleanValue(lhs.compareTo(rhs) == 0);
                } else if (op.toString().equals("!=")) {
                    return new BooleanValue(lhs.compareTo(rhs) != 0);
                } else if (op.toString().equals(">")) {
                    return new BooleanValue(lhs.compareTo(rhs) > 0);
                } else if (op.toString().equals(">=")) {
                    return new BooleanValue(lhs.compareTo(rhs) >= 0);
                } else if (op.toString().equals("<")) {
                    return new BooleanValue(lhs.compareTo(rhs) < 0);
                } else if (op.toString().equals("<=")) {
                    return new BooleanValue(lhs.compareTo(rhs) <= 0);
                } else if (lhs.getType() == DataValue.Type.STRING_VALUE) {
                    if (op.toString().equals("+")) {
                        String str1 = lhs.toString();
                        String str2 = rhs.toString();
                        return new StringValue(str1.substring(0, str1.length() - 1)
                                + str2.substring(1));
                    }
                } else if (lhs.getType() == DataValue.Type.INTEGER_VALUE) {
                    int num1 = ((Integer) (lhs.getValue()));
                    int num2 = ((Integer) (rhs.getValue()));

                    if (op.toString().equals("+")) {
                        return new IntegerValue(num1 + num2);
                    } else if (op.toString().equals("-")) {
                        return new IntegerValue(num1 - num2);
                    } else if (op.toString().equals("*")) {
                        return new IntegerValue(num1 * num2);
                    } else if (op.toString().equals("/")) {
                        return new IntegerValue(num1 / num2);
                    } else if (op.toString().equals("%")) {
                        return new IntegerValue(num1 % num2);
                    } else if (op.toString().equals("^")) {
                        return new IntegerValue((int)Math.pow(num1, num2));
                    }
                } else if (lhs.getType() == DataValue.Type.BOOLEAN_VALUE) {
                	boolean left = ((boolean)(lhs.getValue()));
                	boolean right = ((boolean)(rhs.getValue()));
                	
                	if (op.toString().equals("and")) {
                		return new BooleanValue(left && right);
                	}else if (op.toString().equals("or")) {
                		return new BooleanValue(left || right);
                	}
                }
            }
        }
        return null;
    }

    /**
     * Converts the current expression into a String.
     *   @return the String representation of this expression
     */
    public String toString() {
        String result = "";
        if (this.term1 != null) {
            result += this.term1.toString();
        }
        if (this.op != null) {
            result += " " + this.op.toString();
        }
        if (this.term2 != null) {
            result += " " + this.term2.toString();
        }
        if (result.length() > 0 && result.charAt(0) == ' ') {
            result = result.substring(1);
        }
        return result;
    }
}