package tp03.model;

/**
 * @inv
 * arity() == 1
 * toString.equals("x")
 * value(x) == x
 */
public class Number implements Token {
	
	    @Override
	    public int arity() {
	        return 1 ;
	    }
	    
	    @Override
	    public String toString() {
	        return " ";
	    }
	    
	    @Override
	    public int value(int... operands) {
	        if (operands.length != arity()) {
	            throw new AssertionError();
	        }
	        
	        return operands[0] ;
	    }
}
