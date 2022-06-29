import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class MemorySpace {
    private HashMap<Token, VarPair> stack;
    private ArrayList<DataValue> heap;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stack = new HashMap<Token, VarPair>();
        this.heap = new ArrayList<DataValue>();
    }

    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declare(Token variable, DataValue.Type varType) {
        this.stack.put(variable, new VarPair(varType, null));
    }

    /**
     * Determines whether a variable is already declared.
     *   @param variable the variable to look up
     *   @return true if the variable is declared (and active)
     */
    public boolean isDeclared(Token variable) {
        return this.stack.containsKey(variable);
    }

    /**
     * Determines the type associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the type associated with that variable
     */    
    public DataValue.Type getType(Token variable) {
        return this.stack.get(variable).getType();
    }

    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public DataValue getValue(Token variable) {
        VarPair pair = this.stack.get(variable);
        if (pair.getType() == DataValue.Type.STRING_VALUE) {
            return this.heap.get((Integer)(pair.getValue().getValue()));
        }
        else {
            return pair.getValue();
        }
    }
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void setValue(Token variable, DataValue val)  {
        VarPair pair = this.stack.get(variable);
        if (pair.getType() == DataValue.Type.STRING_VALUE) {
            this.heap.add(val);
            pair.setValue(new IntegerValue(heap.size()-1));
        }
        else {
            pair.setValue(val);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Hidden class for storing variable type-value pairs.
     */
    private class VarPair {
        DataValue.Type varType;
        DataValue value;
    
        public VarPair(DataValue.Type type, DataValue val) {
            this.varType = type;
            this.value = val;
        }
    
        public DataValue.Type getType() {
            return this.varType;
        }
   
        public DataValue getValue() {
            return this.value;
        }
    
        public void setValue(DataValue val) {
            this.value = val;
        }
    }
}