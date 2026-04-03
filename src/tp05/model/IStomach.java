package tp05.model;

/**
 * Modélise un estomac de tamagotchi.
 * Un estomac est un organe que l'on peut remplir de nourriture, pour qu'il
 *  la digère, produisant ainsi de l'énergie qu'il emmagasine dans le réservoir
 *  d'énergie du corps auquel il est rattaché.
 * La taille d'un estomac ainsi que le stock d'énergie qui lui est associé
 *  sont déterminés une fois pour toutes à sa création.
 * Chaque unité de nourriture absorbée par l'estomac produira
 *  <code>ENERGY_GAIN</code> unités d'énergie, et l'estomac peut digérer
 *  à chaque tour de jeu une quantité de nourriture égale
 *  à <code>DIGESTED_FOOD</code>.
 * Durant un tour de jeu, l'estomac commence par absorber une unité de
 *  nourriture pour son propre fonctionnement pendant ce tour, puis il convertit
 *  <code>DIGESTED_FOOD - 1</code> unités de nourriture en énergie, qu'il stocke
 *  dans le réservoir d'énergie.
 * Si la nourriture est en quantité suffisante, l'estomac va donc produire de
 *  l'énergie, mais s'il y a peu de nourriture, l'estomac produira peu, voire
 *  pas du tout, d'énergie.
 * À la limite, s'il n'y a pas de nourriture dans l'estomac, celui-ci va
 *  consommer de l'énergie sans en produire.
 * Pire ! S'il n'y a pas d'énergie dans le réservoir, ni de nourriture dans
 *  l'estomac, pendant plus d'un tour, ce dernier arrête de fonctionner.
 * Enfin, si un estomac tente d'absorber plus de nourriture qu'il ne peut en
 *  contenir, il explose et n'est donc plus fonctionnel.
 * @inv <pre>
 *     describe() != null
 *     energyStock() != null
 *     size() > 0
 *     0 <= foodQuantity() <= size()
 *     isEmpty() <==> (foodQuantity() == 0)
 *     isExploded() ==> !isWorking() </pre>
 * @cons <pre>
 *     $DESC$
 *         Un estomac vide et fonctionnel, de capacité c.
 *         Le réservoir d'énergie est donné par stock
 *     $ARGS$ int c, IEnergyStock stock
 *     $PRE$
 *         c > 0
 *         stock != null
 *     $POST$
 *         size() == c
 *         energyStock() == stock
 *         foodQuantity() == 0
 *         !isExploded()
 *         isWorking() </pre>
 */
public interface IStomach {

    // ATTRIBUTS STATIQUES

    /**
     * Quantité d'énergie produite par la digestion d'une unité de nourriture.
     */
    int ENERGY_GAIN = 2;

    /**
     * Quantité maximale de nourriture convertie en énergie par tour de jeu.
     */
    int DIGESTED_FOOD = 4;

    // REQUETES

    /**
     * Décrit cet estomac à l'aide d'une courte chaîne de caractères, du genre
     *  "stomach: x/y/w" où x représente la quantité de nourriture, y la taille,
     *  et w l'indication "fonctionnel" ou "détruit".
     */
    String describe();

    /**
     * Le stock d'énergie associé à cet estomac.
     */
    IEnergyStock energyStock();

    /**
     * La quantité de nourriture que contient cet estomac.
     */
    int foodQuantity();

    /**
     * Indique si cet estomac est vide.
     */
    boolean isEmpty();

    /**
     * Indique si cet estomac a explosé.
     */
    boolean isExploded();

    /**
     * Indique si cet estomac fonctionne.
     */
    boolean isWorking();

    /**
     * La capacité de cet estomac.
     */
    int size();

    // COMMANDES

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
    void evolve();

    /**
     * Remplit cet estomac de qty unités de nourriture.
     * @pre <pre>
     *     isWorking()
     *     qty >= 0 </pre>
     * @post <pre>
     *     foodQuantity() == min(size(), old foodQuantity() + qty)
     *     isExploded() == (old foodQuantity() + qty > size())
     *     energyStock().crtLevel() == old energyStock().crtLevel()
     *     size() == old size() </pre>
     */
    void fill(int qty);
}
