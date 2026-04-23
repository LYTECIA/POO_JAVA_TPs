package tp04;
import java.util.Random;
import util.Contract;
public class Dice implements IDice {
	
	//ATTRIBUTS

    private int nbOfSides;

    private boolean isTaken;

    private int value;
	
    //CONSTRUCTEUR
    
    public Dice(int nbOfSides) {
    	Contract.checkCondition(nbOfSides >= MIN_SIDES_NB , "le dé doit avoir minimum 2 faces " );
    	
    	isTaken = false;
    	value = 1;
    	this.nbOfSides = nbOfSides;
    }
    
    //REQUETES
    
    public int nbOfSides() {
    	return nbOfSides;
    }
    
    public boolean isTaken(){
    	return isTaken;
    }
    
    public int value() {
    	Contract.checkCondition(isTaken == false , "le dé ne doit pas etre déja  pris" );

    	return value;
    }
    
    //COMMANDES
    
    public void roll() {
    	Contract.checkCondition(isTaken == true , "le dé doit etre déja  pris" );
    	
    	isTaken = false;
    	Random r = new Random();
    	value = r.nextInt(nbOfSides) +1;
    	
    }
    
    public void take() {
    	Contract.checkCondition(isTaken == false , "le dé ne doit pas etre déja  pris" );
    	
    	isTaken = true;
    }
}
