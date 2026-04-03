package tp02;

/**
 * Un encodeur selon le principe du décalage circulaire (aussi appelé technique
 *  de "César").
 * La méthode <code>buildShiftedTextFor</code> permet d'encoder un message
 *  (décalage à droite) ou de le décoder (décalage à gauche) selon la valeur
 *  actuelle du décalage.
 * @inv <pre>
 *     -ALPHABET_SIZE < getShift() < ALPHABET_SIZE
 *     getLastShiftedText() != null </pre>
 */
public class SubstCipher {

    // ATTRIBUTS STATIQUES

    /**
     * Taille de l'alphabet.
     */
    public static final int ALPHABET_SIZE = 26;

    /**
     * Caractère (majuscule) le plus fréquent en français (et en anglais).
     */
    public static final char MOST_FREQUENT_CHAR = 'E';

    // ATTRIBUTS D'INSTANCE

    private int shift;
    private String LastShiftedText;

    // CONSTRUCTEURS

    /**
     * Un encodeur de décalage 0 (i.e. l'identité).
     * @post <pre>
     *     getShift() == 0
     *     getLastShiftedText().isEmpty() </pre>
     */
    public SubstCipher() {
        this(0);
    }

    /**
     * Un encodeur de décalage <code>shift</code>.
     * @pre <pre>
     *     -ALPHABET_SIZE < shift < ALPHABET_SIZE </pre>
     * @post <pre>
     *     getShift() == shift
     *     getLastShiftedText().isEmpty() </pre>
     */
    public SubstCipher(int shift) {
    	
        if(!(-ALPHABET_SIZE < shift && shift< ALPHABET_SIZE)) {
          throw new AssertionError("shift doit etre compris entre -26 et 26");
        }
          this.shift = shift;
          LastShiftedText ="";
        }
  
    
    	

    // REQUETES

    /**
     * Le dernier texte encodé par l'encodeur avec un décalage de
     *  <code>getShift()</code>.
     */
    public String getLastShiftedText() {
        return LastShiftedText;
    }

    /**
     * Le décalage courant de l'encodeur.
     */
    public int getShift() {
        return shift;
    }

    // COMMANDES

    /**
     * Affecte un nouveau décalage à l'encodeur.
     * @pre <pre>
     *     -ALPHABET_SIZE < shift < ALPHABET_SIZE </pre>
     * @post <pre>
     *     getShift() == shift
     *     getLastShiftedText().isEmpty() </pre>
     */
    public void setShift(int shift) {
    	if(!(-ALPHABET_SIZE < shift && shift< ALPHABET_SIZE)) {
            throw new AssertionError("shift doit etre compris entre -26 et 26");
          }
    	this.shift =shift;
    	LastShiftedText = "";
    }

    /**
     * Construit une chaîne à partir de celle fournie en paramètre en décalant
     *  circulairement les caractères alphabétiques selon le décalage donné par
     *  <code>getShift()</code>.
     * Le décalage se fait à droite si <code>getShift() &geq; 0</code>, ou
     *  à gauche si <code>getShift() &lt; 0</code>.
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     getLastShiftedText() != null
     *     getLastShiftedText().length() == text.length()
     *     forall i, 0 <= i < text.length() :
     *         Let ci ::= text.charAt(i)
     *             xi ::= getLastShiftedText().charAt(i)
     *         isNonAccentedLetter(ci) ==> xi == ci décalé de getShift()
     *         !isNonAccentedLetter(ci) ==> xi == ci </pre>
     */
    public void buildShiftedTextFor(String text) {
        ...
    }

    /**
     * Configure l'encodeur pour un futur encodage.
     * @post <pre>
     *     getShift() == +abs(old getShift())
     *     getLastShiftedText().isEmpty() </pre>
     */
    public void ensurePositiveShift() {
        ...
    }

    /**
     * Configure l'encodeur pour un futur décodage.
     * @post <pre>
     *     getShift() == -abs(old getShift())
     *     getLastShiftedText().isEmpty() </pre>
     */
    public void ensureNegativeShift() {
        ...
    }

    // OUTILS

    /**
     * Calcule un décalage à partir du message donné en paramètre selon
     *  l'algorithme qui suit.
     * <ul>
     *   <li>compter l'occurrence de chaque lettre non accentuée (minuscule ou
     *       majuscule) de <code>text</code> ;
     *   </li>
     *   <li>déterminer un décalage pour encoder <code>MOST_FREQUENT_CHAR</code>
     *       par la lettre apparaissant le plus souvent dans <code>text</code>.
     *   </li>
     * </ul>
     * En cas d'égalité de fréquence de plusieurs lettres de <code>text<code>,
     *  la plus petite parmi ces lettres (pour l'ordre alphabétique) est
     *  retournée.
     * Pour un message vide, la valeur retournée est <code>0</code>.
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     text.isEmpty() ==> result == 0
     *     !text.isEmpty()
     *         ==> Let f ::= la lettre la plus fréquente de text
     *             result ==
     *                 ((f - MOST_FREQUENT_CHAR) + ALPHABET_SIZE)
     *                     % ALPHABET_SIZE </pre>
     */
    public static int guessShiftFrom(String text) {
        ...
    }

    ...
}
