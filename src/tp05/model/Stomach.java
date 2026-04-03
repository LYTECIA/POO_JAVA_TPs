package tp05.model;

public class Stomach implements IStomach {
	private IEnergyStock energyStock;
	private int foodQuantity;
	private int size;
	private boolean isWorking;
	private boolean isExploded;
	
	 

    /**
     * Le stock d'énergie associé à cet estomac.
     */
    public IEnergyStock energyStock() {
    	return energyStock;
    }

    /**
     * La quantité de nourriture que contient cet estomac.
     */
    public int foodQuantity() {
    	return foodQuantity;
    }

    /**
     * Indique si cet estomac est vide.
     */
    public boolean isEmpty() {
    	return foodQuantity == 0;
    }

    /**
     * Indique si cet estomac a explosé.
     */
    public boolean isExploded(){
    	return isExploded ;
    }

    /**
     * Indique si cet estomac fonctionne.
     */
    public boolean isWorking(){
    	return isWorking;
    }

    /**
     * La capacité de cet estomac.
     */
    public int size() {
    	return size;
    }
    
    /**
     * Décrit cet estomac à l'aide d'une courte chaîne de caractères, du genre
     *  "stomach: x/y/w" où x représente la quantité de nourriture, y la taille,
     *  et w l'indication "fonctionnel" ou "détruit".
     */
    public String describe();
    
    public Stomach(int c , IEnergyStock stock) {
    	if(!(c>0 && stock != null)) {
			throw new AssertionError("erreur") ;
		}
    	size = c;
        energyStock = stock;
        foodQuantity = 0;
    	isExploded = false;
        isWorking= true;  
        }
    /**
     * Fait vivre cet estomac pour un tour de jeu.
     * @pre <pre>
     *     isWorking() </pre>
     * @post <pre>
     *     Let oldFood ::= old foodQuantity()
     *         oldLevel ::= old energyStock().crtLevel()
     *         digestedFood ::= min(oldFood, DIGESTED_FOOD)
     *         
     *     isWorking() == (oldFood > 0) || (oldLevel >= ENERGY_GAIN)
     *     foodQuantity() == oldFood - digestedFood
     *     !isExploded()
     *     oldFood > 0
     *         ==> energyStock().crtLevel() ==
     *                 oldLevel + (digestedFood - 1) * ENERGY_GAIN
     *     oldFood == 0
     *         ==> energyStock().crtLevel() ==
     *                 oldLevel - min(oldLevel, ENERGY_GAIN)
     *     size() == old size() </pre>
     */
    public void evolve() {
    	if(!isWorking) {
    		throw new AssertionError("erreur") ;
    	}
    	
    	
    }
    

    

}
