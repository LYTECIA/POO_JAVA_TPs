package tp02;

import java.util.StringTokenizer;

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

    ...

    // CONSTRUCTEURS

    /**
     * Un chiffreur dont les messages <code>getClearText()</code> et
     *  <code>getCipherText()</code> retournent la chaîne vide.
     * @post <pre>
     *     getClearText().isEmpty()
     *     getCipherText().isEmpty() </pre>
     */
    public Cipher() {
        ...
    }

    // REQUETES

    /**
     * Le message courant en clair.
     * Sa valeur correspond au message décodé de <code>getCipherText()</code>.
     */
    public String getClearText() {
        ...
    }

    /**
     * Le message courant chiffré.
     * Sa valeur correspond au message encodé de <code>getClearText()</code>.
     */
    public String getCipherText() {
        ...
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
        ...
    }

    /**
     * Modification du message courant chiffré.
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     getCipherText().equals(text) </pre>
     */
    public void setCipherText(String text) {
        ...
    }

    // OUTILS

    ...
}
