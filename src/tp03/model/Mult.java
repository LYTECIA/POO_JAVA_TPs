package tp03.model;

/**
 * @inv
 *     arity() == 2
 *     toString().equals("×")
 *     value(x, y) == x × y
 */
class Mult implements Token {
    
    @Override
    public int arity() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "×";
    }
    
    @Override
    public int value(int... operands) {
        if (operands.length != arity()) {
            throw new AssertionError();
        }
        
        return operands[0] * operands[1];
    }
}
