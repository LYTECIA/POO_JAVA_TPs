package tp01;

/**
 * Propriétés invariantes.
 * - l’âge du tamagotchi varie entre 0 et MAX_AGE :
 *     0 <= this.age() <= MAX_AGE
 * - l’énergie du tamagotchi varie entre 0 et MAX_ENERGY :
 *     0 <= this.energy() <= MAX_ENERGY
 * - le tamagotchi est vivant ssi son énergie est strictement positive
 *   et son âge non maximal :
 *     this.isAlive() <==> 0 < this.energy() && this.age() < MAX_AGE
 */
public class Tamagotchi {
    
    // ATTRIBUTS STATIQUES
    
    /**
     * La durée de vie maximale de tous les tamagotchis.
     */
    public static final int MAX_AGE = 10;
    
    /**
     * Le niveau maximal d’énergie de tous les tamagotchis.
     */
    public static final int MAX_ENERGY = 5;
    
    // ATTRIBUTS D'INSTANCE
    
    private int age;
    
    private int energy;
  
    // CONSTRUCTEURS
    
    /**
     * Un tamagotchi qui vient de naître.
     * En sortie
     * - l’âge du tamagotchi est minimal :
     *     this.age() == 0
     * - l’énergie du tamagotchi est maximale :
     *     this.energy() == MAX_ENERGY
     */
    public Tamagotchi() {
    	age=0;
    	energy=MAX_ENERGY;
      
    }

    // REQUETES
    
    /**
     * L’âge du tamagotchi.
     */
    public int age() {
        return age;
    }

    /**
     * Une description textuelle du tamagotchi.
     * En sortie
     * - la valeur retournée est une chaîne de caractères de la forme
     *     Tama[âge:a(A) ; énergie:e(m/M)]
     *   où a et A représentent l'âge courant et l'âge maximal du tamagotchi,
     *   et m, e et M représentent respectivement son énergie minimale, son
     *   énergie courante et son énergie maximale
     */
    public String describe() {
    	 return "Tama[âge:" + this.age() + "(" + MAX_AGE + ") ; énergie:" 
    	           + this.energy() + "(0/" + MAX_ENERGY + ")]";
    }

    /**
     * L’énergie du tamagotchi.
     */
    public int energy() {
    	return energy;
    }

    /**
     * Indique si le tamagotchi est bien vivant.
     */
    public boolean isAlive() {
        return 0 < this.energy() && this.age() < MAX_AGE;
    }

    // COMMANDES
    
    /**
     * Nourrit le tamagotchi.
     * En entrée
     * - le tamagotchi est vivant :
     *     this.isAlive()
     * En sortie
     * - l’âge du tamagotchi n’a pas changé :
     *     this.age() == old this.age()
     * - l’énergie du tamagotchi est maximale :
     *     this.energy() == MAX_ENERGY
     */
    public void eat() {
    	if (!this.isAlive()) {
            throw new AssertionError("Impossible de nourrir un tamagotchi mort.");
        }
        this.energy = MAX_ENERGY;
    }

    /**
     * <pre>
     * Passe un tour de jeu pour le tamagotchi.
     * En entrée
     * - le tamagotchi est vivant :
     *     this.isAlive()
     * En sortie
     * - l’âge du tamagotchi a augmenté de 1 :
     *     this.age() == old this.age() + 1
     * - l’énergie du tamagotchi a diminué de 1 :
     *     this.energy() == old this.energy() - 1
     *  </pre>
     */
    public void evolve() {
        if(!this.isAlive()) {
        	throw new AssertionError("Impossible de faire évoluer un tamagotchi mort");
        }
        age+=1;
    	energy-=1;
    }
}

