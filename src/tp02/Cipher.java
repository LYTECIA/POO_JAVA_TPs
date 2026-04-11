package tp02;

import java.util.*;

/**
 * Un chiffreur/déchiffreur de messages.
 *
 * Les algorithmes utilisés sont les suivants :
 * <ul>
 * <li> pour le chiffrage :
 * <ol>
 *     <li> le message est transformé une première fois globalement par
 *         un encodeur circulaire avec un décalage aléatoire à droite ;
 *     </li>
 *     <li> le message transformé est modifié une nouvelle fois mais mot
 *         à mot, à l'aide d'un encodeur circulaire à droite dont le décalage
 *         prend les longueurs des mots successivement rencontrés.
 *     </li>
 * </ol>
 * </li>
 *
 * <li> pour le déchiffrage :
 * <ol>
 *     <li> le message est travaillé une première fois à l'aide d'un encodeur
 *         circulaire à gauche dont le décalage prend en compte la longueur des
 *         mots successifs ;
 *     </li>
 *     <li> pour annuler l'utilisation lors du chiffrage du premier encodeur
 *         circulaire on retrouve le décalage aléatoire utilisé, en s'appuyant
 *         sur le fait que la lettre la plus fréquente dans un texte français
 *         (ou anglais) est le 'e'.
 *     </li>
 * </ol>
 * </li>
 * </ul>
 * @inv <pre>
 *     getClearText() != null
 *     getCipherText() != null
 *     getClearText() est le déchiffré de getCipherText() </pre>
 */
public class Cipher {

    // ATTRIBUTS D'INSTANCE
	
     /** Le message courant en clair **/
    private String clearText;
    
	/** Le message courant chiffré **/
    private String cipherText ;

    // CONSTRUCTEURS

    /**
     * Un chiffreur dont les messages <code>getClearText()</code> et
     *  <code>getCipherText()</code> retournent la chaîne vide.
     * @post <pre>
     *     getClearText().isEmpty()
     *     getCipherText().isEmpty() </pre>
     */
    public Cipher() {
        cipherText ="";
        clearText ="";
    }

    // REQUETES

    /**
     * Le message courant en clair.
     * Sa valeur correspond au message décodé de <code>getCipherText()</code>.
     */
    public String getClearText() {
        return clearText;
    }

    /**
     * Le message courant chiffré.
     * Sa valeur correspond au message encodé de <code>getClearText()</code>.
     */
    public String getCipherText() {
        return cipherText;
    }

    // COMMANDES

    /**
     * Modification du message courant en clair.
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     getClearText().equals(text) </pre>
     */
    public void setClearText(String text) {
    	 if (text == null) {
    	        throw new AssertionError("text ne doit pas être null");
    	    }
    	    clearText = text;
    	    Random g = new Random();
    	    int shift = g.nextInt(26);
    	    SubstCipher globalCipher = new SubstCipher(shift);
    	    globalCipher.buildShiftedTextFor(text);
    	    String firstPass = globalCipher.getLastShiftedText();
    	    cipherText = shiftByWordLength(firstPass, 1);
    }

    /**
     * Modification du message courant chiffré.
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     getCipherText().equals(text) </pre>
     */
    public void setCipherText(String text) {
    	if (text == null) {
            throw new AssertionError("text ne doit pas être null");
        }
        cipherText = text;
        String firstPass = shiftByWordLength(text, -1);
        int shift = SubstCipher.guessShiftFrom(firstPass);
        SubstCipher globalCipher = new SubstCipher(-shift);
        globalCipher.buildShiftedTextFor(firstPass);
        clearText = globalCipher.getLastShiftedText();
    }

 // OUTILS
    /**
     * Applique un décalage circulaire mot à mot.
     * @param text le texte à traiter
     * @param direction +1 pour chiffrer (droite), -1 pour déchiffrer (gauche)
     */
    private String shiftByWordLength(String text, int direction) {
        StringBuilder result = new StringBuilder();
        StringTokenizer tokens = new StringTokenizer(text, " ", true);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token.equals(" ")) {
                result.append(token);
            } else {
                int wordShift = token.length() * direction;
                SubstCipher wordCipher = new SubstCipher(wordShift);
                wordCipher.buildShiftedTextFor(token);
                result.append(wordCipher.getLastShiftedText());
            }
        }
        return result.toString();
    }
}
