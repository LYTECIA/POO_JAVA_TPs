package tp03.util;

import java.util.ArrayList;
import java.util.List;

public class Stack<E> {
    private final List<E> data;
    
    public Stack() {
        data = new ArrayList<>();
    }
    
    public Stack(List<E> list) {
        data = new ArrayList<>(list);
    }
    
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    public E peek() {
        if (isEmpty()) {
            throw new AssertionError();
        }
        
        return data.getLast();
    }
    
    public void pop() {
        if (isEmpty()) {
            throw new AssertionError();
        }
        
        data.removeLast();
    }
    
    public void push(E e) {
        data.add(e);
    }
}
