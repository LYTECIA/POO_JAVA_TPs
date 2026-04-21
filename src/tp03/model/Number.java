package tp03.model;

/**
 * @inv
 * arity() == 0
 * value() == la valeur du nombre
 * toString().equals("la valeur du nombre")
 */
public class Number implements Token {
	     // ATTRIBUTS
         private final int val;
         
         // CONSTRUCTEUR
         Number(int val) {
             this.val = val;
         }
	
	    @Override
	    public int arity() {
	        return 0;
	    }
	    
	    @Override
	    public String toString() {
	        return String.valueOf(val);
	    }
	    
	    @Override
	    public int value(int... operands) {
	        if (operands.length != arity()) {
	            throw new AssertionError();
	        }
	        
	        return val;
	    }
}
