package tp03.model;
import java.util.Arrays;
class Analyzer implements IAnalyzer {

    // ATTRIBUTS D'INSTANCE
    
    /**
     * La chaîne à découper en tokens.
     */
    private String input;
    
    /**
     * L'index sur input qui indique la position du premier caractère de la
     *  prochaine lecture de token.
     * Vaut 0 en début de lecture de input et input.length() en fin de lecture.
     */
    private int index;
    
    /**
     * Le token qui vient d'être lu.
     * Initialement null.
     */
    private Token current;
    
    /**
     * Le prochain token à lire.
     * Initialement le premier token de input.
     * Vaut null à la fin de input.
     */
    private Token next;
    
    /**
     * La version normalisée (exactement un espace entre deux tokens) de input.
     * Cette chaîne est construite au fur et à mesure de l'obtention des
     *  tokens.
     */
    private String normalizedInput;
    
    // CONSTRUCTEURS
    
    public Analyzer() {
        reinit("");
    }

    // REQUETES
    
    @Override
    public Token current() {
        return current;
    }
    
    @Override
    public String getNormalizedInput() {
        return normalizedInput;
    }
    
    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public boolean isValidInput(String s) {
    	char[] digits = new char[] {'0','1','2','3','4','5','6','7','8','9'};
    	char[] operators = new char[] {'+','-','/','*','%'};
        for(int i = 0; i < s.length(); i++) {
        	char c = s.charAt(i);
        	Arrays.sort(digits);
        	Arrays.sort(operators);
        	int idx1 = Arrays.binarySearch(digits,c);
        	int idx2 = Arrays.binarySearch(operators,c);
        	if(!(idx1 >= 0) && !(idx2>=0) && (c != ' ')){
        		return false;
        	}
        	
        }
        return true;
    }
    
    // COMMANDES
    
    @Override
    public void setInput(String s) {
        if (s == null || !isValidInput(s)) {
            throw new AssertionError();
        }
        
        reinit(s);
    }
    
    @Override
    public void toNext() {
        if (!hasNext()) {
            throw new AssertionError();
        }
        
        current = next;
        next = getNext();
        normalizedInput += current;
        if (next != null) {
            normalizedInput += " ";
        }
    }

    // OUTILS
    
    private void reinit(String s) {
        assert s != null;
        
        input = s;
        normalizedInput = "";
        index = 0;
        current = null;
        next = getNext();
    }
    
    /**
     * Construit le prochain token en lisant la chaîne input à partir de la
     *  position courante index.
     * Vous pouvez par exemple coder l'algorithme suivant :
     * 
     * Soit t le token que l'on va construire
     * Soit n la valeur entière que l'on va "peut-être" lire
     * // Rq : on pourrait lire un opérateur et non un nombre
     * la construction de t commence ici avec t <- null
     * TantQue on est en cours de construction de t Faire
     *   Soit c le caractère en position index dans input
     *   Si c est un chiffre Alors
     *     Si on est en cours de lecture de n Alors
     *       n <- n * 10
     *     Sinon
     *       la lecture de n commence ici avec n <- 0
     *     FinSi
     *     n <- n + (c - '0')
     *   Sinon
     *     Si c est un opérateur Alors
     *       Si on est en cours de lecture de n Alors
     *         t est le Nombre de valeur n
     *         la lecture de n se termine ici
     *         reculer index d'une position
     *       Sinon
     *         t est l'opérateur représenté par c
     *       FinSi
     *       la construction de t se termine ici
     *     Sinon
     *       Si c est un espace Alors
     *         Si on est en cours de lecture de n Alors
     *           t est le Nombre de valeur n
     *           la lecture de n se termine ici
     *           la construction de t se termine ici
     *         FinSi
     *         lire tous les espaces consécutifs dans input, en avançant index
     *           jusqu'au prochain caractère non espace exclus
     *       FinSi
     *     FinSi
     *   FinSi
     * FinTantQue
     * Si on est en cours de lecture de n Alors
     *   t est le Nombre de valeur n
     *   la lecture de n se termine ici
     *   la construction de t se termine ici
     * FinSi
     */
    private Token getNext() {
        ...
    }
    
    ...
}
