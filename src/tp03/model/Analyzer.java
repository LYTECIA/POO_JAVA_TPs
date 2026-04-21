package tp03.model;
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
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isDigit    = (c >= '0' && c <= '9');
            boolean isOperator = (c == '+' || c == '-' || c == '*' || c == '/' || c == '%');
            boolean isSpace    = (c == ' ');

            if (!isDigit && !isOperator && !isSpace) {
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
        Token t = null;
        int n = -1;         
        boolean readingN = false;

        while (t == null && index < input.length()) {
            char c = input.charAt(index);
            index++;

            if (isDigit(c)) {
                if (readingN) {
                    n = n * 10;
                } else {
                    n = 0;
                    readingN = true;
                }
                n = n + (c - '0');

            } else if (isOperator(c)) {
                if (readingN) {
                    t = new Number(n);
                    readingN = false;
                    index--;         
                } else {
                    t = tokenForOperator(c);
                }

            } else if (c == ' ') {
                if (readingN) {
                    t = new Number(n);
                    readingN = false;
                }
                skipSpaces();
            }
        }

        if (readingN) {
            t = new Number(n);
        }

        return t;
    }

    // --- méthodes auxiliaires ---

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private void skipSpaces() {
        while (index < input.length() && input.charAt(index) == ' ') {
            index++;
        }
    }

    private Token tokenForOperator(char c) {
        switch (c) {
            case '+': return new Plus();
            case '-': return new Minus();
            case '*': return new Mult();
            case '/': return new Div();
            case '%': return new Mod();
            default:  throw new AssertionError("Opérateur inconnu : " + c);
        }
    }
}
