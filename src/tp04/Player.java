package tp04;

import util.Contract;

public class Player implements IPlayer {
	//ATTRIBUTS
	
	private boolean hasEverPlayed;
	private int lastGain;
	private int minGain;
	private int maxGain;
	private int score;
	private Dice[] dicearray; 
	
	//CONSTRUCTEUR
	
	public Player(int[] sidesNumbers){
		Contract.checkCondition(preConditionPlayer(sidesNumbers), "le tableau doit etre valide et pas vide et ses element doivent respectés les condition de contruction d'un dé");
		dicearray = createDiceArray(sidesNumbers) ;
		hasEverPlayed = false ;
		minGain =  sidesNumbers.length;
		maxGain =  sumSides(sidesNumbers) ;
		score = 0;
		}
	
	//REQUETES
	
	public boolean hasEverPlayed() {
		return hasEverPlayed;
	}

    
    public int lastGain() {
		Contract.checkCondition(hasEverPlayed, "le joueur doit avoir deja joué");
		return lastGain;
    }

    
    public int maxGain() {
    	return maxGain;
    }

    
    public int minGain() {
    	return minGain;
    }

    
    public int score() {
    	return score;
    }
    
    //COMMANDES
    
    public void addToScore() {
		Contract.checkCondition(hasEverPlayed, "le joueur doit avoir deja joué");
		score += lastGain;
    }
    
    public void playOnce(){
    	hasEverPlayed = true ;
    	int sum =0;
    	for(int i =0 ; i < dicearray.length; i++) {
    		dicearray[i].take();
    		dicearray[i].roll();
    		sum += dicearray[i].value();
    	}
    	lastGain = sum;
    }
    
    public void resetScore() {
    	hasEverPlayed = false;
    	score = 0;
    }
    
    /**
     * Vérifie que sidesNumbers satisfait les préconditions du constructeur :
     * non null, non vide, et chaque élément >= IDice.MIN_SIDES_NB.
     * @param sidesNumbers le tableau de nombres de faces à vérifier
     * @return true si toutes les préconditions sont respectées, false sinon
     */
	private boolean preConditionPlayer(int[] sidesNumbers) {
		
		if(!(sidesNumbers != null && sidesNumbers.length > 0)) {
			return false;
		}
		
		for(int i=0 ; i < sidesNumbers.length;i++) {
			if(sidesNumbers[i]< IDice.MIN_SIDES_NB) {
				  return false;
			}
		}
		return true;
	}
	
	/**
	 * Calcule la somme des éléments de sidesNumbers.
	 * @pre sidesNumbers != null && sidesNumbers.length > 0
	 * @param sidesNumbers le tableau de nombres de faces
	 * @return la somme de tous les éléments du tableau
	 */
	private int sumSides(int[] sidesNumbers) {
		int sum = 0;
		for(int i=0 ; i < sidesNumbers.length;i++) {
			sum += sidesNumbers[i];
		}
		return sum;
	}
	
	/**
	 * Crée un tableau de dés dont le nombre de faces de chaque dé
	 * est décrit par sidesNumbers.
	 * @pre sidesNumbers != null && sidesNumbers.length > 0
	 *      forall f in sidesNumbers : f >= IDice.MIN_SIDES_NB
	 * @param sidesNumbers le tableau de nombres de faces
	 * @return un tableau de dés correspondant à sidesNumbers
	 */
	private Dice[] createDiceArray(int[] sidesNumbers) {
		Dice[] result= new Dice[sidesNumbers.length];
		
		for(int i=0 ; i < sidesNumbers.length;i++) {
			result[i] = new Dice(sidesNumbers[i]);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
